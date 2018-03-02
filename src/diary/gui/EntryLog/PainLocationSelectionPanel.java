package diary.gui.EntryLog;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import diary.constants.XMLIdentifier;
import diary.methods.Methods;

public class PainLocationSelectionPanel extends JPanel implements ItemListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6969945683971404114L;
	
	private JRadioButton radPreset, radCustom;
	private PainLocationPresetSelectionPanel preset;
	private PainLocationCustomSelectionPanel custom;
	private ButtonGroup group;
	
	//Constructors
	public PainLocationSelectionPanel()
	{
		//Initialization
		this.radCustom = new JRadioButton(Methods.getLanguageText(XMLIdentifier.CUSTOM_TEXT));
		this.radPreset = new JRadioButton(Methods.getLanguageText(XMLIdentifier.PRESETS_TEXT));
		this.preset = new PainLocationPresetSelectionPanel();
		this.custom = new PainLocationCustomSelectionPanel(new PainLocationCustomSelection());
		this.group = new ButtonGroup();
		
		//Properties
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setOpaque(false);
		this.setBorder(BorderFactory.createTitledBorder(Methods.getLanguageText(XMLIdentifier.YOUR_MOST_PAINFUL_AREA_LABEL)));
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
		
		//Add to panel
		this.add(this.radPreset);
		this.add(this.preset);
		this.add(this.radCustom);
		this.add(this.custom);
	}
	
	//Methods
	
	//Interfaces
	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		if (this.radPreset.isSelected())
		{
			this.custom.setEnabled(false);
			this.preset.setEnabled(true);
		}
		else
		{
			this.custom.setEnabled(true);
			this.preset.setEnabled(false);
		}
	}
}
