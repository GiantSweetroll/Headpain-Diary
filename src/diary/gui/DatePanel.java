package diary.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import diary.constants.Constants;
import diary.methods.DateOperation;
import giantsweetroll.VectorInt;
import giantsweetroll.gui.swing.Gbm;

public class DatePanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1315020675324622006L;
	private JComboBox<String> comboDay, comboMonth, comboYear;
	private GridBagConstraints c;
	
	//Vectors
	private VectorInt vecDay;
	
	protected DatePanel(boolean enabled)
	{
		this.init();
		
		if (!enabled)
		{
			this.comboDay.setEnabled(false);
			this.comboMonth.setEnabled(false);
			this.comboYear.setEnabled(false);
		}
	}
	
	//Methods
	private final void init()
	{
		//Initialization
		this.comboYear = new JComboBox<String>(DateOperation.getYearRangeString());
		this.comboMonth = new JComboBox<String>(DateOperation.getMonthsString());
		this.comboDay = new JComboBox<String>(DateOperation.getMaxDaysString(Byte.parseByte(this.comboMonth.getSelectedItem().toString()), Short.parseShort(this.comboYear.getSelectedItem().toString())));
		this.c = new GridBagConstraints();
		
		//Properties
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		this.comboYear.addActionListener(this.yearListener);
		this.comboMonth.addActionListener(this.monthListener);
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_GENERAL;
		this.vecDay = new VectorInt(c.gridx, c.gridy);
		this.add(this.comboDay, c);
		Gbm.nextGridColumn(c);
		this.add(this.comboMonth, c);
		Gbm.nextGridColumn(c);
		this.add(this.comboYear, c);
	}
	
	private void refreshDayRange()
	{
		this.remove(this.comboDay);
		this.comboDay = new JComboBox<String>(DateOperation.getMaxDaysString(Byte.parseByte(this.comboMonth.getSelectedItem().toString()), Short.parseShort(this.comboYear.getSelectedItem().toString())));
		c.gridx = this.vecDay.x;
		c.gridy = this.vecDay.y;
		this.add(this.comboDay, c);
		
		this.revalidate();
		this.repaint();
	}
	
	protected void setDate(String day, String month, String year)
	{
		this.comboYear.setSelectedItem(year);
		this.comboMonth.setSelectedItem(month);
		this.comboDay.setSelectedItem(day);
	}
	
	//Interfaces
	private ActionListener yearListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				refreshDayRange();
			}
		};
			
	private ActionListener monthListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			refreshDayRange();
		}
	};
}
