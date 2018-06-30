package diary.gui.EntryLog;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import diary.constants.Constants;
import diary.constants.PainDataIdentifier;
import diary.constants.PanelName;
import diary.constants.XMLIdentifier;
import diary.data.PainEntryData;
import diary.gui.ActivePatientPanel;
import diary.gui.MainFrame;
import diary.gui.MainFramePanel;
import diary.gui.EntryLog.forms.ActiveUser;
import diary.gui.EntryLog.forms.Comments;
import diary.gui.EntryLog.forms.DateTimeSelect;
import diary.gui.EntryLog.forms.Duration;
import diary.gui.EntryLog.forms.Intensity;
import diary.gui.EntryLog.forms.PainKind;
import diary.gui.EntryLog.forms.PainLocation;
import diary.gui.EntryLog.forms.RecentMedication;
import diary.gui.EntryLog.forms.Trigger;
import diary.interfaces.GUIFunction;
import diary.methods.Methods;
import diary.patientdata.PatientData;

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
	private ActiveUser activeUser;
	private Comments comments;
	private DateTimeSelect dateTime;
	private Duration duration;
	private Intensity intensity;
	private PainKind painKind;
	private PainLocation painLoc;
	private RecentMedication recentMeds;
	private Trigger trigger;
	private PainEntryData oldEntry;
	private PatientData oldPatient;
	
	private boolean isNewEntry;
	private byte panelState;
	
	//Constants
	protected final String ACTIVE_PATIENT = "activepatiente", COMMENTS="commente", DATE_TIME="datetimee", DURATION="duratione", INTENSITY="intensitye",
							PAIN_KIND="poaindkine", PAIN_LOCATION="painloce", RECENT_MEDICATION="recentmede", TRIGGER="triggere";
	protected static final byte FIRST_SECTION = 0, LAST_SECTION = 8;	//To be used with EntryLogButtonControl
	
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
		this.activeUser = new ActiveUser(new ActivePatientPanel(this.getMainFrameReference()));
		this.comments = new Comments();
		this.dateTime= new DateTimeSelect();
		this.duration = new Duration();
		this.intensity = new Intensity();
		this.painKind = new PainKind();
		this.painLoc = new PainLocation();
		this.recentMeds = new RecentMedication();
		this.trigger = new Trigger();
		
		//Properties
		this.panelCenter.setLayout(new CardLayout());
		this.panelCenter.setOpaque(false);
		this.activeUser.setName(this.ACTIVE_PATIENT);
		this.comments.setName(this.COMMENTS);
		this.dateTime.setName(this.DATE_TIME);
		this.duration.setName(this.DURATION);
		this.intensity.setName(this.INTENSITY);
		this.painKind.setName(this.PAIN_KIND);
		this.painLoc.setName(this.PAIN_LOCATION);
		this.recentMeds.setName(this.RECENT_MEDICATION);
		this.trigger.setName(this.TRIGGER);
		
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
		this.labTitle = new JLabel(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_TITLE), SwingConstants.CENTER);
		this.butCancel = new JButton(Methods.getLanguageText(XMLIdentifier.CANCEL_TEXT));
		
		//Properties
		this.panelTop.setLayout(new BorderLayout());
		this.panelTop.setOpaque(false);
		this.butCancel.addActionListener(this);
		this.labTitle.setFont(Constants.FONT_SUB_TITLE);
		
		//Add to panel
		this.panelTop.add(this.labTitle, BorderLayout.CENTER);
		this.panelTop.add(this.butCancel, BorderLayout.WEST);
	}
	private void initPanelBelow()
	{
		//Initialization
		this.panelBelow = new JPanel();
		this.panelState = EntryLog.FIRST_SECTION;
		this.butBack = new EntryLogButtonControl(this.getMainFrameReference(), this, EntryLogButtonControl.BACK, "");
		this.butNext = new EntryLogButtonControl(this.getMainFrameReference(), this, EntryLogButtonControl.NEXT, "");
		
		//Properties
		this.panelBelow.setLayout(new BorderLayout());
		this.panelBelow.setOpaque(false);
		
		//Add to panel
		this.panelBelow.add(this.butBack, BorderLayout.WEST);
		this.panelBelow.add(this.butNext, BorderLayout.EAST);
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
	
	public void loadData(PatientData patient, PainEntryData entry)
	{
		this.resetDefaults();
		this.oldEntry = entry;
		this.oldPatient = patient;
		this.fillData(patient, entry);
		this.revalidate();
		this.repaint();
		this.isNewEntry = false;
	}
	private void fillData(PatientData patient, PainEntryData entry)
	{
		this.activeUser.setData(patient);
		this.comments.setData(entry.getDataMap().get(PainDataIdentifier.COMMENTS));
		this.dateTime.setData(entry);
		this.duration.setData(entry.getDataMap().get(PainDataIdentifier.DURATION));
		this.intensity.setData(entry.getDataMap().get(PainDataIdentifier.INTENSITY));
		this.painKind.setData(entry.getDataMap().get(PainDataIdentifier.PAIN_KIND));
		this.painLoc.setData(entry);
		this.recentMeds.setData(entry.getDataMap().get(PainDataIdentifier.RECENT_MEDICATION).toString(), entry.getDataMap().get(PainDataIdentifier.MEDICINE_COMPLAINT).toString());
		this.trigger.setData(entry);
	}

	public byte getPanelState()
	{
		return this.panelState;
	}
	public void setPanelState(byte state)
	{
		this.panelState = state;
	}
	
	//Interfaces
	@Override
	public void resetDefaults() 
	{
		this.panelState = EntryLog.FIRST_SECTION;
		this.activeUser.resetDefaults();
		this.comments.resetDefaults();
		this.dateTime.resetDefaults();
		this.duration.resetDefaults();
		this.intensity.resetDefaults();
		this.painKind.resetDefaults();
		this.painLoc.resetDefaults();
		this.recentMeds.resetDefaults();
		this.trigger.resetDefaults();
		this.changeActiveSection(this.ACTIVE_PATIENT);
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
