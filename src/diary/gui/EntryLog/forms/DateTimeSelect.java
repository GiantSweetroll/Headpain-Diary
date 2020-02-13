package diary.gui.EntryLog.forms;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusEvent;

import diary.constants.Constants;
import diary.constants.XMLIdentifier;
import diary.data.PainEntryData;
import diary.gui.DatePanel;
import diary.gui.TimePanel;
import diary.methods.Methods;
import giantsweetroll.date.Date;

public class DateTimeSelect extends FormElement
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5558189319140518879L;

	private DatePanel date;
	private TimePanel time;
	
	//Constructor
	public DateTimeSelect()
	{
		super(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_ELEMENT_TYPE_DATE_TIME), true);
		
		//Initialization
		this.date = new DatePanel(true);
		this.time = new TimePanel(true);
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		this.getPanel().setLayout(new GridBagLayout());
		this.date.autoSetDate();
		this.date.setAsDefaultDataThis();
		this.time.setToCurrentTime();
		this.time.setAsDefaultTimeThis();
		
		//add to panel
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = Constants.INSETS_TOP_COMPONENT;
		this.getPanel().add(this.getFormTitleLabel(), c);
		this.getPanel().add(this.date, c);
		this.getPanel().add(this.time, c);
	}
	
	//Public Methods
	public Date getDate()
	{
		return this.date.getDate();
	}
	public String getTimeHour()
	{
		return this.time.getHour();
	}
	public String getTimeMinutes()
	{
		return this.time.getMinutes();
	}
	public void setDate(Date date)
	{
		this.date.setDate(date);
	}
	public void setTime(PainEntryData entry)
	{
		this.time.setTime(entry.getTimeHour(), entry.getTimeMinutes());
	}
	public void setData(PainEntryData entry)
	{
		this.setDate(entry.getDate());
		this.setTime(entry);
	}
	public boolean dateSameAsDefault()
	{
		return this.date.sameAsDefault();
	}
	public boolean timeSameAsDefault()
	{
		return this.time.sameAsDefault();
	}
	public void setAsDefaultThis()
	{
		this.setDefaultDateAsThis();
		this.setDefaultTimeAsThis();
	}
	public void setDefaultDateAsThis()
	{
		this.date.setAsDefaultDataThis();
	}
	public void setDefaultTimeAsThis()
	{
		this.time.setAsDefaultTimeThis();
	}
	
	//Overridden Methods
	@Override
	public void resetDefaults() 
	{
		super.resetDefaults();
		this.date.resetDefault();
		this.time.resetDefault();
		this.time.setToCurrentTime();
		this.time.setAsDefaultTimeThis();
	}
	
	@Override
	public void refresh() {};

	@Deprecated
	public Object getData()
	{
		return null;
	}

	@Deprecated
	public void setData(Object obj) {}

	@Override
	public boolean allFilled() 
	{
		return true;
	}

	@Override
	public void revalidateLanguage() 
	{
		this.setFormTitle(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_ELEMENT_TYPE_DATE_TIME));
		this.date.revalidateLanguage();
		this.time.revalidateLanguage();
	}

	@Override
	public String getName()
	{
		return Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_MAP_BUTTON_DATE_TIME_TEXT);
	}
	
	@Override
	public void focusGained(FocusEvent e) {}

	@Override
	public void focusLost(FocusEvent e) {}
}
