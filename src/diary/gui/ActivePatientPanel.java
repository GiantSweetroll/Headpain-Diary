package diary.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import diary.constants.Globals;
import diary.constants.XMLIdentifier;
import diary.methods.FileOperation;
import diary.methods.Methods;
import diary.methods.PatientDataOperation;
import diary.patientdata.PatientData;
import diary.patientdata.PatientDataFilterPanel;
import diary.patientdata.PatientDataForm;
import diary.patientdata.PatientDataRenderer;
import diary.patientdata.PatientDataTextPanel;

public class ActivePatientPanel extends MainFramePanel implements ItemListener, ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 136156090085473200L;
	
	private JLabel labUser;
	private JComboBox<PatientData> comboUsers;
	private JRadioButton radShow;
	private PatientDataForm dataForm;
	private PatientDataTextPanel dataText;
	private JPanel panelCenter, panelSelection, panelShow, panelFilter;
	private PatientDataFilterPanel patientFilter;
	private JButton buttonFilter;
	
	//Constructors
	public ActivePatientPanel(MainFrame frame)
	{
		super(frame);
		this.init();
	}
	public ActivePatientPanel(MainFrame frame, PatientData patient)
	{
		super(frame);
		this.init();
		this.setSelectedPatient(patient);
	}
	
	//Methods
	private void init()
	{
		//Initialization
		this.initPanelCenter();
		this.initPanelFilter();
		
		//Properties
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
		this.setOpaque(false);
		
		//Add to panel
		this.add(this.panelCenter, BorderLayout.CENTER);	
		this.add(this.panelFilter, BorderLayout.EAST);
	}
	private void initPanelSelection()
	{
		//Initialization
		this.panelSelection = new JPanel();
		this.labUser = new JLabel(Methods.getLanguageText(XMLIdentifier.ACTIVE_PATIENT_PANEL_PATIENT_TEXT), SwingConstants.CENTER);
		List<PatientData> list = FileOperation.getListOfPatients();
		this.comboUsers = new JComboBox<PatientData>(list.toArray(new PatientData[list.size()]));
		
		//Properties
		this.panelSelection.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
		this.panelSelection.setOpaque(false);
		this.comboUsers.setRenderer(new PatientDataRenderer());
		this.comboUsers.setBackground(Color.WHITE);
		this.comboUsers.addItemListener(new ItemListener()
				{
					@Override
					public void itemStateChanged(ItemEvent e)
					{
						displayPatientDetails();
						Methods.refresHistories(getSelectedPatientData());
						/*
						if (MainFrame.jComponent instanceof EntryLog)
						{
							getMainFrameReference().ENTRY_LOG.refreshHistories();
						}
						else if (MainFrame.jComponent instanceof GraphPanel)
						{
							getMainFrameReference().GRAPH_FILTER_PANEL.refresh(getSelectedPatientData());
						}
						*/
						getMainFrameReference().ENTRY_LOG.refreshHistories();
						getMainFrameReference().GRAPH_FILTER_PANEL.refresh(getSelectedPatientData());
					}
					
				});
		
		//Add to panel
		this.panelSelection.add(this.labUser);
		this.panelSelection.add(this.comboUsers);
	}
	private void initPanelCenter()
	{
		//Initialization
		this.panelCenter = new JPanel();
		this.initPanelSelection();
		this.initPanelShow();
		
		//Properties
		this.panelCenter.setLayout(new BoxLayout(this.panelCenter, BoxLayout.Y_AXIS));
		this.panelCenter.setOpaque(false);
		
		//Add to panel
		this.panelCenter.add(this.panelSelection);
		this.panelCenter.add(this.panelShow);
	}
	private void initPanelShow()
	{
		//Initialization
		this.panelShow = new JPanel();
		this.radShow = new JRadioButton(Methods.getLanguageText(XMLIdentifier.DETAILS_TEXT));
		
		//Properties
		this.panelShow.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.panelShow.setOpaque(false);
		this.radShow.addItemListener(this);
		this.radShow.setOpaque(false);
		
		//Add to panel
		this.panelShow.add(this.radShow);
	}
	public void initPanelFilter()
	{
		//Initialization
		this.panelFilter = new JPanel();
		this.patientFilter = new PatientDataFilterPanel();
		this.buttonFilter = new JButton(Methods.getLanguageText(XMLIdentifier.FILTER_TEXT));
		
		//Properties
		this.panelFilter.setLayout(new BoxLayout(this.panelFilter, BoxLayout.Y_AXIS));
		this.panelFilter.setOpaque(false);
		this.patientFilter.setBorder(BorderFactory.createTitledBorder(Methods.getLanguageText(XMLIdentifier.FILTER_TEXT)));
		this.buttonFilter.addActionListener(this);
		this.patientFilter.setAlignmentX(CENTER_ALIGNMENT);
		this.buttonFilter.setAlignmentX(CENTER_ALIGNMENT);
		
		//Add to panel
		this.panelFilter.add(this.patientFilter);
		this.panelFilter.add(Box.createRigidArea(new Dimension(5, 5)));			///Empty Component for BoxLayout spacing
		this.panelFilter.add(this.buttonFilter);
		this.panelFilter.add(Box.createRigidArea(new Dimension(5, 5)));			///Empty Component for BoxLayout spacing
	}
	public PatientData getSelectedPatientData()
	{
		return (PatientData)this.comboUsers.getSelectedItem();
	}
	public void setSelectedPatient(PatientData patient)
	{
		try
		{
//			this.comboUsers.setSelectedItem(patient);
			
			for (int i=0; i<this.comboUsers.getItemCount(); i++)
			{
				if (this.comboUsers.getItemAt(i).getNameAndID().equals(patient.getNameAndID()))
				{
					this.comboUsers.setSelectedIndex(i);
					break;
				}
//				System.out.println();
			}
			
			getMainFrameReference().ENTRY_LOG.refreshHistories();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public void refresh()
	{
		PatientData active = this.getSelectedPatientData();
		List<PatientData> list = FileOperation.getListOfPatients();
		this.comboUsers.setModel(new DefaultComboBoxModel<PatientData>(list.toArray(new PatientData[list.size()])));
		try
		{
			for (int i=0; i<this.comboUsers.getItemCount(); i++)
			{
				if (this.comboUsers.getItemAt(i).getNameAndID().equals(active.getNameAndID()))
				{
					this.comboUsers.setSelectedIndex(i);
					break;
				}
//				System.out.println();
			}
		}
		catch(NullPointerException ex) 
		{
			ex.printStackTrace();
		}
		this.revalidate();
		this.repaint();
	}
	private void displayPatientDetails()
	{
		if (this.radShow.isSelected())
		{
			try
			{
				this.remove(this.dataText);
				this.revalidate();
				this.repaint();
			}
			catch (NullPointerException ex) {}
			
			this.dataForm = new PatientDataForm((PatientData)this.comboUsers.getSelectedItem(), false);
			this.dataText = new PatientDataTextPanel(dataForm.getData());
			this.add(this.dataText, BorderLayout.SOUTH);
			this.revalidate();
			this.repaint();
		}
		else
		{
			try
			{
				this.remove(this.dataText);
				this.revalidate();
				this.repaint();
			}
			catch (NullPointerException ex) {}
		}
	}
	public boolean isPatientSelected()
	{
		try
		{
			if (this.getSelectedPatientData() == null)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		catch(NullPointerException ex) {return false;}
	}
	
	//Interfaces
	@Override
	public void itemStateChanged(ItemEvent e)
	{
		this.displayPatientDetails();
	}

	public void actionPerformed(ActionEvent e)
	{
		List<PatientData> list = FileOperation.getListOfPatients();
		if (this.patientFilter.isNameFilterSelected())
		{
			list = PatientDataOperation.filter(list, PatientData.NAME, this.patientFilter.getNameFilterKeyword());
		}
		
		if (this.patientFilter.isIDFilterSelected())
		{
			list = PatientDataOperation.filter(list, PatientData.MEDICAL_RECORD_ID, this.patientFilter.getIDFilterKeyword());
		}
		
		this.comboUsers.setModel(new DefaultComboBoxModel<PatientData>(list.toArray(new PatientData[list.size()])));
		this.displayPatientDetails();
		Globals.HISTORY_RECENT_MEDICATION.refresh(this.getSelectedPatientData());
		Globals.HISTORY_MEDICINE_COMPLAINT.refresh(this.getSelectedPatientData());
		getMainFrameReference().ENTRY_LOG.refreshHistories();
		this.revalidate();
		this.repaint();
	}
	
	@Override
	public void revalidateLanguage() 
	{
		this.labUser.setText(Methods.getLanguageText(XMLIdentifier.ACTIVE_PATIENT_PANEL_PATIENT_TEXT));
		this.radShow.setText(Methods.getLanguageText(XMLIdentifier.DETAILS_TEXT));
		this.panelFilter.setBorder(BorderFactory.createTitledBorder(Methods.getLanguageText(XMLIdentifier.FILTER_TEXT)));
		this.patientFilter.revalidateLanguage();this.dataText.revalidateLanguage();
		this.buttonFilter.setText(Methods.getLanguageText(XMLIdentifier.FILTER_TEXT));
	}
}
