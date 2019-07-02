	package diary.gui.settings;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LookAndFeel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import diary.constants.Constants;
import diary.constants.Globals;
import diary.constants.PanelName;
import diary.constants.XMLIdentifier;
import diary.data.Settings;
import diary.interfaces.LanguageListener;
import diary.methods.FileOperation;
import diary.methods.Methods;
import giantsweetroll.gui.swing.Gbm;
import giantsweetroll.gui.swing.ScrollPaneManager;

public class SettingsPanel extends JPanel implements ActionListener, LanguageListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8823949814661108143L;	
	
	private JPanel panelCenter, 
					panelBelow;
	private SettingsCategoryPanel catDatabase, 
									catWindow, 
									catProgram;
	private JButton butCancel, 
					butSave;
	private LinkedHashMap<String, String> dataMap;
	private JScrollPane scroll;
	
	//Window Category
	private JLabel labWindowMode;
	private JRadioButton radFullscreen, 
						radWindowed;
	//Program Category
	private JLabel labLanguage;
	private JComboBox<String> comboLanguages;
	private JButton butLanguageRefresh;
	//Database Category
	private JLabel labDatabasePath, 
					labUserDatabasePath;
	private JTextField tfDatabasePath, 
						tfUserDatabasePath;
	private JButton butBrowseUserDatabasePath, 
					butDefaultUserDatabase, 
					butBrowseDatabasePath, 
					butDefaultDatabase;
	
	//Constants
	private final String CANCEL = "cancel";
	private final String SAVE = "save";
	
	//Constructors
	public SettingsPanel()
	{
		//Initialization
		this.gatherData();
		this.initPanelBelow();
		this.initPanelCenter();
		this.scroll = ScrollPaneManager.generateDefaultScrollPane(this.panelCenter, 10, 10);
		
		//Properties
		this.setLayout(new BorderLayout());
		this.setBackground(Constants.COLOR_MAIN_MENU_BACKGROUND);
//		this.setBackground(Color.WHITE);
//		this.setBackground(Constants.COLOR_MAIN_MENU_BACKGROUND);
		
		//Add to panel
		this.add(this.scroll, BorderLayout.CENTER);
		this.add(this.panelBelow, BorderLayout.SOUTH);
	}
	
	//Methods
	private void initPanelCenter()
	{
		//Initialization
		this.panelCenter = new JPanel();
		this.initCatDatabase();
		this.initCatWindow();
		this.initCatProgram();
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		this.panelCenter.setLayout(new GridBagLayout());
		this.panelCenter.setOpaque(false);
		
		//Add to panel
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = Constants.INSETS_TOP_COMPONENT;
		c.fill = GridBagConstraints.BOTH;
		this.panelCenter.add(this.catProgram, c);					//Program Settings
		c.insets = Constants.INSETS_GENERAL;
		this.panelCenter.add(this.catWindow, c);					//Window Settings
		this.panelCenter.add(this.catDatabase, c);					//Database Settings		
//		this.panelCenter.add(scrollDatabase, c);					//Database settings
	}
	private void initCatDatabase()
	{
		//Initialization
		this.catDatabase = new SettingsCategoryPanel(Methods.getLanguageText(XMLIdentifier.SETTINGS_DATABASE_TITLE));
		this.labDatabasePath = new JLabel (Methods.getLanguageText(XMLIdentifier.SETTINGS_DATABASE_PATH_TEXT), SwingConstants.RIGHT);
		this.tfDatabasePath = new JTextField(this.dataMap.get(Settings.DATABASE_PATH), 30);
		this.butBrowseDatabasePath = new JButton(Methods.getLanguageText(XMLIdentifier.BROWSE_TEXT));
		this.butDefaultDatabase = new JButton(Methods.getLanguageText(XMLIdentifier.RESET_TEXT));
		this.labUserDatabasePath = new JLabel (Methods.getLanguageText(XMLIdentifier.SETTINGS_DATABASE_USERS_PATH_TEXT));
		this.tfUserDatabasePath = new JTextField(this.dataMap.get(Settings.DATABASE_USERS_PATH), 30);
		this.butBrowseUserDatabasePath = new JButton(Methods.getLanguageText(XMLIdentifier.BROWSE_TEXT));
		this.butDefaultUserDatabase = new JButton(Methods.getLanguageText(XMLIdentifier.RESET_TEXT));
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		this.catDatabase.getPanelCanvas().setLayout(new GridBagLayout());
	//	this.catDatabase.setOpaque(false);
		tfDatabasePath.setEditable(false);
		tfDatabasePath.setOpaque(false);
		tfUserDatabasePath.setEditable(false);
		tfUserDatabasePath.setOpaque(false);
		butBrowseDatabasePath.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				LookAndFeel oldLF = UIManager.getLookAndFeel();
				try
				{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				}
				catch(Exception ex) {}
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				jfc.setDialogTitle(Methods.getLanguageText(XMLIdentifier.SETTINGS_DATABASE_PATH_SELECT_TEXT));
				int response = jfc.showDialog(null, Methods.getLanguageText(XMLIdentifier.SELECT_TEXT));
				
				if (response == JFileChooser.APPROVE_OPTION)
				{
					tfDatabasePath.setText(jfc.getSelectedFile().getAbsolutePath());
				}
				
				try
				{
					UIManager.setLookAndFeel(oldLF);
				}
				catch(Exception ex) {}
			}
		});
		butDefaultDatabase.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				File file = new File(Constants.DATABASE_DEFAULT_PATH);
				tfDatabasePath.setText(file.getAbsolutePath());
			}
		});
		tfDatabasePath.getDocument().addDocumentListener(new DocumentListener()
		{

			@Override
			public void changedUpdate(DocumentEvent e) 
			{
				dataMap.put(Settings.DATABASE_PATH, tfDatabasePath.getText().trim());
			}

			@Override
			public void insertUpdate(DocumentEvent e) 
			{
				dataMap.put(Settings.DATABASE_PATH, tfDatabasePath.getText().trim());
			}

			@Override
			public void removeUpdate(DocumentEvent e) 
			{
				dataMap.put(Settings.DATABASE_PATH, tfDatabasePath.getText().trim());
			}
			
		});
		butBrowseUserDatabasePath.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				LookAndFeel oldLF = UIManager.getLookAndFeel();
				try
				{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				}
				catch(Exception ex) {}
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				jfc.setDialogTitle(Methods.getLanguageText(XMLIdentifier.SETTINGS_DATABASE_USERS_SELECT_TEXT));
				int response = jfc.showDialog(null, Methods.getLanguageText(XMLIdentifier.SELECT_TEXT));
				
				if (response == JFileChooser.APPROVE_OPTION)
				{
					tfUserDatabasePath.setText(jfc.getSelectedFile().getAbsolutePath());
				}
				
				try
				{
					UIManager.setLookAndFeel(oldLF);
				}
				catch(Exception ex) {}
			}
		});
		butDefaultUserDatabase.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				File file = new File(Constants.PATIENTS_LIST_DEFAULT_PATH);
				tfUserDatabasePath.setText(file.getAbsolutePath());
			}
		});
		tfUserDatabasePath.getDocument().addDocumentListener(new DocumentListener()
		{

			@Override
			public void changedUpdate(DocumentEvent e) 
			{
				dataMap.put(Settings.DATABASE_USERS_PATH, tfUserDatabasePath.getText().trim());
			}

			@Override
			public void insertUpdate(DocumentEvent e) 
			{
				dataMap.put(Settings.DATABASE_USERS_PATH, tfUserDatabasePath.getText().trim());
			}

			@Override
			public void removeUpdate(DocumentEvent e) 
			{
				dataMap.put(Settings.DATABASE_USERS_PATH, tfUserDatabasePath.getText().trim());
			}
			
		});
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_TOP_COMPONENT;
		c.fill = GridBagConstraints.BOTH;
		this.catDatabase.getPanelCanvas().add(labDatabasePath, c);			//Database Path
		Gbm.nextGridColumn(c);
		this.catDatabase.getPanelCanvas().add(tfDatabasePath, c);			//Database Path Text Field
		Gbm.nextGridColumn(c);
		this.catDatabase.getPanelCanvas().add(butBrowseDatabasePath , c);	//Browse database Path button
		Gbm.nextGridColumn(c);
		this.catDatabase.getPanelCanvas().add(butDefaultDatabase , c);		//Reset Database button
		Gbm.newGridLine(c);
		this.catDatabase.getPanelCanvas().add(labUserDatabasePath, c);		//User Database path
		Gbm.nextGridColumn(c);
		this.catDatabase.getPanelCanvas().add(tfUserDatabasePath, c);		//User Database path Text Field
		Gbm.nextGridColumn(c);
		this.catDatabase.getPanelCanvas().add(butBrowseUserDatabasePath, c);	//Browse User Database Path Button
		Gbm.nextGridColumn(c);
		this.catDatabase.getPanelCanvas().add(butDefaultUserDatabase, c);	//Reset User Database Path Button
	}
	private void initCatWindow()
	{
		//Initialization
		this.catWindow = new SettingsCategoryPanel(Methods.getLanguageText(XMLIdentifier.SETTINGS_WINDOW_TITLE));
		this.labWindowMode = new JLabel (Methods.getLanguageText(XMLIdentifier.SETTINGS_WINDOW_MODE_TEXT));
		this.radFullscreen = new JRadioButton(Methods.getLanguageText(XMLIdentifier.SETTINGS_WINDOW_MODE_FULLSCREEN_TEXT));
		this.radWindowed = new JRadioButton(Methods.getLanguageText(XMLIdentifier.SETTINGS_WINDOW_MODE_WINDOWED_TEXT));
		ButtonGroup group = new ButtonGroup();
		GridBagConstraints c = new GridBagConstraints();
		String chosenWindowState = this.dataMap.get(Settings.WINDOW_MODE);
		
		//Properties
		this.catWindow.getPanelCanvas().setLayout(new GridBagLayout());
//		this.catWindow.setOpaque(false);
		group.add(radWindowed);
		group.add(radFullscreen);
		radWindowed.setOpaque(false);
		radFullscreen.setOpaque(false);
		if (chosenWindowState.equals(Settings.FULLSCREEN))
		{
			radFullscreen.setSelected(true);
		}
		else
		{
			radWindowed.setSelected(true);
		}
		radWindowed.addItemListener(new ItemListener()
				{
			@Override
			public void itemStateChanged(ItemEvent e) 
			{
				if (radWindowed.isSelected())
				{
					dataMap.put(Settings.WINDOW_MODE, Settings.WINDOWED);
				}
				else
				{
					dataMap.put(Settings.WINDOW_MODE, Settings.FULLSCREEN);
				}
			}
			
		});
		radFullscreen.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e) 
			{
				if (radWindowed.isSelected())
				{
					dataMap.put(Settings.WINDOW_MODE, Settings.WINDOWED);
				}
				else
				{
					dataMap.put(Settings.WINDOW_MODE, Settings.FULLSCREEN);
				}
			}
			
		});		
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_TOP_COMPONENT;
		c.fill = GridBagConstraints.BOTH;
		this.catWindow.getPanelCanvas().add(labWindowMode, c);			//Window Mode
		Gbm.nextGridColumn(c);
		this.catWindow.getPanelCanvas().add(radFullscreen, c);			//Fullscreen radio Button
		Gbm.nextGridColumn(c);
		this.catWindow.getPanelCanvas().add(radWindowed, c);				//Windowed radio button
	}
	private void initCatProgram()
	{
		//Initialization
		this.catProgram = new SettingsCategoryPanel(Methods.getLanguageText(XMLIdentifier.SETTINGS_PROGRAM_TITLE));
		this.labLanguage = new JLabel(Methods.getLanguageText(XMLIdentifier.SETTINGS_LANGUAGE_TEXT));
		this.comboLanguages = new JComboBox<String>(Methods.getLanguages());
		this.butLanguageRefresh = new JButton(Methods.getLanguageText(XMLIdentifier.REFRESH_TEXT));
		GridBagConstraints c = new GridBagConstraints();
		String selectedLanguage = this.dataMap.get(Settings.LANGUAGE);
		
		//Properties
		this.catProgram.getPanelCanvas().setLayout(new GridBagLayout());
//		this.catProgram.setOpaque(false);
		comboLanguages.setBackground(Color.WHITE);
		butLanguageRefresh.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				comboLanguages.setModel(new DefaultComboBoxModel<String>(Methods.getLanguages()));
				comboLanguages.setSelectedItem(selectedLanguage);
			}
		});
		comboLanguages.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dataMap.put(Settings.LANGUAGE, comboLanguages.getSelectedItem().toString());
			}
		});
		comboLanguages.setSelectedItem(selectedLanguage);
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_TOP_COMPONENT;
		c.fill = GridBagConstraints.BOTH;
		this.catProgram.getPanelCanvas().add(labLanguage, c);			//Language
		Gbm.nextGridColumn(c);
		this.catProgram.getPanelCanvas().add(comboLanguages, c);			//Language Options
		Gbm.nextGridColumn(c);
		this.catProgram.getPanelCanvas().add(butLanguageRefresh, c);				//Refresh Language Button
	}
	private void initPanelBelow()
	{
		//Initialization
		this.panelBelow = new JPanel();
		this.butCancel = new JButton(Methods.getLanguageText(XMLIdentifier.CANCEL_TEXT));
		this.butSave = new JButton(Methods.getLanguageText(XMLIdentifier.SAVE_TEXT));
		
		//Properties
		this.panelBelow.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.panelBelow.setOpaque(false);
		this.butCancel.setActionCommand(this.CANCEL);
		this.butCancel.addActionListener(this);
		this.butCancel.setMnemonic(KeyEvent.VK_C);
		this.butSave.setActionCommand(this.SAVE);
		this.butSave.addActionListener(this);
		this.butSave.setMnemonic(KeyEvent.VK_S);
		
		//Add to panel
		this.panelBelow.add(this.butCancel);
		this.panelBelow.add(this.butSave);
	}
	private void gatherData()
	{
		try 
		{
			Settings setting = new Settings(FileOperation.loadSettingsDocument());
			this.dataMap = setting.getDataMap();
		} 
		catch (ParserConfigurationException | SAXException | IOException e) 
		{
			e.printStackTrace();
			Settings setting = new Settings();
			this.dataMap = setting.getDataMap();
		}
	}
	
	//Interfaces
	public void actionPerformed(ActionEvent e)
	{
		switch (e.getActionCommand())
		{
			case CANCEL:
		//		MainFrame.changePanel(Globals.MAIN_MENU);
				Globals.MAIN_FRAME.changePanel(PanelName.MAIN_MENU);
				break;
				
			case SAVE:
				FileOperation.saveSettings(new Settings(this.dataMap));
		//		MainFrame.changePanel(Globals.MAIN_MENU);
		//		MainFrame.refreshSettings();
				Globals.MAIN_FRAME.changePanel(PanelName.MAIN_MENU);
				Globals.MAIN_FRAME.refreshSettings();
				break;
		}
	}

	@Override
	public void revalidateLanguage()
	{
		this.catProgram.setBorderText(Methods.getLanguageText(XMLIdentifier.SETTINGS_PROGRAM_TITLE));
		this.catWindow.setBorderText(Methods.getLanguageText(XMLIdentifier.SETTINGS_WINDOW_TITLE));
		this.catDatabase.setBorderText(Methods.getLanguageText(XMLIdentifier.SETTINGS_DATABASE_TITLE));
		this.labWindowMode.setText(Methods.getLanguageText(XMLIdentifier.SETTINGS_WINDOW_MODE_TEXT));
		this.radWindowed.setText(Methods.getLanguageText(XMLIdentifier.SETTINGS_WINDOW_MODE_WINDOWED_TEXT));
		this.radFullscreen.setText(Methods.getLanguageText(XMLIdentifier.SETTINGS_WINDOW_MODE_FULLSCREEN_TEXT));
		this.labLanguage.setText(Methods.getLanguageText(XMLIdentifier.SETTINGS_LANGUAGE_TEXT));
		this.butLanguageRefresh.setText(Methods.getLanguageText(XMLIdentifier.REFRESH_TEXT));
		this.labDatabasePath.setText(Methods.getLanguageText(XMLIdentifier.SETTINGS_DATABASE_PATH_TEXT));
		this.labUserDatabasePath.setText(Methods.getLanguageText(XMLIdentifier.SETTINGS_DATABASE_USERS_PATH_TEXT));
		this.butBrowseDatabasePath.setText(Methods.getLanguageText(XMLIdentifier.BROWSE_TEXT));
		this.butBrowseUserDatabasePath.setText(Methods.getLanguageText(XMLIdentifier.BROWSE_TEXT));
		this.butDefaultDatabase.setText(Methods.getLanguageText(XMLIdentifier.RESET_TEXT));
		this.butDefaultUserDatabase.setText(Methods.getLanguageText(XMLIdentifier.RESET_TEXT));
		this.butSave.setText(Methods.getLanguageText(XMLIdentifier.SAVE_TEXT));
		this.butCancel.setText(Methods.getLanguageText(XMLIdentifier.CANCEL_TEXT));
	}
}
