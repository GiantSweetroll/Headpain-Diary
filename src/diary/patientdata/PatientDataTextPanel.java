package diary.patientdata;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import diary.constants.XMLIdentifier;
import diary.interfaces.LanguageListener;
import diary.methods.Methods;

public class PatientDataTextPanel extends JPanel implements LanguageListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5746105114096276159L;

	private JLabel labName, labMedID, labDOB, labSex, labJob, labCity;
	private PatientData patient;
	
	public PatientDataTextPanel(PatientData patient)
	{
		//Initialization
		this.patient = patient;
		this.labName = new JLabel("  " + Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_NAME_LABEL) + ": " + this.patient.getName());
		this.labMedID = new JLabel("  " + Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_MED_ID_LABEL) + ": " + this.patient.getID());
		this.labDOB = new JLabel("  " + Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_DOB_LABEL) + ": " + this.patient.getDOBString());
		this.labSex = new JLabel("  " + Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_SEX_LABEL) + ": " + Methods.convertSexToLanguage(this.patient.getSex()));
		this.labJob = new JLabel("  " + Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_OCCUPATION_LABEL) + ": " + this.patient.getOccupation());
		this.labCity = new JLabel("  " + Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_CITY_LABEL) + ": " + this.patient.getCity());
		
		//Properties
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setOpaque(false);
	///\	this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		//Add components
		this.add(this.labName);
		this.add(this.labMedID);
		this.add(this.labDOB);
		this.add(this.labSex);
		this.add(this.labJob);
		this.add(this.labCity);
	}


	//Interface
	@Override
	public void revalidateLanguage() 
	{
		this.labName.setText("  " + Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_NAME_LABEL) + ": " + this.patient.getName());
		this.labMedID.setText("  " + Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_MED_ID_LABEL) + ": " + this.patient.getID());
		this.labDOB.setText("  " + Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_DOB_LABEL) + ": " + this.patient.getDOBString());
		this.labSex.setText("  " + Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_SEX_LABEL) + ": " + Methods.convertSexToLanguage(this.patient.getSex()));
		this.labJob.setText("  " + Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_OCCUPATION_LABEL) + ": " + this.patient.getOccupation());
		this.labCity.setText("  " + Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_CITY_LABEL) + ": " + this.patient.getCity());
	}
}
