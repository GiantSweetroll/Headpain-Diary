package diary.test;

import java.util.LinkedHashMap;
import java.util.Map;

import giantsweetroll.numbers.GNumbers;

public class Test 
{

	public static void main(String[] args) 
	{
		float hasil = 432f/2000f * 100f;
		int baru = (int)GNumbers.round(hasil, 0);
		System.out.println(hasil);
		System.out.println(baru);
		System.out.println((432/2000) * 100);
	}
}
