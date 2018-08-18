package diary.patientdata;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.LinkedHashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import diary.constants.Constants;
import diary.constants.XMLIdentifier;
import diary.gui.DatePanel;
import diary.interfaces.LanguageListener;
import diary.methods.Methods;
import giantsweetroll.date.Date;
import giantsweetroll.gui.swing.Gbm;
import giantsweetroll.gui.swing.TextAreaManager;

public class PatientDataForm extends JPanel implements LanguageListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2197578997400146750L;
	
	private JLabel labMedID, labName, labDOB, labPrevHeadpain;
	private JTextField tfMedID, tfName;
	private DatePanel panelDOB;
	private JTextArea taPrevHeadpain;
	
	public PatientDataForm(boolean enable)
	{
		this.init();
		this.enableComponents(enable);
	}
	public PatientDataForm(PatientData patientData, boolean enable)
	{
		this.init();
		
		this.setData(patientData);
		this.enableComponents(enable);
	}
	
	//Methods
	private void enableComponents(boolean enable)
	{
		this.tfMedID.setEditable(enable);
		this.tfName.setEditable(enable);
		this.panelDOB.enableComponents(enable);
		this.taPrevHeadpain.setEditable(enable);
	}
	private void init()
	{
		//Initialization
		this.labMedID = new JLabel(Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_MED_ID_LABEL), SwingConstants.RIGHT);
		this.tfMedID = new JTextField(10);
		this.labName = new JLabel (Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_NAME_LABEL), SwingConstants.RIGHT);
		this.tfName = new JTextField (10);
		this.labDOB = new JLabel (Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_DOB_LABEL), SwingConstants.RIGHT);
		this.panelDOB = new DatePanel(true);
		this.labPrevHeadpain = new JLabel (Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_PREVIOUS_HEADPAIN_LABEL), SwingConstants.RIGHT);
		this.taPrevHeadpain = new JTextArea(15, 30);
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		this.tfMedID.setHorizontalAlignment(SwingConstants.CENTER);
		this.tfMedID.setToolTipText(Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_MED_ID_TOOLTIP_TEXT));
		this.tfMedID.setBackground(Color.WHITE);
		this.tfName.setHorizontalAlignment(SwingConstants.CENTER);
		this.tfName.setBackground(Color.WHITE);
		TextAreaManager.autoConfigureTextArea(this.taPrevHeadpain, true);
		
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
		Gbm.newGridLine(c);
		this.add(this.labPrevHeadpain, c);			//Previous Headpain Label
		Gbm.nextGridColumn(c);
		this.add(this.taPrevHeadpain, c);			//Previous Headpains
	}
	public PatientData getData()
	{
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put(PatientData.MEDICAL_RECORD_ID, Methods.getTextData(this.tfMedID));
		map.put(PatientData.NAME, Methods.getTextData(this.tfName));
		Date dob = this.panelDOB.getDate();
		map.put(PatientData.DOB_DAY, Integer.toString(dob.getDay()));
		map.put(PatientData.DOB_MONTH, Integer.toString(dob.getMonth()));
		map.put(PatientData.DOB_YEAR, Integer.toString(dob.getYear()));
		map.put(PatientData.PREVIOUS_HEADPAINS, this.taPrevHeadpain.getText());
		
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
	public void setData(PatientData patient)
	{
		this.tfMedID.setText(patient.getID());
		this.tfName.setText(patient.getName());
		this.panelDOB.setDate(patient.getDOB());
		this.panelDOB.setAsDefaultDataThis();
		this.taPrevHeadpain.setText(patient.getPreviousHeadpains());
		this.revalidate();
		this.repaint();
	}
	
	@Override
	public void revalidateLanguage() 
	{
		this.labMedID.setText(Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_MED_ID_LABEL));
		this.labName.setText(Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_NAME_LABEL));
		this.labDOB.setText(Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_DOB_LABEL));
		this.labPrevHeadpain.setText(Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_PREVIOUS_HEADPAIN_LABEL));
	}
}
