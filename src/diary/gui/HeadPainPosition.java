package diary.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import diary.constants.Constants;

public class HeadPainPosition extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5338976948643380687L;

	private GridBagConstraints c;
	
	private JLabel labGeneralPosition;
	private JLabel labPainKind;
	private JLabel labIntensity;
	private JLabel labDuration;
	private JLabel labIntensityRange;
	private JLabel labDurationUnit;
	
	private JComboBox<String> comboGeneralPos;
	
	private JTextField tfPainKind;
	
	private JFormattedTextField tfIntensity, tfDuration;
	
	//Constants
	
	protected HeadPainPosition()
	{
		this.init();
	}
	
	private void init()
	{
		//Initialization
		this.c = new GridBagConstraints();
		this.labGeneralPosition = new JLabel (Constants.LANGUAGE.generalPositionLabel, SwingConstants.RIGHT);
		this.comboGeneralPos = new JComboBox<String>(this.getGeneralLocationOptions());
		
		//Properties
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		
		//add to panel
	}
	
	private String[] getGeneralLocationOptions()
	{
		String[] array = {Constants.LANGUAGE.generalPositionHeadText,
							Constants.LANGUAGE.generalPositionEyesText,
							Constants.LANGUAGE.generalPositionEarsText,
							Constants.LANGUAGE.generalPositionCheeksText,
							Constants.LANGUAGE.generalPositionChinText};
		return array;
	}
}
