package diary.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import diary.constants.Constants;
import diary.constants.ImageConstants;
import diary.constants.XMLIdentifier;
import diary.gui.MainFrame;
import diary.gui.EntryLog.EntryLog;
import diary.gui.graphs.GraphPanel;
import diary.gui.settings.SettingsPanel;
import diary.gui.table.TableScreen;
import diary.methods.Methods;
import diary.patientdata.PatientDataManagePanel;
import giantsweetroll.ImageManager;
import giantsweetroll.gui.swing.Gbm;

public class MainMenu extends JPanel implements ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2572196485419295746L;

	private JLabel labLogo, labCreatedBy, labName, labSupport;
	private List<JLabel> supportersList;
	private JButton butNewEntry, butGraph, butTable, butSettings, butExit, butManagePatients;
	private JPanel panelMainButtons, panelSupport, panelBelow, panelBelowLeft, panelBelowRight, panelBelowCenter, panelCenter;
	
	//Constants
	private final String NEW_ENTRY = "new entry";
	private final String VIEW_GRAPH = "view graph";
	private final String VIEW_TABLE = "view table";
	private final String SETTINGS = "settings";
	private final String EXIT = "exit";
	private final String MANAGE_PATIENTS = "manage patients";
	
	//Constructors
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
		this.setBackground(Constants.COLOR_MAIN_MENU_BACKGROUND);
		
		//Add to panel
		this.add(this.panelCenter, BorderLayout.CENTER);
		this.add(this.panelBelow, BorderLayout.SOUTH);
	}
	private void initPanelCenter()
	{
		//Initialization
		this.panelCenter = new JPanel();
		this.initPanelSupport();
		this.initPanelMainButtons();
		this.labLogo = new JLabel(ImageManager.getImageIcon(ImageConstants.LOGO));
		this.labCreatedBy = new JLabel(Methods.getLanguageText(XMLIdentifier.AUTHOR_TEXT) + ":");
		this.labName = new JLabel ("Gardyan P Akbar");
		this.labSupport = new JLabel(Methods.getLanguageText(XMLIdentifier.SUPPORTED_BY_TEXT) + ":");
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		this.panelCenter.setLayout(new GridBagLayout());
		this.panelCenter.setOpaque(false);
		this.labLogo.setFont(Constants.FONT_GENERAL);
		this.labLogo.setForeground(Color.WHITE);
		this.labCreatedBy.setFont(Constants.FONT_GENERAL);
		this.labCreatedBy.setForeground(Color.WHITE);
		this.labName.setFont(Constants.FONT_GENERAL_A_BIT_BIGGER);
		this.labName.setForeground(Color.WHITE);
		this.labSupport.setFont(Constants.FONT_GENERAL);
		this.labSupport.setForeground(Color.WHITE);
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_TOP_COMPONENT;
		c.gridwidth = 100;
		this.panelCenter.add(this.labLogo, c);				//Logo
		Gbm.newGridLine(c);
		c.insets = Constants.INSETS_GENERAL;
		c.gridwidth = 1;
		this.panelCenter.add(this.panelMainButtons, c);		//Main Buttons Panel
		Gbm.newGridLine(c);
		c.insets = Constants.INSETS_TOP_COMPONENT;
		this.panelCenter.add(this.labCreatedBy, c);			//Created By
		Gbm.newGridLine(c);
		c.insets = Constants.INSETS_GENERAL;
		this.panelCenter.add(this.labName, c);				//Name
		Gbm.newGridLine(c);
		c.insets = Constants.INSETS_TOP_COMPONENT;
		this.panelCenter.add(this.labSupport, c);			//Supported By
		Gbm.newGridLine(c);
		c.insets = Constants.INSETS_GENERAL;
		this.panelCenter.add(this.panelSupport, c);			//Supported By Panel		
	}
	private void initPanelSupport()
	{
		//Initialization
		this.panelSupport = new JPanel();
		this.supportersList = new ArrayList<JLabel>();
		
		//Prepare JLabels
		this.supportersList.add(new JLabel(ImageManager.getImageIcon(ImageConstants.FKUI)));
		this.supportersList.add(new JLabel(ImageManager.getImageIcon(ImageConstants.RSCM)));
		this.supportersList.add(new JLabel(ImageManager.getImageIcon(ImageConstants.MEDICAL_MEDIA)));	
	
		//Properties
		this.panelSupport.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.panelSupport.setOpaque(false);
		
		//Add to panel
		for (JLabel label : this.supportersList)
		{
			this.panelSupport.add(label);
		}
	}
	private void initPanelMainButtons()
	{
		//Initialization
		this.panelMainButtons = new JPanel();
		this.butNewEntry = new JButton(ImageManager.getImageIcon(ImageConstants.NEW_ENTRY));
		this.butGraph = new JButton(ImageManager.getImageIcon(ImageConstants.VIEW_GRAPH));
		this.butTable = new JButton(ImageManager.getImageIcon(ImageConstants.VIEW_TABLE));
		
		//Properties
		this.panelMainButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.panelMainButtons.setOpaque(false);
		this.butNewEntry.setActionCommand(this.NEW_ENTRY);
		this.butNewEntry.addActionListener(this);
		this.butNewEntry.setToolTipText(Methods.getLanguageText(XMLIdentifier.NEW_ENTRY_BUTTON_TOOLTIP));
		this.butGraph.setActionCommand(this.VIEW_GRAPH);
		this.butGraph.addActionListener(this);
		this.butGraph.setToolTipText(Methods.getLanguageText(XMLIdentifier.VIEW_GRAPH_BUTTON_TOOLTIP));
		this.butTable.setActionCommand(this.VIEW_TABLE);
		this.butTable.addActionListener(this);
		this.butTable.setToolTipText(Methods.getLanguageText(XMLIdentifier.VIEW_TABLE_BUTTON_TOOLTIP));
		
		//Add to panel
		this.panelMainButtons.add(this.butNewEntry);
		this.panelMainButtons.add(this.butGraph);
		this.panelMainButtons.add(this.butTable);
	}
	private void initPanelBelowLeft()
	{
		//Initialization
		this.panelBelowLeft = new JPanel();
		this.butSettings = new JButton(Methods.getLanguageText(XMLIdentifier.SETTINGS_BUTTON_TEXT));
		
		//Properties
		this.panelBelowLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.panelBelowLeft.setOpaque(false);
		this.butSettings.setToolTipText(Methods.getLanguageText(XMLIdentifier.SETTINGS_BUTTON_TOOLTIP));
		this.butSettings.setActionCommand(this.SETTINGS);
		this.butSettings.addActionListener(this);
		this.butSettings.setFont(Constants.FONT_GENERAL);
		this.butSettings.setBackground(Constants.COLOR_MAIN_MENU_BUTTONS);
		this.butSettings.setForeground(Color.WHITE);
		
		//Add to panel
		this.panelBelowLeft.add(this.butSettings);
	}
	private void initPanelBelowRight()
	{
		//Initialization
		this.panelBelowRight = new JPanel();
		this.butExit = new JButton(Methods.getLanguageText(XMLIdentifier.EXIT_BUTTON_TEXT));
		
		//Properties
		this.panelBelowRight.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.panelBelowRight.setOpaque(false);
		this.butExit.setActionCommand(this.EXIT);
		this.butExit.addActionListener(this);
		this.butExit.setBackground(Constants.COLOR_MAIN_MENU_BUTTONS);
		this.butExit.setForeground(Color.WHITE);
		
		//Add to panel
		this.panelBelowRight.add(this.butExit);
	}
	private void initPanelBelowCenter()
	{
		//Initialization
		this.panelBelowCenter = new JPanel();
		this.butManagePatients = new JButton(Methods.getLanguageText(XMLIdentifier.MANAGE_PATIENTS_BUTTON_TEXT));
		
		//Properties
		this.panelBelowCenter.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.panelBelowCenter.setOpaque(false);
		this.butManagePatients.setToolTipText(Methods.getLanguageText(XMLIdentifier.MANAGE_PATIENTS_BUTTON_TOOLTIP));
		this.butManagePatients.setActionCommand(this.MANAGE_PATIENTS);
		this.butManagePatients.addActionListener(this);
		this.butManagePatients.setFont(Constants.FONT_GENERAL);
		this.butManagePatients.setBackground(Constants.COLOR_MAIN_MENU_BUTTONS);
		this.butManagePatients.setForeground(Color.WHITE);
		
		//Add to panel
		this.panelBelowCenter.add(this.butManagePatients);	
	}
	private void initPanelBelow()
	{
		//Initialization
		this.panelBelow = new JPanel();
		this.initPanelBelowCenter();
		this.initPanelBelowLeft();
		this.initPanelBelowRight();
		
		//Properties
		this.panelBelow.setLayout(new BorderLayout());
		this.panelBelow.setOpaque(false);
		
		//Add to panel
		this.panelBelow.add(this.panelBelowLeft, BorderLayout.WEST);
		this.panelBelow.add(this.panelBelowCenter, BorderLayout.CENTER);
		this.panelBelow.add(this.panelBelowRight, BorderLayout.EAST);
	}
	
	//Interfaces
	@Override
	public void actionPerformed(ActionEvent e)
	{
		switch (e.getActionCommand())
		{
		case NEW_ENTRY:
			MainFrame.changePanel(new EntryLog());
			break;
			
		case VIEW_GRAPH:
			MainFrame.changePanel(new GraphPanel());
			break;
			
		case VIEW_TABLE:
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
}
