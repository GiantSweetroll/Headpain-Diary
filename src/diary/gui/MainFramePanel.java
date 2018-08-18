package diary.gui;

import javax.swing.JPanel;

import diary.interfaces.LanguageListener;

public abstract class MainFramePanel extends JPanel implements LanguageListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2063105167102825387L;

	private MainFrame mainFrame;
	
	public MainFramePanel(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
	}
	
	public MainFrame getMainFrameReference()
	{
		return this.mainFrame;
	}
}
