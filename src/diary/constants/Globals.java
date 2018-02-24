package diary.constants;

import diary.gui.MainMenu;
import diary.gui.graphs.GraphPanel;
import diary.gui.settings.SettingsPanel;
import diary.gui.table.TableScreen;
import diary.history.History;
import diary.patientdata.PatientDataManagePanel;

public class Globals 
{
	//Histories
	public static final History HISTORY_RECENT_MEDICATION = new History(Constants.HISTORY_RECENT_MEDICATION_NAME);
	
	//Panels
	public static final MainMenu MAIN_MENU = new MainMenu();
	public static final SettingsPanel SETTINGS_PANEL = new SettingsPanel();
	public static final PatientDataManagePanel MANAGE_PATIENTS_PANEL = new PatientDataManagePanel();
	public static final GraphPanel GRAPH_PANEL = new GraphPanel();
	public static final TableScreen PAIN_TABLE = new TableScreen();
}
