package diary.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import diary.constants.Constants;
import diary.constants.DateConstants;
import diary.constants.PainDataIdentifier;
import diary.constants.XMLIdentifier;
import diary.methods.DateOperation;
import giantsweetroll.GDateManager;
import giantsweetroll.VectorInt;
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
	private VectorInt vecDay;
	
	//Constants
	private final String AUTO = "auto";
	private final String DEFAULT = "default";
	private final String YEAR = "year";
	private final String MONTH = "month";
	private final String DAY = "day";
	
	public DatePanel(boolean enabled)
	{
		this.init();
		
		if (!enabled)
		{
			this.comboDay.setEnabled(false);
			this.comboMonth.setEnabled(false);
			this.comboYear.setEnabled(false);
			this.butAuto.setEnabled(false);
		}
	}
	
	//Methods
	private final void init()
	{
		//Initialization
		this.comboYear = new JComboBox<String>(DateOperation.getYearRangeString());
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
		this.butAuto.addActionListener(this);
		this.butAuto.setActionCommand(this.AUTO);
		this.butAuto.setToolTipText(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.DATE_AUTO_BUTTON_TOOLTIP_TEXT));
		this.butDefault.addActionListener(this);
		this.butDefault.setActionCommand(this.DEFAULT);
		this.butDefault.setToolTipText(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.DATE_RESET_BUTTON_TOOLTIP_TEXT));
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_GENERAL;
		this.vecDay = new VectorInt(c.gridx, c.gridy);
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
		this.remove(this.comboDay);
		this.comboDay = new JComboBox<String>(DateOperation.getMaxDaysString(Byte.parseByte(Integer.toString(this.comboMonth.getSelectedIndex()+1)), Short.parseShort(this.comboYear.getSelectedItem().toString())));
		c.gridx = this.vecDay.x;
		c.gridy = this.vecDay.y;
		this.add(this.comboDay, c);
		
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
	public String getYear()
	{
		return this.comboYear.getSelectedItem().toString();
	}
	
	public void setAsDefaultDataThis()
	{
		this.setDefaultData(this.getDay(), this.getMonth(), this.getYear());
	}
	
	public void setDefaultData(String day, String month, String year)
	{
		this.defaultMap.put(this.DAY, day);
		this.defaultMap.put(this.MONTH, month);
		this.defaultMap.put(this.YEAR, year);
	}
	
	public void resetDefault()
	{
		this.comboYear.setSelectedItem(this.defaultMap.get(this.YEAR));
		this.comboMonth.setSelectedIndex(Integer.parseInt(this.defaultMap.get(this.MONTH))-1);
		this.comboDay.setSelectedItem(this.defaultMap.get(this.DAY));
	}
	
	public void autoSetDate()
	{
		this.setDate(GDateManager.getCurrentDay(), GDateManager.getCurrentMonth(), GDateManager.getCurrentYear());
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
