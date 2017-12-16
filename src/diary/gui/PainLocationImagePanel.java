package diary.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import diary.constants.Constants;

public class PainLocationImagePanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3980045206256622812L;
	private JLabel labImage, labText;
	private GridBagConstraints c;
	
	protected PainLocationImagePanel(String text, ImageIcon imageIcon)
	{
		//Initialization
		this.labImage = new JLabel (imageIcon);
		this.labText = new JLabel (text, SwingConstants.CENTER);
		this.c = new GridBagConstraints();
		
		//Properties
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		
		//Add to panel
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = Constants.INSETS_TOP_COMPONENT;
		this.add(this.labImage, c);
		c.insets = Constants.INSETS_GENERAL;
		this.add(this.labText, c);
	}
}
