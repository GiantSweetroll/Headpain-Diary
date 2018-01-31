package diary.gui.graphs;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import diary.constants.Constants;
import diary.constants.XMLIdentifier;

public class GraphSettingsPanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6912507407501414504L;
	
	private JCheckBox enableDataValues, displayVoidData, displayDataPoints;
	
	protected GraphSettingsPanel()
	{
		this.init();
	}
	
	private void init()
	{
		//Initialization
		this.enableDataValues = new JCheckBox(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.GRAPH_SETTINGS_ENABLE_DATA_VALUES));
		this.displayVoidData = new JCheckBox(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.GRAPH_SETTINGS_DISPLAY_VOID_DATA));
		this.displayDataPoints = new JCheckBox(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.GRAPH_SETTINGS_DISPLAY_DATA_POINTS));
		
		//Properties
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setOpaque(false);
		this.setBorder(BorderFactory.createTitledBorder(Constants.LANGUAGE.getTextMap().get(XMLIdentifier.SETTINGS_BUTTON_TEXT)));
		
		//add to panel
		this.add(this.enableDataValues);
		this.add(this.displayVoidData);
		this.add(this.displayDataPoints);
	}
	
	protected boolean isDataValuesEnabled()
	{
		return this.enableDataValues.isSelected();
	}
	
	protected boolean isDisplayVoidData()
	{
		return this.displayVoidData.isSelected();
	}
	
	protected boolean isDisplayDataPoints()
	{
		return this.displayDataPoints.isSelected();
	}
}
