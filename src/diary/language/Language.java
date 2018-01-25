package diary.language;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import diary.constants.Constants;
import diary.constants.XMLIdentifier;
import diary.methods.XMLGenerator;
import giantsweetroll.message.MessageManager;
import giantsweetroll.xml.dom.XMLManager;

public class Language 
{	
	private LinkedHashMap<String, String> map;
	
	public Language()
	{
		this.setLanguage("english");
	}
	
	public Language(String language)
	{
		this.setLanguage(language);
	}
	
	//Methods
	public void setLanguage(String language)
	{
		try 
		{
			//Initialize Document
			Document xmlDoc;
			try
			{
				xmlDoc = XMLManager.createDocument(Constants.LANGUAGE_FOLDER_PATH + language + ".xml", false);
			}
			catch(FileNotFoundException ex)
			{
				XMLGenerator.generateXML();
				xmlDoc = XMLManager.createDocument(Constants.LANGUAGE_FOLDER_PATH + language + ".xml", false);
			}
			NodeList rootNodes = xmlDoc.getElementsByTagName(XMLIdentifier.NODE_NAME_LANGUAGE);
			Element rootElement = (Element)XMLManager.getElement(rootNodes, 0);
			
			//Getting the texts
			this.map = this.createTextMap(rootElement);
			
		}
		catch (ParserConfigurationException | SAXException | IOException e) 
		{
			e.printStackTrace();
			MessageManager.showErrorDialog(e);
		}
	}
	
	private LinkedHashMap<String, String> createTextMap(Element rootElement)
	{
		ArrayList<Element> textList = XMLManager.getElements(rootElement.getElementsByTagName(XMLIdentifier.NODE_NAME_TEXT));
		
		LinkedHashMap<String, String> textMap = new LinkedHashMap<String, String>();
		for (int i=0; i<textList.size(); i++)
		{
			textMap.put(textList.get(i).getAttribute(XMLIdentifier.ATTRIBUTE_IDENTIFIER), 
					textList.get(i).getTextContent());
		}
		
		return textMap;
	}
	
	public LinkedHashMap<String, String> getTextMap()
	{
		return this.map;
	}
}
