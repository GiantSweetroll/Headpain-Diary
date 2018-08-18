package diary.gui.EntryLog.forms;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;

import diary.constants.Constants;
import diary.constants.Globals;
import diary.constants.XMLIdentifier;
import diary.history.HistoryPanel;
import diary.methods.Methods;
import diary.patientdata.PatientData;
import giantsweetroll.gui.swing.Gbm;

public class RecentMedication extends FormElement
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2910335569921535235L;

	private HistoryPanel recentMedication, medicineComplaint;
	private JLabel labRecentMeds, labMedecineComplaint;
	
	public RecentMedication()
	{
		super(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_ELEMENT_TYPE_RECENT_MEDICATION), false);
		
		//Initialization
		this.recentMedication = new HistoryPanel(Globals.HISTORY_RECENT_MEDICATION, PatientData.LAST_RECENT_MEDS, Constants.DEFAULT_RECENT_MEDICATION);
		this.medicineComplaint = new HistoryPanel(Globals.HISTORY_MEDICINE_COMPLAINT, PatientData.LAST_MEDICINE_COMPLAINT, Constants.EMPTY_STRING_ARRAY);
		this.labRecentMeds = new JLabel(Methods.getLanguageText(XMLIdentifier.RECENT_MEDICATION_LABEL));
		this.labMedecineComplaint = new JLabel(Methods.getLanguageText(XMLIdentifier.MEDICINE_COMPLAINT_LABEL));
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		this.getPanel().setLayout(new GridBagLayout());
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_TOP_COMPONENT;
		c.fill = GridBagConstraints.HORIZONTAL;
		this.getPanel().add(this.labRecentMeds, c);
		Gbm.nextGridColumn(c);
		this.getPanel().add(this.recentMedication, c);
		Gbm.newGridLine(c);
		c.insets = Constants.INSETS_GENERAL;
		this.getPanel().add(this.labMedecineComplaint, c);
		Gbm.nextGridColumn(c);
		this.getPanel().add(this.medicineComplaint, c);
	}
	
	//Methods
	public String getRecentMedication()
	{
		return this.recentMedication.getItem();
	}
	public String getMedicineComplaint()
	{
		return this.medicineComplaint.getItem();
	}
	public void setRecentMedication(String item)
	{
		this.recentMedication.setActiveItem(item);
	}
	public void setMedicineComplaint(String item)
	{
		this.medicineComplaint.setActiveItem(item);
	}
	public void setData(String recentMeds, String medComplaint)
	{
		this.setRecentMedication(recentMeds);
		this.setMedicineComplaint(medComplaint);
	}
	public void refreshHistories(PatientData patient)
	{
		this.recentMedication.refresh(Globals.HISTORY_RECENT_MEDICATION, patient);
		this.medicineComplaint.refresh(Globals.HISTORY_MEDICINE_COMPLAINT, patient);
	}
	
	//Overridden Methods
	@Override
	public void resetDefaults() {
		this.recentMedication.resetDefaults();
		this.medicineComplaint.resetDefaults();
	}
	
	@Override
	public void refresh() {}
	
	@Deprecated
	@Override
	public Object getData() {return null;}

	@Deprecated
	@Override
	public void setData(Object obj) {}

	@Override
	public boolean allFilled()
	{
		return true;
	}

	@Override
	public void revalidateLanguage() 
	{
		this.labRecentMeds.setText(Methods.getLanguageText(XMLIdentifier.RECENT_MEDICATION_LABEL));
		this.labMedecineComplaint.setText(Methods.getLanguageText(XMLIdentifier.MEDICINE_COMPLAINT_LABEL));
	}
}
