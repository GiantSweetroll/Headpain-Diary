package diary.test;

import java.util.LinkedHashMap;
import java.util.Map;

public class Test 
{

	public static void main(String[] args) 
	{
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("asdasd", "vvvv");
		map.put("12345", "cdkd");
		
		System.out.println(map.size());
		
//		map.remove("12345");
		
		for (Map.Entry<String, String> entry : map.entrySet())
		{
	//		map.remove(entry.getKey());
			map.entrySet().removeIf(e -> entry.getKey().equals("12345"));
		}
		
		System.out.println(map.size());
	}
}
