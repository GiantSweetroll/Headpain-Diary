package diary.puzzleimage;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.JLabel;

import diary.constants.Constants;
import diary.constants.Globals;
import diary.methods.Methods;
import giantsweetroll.ImageManager;

public class ImagePiece extends JLabel implements MouseListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5276715106120718243L;
	
	private boolean isColored;
	
	//Constructors
	public ImagePiece(URL url, String name, boolean canColor)
	{
		super(Methods.resizeImageByRatio(ImageManager.getImageIcon(url), 20));
//		super(ImageManager.getImageIcon(url));
		this.isColored = false;
		this.setName(name);
		if (canColor)
		{
			this.addMouseListener(this);
		}
	}
	
	//Methods
	public void color(boolean color)		//Set component to color or not
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
	public void color()					//Color the component
	{
		if (this.isEnabled()) 
		{
			this.color(!this.isColored());
			this.repaint();
		}
	}
	
	//Overridden Methods
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (this.isColored())
		{
//			System.out.println("Toodles");
			g.setColor(Constants.COLOR_CUSTOM_PAIN_LOCATION_HIGHLIGHT);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		//TODO
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		if (Globals.mouseDown)
		{
			this.color();
		}
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		//TODO
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		if (e.getButton() == MouseEvent.BUTTON1)
		{
			Globals.mouseDown = true;
			this.color();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		if (e.getButton() == MouseEvent.BUTTON1)
		{
			Globals.mouseDown = false;
		}
	}
}
