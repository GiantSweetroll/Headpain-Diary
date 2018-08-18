package diary.patientdata;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import diary.constants.Constants;
import diary.constants.PanelName;
import diary.constants.XMLIdentifier;
import diary.gui.CustomDialog;
import diary.gui.MainFrame;
import diary.gui.MainFramePanel;
import diary.methods.FileOperation;
import diary.methods.Methods;
import giantsweetroll.message.MessageManager;

public class PatientDataRegisterationForm extends MainFramePanel implements ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4531072921811000380L;
	
	private JPanel panelTitle, panelCenter, panelBelow, panelBelowLeft, panelBelowRight;
	private PatientDataForm patientForm;
	private JLabel labTitle;
	private JButton butCancel, butSave;
	
	//Conditioning
	private String situation;
	private String lastMedID;
	private boolean canCancel;
	
	//Constants
	private final String CANCEL = "cancel";
	private final String SAVE = "save";
	private final String NEW_USER = "new user";
	private final String EDIT_USER = "edit user";
	
	public PatientDataRegisterationForm(MainFrame frame, boolean allowCancel)
	{
		super(frame);
		this.canCancel = allowCancel;
		this.createAndShowGUI();
		this.situation = this.NEW_USER;
	}
	
	public PatientDataRegisterationForm(MainFrame frame, PatientData patientData)
	{
		super(frame);
		this.createAndShowGUI();
		this.situation = this.EDIT_USER;
		this.lastMedID = patientData.getDataMap().get(PatientData.MEDICAL_RECORD_ID);
		this.canCancel = true;
		this.patientForm.setData(patientData);
	}
	
	//Methods
	//init gui
	private void createAndShowGUI()
	{
		//Initialization
		this.initPanelBelow();
		this.initPanelCenter();
		
		//properties
		this.setLayout(new BorderLayout());
		this.setOpaque(false);
		
		//add to panel
		this.add(this.panelCenter, BorderLayout.CENTER);
		this.add(this.panelBelow, BorderLayout.SOUTH);
	}
	private void initPanelTitle()
	{
		//Initialization
		this.panelTitle = new JPanel();
		this.labTitle = new JLabel (Constants.LANGUAGE.getTextMap().get(XMLIdentifier.PATIENT_DATA_REGISTERATION_FORM_TITLE_LABEL));
		
		//Properties
		this.panelTitle.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.panelTitle.setOpaque(false);
		this.labTitle.setFont(Constants.FONT_SUB_TITLE);
		
		//add to panel
		this.panelTitle.add(this.labTitle);
	}
	private void initPanelBelowLeft()
	{
		//Initialization
		this.panelBelowLeft = new JPanel();
		this.butCancel = new JButton(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.CANCEL_TEXT));
		
		//Properties
		this.panelBelowLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.panelBelowLeft.setOpaque(false);
		this.butCancel.setActionCommand(this.CANCEL);
		this.butCancel.addActionListener(this);
		
		//add to panel
		this.panelBelowLeft.add(this.butCancel);
	}
	private void initPanelBelowRight()
	{
		//Initialization
		this.panelBelowRight = new JPanel();
		this.butSave = new JButton(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SAVE_TEXT));
		
		//Properties
		this.panelBelowRight.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.panelBelowRight.setOpaque(false);
		this.butSave.setActionCommand(this.SAVE);
		this.butSave.addActionListener(this);
		
		//Add to panel
		this.panelBelowRight.add(this.butSave);
	}
	private void initPanelBelow()
	{
		//Initialization
		this.panelBelow = new JPanel();
		this.initPanelBelowLeft();
		this.initPanelBelowRight();
		
		//Properties
		this.panelBelow.setLayout(new BorderLayout());
		this.panelBelow.setOpaque(false);
		
		//add to panel
		this.panelBelow.add(this.panelBelowLeft, BorderLayout.WEST);
		this.panelBelow.add(this.panelBelowRight, BorderLayout.EAST);
	}
	private void initPanelCenter()
	{
		//Initialization
		this.panelCenter = new JPanel();
		this.initPanelTitle();
		this.patientForm = new PatientDataForm(true);
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		this.panelCenter.setLayout(new GridBagLayout());
		this.panelCenter.setOpaque(false);
		
		//add to panel
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = Constants.INSETS_TITLE;
		this.panelCenter.add(this.panelTitle, c);			//Title
		c.insets = Constants.INSETS_GENERAL;
		this.panelCenter.add(this.patientForm, c);			//Patient Data Form
	}
	
	//Interfaces
	public void actionPerformed(ActionEvent e)
	{
		switch(e.getActionCommand())
		{
			case CANCEL:
				if (this.canCancel)
				{
					try
					{
		//				MainFrame.changePanel(MainFrame.lastComponent);
						this.getMainFrameReference().changePanel(PanelName.MANAGE_PATIENTS_PANEL);
					}
					catch(NullPointerException ex) {ex.printStackTrace();}
				}
				break;
				
			case SAVE:
				if(this.patientForm.isEmptyID())
				{
					MessageManager.showErrorDialog(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.PATIENT_DATA_REGISTERATION_FORM_EMPTY_ID_MESSAGE),
													Constants.LANGUAGE.getTextMap().get(XMLIdentifier.PATIENT_DATA_REGISTERATION_FORM_EMPTY_ID_MESSAGE_TITLE));
				}
				else if (this.patientForm.isEmptyName())
				{
					MessageManager.showErrorDialog(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.PATIENT_DATA_REGISTERATION_FORM_EMPTY_NAME_MESSAGE),
													Constants.LANGUAGE.getTextMap().get(XMLIdentifier.PATIENT_DATA_REGISTERATION_FORM_EMPTY_NAME_MESSAGE_TITLE));
				}
				else
				{
					PatientData patientData = this.patientForm.getData();
					
					if (this.situation.equals(this.NEW_USER))
					{
						//Check if the same medical ID exists
						List<PatientData> patients = FileOperation.getListOfPatients();
						boolean unique = true;
						for (int i=0; i<patients.size(); i++)
						{
							if (patients.get(i).getDataMap().get(PatientData.MEDICAL_RECORD_ID).equals(patientData.getDataMap().get(PatientData.MEDICAL_RECORD_ID)))		//Case sensitive
							{
								unique = false;
								break;
							}
						}
						
						if(!unique)
						{
							int response = CustomDialog.showConfirmDialog(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.PATIENT_DATA_REGISTERATION_FORM_OVERWRITE_MESSAGE_TITLE),
															Constants.LANGUAGE.getTextMap().get(XMLIdentifier.PATIENT_DATA_REGISTERATION_FORM_OVERWRITE_MESSAGE_TEXT));
							
							if (response == JOptionPane.YES_OPTION)
							{
								FileOperation.savePatientData(patientData);
			//					MainFrame.changePanel(MainFrame.lastComponent);
								this.getMainFrameReference().changePanel(PanelName.MAIN_MENU);
							}
						}
						else
						{
							FileOperation.savePatientData(patientData);
			//				MainFrame.changePanel(MainFrame.lastComponent);
							this.getMainFrameReference().changePanel(PanelName.MAIN_MENU);
						}
					}
					else if (this.situation.equals(this.EDIT_USER))
					{
						//Check for change in med id, if yes, delete the old one
						FileOperation.savePatientData(patientData);
						if (!this.lastMedID.equals(patientData.getDataMap().get(PatientData.MEDICAL_RECORD_ID)))
						{
							FileOperation.deletePatientData(this.lastMedID);
						}
		//				MainFrame.changePanel(MainFrame.lastComponent);
						this.getMainFrameReference().changePanel(PanelName.MANAGE_PATIENTS_PANEL);
					}
					this.getMainFrameReference().MANAGE_PATIENTS_PANEL.refresh();
				}
				break;
		}
	}

	@Override
	public void revalidateLanguage() 
	{
		this.labTitle.setText(Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_REGISTERATION_FORM_TITLE_LABEL));
		this.patientForm.revalidateLanguage();
	}
}
