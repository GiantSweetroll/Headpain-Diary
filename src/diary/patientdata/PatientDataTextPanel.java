package diary.patientdata;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import diary.constants.XMLIdentifier;
import diary.methods.Methods;

public class PatientDataTextPanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5746105114096276159L;

	
	public PatientDataTextPanel(PatientData patient)
	{
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setOpaque(false);
	///\	this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		//Add components
		this.add(new JLabel("  " + Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_NAME_LABEL) + ": " + patient.getName()));
		this.add(new JLabel("  " + Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_MED_ID_LABEL) + ": " + patient.getID()));
		this.add(new JLabel("  " + Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_DOB_LABEL) + ": " + patient.getDOB()));
	}
}
