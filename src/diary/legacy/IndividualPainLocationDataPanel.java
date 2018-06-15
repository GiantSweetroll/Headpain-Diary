package diary.legacy;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.LinkedHashMap;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import diary.constants.Constants;
import diary.constants.PainDataIdentifier;
import diary.constants.PainLocationConstants;
import diary.constants.XMLIdentifier;
import diary.methods.Methods;
import giantsweetroll.GMisc;
import giantsweetroll.VectorInt;
import giantsweetroll.gui.swing.Gbm;

public class IndividualPainLocationDataPanel extends JPanel implements FocusListener
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
//	private JLabel labDurationUnit;
	
	private JComboBox<String> comboGeneralPos, comboDurationUnit, comboPainKind;
	
	private JTextField tfPainKind;
	
	private JFormattedTextField tfIntensity, tfDuration;
	
	private PainLocationSelectionPanel generalPos, specificPos;
	
	private JScrollPane scrollGeneral, scrollSpecific;
	
	//Constants
	protected static final String SECONDS = "seconds";
	protected static final String MINUTES = "minutes";
	protected static final String HOURS = "hours";
	
	//Vectors
	private VectorInt vecGeneralPosPanel;
	private VectorInt vecSpecificPosPanel;
	
	protected IndividualPainLocationDataPanel()
	{
		this.init();
	}
	
	//Methods
	private void init()
	{
		//Initialization
		this.c = new GridBagConstraints();
		this.labGeneralPosition = new JLabel (Constants.REQUIRED_IDENTIFIER + Methods.getLanguageText(XMLIdentifier.GENERAL_POSITION_LABEL), SwingConstants.RIGHT);
		this.comboGeneralPos = new JComboBox<String>(this.getGeneralLocationOptions());
		this.labPainKind = new JLabel(Constants.REQUIRED_IDENTIFIER + Methods.getLanguageText(XMLIdentifier.KIND_OF_HEADPAIN_LABEL), SwingConstants.RIGHT);
		this.tfPainKind = new JTextField("", 10);
		this.comboPainKind = new JComboBox<String>(Constants.DEFAULT_PAIN_KIND);
		this.labIntensity = new JLabel(Constants.REQUIRED_IDENTIFIER + Methods.getLanguageText(XMLIdentifier.INTENSITY_LABEL), SwingConstants.RIGHT);
		this.tfIntensity = new JFormattedTextField(Constants.AMOUNT_FORMAT);
		this.labIntensityRange = new JLabel(Methods.getLanguageText(XMLIdentifier.INTENSITIY_DESCRIPTION_LABEL), SwingConstants.LEFT);
		this.labDuration = new JLabel(Constants.REQUIRED_IDENTIFIER + Methods.getLanguageText(XMLIdentifier.DURATION_LABEL), SwingConstants.RIGHT);
		this.tfDuration = new JFormattedTextField(Constants.AMOUNT_FORMAT);
	//	this.labDurationUnit = new JLabel (Methods.getLanguageText(XMLIdentifier.DURATION_UNIT_LABEL), SwingConstants.LEFT);
		this.comboDurationUnit = new JComboBox<String>(Constants.DURATION_UNITS);
		this.generalPos = new PainLocationSelectionPanel(this.getLocationIdentifier(GMisc.getItem(this.comboGeneralPos).toString()));
		this.scrollGeneral = new JScrollPane(this.generalPos, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.specificPos = new PainLocationSelectionPanel(this.generalPos.getSelected());
		this.scrollSpecific = new JScrollPane(this.specificPos, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		//Properties
		this.generalPos.addLinkedPanel(this.specificPos);
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		this.tfIntensity.setColumns(5);
		this.tfIntensity.addFocusListener(this);
		this.tfDuration.setColumns(5);
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		this.scrollGeneral.setOpaque(false);
		this.scrollGeneral.getViewport().setOpaque(false);
		this.scrollSpecific.setOpaque(false);
		this.scrollSpecific.getViewport().setOpaque(false);
		this.tfPainKind.setEditable(false);
		
		//add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_TOP_COMPONENT;
		c.fill = GridBagConstraints.BOTH;
		this.add(this.labGeneralPosition, c);						//General Position
		Gbm.nextGridColumn(c);
		this.add(this.comboGeneralPos, c);							//General Position Options
		c.insets = Constants.INSETS_GENERAL;
		Gbm.newGridLine(c);
		c.gridwidth = 100;
		this.vecGeneralPosPanel = new VectorInt(c.gridx, c.gridy);
		this.add(this.scrollGeneral, c);								//Image Button Panel (General)
		Gbm.newGridLine(c);
		this.vecSpecificPosPanel = new VectorInt(c.gridx, c.gridy);
		this.add(this.scrollSpecific, c);								//Image Button Panel (Specific)
		Gbm.newGridLine(c);
		c.gridwidth = 1;
		this.add(this.labPainKind, c);								//Kind of Headpain
		Gbm.nextGridColumn(c);
		c.gridwidth = 2;											
		this.add(this.comboPainKind, c);							//Kind of Headpain Options
		Gbm.nextGridColumn(c, 2);
		c.gridwidth = 1;
		this.add(this.tfPainKind, c);								//Kind of Headpain Text field
		Gbm.newGridLine(c);
		this.add(this.labIntensity, c);								//Intensity
		Gbm.nextGridColumn(c);
		this.add(this.tfIntensity, c);								//Intensity Text Field
		Gbm.nextGridColumn(c);
		c.gridwidth = 2;
		this.add(this.labIntensityRange, c);						//Intensity Range
		Gbm.newGridLine(c);
		c.gridwidth = 1;
		this.add(this.labDuration, c);								//Duration
		Gbm.nextGridColumn(c);
		this.add(this.tfDuration, c);								//Duration Text Field
		Gbm.nextGridColumn(c);
//		this.add(this.labDurationUnit, c);							//Duration Unit
		this.add(this.comboDurationUnit, c);						//Duration Unit Options
		
		this.configureListenersForMembers();
	}
	private void configureListenersForMembers()
	{
		this.comboGeneralPos.addActionListener(this.generalPositionListener);
		this.comboPainKind.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if (comboPainKind.getSelectedIndex() == Constants.DEFAULT_PAIN_KIND.length-1)
						{
							tfPainKind.setEditable(true);
						}
						else
						{
							tfPainKind.setEditable(false);
						}
					}
				});
	}
	
	private String[] getGeneralLocationOptions()
	{
		String[] array = {Methods.getLanguageText(XMLIdentifier.GENERAL_POSITION_HEAD_TEXT),
							Methods.getLanguageText(XMLIdentifier.GENERAL_POSITION_EARS_TEXT),
							Methods.getLanguageText(XMLIdentifier.GENERAL_POSITION_EYES_TEXT),
							Methods.getLanguageText(XMLIdentifier.GENERAL_POSITION_CHEEKS_TEXT)};
		return array;
	}

	protected void setPainKind(String painKind)
	{
	//	this.tfPainKind.setText(painKind);
		if (Methods.isPartOfDefaultPainKind(painKind))
		{
			this.comboPainKind.setSelectedItem(painKind);
			this.tfPainKind.setEditable(false);
		}
		else
		{
			this.comboPainKind.setSelectedIndex(Constants.DEFAULT_PAIN_KIND.length-1);
			this.tfPainKind.setText(painKind);
			this.tfPainKind.setEditable(true);
		}
	}
	protected void setIntensity(String intensity)
	{
		this.tfIntensity.setText(intensity);
	}
	protected void setDuration(String duration)
	{
		this.tfDuration.setText(duration);
	}
	protected void setDurationUnit(String unit)		//Parameter is from this class constants
	{
		if (unit.equals(SECONDS))
		{
			this.comboDurationUnit.setSelectedItem(Methods.getLanguageText(XMLIdentifier.DURATION_UNIT_SECONDS_TEXT));
		}
		else if (unit.equals(MINUTES))
		{
			this.comboDurationUnit.setSelectedItem(Methods.getLanguageText(XMLIdentifier.DURATION_UNIT_MINUTES_TEXT));
		}
		else if (unit.equals(HOURS))
		{
			this.comboDurationUnit.setSelectedItem(Methods.getLanguageText(XMLIdentifier.DURATION_UNIT_HOURS_TEXT));
		}
	}
	
	protected void setSelectedPosition(String generalPositionConstant, String specificPositionConstant, String verySpecificPositionConstant)
	{
		this.comboGeneralPos.setSelectedItem(IndividualPainLocationDataPanel.getLocationLanguage(generalPositionConstant));
		this.changeGeneralSelectionOptions(generalPositionConstant);
		this.generalPos.setSelected(specificPositionConstant);
		this.changeSpecificSelectionOptions(this.generalPos.getSelected());
		this.specificPos.setSelected(verySpecificPositionConstant);
		this.revalidate();
		this.repaint();
	}
	
	private void changeGeneralSelectionOptions(String location)
	{
		this.remove(this.scrollGeneral);
		
		this.generalPos = new PainLocationSelectionPanel(location);
		this.scrollGeneral = new JScrollPane(this.generalPos, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.scrollGeneral.setOpaque(false);
		this.scrollGeneral.getViewport().setOpaque(false);
		
		c.gridwidth = 100;
		c.gridx = this.vecGeneralPosPanel.x;
		c.gridy = this.vecGeneralPosPanel.y;
		this.add(this.scrollGeneral, c);
		c.gridwidth = 1;
		
		this.revalidate();
		this.repaint();
	}
	private void changeSpecificSelectionOptions(String location)
	{
		this.remove(this.scrollSpecific);
		
		this.specificPos = new PainLocationSelectionPanel(location);
		this.scrollSpecific = new JScrollPane(this.specificPos, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.scrollSpecific.setOpaque(false);
		this.scrollSpecific.getViewport().setOpaque(false);
		this.generalPos.addLinkedPanel(this.specificPos);
		
		c.gridwidth = 100;
		c.gridx = this.vecSpecificPosPanel.x;
		c.gridy = this.vecSpecificPosPanel.y;
		this.add(this.scrollSpecific, c);
		c.gridwidth = 1;
		
		this.revalidate();
		this.repaint();
	}
	protected static String getLocationIdentifier(String item)
	{
		String location = "";
		
		if (item.equals(Methods.getLanguageText(XMLIdentifier.GENERAL_POSITION_HEAD_TEXT)))
		{
			location = PainLocationConstants.HEAD;
		}
		else if (item.equals(Methods.getLanguageText(XMLIdentifier.GENERAL_POSITION_EYES_TEXT)))
		{
			location = PainLocationConstants.EYES;
		}
		else if (item.equals(Methods.getLanguageText(XMLIdentifier.GENERAL_POSITION_EARS_TEXT)))
		{
			location = PainLocationConstants.EARS;
		}
		else if (item.equals(Methods.getLanguageText(XMLIdentifier.GENERAL_POSITION_CHEEKS_TEXT)))
		{
			location = PainLocationConstants.CHEEKS;
		}
		
		return location;
	}
	protected static String getLocationLanguage(String painLocationConstant)
	{
		String location = "";
		
		if (painLocationConstant.equals(PainLocationConstants.HEAD))
		{
			location = Methods.getLanguageText(XMLIdentifier.GENERAL_POSITION_HEAD_TEXT);
		}
		else if (painLocationConstant.equals(PainLocationConstants.EYES))
		{
			location = Methods.getLanguageText(XMLIdentifier.GENERAL_POSITION_EYES_TEXT);
		}
		else if (painLocationConstant.equals(PainLocationConstants.EARS))
		{
			location = Methods.getLanguageText(XMLIdentifier.GENERAL_POSITION_EARS_TEXT);
		}
		else if (painLocationConstant.equals(PainLocationConstants.CHEEKS))
		{
			location = Methods.getLanguageText(XMLIdentifier.GENERAL_POSITION_CHEEKS_TEXT);
		}
		
		return location;
	}
	
	protected LinkedHashMap<String, String> getDataMap()
	{
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		
		map.put(PainDataIdentifier.GENERAL_POSITION, GMisc.getItem(this.comboGeneralPos).toString());
		map.put(PainDataIdentifier.GENERAL_POSITION_2, this.generalPos.getSelected());
		map.put(PainDataIdentifier.SPECIFIC_LOCATION, this.specificPos.getSelected());
		if (Methods.isLastIndex(this.comboPainKind))
		{
			map.put(PainDataIdentifier.PAIN_KIND, Methods.getTextData(this.tfPainKind));
		}
		else
		{
			map.put(PainDataIdentifier.PAIN_KIND, this.comboPainKind.getSelectedItem().toString());
		}
		map.put(PainDataIdentifier.INTENSITY, Methods.getTextData(this.tfIntensity));
		map.put(PainDataIdentifier.DURATION, Methods.getTextData(this.tfDuration));
		
		return map;
	}
	
	protected Element getDataAsElement(Document doc, String id)				//Puts the data in an Element of DOM XML
	{
		Element locationElement = doc.createElement(PainDataIdentifier.PAIN_LOCATION);
		locationElement.setAttribute(PainDataIdentifier.PAIN_LOCATION_ID, id);
		
		this.appendToNode(doc, locationElement, PainDataIdentifier.GENERAL_POSITION, IndividualPainLocationDataPanel.getLocationIdentifier(this.comboGeneralPos.getSelectedItem().toString()));
		this.appendToNode(doc, locationElement, PainDataIdentifier.GENERAL_POSITION_2, this.generalPos.getSelected());
		this.appendToNode(doc, locationElement, PainDataIdentifier.SPECIFIC_LOCATION, this.specificPos.getSelected());	
		if (Methods.isLastIndex(this.comboPainKind))
		{
			this.appendToNode(doc, locationElement, PainDataIdentifier.PAIN_KIND, Methods.getTextData(this.tfPainKind));
		}
		else
		{
			this.appendToNode(doc, locationElement, PainDataIdentifier.PAIN_KIND, this.comboPainKind.getSelectedItem().toString());
		}
		this.appendToNode(doc, locationElement, PainDataIdentifier.INTENSITY, Methods.getTextData(this.tfIntensity));
		String durationUnit = this.comboDurationUnit.getSelectedItem().toString();
		if (durationUnit.equals(Methods.getLanguageText(XMLIdentifier.DURATION_UNIT_SECONDS_TEXT)))
		{
			this.appendToNode(doc, locationElement, PainDataIdentifier.DURATION, Methods.getTextData(this.tfDuration));
		}
		else if (durationUnit.equals(Methods.getLanguageText(XMLIdentifier.DURATION_UNIT_MINUTES_TEXT)))
		{
			double durationSeconds = Double.parseDouble(Methods.getTextData(this.tfDuration)) * 60d;
			this.appendToNode(doc, locationElement, PainDataIdentifier.DURATION, Double.toString(durationSeconds));
		}
		else if (durationUnit.equals(Methods.getLanguageText(XMLIdentifier.DURATION_UNIT_HOURS_TEXT)))
		{
			double durationSeconds = Double.parseDouble(Methods.getTextData(this.tfDuration)) * 3600d;
			this.appendToNode(doc, locationElement, PainDataIdentifier.DURATION, Double.toString(durationSeconds));
		}
		
		return locationElement;
	}
	
	private void appendToNode(Document doc, Element node, String key, String content)			//For use with getDataAsElement()
	{
		Element element = doc.createElement(key);
		element.appendChild(doc.createTextNode(content));
		node.appendChild(element);
	}
	
	protected boolean allFieldsEntered()
	{
//		Methods.isEmpty(this.tfPainKind)||
		if (Methods.isEmpty(this.tfIntensity) ||
			Methods.isEmpty(this.tfDuration))
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	protected void setData(String generalPos, String specificPos, String verySpecificPos)
	{
		this.comboGeneralPos.setSelectedItem(generalPos);
		this.generalPos.changeLocation(this.specificPos, generalPos);
		this.generalPos.setSelected(specificPos);
		this.specificPos.setSelected(verySpecificPos);
		this.revalidate();
		this.repaint();
	}
	
	//Interfaces
	private ActionListener generalPositionListener = new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					JComboBox combo = (JComboBox)e.getSource();
					String item = combo.getSelectedItem().toString();
					changeGeneralSelectionOptions(getLocationIdentifier(item));
					changeSpecificSelectionOptions(generalPos.getSelected());
				}
			};

	@Override
	public void focusGained(FocusEvent e){}

	@Override
	public void focusLost(FocusEvent e)
	{
		try
		{
			int intensity = Integer.parseInt(Methods.getTextData(this.tfIntensity));
			if (intensity < 0)
			{
				this.tfIntensity.setText("0");
			}
			else if (intensity > 10)
			{
				this.tfIntensity.setText("10");
			}
		}
		catch (Exception ex)
		{
			this.tfIntensity.setText("");
		}
	}
}
