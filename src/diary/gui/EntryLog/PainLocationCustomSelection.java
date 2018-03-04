package diary.gui.EntryLog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import diary.constants.Constants;
import diary.constants.ImageConstants;
import diary.gui.MainFrame;
import diary.methods.Methods;
import giantsweetroll.ImageManager;
import giantsweetroll.numbers.GNumbers;

public class PainLocationCustomSelection extends JLabel implements MouseListener, MouseMotionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6306460492486489780L;
	
	private List<Point> coordinates;
	private ImageIcon icon;
	private Dimension originalImageSize;
	
	//Constants
	private final int COORDINATE_POINT_RADIUS = 6;
	
	//Conditionals
	private int imageScale;
	
	//Constructors
	public PainLocationCustomSelection(int scaleFromOriginal)
	{
		//Initialization
		this.imageScale = scaleFromOriginal;
		icon = ImageManager.getImageIcon(ImageConstants.PAIN_LOCATION_CUSTOM);
		this.originalImageSize = new Dimension(icon.getIconWidth(), icon.getIconHeight());
		this.coordinates = new ArrayList<Point>();
		this.setIcon(Methods.resizeImageByRatio(icon, Methods.getPercentage(icon, Methods.getPercentageValue(MainFrame.frame.getWidth(), this.imageScale))));
	
		//Properties
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	//Methods
	public List<Point> getCoordinates()
	{
		return this.coordinates;
	}
	public Point convertPointToOriginalImage(Point point)
	{
		int x = (int)GNumbers.round((((float)point.x)*((float)originalImageSize.getWidth()))/((float)this.icon.getIconWidth()), 0);
		int y = (int)GNumbers.round((((float)point.y)*((float)originalImageSize.getHeight()))/((float)this.icon.getIconHeight()), 0);
		
		return new Point(x, y);
	}
	public Point convertPointFromOriginalImage(Point point)
	{
		int x = (int)GNumbers.round((((float)point.x)*((float)this.icon.getIconWidth()))/((float)originalImageSize.getWidth()), 0);
		int y = (int)GNumbers.round((((float)point.y)*((float)this.icon.getIconHeight()))/((float)originalImageSize.getHeight()), 0);
		
		return new Point(x, y);
	}
	public void resetCoordinates()
	{
		this.coordinates = new ArrayList<Point>();
		this.repaint();
	}
	public void drawCoordinatePoint(Graphics g, Color color, Point coordinate)
	{
		g.setColor(color);
		g.fillOval(coordinate.x - this.COORDINATE_POINT_RADIUS, coordinate.y - this.COORDINATE_POINT_RADIUS, this.COORDINATE_POINT_RADIUS*2, this.COORDINATE_POINT_RADIUS*2);
	}
	public void setCoordinate(Point point)
	{
		this.appendCoordinate(point);
		this.repaint();
	}
	public void setCoordinates(List<Point> points, boolean addToExisting)
	{
		if (addToExisting)
		{
			this.appendCoordinate(points);
		}
		else
		{
			this.coordinates = points;
		}
		
		this.repaint();
	}
	public void setCoordinate(MouseEvent e)
	{
		PointerInfo a = MouseInfo.getPointerInfo();
		Point point = new Point(a.getLocation());
		SwingUtilities.convertPointFromScreen(point, e.getComponent());
		if (point.x < 0 ||
			point.x > this.getWidth() ||
			point.y < 0 ||
			point.y > this.getHeight())
		{
			point = null;
		}
		
		if (point != null)
		{
			this.appendCoordinate(point);
		}
		
		this.repaint();
	}
	public void appendCoordinate(Point point)
	{
		for (Point pos : this.coordinates)		//Check for existing position
		{
			if (pos.x == point.x && pos.y == point.y)
			{
				break;		//If found, don't add
			}
		}
		this.coordinates.add(point);
	}
	public void appendCoordinate(List<Point> points)
	{
		//Check for duplicates
		for (int i=0; i<this.coordinates.size(); i++)
		{
			for (int a=0; a<points.size(); a++)
			{
				if (this.coordinates.get(i).x == points.get(a).x &&
					this.coordinates.get(i).y == points.get(a).y)
				{
					points.remove(a);
					a=-1;
					continue;
				}
			}
		}
		
		this.coordinates.addAll(points);
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
				for (Point point : this.coordinates)
				{
					this.drawCoordinatePoint(g, Constants.COLOR_CUSTOM_PAIN_LOCATION_HIGHLIGHT, point);
				}
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
