package diary.history;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import diary.constants.Constants;
import diary.methods.FileOperation;
import diary.patientdata.PatientData;

public class History
{
	private String name;
	private List<String> history;

	//Constants
	public static final String FILE_EXTENSION_NAME = ".hist";
	
	//Constructors
	public History(String historyName, PatientData patient)
	{
		this.name = historyName;
//		this.history = new ArrayList<String>();
		this.refresh(patient);
	}
	
	//Methods
	public void refresh(PatientData patient)
	{
//		System.out.println(patient.getNameAndID());
		try
		{
			this.history = FileOperation.loadTextFile(new File(this.getFilePath(patient)));
		}
		catch(Exception ex)
		{
		//	ex.printStackTrace();
			this.history = new ArrayList<String>();
		}
//		System.out.println(history);
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
	
	public String getFilePath(PatientData patient)
	{
		return Constants.HISTORY_FOLDER_PATH + patient.getID() + File.separator + this.getFileName();
	}
	
	public List<String> getHistory()
	{
		return this.history;
	}
	
	public void export(PatientData patient)
	{
		FileOperation.saveHistory(this, patient);
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
