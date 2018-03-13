package diary;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import diary.constants.XMLIdentifier;
import diary.methods.Methods;

public class ImageExportPanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5995925028433994067L;
	
	private JPanel panelTop;
	
	//Constructors
	public ImageExportPanel(String name, String medID, String dateRange, String medication, JComponent component)
	{
		//Initialization
		this.initPanelTop(name, medID, dateRange, medication);
		
		//Properties
		this.setBackground(Color.WHITE);
		this.setLayout(new BorderLayout());
		
		//Add to panel
		this.add(this.panelTop, BorderLayout.NORTH);
		this.add(component, BorderLayout.CENTER);
	}
	
	//Methods
	private void initPanelTop(String name, String medID, String dateRange, String medication)
	{
		//Initialization
		this.panelTop = new JPanel();
		JLabel labName = new JLabel(Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_NAME_LABEL) + ": " + name, SwingConstants.LEFT);
		JLabel labMedID = new JLabel (Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_MED_ID_LABEL) + ": " + medID, SwingConstants.LEFT);
		JLabel labDateRange = new JLabel (Methods.getLanguageText(XMLIdentifier.DATE_RANGE_TEXT) + ": " + dateRange, SwingConstants.LEFT);
		JLabel labMedication = new JLabel (Methods.getLanguageText(XMLIdentifier.RECENT_MEDICATION_LABEL) + ": " + medication, SwingConstants.LEFT);
		
		//Properties
		this.panelTop.setLayout(new BoxLayout(this.panelTop, BoxLayout.Y_AXIS));
		this.panelTop.setOpaque(false);
		
		//Add to panel
		this.panelTop.add(labName);			//Name
		this.panelTop.add(labMedID);		//Med ID
		this.panelTop.add(labDateRange);	//Date Range
		this.panelTop.add(labMedication);	//Medication
	}
}
