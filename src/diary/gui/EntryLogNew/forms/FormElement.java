package diary.gui.EntryLogNew.forms;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import diary.constants.Constants;
import diary.gui.WrappableJLabel;
import diary.interfaces.GUIFunction;

public abstract class FormElement extends JPanel implements GUIFunction
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -94968805497961745L;
	
	private JLabel labName;
	private WrappableJLabel info;
	private GridBagConstraints c;
	private JPanel panelBelow, panelCenter;
	
	//Constructors
	public FormElement(String name, String info)
	{
		this.info = new WrappableJLabel(info);
		this.init(name);
	}
	public FormElement(String name)
	{
		this.info = new WrappableJLabel("");
		this.init(name);
	}
	
	//Initialize GUI
	private void init(String name)
	{
		//Initialization
		this.labName = new JLabel(name);
		this.initPanelCenter();
		this.initPanelBelow();
		
		//Properties
		this.setLayout(new BorderLayout());
		this.setOpaque(false);
		
		//Add to panel
		this.add(this.panelBelow, BorderLayout.SOUTH);
		this.add(this.panelCenter, BorderLayout.CENTER);
	}
	private void initPanelCenter()
	{
		//Initialization
		this.panelCenter = new JPanel();;
		
		//Properties
		this.panelCenter.setLayout(new GridBagLayout());
		this.panelCenter.setOpaque(false);
		
		//Add to panel
		c.insets = Constants.INSETS_TOP_COMPONENT;
		c.gridwidth = GridBagConstraints.REMAINDER;
		this.panelCenter.add(this.labName, c);
		c.insets = Constants.INSETS_GENERAL;
	}
	private void initPanelBelow()
	{
		//Initialization
		this.panelBelow = new JPanel();
		
		//Properties
		this.panelBelow.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.panelBelow.setOpaque(false);
		
		//Add to panel
		this.panelBelow.add(this.info);
	}

	//Methods
	public void addComponent(JComponent component)
	{
		this.panelCenter.add(component, c);
	}
	public abstract Object getData();
	public abstract void setData(Object obj);
}
