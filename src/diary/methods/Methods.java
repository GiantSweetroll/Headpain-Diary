package diary.methods;

import java.io.File;
import java.util.List;

import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import diary.constants.PainDataIdentifier;
import giantsweetroll.xml.dom.XMLManager;

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
	
	public static String generatePainDataFolderPathName(Document doc)
	{
		Element rootElement = XMLManager.getElement(doc.getElementsByTagName(PainDataIdentifier.MASTER_NODE), 0);
		
		return "." + File.separator + "data" + File.separator + "database" + File.separator +
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
	
	public static int getHighestValue(List<Integer> list)
	{
		int max = 0;
		for (int i=0; i<list.size(); i++)
		{
			if (list.get(i)>max)
			{
				max = list.get(i);
			}
		}
		
		return max;
	}
}
