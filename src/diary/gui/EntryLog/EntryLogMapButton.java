package diary.gui.EntryLog;

import java.awt.Color;

import javax.swing.JButton;

import diary.constants.Constants;
import diary.interfaces.GUIFunction;

public class EntryLogMapButton extends JButton implements GUIFunction
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -873903782280068320L;
	
	public EntryLogMapButton(String text)
	{
		super(text);
		
		//Properties
	//	this.setOpaque(false);
	//	this.setBackground(Constants.COLOR_TRANSPARENT);
	//	this.setBorder(null);
		this.setFont(Constants.FONT_GENERAL);
		this.resetDefaults();
	}
	
	//Public methods
	public void setToHighlightedColor(boolean b)
	{
		if (b)
		{
			this.setForeground(Color.white);
			this.setBackground(new Color(28, 203, 15));
		}
		else
		{
			this.resetDefaults();
		}
	}

	@Override
	public void resetDefaults()
	{
		this.setForeground(Color.white);
		this.setBackground(Constants.COLOR_MAIN_MENU_BUTTONS);
	}

	@Override
	public void refresh() {}
}
