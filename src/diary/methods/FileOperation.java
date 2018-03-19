package diary.methods;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
import diary.constants.PainDataIdentifier;
import diary.constants.XMLIdentifier;
import diary.data.PainEntryData;
import diary.data.Settings;
import diary.fileFilters.FileTypeFilter;
import diary.gui.MainFrame;
import diary.gui.table.Table;
import diary.history.History;
import diary.patientdata.PatientData;
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
		try 
		{
			XMLManager.exportXML(setting.getXMLDocument(), new File(Constants.SETTINGS_FOLDER_PATH + Constants.SETTINGS_FILE_NAME), 5);
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
		if (!file.exists())				//Check if the folder directory exists, if not make it
		{
			file.mkdirs();
		}
		
		file = new File(Methods.generatePainDataFilePathName(patient, entry));
		try 
		{
			XMLManager.exportXML(entry.getDocumentForm(), file, 5);
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
	
	public static List<PainEntryData> getListOfEntries(PatientData patient, LinkedHashMap<String, String> dateFromMap, LinkedHashMap<String, String> dateToMap)
	{
		List<PainEntryData> list = new ArrayList<PainEntryData>();
		try
		{
			//Filtering Start
			String userDatabasePath = MainFrame.setting.getDataMap().get(Settings.DATABASE_PATH) + File.separator + patient.getID() + File.separator;
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
					int yearMin = Integer.parseInt(dateFromMap.get(PainDataIdentifier.DATE_YEAR));
					int yearMax = Integer.parseInt(dateToMap.get(PainDataIdentifier.DATE_YEAR));
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
//			MessageManager.printLine("Size of elligible years: " + legibleYears.size());
			
			//Get month range
			LinkedHashMap<String, List<String>> legibleMonthsMap = new LinkedHashMap<String, List<String>>();
		//	MessageManager.printLine("Size of eligible months before: " + legibleMonthsMap.size());
			if (legibleYears.size() == 1)
			{
				List<String> legibleMonths = new ArrayList<String>();
				FileManager.getListOfFiles(legibleMonths, userDatabasePath + legibleYears.get(0), false, FileManager.FOLDER_ONLY, FileManager.NAME_ONLY);
		//		MessageManager.printLine("Number of months: " + legibleMonths.size());
				for (int i=0; i<legibleMonths.size(); i++)
				{
			//		MessageManager.printLine("Iteration month: " + i);
					try
					{
						int monthNow = Integer.parseInt(legibleMonths.get(i));
						int monthMin = Integer.parseInt(dateFromMap.get(PainDataIdentifier.DATE_MONTH));
						int monthMax = Integer.parseInt(dateToMap.get(PainDataIdentifier.DATE_MONTH));
						if (legibleYears.get(0).equals(dateToMap.get(PainDataIdentifier.DATE_YEAR)))		//If the only legible year is the same as the max year
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
			//	MessageManager.printLine("Number of months after filter: " + legibleMonths.size());
				legibleMonthsMap.put(legibleYears.get(0), legibleMonths);
			}
			else
			{
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
								int monthMin = Integer.parseInt(dateFromMap.get(PainDataIdentifier.DATE_MONTH));
								if (monthNow < monthMin)
								{
									legibleMonths.remove(a);	//remove Illegible month
									i = -1;		//Reset index to loop from beginning of array (it's -1 because at the end of loop will be added by 1 = 0)
								}
							}
							catch (NumberFormatException ex)
							{
								legibleMonths.remove(a);	//remove Illegible month
								i = -1;		//Reset index to loop from beginning of array (it's -1 because at the end of loop will be added by 1 = 0)
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
								int monthMax = Integer.parseInt(dateToMap.get(PainDataIdentifier.DATE_MONTH));
								if (monthNow > monthMax)
								{
									legibleMonths.remove(a);	//remove Illegible month
									i = -1;		//Reset index to loop from beginning of array (it's -1 because at the end of loop will be added by 1 = 0)
								}
							}
							catch (NumberFormatException ex)
							{
								legibleMonths.remove(a);	//remove Illegible month
								i = -1;		//Reset index to loop from beginning of array (it's -1 because at the end of loop will be added by 1 = 0)
							}
						}
					}
					legibleMonthsMap.put(legibleYears.get(i), legibleMonths);
				}
			}
		//	MessageManager.printLine("Size of eligible months after: " + legibleMonthsMap.size());
			
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
					FileManager.getListOfFiles(legibleDays, userDatabasePath + entry.getKey() + File.separator + entry.getValue().get(i), false, FileManager.FOLDER_ONLY, FileManager.NAME_ONLY);
		//			MessageManager.printLine("Amount of legible days from month " + entry.getValue().get(i) + "before filter: " + legibleDays.size());
					
					if (entry.getKey().equals(dateFromMap.get(PainDataIdentifier.DATE_YEAR)))		//if the first eligible year is equal to the min year
					{
						if (entry.getValue().get(i).equals(dateFromMap.get(PainDataIdentifier.DATE_MONTH)))		//If the month is equal to the min month
						{
							int dayMin = Integer.parseInt(dateFromMap.get(PainDataIdentifier.DATE_DAY));
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
					else if (entry.getKey().equals(dateToMap.get(PainDataIdentifier.DATE_YEAR)))	//if the last eligible year is equal to the max year
					{
						if (entry.getValue().get(i).equals(dateToMap.get(PainDataIdentifier.DATE_MONTH)))	//if the month is equal to the max month
						{
							int dayMax = Integer.parseInt(dateToMap.get(PainDataIdentifier.DATE_DAY));
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
					
					monthDayMap.put(entry.getValue().get(i), legibleDays);
				}
				legibleDaysMap.put(entry.getKey(), monthDayMap);
			}
			
			/*
			for (Map.Entry<String, List<String>> entry : legibleMonthsMap.entrySet())
			{
				LinkedHashMap<String, String> yearMonthMap = new LinkedHashMap<String, String>();
				int iteration = 0;
				for (int b=0; b<entry.getValue().size(); b++)
				{
					List<String> legibleDays = new ArrayList<String>();
					MessageManager.printLine(MainFrame.setting.getDataMap().get(Settings.DATABASE_PATH) + File.separator + entry.getKey() + File.separator + entry.getValue().get(b));
					FileManager.getListOfFiles(legibleDays, MainFrame.setting.getDataMap().get(Settings.DATABASE_PATH) + File.separator + entry.getKey() + File.separator + entry.getValue().get(b), false, FileManager.FOLDER_ONLY, FileManager.NAME_ONLY);
					MessageManager.printLine("Number of days before filter: " + legibleDays.size());
							
					if (iteration==0)
					{
						for (int a=0; a<legibleDays.size(); a++)
						{
							try
							{
								int dayNow = Integer.parseInt(legibleDays.get(a));
								int dayMin = Integer.parseInt(dateFromMap.get(PainDataIdentifier.DATE_DAY));
								if (dayNow < dayMin)
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
					
					if (iteration==legibleMonthsMap.size()-1)
					{
						for (int a=0; a<legibleDays.size(); a++)
						{
							try
							{
								int dayNow = Integer.parseInt(legibleDays.get(a));
								int dayMax = Integer.parseInt(dateToMap.get(PainDataIdentifier.DATE_DAY));
								if (dayNow > dayMax)
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
					yearMonthMap.put(entry.getKey(), entry.getValue().get(b));
					legibleDaysMap.put(yearMonthMap, legibleDays);
					iteration++;
					MessageManager.printLine("Number of legible days after filter: " + legibleDays.size());
				}
			}
			*/
			//Filtering end
			
			//Gather list of files from each folder
			List<String> filePaths = new ArrayList<String>();
			for (Map.Entry<String, LinkedHashMap<String, List<String>>> entryYear : legibleDaysMap.entrySet())
			{
				for (Map.Entry<String, List<String>> entryMonth : entryYear.getValue().entrySet())
				{
					for (int i=0; i<entryMonth.getValue().size(); i++)
					{
						List<String> fileList = new ArrayList<String>();
						FileManager.getListOfFiles(fileList, userDatabasePath + entryYear.getKey() + File.separator + entryMonth.getKey() + File.separator + entryMonth.getValue().get(i), false, FileManager.FILE_ONLY, FileManager.ABSOLUTE_PATH);
						for (int a=0; a<fileList.size(); a++)
						{
							filePaths.add(fileList.get(a));
						}
					}
				}
			}
			/*
			for (Map.Entry<LinkedHashMap<String, String>, List<String>> entry : legibleDaysMap.entrySet())
			{
				for (Map.Entry<String, String> entry2 : entry.getKey().entrySet())
				{
					for (int i=0; i<entry.getValue().size(); i++)
					{
						List<String> fileList = new ArrayList<String>();
						FileManager.getListOfFiles(fileList, MainFrame.setting.getDataMap().get(Settings.DATABASE_PATH) + File.separator + entry2.getKey() + File.separator + entry2.getValue() + File.separator + entry.getValue().get(i), false, FileManager.FILE_ONLY, FileManager.ABSOLUTE_PATH);
						for (int a=0; a<fileList.size(); a++)
						{
							filePaths.add(fileList.get(a));
						}
					}
				}
			}
			*/
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
		
		FileManager.getListOfFiles(files, MainFrame.setting.getDataMap().get(Settings.DATABASE_USERS_PATH), false, FileManager.FILE_ONLY, FileManager.ABSOLUTE_PATH);
		
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
		
		return list;
	}
	
	public static void savePatientData(PatientData patientData)
	{
		try
		{
			XMLManager.exportXML(patientData.getXMLDocument(), new File(MainFrame.setting.getDataMap().get(Settings.DATABASE_USERS_PATH) + File.separator + patientData.getDataMap().get(PatientData.MEDICAL_RECORD_ID) + ".xml"), 5);
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
		
		FileManager.getListOfFiles(files, MainFrame.setting.getDataMap().get(Settings.DATABASE_USERS_PATH) + File.separator, false, FileManager.FILE_ONLY, FileManager.ABSOLUTE_PATH);
		
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
		File file = new File (MainFrame.setting.getDataMap().get(Settings.DATABASE_USERS_PATH) + File.separator + medID + ".xml");
		file.delete();
	}
	
	public static void saveHistory(History history)
	{
		try
		{
			File file = new File(Constants.HISTORY_FOLDER_PATH + history.getFileName());
			
			if (!file.exists())
			{
				file.createNewFile();
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
	public static void updateHistory(History history, String item)
	{
		history.add(item);
		FileOperation.saveHistory(history);
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
			try
			{
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				
				//Write table headers
				for (int i=1; i<Constants.ENTRY_TABLE_HEADERS.length; i++)
				{
					bw.write(Constants.ENTRY_TABLE_HEADERS[i]);
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
}
