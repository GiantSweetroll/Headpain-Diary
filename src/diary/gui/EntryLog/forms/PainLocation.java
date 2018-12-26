package diary.gui.EntryLog.forms;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.SwingConstants;

import diary.constants.XMLIdentifier;
import diary.data.PainEntryData;
import diary.gui.EntryLog.painLocation.PainLocationSelectionPanel;
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
		super(Methods.getLanguageText(XMLIdentifier.YOUR_MOST_PAINFUL_AREA_LABEL), true);
		
		//Initialization
		this.painLocation = new PainLocationSelectionPanel();
//		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
//		this.getPanel().setLayout(new GridBagLayout());
		this.getPanel().setLayout(new BorderLayout());
		this.getFormTitleLabel().setHorizontalAlignment(SwingConstants.CENTER);
		
		//Add to panel
	//	c.insets = Constants.INSETS_TOP_COMPONENT;
	//	c.gridwidth = GridBagConstraints.REMAINDER;
		this.getPanel().add(this.getFormTitleLabel(), BorderLayout.NORTH);
		this.getPanel().add(this.painLocation, BorderLayout.CENTER);
	}
	
	//Public Methods
	public boolean isPresetLocationSelected()
	{
		return this.painLocation.presetLocationSelected();
	}
	public boolean isCustomLocationSelected()
	{
		return this.painLocation.customLocationSelected();
	}
	
	//Overridden Methods
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
	public void setData(Object entry) 
	{
		if (entry instanceof PainEntryData)
		{
			this.painLocation.setSelectedPosition((PainEntryData)entry);
		}
	}

	@Override
	public boolean allFilled() 
	{
		return this.painLocation.isPainLocationSelected();
	}

	@Override
	public void revalidateLanguage() 
	{
		this.setFormTitle(Methods.getLanguageText(XMLIdentifier.SELECT_LOCATION_LABEL));
		this.painLocation.revalidateLanguage();
	}
}