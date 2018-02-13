package diary;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import diary.gui.DateRangePanel;
import diary.patientdata.PatientDataForm;

public class ImagePanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4196085130013099730L;
	
	//Constructors
	public ImagePanel(JComponent mainComponent, PatientDataForm patientData, DateRangePanel dateRange)
	{
		//Initialization
		JPanel panelTop = new JPanel();
		
		//Properties
		this.setLayout(new BorderLayout());
		this.setOpaque(false);
		panelTop.setLayout(new BorderLayout());
		panelTop.setOpaque(false);
		
		//Add to panel
		//panelTop
		panelTop.add(patientData, BorderLayout.WEST);
		panelTop.add(dateRange, BorderLayout.EAST);
		//panel
		this.add(panelTop, BorderLayout.NORTH);
		this.add(mainComponent, BorderLayout.CENTER);
	}
}
