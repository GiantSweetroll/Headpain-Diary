package diary.gui.settings;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import diary.constants.Constants;
import diary.constants.XMLIdentifier;
import giantsweetroll.gui.swing.Gbm;
import giantsweetroll.xml.dom.XMLManager;

public class DatabaseSettingsPanel extends SettingsCategoryPanel implements ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4610748538970196462L;
	
	private JLabel labDatabasePath;
	private JTextField tfDatabasePath;
	private JButton butBrowseDatabasePath;
	
	//Constants
	private final String BROWSE_DATABASE_PATH = "broswe database path";
	private final String DATABASE_PATH_NODE = "database_path";
	
	public DatabaseSettingsPanel()
	{
		super(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SETTINGS_DATABASE_TITLE), "database");
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
		this.labDatabasePath = new JLabel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SETTINGS_DATABASE_PATH_TEXT));
		this.tfDatabasePath = new JTextField("", 30);
		this.butBrowseDatabasePath = new JButton(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.BROWSE_TEXT));
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		this.butBrowseDatabasePath.addActionListener(this);
		this.butBrowseDatabasePath.setActionCommand(this.BROWSE_DATABASE_PATH);
//		this.tfDatabasePath.setEditable(false);
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_TOP_COMPONENT;
		c.fill = GridBagConstraints.BOTH;
		this.add(this.labDatabasePath, c);				//Database Path
		Gbm.nextGridColumn(c);
		this.add(this.tfDatabasePath, c);				//Database Path Text Field
		Gbm.nextGridColumn(c);
		this.add(this.butBrowseDatabasePath, c);		//Browse database Path button
		
		this.loadData();
	}
	
	//Interfaces
	public void actionPerformed(ActionEvent e)
	{
		switch(e.getActionCommand())
		{
			case BROWSE_DATABASE_PATH:
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				int returnValue = jfc.showDialog(null, Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SELECT_TEXT));
				
				if (returnValue == JFileChooser.APPROVE_OPTION)
				{
					File selectedFile = jfc.getSelectedFile();
					this.tfDatabasePath.setText(selectedFile.getAbsolutePath());
				}
				break;
		}
	}

	//Overriden Methods
	@Override
	protected void loadData() 
	{
		try 
		{
			LinkedHashMap<String, Object> dataMap = this.loadDataMap(XMLManager.createDocument(this.SETTING_DATA_FILE_PATH, false));
			
			this.tfDatabasePath.setText(dataMap.get(this.DATABASE_PATH_NODE).toString());
			
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
			Element databasePathElement = XMLManager.getElement(rootElement.getElementsByTagName(this.DATABASE_PATH_NODE), 0);
			databasePathElement.setTextContent(this.tfDatabasePath.getText().trim());
			
			//Export
			XMLManager.exportXML(doc, new File(this.SETTING_DATA_FILE_PATH), 5);
		} 
		catch (ParserConfigurationException | TransformerException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	protected Document generateDataDocument() throws ParserConfigurationException 
	{
		Document doc = XMLManager.createDocument();
		Element rootElement = doc.createElement(super.ROOT_NODE);
		
		Element databasePathElement = doc.createElement(this.DATABASE_PATH_NODE);
		File file = new File(Constants.DATABASE_DEFAULT_PATH);
		databasePathElement.setTextContent(file.getAbsolutePath());
		
		rootElement.appendChild(databasePathElement);
		doc.appendChild(rootElement);
		
		return doc;
	}

	@Override
	protected LinkedHashMap<String, Object> loadDataMap(Document doc) 
	{
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		
		Element rootElement = XMLManager.getElement(doc.getElementsByTagName(this.ROOT_NODE), 0);
		
		Element databasePathElement = XMLManager.getElement(rootElement.getElementsByTagName(this.DATABASE_PATH_NODE), 0);
		
		map.put(databasePathElement.getNodeName(), databasePathElement.getTextContent());
		
		return map;
	}
}
