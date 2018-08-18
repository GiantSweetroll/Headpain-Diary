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

	private JLabel labName, labMedID, labDOB;
	private PatientData patient;
	
	public PatientDataTextPanel(PatientData patient)
	{
		//Initialization
		this.patient = patient;
		this.labName = new JLabel("  " + Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_NAME_LABEL) + ": " + this.patient.getName());
		this.labMedID = new JLabel("  " + Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_MED_ID_LABEL) + ": " + this.patient.getID());
		this.labDOB = new JLabel("  " + Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_DOB_LABEL) + ": " + this.patient.getDOBString());
		
		//Properties
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setOpaque(false);
	///\	this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		//Add components
		this.add(this.labName);
		this.add(this.labMedID);
		this.add(this.labDOB);
	}


	//Interface
	@Override
	public void revalidateLanguage() 
	{
		this.labName.setText("  " + Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_NAME_LABEL) + ": " + this.patient.getName());
		this.labMedID.setText("  " + Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_MED_ID_LABEL) + ": " + this.patient.getID());
		this.labDOB.setText("  " + Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_DOB_LABEL) + ": " + this.patient.getDOBString());
	}
}
