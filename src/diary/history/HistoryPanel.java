package diary.history;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import diary.constants.Constants;
import diary.methods.Methods;
import giantsweetroll.gui.swing.Gbm;

public class HistoryPanel extends JPanel implements ItemListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2435079856803345604L;

	private JRadioButton radHistory, radNew;
	private ButtonGroup group;
	private JComboBox<String> comboHistory;
	private JTextField tfHistory;
	private History history;
	
	//Constructors
	public HistoryPanel(History history)
	{
		this.history = history;
		this.init();
		this.comboHistory.setModel(new DefaultComboBoxModel(history.getHistory().toArray(new String[history.getHistory().size()])));
		
		if (history.getHistory().size() > 0)
		{
			this.radHistory.setSelected(true);
		}
	}
	
	//Methods
	private void init()
	{
		//Initialization
		this.radHistory = new JRadioButton();
		this.radNew =  new JRadioButton();
		this.comboHistory = new JComboBox<String>();
		this.tfHistory = new JTextField(10);
		this.group = new ButtonGroup();
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		this.group.add(this.radHistory);
		this.group.add(this.radNew);
		this.radHistory.addItemListener(this);
		this.radHistory.setOpaque(false);
		this.radNew.addItemListener(this);
		this.radNew.setSelected(true);
		this.radNew.setOpaque(false);
		this.comboHistory.setBackground(Color.WHITE);
		this.radHistory.setEnabled(this.hasHistory());
		this.tfHistory.setOpaque(false);
		this.tfHistory.addMouseListener(new MouseListener()
				{

					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mousePressed(MouseEvent e) 
					{
						if (!tfHistory.isEditable())
						{
							tfHistory.setEditable(true);
							radNew.setSelected(true);
						}
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
			
				});
		this.comboHistory.addMouseListener(new MouseListener()
				{

					@Override
					public void mouseClicked(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseEntered(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseExited(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mousePressed(MouseEvent e) 
					{
						if (hasHistory() && !comboHistory.isEnabled())
						{
							comboHistory.setEnabled(true);
							radHistory.setSelected(true);
						}
					}

					@Override
					public void mouseReleased(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}
			
				});
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_TOP_COMPONENT;
		c.fill = GridBagConstraints.BOTH;
		this.add(this.radHistory, c);			//History Radio Button
		Gbm.nextGridColumn(c);
		this.add(this.comboHistory, c);			//History Options
		Gbm.newGridLine(c);
		c.insets = Constants.INSETS_GENERAL;
		this.add(this.radNew, c);				//New History Radio Button
		Gbm.nextGridColumn(c);
		this.add(this.tfHistory, c);			//New History Text Field
	}
	public void setActiveItem(String item)
	{
		if (this.history.itemExists(item))
		{
			this.radHistory.setSelected(true);
			this.comboHistory.setSelectedItem(item);
		}
		else
		{
			this.radNew.setSelected(true);
			this.tfHistory.setText(item);
		}
	}
	public String getItem()
	{
		if (this.radHistory.isSelected())
		{
			try
			{
				return this.comboHistory.getSelectedItem().toString();
			}
			catch(NullPointerException ex)
			{
				return Methods.getTextData(this.tfHistory);
			}
		}
		else
		{
			return Methods.getTextData(this.tfHistory);
		}
	}
	public void resetDefaults()
	{
		if(this.hasHistory())
		{
			this.radHistory.setSelected(true);
		}
		else
		{
			this.radNew.setSelected(true);
		}
		try
		{
			this.comboHistory.setSelectedIndex(0);
		}
		catch(IllegalArgumentException ex) {}
	}
	public void refresh()
	{
		this.comboHistory.setModel(new DefaultComboBoxModel(history.getHistory().toArray(new String[history.getHistory().size()])));
		this.radHistory.setEnabled(this.hasHistory());
		this.revalidate();
		this.repaint();
	}
	public boolean hasHistory()
	{
		return this.comboHistory.getItemCount()!=0;
	}
	
	//Overriden Methods
	@Override
	public void setEnabled(boolean enabled)
	{
		super.setEnabled(enabled);
		
		this.radHistory.setEnabled(enabled);
		this.radNew.setEnabled(enabled);
		this.comboHistory.setEnabled(enabled);
		this.tfHistory.setEnabled(enabled);
	}
	
	//Interfaces
	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		if (this.radHistory.isSelected())
		{
			this.tfHistory.setText("");
			this.tfHistory.setEditable(false);
			this.comboHistory.setEnabled(true);
		}
		else
		{
			this.tfHistory.setEditable(true);
			this.comboHistory.setEnabled(false);
		}
	}
}
