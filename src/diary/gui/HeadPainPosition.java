package diary.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import diary.constants.Constants;
import giantsweetroll.Misc;
import giantsweetroll.VectorInt;
import giantsweetroll.gui.swing.Gbm;

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
	
	private ImageButtonPanel ibpGeneral, ibpSpecific;
	
	//Vectors
	private VectorInt vecGeneralPosPanel;
	private VectorInt vecSpecificPosPanel;
	
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
		this.labPainKind = new JLabel(Constants.LANGUAGE.kindOfHeadpainLabel, SwingConstants.RIGHT);
		this.tfPainKind = new JTextField("", 20);
		this.labIntensity = new JLabel(Constants.LANGUAGE.intensityLabel, SwingConstants.RIGHT);
		this.tfIntensity = new JFormattedTextField(Constants.AMOUNT_FORMAT);
		this.labIntensityRange = new JLabel("(0.0 - 10.0)", SwingConstants.RIGHT);
		this.labDuration = new JLabel(Constants.LANGUAGE.durationLabel, SwingConstants.RIGHT);
		this.tfDuration = new JFormattedTextField(Constants.AMOUNT_FORMAT);
		this.labDurationUnit = new JLabel (Constants.LANGUAGE.durationUnitsLabel, SwingConstants.RIGHT);
		this.changeGeneralSelectionOptions(this.getLocationIdentifier(Misc.getItem(this.comboGeneralPos).toString()));
		
		//Properties
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		this.tfIntensity.setColumns(5);
		this.tfDuration.setColumns(5);
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		//add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_TOP_COMPONENT;
		c.fill = GridBagConstraints.BOTH;
		this.add(this.labGeneralPosition, c);						//General Position
		Gbm.nextGridColumn(c);
		this.add(this.comboGeneralPos, c);							//General Position Options
		c.insets = Constants.INSETS_GENERAL;
		Gbm.newGridLine(c);
		this.vecGeneralPosPanel.x = c.gridx;
		this.vecGeneralPosPanel.y = c.gridy;
		this.add(this.ibpGeneral, c);								//Image Button Panel (General)
		Gbm.newGridLine(c);
		this.vecSpecificPosPanel.x = c.gridx;
		this.vecSpecificPosPanel.y = c.gridy;
//		this.add(this.ibpSpecific, c);								//Image Button Panel (Specific)
		Gbm.newGridLine(c);
		this.add(this.labPainKind, c);								//Kind of Headpain
		Gbm.nextGridColumn(c);
		c.gridwidth = 2;
		this.add(this.tfPainKind, c);								//Kind of Headpain Text Field
		Gbm.newGridLine(c);
		c.gridwidth = 1;
		this.add(this.labIntensity, c);								//Intensity
		Gbm.nextGridColumn(c);
		this.add(this.tfIntensity, c);								//Intensity Text Field
		Gbm.nextGridColumn(c);
		this.add(this.labIntensityRange, c);						//Intensity Range
		Gbm.newGridLine(c);
		this.add(this.labDuration, c);								//Duration
		Gbm.nextGridColumn(c);
		this.add(this.tfDuration, c);								//Duration Text Field
		Gbm.nextGridColumn(c);
		this.add(this.labDurationUnit, c);							//Duration Unit
		
		this.configureListenersForMembers();
	}
	private void configureListenersForMembers()
	{
		this.comboGeneralPos.addActionListener(this.generalPositionListener);
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

	private void changeGeneralSelectionOptions(byte change)
	{
		this.ibpGeneral = new ImageButtonPanel(ImageButtonPanel.GENERAL_SELECTION, change);
	}
	private void changeSpecificSelectionOptions(byte change)
	{
		this.ibpSpecific = new ImageButtonPanel(ImageButtonPanel.SPECIFIC_SELECTION, change);
	}
	private byte getLocationIdentifier(String item)
	{
		byte mark = ImageButtonPanel.HEAD;
		
		if (item.equals(Constants.LANGUAGE.generalPositionHeadText))
		{
			mark = ImageButtonPanel.HEAD;
		}
		else if (item.equals(Constants.LANGUAGE.generalPositionEyesText))
		{
			mark = ImageButtonPanel.EYES;
		}
		else if (item.equals(Constants.LANGUAGE.generalPositionEarsText))
		{
			mark = ImageButtonPanel.EARS;
		}
		else if (item.equals(Constants.LANGUAGE.generalPositionCheeksText))
		{
			mark = ImageButtonPanel.CHEEKS;
		}
		else if (item.equals(Constants.LANGUAGE.generalPositionChinText))
		{
			mark = ImageButtonPanel.CHIN;
		}
		
		return mark;
	}
	//Interfaces
	private ActionListener generalPositionListener = new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					JComboBox combo = (JComboBox)e.getSource();
					String item = combo.getSelectedItem().toString();
					changeGeneralSelectionOptions(getLocationIdentifier(item));
				}
			};
}
