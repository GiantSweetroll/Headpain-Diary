package diary.methods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import diary.patientdata.PatientData;

public class PatientDataOperation 
{
	public static List<String> getListOfNamesAndID()
	{
		List<PatientData> list = FileOperation.getListOfPatients();
		List<String> names = new ArrayList<String>();
		
		for (int i=0; i<list.size(); i++)
		{
			names.add(list.get(i).getNameAndID());
		}
		
		Collections.sort(names);
		
		return names;
	}
	
	public static List<String> getListOfIDAndNames()
	{
		List<PatientData> list = FileOperation.getListOfPatients();
		List<String> names = new ArrayList<String>();
		
		for (int i=0; i<list.size(); i++)
		{
			names.add(list.get(i).getIDAndName());
		}
		
		Collections.sort(names);
		
		return names;
	}
}
