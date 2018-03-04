package diary.data;

import diary.constants.PainDataIdentifier;

public class PainEntryDataVoid extends PainEntryData
{
	public PainEntryDataVoid(String day, String month, String year)
	{
		super();
		
		if (day.length()==1)
		{
			day = "0" + day;
		}
		super.getDataMap().put(PainDataIdentifier.DATE_DAY, day);
		super.getDataMap().put(PainDataIdentifier.DATE_MONTH, month);
		super.getDataMap().put(PainDataIdentifier.DATE_YEAR, year);
		super.getDataMap().put(PainDataIdentifier.PAIN_AMOUNT, "0");
	}
}
