package diary.gui.EntryLog.forms;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import diary.constants.Constants;
import diary.gui.WrappableJLabel;
import diary.interfaces.GUIFunction;

public abstract class FormElement extends JScrollPane implements GUIFunction
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -94968805497961745L;
	
	private JLabel labName;
	private WrappableJLabel info;
	private GridBagConstraints c;
	private JPanel panel, panelBelow, panelCenter;
	
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
		this.panel = new JPanel();
		this.labName = new JLabel(name);
		this.initPanelCenter();
		this.initPanelBelow();
		
		//Properties
		this.panel.setLayout(new BorderLayout());
		this.panel.setOpaque(false);
		this.setViewportView(this.panel);
		this.getVerticalScrollBar().setUnitIncrement(10);
		this.getHorizontalScrollBar().setUnitIncrement(10);
		this.getViewport().setOpaque(false);
		this.setOpaque(false);
		this.labName.setFont(Constants.FONT_HEADER);
		
		//Add to panel
		this.panel.add(this.panelBelow, BorderLayout.SOUTH);
		this.panel.add(this.panelCenter, BorderLayout.CENTER);
	}
	private void initPanelCenter()
	{
		//Initialization
		this.panelCenter = new JPanel();
		this.c = new GridBagConstraints();
		
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
		this.panelBelow.setLayout(new FlowLayout(FlowLayout.CENTER));
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
