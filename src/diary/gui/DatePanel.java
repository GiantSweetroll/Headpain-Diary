package diary.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import diary.constants.Constants;
import diary.constants.Globals;
import diary.constants.XMLIdentifier;
import diary.interfaces.LanguageListener;
import diary.methods.DateOperation;
import diary.methods.Methods;
import giantsweetroll.date.Date;
import giantsweetroll.gui.swing.Gbm;

public class DatePanel extends JPanel implements ActionListener, LanguageListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1315020675324622006L;
	private JComboBox<String> comboDay, comboMonth, comboYear;
	private GButton butAuto, butDefault;
	private GridBagConstraints c;
	private Date defaultDate;
	
	//Vectors
//	private VectorInt vecDay;
	
	//Constants
	private final String AUTO = "auto";
	private final String DEFAULT = "default";
	
	//Conditional
	private boolean enable;
	
	//Constructor
	public DatePanel(boolean enabled)
	{
		this.enable = enabled;
		this.init();
		this.enableComponents(enabled);
	}
	
	//Create GUI
	private final void init()
	{
		//Initialization
		this.comboYear = new JComboBox<String>(DateOperation.getYearRangeStringReversed());
	//	this.comboYear = new JComboBox<String>(DateOperation.getYearRangeString());
		this.comboMonth = new JComboBox<String>(DateOperation.getMonthNameList().toArray(new String[12]));
		this.comboDay = new JComboBox<String>(DateOperation.getMaxDaysString(Byte.parseByte(Integer.toString(this.comboMonth.getSelectedIndex()+1)), Short.parseShort(this.comboYear.getSelectedItem().toString())));
		this.butAuto = new GButton(Globals.LANGUAGE.getTextMap().get(XMLIdentifier.AUTO_TEXT));
		this.butDefault = new GButton(Globals.LANGUAGE.getTextMap().get(XMLIdentifier.RESET_TEXT));
		this.c = new GridBagConstraints();
		this.defaultDate = new Date();
		
		//Properties
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		this.comboYear.addActionListener(this.yearListener);
		this.comboMonth.addActionListener(this.monthListener);
		this.comboDay.setEnabled(this.enable);
		this.butAuto.addActionListener(this);
		this.butAuto.setActionCommand(this.AUTO);
		this.butAuto.setToolTipText(Globals.LANGUAGE.getTextMap().get(XMLIdentifier.DATE_AUTO_BUTTON_TOOLTIP_TEXT));
		this.butDefault.addActionListener(this);
		this.butDefault.setActionCommand(this.DEFAULT);
		this.butDefault.setToolTipText(Globals.LANGUAGE.getTextMap().get(XMLIdentifier.DATE_RESET_BUTTON_TOOLTIP_TEXT));
		this.comboDay.setBackground(Color.WHITE);
		this.comboMonth.setBackground(Color.WHITE);
		this.comboYear.setBackground(Color.WHITE);
		this.setAsDefaultDataThis();
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_GENERAL;
	//	this.vecDay = new VectorInt(c.gridx, c.gridy);
		this.add(this.comboDay, c);							//Day
		Gbm.nextGridColumn(c);
		this.add(this.comboMonth, c);						//Month
		Gbm.nextGridColumn(c);
		this.add(this.comboYear, c);						//Year
		Gbm.nextGridColumn(c);
		this.add(this.butDefault, c);
		Gbm.nextGridColumn(c);
		this.add(this.butAuto, c);							//Auto Button
	}
	
	//Public Methods
	public void enableComponents(boolean enable)
	{
		this.comboMonth.setEnabled(enable);
		this.comboYear.setEnabled(enable);
		this.comboDay.setEnabled(enable);
		this.butAuto.setEnabled(enable);
		this.butDefault.setEnabled(enable);
	}
	public void setDate(Date date)
	{
		this.comboYear.setSelectedItem(Integer.toString(date.getYear()));
		this.comboMonth.setSelectedIndex(date.getMonth()-1);
		this.comboDay.setSelectedIndex(date.getDay()-1);
	}
	
	public String getDay()
	{
		return this.comboDay.getSelectedItem().toString();
	}
	public String getMonth()			//As String
	{
		return this.comboMonth.getSelectedItem().toString();
	}
	public int getMonthValue()			//In value form
	{
		return this.comboMonth.getSelectedIndex() + 1;
	}
	public String getYear()
	{
		return this.comboYear.getSelectedItem().toString();
	}
	
	public void setAsDefaultDataThis()
	{
		this.setDefaultDate(new Date(Integer.parseInt(this.getDay()), this.getMonthValue(), Integer.parseInt(this.getYear())));
	}
	
	public void setDefaultDate(Date date)
	{
		this.defaultDate = date;
	}
	
	public void resetDefault()
	{
		try
		{
			this.setDate(this.defaultDate);
		}
		catch(NullPointerException ex){ex.printStackTrace();}
	}
	
	public void autoSetDate()
	{
		this.setDate(new Date());
	}
	
	public boolean sameAsDefault()
	{
		Date date = this.getDate();
		if (this.defaultDate.getYear() == date.getYear() && 
				this.defaultDate.getMonth() == date.getMonth() && 
				this.defaultDate.getDay() == date.getDay())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public String getDateAsString()
	{
		return this.getDay() + "/" + this.getMonthValue() + "/" + this.getYear();
	}
	
	public Date getDate()
	{
		return new Date(Integer.parseInt(this.getDay()), this.getMonthValue(), Integer.parseInt(this.getYear()));
	}	
	
	//Private Methods
	private void refreshDayRange()
	{	
		int index = -1;
		try
		{
			index = this.comboDay.getSelectedIndex();
		}
		catch(NullPointerException ex) {}
		this.comboDay.setModel(new DefaultComboBoxModel<String>(DateOperation.getMaxDaysString(Byte.parseByte(Integer.toString(this.comboMonth.getSelectedIndex()+1)), 
																				Short.parseShort(this.comboYear.getSelectedItem().toString()))));
		int size = this.comboDay.getModel().getSize();
		if (index < 0)
		{
			this.comboDay.setSelectedIndex(0);			//First index
		}
		else if (index <= size-1)
		{
			this.comboDay.setSelectedIndex(index);
		}
		else
		{
			this.comboDay.setSelectedIndex(size-1);		//Last index
		}
		
		this.revalidate();
		this.repaint();
	}
	
	//Overridden Methods
	@Override
	public void setEnabled(boolean enable)
	{
		super.setEnabled(enable);
		
		this.comboDay.setEnabled(enable);
		this.comboMonth.setEnabled(enable);
		this.comboYear.setEnabled(enable);
		this.butAuto.setEnabled(enable);
		this.butDefault.setEnabled(enable);
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

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		switch(e.getActionCommand())
		{
			case AUTO:
				this.autoSetDate();
				break;
				
			case DEFAULT:
				this.resetDefault();
				break;
		}
	}

	@Override
	public void revalidateLanguage() 
	{
		int index = this.comboMonth.getSelectedIndex();
		int indexDay = this.comboDay.getSelectedIndex();
		this.comboMonth.setModel(new DefaultComboBoxModel<String>(DateOperation.getMonthNameList().toArray(new String[12])));
		this.butAuto.setText(Methods.getLanguageText(XMLIdentifier.AUTO_TEXT));
		this.butDefault.setText(Methods.getLanguageText(XMLIdentifier.RESET_TEXT));
		this.comboMonth.setSelectedIndex(index);
		this.comboDay.setSelectedIndex(indexDay);
		this.revalidate();
		this.repaint();
	}
}
