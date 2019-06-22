package diary.history;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import diary.constants.XMLIdentifier;
import diary.interfaces.GUIFunction;
import diary.methods.Methods;

public class HistoryComboBox extends JComboBox<String> implements GUIFunction
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3915042063372232284L;
	
	private History history;
	private String defaultSelection;
	private String[] defaultOptions;
	private boolean sorted, haveNone;
	
	//Constants
	private final String NO_DEFAULTS = "";

	public HistoryComboBox(History history, String[] defaults, boolean sorted, boolean haveNone)
	{
		super();
		this.sorted = sorted;
		this.haveNone = haveNone;
		this.setDefaultOptions(defaults);
		this.setDefaultSelection(this.NO_DEFAULTS);
		this.setHistory(history);
		this.refresh();
	}
	
	private List<String> initOptions(History history)
	{
		List<String> list = new ArrayList<String>();
		List<String> histories = history.getHistory();
		Set<String> set = new HashSet<String>();
		List<String> subList = new ArrayList<String>();
		
		for (String str : defaultOptions)
		{
			set.add(str);
		}
		for (String str : histories)
		{
			set.add(str);
		}
		
		for (String str : set)
		{
			subList.add(str);
		}
		if (this.sorted)
		{
			Collections.sort(subList);
		}
		
		if (this.haveNone)
		{
			list.add(Methods.frameStringWithDashes(Methods.getLanguageText(XMLIdentifier.CHOOSE_NONE_TEXT)));
		}
		list.addAll(subList);
		list.add(Methods.frameStringWithDashes(Methods.getLanguageText(XMLIdentifier.OTHER_TEXT)));
		
		return list;
	}
	
	//Public Methods
	public void setHistory(History history)
	{
		this.history = history;
		this.refresh();
	}
	public void setDefaultOptions(String[] defaults)
	{
		this.defaultOptions = defaults;
	}
	public void setDefaultSelection(String option)
	{
		this.defaultSelection = option;
	}
	public void setSelectedToOther()
	{
		this.setSelectedIndex(this.getLastIndex());			//Last index
	}
	public void setSelectedToNone()
	{
		if(this.haveNone)
		{
			this.setSelectedIndex(0);
		}
		else
		{
			this.setSelectedToOther();
		}
	}
	public int getLastIndex()
	{
//		return this.history.getHistory().size()+1;
		return this.getItemCount()-1;
	}
	public boolean lastIndexSelected()
	{
		return this.getSelectedIndex() == this.getLastIndex();
	}
	public boolean otherOptionSelected()
	{
		return this.lastIndexSelected();
	}
	public boolean noneOptionSelected()
	{
		if (this.haveNone)
		{
			return this.getSelectedIndex() == 0;
		}
		else
		{
			return false;
		}
	}
	
	//Private Methods
	private boolean hasCustomDefaultOption()
	{
		try
		{
			return !this.defaultSelection.equals(this.NO_DEFAULTS);
		}
		catch(NullPointerException ex)
		{
			return false;
		}
	}
	public void revalidateLanguage(String[] defaultOptions)
	{
		this.setDefaultOptions(defaultOptions);
		this.refresh();
	}

	//Interfaces
	@Override
	public void resetDefaults()
	{
		if (this.hasCustomDefaultOption())
		{
			this.setSelectedItem(this.defaultSelection);
		}
		else
		{
			this.setSelectedToNone();
		}
	}

	@Override
	public void refresh() 
	{
		List<String> options = this.initOptions(this.history);
		this.setModel(new DefaultComboBoxModel<String>(options.toArray(new String[options.size()])));
	}
}
