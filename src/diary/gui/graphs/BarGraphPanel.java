package diary.gui.graphs;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.LinkedHashMap;

import giantsweetroll.numbers.GNumbers;

public class BarGraphPanel extends Graph
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -636744451243862364L;
	
	private final Color BAR_COLOR = Color.RED;
	
	//Constructors
	public BarGraphPanel(LinkedHashMap<String, Double> dataMap)
	{
		super(dataMap);
		super.setMaxMarkersInXAxis(dataMap.size());
	}
	public BarGraphPanel(LinkedHashMap<String, Double> dataMap, String xAxisName, String yAxisName)
	{
		super(dataMap, xAxisName, yAxisName);
		super.setMaxMarkersInXAxis(dataMap.size());
	}
	
	//Methods
	private boolean xAxisMarkerLabelHaveSpace(Graphics g)
	{
		//Predict amount of space needed
		long pixlesNeeded = 0;
		for (String item : super.xAxisLabels)
		{
			pixlesNeeded += g.getFontMetrics().stringWidth(item);
		}
		
		//Check if there's room
		if (pixlesNeeded > super.getWidth())
		{
			return false;
		}
		
		return true;
	}
	
	//Overridden Methods
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		this.drawDataPoints(this.graph2DImage, this.BAR_COLOR, 10);			//This is required so that the bars shows up in the first run
		g.drawImage(this.getGraphImage(), 0, 0, null);
	}

	@Override
	protected void drawDataPoints(Graphics g, Color c, int width)
	{
		g.setColor(c);
		
		for (int i=0; i<super.dataPoints.size(); i++)
		{
			g.fillRect(super.dataPoints.get(i).x-width/2, super.dataPoints.get(i).y, width, super.axesOrigin.y - super.dataPoints.get(i).y);
		}
	}	
	
	@Override
	protected void drawXAxisMarkerLabels(Graphics g, Color c, Font font)
	{
		g.setColor(c);
		g.setFont(font);
		
		if (this.xAxisMarkerLabelHaveSpace(g))
		{
			//Draw Labels Horizontally
			int diff = 0;
			super.maxXAxisMarkerLabelHeight = 0;
			
			if (super.xAxisLabels.size()<=super.maxMarkersXAxis)
			{
				diff = 1;
			}
			else
			{
				diff = (int)GNumbers.round(super.xAxisLabels.size()/super.maxMarkersXAxis, 1);
				if (diff==1)
				{
					diff = 2;
				}
			}
			
			for (int i = 0; i<this.dataPoints.size(); i+=diff)
			{
				String text = super.xAxisLabels.get(i);
				int textWidth = g.getFontMetrics().stringWidth(text);
				int x = super.dataPoints.get(i).x - textWidth/2;
				int y = super.axesOrigin.y + super.AXES_POINTERS_LENGTH + super.MARKER_LABEL_PADDING;
				g.drawString(text, x, y);
				
				//Get max height
				if (g.getFontMetrics().getHeight() > super.maxXAxisMarkerLabelHeight)
				{
					super.maxXAxisMarkerLabelHeight = g.getFontMetrics().getHeight();
				}
			}
		}
		else
		{
			//Draw Labels Vertically
			int diff = 0;
			super.maxXAxisMarkerLabelHeight = 0;
			
			if (super.xAxisLabels.size()<=super.maxMarkersXAxis)
			{
				diff = 1;
			}
			else
			{
				diff = (int)GNumbers.round(super.xAxisLabels.size()/super.maxMarkersXAxis, 1);
				if (diff==1)
				{
					diff = 2;
				}
			}
			
			Graphics2D g2 = (Graphics2D)g;
			AffineTransform defaultAt = g2.getTransform();
			AffineTransform at = new AffineTransform();
			at.rotate(-Math.toRadians(90));
			g2.setTransform(at);
			
			for (int i = 0; i<this.dataPoints.size(); i+=diff)
			{
				String text = super.xAxisLabels.get(i);
				int textWidth = g.getFontMetrics().stringWidth(text);
				int x = super.dataPoints.get(i).x + g2.getFontMetrics().getHeight()/5;
				int y = super.axesOrigin.y + super.AXES_POINTERS_LENGTH + super.MARKER_LABEL_PADDING + textWidth;
		//		g2.drawString(text, x, y);
				g2.drawString(text, -y, x);
				
				//Get max height
				if (g.getFontMetrics().stringWidth(text) > super.maxXAxisMarkerLabelHeight)
				{
					super.maxXAxisMarkerLabelHeight = g.getFontMetrics().stringWidth(text);
				}
			}
			g2.setTransform(defaultAt);
		}
	}
	@Deprecated
	@Override
	public void revalidateLanguage() 
	{
		
	}
}
