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

public class PainLocationSelectionPanelGeneral extends JPanel implements ItemListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6036032175397726741L;
	private JLabel labSelectPos;
	private ButtonGroup group;
	private List<JCheckBox> checkList;
	private List<PainLocationImagePanel> imageList;
	private String selectedPosition;
	
	protected PainLocationSelectionPanelGeneral(String location)
	{
		this.init(location);
		this.checkList.get(0).setSelected(true);
		this.selectedPosition = this.checkList.get(0).getName();
	}
	
	private void init(String location)
	{
		//Initialization
		this.labSelectPos = new JLabel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SELECT_LOCATION_LABEL) + ":");
		this.group = new ButtonGroup();
		this.checkList = new ArrayList<JCheckBox>();
		this.imageList = new ArrayList<PainLocationImagePanel>();
		
		//add to checkList and imageList
		if (location.equals(PainLocationConstants.HEAD))
		{
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SPECIFIC_POSITION_HEAD_BACK_TEXT), ImageManager.getImageIcon(ImageConstants.HEAD_BACK_GENERAL, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SPECIFIC_POSITION_HEAD_FRONT_TEXT), ImageManager.getImageIcon(ImageConstants.HEAD_FRONT_GENERAL, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SPECIFIC_POSITION_HEAD_LEFT_TEXT), ImageManager.getImageIcon(ImageConstants.HEAD_LEFT_GENERAL, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SPECIFIC_POSITION_HEAD_RIGHT_TEXT), ImageManager.getImageIcon(ImageConstants.HEAD_RIGHT_GENERAL, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
			
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
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SPECIFIC_POSITION_EARS_LEFT_TEXT), ImageManager.getImageIcon(ImageConstants.EARS_LEFT_GENERAL, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SPECIFIC_POSITION_EARS_RIGHT_TEXT), ImageManager.getImageIcon(ImageConstants.EARS_RIGHT_GENERAL, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
		
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
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SPECIFIC_POSITION_EYES_LEFT_TEXT), ImageManager.getImageIcon(ImageConstants.EYES_LEFT_GENERAL, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SPECIFIC_POSITION_EYES_RIGHT_TEXT), ImageManager.getImageIcon(ImageConstants.EYES_RIGHT_GENERAL, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
		
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
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SPECIFIC_POSITION_CHEEKS_LEFT_TEXT), ImageManager.getImageIcon(ImageConstants.CHEEKS_LEFT_GENERAL, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
			this.imageList.add(new PainLocationImagePanel(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SPECIFIC_POSITION_CHEEKS_RIGHT_TEXT), ImageManager.getImageIcon(ImageConstants.CHEEKS_RIGHT_GENERAL, Constants.IMAGE_SIZE.width, Constants.IMAGE_SIZE.height)));
		
			for (int i=0; i<this.imageList.size(); i++)
			{
				this.checkList.add(new JCheckBox());
			}
			
			//Set ID
			this.checkList.get(0).setName(PainLocationConstants.CHEEKS_LEFT_GENERAL);
			this.checkList.get(1).setName(PainLocationConstants.CHEEKS_RIGHT_GENERAL);
		}
		
		//Properties
		for (int i=0; i<this.checkList.size(); i++)			//Only allow one selection at a time
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
