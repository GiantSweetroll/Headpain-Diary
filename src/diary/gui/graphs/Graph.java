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
import diary.constants.Globals;
import diary.constants.XMLIdentifier;
import diary.interfaces.LanguageListener;
import diary.methods.Methods;
import giantsweetroll.numbers.GNumbers;

public abstract class Graph extends JPanel implements LanguageListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4719770210747311133L;
	
	private String xAxisName;
	private String yAxisName;
	private LinkedHashMap<String, Double> dataMap;
	
	protected Point axesOrigin;
	protected Point axesLength;
	protected Point xAxisEndCoordinates;
	protected Point yAxisEndCoordinates;
	protected List<Point> dataPoints;
	protected List<String> xAxisLabels;
	protected List<Double> yAxisValues;
	protected List<Point> yAxisMarkerPoints;
	protected int maxYAxisMarkerLabelLength;
	protected int maxXAxisMarkerLabelHeight;
	protected int yAxisNameTextHeight;
	protected int maxMarkersXAxis;
	protected int axesPaddingWithPanelEdgeSides = 50;
	protected int axesPaddingWithPanelEdgeBelow = 50;
	protected int axesPaddingWithPanelEdgeTop = 50;
	protected int xAxisNameTextHeight;
	
	//Options
	protected boolean enableDataValueMarkers;
	protected boolean displayDataPoint;
	protected boolean showGraphLinesOfY;
	protected boolean showGraphLinesOfX;
	
	//Constants
	protected final int DATA_POINT_WIDTH = 10;
	protected final int AXES_POINTERS_LENGTH = 15;
	protected final int MARKER_LABEL_PADDING = 5;
	protected final int MAX_MARKERS_IN_Y_AXIS = 10;
	protected final int MAX_MARKERS_IN_X_AXIS = 4;	
	protected final int GENERAL_PADDING = 10;
	protected final int DECIMAL_PLACES = 1;
	protected final int X_AXIS_NAME_PADDING = 20;
	protected final int Y_AXIS_NAME_PADDING = 20;
	
	//Constructors
	public Graph(LinkedHashMap<String, Double> dataMap) 
	{
		this.dataMap = dataMap;
		this.xAxisName = "";
		this.yAxisName = "";
		this.maxYAxisMarkerLabelLength = 0;
		this.maxXAxisMarkerLabelHeight = 0;
		this.yAxisNameTextHeight = 0;
		this.xAxisLabels = new ArrayList<String>();
		this.yAxisValues = new ArrayList<Double>();
		this.yAxisMarkerPoints = new ArrayList<Point>();
		this.dataPoints = new ArrayList<Point>();
		
		this.enableDataValueMarkers = false;
		this.displayDataPoint = true;
		this.maxMarkersXAxis = this.MAX_MARKERS_IN_X_AXIS;
		this.showGraphLinesOfX = true;
		this.showGraphLinesOfY = true;
		
		for (Map.Entry<String, Double> entry : dataMap.entrySet())
		{
			this.xAxisLabels.add(entry.getKey());
			this.yAxisValues.add(entry.getValue());
		}
		
		this.setOpaque(false);
	}
	public Graph(LinkedHashMap<String, Double> dataMap, String xAxisName, String yAxisName)
	{
		this.dataMap = dataMap;
		this.xAxisName = xAxisName;
		this.yAxisName = yAxisName;
		this.maxYAxisMarkerLabelLength = 0;
		this.maxXAxisMarkerLabelHeight = 0;
		this.yAxisNameTextHeight = 0;
		this.xAxisLabels = new ArrayList<String>();
		this.yAxisValues = new ArrayList<Double>();
		this.yAxisMarkerPoints = new ArrayList<Point>();
		this.dataPoints = new ArrayList<Point>();
		
		this.enableDataValueMarkers = false;
		this.displayDataPoint = true;
		this.maxMarkersXAxis = this.MAX_MARKERS_IN_X_AXIS;
		this.showGraphLinesOfX = true;
		this.showGraphLinesOfY = true;
		
		for (Map.Entry<String, Double> entry : dataMap.entrySet())
		{
			this.xAxisLabels.add(entry.getKey());
			this.yAxisValues.add(entry.getValue());
		}
		
		this.setOpaque(false);		
	}
	public Graph(String xAxisName, String yAxisName)
	{
	//	this.dataMap = dataMap;
		this.xAxisName = xAxisName;
		this.yAxisName = yAxisName;
		this.maxYAxisMarkerLabelLength = 0;
		this.maxXAxisMarkerLabelHeight = 0;
		this.yAxisNameTextHeight = 0;
		this.xAxisLabels = new ArrayList<String>();
		this.yAxisValues = new ArrayList<Double>();
		this.yAxisMarkerPoints = new ArrayList<Point>();
		this.dataPoints = new ArrayList<Point>();
		
		this.enableDataValueMarkers = false;
		this.displayDataPoint = true;
		this.maxMarkersXAxis = this.MAX_MARKERS_IN_X_AXIS;
		this.showGraphLinesOfX = true;
		this.showGraphLinesOfY = true;
		
		/*
		for (Map.Entry<String, Double> entry : dataMap.entrySet())
		{
			this.xAxisLabels.add(entry.getKey());
			this.yAxisValues.add(entry.getValue());
		}		*/
		
		this.setOpaque(false);
	}
	public Graph()
	{
	//	this.dataMap = dataMap;
		this.xAxisName = "";
		this.yAxisName = "";
		this.maxYAxisMarkerLabelLength = 0;
		this.maxXAxisMarkerLabelHeight = 0;
		this.yAxisNameTextHeight = 0;
		this.xAxisLabels = new ArrayList<String>();
		this.yAxisValues = new ArrayList<Double>();
		this.yAxisMarkerPoints = new ArrayList<Point>();
		this.dataPoints = new ArrayList<Point>();
		
		this.enableDataValueMarkers = false;
		this.displayDataPoint = true;
		this.maxMarkersXAxis = this.MAX_MARKERS_IN_X_AXIS;
		this.showGraphLinesOfX = true;
		this.showGraphLinesOfY = true;
		
		/*
		for (Map.Entry<String, Double> entry : dataMap.entrySet())
		{
			this.xAxisLabels.add(entry.getKey());
			this.yAxisValues.add(entry.getValue());
		}		*/
		
		this.setOpaque(false);		
	}
	
	//Methods
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
	//	g.setFont(Constants.FONT_GENERAL);
		
		this.axesOrigin = new Point(this.axesPaddingWithPanelEdgeSides, this.getHeight()-this.axesPaddingWithPanelEdgeBelow);
		this.drawAxes(g, Color.BLACK, this.getWidth()-this.axesPaddingWithPanelEdgeSides, this.axesPaddingWithPanelEdgeTop);
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
			this.drawGraphLines(g, Color.LIGHT_GRAY);
			this.drawAxisNames(g, Color.BLACK, Color.BLACK, this.xAxisName, this.yAxisName);
			this.drawRecentMedicationText(g, Color.BLACK);
			g.setFont(Constants.FONT_GENERAL);
			if (this.enableDataValueMarkers)
			{
				this.drawDataValues(g, Color.BLACK);
			}
			
			if (this.getBehindAxesDifferenceWithPanelEdgeLeft()>0)
			{
				this.axesPaddingWithPanelEdgeSides += this.getBehindAxesDifferenceWithPanelEdgeLeft();
				this.repaint();
			}
			else if (this.getBelowAxesDifferenceWithPanelEdgeBelow()>0)
			{
				this.axesPaddingWithPanelEdgeBelow += this.getBelowAxesDifferenceWithPanelEdgeBelow();
				this.repaint();
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
	protected LinkedHashMap<String, Double> getDataMap()
	{
		return this.dataMap;
	}
	protected int getMinWidth()
	{
		return this.yAxisNameTextHeight + 
				this.Y_AXIS_NAME_PADDING + 
				this.maxYAxisMarkerLabelLength + 
				this.MARKER_LABEL_PADDING + 
				this.AXES_POINTERS_LENGTH +
				this.axesLength.x + 
				this.axesPaddingWithPanelEdgeSides;
	}
	protected int getBehindAxesDifferenceWithPanelEdgeLeft()
	{
		int usage = this.yAxisNameTextHeight + this.Y_AXIS_NAME_PADDING + this.maxYAxisMarkerLabelLength + this.MARKER_LABEL_PADDING + this.AXES_POINTERS_LENGTH;
		return usage - this.axesPaddingWithPanelEdgeSides;
	}
	protected int getBelowAxesDifferenceWithPanelEdgeBelow()
	{
		int usage = this.xAxisNameTextHeight + this.X_AXIS_NAME_PADDING + this.maxXAxisMarkerLabelHeight + this.MARKER_LABEL_PADDING + this.AXES_POINTERS_LENGTH;
		return usage - this.axesPaddingWithPanelEdgeBelow;
	}
	protected void setXAxisLabels(List<String> labels)
	{
		this.xAxisLabels = labels;
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
		
		//Get end coordinates
		this.xAxisEndCoordinates = new Point(xEnd, this.axesOrigin.y);
		this.yAxisEndCoordinates = new Point(this.axesOrigin.x, yEnd);
	}
	protected void generateDataPoints(List<Double> dataValues)
	{
		//Set Distance between each data entry in the x-axis
		double xDiff = this.axesLength.x/dataValues.size();
		//Set distance between each unit increment in the y-axis
		double yDiff = this.axesLength.y/Methods.getHighestValue(dataValues);
		
		//Translate into coordinate points
		this.dataPoints = new ArrayList<Point>();
		int xPos = (int)GNumbers.round(xDiff, 1);
		for (double i=0; i<dataValues.size(); i++)
		{
			Double roundedDataValue = GNumbers.round(dataValues.get((int)i), this.DECIMAL_PLACES);
			Double DoubleCoordinate = (double)this.axesOrigin.y - yDiff*roundedDataValue;
			this.dataPoints.add(new Point(this.axesOrigin.x + xPos, (int)GNumbers.round(DoubleCoordinate, this.DECIMAL_PLACES)));
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
		
		double highestVal = Methods.getHighestValue(this.yAxisValues);		//Get highest value
		double diff = highestVal/(double)this.MAX_MARKERS_IN_Y_AXIS;			//Get unit increment
		
		this.maxYAxisMarkerLabelLength = 0;
		
		for (double i = 0; i<this.yAxisMarkerPoints.size(); i++)
		{
//			String text = Double.toString(diff*(i+1));
//			System.out.println(text);
//			text = text.substring(0, text.indexOf(".") + 2);		//One Decimal Place
			String text = Double.toString(GNumbers.round(diff*(i+1), this.DECIMAL_PLACES));		//Rounded to X Decimal Place
			int textWidth = g.getFontMetrics().stringWidth(text);
			
			if (textWidth > this.maxYAxisMarkerLabelLength)
			{
				this.maxYAxisMarkerLabelLength = textWidth;
			}
			
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
		this.maxXAxisMarkerLabelHeight = 0;
		
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
			int x = this.dataPoints.get(i).x - textWidth/2;
			int y = this.axesOrigin.y + this.AXES_POINTERS_LENGTH + this.MARKER_LABEL_PADDING;
			g.drawString(text, x, y);
			
			//Get max height
			if (g.getFontMetrics().getHeight() > this.maxXAxisMarkerLabelHeight)
			{
				this.maxXAxisMarkerLabelHeight = g.getFontMetrics().getHeight();
			}
		}
	}
	protected void drawDataValues(Graphics g, Color c)
	{
		g.setColor(c);
		
		g.setFont(Constants.FONT_GENERAL_BOLD);
		for (int i=0; i<this.yAxisValues.size(); i++)
		{
			String text = Double.toString(GNumbers.round(this.yAxisValues.get(i), this.DECIMAL_PLACES));
			int textWidth = g.getFontMetrics().stringWidth(text);
			
			g.drawString(text, this.dataPoints.get(i).x - textWidth/2, this.dataPoints.get(i).y - this.GENERAL_PADDING);
		}
		g.setFont(Constants.FONT_GENERAL);
	}
	protected void drawAxisNames(Graphics g, Color colx, Color coly, String x, String y)
	{
		g.setFont(Constants.FONT_GENERAL_A_BIT_BIGGER);
		this.xAxisNameTextHeight = g.getFontMetrics().getHeight();
		//Draw X-Axis name
		g.setColor(colx);
		
		int textLength = g.getFontMetrics().stringWidth(x);
		g.drawString(x, 
						this.axesOrigin.x + (this.axesLength.x/2) - textLength/2, 
						this.axesOrigin.y + this.AXES_POINTERS_LENGTH + this.MARKER_LABEL_PADDING + this.maxXAxisMarkerLabelHeight + this.X_AXIS_NAME_PADDING);
	
		//Draw Y-Axis Name
		textLength = g.getFontMetrics().stringWidth(y);
		Graphics2D g2  = (Graphics2D)g;
		g2.setColor(coly);
//		g2.drawString(y, this.axesOrigin.x - this.MARKER_LABEL_PADDING - this.maxYAxisMarkerLabelLength - this.Y_AXIS_NAME_PADDING, this.axesOrigin.y - this.axesLength.y/2 + textLength/2);
		this.yAxisNameTextHeight = g2.getFontMetrics().getHeight();
		AffineTransform at = new AffineTransform();
		AffineTransform defaultAt = g2.getTransform();
		at.rotate(-Math.toRadians(90));
		g2.setTransform(at);
	//	g2.drawString(y, (this.axesOrigin.y - this.axesLength.y/2 + textLength/2)*-1, (this.axesLength.y-this.axesLength.y/2+textLength/2)/4 - this.MARKER_LABEL_PADDING - this.maxYAxisMarkerLabelLength*1.8f);
		g2.drawString(y, 
						(this.axesOrigin.y - this.axesLength.y/2 + textLength/2)*-1, 
						this.axesOrigin.x - this.AXES_POINTERS_LENGTH - this.MARKER_LABEL_PADDING - this.maxYAxisMarkerLabelLength - this.Y_AXIS_NAME_PADDING);
		g2.setTransform(defaultAt);
	}
	protected void drawGraphLines(Graphics g, Color c)
	{
		g.setColor(c);
		
		if (this.showGraphLinesOfY)		//Displaying Graph Lines of Y
		{
			for (Point marker : this.yAxisMarkerPoints)
			{
				g.drawLine(this.axesOrigin.x, marker.y, this.xAxisEndCoordinates.x, marker.y);
			}
		}
		
		if (this.showGraphLinesOfX)
		{
			for (Point marker : this.dataPoints)
			{
				g.drawLine(marker.x, this.axesOrigin.y, marker.x, this.yAxisEndCoordinates.y);
			}
		}
	}
	protected void drawRecentMedicationText(Graphics g, Color c)
	{
		if (Globals.GRAPH_FILTER_PANEL.isRecentMedicationSelected())
		{
			g.setColor(c);
			
			g.drawString(Methods.getLanguageText(XMLIdentifier.RECENT_MEDICATION_LABEL) + ": " + Globals.GRAPH_FILTER_PANEL.getRecentMedicationFilter(), 
							this.axesOrigin.x, 
							this.axesOrigin.y + this.AXES_POINTERS_LENGTH + this.MARKER_LABEL_PADDING + this.maxXAxisMarkerLabelHeight + this.X_AXIS_NAME_PADDING);
		}
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
	protected void displayGraphLinesOfY(boolean show)
	{
		this.showGraphLinesOfY = show;
		this.repaint();
	}
	protected void displayGraphLinesOfX(boolean show)
	{
		this.showGraphLinesOfX = show;
		this.repaint();
	}
}
