package diary.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.LinkedHashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import diary.constants.Constants;
import diary.constants.XMLIdentifier;
import giantsweetroll.gui.swing.Gbm;

public class DateRangePanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3166809849008171874L;
	private DatePanel dateFrom, dateTo;
	private JLabel labFrom, labTo;
	private GridBagConstraints c;
	
	//Constants
	public static final String FROM = "from";
	public static final String TO = "to";
	
	public DateRangePanel()
	{
		//Initialization
		this.dateFrom = new DatePanel(true);
		this.dateTo = new DatePanel(true);
		this.c = new GridBagConstraints();
		this.labFrom = new JLabel (Constants.LANGUAGE.getTextMap().get(XMLIdentifier.DATE_FROM_LABEL) + ":", SwingConstants.RIGHT);
		this.labTo = new JLabel (Constants.LANGUAGE.getTextMap().get(XMLIdentifier.DATE_TO_LABEL) + ":", SwingConstants.RIGHT);
		
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
}
