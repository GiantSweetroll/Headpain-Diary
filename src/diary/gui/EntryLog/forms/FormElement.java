package diary.gui.EntryLog.forms;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import diary.constants.Constants;
import diary.interfaces.GUIFunction;
import diary.interfaces.LanguageListener;
import diary.methods.Methods;

public abstract class FormElement extends JScrollPane implements GUIFunction, LanguageListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -94968805497961745L;
	
	private JLabel labName;
	private JPanel panel;
	private boolean required;
	
	//Constructors
	public FormElement(String name, boolean required)
	{
		this.required = required;
		this.init(name);
	}
	
	//Initialize GUI
	private void init(String name)
	{
		//Initialization
		this.panel = new JPanel();
		this.labName = new JLabel();
		
		//Properties
		this.panel.setOpaque(false);
		this.setViewportView(this.panel);
		this.getVerticalScrollBar().setUnitIncrement(10);
		this.getHorizontalScrollBar().setUnitIncrement(10);
		this.getViewport().setOpaque(false);
		this.setOpaque(false);
		this.setFormTitle(name);
		this.labName.setFont(Constants.FONT_HEADER);
	}

	//Methods
	public JLabel getFormTitleLabel()
	{
		return this.labName;
	}
	public void setFormTitle(String name)
	{
		if(this.isRequired())
		{
			this.labName.setText(Methods.createTextWithRequiredIdentifier(name));
		}
		else
		{
			this.labName.setText(name);
		}
	}
	public void setRequired(boolean required)
	{
		this.required = required;
	}
	public JPanel getPanel()
	{
		return this.panel;
	}
	
	public boolean isRequired()
	{
		return this.required;
	}
	
	//Abstract Methods
	public abstract Object getData();
	public abstract void setData(Object obj);
	public abstract boolean allFilled();
}
