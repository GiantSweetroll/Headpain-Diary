package diary.methods;

import java.io.File;
import java.util.List;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JTextField;

import diary.PainEntryData;
import diary.Settings;
import diary.constants.Constants;
import diary.constants.PainDataIdentifier;
import diary.constants.PainLocationConstants;
import diary.constants.XMLIdentifier;
import diary.gui.MainFrame;

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
	
	public static boolean isEmpty(JTextField tf)
	{
		if (Methods.getTextData(tf).equals(""))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	public static String generatePainDataFolderPathName(PainEntryData entry)
	{
		return MainFrame.setting.getDataMap().get(Settings.DATABASE_PATH) + File.separator +
				entry.getDataMap().get(PainDataIdentifier.DATE_YEAR) + File.separator +
				entry.getDataMap().get(PainDataIdentifier.DATE_MONTH) + File.separator +
				entry.getDataMap().get(PainDataIdentifier.DATE_DAY);
	}
	public static String generatePainDataFilePathName(PainEntryData entry)
	{
		return 	generatePainDataFolderPathName(entry) + File.separator +
				entry.getDataMap().get(PainDataIdentifier.TIME_HOUR) + "-" +
				entry.getDataMap().get(PainDataIdentifier.TIME_MINUTE) + "-" +
				entry.getDataMap().get(PainDataIdentifier.TIME_SECONDS) +
				".xml";
	}
	/*
	public static String generatePainDataFolderPathName(Document doc)
	{
		Element rootElement = XMLManager.getElement(doc.getElementsByTagName(PainDataIdentifier.MASTER_NODE), 0);
		
		return MainFrame.setting.getDataMap().get(Settings.DATABASE_PATH) + File.separator +
				XMLManager.getElement(rootElement.getElementsByTagName(PainDataIdentifier.DATE_YEAR), 0).getTextContent() + File.separator +
				XMLManager.getElement(rootElement.getElementsByTagName(PainDataIdentifier.DATE_MONTH), 0).getTextContent() + File.separator +
				XMLManager.getElement(rootElement.getElementsByTagName(PainDataIdentifier.DATE_DAY), 0).getTextContent();
	}
	
	public static String generatePainDataFilePathName(Document doc)
	{
		Element rootElement = XMLManager.getElement(doc.getElementsByTagName(PainDataIdentifier.MASTER_NODE), 0);
		
		return 	Methods.generatePainDataFolderPathName(doc) + File.separator +
				XMLManager.getElement(rootElement.getElementsByTagName(PainDataIdentifier.TIME_HOUR), 0).getTextContent() + "-" +
				XMLManager.getElement(rootElement.getElementsByTagName(PainDataIdentifier.TIME_MINUTE), 0).getTextContent() + "-" +
				XMLManager.getElement(rootElement.getElementsByTagName(PainDataIdentifier.TIME_SECONDS), 0).getTextContent() +
				".xml";
	}
	*/
	
	public static float getHighestValue(List<Float> list)
	{
		float max = 0;
		for (int i=0; i<list.size(); i++)
		{
			if (list.get(i)>max)
			{
				max = list.get(i);
			}
		}
		
		return max;
	}
	
	public static float round(float value, int precision)
	{
	    int scale = (int) Math.pow(10, precision);
	    return (float) Math.round(value * scale) / scale;
	}
	
	public static String convertToLanguageText(String painLocationID)	//Method used to convert Pain Location ID to the language's text
	{
		//General
		if (painLocationID.equals(PainLocationConstants.HEAD))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.GENERAL_POSITION_HEAD_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EARS))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.GENERAL_POSITION_EARS_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EYES))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.GENERAL_POSITION_EYES_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.CHEEKS))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.GENERAL_POSITION_CHEEKS_TEXT);
		}
		//General 2
		else if (painLocationID.equals(PainLocationConstants.HEAD_BACK_GENERAL))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SPECIFIC_POSITION_HEAD_BACK_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.HEAD_FRONT_GENERAL))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SPECIFIC_POSITION_HEAD_FRONT_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.HEAD_SIDE_LEFT_GENERAL))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SPECIFIC_POSITION_HEAD_LEFT_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.HEAD_SIDE_RIGHT_GENERAL))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SPECIFIC_POSITION_HEAD_RIGHT_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EARS_LEFT_GENERAL))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SPECIFIC_POSITION_EARS_LEFT_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EARS_RIGHT_GENERAL))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SPECIFIC_POSITION_EARS_RIGHT_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EARS_LEFT_GENERAL))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SPECIFIC_POSITION_EYES_LEFT_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EARS_RIGHT_GENERAL))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SPECIFIC_POSITION_EYES_RIGHT_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.CHEEKS_LEFT_GENERAL))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SPECIFIC_POSITION_CHEEKS_LEFT_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.CHEEKS_RIGHT_GENERAL))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SPECIFIC_POSITION_CHEEKS_RIGHT_TEXT);
		}
		//Specific
		else if (painLocationID.equals(PainLocationConstants.HEAD_BACK_BOTTOM_LEFT))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_BACK_BOTTOM_LEFT_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.HEAD_BACK_BOTTOM_RIGHT))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_BACK_BOTTOM_RIGHT_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.HEAD_BACK_CENTER))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_BACK_CENTER_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.HEAD_BACK_TOP_LEFT))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_BACK_TOP_LEFT_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.HEAD_BACK_TOP_RIGHT))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_BACK_TOP_RIGHT_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.HEAD_FRONT_CENTER))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_FRONT_CENTER_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.HEAD_FRONT_LEFT))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_FRONT_LEFT_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.HEAD_FRONT_RIGHT))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_FRONT_RIGHT_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.HEAD_SIDE_LEFT_BACK))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_SIDE_BACK_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.HEAD_SIDE_LEFT_CENTER))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_SIDE_CENTER_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.HEAD_SIDE_LEFT_FRONT))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_SIDE_FRONT_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.HEAD_SIDE_RIGHT_BACK))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_SIDE_BACK_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.HEAD_SIDE_RIGHT_CENTER))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_SIDE_CENTER_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.HEAD_SIDE_RIGHT_FRONT))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_SIDE_FRONT_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EARS_LEFT_HOLE))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EARS_HOLE_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EARS_LEFT_LEAF_BOTTOM))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EARS_LOWER_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EARS_LEFT_LEAF_TOP))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EARS_UPPER_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EARS_RIGHT_HOLE))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EARS_HOLE_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EARS_RIGHT_LEAF_BOTTOM))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EARS_LOWER_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EARS_RIGHT_LEAF_TOP))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EARS_UPPER_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EYES_LEFT_EYEBALL))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EYES_EYEBALL_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EYES_LEFT_BEHIND))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EYES_BEHIND_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EYES_LEFT_EYEBROW))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EYES_EYEBROW_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EYES_LEFT_SOCKET))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EYES_SOCKET_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EYES_RIGHT_EYEBALL))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EYES_EYEBALL_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EYES_RIGHT_BEHIND))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EYES_BEHIND_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EYES_RIGHT_EYEBROW))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EYES_EYEBROW_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EYES_RIGHT_SOCKET))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EYES_SOCKET_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.CHEEKS_LEFT_LOWER))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_CHEEKS_LOWER_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.CHEEKS_LEFT_UPPER))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_CHEEKS_UPPER_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.CHEEKS_RIGHT_LOWER))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_CHEEKS_LOWER_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.CHEEKS_RIGHT_UPPER))
		{
			return Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_CHEEKS_UPPER_TEXT);
		}
		
		return "";
	}
	
	public static void makeFullscreen(JFrame frame)
	{
		frame.dispose();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	public static void makeWindowed(JFrame frame)
	{
		frame.dispose();
		frame.setExtendedState(JFrame.NORMAL);
		frame.setUndecorated(false);
		frame.setSize((Constants.SCREENSIZE.width/4)*3, (Constants.SCREENSIZE.height/4)*3);
	//	frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
