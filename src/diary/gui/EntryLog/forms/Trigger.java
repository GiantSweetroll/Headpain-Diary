package diary.gui.EntryLog.forms;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import diary.constants.Constants;
import diary.constants.Globals;
import diary.constants.XMLIdentifier;
import diary.data.PainEntryData;
import diary.history.HistoryPanel;
import diary.methods.Methods;
import diary.patientdata.PatientData;
import giantsweetroll.gui.swing.Gbm;

public class Trigger extends FormElement
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1806833429132288139L;

//	private JComboBox<String> combo;
//	private JTextField tf;
	private HistoryPanel trigger;
	
	//Constructor
	public Trigger()
	{
		super(Methods.getLanguageText(XMLIdentifier.TRIGGER_TEXT), true);

		//Initialization
//		this.combo = new JComboBox<String>(Methods.getDefaultTriggers());
//		this.tf = new JTextField(10);
		this.trigger = new HistoryPanel(Globals.HISTORY_TRIGGER, PatientData.LAST_PAIN_KIND, Methods.getDefaultTriggers(), true, false);
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		this.getPanel().setLayout(new GridBagLayout());
//		this.combo.addActionListener(this);
//		this.combo.setBackground(Color.WHITE);
//		this.tf.addMouseListener(this);
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.gridwidth = 2;
		c.insets = Constants.INSETS_TOP_COMPONENT;
		this.getPanel().add(this.getFormTitleLabel(), c);
		c.gridwidth = 1;
		Gbm.newGridLine(c);
		this.getPanel().add(this.trigger, c);
//		this.getPanel().add(this.combo, c);
//		Gbm.nextGridColumn(c);
//		this.getPanel().add(this.tf, c);
	}
	
	//Methods
	public void refreshHistory(PatientData patient)
	{
		try
		{
			this.trigger.refresh(Globals.HISTORY_TRIGGER, patient);
			this.trigger.setActiveItem(patient.getLastTrigger());
		}
		catch(NullPointerException ex) {}
	}
	
	//Overridden Methods
	@Override
	public void resetDefaults() 
	{
		super.resetDefaults();
		this.trigger.resetDefaults();
	//	this.combo.setSelectedIndex(0);
	//	this.tf.setText("");
	}
	
	@Override
	public void refresh() {};

	@Override
	public String getData()
	{
	/*	if (this.combo.getSelectedIndex() == Methods.getDefaultTriggers().length-1)		//If last index = Other
		{
			return this.tf.getText().trim();
		}
		else
		{
			return GGUtilities.getItem(this.combo).toString();
		}		*/
		return this.trigger.getItem();
	}

	@Override
	public void setData(Object entry)
	{
		if (entry instanceof PainEntryData)
		{
			String trigger = ((PainEntryData)entry).getTrigger();
			this.trigger.setActiveItem(trigger);
		}
	}	
/*	
	public void actionPerformed(ActionEvent e)
	{
		String item = GGUtilities.getItem(combo).toString();
		if (item.equals(Methods.getLanguageText(XMLIdentifier.OTHER_TEXT)))
		{
			tf.setEditable(true);
		}
		else
		{
			tf.setEditable(false);
			tf.setText("");
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (!tf.isEditable())
		{
			combo.setSelectedIndex(Methods.getDefaultTriggers().length-1);
			tf.setEditable(true);
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if (!tf.isEditable())
		{
			combo.setSelectedIndex(Methods.getDefaultTriggers().length-1);
			tf.setEditable(true);
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

	@Override
	public boolean allFilled()
	{
		if (this.combo.getSelectedIndex() == Methods.getDefaultTriggers().length-1)		//If last index = Other
		{
			return !Methods.getTextData(this.tf).equals("");
		}
		else
		{
			return true;
		}
	}		*/

	@Override
	public void revalidateLanguage() 
	{
		this.setFormTitle(Methods.getLanguageText(XMLIdentifier.TRIGGER_TEXT));
//		this.combo.setModel(new DefaultComboBoxModel<String>(Methods.getDefaultTriggers()));
		this.trigger.revalidateLanguage(Methods.getDefaultTriggers());
		this.revalidate();
		this.repaint();
	}

	@Override
	public boolean allFilled()
	{
		if (this.trigger.getSelectedIndex() == Methods.getDefaultTriggers().length)		//If last index = Other
		{
//			return !Methods.getTextData(this.tf).equals("");
			return !this.trigger.getItem().equals("");
		}
		else
		{
			return true;
		}
	}
}