package diary.gui;

import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import diary.constants.Constants;
import diary.constants.ImageConstants;
import diary.constants.PainLocationConstants;
import diary.constants.XMLIdentifier;
import giantsweetroll.ImageManager;

public class PainLocationSelectionPanelSpecific extends JPanel implements ItemListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3593645171381986255L;
	private JLabel labSelectPos;
	private ButtonGroup group;
	private List<JCheckBox> checkList;
	private List<PainLocationImagePanel> imageList;
	private String selectedPosition;
	
	protected PainLocationSelectionPanelSpecific(String location)
	{
		this.init(location);
		this.checkList.get(0).setSelected(true);
		this.selectedPosition = this.checkList.get(0).getName();
	}
	
	private void init(String location)
	{
		//Initialization
		this.labSelectPos = new JLabel (Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SELECT_LOCATION_LABEL) + ":");
		this.group = new ButtonGroup();
		this.checkList = new ArrayList<JCheckBox>();
		this.imageList = new ArrayList<PainLocationImagePanel>();
		
		//Add to checkList
		if (location.equals(PainLocationConstants.HEAD_BACK_GENERAL))
		{
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_BACK_BOTTOM_LEFT_TEXT), ImageManager.getImageIcon(ImageConstants.HEAD_BACK_BOTTOM_LEFT, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_BACK_BOTTOM_RIGHT_TEXT), ImageManager.getImageIcon(ImageConstants.HEAD_BACK_BOTTOM_RIGHT, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_BACK_CENTER_TEXT), ImageManager.getImageIcon(ImageConstants.HEAD_BACK_CENTER, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_BACK_TOP_LEFT_TEXT), ImageManager.getImageIcon(ImageConstants.HEAD_BACK_TOP_LEFT, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_BACK_TOP_RIGHT_TEXT), ImageManager.getImageIcon(ImageConstants.HEAD_BACK_TOP_RIGHT, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
		
			for (int i=0; i<this.imageList.size(); i++)
			{
				this.checkList.add(new JCheckBox());
			}
			
			//Set ID
			this.checkList.get(0).setName(PainLocationConstants.HEAD_BACK_BOTTOM_LEFT);
			this.checkList.get(1).setName(PainLocationConstants.HEAD_BACK_BOTTOM_RIGHT);
			this.checkList.get(2).setName(PainLocationConstants.HEAD_BACK_CENTER);
			this.checkList.get(3).setName(PainLocationConstants.HEAD_BACK_TOP_LEFT);
			this.checkList.get(4).setName(PainLocationConstants.HEAD_BACK_TOP_RIGHT);
		}
		else if (location.equals(PainLocationConstants.HEAD_FRONT_GENERAL))
		{
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_FRONT_CENTER_TEXT), ImageManager.getImageIcon(ImageConstants.HEAD_FRONT_CENTER, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_FRONT_LEFT_TEXT), ImageManager.getImageIcon(ImageConstants.HEAD_FRONT_LEFT, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_FRONT_RIGHT_TEXT), ImageManager.getImageIcon(ImageConstants.HEAD_FRONT_RIGHT, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
			
			for (int i=0; i<this.imageList.size(); i++)
			{
				this.checkList.add(new JCheckBox());
			}
			
			//Set ID
			this.checkList.get(0).setName(PainLocationConstants.HEAD_FRONT_CENTER);
			this.checkList.get(1).setName(PainLocationConstants.HEAD_FRONT_LEFT);
			this.checkList.get(2).setName(PainLocationConstants.HEAD_FRONT_RIGHT);
		}
		else if (location.equals(PainLocationConstants.HEAD_SIDE_LEFT_GENERAL))
		{
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_SIDE_BACK_TEXT), ImageManager.getImageIcon(ImageConstants.HEAD_LEFT_BACK, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_SIDE_CENTER_TEXT), ImageManager.getImageIcon(ImageConstants.HEAD_LEFT_CENTER, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_SIDE_FRONT_TEXT), ImageManager.getImageIcon(ImageConstants.HEAD_LEFT_FRONT, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
		
			for (int i=0; i<this.imageList.size(); i++)
			{
				this.checkList.add(new JCheckBox());
			}
			
			//Set ID
			this.checkList.get(0).setName(PainLocationConstants.HEAD_SIDE_LEFT_BACK);
			this.checkList.get(1).setName(PainLocationConstants.HEAD_SIDE_LEFT_CENTER);
			this.checkList.get(2).setName(PainLocationConstants.HEAD_SIDE_LEFT_FRONT);
		}
		else if (location.equals(PainLocationConstants.HEAD_SIDE_RIGHT_GENERAL))
		{
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_SIDE_BACK_TEXT), ImageManager.getImageIcon(ImageConstants.HEAD_RIGHT_BACK, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_SIDE_CENTER_TEXT), ImageManager.getImageIcon(ImageConstants.HEAD_RIGHT_CENTER, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_SIDE_FRONT_TEXT), ImageManager.getImageIcon(ImageConstants.HEAD_RIGHT_FRONT, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
		
			for (int i=0; i<this.imageList.size(); i++)
			{
				this.checkList.add(new JCheckBox());
			}
			
			//Set ID
			this.checkList.get(0).setName(PainLocationConstants.HEAD_SIDE_RIGHT_BACK);
			this.checkList.get(1).setName(PainLocationConstants.HEAD_SIDE_RIGHT_CENTER);
			this.checkList.get(2).setName(PainLocationConstants.HEAD_SIDE_RIGHT_FRONT);
		}
		else if (location.equals(PainLocationConstants.EYES_LEFT_GENERAL))
		{
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EYES_BEHIND_TEXT), ImageManager.getImageIcon(ImageConstants.EYES_LEFT_BEHIND, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EYES_EYEBALL_TEXT), ImageManager.getImageIcon(ImageConstants.EYES_LEFT_EYEBALL, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EYES_EYEBROW_TEXT), ImageManager.getImageIcon(ImageConstants.EYES_LEFT_EYEBROW, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EYES_SOCKET_TEXT), ImageManager.getImageIcon(ImageConstants.EYES_LEFT_SOCKET, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
		
			for (int i=0; i<this.imageList.size(); i++)
			{
				this.checkList.add(new JCheckBox());
			}
			
			//Set ID
			this.checkList.get(0).setName(PainLocationConstants.EYES_LEFT_BEHIND);
			this.checkList.get(1).setName(PainLocationConstants.EYES_LEFT_EYEBALL);
			this.checkList.get(2).setName(PainLocationConstants.EYES_LEFT_EYEBROW);
			this.checkList.get(3).setName(PainLocationConstants.EYES_LEFT_SOCKET);
		}
		else if (location.equals(PainLocationConstants.EYES_RIGHT_GENERAL))
		{
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EYES_BEHIND_TEXT), ImageManager.getImageIcon(ImageConstants.EYES_RIGHT_BEHIND, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EYES_EYEBALL_TEXT), ImageManager.getImageIcon(ImageConstants.EYES_RIGHT_EYEBALL, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EYES_EYEBROW_TEXT), ImageManager.getImageIcon(ImageConstants.EYES_RIGHT_EYEBROW, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EYES_SOCKET_TEXT), ImageManager.getImageIcon(ImageConstants.EYES_RIGHT_SOCKET, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
		
			for (int i=0; i<this.imageList.size(); i++)
			{
				this.checkList.add(new JCheckBox());
			}
			
			//Set ID
			this.checkList.get(0).setName(PainLocationConstants.EYES_RIGHT_BEHIND);
			this.checkList.get(1).setName(PainLocationConstants.EYES_RIGHT_EYEBALL);
			this.checkList.get(2).setName(PainLocationConstants.EYES_RIGHT_EYEBROW);
			this.checkList.get(3).setName(PainLocationConstants.EYES_RIGHT_SOCKET);
		}
		else if (location.equals(PainLocationConstants.EARS_LEFT_GENERAL))
		{
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EARS_HOLE_TEXT), ImageManager.getImageIcon(ImageConstants.EARS_LEFT_HOLE, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EARS_LOWER_TEXT), ImageManager.getImageIcon(ImageConstants.EARS_LEFT_LOWER, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EARS_UPPER_TEXT), ImageManager.getImageIcon(ImageConstants.EARS_LEFT_UPPER, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
		
			for (int i=0; i<this.imageList.size(); i++)
			{
				this.checkList.add(new JCheckBox());
			}
			
			//Set ID
			this.checkList.get(0).setName(PainLocationConstants.EARS_LEFT_HOLE);
			this.checkList.get(1).setName(PainLocationConstants.EARS_LEFT_LEAF_BOTTOM);
			this.checkList.get(2).setName(PainLocationConstants.EARS_LEFT_LEAF_TOP);
		}
		else if (location.endsWith(PainLocationConstants.EARS_RIGHT_GENERAL))
		{
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EARS_HOLE_TEXT), ImageManager.getImageIcon(ImageConstants.EARS_RIGHT_HOLE, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EARS_LOWER_TEXT), ImageManager.getImageIcon(ImageConstants.EARS_RIGHT_LOWER, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EARS_UPPER_TEXT), ImageManager.getImageIcon(ImageConstants.EARS_RIGHT_UPPER, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
		
			for (int i=0; i<this.imageList.size(); i++)
			{
				this.checkList.add(new JCheckBox());
			}
			
			//Set ID
			this.checkList.get(0).setName(PainLocationConstants.EARS_RIGHT_HOLE);
			this.checkList.get(1).setName(PainLocationConstants.EARS_RIGHT_LEAF_BOTTOM);
			this.checkList.get(2).setName(PainLocationConstants.EARS_RIGHT_LEAF_TOP);
		}
		else if (location.equals(PainLocationConstants.CHEEKS_LEFT_GENERAL))
		{
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_CHEEKS_LOWER_TEXT), ImageManager.getImageIcon(ImageConstants.CHEEKS_LEFT_LOWER, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_CHEEKS_UPPER_TEXT), ImageManager.getImageIcon(ImageConstants.CHEEKS_LEFT_UPPER, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
		
			for (int i=0; i<this.imageList.size(); i++)
			{
				this.checkList.add(new JCheckBox());
			}
			
			//Set ID
			this.checkList.get(0).setName(PainLocationConstants.CHEEKS_LEFT_LOWER);
			this.checkList.get(1).setName(PainLocationConstants.CHEEKS_LEFT_UPPER);
		}
		else if (location.equals(PainLocationConstants.CHEEKS_RIGHT_GENERAL))
		{
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_CHEEKS_LOWER_TEXT), ImageManager.getImageIcon(ImageConstants.CHEEKS_RIGHT_LOWER, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_CHEEKS_UPPER_TEXT), ImageManager.getImageIcon(ImageConstants.CHEEKS_RIGHT_UPPER, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
		
			for (int i=0; i<this.imageList.size(); i++)
			{
				this.checkList.add(new JCheckBox());
			}
			
			//Set ID
			this.checkList.get(0).setName(PainLocationConstants.CHEEKS_RIGHT_LOWER);
			this.checkList.get(1).setName(PainLocationConstants.CHEEKS_RIGHT_UPPER);
		}
		
		//Properties
		for (int i=0; i<this.checkList.size(); i++)			//Only allow one selection
		{
			this.group.add(this.checkList.get(i));
			this.checkList.get(i).addItemListener(this);
		}
		this.setOpaque(false);
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		//add to panel
		this.add(this.labSelectPos);
		for (int i=0; i<this.checkList.size(); i++)
		{
			this.add(this.checkList.get(i));
			this.add(this.imageList.get(i));
		}
	}
	
	protected String getSelected()
	{
		return this.selectedPosition;
	}
	protected void setSelected(String location)
	{
		for (int i=0; i<this.checkList.size(); i++)
		{
			if (this.checkList.get(i).getName().equals(location))
			{
				this.checkList.get(i).setSelected(true);
			}
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		JCheckBox cb = (JCheckBox)e.getSource();
		this.selectedPosition = cb.getName();
	}
}
