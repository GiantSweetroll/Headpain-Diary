package diary.gui.EntryLog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import diary.constants.Constants;
import diary.constants.Globals;
import diary.constants.XMLIdentifier;
import diary.gui.DateRangePanel;
import diary.gui.MainFrame;
import diary.methods.Methods;
import giantsweetroll.date.Date;
import giantsweetroll.gui.swing.GSPanel;
import giantsweetroll.gui.swing.Gbm;

public class EntryLogMulti extends JPanel implements ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6355641843914398727L;
	
	private JLabel labTitle;
	private DateRangePanel dateRange;
	private JButton butNext, butBack;
	private JPanel panelCenter, panelBelow;
	
	//Constants
	private final String BACK="back", NEXT = "next";

	//Constructors
	public EntryLogMulti()
	{
		this.init();
	}
	
	//Create GUI
	private void init()
	{
		this.initPanelBelow();
		this.initPanelCenter();
		
		//Properties
		this.setLayout(new BorderLayout());
		this.setBackground(Constants.COLOR_MAIN_MENU_BACKGROUND);
		
		//add to panel
		this.add(this.panelCenter, BorderLayout.CENTER);
		this.add(this.panelBelow, BorderLayout.SOUTH);
	}
	private void initPanelBelow()
	{
		//Initialization
		this.panelBelow = new GSPanel(new FlowLayout(FlowLayout.CENTER), false);
		this.butBack = new JButton(Methods.getLanguageText(XMLIdentifier.BACK_TEXT));
		
		//Properties
		this.butBack.setActionCommand(this.BACK);
		this.butBack.addActionListener(this);
		Methods.setButtonToFollowMainMenuLayout(this.butBack);
		
		//Add to panel
		this.panelBelow.add(this.butBack);
	}
	private void initPanelCenter()
	{
		//Initialization
		this.panelCenter = new GSPanel(new GridBagLayout(), false);
		this.labTitle = new JLabel(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_MULTI_DATE_RANGE_SELECT_TEXT), SwingConstants.CENTER);
		this.dateRange = new DateRangePanel();
		this.butNext = new JButton(Methods.getLanguageText(XMLIdentifier.NEXT_TEXT));
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		this.labTitle.setFont(Constants.FONT_SUB_TITLE);
		this.labTitle.setForeground(Color.white);
		Methods.setButtonToFollowMainMenuLayout(this.butNext);
		this.butNext.setActionCommand(this.NEXT);
		this.butNext.addActionListener(this);
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_TOP_COMPONENT;
		this.panelCenter.add(this.labTitle, c);
		Gbm.newGridLine(c);
		c.insets = Constants.INSETS_GENERAL;
		this.panelCenter.add(this.dateRange, c);
		Gbm.newGridLine(c);
		this.panelCenter.add(this.butNext, c);
	}
	
	//Methods
	public void resetDefault()
	{
		this.dateRange.autoSetDateRanges();
	}
	
	//Interfaces
	public void actionPerformed(ActionEvent e)
	{
		switch(e.getActionCommand())
		{
			case NEXT:
				LinkedHashMap<String, Date> map = this.dateRange.getDateRange();
				System.out.println(Date.getDaysDifference(map.get(DateRangePanel.FROM), map.get(DateRangePanel.TO), true));
				break;
				
			case BACK:
				MainFrame.changePanel(Globals.ENTRY_LOG_TYPE_SELECTION);
				this.resetDefault();
				break;
		}
	}
}
