package diary.methods;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import diary.PainEntryData;
import diary.Settings;
import diary.constants.Constants;
import diary.constants.PainDataIdentifier;
import diary.gui.MainFrame;
import giantsweetroll.files.FileManager;
import giantsweetroll.message.MessageManager;
import giantsweetroll.xml.dom.XMLManager;

public class FileOperation
{
	public static final boolean entryExists(PainEntryData entry)
	{
		File file = new File(Methods.generatePainDataFilePathName(entry));
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
	
	public static final void exportPainData(PainEntryData entry)
	{
		File file = new File(Methods.generatePainDataFolderPathName(entry));
		if (!file.exists())				//Check if the folder directory exists, if not make it
		{
			file.mkdirs();
		}
		
		file = new File(Methods.generatePainDataFilePathName(entry));
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
	
	public static List<PainEntryData> getListOfEntries(LinkedHashMap<String, String> dateFromMap, LinkedHashMap<String, String> dateToMap)
	{
		List<PainEntryData> list = new ArrayList<PainEntryData>();
		
		//Filtering Start
		//Get year range
		List<String> legibleYears = new ArrayList<String>();
		FileManager.getListOfFiles(legibleYears, MainFrame.setting.getDataMap().get(Settings.DATABASE_PATH) + File.separator, false, FileManager.FOLDER_ONLY, FileManager.NAME_ONLY);
//		MessageManager.printLine("Size of years: " + legibleYears.size());
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
//		MessageManager.printLine("Size of elligible years: " + legibleYears.size());
		
		//Get month range
		LinkedHashMap<String, List<String>> legibleMonthsMap = new LinkedHashMap<String, List<String>>();
	//	MessageManager.printLine("Size of eligible months before: " + legibleMonthsMap.size());
		/*
		 * Program only needs to check the first month of the first year,
		 * and the last month of the last year,
		 * as all other months in between will be selected
		 */
		if (legibleYears.size() == 1)
		{
			List<String> legibleMonths = new ArrayList<String>();
			FileManager.getListOfFiles(legibleMonths, MainFrame.setting.getDataMap().get(Settings.DATABASE_PATH) + File.separator + legibleYears.get(0), false, FileManager.FOLDER_ONLY, FileManager.NAME_ONLY);
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
				FileManager.getListOfFiles(legibleMonths, MainFrame.setting.getDataMap().get(Settings.DATABASE_PATH) + File.separator + legibleYears.get(i), false, FileManager.FOLDER_ONLY, FileManager.NAME_ONLY);
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
//		LinkedHashMap<LinkedHashMap<String, String>, List<String>> legibleDaysMap = new LinkedHashMap<LinkedHashMap<String, String>, List<String>>();
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
				FileManager.getListOfFiles(legibleDays, MainFrame.setting.getDataMap().get(Settings.DATABASE_PATH) + File.separator + entry.getKey() + File.separator + entry.getValue().get(i), false, FileManager.FOLDER_ONLY, FileManager.NAME_ONLY);
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
					FileManager.getListOfFiles(fileList, MainFrame.setting.getDataMap().get(Settings.DATABASE_PATH) + File.separator + entryYear.getKey() + File.separator + entryMonth.getKey() + File.separator + entryMonth.getValue().get(i), false, FileManager.FILE_ONLY, FileManager.ABSOLUTE_PATH);
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
		
//		MessageManager.printLine("Size of entries: " + list.size());
		
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
}
