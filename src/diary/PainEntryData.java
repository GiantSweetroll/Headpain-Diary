package diary;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import diary.constants.PainDataIdentifier;
import diary.constants.PainLocationConstants;
import giantsweetroll.xml.dom.XMLManager;

public class PainEntryData 
{
	private LinkedHashMap<String, Object> dataMap;
	private Document document;
	
	//Constructor
	public PainEntryData()		//Empty data
	{
		this.dataMap = new LinkedHashMap<String, Object>();
		
		//Enter default values
		this.dataMap.put(PainDataIdentifier.DATE_DAY, "01");
		this.dataMap.put(PainDataIdentifier.DATE_MONTH, "1");
		this.dataMap.put(PainDataIdentifier.DATE_YEAR, "2016");
		this.dataMap.put(PainDataIdentifier.TIME_HOUR, "00");
		this.dataMap.put(PainDataIdentifier.TIME_MINUTE, "00");
		this.dataMap.put(PainDataIdentifier.TIME_SECONDS, "00");
		this.dataMap.put(PainDataIdentifier.PAIN_AMOUNT, "1");
		List<LinkedHashMap<String, Object>> painLocationsMapList = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put(PainDataIdentifier.GENERAL_POSITION, PainLocationConstants.HEAD);
		map.put(PainDataIdentifier.GENERAL_POSITION_2, PainLocationConstants.HEAD_BACK_GENERAL);
		map.put(PainDataIdentifier.SPECIFIC_LOCATION, PainLocationConstants.HEAD_BACK_BOTTOM_LEFT);
		map.put(PainDataIdentifier.PAIN_KIND, "");
		map.put(PainDataIdentifier.INTENSITY, "0");
		map.put(PainDataIdentifier.DURATION, "0");
		painLocationsMapList.add(map);
		this.dataMap.put(PainDataIdentifier.PAIN_LOCATIONS, painLocationsMapList);
		this.dataMap.put(PainDataIdentifier.ACTIVITY, "");
		this.dataMap.put(PainDataIdentifier.ACTIVITY_DETAILS, "");
		this.dataMap.put(PainDataIdentifier.RECENT_MEDICATION, "");
		this.dataMap.put(PainDataIdentifier.COMMENTS, "");
	}
	
	public PainEntryData(LinkedHashMap<String, Object> dataMap)
	{
		this.dataMap = dataMap;
	}
	
	public PainEntryData(Document doc)
	{
		this.document = doc;
		this.dataMap = new LinkedHashMap<String, Object>();
		Element rootElement = XMLManager.getElement(doc.getElementsByTagName(PainDataIdentifier.MASTER_NODE), 0);
		
		//add to map
		//Date
		appendToMap(this.dataMap, rootElement, PainDataIdentifier.DATE_DAY);
		appendToMap(this.dataMap, rootElement, PainDataIdentifier.DATE_MONTH);
		appendToMap(this.dataMap, rootElement, PainDataIdentifier.DATE_YEAR);
		//Time
		appendToMap(this.dataMap, rootElement, PainDataIdentifier.TIME_HOUR);
		appendToMap(this.dataMap, rootElement, PainDataIdentifier.TIME_MINUTE);
		appendToMap(this.dataMap, rootElement, PainDataIdentifier.TIME_SECONDS);
		//Pain
		appendToMap(this.dataMap, rootElement, PainDataIdentifier.PAIN_AMOUNT);
		Element painLocations = XMLManager.getElement(rootElement.getElementsByTagName(PainDataIdentifier.PAIN_LOCATIONS), 0);
		List<Element> painLocationsElementList = XMLManager.getElements(painLocations.getElementsByTagName(PainDataIdentifier.PAIN_LOCATION));
		List<LinkedHashMap<String, Object>> painLocationsMapList = new ArrayList<LinkedHashMap<String, Object>>();
		for (int i=0; i<painLocationsElementList.size(); i++)
		{
			painLocationsMapList.add(this.mapPainLocationData(painLocationsElementList.get(i)));
		}
		this.dataMap.put(PainDataIdentifier.PAIN_LOCATIONS, painLocationsMapList);
		//Others
		appendToMap(this.dataMap, rootElement, PainDataIdentifier.ACTIVITY);
		appendToMap(this.dataMap, rootElement, PainDataIdentifier.ACTIVITY_DETAILS);
		appendToMap(this.dataMap, rootElement, PainDataIdentifier.RECENT_MEDICATION);
		appendToMap(this.dataMap, rootElement, PainDataIdentifier.COMMENTS);
	}
	
	private LinkedHashMap<String, Object> mapPainLocationData(Element element)
	{
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		
		appendToMap(map, element, PainDataIdentifier.GENERAL_POSITION);
		appendToMap(map, element, PainDataIdentifier.GENERAL_POSITION_2);
		appendToMap(map, element, PainDataIdentifier.SPECIFIC_LOCATION);
		appendToMap(map, element, PainDataIdentifier.PAIN_KIND);
		appendToMap(map, element, PainDataIdentifier.INTENSITY);
		appendToMap(map, element, PainDataIdentifier.DURATION);
		
		return map;
	}
	
	private void appendToMap(LinkedHashMap<String, Object> map, Element sourceElement, String dataID)
	{
		try
		{
			map.put(dataID, XMLManager.getElement(sourceElement.getElementsByTagName(dataID), 0).getTextContent());
		}
		catch(NullPointerException ex)
		{
			map.put(dataID, "");
		}
	}
	
	public LinkedHashMap<String, Object> getDataMap()
	{
		return this.dataMap;
	}
	
	public String getFullDate()
	{
		return this.dataMap.get(PainDataIdentifier.DATE_DAY) + "/"
				+ this.dataMap.get(PainDataIdentifier.DATE_MONTH) + "/"
				+ this.dataMap.get(PainDataIdentifier.DATE_YEAR);
	}
	
	public String getFullTime()
	{
		return this.dataMap.get(PainDataIdentifier.TIME_HOUR) + ":"
				+ this.dataMap.get(PainDataIdentifier.TIME_MINUTE) + ":"
				+ this.dataMap.get(PainDataIdentifier.TIME_SECONDS);
	}
	
	public String getFullTimeAndDate()
	{
		return this.getFullTime() + " " + this.getFullDate();
	}
	
	public LinkedHashMap<String, String> getDateMap()
	{
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		
		map.put(PainDataIdentifier.DATE_DAY, this.dataMap.get(PainDataIdentifier.DATE_DAY).toString());
		map.put(PainDataIdentifier.DATE_MONTH, this.dataMap.get(PainDataIdentifier.DATE_MONTH).toString());
		map.put(PainDataIdentifier.DATE_YEAR, this.dataMap.get(PainDataIdentifier.DATE_YEAR).toString());
		
		return map;
	}
	
	public String getPainPositionsAsString()
	{
		StringBuilder sb = new StringBuilder();
		
		List<LinkedHashMap<String, Object>> list = (ArrayList<LinkedHashMap<String, Object>>)this.getDataMap().get(PainDataIdentifier.PAIN_LOCATIONS);
		for (int i=0; i<list.size(); i++)
		{
			sb.append(list.get(i).get(PainDataIdentifier.GENERAL_POSITION).toString());
			sb.append(" (" + list.get(i).get(PainDataIdentifier.GENERAL_POSITION_2).toString() + ")");
			sb.append(" (" + list.get(i).get(PainDataIdentifier.SPECIFIC_LOCATION) + ")");
			
			if (list.size()-i!=1)
			{
				sb.append(", ");
			}
		}
		
		return sb.toString();
	}
	
	public String getPainKindAsString()
	{
		StringBuilder sb = new StringBuilder();
		
		List<LinkedHashMap<String, Object>> list = (ArrayList<LinkedHashMap<String, Object>>)this.getDataMap().get(PainDataIdentifier.PAIN_LOCATIONS);
		for (int i=0; i<list.size(); i++)
		{
			sb.append(list.get(i).get(PainDataIdentifier.PAIN_KIND).toString());
			
			if (list.size()-i!=1)
			{
				sb.append(", ");
			}
		}
		
		return sb.toString();
	}
	
	public String getIntensitiesAsString()
	{
		StringBuilder sb = new StringBuilder();
		
		List<LinkedHashMap<String, Object>> list = (ArrayList<LinkedHashMap<String, Object>>)this.getDataMap().get(PainDataIdentifier.PAIN_LOCATIONS);
		for (int i=0; i<list.size(); i++)
		{
			sb.append(list.get(i).get(PainDataIdentifier.INTENSITY).toString());
			
			if (list.size()-i!=1)
			{
				sb.append(", ");
			}
		}
		
		return sb.toString();
	}
	
	public String getDurationsAsString()
	{
		StringBuilder sb = new StringBuilder();
		
		List<LinkedHashMap<String, Object>> list = (ArrayList<LinkedHashMap<String, Object>>)this.getDataMap().get(PainDataIdentifier.PAIN_LOCATIONS);
		for (int i=0; i<list.size(); i++)
		{
			sb.append(list.get(i).get(PainDataIdentifier.DURATION).toString());
			
			if (list.size()-i!=1)
			{
				sb.append(", ");
			}
		}
		
		return sb.toString();
	}
	
	public Document getDocumentForm() 
	{
		return this.document;
	}
}
