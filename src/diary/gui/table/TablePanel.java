package diary.gui.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import diary.data.PainEntryData;
import diary.interfaces.LanguageListener;
import diary.methods.Methods;
import diary.methods.PainDataOperation;

public class TablePanel extends JPanel implements LanguageListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7051752509928960116L;
	private Table table;
	private JScrollPane scroll;
	
	protected TablePanel(List<PainEntryData> list, String filterType, String filter)
	{
		//Initialization
		list = PainDataOperation.getFilteredData(filterType, filter, list);
		Object[][] objects = this.convertToTableDataArray(list);
		this.table = new Table(objects, Methods.getTableHeaders());
		this.scroll = new JScrollPane(this.table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		//Properties
		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);
		
		//Add to panel
		this.add(this.scroll);
	}
	
	private Object[][] convertToTableDataArray(List<PainEntryData> list)
	{
		Object arr[][] = new Object[list.size()][12];
		
		for (int i=0; i<arr.length; i++)
		{	
			int index = 0;
			arr[i][index] = new Boolean(false);				//Selected boolean
			index++;
			arr[i][index]= list.get(i).getFullDate();		//Date
			index++;
			arr[i][index]= list.get(i).getFullTime();		//Time
			index++;
	//		arr[i][index]= list.get(i).getDataMap().get(PainDataIdentifier.PAIN_AMOUNT);		//Pain Amount
	//		index++;
			arr[i][index]= list.get(i).getPainPositionsAsString();		//Pain Positions
			index++;
			arr[i][index]= Methods.convertPainKindIDToLanguage(list.get(i).getPainKind());			//Pain Kind
			index++;
			arr[i][index]= list.get(i).getIntensity();		//Intensity
			index++;
			arr[i][index]= list.get(i).getDuration();		//Durations
			index++;
			arr[i][index]= Methods.convertActivityIDToLanguage(list.get(i).getTrigger());		//Trigger
			index++;
	//		arr[i][index]= list.get(i).getDataMap().get(PainDataIdentifier.ACTIVITY_DETAILS);//Activity Details
	//		index++;
			arr[i][index]= list.get(i).getRecentMedication();//Recent Medication
			index++;
			arr[i][index]= list.get(i).getMedicineComplaint();//Medicine Complaint
			index++;
			arr[i][index]= list.get(i).getComments();		//Comments
		}
		
		return arr;
	}

	protected Table getTable()
	{
		return this.table;
	}
	
	public JScrollPane getScrollPane()
	{
		return this.scroll;
	}
	
	//Interfaces
	@Override
	public void revalidateLanguage() 
	{
		this.table.revalidateLanguage();
	}
}
