package diary.puzzleimage;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.JLabel;

import diary.constants.Constants;
import giantsweetroll.ImageManager;

public class ImagePiece extends JLabel implements MouseListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5276715106120718243L;
	
	private boolean isColored;
	
	//Constructors
	public ImagePiece(URL url, String name)
	{
		super(ImageManager.getImageIcon(url));
		this.isColored = false;
		this.setName(name);
	}
	
	//Methods
	public void color(boolean color)
	{
		this.isColored = color;
	}
	public void setImage(URL url)
	{
		this.setIcon(ImageManager.getImageIcon(url));
	}
	public boolean isColored()
	{
		return this.isColored;
	}
	
	//Overridden Methods
	@Override
	public void paintComponent(Graphics g)
	{
		if (this.isColored)
		{
			g.setColor(Constants.COLOR_CUSTOM_PAIN_LOCATION_HIGHLIGHT);
			g.drawRect(0, 0, this.getWidth(), this.getHeight());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		this.color(true);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
