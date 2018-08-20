package diary.gui.graphs;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedHashMap;

import diary.gui.MainFrame;

public class LineGraphPanel extends Graph
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -224326087999891758L;
	
	public LineGraphPanel(MainFrame frame, LinkedHashMap<String, Double> dataMap)
	{
		super(frame, dataMap);
	}
	public LineGraphPanel(MainFrame frame, LinkedHashMap<String, Double> dataMap, String xAxisName, String yAxisName)
	{
		super(frame, dataMap, xAxisName, yAxisName);
	}
	
	
	//Methods
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		this.drawConnectingLines(g, Color.RED);
		
		this.repaint();
	}
	
	//Drawing parts of the graph
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


	@Override
	protected void drawDataPoints(Graphics g, Color c, int width) 
	{
		g.setColor(c);
		for (int i=0; i<this.dataPoints.size(); i++)
		{
			g.fillOval(this.dataPoints.get(i).x - width/2, this.dataPoints.get(i).y - width/2, width, width);
		}
	}
	@Override
	public void revalidateLanguage() {}
}
