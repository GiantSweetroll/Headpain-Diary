package diary;

import java.io.File;
import java.util.LinkedHashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import diary.constants.Constants;
import giantsweetroll.xml.dom.XMLManager;

public class Settings 
{
	private LinkedHashMap<String, String> map;
	
	//Constants
	public static final String ROOT_NODE_NAME = "settings";
	public static final String DATABASE_PATH = "database_path";
	public static final String WINDOW_MODE = "window_mode";
	public static final String FULLSCREEN = "fullscreen";
	public static final String WINDOWED = "windowed";
	
	//Constructors
	public Settings()
	{
		this.map = new LinkedHashMap<String, String>();
		
		this.generateDefaultData(); 
	}
	public Settings(LinkedHashMap<String, String> map)
	{
		this.map = new LinkedHashMap<String, String>();
		
		this.map.put(Settings.DATABASE_PATH, map.get(Settings.DATABASE_PATH));
		this.map.put(Settings.WINDOW_MODE, map.get(Settings.WINDOW_MODE));
	}
	public Settings(Document doc)
	{
		this.map = new LinkedHashMap<String, String>();
		Element rootElement = XMLManager.getElement(doc.getElementsByTagName(Settings.ROOT_NODE_NAME), 0);
		
		this.map.put(Settings.DATABASE_PATH, XMLManager.getElement(rootElement.getElementsByTagName(Settings.DATABASE_PATH), 0).getTextContent());
		this.map.put(Settings.WINDOW_MODE, XMLManager.getElement(rootElement.getElementsByTagName(Settings.WINDOW_MODE), 0).getTextContent());
	}
	
	//Methods
	//Private methods
	private void generateDefaultData()
	{
		 this.map.put(Settings.WINDOW_MODE, Settings.WINDOWED);
		 File file = new File(Constants.DATABASE_DEFAULT_PATH);
		 this.map.put(Settings.DATABASE_PATH, file.getAbsolutePath());
	}
	
	//Public methods
	public LinkedHashMap<String, String> getDataMap()
	{
		return this.map; 
	}
	
	public Document getXMLDocument() throws ParserConfigurationException
	{
		Document doc = XMLManager.createDocument();
		Element rootElement = doc.createElement(Settings.ROOT_NODE_NAME);
		
		//Initialize individual nodes
		Element databasePathElement = doc.createElement(Settings.DATABASE_PATH);
		databasePathElement.setTextContent(this.map.get(Settings.DATABASE_PATH));
		
		Element windowModeElement = doc.createElement(Settings.WINDOW_MODE);
		windowModeElement.setTextContent(this.map.get(Settings.WINDOW_MODE));
		
		//append to rootElement
		rootElement.appendChild(databasePathElement);
		rootElement.appendChild(windowModeElement);
		
		//append to document
		doc.appendChild(rootElement);
		
		return doc;
	}
}
