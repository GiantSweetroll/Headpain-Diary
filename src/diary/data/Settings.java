package diary.data;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

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
	public static final String DATABASE_USERS_PATH = "database_users_path";
	public static final String WINDOW_MODE = "window_mode";
	public static final String FULLSCREEN = "fullscreen";
	public static final String WINDOWED = "windowed";
	public static final String LANGUAGE = "language";
	
	//Constructors
	public Settings()
	{
		this.map = new LinkedHashMap<String, String>();
		
		this.generateDefaultData(); 
	}
	public Settings(LinkedHashMap<String, String> map)
	{
		/*
		this.map = new LinkedHashMap<String, String>();
		
		this.map.put(Settings.DATABASE_PATH, map.get(Settings.DATABASE_PATH));
		this.map.put(Settings.DATABASE_USERS_PATH, map.get(Settings.DATABASE_USERS_PATH));
		this.map.put(Settings.WINDOW_MODE, map.get(Settings.WINDOW_MODE));
		this.map.put(Settings.LANGUAGE, map.get(Settings.LANGUAGE));
		
		*/
		
		this.map = map;
	}
	public Settings(Document doc)
	{
		this.map = new LinkedHashMap<String, String>();
		
		Element rootElement = XMLManager.getElement(doc.getElementsByTagName(Settings.ROOT_NODE_NAME), 0);
		
		try
		{
			this.map.put(Settings.DATABASE_PATH, XMLManager.getElement(rootElement.getElementsByTagName(Settings.DATABASE_PATH), 0).getTextContent());
		}
		catch (NullPointerException ex)
		{
			File file = new File(Constants.DATABASE_DEFAULT_PATH);
			this.map.put(Settings.DATABASE_PATH, file.getAbsolutePath());
		}
		try
		{
			this.map.put(Settings.DATABASE_USERS_PATH, XMLManager.getElement(rootElement.getElementsByTagName(Settings.DATABASE_USERS_PATH), 0).getTextContent());
		}
		catch (NullPointerException ex)
		{
			File file = new File(Constants.PATIENTS_LIST_DEFAULT_PATH);
			this.map.put(Settings.DATABASE_USERS_PATH, file.getAbsolutePath());
		}
		try
		{
			this.map.put(Settings.WINDOW_MODE, XMLManager.getElement(rootElement.getElementsByTagName(Settings.WINDOW_MODE), 0).getTextContent());
		}
		catch (NullPointerException ex)
		{
			this.map.put(Settings.WINDOW_MODE, Settings.WINDOWED);
		}
		try
		{
			this.map.put(Settings.LANGUAGE, XMLManager.getElement(rootElement.getElementsByTagName(Settings.LANGUAGE), 0).getTextContent());
		}
		catch (NullPointerException ex)
		{
			this.map.put(Settings.LANGUAGE, Constants.DEFAULT_LANGUAGE);
		}
	}
	
	//Methods
	//Private methods
	private void generateDefaultData()
	{
		 this.map.put(Settings.WINDOW_MODE, Settings.FULLSCREEN);
		 File file = new File(Constants.DATABASE_DEFAULT_PATH);
		 this.map.put(Settings.DATABASE_PATH, file.getAbsolutePath());
		 file = new File(Constants.PATIENTS_LIST_DEFAULT_PATH);
		 this.map.put(Settings.DATABASE_USERS_PATH, file.getAbsolutePath());
		 this.map.put(Settings.LANGUAGE, Constants.DEFAULT_LANGUAGE);
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
		
		/*
		//Initialize individual nodes
		Element databasePathElement = doc.createElement(Settings.DATABASE_PATH);
		databasePathElement.setTextContent(this.map.get(Settings.DATABASE_PATH));
		
		Element windowModeElement = doc.createElement(Settings.WINDOW_MODE);
		windowModeElement.setTextContent(this.map.get(Settings.WINDOW_MODE));
		
		Element languageElement = doc.createElement(Settings.LANGUAGE);
		languageElement.setTextContent(this.map.get(Settings.LANGUAGE));
		
		//append to rootElement
		rootElement.appendChild(databasePathElement);
		rootElement.appendChild(windowModeElement);
		rootElement.appendChild(languageElement);
		*/
		
		for (Map.Entry<String, String> entry : this.map.entrySet())
		{
			Element element = doc.createElement(entry.getKey());
			element.setTextContent(entry.getValue());
			rootElement.appendChild(element);
		}
		
		//append to document
		doc.appendChild(rootElement);
		
		return doc;
	}
	public String getUserDatabasePath()
	{
		return this.getDataMap().get(Settings.DATABASE_USERS_PATH);
	}
	public String getEntriesDatabasePath()
	{
		return this.getDataMap().get(Settings.DATABASE_PATH);
	}
	public String getUserDatabaseFolderName()
	{
		File file = new File(this.getUserDatabasePath());
		return file.getName();
	}
	public String getEntriesDatawbaseFolderName()
	{
		File file = new File(this.getEntriesDatabasePath());
		return file.getName();
	}
}
