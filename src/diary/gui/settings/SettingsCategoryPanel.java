package diary.gui.settings;

import java.util.LinkedHashMap;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

import diary.constants.Constants;
import diary.methods.FileOperation;

public abstract class SettingsCategoryPanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4624584191728657218L;
	
	protected String CATEGORY_KEY;
	protected String SETTING_DATA_FILE_PATH;
	
	//Constants
	protected final String ROOT_NODE = "setting";
	
	public SettingsCategoryPanel(String borderText, String key)
	{
		this.setBorder(BorderFactory.createTitledBorder(borderText));
		this.CATEGORY_KEY = key;
		this.SETTING_DATA_FILE_PATH = Constants.SETTINGS_FOLDER_PATH + key + ".xml";
	}
	
	//Non-abstract methods
	protected boolean dataExists()
	{
		return FileOperation.dataExists(Constants.SETTINGS_FOLDER_PATH + this.CATEGORY_KEY + ".xml");
	}
	//Abstract methods
	protected abstract LinkedHashMap<String, Object> loadDataMap(Document doc);
	protected abstract void loadData();
	protected abstract void saveData();
	protected abstract Document generateDataDocument()  throws ParserConfigurationException;
}
