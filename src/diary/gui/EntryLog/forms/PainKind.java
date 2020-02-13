package diary.gui.EntryLog.forms;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusEvent;

import diary.constants.Constants;
import diary.constants.Globals;
import diary.constants.XMLIdentifier;
import diary.data.PainEntryData;
import diary.history.HistoryPanel;
import diary.methods.Methods;
import diary.patientdata.PatientData;
import giantsweetroll.gui.swing.Gbm;

public class PainKind extends FormElement
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3663163509782789403L;
	
//	private JComboBox<String> combo;
//	private JTextField tf;
	private HistoryPanel painKind;
	
	//Constructor
	public PainKind()
	{
		super(Methods.getLanguageText(XMLIdentifier.KIND_OF_HEADPAIN_LABEL), true);
		
		//Initialization
//		this.combo = new JComboBox<String>(Methods.getDefaultPainKinds());
//		this.tf = new JTextField(10);
		this.painKind = new HistoryPanel(Globals.HISTORY_PAIN_KIND, PatientData.LAST_PAIN_KIND, Methods.getDefaultPainKinds(), true, false);
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		this.getPanel().setLayout(new GridBagLayout());
//		this.combo.addActionListener(this);
//		this.combo.setBackground(Color.WHITE);
//		this.tf.addMouseListener(this);
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_TOP_COMPONENT;
		c.gridwidth = 2;
		this.getPanel().add(this.getFormTitleLabel(), c);
		c.gridwidth = 1;
		Gbm.newGridLine(c);
		this.getPanel().add(this.painKind, c);
//		this.getPanel().add(this.combo, c);
//		Gbm.nextGridColumn(c);
//		this.getPanel().add(this.tf, c);
	}
	
	//Methods
	public void setPainKind(String painKind)
	{
		/*
		System.out.println("pain kind:" + painKind);
		if (painKind.equals(PainDataIdentifier.THROBBING) || painKind.equals(""))
		{
			this.painKind.setSelectedIndex(0);
		}
		else if (painKind.equals(PainDataIdentifier.PULSATING))
		{
			this.painKind.setSelectedIndex(1);
		}
		else if (painKind.equals(PainDataIdentifier.RADIATING))
		{
			this.painKind.setSelectedIndex(2);
		}
		else if (painKind.equals(PainDataIdentifier.TIGHT_BAND))
		{
			this.painKind.setSelectedIndex(3);
		}
		else
		{
//			this.combo.setSelectedItem(Methods.getLanguageText(XMLIdentifier.OTHER_TEXT));
//			this.tf.setText(painKind);
			this.painKind.setActiveItem(painKind);
		}		*/
		this.painKind.setActiveItem(painKind);
	}
	public void refreshHistory(PatientData patient)
	{
		try
		{
			this.painKind.refresh(Globals.HISTORY_PAIN_KIND, patient);
			this.painKind.setActiveItem(patient.getLastPainKind());
		}
		catch(NullPointerException ex) {}
	}
	
	//Overridden Methods
	@Override
	public void resetDefaults() 
	{
//		this.combo.setSelectedIndex(0);
//		this.tf.setText("");
		super.resetDefaults();
		this.painKind.resetDefaults();
	}
	
	@Override
	public void refresh() {};

	@Override
	public String getData() 
	{
		/*
		int index = this.painKind.getSelectedIndex();
		if (index == 0)
		{
			return PainDataIdentifier.THROBBING;
		}
		else if (index == 1)
		{
			return PainDataIdentifier.PULSATING;
		}
		else if (index == 2)
		{
			return PainDataIdentifier.RADIATING;
		}
		else if (index == 3)
		{
			return PainDataIdentifier.TIGHT_BAND;
		}
		else
		{
			return this.painKind.getItem();
		}
		*/
		return this.painKind.getItem();
	}

	@Override
	public void setData(Object obj)
	{
		if (obj instanceof PainEntryData)
		{
//			System.out.println("Hello");
			/*
			String painKind = Methods.convertPainKindIDToLanguage(((PainEntryData)obj).getPainKind());
			if (Methods.isPartOfDefaultPainKind(painKind))
			{
				this.combo.setSelectedItem(painKind);
			}
			else
			{
				this.combo.setSelectedItem(Methods.getLanguageText(XMLIdentifier.OTHER_TEXT));
				this.tf.setText(painKind);
			}
			*/
			String painKind = ((PainEntryData)obj).getPainKind();
			this.setPainKind(painKind);
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
			combo.setSelectedIndex(Methods.getDefaultPainKinds().length-1);
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
			combo.setSelectedIndex(Methods.getDefaultPainKinds().length-1);
			tf.setEditable(true);
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
	*/
	
	@Override
	public boolean allFilled() 
	{
		return !this.painKind.getItem().trim().equals("");
	}

	@Override
	public void revalidateLanguage() 
	{
		this.setFormTitle(Methods.getLanguageText(XMLIdentifier.KIND_OF_HEADPAIN_LABEL));
//		this.combo.setModel(new DefaultComboBoxModel<String>(Methods.getDefaultPainKinds()));
		this.painKind.revalidateLanguage(Methods.getDefaultPainKinds());
		this.revalidate();
		this.repaint();
	}

	@Override
	public String getName()
	{
		return Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_MAP_BUTTON_PAIN_KIND_TEXT);
	}
	
	@Override
	public void focusGained(FocusEvent e) 
	{
		this.painKind.requestFocusInWindow();
	}

	@Override
	public void focusLost(FocusEvent e) {}
}