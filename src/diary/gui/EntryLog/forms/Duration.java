package diary.gui.EntryLog.forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import diary.constants.Constants;
import diary.constants.XMLIdentifier;
import diary.gui.SliderPanel;
import diary.methods.Methods;
import giantsweetroll.GMisc;
import giantsweetroll.gui.swing.Gbm;

public class Duration extends FormElement implements KeyListener, ActionListener, ChangeListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1244113343095232112L;

	private JSlider slider;
	private JComboBox<String> units;
	private JLabel selected;
	private SliderPanel sliderPanel;
	
	private int selectedValue;
	
	//Constants
	private final int SECONDS_MAJOR_TICK_COUNT = 10,
						SECONDS_MINOR_TICK_COUNT = 2,
						MINUTES_MAJOR_TICK_COUNT = SECONDS_MAJOR_TICK_COUNT,
						MINUTES_MINOR_TICK_COUNT = SECONDS_MINOR_TICK_COUNT,
						HOURS_MAJOR_TICK_COUNT = 2,
						HOURS_MINOR_TICK_COUNT = 1,
						DAYS_MAJOR_TICK_COUNT = 2,
						DAYS_MINOR_TICK_COUNT = 1;
	
	
	public Duration()
	{
		super(Methods.getLanguageText(XMLIdentifier.DURATION_LABEL), true);
		
		//Initialization
		this.slider = new JSlider(JSlider.HORIZONTAL, 1, 60, 1);
		this.units = new JComboBox<String>(Constants.DURATION_UNITS);
		this.selectedValue = this.slider.getValue();
		this.selected = new JLabel(this.getSelectedDataInformation());
		this.sliderPanel = new SliderPanel(this.slider);
//		SpringLayout spr = new SpringLayout();
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		//For Spring Layout
		/*
		spr.putConstraint(SpringLayout.WEST, this.getFormTitleLabel(), Constants.INSETS_TOP_COMPONENT.left, SpringLayout.WEST, this.getPanel());
		spr.putConstraint(SpringLayout.NORTH, this.getFormTitleLabel(), Constants.INSETS_TOP_COMPONENT.top, SpringLayout.NORTH, this.getPanel());
		spr.putConstraint(SpringLayout.NORTH, this.sliderPanel, Constants.INSETS_TOP_COMPONENT.top, SpringLayout.SOUTH, this.getFormTitleLabel());
		spr.putConstraint(SpringLayout.WEST, this.sliderPanel, Constants.INSETS_TOP_COMPONENT.left, SpringLayout.WEST, this.getPanel());
		spr.putConstraint(SpringLayout.NORTH, this.units, Constants.INSETS_TOP_COMPONENT.top, SpringLayout.SOUTH, this.sliderPanel);
		spr.putConstraint(SpringLayout.WEST, this.units, Constants.INSETS_TOP_COMPONENT.left, SpringLayout.WEST, this.getPanel());
		spr.putConstraint(SpringLayout.NORTH, this.selected, Constants.INSETS_TOP_COMPONENT.top, SpringLayout.SOUTH, this.units);
		spr.putConstraint(SpringLayout.WEST, this.selected, Constants.INSETS_TOP_COMPONENT.left, SpringLayout.WEST, this.getPanel());
		this.getPanel().setLayout(spr);		*/
		//Other
		this.getPanel().setLayout(new GridBagLayout());
		this.units.addActionListener(this);
		this.slider.setMajorTickSpacing(this.SECONDS_MAJOR_TICK_COUNT);
		this.slider.setMinorTickSpacing(this.SECONDS_MINOR_TICK_COUNT);
		this.slider.setPaintTicks(true);
		this.slider.setPaintLabels(true);
		this.slider.setOpaque(false);
		this.slider.addChangeListener(this);
//		this.slider.setUI(Constants.SLIDER_CUSTOM_UI);
		this.units.setBackground(Color.WHITE);
		this.sliderPanel.setMinimumSize(new Dimension(this.slider.getSize().width*2, this.slider.getSize().height));
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_TOP_COMPONENT;
		c.gridwidth = 3;
		this.getPanel().add(this.getFormTitleLabel(), c);
		Gbm.newGridLine(c);
		c.gridwidth = 2;
		this.getPanel().add(this.sliderPanel, c);
		Gbm.nextGridColumn(c, 2);
		c.gridwidth = 1;
		this.getPanel().add(this.selected, c);
		c.insets = Constants.INSETS_GENERAL;
		Gbm.newGridLine(c);
		c.gridwidth = 2;
		this.getPanel().add(this.units, c);
	}	
	
	//Methods
	private String getSelectedDataInformation()
	{
		return " = " + Integer.toString(this.selectedValue) + " " + GMisc.getItem(this.units).toString();
	}
	private void showSelectedData()
	{
		this.selected.setText(this.getSelectedDataInformation());
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
			return Integer.toString(Methods.minutesToSeconds(this.slider.getValue()));
		}
		else if (durationUnit.equals(Methods.getLanguageText(XMLIdentifier.DURATION_UNIT_HOURS_TEXT)))		//Hours
		{
			return Integer.toString(Methods.hoursToSeconds(this.slider.getValue()));
		}
		else																								//Days
		{
			return Long.toString(Methods.daysToSeconds(this.slider.getValue()));
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
				value = Methods.secondsToDays(value);
				durationUnit = Methods.getLanguageText(XMLIdentifier.DURATION_UNIT_DAYS_TEXT);
			}
			else if (value>=3600d && value<86400d)	//Hours
			{
				value = Methods.secondsToHours(value);
				durationUnit = Methods.getLanguageText(XMLIdentifier.DURATION_UNIT_HOURS_TEXT);
			}
			else if (value<3600d && value>=60d)		//Minutes
			{
				value = Methods.secondsToMinutes(value);
				durationUnit = Methods.getLanguageText(XMLIdentifier.DURATION_UNIT_MINUTES_TEXT);
			}
			
			this.slider.setValue(value);
			this.units.setSelectedItem(durationUnit);
		}
		catch(NumberFormatException ex)
		{
			double val = Double.parseDouble(obj.toString());
			long value = (long)val;
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
		if (durationUnit.equals(Methods.getLanguageText(XMLIdentifier.DURATION_UNIT_SECONDS_TEXT)))			//Seconds
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
		
		this.showSelectedData();
	}

	@Override
	public void stateChanged(ChangeEvent e)
	{
		this.selectedValue = this.slider.getValue();
		
		this.showSelectedData();
	}

	@Override
	public boolean allFilled()
	{
		return true;
	}

	@Override
	public void revalidateLanguage() 
	{
		this.setFormTitle(Methods.getLanguageText(XMLIdentifier.DURATION_LABEL));
		this.units.setModel(new DefaultComboBoxModel<String>(Constants.DURATION_UNITS));
		this.revalidate();
		this.repaint();
	}
}
