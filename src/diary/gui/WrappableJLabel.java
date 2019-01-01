package diary.gui;

import java.awt.ComponentOrientation;

import javax.swing.JTextArea;

import giantsweetroll.gui.swing.TextAreaManager;

public class WrappableJLabel extends JTextArea
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1969264727102841691L;
	
	public WrappableJLabel(String text)
	{
		super(1, 1);
		this.init(text);
	}
	public WrappableJLabel(String text, ComponentOrientation orientation)
	{
		super(1, 1);
		this.setComponentOrientation(orientation);
		this.init(text);
	}
	
	//Methods
	private void init(String text)
	{
		TextAreaManager.autoConfigureTextArea(this, false);
		this.setBorder(null);
		this.setOpaque(false);
		this.setText(text);
	}
}