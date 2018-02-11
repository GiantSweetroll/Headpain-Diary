package diary.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import diary.constants.Constants;
import diary.constants.XMLIdentifier;
import diary.methods.FileOperation;
import diary.methods.Methods;
import diary.patientdata.PatientData;
import diary.patientdata.PatientDataForm;
import diary.patientdata.PatientDataRenderer;
import giantsweetroll.gui.swing.Gbm;

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
	private GridBagConstraints c;
	private JPanel panelCenter;
	
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
		this.setOpaque(false);
		
		//Add to panel
		this.add(this.panelCenter, BorderLayout.CENTER);		
	}
	private void initPanelCenter()
	{
		//Initialization
		this.panelCenter = new JPanel();
		this.labUser = new JLabel(Methods.getLanguageText(XMLIdentifier.ACTIVE_PATIENT_PANEL_PATIENT_TEXT), SwingConstants.CENTER);
		List<PatientData> list = FileOperation.getListOfPatients();
		this.comboUsers = new JComboBox<PatientData>(list.toArray(new PatientData[list.size()]));
		this.radShow = new JRadioButton(Methods.getLanguageText(XMLIdentifier.SHOW_TEXT));
		this.c = new GridBagConstraints();
		
		//Properties
		this.panelCenter.setLayout(new GridBagLayout());
		this.panelCenter.setOpaque(false);
		this.radShow.addItemListener(this);
		this.comboUsers.setRenderer(new PatientDataRenderer());
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.fill = GridBagConstraints.BOTH;
		c.insets = Constants.INSETS_TOP_COMPONENT;
		this.panelCenter.add(this.labUser, c);						//Active Patient
		Gbm.nextGridColumn(c);
		this.panelCenter.add(this.comboUsers, c);					//Patients List
		Gbm.newGridLine(c);
		c.insets = Constants.INSETS_GENERAL;
		this.panelCenter.add(this.radShow, c);						//Show Radio Button
		Gbm.newGridLine(c);		
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
			this.add(this.dataForm, BorderLayout.SOUTH);
			this.revalidate();
			this.repaint();
		}
		else
		{
			try
			{
				this.remove(this.dataForm);
				this.revalidate();
				this.repaint();
			}
			catch (NullPointerException ex) {}
		}
	}
}
