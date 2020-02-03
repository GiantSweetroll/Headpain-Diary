package diary.gui.graphs;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
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
	private BufferedImage graphImage;
	
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
	protected Graphics2D graph2DImage;
	
	//Options
	protected boolean enableDataValueMarkers;
	protected boolean displayDataPoint;
	protected boolean showGraphLinesOfY;
	protected boolean showGraphLinesOfX;
	
	//Constants
	protected final Dimension GRAPH_IMAGE_SIZE = new Dimension(1280, 1024);
	protected final int DATA_POINT_WIDTH = 10;
	protected final int AXES_POINTERS_LENGTH = 15;
	protected final int MARKER_LABEL_PADDING = 5;
	protected final int MAX_MARKERS_IN_Y_AXIS = 10;
	protected final int MAX_MARKERS_IN_X_AXIS = 4;	
	protected final int GENERAL_PADDING = 10;
	protected final int DECIMAL_PLACES = 1;
	protected final int X_AXIS_NAME_PADDING = 20;
	protected final int Y_AXIS_NAME_PADDING = 20;
	private final int STROKE_SIZE = 1;
	
	//Constructors
	public Graph(LinkedHashMap<String, Double> dataMap) 
	{
		this.init(dataMap, "", "");
	}
	public Graph(LinkedHashMap<String, Double> dataMap, String xAxisName, String yAxisName)
	{
		this.init(dataMap, xAxisName, yAxisName);
	}
	public Graph(String xAxisName, String yAxisName)
	{
		this.init(null, xAxisName, yAxisName);
	}
	public Graph()
	{
		this.init(null, "", "");
	}
	
	//Methods
	private void init(LinkedHashMap<String, Double> dataMap, String xAxisName, String yAxisName)
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
		this.graphImage = new BufferedImage(this.GRAPH_IMAGE_SIZE.width, this.GRAPH_IMAGE_SIZE.height, BufferedImage.TYPE_INT_ARGB);
		this.graph2DImage = this.graphImage.createGraphics();
		this.graph2DImage.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		this.graph2DImage.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
									        RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);	//Text Anti-Aliasing
		this.graph2DImage.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
											RenderingHints.VALUE_ANTIALIAS_ON);				//Anti-aliasing for the graph lines
		
		this.enableDataValueMarkers = false;
		this.displayDataPoint = true;
		this.maxMarkersXAxis = this.MAX_MARKERS_IN_X_AXIS;
		this.showGraphLinesOfX = true;
		this.showGraphLinesOfY = true;
		
		if (dataMap != null)
		{
			for (Map.Entry<String, Double> entry : dataMap.entrySet())
			{
				this.xAxisLabels.add(entry.getKey());
				this.yAxisValues.add(entry.getValue());
			}
		}
		
		this.setOpaque(false);		
		this.setPreferredSize(this.GRAPH_IMAGE_SIZE);
	}
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
	//	g.setFont(Constants.FONT_GENERAL);
		
		g.setColor(Color.WHITE);
//		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		this.graph2DImage.setColor(Color.WHITE);
		this.graph2DImage.setStroke(new BasicStroke(this.STROKE_SIZE));
//		this.graph2DImage.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//							                RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		this.graph2DImage.fillRect(0, 0, this.GRAPH_IMAGE_SIZE.width, this.GRAPH_IMAGE_SIZE.height);

		this.axesOrigin = new Point(this.axesPaddingWithPanelEdgeSides, this.GRAPH_IMAGE_SIZE.height-this.axesPaddingWithPanelEdgeBelow);
		this.drawAxesWithDefaultSettings();
		try
		{
			this.generateDataPoints(this.yAxisValues);
			this.drawAxesMarkers(this.graph2DImage, Color.BLACK);
			this.drawXAxisMarkerLabels(this.graph2DImage, Constants.COLOR_GRAPH_AXES_MARKER_LABELS, Constants.FONT_GENERAL_BOLD);
			this.drawYAxisMarkerLabels(this.graph2DImage, Constants.COLOR_GRAPH_AXES_MARKER_LABELS, Constants.FONT_GENERAL_BOLD);
			this.drawGraphLines(this.graph2DImage, Color.LIGHT_GRAY);
			this.drawAxisNames(this.graph2DImage, Color.BLACK, Color.BLACK, this.xAxisName, this.yAxisName);
			this.drawRecentMedicationText(this.graph2DImage, Color.BLACK);
			this.graph2DImage.setFont(Constants.FONT_GENERAL);
			if (this.enableDataValueMarkers)
			{
				this.drawDataValues(this.graph2DImage, Color.BLACK);
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
			
			if (this.displayDataPoint)
			{
				this.drawDataPoints(this.graph2DImage, Color.GREEN, this.DATA_POINT_WIDTH);
			}
		}
		catch(ArithmeticException ex)
		{
//			ex.printStackTrace();
		}
		
//		g.drawImage(this.graphImage, 
//					this.GRAPH_IMAGE_SIZE.width,
//					0, 
//					this.GRAPH_IMAGE_SIZE.width,
//					this.GRAPH_IMAGE_SIZE.height, 
//					0, 
//					0, 
//					0, 
//					this.GRAPH_IMAGE_SIZE.height, 
//					Color.WHITE, 
//					null);
//		g.drawImage(this.graphImage, 0, 0, null);
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
	public BufferedImage getGraphImage()
	{
		return this.graphImage;
	}
	public void setGraphImageSize(int width, int height)
	{
		this.graphImage = Methods.resize(this.graphImage, width, height);
	}
	public void setGraphImageSize(Dimension dim)
	{
		this.setGraphImageSize(dim.width, dim.height);
	}
	protected void drawAxesWithDefaultSettings()
	{
		this.drawAxes(this.graph2DImage, Color.BLACK, this.GRAPH_IMAGE_SIZE.width-this.axesPaddingWithPanelEdgeSides, this.axesPaddingWithPanelEdgeTop);
	}
	
	//Draw Sections
	protected abstract void drawDataPoints(Graphics g, Color c, int width);
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
		double xDiff = GNumbers.round(this.axesLength.x/dataValues.size(), 0);
		//Set distance between each unit increment in the y-axis
//		double incr = (float)this.axesLength.y/(float)Methods.getHighestValue(dataValues);
//		System.out.println("Increment: " + incr);
////		double yDiff = (double)Math.round(incr);
//		double yDiff = incr;
		double yDiff = (float)this.axesLength.y/(float)Methods.getHighestValue(dataValues);
//		System.out.println(this.axesLength.y + " / " + Methods.getHighestValue(dataValues));
//		System.out.println("yDiff: " + yDiff);
		
		//Translate into coordinate points
		this.dataPoints = new ArrayList<Point>();
		int xPos = (int)GNumbers.round(xDiff, 1);
//		double maxY = 0d;
//		double minY = Double.MAX_VALUE;
//		int axesYStart = this.axesOrigin.y;
//		int axesYEnd = this.axesOrigin.y - this.axesLength.y;	//Y coordinate of where the Y-Axis ends
		for (int i=0; i<dataValues.size(); i++)
		{
			Double doubleCoordinate = (double)this.axesOrigin.y - yDiff*dataValues.get(i);
			int yCoordinate = (int)GNumbers.round(doubleCoordinate, this.DECIMAL_PLACES);
//			if (yCoordinate > maxY)
//			{
//				maxY = yCoordinate;
//			}
//			else if (yCoordinate < minY)
//			{
//				minY = yCoordinate;
//			}
//			if (yCoordinate < axesYEnd)	//If the data point is more than the Y-Axis, force it to follow
//			{
//				yCoordinate = axesYEnd;
//			}
			this.dataPoints.add(new Point(this.axesOrigin.x + xPos, yCoordinate));
			xPos+=xDiff;
//			MessageManager.printLine("(" + this.dataPoints.get((int)i).x + ", " + this.dataPoints.get((int)i).y);
		}
//		System.out.println("Y-axis start: " + axesYStart);
//		System.out.println("Y-axis end: " + axesYEnd);
//		System.out.println("Largest y: " + maxY);
//		System.out.println("Smallest y: " + minY);
//		System.out.println();
	}
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
		double yDiff = (double)this.axesLength.y/(double)this.MAX_MARKERS_IN_Y_AXIS;
	//	MessageManager.printLine("yDiff: " + this.axesLength.y + "/" + this.MAX_MARKERS_IN_Y_AXIS + " = " + yDiff);
		this.yAxisMarkerPoints = new ArrayList<Point>();
		for (int i=1; i<=this.MAX_MARKERS_IN_Y_AXIS; i++)
		{
			int x = this.axesOrigin.x - this.AXES_POINTERS_LENGTH/2;
			int y = this.axesOrigin.y - (int)GNumbers.round((yDiff*(double)i), 0);
			g.drawLine(x, 
						y, 
						this.axesOrigin.x + this.AXES_POINTERS_LENGTH/2, 
						y);
			
			this.yAxisMarkerPoints.add(new Point(x, y));	//Take note of the coordinate position of each Y-Axis markers
//			System.out.println("Y marker: " + this.yAxisMarkerPoints.get(i-1).y);
		}
//		System.out.println();
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
			Double value = GNumbers.round(this.yAxisValues.get(i), this.DECIMAL_PLACES);
			if (value <= 0d)
			{
				continue;
			}
			String text = Double.toString(value);
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
				g.drawLine(this.axesOrigin.x+this.STROKE_SIZE, 	//+ SROKE_SIZE so that it does not get drawn on the Y-Axis
							marker.y, 
							this.xAxisEndCoordinates.x, 
							marker.y);
			}
		}
		
		if (this.showGraphLinesOfX)		//Displaying Graph Lines of X
		{
			for (Point marker : this.dataPoints)
			{
				g.drawLine(marker.x, 
							this.axesOrigin.y-this.STROKE_SIZE, //- SROKE_SIZE so that it does not get drawn on the X-Axis
							marker.x, 
							this.yAxisEndCoordinates.y);
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
