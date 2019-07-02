package diary.gui.settings;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class SettingsCategoryPanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4624584191728657218L;
	
	private JPanel panel;
	
	public SettingsCategoryPanel(String borderText)
	{
		//Initialization
		this.panel = new JPanel();
		
		//Properties
		this.setBorderText(borderText);
		this.setLayout(new BorderLayout());
		this.setOpaque(false);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.panel.setBackground(Color.WHITE);
		
		//Add to panel
		this.add(this.panel, BorderLayout.CENTER);
	}
	
	//Public Methods
	public void setBorderText(String borderText)
	{
//		this.setBorder(BorderFactory.createTitledBorder(borderText));
		this.panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE), borderText));
	}
	public JPanel getPanelCanvas()
	{
		return this.panel;
	}
}
