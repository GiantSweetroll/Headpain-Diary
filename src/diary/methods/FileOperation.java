package diary.methods;

import java.io.File;

import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;

import diary.constants.Constants;
import giantsweetroll.message.MessageManager;
import giantsweetroll.xml.dom.XMLManager;

public class FileOperation
{
	public static final void exportPainData(Document doc)
	{
		File file = new File(Methods.generatePainDataFolderPathName(doc));
		if (!file.exists())				//Check if the folder directory exists, if not make it
		{
			file.mkdirs();
		}
		
		file = new File(Methods.generatePainDataFilePathName(doc));
		try 
		{
			XMLManager.exportXML(doc, file, 5);
		} 
		catch (TransformerException e) 
		{
			e.printStackTrace();
			MessageManager.showErrorDialog(e);
		}
	}
	
	public static final boolean defaultLanguageExists()
	{
		File file = new File(Constants.LANGUAGE_FOLDER_PATH);
		
		if (file.exists())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
