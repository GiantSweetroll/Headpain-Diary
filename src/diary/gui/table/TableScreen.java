package diary.gui.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import diary.constants.Constants;
import diary.constants.PanelName;
import diary.constants.XMLIdentifier;
import diary.data.PainEntryData;
import diary.gui.ActivePatientPanel;
import diary.gui.CustomDialog;
import diary.gui.DateRangePanel;
import diary.gui.MainFrame;
import diary.gui.MainFramePanel;
import diary.methods.FileOperation;
import diary.methods.Methods;
import giantsweetroll.gui.swing.Gbm;
import giantsweetroll.gui.swing.ScrollPaneManager;

public class TableScreen extends MainFramePanel implements ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6429132267457024269L;
	
	private JPanel panelTop, panelTopLeft, panelBelowLeft, panelBelowCenter, panelBelowRight, panelBelow;
	private TablePanel table;
	private DateRangePanel panelDateRange;
	private JComboBox<String> comboFilter;
	private JTextField tfFilter;
	private JButton butFilter, butBack, butDelete, butSelect, butSave, butRefresh;
	private JLabel labFilter, labKeyword, labGuide;
	private List<String> selectedEntryIDs;
	private List<PainEntryData> entries;
	private PainEntryData activeEntry;
	private ActivePatientPanel activePatientPanel;
	private JTabbedPane tableConfig;
	
	//Constants
	private final String FILTER = "filter";
	private final String BACK = "back";
	private final String DELETE = "delete";
	private final String SELECT = "select";
	private final String SAVE = "save image";
	private final String REFRESH = "refresh";
	
	public TableScreen(MainFrame frame)
	{
		super(frame);
		this.init();
	}
	
	//Methods
	//Initialize GUI
	private void init()
	{
		//Initialization
		this.initPanelBelow();
		this.initPanelTop();
		JScrollPane scroll = ScrollPaneManager.generateDefaultScrollPane(this.panelTop, 10, 10);
		
		//Properties
		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);
	//	scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scroll.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		this.initTable();
		
		//Add to panel
		this.add(scroll, BorderLayout.NORTH);
		this.add(this.panelBelow, BorderLayout.SOUTH);
	}
	private void initPanelBelowLeft()
	{
		//Initialization
		this.panelBelowLeft = new JPanel();
		this.butBack = new JButton(Methods.getLanguageText(XMLIdentifier.BACK_TEXT));
		
		//Properties
		this.panelBelowLeft.setOpaque(false);
		this.panelBelowLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.butBack.setActionCommand(this.BACK);
		this.butBack.setToolTipText(Methods.getLanguageText(XMLIdentifier.TABLE_TOOLTIPS_BUTTON_BACK_TEXT));
		this.butBack.addActionListener(this);
		this.butBack.setBackground(Constants.COLOR_MAIN_MENU_BUTTONS);
		this.butBack.setForeground(Color.white);
		
		//add to panel
		this.panelBelowLeft.add(this.butBack);
	}
	private void initPanelBelowCenter()
	{
		//Initialization
		this.panelBelowCenter = new JPanel();
		this.butDelete = new JButton(Methods.getLanguageText(XMLIdentifier.DELETE_TEXT));
		this.butSave = new JButton(Methods.getLanguageText(XMLIdentifier.EXPORT_TEXT));
		this.butRefresh = new JButton(Methods.getLanguageText(XMLIdentifier.REFRESH_TEXT));
		
		//Properties
		this.panelBelowCenter.setOpaque(false);
		this.panelBelowCenter.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.butDelete.setActionCommand(this.DELETE);
		this.butDelete.setToolTipText(Methods.getLanguageText(XMLIdentifier.TABLE_TOOLTIPS_BUTTON_DELETE_TEXT));
		this.butDelete.addActionListener(this);
		this.butDelete.setBackground(Constants.COLOR_MAIN_MENU_BUTTONS);
		this.butDelete.setForeground(Color.white);
		this.butSave.setActionCommand(this.SAVE);
		this.butSave.addActionListener(this);
		this.butSave.setToolTipText(Methods.getLanguageText(XMLIdentifier.SAVE_IMAGE_TOOLIP_TEXT));
		this.butSave.setBackground(Constants.COLOR_MAIN_MENU_BUTTONS);
		this.butSave.setForeground(Color.white);
		this.butRefresh.addActionListener(this);
		this.butRefresh.setActionCommand(this.REFRESH);
		this.butRefresh.setBackground(Constants.COLOR_MAIN_MENU_BUTTONS);
		this.butRefresh.setForeground(Color.white);
		
		//add to panel
//		this.panelBelowCenter.add(this.butSave);
		this.panelBelowCenter.add(this.butRefresh);
		this.panelBelowCenter.add(this.butDelete);
	}
	private void initPanelBelowRight()
	{
		//Initialization
		this.panelBelowRight = new JPanel();
		this.butSelect = new JButton(Methods.getLanguageText(XMLIdentifier.SELECT_TEXT));
		
		//Properties
		this.panelBelowRight.setOpaque(false);
		this.panelBelowRight.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.butSelect.setActionCommand(this.SELECT);
		this.butSelect.setToolTipText(Methods.getLanguageText(XMLIdentifier.TABLE_TOOLTIPS_BUTTON_SELECT_TEXT));
		this.butSelect.addActionListener(this);
		this.butSelect.setBackground(Constants.COLOR_MAIN_MENU_BUTTONS);
		this.butSelect.setForeground(Color.white);
		
		//add to panel
		this.panelBelowRight.add(this.butSelect);
	}
	private void initPanelBelow()
	{
		//Initialization
		this.panelBelow = new JPanel();
		this.initPanelBelowCenter();
		this.initPanelBelowLeft();
		this.initPanelBelowRight();
		
		//Properties
		this.panelBelow.setBackground(Constants.COLOR_MAIN_MENU_BACKGROUND);
		this.panelBelow.setLayout(new BorderLayout());
		
		//add to panel
		this.panelBelow.add(this.panelBelowLeft, BorderLayout.WEST);
		this.panelBelow.add(this.panelBelowCenter, BorderLayout.CENTER);
		this.panelBelow.add(this.panelBelowRight, BorderLayout.EAST);
	}
	private void initPanelTopLeft()
	{
		//Initialization
		this.panelTopLeft = new JPanel();
		this.labFilter = new JLabel(Methods.getLanguageText(XMLIdentifier.TABLE_FILTER_BY_LABEL), SwingConstants.RIGHT);
		this.labKeyword = new JLabel(Methods.getLanguageText(XMLIdentifier.TABLE_FILTER_KEYWORD_LABEL), SwingConstants.RIGHT);
		this.tfFilter = new JTextField("", 20);
		this.comboFilter = new JComboBox<String>(Constants.TABLE_FILTER_OPTIONS);
		this.butFilter = new JButton(Methods.getLanguageText(XMLIdentifier.FILTER_TEXT));
		this.labGuide = new JLabel ("<html> " + Methods.getLanguageText(XMLIdentifier.NOTE_TEXT) + ": " + "<br/>" + Methods.getLanguageText(XMLIdentifier.TABLE_FILTER_GUIDE_USAGE_TEXT) + "</html>");
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
//		this.panelTopLeft.setOpaque(false);
		this.panelTopLeft.setBackground(Color.WHITE);
		this.panelTopLeft.setLayout(new GridBagLayout());
		this.labGuide.setFont(Constants.FONT_SMALL_NOTE);
		this.butFilter.addActionListener(this);
		this.butFilter.setActionCommand(this.FILTER);
		this.comboFilter.setBackground(Color.WHITE);
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_TOP_COMPONENT;
		c.fill = GridBagConstraints.BOTH;
		this.panelTopLeft.add(this.labFilter, c);			//Filter By
		Gbm.nextGridColumn(c);
		this.panelTopLeft.add(this.comboFilter, c);			//Filter Options
		Gbm.newGridLine(c);
		c.insets = Constants.INSETS_GENERAL;
		this.panelTopLeft.add(this.labKeyword, c);			//Search Keyword
		Gbm.nextGridColumn(c);
		this.panelTopLeft.add(this.tfFilter, c);			//Search Keyword Text Field;
		Gbm.newGridLine(c);
		c.gridwidth = 2;
//		this.panelTopLeft.add(this.labGuide, c);			//Small Note
		Gbm.newGridLine(c);
		c.fill = GridBagConstraints.NONE;
		c.insets = Constants.INSETS_TOP_COMPONENT;
		this.panelTopLeft.add(this.butFilter, c);			//Filter Button
	}
	private void initPanelTop()
	{
		//Initialization
		this.panelTop = new JPanel();
		this.initPanelTopLeft();
		this.activePatientPanel = new ActivePatientPanel(this.getMainFrameReference());
		this.panelDateRange = new DateRangePanel();
		this.tableConfig = new JTabbedPane();
		
		//Properties
		this.panelTop.setOpaque(false);
		this.panelTop.setLayout(new BorderLayout());
		this.panelDateRange.dateFrom.autoSetDate();
		this.panelDateRange.dateTo.autoSetDate();
		this.activePatientPanel.setOpaque(true);
		this.tableConfig.addTab("1", this.panelTopLeft);
		this.tableConfig.addTab("2", this.activePatientPanel);
		this.tableConfig.addTab("3", this.panelDateRange);
		
		//Add to panel
		this.panelTop.add(this.tableConfig, BorderLayout.CENTER);
	}
	//Other Methods
	public void refreshTable()
	{
		this.activePatientPanel.refresh();
		this.initTable();
	}
	private void initTable()
	{
		try
		{
			this.remove(this.table);
		}
		catch(NullPointerException ex) {};
		
		this.butDelete.setEnabled(false);
		this.butSelect.setEnabled(false);
		
		String filterType = this.getFilterType();
		
		String filter = Methods.getTextData(this.tfFilter);
		this.entries = FileOperation.getListOfEntries(this.activePatientPanel.getSelectedPatientData(), this.panelDateRange.getFromDate(), this.panelDateRange.getToDate());
		this.table = new TablePanel(this.entries, filterType, filter);
		this.add(this.table, BorderLayout.CENTER);
		
		this.table.getTable().getModel().addTableModelListener(new TableModelListener()
				{
					@Override
					public void tableChanged(TableModelEvent e)
					{
						gatherSelectedEntries(table.getTable());
					}
					
				});
		this.table.getTable().addMouseListener(new MouseListener()
				{

					@Override
					public void mouseClicked(MouseEvent e) 
					{
						gatherSelectedEntries(table.getTable());
					}

					@Override
					public void mouseEntered(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseExited(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mousePressed(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseReleased(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}
			
				});
		
		this.revalidate();
		this.repaint();
	}
	private String getFilterType()
	{
		String filterType = this.comboFilter.getSelectedItem().toString();
		
		if (filterType.equals(Methods.getLanguageText(XMLIdentifier.TABLE_FILTER_TYPE_PAIN_AMOUNT_TEXT)))
		{
			filterType = XMLIdentifier.TABLE_FILTER_TYPE_PAIN_AMOUNT_TEXT;
		}
		else if (filterType.equals(Methods.getLanguageText(XMLIdentifier.TABLE_FILTER_TYPE_PAIN_POSITIONS_TEXT)))
		{
			filterType = XMLIdentifier.TABLE_FILTER_TYPE_PAIN_POSITIONS_TEXT;
		}
		else if (filterType.equals(Methods.getLanguageText(XMLIdentifier.TABLE_FILTER_TYPE_PAIN_KINDS_TEXT)))
		{
			filterType = XMLIdentifier.TABLE_FILTER_TYPE_PAIN_KINDS_TEXT;
		}
		else if (filterType.equals(Methods.getLanguageText(XMLIdentifier.TABLE_FILTER_TYPE_INTENSITIES_TEXT)))
		{
			filterType = XMLIdentifier.TABLE_FILTER_TYPE_INTENSITIES_TEXT;
		}
		else if (filterType.equals(Methods.getLanguageText(XMLIdentifier.TABLE_FILTER_TYPE_DURATIONS_TEXT)))
		{
			filterType = XMLIdentifier.TABLE_FILTER_TYPE_DURATIONS_TEXT;
		}
		else if (filterType.equals(Methods.getLanguageText(XMLIdentifier.TABLE_FILTER_TYPE_TRIGGER_TEXT)))
		{
			filterType = XMLIdentifier.TABLE_FILTER_TYPE_TRIGGER_TEXT;
		}
		else if (filterType.equals(Methods.getLanguageText(XMLIdentifier.TABLE_FILTER_TYPE_COMMENTS_TEXT)))
		{
			filterType = XMLIdentifier.TABLE_FILTER_TYPE_COMMENTS_TEXT;
		}
		
		return filterType;
	}
	private void gatherSelectedEntries(JTable table)
	{
		this.selectedEntryIDs = new ArrayList<String>();
		int selected = 0;
		
		for (int i=0; i<table.getRowCount(); i++)		//Check which entries are selected
		{
			if ((Boolean)table.getValueAt(i, 0))
			{
				String key = table.getModel().getValueAt(table.convertRowIndexToView(i), 2).toString();
				PainEntryData entry = null;
				for (int a=0; a<this.entries.size(); a++)
				{
					if(key.equals(this.entries.get(i).getFullTime()))
					{
						entry = this.entries.get(i);
					}
				}
				//Entry ID is the start time
				
				this.activeEntry = entry;
				this.selectedEntryIDs.add(Methods.generatePainDataFolderPathName(this.activePatientPanel.getSelectedPatientData(), entry) + File.separator +
											key.replaceAll(":", "-") + Constants.PAIN_DATA_ENTRY_FILE_EXTENSION);		//Replace ":" to "-" to unify with file name
				selected++;
			}
		}
		
		if (selected==1)
		{
			this.butSelect.setEnabled(true);
			this.butDelete.setEnabled(true);
		}
		else if (selected > 1)
		{
			this.butSelect.setEnabled(false);
			this.butDelete.setEnabled(true);;
		}
		else
		{
			this.butDelete.setEnabled(false);
			this.butSelect.setEnabled(false);
		}
	}
	
	//Interfaces
	@Override
	public void actionPerformed(ActionEvent e)
	{
		switch (e.getActionCommand())
		{
			case FILTER:
				this.initTable();
				break;
				
			case REFRESH:
				this.initTable();
				break;
				
			case SAVE:
				/*
				ImagePanel imagePanel = new ImagePanel(this.table.getScrollPane(), 
														new PatientDataForm(this.activePatientPanel.getSelectedPatientData(), false), 
														new DateRangePanel(this.panelDateRange.getDateRangeMap()));
				Methods.exportPanelImage(imagePanel, true);
				*/
				/*
				PatientData patient = this.activePatientPanel.getSelectedPatientData();
				ImageExportPanel exportImage = new ImageExportPanel(patient.getName(), 
																	patient.getID(), 
																	this.panelDateRange.getDateRangeAsString(), 
																	Globals.GRAPH_FILTER_PANEL.getRecentMedicationFilter(), 
																	this.table);
				Methods.exportPanelImage(exportImage, true);
				*/
				FileOperation.exportTableAsExcel(this.table.getTable());
				break;
				
			case DELETE:
				int response = CustomDialog.showConfirmDialog(Methods.getLanguageText(XMLIdentifier.MESSAGE_DELETE_CONFIRM_TEXT), 
																Methods.getLanguageText(XMLIdentifier.MESSAGE_DELETE_CONFIRM_TITLE));
				
				if (response == JOptionPane.YES_OPTION)
				{
					FileOperation.deleteEntries(this.selectedEntryIDs);
					this.initTable();
				}
				break;
				
			case SELECT:
				this.getMainFrameReference().ENTRY_LOG.loadData(this.activePatientPanel.getSelectedPatientData(), this.activeEntry);
		//		this.getMainFrameReference().ENTRY_LOG.getActivePatientPanel().setSelectedPatient(this.activePatientPanel.getSelectedPatientData());
				this.getMainFrameReference().changePanel(PanelName.ENTRY_LOG);
				break;
				
			case BACK:
				this.getMainFrameReference().changePanel(PanelName.MAIN_MENU);
				break;
		}
	}
}
