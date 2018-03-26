package diary.data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import diary.constants.PainDataIdentifier;
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
	//	this.dataMap.put(PainDataIdentifier.TIME_SECONDS, "00");
		this.dataMap.put(PainDataIdentifier.PAIN_AMOUNT, "1");
		this.dataMap.put(PainDataIdentifier.PAIN_LOCATION_PRESET, "");
		this.dataMap.put(PainDataIdentifier.PAIN_LOCATION_CUSTOM, "");
		this.dataMap.put(PainDataIdentifier.PAIN_KIND, "");
		this.dataMap.put(PainDataIdentifier.INTENSITY, "0");
		this.dataMap.put(PainDataIdentifier.DURATION, "0");
		this.dataMap.put(PainDataIdentifier.ACTIVITY, "");
		this.dataMap.put(PainDataIdentifier.ACTIVITY_DETAILS, "");
		this.dataMap.put(PainDataIdentifier.RECENT_MEDICATION, "");
		this.dataMap.put(PainDataIdentifier.MEDICINE_COMPLAINT, "");
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
//		appendToMap(this.dataMap, rootElement, PainDataIdentifier.TIME_SECONDS);
		//Pain
		List<String> presetLocations = new ArrayList<String>();
		for (Element element : XMLManager.getElements(rootElement.getElementsByTagName(PainDataIdentifier.PAIN_LOCATION_PRESET)))
		{
			presetLocations.add(element.getTextContent());
		}
		List<String> customLocations = new ArrayList<String>();
		for (Element element : XMLManager.getElements(rootElement.getElementsByTagName(PainDataIdentifier.PAIN_LOCATION_CUSTOM)))
		{
			customLocations.add(element.getTextContent());
		}
		this.dataMap.put(PainDataIdentifier.PAIN_LOCATION_PRESET, presetLocations);
		this.dataMap.put(PainDataIdentifier.PAIN_LOCATION_CUSTOM, customLocations);
		//Others
		appendToMap(this.dataMap, rootElement, PainDataIdentifier.PAIN_KIND);
		appendToMap(this.dataMap, rootElement, PainDataIdentifier.ACTIVITY);
		appendToMap(this.dataMap, rootElement, PainDataIdentifier.INTENSITY);
		appendToMap(this.dataMap, rootElement, PainDataIdentifier.DURATION);
		appendToMap(this.dataMap, rootElement, PainDataIdentifier.ACTIVITY_DETAILS);
		appendToMap(this.dataMap, rootElement, PainDataIdentifier.RECENT_MEDICATION);
		appendToMap(this.dataMap, rootElement, PainDataIdentifier.MEDICINE_COMPLAINT);
		appendToMap(this.dataMap, rootElement, PainDataIdentifier.COMMENTS);
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
	
	public String getMonthAndYear()
	{
		return this.dataMap.get(PainDataIdentifier.DATE_MONTH) + "/"
				+ this.dataMap.get(PainDataIdentifier.DATE_YEAR);
	}
	
	public String getFullTime()
	{
		return this.dataMap.get(PainDataIdentifier.TIME_HOUR) + ":"
				+ this.dataMap.get(PainDataIdentifier.TIME_MINUTE)/* + ":"
				+ this.dataMap.get(PainDataIdentifier.TIME_SECONDS)*/;
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
		
		//Preset
		List<String> locations = (List<String>)this.dataMap.get(PainDataIdentifier.PAIN_LOCATION_PRESET);
		for (int a=0; a<locations.size(); a++)
		{
			sb.append(locations.get(a));
			if (locations.size()-a!=1)
			{
				sb.append(", ");
			}
		}
		
		//Custom
		locations = (List<String>)this.dataMap.get(PainDataIdentifier.PAIN_LOCATION_CUSTOM);
		for (int a=0; a<locations.size(); a++)
		{
			sb.append("(");
			sb.append(locations.get(a));
			sb.append(")");
			if (locations.size()-a!=1)
			{
				sb.append(", ");
			}
		}
		
		return sb.toString();
	}
	
	/*
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
	
	/*
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
	*/
	
	public Document getDocumentForm() 
	{
		return this.document;
	}
}
