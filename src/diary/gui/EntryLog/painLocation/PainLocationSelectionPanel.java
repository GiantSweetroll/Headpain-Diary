package diary.gui.EntryLog.painLocation;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import diary.constants.PainDataIdentifier;
import diary.constants.XMLIdentifier;
import diary.data.PainEntryData;
import diary.interfaces.GUIFunction;
import diary.interfaces.LanguageListener;
import diary.methods.Methods;

public class PainLocationSelectionPanel extends JPanel implements ItemListener, GUIFunction, LanguageListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6969945683971404114L;
	
	private JRadioButton radPreset, radCustom;
	private PainLocationPresetSelectionPanel preset;
	private PainLocationCustomSelection custom;
	private ButtonGroup group;
	
	//Constructors
	public PainLocationSelectionPanel()
	{
		//Initialization
		this.radCustom = new JRadioButton(Methods.getLanguageText(XMLIdentifier.PAIN_LOCATION_CUSTOM_LABEL));
		this.radPreset = new JRadioButton(Methods.getLanguageText(XMLIdentifier.PAIN_LOCATION_PRESETS_LABEL));
		this.preset = new PainLocationPresetSelectionPanel();
		this.custom = new PainLocationCustomSelection();
		this.group = new ButtonGroup();
		
		//Properties
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setOpaque(false);
//		this.setBorder(BorderFactory.createTitledBorder(Methods.createTextWithRequiredIdentifier(Methods.getLanguageText(XMLIdentifier.YOUR_MOST_PAINFUL_AREA_LABEL))));
	//	this.preset.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
	//	this.custom.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
		this.radPreset.setSelected(true);
		this.custom.setEnabled(false);
		this.group.add(this.radCustom);
		this.group.add(this.radPreset);
		this.radCustom.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.radPreset.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.custom.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.preset.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.radCustom.addItemListener(this);
		this.radPreset.addItemListener(this);
		this.radCustom.setOpaque(false);
		this.radPreset.setOpaque(false);
		
		//Add to panel
		this.add(this.radPreset);
		this.add(this.preset);
		this.add(this.radCustom);
		this.add(this.custom);
	}
	
	//Public Methods
	public List<String> getSelectedPositions()
	{
		if (this.radPreset.isSelected())
		{
			List<String> list = this.preset.getSelectedPosition();
			Methods.removeDuplicatesFromStringList(list);
			return list;
		}
		else
		{
			List<String> list = new ArrayList<String>();
			for (String loc : this.custom.getLocations())
			{
				list.add(loc);
			}
			Methods.removeDuplicatesFromStringList(list);
			return list;
		}
	}
	public boolean presetLocationSelected()
	{
		return this.radPreset.isSelected();
	}
	public boolean customLocationSelected()
	{
		return this.radCustom.isSelected();
	}
	
	public void setSelectedPosition(PainEntryData entry)
	{
		List<String> presetLocations = (List<String>)entry.getDataMap().get(PainDataIdentifier.PAIN_LOCATION_PRESET);
		List<String> customLocations = (List<String>)entry.getDataMap().get(PainDataIdentifier.PAIN_LOCATION_CUSTOM);
	
		if (presetLocations.size() == 0)
		{
			this.radCustom.setSelected(true);
			List<String> list = new ArrayList<String>();
			for (String location : customLocations)
			{
				try
				{
					list.add(location);
				}
				catch(Exception ex) {}
			}
			this.custom.setLocations(list);
		}
		else
		{
			this.radPreset.setSelected(true);
			for (String location : presetLocations)
			{
				this.preset.setSelected(location);
			}
		}
	}
	public boolean isPainLocationSelected()
	{
		return this.getSelectedPositions().size() > 0;
	}
	
	//Private Methods
	public void changeSelection()
	{
		if (this.radPreset.isSelected())
		{
			this.custom.setEnabled(false);
			this.custom.resetDefaults();
			this.preset.setEnabled(true);
		}
		else
		{
			this.custom.setEnabled(true);
			this.preset.setEnabled(false);
		}
	}
	
	//Interfaces
	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		this.changeSelection();
	}
	
	@Override
	public void resetDefaults()
	{
		this.preset.resetDefaults();
		this.custom.resetDefaults();
		this.radPreset.setSelected(true);
		this.preset.unmarkAllButtons();
	}
	@Override
	public void refresh() {};
	
	@Override
	public void revalidateLanguage()
	{
		this.radPreset.setText(Methods.getLanguageText(XMLIdentifier.PRESETS_TEXT));
		this.radCustom.setText(Methods.getLanguageText(XMLIdentifier.CUSTOM_TEXT));
		this.custom.revalidateLanguage();
		this.preset.revalidateLanguage();
	}
}
