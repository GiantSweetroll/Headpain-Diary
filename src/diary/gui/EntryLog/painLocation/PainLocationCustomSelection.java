package diary.gui.EntryLog.painLocation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import diary.constants.Constants;
import diary.constants.XMLIdentifier;
import diary.gui.GButton;
import diary.interfaces.GUIFunction;
import diary.interfaces.LanguageListener;
import diary.methods.Methods;
import diary.methods.PainLocationMethods;
import diary.puzzleimage.ImageCollection;
import giantsweetroll.gui.swing.Gbm;

public class PainLocationCustomSelection extends JPanel implements GUIFunction, ActionListener, LanguageListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4593583360580989292L;

	private ImageCollection back, front, right, left;
	private List<ImageCollection> imageCollection;
	private JPanel panelImages, panelBelow, panelCenter, panelDirection;
	private GButton butReset;
	private JLabel labRight, labLeft;
	
	//Constructors
	public PainLocationCustomSelection()
	{
		//Initialization
		this.initPanelBelow();
		this.initPanelCenter();
		this.imageCollection = new ArrayList<ImageCollection>();
		
		//Properties
		this.setLayout(new BorderLayout());
		this.setBackground(Constants.COLOR_DISABLED_COMPONENT);
		
		//Add to panel
		this.add(this.panelCenter, BorderLayout.CENTER);
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
		this.butReset = new GButton(Methods.getLanguageText(XMLIdentifier.RESET_TEXT));
		
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
		/*
		SpringLayout layout = new SpringLayout();
		this.panelImages = new JPanel()
				{
					private static final long serialVersionUID = 1060528737853563125L;

					@Override
					public Dimension getMinimumSize()
					{
						return new Dimension(super.getMinimumSize().width, back.getMinimumSize().height +
																			left.getMinimumSize().height +
																			labRight.getMinimumSize().height);
					}
				};
		*/
		//Properties
//		this.panelImages.setLayout(layout);
		this.panelImages.setLayout(new GridLayout(0, 2));
//		this.panelImages.setOpaque(false);
		/*
		layout.putConstraint(SpringLayout.EAST, this.front, Constants.INSETS_BASE, SpringLayout.HORIZONTAL_CENTER, this.panelImages);
		layout.putConstraint(SpringLayout.SOUTH, this.front, Constants.INSETS_BASE, SpringLayout.VERTICAL_CENTER, this.panelImages);
		layout.putConstraint(SpringLayout.WEST, this.back, Constants.INSETS_BASE, SpringLayout.HORIZONTAL_CENTER, this.panelImages);
		layout.putConstraint(SpringLayout.SOUTH, this.back, Constants.INSETS_BASE, SpringLayout.VERTICAL_CENTER, this.panelImages);
		layout.putConstraint(SpringLayout.NORTH, this.left, Constants.INSETS_BASE, SpringLayout.VERTICAL_CENTER, this.panelImages);
		layout.putConstraint(SpringLayout.EAST, this.left, Constants.INSETS_BASE, SpringLayout.HORIZONTAL_CENTER, this.panelImages);
		layout.putConstraint(SpringLayout.WEST, this.right, Constants.INSETS_BASE, SpringLayout.HORIZONTAL_CENTER, this.panelImages);
		layout.putConstraint(SpringLayout.NORTH, this.right, Constants.INSETS_BASE, SpringLayout.VERTICAL_CENTER, this.panelImages);
		layout.putConstraint(SpringLayout.NORTH, this.labLeft, Constants.INSETS_BASE, SpringLayout.SOUTH, this.left);
		layout.putConstraint(SpringLayout.EAST, this.labLeft, Constants.INSETS_BASE, SpringLayout.HORIZONTAL_CENTER, this.panelImages);
		layout.putConstraint(SpringLayout.WEST, this.labRight, Constants.INSETS_BASE, SpringLayout.HORIZONTAL_CENTER, this.panelImages);
		layout.putConstraint(SpringLayout.NORTH, this.labRight, Constants.INSETS_BASE, SpringLayout.SOUTH, this.panelImages);
		*/
		
		//Add to panel
		this.panelImages.add(this.front);
		this.panelImages.add(this.back);
		this.panelImages.add(this.left);
		this.panelImages.add(this.right);
	}
	private void initPanelCenter()
	{
		//Initialization
		this.panelCenter = new JPanel();
		this.initPanelImages();
		this.initPanelDirection();
		GridBagConstraints c = new GridBagConstraints ();
		
		//Properties
		this.panelCenter.setLayout(new GridBagLayout());
//		this.setOpaque(false);
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_GENERAL;
		c.fill = GridBagConstraints.BOTH;
		this.panelCenter.add(this.panelImages, c);
		Gbm.newGridLine(c);
		this.panelCenter.add(this.panelDirection, c);
	}
	private void initPanelDirection()
	{
		//Initialization
		this.panelDirection = new JPanel();
		this.labRight = new JLabel(Methods.getLanguageText(XMLIdentifier.YOUR_RIGHT_TEXT), SwingConstants.CENTER);
		this.labLeft = new JLabel(Methods.getLanguageText(XMLIdentifier.YOUR_LEFT_TEXT), SwingConstants.CENTER);
		
		//Properties
		this.panelDirection.setLayout(new GridLayout(0, 2));
		this.panelDirection.setOpaque(false);
		
		//Add to panel
		this.panelDirection.add(this.labLeft);
		this.panelDirection.add(this.labRight);
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

	//Overridden Methods
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
			this.panelImages.setBackground(Constants.COLOR_DISABLED_COMPONENT);
			this.panelCenter.setBackground(Constants.COLOR_DISABLED_COMPONENT);
		}
		else
		{
			this.setBackground(Color.WHITE);
			this.panelImages.setBackground(Color.WHITE);
			this.panelCenter.setBackground(Color.WHITE);
		}
		this.butReset.setEnabled(enabled);
	}
	
	@Override
	public void revalidateLanguage()
	{
		this.butReset.setText(Methods.getLanguageText(XMLIdentifier.RESET_TEXT));
		this.labLeft.setText(Methods.getLanguageText(XMLIdentifier.YOUR_LEFT_TEXT));
		this.labRight.setText(Methods.getLanguageText(XMLIdentifier.YOUR_RIGHT_TEXT));
	}
}
