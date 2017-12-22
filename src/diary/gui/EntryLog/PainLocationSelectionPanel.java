package diary.gui.EntryLog;

import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import diary.constants.Constants;
import diary.constants.ImageConstants;
import diary.constants.PainLocationConstants;
import diary.constants.XMLIdentifier;

public class PainLocationSelectionPanel extends JPanel implements ItemListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8670810287480715132L;
	private JLabel labSelectPos;
	private ButtonGroup group;
	private List<JCheckBox> checkList;
	private List<PainLocationImagePanel> imageList;
	private String selectedPosition;
	private PainLocationSelectionPanel linkedPanel;
	
	public PainLocationSelectionPanel(String location)
	{
		this.init(location);
		this.checkList.get(0).setSelected(true);
		this.selectedPosition = this.checkList.get(0).getName();
	}
	
	private void init(String location)
	{
		//Initialization
		this.labSelectPos = new JLabel (Constants.REQUIRED_IDENTIFIER + Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SELECT_LOCATION_LABEL) + ":");
		this.group = new ButtonGroup();
		this.checkList = new ArrayList<JCheckBox>();
		this.imageList = new ArrayList<PainLocationImagePanel>();
		
		this.gatherImage(location);
		
		//Properties
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
	
	public void changeLocation(String location)
	{
		//Remove all images from panel
		for (int i=0; i<this.checkList.size(); i++)
		{
			this.remove(this.checkList.get(i));
			this.remove(this.imageList.get(i));
		}
		
		//Prepare images
		this.gatherImage(location);
		
		//add back to panel
		for (int i=0; i<this.checkList.size(); i++)
		{
			this.add(this.checkList.get(i));
			this.add(this.imageList.get(i));
		}
		
		this.revalidate();
		this.repaint();
	}
	public void changeLocation(PainLocationSelectionPanel panel, String location)
	{
		//Remove all images from panel
		for (int i=0; i<panel.checkList.size(); i++)
		{
			panel.remove(panel.checkList.get(i));
			panel.remove(panel.imageList.get(i));
		}
		
		//Prepare images
		panel.gatherImage(location);
		
		//add back to panel
		for (int i=0; i<panel.checkList.size(); i++)
		{
			panel.add(panel.checkList.get(i));
			panel.add(panel.imageList.get(i));
		}
		
		panel.revalidate();
		panel.repaint();
	}
	
	private void gatherImage(String location)
	{
		this.checkList.clear();
		this.imageList.clear();
		
		if (location.equals(PainLocationConstants.HEAD))
		{
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SPECIFIC_POSITION_HEAD_BACK_TEXT), ImageConstants.HEAD_BACK_GENERAL));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SPECIFIC_POSITION_HEAD_FRONT_TEXT), ImageConstants.HEAD_FRONT_GENERAL));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SPECIFIC_POSITION_HEAD_LEFT_TEXT), ImageConstants.HEAD_LEFT_GENERAL));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SPECIFIC_POSITION_HEAD_RIGHT_TEXT), ImageConstants.HEAD_RIGHT_GENERAL));
			
			for (int i=0; i<this.imageList.size(); i++)
			{
				this.checkList.add(new JCheckBox());
			}
			
			//Set ID
			this.checkList.get(0).setName(PainLocationConstants.HEAD_BACK_GENERAL);
			this.checkList.get(1).setName(PainLocationConstants.HEAD_FRONT_GENERAL);
			this.checkList.get(2).setName(PainLocationConstants.HEAD_SIDE_LEFT_GENERAL);
			this.checkList.get(3).setName(PainLocationConstants.HEAD_SIDE_RIGHT_GENERAL);
		}
		else if (location.equals(PainLocationConstants.EARS))
		{
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SPECIFIC_POSITION_EARS_LEFT_TEXT), ImageConstants.EARS_LEFT_GENERAL));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SPECIFIC_POSITION_EARS_RIGHT_TEXT), ImageConstants.EARS_RIGHT_GENERAL));
		
			for (int i=0; i<this.imageList.size(); i++)
			{
				this.checkList.add(new JCheckBox());
			}
			
			//Set ID
			this.checkList.get(0).setName(PainLocationConstants.EARS_LEFT_GENERAL);
			this.checkList.get(1).setName(PainLocationConstants.EARS_RIGHT_GENERAL);
		}
		else if (location.equals(PainLocationConstants.EYES))
		{
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SPECIFIC_POSITION_EYES_LEFT_TEXT), ImageConstants.EYES_LEFT_GENERAL));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SPECIFIC_POSITION_EYES_RIGHT_TEXT), ImageConstants.EYES_RIGHT_GENERAL));
		
			for (int i=0; i<this.imageList.size(); i++)
			{
				this.checkList.add(new JCheckBox());
			}
			
			//Set ID
			this.checkList.get(0).setName(PainLocationConstants.EYES_LEFT_GENERAL);
			this.checkList.get(1).setName(PainLocationConstants.EYES_RIGHT_GENERAL);
		}
		else if (location.equals(PainLocationConstants.CHEEKS))
		{
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SPECIFIC_POSITION_CHEEKS_LEFT_TEXT), ImageConstants.CHEEKS_LEFT_GENERAL));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SPECIFIC_POSITION_CHEEKS_RIGHT_TEXT), ImageConstants.CHEEKS_RIGHT_GENERAL));
		
			for (int i=0; i<this.imageList.size(); i++)
			{
				this.checkList.add(new JCheckBox());
			}
			
			//Set ID
			this.checkList.get(0).setName(PainLocationConstants.CHEEKS_LEFT_GENERAL);
			this.checkList.get(1).setName(PainLocationConstants.CHEEKS_RIGHT_GENERAL);
		}
		else if (location.equals(PainLocationConstants.HEAD_BACK_GENERAL))
		{
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_BACK_BOTTOM_LEFT_TEXT), ImageConstants.HEAD_BACK_BOTTOM_LEFT));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_BACK_BOTTOM_RIGHT_TEXT), ImageConstants.HEAD_BACK_BOTTOM_RIGHT));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_BACK_CENTER_TEXT), ImageConstants.HEAD_BACK_CENTER));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_BACK_TOP_LEFT_TEXT), ImageConstants.HEAD_BACK_TOP_LEFT));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_BACK_TOP_RIGHT_TEXT), ImageConstants.HEAD_BACK_TOP_RIGHT));
		
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
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_FRONT_CENTER_TEXT), ImageConstants.HEAD_FRONT_CENTER));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_FRONT_LEFT_TEXT), ImageConstants.HEAD_FRONT_LEFT));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_FRONT_RIGHT_TEXT), ImageConstants.HEAD_FRONT_RIGHT));
			
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
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_SIDE_BACK_TEXT), ImageConstants.HEAD_LEFT_BACK));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_SIDE_CENTER_TEXT), ImageConstants.HEAD_LEFT_CENTER));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_SIDE_FRONT_TEXT), ImageConstants.HEAD_LEFT_FRONT));
		
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
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_SIDE_BACK_TEXT), ImageConstants.HEAD_RIGHT_BACK));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_SIDE_CENTER_TEXT), ImageConstants.HEAD_RIGHT_CENTER));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_SIDE_FRONT_TEXT), ImageConstants.HEAD_RIGHT_FRONT));
		
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
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EYES_BEHIND_TEXT), ImageConstants.EYES_LEFT_BEHIND));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EYES_EYEBALL_TEXT), ImageConstants.EYES_LEFT_EYEBALL));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EYES_EYEBROW_TEXT), ImageConstants.EYES_LEFT_EYEBROW));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EYES_SOCKET_TEXT), ImageConstants.EYES_LEFT_SOCKET));
		
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
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EYES_BEHIND_TEXT), ImageConstants.EYES_RIGHT_BEHIND));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EYES_EYEBALL_TEXT), ImageConstants.EYES_RIGHT_EYEBALL));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EYES_EYEBROW_TEXT), ImageConstants.EYES_RIGHT_EYEBROW));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EYES_SOCKET_TEXT), ImageConstants.EYES_RIGHT_SOCKET));
		
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
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EARS_HOLE_TEXT), ImageConstants.EARS_LEFT_HOLE));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EARS_LOWER_TEXT), ImageConstants.EARS_LEFT_LOWER));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EARS_UPPER_TEXT), ImageConstants.EARS_LEFT_UPPER));
		
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
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EARS_HOLE_TEXT), ImageConstants.EARS_RIGHT_HOLE));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EARS_LOWER_TEXT), ImageConstants.EARS_RIGHT_LOWER));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_EARS_UPPER_TEXT), ImageConstants.EARS_RIGHT_UPPER));
		
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
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_CHEEKS_LOWER_TEXT), ImageConstants.CHEEKS_LEFT_LOWER));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_CHEEKS_UPPER_TEXT), ImageConstants.CHEEKS_LEFT_UPPER));
		
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
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_CHEEKS_LOWER_TEXT), ImageConstants.CHEEKS_RIGHT_LOWER));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.VERY_SPECIFIC_POSITION_CHEEKS_UPPER_TEXT), ImageConstants.CHEEKS_RIGHT_UPPER));
		
			for (int i=0; i<this.imageList.size(); i++)
			{
				this.checkList.add(new JCheckBox());
			}
			
			//Set ID
			this.checkList.get(0).setName(PainLocationConstants.CHEEKS_RIGHT_LOWER);
			this.checkList.get(1).setName(PainLocationConstants.CHEEKS_RIGHT_UPPER);
		}
		
		//Properties
		for (int i=0; i<this.checkList.size(); i++)			
		{
			this.group.add(this.checkList.get(i));			//Only allow one selection
			this.checkList.get(i).addItemListener(this);	//Add Item Listener to each
		}
		this.checkList.get(0).setSelected(true);
		this.setSelected(this.checkList.get(0).getName());
	}
	
	public String getSelected()
	{
		return this.selectedPosition;
	}
	public void setSelected(String location)
	{
		for (int i=0; i<this.checkList.size(); i++)
		{
			if (this.checkList.get(i).getName().equals(location))
			{
				this.checkList.get(i).setSelected(true);
			}
		}
	}
	
	public void addLinkedPanel(PainLocationSelectionPanel panel)
	{
		this.linkedPanel = panel;
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		JCheckBox cb = (JCheckBox)e.getSource();
		this.selectedPosition = cb.getName();
		try
		{
			this.changeLocation(this.linkedPanel, this.selectedPosition);
		}
		catch(NullPointerException ex) {};
	}
}
