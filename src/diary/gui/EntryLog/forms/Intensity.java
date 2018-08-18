package diary.gui.EntryLog.forms;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import diary.constants.Constants;
import diary.constants.XMLIdentifier;
import diary.gui.SliderPanel;
import diary.methods.Methods;
import giantsweetroll.gui.swing.Gbm;

public class Intensity extends FormElement implements KeyListener, ChangeListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3234503424292394778L;

	private JSlider slider;
	private SliderPanel sliderPanel;
	private JLabel painDetail, selected;
	
	public Intensity()
	{
		super(Methods.getLanguageText(XMLIdentifier.INTENSITY_LABEL), true);
		
		//Initialization
		this.slider = new JSlider(JSlider.HORIZONTAL, 0, 10, 0);
		this.selected = new JLabel(this.getSelectedDataDetails());
		this.painDetail = new JLabel(Methods.getLanguageText(XMLIdentifier.INTENSITIY_DESCRIPTION_LABEL));
		this.sliderPanel = new SliderPanel(slider);
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		this.getPanel().setLayout(new GridBagLayout());
		this.slider.setMajorTickSpacing(2);
		this.slider.setMinorTickSpacing(1);
		this.slider.setPaintTicks(true);
		this.slider.setPaintLabels(true);
		this.slider.setOpaque(false);
		this.slider.addChangeListener(this);
//		this.slider.setUI(Constants.SLIDER_CUSTOM_UI);
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.gridwidth = 2;
		c.insets = Constants.INSETS_TOP_COMPONENT;
		this.getPanel().add(this.getFormTitleLabel(), c);
		Gbm.newGridLine(c);
		c.gridwidth = 1;
		this.getPanel().add(this.sliderPanel, c);
		Gbm.nextGridColumn(c);
		this.getPanel().add(this.selected, c);
		Gbm.newGridLine(c);
		c.insets = Constants.INSETS_GENERAL;
		this.getPanel().add(this.painDetail, c);
	}
	
	//Methods
	private String getSelectedDataDetails()
	{
		return " = " + Integer.toString(this.slider.getValue());
	}
	private void showSelectedData()
	{
		this.selected.setText(this.getSelectedDataDetails());
	}
	
	//Overridden Methods
	@Override
	public void resetDefaults() {
		this.slider.setValue(0);
	}
	
	@Override
	public void refresh() {};

	@Override
	public String getData() {
		return Integer.toString(this.slider.getValue());
	}

	@Override
	public void setData(Object obj) 
	{
		try
		{
			this.slider.setValue(Integer.parseInt(obj.toString()));
		}
		catch(NumberFormatException ex){ex.printStackTrace();}
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
	public boolean allFilled() {return true;}
	
	@Override
	public void stateChanged(ChangeEvent e)
	{
		this.showSelectedData();
	}

	@Override
	public void revalidateLanguage() 
	{
		this.setFormTitle(Methods.getLanguageText(XMLIdentifier.INTENSITY_LABEL));
		this.painDetail.setText(Methods.getLanguageText(XMLIdentifier.INTENSITIY_DESCRIPTION_LABEL));
	}
}
