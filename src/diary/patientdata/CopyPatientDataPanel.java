package diary.patientdata;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;

import diary.constants.Constants;
import diary.constants.XMLIdentifier;
import diary.gui.DateRangePanel;
import diary.gui.GButton;
import diary.methods.FileOperation;
import diary.methods.Methods;
import giantsweetroll.gui.swing.Gbm;

public class CopyPatientDataPanel extends JPanel implements ItemListener, ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2881333545176265281L;
	
	private JRadioButton radAllData, radDateRange;
	private ButtonGroup group;
	private DateRangePanel dateRangePanel;
	private JLabel labExportTo;
	private JTextField tfExportTo;
	private GButton butBrowse, butReset;
	private JPanel panelDate;
	
	//Constants
	private final String BROWSE = "brows",
							RESET = "reset",
							EXPORT_PATH_DEFAULT = FileOperation.getRootFolderPath() + File.separator + "data.zip";
	
	//Constructor
	public CopyPatientDataPanel()
	{
		this.init();
	}
	
	//Create GUI
	private void init()
	{
		//Initialization
		this.initPanelDate();
		this.radAllData = new JRadioButton(Methods.getLanguageText(XMLIdentifier.ALL_DATA_TEXT));
		this.group = new ButtonGroup();
		this.labExportTo = new JLabel(Methods.getLanguageText(XMLIdentifier.EXPORT_TO_TEXT));
		this.tfExportTo = new JTextField(this.EXPORT_PATH_DEFAULT, 20);
		this.butBrowse = new GButton(Methods.getLanguageText(XMLIdentifier.BROWSE_TEXT));
		this.butReset = new GButton(Methods.getLanguageText(XMLIdentifier.RESET_TEXT));
		GridBagConstraints c = new GridBagConstraints();
		
		//Properties
		this.setLayout(new GridBagLayout());
//		this.setBackground(Constants.COLOR_MAIN_MENU_BACKGROUND);
		this.setOpaque(false);
	//	this.radAllData.setForeground(Color.white);
		this.radAllData.setOpaque(false);
		this.radAllData.addItemListener(this);
		this.group.add(this.radAllData);
		this.group.add(this.radDateRange);
		this.radAllData.setSelected(true);
		this.tfExportTo.setEditable(false);
		this.tfExportTo.setOpaque(false);
		this.butBrowse.setActionCommand(this.BROWSE);
		this.butBrowse.addActionListener(this);
		this.butReset.setActionCommand(this.RESET);
		this.butReset.addActionListener(this);
		
		//Add to panel
		Gbm.goToOrigin(c);
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 2;
		c.insets = Constants.INSETS_GENERAL;
		this.add(this.radAllData, c);				//All Data Radio Button
		Gbm.newGridLine(c);
//		c.gridwidth = 1;
		this.add(this.panelDate, c);				//Date Range Panel
		Gbm.newGridLine(c);
		c.gridwidth = 1;
		this.add(this.labExportTo, c);				//Export To Label
		Gbm.nextGridColumn(c);
		c.gridwidth = 2;
		this.add(this.tfExportTo, c);				//Export To Text Field
		Gbm.nextGridColumn(c);
		Gbm.nextGridColumn(c);
		c.gridwidth = 1;
		this.add(this.butBrowse, c);				//Browse Button
		Gbm.nextGridColumn(c);
		this.add(this.butReset, c);					//Reset Button
	}
	private void initPanelDate()
	{
		//Initialization
		this.panelDate = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.radDateRange = new JRadioButton();
		this.dateRangePanel = new DateRangePanel();
		
		//Properties
		this.panelDate.setOpaque(false);
//		this.radDateRange.setForeground(Color.WHITE);
		this.radDateRange.setOpaque(false);
		this.radDateRange.addItemListener(this);
		this.dateRangePanel.setEnabled(false);
		
		//Add to panel
		this.panelDate.add(this.radDateRange);
		this.panelDate.add(this.dateRangePanel);
	}

	//Public Methods
	public boolean allDataSelected()
	{
		return this.radAllData.isSelected();
	}
	public boolean specificDateRangeSelected()
	{
		return this.radDateRange.isSelected();
	}
	public DateRangePanel getDateRangePanel()
	{
		return this.dateRangePanel;
	}
	public String getZipPath()
	{
		return this.tfExportTo.getText().trim();
	}

	//Interfaces
	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		if (this.radAllData.isSelected())
		{
			this.dateRangePanel.setEnabled(false);
		}
		else
		{
			this.dateRangePanel.setEnabled(true);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		switch(e.getActionCommand())
		{
			case BROWSE:
				LookAndFeel oldLF = UIManager.getLookAndFeel();
				try
				{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				}
				catch(Exception ex) {}
				JFileChooser jfc = new JFileChooser();
				jfc.setCurrentDirectory(new File("." + File.separator));
				
				int response = jfc.showDialog(null, Methods.getLanguageText(XMLIdentifier.SAVE_TEXT));
				if (response == JFileChooser.APPROVE_OPTION)
				{
					String extension = FileOperation.getExtension(jfc.getSelectedFile());
					if (!extension.equalsIgnoreCase("zip"))
					{
						extension = "zip";
						jfc.setSelectedFile(new File(jfc.getSelectedFile().getAbsolutePath() + "." + extension));
					}
					this.tfExportTo.setText(jfc.getSelectedFile().getAbsolutePath());
				}
				
				try
				{
					UIManager.setLookAndFeel(oldLF);
				}
				catch(Exception ex) {}
				break;
				
			case RESET:
				this.tfExportTo.setText(this.EXPORT_PATH_DEFAULT);
				break;
		}
	}
}