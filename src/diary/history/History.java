package diary.history;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import diary.constants.Constants;
import diary.methods.FileOperation;

public class History
{
	private String name;
	private List<String> history;

	//Constants
	public static final String FILE_EXTENSION_NAME = ".hist";
	
	//Constructors
	public History(String historyName)
	{
		this.name = historyName;
		this.refresh();
	}
	
	//Methods
	public void refresh()
	{
		try
		{
			this.history = FileOperation.loadTextFile(new File(this.getFilePath()));
		}
		catch(Exception ex)
		{
			this.history = new ArrayList<String>();
		}
	}
	public String getName()
	{
		return this.name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getFileName()
	{
		return this.name + History.FILE_EXTENSION_NAME;
	}
	
	public String getFilePath()
	{
		return Constants.HISTORY_FOLDER_PATH + this.getFileName();
	}
	
	public List<String> getHistory()
	{
		return this.history;
	}
	
	public void export()
	{
		FileOperation.saveHistory(this);
	}
	
	public boolean itemExists(String item)
	{
		for (String text : this.history)
		{
			if (text.equals(item))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public void add(String item)
	{
		if (!this.itemExists(item))
		{
			this.history.add(item);
		}
	}
}
