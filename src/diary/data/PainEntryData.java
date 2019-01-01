package diary.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import diary.constants.Constants;
import diary.constants.PainDataIdentifier;
import diary.methods.Methods;
import giantsweetroll.date.Date;
import giantsweetroll.xml.dom.XMLManager;

public class PainEntryData 
{
	private HashMap<String, Object> dataMap;

	//Constructors
	public PainEntryData()
	{
		this.dataMap = new HashMap<String, Object>();
		
		this.setComments("");
		this.setDate(new Date());
		this.setTime("00", "00");
		this.setDuration("0");
		this.setIntensity("0");
		this.setPainKind("");
		this.setRecentMedicaiton("");
		this.setMedicineComplaint("");
		this.setTrigger("");
	}
	public PainEntryData(Document doc)
	{
		this.dataMap = new HashMap<String, Object>();
		
		this.setComments(Methods.getElementTextContent(doc, PainDataIdentifier.COMMENTS));
		this.setDate(new Date(Integer.parseInt(Methods.getElementTextContent(doc, PainDataIdentifier.DATE_DAY)), 
								Integer.parseInt(Methods.getElementTextContent(doc, PainDataIdentifier.DATE_MONTH)),
								Integer.parseInt(Methods.getElementTextContent(doc, PainDataIdentifier.DATE_YEAR))));
		this.setTime(Methods.getElementTextContent(doc, PainDataIdentifier.TIME_HOUR), 
						Methods.getElementTextContent(doc, PainDataIdentifier.TIME_MINUTE));
		this.setDuration(Methods.getElementTextContent(doc, PainDataIdentifier.DURATION));
		this.setIntensity(Methods.getElementTextContent(doc, PainDataIdentifier.INTENSITY));
		this.setPainKind(Methods.getElementTextContent(doc, PainDataIdentifier.PAIN_KIND));
		this.setCustomPainLocations(Methods.getTextContentsFromElements(XMLManager.getElements(doc.getElementsByTagName(PainDataIdentifier.PAIN_LOCATION_CUSTOM))));
		this.setPresetPainLocations(Methods.getTextContentsFromElements(XMLManager.getElements(doc.getElementsByTagName(PainDataIdentifier.PAIN_LOCATION_PRESET))));
		this.setRecentMedicaiton(Methods.getElementTextContent(doc, PainDataIdentifier.RECENT_MEDICATION));
		this.setMedicineComplaint(Methods.getElementTextContent(doc, PainDataIdentifier.MEDICINE_COMPLAINT));
		this.setTrigger(Methods.getElementTextContent(doc, PainDataIdentifier.ACTIVITY));
	}
	
	//Public Methods
	public void setData(String key, Object data)
	{
		this.dataMap.put(key, data);
	}
	public void setDate(Date date)
	{
		this.setData(PainDataIdentifier.DATE_DAY, date.getDay());
		this.setData(PainDataIdentifier.DATE_MONTH, date.getMonth());
		this.setData(PainDataIdentifier.DATE_YEAR, date.getYear());
	}
	public Date getDate()
	{
		return new Date(Integer.parseInt(this.dataMap.get(PainDataIdentifier.DATE_DAY).toString()),
				Integer.parseInt(this.dataMap.get(PainDataIdentifier.DATE_MONTH).toString()),
				Integer.parseInt(this.dataMap.get(PainDataIdentifier.DATE_YEAR).toString()));
	}
	public void setTime(String hour, String minutes)
	{
		this.setData(PainDataIdentifier.TIME_HOUR, hour);
		this.setData(PainDataIdentifier.TIME_MINUTE, minutes);
	}
	public void setTimeHour(String hour)
	{
		this.setData(PainDataIdentifier.TIME_HOUR, hour);
	}
	public void setTimeMinutes(String minutes)
	{
		this.setData(PainDataIdentifier.TIME_MINUTE, minutes);
	}
	public String getTimeHour()
	{
		return this.getDataAt(PainDataIdentifier.TIME_HOUR).toString();
	}
	public String getTimeMinutes()
	{
		return this.getDataAt(PainDataIdentifier.TIME_MINUTE).toString();
	}
	public void setPainKind(String painKind)
	{
		this.setData(PainDataIdentifier.PAIN_KIND, painKind);
	}
	public String getPainKind()
	{
		return this.getDataAt(PainDataIdentifier.PAIN_KIND).toString();
	}
	public void setTrigger(String trigger)
	{
		this.setData(PainDataIdentifier.ACTIVITY, trigger);
	}
	public String getTrigger()
	{
		return this.getDataAt(PainDataIdentifier.ACTIVITY).toString();
	}
	public void setIntensity(String intensity)
	{
		this.setData(PainDataIdentifier.INTENSITY, intensity);
	}
	public String getIntensity()
	{
		return this.getDataAt(PainDataIdentifier.INTENSITY).toString();
	}
	public void setDuration(String duration)
	{
		this.setData(PainDataIdentifier.DURATION, duration);
	}
	public String getDuration()
	{
		return this.getDataAt(PainDataIdentifier.DURATION).toString();
	}
	public void setRecentMedicaiton(String meds)
	{
		this.setData(PainDataIdentifier.RECENT_MEDICATION, meds);
	}
	public String getRecentMedication()
	{
		return this.getDataAt(PainDataIdentifier.RECENT_MEDICATION).toString();
	}
	public void setMedicineComplaint(String complaint)
	{
		this.setData(PainDataIdentifier.MEDICINE_COMPLAINT, complaint);
	}
	public String getMedicineComplaint()
	{
		return this.getDataAt(PainDataIdentifier.MEDICINE_COMPLAINT).toString();
	}
	public void setComments(String comments)
	{
		this.setData(PainDataIdentifier.COMMENTS, comments);
	}
	public String getComments()
	{
		return this.getDataAt(PainDataIdentifier.COMMENTS).toString();
	}
	public void setPresetPainLocations(List<String> painLoc)
	{	
		if (painLoc.size() != 0)
		{
			this.setData(PainDataIdentifier.PAIN_LOCATION_PRESET, painLoc);
			
			try
			{
				this.setData(PainDataIdentifier.PAIN_LOCATION_CUSTOM, new ArrayList<String>());
			}
			catch(NullPointerException ex) {ex.printStackTrace();}
		}
	}
	public List<String> getPresetPainLocations()
	{
		return (List<String>)this.getDataAt(PainDataIdentifier.PAIN_LOCATION_PRESET);
	}
	public void setCustomPainLocations(List<String> painLoc)
	{
		if (painLoc.size() != 0)
		{
			this.setData(PainDataIdentifier.PAIN_LOCATION_CUSTOM, painLoc);
			
			try
			{
				this.setData(PainDataIdentifier.PAIN_LOCATION_PRESET, new ArrayList<String>());
			}
			catch(NullPointerException ex) {ex.printStackTrace();}
		}
	}
	public List<String> getCustomPainLocations()
	{
		return (List<String>)this.getDataAt(PainDataIdentifier.PAIN_LOCATION_CUSTOM);
	}
	public String getFullDate()
	{
		return this.dataMap.get(PainDataIdentifier.DATE_DAY) + Constants.PAIN_ENTRY_DATE_SEPARATOR
				+ this.dataMap.get(PainDataIdentifier.DATE_MONTH) + Constants.PAIN_ENTRY_DATE_SEPARATOR
				+ this.dataMap.get(PainDataIdentifier.DATE_YEAR);
	}
	
	public String getMonthAndYear()
	{
		return this.dataMap.get(PainDataIdentifier.DATE_MONTH) + Constants.PAIN_ENTRY_DATE_SEPARATOR
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
	
	public HashMap<String, Object> getDataMap()
	{
		return this.dataMap;
	}

	public Document getXMLDocument()
	{
		Document doc = null;
		try
		{
			doc = XMLManager.createDocument();
			
			Element root = doc.createElement(PainDataIdentifier.MASTER_NODE);
			
			for (Map.Entry<String, Object> entry : this.dataMap.entrySet())
			{
				if (entry.getValue() instanceof List<?>)
				{
					List<String> list = (List<String>)entry.getValue();
					for (int i=0; i<list.size(); i++)
					{
						Element element = doc.createElement(entry.getKey());
						element.setTextContent(list.get(i));
						element.setAttribute(PainDataIdentifier.PAIN_LOCATION_ID, Integer.toString(i));
						root.appendChild(element);
					}
				}
				else
				{
					Element element = doc.createElement(entry.getKey());
					element.setTextContent(entry.getValue().toString());
					root.appendChild(element);
				}
			}
			
			doc.appendChild(root);
			
			return doc;
		}
		catch(Exception ex)
		{
			return doc;
		}
	}
	
	public String getPainPositionsAsString()
	{
		StringBuilder sb = new StringBuilder();
		List<String> locations = new ArrayList<String>();
		
		//Preset
		try
		{
			locations = this.getPresetPainLocations();
			for (int a=0; a<locations.size(); a++)
			{
				sb.append(locations.get(a));
				if (locations.size()-a!=1)
				{
					sb.append(", ");
				}
			}
		}
		catch(ClassCastException ex) {ex.printStackTrace();}
		
		//Custom
		try
		{
			locations = this.getCustomPainLocations();
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
		}
		catch(ClassCastException ex) {ex.printStackTrace();}
		
		return sb.toString();
	}
	
	public Object getDataAt(String key)
	{
		return this.dataMap.get(key);
	}
	
	public boolean isSingleEntry()
	{
		return Methods.secondsToDays(Long.parseLong(this.getDuration())) <= 1;
	}

	public String[] getDataAsStringArray()
	{
		List<String> arr = new ArrayList<String>();
		
		arr.add(this.getFullDate());
		arr.add(this.getFullTime());
		arr.add(this.getPainPositionsAsString());
		arr.add(Methods.convertPainKindLanguageToID(this.getPainKind()));
		arr.add(this.getIntensity());
		arr.add(this.getDuration());
		arr.add(Methods.convertActivityIDToLanguage(this.getTrigger()));
		arr.add(this.getRecentMedication());
		arr.add(this.getMedicineComplaint());
		arr.add(this.getComments());
		
		return arr.toArray(new String[arr.size()]);
	}
}
