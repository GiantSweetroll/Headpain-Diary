package diary.gui.EntryLog;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import diary.constants.Constants;

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
		
		this.repaint();
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(image, 0, 0, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height, null);
	}
}