package diary.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import diary.ImageTextButton;
import diary.constants.Constants;
import diary.constants.Globals;
import diary.constants.ImageConstants;
import diary.constants.PanelName;
import diary.constants.XMLIdentifier;
import diary.interfaces.LanguageListener;
import diary.methods.Methods;
import giantsweetroll.ImageManager;
import giantsweetroll.gui.swing.ScrollPaneManager;

public class MainMenu extends JPanel implements ActionListener, LanguageListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3475768587288928741L;
	
	private JLabel labLogo, labCreatedBy, labName, labSupport;
	private List<JLabel> supportersList;
	private JButton butSettings, butExit, butManagePatients;
	private ImageTextButton butNewEntry, butGraph, butTable;
	private JPanel panelMainButtons, panelSupport, panelAuthor, panelBelow, panelBelowLeft, panelBelowRight, panelBelowCenter, panelCenter, panelCenterCenter, panelCenterBelow, panelMain;
	private JScrollPane scroll;
	
	//Constants
	private final String NEW_ENTRY = "new entry";
	private final String VIEW_GRAPH = "view graph";
	private final String VIEW_TABLE = "view table";
	private final String SETTINGS = "settings";
	private final String EXIT = "exit";
	private final String MANAGE_PATIENTS = "manage patients";
//	private final Dimension IMAGE_BUTTONS_SIZE = new Dimension(200, 180);
//	private final Dimension IMAGE_SUPPORTERS_SIZE = new Dimension(120, 100);
//	private final Dimension IMAGE_LOGO_SIZE = new Dimension(320, 280);
	
	//Constructors
	public MainMenu()
	{
		this.init();
	}
	
	//Methods
	private void init()
	{
		//Initialization
		this.initPanelMain();
		this.scroll = ScrollPaneManager.generateDefaultScrollPane(this.panelMain, 10, 10);
		
		//Properties
		this.setLayout(new BorderLayout());
		this.setOpaque(false);
		this.scroll.getVerticalScrollBar().setUnitIncrement(10);
		this.scroll.getHorizontalScrollBar().setUnitIncrement(10);
		this.scroll.setBorder(null);
		
		//Add to panel
		this.add(this.scroll, BorderLayout.CENTER);
	}
	private void initPanelMain()
	{
		//Initialization
		this.initPanelCenter();
		this.initPanelBelow();
		this.panelMain = new JPanel();
		
		//Properties
		this.panelMain.setLayout(new BorderLayout());
		this.panelMain.setBackground(Constants.COLOR_MAIN_MENU_BACKGROUND);
		
		//Add to panel
		this.panelMain.add(this.panelCenter, BorderLayout.CENTER);
		this.panelMain.add(this.panelBelow, BorderLayout.SOUTH);
	}
	private void initPanelCenter()
	{
		//Initialization
		this.panelCenter = new JPanel();
		this.initPanelCenterCenter();
		this.initPanelCenterBelow();
		
		//Properties
		this.panelCenter.setLayout(new BorderLayout());
		this.panelCenter.setOpaque(false);
		
		//Add to panel
		this.panelCenter.add(this.panelCenterCenter, BorderLayout.CENTER);
		this.panelCenter.add(this.panelCenterBelow, BorderLayout.SOUTH);
	}
	private void initPanelCenterBelow()
	{
		//Initialization
		this.panelCenterBelow = new JPanel();
		this.initPanelSupport();
		this.initPanelAuthor();
		this.labSupport = new JLabel(Methods.getLanguageText(XMLIdentifier.SUPPORTED_BY_TEXT) + ":");
		
		//Properties
		this.panelCenterBelow.setLayout(new BoxLayout(this.panelCenterBelow, BoxLayout.Y_AXIS));
		this.panelCenterBelow.setOpaque(false);
		this.panelSupport.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.labSupport.setFont(Constants.FONT_GENERAL);
		this.labSupport.setForeground(Color.WHITE);
		this.labSupport.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		//Add to panel
		this.panelCenterBelow.add(this.panelAuthor);
		this.panelCenterBelow.add(this.labSupport);
		this.panelCenterBelow.add(this.panelSupport);
	}
	private void initPanelCenterCenter()
	{
		//Initialization
		this.panelCenterCenter = new JPanel();
		this.initPanelMainButtons();
		ImageIcon image = ImageManager.getImageIcon(ImageConstants.LOGO);
		this.labLogo = new JLabel(Methods.resizeImageByRatio
									(image, Methods.getPercentage
												(image, Methods.getPercentageValue
															//(Constants.SCREENSIZE.width, 48))));
															(Constants.SCREENSIZE.width, 15))));
//		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		this.panelCenterCenter.setLayout(new BoxLayout(this.panelCenterCenter, BoxLayout.Y_AXIS));
//		this.panelCenterCenter.setLayout(new GridBagLayout());
		this.panelCenterCenter.setOpaque(false);
		this.panelMainButtons.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.labLogo.setFont(Constants.FONT_GENERAL);
		this.labLogo.setForeground(Color.WHITE);
		this.labLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		//Add to panel
	//	c.gridwidth = GridBagConstraints.REMAINDER;
		this.panelCenterCenter.add(this.labLogo);
		this.panelCenterCenter.add(this.panelMainButtons);
	}
	private void initPanelAuthor()
	{
		//Initialization
		this.panelAuthor = new JPanel();
		this.labCreatedBy = new JLabel(Methods.getLanguageText(XMLIdentifier.AUTHOR_TEXT) + ":");
		this.labName = new JLabel ("Gardyan P Akbar");
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		this.panelAuthor.setLayout(new GridBagLayout());
		this.panelAuthor.setOpaque(false);
//		this.panelAuthor.setBorder(BorderFactory.createLineBorder(new Color (0, 0, 0, 0), 10));			//Create empty/transparent border, to serve as padding
		this.labCreatedBy.setFont(Constants.FONT_GENERAL);
		this.labCreatedBy.setForeground(Color.WHITE);
		this.labCreatedBy.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.labName.setFont(Constants.FONT_GENERAL_A_BIT_BIGGER);
		this.labName.setForeground(Color.WHITE);
		this.labName.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		//Add to panel
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = Constants.INSETS_TOP_COMPONENT;
		this.panelAuthor.add(this.labCreatedBy, c);
		c.insets = new Insets (Constants.INSETS_BASE, Constants.INSETS_TITLE.left, Constants.INSETS_TITLE.bottom, Constants.INSETS_TITLE.right);
		this.panelAuthor.add(this.labName, c);
	}
	private void initPanelSupport()
	{
		//Initialization
		this.panelSupport = new JPanel();
		this.supportersList = new ArrayList<JLabel>();
		
		//Prepare JLabels
		ImageIcon image = ImageManager.getImageIcon(ImageConstants.FKUI);
//		image = Methods.resizeImageByRatio(image, 30);
///		image = Methods.resizeImageByRatio(image, Methods.getPercentage(image, Methods.getPercentageValue(Constants.SCREENSIZE.width, 28)));
		image = Methods.resizeImageByRatio(image, Methods.getPercentage(image, Methods.getPercentageValue(Constants.SCREENSIZE.width, 15)));
		this.supportersList.add(new JLabel(image));
		image = ImageManager.getImageIcon(ImageConstants.RSCM);
//		image = Methods.resizeImageByRatio(image, 10);
		image = Methods.resizeImageByRatio(image, Methods.getPercentage(image, Methods.getPercentageValue(Constants.SCREENSIZE.width, 9)));
		this.supportersList.add(new JLabel(image));
		image = ImageManager.getImageIcon(ImageConstants.MEDICAL_MEDIA);
//		image = Methods.resizeImageByRatio(image, 20);
		image = Methods.resizeImageByRatio(image, Methods.getPercentage(image, Methods.getPercentageValue(Constants.SCREENSIZE.width, 9)));
		this.supportersList.add(new JLabel(image));	
	
		//Properties
		this.panelSupport.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.panelSupport.setOpaque(false);
		
		//Add to panel
		for (JLabel label : this.supportersList)
		{
			this.panelSupport.add(label);
		}
	}
/*	private void initButton(JButton button, ImageIcon image, String text)
	{
	//	image = Methods.resizeImageByRatio(image, Methods.getPercentage(image, Methods.getPercentageValue(Constants.SCREENSIZE.width, 8)));
		image = Methods.resizeImageByRatio(image, Methods.getPercentage(image, Constants.BUTTON_IMAGE_SIZE_RATIO));
		ImageTextPanel panelImage = new ImageTextPanel(image, text);
		panelImage.getTextLabel().setForeground(Color.WHITE);
		panelImage.getTextLabel().setFont(Constants.FONT_SUB_TITLE);
		button.add(panelImage);
	}		*/
	private void initPanelMainButtons()
	{
		//Initialization
		this.panelMainButtons = new JPanel()
				{
					/**
					 * 
					 */
					private static final long serialVersionUID = 1324024801522805199L;

					@Override
					public Dimension getMaximumSize()			//Overridden to prevent height from being resized when component resizes
					{
						Dimension original = super.getMaximumSize();
						
						return new Dimension(original.width, super.getPreferredSize().height);
					}
				};
		ImageIcon image = ImageManager.getImageIcon(ImageConstants.NEW_ENTRY);
		this.butNewEntry = new ImageTextButton(Methods.resizeImageByRatio(image, Methods.getPercentage(image, Constants.BUTTON_IMAGE_SIZE_RATIO)),
												Methods.getLanguageText(XMLIdentifier.NEW_ENTRY_BUTTON_TEXT),
												Constants.FONT_SUB_TITLE,
												Color.WHITE);
		image = ImageManager.getImageIcon(ImageConstants.VIEW_GRAPH);
		this.butGraph = new ImageTextButton(Methods.resizeImageByRatio(image, Methods.getPercentage(image, Constants.BUTTON_IMAGE_SIZE_RATIO)),
												Methods.getLanguageText(XMLIdentifier.VIEW_GRAPH_BUTTON_TEXT),
												Constants.FONT_SUB_TITLE,
												Color.WHITE);
		image = ImageManager.getImageIcon(ImageConstants.VIEW_TABLE);
		this.butTable = new ImageTextButton(Methods.resizeImageByRatio(image, Methods.getPercentage(image, Constants.BUTTON_IMAGE_SIZE_RATIO)),
												Methods.getLanguageText(XMLIdentifier.VIEW_TABLE_BUTTON_TEXT),
												Constants.FONT_SUB_TITLE,
												Color.WHITE);
		
		//Properties
		this.panelMainButtons.setLayout(new GridLayout(1, 0, 20, 20));
		this.panelMainButtons.setOpaque(false);
		this.panelMainButtons.setBorder(Methods.createTransparentBorder(20));			//Create empty/transparent border, to serve as padding
		this.butNewEntry.setActionCommand(this.NEW_ENTRY);
		this.butNewEntry.addActionListener(this);
		this.butNewEntry.setBackground(Constants.COLOR_MAIN_MENU_BUTTONS);
		this.butNewEntry.setToolTipText(Methods.getLanguageText(XMLIdentifier.NEW_ENTRY_BUTTON_TOOLTIP));
		this.butNewEntry.setMnemonic(KeyEvent.VK_N);
		this.butGraph.setActionCommand(this.VIEW_GRAPH);
		this.butGraph.addActionListener(this);
		this.butGraph.setBackground(Constants.COLOR_MAIN_MENU_BUTTONS);
		this.butGraph.setToolTipText(Methods.getLanguageText(XMLIdentifier.VIEW_GRAPH_BUTTON_TOOLTIP));
		this.butGraph.setMnemonic(KeyEvent.VK_G);
		this.butTable.setActionCommand(this.VIEW_TABLE);
		this.butTable.addActionListener(this);
		this.butTable.setBackground(Constants.COLOR_MAIN_MENU_BUTTONS);
		this.butTable.setToolTipText(Methods.getLanguageText(XMLIdentifier.VIEW_TABLE_BUTTON_TOOLTIP));
		this.butTable.setMnemonic(KeyEvent.VK_T);
		
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
		this.butSettings.setMnemonic(KeyEvent.VK_S);
		
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
		this.butExit.setFont(Constants.FONT_GENERAL);
		this.butExit.setActionCommand(this.EXIT);
		this.butExit.addActionListener(this);
		this.butExit.setBackground(Constants.COLOR_MAIN_MENU_BUTTONS);
		this.butExit.setForeground(Color.WHITE);
		this.butExit.setMnemonic(KeyEvent.VK_E);
		
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
		this.butManagePatients.setMnemonic(KeyEvent.VK_M);
		
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
	//		this.ENTRY_LOG.resetDefaults();
	//		MainFrame.changePanel(Globals.ENTRY_LOG);
		//	MainFrame.changePanel(Globals.ENTRY_LOG_TYPE_SELECTION);
			
			Globals.ENTRY_LOG.resetDefaults();
			Globals.MAIN_FRAME.changePanel(PanelName.ENTRY_LOG);
			break;
			
		case VIEW_GRAPH:
	//		Globals.GRAPH_FILTER_PANEL.refresh(Globals.GRAPH_PANEL.getActivePatientPanel().getSelectedPatientData());
	//		MainFrame.changePanel(Globals.GRAPH_PANEL);
			
			Globals.GRAPH_FILTER_PANEL.refresh(Globals.GRAPH_PANEL.getActivePatientPanel().getSelectedPatientData());
			Globals.MAIN_FRAME.changePanel(PanelName.GRAPH_PANEL);
			break;
			
		case VIEW_TABLE:
		//	MainFrame.changePanel(Globals.PAIN_TABLE);
			Globals.MAIN_FRAME.changePanel(PanelName.PAIN_TABLE);
			break;
			
		case MANAGE_PATIENTS:
		//	MainFrame.changePanel(Globals.MANAGE_PATIENTS_PANEL);
			Globals.MAIN_FRAME.changePanel(PanelName.MANAGE_PATIENTS_PANEL);
			break;
			
		case SETTINGS:
	//		MainFrame.changePanel(Globals.SETTINGS_PANEL);
		Globals.MAIN_FRAME.changePanel(PanelName.SETTING_PANEL);
			break;
			
		case EXIT:
			System.exit(0);
			break;		
		}
	}
	
	@Override
	public void revalidateLanguage()
	{
		this.butNewEntry.setText(Methods.getLanguageText(XMLIdentifier.NEW_ENTRY_BUTTON_TEXT));
		this.butNewEntry.setToolTipText(Methods.getLanguageText(XMLIdentifier.NEW_ENTRY_BUTTON_TOOLTIP));
		this.butGraph.setText(Methods.getLanguageText(XMLIdentifier.VIEW_GRAPH_BUTTON_TEXT));
		this.butGraph.setToolTipText(Methods.getLanguageText(XMLIdentifier.VIEW_GRAPH_BUTTON_TOOLTIP));
		this.butTable.setText(Methods.getLanguageText(XMLIdentifier.VIEW_TABLE_BUTTON_TEXT));
		this.butTable.setToolTipText(Methods.getLanguageText(XMLIdentifier.VIEW_TABLE_BUTTON_TOOLTIP));
		this.labCreatedBy.setText(Methods.getLanguageText(XMLIdentifier.AUTHOR_TEXT));
		this.labSupport.setText(Methods.getLanguageText(XMLIdentifier.SUPPORTED_BY_TEXT));
		this.butSettings.setText(Methods.getLanguageText(XMLIdentifier.SETTINGS_BUTTON_TEXT));
		this.butSettings.setToolTipText(Methods.getLanguageText(XMLIdentifier.SETTINGS_BUTTON_TOOLTIP));
		this.butManagePatients.setText(Methods.getLanguageText(XMLIdentifier.MANAGE_PATIENTS_BUTTON_TEXT));
		this.butManagePatients.setToolTipText(Methods.getLanguageText(XMLIdentifier.MANAGE_PATIENTS_BUTTON_TOOLTIP));
		this.butExit.setText(Methods.getLanguageText(XMLIdentifier.EXIT_BUTTON_TEXT));
	}
}
