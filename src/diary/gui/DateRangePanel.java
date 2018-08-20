package diary.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.LinkedHashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import diary.constants.Constants;
import diary.constants.XMLIdentifier;
import diary.interfaces.LanguageListener;
import diary.methods.Methods;
import giantsweetroll.date.Date;
import giantsweetroll.gui.swing.Gbm;

public class DateRangePanel extends JPanel implements LanguageListener
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
	
	public DateRangePanel(Date dateFrom, Date dateTo)
	{
		this.init();
		this.setFromDate(dateFrom);
		this.setToDate(dateTo);
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
		this.dateFrom.autoSetDate();
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
	
	public void setFromDate(Date date)
	{
		this.dateFrom.setDate(date);
	}
	public void setToDate(Date date)
	{
		this.dateTo.setDate(date);
	}
	
	public Date getFromDate()
	{
		return Date.getEarlierDate(this.dateFrom.getDate(), this.dateTo.getDate());
	}
	public Date getToDate()
	{
		return Date.getLaterDate(this.dateFrom.getDate(), this.dateTo.getDate());
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

	@Override
	public void revalidateLanguage()
	{
		this.labFrom.setText(Methods.getLanguageText(XMLIdentifier.DATE_FROM_LABEL));
		this.labTo.setText(Methods.getLanguageText(XMLIdentifier.DATE_TO_LABEL));
		this.dateFrom.revalidateLanguage();
		this.dateTo.revalidateLanguage();
	}
}
