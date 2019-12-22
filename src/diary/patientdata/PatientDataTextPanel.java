package diary.patientdata;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import diary.constants.Constants;
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
		this.labName = new JLabel("<html>" + "  " + "<b>" + Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_NAME_LABEL) + ": " + "</b>" + this.patient.getName() + "</html>");
		this.labMedID = new JLabel("<html>" + "  " + "<b>" + Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_MED_ID_LABEL) + ": " + "</b>" + this.patient.getID() + "</html>");
		this.labDOB = new JLabel("<html>" + "  " + "<b>" + Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_DOB_LABEL) + ": " + "</b>" + this.patient.getDOBString() + "</html>");
		this.labSex = new JLabel("<html>" + "  " + "<b>" + Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_SEX_LABEL) + ": " + "</b>" + Methods.convertSexToLanguage(this.patient.getSex()) + "</html>");
		this.labJob = new JLabel("<html>" + "  " + "<b>" + Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_OCCUPATION_LABEL) + ": " + "</b>" + this.patient.getOccupation() + "</html>");
		this.labCity = new JLabel("<html>" + "  " + "<b>" + Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_CITY_LABEL) + ": " + "</b>" + this.patient.getCity() + "</html>");
		
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
		this.labName.setText("<html>" + "  " + "<b>" + Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_NAME_LABEL) + ": " + "</b>" + this.patient.getName() + "</html>");
		this.labMedID.setText("<html>" + "  " + "<b>" + Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_MED_ID_LABEL) + ": " + "</b>" + this.patient.getID() + "</html>");
		this.labDOB.setText("<html>" + "  " + "<b>" + Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_DOB_LABEL) + ": " + "</b>" + this.patient.getDOBString() + "</html>");
		this.labSex.setText("<html>" + "  " + "<b>" + Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_SEX_LABEL) + ": " + "</b>" + Methods.convertSexToLanguage(this.patient.getSex()) + "</html>");
		this.labJob.setText("<html>" + "  " + "<b>" + Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_OCCUPATION_LABEL) + ": " + "</b>" + this.patient.getOccupation() + "</html>");
		this.labCity.setText("<html>" + "  " + "<b>" + Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_CITY_LABEL) + ": " + "</b>" + this.patient.getCity() + "</html>");
	}
}
