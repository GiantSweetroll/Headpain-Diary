package diary.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.LinkedHashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import diary.constants.Constants;
import diary.constants.PainDataIdentifier;
import diary.constants.XMLIdentifier;
import diary.methods.Methods;
import giantsweetroll.date.Date;
import giantsweetroll.gui.swing.Gbm;

public class DateRangePanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3166809849008171874L;
	public DatePanel dateFrom, dateTo;
	private JLabel labFrom, labTo;
	private GridBagConstraints c;
	
	//Constants
	public static final String FROM = "from";
	public static final String TO = "to";
	
	public DateRangePanel()
	{
		this.init();
	}
	
	public DateRangePanel(LinkedHashMap<String, LinkedHashMap<String, String>> dateRangeMap)
	{
		this.init();
		this.setFromDate(dateRangeMap.get(FROM).get(PainDataIdentifier.DATE_DAY),
							dateRangeMap.get(FROM).get(PainDataIdentifier.DATE_MONTH),
							dateRangeMap.get(FROM).get(PainDataIdentifier.DATE_YEAR));
		this.setToDate(dateRangeMap.get(TO).get(PainDataIdentifier.DATE_DAY),
						dateRangeMap.get(TO).get(PainDataIdentifier.DATE_MONTH),
						dateRangeMap.get(TO).get(PainDataIdentifier.DATE_YEAR));
	}
	
	private void init()
	{
		//Initialization
		this.dateFrom = new DatePanel(true);
		this.dateTo = new DatePanel(true);
		this.c = new GridBagConstraints();
		this.labFrom = new JLabel (Methods.getLanguageText(XMLIdentifier.DATE_FROM_LABEL) + ":", SwingConstants.RIGHT);
		this.labTo = new JLabel (Methods.getLanguageText(XMLIdentifier.DATE_TO_LABEL) + ":", SwingConstants.RIGHT);
		
		//Properties
		this.dateFrom.setAsDefaultDataThis();
		this.dateTo.autoSetDate();
		this.dateTo.setAsDefaultDataThis();
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_TOP_COMPONENT;
		c.fill = GridBagConstraints.BOTH;
		this.add(this.labFrom, c);
		Gbm.nextGridColumn(c);
		this.add(this.dateFrom, c);
		Gbm.newGridLine(c);
		c.insets = Constants.INSETS_GENERAL;
		this.add(this.labTo, c);
		Gbm.nextGridColumn(c);
		this.add(this.dateTo, c);		
	}
	
	public LinkedHashMap<String, LinkedHashMap<String, String>> getDateRangeMap()
	{
		LinkedHashMap<String, LinkedHashMap<String, String>> map = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		
		map.put(FROM, this.dateFrom.getData());
		map.put(TO, this.dateTo.getData());
		
		return map;
	}
	
	public void setFromDate(String day, String month, String year)
	{
		this.dateFrom.setDate(day, month, year);
	}
	public void setToDate(String day, String month, String year)
	{
		this.dateTo.setDate(day, month, year);
	}
	public void setFromDate(Date date)
	{
		this.dateFrom.setDate(date);
	}
	public void setToDate(Date date)
	{
		this.dateTo.setDate(date);
	}
	
	public String getDateRangeAsString()
	{
		return this.dateFrom.getDateAsString() + " - " + this.dateTo.getDateAsString();
	}
	
	public LinkedHashMap<String, Date> getDateRange()
	{
		LinkedHashMap<String, Date> map = new LinkedHashMap<String, Date>();
		
		map.put(FROM, this.dateFrom.getDate());
		map.put(TO, this.dateTo.getDate());
		
		return map;
	}

	public void autoSetDateRanges()
	{
		this.setFromDate(new Date());
		this.setToDate(new Date());
	}
}
