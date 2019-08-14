package diary.gui.EntryLog.forms;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import diary.constants.Constants;
import diary.constants.XMLIdentifier;
import diary.methods.Methods;
import giantsweetroll.gui.swing.ScrollPaneManager;
import giantsweetroll.gui.swing.TextAreaManager;

public class Comments extends FormElement<String>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7505841736348815756L;

	private JTextArea ta;
	private JScrollPane scroll;
	
	public Comments()
	{
		super(Methods.getLanguageText(XMLIdentifier.COMMENTS_LABEL), false);
		
		//Initialization
		this.ta = new JTextArea(15, 30);
		this.scroll = ScrollPaneManager.generateDefaultScrollPane(this.ta, 10, 10);
//		GridBagConstraints c = new GridBagConstraints();
		SpringLayout spr = new SpringLayout();
		
		//Properties
		this.getPanel().setLayout(spr);
//		this.getPanel().setLayout(new GridBagLayout());
		TextAreaManager.autoConfigureTextArea(ta, true);
		this.getFormTitleLabel().setHorizontalAlignment(SwingConstants.LEFT);
		this.scroll.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		spr.putConstraint(SpringLayout.NORTH, this.getFormTitleLabel(), Constants.INSETS_BASE, SpringLayout.NORTH, this.getPanel());
		spr.putConstraint(SpringLayout.WEST, this.getFormTitleLabel(), Constants.INSETS_BASE*3, SpringLayout.WEST, this.getPanel());
		spr.putConstraint(SpringLayout.NORTH, this.scroll, Constants.INSETS_BASE, SpringLayout.SOUTH, this.getFormTitleLabel());
		spr.putConstraint(SpringLayout.WEST, this.scroll, Constants.INSETS_BASE*3, SpringLayout.WEST, this.getPanel());
		spr.putConstraint(SpringLayout.EAST, this.scroll, Constants.INSETS_BASE*-3, SpringLayout.EAST, this.getPanel());
		spr.putConstraint(SpringLayout.SOUTH, this.scroll, Constants.INSETS_BASE*-3, SpringLayout.SOUTH, this.getPanel());
		
		//Add to panel
		/*
		c.insets = Constants.INSETS_TOP_COMPONENT;
		c.gridwidth = GridBagConstraints.REMAINDER;
		this.getPanel().add(this.getFormTitleLabel(), c);
		this.getPanel().add(this.ta, c);
		*/
		this.getPanel().add(this.getFormTitleLabel());
		this.getPanel().add(this.scroll);
	}
	
	//Overridden Methods
	@Override
	public void resetDefaults() 
	{
		super.resetDefaults();
		this.ta.setText("");
	}

	@Override
	public void refresh() {};
	
	@Override
	public String getData() {return this.ta.getText().trim();}

	@Override
	public void setData(String obj) {this.ta.setText(obj);}

	@Override
	public boolean allFilled() {
		return true;
	}

	@Override
	public void revalidateLanguage() 
	{
		this.setFormTitle(Methods.getLanguageText(XMLIdentifier.COMMENTS_LABEL));
	}
}
