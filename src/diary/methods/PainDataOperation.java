package diary.methods;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import diary.constants.DateConstants;
import diary.constants.PainDataIdentifier;
import diary.constants.XMLIdentifier;
import diary.data.PainEntryData;
import diary.data.PainEntryDataVoid;

public class PainDataOperation 
{	
	public static LinkedHashMap<String, Float> getAmountOfEntriesVSDate(List<PainEntryData> list)
	{
		LinkedHashMap<String, Float> map = new LinkedHashMap<String, Float>();
		
		for (int i=0; i<list.size(); i++)
		{
			try
			{
				map.put(list.get(i).getFullDate(), map.get(list.get(i).getFullDate())+1f);
			}
			catch(NullPointerException ex) 
			{
				map.put(list.get(i).getFullDate(), 1f);
			}
			
			if (list.get(i) instanceof PainEntryDataVoid)
			{
				map.put(list.get(i).getFullDate(), 0f);
			}
		}
		
		return map;
	}
	
	/*
	public static LinkedHashMap<String, Float> getAmountOfHeadPainsVSDate(List<PainEntryData> list)
	{
		LinkedHashMap<String, Float> map = new LinkedHashMap<String, Float>();
		
		for (int i=0; i<list.size(); i++)
		{
			try
			{
				map.put(list.get(i).getFullDate(), (map.get(list.get(i).getFullDate()) + Float.parseFloat((String)list.get(i).getDataMap().get(PainDataIdentifier.PAIN_AMOUNT))));
			}
			catch(NullPointerException ex)
			{
				map.put(list.get(i).getFullDate(), Float.parseFloat((String)list.get(i).getDataMap().get(PainDataIdentifier.PAIN_AMOUNT)));
			}
		}
		
		return map;
	}
	*/
	
	/*
	@Deprecated
	public static LinkedHashMap<String, Float> getIntensityAverageVSTime(List<PainEntryData> list)
	{
		LinkedHashMap<String, Float> map = new LinkedHashMap<String, Float>();
		
		for (int i=0; i<list.size(); i++)
		{
			List<LinkedHashMap<String, Object>> painLocations = (List<LinkedHashMap<String, Object>>)list.get(i).getDataMap().get(PainDataIdentifier.PAIN_LOCATIONS);
			for (int a=0; a<painLocations.size(); a++)
			{
				try
				{
					map.put(list.get(i).getFullTimeAndDate(), 
								map.get(list.get(i).getFullTimeAndDate()) + Float.parseFloat((String)painLocations.get(a).get(PainDataIdentifier.INTENSITY)));
				}
				catch(NullPointerException ex)
				{
					map.put(list.get(i).getFullTimeAndDate(), Float.parseFloat((String)painLocations.get(a).get(PainDataIdentifier.INTENSITY)));
				}
				
				if (painLocations.size()-a==1)		//If final iteration, average amount of intensity
				{
					map.put(list.get(i).getFullTimeAndDate(), map.get(list.get(i).getFullTimeAndDate())/(a+1));
				}
			}
		}
		
		return map;
	}
	*/
	public static LinkedHashMap<String, Float> getIntensityVSTime(List<PainEntryData> list)
	{
		LinkedHashMap<String, Float> map = new LinkedHashMap<String, Float>();
		
		for (PainEntryData entry : list)
		{
			map.put(entry.getFullTimeAndDate(), Float.parseFloat(entry.getDataMap().get(PainDataIdentifier.INTENSITY).toString()));
		}
		
		return map;
	}
	
	/*
	public static LinkedHashMap<String, Float> getDurationAverageVSTime(List<PainEntryData> list)
	{
		LinkedHashMap<String, Float> map = new LinkedHashMap<String, Float>();
		
		for (int i=0; i<list.size(); i++)
		{
			List<LinkedHashMap<String, Object>> painLocations = (List<LinkedHashMap<String, Object>>)list.get(i).getDataMap().get(PainDataIdentifier.PAIN_LOCATIONS);
			for (int a=0; a<painLocations.size(); a++)
			{
				try
				{
					map.put(list.get(i).getFullTimeAndDate(), 
							map.get(list.get(i).getFullTimeAndDate()) + Float.parseFloat((String)painLocations.get(a).get(PainDataIdentifier.DURATION)));
				}
				catch(NullPointerException ex)
				{
					map.put(list.get(i).getFullTimeAndDate(), Float.parseFloat((String)painLocations.get(a).get(PainDataIdentifier.DURATION)));
				}
				
				if (painLocations.size()-a==1)		//If final iteration, average amount of duration
				{
					map.put(list.get(i).getFullTimeAndDate(), map.get(list.get(i).getFullTimeAndDate())/(a+1));
				}
			}
		}
		
		return map;
	}
	*/

	/*
	public static LinkedHashMap<String, Float> getNumberOfDifferentPainKind(List<PainEntryData> list)
	{
		LinkedHashMap<String, Float> map = new LinkedHashMap<String, Float>();
		
		for (int i=0; i<list.size(); i++)
		{
			if (list.get(i) instanceof PainEntryDataVoid)
			{
				continue;
			}
			
			List<LinkedHashMap<String, Object>> painLocations = (List<LinkedHashMap<String, Object>>)list.get(i).getDataMap().get(PainDataIdentifier.PAIN_LOCATIONS);
			for (int a=0; a<painLocations.size(); a++)
			{
				String key = painLocations.get(a).get(PainDataIdentifier.PAIN_KIND).toString();
				
				try
				{
					map.put(key, map.get(key) + 1f);
				}
				catch(NullPointerException ex)
				{
					map.put(key, 1f);
				}
			}
		}
		
		return map;
	}
	
	@Deprecated
	public static LinkedHashMap<String, Float> getNumberOfDifferentPainLocations(List<PainEntryData> list)
	{
		LinkedHashMap<String, Float> map = new LinkedHashMap<String, Float>();
		
		for (int i=0; i<list.size(); i++)
		{
			if (list.get(i) instanceof PainEntryDataVoid)
			{
				continue;
			}
			
			List<LinkedHashMap<String, Object>> painLocations = (List<LinkedHashMap<String, Object>>)list.get(i).getDataMap().get(PainDataIdentifier.PAIN_LOCATIONS);
			for (int a=0; a<painLocations.size(); a++)
			{
				String key = Methods.convertToLanguageText(painLocations.get(a).get(PainDataIdentifier.SPECIFIC_LOCATION).toString());
				
				try
				{
					map.put(key, map.get(key) + 1f);
				}
				catch(NullPointerException ex)
				{
					map.put(key, 1f);
				}
			}
		}
		
		return map;
	}
	*/
	
	public static LinkedHashMap<String, Float> getAmountOfActivity(List<PainEntryData> list)
	{
		LinkedHashMap<String, Float> map = new LinkedHashMap<String, Float>();
		
		for (int i=0; i<list.size(); i++)
		{
			if (list.get(i) instanceof PainEntryDataVoid)
			{
				continue;
			}
			
			String key = list.get(i).getDataMap().get(PainDataIdentifier.ACTIVITY).toString();
			
			try
			{
				map.put(key, map.get(key) + 1f);
			}
			catch(NullPointerException ex)
			{
				map.put(key, 1f);
			}
		}
		
		return map;
	}

	public static List<PainEntryData> getFilteredData(String filterType, String filter, List<PainEntryData> list)
	{
		if (!filter.equals(""))			//If filter is not empty
		{
			loop:
			for (int i=0; i<list.size(); i++)
			{
				if (filterType.equals(XMLIdentifier.TABLE_FILTER_TYPE_PAIN_AMOUNT_TEXT))		//If filtered by amount of pain
				{
					if (!list.get(i).getDataMap().get(PainDataIdentifier.PAIN_AMOUNT).toString().equalsIgnoreCase(filter))
					{
						list.remove(i);
						i=-1;
						continue loop;
					}
				}
				else if (filterType.equals(XMLIdentifier.TABLE_FILTER_TYPE_PAIN_POSITIONS_TEXT))		//If filtered by pain position
				{
					String positions = list.get(i).getPainPositionsAsString();
					for (int a=0; a<positions.length(); a++)
					{
						for (int b=a+1; b<=positions.length(); b++)
						{
							if (positions.substring(a, b).equalsIgnoreCase(filter))
							{
								continue loop;
							}
						}
					}
					list.remove(i);
					i=-1;
					continue loop;
				}
				else if (filterType.equals(XMLIdentifier.TABLE_FILTER_TYPE_PAIN_KINDS_TEXT))		//If filtered by pain kinds
				{
					String painKinds = list.get(i).getDataMap().get(PainDataIdentifier.PAIN_KIND).toString();
					for (int a=0; a<painKinds.length(); a++)
					{
						for (int b=a+1; b<=painKinds.length(); b++)
						{
							if (painKinds.substring(a, b).equalsIgnoreCase(filter))
							{
								continue loop;
							}
						}
					}
					list.remove(i);
					i=-1;
					continue loop;
				}
				else if (filterType.equals(XMLIdentifier.TABLE_FILTER_TYPE_INTENSITIES_TEXT))		//If filtered by intensities
				{
		//			String intensities = list.get(i).getIntensitiesAsString();
					String intensities = list.get(i).getDataMap().get(PainDataIdentifier.INTENSITY).toString();
					for (int a=0; a<intensities.length(); a++)
					{
						for (int b=a+1; b<=intensities.length(); b++)
						{
							if (intensities.substring(a, b).equalsIgnoreCase(filter))
							{
								continue loop;
							}
						}
					}
					list.remove(i);
					i=-1;
					continue loop;
				}
				else if (filterType.equals(XMLIdentifier.TABLE_FILTER_TYPE_DURATIONS_TEXT))		//If filtered by durations
				{
					String durations = list.get(i).getDataMap().get(PainDataIdentifier.DURATION).toString();
					for (int a=0; a<durations.length(); a++)
					{
						for (int b=a+1; b<=durations.length(); b++)
						{
							if (durations.substring(a, b).equalsIgnoreCase(filter))
							{
								continue loop;
							}
						}
					}
					list.remove(i);
					i=-1;
					continue loop;
				}
				else if (filterType.equals(XMLIdentifier.TABLE_FILTER_TYPE_ACTIVITY_TEXT))		//If filtered by activity
				{
					String activity = list.get(i).getDataMap().get(PainDataIdentifier.ACTIVITY).toString();
					for (int a=0; a<activity.length(); a++)
					{
						for (int b=a+1; b<=activity.length(); b++)
						{
							if (activity.substring(a, b).equalsIgnoreCase(filter))
							{
								continue loop;
							}
						}
					}
					list.remove(i);
					i=-1;
					continue loop;
				}
				else if (filterType.equals(XMLIdentifier.TABLE_FILTER_TYPE_COMMENTS_TEXT))		//If filtered by comments
				{
					String comment = list.get(i).getDataMap().get(PainDataIdentifier.COMMENTS).toString();
					for (int a=0; a<comment.length(); a++)
					{
						for (int b=a+1; b<=comment.length(); b++)
						{
							if (comment.substring(a, b).equalsIgnoreCase(filter))
							{
								continue loop;
							}
						}
					}
					list.remove(i);
					i=-1;
					continue loop;
				}
				
			}			
		}
		
		return list;
	}

	public static List<PainEntryData> insertEmptyData(List<PainEntryData> source)
	{
		List<PainEntryData> list = new ArrayList<PainEntryData>();
		LinkedHashMap<Integer, List<PainEntryData>> gaps = new LinkedHashMap<Integer, List<PainEntryData>>();
		
		for (int i=0; i<source.size()-1; i++)
		{
			List<PainEntryData> subList = new ArrayList<PainEntryData>();		//Array between 2 dates
			
			//Get Min Date
			int yearMin = Integer.parseInt(source.get(i).getDataMap().get(PainDataIdentifier.DATE_YEAR).toString());
			byte monthMin = Byte.parseByte(source.get(i).getDataMap().get(PainDataIdentifier.DATE_MONTH).toString());
			byte dayMin = Byte.parseByte(source.get(i).getDataMap().get(PainDataIdentifier.DATE_DAY).toString());
			
			//Get Max Date
			int yearMax = Integer.parseInt(source.get(i+1).getDataMap().get(PainDataIdentifier.DATE_YEAR).toString());
			byte monthMax = Byte.parseByte(source.get(i+1).getDataMap().get(PainDataIdentifier.DATE_MONTH).toString());
			byte dayMax = Byte.parseByte(source.get(i+1).getDataMap().get(PainDataIdentifier.DATE_DAY).toString());
			
			boolean done = false;
			byte day = dayMin;
			byte month = monthMin;
			int year = yearMin;
			while(!done)
			{
				//Check if the current day, month, and year is exactly the same as the max
				if (day==dayMax && month==monthMax && year==yearMax)
				{
					done = true;
					break;
				}
				
				//Keep adding by 1 day
				day++;
				//Check if day is greater than the available days in that month, if so, change day to 1, add 1 to month
				if (DateOperation.is30Days(month))			//If month is 30 days
				{
					if (day>30)
					{
						day = 1;
						month++;
					}
				}
				else if (DateOperation.is31Days(month))		//If month is 31 days
				{
					if (day>31)
					{
						day = 1;
						month++;
					}
				}
				else if (month == DateConstants.FEBRUARY)	//If month is february
				{
					if (DateOperation.isLeapYear(year))		//If it is a leap year
					{
						if (day>29)
						{
							day = 1;
							month++;
						}
					}
					else
					{
						if (day>28)
						{
							day = 1;
							month++;
						}
					}
				}
				//System.out.println("Day: " + day);
				//Check if month is greater than 12, if so set month to 1, add 1 to year
				if (month > 12)
				{
					month = 1;
					year++;
				}
				//System.out.println("Month: " + month);
				//System.out.println("Year: " + year);
				//System.out.println();
				//Check if the current day, month, and year is exactly the same as the max
				if (day==dayMax && month==monthMax && year==yearMax)
				{
					done = true;
					break;
				}
				
				PainEntryData voidEntry = new PainEntryDataVoid(Integer.toString(day), Integer.toString(month), Integer.toString(year));
				subList.add(voidEntry);
			}
			
			//System.out.println("subList size: " + subList.size());
			/*
			for (int a = 0; a<subList.size(); a++)
			{
				System.out.println("Entry Date: " + subList.get(a).getDataMap().get(PainDataIdentifier.DATE_DAY) + "/"
						+ subList.get(a).getDataMap().get(PainDataIdentifier.DATE_MONTH) + "/"
						+ subList.get(a).getDataMap().get(PainDataIdentifier.DATE_YEAR));
			} */
			
			if (subList.size()>0)
			{
				gaps.put(i, subList);
			}
		}
		//System.out.println("gaps size: " + gaps.size());
		
		//Merge all entries
		for (int i=0; i<source.size(); i++)
		{
			list.add(source.get(i));
			try
			{
				List<PainEntryData> subList = gaps.get(i);
				for (int a=0; a<subList.size(); a++)
				{
					list.add(subList.get(a));
				}
			}
			catch(NullPointerException ex) {}
		}
		
		return list;
	}
}
