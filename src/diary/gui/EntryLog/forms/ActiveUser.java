package diary.gui.EntryLog.forms;

import diary.constants.XMLIdentifier;
import diary.gui.ActivePatientPanel;
import diary.methods.Methods;
import diary.patientdata.PatientData;

public class ActiveUser extends FormElement
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4333529064910123921L;

	private ActivePatientPanel patient;
	
	public ActiveUser(ActivePatientPanel patient)
	{
		super(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_ELEMENT_TYPE_ACTIVE_USER));
		
		this.patient = patient;
		
		this.addComponent(this.patient);
	}
	
	//Methods
	public ActivePatientPanel getActivePatientPanel()
	{
		return this.patient;
	}
	
	//Overriden Methods
	@Override
	public void resetDefaults() 
	{
		this.patient.refresh();
	}
	
	@Override
	public void refresh() {};

	@Override
	public Object getData()
	{
		return this.patient.getSelectedPatientData();
	}

	@Override
	public void setData(Object obj) 
	{
		if (obj instanceof PatientData)
		{
			this.patient.setSelectedPatient((PatientData)obj);
		}
	}

}
