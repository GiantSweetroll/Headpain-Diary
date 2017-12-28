package diary.gui.graphs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import diary.methods.Methods;
import giantsweetroll.message.MessageManager;

public class LineGraphPanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -224326087999891758L;
	
	List<String> xAxisLabels;
	List<Integer> yAxisValues;
	List<Point> dataPoints; 
	Point axesOrigin;
	
	//Constants
	private final int AXES_PADDING_WITH_PANEL_EDGE = 50;
	private final int DATA_POINT_RADIUS = 10;
	private final int AXES_POINTERS_LENGTH = 10;
	private final int MARKER_LABEL_PADDING = 5;
	
	public LineGraphPanel(LinkedHashMap<String, Integer> dataMap)
	{
		this.xAxisLabels = new ArrayList<String>();
		this.yAxisValues = new ArrayList<Integer>();
		this.dataPoints = new ArrayList<Point>();
		
		for (Map.Entry<String, Integer> entry : dataMap.entrySet())
		{
			this.xAxisLabels.add(entry.getKey());
			this.yAxisValues.add(entry.getValue());
		}
	}
	
	
	//Methods
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		this.axesOrigin = new Point(this.AXES_PADDING_WITH_PANEL_EDGE, this.getHeight()-this.AXES_PADDING_WITH_PANEL_EDGE);
		
		this.drawAxes(g, Color.BLACK, 
						this.axesOrigin,
						this.getWidth()-this.AXES_PADDING_WITH_PANEL_EDGE, 
						this.AXES_PADDING_WITH_PANEL_EDGE);
		try
		{
			this.drawDataPoints(g, Color.GREEN, 
					this.yAxisValues, 
					this.axesOrigin, 
					this.getWidth()-this.AXES_PADDING_WITH_PANEL_EDGE, 
					this.AXES_PADDING_WITH_PANEL_EDGE, 
					this.DATA_POINT_RADIUS);

			this.drawAxesMarkers(g, Color.BLACK);
			this.drawXAxisMarkerLabels(g, Color.BLUE);
			this.drawYAxisMarkerLabels(g, Color.BLUE);
			this.drawConnectingLines(g, Color.RED);
		}
		catch(ArithmeticException ex)
		{
//			ex.printStackTrace();
		};
	}
	
	//Drawing parts of the graph
	private void drawAxes(Graphics g, Color c, Point origin, int xLength, int yLength)
	{
		g.setColor(c);
		
		//Draw X-Axis
		g.drawLine(origin.x, origin.y, xLength, origin.y);
		
		//Draw Y-Axis
		g.drawLine(origin.x, origin.y, origin.x, yLength);
	}
	private void drawDataPoints(Graphics g, Color c, List<Integer> data, Point origin, int xAxisEnd, int yAxisEnd, int diameter)
	{
		//Set distance between each data point in the x-axis
		int xDiff = (xAxisEnd - origin.x)/data.size();
		//Set distance between each data increment in the y-axis
		int yDiff = (yAxisEnd - origin.y)/Methods.getHighestValue(data);
		
		//Translate into coordinate points
		this.dataPoints = new ArrayList<Point>();
		int xPos = xDiff;
		for (int i=0; i<data.size(); i++)
		{
			this.dataPoints.add(new Point (xPos, yAxisEnd - yDiff*data.get(i)));
			xPos+=xDiff;
		}
		
		for (int i=0; i<this.dataPoints.size(); i++)
		{
			MessageManager.printLine("Data: " + data.get(i));
			MessageManager.printLine("Coordinate y: " + dataPoints.get(i));
		}
		
		//Draw
		g.setColor(c);
		for (int i=0; i<this.dataPoints.size(); i++)
		{
			g.fillOval(this.dataPoints.get(i).x - diameter/2, this.dataPoints.get(i).y - diameter/2, diameter, diameter);
		}
	}
	private void drawAxesMarkers(Graphics g, Color c)
	{
		g.setColor(c);
		for (int i=0; i<this.dataPoints.size(); i++)
		{
			//Draw x-axis markers
			g.drawLine(this.dataPoints.get(i).x, 
						this.axesOrigin.y + this.AXES_POINTERS_LENGTH/2, 
						this.dataPoints.get(i).x, 
						this.axesOrigin.y - this.AXES_POINTERS_LENGTH/2);
			
			//Draw y-axis markers
			g.drawLine(this.axesOrigin.x - this.AXES_POINTERS_LENGTH/2, 
						this.dataPoints.get(i).y, 
						this.axesOrigin.x + this.AXES_POINTERS_LENGTH/2, 
						this.dataPoints.get(i).y);
		}
	}
	private void drawYAxisMarkerLabels(Graphics g, Color c)
	{
		g.setColor(c);
		
		for (int i = 0; i<this.dataPoints.size(); i++)
		{
			String text = Integer.toString(this.yAxisValues.get(i));
			g.drawString(text, 
							this.axesOrigin.x - this.AXES_POINTERS_LENGTH - this.MARKER_LABEL_PADDING - text.length(), 
							this.dataPoints.get(i).y);
		}
	}
	private void drawXAxisMarkerLabels(Graphics g, Color c)
	{
		g.setColor(c);
		
		for (int i = 0; i<this.dataPoints.size(); i++)
		{
			String text = this.xAxisLabels.get(i);
			g.drawString(text, 
							this.dataPoints.get(i).x - text.length(), 
							this.axesOrigin.y + this.AXES_POINTERS_LENGTH + this.MARKER_LABEL_PADDING);
		}
	}
	private void drawConnectingLines(Graphics g, Color c)
	{
		g.setColor(c);
		
		for (int i=0; i<this.dataPoints.size()-1; i++)
		{
			g.drawLine(this.dataPoints.get(i).x, 
						this.dataPoints.get(i).y, 
						this.dataPoints.get(i+1).x,
						this.dataPoints.get(i+1).y);
		}
	}
}
