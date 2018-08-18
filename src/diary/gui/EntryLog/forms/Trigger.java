package diary.gui.EntryLog.forms;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import diary.constants.Constants;
import diary.constants.XMLIdentifier;
import diary.data.PainEntryData;
import diary.methods.Methods;
import giantsweetroll.GMisc;
import giantsweetroll.gui.swing.Gbm;

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
		super(Methods.getLanguageText(XMLIdentifier.TRIGGER_TEXT), true);

		//Initialization
		this.combo = new JComboBox<String>(Constants.DEFAULT_ACTIVITIES);
		this.tf = new JTextField(10);
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		this.getPanel().setLayout(new GridBagLayout());
		this.combo.addActionListener(this);
		this.combo.setBackground(Color.WHITE);
		this.tf.addMouseListener(this);
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.gridwidth = 2;
		c.insets = Constants.INSETS_TOP_COMPONENT;
		this.getPanel().add(this.getFormTitleLabel(), c);
		c.gridwidth = 1;
		Gbm.newGridLine(c);
		this.getPanel().add(this.combo, c);
		Gbm.nextGridColumn(c);
		this.getPanel().add(this.tf, c);
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
			return this.tf.getText().trim();
		}
		else
		{
			return GMisc.getItem(this.combo).toString();
		}
	}

	@Override
	public void setData(Object entry)
	{
		if (entry instanceof PainEntryData)
		{
			String trigger = Methods.convertPainKindIDToLanguage(((PainEntryData)entry).getTrigger());
			if (Methods.isPartOfDefaultActivity(trigger))
			{
				this.combo.setSelectedItem(trigger);
			}
			else
			{
				this.combo.setSelectedItem(Methods.getLanguageText(XMLIdentifier.OTHER_TEXT));
				this.tf.setText(trigger);
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

	@Override
	public boolean allFilled()
	{
		if (this.combo.getSelectedIndex() == Constants.DEFAULT_ACTIVITIES.length-1)		//If last index = Other
		{
			return !Methods.getTextData(this.tf).equals("");
		}
		else
		{
			return true;
		}
	}

	@Override
	public void revalidateLanguage() 
	{
		this.setFormTitle(Methods.getLanguageText(XMLIdentifier.TRIGGER_TEXT));
		this.combo.setModel(new DefaultComboBoxModel<String>(Constants.DEFAULT_ACTIVITIES));
		this.revalidate();
		this.repaint();
	}

}
