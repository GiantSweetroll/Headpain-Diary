package diary.gui.table;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import diary.PainEntryData;
import diary.constants.Constants;
import diary.constants.PainDataIdentifier;

public class TablePanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7051752509928960116L;
	private Table table;
	private JScrollPane scroll;
	
	protected TablePanel(List<PainEntryData> list)
	{
		//Initialization
		Object[][] objects = this.convertToTableDataArray(list);
		this.table = new Table(objects, Constants.TABLE_HEADERS);
		{
			//Internal JTable personalization
		}
		this.scroll = new JScrollPane(this.table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		//Properties
		this.setOpaque(false);
		this.setLayout(new BorderLayout());
		
		//Add to panel
		this.add(this.scroll);
	}
	
	private Object[][] convertToTableDataArray(List<PainEntryData> list)
	{
		Object arr[][] = new Object[list.size()][10];
		
		for (int i=0; i<arr.length; i++)
		{	
			int index = 0;
			arr[i][index] = new Boolean(false);				//Selected boolean
			index++;
			arr[i][index]= list.get(i).getFullDate();		//Date
			index++;
			arr[i][index]= list.get(i).getFullTime();		//Time
			index++;
			arr[i][index]= list.get(i).getDataMap().get(PainDataIdentifier.PAIN_AMOUNT);		//Pain Amount
			index++;
			arr[i][index]= list.get(i).getPainPositionsAsString();		//Pain Positions
			index++;
			arr[i][index]= list.get(i).getPainKindAsString();			//Pain Kinds
			index++;
			arr[i][index]= list.get(i).getIntensitiesAsString();		//Intensities
			index++;
			arr[i][index]= list.get(i).getDurationsAsString();			//Durations
			index++;
			arr[i][index]= list.get(i).getDataMap().get(PainDataIdentifier.ACTIVITY);		//Activity
			index++;
			arr[i][index]= list.get(i).getDataMap().get(PainDataIdentifier.COMMENTS);		//Comments
		}
		
		return arr;
	}
}
