package diary.gui.table;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import diary.gui.DateRangePanel;

public class TablePanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6429132267457024269L;
	
	private JPanel panelTop, panelTopLeft, panelBelowLeft, panelBelowCenter;
	private DateRangePanel panelDateRange;
	private JComboBox<String> comboFilter;
	private JTextField tfFilter;
	
}
