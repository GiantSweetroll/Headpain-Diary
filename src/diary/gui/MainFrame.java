package diary.gui;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import diary.constants.Constants;

// TODO: Auto-generated Javadoc
/**
 * The Class MainFrame.
 */
public final class MainFrame
{
	
	/** The frame. */
	public static JFrame frame;
	
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
		MainMenu mp = new MainMenu();
		
		frame.add(mp);
		
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
		MainFrame.frame.removeAll();
		MainFrame.frame.add(component);
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
							UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
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
