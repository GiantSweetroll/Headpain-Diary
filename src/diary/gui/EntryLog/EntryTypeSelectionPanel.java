package diary.gui.EntryLog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import diary.ImageTextButton;
import diary.constants.Constants;
import diary.constants.Globals;
import diary.constants.ImageConstants;
import diary.constants.XMLIdentifier;
import diary.gui.MainFrame;
import diary.methods.Methods;

public class EntryTypeSelectionPanel extends JPanel implements ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7031573286167719518L;

	private JButton butSingle, butMulti, butBack;
	private JLabel labEntryTypeTitle;
	private JPanel panelCenter, panelBelow, panelButtons;
	
	//Constants
	private final String SINGLE = "single";
	private final String MULTI = "multi";
	private final String BACK = "back";
	
	//Constructors
	public EntryTypeSelectionPanel()
	{
		this.init();
	}
	
	//Methods
	private void init()
	{
		//Initialization
		this.initPanelBelow();
		this.initPanelCenter();
		
		//Properties
		this.setLayout(new BorderLayout());
		this.setBackground(Constants.COLOR_MAIN_MENU_BACKGROUND);
		
		//Add to panel
		this.add(this.panelCenter, BorderLayout.CENTER);
		this.add(this.panelBelow, BorderLayout.SOUTH);
	}
	private void initPanelBelow()
	{
		//Initialization
		this.panelBelow = new JPanel();
		this.butBack = new JButton(Methods.getLanguageText(XMLIdentifier.BACK_TEXT));
		
		//Properties
		this.panelBelow.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.panelBelow.setOpaque(false);
		this.butBack.setActionCommand(this.BACK);
		this.butBack.addActionListener(this);
		this.butBack.setForeground(Color.WHITE);
		this.butBack.setBackground(Constants.COLOR_MAIN_MENU_BUTTONS);
		
		//Add to panel
		this.panelBelow.add(this.butBack);
	}
	private void initPanelCenter()
	{
		//Initialization
		this.panelCenter = new JPanel();
		this.labEntryTypeTitle = new JLabel(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_TYPE_SELECTION_TEXT), SwingConstants.CENTER);
		this.initPanelButtons();
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
	//	this.panelCenter.setLayout(new BoxLayout(this.panelCenter, BoxLayout.Y_AXIS));
		this.panelCenter.setLayout(new GridBagLayout());
		this.panelCenter.setOpaque(false);
		this.panelButtons.setAlignmentX(CENTER_ALIGNMENT);
		this.labEntryTypeTitle.setAlignmentX(CENTER_ALIGNMENT);
		this.labEntryTypeTitle.setFont(Constants.FONT_SUB_TITLE);
		this.labEntryTypeTitle.setForeground(Color.WHITE);
		
		//Add to panel
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = Constants.INSETS_TITLE;
		this.panelCenter.add(this.labEntryTypeTitle, c);
		c.insets = Constants.INSETS_GENERAL;
		this.panelCenter.add(this.panelButtons);
	}
	private void initPanelButtons()
	{
		//Initialization
		this.panelButtons = new JPanel()
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1324024801522805199L;

			@Override
			public Dimension getMaximumSize()			//Overriden to prevent height from being resized when component resizes
			{
				Dimension original = super.getMaximumSize();
				
				return new Dimension(original.width, super.getPreferredSize().height);
			}
		};
		List<ImageIcon> images = new ArrayList<ImageIcon>();
		for (int i=0; i<3; i++)
		{
			ImageIcon img = new ImageIcon(ImageConstants.NEW_ENTRY);
			img = Methods.resizeImageByRatio(img, Methods.getPercentage(img, Constants.BUTTON_IMAGE_SIZE_RATIO));
			images.add(img);
		}
		ImageIcon img = new ImageIcon(ImageConstants.NEW_ENTRY);
		img = Methods.resizeImageByRatio(img, Methods.getPercentage(img, Constants.BUTTON_IMAGE_SIZE_RATIO));
		this.butSingle = new ImageTextButton(img, Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_TYPE_SINGLE_BUTTON_TEXT));
		this.butMulti = new ImageTextButton(images, Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_TYPE_MULTI_BUTTON_TEXT));
		
		//Properties
		this.panelButtons.setLayout(new GridLayout(1, 0, 20, 20));
		this.panelButtons.setOpaque(false);
		this.panelButtons.setBorder(Methods.createTransparentBorder(20));
		this.butSingle.setBackground(Constants.COLOR_MAIN_MENU_BUTTONS);
		this.butSingle.setActionCommand(this.SINGLE);
		this.butSingle.addActionListener(this);
		this.butMulti.setBackground(Constants.COLOR_MAIN_MENU_BUTTONS);
		this.butMulti.setActionCommand(this.MULTI);
		this.butMulti.addActionListener(this);
		
		//Add to panel
		this.panelButtons.add(this.butSingle);
		this.panelButtons.add(this.butMulti);
	}
	
	//Interfaces
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		switch(e.getActionCommand())
		{
			case SINGLE:
				Globals.ENTRY_LOG.resetDefaults();
				Globals.ENTRY_LOG.setToSingleEntryMode();
				MainFrame.changePanel(Globals.ENTRY_LOG);
				break;
				
			case MULTI:
				Globals.ENTRY_LOG.resetDefaults();
				MainFrame.changePanel(Globals.ENTRY_LOG_MULTI_DATE_RANGE_SELECT);
				break;
				
			case BACK:
				MainFrame.changePanel(Globals.MAIN_MENU);
				break;
		}
	}
}
