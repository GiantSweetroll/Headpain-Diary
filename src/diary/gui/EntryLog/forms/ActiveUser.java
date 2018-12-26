package diary.gui.EntryLog.forms;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import diary.constants.Constants;
import diary.constants.XMLIdentifier;
import diary.gui.ActivePatientPanel;
import diary.methods.Methods;
import diary.patientdata.PatientData;

public class ActiveUser extends FormElement<PatientData>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4333529064910123921L;

	private ActivePatientPanel patient;
	
	public ActiveUser(ActivePatientPanel patient)
	{
		super(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_ELEMENT_TYPE_ACTIVE_USER), false);
		
		//Initialization
		this.patient = patient;
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		this.getPanel().setLayout(new GridBagLayout());
		
		//Add to panel
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = Constants.INSETS_TOP_COMPONENT;
		this.getPanel().add(this.getFormTitleLabel(), c);
		this.getPanel().add(this.patient, c);
	}
	
	//Methods
	public ActivePatientPanel getActivePatientPanel()
	{
		return this.patient;
	}
	
	//Overridden Methods
	@Override
	public void resetDefaults() 
	{
		this.patient.refresh();
	}
	
	@Override
	public void refresh() {};

	@Override
	public PatientData getData()
	{
		return this.patient.getSelectedPatientData();
	}

	@Override
	public void setData(PatientData obj) 
	{
		if (obj instanceof PatientData)
		{
			this.patient.setSelectedPatient(obj);
		}
	}

	@Override
	public boolean allFilled()
	{
		return this.patient.isPatientSelected();
	}

	@Override
	public void revalidateLanguage()
	{
		this.setFormTitle(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_ELEMENT_TYPE_ACTIVE_USER));
		this.patient.revalidateLanguage();
	}
}
