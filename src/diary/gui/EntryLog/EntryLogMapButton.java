package diary.gui.EntryLog;

import java.awt.Color;

import diary.constants.Constants;
import diary.gui.GButton;
import diary.interfaces.GUIFunction;

public class EntryLogMapButton extends GButton implements GUIFunction
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -873903782280068320L;
	
	private Color defaultForeground, defaultBackground;
	
	public EntryLogMapButton(String text)
	{
		super(text);
		
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

	@Override
	public void resetDefaults()
	{
		this.setForeground(this.defaultForeground);
		this.setBackground(this.defaultBackground);
	}

	@Override
	public void refresh() {}
}
