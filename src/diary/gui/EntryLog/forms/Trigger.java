package diary.gui.EntryLog.forms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import diary.constants.Constants;
import diary.constants.PainDataIdentifier;
import diary.constants.XMLIdentifier;
import diary.data.PainEntryData;
import diary.methods.Methods;
import giantsweetroll.GMisc;

public class Trigger extends FormElement implements ActionListener, MouseListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1806833429132288139L;

	private JComboBox<String> combo;
	private JTextField tf;
	
	//Constructor
	public Trigger()
	{
		super(Methods.getLanguageText(XMLIdentifier.TRIGGER_TEXT));

		//Initialization
		this.combo = new JComboBox<String>(Constants.DEFAULT_ACTIVITIES);
		this.tf = new JTextField(10);
		
		//Properties
		this.combo.addActionListener(this);
		this.tf.addMouseListener(this);
		
		//Add to panel
		this.addComponent(this.combo);
		this.addComponent(this.tf);
	}
	
	//Overriden Methods
	@Override
	public void resetDefaults() {
		this.combo.setSelectedIndex(0);
		this.tf.setText("");
	}
	
	@Override
	public void refresh() {};

	@Override
	public String getData() {
		if (this.combo.getSelectedIndex() == Constants.DEFAULT_ACTIVITIES.length-1)		//If last index = Other
		{
			return GMisc.getItem(this.combo).toString();
		}
		else
		{
			return this.tf.getText().trim();
		}
	}

	@Override
	public void setData(Object obj)
	{
		if (obj instanceof PainEntryData)
		{
			String painKind = Methods.convertPainKindIDToLanguage(((PainEntryData)obj).getDataMap().get(PainDataIdentifier.PAIN_KIND).toString());
			if (Methods.isPartOfDefaultPainKind(painKind))
			{
				this.combo.setSelectedItem(painKind);
			}
			else
			{
				this.combo.setSelectedItem(Methods.getLanguageText(XMLIdentifier.OTHER_TEXT));
				this.tf.setText(painKind);
			}
		}
	}	
	
	public void actionPerformed(ActionEvent e)
	{
		String item = GMisc.getItem(combo).toString();
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
			combo.setSelectedIndex(Constants.DEFAULT_ACTIVITIES.length-1);
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
			combo.setSelectedIndex(Constants.DEFAULT_ACTIVITIES.length-1);
			tf.setEditable(true);
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

}
