package diary.test;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import diary.constants.ImageConstants;
import diary.gui.ImageTextPanel;
import giantsweetroll.ImageManager;

public class Test
{

	public static void main(String[] args)
	{
		JButton button = new JButton();
		button.add(new ImageTextPanel(ImageManager.getImageIcon(ImageConstants.PAIN_LOCATION_EYES_AND_FOREHEAD), "String", 20));
		
		button.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						button.setEnabled(false);
						for (Component component : button.getComponents())
						{
							component.setEnabled(false);
						}
					}
				});
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 300);
		frame.setLayout(new FlowLayout());
		
		frame.add(button);
		
		frame.setVisible(true);
	}
}
