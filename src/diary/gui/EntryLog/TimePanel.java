package diary.gui.EntryLog;

import java.awt.Color;
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
import diary.methods.Methods;
import giantsweetroll.date.DateManager;
import giantsweetroll.gui.swing.Gbm;

public class TimePanel extends JPanel implements ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5455616933083251455L;
	private JComboBox<String> comboHour, comboMinute;
	private JButton butReset, butCurrent;
	private GridBagConstraints c;
	private Map<String, String> defaultMap;
	
	//Constants
	private final String RESET = "reset";
	private final String CURRENT = "current";
	private final String HOUR = "hour";
	private final String MINUTE = "minute";
	private final String TIME_DIVIDER = ":";
	
	protected TimePanel(boolean enable)
	{
		this.init();
		if(!enable)
		{
			this.comboHour.setEnabled(false);
			this.comboMinute.setEnabled(false);
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
		this.butCurrent = new JButton(Methods.getLanguageText(XMLIdentifier.AUTO_TEXT));
		this.butReset = new JButton(Methods.getLanguageText(XMLIdentifier.RESET_TEXT));
		this.c = new GridBagConstraints();
		this.defaultMap = new LinkedHashMap<String, String>();
		
		//Properties
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		this.comboHour.setBackground(Color.WHITE);
		this.comboMinute.setBackground(Color.WHITE);
		this.butCurrent.addActionListener(this);
		this.butCurrent.setActionCommand(this.CURRENT);
		this.butCurrent.setToolTipText(Methods.getLanguageText(XMLIdentifier.TIME_AUTO_BUTTON_TOOLTIP_TEXT));
		this.butReset.addActionListener(this);
		this.butReset.setActionCommand(this.RESET);
		this.butReset.setToolTipText(Methods.getLanguageText(XMLIdentifier.TIME_RESET_BUTTON_TOOLTIP_TEXT));
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_GENERAL;
		this.add(this.comboHour, c);			//Hour
		Gbm.nextGridColumn(c);
		this.add(new JLabel (this.TIME_DIVIDER), c);
		Gbm.nextGridColumn(c);
		this.add(this.comboMinute, c);			//Minute
		Gbm.nextGridColumn(c);
		this.add(this.butReset, c);				//Reset
		Gbm.nextGridColumn(c);
		this.add(this.butCurrent, c);			//Current
	}
	
	protected void setTime(String hour, String minute)
	{
		this.comboHour.setSelectedItem(hour);
		this.comboMinute.setSelectedItem(minute);
	}
	protected void setDefaultTime(String hour, String minute)
	{
		this.defaultMap.put(this.HOUR, hour);
		this.defaultMap.put(this.MINUTE, minute);
	}
	protected void setAsDefaultTimeThis()
	{
		this.defaultMap.put(this.HOUR, this.comboHour.getSelectedItem().toString());
		this.defaultMap.put(this.MINUTE, this.comboMinute.getSelectedItem().toString());
	}
	
	protected void resetDefault()
	{
		this.comboHour.setSelectedItem(this.defaultMap.get(this.HOUR));
		this.comboMinute.setSelectedItem(this.defaultMap.get(this.MINUTE));
	}
	
	protected Map<String, String> getDefaultMap()
	{
		return this.defaultMap;
	}
	
	protected void setToCurrentTime()
	{
		this.comboHour.setSelectedItem(DateManager.getCurrentHour());
		this.comboMinute.setSelectedItem(DateManager.getCurrentMinute());
	}
	
	protected LinkedHashMap<String, String> getData()
	{
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		
		map.put(PainDataIdentifier.TIME_HOUR, this.comboHour.getSelectedItem().toString());
		map.put(PainDataIdentifier.TIME_MINUTE, this.comboMinute.getSelectedItem().toString());
		
		return map;
	}
	
	protected boolean sameAsDefault()
	{
		LinkedHashMap<String, String> data = this.getData();
		if (this.defaultMap.get(this.HOUR).equals(data.get(PainDataIdentifier.TIME_HOUR)) && 
				this.defaultMap.get(this.MINUTE).equals(data.get(PainDataIdentifier.TIME_MINUTE)))
		{
			return true;
		}
		else
		{
			return false;
		}
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
