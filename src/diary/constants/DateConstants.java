package diary.constants;

import java.util.List;

import diary.methods.DateOperation;

public class DateConstants 
{
	public static final byte FEBRUARY = 2;
	
	public static final byte[] MONTHS_31 = {1, 3, 5, 7, 8, 10, 12};
	public static final byte[] MONTHS_30 = {2, 4, 6, 9, 11};
	
	public static final List<String> MONTH_NAME_LIST = DateOperation.getMonthNameList();
}
