package diary.gui.graphs;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedHashMap;

public class BarGraphPanel extends Graph
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -636744451243862364L;
	
	public BarGraphPanel(LinkedHashMap<String, Float> dataMap)
	{
		super(dataMap);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		this.drawDataPoints(g, Color.RED, 20);
		
		this.repaint();
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
	
}
