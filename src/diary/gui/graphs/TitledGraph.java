package diary.gui.graphs;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import diary.constants.Constants;

public class TitledGraph extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5512349153976859234L;
	private Graph graph;
	
	//Constructors
	public TitledGraph(String title, Graph graph)
	{
		//Initialization
		JLabel labTitle = new JLabel(title, SwingConstants.CENTER);
		this.graph = graph;
		
		//Properties
		labTitle.setFont(Constants.FONT_SUB_TITLE);
		labTitle.setAlignmentX(CENTER_ALIGNMENT);
		this.graph.setAlignmentX(CENTER_ALIGNMENT);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setOpaque(false);
		
		//add to panel
		this.add(labTitle);				//Title
		this.add(this.graph);				//Graph
	}
	
	//Methods
	public void refreshGraph()
	{
		this.graph.repaint();
	}
	public Graph getGraph()
	{
		return this.graph;
	}
}
