package diary.constants;

import diary.gui.MainMenu;
import diary.gui.EntryLog.EntryLog;
import diary.gui.EntryLog.EntryTypeSelectionPanel;
import diary.gui.graphs.GraphFilterPanel;
import diary.gui.graphs.GraphPanel;
import diary.gui.settings.SettingsPanel;
import diary.gui.table.TableScreen;
import diary.history.History;
import diary.patientdata.PatientData;
import diary.patientdata.PatientDataManagePanel;

public class Globals 
{	
	//Histories
	public static final History HISTORY_RECENT_MEDICATION = new History(Constants.HISTORY_RECENT_MEDICATION_NAME, new PatientData());
	public static final History HISTORY_MEDICINE_COMPLAINT = new History(Constants.HISTORY_MEDICINE_COMPLAINT_NAME, new PatientData());
	
	//Panels
	public static MainMenu MAIN_MENU = new MainMenu();
	public static SettingsPanel SETTINGS_PANEL = new SettingsPanel();
	public static PatientDataManagePanel MANAGE_PATIENTS_PANEL = new PatientDataManagePanel();
	public static GraphFilterPanel GRAPH_FILTER_PANEL = new GraphFilterPanel();
	public static GraphPanel GRAPH_PANEL = new GraphPanel();
	public static TableScreen PAIN_TABLE = new TableScreen();
	public static EntryLog ENTRY_LOG = new EntryLog();
	public static EntryTypeSelectionPanel ENTRY_LOG_TYPE_SELECTION = new EntryTypeSelectionPanel();
}
