package diary.gui.EntryLog;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import diary.constants.Constants;
import diary.constants.ImageConstants;
import diary.gui.MainFrame;
import diary.methods.Methods;
import giantsweetroll.ImageManager;

public class PainLocationCustomSelection extends JLabel implements MouseListener, MouseMotionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6306460492486489780L;
	
	private Point coordinates;
	
	//Constants
	private final int COORDINATE_POINT_RADIUS = 6;
	
	//Constructors
	public PainLocationCustomSelection()
	{
		//Initialization
		ImageIcon icon = ImageManager.getImageIcon(ImageConstants.PAIN_LOCATION_CUSTOM);
		this.setIcon(Methods.resizeImageByRatio(icon, Methods.getPercentage(icon, Methods.getPercentageValue(MainFrame.frame.getWidth(), 20))));
	
		//Properties
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	//Methods
	public Point getCoordinates()
	{
		return this.coordinates;
	}
	public void resetCoordinates()
	{
		this.coordinates = null;
		this.repaint();
	}
	public void drawCoordinatePoint(Graphics g, Color color, Point coordinate)
	{
		g.setColor(color);
		g.fillOval(coordinate.x - this.COORDINATE_POINT_RADIUS, coordinate.y - this.COORDINATE_POINT_RADIUS, this.COORDINATE_POINT_RADIUS*2, this.COORDINATE_POINT_RADIUS*2);
	}
	public void setCoordinate(int x, int y)
	{
		this.coordinates.setLocation(x, y);
		this.repaint();
	}
	public void setCoordinate(MouseEvent e)
	{
		PointerInfo a = MouseInfo.getPointerInfo();
		this.coordinates = new Point(a.getLocation());
		SwingUtilities.convertPointFromScreen(this.coordinates, e.getComponent());
		if (this.coordinates.x < 0 ||
			this.coordinates.x > this.getWidth() ||
			this.coordinates.y < 0 ||
			this.coordinates.y > this.getHeight())
		{
			this.coordinates = null;
		}
		this.repaint();
	}
	
	//Overriden Methods
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		if (this.isEnabled())
		{
			try
			{
				this.drawCoordinatePoint(g, Constants.COLOR_CUSTOM_PAIN_LOCATION_HIGHLIGHT, this.coordinates);
			}
			catch(NullPointerException ex){}
		}
	}
	
	//Interfaces
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		this.setCoordinate(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) 
	{
		this.setCoordinate(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {}
}
