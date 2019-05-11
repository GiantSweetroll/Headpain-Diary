package diary.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import diary.methods.Methods;

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
		this.panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.panel.add(new JLabel(image));
		this.panel.setOpaque(false);
		
		this.createAndShowGUI();
	}
	public ImageTextPanel(ImageIcon image, String text, int imageSizePercentage)
	{
		//Initialization
		this.label = new JLabel(text, SwingConstants.CENTER);
		this.panel = new JPanel();
		
		//Properties
		image = Methods.resizeImageByRatio(image, imageSizePercentage);
		this.panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.panel.add(new JLabel(image));
		this.panel.setOpaque(false);
		
		this.createAndShowGUI();		
	}
	public ImageTextPanel(List<ImageIcon> images, String text)
	{
		//Initialization
		this.label = new JLabel(text, SwingConstants.CENTER);
		this.panel = new JPanel();
		
		//Properties
		this.panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		for (ImageIcon image : images)
		{
			this.panel.add(new JLabel(image));
		}
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
	
	//Overridden Methods
	@Override
	public void setEnabled(boolean enabled)
	{
		super.setEnabled(enabled);
		
		this.panel.setEnabled(enabled);
		this.label.setEnabled(enabled);
		
		for (Component component : this.panel.getComponents())
		{
			component.setEnabled(enabled);
		}
	}
}
