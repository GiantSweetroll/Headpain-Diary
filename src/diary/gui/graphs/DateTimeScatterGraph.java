package diary.gui.graphs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import diary.constants.Constants;
import diary.constants.XMLIdentifier;
import diary.data.PainEntryData;
import diary.methods.Methods;

public class DateTimeScatterGraph extends Graph
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1977863279972882018L;
	
	//Constants
	public static final int DAY = 0,
							MONTH = 1,
							YEAR = 2;
	
	private int format;
	
	public DateTimeScatterGraph(List<PainEntryData> entries, int format)
	{
		this.format = format;
		this.changeXAxisNameBasedOnFormat();
		this.setYAxisName(Methods.getLanguageText(XMLIdentifier.TIME_TEXT));
	}

	//Private Methods
	private void changeXAxisNameBasedOnFormat()
	{
		if (format == DAY)
		{
			this.setXAxisName(Methods.getLanguageText(XMLIdentifier.DAY_TEXT));
		}
		else if (format == MONTH)
		{
			this.setXAxisName(Methods.getLanguageText(XMLIdentifier.MONTH_TEXT));
		}
		else if (format == YEAR)
		{
			this.setXAxisName(Methods.getLanguageText(XMLIdentifier.YEAR_TEXT));
		}
	}
	private List<String> getXAxisLabels(List<PainEntryData> entries)
	{
		List<String> list = new ArrayList<String>();
		Set<String> set = new HashSet<String>();
		
		if (this.format == DAY)
		{
			for (PainEntryData entry : entries)
			{
				set.add(entry.getFullDate());
			}
		}
		else if (this.format == MONTH)
		{
			for (PainEntryData entry : entries)
			{
				set.add(entry.getMonthAndYear());
			}
		}
		else if (this.format == YEAR)
		{
			for (PainEntryData entry : entries)
			{
				set.add(Integer.toString(entry.getDate().getYear()));
			}
		}
		
		list.addAll(set);
		return list;
	}
	
	//Overridden Methods
	@Override
	public void revalidateLanguage() 
	{
		// TODO Auto-generated method stub
		this.changeXAxisNameBasedOnFormat();
		this.setYAxisName(Methods.getLanguageText(XMLIdentifier.TIME_TEXT));
	}

	@Override
	protected void drawDataPoints(Graphics g, Color c, int width) 
	{
		// TODO Auto-generated method stub
		
	}
	
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
			this.drawAxisNames(g, Color.BLACK, Color.BLACK, this.getXAxisName(), this.getYAxisName());
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
}
