package diary.methods;

import java.util.LinkedHashMap;
import java.util.List;

import diary.PainEntryData;
import diary.constants.PainDataIdentifier;
import diary.constants.XMLIdentifier;

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
		}
		
		return map;
	}
	
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

	public static LinkedHashMap<String, Float> getNumberOfDifferentPainKind(List<PainEntryData> list)
	{
		LinkedHashMap<String, Float> map = new LinkedHashMap<String, Float>();
		
		for (int i=0; i<list.size(); i++)
		{
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
	
	public static LinkedHashMap<String, Float> getNumberOfDifferentPainLocations(List<PainEntryData> list)
	{
		LinkedHashMap<String, Float> map = new LinkedHashMap<String, Float>();
		
		for (int i=0; i<list.size(); i++)
		{
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
	
	public static LinkedHashMap<String, Float> getAmountOfActivity(List<PainEntryData> list)
	{
		LinkedHashMap<String, Float> map = new LinkedHashMap<String, Float>();
		
		for (int i=0; i<list.size(); i++)
		{
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
					for (int a=0; a<positions.length()-1; a++)
					{
						for (int b=a+1; b<positions.length(); b++)
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
					String painKinds = list.get(i).getPainKindAsString();
					for (int a=0; a<painKinds.length()-1; a++)
					{
						for (int b=a+1; b<painKinds.length(); b++)
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
					String intensities = list.get(i).getIntensitiesAsString();
					for (int a=0; a<intensities.length()-1; a++)
					{
						for (int b=a+1; b<intensities.length(); b++)
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
					String durations = list.get(i).getDurationsAsString();
					for (int a=0; a<durations.length()-1; a++)
					{
						for (int b=a+1; b<durations.length(); b++)
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
				else if (filterType.equals(XMLIdentifier.TABLE_FILTER_TYPE_DURATIONS_TEXT))		//If filtered by activity
				{
					String activity = list.get(i).getDataMap().get(PainDataIdentifier.ACTIVITY).toString();
					for (int a=0; a<activity.length()-1; a++)
					{
						for (int b=a+1; b<activity.length(); b++)
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
					for (int a=0; a<comment.length()-1; a++)
					{
						for (int b=a+1; b<comment.length(); b++)
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
}
