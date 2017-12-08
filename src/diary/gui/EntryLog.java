package diary.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
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

public class EntryLog extends JPanel implements DocumentListener, ActionListener
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
	
	private JScrollPane scrollCenter, scrollComments;
	
	private JLabel labTitle, labStartTime, labNyeriAmount, labActivity, labComments;
	
	private JFormattedTextField tfStartTime, tfNyeriAmount;
	private JTextField tfActivity;
	private JTextArea taComments;
	private JButton butBack, butFinish;
	
	//Constants
	private final String BACK = "back";
	private final String FINISH = "finish";
	
	private int nyeriAmount;
	
	public EntryLog()
	{
		this.createAndShowGUI();
	}
	
	//Initialization of GUI
	private void createAndShowGUI()
	{
		//Initialize
		this.initPanelTitle();
		this.initPanelCenter();
		this.initPanelBelow();
		this.initScrollPanes();
		
		//properties
		this.setLayout(new BorderLayout());
		this.setOpaque(false);
		
		//add
		this.add(this.panelTitle, BorderLayout.NORTH);
		this.add(this.scrollCenter, BorderLayout.CENTER);
		this.add(this.panelBelow, BorderLayout.SOUTH);
		
		//Initialize Listeners
		this.configureListenersForMembers();
		
		this.revalidate();
		this.repaint();
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
		this.taComments = new JTextArea(10, 35);
		this.scrollComments = ScrollPaneManager.generateDefaultScrollPane(this.taComments, 10, 10);
		
		//Properties
		this.panelCenter.setLayout(new GridBagLayout());
		this.panelCenter.setOpaque(false);
		this.tfStartTime.setColumns(10);
		this.tfStartTime.setHorizontalAlignment(SwingConstants.CENTER);
		this.tfNyeriAmount.setColumns(5);
		this.tfNyeriAmount.setHorizontalAlignment(SwingConstants.CENTER);
		this.tfNyeriAmount.setText("1");
		this.tfActivity.setHorizontalAlignment(SwingConstants.CENTER);
		this.taComments.setBorder(this.tfActivity.getBorder());
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_TOP_COMPONENT;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 1;
		this.panelCenter.add(this.labStartTime, c);			//Start Time
		Gbm.nextGridColumn(c);
		this.panelCenter.add(this.tfStartTime, c);			//Start Time Text Field
		Gbm.newGridLine(c);
		this.panelCenter.add(this.labNyeriAmount, c);		//Amount of head pain
		Gbm.nextGridColumn(c);
		this.panelCenter.add(this.tfNyeriAmount, c);		//Amount of head pain text field
		Gbm.newGridLine(c);
		
		//insert headpain position panel here
		Gbm.newGridLine(c);
		this.panelCenter.add(this.labActivity, c);			//Activity
		c.gridwidth = 2;
		Gbm.nextGridColumn(c);
		this.panelCenter.add(this.tfActivity, c);			//Activity Text Field
		Gbm.newGridLine(c);
		c.gridwidth = 1;
		this.panelCenter.add(this.labComments, c);			//Comments
		Gbm.nextGridColumn(c);
		c.gridwidth = 3;
		this.panelCenter.add(this.scrollComments, c);		//Comments Text Area
		
	}
	private void initScrollPanes()
	{
		//Initialization
		this.scrollCenter = ScrollPaneManager.generateDefaultScrollPane(this.panelCenter, Constants.SCROLL_SPEED, Constants.SCROLL_SPEED);
	}
	private void initPanelBelowLeft()
	{
		//Initialization
		this.panelBelowLeft = new JPanel();
		this.butBack = new JButton(Constants.LANGUAGE.backText);
		
		//properties
		this.panelBelowLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		//add to panel
		this.panelBelowLeft.add(this.butBack);
	}
	private void initPanelBelowRight()
	{
		//Initialization
		this.panelBelowRight = new JPanel();
		this.butFinish = new JButton(Constants.LANGUAGE.finishText);
		
		//properties
		this.panelBelowRight.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		//add to panel
		this.panelBelowRight.add(this.butFinish);
	}
	private void initPanelBelow()
	{
		//Initialization
		this.panelBelow = new JPanel();
		this.initPanelBelowLeft();
		this.initPanelBelowRight();
		
		//Properties
		this.panelBelow.setLayout(new BorderLayout());
		
		//Add to panel
		this.panelBelow.add(this.panelBelowLeft, BorderLayout.WEST);
		this.panelBelow.add(this.panelBelowRight, BorderLayout.EAST);
	}
	private void configureListenersForMembers()
	{
//		this.tfNyeriAmount.getDocument().addDocumentListener(this);
		this.butBack.addActionListener(this);
		this.butBack.setActionCommand(this.BACK);
		this.butFinish.addActionListener(this);
		this.butFinish.setActionCommand(this.FINISH);
	}
	private void getNyeriAmount()
	{
		int amount = 1;
		try
		{
			if (this.tfNyeriAmount.getText().trim().equals(""))
			{
				amount = 1;
				this.tfNyeriAmount.setText("1");
			}
			else
			{
				amount = Integer.parseInt(this.tfNyeriAmount.getText().trim().replaceAll(",", ""));
				if (amount <= 0)
				{
					amount = 1;
					this.tfNyeriAmount.setText("1");
				}
			}
		}
		catch (NullPointerException ex)
		{
			amount = 1;
		}
		
		this.nyeriAmount = amount;
	}
	
	//Interfaces
	@Override
	public void changedUpdate(DocumentEvent e) 
	{
		this.getNyeriAmount();
	}

	@Override
	public void insertUpdate(DocumentEvent e) 
	{
		this.getNyeriAmount();
	}

	@Override
	public void removeUpdate(DocumentEvent e) 
	{
		this.getNyeriAmount();
	}	
	
	public void actionPerformed(ActionEvent e)
	{
		switch (e.getActionCommand())
		{
			case BACK:
				MainFrame.changePanel(new MainMenu());
				break;
				
			case FINISH:
				break;
		}
	}
	
	public static void main(String args[])
	{
		JFrame frame = new JFrame();
		frame.add(new EntryLog());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
