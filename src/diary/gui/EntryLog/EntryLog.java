package diary.gui.EntryLog;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import diary.constants.Constants;
import diary.constants.Globals;
import diary.constants.PanelName;
import diary.constants.XMLIdentifier;
import diary.data.PainEntryData;
import diary.gui.ActivePatientPanel;
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
import diary.interfaces.LanguageListener;
import diary.methods.FileOperation;
import diary.methods.Methods;
import diary.patientdata.PatientData;
import giantsweetroll.gui.swing.ScrollPaneManager;

public class EntryLog extends JPanel implements GUIFunction, ActionListener, LanguageListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2559541533340658831L;
	
	private JPanel panelTop, panelInput, panelBelow, panelCenter;
	private EntryLogMapPanel panelEntryLogMap;
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
	private JScrollPane scrollCenter;
	
	private boolean isNewEntry;
	private byte panelState;
	
	//Constants
	protected static final String ACTIVE_PATIENT = "activepatiente", 
									COMMENTS="commente", 
									DATE_TIME="datetimee",
									DURATION="duratione", 
									INTENSITY="intensitye",
									PAIN_KIND="poaindkine", 
									PAIN_LOCATION="painloce", 
									RECENT_MEDICATION="recentmede", 
									TRIGGER="triggere";
	protected static final byte PROFILE_SELECTION = 0,
								DATE_TIME_SELECTION = PROFILE_SELECTION+1,
								DURATION_SELECTION = DATE_TIME_SELECTION+1,
								PAIN_LOCATION_SELECITON = DURATION_SELECTION+1,
								PAIN_KIND_SELECTION = PAIN_LOCATION_SELECITON+1,
								INTENSITY_SELECTION = PAIN_KIND_SELECTION+1,
								TRIGGER_SELECTION = INTENSITY_SELECTION+1,
								RECENT_MEDICAITON_SELECTION = TRIGGER_SELECTION+1,
								COMMENTS_SELECTION = RECENT_MEDICAITON_SELECTION+1;		//To be used with EntryLogButtonControl
	
	//Constructor
	public EntryLog()
	{
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
	private void initPanelInput()
	{
		//Initialization
		this.panelInput = new JPanel();
		this.activeUser = new ActiveUser(new ActivePatientPanel());
		this.comments = new Comments();
		this.dateTime= new DateTimeSelect();
		this.duration = new Duration();
		this.intensity = new Intensity();
		this.painKind = new PainKind();
		this.painLoc = new PainLocation();
		this.recentMeds = new RecentMedication();
		this.trigger = new Trigger();
		
		//Properties
		this.panelInput.setLayout(new CardLayout());
		this.panelInput.setBackground(Color.WHITE);
		this.activeUser.setName(EntryLog.ACTIVE_PATIENT);
		this.comments.setName(EntryLog.COMMENTS);
		this.dateTime.setName(EntryLog.DATE_TIME);
		this.duration.setName(EntryLog.DURATION);
		this.intensity.setName(EntryLog.INTENSITY);
		this.painKind.setName(EntryLog.PAIN_KIND);
		this.painLoc.setName(EntryLog.PAIN_LOCATION);
		this.recentMeds.setName(EntryLog.RECENT_MEDICATION);
		this.trigger.setName(EntryLog.TRIGGER);
		
		//Add to panel
		this.panelInput.add(this.activeUser, EntryLog.ACTIVE_PATIENT);
		this.panelInput.add(this.dateTime, EntryLog.DATE_TIME);
		this.panelInput.add(this.duration, EntryLog.DURATION);
		this.panelInput.add(this.painLoc, EntryLog.PAIN_LOCATION);
		this.panelInput.add(this.painKind, EntryLog.PAIN_KIND);
		this.panelInput.add(this.intensity, EntryLog.INTENSITY);
		this.panelInput.add(this.trigger, EntryLog.TRIGGER);
		this.panelInput.add(this.recentMeds, EntryLog.RECENT_MEDICATION);
		this.panelInput.add(this.comments, EntryLog.COMMENTS);
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
//		this.panelTop.setBorder(Methods.createTransparentBorder(5));
		this.panelTop.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		this.butCancel.addActionListener(this);
//		this.butCancel.setBackground(Constants.COLOR_MAIN_MENU_BUTTONS);
		this.butCancel.setBackground(new Color(225, 29, 29));
		this.butCancel.setForeground(Color.WHITE);
		this.butCancel.setMnemonic(KeyEvent.VK_X);
		this.butCancel.setToolTipText("alt+x");
		this.labTitle.setFont(Constants.FONT_SUB_TITLE);
		
		//Add to panel
		this.panelTop.add(this.labTitle, BorderLayout.CENTER);
		this.panelTop.add(this.butCancel, BorderLayout.WEST);
	}
	private void initPanelBelow()
	{
		//Initialization
		this.panelBelow = new JPanel();
		this.panelState = EntryLog.PROFILE_SELECTION;
		this.butBack = new EntryLogButtonControl(this, EntryLogButtonControl.BACK, "");
		this.butNext = new EntryLogButtonControl(this, EntryLogButtonControl.NEXT, "");
		
		//Properties
		this.panelBelow.setLayout(new BorderLayout());
		this.panelBelow.setOpaque(false);
		
		//Add to panel
		this.panelBelow.add(this.butBack, BorderLayout.WEST);
		this.panelBelow.add(this.butNext, BorderLayout.EAST);
	}
	private void initPanelCenter()
	{
		//Initialization
		this.panelCenter = new JPanel(new BorderLayout());
		this.panelEntryLogMap = new EntryLogMapPanel();
		this.initPanelInput();
		this.scrollCenter = ScrollPaneManager.generateDefaultScrollPane(this.panelEntryLogMap, 10, 10);
		
		//Properties
		this.panelEntryLogMap.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		
		//Add to panel
		this.panelCenter.add(scrollCenter, BorderLayout.WEST);
		this.panelCenter.add(this.panelInput, BorderLayout.CENTER);
	}
	//Methods
	public void changeActiveSection(String section)
	{
		Methods.changePanel(this.panelInput, section);
	}
	public EntryLogButtonControl getNextButton()
	{
		return this.butNext;
	}
	public EntryLogButtonControl getBackButton()
	{
		return this.butBack;
	}
	public JPanel getPanelInput()
	{
		return this.panelInput;
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
		this.intensity.setData(Integer.parseInt(entry.getIntensity()));
		this.painKind.setData(entry);
		this.painLoc.setData(entry);
		this.recentMeds.setData(entry.getRecentMedication(), entry.getMedicineComplaint());
		this.trigger.setData(entry);
	}
	public EntryLogMapPanel getFormSelectionMapPanel()
	{
		return this.panelEntryLogMap;
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
		entry.setIntensity(Integer.toString(this.intensity.getData()));
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
	protected boolean lastPainKindSame(PatientData patient, PainEntryData entry)
	{
		try
		{
			return patient.getLastPainKind().equals(entry.getPainKind());
		}
		catch(NullPointerException ex) 
		{
			return false;
		}
	}
	protected boolean lastRecentMedsSame(PatientData patient, PainEntryData entry)
	{
		try
		{
			return patient.getLastRecentMeds().equals(entry.getRecentMedication());
		}
		catch(NullPointerException ex) 
		{
			return false;
		}
	}
	protected boolean lastMedicineComplaintSame(PatientData patient, PainEntryData entry)
	{
		try
		{
			return patient.getLastMedicineComplaint().equals(entry.getMedicineComplaint());
		}
		catch(NullPointerException ex) 
		{
			return false;
		}
	}
	
	protected void export(PatientData patient, PainEntryData entry)
	{
		if(!this.lastPainKindSame(patient, entry))
		{
			patient.setLastPainKind(entry.getPainKind());
		}
		if(!this.lastRecentMedsSame(patient, entry))
		{
			patient.setLastRecentMeds(entry.getRecentMedication());
		}
		if(!this.lastMedicineComplaintSame(patient, entry))
		{
			patient.setLastMedicineComplaint(entry.getMedicineComplaint());
		}
		FileOperation.savePatientData(patient);
		FileOperation.updateHistory(Globals.HISTORY_RECENT_MEDICATION, this.getSelectedPatient(), entry.getRecentMedication());
		FileOperation.updateHistory(Globals.HISTORY_MEDICINE_COMPLAINT, this.getSelectedPatient(), entry.getMedicineComplaint());
		FileOperation.updateHistory(Globals.HISTORY_PAIN_KIND, this.getSelectedPatient(), entry.getPainKind());
		FileOperation.exportPainData(patient, entry);
		
		if (!this.isNewEntry())
		{
			if (!this.dateTime.timeSameAsDefault() || !this.dateTime.dateSameAsDefault())		//Check if the start time or date has been altered
			{
				FileOperation.deleteEntry(Methods.generatePainDataFilePathName(this.oldPatient, this.oldEntry));
			}
		}
		Globals.MAIN_FRAME.changePanel(PanelName.MAIN_MENU);
		Globals.GRAPH_PANEL.refresh();
		Globals.PAIN_TABLE.refresh();
		Methods.refresHistories(this.getSelectedPatient());
		Globals.GRAPH_FILTER_PANEL.refresh(Globals.GRAPH_PANEL.getActivePatientPanel().getSelectedPatientData());
		this.resetDefaults();
	}
	
	//Interfaces
	@Override
	public void resetDefaults() 
	{
		this.panelState = EntryLog.PROFILE_SELECTION;
		this.activeUser.resetDefaults();
		this.comments.resetDefaults();
		this.dateTime.resetDefaults();
		this.duration.resetDefaults();
		this.intensity.resetDefaults();
		this.painKind.resetDefaults();
		this.painLoc.resetDefaults();
		this.recentMeds.resetDefaults();
		this.refreshHistories();
		this.trigger.resetDefaults();
		this.changeActiveSection(EntryLog.ACTIVE_PATIENT);
		this.panelEntryLogMap.resetDefaults();
		this.scrollCenter.getVerticalScrollBar().setValue(0);
		this.scrollCenter.getHorizontalScrollBar().setValue(0);
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
		Globals.MAIN_FRAME.changePanel(PanelName.MAIN_MENU);
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
		this.panelEntryLogMap.revalidateLanguage();
	}
}
