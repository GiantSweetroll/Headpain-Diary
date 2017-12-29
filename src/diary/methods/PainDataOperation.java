package diary.methods;

import java.util.LinkedHashMap;
import java.util.List;

import diary.PainEntryData;
import diary.constants.PainDataIdentifier;

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
			List<LinkedHashMap<String, Object>> painLocations = (List<LinkedHashMap<String, Object>>)list.get(i).getDataMap().get(PainDataIdentifier.PAIN_LOCATIONS);
			for (int a=0; a<painLocations.size(); a++)
			{
				String key = painLocations.get(a).get(PainDataIdentifier.ACTIVITY).toString();
				
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
}
