package diary.gui.table;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import diary.constants.Constants;
import diary.constants.XMLIdentifier;
import diary.gui.DateRangePanel;
import diary.gui.MainFrame;
import diary.gui.MainMenu;
import diary.methods.FileOperation;
import giantsweetroll.gui.swing.Gbm;

public class TableScreen extends JPanel implements ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6429132267457024269L;
	
	private JPanel panelTop, panelTopLeft, panelBelowLeft, panelBelowCenter, panelBelowRight, panelBelow;
	private TablePanel table;
	private DateRangePanel panelDateRange;
	private JComboBox<String> comboFilter;
	private JTextField tfFilter;
	private JButton butFilter, butBack, butDelete, butSelect;
	private JLabel labFilter, labKeyword, labGuide;
	
	//Constants
	private final String FILTER = "filter";
	private final String BACK = "back";
	private final String DELETE = "delete";
	private final String SELECT = "select";
	
	public TableScreen()
	{
		this.init();
	}
	
	//Methods
	//Initialize GUI
	private void init()
	{
		//Initialization
		this.initPanelBelow();
		this.initPanelTop();
		
		//Properties
		this.setOpaque(false);
		this.setLayout(new BorderLayout());
		
		this.initTable();
		
		//Add to panel
		this.add(this.panelTop, BorderLayout.NORTH);
		this.add(this.panelBelow, BorderLayout.SOUTH);
	}
	private void initPanelBelowLeft()
	{
		//Initialization
		this.panelBelowLeft = new JPanel();
		this.butBack = new JButton(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.BACK_TEXT));
		
		//Properties
		this.panelBelowLeft.setOpaque(false);
		this.panelBelowLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.butBack.setActionCommand(this.BACK);
		this.butBack.setToolTipText(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.TABLE_TOOLTIPS_BUTTON_BACK_TEXT));
		this.butBack.addActionListener(this);
		
		//add to panel
		this.panelBelowLeft.add(this.butBack);
	}
	private void initPanelBelowCenter()
	{
		//Initialization
		this.panelBelowCenter = new JPanel();
		this.butDelete = new JButton(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.DELETE_TEXT));
		
		//Properties
		this.panelBelowCenter.setOpaque(false);
		this.panelBelowCenter.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.butDelete.setActionCommand(this.DELETE);
		this.butDelete.setToolTipText(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.TABLE_TOOLTIPS_BUTTON_DELETE_TEXT));
		this.butDelete.addActionListener(this);
		
		//add to panel
		this.panelBelowCenter.add(this.butDelete);
	}
	private void initPanelBelowRight()
	{
		//Initialization
		this.panelBelowRight = new JPanel();
		this.butSelect = new JButton(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SELECT_TEXT));
		
		//Properties
		this.panelBelowRight.setOpaque(false);
		this.panelBelowRight.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.butSelect.setActionCommand(this.SELECT);
		this.butSelect.setToolTipText(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.TABLE_TOOLTIPS_BUTTON_SELECT_TEXT));
		this.butSelect.addActionListener(this);
		
		//add to panel
		this.panelBelowRight.add(this.butSelect);
	}
	private void initPanelBelow()
	{
		//Initialization
		this.panelBelow = new JPanel();
		this.initPanelBelowCenter();
		this.initPanelBelowLeft();
		this.initPanelBelowRight();
		
		//Properties
		this.panelBelow.setOpaque(false);
		this.panelBelow.setLayout(new BorderLayout());
		
		//add to panel
		this.panelBelow.add(this.panelBelowLeft, BorderLayout.WEST);
		this.panelBelow.add(this.panelBelowCenter, BorderLayout.CENTER);
		this.panelBelow.add(this.panelBelowRight, BorderLayout.EAST);
	}
	private void initPanelTopLeft()
	{
		//Initialization
		this.panelTopLeft = new JPanel();
		this.labFilter = new JLabel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.TABLE_FILTER_BY_LABEL), SwingConstants.RIGHT);
		this.labKeyword = new JLabel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.TABLE_FILTER_KEYWORD_LABEL), SwingConstants.RIGHT);
		this.tfFilter = new JTextField("", 20);
		this.comboFilter = new JComboBox<String>(Constants.TABLE_FILTER_OPTIONS);
		this.butFilter = new JButton(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.FILTER_TEXT));
		this.labGuide = new JLabel ("<html> " + Constants.LANGUAGE.getTextMap().get(XMLIdentifier.NOTE_TEXT) + ": " + "<br/>" + Constants.LANGUAGE.getTextMap().get(XMLIdentifier.TABLE_FILTER_GUIDE_USAGE_TEXT) + "</html>");
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		this.panelTopLeft.setOpaque(false);
		this.panelTopLeft.setLayout(new GridBagLayout());
		this.labGuide.setFont(Constants.FONT_SMALL_NOTE);
		this.butFilter.addActionListener(this);
		this.butFilter.setActionCommand(this.FILTER);
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_TOP_COMPONENT;
		c.fill = GridBagConstraints.BOTH;
		this.panelTopLeft.add(this.labFilter, c);			//Filter By
		Gbm.nextGridColumn(c);
		this.panelTopLeft.add(this.comboFilter, c);			//Filter Options
		Gbm.newGridLine(c);
		c.insets = Constants.INSETS_GENERAL;
		this.panelTopLeft.add(this.labKeyword, c);			//Search Keyword
		Gbm.nextGridColumn(c);
		this.panelTopLeft.add(this.tfFilter, c);			//Search Keyword Text Field;
		Gbm.newGridLine(c);
		c.gridwidth = 2;
		this.panelTopLeft.add(this.labGuide, c);			//Small Note
		Gbm.newGridLine(c);
		c.fill = GridBagConstraints.NONE;
		c.insets = Constants.INSETS_TOP_COMPONENT;
		this.panelTopLeft.add(this.butFilter, c);			//Filter Button
	}
	private void initPanelTop()
	{
		//Initialization
		this.panelTop = new JPanel();
		this.panelDateRange = new DateRangePanel();
		this.initPanelTopLeft();
		
		//Properties
		this.panelTop.setOpaque(false);
		this.panelTop.setLayout(new BorderLayout());
		this.panelDateRange.dateFrom.autoSetDate();
		this.panelDateRange.dateTo.autoSetDate();
		
		//Add to panel
		this.panelTop.add(this.panelTopLeft, BorderLayout.WEST);
		this.panelTop.add(this.panelDateRange, BorderLayout.EAST);
	}
	//Other Methods
	private void initTable()
	{
		try
		{
			this.remove(this.table);
		}
		catch(NullPointerException ex) {};
		
		this.table = new TablePanel(FileOperation.getListOfEntries(this.panelDateRange.getDateRangeMap().get(DateRangePanel.FROM), this.panelDateRange.getDateRangeMap().get(DateRangePanel.TO)));
		this.add(this.table, BorderLayout.CENTER);
		
		this.revalidate();
		this.repaint();
	}
	
	//Interfaces
	@Override
	public void actionPerformed(ActionEvent e)
	{
		switch (e.getActionCommand())
		{
			case FILTER:
				this.initTable();
				break;
				
			case DELETE:
				break;
				
			case SELECT:
				break;
				
			case BACK:
				MainFrame.changePanel(new MainMenu());
				break;
		}
	}
}
