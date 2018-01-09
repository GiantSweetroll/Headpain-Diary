package diary.gui.settings;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class SettingsCategoryPanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4624584191728657218L;
	
	public SettingsCategoryPanel(String borderText)
	{
		this.setBorder(BorderFactory.createTitledBorder(borderText));
	}
}
