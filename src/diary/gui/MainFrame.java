package diary.gui;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import diary.constants.Constants;
import diary.methods.FileOperation;
import diary.methods.XMLGenerator;

// TODO: Auto-generated Javadoc
/**
 * The Class MainFrame.
 */
public class MainFrame
{
	
	/** The frame. */
	public static JFrame frame;
	
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
							
							if (!FileOperation.defaultLanguageExists())
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
