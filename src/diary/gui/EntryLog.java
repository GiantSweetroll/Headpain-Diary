package diary.gui;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import diary.constants.Constants;
import giantsweetroll.gui.swing.Gbm;
import giantsweetroll.gui.swing.ScrollPaneManager;

public class EntryLog extends JPanel implements DocumentListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5981497184958769730L;
	
	private JPanel panelTitle;
	private JPanel panelCenter;
	private JPanel panelNyeriTypes;
	private JPanel panelBelow;
	private JPanel panelBelowLeft, panelBelowRight;
	
	private JScrollPane scrollCenter;
	private JScrollPane scrollNyeriTypes;
	
	private JLabel labTitle, labStartTime, labNyeriAmount, labActivity, labComments;
	
	private JFormattedTextField tfStartTime, tfNyeriAmount;
	private JTextField tfActivity;
	private JTextArea taComments;
	private JButton butBack, butFinish;
	
	protected EntryLog()
	{
		
	}
	
	//Initialization of GUI
	private void createAndShowGUI()
	{
		
	}
	private void initPanelTitle()
	{
		//Initialization
		this.panelTitle = new JPanel();
		this.labTitle = new JLabel(Constants.LANGUAGE.entryLogTitle);
		
		//Properties
		this.labTitle.setFont(Constants.FONT_TITLE);
		this.panelTitle.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.panelTitle.setOpaque(false);
		
		//add to panel
		this.panelTitle.add(this.labTitle);
	}
	private void initPanelCenter()
	{
		//Initialization
		this.panelCenter = new JPanel();
		GridBagConstraints c = new GridBagConstraints();
		this.labStartTime = new JLabel(Constants.LANGUAGE.entryLogStartTimeLabel, SwingConstants.RIGHT);
		this.tfStartTime = new JFormattedTextField(Constants.DATE_FORMAT);
		this.labNyeriAmount = new JLabel (Constants.LANGUAGE.entryLogHeadpainLocationAmountLabel, SwingConstants.RIGHT);
		this.tfNyeriAmount = new JFormattedTextField(Constants.AMOUNT_FORMAT);
		this.labActivity = new JLabel(Constants.LANGUAGE.entryLogActivityLabel, SwingConstants.RIGHT);
		this.tfActivity = new JTextField("", 20);
		this.labComments = new JLabel(Constants.LANGUAGE.entryLogCommentsLabel, SwingConstants.RIGHT);
		this.taComments = new JTextArea(20, 35);
		
		//Properties
		this.panelCenter.setLayout(new GridBagLayout());
		this.panelCenter.setOpaque(false);
		this.tfStartTime.setColumns(20);
		this.tfStartTime.setHorizontalAlignment(SwingConstants.CENTER);
		this.tfNyeriAmount.setColumns(5);
		this.tfNyeriAmount.setHorizontalAlignment(SwingConstants.CENTER);
		this.tfActivity.setHorizontalAlignment(SwingConstants.CENTER);
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_TOP_COMPONENT;
		c.fill = GridBagConstraints.BOTH;
		this.panelCenter.add(this.labStartTime, c);			//Start Time
		Gbm.nextGridColumn(c);
		this.panelCenter.add(this.tfStartTime, c);			//Start Time Text Field
		Gbm.newGridLine(c);
		this.panelCenter.add(this.labNyeriAmount, c);		//Amount of head pain
		Gbm.nextGridColumn(c);
		this.panelCenter.add(this.tfNyeriAmount, c);		//Amount of head pain text field
		
		
	}
	private void initScrollPanes()
	{
		//Initialization
		this.scrollCenter = ScrollPaneManager.generateDefaultScrollPane(this.panelCenter, Constants.SCROLL_SPEED, Constants.SCROLL_SPEED);
		this.scrollNyeriTypes = ScrollPaneManager.generateDefaultScrollPane(this.panelNyeriTypes, Constants.SCROLL_SPEED, Constants.SCROLL_SPEED);
	}
	
	//Interfaces
	@Override
	public void changedUpdate(DocumentEvent e) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertUpdate(DocumentEvent e) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeUpdate(DocumentEvent e) 
	{
		// TODO Auto-generated method stub
		
	}	
}
