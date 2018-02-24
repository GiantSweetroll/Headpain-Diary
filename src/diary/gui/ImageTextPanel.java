package diary.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ImageTextPanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3684223863746252720L;
	
	private JLabel label;
	private JPanel panel;
	
	//Constructors
	public ImageTextPanel(ImageIcon image, String text)
	{
		//Initialization
		this.label = new JLabel(text, SwingConstants.CENTER);
		this.panel = new JPanel();
		
		//Properties
		this.panel.setLayout(new BorderLayout());
		this.panel.add(new JLabel(image), BorderLayout.CENTER);
		this.panel.setOpaque(false);
		
		this.createAndShowGUI();
	}
	
	//Methods
	public JLabel getTextLabel()
	{
		return this.label;
	}
	public void setImage(ImageIcon image)
	{
		this.panel.add(new JLabel(image), BorderLayout.CENTER);
	}
	private void createAndShowGUI()
	{	
		//Properties
		this.setLayout(new GridLayout(0, 1));
		this.setOpaque(false);
		
		//Add to panel
		this.add(this.panel);
		this.add(this.label);
	}
}
