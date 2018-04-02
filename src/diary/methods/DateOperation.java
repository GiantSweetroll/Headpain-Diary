package diary.methods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import diary.constants.Constants;
import diary.constants.DateConstants;
import diary.constants.XMLIdentifier;
import giantsweetroll.date.DateManager;

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
		short startYear = DateConstants.START_YEAR_RANGE;
		short maxYear = Short.parseShort(DateManager.getCurrentYear());
		
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
			if (isOneDigit(Byte.toString(days[i])))
			{
				daysString[i] = 0 + Byte.toString(days[i]);
			}
			else
			{
				daysString[i] = Byte.toString(days[i]);
			}
		}
		
		return daysString;
	}
	public static String[] getMonthsString()
	{
		String[] arr = new String[12];
		
		for (int i=0; i<arr.length; i++)
		{
			if (isOneDigit(Integer.toString(i)))
			{
				arr[i] = 0 + Integer.toString(i+1);
			}
			else
			{
				arr[i] = Integer.toString(i+1);
			}
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
	public static boolean isLeapYear(int year)
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
	public static String[] getYearRangeStringReversed()
	{
		List<String> list = Arrays.asList(DateOperation.getYearRangeString());
		Collections.reverse(list);
		return list.toArray(new String[list.size()]);
	}
	
	public static final List<String> getMonthNameList()
	{
		List<String> list = new ArrayList<String>();
		
		list.add(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.DATE_MONTH_JANUARY));
		list.add(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.DATE_MONTH_FEBRUARY));
		list.add(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.DATE_MONTH_MARCH));
		list.add(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.DATE_MONTH_APRIL));
		list.add(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.DATE_MONTH_MAY));
		list.add(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.DATE_MONTH_JUNE));
		list.add(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.DATE_MONTH_JULY));
		list.add(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.DATE_MONTH_AUGUST));
		list.add(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.DATE_MONTH_SEPTEMBER));
		list.add(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.DATE_MONTH_OCTOBER));
		list.add(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.DATE_MONTH_NOVEMBER));
		list.add(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.DATE_MONTH_DECEMBER));
		
		return list;
	}
	
	public static boolean isOneDigit(String value)
	{
		if (value.length()==1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
