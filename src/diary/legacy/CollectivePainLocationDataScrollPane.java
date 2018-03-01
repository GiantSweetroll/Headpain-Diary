package diary.legacy;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import diary.constants.PainDataIdentifier;

public class CollectivePainLocationDataScrollPane extends JScrollPane
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7167068873969898308L;

	private List<IndividualPainLocationDataPanel> list;
	private JPanel panel;
	
	public CollectivePainLocationDataScrollPane(int amount)
	{
		//Initialization
		this.list = new ArrayList<IndividualPainLocationDataPanel>();
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
			this.list.add(new IndividualPainLocationDataPanel());
			this.panel.add(this.list.get(i));
		}
	}
	
	protected List<IndividualPainLocationDataPanel> getPainPositions()
	{
		return this.list;
	}
	
	protected Element getLocationsElement(Document doc)
	{
		Element locationsElement = doc.createElement(PainDataIdentifier.PAIN_LOCATIONS);
		
		for (int i=0; i<this.list.size(); i++)
		{
			locationsElement.appendChild(this.list.get(i).getDataAsElement(doc, Integer.toString(i)));
		}
		
		return locationsElement;
	}
	
	protected boolean allFieldsEntered()
	{
		for (int i=0; i<this.list.size(); i++)
		{
			if (!this.list.get(i).allFieldsEntered())
			{
				return false;
			}
		}
		return true;
	}
}
