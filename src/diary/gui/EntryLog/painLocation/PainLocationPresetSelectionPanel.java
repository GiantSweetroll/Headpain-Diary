package diary.gui.EntryLog.painLocation;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

import diary.constants.Constants;
import diary.gui.ImageTextPanel;
import diary.interfaces.GUIFunction;
import diary.interfaces.LanguageListener;
import diary.methods.Methods;
import giantsweetroll.ImageManager;

public class PainLocationPresetSelectionPanel extends JPanel implements ActionListener, GUIFunction, LanguageListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086464692168288860L;
	private List<JButton> buttons;
	private List<String> selectedPos;
	private Border defaultBorder;
	
	//Constructors
	public PainLocationPresetSelectionPanel()
	{
		//Initialization
		this.initButtons();
		this.selectedPos = new ArrayList<String>();
		
		//Properties
		this.setLayout(new GridLayout(0,3, 10, 10));
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
		try
		{
			this.buttons.clear();
		}
		catch(NullPointerException ex)
		{
			this.buttons = new ArrayList<JButton>();
		}
		
		for(Map.Entry<String, LinkedHashMap<URL, String>> entry : Methods.generatePainLocationsTextURLMap().entrySet())
		{
			for (Map.Entry<URL, String> subEntry : entry.getValue().entrySet())
			{
				ImageIcon image = ImageManager.getImageIcon(subEntry.getKey());
				//ImageTextPanel imagePanel = new ImageTextPanel(image, entry.getKey(), Methods.getPercentage(image, Methods.getPercentageValue(MainFrame.frame.getWidth(), 10)));
				ImageTextPanel imagePanel = new ImageTextPanel(image, entry.getKey(), Methods.getPercentage(image, Methods.getPercentageValue(Constants.SCREENSIZE.width, 5)));
				JButton button = new JButton();
				button.add(imagePanel);
				button.setActionCommand(subEntry.getValue());
				button.addActionListener(this);
				button.setBackground(Constants.COLOR_HIGHLIGHT_BUTTON);
				button.setOpaque(false);
				this.defaultBorder = button.getBorder();
				this.buttons.add(button);
			}
		}
	}
	public void unmarkAllButtons()
	{
		for (JButton button : this.buttons)
		{
			button.setBorder(this.defaultBorder);
			button.setOpaque(false);
		}
	}
	private void setMarked(JButton button, boolean mark)
	{
		if (mark)
		{
			button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
			button.setOpaque(true);
		}
		else
		{
			button.setBorder(this.defaultBorder);
			button.setOpaque(false);
		}
	}
	public List<String> getSelectedPosition()
	{
		return this.selectedPos;
	}
	public boolean isMarked(JButton button)
	{
		if (button.getBorder() == this.defaultBorder)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	public void enableButtons(boolean enable)
	{
		for (JButton button : this.buttons)
		{
			button.setEnabled(enable);
			for(Component component : button.getComponents())
			{
				component.setEnabled(enable);
			}
		}
	}
	public void setSelected(String painLocationConstant)		//Parameter is String from PainLocationConstants
	{
		for (JButton button : this.buttons)
		{
			if (button.getActionCommand().equals(painLocationConstant))
			{
				this.setMarked(button, true);
				this.appendLocation(painLocationConstant);
				break;
			}
		}
	}
	public void appendLocation(String painLocationConstant)		//Parameter is String from PainLocationConstants
	{
		if (!Methods.elementExists(this.selectedPos, painLocationConstant, false));
		{
			this.selectedPos.add(painLocationConstant);
		}
	}
	
	//Overridden Methods
	@Override
	public void setEnabled(boolean enabled)
	{
		super.setEnabled(enabled);
		
		this.enableButtons(enabled);
		if (!enabled)
		{
			this.unmarkAllButtons();
		}
	}
	@Override
	public void refresh() {};
	
	//Interfaces
	public void actionPerformed(ActionEvent e)
	{
		JButton button = (JButton)e.getSource();
		if (this.isMarked(button))
		{
			this.setMarked(button, false);
			Methods.deleteElement(selectedPos, e.getActionCommand(), false);
		}
		else
		{
			this.setMarked(button, true);
			this.appendLocation(e.getActionCommand());
		}
	}

	public void resetDefaults() 
	{
		this.selectedPos.clear();
	}

	@Override
	public void revalidateLanguage() 
	{
		for (JButton button : this.buttons)
		{
			this.remove(button);
		}
		
		this.initButtons();
		
		for (JButton button: this.buttons)
		{
			this.add(button);
		}
		this.revalidate();
		this.refresh();
	}
}
