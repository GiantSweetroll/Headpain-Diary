package diary.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import diary.constants.Constants;
import diary.constants.Globals;
import diary.constants.PanelName;
import diary.data.Settings;
import diary.gui.EntryLog.EntryLog;
import diary.gui.graphs.GraphFilterPanel;
import diary.gui.graphs.GraphPanel;
import diary.gui.settings.SettingsPanel;
import diary.gui.table.TableScreen;
import diary.methods.FileOperation;
import diary.methods.Methods;
import diary.methods.XMLGenerator;
import diary.patientdata.PatientDataManagePanel;
import diary.patientdata.PatientDataRegisterationForm;

// TODO: Auto-generated Javadoc
/**
 * The Class MainFrame.
 */
public class MainFrame
{
	 
	/** The frame. */
	private JFrame frame;
//	public static Settings setting;
	
	public static boolean isFullScreen;
	
	private JPanel panel;
	
	//Panels
	public final MainMenu MAIN_MENU = new MainMenu(this);
	public final SettingsPanel SETTINGS_PANEL = new SettingsPanel(this);
	public final PatientDataManagePanel MANAGE_PATIENTS_PANEL = new PatientDataManagePanel(this);
	public final GraphFilterPanel GRAPH_FILTER_PANEL = new GraphFilterPanel();
	public final GraphPanel GRAPH_PANEL = new GraphPanel(this);
	public final TableScreen PAIN_TABLE = new TableScreen(this);
	public final EntryLog ENTRY_LOG = new EntryLog(this);
	
	/**
	 * Instantiates a new main frame.
	 */
	public MainFrame()
	{
		this.init();
	}
	
	/**
	 * Initializes the frame.
	 */
	private void init()
	{
		//Initialization
		frame = new JFrame(Constants.PROGRAM_TITLE);
		this.panel = new JPanel();
		
		//Properties
		this.panel.setLayout(new CardLayout());
		MainFrame.isFullScreen = false;
	//	this.refreshSettings();
		Methods.addLanguageListener(this.MAIN_MENU);
		Methods.addLanguageListener(this.SETTINGS_PANEL);
		Methods.addLanguageListener(this.MANAGE_PATIENTS_PANEL);
		Methods.addLanguageListener(this.GRAPH_FILTER_PANEL);
		Methods.addLanguageListener(this.GRAPH_PANEL);
		Methods.addLanguageListener(this.PAIN_TABLE);
		Methods.addLanguageListener(this.ENTRY_LOG);
	//	jComponent = Globals.MAIN_MENU;
		
		//Add to panel
		this.panel.add(this.MAIN_MENU, PanelName.MAIN_MENU);
		this.panel.add(this.ENTRY_LOG, PanelName.ENTRY_LOG);
//		this.panel.add(this.GRAPH_FILTER_PANEL, PanelName.GRAPH_FILTER_PANEL);
		this.panel.add(this.GRAPH_PANEL, PanelName.GRAPH_PANEL);
		this.panel.add(this.MANAGE_PATIENTS_PANEL, PanelName.MANAGE_PATIENTS_PANEL);
		this.panel.add(this.SETTINGS_PANEL, PanelName.SETTING_PANEL);
		this.panel.add(this.PAIN_TABLE, PanelName.PAIN_TABLE);
		
		//Add to frame
		frame.add(panel);
		this.refreshSettings();
		
		/*
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setSize(Constants.SCREENSIZE.width/2, (Constants.SCREENSIZE.height/4)*3);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		*/
		
	//	System.out.println(frame.getSize());
	}
	/**
	 * Change the active panel of the frame.
	 *
	 * @param component the component to display
	 */
	/*
	public static final void changePanel(JComponent component)
	{
		try
		{
			lastComponent = jComponent;
			frame.remove(jComponent);
		}
		catch(NullPointerException ex) {}
		try
		{
			if(component instanceof MainMenu)
			{
				MainFrame.checkUsers();
				
				/*
				if (Globals.MAIN_MENU.getVerticalScrollBar().isVisible())		//Experimental
				{
					MainFrame.GENERAL_FONT_SIZE--;
					MainFrame.changePanel(Globals.MAIN_MENU);
				}
				
			}
			else
			{
				frame.add(component);
				jComponent = component;
			}
			frame.revalidate();
			frame.repaint();
		}
		catch(NullPointerException ex)
		{
			ex.printStackTrace();
		}
	}
	*/
	public void changePanel(String key)
	{
		try
		{
			/*
			if (key.equals(PanelName.MAIN_MENU))
			{
				this.checkUsers();
			}
			*/
			
			Methods.changePanel(this.panel, key);
		}
		catch(NullPointerException ex) {ex.printStackTrace();}
	}
	public void changePanel(JPanel panel, String key)
	{
		this.panel.add(panel, key);
		this.changePanel(key);
	}
	
	public JPanel getPanelCanvas()
	{
		return this.panel;
	}
	public JFrame getFrameInstance()
	{
		return this.frame;
	}
	
	public static final boolean isFullScreen()
	{
		return MainFrame.isFullScreen;
	}
	
	public final void refreshSettings()
	{
		try 
		{
			Globals.setting = new Settings(FileOperation.loadSettingsDocument());
			//Select window mode
			if(Globals.setting.getDataMap().get(Settings.WINDOW_MODE).equals(Settings.WINDOWED))
			{
				Methods.makeWindowed(this);
			}
			else
			{
				Methods.makeFullscreen(this);
			}
			
			//Selected Language
			Globals.LANGUAGE.setLanguage(Globals.setting.getDataMap().get(Settings.LANGUAGE));
			
			//Database path
			File file = new File(Globals.setting.getDataMap().get(Settings.DATABASE_PATH));
			if(!file.exists())
			{
				file.mkdirs();
			}
			//User Database path
			file = new File(Globals.setting.getDataMap().get(Settings.DATABASE_USERS_PATH));
			if (!file.exists())
			{
				file.mkdirs();
			}
			//Check for users
			this.checkUsers();
			
			//If history path is not found
			file = new File(Constants.HISTORY_FOLDER_PATH);
			if (!file.exists())
			{
				file.mkdirs();
			}
		} 
		catch (ParserConfigurationException | SAXException | IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void checkUsers()
	{
		try
		{
			if (!Methods.hasRegisteredUser())
			{
				try
				{
					/*
					jComponent = Globals.MAIN_MENU;
					MainFrame.lastComponent = Globals.MAIN_MENU;
					MainFrame.changePanel(new PatientDataRegisterationForm());
					*/
					this.changePanel(new PatientDataRegisterationForm(this, false), PanelName.PATIENT_REGISTERATION_FORM);
				}
				catch(NullPointerException ex)
				{
					ex.printStackTrace();
				}
			}
			else
			{
	//			jComponent = Globals.MAIN_MENU;
	//			MainFrame.frame.add(jComponent);
	//			MainFrame.changePanel(Globals.MAIN_MENU);
				this.changePanel(PanelName.MAIN_MENU);
			}
		}
		catch(NullPointerException ex)			//no Settings file
		{
			FileOperation.saveSettings(new Settings());
			if (!Methods.hasRegisteredUser())
			{
				try
				{
		//			jComponent = Globals.MAIN_MENU;
		//			MainFrame.lastComponent = Globals.MAIN_MENU;
		//			MainFrame.changePanel(new PatientDataRegisterationForm());
					this.changePanel(new PatientDataRegisterationForm(this, false), PanelName.PATIENT_REGISTERATION_FORM);
				}
				catch(NullPointerException ex1)
				{
					ex1.printStackTrace();
				}
			}
			else
			{
	//			jComponent = Globals.MAIN_MENU;
	//			MainFrame.frame.add(jComponent);
				this.changePanel(PanelName.MAIN_MENU);
			}
		}
	}

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
							
							MainFrame mainFrame = new MainFrame();
				//			Globals.MAIN_MENU = new MainMenu();
							Globals.HISTORY_MEDICINE_COMPLAINT.refresh(mainFrame.ENTRY_LOG.getActivePatientPanel().getSelectedPatientData());
							Globals.HISTORY_RECENT_MEDICATION.refresh(mainFrame.ENTRY_LOG.getActivePatientPanel().getSelectedPatientData());
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
