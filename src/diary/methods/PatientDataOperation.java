package diary.methods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import diary.constants.PainDataIdentifier;
import diary.patientdata.PatientData;
import diary.patientdata.PatientDataManagePanel;

public class PatientDataOperation 
{
	public static List<String> getListOfNamesAndID()
	{
		List<PatientData> list = FileOperation.getListOfPatients();
		List<String> names = new ArrayList<String>();
		
		for (int i=0; i<list.size(); i++)
		{
			names.add(list.get(i).getNameAndID());
		}
		
		Collections.sort(names);
		
		return names;
	}
	
	public static List<String> getListOfIDAndNames()
	{
		List<PatientData> list = FileOperation.getListOfPatients();
		List<String> names = new ArrayList<String>();
		
		for (int i=0; i<list.size(); i++)
		{
			names.add(list.get(i).getIDAndName());
		}
		
		Collections.sort(names);
		
		return names;
	}
	
	public static List<PatientData> getFilteredData(String filterType, String filter, List<PatientData> source)		//filterType: PatientDataManagePanel.FILTER_NAME or PatientDataManagePanel.FILTER_MED_REC
	{
		List<PatientData> list = new ArrayList<PatientData>();
		
		if (filterType.equals(PatientDataManagePanel.FILTER_NAME))
		{
			loop:
			for (int i=0; i<source.size(); i++)
			{
				String name = source.get(i).getName();
				
				for (int a=0; a<name.length()-1; a++)
				{
					for (int b=a+1; b<name.length(); b++)
					{
						if (name.substring(a, b).equalsIgnoreCase(filter))
						{
							list.add(source.get(i));
							continue loop;
						}
					}
				}
				
			}
		}
		else if (filterType.equals(PatientDataManagePanel.FILTER_MED_REC))
		{
			loop:
				for (int i=0; i<source.size(); i++)
				{
					String medrec = source.get(i).getID();
					
					for (int a=0; a<medrec.length()-1; a++)
					{
						for (int b=a+1; b<medrec.length(); b++)
						{
							if (medrec.substring(a, b).equalsIgnoreCase(filter))
							{
								list.add(source.get(i));
								continue loop;
							}
						}
					}
					
				}
		}
		
		return list;
	}

	public static List<PatientData> getFilteredData(LinkedHashMap<String, String> dateFromMap, LinkedHashMap<String, String> dateToMap, List<PatientData> source)
	{
		List<PatientData> list = new ArrayList<PatientData>();
		
		//Data Sorting Start
		//Sort by year
		sortData(source, PatientData.DOB_YEAR);
		//Prepare for sorting for month (Separate collections of patient data based on their year)
		LinkedHashMap<String, List<PatientData>> yearMap = new LinkedHashMap<String, List<PatientData>>();
		for (int i=0; i<source.size(); i++)
		{
			PatientData patient = source.get(i);
			String year = patient.getDataMap().get(PatientData.DOB_YEAR);
			if (yearMap.get(year) == null)
			{
				yearMap.put(year, new ArrayList<PatientData>());
				yearMap.get(year).add(patient);
			}
			else
			{
				yearMap.get(year).add(patient);
			}
		}
		//Sort by month
		for (Map.Entry<String, List<PatientData>> entry : yearMap.entrySet())
		{
			sortData(entry.getValue(), PatientData.DOB_MONTH);
		}
		//Prepare for sorting for day (Separate collections of patient data based on they month)
		LinkedHashMap<String, LinkedHashMap<String, List<PatientData>>> yearMonthMap = new LinkedHashMap<String, LinkedHashMap<String, List<PatientData>>>();
		for (Map.Entry<String, List<PatientData>> entry : yearMap.entrySet())
		{
			LinkedHashMap<String, List<PatientData>> subMap = new LinkedHashMap<String, List<PatientData>>();
			for (int i=0; i<entry.getValue().size(); i++)
			{
				PatientData patient = entry.getValue().get(i);
				String month = patient.getDataMap().get(PatientData.DOB_MONTH);
				if (subMap.get(month) == null)
				{
					subMap.put(month, new ArrayList<PatientData>());
					subMap.get(month).add(patient);
				}
				else
				{
					subMap.get(month).add(patient);
				}
			}
			yearMonthMap.put(entry.getKey(), subMap);
		}
		//Sort by Day
		for (Map.Entry<String, LinkedHashMap<String, List<PatientData>>> entry : yearMonthMap.entrySet())
		{
			for (Map.Entry<String, List<PatientData>> subEntry : entry.getValue().entrySet())
			{
				sortData(subEntry.getValue(), PatientData.DOB_DAY);
			}
		}
		//Data Sorting End
		
		//Filter Start
//		System.out.println("Filtering years...");
		//Filter Year
		int yearMin = Integer.parseInt(dateFromMap.get(PainDataIdentifier.DATE_YEAR));
		int yearMax = Integer.parseInt(dateToMap.get(PainDataIdentifier.DATE_YEAR));
		List<String> keysToRemove = new ArrayList<String>();
		for (Map.Entry<String, LinkedHashMap<String, List<PatientData>>> entry : yearMonthMap.entrySet())
		{
			if (Integer.parseInt(entry.getKey()) < yearMin || Integer.parseInt(entry.getKey()) > yearMax)
			{
				keysToRemove.add(entry.getKey());
			}
		}
		//Clear years
		for (String key : keysToRemove)
		{
			yearMonthMap.remove(key);
		}
		keysToRemove.clear();
		if (yearMonthMap.size()==0)			//Check if no legible data
		{
			return list;
		}
//		System.out.println("Filtering month...");
		//Filter Month
		/*
		 * Program only needs to check the first months of the first year,
		 * and last months of the last year,
		 * as all other months in between will be selected nonetheless
		 */
		Map.Entry<String, LinkedHashMap<String, List<PatientData>>> firstYearEntry = yearMonthMap.entrySet().iterator().next();		//get first year
		String lastYear = "";
		Iterator<Entry<String, LinkedHashMap<String, List<PatientData>>>> iterator = yearMonthMap.entrySet().iterator();
		while(iterator.hasNext())		///Get Last key (last year)
		{
			lastYear = iterator.next().getKey();
	//		System.out.println("last year: " + lastYear);
		}
		int monthMin = Integer.parseInt(dateFromMap.get(PainDataIdentifier.DATE_MONTH));
		int monthMax = Integer.parseInt(dateToMap.get(PainDataIdentifier.DATE_MONTH));
		for (Map.Entry<String, List<PatientData>> subEntry : firstYearEntry.getValue().entrySet())		//First year
		{
			if (Integer.parseInt(subEntry.getKey()) < monthMin)
			{
				keysToRemove.add(subEntry.getKey());
			}
		}
		//Clear keys from first year
		for (String key : keysToRemove)
		{
			yearMonthMap.get(firstYearEntry.getKey()).remove(key);
		}
		keysToRemove.clear();
		//Last year
		for (Map.Entry<String, List<PatientData>> subEntry : yearMonthMap.get(lastYear).entrySet())
		{
			if (Integer.parseInt(subEntry.getKey()) > monthMax)
			{
				keysToRemove.add(subEntry.getKey());
			}
		}
		//Clear keys from last year
		for (String key : keysToRemove)
		{
			yearMonthMap.get(lastYear).remove(key);
		}
		keysToRemove.clear();
//		System.out.println("Filtering days...");
		//Filter Day
		/*
		 * Program only needs to check the first days of the first month of the first year,
		 * and last days of the last month of the last year,
		 * as all days in between will be selected nonetheless
		 */
		Map.Entry<String, List<PatientData>> firstMonthEntry = firstYearEntry.getValue().entrySet().iterator().next();
		String lastMonth = "";
		Iterator<Entry<String, List<PatientData>>> iterator2 = firstYearEntry.getValue().entrySet().iterator();
		while (iterator2.hasNext()) 
		{
			lastMonth = iterator2.next().getKey();
		}
		int dayMin = Integer.parseInt(dateFromMap.get(PainDataIdentifier.DATE_DAY));
		int dayMax = Integer.parseInt(dateToMap.get(PainDataIdentifier.DATE_DAY));
		for (int i=0; i<firstMonthEntry.getValue().size(); i++)
		{
			PatientData patient = firstMonthEntry.getValue().get(i);
			if (Integer.parseInt(patient.getDataMap().get(PatientData.DOB_DAY)) < dayMin)
			{
				keysToRemove.add(Integer.toString(i));
			}
		}
		//Clear first month
		for (String index : keysToRemove)
		{
			yearMonthMap.get(firstYearEntry.getKey()).get(firstMonthEntry.getKey()).get(Integer.parseInt(index));
		}
		keysToRemove.clear();
		//last month
		for (int i=0; i<yearMonthMap.get(firstYearEntry.getKey()).get(lastMonth).size(); i++)
		{
			PatientData patient = yearMonthMap.get(firstYearEntry.getKey()).get(lastMonth).get(i);
			if (Integer.parseInt(patient.getDataMap().get(PatientData.DOB_DAY)) > dayMax)
			{
				keysToRemove.add(Integer.toString(i));
			}
		}
		//Clear last month
		for (String index : keysToRemove)
		{
			yearMonthMap.get(firstYearEntry.getKey()).get(lastMonth).remove(Integer.parseInt(index));
		}
		keysToRemove.clear();
		//Filter End
		
		//Merge into 1 list
		for (Map.Entry<String, LinkedHashMap<String, List<PatientData>>> entry : yearMonthMap.entrySet())
		{
			for (Map.Entry<String, List<PatientData>> subEntry : entry.getValue().entrySet())
			{
				for (PatientData patient : subEntry.getValue())
				{
					list.add(patient);
				}
			}
		}
		
		return list;
	}
	
	private static void sortData(List<PatientData> list, String sortKey)		//Sort key is constants from PatientData, either DOB_YEAR, DOB_MONTH, or DOB_DAY
	{
		List<Integer> sublist = new ArrayList<Integer>();
		
		for (int i=0; i<list.size(); i++)		//Prepare sublist
		{
			sublist.add(Integer.parseInt(list.get(i).getDataMap().get(sortKey)));
		}
		
		//Sort
		for (int i=0; i<sublist.size(); i++)
		{
			for (int a=i+1; a<sublist.size(); a++)
			{
				if (sublist.get(i) > sublist.get(a))
				{
					//Sort sublist
					int tampung = sublist.get(i);
					sublist.set(i, sublist.get(a));
					sublist.set(a, tampung);
					
					//Synchronize with parent list
					PatientData temp = list.get(i);
					list.set(i, list.get(a));
					list.set(a, temp);
				}
			}
		}
	}
}
