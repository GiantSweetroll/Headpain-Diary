package diary.gui.EntryLog;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import diary.constants.Constants;
import diary.constants.PanelName;
import diary.constants.XMLIdentifier;
import diary.gui.MainFrame;
import diary.gui.MainFramePanel;
import diary.methods.Methods;
import giantsweetroll.gui.swing.Gbm;

public class EntryLogButtonControl extends MainFramePanel implements ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3986380191389634823L;

	private JButton button;
	private JLabel label, labelDirection;
	private String nextComponentName;
	private EntryLog entryLog;
	private boolean buttonType;
	
	//Constants
	public static final boolean NEXT = true;
	public static final boolean BACK = false;
	public static final String RIGHT_DIRECTION = "-->";
	public static final String LEFT_DIRECTION = "<--";
	
	//Constructor
	public EntryLogButtonControl(MainFrame mainFrame, EntryLog entryLog, boolean next, String componentName, String text)
	{
		super(mainFrame);
		//Initialization
		this.entryLog = entryLog;
		this.nextComponentName = componentName;
		this.buttonType = next;
		if (next)
		{
			this.button = new JButton(Methods.getLanguageText(XMLIdentifier.NEXT_TEXT));
			this.labelDirection = new JLabel(RIGHT_DIRECTION);
		}
		else
		{
			this.button = new JButton(Methods.getLanguageText(XMLIdentifier.BACK_TEXT));
			this.labelDirection = new JLabel(LEFT_DIRECTION);
		}
		this.label = new JLabel(text);
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_TOP_COMPONENT;
		c.gridwidth = 2;
		this.add(this.button, c);				//Button
		Gbm.newGridLine(c);
		c.gridwidth = 1;
		c.insets = Constants.INSETS_GENERAL;
		if (next)
		{
			this.add(this.label, c);				//Text
			Gbm.nextGridColumn(c);
			this.add(this.labelDirection, c);		//Direction
		}
		else
		{
			this.add(this.labelDirection, c);		//Direction
			Gbm.nextGridColumn(c);
			this.add(this.label, c);				//Text
		}
	}

	//Methods
	public void setNextComponentName(String name)
	{
		this.nextComponentName = name;
	}
	
	//Interface
	@Override
	public void actionPerformed(ActionEvent e)
	{
		//Change entry log active center panel
		if (this.nextComponentName.equals(PanelName.MAIN_MENU) || this.nextComponentName.equals(""))
		{
			this.getMainFrameReference().changePanel(PanelName.MAIN_MENU);
		}
		else
		{
			if (this.buttonType == EntryLogButtonControl.BACK)
			{
				((CardLayout)this.entryLog.getLayout()).previous(this.entryLog.getPanelCenter());
			}
			else
			{
				((CardLayout)this.entryLog.getLayout()).next(this.entryLog.getPanelCenter());
			}
		}
	}
}
