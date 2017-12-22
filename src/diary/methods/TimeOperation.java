package diary.methods;

public final class TimeOperation 
{
	public static String[] getHourRange()
	{
		String[] arr = new String[25];
		
		for (int i=0; i<arr.length; i++)
		{
			if(isOneDigit(Integer.toString(i)))
			{
				arr[i] = 0 + Integer.toString(i);
			}
			else
			{
				arr[i] = Integer.toString(i);
			}
		}
		
		return arr;
	}
	
	public static String[] getTimeRange60()
	{
		String[] arr = new String[61];
		
		for (int i=0; i<arr.length; i++)
		{
			if(isOneDigit(Integer.toString(i)))
			{
				arr[i] = 0 + Integer.toString(i);
			}
			else
			{
				arr[i] = Integer.toString(i);
			}
		}
		
		return arr;
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
