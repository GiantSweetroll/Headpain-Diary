package diary.gui.EntryLog;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import diary.constants.Constants;
import giantsweetroll.message.MessageManager;

public class ImagePanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8666216117020248796L;
	private BufferedImage image;
	
	protected ImagePanel(URL imageUrl)
	{
		try
		{
			this.image = ImageIO.read(imageUrl);
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		catch(Exception ex)
		{
			MessageManager.showErrorDialog(ex);
			try {
				ex.printStackTrace(new PrintStream(new File("debug.txt")));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		this.repaint();
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(image, 0, 0, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height, this);
	}
}
