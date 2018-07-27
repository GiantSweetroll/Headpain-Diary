package diary.gui.EntryLog;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import diary.constants.Constants;
import diary.constants.Globals;
import diary.constants.PanelName;
import diary.constants.XMLIdentifier;
import diary.data.PainEntryData;
import diary.gui.CustomDialog;
import diary.gui.MainFrame;
import diary.gui.MainFramePanel;
import diary.methods.FileOperation;
import diary.methods.Methods;
import diary.methods.PainDataOperation;
import diary.patientdata.PatientData;
import giantsweetroll.date.Date;
import giantsweetroll.gui.swing.Gbm;
import giantsweetroll.message.MessageManager;

public class EntryLogButtonControl extends MainFramePanel implements ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3986380191389634823L;

	private JButton button;
	private JLabel label, labelDirection;
	private EntryLog entryLog;
	
	private boolean buttonType;
	
	//Constants
	public static final boolean NEXT = true;
	public static final boolean BACK = false;
	public static final String RIGHT_DIRECTION = "-->";
	public static final String LEFT_DIRECTION = "<--";
	
	//Constructor
	public EntryLogButtonControl(MainFrame mainFrame, EntryLog entryLog, boolean next, String text)
	{
		super(mainFrame);
		//Initialization
		this.entryLog = entryLog;
		this.buttonType = next;
		if (next)
		{
			this.button = new JButton(Methods.getLanguageText(XMLIdentifier.NEXT_TEXT));
			this.labelDirection = new JLabel(RIGHT_DIRECTION);
		}
		else
		{
			this.button = new JButton(Methods.getLanguageText(XMLIdentifier.BACK_TEXT));
			this.labelDirection = new JLabel(LEFT_DIRECTION);
		}
		this.label = new JLabel(text);
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		this.button.addActionListener(this);
		this.button.setBackground(Constants.COLOR_MAIN_MENU_BUTTONS);
		this.button.setForeground(Color.white);
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_TOP_COMPONENT;
		c.gridwidth = 2;
		this.add(this.button, c);				//Button
		Gbm.newGridLine(c);
		c.gridwidth = 1;
		c.insets = Constants.INSETS_GENERAL;
		if (next)
		{
			this.add(this.label, c);				//Text
			Gbm.nextGridColumn(c);
			this.add(this.labelDirection, c);		//Direction
		}
		else
		{
			this.add(this.labelDirection, c);		//Direction
			Gbm.nextGridColumn(c);
			this.add(this.label, c);				//Text
		}
	}
	
	//Methods
	public void changeEntryLogPanelState()
	{
		//Change entry log active center panel
		if (this.buttonType == EntryLogButtonControl.BACK)
		{	
			if (this.entryLog.getPanelState() == EntryLog.FIRST_SECTION)
			{
				this.getMainFrameReference().changePanel(PanelName.MAIN_MENU);
			}
			else
			{
				byte state = this.entryLog.getPanelState();
				state--;
				this.entryLog.setPanelState(state);
				((CardLayout)this.entryLog.getPanelCenter().getLayout()).previous(this.entryLog.getPanelCenter());
			}
		}
		else
		{
			if (this.entryLog.getPanelState() == EntryLog.LAST_SECTION)
			{
				if (this.entryLog.allRequiredFieldsFilled())
				{
					PainEntryData entry = this.entryLog.getData();
					PatientData patient = this.entryLog.getSelectedPatient();
					
					if(entry.isSingleEntry()) 
					{
						if (FileOperation.entryExists(patient, entry) && this.entryLog.isNewEntry())
						{
							int response = CustomDialog.showConfirmDialog(Methods.getLanguageText(XMLIdentifier.MESSAGE_OVERWRITE_CONFIRM_TITLE), 
																		Methods.getLanguageText(XMLIdentifier.MESSAGE_OVERWRITE_CONFIRM_TEXT));
							
							if (response == JOptionPane.YES_OPTION)
							{
								this.entryLog.export(patient, entry);
							}
						}
						else
						{
							this.entryLog.export(patient, entry);
						}
						this.entryLog.refresh();
					}
					else
					{
						List<PainEntryData> duplicateEntries = PainDataOperation.generateDuplicates(entry, new Date(entry.getDate().getDay() + Methods.secondsToDays(Long.parseLong(entry.getDuration())),
																													entry.getDate().getMonth(),
																													entry.getDate().getYear()));
						
						FileOperation.updateHistory(Globals.HISTORY_RECENT_MEDICATION, this.entryLog.getSelectedPatient(), entry.getRecentMedication());
						FileOperation.updateHistory(Globals.HISTORY_MEDICINE_COMPLAINT, this.entryLog.getSelectedPatient(), entry.getMedicineComplaint());
						for (PainEntryData painEntry : duplicateEntries)
						{
							FileOperation.exportPainData(patient, painEntry);
						}
						this.getMainFrameReference().changePanel(PanelName.MAIN_MENU);
						this.getMainFrameReference().GRAPH_PANEL.refreshGraph();
						this.getMainFrameReference().PAIN_TABLE.refreshTable();
						Methods.refresHistories(this.entryLog.getSelectedPatient());
						this.getMainFrameReference().GRAPH_FILTER_PANEL.refresh(this.getMainFrameReference().GRAPH_PANEL.getActivePatientPanel().getSelectedPatientData());
						this.entryLog.refresh();
						this.entryLog.resetDefaults();
					}
				}
				else
				{
					MessageManager.showErrorDialog(Methods.getLanguageText(XMLIdentifier.ERROR_REQUIRED_FIELDS_DIALOG_TEXT),
							Methods.getLanguageText(XMLIdentifier.ERROR_REQUIRED_FIELDS_DIALOG_TITLE));
				}
			}
			else
			{
				byte state = this.entryLog.getPanelState();
				state++;
				this.entryLog.setPanelState(state);
				((CardLayout)this.entryLog.getPanelCenter().getLayout()).next(this.entryLog.getPanelCenter());
			}
		}		
	}
	
	//Interface
	@Override
	public void actionPerformed(ActionEvent e)
	{
		this.changeEntryLogPanelState();
	}
}
