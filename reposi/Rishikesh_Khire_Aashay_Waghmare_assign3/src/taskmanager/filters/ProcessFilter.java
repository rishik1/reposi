package taskmanager.filters;

import taskmanager.util.Logger;


/**
 * ProcessFilter implements Filter interface
 * implements the filter method of interface
 * @author aashay-Rishikesh
 *
 */
public class ProcessFilter implements Fileter{
	
	public ProcessFilter()
	{
		Logger.dump(2, "IN the ProcessFilter constructor ");
	}
/**
 * filter method sets the value tab1 for Process interface
 */
	
	
	@Override
	public String filter() {
		
		return "tab1";
	}

}
