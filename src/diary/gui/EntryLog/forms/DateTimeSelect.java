package diary.gui.EntryLog.forms;

import java.util.LinkedHashMap;

import diary.constants.PainDataIdentifier;
import diary.constants.XMLIdentifier;
import diary.data.PainEntryData;
import diary.gui.DatePanel;
import diary.gui.EntryLog.TimePanel;
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
		super(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_ELEMENT_TYPE_DATE_TIME));
		
		this.date = new DatePanel(true);
		this.time = new TimePanel(true);
		
		this.time.setToCurrentTime();
		this.time.setAsDefaultTimeThis();
		
		this.addComponent(this.date);
		this.addComponent(this.time);
	}
	
	//Methods
	public Date getDate()
	{
		return this.date.getDate();
	}
	public LinkedHashMap<String, String> getTime()
	{
		return this.time.getData();
	}
	public void setDate(Date date)
	{
		this.date.setDate(date);
	}
	public void setTime(PainEntryData entry)
	{
		this.time.setTime(entry.getDataMap().get(PainDataIdentifier.TIME_HOUR).toString(), entry.getDataMap().get(PainDataIdentifier.TIME_MINUTE).toString());
	}
	public void setData(PainEntryData entry)
	{
		this.setDate(entry.getDate());
		this.setTime(entry);
	}
	
	//Overriden Methods
	@Override
	public void resetDefaults() 
	{
		this.date.resetDefault();
		this.time.resetDefault();
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

}
