package diary.gui.EntryLog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import diary.PainEntryData;
import diary.constants.Constants;
import diary.constants.Globals;
import diary.constants.PainDataIdentifier;
import diary.constants.XMLIdentifier;
import diary.gui.ActivePatientPanel;
import diary.gui.CustomDialog;
import diary.gui.DatePanel;
import diary.gui.MainFrame;
import diary.history.HistoryPanel;
import diary.methods.FileOperation;
import diary.methods.Methods;
import diary.patientdata.PatientData;
import giantsweetroll.GDateManager;
import giantsweetroll.VectorInt;
import giantsweetroll.gui.swing.Gbm;
import giantsweetroll.gui.swing.ScrollPaneManager;
import giantsweetroll.message.MessageManager;
import giantsweetroll.xml.dom.XMLManager;

public class EntryLog extends JPanel implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5981497184958769730L;
	
	private JPanel panelTitle;
	private JPanel panelCenter;
//	private JScrollPane panelNyeriTypes;
	private JPanel panelBelow;
	private JPanel panelBelowLeft, panelBelowRight;
	
	private JScrollPane scrollCenter, scrollComments;
	
	private JLabel labTitle, labDate, labStartTime, /*labNyeriAmount,*/ labActivity, labRecentMedication, labComments, labPainKind, labIntensity, labIntensityDesc, labDuration;
	
	private PainLocationPresetSelectionPanel presetLocations;
	private DatePanel panelDate;
	private TimePanel panelTime;
	private ActivePatientPanel activePatientPanel;
	private HistoryPanel historyRecentMedication;
	
//	private JFormattedTextField tfNyeriAmount;
	private JTextField tfActivity, tfPainKind, tfDuration, tfIntensity;
	private JComboBox<String> comboActivity, comboPainKind, comboDurationUnit;
	private JTextArea taComments;
	private JButton butBack, butFinish/*, butConfirm*/;
		
	private GridBagConstraints c;
	
	private boolean isNewEntry;
	private PainEntryData oldEntry;
	private PatientData oldPatient;
	
	//Constants
	private final String BACK = "back";
	private final String FINISH = "finish";
	
	private int nyeriAmount;
	
	//Vectors
	private VectorInt vecPainLocationPanel;
	
	//Constructors
	public EntryLog()
	{
		this.createAndShowGUI();
		this.isNewEntry = true;
	}
	public EntryLog(PatientData patient, PainEntryData entry)
	{
		this.createAndShowGUI();
		this.oldEntry = entry;
		this.oldPatient = patient;
		this.fillData(patient, entry);
		this.revalidate();
		this.repaint();
		this.isNewEntry = false;
	}
	
	//Initialization of GUI
	private void createAndShowGUI()
	{
		//Initialize
		this.initPanelTitle();
		this.initPanelCenter();
		this.initPanelBelow();
		this.initScrollPanes();
		
		//properties
		this.setLayout(new BorderLayout());
		this.setOpaque(false);
		
		//add
		this.add(this.panelTitle, BorderLayout.NORTH);
		this.add(this.scrollCenter, BorderLayout.CENTER);
		this.add(this.panelBelow, BorderLayout.SOUTH);
		
		//Initialize Listeners
		this.configureListenersForMembers();
		
		this.revalidate();
		this.repaint();
	}
	private void initPanelTitle()
	{
		//Initialization
		this.panelTitle = new JPanel();
		this.labTitle = new JLabel(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_TITLE));
		
		//Properties
		this.labTitle.setFont(Constants.FONT_TITLE);
		this.panelTitle.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.panelTitle.setOpaque(false);
		
		//add to panel
		this.panelTitle.add(this.labTitle);
	}
	private void initPanelCenter()
	{
		//Initialization
		this.panelCenter = new JPanel();
		this.c = new GridBagConstraints();
		this.activePatientPanel = new ActivePatientPanel();
		this.labDate = new JLabel(Constants.REQUIRED_IDENTIFIER + Methods.getLanguageText(XMLIdentifier.DATE_LABEL), SwingConstants.RIGHT);
		this.panelDate = new DatePanel(true);
		this.labStartTime = new JLabel(Constants.REQUIRED_IDENTIFIER + Methods.getLanguageText(XMLIdentifier.START_TIME_LABEL), SwingConstants.RIGHT);
		this.panelTime = new TimePanel(true);
/*		this.labNyeriAmount = new JLabel (Constants.REQUIRED_IDENTIFIER + Methods.getLanguageText(XMLIdentifier.HEADPAIN_LOCATION_AMOUNT_LABEL), SwingConstants.RIGHT);
		this.tfNyeriAmount = new JFormattedTextField(Constants.AMOUNT_FORMAT);
		this.butConfirm = new JButton(Methods.getLanguageText(XMLIdentifier.CONFIRM_TEXT));		*/
		this.presetLocations = new PainLocationPresetSelectionPanel();
		this.labActivity = new JLabel(Constants.REQUIRED_IDENTIFIER + Methods.getLanguageText(XMLIdentifier.ACTIVITY_LABEL), SwingConstants.RIGHT);
		this.comboActivity = new JComboBox<String>(Constants.DEFAULT_ACTIVITIES);
		this.tfActivity = new JTextField("", 20);
		this.labRecentMedication = new JLabel(Methods.getLanguageText(XMLIdentifier.RECENT_MEDICATION_LABEL), SwingConstants.RIGHT);
		this.historyRecentMedication = new HistoryPanel(Globals.HISTORY_RECENT_MEDICATION);
		this.labComments = new JLabel(Methods.getLanguageText(XMLIdentifier.COMMENTS_LABEL), SwingConstants.RIGHT);
		this.taComments = new JTextArea(10, 35);
		this.scrollComments = ScrollPaneManager.generateDefaultScrollPane(this.taComments, 10, 10);
		
		//Properties
		this.panelCenter.setLayout(new GridBagLayout());
		this.panelCenter.setOpaque(false);
		this.activePatientPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.panelTime.setDefaultTime(GDateManager.getCurrentHour(), GDateManager.getCurrentMinute(), GDateManager.getCurrentSecond());
		this.panelTime.resetDefault();
/*		this.tfNyeriAmount.setColumns(5);
		this.tfNyeriAmount.setHorizontalAlignment(SwingConstants.CENTER);
		this.tfNyeriAmount.setText("1");	*/
		this.tfActivity.setEditable(false);
		this.tfActivity.setHorizontalAlignment(SwingConstants.CENTER);
		this.taComments.setBorder(this.tfActivity.getBorder());
		this.panelDate.setDefaultData(GDateManager.getCurrentDay(), GDateManager.getCurrentMonth(), GDateManager.getCurrentYear());
		this.panelDate.resetDefault();
		
//		this.panelNyeriTypes = new CollectivePainLocationDataScrollPane(Integer.parseInt(this.tfNyeriAmount.getText().trim()));
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_TOP_COMPONENT;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 1000;
		this.panelCenter.add(this.activePatientPanel, c);	//Active Patient Panel
		Gbm.newGridLine(c);
		c.insets = Constants.INSETS_GENERAL;
		c.gridwidth = 1;
		this.panelCenter.add(this.labDate, c);				//Date
		Gbm.nextGridColumn(c);
		c.gridwidth = 4;
		this.panelCenter.add(this.panelDate, c);			//Date Panel
		Gbm.newGridLine(c);
		c.gridwidth = 1;
		this.panelCenter.add(this.labStartTime, c);			//Start Time
		Gbm.nextGridColumn(c);
		c.gridwidth = 4;
		this.panelCenter.add(this.panelTime, c);			//Start Time Panel
		Gbm.newGridLine(c);
		c.gridwidth = 1;
		this.panelCenter.add(this.presetLocations, c);		//Preset Locations
		
		this.panelCenter.add(this.labNyeriAmount, c);		//Amount of head pain
		Gbm.nextGridColumn(c);
		this.panelCenter.add(this.tfNyeriAmount, c);		//Amount of head pain text field
		Gbm.nextGridColumn(c);
		this.panelCenter.add(this.butConfirm, c);			//Confirm button
		Gbm.newGridLine(c);
		c.gridwidth = 100;
	//	Gbm.nextGridColumn(c);
		this.vecPainLocationPanel = new VectorInt(c.gridx, c.gridy);
		this.panelCenter.add(this.panelNyeriTypes, c);		//Nyeri type scroll pane
		Gbm.newGridLine(c);
		c.gridwidth = 1;
		this.panelCenter.add(this.labActivity, c);			//Activity
		Gbm.nextGridColumn(c);
		this.panelCenter.add(this.comboActivity, c);		//Default Activities
		Gbm.nextGridColumn(c);
		this.panelCenter.add(this.tfActivity, c);			//Activity Text Field
		Gbm.newGridLine(c);
		c.gridwidth = 1;
		this.panelCenter.add(this.labRecentMedication, c);	//Recent Medication
		Gbm.nextGridColumn(c);
		this.panelCenter.add(this.historyRecentMedication, c);	//Recent Medication Historu Panel
		Gbm.newGridLine(c);
		this.panelCenter.add(this.labComments, c);			//Comments
		Gbm.nextGridColumn(c);
		c.gridwidth = 3;
		this.panelCenter.add(this.scrollComments, c);		//Comments Text Area
		
		this.getNyeriAmount();
	}
	private void initScrollPanes()
	{
		//Initialization
		this.scrollCenter = ScrollPaneManager.generateDefaultScrollPane(this.panelCenter, Constants.SCROLL_SPEED, Constants.SCROLL_SPEED);
	}
	private void initPanelBelowLeft()
	{
		//Initialization
		this.panelBelowLeft = new JPanel();
		this.butBack = new JButton(Methods.getLanguageText(XMLIdentifier.BACK_TEXT));
		
		//properties
		this.panelBelowLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		//add to panel
		this.panelBelowLeft.add(this.butBack);
	}
	private void initPanelBelowRight()
	{
		//Initialization
		this.panelBelowRight = new JPanel();
		this.butFinish = new JButton(Methods.getLanguageText(XMLIdentifier.FINISH_TEXT));
		
		//properties
		this.panelBelowRight.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		//add to panel
		this.panelBelowRight.add(this.butFinish);
	}
	private void initPanelBelow()
	{
		//Initialization
		this.panelBelow = new JPanel();
		this.initPanelBelowLeft();
		this.initPanelBelowRight();
		
		//Properties
		this.panelBelow.setLayout(new BorderLayout());
		
		//Add to panel
		this.panelBelow.add(this.panelBelowLeft, BorderLayout.WEST);
		this.panelBelow.add(this.panelBelowRight, BorderLayout.EAST);
	}
	private void configureListenersForMembers()
	{
//		this.tfNyeriAmount.getDocument().addDocumentListener(this);
		this.butBack.addActionListener(this);
		this.butBack.setActionCommand(this.BACK);
		this.butFinish.addActionListener(this);
		this.butFinish.setActionCommand(this.FINISH);
		this.butConfirm.addActionListener(this.nyeriAmountListener);
		this.comboActivity.addActionListener(this.activityListener);
	}
	private void getNyeriAmount()
	{
		int amount = 1;
		try
		{
			if (this.tfNyeriAmount.getText().trim().equals(""))
			{
				amount = 1;
				this.tfNyeriAmount.setText("1");
			}
			else
			{
				amount = Integer.parseInt(this.tfNyeriAmount.getText().trim().replaceAll(",", ""));
				if (amount <= 0)
				{
					amount = 1;
					this.tfNyeriAmount.setText("1");
				}
			}
		}
		catch (NullPointerException ex)
		{
			amount = 1;
		}
		
		this.nyeriAmount = amount;
	}
	
	//Methods
	protected Document createDataXMLDocument()
	{
		Document doc = null;
		
		try 
		{
			doc = XMLManager.createDocument();
			LinkedHashMap<String, String> mapTime = this.panelTime.getData();
			LinkedHashMap<String, String> mapDate = this.panelDate.getData();
			
			Element rootElement = doc.createElement(PainDataIdentifier.MASTER_NODE);
			
			//Date
			this.appendToRootNode(doc, rootElement, PainDataIdentifier.DATE_DAY, mapDate.get(PainDataIdentifier.DATE_DAY));
			this.appendToRootNode(doc, rootElement, PainDataIdentifier.DATE_MONTH, mapDate.get(PainDataIdentifier.DATE_MONTH));
			this.appendToRootNode(doc, rootElement, PainDataIdentifier.DATE_YEAR, mapDate.get(PainDataIdentifier.DATE_YEAR));
			
			//Time
			this.appendToRootNode(doc, rootElement, PainDataIdentifier.TIME_HOUR, mapTime.get(PainDataIdentifier.TIME_HOUR));
			this.appendToRootNode(doc, rootElement, PainDataIdentifier.TIME_MINUTE, mapTime.get(PainDataIdentifier.TIME_MINUTE));
			this.appendToRootNode(doc, rootElement, PainDataIdentifier.TIME_SECONDS, mapTime.get(PainDataIdentifier.TIME_SECONDS));
			
			this.appendToRootNode(doc, rootElement, PainDataIdentifier.PAIN_AMOUNT, Integer.toString(this.nyeriAmount));
			
			//Pain Locations
			rootElement.appendChild(((CollectivePainLocationDataScrollPane)this.panelNyeriTypes).getLocationsElement(doc));
			
			this.appendToRootNode(doc, rootElement, PainDataIdentifier.ACTIVITY, this.comboActivity.getSelectedItem().toString());
			this.appendToRootNode(doc, rootElement, PainDataIdentifier.ACTIVITY_DETAILS, Methods.getTextData(this.tfActivity));
			this.appendToRootNode(doc, rootElement, PainDataIdentifier.RECENT_MEDICATION, this.historyRecentMedication.getItem());
			this.appendToRootNode(doc, rootElement, PainDataIdentifier.COMMENTS, this.taComments.getText());
			
			doc.appendChild(rootElement);
		}
		catch (ParserConfigurationException e) 
		{
			e.printStackTrace();
		}
		
		
		return doc;
	}
	
	private void appendToRootNode(Document doc, Element rootElement, String key, String content)
	{
		Element element = doc.createElement(key);
		element.appendChild(doc.createTextNode(content));
		
		rootElement.appendChild(element);
	}
	
	private boolean activityShouldBeFilled()
	{
		if (this.selectedActivityIsOther())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	private boolean activityFilled()
	{
		if (this.activityShouldBeFilled())
		{
			if (Methods.isEmpty(this.tfActivity))
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		else
		{
			return true;
		}
	}
	
	private boolean allRequiredFieldsFilled()
	{
		if (Methods.isEmpty(this.tfNyeriAmount) ||
			!this.activityFilled() ||
			!((CollectivePainLocationDataScrollPane)this.panelNyeriTypes).allFieldsEntered())
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	private void fillData(PatientData patient, PainEntryData entry)
	{
		this.activePatientPanel.setSelectedPatient(patient);
		this.panelDate.setDate(entry.getDataMap().get(PainDataIdentifier.DATE_DAY).toString(), 
								entry.getDataMap().get(PainDataIdentifier.DATE_MONTH).toString(), 
								entry.getDataMap().get(PainDataIdentifier.DATE_YEAR).toString());
		this.panelDate.setAsDefaultDataThis();
		
		this.panelTime.setTime(entry.getDataMap().get(PainDataIdentifier.TIME_HOUR).toString(),
								entry.getDataMap().get(PainDataIdentifier.TIME_MINUTE).toString(),
								entry.getDataMap().get(PainDataIdentifier.TIME_SECONDS).toString());
		this.panelTime.setAsDefaultTimeThis();
		
		this.tfNyeriAmount.setText(entry.getDataMap().get(PainDataIdentifier.PAIN_AMOUNT).toString());
		this.refreshPainPositions();
		
		List<LinkedHashMap<String, Object>> painLocations = (List<LinkedHashMap<String, Object>>)entry.getDataMap().get(PainDataIdentifier.PAIN_LOCATIONS);
		for (int i=0; i<painLocations.size(); i++)
		{
			((CollectivePainLocationDataScrollPane)this.panelNyeriTypes).getPainPositions().get(i).setSelectedPosition(painLocations.get(i).get(PainDataIdentifier.GENERAL_POSITION).toString(),
																														painLocations.get(i).get(PainDataIdentifier.GENERAL_POSITION_2).toString(),
																														painLocations.get(i).get(PainDataIdentifier.SPECIFIC_LOCATION).toString());
			((CollectivePainLocationDataScrollPane)this.panelNyeriTypes).getPainPositions().get(i).setPainKind(painLocations.get(i).get(PainDataIdentifier.PAIN_KIND).toString());
			((CollectivePainLocationDataScrollPane)this.panelNyeriTypes).getPainPositions().get(i).setIntensity(painLocations.get(i).get(PainDataIdentifier.INTENSITY).toString());
			//Duration unit handling
			String duration = painLocations.get(i).get(PainDataIdentifier.DURATION).toString();
			double durationValue = Double.parseDouble(duration);
			String durationUnit = IndividualPainLocationDataPanel.SECONDS;
			if (durationValue>=3600d)	//Hours
			{
				durationValue = durationValue/3600d;
				duration = Double.toString(durationValue);
				durationUnit = IndividualPainLocationDataPanel.HOURS;
			}
			else if (durationValue<3600 && durationValue>=60)		//Minutes
			{
				durationValue = durationValue/60d;
				duration = Double.toString(durationValue);
				durationUnit = IndividualPainLocationDataPanel.MINUTES;
			}
			else
			{
				//Nothing, leave it at seconds
			}
			((CollectivePainLocationDataScrollPane)this.panelNyeriTypes).getPainPositions().get(i).setDuration(duration);
			((CollectivePainLocationDataScrollPane)this.panelNyeriTypes).getPainPositions().get(i).setDurationUnit(durationUnit);
		}
	//	this.tfActivity.setText(entry.getDataMap().get(PainDataIdentifier.ACTIVITY).toString());
		String activity = entry.getDataMap().get(PainDataIdentifier.ACTIVITY).toString();
		if (Methods.isPartOfDefaultActivity(activity))
		{
			this.comboActivity.setSelectedItem(entry.getDataMap().get(PainDataIdentifier.ACTIVITY).toString());
		}
		else
		{
			this.comboActivity.setSelectedIndex(Constants.DEFAULT_ACTIVITIES.length-1);
			this.tfActivity.setText(entry.getDataMap().get(PainDataIdentifier.ACTIVITY).toString());
		}
		try
		{
			this.tfActivity.setText(entry.getDataMap().get(PainDataIdentifier.ACTIVITY_DETAILS).toString());
		}
		catch(NullPointerException ex) {}
		this.taComments.setText(entry.getDataMap().get(PainDataIdentifier.COMMENTS).toString());
	}
	
	private boolean selectedActivityIsOther()
	{
		if (this.comboActivity.getSelectedIndex() == Constants.DEFAULT_ACTIVITIES.length-1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private void refreshPainPositions()
	{
		getNyeriAmount();
		
		panelCenter.remove(panelNyeriTypes);
		
		panelNyeriTypes = new CollectivePainLocationDataScrollPane(nyeriAmount);
		
		c.gridwidth = 1000;
		c.gridx = vecPainLocationPanel.x;
		c.gridy = vecPainLocationPanel.y;
		panelCenter.add(panelNyeriTypes, c);
		
		revalidate();
		repaint();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		switch (e.getActionCommand())
		{
			case BACK:
				MainFrame.changePanel(Globals.MAIN_MENU);
				break;
				
			case FINISH:
				if (this.allRequiredFieldsFilled())
				{
					PainEntryData entry = new PainEntryData(this.createDataXMLDocument());
					PatientData patient = this.activePatientPanel.getSelectedPatientData();
					
					if (FileOperation.entryExists(patient, entry) && this.isNewEntry)
					{
						int response = CustomDialog.showConfirmDialog(Methods.getLanguageText(XMLIdentifier.MESSAGE_OVERWRITE_CONFIRM_TITLE), 
																	Methods.getLanguageText(XMLIdentifier.MESSAGE_OVERWRITE_CONFIRM_TEXT));
						
						if (response == JOptionPane.YES_OPTION)
						{
							FileOperation.updateHistory(Globals.HISTORY_RECENT_MEDICATION, this.historyRecentMedication.getItem());
							FileOperation.exportPainData(patient, entry);
							MainFrame.changePanel(Globals.MAIN_MENU);
						}
					}
					else
					{
						FileOperation.updateHistory(Globals.HISTORY_RECENT_MEDICATION, this.historyRecentMedication.getItem());
						FileOperation.exportPainData(patient, entry);
						if (!this.isNewEntry)
						{
							if (!this.panelTime.sameAsDefault() || !this.panelDate.sameAsDefault())		//Check if the start time or date has been altered
							{
								FileOperation.deleteEntry(Methods.generatePainDataFilePathName(this.oldPatient, this.oldEntry));
							}
						}
						MainFrame.changePanel(Globals.MAIN_MENU);
					}
				}
				else
				{
					MessageManager.showErrorDialog(Methods.getLanguageText(XMLIdentifier.ERROR_REQUIRED_FIELDS_DIALOG_TEXT),
													Methods.getLanguageText(XMLIdentifier.ERROR_REQUIRED_FIELDS_DIALOG_TITLE));
				}
				break;
		}
	}
	
	private ActionListener nyeriAmountListener = new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					refreshPainPositions();
				}
			};
	
	private ActionListener activityListener = new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					int index = comboActivity.getSelectedIndex();
					if (index == Constants.DEFAULT_ACTIVITIES.length-1)
					{
						tfActivity.setEditable(true);
					}
					else
					{
						tfActivity.setEditable(false);
						tfActivity.setText("");
					}
				}
			};
}
