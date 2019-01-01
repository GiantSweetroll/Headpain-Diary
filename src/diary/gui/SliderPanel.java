package diary.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSlider;

public class SliderPanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7079638567395541519L;
	
	private JSlider slider;
	
	public SliderPanel(JSlider slider)
	{
		this.setLayout(new BorderLayout());
		this.setOpaque(false);
		
		this.add(slider, BorderLayout.CENTER);
	}
	
	public SliderPanel(JSlider slider, int xPad, int yPad)
	{
		this.setLayout(new BorderLayout(xPad, yPad));
		this.setOpaque(false);
		
		this.add(new JPanel(), BorderLayout.EAST);
		this.add(slider, BorderLayout.CENTER);
		this.add(new JPanel(), BorderLayout.WEST);
	}
	
	public JSlider getSlider()
	{
		return this.slider;
	}
	public void setSlider(JSlider slider)
	{
		this.slider = slider;
		this.revalidate();
		this.repaint();
	}
}
