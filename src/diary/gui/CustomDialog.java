package diary.gui;

import javax.swing.JOptionPane;

import diary.constants.Constants;

public class CustomDialog 
{
	public static int showConfirmDialog(String title, String message)
	{
		return JOptionPane.showOptionDialog(null, 
											message, 
											title, 
											JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE,
											null, 
											Constants.OPTION_PANE_YES_NO_CANCEL_BUTTON_TEXTS, 
											Constants.OPTION_PANE_YES_NO_CANCEL_BUTTON_TEXTS[2]);
	}
}
