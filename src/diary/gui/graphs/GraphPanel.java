package diary.gui.graphs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import diary.PainEntryData;
import diary.constants.Constants;
import diary.constants.XMLIdentifier;
import diary.gui.DateRangePanel;
import diary.gui.MainFrame;
import diary.gui.MainMenu;
import diary.methods.FileOperation;
import diary.methods.PainDataOperation;
import giantsweetroll.gui.swing.Gbm;

public class GraphPanel extends JPanel implements ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5865211789435235389L;
	private JPanel panelTop, panelTopLeft, panelBelow, panelBelowLeft, panelBelowCenter;
	private JLabel labCategory;
	private JComboBox<String> comboCategory;
	private Graph graph;
	private JScrollPane scrollGraph;
	private DateRangePanel panelDateRange;
	private JButton butBack, butRefresh, butSwitchGraph;
	private GraphSettingsPanel panelGraphSettings;
	
	//Condition storing
	private boolean graphReversed;
	
	//Constants
	private final String BACK = "back";
	private final String REFRESH = "refresh";
	private final String SWITCH_GRAPH = "switch graph";
	
	public GraphPanel()
	{
		this.init();
	}
	
	//Create GUI
	private void init()
	{
		//Initialization
		this.initPanelTop();
		this.initPanelBelow();
		
		//Properties
		this.setLayout(new BorderLayout());
		this.setOpaque(false);
		
		this.initGraph();
		
		//add to panel
		this.add(this.panelTop, BorderLayout.NORTH);
		this.add(this.panelBelow, BorderLayout.SOUTH);
	}
	private void initPanelTop()
	{
		//Initialization
		this.panelTop = new JPanel();
		this.initPanelTopLeft();
		this.panelDateRange = new DateRangePanel();
		
		//Properties
		this.panelTop.setOpaque(false);
		this.panelTop.setLayout(new BorderLayout());
		this.panelDateRange.dateFrom.autoSetDate();
		this.panelDateRange.dateFrom.setAsDefaultDataThis();
		this.panelDateRange.dateTo.autoSetDate();
		this.panelDateRange.dateTo.setAsDefaultDataThis();

		//Add to panel
		this.panelTop.add(this.panelTopLeft, BorderLayout.WEST);
		this.panelTop.add(this.panelDateRange, BorderLayout.EAST);
	}
	private void initPanelTopLeft()
	{
		//Initialization
		this.panelTopLeft = new JPanel();
		this.labCategory = new JLabel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.GRAPH_CATEGORY_LABEL), SwingConstants.RIGHT);
		this.comboCategory = new JComboBox<String>(Constants.GRAPH_CATEGORIES);
		this.panelGraphSettings = new GraphSettingsPanel();
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		this.panelTopLeft.setOpaque(false);
		this.panelTopLeft.setLayout(new GridBagLayout());
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_TOP_COMPONENT;
		this.panelTopLeft.add(this.labCategory, c);					//Category
		Gbm.nextGridColumn(c);
		this.panelTopLeft.add(this.comboCategory, c);				//Category Selection
		Gbm.newGridLine(c);
		c.gridwidth = 2;
		this.panelTopLeft.add(this.panelGraphSettings, c);			//Graph Settings Panel
	}
	private void initPanelBelow()
	{
		//Initialization
		this.panelBelow = new JPanel();
		this.initPanelBelowCenter();
		this.initPanelBelowLeft();
		
		//Properties
		this.panelBelow.setOpaque(false);
		this.panelBelow.setLayout(new BorderLayout());
		
		//Add to panel
		this.panelBelow.add(this.panelBelowLeft, BorderLayout.WEST);
		this.panelBelow.add(this.panelBelowCenter, BorderLayout.CENTER);
	}
	private void initPanelBelowLeft()
	{
		//Initialization
		this.panelBelowLeft = new JPanel();
		this.butBack = new JButton(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.BACK_TEXT));
		
		//Properties
		this.panelBelowLeft.setOpaque(false);
		this.panelBelowLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.butBack.setActionCommand(this.BACK);
		this.butBack.addActionListener(this);
		
		//add to panel
		this.panelBelowLeft.add(this.butBack);
	}
	private void initPanelBelowCenter()
	{
		//Initialization
		this.panelBelowCenter = new JPanel();
		this.butRefresh = new JButton(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.REFRESH_TEXT));
		this.butSwitchGraph = new JButton(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SWITCH_TEXT));
		
		//Properties
		this.panelBelowCenter.setOpaque(false);
		this.panelBelowCenter.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.butRefresh.setActionCommand(this.REFRESH);
		this.butRefresh.addActionListener(this);
		this.butSwitchGraph.setActionCommand(this.SWITCH_GRAPH);
		this.butSwitchGraph.addActionListener(this);
		
		//add to panel
		this.panelBelowCenter.add(this.butRefresh);
		this.panelBelowCenter.add(this.butSwitchGraph);
	}
	private void initGraph()
	{
		try
		{
			this.remove(this.scrollGraph);
		}
		catch(NullPointerException ex) {};
		
		this.graphReversed = false;
		
		LinkedHashMap<String, LinkedHashMap<String, String>> dateRangeMap = this.panelDateRange.getDateRangeMap();		//Get date range
		String category = this.comboCategory.getSelectedItem().toString();
		
		List<PainEntryData> list = FileOperation.getListOfEntries(dateRangeMap.get(DateRangePanel.FROM), dateRangeMap.get(DateRangePanel.TO));
		
		if (category.equals(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.GRAPH_CATEGORY_PAIN_VS_DATE_TEXT)))
		{
			this.graph = new LineGraphPanel(PainDataOperation.getAmountOfHeadPainsVSDate(list));
		}
		else if (category.equals(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.GRAPH_CATEGORY_ENTRIES_VS_DATE_TEXT)))
		{
			this.graph = new LineGraphPanel(PainDataOperation.getAmountOfEntriesVSDate(list));
		}
		else if (category.equals(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.GRAPH_CATEGORY_INTENSITY_AVERAGE_VS_TIME_TEXT)))
		{
			this.graph = new LineGraphPanel(PainDataOperation.getIntensityAverageVSTime(list));
		}
		else if (category.equals(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.GRAPH_CATEGORY_DURATION_AVERAGE_VS_TIME_TEXT)))
		{
			this.graph = new LineGraphPanel(PainDataOperation.getDurationAverageVSTime(list));
		}
		else if (category.equals(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.GRAPH_CATEGORY_PAIN_KIND_VS_DATE)))
		{
			this.graph = new BarGraphPanel(PainDataOperation.getNumberOfDifferentPainKind(list));
		}
		else if (category.equals(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.GRAPH_CATEGORY_PAIN_LOCATION_VS_DATE)))
		{
			this.graph = new BarGraphPanel(PainDataOperation.getNumberOfDifferentPainLocations(list));
		}
		else if (category.equals(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.GRAPH_CATEGORY_ACTIVITY_VS_DATE)))
		{
			this.graph = new BarGraphPanel(PainDataOperation.getAmountOfActivity(list));
		}
		this.graph.displayDataValues(this.panelGraphSettings.isDataValuesEnabled());
		this.initGraphScroll(graph);
		
		this.add(this.scrollGraph, BorderLayout.CENTER);
		this.revalidate();
		this.repaint();
	}
	private void initReverseGraph()
	{
		try
		{
			this.remove(this.scrollGraph);
		}
		catch(NullPointerException ex) {};
		
		LinkedHashMap<String, LinkedHashMap<String, String>> dateRangeMap = this.panelDateRange.getDateRangeMap();		//Get date range
		String category = this.comboCategory.getSelectedItem().toString();
		
		List<PainEntryData> list = FileOperation.getListOfEntries(dateRangeMap.get(DateRangePanel.FROM), dateRangeMap.get(DateRangePanel.TO));
		
		if (category.equals(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.GRAPH_CATEGORY_PAIN_VS_DATE_TEXT)))
		{
			this.graph = new BarGraphPanel(PainDataOperation.getAmountOfHeadPainsVSDate(list));
		}
		else if (category.equals(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.GRAPH_CATEGORY_ENTRIES_VS_DATE_TEXT)))
		{
			this.graph = new BarGraphPanel(PainDataOperation.getAmountOfEntriesVSDate(list));
		}
		else if (category.equals(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.GRAPH_CATEGORY_INTENSITY_AVERAGE_VS_TIME_TEXT)))
		{
			this.graph = new BarGraphPanel(PainDataOperation.getIntensityAverageVSTime(list));
		}
		else if (category.equals(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.GRAPH_CATEGORY_DURATION_AVERAGE_VS_TIME_TEXT)))
		{
			this.graph = new BarGraphPanel(PainDataOperation.getDurationAverageVSTime(list));
		}
		else if (category.equals(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.GRAPH_CATEGORY_PAIN_KIND_VS_DATE)))
		{
			this.graph = new LineGraphPanel(PainDataOperation.getNumberOfDifferentPainKind(list));
		}
		else if (category.equals(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.GRAPH_CATEGORY_PAIN_LOCATION_VS_DATE)))
		{
			this.graph = new LineGraphPanel(PainDataOperation.getNumberOfDifferentPainLocations(list));
		}
		else if (category.equals(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.GRAPH_CATEGORY_ACTIVITY_VS_DATE)))
		{
			this.graph = new LineGraphPanel(PainDataOperation.getAmountOfActivity(list));
		}
		this.graph.displayDataValues(this.panelGraphSettings.isDataValuesEnabled());
		this.initGraphScroll(graph);
		
		this.add(this.scrollGraph, BorderLayout.CENTER);
		this.revalidate();
		this.repaint();
	}
	
	private void initGraphScroll(Graph graph)
	{
		this.scrollGraph = new JScrollPane(graph, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.scrollGraph.setOpaque(false);
		this.scrollGraph.getViewport().setOpaque(false);
	}

	//Interfaces
	@Override
	public void actionPerformed(ActionEvent e)
	{
		switch(e.getActionCommand())
			{
			case BACK:
				MainFrame.changePanel(new MainMenu());
				break;
				
			case REFRESH:
				this.initGraph();
				break;
				
			case SWITCH_GRAPH:
				if(this.graphReversed)
				{
					this.graphReversed = false;
					this.initGraph();
				}
				else
				{
					this.graphReversed = true;
					this.initReverseGraph();
				}
				break;
		}
	}
}
