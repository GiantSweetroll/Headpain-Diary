package diary.methods;

import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

import diary.constants.DateConstants;
import giantsweetroll.GDateManager;

public class Methods 
{
	public static String getTextData(JTextField tf)
	{
		return tf.getText().trim();
	}
	public static String getTextData(JFormattedTextField tf)
	{
		return tf.getText().trim().replace(",", "");
	}
}
