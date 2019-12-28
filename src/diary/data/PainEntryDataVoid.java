package diary.data;

import giantsweetroll.date.Date;

public class PainEntryDataVoid extends PainEntryData
{
	public PainEntryDataVoid(Date date)
	{
		super();
		
		super.setDate(date);
	}
	
	//Overridden Methods
	@Override
	public String getFullTime()
	{
		return "";
	}
	
	@Override
	public String getFullTimeAndDate()
	{
		return this.getFullDate();
	}
}
