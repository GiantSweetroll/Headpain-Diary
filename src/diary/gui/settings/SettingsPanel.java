package diary.gui.settings;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import diary.constants.Constants;
import diary.constants.XMLIdentifier;
import diary.gui.MainFrame;
import diary.gui.MainMenu;

public class SettingsPanel extends JPanel implements ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8823949814661108143L;	
	
	private JPanel panelCenter, panelBelow;
	private DatabaseSettingsPanel catDatabase;
	private WindowSettingsPanel catWindow;
	private JButton butCancel, butSave;
	
	//Constants
	private final String CANCEL = "cancel";
	private final String SAVE = "save";
	
	public SettingsPanel()
	{
		//Initialization
		this.initPanelBelow();
		this.initPanelCenter();
		
		//Properties
		this.setLayout(new BorderLayout());
		this.setOpaque(false);
		
		//Add to panel
		this.add(this.panelCenter, BorderLayout.CENTER);
		this.add(this.panelBelow, BorderLayout.SOUTH);
	}
	
	//Methods
	private void initPanelCenter()
	{
		//Initialization
		this.panelCenter = new JPanel();
		this.catDatabase = new DatabaseSettingsPanel();
		this.catWindow = new WindowSettingsPanel();
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		this.panelCenter.setLayout(new GridBagLayout());
		this.panelCenter.setOpaque(false);
		
		//Add to panel
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = Constants.INSETS_TOP_COMPONENT;
		c.fill = GridBagConstraints.BOTH;
		this.panelCenter.add(this.catWindow, c);					//Window Settings
		c.insets = Constants.INSETS_GENERAL;
		this.panelCenter.add(this.catDatabase, c);					//Database Settings		
	}
	private void initPanelBelow()
	{
		//Initialization
		this.panelBelow = new JPanel();
		this.butCancel = new JButton(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.CANCEL_TEXT));
		this.butSave = new JButton(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SAVE_TEXT));
		
		//Properties
		this.panelBelow.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.panelBelow.setOpaque(false);
		this.butCancel.setActionCommand(this.CANCEL);
		this.butCancel.addActionListener(this);
		this.butSave.setActionCommand(this.SAVE);
		this.butSave.addActionListener(this);
		
		//Add to panel
		this.panelBelow.add(this.butCancel);
		this.panelBelow.add(this.butSave);
	}
	
	//Interfaces
	public void actionPerformed(ActionEvent e)
	{
		switch (e.getActionCommand())
		{
			case CANCEL:
				MainFrame.changePanel(new MainMenu());
				break;
				
			case SAVE:
				this.catDatabase.saveData();
				this.catWindow.saveData();
				MainFrame.changePanel(new MainMenu());
				break;
		}
	}
}
