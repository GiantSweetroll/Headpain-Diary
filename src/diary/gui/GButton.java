package diary.gui;

import java.awt.Color;

import javax.swing.JButton;

import diary.constants.Constants;

public class GButton extends JButton
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8764716024009754150L;

	public GButton()
	{
		super();
		this.init();
	}
	public GButton(String text)
	{
		super(text);
		this.init();
	}
	
	//Methods
	private void init()
	{
//		this.setContentAreaFilled(false);
//		this.setBorder(new RoundedBorder(5, Constants.COLOR_MAIN_MENU_BUTTONS));
		this.setForeground(Color.WHITE);
		this.setBackground(Constants.COLOR_MAIN_MENU_BUTTONS);
		this.setFont(Constants.FONT_GENERAL);
	}
}
