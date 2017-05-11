package taskmanager.filters;

import taskmanager.util.Logger;


/**
 * UseFilter implements the Filter Interface
 * it implements the filter method
 * @author aashay-Rishikesh
 *
 */
public class UserFilter implements Fileter {
	
	public UserFilter()
	{
		Logger.dump(2, "IN the UserFilter constructor ");
	}
/**
 * This method sets the value tab3 for the UserFilter
 */
	@Override
	public String filter() {
		
		return "tab3";
	}

}
