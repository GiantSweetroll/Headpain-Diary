package diary.test;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
	
	public static void main(String args[])
	{
		//Initialize
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		GridBagConstraints c = new GridBagConstraints();
		JLabel label1 = new JLabel("Hello");
		JLabel label2 = new JLabel("World");
		JLabel label3 = new JLabel("KLJDSAKDASJLDKSJDLKAJDKLJADJADL:ADAS");
		
		//Panel properties
		panel.setLayout(new GridBagLayout());
		label1.setOpaque(true);
		label1.setBackground(Color.BLUE);
		label1.setForeground(Color.white);
		label3.setOpaque(true);
		label3.setBackground(Color.cyan);
		
		//Add to panel
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(10, 10, 10, 10);
		panel.add(label1, c);
		c.gridx = 1;
		panel.add(label2, c);
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		panel.add(label3, c);
		
		//Add panel to frame
		frame.add(panel);
		
		//Frame properties
		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	private static void goToNextLayout(CardLayout card)
	{
	}
	
	private static void viewDetails()
	{
		
	}
	
	private static void hashGenerator(int num, int i)
	{
		int val = (num + i * (1 + (num % 10))) % 11;
		System.out.println(val);
	}
	
	private static void print(Object obj)
	{
		System.out.println(obj);
	}
}