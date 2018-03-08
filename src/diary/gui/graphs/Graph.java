package diary.gui.graphs;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import diary.constants.Constants;
import diary.methods.Methods;
import giantsweetroll.numbers.GNumbers;

public abstract class Graph extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4719770210747311133L;
	
	private String xAxisName;
	private String yAxisName;
	
	protected Point axesOrigin;
	protected Point axesLength;
	protected List<Point> dataPoints;
	protected List<String> xAxisLabels;
	protected List<Float> yAxisValues;
	protected List<Point> yAxisMarkerPoints;
	
	//Options
	protected boolean enableDataValueMarkers;
	protected boolean displayDataPoint;
	protected int maxMarkersXAxis;
	
	//Constants
	protected final int AXES_PADDING_WITH_PANEL_EDGE = 50;
	protected final int DATA_POINT_WIDTH = 10;
	protected final int AXES_POINTERS_LENGTH = 10;
	protected final int MARKER_LABEL_PADDING = 5;
	protected final int MAX_MARKERS_IN_Y_AXIS = 10;
	protected final int MAX_MARKERS_IN_X_AXIS = 5;
	protected final int GENERAL_PADDING = 10;
	protected final int DECIMAL_PLACES = 1;
	protected final int X_AXIS_NAME_PADDING = 20;
	protected final int Y_AXIS_NAME_PADDING = 5;
	
	public Graph(LinkedHashMap<String, Float> dataMap) 
	{
		this.xAxisName = "";
		this.yAxisName = "";
		this.xAxisLabels = new ArrayList<String>();
		this.yAxisValues = new ArrayList<Float>();
		this.yAxisMarkerPoints = new ArrayList<Point>();
		this.dataPoints = new ArrayList<Point>();
		
		this.enableDataValueMarkers = false;
		this.displayDataPoint = true;
		this.maxMarkersXAxis = this.MAX_MARKERS_IN_X_AXIS;
		
		for (Map.Entry<String, Float> entry : dataMap.entrySet())
		{
			this.xAxisLabels.add(entry.getKey());
			this.yAxisValues.add(entry.getValue());
		}
		
		this.setOpaque(false);
	}
	
	//Methods
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
	//	g.setFont(Constants.FONT_GENERAL);
		
		this.axesOrigin = new Point(this.AXES_PADDING_WITH_PANEL_EDGE, this.getHeight()-this.AXES_PADDING_WITH_PANEL_EDGE);
		this.drawAxes(g, Color.BLACK, this.getWidth()-this.AXES_PADDING_WITH_PANEL_EDGE, this.AXES_PADDING_WITH_PANEL_EDGE);
		try
		{
			if (this.displayDataPoint)
			{
				this.drawDataPoints(g, Color.GREEN, this.DATA_POINT_WIDTH);
			}

			this.generateDataPoints(this.yAxisValues);
			this.drawAxesMarkers(g, Color.BLACK);
			this.drawXAxisMarkerLabels(g, Constants.COLOR_GRAPH_AXES_MARKER_LABELS, Constants.FONT_GENERAL_BOLD);
			this.drawYAxisMarkerLabels(g, Constants.COLOR_GRAPH_AXES_MARKER_LABELS, Constants.FONT_GENERAL_BOLD);
	//		this.drawAxisNames(g, Color.BLACK, Color.black, this.xAxisName, this.yAxisName);
	//		g.setFont(Constants.FONT_GENERAL);
			if (this.enableDataValueMarkers)
			{
				this.drawDataValues(g, Color.BLACK);
			}
		}
		catch(ArithmeticException ex)
		{
//			ex.printStackTrace();
		}
	}
	protected void setMaxMarkersInXAxis(int max)
	{
		this.maxMarkersXAxis = max;
	}
	protected void setXAxisName(String text)
	{
		this.xAxisName = text;
		this.repaint();
	}
	protected void setYAxisName(String text)
	{
		this.yAxisName = text;
		this.repaint();
	}
	protected String getXAxisName()
	{
		return this.xAxisName;
	}
	protected String getYAxisName()
	{
		return this.yAxisName;
	}
	
	//Draw Sections
	protected void drawAxes(Graphics g, Color c, int xEnd, int yEnd)
	{
		g.setColor(c);
		
		//Draw X-Axis
		g.drawLine(this.axesOrigin.x, this.axesOrigin.y, xEnd, this.axesOrigin.y);
		
		//Draw Y-Axis
		g.drawLine(this.axesOrigin.x, this.axesOrigin.y, this.axesOrigin.x, yEnd);
		
		//Get length
		this.axesLength = new Point(xEnd - this.axesOrigin.x, this.axesOrigin.y - yEnd);
	}
	protected void generateDataPoints(List<Float> dataValues)
	{
		//Set Distance between each data entry in the x-axis
		float xDiff = this.axesLength.x/dataValues.size();
		//Set distance between each unit increment in the y-axis
		float yDiff = this.axesLength.y/Methods.getHighestValue(dataValues);
		
		//Translate into coordinate points
		this.dataPoints = new ArrayList<Point>();
		int xPos = (int)GNumbers.round(xDiff, 1);
		for (float i=0; i<dataValues.size(); i++)
		{
			float roundedDataValue = GNumbers.round(dataValues.get((int)i), this.DECIMAL_PLACES);
			float floatCoordinate = (float)this.axesOrigin.y - yDiff*roundedDataValue;
			this.dataPoints.add(new Point(this.axesOrigin.x + xPos, (int)GNumbers.round(floatCoordinate, this.DECIMAL_PLACES)));
			xPos+=xDiff;
		}
	}
	protected abstract void drawDataPoints(Graphics g, Color c, int width);
	protected void drawAxesMarkers(Graphics g, Color c)
	{
		g.setColor(c);
		
		//Draw X-Axis Marker Labels
		int diff = 0;
		
		if (this.xAxisLabels.size()<=this.maxMarkersXAxis)
		{
			diff = 1;
		} 
		else
		{
			diff = (int)GNumbers.round(this.xAxisLabels.size()/this.maxMarkersXAxis, 1);
			if (diff==1)
			{
				diff = 2;
			}
		}
		
		for (int i=0; i<this.dataPoints.size(); i+=diff)
		{
			g.drawLine(this.dataPoints.get(i).x, 
						this.axesOrigin.y + this.AXES_POINTERS_LENGTH/2, 
						this.dataPoints.get(i).x, 
						this.axesOrigin.y - this.AXES_POINTERS_LENGTH/2);
		}
		
		//Draw Y-Axis Marker Labels
		int yDiff = this.axesLength.y/this.MAX_MARKERS_IN_Y_AXIS;
		this.yAxisMarkerPoints = new ArrayList<Point>();
		for (int i=1; i<=this.MAX_MARKERS_IN_Y_AXIS; i++)
		{
			int x = this.axesOrigin.x - this.AXES_POINTERS_LENGTH/2;
			int y = this.axesOrigin.y - (yDiff*i);
			g.drawLine(x, 
						y, 
						this.axesOrigin.x + this.AXES_POINTERS_LENGTH/2, 
						y);
			
			this.yAxisMarkerPoints.add(new Point(x, y));	//Take note of the coordinate position of each Y-Axis markers
		}
	}
	protected void drawYAxisMarkerLabels(Graphics g, Color c, Font font)
	{
		g.setColor(c);
		g.setFont(font);
		
		float highestVal = Methods.getHighestValue(this.yAxisValues);		//Get highest value
		float diff = highestVal/(float)this.MAX_MARKERS_IN_Y_AXIS;			//Get unit increment
		
		for (float i = 0; i<this.yAxisMarkerPoints.size(); i++)
		{
//			String text = Float.toString(diff*(i+1));
//			System.out.println(text);
//			text = text.substring(0, text.indexOf(".") + 2);		//One Decimal Place
			String text = Float.toString(Methods.round(diff*(i+1), this.DECIMAL_PLACES));		//Rounded to X Decimal Place
			int textWidth = g.getFontMetrics().stringWidth(text);
			
			g.drawString(text, 
							this.axesOrigin.x - this.AXES_POINTERS_LENGTH - this.MARKER_LABEL_PADDING - textWidth, 
							this.yAxisMarkerPoints.get((int)i).y + 4);
		}
	}
	protected void drawXAxisMarkerLabels(Graphics g, Color c, Font font)
	{
		g.setColor(c);
		g.setFont(font);
		
		int diff = 0;
		
		if (this.xAxisLabels.size()<=this.maxMarkersXAxis)
		{
			diff = 1;
		}
		else
		{
			diff = (int)GNumbers.round(this.xAxisLabels.size()/this.maxMarkersXAxis, 1);
			if (diff==1)
			{
				diff = 2;
			}
		}
		
		for (int i = 0; i<this.dataPoints.size(); i+=diff)
		{
			String text = this.xAxisLabels.get(i);
			int textWidth = g.getFontMetrics().stringWidth(text);
			g.drawString(text, 
							this.dataPoints.get(i).x - textWidth/2, 
							this.axesOrigin.y + this.AXES_POINTERS_LENGTH + this.MARKER_LABEL_PADDING);
		}
	}
	protected void drawDataValues(Graphics g, Color c)
	{
		g.setColor(c);
		
		for (int i=0; i<this.yAxisValues.size(); i++)
		{
			String text = Float.toString(Methods.round(this.yAxisValues.get(i), this.DECIMAL_PLACES));
			int textWidth = g.getFontMetrics().stringWidth(text);
			
			g.drawString(text, this.dataPoints.get(i).x - textWidth/2, this.dataPoints.get(i).y - this.GENERAL_PADDING);
		}
	}
	protected void drawAxisNames(Graphics g, Color colx, Color coly, String x, String y)
	{
		//Draw X-Axis name
		g.setColor(colx);
		
		int textLength = g.getFontMetrics().stringWidth(x);
		g.drawString(x, this.axesOrigin.x + (this.axesLength.x/2) - textLength/2, this.axesOrigin.y + this.AXES_POINTERS_LENGTH + this.MARKER_LABEL_PADDING + this.X_AXIS_NAME_PADDING);
	
		//Draw Y-Axis Name
		textLength = g.getFontMetrics().stringWidth(y);
		Graphics2D g2  = (Graphics2D)g;
		g2.setColor(coly);
	//	g2.drawString(y, this.axesOrigin.x, this.axesOrigin.y - this.axesLength.y/2);
		AffineTransform at = new AffineTransform();
		at.setToRotation(Math.toRadians(90));
		g2.setTransform(at);
	//	g2.drawString(y, this.axesOrigin.x, this.axesOrigin.y - this.axesLength.y/2);
	}
	
	//Graph Settings
	protected void displayDataValues(boolean bool)
	{
		this.enableDataValueMarkers = bool;
		this.repaint();
	}
	protected void displayDataPoint(boolean show)
	{
		this.displayDataPoint = show;
		this.repaint();
	}
}
