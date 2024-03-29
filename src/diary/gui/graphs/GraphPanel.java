package diary.gui.graphs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import diary.constants.Constants;
import diary.constants.Globals;
import diary.constants.PainDataIdentifier;
import diary.constants.PanelName;
import diary.constants.XMLIdentifier;
import diary.data.PainEntryData;
import diary.gui.ActivePatientPanel;
import diary.gui.DateRangePanel;
import diary.gui.GButton;
import diary.interfaces.GUIFunction;
import diary.interfaces.LanguageListener;
import diary.methods.FileOperation;
import diary.methods.Methods;
import diary.methods.PainDataOperation;
import diary.patientdata.PatientData;
import giantsweetroll.gui.swing.Gbm;
import giantsweetroll.gui.swing.ScrollPaneManager;

public class GraphPanel extends JPanel implements ActionListener, LanguageListener, GUIFunction
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5865211789435235389L;
	private JPanel panelGraphConfig,
					panelGraphCategory,
					panelLeft,
					panelBelow, 
					panelBelowLeft, 
					panelBelowCenter;
	private JLabel labCategory;
	private JComboBox<String> comboCategory;
	private Graph graph;
	private JScrollPane scrollGraph;
	private DateRangePanel panelDateRange;
	private GButton butBack, 
					butRefresh, 
					butSwitchGraph, 
					buttonSave, 
					butOptions;
	private GraphSettingsPanel panelGraphSettings;
	private ActivePatientPanel activePatientPanel;
	
	//Condition storing
	private boolean graphReversed;
	
	//Constants
	private final String BACK = "back";
	private final String REFRESH = "refresh";
	private final String SWITCH_GRAPH = "switch graph";
	private final String SAVE = "save";
	private final String OPTIONS = "options";
	
	public GraphPanel()
	{
		this.init();
	}
	
	//Create GUI
	private void init()
	{
		//Initialization
		this.initPanelLeft();
		this.initPanelBelow();
		JScrollPane scroll = ScrollPaneManager.generateDefaultScrollPane(this.panelLeft, 10, 10);
		
		//Properties
		this.setLayout(new BorderLayout());
//		this.setOpaque(false);
		this.setBackground(Color.WHITE);
		this.panelLeft.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		this.panelGraphSettings.setDisplayVoidData(true);
//		scroll.setBorder(BorderFactory.createLineBorder(Color.black));
		
		this.initGraph();
		
		//add to panel
		this.add(scroll, BorderLayout.WEST);
		this.add(this.panelBelow, BorderLayout.SOUTH);
	}
	private void initPanelGraphConfig()
	{
		//Initialization
		this.panelGraphConfig = new JPanel();
		this.activePatientPanel = new ActivePatientPanel();
		this.panelDateRange = new DateRangePanel();
		this.panelGraphSettings = new GraphSettingsPanel(false);
		this.initPanelGraphCategory();
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
//		this.panelGraphConfig.setLayout(new BoxLayout(this.panelGraphConfig, BoxLayout.Y_AXIS));
		this.panelGraphConfig.setLayout(new GridBagLayout());
		this.panelGraphConfig.setOpaque(false);
		this.panelDateRange.dateFrom.autoSetDate();
		this.panelDateRange.dateFrom.setAsDefaultDataThis();
		this.panelDateRange.dateTo.autoSetDate();
		this.panelDateRange.dateTo.setAsDefaultDataThis();
		this.activePatientPanel.setBackground(Color.WHITE);
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.insets = Constants.INSETS_TOP_COMPONENT;
		this.panelGraphConfig.add(this.activePatientPanel, c);
		c.insets = Constants.INSETS_GENERAL;
		Gbm.newGridLine(c);
		this.panelGraphConfig.add(this.panelDateRange, c);
		Gbm.newGridLine(c);
		this.panelGraphConfig.add(this.panelGraphCategory, c);
		Gbm.newGridLine(c);
		this.panelGraphConfig.add(this.panelGraphSettings, c);
	}
	private void initPanelLeft()
	{
		//Initialization
		this.panelLeft = new JPanel();
		this.initPanelGraphConfig();
		
		//Properties
		this.panelLeft.setLayout(new BorderLayout());
		this.panelLeft.setBackground(Constants.COLOR_MAIN_MENU_BACKGROUND);
		
		//Add to panel
		this.panelLeft.add(this.panelGraphConfig, BorderLayout.NORTH);
	}
	private void initPanelGraphCategory()
	{
		//Initialization
		this.panelGraphCategory = new JPanel();
		this.labCategory = new JLabel(Methods.getLanguageText(XMLIdentifier.GRAPH_CATEGORY_LABEL), SwingConstants.RIGHT);
		this.comboCategory = new JComboBox<String>(Methods.getGraphCategories());
		
		//Properties
//		this.panelTopLeft.setOpaque(false);
		this.panelGraphCategory.setOpaque(false);
		this.panelGraphCategory.setLayout(new FlowLayout(FlowLayout.LEFT, Constants.INSETS_BASE, Constants.INSETS_BASE));
		this.comboCategory.setBackground(Color.WHITE);
	//	this.labCategory.setForeground(Color.white);
		
		//Add to panel
		this.panelGraphCategory.add(this.labCategory);					//Category
		this.panelGraphCategory.add(this.comboCategory);				//Category Selection
	}
	private void initPanelBelow()
	{
		//Initialization
		this.panelBelow = new JPanel();
		this.initPanelBelowCenter();
		this.initPanelBelowLeft();
		
		//Properties
		this.panelBelow.setBackground(Constants.COLOR_MAIN_MENU_BACKGROUND);
		this.panelBelow.setLayout(new BorderLayout());
		this.panelBelow.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		
		//Add to panel
		this.panelBelow.add(this.panelBelowLeft, BorderLayout.WEST);
		this.panelBelow.add(this.panelBelowCenter, BorderLayout.CENTER);
	}
	private void initPanelBelowLeft()
	{
		//Initialization
		this.panelBelowLeft = new JPanel();
		this.butBack = new GButton(Methods.getLanguageText(XMLIdentifier.BACK_TEXT));
		
		//Properties
		this.panelBelowLeft.setOpaque(false);
		this.panelBelowLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.butBack.setActionCommand(this.BACK);
		this.butBack.addActionListener(this);
//		this.butBack.setBackground(Constants.COLOR_MAIN_MENU_BUTTONS);
//		this.butBack.setForeground(Color.white);
		this.butBack.setMnemonic(KeyEvent.VK_B);
		
		//add to panel
		this.panelBelowLeft.add(this.butBack);
	}
	private void initPanelBelowCenter()
	{
		//Initialization
		this.panelBelowCenter = new JPanel();
		this.butRefresh = new GButton(Methods.getLanguageText(XMLIdentifier.REFRESH_TEXT));
		this.butSwitchGraph = new GButton(Methods.getLanguageText(XMLIdentifier.SWITCH_TEXT));
		this.buttonSave = new GButton(Methods.getLanguageText(XMLIdentifier.EXPORT_TEXT));
		this.butOptions = new GButton (Methods.getLanguageText(XMLIdentifier.OPTIONS_TEXT));
		
		//Properties
		this.panelBelowCenter.setOpaque(false);
		this.panelBelowCenter.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.butRefresh.setActionCommand(this.REFRESH);
		this.butRefresh.addActionListener(this);
	//	this.butRefresh.setBackground(Constants.COLOR_MAIN_MENU_BUTTONS);
	//	this.butRefresh.setForeground(Color.white);
		this.butRefresh.setMnemonic(KeyEvent.VK_R);
		this.butSwitchGraph.setActionCommand(this.SWITCH_GRAPH);
		this.butSwitchGraph.addActionListener(this);
	//	this.butSwitchGraph.setBackground(Constants.COLOR_MAIN_MENU_BUTTONS);
	//	this.butSwitchGraph.setForeground(Color.white);
		this.butSwitchGraph.setMnemonic(KeyEvent.VK_S);
		this.buttonSave.setActionCommand(this.SAVE);
		this.buttonSave.addActionListener(this);
		this.buttonSave.setToolTipText(Methods.getLanguageText(XMLIdentifier.SAVE_IMAGE_TOOLIP_TEXT));
	//	this.buttonSave.setBackground(Constants.COLOR_MAIN_MENU_BUTTONS);
	//	this.buttonSave.setForeground(Color.white);
		this.buttonSave.setMnemonic(KeyEvent.VK_E);
		this.butOptions.setActionCommand(this.OPTIONS);
		this.butOptions.addActionListener(this);
	//	this.butOptions.setBackground(Constants.COLOR_MAIN_MENU_BUTTONS);
	//	this.butOptions.setForeground(Color.white);
		this.butOptions.setMnemonic(KeyEvent.VK_O);
		
		//add to panel
		this.panelBelowCenter.add(this.buttonSave);
		this.panelBelowCenter.add(this.butOptions);
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
		
		String category = this.comboCategory.getSelectedItem().toString();
		
		List<PainEntryData> list = null;
		try
		{
			list = FileOperation.getListOfEntries(this.activePatientPanel.getSelectedPatientData().getID(), this.panelDateRange.getFromDate(), this.panelDateRange.getToDate());
		}
		catch(NullPointerException ex)
		{
			list = new ArrayList<PainEntryData>();
		}
		
//		System.out.println("Before: " + list.size());
		
		if (Globals.GRAPH_FILTER_PANEL.isRecentMedicationSelected())
		{
			list = PainDataOperation.filter(list, PainDataIdentifier.RECENT_MEDICATION, Globals.GRAPH_FILTER_PANEL.getRecentMedicationFilter());
		}
		
		if (this.panelGraphSettings.isDisplayVoidData())
		{	
			list = PainDataOperation.insertEmptyData(list, this.panelDateRange.getFromDate(), this.panelDateRange.getToDate());
		}
		
//		System.out.println("After: " + list.size());
		
		if (category.equals(Methods.getLanguageText(XMLIdentifier.GRAPH_CATEGORY_ENTRIES_VS_DATE_TEXT)))
		{
			this.graph = new LineGraphPanel(PainDataOperation.getAmountOfEntriesVSDate(list));
			this.graph.setXAxisName(Methods.getLanguageText(XMLIdentifier.DAY_TEXT));
			this.graph.setYAxisName(Methods.getLanguageText(XMLIdentifier.AMOUNT_OF_ENTRIES_TEXT));
		}
		else if (category.equals(Methods.getLanguageText(XMLIdentifier.GRAPH_CATEGORY_INTENSITY_VS_TIME)))
		{
			this.graph = new LineGraphPanel(PainDataOperation.getIntensityVSTime(list));
			this.graph.setXAxisName(Methods.getLanguageText(XMLIdentifier.EPISODE_TEXT));
			this.graph.setYAxisName(Methods.getLanguageText(XMLIdentifier.INTENSITY_LABEL));
		}
		else if (category.equals(Methods.getLanguageText(XMLIdentifier.GRAPH_CATEGORY_DURATION_VS_TIME)))
		{
			this.graph = new LineGraphPanel(PainDataOperation.getDurationVSTime(list));
			this.graph.setXAxisName(Methods.getLanguageText(XMLIdentifier.EPISODE_TEXT));
			this.graph.setYAxisName(Methods.getLanguageText(XMLIdentifier.DURATION_LABEL));
		}
		else if (category.equals(Methods.getLanguageText(XMLIdentifier.GRAPH_CATEGORY_INTENSITY_AVERAGE_VS_DATE_TEXT)))
		{
			this.graph = new LineGraphPanel(PainDataOperation.getAverageIntensityVSDate(list));
			this.graph.setXAxisName(Methods.getLanguageText(XMLIdentifier.DAY_TEXT));
			this.graph.setYAxisName(Methods.getLanguageText(XMLIdentifier.AVERAGE_INTENSITY_TEXT));
		}
		else if (category.equals(Methods.getLanguageText(XMLIdentifier.GRAPH_CATEGORY_DURATION_AVERAGE_VS_DATE_TEXT)))
		{
			this.graph = new LineGraphPanel(PainDataOperation.getAverageDurationVSDate(list));
			this.graph.setXAxisName(Methods.getLanguageText(XMLIdentifier.DAY_TEXT));
			this.graph.setYAxisName(Methods.getLanguageText(XMLIdentifier.AVERAGE_DURATION_TEXT));
		}
		else if (category.equals(Methods.getLanguageText(XMLIdentifier.GRAPH_CATEGORIES_INTENSITY_AVERAGE_VS_MONTH_TEXT)))
		{
			this.graph = new LineGraphPanel(PainDataOperation.getAverageIntensityVSMonth(list));
			this.graph.setXAxisName(Methods.getLanguageText(XMLIdentifier.MONTH_TEXT));
			this.graph.setYAxisName(Methods.getLanguageText(XMLIdentifier.AVERAGE_INTENSITY_TEXT));
		}
		else if (category.equals(Methods.getLanguageText(XMLIdentifier.GRAPH_CATEGORIES_DURATION_AVERAGE_VS_MONTH_TEXT)))
		{
			this.graph = new LineGraphPanel(PainDataOperation.getAverageDurationVSMonth(list));
			this.graph.setXAxisName(Methods.getLanguageText(XMLIdentifier.MONTH_TEXT));
			this.graph.setYAxisName(Methods.getLanguageText(XMLIdentifier.AVERAGE_DURATION_TEXT));
		}
		else if (category.equals(Methods.getLanguageText(XMLIdentifier.GRAPH_CATEGORY_PAIN_KIND_VS_AMOUNT)))
		{
			this.graph = new BarGraphPanel(PainDataOperation.getNumberOfDifferentPainKind(list));
			this.graph.setXAxisName(Methods.getLanguageText(XMLIdentifier.KINDS_OF_HEADPAINS_TEXT));
			this.graph.setYAxisName(Methods.getLanguageText(XMLIdentifier.AMOUNT_TEXT));
		}
		else if (category.equals(Methods.getLanguageText(XMLIdentifier.GRAPH_CATEGORY_TRIGGER_VS_AMOUNT)))
		{
			this.graph = new BarGraphPanel(PainDataOperation.getAmountOfActivity(list));
			this.graph.setXAxisName(Methods.getLanguageText(XMLIdentifier.TRIGGER_TEXT));
			this.graph.setYAxisName(Methods.getLanguageText(XMLIdentifier.AMOUNT_TEXT));
		}
		this.graph.displayDataValues(this.panelGraphSettings.isDataValuesEnabled());
		this.graph.displayDataPoint(this.panelGraphSettings.isDisplayDataPoints());
		this.initGraphScroll(graph);
	}
	private void initReverseGraph()
	{
		try
		{
			this.remove(this.scrollGraph);
		}
		catch(NullPointerException ex) {};
		
		if (this.graph instanceof LineGraphPanel)
		{
			this.graph = new BarGraphPanel(this.graph.getDataMap(), this.graph.getXAxisName(), this.graph.getYAxisName());
		}
		else
		{
			this.graph = new LineGraphPanel(this.graph.getDataMap(), this.graph.getXAxisName(), this.graph.getYAxisName());
			this.graph.displayDataPoint(this.panelGraphSettings.isDisplayDataPoints());
		}
		this.graph.displayDataValues(this.panelGraphSettings.isDataValuesEnabled());
		this.initGraphScroll(graph);
	}

	private void initGraphScroll(Graph graph)
	{
		this.scrollGraph = new JScrollPane(graph, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.scrollGraph.getVerticalScrollBar().setUnitIncrement(20);
		this.scrollGraph.getHorizontalScrollBar().setUnitIncrement(20);
		this.scrollGraph.setOpaque(false);
		this.scrollGraph.getViewport().setOpaque(false);
		
		this.add(this.scrollGraph, BorderLayout.CENTER);
		this.revalidate();
		this.repaint();
	}
	
	public void refreshGraph()
	{
		this.activePatientPanel.refresh();
		if (this.graphReversed)
		{
			this.initReverseGraph();
		}
		else
		{
			this.initGraph();
		}
	}
	
	public ActivePatientPanel getActivePatientPanel()
	{
		return this.activePatientPanel;
	}
	
	//Interfaces
	@Override
	public void actionPerformed(ActionEvent e)
	{
		switch(e.getActionCommand())
			{
			case BACK:
		//		MainFrame.changePanel(Globals.MAIN_MENU);
				Globals.MAIN_FRAME.changePanel(PanelName.MAIN_MENU);
				break;
				
			case REFRESH:
				refreshGraph();
				break;
				
			case SWITCH_GRAPH:
				/*
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
				*/
				this.initReverseGraph();
				break;
				
			case SAVE:
				/*
				ImagePanel imagePanel = new ImagePanel(this.graph, 
														new PatientDataForm(this.activePatientPanel.getSelectedPatientData(), false), 
														new DateRangePanel(this.panelDateRange.getDateRangeMap()));
				Methods.exportPanelImage(imagePanel, false);
				*/
				PatientData patient = this.activePatientPanel.getSelectedPatientData();
				/*
				ImageExportPanel exportImage = new ImageExportPanel(patient.getName(), 
																	patient.getID(), 
																	this.panelDateRange.getDateRangeAsString(), 
																	Globals.GRAPH_FILTER_PANEL.getRecentMedicationFilter(), 
																	new TitledGraph(this.comboCategory.getSelectedItem().toString(), this.graph));
				FileOperation.exportPanelImage(exportImage, true);	*/
				FileOperation.exportImage(Methods.getGraphExportImage(patient.getName(), 
											patient.getID(), 
											this.panelDateRange.getDateRangeAsString(), 
											Globals.GRAPH_FILTER_PANEL.getRecentMedicationFilter(),
											this.comboCategory.getSelectedItem().toString(),
											this.graph.getGraphImage()));
				break;
				
			case OPTIONS:
				JOptionPane.showMessageDialog(null, Globals.GRAPH_FILTER_PANEL, Methods.getLanguageText(XMLIdentifier.OPTIONS_TEXT), JOptionPane.PLAIN_MESSAGE);
				break;
		}
	}

	@Override
	public void revalidateLanguage()
	{
		int categoryIndex = this.comboCategory.getSelectedIndex();
		
		this.labCategory.setText(Methods.getLanguageText(XMLIdentifier.SHOW_TEXT));
		this.comboCategory.setModel(new DefaultComboBoxModel<String>(Methods.getGraphCategories()));
		this.activePatientPanel.revalidateLanguage();
		this.panelDateRange.revalidateLanguage();
		this.butBack.setText(Methods.getLanguageText(XMLIdentifier.BACK_TEXT));
		this.buttonSave.setText(Methods.getLanguageText(XMLIdentifier.EXPORT_TEXT));
		this.butOptions.setText(Methods.getLanguageText(XMLIdentifier.OPTIONS_TEXT));
		this.butRefresh.setText(Methods.getLanguageText(XMLIdentifier.REFRESH_TEXT));
		this.butSwitchGraph.setText(Methods.getLanguageText(XMLIdentifier.SWITCH_TEXT));
		this.refreshGraph();
		this.panelGraphSettings.revalidateLanguage();
//		this.graphConfig.setTitleAt(0, Methods.getLanguageText(XMLIdentifier.SETTINGS_BUTTON_TEXT));
//		this.graphConfig.setTitleAt(1, Methods.getLanguageText(XMLIdentifier.PROFILE_TEXT));
//		this.graphConfig.setTitleAt(2, Methods.getLanguageText(XMLIdentifier.DATE_RANGE_TEXT));
		
		this.comboCategory.setSelectedIndex(categoryIndex);
		
		this.revalidate();
		this.repaint();
	}

	@Override
	public void resetDefaults()
	{}

	@Override
	public void refresh() 
	{
		this.activePatientPanel.refresh();
		this.refreshGraph();
	}
}
