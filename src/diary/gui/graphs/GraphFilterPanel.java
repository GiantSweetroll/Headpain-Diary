package diary.gui.graphs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import diary.constants.Globals;
import diary.constants.XMLIdentifier;
import diary.history.HistoryPanel;
import diary.methods.Methods;
import giantsweetroll.gui.swing.Gbm;

public class GraphFilterPanel extends JPanel
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
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.fill = GridBagConstraints.BOTH;
		this.add(this.checkRecMed, c);			//Medication
		Gbm.nextGridColumn(c);
		this.add(this.medHistory, c);			//Medication History
	}
	
	//Methods
	public boolean isRecentMedicationSelected()
	{
		return this.checkRecMed.isSelected();
	}

	public String getRecentMedicationFilter()
	{
		return this.medHistory.getItem();
	}
}
