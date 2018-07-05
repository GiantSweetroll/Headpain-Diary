package diary.legacy.data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import diary.constants.PainDataIdentifier;
import giantsweetroll.date.Date;
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
	
	public PainEntryData(PainEntryData entry)
	{
		this(entry.getDocumentForm()); 
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
	
	public Date getDate()
	{
		return new Date(Integer.parseInt(this.dataMap.get(PainDataIdentifier.DATE_DAY).toString()),
				Integer.parseInt(this.dataMap.get(PainDataIdentifier.DATE_MONTH).toString()),
				Integer.parseInt(this.dataMap.get(PainDataIdentifier.DATE_YEAR).toString()));
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
	
	public Document getDocumentForm() 
	{
		this.synchronizeDocumentWithMap();
		return this.document;
	}
	
	public void setDate(Date date)
	{
		String day = Integer.toString(date.getDay());
		
		if (day.length()==1)
		{
			day = "0" + day;
		}
		
		this.dataMap.put(PainDataIdentifier.DATE_DAY, day);
		this.dataMap.put(PainDataIdentifier.DATE_MONTH, Integer.toString(date.getMonth()));
		this.dataMap.put(PainDataIdentifier.DATE_YEAR, Integer.toString(date.getYear()));
	}
	
	
	private void synchronizeDocumentWithMap()
	{
		try 
		{
			this.document = XMLManager.createDocument();
			this.document.appendChild(this.document.createElement(PainDataIdentifier.MASTER_NODE));
		} 
		catch (ParserConfigurationException e) 
		{
			e.printStackTrace();
		}
		
		for (Map.Entry<String, Object> entry : this.dataMap.entrySet())
		{
			if (entry.getValue() instanceof List<?>)
			{
				for (int i=0; i<((List<?>)entry.getValue()).size(); i++)
				{
					Element element = this.document.createElement(entry.getKey());
					element.setAttribute(PainDataIdentifier.PAIN_LOCATION_ID, Integer.toString(i));
					element.setTextContent(((List<?>)entry.getValue()).get(i).toString());
			//		this.document.replaceChild(element, XMLManager.getElement(this.document.getElementsByTagName(entry.getKey()), i));
					XMLManager.getElement(this.document.getElementsByTagName(PainDataIdentifier.MASTER_NODE), 0).appendChild(element);
				}
			}
			else
			{
				Element element = this.document.createElement(entry.getKey());
				element.setTextContent(entry.getValue().toString());
		//		this.document.replaceChild(element, XMLManager.getElement(this.document.getElementsByTagName(entry.getKey()), 0));
				XMLManager.getElement(this.document.getElementsByTagName(PainDataIdentifier.MASTER_NODE), 0).appendChild(element);
			}
		}
	}
}
