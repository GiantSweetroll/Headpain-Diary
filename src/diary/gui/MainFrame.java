package diary.gui;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import diary.constants.Constants;
import diary.constants.Globals;
import diary.data.Settings;
import diary.methods.FileOperation;
import diary.methods.Methods;
import diary.methods.XMLGenerator;
import diary.patientdata.PatientDataRegisterationForm;

// TODO: Auto-generated Javadoc
/**
 * The Class MainFrame.
 */
public class MainFrame
{
	
	/** The frame. */
	public static JFrame frame;
	public static Settings setting;
	public static JComponent lastComponent;
	
	public static JComponent jComponent;
	
	public static int GENERAL_FONT_SIZE = 15;
	
	public static boolean isFullScreen;
	
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
		MainFrame.isFullScreen = false;
		MainFrame.refreshSettings();
	//	jComponent = Globals.MAIN_MENU;
		
		frame.add(jComponent);
		
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
				*/
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
	
	public static final boolean isFullScreen()
	{
		return MainFrame.isFullScreen;
	}
	
	public static final void refreshSettings()
	{
		try 
		{
			MainFrame.setting = new Settings(FileOperation.loadSettingsDocument());
			//Select window mode
			if(MainFrame.setting.getDataMap().get(Settings.WINDOW_MODE).equals(Settings.WINDOWED))
			{
				Methods.makeWindowed(MainFrame.frame);
			}
			else
			{
				Methods.makeFullscreen(MainFrame.frame);
			}
			
			//Selected Language
			Constants.LANGUAGE.setLanguage(MainFrame.setting.getDataMap().get(Settings.LANGUAGE));
			
			//Database path
			File file = new File(setting.getDataMap().get(Settings.DATABASE_PATH));
			if(!file.exists())
			{
				file.mkdirs();
			}
			//User Database path
			file = new File(setting.getDataMap().get(Settings.DATABASE_USERS_PATH));
			if (!file.exists())
			{
				file.mkdirs();
			}
			//Check for users
			MainFrame.checkUsers();
			
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
	
	public static void checkUsers()
	{
		try
		{
			if (!Methods.hasRegisteredUser())
			{
				try
				{
					jComponent = Globals.MAIN_MENU;
					MainFrame.lastComponent = Globals.MAIN_MENU;
					MainFrame.changePanel(new PatientDataRegisterationForm());
				}
				catch(NullPointerException ex)
				{
					ex.printStackTrace();
				}
			}
			else
			{
				jComponent = Globals.MAIN_MENU;
				MainFrame.frame.add(jComponent);
	//			MainFrame.changePanel(Globals.MAIN_MENU);
			}
		}
		catch(NullPointerException ex)
		{
			FileOperation.saveSettings(new Settings());
			if (!Methods.hasRegisteredUser())
			{
				try
				{
					jComponent = Globals.MAIN_MENU;
					MainFrame.lastComponent = Globals.MAIN_MENU;
					MainFrame.changePanel(new PatientDataRegisterationForm());
				}
				catch(NullPointerException ex1)
				{
					ex1.printStackTrace();
				}
			}
			else
			{
				jComponent = Globals.MAIN_MENU;
				MainFrame.frame.add(jComponent);
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
							Methods.setUIFont(new FontUIResource(Constants.FONT_TYPE_GENERAL, Font.PLAIN, MainFrame.GENERAL_FONT_SIZE));
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
							
							new MainFrame();
							Globals.MAIN_MENU = new MainMenu();
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
