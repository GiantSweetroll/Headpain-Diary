package diary.test;

public class Test
{	
	public static void main(String args[])
	{
		long total = Runtime.getRuntime().totalMemory()/1024;
		long free = Runtime.getRuntime().freeMemory()/1024;
		long start = System.nanoTime();
		
		System.out.println("Total Memory: " + total);
		System.out.println("Free Memory: " + free);
		System.out.println("Used Memory: " + (total-free));
		
		long end = System.nanoTime();
		
		double sec = (double)(end-start)/100000d;
		
		System.out.println("Time taken: " + sec + " seconds");
	}
}