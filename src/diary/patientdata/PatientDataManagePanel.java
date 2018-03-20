package diary.patientdata;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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
import diary.constants.XMLIdentifier;
import diary.gui.CustomDialog;
import diary.gui.DateRangePanel;
import diary.gui.MainFrame;
import diary.gui.table.Table;
import diary.methods.FileOperation;
import diary.methods.Methods;
import diary.methods.PatientDataOperation;
import giantsweetroll.gui.swing.Gbm;

public class PatientDataManagePanel extends JPanel implements ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7220293959734922865L;
	
	private JPanel panelTop, panelTopLeft, panelTable, panelBelowLeft, panelBelowCenter, panelBelowRight, panelBelow;
	private JCheckBox checkMedRec, checkName, checkDOB;
	private JTextField tfMedRec, tfName;
	private DateRangePanel panelDateRange;
	private JButton butBack, butNew, butDelete, butSelect, butFilter, butRefresh;
	private Table table;
	private List<PatientData> patients;
	private List<String> selectedPatientIDs;
	private PatientData activePatient;
	private JScrollPane scrollTable;
	
	//Constants
	private final String BACK = "back";
	private final String NEW = "new";
	private final String DELETE = "delete";
	private final String SELECT = "select";
	private final String FILTER = "filter";
	public static final String FILTER_MED_REC = "filter med rec";
	public static final String FILTER_NAME = "name";
	public static final String FILTER_DOB = "filter dob";
	
	//Constructors
	public PatientDataManagePanel()
	{
		this.init();
	}
	
	//Methods
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
		this.butBack = new JButton(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.BACK_TEXT));
		
		//Properties
		this.panelBelowLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.panelBelowLeft.setOpaque(false);
		this.butBack.setActionCommand(this.BACK);
		this.butBack.addActionListener(this);
		
		//add to panel
		this.panelBelowLeft.add(this.butBack);
	}
	private void initPanelBelowRight()
	{
		//Initialization
		this.panelBelowRight = new JPanel();
		this.butNew = new JButton(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.NEW_TEXT));
	
		//Properties
		this.panelBelowRight.setOpaque(false);
		this.butNew.setActionCommand(this.NEW);
		this.butNew.addActionListener(this);
		
		//Add to panel
		this.panelBelowRight.add(this.butNew);
	}
	private void initPanelBelowCenter()
	{
		//Initializations
		this.panelBelowCenter = new JPanel();
		this.butDelete = new JButton(Methods.getLanguageText(XMLIdentifier.DELETE_TEXT));
		this.butRefresh = new JButton(Methods.getLanguageText(XMLIdentifier.REFRESH_TEXT));
		this.butSelect = new JButton(Methods.getLanguageText(XMLIdentifier.SELECT_TEXT));
		
		//Properties
		this.panelBelowCenter.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.panelBelowCenter.setOpaque(false);
		this.butDelete.setActionCommand(this.DELETE);
		this.butDelete.addActionListener(this);
		this.butRefresh.setActionCommand(this.FILTER);
		this.butRefresh.addActionListener(this);
		this.butSelect.setActionCommand(this.SELECT);
		this.butSelect.addActionListener(this);
		
		//add to panel
		this.panelBelowCenter.add(this.butDelete);
		this.panelBelowCenter.add(this.butRefresh);
		this.panelBelowCenter.add(this.butSelect);
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
		this.panelBelow.setOpaque(false);
		
		//add to panel
		this.panelBelow.add(this.panelBelowCenter, BorderLayout.CENTER);
		this.panelBelow.add(this.panelBelowLeft, BorderLayout.WEST);
		this.panelBelow.add(this.panelBelowRight, BorderLayout.EAST);
	}
	private void initPanelTopLeft()
	{
		//Initialization
		this.panelTopLeft = new JPanel();
		this.checkMedRec = new JCheckBox (Constants.LANGUAGE.getTextMap().get(XMLIdentifier.PATIENT_DATA_FORM_MED_ID_LABEL));
		this.tfMedRec = new JTextField("", 10);
		this.checkName = new JCheckBox(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.PATIENT_DATA_FORM_NAME_LABEL));
		this.tfName = new JTextField("", 20);
		this.checkDOB = new JCheckBox(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.PATIENT_DATA_FORM_DOB_LABEL));
		this.panelDateRange = new DateRangePanel();
		this.butFilter = new JButton(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.FILTER_TEXT));
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		this.panelTopLeft.setLayout(new GridBagLayout());
		this.panelTopLeft.setOpaque(false);
		this.setBorder(BorderFactory.createTitledBorder(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.FILTER_TEXT)));
		this.checkDOB.setOpaque(false);
		this.checkMedRec.setOpaque(false);
		this.checkName.setOpaque(false);
		this.butFilter.setActionCommand(this.FILTER);
		this.butFilter.addActionListener(this);
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_TOP_COMPONENT;
		c.fill = GridBagConstraints.BOTH;
		this.panelTopLeft.add(this.checkMedRec, c);					//Medical Record Check Box
		Gbm.nextGridColumn(c);
		this.panelTopLeft.add(this.tfMedRec, c);					//Medical Record Text Field
		Gbm.newGridLine(c);
		c.insets = Constants.INSETS_GENERAL;
		this.panelTopLeft.add(this.checkName, c);					//Name Check Box
		Gbm.nextGridColumn(c);
		this.panelTopLeft.add(this.tfName, c);						//Name Text Field
		Gbm.newGridLine(c);
		this.panelTopLeft.add(this.checkDOB, c);					//DOB Check Box
		Gbm.nextGridColumn(c);
		this.panelTopLeft.add(this.panelDateRange, c);				//DOB Panel
		Gbm.newGridLine(c);
		this.panelTopLeft.add(this.butFilter, c);					//Filter Button
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
	
	private void initTable()
	{
		try
		{
			this.panelTable.remove(this.scrollTable);
		}
		catch(NullPointerException ex) {}
	
		this.butSelect.setEnabled(false);
		this.butDelete.setEnabled(false);
		
		this.patients = FileOperation.getListOfPatients();
		this.filterPatients();
		this.table = new Table(convertToTableArray(this.patients), Constants.PATIENT_TABLE_HEADERS);
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
			obj[i][index] = list.get(i).getDOB();
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
			this.patients = PatientDataOperation.getFilteredData(this.panelDateRange.getDateRangeMap().get(DateRangePanel.FROM), this.panelDateRange.getDateRangeMap().get(DateRangePanel.TO), this.patients);
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
	public void actionPerformed(ActionEvent e)
	{
		switch(e.getActionCommand())
		{
			case BACK:
				Globals.GRAPH_PANEL.refreshGraph();
				Globals.PAIN_TABLE.refreshTable();
				MainFrame.changePanel(Globals.MAIN_MENU);
				break;
				
			case NEW:
				MainFrame.changePanel(new PatientDataRegisterationForm());
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
				MainFrame.changePanel(new PatientDataRegisterationForm(this.activePatient));
				break;
		}
	}
}
