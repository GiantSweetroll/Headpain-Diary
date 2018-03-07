package diary.gui.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import diary.constants.Constants;

public class Table extends JTable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7051752509928960116L;
	
	public Table(Object[][] data, String[] columns)
	{
		super(data, columns);
		
		//Table properties
//		this.getTableHeader().setBackground(new Color (8, 243, 240));			//Set Header color background
//		this.getTableHeader().setBackground(Color.WHITE);	
		this.getTableHeader().setBackground(Constants.COLOR_TABLE_HEADER);
//		this.getTableHeader().setOpaque(false);					//Make the default background of the table transparent (to see background color)
	//	this.getTableHeader().setFont(Constants.FONT_GENERAL_BOLD);
		this.getTableHeader().setForeground(Color.WHITE);
		this.setBackground(Color.WHITE);
		this.setAutoCreateRowSorter(true);					//Automatically create Row sorter
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);			//Set to manual column size, so it works with the JScrollPane
		
		for (int i=0; i<this.getColumnCount(); i++)
		{
			this.getColumnModel().getColumn(i).setMinWidth(this.getColumnName(i).length()*10);
		}
		
		//Center align headers
		JLabel headerLabel = ((JLabel)this.getTableHeader().getDefaultRenderer());
		headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		//Center align cells data
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i=1; i<this.getColumnCount(); i++)			//Ignore first column, otherwise won't be able to display boolean check box
		{
			this.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
	}

	//Override Methods
	@Override
	public boolean isCellEditable(int row, int column)		//Make the table data un-editable (except the check box column)
	{
		switch (column)
		{
			case 0:
				return true;
				
			default:
				return false;
		}
	}
	
	@Override
    public Class<?> getColumnClass(int column)
    {
		return getValueAt(0, column).getClass();
    }
	
	@Override
	public Component prepareRenderer(TableCellRenderer r, int row, int column)
	{
		Component c = super.prepareRenderer(r, row, column);
		
		//Table row color patterns
		if (row%2==1)
		{
			//c.setBackground(Color.WHITE);
			//c.setBackground(new Color(101, 214, 214));		//Cyan blue
			c.setBackground(new Color(222, 220, 220));
		}
		else
		{
			c.setBackground(Color.LIGHT_GRAY);
			//c.setBackground(new Color(0, 45, 255));			//Lapis Blue
		}
		
		//Different color for selected row(s)
		if (super.isCellSelected(row, column))
		{
			//c.setBackground(new Color (8, 243, 240));		//Cyan Blue
			c.setBackground(new Color(77, 72, 72));
			c.setForeground(Color.WHITE);
		}
		else
		{
			c.setForeground(Color.BLACK);
		}
		
		return c;
	}
	
	@Override
	public boolean getScrollableTracksViewportWidth()			//Resizes the table cells width to its preferred size or the viewport size, whichever is greater
	{
		return getPreferredSize().width < getParent().getWidth();
	}
}
