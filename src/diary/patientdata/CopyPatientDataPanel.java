package diary.patientdata;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import diary.constants.Constants;
import diary.constants.XMLIdentifier;
import diary.gui.DateRangePanel;
import diary.methods.Methods;
import giantsweetroll.gui.swing.Gbm;

public class CopyPatientDataPanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2881333545176265281L;
	
	private JRadioButton radAllData, radDateRange;
	private ButtonGroup group;
	private DateRangePanel dateRangePanel;
	
	//Constructor
	public CopyPatientDataPanel()
	{
		this.init();
	}
	
	//Create GUI
	private void init()
	{
		//Initialization
		this.radAllData = new JRadioButton(Methods.getLanguageText(XMLIdentifier.ALL_DATA_TEXT));
		this.radDateRange = new JRadioButton();
		this.dateRangePanel = new DateRangePanel();
		this.group = new ButtonGroup();
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		this.setLayout(new GridBagLayout());
		this.setBackground(Constants.COLOR_MAIN_MENU_BACKGROUND);
		this.radAllData.setForeground(Color.white);
		this.radAllData.setOpaque(false);
		this.radDateRange.setForeground(Color.WHITE);
		this.radDateRange.setOpaque(false);
		this.group.add(this.radAllData);
		this.group.add(this.radDateRange);
		this.radAllData.setSelected(true);
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.gridwidth = 2;
		this.add(this.radAllData, c);				//All Data Radio Button
		Gbm.newGridLine(c);
		c.gridwidth = 1;
		this.add(this.radDateRange, c);				//Date Range Radio Button
		Gbm.nextGridColumn(c);
		this.add(this.dateRangePanel, c);			//Date Range Panel
	}

	//Public Methods
	public boolean allDataSelected()
	{
		return this.radAllData.isSelected();
	}
	public boolean specificDateRangeSelected()
	{
		return this.radDateRange.isSelected();
	}
	public DateRangePanel getDateRangePanel()
	{
		return this.dateRangePanel;
	}
}