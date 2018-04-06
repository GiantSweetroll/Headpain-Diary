package diary.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import diary.constants.Constants;
import diary.constants.XMLIdentifier;
import diary.methods.Methods;

public class EntryTypeSelectionPanel extends JPanel implements ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7031573286167719518L;

	private JButton butSingle, butMulti, butBack;
	private JLabel labEntryTypeTitle;
	private JPanel panelCenter, panelBelow;
	
	//Constants
	private final String SINGLE = "single";
	private final String MULTI = "multi";
	private final String BACK = "back";
	
	//Constructors
	public EntryTypeSelectionPanel()
	{
		this.init();
	}
	
	//Methods
	private void init()
	{
		//Initialization
		this.initPanelBelow();
		this.initPanelCenter();
		
		//Properties
		this.setLayout(new BorderLayout());
		this.setBackground(Constants.COLOR_MAIN_MENU_BACKGROUND);
		
		//Add to panel
		this.add(this.panelCenter, BorderLayout.CENTER);
		this.add(this.panelBelow, BorderLayout.SOUTH);
	}
	private void initPanelBelow()
	{
		//Initialization
		this.panelBelow = new JPanel();
		this.butBack = new JButton(Methods.getLanguageText(XMLIdentifier.BACK_TEXT));
		
		//Properties
		this.panelBelow.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.panelBelow.setOpaque(false);
		
		//Add to panel
		this.panelBelow.add(this.butBack);
	}
	private void initPanelCenter()
	{
		//Initialization
		this.panelCenter = new JPanel();
		
		//Properties
		this.panelCenter.setLayout(new GridLayout(0, 1));
		this.panelCenter.setOpaque(false);
		
		//Add to panel
	}
	
	//Interfaces
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		switch(e.getActionCommand())
		{
			case SINGLE:
				break;
				
			case MULTI:
				break;
				
			case BACK:
				break;
		}
	}
}
