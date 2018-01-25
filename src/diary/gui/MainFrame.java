package diary.gui;

import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import diary.Settings;
import diary.constants.Constants;
import diary.methods.FileOperation;
import diary.methods.Methods;
import diary.methods.XMLGenerator;

// TODO: Auto-generated Javadoc
/**
 * The Class MainFrame.
 */
public class MainFrame
{
	
	/** The frame. */
	public static JFrame frame;
	public static Settings setting;
	
	private static JComponent jComponent;
	
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
		jComponent = new MainMenu();
		MainFrame.refreshSettings();
		
		frame.add(jComponent);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(Constants.SCREENSIZE.width/2, (Constants.SCREENSIZE.height/4)*3);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	/**
	 * Change the active panel of the frame.
	 *
	 * @param component the component to display
	 */
	public static final void changePanel(JComponent component)
	{
		frame.remove(jComponent);
		frame.add(component);
		jComponent = component;
		frame.revalidate();
		frame.repaint();
	}
	
	public static final void refreshSettings()
	{
		try 
		{
			MainFrame.setting = new Settings(FileOperation.loadSettingsDocument());
			if(MainFrame.setting.getDataMap().get(Settings.WINDOW_MODE).equals(Settings.WINDOWED))
			{
				Methods.makeWindowed(MainFrame.frame);
			}
			else
			{
				Methods.makeFullscreen(MainFrame.frame);
			}
			
			Constants.LANGUAGE.setLanguage(MainFrame.setting.getDataMap().get(Settings.LANGUAGE));
		} 
		catch (ParserConfigurationException | SAXException | IOException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * The main method.
	 *
	 * @param args the argu22ments 
	 */
	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(new Runnable()
				{
					public void run()
					{
						try
						{
							UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
							
							if(!FileOperation.dataExists(Constants.SETTINGS_FOLDER_PATH + Constants.SETTINGS_FILE_NAME))		//If settings file doesn't exists
							{
								FileOperation.saveSettings(new Settings());
							}
							
							if (!FileOperation.defaultLanguageExists())		//If default language (english) doesn't exist
							{
								XMLGenerator.generateXML();
							}
							
							new MainFrame();
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
