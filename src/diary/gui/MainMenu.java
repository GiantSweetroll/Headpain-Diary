package diary.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import diary.constants.Constants;
import diary.constants.XMLIdentifier;
import diary.gui.EntryLog.EntryLog;
import diary.gui.graphs.GraphPanel;
import diary.gui.settings.SettingsPanel;
import diary.gui.table.TableScreen;
import diary.methods.Methods;
import diary.patientdata.PatientDataManagePanel;
import giantsweetroll.gui.swing.Gbm;

// TODO: Auto-generated Javadoc
/**
 * The Class MainMenu.
 */
public final class MainMenu extends JPanel implements ActionListener
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8438576029794021570L;
	
	
	//JPanels
	private JPanel panelBelow;
	private JPanel panelBelowAuthor;
	private JPanel panelBelowVersion;
	private JPanel panelTitle;
	private JPanel panelButtons;
	private JPanel panelCenter;
	
	//JScrollPanes
	private JScrollPane scrollPanelCenter;

	//panel components
	private JLabel labTitle;
	private JButton butNew;
	private JButton butGraph;
	private JButton butTable;
	private JButton butManagePatients;
	private JButton butSettings;
	private JButton butExit;
	private JLabel labAuthor;
	private JLabel labVersion;
	
	//Arrays
//	private ArrayList<ArrayList<JLabel>> listButtonTextLabels;
	private ArrayList<JButton> listButtons;
	
	//Constants
	private final String NEW_LOG = "new";
	private final String GRAPH = "graph";
	private final String TABLE = "table";
	private final String MANAGE_PATIENTS = "manage patients";
	private final String SETTINGS = "settings";
	private final String EXIT = "exit";
	
	/**
	 * Instantiates a new main menu.
	 */
	public MainMenu()
	{
		this.init();
	}
	
	//Methods
	private void init()
	{
		//Initialization
		this.initPanelCenter();
		this.initPanelBelow();
		
		//Properties
		this.setLayout(new BorderLayout());
		this.setOpaque(false);
		
		//Add to panel
		this.add(this.scrollPanelCenter, BorderLayout.CENTER);
		this.add(this.panelBelow, BorderLayout.SOUTH);
	}
	
	private void initPanelBelowAuthor()
	{
		//Initialization
		this.panelBelowAuthor = new JPanel();
		this.labAuthor = new JLabel ("   " + Methods.getLanguageText(XMLIdentifier.AUTHOR_TEXT) + " Gardyan P. Akbar   ");
		
		//Properties
		this.panelBelowAuthor.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.panelBelowAuthor.setOpaque(false);
		this.labAuthor.setFont(Constants.FONT_GENERAL_BOLD);
		
		//add to panel
		this.panelBelowAuthor.add(this.labAuthor);
	}

	private void initPanelBelowVersion()
	{
		//Initialization
		this.panelBelowVersion = new JPanel();
		this.labVersion = new JLabel ("   " + Methods.getLanguageText(XMLIdentifier.VERSION_TEXT) + " " + Constants.VERSION + "   ");
		
		//Properties
		this.panelBelowVersion.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.panelBelowVersion.setOpaque(false);
		this.labVersion.setFont(Constants.FONT_GENERAL_BOLD);
		
		//add to panel
		this.panelBelowVersion.add(this.labVersion);
	}
	
	private void initPanelBelow()
	{
		//Initialization
		this.panelBelow = new JPanel();
		this.initPanelBelowAuthor();
		this.initPanelBelowVersion();
		
		//Properties
		this.panelBelow.setLayout(new BorderLayout());
		this.panelBelow.setOpaque(false);
		
		//add to panel
		this.panelBelow.add(this.panelBelowAuthor, BorderLayout.WEST);
		this.panelBelow.add(this.panelBelowVersion, BorderLayout.EAST);
	}
	
	private void initPanelTitle()
	{
		//Initialization
		this.panelTitle = new JPanel();
		this.labTitle = new JLabel (Constants.PROGRAM_TITLE);
		
		//Properties
		this.panelTitle.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.panelTitle.setOpaque(false);
		this.labTitle.setFont(Constants.FONT_TITLE);
		this.labTitle.setHorizontalAlignment(SwingConstants.CENTER);
	
		//Add to panel
		this.panelTitle.add(this.labTitle);
	}
	
	private void initPanelButtons()
	{
		//Initialization
		this.panelButtons = new JPanel();
		GridBagConstraints c = new GridBagConstraints();
		this.listButtons = new ArrayList<JButton>();
		this.butNew = new JButton (Methods.getLanguageText(XMLIdentifier.NEW_ENTRY_BUTTON_TEXT));
		this.butGraph = new JButton (Methods.getLanguageText(XMLIdentifier.VIEW_GRAPH_BUTTON_TEXT));
		this.butTable = new JButton (Methods.getLanguageText(XMLIdentifier.VIEW_TABLE_BUTTON_TEXT));
		this.butManagePatients = new JButton(Methods.getLanguageText(XMLIdentifier.MANAGE_PATIENTS_BUTTON_TEXT));
		this.butSettings = new JButton (Methods.getLanguageText(XMLIdentifier.SETTINGS_BUTTON_TEXT));
		this.butExit = new JButton (Methods.getLanguageText(XMLIdentifier.EXIT_BUTTON_TEXT));
		ArrayList<JLabel> listNumbers = new ArrayList<JLabel>();
		
		//Add to array
		this.listButtons.add(this.butNew);
		this.listButtons.add(this.butGraph);
		this.listButtons.add(this.butTable);
		this.listButtons.add(this.butManagePatients);
		this.listButtons.add(this.butSettings);
		this.listButtons.add(this.butExit);
		
		//Properties
		this.panelButtons.setLayout(new GridBagLayout());
		this.panelButtons.setOpaque(false);
		this.butNew.setActionCommand(this.NEW_LOG);
		this.butGraph.setActionCommand(this.GRAPH);
		this.butTable.setActionCommand(this.TABLE);
		this.butManagePatients.setActionCommand(this.MANAGE_PATIENTS);
		this.butSettings.setActionCommand(this.SETTINGS);
		this.butExit.setActionCommand(this.EXIT);
		this.butNew.setToolTipText(Methods.getLanguageText(XMLIdentifier.NEW_ENTRY_BUTTON_TOOLTIP));
		this.butGraph.setToolTipText(Methods.getLanguageText(XMLIdentifier.VIEW_GRAPH_BUTTON_TOOLTIP));
		this.butTable.setToolTipText(Methods.getLanguageText(XMLIdentifier.VIEW_TABLE_BUTTON_TOOLTIP));
		this.butManagePatients.setToolTipText(Methods.getLanguageText(XMLIdentifier.MANAGE_PATIENTS_BUTTON_TOOLTIP));
		this.butSettings.setToolTipText(Methods.getLanguageText(XMLIdentifier.SETTINGS_BUTTON_TOOLTIP));
		for (int i=0; i<this.listButtons.size(); i++)
		{
			this.listButtons.get(i).setFont(Constants.FONT_GENERAL);
			this.listButtons.get(i).addActionListener(this);
			this.listButtons.get(i).addKeyListener(this.keyListener);
			listNumbers.add(new JLabel(Integer.toString(i+1) + "."));
			listNumbers.get(i).setFont(Constants.FONT_GENERAL_BOLD);
			listNumbers.get(i).setHorizontalAlignment(SwingConstants.LEFT);
		}
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.fill = GridBagConstraints.BOTH;
		c.insets = Constants.INSETS_TOP_COMPONENT;
		Gbm.nextGridColumn(c);
		this.panelButtons.add(this.butNew, c);				//New Entry
		c.insets = Constants.INSETS_GENERAL;
		Gbm.newGridLine(c);
		Gbm.nextGridColumn(c);
		this.panelButtons.add(this.butGraph, c);			//View Graph
		Gbm.newGridLine(c);
		Gbm.nextGridColumn(c);
		this.panelButtons.add(this.butTable, c);			//View Table
		Gbm.newGridLine(c);
		Gbm.nextGridColumn(c);
		this.panelButtons.add(this.butManagePatients, c);	//Manage Patients
		Gbm.newGridLine(c);
		Gbm.nextGridColumn(c);
		this.panelButtons.add(this.butSettings, c);			//Settings
		Gbm.newGridLine(c);
		Gbm.nextGridColumn(c);
		c.fill = GridBagConstraints.NONE;
		c.insets = Constants.INSETS_TOP_COMPONENT;
		this.panelButtons.add(this.butExit, c);				//Exit
		
		//Display numbers besides buttons
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_TOP_COMPONENT_MORE_LEFT;
		for (int i=0; i<listNumbers.size(); i++)
		{
			this.panelButtons.add(listNumbers.get(i), c);
			c.insets = Constants.INSETS_GENERAL_MORE_LEFT;
			Gbm.newGridLine(c);
		}
	}
	
	private void initPanelCenter()
	{
		//Initialization
		this.panelCenter = new JPanel();
		GridBagConstraints c = new GridBagConstraints();
		this.initPanelTitle();
		this.initPanelButtons();
		
		//Properties
		this.panelCenter.setLayout(new GridBagLayout());
		this.panelCenter.setOpaque(false);
		
		//Add to panel
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = Constants.INSETS_TITLE;
		this.panelCenter.add(this.panelTitle, c);			//Title
		c.insets = Constants.INSETS_GENERAL;
		this.panelCenter.add(this.panelButtons, c);			//Buttons
		
		//Initialize JScrollPane
		this.scrollPanelCenter =  new JScrollPane(this.panelCenter, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.scrollPanelCenter.getViewport().setOpaque(false);
		this.scrollPanelCenter.setOpaque(false);
		this.scrollPanelCenter.getVerticalScrollBar().setUnitIncrement(Constants.SCROLL_SPEED);
		this.scrollPanelCenter.getHorizontalScrollBar().setUnitIncrement(Constants.SCROLL_SPEED);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	//Interfaces
	private KeyListener keyListener = new KeyListener()
			{
				@Override
				public void keyPressed(KeyEvent e)
				{
					int keyCode = e.getKeyCode();
					
					if (keyCode == KeyEvent.VK_1 || keyCode == KeyEvent.VK_NUMPAD1)
					{
						//Log new
						MainFrame.changePanel(new EntryLog());
					}
					else if (keyCode == KeyEvent.VK_2 || keyCode == KeyEvent.VK_NUMPAD2)
					{
						//Graph
						MainFrame.changePanel(new GraphPanel());
					}
					else if (keyCode == KeyEvent.VK_3 || keyCode == KeyEvent.VK_NUMPAD3)
					{
						//Table
						MainFrame.changePanel(new TableScreen());
					}
					else if (keyCode == KeyEvent.VK_4 || keyCode == KeyEvent.VK_NUMPAD4)
					{
						//Manage Patients
						MainFrame.changePanel(new PatientDataManagePanel());
					}
					else if (keyCode == KeyEvent.VK_5 || keyCode == KeyEvent.VK_NUMPAD5)
					{
						//Settings
						MainFrame.changePanel(new SettingsPanel());
					}
					else if (keyCode == KeyEvent.VK_6 || keyCode == KeyEvent.VK_NUMPAD6)
					{
						System.exit(0);
					}
				}
				@Override
				public void keyReleased(KeyEvent e) 
				{
					
				}
				@Override
				public void keyTyped(KeyEvent e) 
				{
					
				}
			};
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		switch(e.getActionCommand())
		{
			case NEW_LOG:
				MainFrame.changePanel(new EntryLog());
				break;
				
			case GRAPH:
				MainFrame.changePanel(new GraphPanel());
				break;
				
			case TABLE:
				MainFrame.changePanel(new TableScreen());
				break;
				
			case MANAGE_PATIENTS:
				MainFrame.changePanel(new PatientDataManagePanel());
				break;
				
			case SETTINGS:
				MainFrame.changePanel(new SettingsPanel());
				break;
				
			case EXIT:
				System.exit(0);
				break;
		}
	}
	
	public static void main(String args[])
	{
		JFrame frame = new JFrame();
		frame.add(new EntryLog());
		frame.pack();
		frame.setVisible(true);
	}
}
