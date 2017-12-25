package diary;

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
	
	public PainEntryData(LinkedHashMap<String, Object> dataMap)
	{
		this.dataMap = dataMap;
	}
	
	public PainEntryData(Document doc)
	{
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
		map.put(dataID, XMLManager.getElement(sourceElement.getElementsByTagName(dataID), 0).getTextContent());
	}
	
	public LinkedHashMap<String, Object> getDataMap()
	{
		return this.dataMap;
	}
}
