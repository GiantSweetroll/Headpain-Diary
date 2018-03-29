package diary.gui.graphs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import diary.constants.Globals;
import diary.constants.XMLIdentifier;
import diary.history.HistoryPanel;
import diary.methods.Methods;
import diary.patientdata.PatientData;
import giantsweetroll.gui.swing.Gbm;

public class GraphFilterPanel extends JPanel implements ItemListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1467377862622026563L;

	private HistoryPanel medHistory;
	private JCheckBox checkRecMed;
	
	public GraphFilterPanel()
	{
		//Initialization
		this.checkRecMed = new JCheckBox(Methods.getLanguageText(XMLIdentifier.RECENT_MEDICATION_LABEL));
		this.medHistory = new HistoryPanel(Globals.HISTORY_RECENT_MEDICATION);
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		this.setBorder(BorderFactory.createTitledBorder(Methods.getLanguageText(XMLIdentifier.FILTER_TEXT)));
		this.checkRecMed.setOpaque(false);
		this.checkRecMed.addItemListener(this);
		this.medHistory.setEnabled(false);
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.fill = GridBagConstraints.BOTH;
		this.add(this.checkRecMed, c);			//Medication
		Gbm.nextGridColumn(c);
		this.add(this.medHistory, c);			//Medication History
	}
	
	//Methods
	public void refresh(PatientData patient)
	{
//		this.medHistory.refresh(Globals.HISTORY_RECENT_MEDICATION);
		this.medHistory.refresh(Globals.HISTORY_RECENT_MEDICATION, patient);
	}
	public boolean isRecentMedicationSelected()
	{
		return this.checkRecMed.isSelected();
	}
	public void setRecentMedicationSelected(boolean select)
	{
		this.checkRecMed.setSelected(select);
	}
	public String getRecentMedicationFilter()
	{
		if(this.isRecentMedicationSelected()) 
		{
			return this.medHistory.getItem();
		}
		else
		{
			return "";
		}
	}

	//Interface
	@Override
	public void itemStateChanged(ItemEvent e)
	{
		this.medHistory.setEnabled(this.checkRecMed.isSelected());
	}
}
