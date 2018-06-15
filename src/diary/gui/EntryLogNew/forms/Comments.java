package diary.gui.EntryLogNew.forms;

import javax.swing.JTextArea;

import diary.constants.XMLIdentifier;
import diary.methods.Methods;
import giantsweetroll.gui.swing.TextAreaManager;

public class Comments extends FormElement
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7505841736348815756L;

	private JTextArea ta;
	
	public Comments()
	{
		super(Methods.getLanguageText(XMLIdentifier.COMMENTS_LABEL));
		
		//Initialization
		this.ta = new JTextArea(15, 20);
		
		//Properties
		TextAreaManager.autoConfigureTextArea(ta, true);
		
		//Add to panel
		this.addComponent(this.ta);
	}
	
	//Overridden Methods
	@Override
	public void resetDefaults() {this.ta.setText("");}

	@Override
	public void refresh() {};
	
	@Override
	public Object getData() {return this.ta.getText().trim();}

	@Override
	public void setData(Object obj) {this.ta.setText(obj.toString());}
}
