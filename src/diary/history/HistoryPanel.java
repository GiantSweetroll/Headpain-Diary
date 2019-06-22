package diary.history;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import diary.constants.Constants;
import diary.interfaces.GUIFunction;
import diary.interfaces.LanguageListener;
import diary.methods.Methods;
import diary.patientdata.PatientData;
import giantsweetroll.GGUtilities;
import giantsweetroll.gui.swing.Gbm;

public class HistoryPanel extends JPanel implements GUIFunction, ActionListener, LanguageListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2435079856803345604L;

	private HistoryComboBox comboHistory;
	private JTextField tfHistory;
	private History history;
	private String historyKey;
	
	//Constructors
	public HistoryPanel(History history, String patientDataConstant, String[] defaultOptions, boolean sorted, boolean haveNone)		//patientDataConstant are constants from PatientData class used for the recent selected options
	{
		this.historyKey = patientDataConstant;
		this.history = history;
		this.init(defaultOptions, sorted, haveNone);
		this.comboHistory.setModel(new DefaultComboBoxModel<String>(history.getHistory().toArray(new String[history.getHistory().size()])));
	}
	
	//Methods
	private void init(String[] defaultOptions, boolean sorted, boolean haveNone)
	{
		//Initialization
		this.comboHistory = new HistoryComboBox(this.history, defaultOptions, sorted, haveNone);
		this.tfHistory = new JTextField(10);
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		this.comboHistory.setBackground(Color.WHITE);
		this.comboHistory.addActionListener(this);
		this.tfHistory.setOpaque(false);
		this.tfHistory.addMouseListener(new MouseListener()
				{

					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mousePressed(MouseEvent e) 
					{
						if (!tfHistory.isEditable())
						{
							comboHistory.setSelectedToOther();
							tfHistory.setEditable(true);
						}
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
			
				});
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_TOP_COMPONENT;
		c.fill = GridBagConstraints.BOTH;
		this.add(this.comboHistory, c);					//History Combo Box
		Gbm.nextGridColumn(c);
		this.add(this.tfHistory, c);					//History Text Field
	}
	public void setActiveItem(String item)
	{
		if (this.history.itemExists(item))
		{
			this.comboHistory.setSelectedItem(item);
		}
		else
		{
			this.comboHistory.setSelectedToOther();
			this.tfHistory.setText(item);
		}
	}
	public String getItem()
	{
		if (this.comboHistory.otherOptionSelected())
		{
			return Methods.getTextData(this.tfHistory);
		}
		else if (this.comboHistory.noneOptionSelected())
		{
			return "";
		}
		else
		{
			return GGUtilities.getItem(this.comboHistory).toString();
		}
	}
	public void refresh(History history, PatientData patient)
	{
		history.refresh(patient);
		this.comboHistory.setHistory(history);
		this.comboHistory.refresh();
		try
		{
			this.comboHistory.setDefaultSelection(patient.getRecentSelectedOption(this.historyKey));
		}
		catch(NullPointerException ex) {}
		this.revalidate();
		this.repaint();
	}
	public boolean hasHistory()
	{
		return this.comboHistory.getItemCount()!=0;
	}
	public void revalidateLanguage(String[] options)
	{
		this.comboHistory.revalidateLanguage(options);
	}
	public int getSelectedIndex()
	{
		return this.comboHistory.getSelectedIndex();
	}
	public void setSelectedIndex(int i)
	{
		this.comboHistory.setSelectedIndex(i);
	}
	
	//Overridden Methods
	@Override
	public void setEnabled(boolean enabled)
	{
		super.setEnabled(enabled);
		
		this.comboHistory.setEnabled(enabled);
		this.tfHistory.setEnabled(enabled);
	}
	
	//Interfaces
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (this.comboHistory.otherOptionSelected())
		{
			this.tfHistory.setEditable(true);
		}
		else
		{
			this.tfHistory.setText("");
			this.tfHistory.setEditable(false);
		}
	}
	
	@Override
	public void resetDefaults()
	{
		this.comboHistory.resetDefaults();
	}

	@Override
	public void refresh() 
	{
		this.comboHistory.refresh();
	}

	@Override
	public void revalidateLanguage() 
	{
		this.refresh();
	}
}
