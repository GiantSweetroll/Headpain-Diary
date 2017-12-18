package diary.methods;

import diary.constants.DateConstants;
import giantsweetroll.GDateManager;
import giantsweetroll.message.MessageManager;

public class DateOperation 
{
	public static byte[] getMaxDays(byte month, short year)
	{
		byte[] days;
		
		if (month == DateConstants.FEBRUARY)
		{
			if (DateOperation.isLeapYear(year))
			{
				days = new byte[29];
				for (byte i=0; i<days.length; i++)
				{
					days[i] = (byte)(i+1);
				}
			}
			else
			{
				days = new byte[28];
				for (byte i=0; i<days.length; i++)
				{
					days[i] = (byte)(i+1);
				}
			}
		}
		else
		{
			if (DateOperation.is30Days(month))
			{
				days = new byte[30];
				for (byte i=0; i<days.length; i++)
				{
					days[i] = (byte)(i+1);
				}
			}
			else
			{
				days = new byte[31];
				for (byte i=0; i<days.length; i++)
				{
					days[i] = (byte)(i+1);
				}
			}
		}
		
		return days;
	}
	public static short[] getYearRange()
	{
		short startYear = 2016;
		short maxYear = Short.parseShort(GDateManager.getCurrentYear());
		
		short[] years;
		
		if (startYear == maxYear)
		{
			years = new short[1];
			years[0] = startYear;
		}
		else
		{
			short range = (short)(maxYear - startYear);
			years = new short[range+1];
			
			for (short i=0; i<=range; i++)
			{
				years[i] = (short)(startYear+i);
			}
		}
		
		return years;
	}
	public static String[] getYearRangeString()
	{
		short[] raw = DateOperation.getYearRange();
		String[] string = new String[raw.length];
		
		for (int i=0; i<string.length; i++)
		{
			string[i] = Short.toString(raw[i]);
		}
		
		return string;
	}
	public static String[] getMaxDaysString(byte month, short year)
	{
		byte[] days = DateOperation.getMaxDays(month, year);
		String[] daysString = new String[days.length];
		
		for (int i=0; i<daysString.length; i++)
		{
			daysString[i] = Byte.toString(days[i]);
		}
		
		return daysString;
	}
	public static String[] getMonthsString()
	{
		String[] arr = new String[12];
		
		for (int i=0; i<arr.length; i++)
		{
			arr[i] = Integer.toString(i+1);
		}
		
		return arr;
	}
	public static boolean is30Days(byte month)
	{
		for (int i=0; i<DateConstants.MONTHS_30.length; i++)
		{
			if (month == DateConstants.MONTHS_30[i])
			{
				return true;
			}
		}
		return false;
	}
	public static boolean is31Days(byte month)
	{
		for (int i=0; i<DateConstants.MONTHS_31.length; i++)
		{
			if (month == DateConstants.MONTHS_31[i])
			{
				return true;
			}
		}
		return false;
	}
	public static boolean isLeapYear(short year)
	{
		if (year%4==0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static String getCurrentDay()
	{
		String day = GDateManager.getCurrentDay();
		
		if (day.substring(0, 1).equals("0"))
		{
			return day.substring(1);
		}
		else
		{
			return day;
		}
	}
	public static String getCurrentMonth()
	{
		String month = GDateManager.getCurrentMonth();
		
		if (month.substring(0, 1).equals("0"))
		{
			return month.substring(1);
		}
		else
		{
			return month;
		}
	}
}
