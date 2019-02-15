package diary.gui;

import javax.swing.JOptionPane;

import diary.methods.Methods;

public class CustomDialog 
{
	public static int showConfirmDialog(String title, String message)
	{
		return JOptionPane.showOptionDialog(null, 
											message, 
											title, 
											JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE,
											null, 
											Methods.getOptionPaneYesNoCancelButtonTexts(), 
											Methods.getOptionPaneYesNoCancelButtonTexts()[2]);
	}
	public static int showConfirmDialog(String title, Object message)
	{
		return JOptionPane.showOptionDialog(null, 
											message, 
											title, 
											JOptionPane.YES_NO_CANCEL_OPTION, 
											JOptionPane.PLAIN_MESSAGE,
											null, 
											Methods.getOptionPaneYesNoCancelButtonTexts(), 
											Methods.getOptionPaneYesNoCancelButtonTexts()[2]);
	}
}
