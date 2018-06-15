package diary.gui.EntryLogNew.forms;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JSlider;

import diary.constants.XMLIdentifier;
import diary.methods.Methods;

public class Intensity extends FormElement implements KeyListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3234503424292394778L;

	private JSlider slider;
	
	public Intensity()
	{
		super(Methods.getLanguageText(XMLIdentifier.INTENSITY_LABEL), Methods.getLanguageText(XMLIdentifier.INTENSITIY_DESCRIPTION_LABEL));
		
		//Initialization
		this.slider = new JSlider(JSlider.HORIZONTAL, 0, 10, 0);
		
		//Properties
		this.slider.setMajorTickSpacing(2);
		this.slider.setMinorTickSpacing(10);
		this.slider.setPaintTicks(true);
		
		//Add to panel
		this.addComponent(this.slider);
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
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
