package diary.puzzleimage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

import diary.constants.Constants;
import giantsweetroll.gui.swing.Gbm;

public class ImageCollection extends JPanel implements MouseListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1485780389245875969L;

	private List<ImagePiece> images;
	private GridBagConstraints c;
	private Set<String> selectedImages;
	
	public ImageCollection(List<ImagePiece> images)
	{
		//Initialization
		this.images = images;
		c = new GridBagConstraints();
		this.selectedImages = new HashSet<String>();
		
		//Properties
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.WHITE);
		
		//Add to panel
		this.initImages();
	}
	
	//Public Methods
	public Set<String> getSelectedImagesNames()
	{
		return this.selectedImages;
	}
	public void resetSelection()
	{
		this.selectedImages.clear();
		for(ImagePiece piece : this.images)
		{
			piece.color(false);
		}
	}
	public void setSelection(Set<String> selections)
	{
		this.selectedImages = selections;
		for (String name : this.selectedImages)
		{
			for (ImagePiece piece : this.images)
			{
				if (piece.getName().equals(name))
				{
					piece.color(true);
					break;
				}
			}
		}
	}
	
	//Private Methods
	private void initImages()
	{
		Gbm.goToOrigin(c);
		for (int i=0; i<this.images.size(); i++)		//array counter
		{
			for (c.gridx=0; c.gridx<4; c.gridx++, i++)			//gridx counter
			{
				this.add(this.images.get(i), c);
			}
			c.gridy++;
			i--;
		}
	}
	
	//Overridden Methods
	@Override
	public Dimension getMinimumSize()
	{	
		return Constants.CUSTOM_PAIN_LOCATION_IMAGE_SIZE;
	}
	
	//Interface
	@Override
	public void mouseClicked(MouseEvent e)
	{
		for (ImagePiece piece : this.images)
		{
			if (piece.isColored())
			{
				this.selectedImages.add(piece.getName());
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
