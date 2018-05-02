package diary.test;

import java.util.ArrayList;
import java.util.List;

public class Test
{	
	public static void main(String args[])
	{
		String str = "4C4F4B492044494544";
		List<String> list = new ArrayList<String>();
		
		for (int i=0; i<=str.length()-2; i+=2)
		{
			list.add(str.substring(i, i+2));
		}
		
		for (String chk : list)
		{
			System.out.print(chk + " ");
		}
	}
}