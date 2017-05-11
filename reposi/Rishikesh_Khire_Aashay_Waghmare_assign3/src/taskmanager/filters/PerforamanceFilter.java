package taskmanager.filters;

import taskmanager.util.Logger;


/**
 * PerformanceFilter implements the filter interface
 * it implements the filter interface
 * @author aashay-Rishikesh
 *
 */
public class PerforamanceFilter implements Fileter {
/**
 * filter method sets the value tab2 for PerformanceFilter
 */
	public PerforamanceFilter()
	{
		Logger.dump(2, "IN the PerforamanceFilter constructor ");
	}
	@Override
	public String filter() {
		
		return "tab2";
	}

}
