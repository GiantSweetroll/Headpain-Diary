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
		super.drawAxesWithDefaultSettings();		//To hide connecting lines on the X-Axis
		g.drawImage(this.getGraphImage(), 0, 0, null);
	}
	
	//Drawing parts of the graph
	private void drawConnectingLines(Graphics g, Color color)
	{
		g.setColor(color);
		
		for (int i=0; i<this.dataPoints.size()-1; i++)
		{
			g.drawLine(this.dataPoints.get(i).x, 
						this.dataPoints.get(i).y, 
						this.dataPoints.get(i+1).x,
						this.dataPoints.get(i+1).y);
			
//			//Create cosine function
//			Point current = this.dataPoints.get(i);
//			Point next = this.dataPoints.get(i+1);
////			if (current.y == super.axesOrigin.y)
////			{
////				continue;
////			}
//			if (current.y == next.y)
//			{
//				g.drawLine(current.x, current.y, next.x, next.y);
//				continue;
//			}
//			
//			double period = (next.x - current.x)*2d;
//			double a = (double)(current.y - next.y)/2d;
//			double b = 2d*Math.PI/period;
//			double d = (double)(current.y + next.y)/2d;
//			double c = (double)((current.x > next.x ? next.x : current.x) + (current.x > next.x ? current.x : next.x))/2d;
//			for (int x=current.x; x<next.x; x++)
//			{
//				int y = (int)GNumbers.round(Methods.getCosineFunction(a, b, c, d, x), 0);
//				if (y < super.axesOrigin.y)		//If y coordinate is above the y coordinate of the origin
//				{
//					//g.fillOval(x, y, super.DATA_POINT_WIDTH, super.DATA_POINT_WIDTH);
//					g.drawLine(x, y, x+1, (int)GNumbers.round(Methods.getCosineFunction(a, b, c, d, x+1), 0));
//				}
//			}
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
			g.fillOval(this.dataPoints.get(i).x - width/2, 
						this.dataPoints.get(i).y - width/2, 
						width, 
						width);
/*			System.out.println("Data point: " + this.dataPoints.get(i).x + ", " + this.dataPoints.get(i).y);
			System.out.println("Y-Axis: " + this.yAxisMarkerPoints.get(this.yAxisMarkerPoints.size()-1).y);
			System.out.println("Y-Axis Tip: " + (this.axesOrigin.y - this.axesLength.y));
			System.out.println();	*/
		}
	}
	@Deprecated
	@Override
	public void revalidateLanguage() {}
}
