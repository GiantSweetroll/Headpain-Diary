package diary.patientdata;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.LinkedHashMap;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.PlainDocument;

import diary.constants.Constants;
import diary.constants.XMLIdentifier;
import diary.gui.DatePanel;
import diary.gui.WrappableJLabel;
import diary.interfaces.LanguageListener;
import diary.methods.Methods;
import giantsweetroll.date.Date;
import giantsweetroll.filters.IntegerFilter;
import giantsweetroll.gui.swing.Gbm;
import giantsweetroll.gui.swing.ScrollPaneManager;
import giantsweetroll.gui.swing.TextAreaManager;

public class PatientDataForm extends JPanel implements LanguageListener, ItemListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2197578997400146750L;
	
	private JLabel labMedID, labName, labDOB, labPrevHeadpain, labFreqPainLastMonthUnit, labDaysActivityDisturbedUnit, labAgo, labNotes;
	private WrappableJLabel labFreqPainLastMonth, labDaysActivityDisturbed, labHasHeadpainHistory;
	private JTextField tfMedID, tfName, tfFreqPainLastMonth, tfDaysActivityDisturbed, tfHeadPainSince;
	private DatePanel panelDOB;
	private JRadioButton radYes, radNo;
	private ButtonGroup groupHasPrevHeadPain;
	private JComboBox<String> comboHeadPainSince;
	private JTextArea taNotes;
	private JScrollPane scrollNotes;
	
	public PatientDataForm(boolean enable)
	{
		this.init();
		this.enableComponents(enable);
	}
	public PatientDataForm(PatientData patientData, boolean enable)
	{
		this.init();
		
		this.setData(patientData);
		this.enableComponents(enable);
	}
	
	//Methods
	private void enableComponents(boolean enable)
	{
		this.tfMedID.setEditable(enable);
		this.tfName.setEditable(enable);
		this.panelDOB.enableComponents(enable);
		this.radYes.setEnabled(enable);
		this.radNo.setEnabled(enable);
		boolean b = this.radYes.isSelected();
		this.tfHeadPainSince.setEditable(b);
		this.tfFreqPainLastMonth.setEditable(b);
		this.tfDaysActivityDisturbed.setEditable(b);
		this.comboHeadPainSince.setEnabled(b);
		this.taNotes.setEnabled(enable);
	}
	private void init()
	{
		//Initialization
		this.labMedID = new JLabel(Methods.createTextWithRequiredIdentifier(Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_MED_ID_LABEL)), SwingConstants.RIGHT);
		this.tfMedID = new JTextField(10);
		this.labName = new JLabel (Methods.createTextWithRequiredIdentifier(Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_NAME_LABEL)), SwingConstants.RIGHT);
		this.tfName = new JTextField (10);
		this.labDOB = new JLabel (Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_DOB_LABEL), SwingConstants.RIGHT);
		this.panelDOB = new DatePanel(true);
		this.labPrevHeadpain = new JLabel (Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_PREVIOUS_HEADPAIN_LABEL), SwingConstants.RIGHT);
		this.radYes = new JRadioButton(Methods.getLanguageText(XMLIdentifier.YES_TEXT));
		this.radNo = new JRadioButton(Methods.getLanguageText(XMLIdentifier.NO_TEXT));
		this.groupHasPrevHeadPain = new ButtonGroup();
		this.labFreqPainLastMonth = new WrappableJLabel(Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_FREQUENCY_PAIN_LAST_MONTH_LABEL), ComponentOrientation.LEFT_TO_RIGHT);
		this.labDaysActivityDisturbed = new WrappableJLabel(Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_DAYS_ACTIVITY_DISTURBED_LABEL), ComponentOrientation.LEFT_TO_RIGHT);
		this.tfFreqPainLastMonth = new JTextField(5);
		this.tfDaysActivityDisturbed = new JTextField(5);
		this.labFreqPainLastMonthUnit = new JLabel(Methods.getLanguageText(XMLIdentifier.TIMES_PER_MONTH_TEXT));
		this.labDaysActivityDisturbedUnit = new JLabel(Methods.getLanguageText(XMLIdentifier.DAYS_PER_MONTH_TEXT));
		this.tfHeadPainSince = new JTextField(5);
		this.labHasHeadpainHistory = new WrappableJLabel(Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_HAS_PREVIOUS_HEAD_PAIN_HISTORY_LABEL));
		this.labAgo = new JLabel(Methods.getLanguageText(XMLIdentifier.AGO_TEXT));
		this.comboHeadPainSince = new JComboBox<String>(Methods.getHeadPainSinceOptions());
		this.labNotes = new JLabel(Methods.getLanguageText(XMLIdentifier.NOTES_TEXT), SwingConstants.RIGHT);
		this.taNotes = new JTextArea(10, 10);
		this.scrollNotes = ScrollPaneManager.generateDefaultScrollPane(this.taNotes, 10, 10);
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		this.tfMedID.setHorizontalAlignment(SwingConstants.CENTER);
		this.tfMedID.setToolTipText(Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_MED_ID_TOOLTIP_TEXT));
		this.tfMedID.setBackground(Color.WHITE);
		this.tfName.setHorizontalAlignment(SwingConstants.CENTER);
		this.tfName.setBackground(Color.WHITE);
		this.groupHasPrevHeadPain.add(this.radYes);
		this.groupHasPrevHeadPain.add(this.radNo);
		this.radYes.addItemListener(this);
		this.radYes.setOpaque(false);
		this.radNo.addItemListener(this);
		this.radNo.setSelected(true);
		this.radNo.setOpaque(false);
		this.tfDaysActivityDisturbed.setHorizontalAlignment(SwingConstants.CENTER);
		((PlainDocument)this.tfDaysActivityDisturbed.getDocument()).setDocumentFilter(new IntegerFilter(0, 31));
		this.tfFreqPainLastMonth.setHorizontalAlignment(SwingConstants.CENTER);
		((PlainDocument)this.tfFreqPainLastMonth.getDocument()).setDocumentFilter(new IntegerFilter(0, Integer.MAX_VALUE));
		this.tfHeadPainSince.setHorizontalAlignment(SwingConstants.CENTER);
		((PlainDocument)this.tfHeadPainSince.getDocument()).setDocumentFilter(new IntegerFilter(0, Integer.MAX_VALUE));
		this.comboHeadPainSince.setBackground(Color.white);
		TextAreaManager.autoConfigureTextArea(this.taNotes, true);
		
		//add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_TOP_COMPONENT;
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(this.labMedID, c);					//Medical Record ID
		Gbm.nextGridColumn(c);
		c.gridwidth = 3;
		this.add(this.tfMedID, c);					//Medical Record ID Text Field
		Gbm.newGridLine(c);
		c.gridwidth = 1;
		c.insets = Constants.INSETS_GENERAL;
		this.add(this.labName, c);					//Name
		Gbm.nextGridColumn(c);
		c.gridwidth = 3;
		this.add(this.tfName, c);					//Name Text Field
		Gbm.newGridLine(c);
		c.gridwidth = 1;
		this.add(this.labDOB, c);					//DOB
		Gbm.nextGridColumn(c);
		c.gridwidth = 3;
		this.add(this.panelDOB, c);					//DOB Panel
		Gbm.newGridLine(c);
		c.gridwidth = 1;
		this.add(this.labHasHeadpainHistory, c);	//Has Previous Headpain Label
		Gbm.nextGridColumn(c);
		this.add(this.radYes, c);					//Previous Headpains Yes Radio Button
		Gbm.nextGridColumn(c);
		this.add(this.radNo, c);					//Previous Headpains No Radio Button
		Gbm.newGridLine(c);
		this.add(this.labPrevHeadpain, c);			//Has Head Pains Since
		Gbm.nextGridColumn(c);
		this.add(this.tfHeadPainSince, c);			//Has Head Pains Since Text Field
		Gbm.nextGridColumn(c);
		this.add(this.comboHeadPainSince, c);		//Has Head Pains Since Unit Options
		Gbm.nextGridColumn(c);
		this.add(this.labAgo, c);					//Ago label
		Gbm.newGridLine(c);
		c.gridheight = 2;
		this.add(this.labFreqPainLastMonth, c);		//Frequency of Head pains last month
		Gbm.nextGridColumn(c);
		c.gridheight = 1;
		this.add(this.tfFreqPainLastMonth, c);		//Frequency of Head Pains last month Text Field
		Gbm.nextGridColumn(c);
		this.add(this.labFreqPainLastMonthUnit, c);	//Frequency of Head Pains Last month unit
		Gbm.newGridLine(c);
		Gbm.newGridLine(c);
		c.gridheight = 2;
		this.add(this.labDaysActivityDisturbed, c);	//Days Activities Disrupted due to attack
		Gbm.nextGridColumn(c);
		c.gridheight = 1;
		this.add(this.tfDaysActivityDisturbed, c);	//Days Activities Disrupted due to attack Text Field
		Gbm.nextGridColumn(c);
		this.add(this.labDaysActivityDisturbedUnit, c);	//Days Activities Disrupted due to attack unit
		Gbm.newGridLine(c);
		Gbm.newGridLine(c);
		this.add(this.labNotes, c);					//Notes
		Gbm.nextGridColumn(c);
		c.gridwidth = 3;
		c.gridheight = 2;
		this.add(this.scrollNotes, c);					//Notes Text Area
	}
	public PatientData getData()
	{
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put(PatientData.MEDICAL_RECORD_ID, Methods.getTextData(this.tfMedID));
		map.put(PatientData.NAME, Methods.getTextData(this.tfName));
		Date dob = this.panelDOB.getDate();
		map.put(PatientData.DOB_DAY, Integer.toString(dob.getDay()));
		map.put(PatientData.DOB_MONTH, Integer.toString(dob.getMonth()));
		map.put(PatientData.DOB_YEAR, Integer.toString(dob.getYear()));
		if (this.radYes.isSelected())
		{
			map.put(PatientData.HAS_HEAD_PAIN_HISTORY, Boolean.toString(true));
		}
		else
		{
			map.put(PatientData.HAS_HEAD_PAIN_HISTORY, Boolean.toString(false));
		}
		map.put(PatientData.HEAD_PAINS_SINCE, this.tfHeadPainSince.getText().trim());
		String item = this.comboHeadPainSince.getSelectedItem().toString();
		if (item.equals(Methods.getLanguageText(XMLIdentifier.MONTH_TEXT)))
		{
			map.put(PatientData.HEAD_PAINS_SINCE_UNIT, PatientData.HEAD_PAINS_SINCE_UNIT_MONTH);
		}
		else if (item.equals(Methods.getLanguageText(XMLIdentifier.YEAR_TEXT)))
		{
			map.put(PatientData.HEAD_PAINS_SINCE_UNIT, PatientData.HEAD_PAINS_SINCE_UNIT_YEAR);
		}
		map.put(PatientData.FREQUENCY_HEAD_PAINS_LAST_MONTH, this.tfFreqPainLastMonth.getText().trim());
		map.put(PatientData.DAYS_ACTIVITIES_DISTURBED, this.tfDaysActivityDisturbed.getText().trim());
		map.put(PatientData.NOTES, this.taNotes.getText().trim());
		
		return new PatientData(map);
	}
	public boolean isEmptyID()
	{
		if (Methods.getTextData(this.tfMedID).equals(""))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public boolean isEmptyName()
	{
		if (Methods.getTextData(this.tfName).equals(""))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public void setData(PatientData patient)
	{
		this.tfMedID.setText(patient.getID());
		this.tfName.setText(patient.getName());
		this.panelDOB.setDate(patient.getDOB());
		this.panelDOB.setAsDefaultDataThis();
		boolean hasHeadPainHistory = patient.hasHeadPainsHistory();
		if (hasHeadPainHistory)
		{
			this.radYes.setSelected(true);
			this.tfHeadPainSince.setText(patient.getHeadPainsSince());
			this.comboHeadPainSince.setSelectedItem(patient.getHeadPainsSinceUnit());
			this.tfFreqPainLastMonth.setText(patient.getPainFrequencyLastMonth());
			this.tfDaysActivityDisturbed.setText(patient.getDaysActivitiesDisturbedLastMonth());
		}
		else
		{
			this.radNo.setSelected(true);
		}
		this.taNotes.setText(patient.getNotes());
		this.revalidate();
		this.repaint();
	}
	
	@Override
	public void revalidateLanguage() 
	{
		this.labMedID.setText(Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_MED_ID_LABEL));
		this.labName.setText(Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_NAME_LABEL));
		this.labDOB.setText(Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_DOB_LABEL));
		this.labPrevHeadpain.setText(Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_PREVIOUS_HEADPAIN_LABEL));
		this.labHasHeadpainHistory.setText(Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_HAS_PREVIOUS_HEAD_PAIN_HISTORY_LABEL));
		this.radYes.setText(Methods.getLanguageText(XMLIdentifier.YES_TEXT));
		this.radNo.setText(Methods.getLanguageText(XMLIdentifier.NO_TEXT));
		this.labFreqPainLastMonth.setText(Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_FREQUENCY_PAIN_LAST_MONTH_LABEL));
		this.labDaysActivityDisturbed.setText(Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_DAYS_ACTIVITY_DISTURBED_LABEL));
		this.labFreqPainLastMonthUnit.setText(Methods.getLanguageText(XMLIdentifier.TIMES_PER_MONTH_TEXT));
		this.labDaysActivityDisturbedUnit.setText(Methods.getLanguageText(XMLIdentifier.DAYS_PER_MONTH_TEXT));
		this.comboHeadPainSince.setModel(new DefaultComboBoxModel<String>(Methods.getHeadPainSinceOptions()));
		this.labNotes.setText(Methods.getLanguageText(XMLIdentifier.NOTES_TEXT));
	}
	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		if (this.radYes.isSelected())
		{
			this.tfHeadPainSince.setEditable(true);
			this.tfFreqPainLastMonth.setEditable(true);
			this.tfDaysActivityDisturbed.setEditable(true);
			this.comboHeadPainSince.setEnabled(true);
		}
		else if (this.radNo.isSelected())
		{
			this.tfHeadPainSince.setEditable(false);
			this.tfHeadPainSince.setText("");
			this.tfFreqPainLastMonth.setEditable(false);
			this.tfFreqPainLastMonth.setText("");
			this.tfDaysActivityDisturbed.setEditable(false);
			this.tfDaysActivityDisturbed.setText("");
			this.comboHeadPainSince.setEnabled(false);
		}
	}
}
