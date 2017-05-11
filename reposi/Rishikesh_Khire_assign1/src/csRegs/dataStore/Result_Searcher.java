package csRegs.dataStore;

import java.util.HashMap;
import csRegs.util.Logger;
import csRegs.util.Results;
import csRegs.util.resultInterface;

/**
 * 
 * Result_Searcher implements Searcher 
 * provide search functionality from the HashMap data structure and generate result
 * 
 * @author rkhire1
 *
 */
public class Result_Searcher implements Searcher {
	
	String search_name=null;
	DataStore rstore= new RegistrationStore();
	resultInterface res = new Results();
	
    HashMap <String,StudentInfo> student_list= rstore.getStore();
	
	/**
	 * default Constructor
	 */
	public Result_Searcher() {
	
		Logger.dump(4,"IN the Result_Searcher");
	}
	
	/**
	 * Search the search_name send by FileReader2 and search in HasMap implemented in the RegistrationStore through DataStore 
	 * if search_name found then add the result to Result DataStructure Implented by Results through Interface resultInterface
	 */
	public void search(String search_name)
	{	
	StudentInfo student = student_list.get(search_name);
		if(student!=null)
		{
				Logger.dump(2,"Found Record Added");
			
			res.storeResult(student);
		}
		
	}

	
}