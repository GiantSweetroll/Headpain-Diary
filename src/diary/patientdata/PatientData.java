package diary.patientdata;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

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
	public static final String PREVIOUS_HEADPAINS = "previous_headpains";
	public static final String LAST_RECENT_MEDS = "recent_meds";
	public static final String LAST_MEDICINE_COMPLAINT = "recent_med_complaint";
	public static final String ROOT_NODE = "patient";
	
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
				Element element = doc.createElement(entry.getKey());
				element.setTextContent(entry.getValue());
				rootElement.appendChild(element);
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
	public String getPreviousHeadpains()
	{
		try
		{
			return this.dataMap.get(PatientData.PREVIOUS_HEADPAINS).toString();
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
	public void setLastRecentMeds(String meds)
	{
		this.dataMap.put(PatientData.LAST_RECENT_MEDS, meds);
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
	public void setLastMedicineComplaint(String compl)
	{
		this.dataMap.put(PatientData.LAST_MEDICINE_COMPLAINT, compl);
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
	
	//Override Methods
	@Override
	public String toString()
	{
		return this.getNameAndID();
	}
}
