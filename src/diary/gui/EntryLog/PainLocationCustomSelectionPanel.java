package diary.gui.EntryLog;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import diary.constants.XMLIdentifier;
import diary.methods.Methods;

public class PainLocationCustomSelectionPanel extends JPanel implements ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6306460492486489780L;
	
	private PainLocationCustomSelection image;
	private JPanel panelDirection;
	private JButton butReset;
	private JLabel labLeft, labRight;
	
	//Constants
	private final int BORDER_THICKNESS = 3;
	public static final String CUSTOM_PREFIX = "custom";
	
	//Constructors
	public PainLocationCustomSelectionPanel(PainLocationCustomSelection image)
	{
		//Initialization
		this.image = image;
		this.butReset = new JButton(Methods.getLanguageText(XMLIdentifier.RESET_TEXT));
		this.initPanelDirection();
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties	
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		this.butReset.addActionListener(this);
		if (this.isEnabled())
		{
			this.image.setBorder(BorderFactory.createLineBorder(Color.BLACK, this.BORDER_THICKNESS, true));
		}
		else
		{
			this.image.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
		}
		
		//Add to panel
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.BOTH;
		this.add(this.image, c);
		this.add(this.panelDirection, c);
		this.add(this.butReset, c);
	}
	
	//Methods
	private void initPanelDirection()
	{
		//Initialization
		this.panelDirection = new JPanel();
		this.labLeft = new JLabel(Methods.getLanguageText(XMLIdentifier.YOUR_LEFT_TEXT), SwingConstants.CENTER);
		this.labRight = new JLabel(Methods.getLanguageText(XMLIdentifier.YOUR_RIGHT_TEXT), SwingConstants.CENTER);
		
		//Properties
		this.panelDirection.setLayout(new GridLayout(1, 2));
		this.panelDirection.setOpaque(false);
		
		//Add to panel
		this.panelDirection.add(this.labLeft);
		this.panelDirection.add(this.labRight);
	}
	
	//Overriden Methods
	@Override
	public void setEnabled(boolean enabled)
	{
		super.setEnabled(enabled);
		
		this.image.setEnabled(enabled);
		this.butReset.setEnabled(enabled);
		this.labLeft.setEnabled(enabled);
		this.labRight.setEnabled(enabled);
		if (enabled)
		{
			this.image.setBorder(BorderFactory.createLineBorder(Color.BLACK, this.BORDER_THICKNESS, true));
		}
		else
		{
			this.image.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
		}
	}
	
	//Interfaces
	public void actionPerformed(ActionEvent e)
	{
		image.resetCoordinates();
	}
}
