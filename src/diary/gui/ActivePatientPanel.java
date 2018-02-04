package diary.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
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
import diary.methods.PatientDataOperation;
import diary.patientdata.PatientDataForm;
import giantsweetroll.gui.swing.Gbm;

public class ActivePatientPanel extends JPanel implements ItemListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 136156090085473200L;
	
	private JLabel labUser;
	private JComboBox<String> comboUsers;
	private JRadioButton radShow;
	private PatientDataForm dataForm;
	private GridBagConstraints c;
	private Point locDataForm;
	
	public ActivePatientPanel()
	{
		//Initialization
		this.labUser = new JLabel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.ACTIVE_PATIENT_PANEL_PATIENT_TEXT), SwingConstants.CENTER);
		List<String> list = PatientDataOperation.getListOfNamesAndID();
		this.comboUsers = new JComboBox<String>(list.toArray(new String[list.size()]));
		this.radShow = new JRadioButton(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SHOW_TEXT));
		this.c = new GridBagConstraints();
		this.locDataForm = new Point();
		
		//Properties
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.fill = GridBagConstraints.BOTH;
		c.insets = Constants.INSETS_TOP_COMPONENT;
		this.add(this.labUser, c);						//Active Patient
		Gbm.nextGridColumn(c);
		this.add(this.comboUsers, c);					//Patients List
		Gbm.newGridLine(c);
		c.insets = Constants.INSETS_GENERAL;
		this.add(this.radShow, c);						//Show Radio Button
		Gbm.newGridLine(c);
		this.locDataForm.x = c.gridx;
		this.locDataForm.y = c.gridy;
	}

	//Interfaces
	@Override
	public void itemStateChanged(ItemEvent e)
	{
		if (this.radShow.isSelected())
		{
			
		}
	}
}
