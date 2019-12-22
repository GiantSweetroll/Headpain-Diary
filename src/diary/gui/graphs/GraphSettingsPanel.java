package diary.gui.graphs;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import diary.constants.XMLIdentifier;
import diary.interfaces.LanguageListener;
import diary.methods.Methods;

public class GraphSettingsPanel extends JPanel implements LanguageListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6912507407501414504L;
	
	private JCheckBox enableDataValues, displayVoidData, displayDataPoints;
	private boolean drawBorder;
	
	protected GraphSettingsPanel(boolean drawBorder)
	{
		this.init(drawBorder);
	}
	
	private void init(boolean drawBorder)
	{
		//Initialization
		this.enableDataValues = new JCheckBox(Methods.getLanguageText(XMLIdentifier.GRAPH_SETTINGS_ENABLE_DATA_VALUES));
		this.displayVoidData = new JCheckBox(Methods.getLanguageText(XMLIdentifier.GRAPH_SETTINGS_DISPLAY_VOID_DATA));
		this.displayDataPoints = new JCheckBox(Methods.getLanguageText(XMLIdentifier.GRAPH_SETTINGS_DISPLAY_DATA_POINTS));
		this.drawBorder = drawBorder;
		
		//Properties
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setOpaque(false);
		this.enableDataValues.setOpaque(false);
		this.displayDataPoints.setOpaque(false);
		this.displayVoidData.setOpaque(false);
		if (this.drawBorder)
		{
			this.setBorder(BorderFactory.createTitledBorder(Methods.getLanguageText(XMLIdentifier.SETTINGS_BUTTON_TEXT)));
		}
		
		//add to panel
		this.add(this.enableDataValues);
		this.add(this.displayVoidData);
		this.add(this.displayDataPoints);
	}
	
	protected boolean isDataValuesEnabled()
	{
		return this.enableDataValues.isSelected();
	}
	protected void setDataValuesEnabled(boolean b)
	{
		this.enableDataValues.setSelected(b);
	}
	
	protected boolean isDisplayVoidData()
	{
		return this.displayVoidData.isSelected();
	}
	protected void setDisplayVoidData(boolean b)
	{
		this.displayVoidData.setSelected(b);
	}
	
	protected boolean isDisplayDataPoints()
	{
		return this.displayDataPoints.isSelected();
	}
	protected void setDisplayDataPoints(boolean b)
	{
		this.displayDataPoints.setSelected(b);
	}
	
	protected boolean isDrawBorder()
	{
		return this.drawBorder;
	}
	protected void setDrawBorder(boolean drawBorder)
	{
		this.drawBorder = drawBorder;
	}
	
	@Override
	public void revalidateLanguage() 
	{
		if (this.drawBorder)
		{
			this.setBorder(BorderFactory.createTitledBorder(Methods.getLanguageText(XMLIdentifier.SETTINGS_BUTTON_TEXT)));
		}
		this.displayDataPoints.setText(Methods.getLanguageText(XMLIdentifier.GRAPH_SETTINGS_DISPLAY_DATA_POINTS));
		this.enableDataValues.setText(Methods.getLanguageText(XMLIdentifier.GRAPH_SETTINGS_ENABLE_DATA_VALUES));
		this.displayVoidData.setText(Methods.getLanguageText(XMLIdentifier.GRAPH_SETTINGS_DISPLAY_VOID_DATA));
	}
}
