package diary.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import diary.constants.Constants;
import diary.constants.DateConstants;
import diary.constants.PainDataIdentifier;
import diary.constants.XMLIdentifier;
import diary.methods.DateOperation;
import giantsweetroll.date.DateManager;
import giantsweetroll.gui.swing.Gbm;

public class DatePanel extends JPanel implements ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1315020675324622006L;
	private JComboBox<String> comboDay, comboMonth, comboYear;
	private JButton butAuto, butDefault;
	private GridBagConstraints c;
	private LinkedHashMap<String, String> defaultMap;
	
	//Vectors
//	private VectorInt vecDay;
	
	//Constants
	private final String AUTO = "auto";
	private final String DEFAULT = "default";
	private final String YEAR = "year";
	private final String MONTH = "month";
	private final String DAY = "day";
	
	//Conditional
	private boolean enable;
	
	//Constructor
	public DatePanel(boolean enabled)
	{
		this.enable = enabled;
		this.init();
		this.enableComponents(enabled);
	}
	
	//Methods
	public void enableComponents(boolean enable)
	{
		this.comboMonth.setEnabled(enable);
		this.comboYear.setEnabled(enable);
		this.comboDay.setEnabled(enable);
		this.butAuto.setEnabled(enable);
		this.butDefault.setEnabled(enable);
	}
	private final void init()
	{
		//Initialization
		this.comboYear = new JComboBox<String>(DateOperation.getYearRangeStringReversed());
	//	this.comboYear = new JComboBox<String>(DateOperation.getYearRangeString());
		this.comboMonth = new JComboBox<String>(DateOperation.getMonthNameList().toArray(new String[12]));
		this.comboDay = new JComboBox<String>(DateOperation.getMaxDaysString(Byte.parseByte(Integer.toString(this.comboMonth.getSelectedIndex()+1)), Short.parseShort(this.comboYear.getSelectedItem().toString())));
		this.butAuto = new JButton(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.AUTO_TEXT));
		this.butDefault = new JButton(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.RESET_TEXT));
		this.c = new GridBagConstraints();
		this.defaultMap = new LinkedHashMap<String, String>();
		
		//Properties
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		this.comboYear.addActionListener(this.yearListener);
		this.comboMonth.addActionListener(this.monthListener);
		this.comboDay.setEnabled(this.enable);
		this.butAuto.addActionListener(this);
		this.butAuto.setActionCommand(this.AUTO);
		this.butAuto.setToolTipText(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.DATE_AUTO_BUTTON_TOOLTIP_TEXT));
		this.butDefault.addActionListener(this);
		this.butDefault.setActionCommand(this.DEFAULT);
		this.butDefault.setToolTipText(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.DATE_RESET_BUTTON_TOOLTIP_TEXT));
		this.comboDay.setBackground(Color.WHITE);
		this.comboMonth.setBackground(Color.WHITE);
		this.comboYear.setBackground(Color.WHITE);
		this.setAsDefaultDataThis();
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_GENERAL;
	//	this.vecDay = new VectorInt(c.gridx, c.gridy);
		this.add(this.comboDay, c);							//Day
		Gbm.nextGridColumn(c);
		this.add(this.comboMonth, c);						//Month
		Gbm.nextGridColumn(c);
		this.add(this.comboYear, c);						//Year
		Gbm.nextGridColumn(c);
		this.add(this.butDefault, c);
		Gbm.nextGridColumn(c);
		this.add(this.butAuto, c);							//Auto Button
	}
	
	private void refreshDayRange()
	{
		/*
		this.remove(this.comboDay);
//		System.out.println(this.comboYear.getSelectedItem());
		this.comboDay = new JComboBox<String>(DateOperation.getMaxDaysString(Byte.parseByte(Integer.toString(this.comboMonth.getSelectedIndex()+1)), 
																				Short.parseShort(this.comboYear.getSelectedItem().toString())));
		this.comboDay.setEnabled(this.enable);
		
		c.gridx = this.vecDay.x;
		c.gridy = this.vecDay.y;
		this.add(this.comboDay, c);
		
		*/
		
		this.comboDay.setModel(new DefaultComboBoxModel<String>(DateOperation.getMaxDaysString(Byte.parseByte(Integer.toString(this.comboMonth.getSelectedIndex()+1)), 
																				Short.parseShort(this.comboYear.getSelectedItem().toString()))));
		
		this.revalidate();
		this.repaint();
	}
	
	public void setDate(String day, String month, String year)
	{
		this.comboYear.setSelectedItem(year);
		this.comboMonth.setSelectedItem(DateConstants.MONTH_NAME_LIST.get(Integer.parseInt(month)-1));
		this.comboDay.setSelectedItem(day);
	}
	
	public LinkedHashMap<String, String> getData()
	{
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		
		map.put(PainDataIdentifier.DATE_DAY, this.comboDay.getSelectedItem().toString());
		map.put(PainDataIdentifier.DATE_MONTH, Integer.toString((this.comboMonth.getSelectedIndex())+1));
		map.put(PainDataIdentifier.DATE_YEAR, this.comboYear.getSelectedItem().toString());
		
		return map;
	}
	
	public String getDay()
	{
		return this.comboDay.getSelectedItem().toString();
	}
	public String getMonth()
	{
		return this.comboMonth.getSelectedItem().toString();
	}
	public int getMonthValue()
	{
		return this.comboMonth.getSelectedIndex() + 1;
	}
	public String getYear()
	{
		return this.comboYear.getSelectedItem().toString();
	}
	
	public void setAsDefaultDataThis()
	{
		this.setDefaultData(this.getDay(), Integer.toString(this.getMonthValue()), this.getYear());
	}
	
	public void setDefaultData(String day, String month, String year)
	{
		this.defaultMap.put(this.DAY, day);
		this.defaultMap.put(this.MONTH, month);
		this.defaultMap.put(this.YEAR, year);
	}
	
	public void resetDefault()
	{
		try
		{
			this.comboYear.setSelectedItem(this.defaultMap.get(this.YEAR));
			this.comboMonth.setSelectedIndex(Integer.parseInt(this.defaultMap.get(this.MONTH))-1);
			this.comboDay.setSelectedItem(this.defaultMap.get(this.DAY));
		}
		catch(NullPointerException ex){}
	}
	
	public void autoSetDate()
	{
		this.setDate(DateManager.getCurrentDay(), DateManager.getCurrentMonth(), DateManager.getCurrentYear());
	}
	
	public boolean sameAsDefault()
	{
		LinkedHashMap<String, String> data = this.getData();
		if (this.defaultMap.get(this.YEAR).equals(data.get(PainDataIdentifier.DATE_YEAR)) && 
				this.defaultMap.get(this.MONTH).equals(data.get(PainDataIdentifier.DATE_MONTH)) && 
				this.defaultMap.get(this.DAY).equals(data.get(PainDataIdentifier.DATE_DAY)))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public String getDateAsString()
	{
		return this.getDay() + "/" + this.getMonthValue() + "/" + this.getYear();
	}
	
	//Interfaces
	private ActionListener yearListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				refreshDayRange();
			}
		};
			
	private ActionListener monthListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			refreshDayRange();
		}
	};

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		switch(e.getActionCommand())
		{
			case AUTO:
				this.autoSetDate();
				break;
				
			case DEFAULT:
				this.resetDefault();
				break;
		}
	}
}
