package diary.patientdata;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import diary.constants.Constants;
import diary.constants.XMLIdentifier;
import diary.gui.DateRangePanel;
import giantsweetroll.gui.swing.Gbm;

public class PatientDataManagePanel extends JPanel implements ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7220293959734922865L;
	
	private JPanel panelTop, panelTopLeft, panelTable, panelBelowLeft, panelBelowCenter, panelBelow;
	private JCheckBox checkMedRec, checkName, checkDOB;
	private JTextField tfMedRec, tfName;
	private DateRangePanel panelDateRange;
	private JButton butBack, butNew;
	
	//Constants
	private final String BACK = "back";
	private final String NEW = "new";
	
	//Constructors
	public PatientDataManagePanel()
	{
		
	}
	
	//Methods
	private void init()
	{
		//Initialization
		this.initPanelBelow();
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
	private void initPanelBelowCenter()
	{
		//Initializations
		this.panelBelowCenter = new JPanel();
		this.butNew = new JButton(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.NEW_TEXT));
		
		//Properties
		this.panelBelowCenter.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.panelBelowCenter.setOpaque(false);
		this.butNew.setActionCommand(this.NEW);
		this.butNew.addActionListener(this);
		
		//add to panel
		this.panelBelowCenter.add(this.butNew);		
	}
	private void initPanelBelow()
	{
		//Initialization
		this.panelBelow = new JPanel();
		this.initPanelBelowCenter();
		this.initPanelBelowLeft();
		
		//Properties
		this.panelBelow.setLayout(new BorderLayout());
		this.panelBelow.setOpaque(false);
		
		//add to panel
		this.panelBelow.add(this.panelBelowCenter, BorderLayout.CENTER);
		this.panelBelow.add(this.panelBelowLeft, BorderLayout.WEST);
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
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		this.panelTopLeft.setLayout(new GridBagLayout());
		this.panelTopLeft.setOpaque(false);
		this.setBorder(BorderFactory.createTitledBorder(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.FILTER_TEXT)));
		
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
		Gbm.newGridLine(c);
	}
	
	//Interfaces
	public void actionPerformed(ActionEvent e)
	{
		switch(e.getActionCommand())
		{
			case BACK:
				break;
				
			case NEW:
				break;
		}
	}
}
