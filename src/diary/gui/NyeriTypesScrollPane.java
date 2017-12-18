package diary.gui;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class NyeriTypesScrollPane extends JScrollPane
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7167068873969898308L;

	private ArrayList<HeadPainPosition> list;
	private JPanel panel;
	
	public NyeriTypesScrollPane(int amount)
	{
		//Initialization
		this.list = new ArrayList<HeadPainPosition>();
		this.panel = new JPanel();
		
		//Properties
		this.panel.setOpaque(false);
		this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.Y_AXIS));
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.setViewportView(this.panel);
		
		//add
		for (int i=0; i<amount; i++)
		{
			this.list.add(new HeadPainPosition());
			this.panel.add(this.list.get(i));
		}
	}
	
	protected ArrayList<LinkedHashMap<String, String>> getData()
	{
		ArrayList<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String, String>>();
		for (int i=0; i<this.list.size(); i++)
		{
			list.add(this.list.get(i).getDataMap());
		}
		
		return list;
	}
}
