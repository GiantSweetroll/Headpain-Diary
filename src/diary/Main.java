package diary;

import java.awt.Color;
import java.awt.Font;
import java.util.HashSet;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.FontUIResource;

import diary.constants.Constants;
import diary.constants.Globals;
import diary.constants.XMLIdentifier;
import diary.data.Settings;
import diary.gui.MainFrame;
import diary.gui.MainMenu;
import diary.gui.EntryLog.EntryLog;
import diary.gui.graphs.GraphFilterPanel;
import diary.gui.graphs.GraphPanel;
import diary.gui.settings.SettingsPanel;
import diary.gui.table.TableScreen;
import diary.interfaces.LanguageListener;
import diary.language.Language;
import diary.methods.FileOperation;
import diary.methods.Methods;
import diary.methods.XMLGenerator;
import diary.patientdata.PatientDataManagePanel;

public class Main 
{
	
	/**
	 * The main method.
	 *
	 * @param args the arguments 
	 */
	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(new Runnable()
				{
					public void run()
					{	
						try
						{
				//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
							Methods.setUIFont(new FontUIResource(Constants.FONT_TYPE_GENERAL, Font.PLAIN, Constants.FONT_GENERAL_SIZE));
							UIManager.put("OptionPane.background", Color.WHITE);
							UIManager.put("Panel.background", Color.white);
							
							if(!FileOperation.dataExists(Constants.SETTINGS_FOLDER_PATH + Constants.SETTINGS_FILE_NAME))		//If settings file doesn't exists
							{
								FileOperation.saveSettings(new Settings());
							}
							
							if (!FileOperation.defaultLanguageExists())		//If default language (english) doesn't exist
							{
								XMLGenerator.generateXML();
							}
							
				//			Globals.MAIN_MENU = new MainMenu();
							Globals.LANGUAGE_COMPONENTS = new HashSet<LanguageListener>();
							Globals.LANGUAGE = new Language();
							Globals.MAIN_MENU = new MainMenu();
							Globals.SETTINGS_PANEL = new SettingsPanel();
							Globals.MANAGE_PATIENTS_PANEL = new PatientDataManagePanel();
							Globals.GRAPH_FILTER_PANEL = new GraphFilterPanel();
							Globals.GRAPH_PANEL = new GraphPanel();
							Globals.PAIN_TABLE = new TableScreen();
							Globals.ENTRY_LOG = new EntryLog();
							Globals.MAIN_FRAME = new MainFrame();
							Constants.FILTER_IMAGE_FILES = new FileNameExtensionFilter(Methods.getLanguageText(XMLIdentifier.IMAGE_FILES_TEXT), 
									"jpg",
									"png",
									"jpeg");
							Globals.HISTORY_MEDICINE_COMPLAINT.refresh(Globals.ENTRY_LOG.getActivePatientPanel().getSelectedPatientData());
							Globals.HISTORY_RECENT_MEDICATION.refresh(Globals.ENTRY_LOG.getActivePatientPanel().getSelectedPatientData());
						}
						catch(Exception ex)
						{
							ex.printStackTrace();
						}
					}
				}
				);
	}
}
