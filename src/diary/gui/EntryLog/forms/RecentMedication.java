package diary.gui.EntryLog.forms;

import diary.constants.Globals;
import diary.constants.XMLIdentifier;
import diary.history.HistoryPanel;
import diary.methods.Methods;

public class RecentMedication extends FormElement
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2910335569921535235L;

	private HistoryPanel recentMedication, medicineComplaint;
	
	public RecentMedication()
	{
		super(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_ELEMENT_TYPE_RECENT_MEDICATION));
		
		this.recentMedication = new HistoryPanel(Globals.HISTORY_RECENT_MEDICATION);
		this.medicineComplaint = new HistoryPanel(Globals.HISTORY_MEDICINE_COMPLAINT);
		
		this.addComponent(this.recentMedication);
		this.addComponent(this.medicineComplaint);
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
	
	//Overridden Methods
	@Override
	public void resetDefaults() {
		this.recentMedication.resetDefaults();
		this.medicineComplaint.resetDefaults();
	}
	
	@Override
	public void refresh() {};

	@Deprecated
	@Override
	public Object getData() {return null;}

	@Deprecated
	@Override
	public void setData(Object obj) {}

}
