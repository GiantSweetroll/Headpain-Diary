package diary.gui.EntryLog;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import diary.constants.Constants;
import diary.constants.Globals;
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
import diary.methods.FileOperation;
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
		this.setAsNewEntry(true);
		
		//Properties
		this.setLayout(new BorderLayout());
		this.setBackground(Constants.COLOR_MAIN_MENU_BACKGROUND);
		
		//Add to panel
		this.add(this.panelTop, BorderLayout.NORTH);
		this.add(this.panelBelow, BorderLayout.SOUTH);
		this.add(this.panelCenter, BorderLayout.CENTER);
		
		this.refreshHistories();
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
		this.panelCenter.setBackground(Color.WHITE);
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
		this.panelTop.setLayout(new BorderLayout(10, 20));
		this.panelTop.setOpaque(false);
		this.panelTop.setBorder(Methods.createTransparentBorder(5));
		this.butCancel.addActionListener(this);
		this.butCancel.setBackground(Constants.COLOR_MAIN_MENU_BUTTONS);
		this.butCancel.setForeground(Color.WHITE);
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
		this.recentMeds.refreshHistories(this.getSelectedPatient());
	}
	
	public void loadData(PatientData patient, PainEntryData entry)
	{
		this.resetDefaults();
		this.activeUser.setData(patient);
		this.setOldEntry(entry);
		this.setOldPatientData(patient);
		this.fillData(patient, entry);
		this.setAsNewEntry(false);
		this.revalidate();
		this.repaint();
	}
	private void fillData(PatientData patient, PainEntryData entry)
	{		
		this.activeUser.setData(patient);
		this.comments.setData(entry.getComments());
		this.dateTime.setDate(entry.getDate());
		this.dateTime.setTime(entry);
		this.dateTime.setAsDefaultThis();
		this.duration.setData(entry.getDuration());
		this.intensity.setData(entry.getIntensity());
		this.painKind.setData(entry);
		this.painLoc.setData(entry);
		this.recentMeds.setData(entry.getRecentMedication(), entry.getMedicineComplaint());
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
	
	public PainEntryData getData()
	{
		PainEntryData entry = new PainEntryData();
		
		entry.setComments(this.comments.getData());
		entry.setDate(this.dateTime.getDate());
		entry.setTime(this.dateTime.getTimeHour(), this.dateTime.getTimeMinutes());
		entry.setDuration(this.duration.getData());
		entry.setIntensity(this.intensity.getData());
		entry.setPainKind(this.painKind.getData());
		if (this.painLoc.isPresetLocationSelected())
		{
			entry.setPresetPainLocations(this.painLoc.getData());
		}
		else
		{
			entry.setCustomPainLocations(this.painLoc.getData());
		}
		entry.setRecentMedicaiton(this.recentMeds.getRecentMedication());
		entry.setMedicineComplaint(this.recentMeds.getMedicineComplaint());
		entry.setTrigger(this.trigger.getData());
		
		return entry;
	}
	
	public PatientData getSelectedPatient()
	{
		return this.activeUser.getActivePatientPanel().getSelectedPatientData();
	}
	
	public boolean allRequiredFieldsFilled()
	{
		return this.activeUser.allFilled() &&
				this.comments.allFilled() &&
				this.dateTime.allFilled() &&
				this.duration.allFilled() &&
				this.intensity.allFilled() &&
				this.painKind.allFilled() &&
				this.painLoc.allFilled() &&
				this.recentMeds.allFilled() &&
				this.trigger.allFilled();
	}
	
	public void setAsNewEntry(boolean bool)
	{
		this.isNewEntry = bool;
	}
	public boolean isNewEntry()
	{
		return this.isNewEntry;
	}
	private void setOldPatientData(PatientData patient)
	{
		this.oldPatient = patient;
	}
	private void setOldEntry(PainEntryData entry)
	{
		this.oldEntry = entry;
	}
	public PainEntryData getOldPainEntryData()
	{
		return this.oldEntry;
	}
	public PatientData getOldPatientData()
	{
		return this.oldPatient;
	}
	protected void export(PatientData patient, PainEntryData entry)
	{
		FileOperation.updateHistory(Globals.HISTORY_RECENT_MEDICATION, this.getSelectedPatient(), entry.getRecentMedication());
		FileOperation.updateHistory(Globals.HISTORY_MEDICINE_COMPLAINT, this.getSelectedPatient(), entry.getMedicineComplaint());
		FileOperation.exportPainData(patient, entry);
		if (!this.isNewEntry())
		{
			if (!this.dateTime.timeSameAsDefault() || !this.dateTime.dateSameAsDefault())		//Check if the start time or date has been altered
			{
				FileOperation.deleteEntry(Methods.generatePainDataFilePathName(this.oldPatient, this.oldEntry));
			}
		}
		this.getMainFrameReference().changePanel(PanelName.MAIN_MENU);
		this.getMainFrameReference().GRAPH_PANEL.refreshGraph();
		this.getMainFrameReference().PAIN_TABLE.refreshTable();
		Methods.refresHistories(this.getSelectedPatient());
		this.getMainFrameReference().GRAPH_FILTER_PANEL.refresh(this.getMainFrameReference().GRAPH_PANEL.getActivePatientPanel().getSelectedPatientData());
		this.resetDefaults();
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
		this.refreshHistories();
	};
	@Override
	public void actionPerformed(ActionEvent e)
	{
		this.resetDefaults();
		this.getMainFrameReference().changePanel(PanelName.MAIN_MENU);
	}

	@Override
	public void revalidateLanguage() 
	{
		this.labTitle.setText(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_TITLE));
		this.butBack.revalidateLanguage();
		this.butNext.revalidateLanguage();
		this.activeUser.revalidateLanguage();
		this.dateTime.revalidateLanguage();
		this.comments.revalidateLanguage();
		this.duration.revalidateLanguage();
		this.intensity.revalidateLanguage();
		this.painKind.revalidateLanguage();
		this.painLoc.revalidateLanguage();
		this.recentMeds.revalidateLanguage();
		this.trigger.revalidateLanguage();
		this.butCancel.setText(Methods.getLanguageText(XMLIdentifier.CANCEL_TEXT));
	}
}
