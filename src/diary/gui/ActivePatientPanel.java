package diary.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import diary.constants.XMLIdentifier;
import diary.methods.FileOperation;
import diary.methods.Methods;
import diary.patientdata.PatientData;
import diary.patientdata.PatientDataForm;
import diary.patientdata.PatientDataRenderer;
import diary.patientdata.PatientDataTextPanel;

public class ActivePatientPanel extends JPanel implements ItemListener
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
	private JPanel panelCenter, panelSelection, panelShow;
	
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
		this.initPanelCenter();
		
		//Properties
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
		this.setOpaque(false);
		
		//Add to panel
		this.add(this.panelCenter, BorderLayout.CENTER);		
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
	public PatientData getSelectedPatientData()
	{
		return (PatientData)this.comboUsers.getSelectedItem();
	}
	public void setSelectedPatient(PatientData patient)
	{
		try
		{
			this.comboUsers.setSelectedItem(patient);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	//Interfaces
	@Override
	public void itemStateChanged(ItemEvent e)
	{
//		System.out.println("sdsadsacwacsd");
		if (this.radShow.isSelected())
		{
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
}
