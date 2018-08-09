package diary.gui.EntryLog.painLocation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import diary.constants.Constants;
import diary.constants.XMLIdentifier;
import diary.interfaces.GUIFunction;
import diary.methods.Methods;
import diary.methods.PainLocationMethods;
import diary.puzzleimage.ImageCollection;

public class PainLocationCustomSelection extends JPanel implements GUIFunction, ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4593583360580989292L;

	private ImageCollection back, front, right, left;
	private List<ImageCollection> imageCollection;
	private JPanel panelImages, panelBelow;
	private JButton butReset;
	private JLabel labRight, labLeft;
	
	//Constructors
	public PainLocationCustomSelection()
	{
		//Initialization
		this.initPanelBelow();
		this.initPanelImages();
		this.imageCollection = new ArrayList<ImageCollection>();
		
		//Properties
		this.setLayout(new BorderLayout());
		this.setBackground(Constants.COLOR_DISABLED_COMPONENT);
		
		//Add to panel
		this.add(this.panelImages, BorderLayout.CENTER);
		this.add(this.panelBelow, BorderLayout.SOUTH);
		
		//Add to image collection
		this.imageCollection.add(this.front);
		this.imageCollection.add(this.back);
		this.imageCollection.add(this.right);
		this.imageCollection.add(this.left);
	}
	//Create GUI
	private void initPanelBelow()
	{
		//Initialization
		this.panelBelow = new JPanel();
		this.butReset = new JButton(Methods.getLanguageText(XMLIdentifier.RESET_TEXT));
		
		//Properties
		this.panelBelow.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.panelBelow.setOpaque(false);
		this.butReset.addActionListener(this);
		
		//Add to panel
		this.panelBelow.add(this.butReset);
	}
	private void initPanelImages()
	{
		//Initialization
		this.panelImages = new JPanel();
		this.back = new ImageCollection(PainLocationMethods.getPainLocationBack());
		this.front = new ImageCollection(PainLocationMethods.getPainLocationFront());
		this.right = new ImageCollection(PainLocationMethods.getPainLocationRight());
		this.left = new ImageCollection(PainLocationMethods.getPainLocationLeft());
		this.labRight = new JLabel(Methods.getLanguageText(XMLIdentifier.YOUR_RIGHT_TEXT), SwingConstants.CENTER);
		this.labLeft = new JLabel(Methods.getLanguageText(XMLIdentifier.YOUR_LEFT_TEXT), SwingConstants.CENTER);
		
		//Properties
		this.panelImages.setLayout(new GridLayout(0, 2));
		this.panelImages.setOpaque(false);
		
		//Add to panel
		this.panelImages.add(this.front);
		this.panelImages.add(this.back);
		this.panelImages.add(this.left);
		this.panelImages.add(this.right);
		this.panelImages.add(this.labLeft);
		this.panelImages.add(this.labRight);
	}
	
	//Methods
	public List<String> getLocations()
	{
		List<String> list = new ArrayList<String>();
		
		list.addAll(this.back.getSelectedImagesNames());
		list.addAll(this.front.getSelectedImagesNames());
		list.addAll(this.right.getSelectedImagesNames());
		list.addAll(this.left.getSelectedImagesNames());
		
		return list;
	}
	public void setLocations(List<String> locations)
	{
		Set<String> set = new HashSet<String>();
		set.addAll(locations);
		
		for (ImageCollection collection : this.imageCollection)
		{
			collection.setSelection(set);
		}
	}
	
	//Interfaces
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		this.resetDefaults();
		this.revalidate();
		this.repaint();
	}

	@Override
	public void resetDefaults() 
	{
		this.back.resetSelection();
		this.front.resetSelection();
		this.right.resetSelection();
		this.left.resetSelection();
	}

	@Override
	public void refresh(){}

	@Override
	public void setEnabled(boolean enabled)
	{
		for (ImageCollection collection : this.imageCollection)
		{
			collection.setEnabled(enabled);
		}
		
		if (!enabled)
		{
			this.setBackground(Constants.COLOR_DISABLED_COMPONENT);
		}
		else
		{
			this.setBackground(Color.WHITE);
		}
	}
}
