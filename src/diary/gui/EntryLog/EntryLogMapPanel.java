package diary.gui.EntryLog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import diary.constants.Constants;
import diary.constants.Globals;
import diary.constants.XMLIdentifier;
import diary.interfaces.GUIFunction;
import diary.interfaces.LanguageListener;
import diary.methods.Methods;

public class EntryLogMapPanel extends JPanel implements ActionListener, LanguageListener, GUIFunction
{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6262873772156057769L;

	private EntryLogMapButton bProfile, 
								bDateTime,
								bDuration, 
								bPainLoc, 
								bPainKind,
								bIntensity, 
								bTrigger,
								bRecMeds,
								bComments;
	
	private List<EntryLogMapButton> buttons;
	
	private byte lastButtonPress;
	
	//Constants
	private final String PROFILE = "prof",
							DATE_TIME = "dt",
							DURATION = "dur",
							PAIN_LOCATION = "ploc",
							PAIN_KIND = "pk",
							INTENSITY = "int",
							TRIGGER = "trig",
							RECENT_MEDICATION = "rm",
							COMMENTS = "com";
	
	//Constructor
	public EntryLogMapPanel()
	{
		//Initialization
		this.bProfile = new EntryLogMapButton(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_MAP_BUTTON_PROFILE_TEXT));
		this.bDateTime = new EntryLogMapButton(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_MAP_BUTTON_DATE_TIME_TEXT));
		this.bDuration = new EntryLogMapButton(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_MAP_BUTTON_DURATION_TEXT));
		this.bPainLoc = new EntryLogMapButton(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_MAP_BUTTON_PAIN_LOCATION_TEXT));
		this.bPainKind = new EntryLogMapButton(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_MAP_BUTTON_PAIN_KIND_TEXT));
		this.bIntensity = new EntryLogMapButton(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_MAP_BUTTON_INTENSITY_TEXT));
		this.bTrigger = new EntryLogMapButton(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_MAP_BUTTON_TRIGGER_TEXT));
		this.bRecMeds = new EntryLogMapButton(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_MAP_BUTTON_RECENT_MEDICATION_TEXT));
		this.bComments = new EntryLogMapButton(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_MAP_BUTTON_COMMENTS_TEXT));
		this.buttons = new ArrayList<EntryLogMapButton>();
		this.lastButtonPress = 0;
		
		//Properties
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		this.setBackground(Constants.COLOR_MAIN_MENU_BACKGROUND);
		this.bProfile.setActionCommand(this.PROFILE);
	//	this.bProfile.addActionListener(this);
		this.bDateTime.setActionCommand(this.DATE_TIME);
	//	this.bDateTime.addActionListener(this);
		this.bDuration.setActionCommand(this.DURATION);
	//	this.bDuration.addActionListener(this);
		this.bPainLoc.setActionCommand(this.PAIN_LOCATION);
	//	this.bPainLoc.addActionListener(this);
		this.bPainKind.setActionCommand(this.PAIN_KIND);
	//	this.bPainKind.addActionListener(this);
		this.bIntensity.setActionCommand(this.INTENSITY);
	//	this.bIntensity.addActionListener(this);
		this.bTrigger.setActionCommand(this.TRIGGER);
	//	this.bTrigger.addActionListener(this);
		this.bRecMeds.setActionCommand(this.RECENT_MEDICATION);
	//	this.bRecMeds.addActionListener(this);
		this.bComments.setActionCommand(this.COMMENTS);
	//	this.bComments.addActionListener(this);
		
		//Add to array
		this.buttons.add(this.bProfile);
		this.buttons.add(this.bDateTime);
		this.buttons.add(this.bDuration);
		this.buttons.add(this.bPainLoc);
		this.buttons.add(this.bPainKind);
		this.buttons.add(this.bIntensity);
		this.buttons.add(this.bTrigger);
		this.buttons.add(this.bRecMeds);
		this.buttons.add(this.bComments);
		
		//Button properties
		for (EntryLogMapButton but : buttons)
		{
			but.addActionListener(this);
			but.setMaximumSize(new Dimension(Integer.MAX_VALUE, but.getMinimumSize().height));
			but.setHorizontalAlignment(SwingConstants.LEFT);
		}
		
		//Add to panel
//		this.add(Box.createRigidArea(new Dimension(0, Constants.INSETS_BASE)));
		this.add(this.bProfile);
		this.add(this.bDateTime);
		this.add(this.bDuration);
		this.add(this.bPainLoc);
		this.add(this.bPainKind);
		this.add(this.bIntensity);
		this.add(this.bTrigger);
		this.add(this.bRecMeds);
		this.add(this.bComments);
	}
	
	//Public Methods
	public void updateSelection()
	{
		this.buttons.get(this.lastButtonPress).setToHighlightedColor(false);
		this.lastButtonPress = Globals.ENTRY_LOG.getPanelState();
		this.buttons.get(this.lastButtonPress).setToHighlightedColor(true);
	}
	
	//Interfaces
	@Override
	public void revalidateLanguage() 
	{
		this.bProfile.setText(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_MAP_BUTTON_PROFILE_TEXT));
		this.bDateTime.setText(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_MAP_BUTTON_DATE_TIME_TEXT));
		this.bDuration.setText(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_MAP_BUTTON_DURATION_TEXT));
		this.bPainLoc.setText(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_MAP_BUTTON_PAIN_LOCATION_TEXT));
		this.bPainKind.setText(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_MAP_BUTTON_PAIN_KIND_TEXT));
		this.bIntensity.setText(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_MAP_BUTTON_INTENSITY_TEXT));
		this.bTrigger.setText(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_MAP_BUTTON_TRIGGER_TEXT));
		this.bRecMeds.setText(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_MAP_BUTTON_RECENT_MEDICATION_TEXT));
		this.bComments.setText(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_MAP_BUTTON_COMMENTS_TEXT));
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		switch(e.getActionCommand())
		{
			case PROFILE:
				Globals.ENTRY_LOG.changeActiveSection(EntryLog.ACTIVE_PATIENT);
				Globals.ENTRY_LOG.setPanelState(EntryLog.PROFILE_SELECTION);
				break;
				
			case DATE_TIME:
				Globals.ENTRY_LOG.changeActiveSection(EntryLog.DATE_TIME);
				Globals.ENTRY_LOG.setPanelState(EntryLog.DATE_TIME_SELECTION);
				break;
				
			case DURATION:
				Globals.ENTRY_LOG.changeActiveSection(EntryLog.DURATION);
				Globals.ENTRY_LOG.setPanelState(EntryLog.DURATION_SELECTION);
				break;
				
			case PAIN_LOCATION:
				Globals.ENTRY_LOG.changeActiveSection(EntryLog.PAIN_LOCATION);
				Globals.ENTRY_LOG.setPanelState(EntryLog.PAIN_LOCATION_SELECITON);
				break;
				
			case PAIN_KIND:
				Globals.ENTRY_LOG.changeActiveSection(EntryLog.PAIN_KIND);
				Globals.ENTRY_LOG.setPanelState(EntryLog.PAIN_KIND_SELECTION);
				break;
				
			case INTENSITY:
				Globals.ENTRY_LOG.changeActiveSection(EntryLog.INTENSITY);
				Globals.ENTRY_LOG.setPanelState(EntryLog.INTENSITY_SELECTION);
				break;
				
			case TRIGGER:
				Globals.ENTRY_LOG.changeActiveSection(EntryLog.TRIGGER);
				Globals.ENTRY_LOG.setPanelState(EntryLog.TRIGGER_SELECTION);
				break;
				
			case RECENT_MEDICATION:
				Globals.ENTRY_LOG.changeActiveSection(EntryLog.RECENT_MEDICATION);
				Globals.ENTRY_LOG.setPanelState(EntryLog.RECENT_MEDICAITON_SELECTION);
				break;
				
			case COMMENTS:
				Globals.ENTRY_LOG.changeActiveSection(EntryLog.COMMENTS);
				Globals.ENTRY_LOG.setPanelState(EntryLog.COMMENTS_SELECTION);
				break;
		}
		
		this.updateSelection();
	}

	@Override
	public void resetDefaults() 
	{
		this.buttons.get(this.lastButtonPress).setToHighlightedColor(false);
		this.lastButtonPress = 0;
		this.buttons.get(this.lastButtonPress).setToHighlightedColor(true);
	}

	@Override
	public void refresh(){}
}
