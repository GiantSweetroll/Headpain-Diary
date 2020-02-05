package diary.gui.EntryLog;

import java.awt.Color;

import diary.constants.Constants;
import diary.gui.GButton;
import diary.interfaces.GUIFunction;
import diary.methods.Methods;

public class EntryLogMapButton extends GButton implements GUIFunction
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -873903782280068320L;
	
	private Color defaultForeground, defaultBackground;
	private boolean required;
	
	public EntryLogMapButton(String text, boolean required)
	{
		super(required? Methods.createTextWithRequiredIdentifier(text): text);

		//Initialization
		this.required = required;
		
		//Properties
	//	this.setOpaque(false);
	//	this.setBackground(Constants.COLOR_TRANSPARENT);
	//	this.setBorder(null);
	//	this.setFont(Constants.FONT_GENERAL);
		this.defaultBackground = this.getBackground();
		this.defaultForeground = this.getForeground();
		this.resetDefaults();
	}
	
	//Public methods
	public void setToHighlightedColor(boolean b)
	{
		if (b)
		{
			this.setForeground(Color.white);
			this.setBackground(Constants.COLOR_HIGHLIGHT_BUTTON);
		}
		else
		{
			this.resetDefaults();
		}
	}
	
	public boolean isRequired()
	{
		return this.required;
	}
	public void setRequired(boolean b)
	{
		this.required = b;
	}
	
	//Overridden Methods
	@Override
	public void setText(String text)
	{
		super.setText(this.isRequired()? Methods.createTextWithRequiredIdentifier(text) : text);
	}
	
	@Override
	public void resetDefaults()
	{
		this.setForeground(this.defaultForeground);
		this.setBackground(this.defaultBackground);
	}

	@Override
	public void refresh() {}
}
