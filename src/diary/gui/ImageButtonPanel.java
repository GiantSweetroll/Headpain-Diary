package diary.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import diary.constants.Constants;
import diary.constants.ImageConstants;

public class ImageButtonPanel extends JScrollPane implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2743039689050616980L;

	private JPanel panel;
	
	private JLabel labSelectPosition;
	private ArrayList<JButton> imageButtonList;
	
	//Constants
	//Identifiers
	protected static final byte GENERAL_SELECTION = -6;			//For use with head, eyes, ears, cheeks, and chin constants
	protected static final byte SPECIFIC_SELECTION = -7;			//For use with the other constants
	protected static final byte HEAD = -1;
	protected static final byte EYES = -2;
	protected static final byte EARS = -3;
	protected static final byte CHEEKS = -4;
	protected static final byte CHIN = -5;
	//Head
	protected static final byte HEAD_FRONT = 0;
	protected static final byte HEAD_LEFT = 1;
	protected static final byte HEAD_RIGHT = 2;
	protected static final byte HEAD_BACK = 3;
	protected static final byte HEAD_FRONT_LEFT = 4;
	protected static final byte HEAD_FRONT_CENTER = 5;
	protected static final byte HEAD_FRONT_RIGHT = 6;
	//Eyes
	protected static final byte EYES_RIGHT = 7;
	protected static final byte EYES_LEFT = 8;
	protected static final byte EYES_RIGHT_BROW = 9;
	protected static final byte EYES_RIGHT_EYELASH = 10;
	protected static final byte EYES_RIGHT_SOCKET = 11;
	protected static final byte EYES_RIGHT_EYEBALL = 12;
	protected static final byte EYES_RIGHT_BEHIND = 13;
	protected static final byte EYES_LEFT_BROW = 14;
	protected static final byte EYES_LEFT_EYELASH = 15;
	protected static final byte EYES_LEFT_SOCKET = 16;
	protected static final byte EYES_LEFT_EYEBALL = 17;
	protected static final byte EYES_LEFT_BEHIND = 18;
	//Ears
	protected static final byte EARS_LEFT = 19;
	protected static final byte EARS_RIGHT = 20;
	protected static final byte EARS_LEFT_HOLE = 21;
	protected static final byte EARS_RIGHT_HOLE = 22;
	protected static final byte EARS_LEFT_LEAF = 23;
	protected static final byte EARS_RIGHT_LEAF = 24;
	
	private String selected;
	
	protected ImageButtonPanel(byte selection, byte selection2)
	{
		this.init(selection, selection2);
	}
	
	//Methods
	private void init(byte selection, byte selection2)
	{
		//Initialization
		this.initPanel(selection2);
		
		//Properties
		this.setViewportView(this.panel);
		this.getViewport().setOpaque(false);
		this.setOpaque(false);
	}
	private void initPanel(byte selection2)
	{
		//Initialization
		this.panel = new JPanel();
		this.imageButtonList = this.getImages(selection2);
		this.labSelectPosition = new JLabel(Constants.LANGUAGE.selectLocationLabel, SwingConstants.RIGHT);
		
		//Properties
		this.panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.panel.setOpaque(false);
		
		//Add to panel
		this.panel.add(this.labSelectPosition);				//Select Position
		for (int i=0; i<this.imageButtonList.size(); i++)
		{
			this.panel.add(this.imageButtonList.get(i));
		}
	}
	
	private ArrayList<JButton> getImages(byte selection2)
	{
		ArrayList<JButton> list = new ArrayList<JButton>();
		
		if (selection2 == this.HEAD)
		{
			list.add(new JButton(new ImageIcon(ImageConstants.HEAD_BACK)));
			list.get(0).setActionCommand(Byte.toString(this.HEAD_BACK));
			list.add(new JButton(new ImageIcon(ImageConstants.HEAD_FRONT)));
			list.get(1).setActionCommand(Byte.toString(this.HEAD_FRONT));
			list.add(new JButton(new ImageIcon(ImageConstants.HEAD_LEFT)));
			list.get(2).setActionCommand(Byte.toString(this.HEAD_LEFT));
			list.add(new JButton(new ImageIcon(ImageConstants.HEAD_RIGHT)));
			list.get(3).setActionCommand(Byte.toString(this.HEAD_RIGHT));
		}
		
		return list;
	}
	
	private void refresh()
	{
		this.revalidate();
		this.repaint();
	}
	
	protected String getData()
	{
		return this.selected;
	}
	private void setData(byte data)
	{
		
	}
	
	//Interface
	public void actionPerformed(ActionEvent e)
	{
		this.setData(Byte.parseByte(e.getActionCommand()));
	}
}
