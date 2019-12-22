package diary.gui.graphs;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedHashMap;

public class LineGraphPanel extends Graph
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -224326087999891758L;
	
	public LineGraphPanel(LinkedHashMap<String, Double> dataMap)
	{
		super(dataMap);
	}
	public LineGraphPanel(LinkedHashMap<String, Double> dataMap, String xAxisName, String yAxisName)
	{
		super(dataMap, xAxisName, yAxisName);
	}
	
	
	//Methods
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		this.drawConnectingLines(super.graph2DImage, Color.RED);
		this.drawAxes(super.graph2DImage, Color.BLACK, super.GRAPH_IMAGE_SIZE.width-super.axesPaddingWithPanelEdgeSides, super.axesPaddingWithPanelEdgeTop);
		g.drawImage(this.getGraphImage(), 0, 0, null);
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
			if (this.dataPoints.get(i).y == this.axesOrigin.y)	//Skip data with 0 values
			{
				continue;
			}
			g.fillOval(this.dataPoints.get(i).x - width/2, this.dataPoints.get(i).y - width/2, width, width);
/*			System.out.println("Data point: " + this.dataPoints.get(i).x + ", " + this.dataPoints.get(i).y);
			System.out.println("Y-Axis: " + this.yAxisMarkerPoints.get(this.yAxisMarkerPoints.size()-1).y);
			System.out.println("Y-Axis Tip: " + (this.axesOrigin.y - this.axesLength.y));
			System.out.println();	*/
		}
	}
	@Override
	public void revalidateLanguage() {}
}
