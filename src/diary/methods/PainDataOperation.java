package diary.methods;

import java.util.LinkedHashMap;
import java.util.List;

import diary.PainEntryData;
import diary.constants.PainDataIdentifier;

public class PainDataOperation 
{
	public static LinkedHashMap<String, Integer> getAmountOfEntriesVSDate(List<PainEntryData> list)
	{
		LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
		
		for (int i=0; i<list.size(); i++)
		{
			try
			{
				map.put(list.get(i).getFullDate(), map.get(list.get(i).getFullDate())+1);
			}
			catch(NullPointerException ex) 
			{
				map.put(list.get(i).getFullDate(), 1);
			}
		}
		
		return map;
	}
	
	public static LinkedHashMap<String, Integer> getAmountOfHeadPainsVSDate(List<PainEntryData> list)
	{
		LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
		
		for (int i=0; i<list.size(); i++)
		{
			try
			{
				map.put(list.get(i).getFullDate(), map.get(list.get(i).getFullDate()) + Integer.parseInt((String)list.get(i).getDataMap().get(PainDataIdentifier.PAIN_AMOUNT)));
			}
			catch(NullPointerException ex)
			{
				map.put(list.get(i).getFullDate(), Integer.parseInt((String)list.get(i).getDataMap().get(PainDataIdentifier.PAIN_AMOUNT)));
			}
		}
		
		return map;
	}
	
	public static LinkedHashMap<String, Integer> getIntensityVSTime(List<PainEntryData> list)
	{
		LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
		
		for (int i=0; i<list.size(); i++)
		{
			try
			{
				map.put(list.get(i).getFullTimeAndDate(), 
							map.get(list.get(i).getFullTimeAndDate()) + (int)list.get(i).getDataMap().get(PainDataIdentifier.INTENSITY));
			}
			catch(NullPointerException ex)
			{
				map.put(list.get(i).getFullTimeAndDate(), (int)list.get(i).getDataMap().get(PainDataIdentifier.INTENSITY));
			}
		}
		
		return map;
	}
	
	public static LinkedHashMap<String, Integer> getDurationVSTime(List<PainEntryData> list)
	{
		LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
		
		for (int i=0; i<list.size(); i++)
		{
			try
			{
				map.put(list.get(i).getFullTimeAndDate(), 
						map.get(list.get(i).getFullTimeAndDate()) + (int)list.get(i).getDataMap().get(PainDataIdentifier.DURATION));
			}
			catch(NullPointerException ex)
			{
				map.put(list.get(i).getFullTimeAndDate(), (int)list.get(i).getDataMap().get(PainDataIdentifier.DURATION));
			}
		}
		
		return map;
	}
}
