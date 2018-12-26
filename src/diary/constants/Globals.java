package diary.constants;

import java.util.HashSet;
import java.util.Set;

import diary.data.Settings;
import diary.gui.MainFrame;
import diary.gui.MainMenu;
import diary.gui.EntryLog.EntryLog;
import diary.gui.graphs.GraphFilterPanel;
import diary.gui.graphs.GraphPanel;
import diary.gui.settings.SettingsPanel;
import diary.gui.table.TableScreen;
import diary.history.History;
import diary.interfaces.LanguageListener;
import diary.language.Language;
import diary.patientdata.PatientData;
import diary.patientdata.PatientDataManagePanel;

public class Globals 
{	
	//Histories
	public static final History HISTORY_RECENT_MEDICATION = new History(Constants.HISTORY_RECENT_MEDICATION_NAME, new PatientData());
	public static final History HISTORY_MEDICINE_COMPLAINT = new History(Constants.HISTORY_MEDICINE_COMPLAINT_NAME, new PatientData());
	
	//Settings
	public static Settings setting = new Settings();
	
	//Global Variables
	public static Set<Character> keysPressed = new HashSet<Character>();
	public static boolean mouseDown = false;
	public static Set<LanguageListener> LANGUAGE_COMPONENTS;
	public static Language LANGUAGE;
	
	//Panels
	/*
	public static MainMenu MAIN_MENU = new MainMenu();
	public static SettingsPanel SETTINGS_PANEL = new SettingsPanel();
	public static PatientDataManagePanel MANAGE_PATIENTS_PANEL = new PatientDataManagePanel();
	public static GraphFilterPanel GRAPH_FILTER_PANEL = new GraphFilterPanel();
	public static GraphPanel GRAPH_PANEL = new GraphPanel();
	public static TableScreen PAIN_TABLE = new TableScreen();
	public static EntryLog ENTRY_LOG = new EntryLog();			*/
//	public static EntryTypeSelectionPanel ENTRY_LOG_TYPE_SELECTION = new EntryTypeSelectionPanel();
//	public static EntryLogMulti ENTRY_LOG_MULTI_DATE_RANGE_SELECT = new EntryLogMulti();
	public static MainFrame MAIN_FRAME;
	public static MainMenu MAIN_MENU;
	public static SettingsPanel SETTINGS_PANEL;
	public static PatientDataManagePanel MANAGE_PATIENTS_PANEL;
	public static GraphFilterPanel GRAPH_FILTER_PANEL;
	public static GraphPanel GRAPH_PANEL;
	public static TableScreen PAIN_TABLE;
	public static EntryLog ENTRY_LOG;
}
