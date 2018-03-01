package diary.gui.EntryLog;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import diary.constants.Constants;
import diary.gui.ImageTextPanel;
import giantsweetroll.ImageManager;

public class PainLocationPresetSelectionPanel extends JPanel implements ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086464692168288860L;
	
	private List<JButton> buttons;
	private String selectedPos;
	
	//Constructors
	public PainLocationPresetSelectionPanel()
	{
		//Initialization
		this.initButtons();
		
		//Properties
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		this.setOpaque(false);
		
		//Add to panel
		for(JButton button : this.buttons)
		{
			this.add(button);
		}
	}
	
	//Methods
	private void initButtons()
	{
		this.buttons = new ArrayList<JButton>();
		
		for(Map.Entry<String, LinkedHashMap<URL, String>> entry : Constants.PAIN_LOCATIONS.entrySet())
		{
			for (Map.Entry<URL, String> subEntry : entry.getValue().entrySet())
			{
				ImageTextPanel imagePanel = new ImageTextPanel(ImageManager.getImageIcon(subEntry.getKey()), entry.getKey(), 20);
				JButton button = new JButton();
				button.add(imagePanel);
				button.setActionCommand(subEntry.getValue());
				this.buttons.add(button);
			}
		}
	}
	private void unmarkAllButtons()
	{
		for (JButton button : this.buttons)
		{
			button.setBorder(null);
		}
	}
	private void setMarked(JButton button, boolean marked)
	{
		button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 20));
	}
	public String getSelectedPosition()
	{
		return this.selectedPos;
	}
	public void enableButtons(boolean enable)
	{
		for (JButton button : this.buttons)
		{
			button.setEnabled(enable);
		}
	}
	public void setSelected(String painLocationConstant)		//Parameter is String from PainLocationConstants
	{
		for (JButton button : this.buttons)
		{
			if (button.getActionCommand().equals(painLocationConstant))
			{
				this.setMarked(button, true);
				this.selectedPos = painLocationConstant;
				break;
			}
		}
	}
	
	//Interfaces
	public void actionPerformed(ActionEvent e)
	{
		this.selectedPos = e.getActionCommand();
		this.unmarkAllButtons();
		this.setMarked((JButton)e.getSource(), true);
	}
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		frame.add(new JScrollPane(new PainLocationPresetSelectionPanel()));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 300);
		frame.setVisible(true);
	}
}
