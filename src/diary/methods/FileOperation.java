package diary.methods;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import diary.constants.Constants;
import diary.constants.Globals;
import diary.constants.XMLIdentifier;
import diary.data.PainEntryData;
import diary.data.Settings;
import diary.fileFilters.FileTypeFilter;
import diary.gui.table.Table;
import diary.history.History;
import diary.patientdata.PatientData;
import giantsweetroll.date.Date;
import giantsweetroll.files.FileManager;
import giantsweetroll.message.MessageManager;
import giantsweetroll.xml.dom.XMLManager;

public class FileOperation
{
	public static final boolean entryExists(PatientData patient, PainEntryData entry)
	{
		File file = new File(Methods.generatePainDataFilePathName(patient, entry));
		if (file.exists())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static final boolean dataExists(String path)
	{
		File file = new File(path);
		if (file.exists())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static final void saveSettings(Settings setting)
	{
		File file = new File(Constants.SETTINGS_FOLDER_PATH + Constants.SETTINGS_FILE_NAME);
		
		if (!file.getParentFile().exists())
		{
			file.getParentFile().mkdirs();
		}
		
		try 
		{
			XMLManager.exportXML(setting.getXMLDocument(), file, 5);
		} 
		catch (TransformerException | ParserConfigurationException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static final Document loadSettingsDocument() throws ParserConfigurationException, SAXException, IOException
	{
		return XMLManager.createDocument(Constants.SETTINGS_FOLDER_PATH + Constants.SETTINGS_FILE_NAME, false);
	}
	
	public static final void exportPainData(PatientData patient, PainEntryData entry)
	{
		File file = new File(Methods.generatePainDataFolderPathName(patient, entry));
//		System.out.println(entry.toString());
		if (!file.exists())				//Check if the folder directory exists, if not make it
		{
			file.mkdirs();
		}
		
		file = new File(Methods.generatePainDataFilePathName(patient, entry));
		try 
		{
			XMLManager.exportXML(entry.getXMLDocument(), file, 5);
		} 
		catch (TransformerException e) 
		{
			e.printStackTrace();
			MessageManager.showErrorDialog(e);
		}
	}
	
	public static final boolean defaultLanguageExists()
	{
		File file = new File(Constants.LANGUAGE_FOLDER_PATH);
		
		if (file.exists())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static List<PainEntryData> getListOfEntries(PatientData patient, Date from, Date to)
	{
		List<PainEntryData> list = new ArrayList<PainEntryData>();
		try
		{
			//Filtering Start
			String userDatabasePath = Globals.setting.getDataMap().get(Settings.DATABASE_PATH) + File.separator + patient.getID() + File.separator;
			//Get year range
			List<String> legibleYears = new ArrayList<String>();
			FileManager.getListOfFiles(legibleYears, 
										userDatabasePath,
										false, FileManager.FOLDER_ONLY, 
										FileManager.NAME_ONLY);
//			MessageManager.printLine("Size of years: " + legibleYears.size());
			for (int i=0; i<legibleYears.size(); i++)
			{
				try
				{
					int yearNow = Integer.parseInt(legibleYears.get(i));
					int yearMin = from.getYear();
					int yearMax = to.getYear();
					if (yearNow < yearMin || yearNow > yearMax)
					{
						legibleYears.remove(i);		//Remove Illegible year
						i = -1;			//Reset index to loop from beginning of array (it's -1 because at the end of loop will be added by 1 = 0)
					}
				}
				catch (NumberFormatException ex)
				{
					legibleYears.remove(i);		//Remove Illegible year
					i = -1;			//Reset index to loop from beginning of array (it's -1 because at the end of loop will be added by 1 = 0)
				}
			}
			
			//Add leading zeroes to String, then sort
			Methods.addZeroesToList(legibleYears);
			Collections.sort(legibleYears);
	//		MessageManager.printLine("Size of elligible years: " + legibleYears.size());
			
			//Get month range
			LinkedHashMap<String, List<String>> legibleMonthsMap = new LinkedHashMap<String, List<String>>();
	//		MessageManager.printLine("Size of eligible months before: " + legibleMonthsMap.size());
			if (legibleYears.size() == 1)
			{
				ArrayList<String> legibleMonths = new ArrayList<String>();
				FileManager.getListOfFiles(legibleMonths, userDatabasePath + legibleYears.get(0), false, FileManager.FOLDER_ONLY, FileManager.NAME_ONLY);
		//		MessageManager.printLine("Number of months: " + legibleMonths.size());
				
				if (legibleYears.get(0).equals(Integer.toString(from.getYear())))			//If the first legible year is the same as the min year
				{
					for (int i=0; i<legibleMonths.size(); i++)
					{
				//		MessageManager.printLine("Iteration month: " + i);
						try
						{
							int monthNow = Integer.parseInt(legibleMonths.get(i));
							int monthMin = from.getMonth();
							int monthMax = to.getMonth();
							if (legibleYears.get(0).equals(Integer.toString(to.getYear())))		//If the only legible year is the same as the max year
							{
								if (monthNow < monthMin || monthNow > monthMax)
								{
						//			MessageManager.printLine("Month " + legibleMonths.get(i) + " is not within range of " + monthMin + " and " + monthMax);
									legibleMonths.remove(i);	//remove Illegible month
									i = -1;		//Reset index to loop from beginning of array (it's -1 because at the end of loop will be added by 1 = 0)
								}
							}
							else
							{
								if (monthNow < monthMin)
								{
				//					MessageManager.printLine("Month " + legibleMonths.get(i) + " is less than " + monthMin);
									legibleMonths.remove(i);	//remove Illegible month
									i = -1;		//Reset index to loop from beginning of array (it's -1 because at the end of loop will be added by 1 = 0)
								}
							}
						}
						catch (NumberFormatException ex)
						{
							legibleMonths.remove(i);	//remove Illegible month
							i = -1;		//Reset index to loop from beginning of array (it's -1 because at the end of loop will be added by 1 = 0)
						}
					}
				}
				//If not, accept all months
				
//				MessageManager.printLine("Number of months after filter: " + legibleMonths.size() + " (" + legibleYears.get(0) + ")");
				legibleMonthsMap.put(legibleYears.get(0), legibleMonths);
			}
			else
			{
	//			System.out.println(legibleYears.size());
				for (int i=0; i<legibleYears.size(); i++)
				{
					List<String> legibleMonths = new ArrayList<String>();
					FileManager.getListOfFiles(legibleMonths, userDatabasePath + legibleYears.get(i), false, FileManager.FOLDER_ONLY, FileManager.NAME_ONLY);
					/*
					 * Program only needs to check the first year for the min month,
					 * and the last year for the max month, 
					 * as all months in the year between will be selected regardless
					 */
					if (i==0)
					{
						for (int a=0; a<legibleMonths.size(); a++)
						{
							try
							{
								int monthNow = Integer.parseInt(legibleMonths.get(a));
								int monthMin = from.getMonth();
								if (monthNow < monthMin)
								{
									legibleMonths.remove(a);	//remove Illegible month
									a = -1;		//Reset index to loop from beginning of array (it's -1 because at the end of loop will be added by 1 = 0)
								}
							}
							catch (NumberFormatException ex)
							{
								legibleMonths.remove(a);	//remove Illegible month
								a = -1;		//Reset index to loop from beginning of array (it's -1 because at the end of loop will be added by 1 = 0)
							}
						}
					}
					else if (i==legibleYears.size()-1)
					{
						for (int a=0; a<legibleMonths.size(); a++)
						{
							try
							{
								int monthNow = Integer.parseInt(legibleMonths.get(a));
								int monthMax = to.getMonth();
								if (monthNow > monthMax)
								{
									legibleMonths.remove(a);	//remove Illegible month
									a = -1;		//Reset index to loop from beginning of array (it's -1 because at the end of loop will be added by 1 = 0)
								}
							}
							catch (NumberFormatException ex)
							{
								legibleMonths.remove(a);	//remove Illegible month
								a = -1;		//Reset index to loop from beginning of array (it's -1 because at the end of loop will be added by 1 = 0)
							}
						}
					}
					
					//Add leading zeroes to String, then sort
					Methods.addZeroesToList(legibleMonths);
					Collections.sort(legibleMonths);
					
					legibleMonthsMap.put(legibleYears.get(i), legibleMonths);
				}
			}
	//		MessageManager.printLine("Size of eligible months after: " + legibleMonthsMap.size());
			
			//Get day range
//			LinkedHashMap<LinkedHashMap<String, String>, List<String>> legibleDaysMap = new LinkedHashMap<LinkedHashMap<String, String>, List<String>>();
			LinkedHashMap<String, LinkedHashMap<String, List<String>>> legibleDaysMap = new LinkedHashMap<String, LinkedHashMap<String, List<String>>>();
			/*
			 * Program only needs to check the first month of the first year to get the min day,
			 * and the last month of the last year to get the max day,
			 * as all other days in the months between will be selected regardless
			 */
			
			for (Map.Entry<String, List<String>> entry : legibleMonthsMap.entrySet())
			{	
				LinkedHashMap<String, List<String>> monthDayMap = new LinkedHashMap<String, List<String>>();
				for (int i=0; i<entry.getValue().size(); i++)
				{
					List<String> legibleDays = new ArrayList<String>();
					String path = entry.getValue().get(i);
			//		System.out.println(path);
					path = Methods.removeFirstZeroFromString(path);		//Remove first trailing zero (that was previously added for sorting)
			//		System.out.println(path);
					path = userDatabasePath + entry.getKey() + File.separator + path;
			//		System.out.println(path);
					FileManager.getListOfFiles(legibleDays, path, false, FileManager.FOLDER_ONLY, FileManager.NAME_ONLY);
		//			MessageManager.printLine("Amount of legible days from month " + entry.getValue().get(i) + "before filter: " + legibleDays.size());
					
					if (entry.getKey().equals(Integer.toString(from.getYear())))		//if the first eligible year is equal to the min year
					{
						if (entry.getValue().get(i).equals(Integer.toString(from.getMonth())))		//If the month is equal to the min month
						{
							int dayMin = from.getDay();
							for (int a=0; a<legibleDays.size(); a++)
							{
								try
								{
									int dayNow = Integer.parseInt(legibleDays.get(a));
									if (dayNow < dayMin)		//If day is less than the min day
									{
										legibleDays.remove(a);
										a = -1;
									}
								}
								catch (NumberFormatException ex)
								{
									legibleDays.remove(a);
									a = -1;
								}
							}
						}
					}
					if (entry.getKey().equals(Integer.toString(to.getYear())))	//if the last eligible year is equal to the max year
					{
						if (entry.getValue().get(i).equals(Integer.toString(to.getMonth())))	//if the month is equal to the max month
						{
							int dayMax = to.getDay();
							for (int a=0; a<legibleDays.size(); a++)
							{
								try
								{
									int dayNow = Integer.parseInt(legibleDays.get(a));
									if (dayNow > dayMax)		//If day is greater than the max day
									{
										legibleDays.remove(a);
										a = -1;
									}
								}
								catch (NumberFormatException ex)
								{
									legibleDays.remove(a);
									a = -1;
								}
							}
						}
					}
					
					//Add leading zeroes to String, then sort
					Methods.addZeroesToList(legibleDays);
					Collections.sort(legibleDays);
					
					monthDayMap.put(entry.getValue().get(i), legibleDays);
				}
				legibleDaysMap.put(entry.getKey(), monthDayMap);
			}
			//Filtering end
			
			//Gather list of files from each folder
			List<String> filePaths = new ArrayList<String>();
			for (Map.Entry<String, LinkedHashMap<String, List<String>>> entryYear : legibleDaysMap.entrySet())
			{
				//Remove leading zeroes
				String year = entryYear.getKey();
				if (year.substring(0, 1).equals("0"))
				{
					year = year.substring(1);
				}
				
				for (Map.Entry<String, List<String>> entryMonth : entryYear.getValue().entrySet())
				{	
					//Remove leading zeroes
					String month = entryMonth.getKey();
					if (month.substring(0, 1).equals("0"))
					{
						month = month.substring(1);
					}
					
					for (int i=0; i<entryMonth.getValue().size(); i++)
					{
						//Remove leading zeroes
						String day = entryMonth.getValue().get(i);
						if (day.substring(0, 1).equals("0"))
						{
							day = day.substring(1);
						}
						
						List<String> fileList = new ArrayList<String>();
//						FileManager.getListOfFiles(fileList, userDatabasePath + entryYear.getKey() + File.separator + entryMonth.getKey() + File.separator + entryMonth.getValue().get(i), false, FileManager.FILE_ONLY, FileManager.ABSOLUTE_PATH);
						FileManager.getListOfFiles(fileList, userDatabasePath + year + File.separator + month + File.separator + day, false, FileManager.FILE_ONLY, FileManager.ABSOLUTE_PATH);
						for (int a=0; a<fileList.size(); a++)
						{
							filePaths.add(fileList.get(a));
						}
					}
				}
			}
			
			//Parse into PainEntryData
			for (int i=0; i<filePaths.size(); i++)
			{
				try
				{
					list.add(new PainEntryData(XMLManager.createDocument(filePaths.get(i), false)));
				}
				catch (ParserConfigurationException | SAXException | IOException ex)
				{
					ex.printStackTrace();
				}
			}
			
	//		Collections.sort(list, new SortByDate());
			
//			MessageManager.printLine("Size of entries: " + list.size());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return list;
	}

	public static void deleteEntries(List<String> filePaths)
	{
		for (int i=0; i<filePaths.size(); i++)
		{
			File file = new File(filePaths.get(i));
			file.delete();
		}
	}
	
	public static void deleteEntry(String filePath)
	{
		File file = new File(filePath);
		file.delete();
	}
	
	public static List<PatientData> getListOfPatients()
	{
		List<PatientData> list = new ArrayList<PatientData>();
		List<String> files = new ArrayList<String>();
		
		try
		{
			FileManager.getListOfFiles(files, Globals.setting.getDataMap().get(Settings.DATABASE_USERS_PATH), false, FileManager.FILE_ONLY, FileManager.ABSOLUTE_PATH);
			
			for (int i=0; i<files.size(); i++)
			{
				try 
				{
					list.add(new PatientData(XMLManager.createDocument(files.get(i), false)));
				} 
				catch (ParserConfigurationException | SAXException | IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
		catch(NullPointerException ex) {};
		
		return list;
	}
	
	public static void savePatientData(PatientData patientData)
	{
		try
		{
			XMLManager.exportXML(patientData.getXMLDocument(), new File(Globals.setting.getDataMap().get(Settings.DATABASE_USERS_PATH) + File.separator + patientData.getDataMap().get(PatientData.MEDICAL_RECORD_ID) + ".xml"), 5);
		} 
		catch (TransformerException e) 
		{
			e.printStackTrace();
		}
	}
	public static List<PatientData> loadPatients()
	{
		List<PatientData> list = new ArrayList<PatientData>();
		List<String> files = new ArrayList<String>();
		
		FileManager.getListOfFiles(files, Globals.setting.getDataMap().get(Settings.DATABASE_USERS_PATH) + File.separator, false, FileManager.FILE_ONLY, FileManager.ABSOLUTE_PATH);
		
		for (int i=0; i<files.size(); i++)
		{
			try
			{
				list.add(new PatientData(XMLManager.createDocument(files.get(i), false)));
			}
			catch(Exception ex)
			{
			}
		}
		
		return list;
	}
	public static void deletePatientData(String medID)
	{
		File file = new File (Globals.setting.getDataMap().get(Settings.DATABASE_USERS_PATH) + File.separator + medID + ".xml");
		file.delete();
	}
	
	public static void saveHistory(History history, PatientData patient)
	{
		try
		{
			File file = new File(Constants.HISTORY_FOLDER_PATH + patient.getID() + File.separator + history.getFileName());
			
			if (!file.exists())
			{
				//file.createNewFile();
				file.getParentFile().mkdirs();
			}
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			
			List<String> historyList = history.getHistory();
			
			for (String item : historyList)
			{
				bw.write(item);
				bw.newLine();
			}
			
			bw.close();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
	public static void updateHistory(History history, PatientData patient, String item)
	{
		history.add(item);
		FileOperation.saveHistory(history, patient);
	}
	
	public static List<String> loadTextFile(File file)
	{
		List<String> list = new ArrayList<String>();
		
		try
		{
			Scanner sc = new Scanner(file);
			
			while(sc.hasNext())
			{
				list.add(sc.nextLine());
			}
			sc.close();
		}
		catch(IOException ex)
		{
	//		ex.printStackTrace();
		}
		
		return list;
	}
	
	public static void exportImage(BufferedImage image)
	{
		LookAndFeel oldLF = UIManager.getLookAndFeel();
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception ex) {}
		JFileChooser jfc = new JFileChooser();
		jfc.setCurrentDirectory(new File("." + File.separator));
		
		int response = jfc.showDialog(null, Methods.getLanguageText(XMLIdentifier.SAVE_TEXT));
		if (response == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				String extension = FileOperation.getExtension(jfc.getSelectedFile());
				if (!extension.equalsIgnoreCase("jpg") && !extension.equalsIgnoreCase("png"))
				{
					extension = "jpg";
					jfc.setSelectedFile(new File(jfc.getSelectedFile().getAbsolutePath() + "." + extension));
				}
				ImageIO.write(image, extension, jfc.getSelectedFile());
			}
			catch(IOException ex) 
			{
				ex.printStackTrace();
				MessageManager.showErrorDialog(ex);
			}
		}
		
		try
		{
			UIManager.setLookAndFeel(oldLF);
		}
		catch(Exception ex) {}
	}
	
	public static String getExtension(File file)
	{
		String filePath = file.getName();
		int index = filePath.indexOf(".");
		
		return filePath.substring(index+1, filePath.length());
	}
	
	public static void exportTableAsExcel(Table table)
	{
		//Get Items in Table (ignore boolean cell)
		List<List<String>> items = new ArrayList<List<String>>();
		
		for (int i=0; i<table.getRowCount(); i++)
		{
			List<String> subList = new ArrayList<String>();
			for (int a=1; a<table.getColumnCount(); a++)
			{
				subList.add(table.getValueAt(i, a).toString());
			}
			items.add(subList);
		}
		
		//Create File Dialog
		LookAndFeel oldLF = UIManager.getLookAndFeel();
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception ex) {}
		
		JFileChooser jfc = new JFileChooser();
		jfc.setCurrentDirectory(new File("." + File.separator));
		jfc.addChoosableFileFilter(new FileTypeFilter(".xls", "Microsoft Excel Documents"));
		
		int response = jfc.showDialog(null, Methods.getLanguageText(XMLIdentifier.SAVE_TEXT));
		if (response == JFileChooser.APPROVE_OPTION)
		{
			//Save File
			File file = new File(jfc.getSelectedFile().getAbsolutePath());
			String[] headers = Methods.getTableHeaders();
			try
			{
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				
				//Write table headers
				for (int i=1; i<headers.length; i++)
				{
					bw.write(headers[i]);
					bw.write("	");
				}
				bw.newLine();
				//Write table cells
				for (List<String> subArr : items)
				{
					for (String item : subArr)
					{
						bw.write(item);
						bw.write("	");
					}
					bw.newLine();
				}
				bw.close();
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		
		try
		{
			UIManager.setLookAndFeel(oldLF);
		}
		catch(Exception ex) {}
	}
	
	public static void exportPainEntriesAsTxt(PatientData patient, List<PainEntryData> entries)
	{
		LookAndFeel oldLF = UIManager.getLookAndFeel();
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception ex) {}
		
		JFileChooser jfc = new JFileChooser();
		jfc.setCurrentDirectory(new File("." + File.separator));
		
		int response = jfc.showDialog(null, Methods.getLanguageText(XMLIdentifier.SAVE_TEXT));
		if (response == JFileChooser.APPROVE_OPTION)
		{
			BufferedWriter bw = null;
			try
			{
				String extension = FileOperation.getExtension(jfc.getSelectedFile());
				if (!extension.equalsIgnoreCase("csv"))
				{
					extension = "csv";
					jfc.setSelectedFile(new File(jfc.getSelectedFile().getAbsolutePath() + "." + extension));
				}
				
				//Export
				bw = new BufferedWriter(new FileWriter(jfc.getSelectedFile()));
				//Write Headers
				String arr[] = Methods.getExportTableHeaders();
				for (String str : arr)
				{
					bw.write(str);
					bw.write("\t");
				}
				bw.newLine();
				//Convert entries
				String arr2[][] = new String[entries.size()][];
				for (int i=0; i<entries.size(); i++)
				{
					arr2[i] = entries.get(i).getDataAsStringArrayForTableExport();
				}
				//Write to file
				for (String[] subArr : arr2)
				{
					bw.write(patient.toString());
					bw.write("\t");
					for(String str : subArr)
					{
						bw.write(str);
						bw.write("\t");
					}
					bw.newLine();
				}
			}
			catch(IOException ex) 
			{
				ex.printStackTrace();
				MessageManager.showErrorDialog(ex);
			}
			finally
			{
				if(bw!=null)
				{
					try
					{
						bw.flush();
						bw.close();
					} catch (IOException e) 
					{
						e.printStackTrace();
					}
				}
			}
		}
		
		try
		{
			UIManager.setLookAndFeel(oldLF);
		}
		catch(Exception ex) {}
	}
}
