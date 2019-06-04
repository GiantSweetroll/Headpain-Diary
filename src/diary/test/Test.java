package diary.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import diary.constants.ImageConstants;

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
	
	public static void zipFile(File inputFile, String outputPath)
	{
		try
		{
			FileOutputStream fileOutputStream = new FileOutputStream(outputPath);
			ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
			
			ZipEntry zipEntry = new ZipEntry(inputFile.getName());
			zipOutputStream.putNextEntry(zipEntry);
			
			FileInputStream fileInputStream = new FileInputStream(inputFile);
			byte[] buf = new byte[1024];
			int bytesRead;
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void labelUpdateIcon()
	{
		JLabel label = new JLabel();
		ImageIcon icon = new ImageIcon(ImageConstants.LOGO);
		label.setIcon(icon);
		ImageIcon icon2 = new ImageIcon(ImageConstants.FKUI);
		label.setIcon(icon2);
		JOptionPane.showMessageDialog(null, label);
		ImageIcon icon3 = new ImageIcon(ImageConstants.MEDICAL_MEDIA);
		label.setIcon(icon3);
		JOptionPane.showMessageDialog(null, label);
	}
	public static void labelUpdateIcon2()
	{
		JLabel label = new JLabel();
		ImageIcon icon = new ImageIcon(ImageConstants.LOGO);
		label.setIcon(icon);
		icon = new ImageIcon(ImageConstants.FKUI);
		label.setIcon(icon);
		JOptionPane.showMessageDialog(null, label);
		icon = new ImageIcon(ImageConstants.MEDICAL_MEDIA);
		label.setIcon(icon);
		JOptionPane.showMessageDialog(null, label);
	}
	
	public static void main(String args[])
	{
		labelUpdateIcon2();
	}
}