package diary.gui;

import java.awt.CardLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import diary.constants.Constants;
import diary.constants.Globals;
import diary.constants.ImageConstants;
import diary.constants.PanelName;
import diary.data.Settings;
import diary.methods.FileOperation;
import diary.methods.Methods;
import diary.patientdata.PatientDataRegisterationForm;
import giantsweetroll.ImageManager;

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
		this.frame.setIconImage(ImageManager.getImage(ImageConstants.LOGO_MINI));
	//	this.refreshSettings();
		Methods.addLanguageListener(Globals.MAIN_MENU);
		Methods.addLanguageListener(Globals.SETTINGS_PANEL);
		Methods.addLanguageListener(Globals.MANAGE_PATIENTS_PANEL);
		Methods.addLanguageListener(Globals.GRAPH_FILTER_PANEL);
		Methods.addLanguageListener(Globals.GRAPH_PANEL);
		Methods.addLanguageListener(Globals.PAIN_TABLE);
		Methods.addLanguageListener(Globals.ENTRY_LOG);
	//	jComponent = Globals.MAIN_MENU;
		
		//Add to panel
		this.panel.add(Globals.MAIN_MENU, PanelName.MAIN_MENU);
		this.panel.add(Globals.ENTRY_LOG, PanelName.ENTRY_LOG);
//		this.panel.add(this.GRAPH_FILTER_PANEL, PanelName.GRAPH_FILTER_PANEL);
		this.panel.add(Globals.GRAPH_PANEL, PanelName.GRAPH_PANEL);
		this.panel.add(Globals.MANAGE_PATIENTS_PANEL, PanelName.MANAGE_PATIENTS_PANEL);
		this.panel.add(Globals.SETTINGS_PANEL, PanelName.SETTING_PANEL);
		this.panel.add(Globals.PAIN_TABLE, PanelName.PAIN_TABLE);
		
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
					this.changePanel(new PatientDataRegisterationForm(false), PanelName.PATIENT_REGISTERATION_FORM);
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
					this.changePanel(new PatientDataRegisterationForm(false), PanelName.PATIENT_REGISTERATION_FORM);
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
}
