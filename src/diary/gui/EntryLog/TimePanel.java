package diary.gui.EntryLog;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import diary.constants.Constants;
import diary.constants.PainDataIdentifier;
import diary.constants.TimeConstants;
import diary.constants.XMLIdentifier;
import giantsweetroll.GDateManager;
import giantsweetroll.gui.swing.Gbm;

public class TimePanel extends JPanel implements ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5455616933083251455L;
	private JComboBox<String> comboHour, comboMinute, comboSec;
	private JButton butReset, butCurrent;
	private GridBagConstraints c;
	private Map<String, String> defaultMap;
	
	//Constants
	private final String RESET = "reset";
	private final String CURRENT = "current";
	private final String HOUR = "hour";
	private final String MINUTE = "minute";
	private final String SECONDS = "seconds";
	private final String TIME_DIVIDER = ":";
	
	protected TimePanel(boolean enable)
	{
		this.init();
		if(!enable)
		{
			this.comboHour.setEnabled(false);
			this.comboMinute.setEnabled(false);
			this.comboSec.setEnabled(false);
			this.butCurrent.setEnabled(false);
			this.butReset.setEnabled(false);
		}
	}
	
	//Methods
	private void init()
	{
		//Initialization
		this.comboHour = new JComboBox<String>(TimeConstants.HOURS_RANGE);
		this.comboMinute = new JComboBox<String>(TimeConstants.GENERAL_TIME_RANGE);
		this.comboSec = new JComboBox<String>(TimeConstants.GENERAL_TIME_RANGE);
		this.butCurrent = new JButton(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.AUTO_TEXT));
		this.butReset = new JButton(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.RESET_TEXT));
		this.c = new GridBagConstraints();
		this.defaultMap = new LinkedHashMap<String, String>();
		
		//Properties
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		this.butCurrent.addActionListener(this);
		this.butCurrent.setActionCommand(this.CURRENT);
		this.butCurrent.setToolTipText(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.TIME_AUTO_BUTTON_TOOLTIP_TEXT));
		this.butReset.addActionListener(this);
		this.butReset.setActionCommand(this.RESET);
		this.butReset.setToolTipText(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.TIME_RESET_BUTTON_TOOLTIP_TEXT));
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_GENERAL;
		this.add(this.comboHour, c);			//Hour
		Gbm.nextGridColumn(c);
		this.add(new JLabel (this.TIME_DIVIDER), c);
		Gbm.nextGridColumn(c);
		this.add(this.comboMinute, c);			//Minute
		Gbm.nextGridColumn(c);
		this.add(new JLabel(this.TIME_DIVIDER), c);
		Gbm.nextGridColumn(c);
		this.add(this.comboSec, c);				//Seconds
		Gbm.nextGridColumn(c);
		this.add(this.butReset, c);				//Reset
		Gbm.nextGridColumn(c);
		this.add(this.butCurrent, c);			//Current
	}
	
	protected void setTime(String hour, String minute, String second)
	{
		this.comboHour.setSelectedItem(hour);
		this.comboMinute.setSelectedItem(minute);
		this.comboSec.setSelectedItem(second);
	}
	protected void setDefaultTime(String hour, String minute, String second)
	{
		this.defaultMap.put(this.HOUR, hour);
		this.defaultMap.put(this.MINUTE, minute);
		this.defaultMap.put(this.SECONDS, second);
	}
	protected void setAsDefaultTimeThis()
	{
		this.defaultMap.put(this.HOUR, this.comboHour.getSelectedItem().toString());
		this.defaultMap.put(this.MINUTE, this.comboMinute.getSelectedItem().toString());
		this.defaultMap.put(this.SECONDS, this.comboSec.getSelectedItem().toString());
	}
	
	protected void resetDefault()
	{
		this.comboHour.setSelectedItem(this.defaultMap.get(this.HOUR));
		this.comboMinute.setSelectedItem(this.defaultMap.get(this.MINUTE));
		this.comboSec.setSelectedItem(this.defaultMap.get(this.SECONDS));
	}
	
	protected void setToCurrentTime()
	{
		this.comboHour.setSelectedItem(GDateManager.getCurrentHour());
		this.comboMinute.setSelectedItem(GDateManager.getCurrentMinute());
		this.comboSec.setSelectedItem(GDateManager.getCurrentSecond());
	}
	
	protected LinkedHashMap<String, String> getData()
	{
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		
		map.put(PainDataIdentifier.TIME_HOUR, this.comboHour.getSelectedItem().toString());
		map.put(PainDataIdentifier.TIME_MINUTE, this.comboMinute.getSelectedItem().toString());
		map.put(PainDataIdentifier.TIME_SECONDS, this.comboSec.getSelectedItem().toString());
		
		return map;
	}
	
	//Interfaces
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		switch (e.getActionCommand())
		{
			case RESET:
				this.resetDefault();
				break;
				
			case CURRENT:
				this.setToCurrentTime();
				break;
		}
	}
}
