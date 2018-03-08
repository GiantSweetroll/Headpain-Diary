package diary.test;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test extends JPanel
{
	public void paint(Graphics g) {
	    Graphics2D g2d = (Graphics2D)g;
	    
	    g2d.drawString("aString", 100, 100);
	    AffineTransform at = new AffineTransform();
	    at.setToRotation(Math.PI / 4.0);
//	    at.setToRotation(Math.PI/2.0);
	    g2d.setTransform(at);
	    g2d.drawString("aString", 200, 100);

	  }
	
	public static void main(String args[])
	{
		JFrame frame = new JFrame();

	    frame.add(new Test());

	    frame.setSize(300, 300);
	    frame.setVisible(true);
	}
}