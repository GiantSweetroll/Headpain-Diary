package diary;

import java.awt.Color;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import diary.constants.Constants;
import diary.gui.ImageTextPanel;

public class ImageTextButton extends JButton
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1705288311815602872L;

	public ImageTextButton(ImageIcon image, String text)
	{
		ImageTextPanel panelImage = new ImageTextPanel(image, text);
		panelImage.getTextLabel().setForeground(Color.WHITE);
		panelImage.getTextLabel().setFont(Constants.FONT_SUB_TITLE);
		this.add(panelImage);
	}
	public ImageTextButton(List<ImageIcon> images, String text)
	{
		ImageTextPanel panelImage = new ImageTextPanel(images, text);
		panelImage.getTextLabel().setForeground(Color.WHITE);
		panelImage.getTextLabel().setFont(Constants.FONT_SUB_TITLE);
		this.add(panelImage);
	}
}
