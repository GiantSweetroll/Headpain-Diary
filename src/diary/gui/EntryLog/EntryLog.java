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

import diary.constants.Constants;
import diary.constants.Globals;
import diary.constants.PainDataIdentifier;
import diary.constants.XMLIdentifier;
import diary.data.PainEntryData;
import diary.gui.ActivePatientPanel;
import diary.gui.CustomDialog;
import diary.gui.DatePanel;
import diary.gui.MainFrame;
import diary.history.HistoryPanel;
import diary.methods.FileOperation;
import diary.methods.Methods;
import diary.patientdata.PatientData;
import giantsweetroll.GDateManager;
import giantsweetroll.Misc;
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
	private JPanel panelBelow;
	private JPanel panelBelowLeft, panelBelowRight;
	
	private JScrollPane scrollCenter, scrollComments;
	
	private JLabel labTitle, labDate, labStartTime, labActivity, labRecentMedication, labComments, labPainKind, labIntensity, labIntensityDesc, labDuration;
	
	private PainLocationSelectionPanel painLocation;
	private DatePanel panelDate;
	private TimePanel panelTime;
	private ActivePatientPanel activePatientPanel;
	private HistoryPanel historyRecentMedication;
	
	private JFormattedTextField tfIntensity, tfDuration;
	private JTextField tfActivity, tfPainKind;
	private JComboBox<String> comboActivity, comboPainKind, comboDurationUnit;
	private JTextArea taComments;
	private JButton butBack, butFinish;
		
	private GridBagConstraints c;
	
	private boolean isNewEntry;
	private PainEntryData oldEntry;
	private PatientData oldPatient;
	
	//Constants
	private final String BACK = "back";
	private final String FINISH = "finish";
	
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
		this.labDate = new JLabel(Methods.getLanguageText(XMLIdentifier.DATE_LABEL), SwingConstants.RIGHT);
		this.panelDate = new DatePanel(true);
		this.labStartTime = new JLabel(Methods.getLanguageText(XMLIdentifier.START_TIME_LABEL), SwingConstants.RIGHT);
		this.panelTime = new TimePanel(true);
		this.painLocation = new PainLocationSelectionPanel();
		this.labPainKind = new JLabel(Methods.getLanguageText(XMLIdentifier.KIND_OF_HEADPAIN_LABEL), SwingConstants.RIGHT);
		this.comboPainKind = new JComboBox<String>(Constants.DEFAULT_PAIN_KIND);
		this.tfPainKind = new JTextField(10);
		this.labIntensity = new JLabel(Methods.getLanguageText(XMLIdentifier.INTENSITY_LABEL), SwingConstants.RIGHT);
		this.tfIntensity = new JFormattedTextField(Constants.AMOUNT_FORMAT);
		this.labIntensityDesc = new JLabel(Methods.getLanguageText(XMLIdentifier.INTENSITIY_DESCRIPTION_LABEL));
		this.labDuration = new JLabel(Methods.getLanguageText(XMLIdentifier.DURATION_LABEL), SwingConstants.RIGHT);
		this.tfDuration = new JFormattedTextField(Constants.AMOUNT_FORMAT);
		this.comboDurationUnit = new JComboBox<String>(Constants.DURATION_UNITS);
		this.labActivity = new JLabel(Methods.getLanguageText(XMLIdentifier.ACTIVITY_LABEL), SwingConstants.RIGHT);
		this.comboActivity = new JComboBox<String>(Constants.DEFAULT_ACTIVITIES);
		this.tfActivity = new JTextField("", 10);
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
		this.tfPainKind.setColumns(10);
		this.tfPainKind.setHorizontalAlignment(SwingConstants.CENTER);
		this.tfPainKind.setEditable(false);
		this.tfIntensity.setColumns(5);
		this.tfIntensity.setHorizontalAlignment(SwingConstants.CENTER);
		this.tfDuration.setColumns(5);
		this.tfDuration.setHorizontalAlignment(SwingConstants.CENTER);
		this.tfActivity.setEditable(false);
		this.tfActivity.setHorizontalAlignment(SwingConstants.CENTER);
		this.taComments.setBorder(this.tfActivity.getBorder());
		this.panelDate.setDefaultData(GDateManager.getCurrentDay(), GDateManager.getCurrentMonth(), GDateManager.getCurrentYear());
		this.panelDate.resetDefault();
		
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
		c.gridwidth = 1000;
		this.panelCenter.add(this.painLocation, c);			//Pain Location Selection
		Gbm.newGridLine(c);
		c.gridwidth = 1;
		this.panelCenter.add(this.labPainKind, c);			//Pain Kind
		Gbm.nextGridColumn(c);
		this.panelCenter.add(this.comboPainKind, c);		//Pain Kind Options
		Gbm.nextGridColumn(c);
		this.panelCenter.add(this.tfPainKind, c);			//Pain Kind Text Field
		Gbm.newGridLine(c);
		this.panelCenter.add(this.labIntensity, c);			//Intensity
		Gbm.nextGridColumn(c);
		this.panelCenter.add(this.tfIntensity, c);			//Intensity Text Field
		Gbm.nextGridColumn(c);
		this.panelCenter.add(this.labIntensityDesc, c);		//Intensity Description
		Gbm.newGridLine(c);
		this.panelCenter.add(this.labDuration, c);			//Duration
		Gbm.nextGridColumn(c);
		this.panelCenter.add(this.tfDuration, c);			//Duration Text Field
		Gbm.nextGridColumn(c);
		this.panelCenter.add(this.comboDurationUnit, c);	//Duration unit
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
		
//		this.getNyeriAmount();
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
		this.butBack.addActionListener(this);
		this.butBack.setActionCommand(this.BACK);
		this.butFinish.addActionListener(this);
		this.butFinish.setActionCommand(this.FINISH);
		this.comboPainKind.addActionListener(this.painKindListener);
		this.comboActivity.addActionListener(this.activityListener);
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
			
			//Pain Location
			List<String> locations = this.painLocation.getSelectedPositions();
			if (this.painLocation.presetLocationSelected())
			{
				for (int i=0; i<locations.size(); i++)
				{
					this.appendToRootNode(doc, rootElement, PainDataIdentifier.PAIN_LOCATION_PRESET, locations.get(i), PainDataIdentifier.PAIN_LOCATION_ID, Integer.toString(i));
				}
				this.appendToRootNode(doc, rootElement, PainDataIdentifier.PAIN_LOCATION_CUSTOM, "", PainDataIdentifier.PAIN_LOCATION_ID, "0");
			}
			else
			{
				for (int i=0; i<locations.size(); i++)
				{
					this.appendToRootNode(doc, rootElement, PainDataIdentifier.PAIN_LOCATION_CUSTOM, locations.get(i), PainDataIdentifier.PAIN_LOCATION_ID, Integer.toString(i));
				}
				this.appendToRootNode(doc, rootElement, PainDataIdentifier.PAIN_LOCATION_PRESET, "", PainDataIdentifier.PAIN_LOCATION_ID, "0");
			}
			
			//Other
			if (Methods.isSelectedItem(this.comboPainKind, Methods.getLanguageText(XMLIdentifier.OTHER_TEXT), false))
			{
				this.appendToRootNode(doc, rootElement, PainDataIdentifier.PAIN_KIND, Methods.getTextData(this.tfPainKind));
			}
			else
			{
				this.appendToRootNode(doc, rootElement, PainDataIdentifier.PAIN_KIND, Methods.convertPainKindLanguageToID(this.comboPainKind.getSelectedItem().toString()));
			}
			this.appendToRootNode(doc, rootElement, PainDataIdentifier.INTENSITY, Methods.getTextData(this.tfIntensity));
			this.appendToRootNode(doc, rootElement, PainDataIdentifier.DURATION, Methods.getTextData(this.tfDuration));
			this.appendToRootNode(doc, rootElement, PainDataIdentifier.ACTIVITY, Methods.convertActivityLanguageToID(this.comboActivity.getSelectedItem().toString()));
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
	private void appendToRootNode(Document doc, Element rootElement, String key, String content, String attr, String attrContent)
	{
		Element element = doc.createElement(key);
		element.setAttribute(attr, attrContent);
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
		if (!this.activityFilled() || Methods.isEmpty(this.tfIntensity) || Methods.isEmpty(this.tfDuration))
		{
			return false;
		}
		else
		{
			if (Misc.getItem(this.comboPainKind).toString().equals(XMLIdentifier.OTHER_TEXT))
			{
				if (Methods.isEmpty(this.tfPainKind))
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
		
		this.painLocation.setSelectedPosition(entry);
		
		String painKind = entry.getDataMap().get(PainDataIdentifier.PAIN_KIND).toString();
		if (Methods.isPartOfDefaultPainKind(painKind))
		{
			this.comboPainKind.setSelectedItem(painKind);
		}
		else
		{
			this.comboPainKind.setSelectedItem(Methods.getLanguageText(XMLIdentifier.OTHER_TEXT));
			this.tfPainKind.setText(painKind);
		}
		this.tfIntensity.setText(entry.getDataMap().get(PainDataIdentifier.INTENSITY).toString());
		
		//Duration unit handling
		String duration = entry.getDataMap().get(PainDataIdentifier.DURATION).toString();
		double durationValue = Double.parseDouble(duration);
		String durationUnit = Methods.getLanguageText(XMLIdentifier.DURATION_UNIT_SECONDS_TEXT);
		if (durationValue>=3600d)	//Hours
		{
			durationValue = durationValue/3600d;
			duration = Double.toString(durationValue);
			durationUnit = Methods.getLanguageText(XMLIdentifier.DURATION_UNIT_HOURS_TEXT);
		}
		else if (durationValue<3600 && durationValue>=60)		//Minutes
		{
			durationValue = durationValue/60d;
			duration = Double.toString(durationValue);
			durationUnit = Methods.getLanguageText(XMLIdentifier.DURATION_UNIT_MINUTES_TEXT);
		}
		this.tfDuration.setText(duration);
		this.comboDurationUnit.setSelectedItem(durationUnit);
		
		String activity = entry.getDataMap().get(PainDataIdentifier.ACTIVITY).toString();
		if (Methods.isPartOfDefaultActivity(activity))
		{
			this.comboActivity.setSelectedItem(activity);
		}
		else
		{
			this.comboActivity.setSelectedIndex(Constants.DEFAULT_ACTIVITIES.length-1);
			this.tfActivity.setText(activity);
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
	
	//Interfaces
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
			
	private ActionListener painKindListener = new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					String item = Misc.getItem(comboPainKind).toString();
					if (item.equals(Methods.getLanguageText(XMLIdentifier.OTHER_TEXT)))
					{
						tfPainKind.setEditable(true);
					}
					else
					{
						tfPainKind.setEditable(false);
						tfPainKind.setText("");
					}
				}
			};
}
