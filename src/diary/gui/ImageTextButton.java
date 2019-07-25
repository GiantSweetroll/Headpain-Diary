package diary.gui;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.ImageIcon;

public class ImageTextButton extends GButton
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1705288311815602872L;

	private ImageTextPanel panelImage;
	
	//Constructors
	public ImageTextButton(ImageIcon image, String text)
	{
		this.init(image, text);
	}
	public ImageTextButton(List<ImageIcon> images, String text)
	{
		this.init(images, text);
	}
	public ImageTextButton(ImageIcon image, String text, Font font, Color textColor)
	{
		this.init(image, text);
		this.panelImage.getTextLabel().setForeground(textColor);
		this.panelImage.getTextLabel().setFont(font);
	}
	public ImageTextButton(List<ImageIcon> images, String text, Font font, Color textColor)
	{
		this.init(images, text);
		this.panelImage.getTextLabel().setForeground(textColor);
		this.panelImage.getTextLabel().setFont(font);
	}
	
	//Methods
	public void init(ImageIcon image, String text)
	{
		this.panelImage = new ImageTextPanel(image, text);
		this.add(panelImage);
	}
	public void init(List<ImageIcon> images, String text)
	{
		this.panelImage = new ImageTextPanel(images, text);
		this.add(panelImage);
	}
	
	public ImageTextPanel getImageTextPanel()
	{
		return this.panelImage;
	}
	
	//Overridden Methods
	@Override
	public void setText(String text)
	{
		this.panelImage.getTextLabel().setText(text);
	}
}
