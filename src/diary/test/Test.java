package diary.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import diary.ImageTextButton;
import diary.constants.ImageConstants;
import giantsweetroll.ImageManager;
import giantsweetroll.numbers.GNumbers;

public class Test
{	
	public static void codeGenerator()
	{
		List<String> texts = new ArrayList<String>();
		
		String prefix = "images.add(new ImagePiece(PainLocationPath.CUSTOM_PAIN_LEFT_",
				middle = ", \"kir",
				suffix = "\"));";
		
		int awal = 1,
				until = 20;
		for (int i=awal; i<=until; i++)
		{
			texts.add(prefix + i + middle + i + suffix);
		}
		
		File file = new File("test.txt");
		try
		{
			if (!file.exists())
			{
				file.createNewFile();
			}
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			
			for (String text : texts)
			{
				bw.write(text);
				bw.newLine();
			}
			
			bw.close();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		
		JOptionPane.showMessageDialog(null, "Done");
	}

	public static void main(String args[])
	{
		double initial = 50000d;
		double owed = initial;
		double interest = 1.1d;
		
		int i=1;
		
		while (owed>=0)
		{
			System.out.print("Money owed in year " + i + ": ");
			owed = GNumbers.round(owed * interest - 6000, 2);
			System.out.println(owed);
			i++;
		}
	}
}