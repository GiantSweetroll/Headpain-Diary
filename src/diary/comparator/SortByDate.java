package diary.comparator;

import java.util.Comparator;

import diary.data.PainEntryData;
import giantsweetroll.date.Date;

public class SortByDate implements Comparator<PainEntryData>
{
	/*
	@Override
	public int compare(PainEntryData a, PainEntryData b) 
	{
		Date date1 = a.getDate();
		Date date2 = a.getDate();
		
		//Compare year
		String temp1 = Integer.toString(date1.getYear());
		String temp2 = Integer.toString(date2.getYear());
		if (temp1.equals(temp2))		//If same year
		{
			//Compare month
			temp1 = Integer.toString(date1.getMonth());
			temp2 = Integer.toString(date2.getMonth());
			if (temp1.equals(temp2))	//If same month
			{
				//Compare day
				temp1 = Integer.toString(date1.getDay());
				temp2 = Integer.toString(date2.getDay());
				
				return temp1.compareTo(temp2);
			}
			else
			{
				return temp1.compareTo(temp2);
			}
		}
		else
		{
			return temp1.compareTo(temp2);
		}
	}	*/
	
	@Override
	public int compare(PainEntryData a, PainEntryData b) 
	{
		Date date1 = a.getDate();
		Date date2 = a.getDate();
		
		//Compare year
		int temp1 = date1.getYear();
		int temp2 = date2.getYear();
		if (temp1 == temp2)		//If same year
		{
			//Check month
			temp1 = date1.getMonth();
			temp2 = date2.getMonth();
			if (temp1 == temp2)	//if same month
			{
				//Check day
				temp1 = date1.getDay();
				temp2 = date2.getDay();
				return temp1 = temp2;
			}
			else
			{
				return temp1 - temp2;
			}
		}
		else
		{
			return temp1 - temp2;
		}
	}
}
