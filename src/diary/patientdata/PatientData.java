package diary.patientdata;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import diary.constants.XMLIdentifier;
import diary.methods.Methods;
import giantsweetroll.date.Date;
import giantsweetroll.xml.dom.XMLManager;

public class PatientData 
{
	//Constants
	public static final String MEDICAL_RECORD_ID = "medical_record_id";
	public static final String NAME = "patient_name";
	public static final String DOB_DAY = "patient_dob_day";
	public static final String DOB_MONTH = "patient_dob_month";
	public static final String DOB_YEAR = "patient_dob_year";
	public static final String LAST_RECENT_MEDS = "recent_meds";
	public static final String LAST_MEDICINE_COMPLAINT = "recent_med_complaint";
	public static final String ROOT_NODE = "patient";
	public static final String HAS_HEAD_PAIN_HISTORY = "has_head_pain_history";
	public static final String HEAD_PAINS_SINCE = "head_pains_since";
	public static final String FREQUENCY_HEAD_PAINS_LAST_MONTH = "freq_head_pains_last_month";
	public static final String DAYS_ACTIVITIES_DISTURBED = "days_activities_disturbed";
	public static final String HEAD_PAINS_SINCE_UNIT = "head_pains_since_unit";
	public static final String NOTES = "notes";
	public static final String SEX = "sex";
	public static final String JOB = "job";
	public static final String MALE = "$$male$$";
	public static final String FEMALE = "$$female$$";
	public static final String CITY = "city";
	public static final String INITIAL_DIAGNOSIS = "initial_diagnosis";
	public static final String FINAL_DIAGNOSIS = "final_diagnosis";
	public static final String PAIN_LOCATIONS = "pain_locations";			//Not yet used
	public static final String PAIN_LOCATION_SUB = "pain_location";			//Not yet used
	public static final String PAIN_LOCATION_NODE = "pain_location_node";	//Not yet used
	public static final String LAST_PAIN_KIND = "last_pain_kind";
	public static final String LAST_TRIGGER = "last_trigger";
	
	public static final String HEAD_PAINS_SINCE_UNIT_MONTH = "%%sinceMonth",
								HEAD_PAINS_SINCE_UNIT_YEAR = "%%sinceYear",
								MIGRAINE = "%%migraine",
								TENSION_TYPE_HEADACHE = "%%tensiontype",
								SECONDARY_HEADACHE = "%%secondaryheadache";
	
	private HashMap<String, String> dataMap;
	
	public PatientData(HashMap<String, String> dataMap)
	{
		this.dataMap = dataMap;
	}
	
	public PatientData(Document doc)
	{
		this.dataMap = new LinkedHashMap<String, String>();
		Element rootElement = XMLManager.getElement(doc.getElementsByTagName(PatientData.ROOT_NODE), 0);
		NodeList childNodes = rootElement.getChildNodes();
		
		for (int i=0; i<childNodes.getLength(); i++)
		{
			this.dataMap.put(childNodes.item(i).getNodeName(), childNodes.item(i).getTextContent());
		}
	}
	
	public PatientData()
	{
		this.dataMap = new LinkedHashMap<String, String>();
	}
	
	//Methods
	//Getters
	public HashMap<String, String> getDataMap()
	{
		return this.dataMap;
	}
	
	public Document getXMLDocument()
	{
		Document doc = null;
		try 
		{
			doc = XMLManager.createDocument();
			Element rootElement = doc.createElement(PatientData.ROOT_NODE);
			
			for (Map.Entry<String, String> entry : this.dataMap.entrySet())
			{
				try
				{
					Element element = doc.createElement(entry.getKey());
					element.setTextContent(entry.getValue());
					rootElement.appendChild(element);
				}
				catch(DOMException ex) {}
			}
			
			doc.appendChild(rootElement);
		} 
		catch (ParserConfigurationException e)
		{
			e.printStackTrace();
		}
		
		return doc;
	}
	
	public String getID()
	{
		return this.dataMap.get(PatientData.MEDICAL_RECORD_ID);
	}
	
	public String getName()
	{
		return this.dataMap.get(PatientData.NAME);
	}
	
	public String getDOBString()
	{
		return this.dataMap.get(PatientData.DOB_DAY) + "-" + this.dataMap.get(PatientData.DOB_MONTH) + "-" + this.dataMap.get(PatientData.DOB_YEAR);
	}
	
	public Date getDOB()
	{
		return new Date(Integer.parseInt(this.dataMap.get(PatientData.DOB_DAY)),
						Integer.parseInt(this.dataMap.get(PatientData.DOB_MONTH)),
						Integer.parseInt(this.dataMap.get(PatientData.DOB_YEAR)));
	}
	
	public String getNameAndID()
	{
		return this.dataMap.get(PatientData.NAME) + " - " + this.dataMap.get(PatientData.MEDICAL_RECORD_ID);
	}
	public String getIDAndName()
	{
		return this.dataMap.get(PatientData.MEDICAL_RECORD_ID) + " - " + this.dataMap.get(PatientData.NAME);
	}
	public boolean hasHeadPainsHistory()
	{
		try
		{
			String item = this.dataMap.get(PatientData.HAS_HEAD_PAIN_HISTORY);
			try
			{
				return Boolean.parseBoolean(item);
			}
			catch(Exception ex)
			{
				return false;
			}
		}
		catch(NullPointerException ex)
		{
			return false;
		}
	}
	public String getHeadPainsSince()
	{
		try
		{
			return this.dataMap.get(PatientData.HEAD_PAINS_SINCE);
		}
		catch(NullPointerException ex)
		{
			return "";
		}
	}
	public String getHeadPainsSinceUnit()
	{
		try
		{
			String item = this.dataMap.get(PatientData.HEAD_PAINS_SINCE_UNIT);
			if (item.equals(PatientData.HEAD_PAINS_SINCE_UNIT_MONTH))
			{
				return Methods.getLanguageText(XMLIdentifier.MONTH_TEXT);
			}
			else if (item.equals(PatientData.HEAD_PAINS_SINCE_UNIT_YEAR))
			{
				return Methods.getLanguageText(XMLIdentifier.YEAR_TEXT);
			}
			else
			{
				return "";
			}
		}
		catch(NullPointerException ex)
		{
			return "";
		}
	}
	public String getPainFrequencyLastMonth()
	{
		try
		{
			return this.dataMap.get(PatientData.FREQUENCY_HEAD_PAINS_LAST_MONTH);
		}
		catch(NullPointerException ex)
		{
			return "";
		}
	}
	public String getDaysActivitiesDisturbedLastMonth()
	{
		try
		{
			return this.dataMap.get(PatientData.DAYS_ACTIVITIES_DISTURBED);
		}
		catch(NullPointerException ex)
		{
			return "";
		}
	}
	public String getLastRecentMeds()
	{
		try
		{
			return this.dataMap.get(PatientData.LAST_RECENT_MEDS).toString();
		}
		catch(NullPointerException ex)
		{
			return "";
		}
	}
	public String getLastMedicineComplaint()
	{
		try
		{
			return this.dataMap.get(PatientData.LAST_MEDICINE_COMPLAINT);
		}
		catch(NullPointerException ex)
		{
			return "";
		}
	}
	public String getNotes()
	{
		try
		{
			return this.dataMap.get(PatientData.NOTES);
		}
		catch(NullPointerException ex)
		{
			return "";
		}
	}
	public String getSex()
	{
		try
		{
			return this.dataMap.get(PatientData.SEX);
		}
		catch(NullPointerException ex)
		{
			return "";
		}
	}
	public boolean isMale()
	{
		try
		{
			return this.getSex().equals(PatientData.MALE);

		}
		catch(NullPointerException ex)
		{
			return false;
		}
	}
	public boolean isFemale()
	{
		try
		{
			return this.getSex().equals(PatientData.FEMALE);

		}
		catch(NullPointerException ex)
		{
			return false;
		}
	}
	public String getOccupation()
	{
		try
		{
			return this.dataMap.get(PatientData.JOB);
		}
		catch(NullPointerException ex)
		{
			return "";
		}
	}
	public String getCity()
	{
		try
		{
			return this.dataMap.get(PatientData.CITY);
		}
		catch(NullPointerException ex)
		{
			return "";
		}
	}
	public String getInitialDiagnosis()
	{
		try
		{
			return this.dataMap.get(PatientData.INITIAL_DIAGNOSIS);
		}
		catch(NullPointerException ex)
		{
			return "";
		}
	}
	public String getFinalDiagnosis()
	{
		try
		{
			return this.dataMap.get(PatientData.FINAL_DIAGNOSIS);
		}
		catch(NullPointerException ex)
		{
			return "";
		}
	}
	public String getLastPainKind()
	{
		try
		{
			return this.dataMap.get(PatientData.LAST_PAIN_KIND).toString();
		}
		catch(NullPointerException ex)
		{
			return "";
		}
	}
	public String getLastTrigger()
	{
		try
		{
			return this.dataMap.get(PatientData.LAST_TRIGGER).toString();
		}
		catch(NullPointerException ex)
		{
			return "";
		}		
	}
	public String getRecentSelectedOption(String key)
	{
		try
		{
			return this.dataMap.get(key);
		}
		catch(NullPointerException ex)
		{
			return "";
		}
	}
	
	
	public String getFileName()
	{
		return this.getDataMap().get(PatientData.MEDICAL_RECORD_ID) + ".xml";
	}
	
	//Setters
	public void setLastPainKind(String painKind)
	{
		this.dataMap.put(PatientData.LAST_PAIN_KIND, painKind);
	}
	public void setLastMedicineComplaint(String compl)
	{
		this.dataMap.put(PatientData.LAST_MEDICINE_COMPLAINT, compl);
	}
	public void setLastRecentMeds(String meds)
	{
		this.dataMap.put(PatientData.LAST_RECENT_MEDS, meds);
	}
	public void setLastTrigger(String trigger)
	{
		this.dataMap.put(PatientData.LAST_TRIGGER, trigger);
	}
	
	//Override Methods
	@Override
	public String toString()
	{
		return this.getNameAndID();
	}
}
