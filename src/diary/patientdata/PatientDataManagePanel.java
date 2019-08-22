package diary.patientdata;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import diary.constants.Constants;
import diary.constants.Globals;
import diary.constants.PanelName;
import diary.constants.XMLIdentifier;
import diary.gui.CustomDialog;
import diary.gui.DateRangePanel;
import diary.gui.GButton;
import diary.gui.table.Table;
import diary.interfaces.LanguageListener;
import diary.methods.FileOperation;
import diary.methods.Methods;
import diary.methods.PatientDataOperation;
import giantsweetroll.gui.swing.Gbm;
import giantsweetroll.gui.swing.ScrollPaneManager;
import giantsweetroll.message.MessageManager;

public class PatientDataManagePanel extends JPanel implements ActionListener, LanguageListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7220293959734922865L;
	
	private JPanel panelTop, 
					panelTopLeft, 
					panelTable, 
					panelBelowLeft, 
					panelBelowCenter, 
					panelBelowRight, 
					panelBelow,
					panelFilter;
	private JCheckBox checkMedRec, checkName, checkDOB;
	private JTextField tfMedRec, tfName;
	private DateRangePanel panelDateRange;
	private GButton butBack, 
					butNew, 
					butDelete, 
					butEdit, 
					butFilter, 
					butRefresh, 
					butCopyData, 
					butSelectAll, 
					butDeselectAll;
	private Table table;
	private List<PatientData> patients;
	private List<String> selectedPatientIDs;
	private PatientData activePatient;
	private JScrollPane scrollTable, scrollFilter;
	
	//Constants
	private final String BACK = "back";
	private final String NEW = "new";
	private final String DELETE = "delete";
	private final String SELECT = "select";
	private final String FILTER = "filter";
	private final String COPY_DATA = "copyData";
	private final String SELECT_ALL = "selectAll";
	private final String DESELECT_ALL = "deselectAll";
	public static final String FILTER_MED_REC = "filter med rec";
	public static final String FILTER_NAME = "name";
	public static final String FILTER_DOB = "filter dob";
	
	//Constructors
	public PatientDataManagePanel()
	{
		this.init();
	}
	
	//Create GUI
	private void init()
	{
		//Initialization
		this.initPanelBelow();
		this.initPanelTop();
		this.initPanelTable();
		
		//Properties
		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);
		
		//Add to panel
		this.add(this.panelTop, BorderLayout.NORTH);
		this.add(this.panelTable, BorderLayout.CENTER);
		this.add(this.panelBelow, BorderLayout.SOUTH);
	}
	private void initPanelBelowLeft()
	{
		//Initializations
		this.panelBelowLeft = new JPanel();
		this.butBack = new GButton(Globals.LANGUAGE.getTextMap().get(XMLIdentifier.BACK_TEXT));
		
		//Properties
		this.panelBelowLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.panelBelowLeft.setOpaque(false);
		this.butBack.setActionCommand(this.BACK);
		this.butBack.addActionListener(this);
		this.butBack.setBackground(Constants.COLOR_MAIN_MENU_BUTTONS);
		this.butBack.setForeground(Color.white);
		this.butBack.setMnemonic(KeyEvent.VK_B);
		
		//add to panel
		this.panelBelowLeft.add(this.butBack);
	}
	private void initPanelBelowRight()
	{
		//Initialization
		this.panelBelowRight = new JPanel();
		this.butNew = new GButton(Globals.LANGUAGE.getTextMap().get(XMLIdentifier.NEW_TEXT));
	
		//Properties
		this.panelBelowRight.setOpaque(false);
		this.butNew.setActionCommand(this.NEW);
		this.butNew.addActionListener(this);
		this.butNew.setBackground(Constants.COLOR_MAIN_MENU_BUTTONS);
		this.butNew.setForeground(Color.white);
		this.butNew.setMnemonic(KeyEvent.VK_N);
		
		//Add to panel
		this.panelBelowRight.add(this.butNew);
	}
	private void initPanelBelowCenter()
	{
		//Initializations
		this.panelBelowCenter = new JPanel();
		this.butDelete = new GButton(Methods.getLanguageText(XMLIdentifier.DELETE_TEXT));
		this.butRefresh = new GButton(Methods.getLanguageText(XMLIdentifier.REFRESH_TEXT));
		this.butEdit = new GButton(Methods.getLanguageText(XMLIdentifier.EDIT_TEXT));
		this.butCopyData = new GButton(Methods.getLanguageText(XMLIdentifier.PATIENT_MANAGE_PANEL_COPY_DATA_LABEL));
		this.butSelectAll = new GButton(Methods.getLanguageText(XMLIdentifier.SELECT_ALL_TEXT));
		this.butDeselectAll = new GButton(Methods.getLanguageText(XMLIdentifier.DISSELECT_ALL_TEXT));
		
		//Properties
		this.panelBelowCenter.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.panelBelowCenter.setOpaque(false);
		this.butDelete.setActionCommand(this.DELETE);
		this.butDelete.addActionListener(this);
		this.butDelete.setBackground(Constants.COLOR_MAIN_MENU_BUTTONS);
		this.butDelete.setForeground(Color.white);
		this.butDelete.setMnemonic(KeyEvent.VK_D);
		this.butRefresh.setActionCommand(this.FILTER);
		this.butRefresh.addActionListener(this);
		this.butRefresh.setBackground(Constants.COLOR_MAIN_MENU_BUTTONS);
		this.butRefresh.setForeground(Color.white);
		this.butRefresh.setMnemonic(KeyEvent.VK_R);
		this.butEdit.setActionCommand(this.SELECT);
		this.butEdit.addActionListener(this);
		this.butEdit.setBackground(Constants.COLOR_MAIN_MENU_BUTTONS);
		this.butEdit.setForeground(Color.white);
		this.butEdit.setMnemonic(KeyEvent.VK_S);
		this.butCopyData.setActionCommand(this.COPY_DATA);
		this.butCopyData.addActionListener(this);
		this.butCopyData.setBackground(Constants.COLOR_MAIN_MENU_BUTTONS);
		this.butCopyData.setForeground(Color.white);
		this.butCopyData.setMnemonic(KeyEvent.VK_C);
		this.butSelectAll.setActionCommand(this.SELECT_ALL);
		this.butSelectAll.addActionListener(this);
		this.butSelectAll.setBackground(Constants.COLOR_MAIN_MENU_BUTTONS);
		this.butSelectAll.setForeground(Color.white);
		this.butDeselectAll.setActionCommand(this.DESELECT_ALL);
		this.butDeselectAll.addActionListener(this);
		this.butDeselectAll.setBackground(Constants.COLOR_MAIN_MENU_BUTTONS);
		this.butDeselectAll.setForeground(Color.white);
		
		//add to panel
		this.panelBelowCenter.add(this.butSelectAll);
		this.panelBelowCenter.add(this.butDeselectAll);
		this.panelBelowCenter.add(this.butDelete);
		this.panelBelowCenter.add(this.butRefresh);
		this.panelBelowCenter.add(this.butCopyData);
		this.panelBelowCenter.add(this.butEdit);
	}
	private void initPanelBelow()
	{
		//Initialization
		this.panelBelow = new JPanel();
		this.initPanelBelowCenter();
		this.initPanelBelowLeft();
		this.initPanelBelowRight();
		
		//Properties
		this.panelBelow.setLayout(new BorderLayout());
	//	this.panelBelow.setOpaque(false);
		this.panelBelow.setBackground(Constants.COLOR_MAIN_MENU_BACKGROUND);
		this.panelBelow.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		
		//add to panel
		this.panelBelow.add(this.panelBelowCenter, BorderLayout.CENTER);
		this.panelBelow.add(this.panelBelowLeft, BorderLayout.WEST);
		this.panelBelow.add(this.panelBelowRight, BorderLayout.EAST);
	}
	private void initPanelTopLeft()
	{
		//Initialization
		this.panelTopLeft = new JPanel();
		this.initPanelFilter();
		this.scrollFilter = ScrollPaneManager.generateDefaultScrollPane(this.panelFilter, 10, 10);
		this.butFilter = new GButton(Globals.LANGUAGE.getTextMap().get(XMLIdentifier.FILTER_TEXT));
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		this.panelTopLeft.setLayout(new GridBagLayout());
		this.panelTopLeft.setOpaque(false);
//		this.setBorder(BorderFactory.createTitledBorder(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.FILTER_TEXT)));
		this.butFilter.setActionCommand(this.FILTER);
		this.butFilter.addActionListener(this);
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_TOP_COMPONENT;
//		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.WEST;
		this.panelTopLeft.add(this.scrollFilter, c);				//Filter Panel
		Gbm.newGridLine(c);
		this.panelTopLeft.add(this.butFilter, c);					//Filter Button
	}
	private void initPanelFilter()
	{
		//Initialization
		this.panelFilter = new JPanel();
		this.checkMedRec = new JCheckBox (Globals.LANGUAGE.getTextMap().get(XMLIdentifier.PATIENT_DATA_FORM_MED_ID_LABEL));
		this.tfMedRec = new JTextField("", 10);
		this.checkName = new JCheckBox(Globals.LANGUAGE.getTextMap().get(XMLIdentifier.PATIENT_DATA_FORM_NAME_LABEL));
		this.tfName = new JTextField("", 20);
		this.checkDOB = new JCheckBox(Globals.LANGUAGE.getTextMap().get(XMLIdentifier.PATIENT_DATA_FORM_DOB_LABEL));
		this.panelDateRange = new DateRangePanel();
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		this.panelFilter.setLayout(new GridBagLayout());
		this.panelFilter.setOpaque(false);
		this.checkDOB.setOpaque(false);
		this.checkMedRec.setOpaque(false);
		this.checkName.setOpaque(false);
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_TOP_COMPONENT;
		c.fill = GridBagConstraints.BOTH;
		this.panelFilter.add(this.checkMedRec, c);					//Medical Record Check Box
		Gbm.nextGridColumn(c);
		this.panelFilter.add(this.tfMedRec, c);					//Medical Record Text Field
		Gbm.newGridLine(c);
		c.insets = Constants.INSETS_GENERAL;
		this.panelFilter.add(this.checkName, c);					//Name Check Box
		Gbm.nextGridColumn(c);
		this.panelFilter.add(this.tfName, c);						//Name Text Field
		Gbm.newGridLine(c);
		this.panelFilter.add(this.checkDOB, c);					//DOB Check Box
		Gbm.nextGridColumn(c);
		this.panelFilter.add(this.panelDateRange, c);				//DOB Panel
	}
	private void initPanelTop()
	{
		//Initialization
		this.panelTop = new JPanel();
		this.initPanelTopLeft();
		
		//Properties
		this.panelTop.setLayout(new BorderLayout());
		this.panelTop.setOpaque(false);
		
		//Add to panel
		this.panelTop.add(this.panelTopLeft, BorderLayout.WEST);
	}
	private void initPanelTable()
	{
		//Initialization
		this.panelTable = new JPanel();
		this.initTable();
		this.scrollTable = new JScrollPane(this.table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		//Properties
		this.panelTable.setLayout(new BorderLayout());
		this.panelTable.setOpaque(false);
		
		//Add to panel
		this.panelTable.add(this.scrollTable, BorderLayout.CENTER);
	}
	
	//Methods
	private void initTable()
	{
		try
		{
			this.panelTable.remove(this.scrollTable);
		}
		catch(NullPointerException ex) {}
	
		this.butEdit.setEnabled(false);
		this.butDelete.setEnabled(false);
		this.butCopyData.setEnabled(false);
		
		this.patients = FileOperation.getListOfPatients();
		this.filterPatients();
		this.table = new Table(convertToTableArray(this.patients), Methods.getPatientTableHeaders());
		this.scrollTable = new JScrollPane(this.table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.panelTable.add(this.scrollTable, BorderLayout.CENTER);
		
		this.table.getModel().addTableModelListener(new TableModelListener()
				{
					@Override
					public void tableChanged(TableModelEvent e)
					{
						gatherSelectedPatients(table);
					}
				});
		this.table.getTableHeader().addMouseListener(new MouseListener()
				{

					@Override
					public void mouseClicked(MouseEvent arg0) {
						gatherSelectedPatients(table);
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
		
		this.panelTable.revalidate();
		this.panelTable.repaint();
	}
	
	private Object[][] convertToTableArray(List<PatientData> list)
	{
		Object[][] obj = new Object[list.size()][4];
		
		for (int i=0; i<list.size(); i++)
		{
			int index = 0;
			obj[i][index] = new Boolean(false);		//Select
			index++;
			obj[i][index] = list.get(i).getID();
			index++;
			obj[i][index] = list.get(i).getName();
			index++;
			obj[i][index] = list.get(i).getDOBString();
		}
		
		return obj;
	}
	
	private void gatherSelectedPatients(JTable table)
	{
		this.selectedPatientIDs = new ArrayList<String>();
		int selected = 0;
		
		for (int i=0; i<table.getRowCount(); i++)
		{
			if ((Boolean)table.getValueAt(i, 0))
			{
				String key = table.getModel().getValueAt(table.convertRowIndexToView(i), 1).toString();
				PatientData patient = null;
				for (int a=0; a<this.patients.size(); a++)
				{
					if (key.equals(this.patients.get(i).getID()))
					{
						patient = this.patients.get(i);
					}
				}
				
				this.activePatient = patient;
	//			this.selectedPatientIDs.add(MainFrame.setting.getDataMap().get(Settings.DATABASE_USERS_PATH) + key + ".xml");
				this.selectedPatientIDs.add(key);
				
				selected++;
			}
			
			if (selected==1)
			{
				this.butEdit.setEnabled(true);
				this.butDelete.setEnabled(true);
				this.butCopyData.setEnabled(true);
			}
			else if (selected > 1)
			{
				this.butEdit.setEnabled(false);
				this.butDelete.setEnabled(true);;
				this.butCopyData.setEnabled(true);
			}
			else
			{
				this.butDelete.setEnabled(false);
				this.butEdit.setEnabled(false);
				this.butCopyData.setEnabled(false);
			}
		}
	}
	
	private void filterPatients()
	{
//		System.out.println("Size of list before filter: " + this.patients.size());
		if (this.checkMedRec.isSelected())
		{
			this.patients = PatientDataOperation.getFilteredData(PatientDataManagePanel.FILTER_MED_REC, Methods.getTextData(this.tfMedRec), this.patients);
		}
		if(this.checkName.isSelected())
		{
			this.patients = PatientDataOperation.getFilteredData(PatientDataManagePanel.FILTER_NAME, Methods.getTextData(this.tfName), this.patients);
		}
		if (this.checkDOB.isSelected())
		{
			this.patients = PatientDataOperation.getFilteredData(this.panelDateRange.getFromDate(), this.panelDateRange.getToDate(), this.patients);
		}
//		System.out.println("Size of list after filter: " + this.patients.size());
	}
	
	public void refresh()
	{
		this.initTable();
		this.revalidate();
		this.repaint();
	}
	
	//Interfaces
	@Override
	public void actionPerformed(ActionEvent e)
	{
		switch(e.getActionCommand())
		{
			case BACK:
				Globals.MAIN_FRAME.checkUsers();
				Globals.GRAPH_PANEL.refresh();
				Globals.PAIN_TABLE.refresh();
			//	MainFrame.changePanel(Globals.MAIN_MENU);
				Globals.MAIN_FRAME.changePanel(PanelName.MAIN_MENU);
				break;
				
			case NEW:
			//	MainFrame.changePanel(new PatientDataRegisterationForm());
				Globals.MAIN_FRAME.changePanel(new PatientDataRegisterationForm(true), PanelName.PATIENT_REGISTERATION_FORM);
				break;
				
			case FILTER:
				this.refresh();
				break;
				
			case DELETE:
				int response = CustomDialog.showConfirmDialog(Methods.getLanguageText(XMLIdentifier.MESSAGE_DELETE_CONFIRM_TEXT), 
																Methods.getLanguageText(XMLIdentifier.MESSAGE_DELETE_CONFIRM_TITLE));

				if (response == JOptionPane.YES_OPTION)
				{
					for (String id : this.selectedPatientIDs)
					{
						FileOperation.deletePatientData(id);
					}
					this.initTable();
				}				
				break;
				
			case SELECT:
	//			MainFrame.changePanel(new PatientDataRegisterationForm(this.activePatient));
				Globals.MAIN_FRAME.changePanel(new PatientDataRegisterationForm(this.activePatient), PanelName.PATIENT_REGISTERATION_FORM);
				break;
				
			case COPY_DATA:
				CopyPatientDataPanel copy = new CopyPatientDataPanel();
				
				int response2 = CustomDialog.showConfirmDialog(Methods.getLanguageText(XMLIdentifier.PATIENT_MANAGE_PANEL_COPY_DATA_LABEL), 
																copy);
				
				if (response2 == JOptionPane.YES_OPTION)
				{
					try
					{
						if(copy.allDataSelected())
						{
							FileOperation.exportPatientDataAsZip(copy.getZipPath(), this.selectedPatientIDs);
						}
						else if (copy.specificDateRangeSelected())
						{
							DateRangePanel date = copy.getDateRangePanel();
							FileOperation.exportPatientDataAsZip(copy.getZipPath(), this.selectedPatientIDs, date.getFromDate(), date.getToDate());
						}
					}
					catch(IOException ex)
					{
						ex.printStackTrace();
						MessageManager.showErrorDialog(ex);
					}
				}
				break;
				
			case SELECT_ALL:
				for (int i=0; i<this.table.getRowCount(); i++)
				{
					this.table.setValueAt(true, i, 0);
				}
				break;
				
			case DESELECT_ALL:
				for (int i=0; i<this.table.getRowCount(); i++)
				{
					this.table.setValueAt(false, i, 0);
				}
				break;
		}
	}

	@Override
	public void revalidateLanguage() 
	{
		this.checkMedRec.setText(Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_MED_ID_LABEL));
		this.checkName.setText(Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_NAME_LABEL));
		this.checkDOB.setText(Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_DOB_LABEL));
		this.butFilter.setText(Methods.getLanguageText(XMLIdentifier.FILTER_TEXT));
		this.initTable();
		this.butBack.setText(Methods.getLanguageText(XMLIdentifier.BACK_TEXT));
		this.butDelete.setText(Methods.getLanguageText(XMLIdentifier.DELETE_TEXT));
		this.butRefresh.setText(Methods.getLanguageText(XMLIdentifier.REFRESH_TEXT));
		this.butEdit.setText(Methods.getLanguageText(XMLIdentifier.EDIT_TEXT));
		this.butRefresh.setText(Methods.getLanguageText(XMLIdentifier.REFRESH_TEXT));
		this.butNew.setText(Methods.getLanguageText(XMLIdentifier.NEW_TEXT));
		this.butSelectAll.setText(Methods.getLanguageText(XMLIdentifier.SELECT_ALL_TEXT));
		this.butDeselectAll.setText(Methods.getLanguageText(XMLIdentifier.DISSELECT_ALL_TEXT));
		this.butCopyData.setText(Methods.getLanguageText(XMLIdentifier.PATIENT_MANAGE_PANEL_COPY_DATA_LABEL));
		this.table.changeTableHeaders(Methods.getPatientTableHeaders());
		this.panelDateRange.revalidateLanguage();
		this.revalidate();
		this.repaint();
	}
}
