package diary.gui.EntryLog;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import diary.constants.PanelName;
import diary.constants.XMLIdentifier;
import diary.gui.ActivePatientPanel;
import diary.gui.MainFrame;
import diary.gui.MainFramePanel;
import diary.gui.EntryLog.forms.ActiveUser;
import diary.gui.EntryLog.forms.Comments;
import diary.gui.EntryLog.forms.DateTimeSelect;
import diary.gui.EntryLog.forms.Duration;
import diary.gui.EntryLog.forms.FormElement;
import diary.gui.EntryLog.forms.Intensity;
import diary.gui.EntryLog.forms.PainKind;
import diary.gui.EntryLog.forms.PainLocation;
import diary.gui.EntryLog.forms.RecentMedication;
import diary.interfaces.GUIFunction;
import diary.methods.Methods;

public class EntryLog extends MainFramePanel implements GUIFunction, ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2559541533340658831L;
	
	private JPanel panelTop, panelCenter, panelBelow;
	private JLabel labTitle;
	private JButton butCancel;
	private EntryLogButtonControl butBack, butNext;
	private FormElement activeUser, comments, dateTime, duration, intensity, painKind, painLoc, recentMeds, trigger;

	//Constants
	private final String ACTIVE_PATIENT = "q", COMMENTS="w", DATE_TIME="e", DURATION="r", INTENSITY="t",
							PAIN_KIND="y", PAIN_LOCATION="u", RECENT_MEDICATION="i", TRIGGER="o";
	
	//Constructor
	public EntryLog(MainFrame mainFrame)
	{
		super(mainFrame);
		this.init();
	}
	
	//Create GUI Methods
	private void init()
	{
		//Initialization
		this.initPanelBelow();
		this.initPanelCenter();
		this.initPanelTop();
		
		//Properties
		this.setLayout(new BorderLayout());
		this.setOpaque(false);
		
		//Add to panel
		this.add(this.panelTop, BorderLayout.NORTH);
		this.add(this.panelBelow, BorderLayout.SOUTH);
		this.add(this.panelCenter, BorderLayout.CENTER);
	}
	private void initPanelCenter()
	{
		//Initialization
		this.panelCenter = new JPanel();
		this.activeUser = new ActiveUser();
		this.comments = new Comments();
		this.dateTime= new DateTimeSelect();
		this.duration = new Duration();
		this.intensity = new Intensity();
		this.painKind = new PainKind();
		this.painLoc = new PainLocation();
		this.recentMeds = new RecentMedication();
		this.trigger= new ActiveUser();
		
		//Properties
		this.panelCenter.setLayout(new CardLayout());
		this.panelCenter.setOpaque(false);
		
		//Add to panel
		this.panelCenter.add(this.activeUser, this.ACTIVE_PATIENT);
		this.panelCenter.add(this.dateTime, this.DATE_TIME);
		this.panelCenter.add(this.duration, this.DURATION);
		this.panelCenter.add(this.painLoc, this.PAIN_LOCATION);
		this.panelCenter.add(this.painKind, this.PAIN_KIND);
		this.panelCenter.add(this.intensity, this.INTENSITY);
		this.panelCenter.add(this.trigger, this.TRIGGER);
		this.panelCenter.add(this.recentMeds, this.RECENT_MEDICATION);
		this.panelCenter.add(this.comments, this.COMMENTS);
	}
	private void initPanelTop()
	{
		//Initialization
		this.panelTop = new JPanel();
		this.labTitle = new JLabel(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_TITLE));
		this.butCancel = new JButton(Methods.getLanguageText(XMLIdentifier.CANCEL_TEXT));
		
		//Properties
		this.panelTop.setLayout(new BorderLayout());
		this.panelTop.setOpaque(false);
		this.butCancel.addActionListener(this);
		
		//Add to panel
		this.panelTop.add(this.labTitle, BorderLayout.CENTER);
		this.panelTop.add(this.butCancel, BorderLayout.WEST);
	}
	private void initPanelBelow()
	{
		//Initialization
		this.panelBelow = new JPanel();
		this.butBack = new EntryLogButtonControl(this, EntryLogButtonControl.BACK, PanelName.MAIN_MENU, "");
		this.butNext = new EntryLogButtonControl(this, EntryLogButtonControl.NEXT, this.ACTIVE_PATIENT, "");
		
		//Properties
		this.panelBelow.setLayout(new BorderLayout());
		this.panelBelow.setOpaque(false);
		
		//Add to panel
		this.panelBelow.add(this.butBack, BorderLayout.WEST);
	}
	//Methods
	public void changeActiveSection(String section)
	{
		Methods.changePanel(this.panelCenter, section);
	}
	public EntryLogButtonControl getNextButton()
	{
		return this.butNext;
	}
	public EntryLogButtonControl getBackButton()
	{
		return this.butBack;
	}
	public JPanel getPanelCenter()
	{
		return this.panelCenter;
	}
	public ActivePatientPanel getActivePatientPanel()
	{
		return ((ActiveUser)this.activeUser).getActivePatientPanel();
	}
	public void refreshHistories()
	{
		((RecentMedication)this.recentMeds).refresh();
	}

	//Interfaces
	@Override
	public void resetDefaults() 
	{
		this.activeUser.resetDefaults();
		this.comments.resetDefaults();
		this.dateTime.resetDefaults();
		this.duration.resetDefaults();
		this.intensity.resetDefaults();
		this.painKind.resetDefaults();
		this.painLoc.resetDefaults();
		this.recentMeds.resetDefaults();
		this.trigger.resetDefaults();
		
	}
	@Override
	public void refresh() 
	{
		this.activeUser.refresh();
		this.comments.refresh();
		this.dateTime.refresh();
		this.duration.refresh();
		this.intensity.refresh();
		this.painKind.refresh();
		this.painLoc.refresh();
		this.recentMeds.refresh();
		this.trigger.refresh();
	};
	@Override
	public void actionPerformed(ActionEvent e)
	{
		this.getMainFrameReference().changePanel(PanelName.MAIN_MENU);
	}
}
