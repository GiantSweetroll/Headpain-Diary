package diary.gui.EntryLog;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import diary.constants.Constants;
import diary.constants.ImageConstants;

public class PainLocationImagePanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3980045206256622812L;
	private JLabel labText;
	private PainImagePanel panel;
	private GridBagConstraints c;
	
	protected PainLocationImagePanel(String text, URL imageUrl)
	{
		//Initialization
		this.panel = new PainImagePanel(imageUrl);
		this.labText = new JLabel (text, SwingConstants.CENTER);
		this.c = new GridBagConstraints();
		
		//Properties
		this.panel.setPreferredSize(Constants.IMAGE_SIZE);
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		
		//Add to panel
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = Constants.INSETS_TOP_COMPONENT;
		this.add(this.panel, c);
		c.insets = Constants.INSETS_GENERAL;
		this.add(this.labText, c);
	}
}
