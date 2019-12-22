package diary.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import diary.constants.Constants;
import diary.constants.Globals;
import diary.constants.XMLIdentifier;
import diary.interfaces.LanguageListener;
import diary.methods.FileOperation;
import diary.methods.Methods;
import diary.methods.PatientDataOperation;
import diary.patientdata.PatientData;
import diary.patientdata.PatientDataFilterPanel;
import diary.patientdata.PatientDataForm;
import diary.patientdata.PatientDataRenderer;
import diary.patientdata.PatientDataTextPanel;

public class ActivePatientPanel extends JPanel implements ActionListener, LanguageListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 136156090085473200L;
	
	private JLabel labUser;
	private JComboBox<PatientData> comboUsers;
	private PatientDataForm dataForm;
	private PatientDataTextPanel dataText;
	private JPanel panelFilter;
	private PatientDataFilterPanel patientFilter;
	private GButton buttonFilter, buttonDetails;
	private List<PatientData> patientList;
	
	//Constructors
	public ActivePatientPanel()
	{
		this.init();
	}
	public ActivePatientPanel(PatientData patient)
	{
		this.init();
		this.setSelectedPatient(patient);
	}
	
	//Methods
	private void init()
	{
		//Initialization
		this.labUser = new JLabel(Methods.getLanguageText(XMLIdentifier.ACTIVE_PATIENT_PANEL_PATIENT_TEXT), SwingConstants.CENTER);
		this.patientList = FileOperation.getListOfPatients();
		this.comboUsers = new JComboBox<PatientData>(this.patientList.toArray(new PatientData[this.patientList.size()]));
		this.buttonDetails = new GButton(Methods.getLanguageText(XMLIdentifier.SHOW_DETAILS_TEXT));
		this.buttonFilter = new GButton(Methods.getLanguageText(XMLIdentifier.FILTER_TEXT));
		this.initPanelFilter();
		
		//Properties
		this.setLayout(new FlowLayout(FlowLayout.LEFT, Constants.INSETS_BASE, Constants.INSETS_BASE));
		this.setOpaque(false);
		this.buttonFilter.addActionListener(this);
//		this.buttonFilter.setAlignmentX(CENTER_ALIGNMENT);
		this.comboUsers.setRenderer(new PatientDataRenderer());
		this.comboUsers.setBackground(Color.WHITE);
		this.comboUsers.addItemListener(new ItemListener()
				{
					@Override
					public void itemStateChanged(ItemEvent e)
					{
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
						Globals.ENTRY_LOG.refreshHistories();
						Globals.GRAPH_FILTER_PANEL.refresh(getSelectedPatientData());
					}
					
				});
		this.buttonDetails.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						dataForm = new PatientDataForm((PatientData)comboUsers.getSelectedItem(), false);
						dataText = new PatientDataTextPanel(dataForm.getData());
						JOptionPane.showMessageDialog(null, dataText, Methods.getLanguageText(XMLIdentifier.DETAILS_TEXT), JOptionPane.INFORMATION_MESSAGE);
					}
				});
		
		//Add to panel
		this.add(this.labUser);
		this.add(this.comboUsers);
		this.add(this.buttonDetails);
		this.add(this.buttonFilter);
	}
	public void initPanelFilter()
	{
		//Initialization
		this.panelFilter = new JPanel();
		this.patientFilter = new PatientDataFilterPanel();
		
		//Properties
		this.panelFilter.setLayout(new BoxLayout(this.panelFilter, BoxLayout.Y_AXIS));
		this.panelFilter.setOpaque(false);
//		this.patientFilter.setBorder(BorderFactory.createTitledBorder(Methods.getLanguageText(XMLIdentifier.FILTER_TEXT)));
		this.patientFilter.setAlignmentX(CENTER_ALIGNMENT);
		
		//Add to panel
		this.panelFilter.add(this.patientFilter);
		this.panelFilter.add(Box.createRigidArea(new Dimension(5, 5)));			///Empty Component for BoxLayout spacing
	}
	public PatientData getSelectedPatientData()
	{
		if (this.patientList.size() == 0)
		{
			return null;
		}
		else
		{
			return (PatientData)this.comboUsers.getSelectedItem();
		}
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
			
			Globals.ENTRY_LOG.refreshHistories();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public void refresh()
	{
		PatientData active = this.getSelectedPatientData();
		this.patientList = FileOperation.getListOfPatients();
		this.comboUsers.setModel(new DefaultComboBoxModel<PatientData>(this.patientList.toArray(new PatientData[this.patientList.size()])));
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
	public void setDropDownMenuFocus()
	{
		this.comboUsers.requestFocus();
	}
	public void filter()
	{
		this.patientList = FileOperation.getListOfPatients();
		if (this.patientFilter.isNameFilterSelected())
		{
			this.patientList = PatientDataOperation.filter(this.patientList, PatientData.NAME, this.patientFilter.getNameFilterKeyword());
		}
		
		if (this.patientFilter.isIDFilterSelected())
		{
			this.patientList = PatientDataOperation.filter(this.patientList, PatientData.MEDICAL_RECORD_ID, this.patientFilter.getIDFilterKeyword());
		}
		
		this.comboUsers.setModel(new DefaultComboBoxModel<PatientData>(this.patientList.toArray(new PatientData[this.patientList.size()])));
		Globals.HISTORY_RECENT_MEDICATION.refresh(this.getSelectedPatientData());
		Globals.HISTORY_MEDICINE_COMPLAINT.refresh(this.getSelectedPatientData());
		Globals.HISTORY_PAIN_KIND.refresh(this.getSelectedPatientData());
		Globals.HISTORY_TRIGGER.refresh(this.getSelectedPatientData());
		Globals.ENTRY_LOG.refreshHistories();
		this.revalidate();
		this.repaint();
	}
	
	//Interfaces
	@Override
	public void actionPerformed(ActionEvent e)
	{
		JOptionPane.showMessageDialog(null, this.panelFilter, Methods.getLanguageText(XMLIdentifier.FILTER_TEXT), JOptionPane.PLAIN_MESSAGE);
		this.filter();
	}
	
	public void showDetailsButtonClicked(ActionEvent e)
	{
		JOptionPane.showMessageDialog(null, this.dataText);
	}
	
	@Override
	public void revalidateLanguage() 
	{
		this.labUser.setText(Methods.getLanguageText(XMLIdentifier.ACTIVE_PATIENT_PANEL_PATIENT_TEXT));
		this.patientFilter.revalidateLanguage();
		try
		{
			this.dataText.revalidateLanguage();
		}
		catch(NullPointerException ex){}
		this.buttonFilter.setText(Methods.getLanguageText(XMLIdentifier.FILTER_TEXT));
		this.buttonDetails.setText(Methods.getLanguageText(XMLIdentifier.SHOW_DETAILS_TEXT));
	}
}
