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
import diary.constants.Constants;
import diary.constants.PainDataIdentifier;
import giantsweetroll.files.FileManager;
import giantsweetroll.message.MessageManager;
import giantsweetroll.xml.dom.XMLManager;

public class FileOperation
{
	public static final void exportPainData(Document doc)
	{
		File file = new File(Methods.generatePainDataFolderPathName(doc));
		if (!file.exists())				//Check if the folder directory exists, if not make it
		{
			file.mkdirs();
		}
		
		file = new File(Methods.generatePainDataFilePathName(doc));
		try 
		{
			XMLManager.exportXML(doc, file, 5);
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
		FileManager.getListOfFiles(legibleYears, Constants.DATABASE_PATH, false, FileManager.FOLDER_ONLY, FileManager.NAME_ONLY);
	//	MessageManager.printLine("Size of years: " + legibleYears.size());
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
		if (legibleYears.size() == 1)
		{
			List<String> legibleMonths = new ArrayList<String>();
			FileManager.getListOfFiles(legibleMonths, Constants.DATABASE_PATH + legibleYears.get(0), false, FileManager.FOLDER_ONLY, FileManager.NAME_ONLY);
	//		MessageManager.printLine("Number of months: " + legibleMonths.size());
			for (int i=0; i<legibleMonths.size(); i++)
			{
		//		MessageManager.printLine("Iteration month: " + i);
				try
				{
					int monthNow = Integer.parseInt(legibleMonths.get(i));
					int monthMin = Integer.parseInt(dateFromMap.get(PainDataIdentifier.DATE_MONTH));
					int monthMax = Integer.parseInt(dateToMap.get(PainDataIdentifier.DATE_MONTH));
					if (monthNow < monthMin || monthNow > monthMax)
					{
						legibleMonths.remove(i);	//remove Illegible month
						i = -1;		//Reset index to loop from beginning of array (it's -1 because at the end of loop will be added by 1 = 0)
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
				FileManager.getListOfFiles(legibleMonths, Constants.DATABASE_PATH + legibleYears.get(i), false, FileManager.FOLDER_ONLY, FileManager.NAME_ONLY);
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
							int monthNow = Integer.parseInt(legibleMonths.get(i));
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
							int monthNow = Integer.parseInt(legibleMonths.get(i));
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
		LinkedHashMap<LinkedHashMap<String, String>, List<String>> legibleDaysMap = new LinkedHashMap<LinkedHashMap<String, String>, List<String>>();
		/*
		 * Program only needs to check the first month of the first year to get the min day,
		 * and the last month of the last year to get the max day,
		 * as all other days in the months between will be selected regardless
		 */
		for (Map.Entry<String, List<String>> entry : legibleMonthsMap.entrySet())
		{
			LinkedHashMap<String, String> yearMonthMap = new LinkedHashMap<String, String>();
			for (int b=0; b<entry.getValue().size(); b++)
			{
				List<String> legibleDays = new ArrayList<String>();
		//		MessageManager.printLine(Constants.DATABASE_PATH + entry.getKey() + File.separator + entry.getValue().get(b));
				FileManager.getListOfFiles(legibleDays, Constants.DATABASE_PATH + entry.getKey() + File.separator + entry.getValue().get(b), false, FileManager.FOLDER_ONLY, FileManager.NAME_ONLY);
				
				int iteration = 0;
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
			}
		}
		//Filtering end
		
		//Gather list of files from each folder
		List<String> filePaths = new ArrayList<String>();
		for (Map.Entry<LinkedHashMap<String, String>, List<String>> entry : legibleDaysMap.entrySet())
		{
			for (Map.Entry<String, String> entry2 : entry.getKey().entrySet())
			{
				for (int i=0; i<entry.getValue().size(); i++)
				{
					List<String> fileList = new ArrayList<String>();
					FileManager.getListOfFiles(fileList, Constants.DATABASE_PATH + entry2.getKey() + File.separator + entry2.getValue() + File.separator + entry.getValue().get(i), false, FileManager.FILE_ONLY, FileManager.ABSOLUTE_PATH);
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
		
//		MessageManager.printLine("Size of entries: " + list.size());
		
		return list;
	}
}
