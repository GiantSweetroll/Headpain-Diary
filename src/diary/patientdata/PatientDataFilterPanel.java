package diary.patientdata;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import diary.constants.Constants;
import diary.constants.XMLIdentifier;
import diary.interfaces.LanguageListener;
import diary.methods.Methods;
import giantsweetroll.gui.swing.Gbm;

public class PatientDataFilterPanel extends JPanel implements ItemListener, LanguageListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6871036706181283423L;
	
	private JCheckBox checkName, checkID;
	private JTextField tfName, tfID;
	
	//Constructors
	public PatientDataFilterPanel()
	{
		//Initialization
		this.checkName = new JCheckBox(Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_NAME_LABEL));
		this.checkID = new JCheckBox(Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_MED_ID_LABEL));
		this.tfName = new JTextField(20);
		this.tfID = new JTextField(20);
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		this.checkID.addItemListener(this);
		this.checkName.addItemListener(this);
		this.tfName.setHorizontalAlignment(SwingConstants.CENTER);
		this.tfName.setEditable(false);
		this.tfID.setHorizontalAlignment(SwingConstants.CENTER);
		this.tfID.setEditable(false);
		this.checkID.setOpaque(false);
		this.checkName.setOpaque(false);
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_TOP_COMPONENT;
		c.fill = GridBagConstraints.BOTH;
		this.add(this.checkName, c);			//Name Radio Button
		Gbm.nextGridColumn(c);
		this.add(this.tfName, c);				//Name Text Field
		Gbm.newGridLine(c);
		c.insets = Constants.INSETS_GENERAL;
		this.add(this.checkID, c);				//Medical ID Radio Button
		Gbm.nextGridColumn(c);
		this.add(this.tfID, c);					//Medical ID Text Field
	}
	
	//Methods
	public boolean isNameFilterSelected()
	{
		return this.checkName.isSelected();
	}
	public boolean isIDFilterSelected()
	{
		return this.checkID.isSelected();
	}
	public void setNameFilterSelected(boolean select)
	{
		this.checkName.setSelected(select);
	}
	public void setIDFilterSelected(boolean select)
	{
		this.checkID.setSelected(select);
	}
	public String getNameFilterKeyword()
	{
		if (this.isNameFilterSelected())
		{
			return this.tfName.getText().trim();
		}
		else
		{
			return "";
		}
	}
	public String getIDFilterKeyword()
	{
		if (this.isIDFilterSelected())
		{
			return this.tfID.getText().trim();
		}
		else
		{
			return "";
		}
	}
	
	//Interfaces
	@Override
	public void itemStateChanged(ItemEvent e)
	{
		this.tfName.setEditable(this.checkName.isSelected());
		this.tfID.setEditable(this.checkID.isSelected());
	}

	@Override
	public void revalidateLanguage() 
	{
		this.checkName.setText(Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_NAME_LABEL));
		this.checkID.setText(Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_MED_ID_LABEL));
	}
}
