package diary.patientdata;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.LinkedHashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import diary.constants.Constants;
import diary.constants.PainDataIdentifier;
import diary.constants.XMLIdentifier;
import diary.gui.DatePanel;
import diary.methods.Methods;
import giantsweetroll.gui.swing.Gbm;

public class PatientDataForm extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2197578997400146750L;
	
	private JLabel labMedID, labName, labDOB;
	private JTextField tfMedID, tfName;
	private DatePanel panelDOB;
	
	public PatientDataForm(boolean enable)
	{
		this.init();
		this.enableComponents(enable);
	}
	public PatientDataForm(PatientData patientData, boolean enable)
	{
		this.init();
		
		LinkedHashMap<String, String> dataMap = patientData.getDataMap();
		this.tfMedID.setText(dataMap.get(PatientData.MEDICAL_RECORD_ID));
		this.tfName.setText(dataMap.get(PatientData.NAME));
		this.panelDOB.setDate(dataMap.get(PatientData.DOB_DAY), dataMap.get(PatientData.DOB_MONTH), dataMap.get(PatientData.DOB_YEAR));
		this.panelDOB.setAsDefaultDataThis();
		this.enableComponents(enable);
	}
	
	//Methods
	private void enableComponents(boolean enable)
	{
		this.tfMedID.setEditable(enable);
		this.tfName.setEditable(enable);
		this.panelDOB.enableComponents(enable);
	}
	private void init()
	{
		//Initialization
		this.labMedID = new JLabel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.PATIENT_DATA_FORM_MED_ID_LABEL), SwingConstants.RIGHT);
		this.tfMedID = new JTextField(10);
		this.labName = new JLabel (Constants.LANGUAGE.getTextMap().get(XMLIdentifier.PATIENT_DATA_FORM_NAME_LABEL), SwingConstants.RIGHT);
		this.tfName = new JTextField (10);
		this.labDOB = new JLabel (Constants.LANGUAGE.getTextMap().get(XMLIdentifier.PATIENT_DATA_FORM_DOB_LABEL), SwingConstants.RIGHT);
		this.panelDOB = new DatePanel(true);
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		this.tfMedID.setHorizontalAlignment(SwingConstants.CENTER);
		this.tfMedID.setToolTipText(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.PATIENT_DATA_FORM_MED_ID_TOOLTIP_TEXT));
		this.tfName.setHorizontalAlignment(SwingConstants.CENTER);
		
		//add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_TOP_COMPONENT;
		c.fill = GridBagConstraints.BOTH;
		this.add(this.labMedID, c);					//Medical Record ID
		Gbm.nextGridColumn(c);
		this.add(this.tfMedID, c);					//Medical Record ID Text Field
		Gbm.newGridLine(c);
		c.insets = Constants.INSETS_GENERAL;
		this.add(this.labName, c);					//Name
		Gbm.nextGridColumn(c);
		this.add(this.tfName, c);					//Name Text Field
		Gbm.newGridLine(c);
		this.add(this.labDOB, c);					//DOB
		Gbm.nextGridColumn(c);
		this.add(this.panelDOB, c);					//DOB Panel
	}
	public PatientData getData()
	{
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put(PatientData.MEDICAL_RECORD_ID, Methods.getTextData(this.tfMedID));
		map.put(PatientData.NAME, Methods.getTextData(this.tfName));
		LinkedHashMap<String, String> dobMap = this.panelDOB.getData();
		map.put(PatientData.DOB_DAY, dobMap.get(PainDataIdentifier.DATE_DAY));
		map.put(PatientData.DOB_MONTH, dobMap.get(PainDataIdentifier.DATE_MONTH));
		map.put(PatientData.DOB_YEAR, dobMap.get(PainDataIdentifier.DATE_YEAR));
		
		return new PatientData(map);
	}
	public boolean isEmptyID()
	{
		if (Methods.getTextData(this.tfMedID).equals(""))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public boolean isEmptyName()
	{
		if (Methods.getTextData(this.tfName).equals(""))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}
