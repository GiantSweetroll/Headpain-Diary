package diary.gui.EntryLog;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import diary.constants.Constants;
import diary.constants.Globals;
import diary.constants.PanelName;
import diary.constants.XMLIdentifier;
import diary.data.PainEntryData;
import diary.gui.GButton;
import diary.interfaces.LanguageListener;
import diary.methods.Methods;
import diary.patientdata.PatientData;
import giantsweetroll.gui.swing.Gbm;

public class EntryLogButtonControl extends JPanel implements ActionListener, LanguageListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3986380191389634823L;

	private GButton button;
	private JLabel label, labelDirection;
	private EntryLog entryLog;
	
	private boolean buttonType;
	
	//Constants
	public static final boolean NEXT = true;
	public static final boolean BACK = false;
	public static final String RIGHT_DIRECTION = "-->";
	public static final String LEFT_DIRECTION = "<--";
	
	//Constructor
	public EntryLogButtonControl(EntryLog entryLog, boolean next, String text)
	{
		//Initialization
		this.entryLog = entryLog;
		this.buttonType = next;
		if (next)
		{
			this.button = new GButton(Methods.getLanguageText(XMLIdentifier.NEXT_TEXT));
			this.button.setMnemonic(KeyEvent.VK_RIGHT);
			this.labelDirection = new JLabel(RIGHT_DIRECTION);
		}
		else
		{
			this.button = new GButton(Methods.getLanguageText(XMLIdentifier.BACK_TEXT));
			this.button.setMnemonic(KeyEvent.VK_LEFT);
			this.labelDirection = new JLabel(LEFT_DIRECTION);
		}
		this.label = new JLabel(text);
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		this.button.addActionListener(this);
//		this.button.setBackground(Constants.COLOR_MAIN_MENU_BUTTONS);
//		this.button.setForeground(Color.white);
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
			if (this.entryLog.getPanelState() == EntryLog.PROFILE_SELECTION)
			{
				Globals.MAIN_FRAME.changePanel(PanelName.MAIN_MENU);
			}
			else
			{
				byte state = this.entryLog.getPanelState();
				state--;
				this.entryLog.setPanelState(state);
				((CardLayout)this.entryLog.getPanelInput().getLayout()).previous(this.entryLog.getPanelInput());
				Globals.ENTRY_LOG.getFormSelectionMapPanel().updateSelection();
			}
		}
		else
		{
			if (this.entryLog.getPanelState() == EntryLog.COMMENTS_SELECTION)
			{
				PainEntryData entry = this.entryLog.getData();
				PatientData patient = this.entryLog.getSelectedPatient();
				
				this.entryLog.export(patient, entry);
			}
			else
			{
				byte state = this.entryLog.getPanelState();
				state++;
				this.entryLog.setPanelState(state);
				((CardLayout)this.entryLog.getPanelInput().getLayout()).next(this.entryLog.getPanelInput());
				Globals.ENTRY_LOG.getFormSelectionMapPanel().updateSelection();
			}
		}		
	}
	
	//Interface
	@Override
	public void actionPerformed(ActionEvent e)
	{
		this.changeEntryLogPanelState();
	}

	@Override
	public void revalidateLanguage() 
	{
		if(this.buttonType == EntryLogButtonControl.NEXT)
		{
			this.button.setText(Methods.getLanguageText(XMLIdentifier.NEXT_TEXT));
		}
		else
		{
			this.button.setText(Methods.getLanguageText(XMLIdentifier.BACK_TEXT));
		}
	}
}
