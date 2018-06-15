package diary.gui.EntryLogNew.forms;

import java.util.List;

import diary.constants.XMLIdentifier;
import diary.data.PainEntryData;
import diary.gui.EntryLog.PainLocationSelectionPanel;
import diary.methods.Methods;

public class PainLocation extends FormElement
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3006547001840623989L;

	private PainLocationSelectionPanel painLocation;
	
	public PainLocation()
	{
		super(Methods.getLanguageText(XMLIdentifier.YOUR_MOST_PAINFUL_AREA_LABEL));
		
		this.painLocation = new PainLocationSelectionPanel();
		
		this.addComponent(this.painLocation);
	}
	
	//Overriden Methods
	@Override
	public void resetDefaults() {
		this.painLocation.resetDefaults();
	}
	
	@Override
	public void refresh() {};

	@Override
	public List<String> getData() {
		return this.painLocation.getSelectedPositions();
	}

	@Override
	public void setData(Object obj) 
	{
		if (obj instanceof PainEntryData)
		{
			this.painLocation.setSelectedPosition((PainEntryData)obj);
		}
	}

}
