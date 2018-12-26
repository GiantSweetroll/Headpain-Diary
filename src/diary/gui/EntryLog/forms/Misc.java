package diary.gui.EntryLog.forms;

import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.PlainDocument;

import diary.constants.Constants;
import diary.constants.XMLIdentifier;
import diary.gui.WrappableJLabel;
import diary.methods.Methods;
import giantsweetroll.filters.IntegerFilter;
import giantsweetroll.gui.swing.Gbm;
import giantsweetroll.gui.swing.ScrollPaneManager;
import giantsweetroll.gui.swing.TextAreaManager;

public class Misc extends FormElement
{

	private JLabel labPrevPainHistory, labFreqPainLastMonthUnit, labDaysActivityDisturbedUnit;
	private WrappableJLabel labFreqPainLastMonth, labDaysActivityDisturbed;
	private JTextArea taPrevPainHistory;
	private JTextField tfFreqPainLastMonth, tfDaysActivityDisturbed;
	
	//Constructors
	public Misc()
	{
		super(Methods.getLanguageText(XMLIdentifier.MISC_TEXT), false);
		
		//Initialization
		this.labPrevPainHistory = new JLabel(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_MISC_PREVIOUS_HEAD_PAIN_HISTORY_LABEL), SwingConstants.RIGHT);
		this.labFreqPainLastMonth = new WrappableJLabel(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_MISC_FREQUENCY_PAIN_LAST_MONTH_LABEL), ComponentOrientation.LEFT_TO_RIGHT);
		this.labDaysActivityDisturbed = new WrappableJLabel(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_MISC_DAYS_ACTIVITY_DISTURBED_LABEL), ComponentOrientation.LEFT_TO_RIGHT);
		this.taPrevPainHistory = new JTextArea(15, 30);
		this.tfFreqPainLastMonth = new JTextField(5);
		this.tfDaysActivityDisturbed = new JTextField(5);
		this.labFreqPainLastMonthUnit = new JLabel(Methods.getLanguageText(XMLIdentifier.TIMES_PER_MONTH_TEXT));
		this.labDaysActivityDisturbedUnit = new JLabel(Methods.getLanguageText(XMLIdentifier.DAYS_PER_MONTH_TEXT));
		GridBagConstraints c = new GridBagConstraints();
		JScrollPane scroll = ScrollPaneManager.generateDefaultScrollPane(this.taPrevPainHistory, 10, 10);
		
		//Properties
		this.getPanel().setLayout(new GridBagLayout());
		TextAreaManager.autoConfigureTextArea(this.taPrevPainHistory, true);
		this.getFormTitleLabel().setHorizontalAlignment(SwingConstants.CENTER);
		this.tfDaysActivityDisturbed.setHorizontalAlignment(SwingConstants.CENTER);
		((PlainDocument)this.tfDaysActivityDisturbed.getDocument()).setDocumentFilter(new IntegerFilter(0, 31));
		this.tfFreqPainLastMonth.setHorizontalAlignment(SwingConstants.CENTER);
		((PlainDocument)this.tfFreqPainLastMonth.getDocument()).setDocumentFilter(new IntegerFilter(0, Integer.MAX_VALUE));
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.insets = Constants.INSETS_TITLE;
		c.gridwidth = 100;
		c.fill = GridBagConstraints.HORIZONTAL;
		this.getPanel().add(this.getFormTitleLabel(), c);				//Title
		c.gridwidth = 1;
		Gbm.newGridLine(c);
		c.insets = Constants.INSETS_GENERAL;
		this.getPanel().add(this.labPrevPainHistory, c);				//Previous Head Pain History
		Gbm.nextGridColumn(c);
		c.gridheight = 2;
		c.gridwidth = 2;
		this.getPanel().add(scroll, c);									//Previous Head Pain History Text Area
		Gbm.newGridLine(c, 2);
		c.gridheight = 2;
		c.gridx = 0;
		c.gridwidth = 1;
		this.getPanel().add(this.labFreqPainLastMonth, c);				//Frequency of Head Pain Last Month
		Gbm.nextGridColumn(c);
		c.gridheight = 1;
		this.getPanel().add(this.tfFreqPainLastMonth, c);				//Frequency of Head Pain Last Month Text Field
		Gbm.nextGridColumn(c);
		this.getPanel().add(this.labFreqPainLastMonthUnit, c);			//Frequency of Head Pain Last Month Unit
		Gbm.newGridLine(c);
		Gbm.newGridLine(c);
		c.gridheight = 2;
		this.getPanel().add(this.labDaysActivityDisturbed, c);		//Days Activity Disturbed due to pain
		Gbm.nextGridColumn(c);
		c.gridheight = 1;
		this.getPanel().add(this.tfDaysActivityDisturbed, c);		//Days Activity Disturbed due to pain Text Field
		Gbm.nextGridColumn(c);
		this.getPanel().add(this.labDaysActivityDisturbedUnit, c);	//Days Activity Disturbed due to pain unit
	}
	
	//Overridden Methods
	@Override
	public void resetDefaults()
	{
		this.taPrevPainHistory.setText("");
		this.tfDaysActivityDisturbed.setText("");
		this.tfFreqPainLastMonth.setText("");
	}

	@Override
	public void refresh()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void revalidateLanguage() 
	{
		this.labDaysActivityDisturbed.setText(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_MISC_DAYS_ACTIVITY_DISTURBED_LABEL));
		this.labFreqPainLastMonth.setText(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_MISC_FREQUENCY_PAIN_LAST_MONTH_LABEL));
		this.labPrevPainHistory.setText(Methods.getLanguageText(XMLIdentifier.ENTRY_LOG_MISC_PREVIOUS_HEAD_PAIN_HISTORY_LABEL));
		this.getFormTitleLabel().setText(Methods.getLanguageText(XMLIdentifier.MISC_TEXT));
		this.labFreqPainLastMonthUnit.setText(Methods.getLanguageText(XMLIdentifier.TIMES_PER_MONTH_TEXT));
		this.labDaysActivityDisturbedUnit.setText(Methods.getLanguageText(XMLIdentifier.DAYS_PER_MONTH_TEXT));
	}

	@Deprecated
	@Override
	public Object getData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setData(Object obj) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean allFilled() {return true;}
}
