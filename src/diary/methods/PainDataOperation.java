package diary.methods;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import diary.constants.PainDataIdentifier;
import diary.constants.XMLIdentifier;
import diary.data.PainEntryData;
import diary.data.PainEntryDataVoid;
import giantsweetroll.date.Date;

public class PainDataOperation 
{	
	public static LinkedHashMap<String, Double> getAmountOfEntriesVSDate(List<PainEntryData> list)
	{
		LinkedHashMap<String, Double> map = new LinkedHashMap<String, Double>();
		
		for (int i=0; i<list.size(); i++)
		{
			try
			{
				map.put(list.get(i).getFullDate(), map.get(list.get(i).getFullDate())+1d);
			}
			catch(NullPointerException ex) 
			{
				map.put(list.get(i).getFullDate(), 1d);
			}
			
			if (list.get(i) instanceof PainEntryDataVoid)
			{
				map.put(list.get(i).getFullDate(), 0d);
			}
		}
		
		return map;
	}
	
	/*
	public static LinkedHashMap<String, Double> getAmountOfHeadPainsVSDate(List<PainEntryData> list)
	{
		LinkedHashMap<String, Double> map = new LinkedHashMap<String, Double>();
		
		for (int i=0; i<list.size(); i++)
		{
			try
			{
				map.put(list.get(i).getFullDate(), (map.get(list.get(i).getFullDate()) + Double.parseDouble((String)list.get(i).getDataMap().get(PainDataIdentifier.PAIN_AMOUNT))));
			}
			catch(NullPointerException ex)
			{
				map.put(list.get(i).getFullDate(), Double.parseDouble((String)list.get(i).getDataMap().get(PainDataIdentifier.PAIN_AMOUNT)));
			}
		}
		
		return map;
	}
	*/
	
	/*
	@Deprecated
	public static LinkedHashMap<String, Double> getIntensityAverageVSDate(List<PainEntryData> list)
	{
		LinkedHashMap<String, Double> map = new LinkedHashMap<String, Double>();
		
		for (int i=0; i<list.size(); i++)
		{
			List<LinkedHashMap<String, Object>> painLocations = (List<LinkedHashMap<String, Object>>)list.get(i).getDataMap().get(PainDataIdentifier.PAIN_LOCATIONS);
			for (int a=0; a<painLocations.size(); a++)
			{
				try
				{
					map.put(list.get(i).getFullTimeAndDate(), 
								map.get(list.get(i).getFullTimeAndDate()) + Double.parseDouble((String)painLocations.get(a).get(PainDataIdentifier.INTENSITY)));
				}
				catch(NullPointerException ex)
				{
					map.put(list.get(i).getFullTimeAndDate(), Double.parseDouble((String)painLocations.get(a).get(PainDataIdentifier.INTENSITY)));
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
	public static LinkedHashMap<String, Double> getIntensityVSTime(List<PainEntryData> list)
	{
		LinkedHashMap<String, Double> map = new LinkedHashMap<String, Double>();
		
		for (PainEntryData entry : list)
		{
			map.put(entry.getFullTimeAndDate(), Double.parseDouble(entry.getIntensity()));
		}
		
		return map;
	}
	public static LinkedHashMap<String, Double> getAverageIntensityVSDate(List<PainEntryData> list)
	{
		LinkedHashMap<String, Double> map = new LinkedHashMap<String, Double>();
		LinkedHashMap<String, Double> iterationMap = new LinkedHashMap<String, Double>();
		
		for (int i=0; i<list.size(); i++)
		{	
			try
			{
				map.put(list.get(i).getFullDate(), map.get(list.get(i).getFullDate()) + Double.parseDouble(list.get(i).getIntensity()));
			}
			catch(NullPointerException ex)
			{
				map.put(list.get(i).getFullDate(), Double.parseDouble(list.get(i).getIntensity()));
			}
			
			try
			{
				iterationMap.put(list.get(i).getFullDate(), iterationMap.get(list.get(i).getFullDate()) + 1d);
			}
			catch(NullPointerException ex)
			{
				iterationMap.put(list.get(i).getFullDate(), 1d);
			}
		}
		
		//Average data
		for (Map.Entry<String, Double> entry : iterationMap.entrySet())
		{
			map.put(entry.getKey(), map.get(entry.getKey())/entry.getValue());
		}
		return map;
	}
	
	public static LinkedHashMap<String, Double> getDurationVSTime(List<PainEntryData> list)
	{
		LinkedHashMap<String, Double> map = new LinkedHashMap<String, Double>();
		
		for (PainEntryData entry : list)
		{
			map.put(entry.getFullTimeAndDate(), Double.parseDouble(entry.getDuration()));
		}
		
		return map;
	}
	public static LinkedHashMap<String, Double> getAverageDurationVSDate(List<PainEntryData> list)
	{
		LinkedHashMap<String, Double> map = new LinkedHashMap<String, Double>();
		LinkedHashMap<String, Double> iterationMap = new LinkedHashMap<String, Double>();
		
		for (int i=0; i<list.size(); i++)
		{
			try
			{
				map.put(list.get(i).getFullDate(), map.get(list.get(i).getFullDate()) + Double.parseDouble(list.get(i).getIntensity()));
			}
			catch(NullPointerException ex)
			{
				map.put(list.get(i).getFullDate(), Double.parseDouble(list.get(i).getDuration()));
			}
			
			try
			{
				iterationMap.put(list.get(i).getFullDate(), iterationMap.get(list.get(i).getFullDate()) + 1d);
			}
			catch(NullPointerException ex)
			{
				iterationMap.put(list.get(i).getFullDate(), 1d);
			}
		}
		
		//Average data
		for (Map.Entry<String, Double> entry : iterationMap.entrySet())
		{
			map.put(entry.getKey(), map.get(entry.getKey())/entry.getValue());
		}
		return map;
	}
	
	/*
	public static LinkedHashMap<String, Double> getDurationAverageVSTime(List<PainEntryData> list)
	{
		LinkedHashMap<String, Double> map = new LinkedHashMap<String, Double>();
		
		for (int i=0; i<list.size(); i++)
		{
			List<LinkedHashMap<String, Object>> painLocations = (List<LinkedHashMap<String, Object>>)list.get(i).getDataMap().get(PainDataIdentifier.PAIN_LOCATIONS);
			for (int a=0; a<painLocations.size(); a++)
			{
				try
				{
					map.put(list.get(i).getFullTimeAndDate(), 
							map.get(list.get(i).getFullTimeAndDate()) + Double.parseDouble((String)painLocations.get(a).get(PainDataIdentifier.DURATION)));
				}
				catch(NullPointerException ex)
				{
					map.put(list.get(i).getFullTimeAndDate(), Double.parseDouble((String)painLocations.get(a).get(PainDataIdentifier.DURATION)));
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

	
	public static LinkedHashMap<String, Double> getNumberOfDifferentPainKind(List<PainEntryData> list)
	{
		LinkedHashMap<String, Double> map = new LinkedHashMap<String, Double>();
		
		for (PainEntryData entry : list)
		{
			if (entry instanceof PainEntryDataVoid)
			{
				continue;
			}
			
			String painKind = entry.getPainKind();
			painKind = Methods.convertPainKindIDToLanguage(painKind);
			try
			{
				map.put(painKind, map.get(painKind) + 1d);
			}
			catch(NullPointerException ex)
			{
				map.put(painKind, 1d);
			}
		}
		
		return map;
	}
	
	/*
	@Deprecated
	public static LinkedHashMap<String, Double> getNumberOfDifferentPainLocations(List<PainEntryData> list)
	{
		LinkedHashMap<String, Double> map = new LinkedHashMap<String, Double>();
		
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
					map.put(key, map.get(key) + 1d);
				}
				catch(NullPointerException ex)
				{
					map.put(key, 1d);
				}
			}
		}
		
		return map;
	}
	*/
	
	public static LinkedHashMap<String, Double> getAmountOfActivity(List<PainEntryData> list)
	{
		LinkedHashMap<String, Double> map = new LinkedHashMap<String, Double>();
		
		for (int i=0; i<list.size(); i++)
		{
			if (list.get(i) instanceof PainEntryDataVoid)
			{
				continue;
			}
			
			String activity = list.get(i).getTrigger();
			activity = Methods.convertActivityIDToLanguage(activity);
			
			try
			{
				map.put(activity, map.get(activity) + 1d);
			}
			catch(NullPointerException ex)
			{
				map.put(activity, 1d);
			}
		}
		
		return map;
	}
	
	public static LinkedHashMap<String, Double> getAverageIntensityVSMonth(List<PainEntryData> entries)
	{
		LinkedHashMap<String, Double> map = new LinkedHashMap<String, Double>();
		LinkedHashMap<String, Double> iterationMap = new LinkedHashMap<String, Double>();
		
		for (PainEntryData entry : entries)
		{
			try
			{
				map.put(entry.getMonthAndYear(), map.get(entry.getMonthAndYear()) + Double.parseDouble(entry.getIntensity()));
			}
			catch(NullPointerException ex)
			{
				map.put(entry.getMonthAndYear(), Double.parseDouble(entry.getIntensity()));
			}
			
			try
			{
				iterationMap.put(entry.getMonthAndYear(), iterationMap.get(entry.getMonthAndYear()) + 1d);
			}
			catch(NullPointerException ex)
			{
				iterationMap.put(entry.getMonthAndYear(), 1d);
			}
		}
		
		//Average the data
		for (Map.Entry<String, Double> entry : iterationMap.entrySet())
		{
			map.put(entry.getKey(), map.get(entry.getKey()) / entry.getValue());
		}
		
		return map;
	}
	public static LinkedHashMap<String, Double> getAverageDurationVSMonth(List<PainEntryData> entries)
	{
		LinkedHashMap<String, Double> map = new LinkedHashMap<String, Double>();
		LinkedHashMap<String, Double> iterationMap = new LinkedHashMap<String, Double>();
		
		for (PainEntryData entry : entries)
		{
			try
			{
				map.put(entry.getMonthAndYear(), map.get(entry.getMonthAndYear()) + Double.parseDouble(entry.getDuration()));
			}
			catch(NullPointerException ex)
			{
				map.put(entry.getMonthAndYear(), Double.parseDouble(entry.getDuration()));
			}
			
			try
			{
				iterationMap.put(entry.getMonthAndYear(), iterationMap.get(entry.getMonthAndYear()) + 1d);
			}
			catch(NullPointerException ex)
			{
				iterationMap.put(entry.getMonthAndYear(), 1d);
			}
		}
		
		//Average the data
		for (Map.Entry<String, Double> entry : iterationMap.entrySet())
		{
			map.put(entry.getKey(), map.get(entry.getKey()) / entry.getValue());
		}
		
		return map;
	}	
	
	@Deprecated
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
					String painKinds = list.get(i).getPainKind();
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
					String intensities = list.get(i).getIntensity();
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
					String durations = list.get(i).getDuration();
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
				else if (filterType.equals(XMLIdentifier.TABLE_FILTER_TYPE_TRIGGER_TEXT))		//If filtered by activity
				{
					String activity = list.get(i).getTrigger();
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
					String comment = list.get(i).getComments();
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
	
	
	public static List<PainEntryData> insertEmptyData(List<PainEntryData> source, Date dateFrom, Date dateTo)
	{
		List<PainEntryData> list = new ArrayList<PainEntryData>();
		LinkedHashMap<Integer, List<PainEntryData>> gaps = new LinkedHashMap<Integer, List<PainEntryData>>();
		
		//Check if there are no entries in the dateFrom
		try
		{
			if (source.get(0).getDate().getYear() == dateFrom.getYear())
			{
				if (source.get(0).getDate().getMonth() == dateFrom.getMonth())
				{
					if (source.get(0).getDate().getDay() != dateFrom.getDay())
					{
						source.add(0, new PainEntryDataVoid(dateFrom));
					}
				}
				else
				{
					source.add(0, new PainEntryDataVoid(dateFrom));
				}
			}
			else
			{
				source.add(0, new PainEntryDataVoid(dateFrom));
			}
		}
		catch(Exception ex) {}
		
		//Check if there are no entries in the dateTo
		try
		{
			if (source.get(source.size()-1).getDate().getYear() == dateTo.getYear())
			{
				if (source.get(source.size()-1).getDate().getMonth() == dateTo.getMonth())
				{
					if (source.get(source.size()-1).getDate().getDay() !=dateTo.getDay())
					{
						source.add(new PainEntryDataVoid(dateTo));
					}
				}
				else
				{
					source.add(new PainEntryDataVoid(dateTo));
				}
			}
			else
			{
				source.add(new PainEntryDataVoid(dateTo));
			}
		}
		catch(Exception ex) {}
		
		for (int i=0; i<source.size()-1; i++)
		{
			List<PainEntryData> subList = new ArrayList<PainEntryData>();		//Array between 2 dates
			
			Date dateMin = source.get(i).getDate();
			Date dateMax = source.get(i+1).getDate();
			
			while(!Date.areSameDate(dateMin, dateMax))
			{
				dateMin.setDay(dateMin.getDay()+1);
				if (!Date.areSameDate(dateMin, dateMax))
				{
					subList.add(new PainEntryDataVoid(dateMin));
				}
			}
			
			
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

	public static List<PainEntryData> filter(List<PainEntryData> source, String filterComponent, String medication)		//filterComponent is the constant from PainDataIdentifier
	{
		List<PainEntryData> list = new ArrayList<PainEntryData>();
		
		for (PainEntryData entry : source)
		{
			if (entry.getDataMap().get(filterComponent).equals(medication))
			{
				list.add(entry);
			}
		}
		
		return list;
	}
	
	public static List<PainEntryData> generateDuplicates(PainEntryData entry, Date targetDate)
	{
		List<PainEntryData> list = new ArrayList<PainEntryData>();
		
	//	System.out.println("Day difference: " + Date.getDaysDifference(entry.getDate(), targetDate, true));
		
		for (int i=entry.getDate().getDay(); i<=Date.getDaysDifference(entry.getDate(), targetDate, true)+entry.getDate().getDay()-1; i++)
		{
			PainEntryData duplicate = new PainEntryData(entry.getXMLDocument());
			duplicate.setDate(new Date(i, entry.getDate().getMonth(), entry.getDate().getYear()));
			list.add(duplicate);
		}
		
		return list;
	}
}
