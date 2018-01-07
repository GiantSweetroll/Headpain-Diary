package diary.gui.settings;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import diary.constants.Constants;
import diary.constants.XMLIdentifier;
import giantsweetroll.gui.swing.Gbm;
import giantsweetroll.xml.dom.XMLManager;

public class WindowSettingsPanel extends SettingsCategoryPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -646026543846009237L;
	
	private JLabel labWindowMode;
	private JRadioButton radFullscreen, radWindowed;
	private ButtonGroup groupWindowMode;
	
	//Constants
	private final String WINDOW_MODE_NODE = "window_mode";
	private final String FULLSCREEN = "fullscreen";
	private final String WINDOWED = "windowed";

	protected WindowSettingsPanel()
	{
		super(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SETTINGS_WINDOW_TITLE), "window");
		if(!this.dataExists())
		{
			try 
			{
				XMLManager.exportXML(this.generateDataDocument(), new File(this.SETTING_DATA_FILE_PATH), 5);
			}
			catch (TransformerException | ParserConfigurationException e) 
			{
				e.printStackTrace();
			}
		}
		
		//Initialization
		this.labWindowMode = new JLabel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SETTINGS_WINDOW_MODE_TEXT));
		this.radFullscreen = new JRadioButton(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SETTINGS_WINDOW_MODE_FULLSCREEN_TEXT));
		this.radWindowed = new JRadioButton(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SETTINGS_WINDOW_MODE_WINDOWED_TEXT));
		this.groupWindowMode = new ButtonGroup();
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		this.groupWindowMode.add(this.radFullscreen);
		this.groupWindowMode.add(this.radWindowed);
		this.radWindowed.setSelected(true);
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_TOP_COMPONENT;
		c.fill = GridBagConstraints.BOTH;
		this.add(this.labWindowMode, c);			//Window Mode
		Gbm.nextGridColumn(c);
		this.add(this.radFullscreen, c);			//Fullscreen Radio Button
		Gbm.nextGridColumn(c);
		this.add(this.radWindowed, c);				//Windowed Radio Button
		
		
		this.loadData();
	}

	//Overriden Methods
	@Override
	protected void loadData() 
	{
		try 
		{
			LinkedHashMap<String, Object> dataMap = this.loadDataMap(XMLManager.createDocument(this.SETTING_DATA_FILE_PATH, false));
			
			String windowMode = dataMap.get(this.WINDOW_MODE_NODE).toString();
			if (windowMode.equals(this.FULLSCREEN))
			{
				this.radFullscreen.setSelected(true);
			}
			else
			{
				this.radWindowed.setSelected(true);
			}
			
		} 
		catch (ParserConfigurationException | SAXException | IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	protected void saveData() 
	{
		try
		{
			Document doc = this.generateDataDocument();
			Element rootElement = XMLManager.getElement(doc.getElementsByTagName(this.ROOT_NODE), 0);
			
			//data change
			Element windowModeElement = XMLManager.getElement(rootElement.getElementsByTagName(this.WINDOW_MODE_NODE), 0);
			if (this.radFullscreen.isSelected())
			{
				windowModeElement.setTextContent(this.FULLSCREEN);
			}
			else
			{
				windowModeElement.setTextContent(this.WINDOWED);
			}
			
			//Export
			XMLManager.exportXML(doc, new File(this.SETTING_DATA_FILE_PATH), 5);
		} 
		catch (ParserConfigurationException | TransformerException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	protected LinkedHashMap<String, Object> loadDataMap(Document doc) 
	{
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		
		Element rootElement = XMLManager.getElement(doc.getElementsByTagName(this.ROOT_NODE), 0);
		
		Element databasePathElement = XMLManager.getElement(rootElement.getElementsByTagName(this.WINDOW_MODE_NODE), 0);
		
		map.put(databasePathElement.getNodeName(), databasePathElement.getTextContent());
		
		return map;
	}

	@Override
	protected Document generateDataDocument() throws ParserConfigurationException 
	{
		Document doc = XMLManager.createDocument();
		Element rootElement = doc.createElement(super.ROOT_NODE);
		
		Element databasePathElement = doc.createElement(this.WINDOW_MODE_NODE);
		databasePathElement.setTextContent(this.WINDOWED);
		
		rootElement.appendChild(databasePathElement);
		doc.appendChild(rootElement);
		
		return doc;
	}
}
