package diary.gui.EntryLog.forms;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JTextArea;

import diary.constants.Constants;
import diary.constants.XMLIdentifier;
import diary.methods.Methods;
import giantsweetroll.gui.swing.TextAreaManager;

public class Comments extends FormElement<String>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7505841736348815756L;

	private JTextArea ta;
	
	public Comments()
	{
		super(Methods.getLanguageText(XMLIdentifier.COMMENTS_LABEL), false);
		
		//Initialization
		this.ta = new JTextArea(15, 30);
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		this.getPanel().setLayout(new GridBagLayout());
		TextAreaManager.autoConfigureTextArea(ta, true);
		
		//Add to panel
		c.insets = Constants.INSETS_TOP_COMPONENT;
		c.gridwidth = GridBagConstraints.REMAINDER;
		this.getPanel().add(this.getFormTitleLabel(), c);
		this.getPanel().add(this.ta, c);
	}
	
	//Overridden Methods
	@Override
	public void resetDefaults() 
	{
		super.resetDefaults();
		this.ta.setText("");
	}

	@Override
	public void refresh() {};
	
	@Override
	public String getData() {return this.ta.getText().trim();}

	@Override
	public void setData(String obj) {this.ta.setText(obj);}

	@Override
	public boolean allFilled() {
		return true;
	}

	@Override
	public void revalidateLanguage() 
	{
		this.setFormTitle(Methods.getLanguageText(XMLIdentifier.COMMENTS_LABEL));
	}
}
