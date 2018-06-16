package diary.gui.EntryLog.forms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComboBox;
import javax.swing.JSlider;

import diary.constants.Constants;
import diary.constants.XMLIdentifier;
import diary.methods.Methods;

public class Duration extends FormElement implements KeyListener, ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1244113343095232112L;

	private JSlider slider;
	private JComboBox<String> units;
	
	//Constants
	private final int SECONDS_MAJOR_TICK_COUNT = 6,
						SECONDS_MINOR_TICK_COUNT = 60,
						MINUTES_MAJOR_TICK_COUNT = 6,
						MINUTES_MINOR_TICK_COUNT = 60,
						HOURS_MAJOR_TICK_COUNT = 6,
						HOURS_MINOR_TICK_COUNT = 24,
						DAYS_MAJOR_TICK_COUNT = 5,
						DAYS_MINOR_TICK_COUNT = 31;
	
	
	public Duration()
	{
		super(Methods.getLanguageText(XMLIdentifier.DURATION_LABEL));
		
		//Initialization
		this.slider = new JSlider(JSlider.HORIZONTAL, 1, 60, 1);
		this.units = new JComboBox<String>(Constants.DURATION_UNITS);
		
		//Properties
		this.units.addActionListener(this);
		this.slider.setMajorTickSpacing(this.SECONDS_MAJOR_TICK_COUNT);
		this.slider.setMinorTickSpacing(this.SECONDS_MINOR_TICK_COUNT);
		this.slider.setPaintTicks(true);
		
		//Add to panel
		this.addComponent(this.slider);
		this.addComponent(this.units);
	}
	
	//Overridden Methods
	@Override
	public void resetDefaults() {this.slider.setValue(0);}

	@Override
	public void refresh() {};
	
	@Override
	public String getData() 
	{
		String durationUnit = this.units.getSelectedItem().toString();
		if (durationUnit.equals(Methods.getLanguageText(XMLIdentifier.DURATION_UNIT_SECONDS_TEXT)))			//Days
		{
			return Integer.toString(this.slider.getValue());
		}
		else if (durationUnit.equals(Methods.getLanguageText(XMLIdentifier.DURATION_UNIT_MINUTES_TEXT)))	//Minutes
		{
			return Integer.toString(this.slider.getValue()*60);
		}
		else if (durationUnit.equals(Methods.getLanguageText(XMLIdentifier.DURATION_UNIT_HOURS_TEXT)))		//Hours
		{
			return Integer.toString(this.slider.getValue()*3600);
		}
		else		//Days
		{
			return Long.toString(Long.parseLong(Integer.toString(this.slider.getValue())) * 86400L);
		}
	}

	@Override
	public void setData(Object obj) 
	{
		try
		{
			int value = Integer.parseInt(obj.toString());
			String durationUnit = Methods.getLanguageText(XMLIdentifier.DURATION_UNIT_SECONDS_TEXT);
			
			if (value >=86400d)								//Days
			{
				value = value/86400;
				durationUnit = Methods.getLanguageText(XMLIdentifier.DURATION_UNIT_DAYS_TEXT);
			}
			else if (value>=3600d && value<86400d)	//Hours
			{
				value = value/3600;
				durationUnit = Methods.getLanguageText(XMLIdentifier.DURATION_UNIT_HOURS_TEXT);
			}
			else if (value<3600d && value>=60d)		//Minutes
			{
				value = value/60;
				durationUnit = Methods.getLanguageText(XMLIdentifier.DURATION_UNIT_MINUTES_TEXT);
			}		
			
			this.slider.setValue(value);
			this.units.setSelectedItem(durationUnit);
		}
		catch(NumberFormatException ex)
		{
			long value = Long.parseLong(obj.toString());
			value = value/86400;
			String durationUnit = Methods.getLanguageText(XMLIdentifier.DURATION_UNIT_DAYS_TEXT);
			
			this.slider.setValue(Integer.parseInt(Long.toString(value)));
			this.units.setSelectedItem(durationUnit);
		}
		catch(Exception ex) {ex.printStackTrace();}
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int sliderValue = this.slider.getValue();
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			if (sliderValue>this.slider.getMinimum())
			{
				sliderValue--;
				this.slider.setValue(sliderValue);
			}
		}
		
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			if (sliderValue<this.slider.getMaximum())
			{
				sliderValue++;
				this.slider.setValue(sliderValue);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		String durationUnit = this.units.getSelectedItem().toString();
		if (durationUnit.equals(Methods.getLanguageText(XMLIdentifier.DURATION_UNIT_DAYS_TEXT)))			//Days
		{
			this.slider.setMaximum(60);
			this.slider.setMajorTickSpacing(this.SECONDS_MAJOR_TICK_COUNT);
			this.slider.setMinorTickSpacing(this.SECONDS_MINOR_TICK_COUNT);
		}
		else if (durationUnit.equals(Methods.getLanguageText(XMLIdentifier.DURATION_UNIT_MINUTES_TEXT)))	//Minutes
		{
			this.slider.setMaximum(60);
			this.slider.setMajorTickSpacing(this.MINUTES_MAJOR_TICK_COUNT);
			this.slider.setMinorTickSpacing(this.MINUTES_MINOR_TICK_COUNT);
		}
		else if (durationUnit.equals(Methods.getLanguageText(XMLIdentifier.DURATION_UNIT_HOURS_TEXT)))		//Hours
		{
			this.slider.setMaximum(24);
			this.slider.setMajorTickSpacing(this.HOURS_MAJOR_TICK_COUNT);
			this.slider.setMinorTickSpacing(this.HOURS_MINOR_TICK_COUNT);
		}
		else if (durationUnit.equals(Methods.getLanguageText(XMLIdentifier.DURATION_UNIT_DAYS_TEXT)))		//Days
		{
			this.slider.setMaximum(31);
			this.slider.setMajorTickSpacing(this.DAYS_MAJOR_TICK_COUNT);
			this.slider.setMinorTickSpacing(this.DAYS_MINOR_TICK_COUNT);
		}
	}
}
