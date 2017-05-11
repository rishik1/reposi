package taskmanager.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import taskmanager.filters.Fileter;
import taskmanager.util.Logger;
/**
 * subject implements I_Subject interface
 * has hasmpa data structure to stor observers and their filters
 * @author aashay-Rishikesh
 *
 */
public class Subject implements I_Subject {

	private String Update_String;
	private String Tab_Name;
	private Map<I_Observer,Fileter> update_list=new HashMap<I_Observer,Fileter>();
	/**
	 * constructor
	 */
	public Subject(){
		Logger.dump(2, "IN Subject ");
	}
	
/**
 * notify all the observers by checking to which filter the update string belongs
 */
	@Override
	public void nofify() {
				 for(Entry<I_Observer, Fileter> entry: this.update_list.entrySet()) {
					 if(Tab_Name.equals(entry.getValue().filter()))
					 {		    
						 entry.getKey().update(this.Update_String);
					 }
		    }
				 		 
	}
		
	/**
	 * register method updates the list of object of observers and their filters
	 * @see taskmanager.I_Subject#register(taskmanager.I_Observer, filters.Fileter)
	 */
	@Override
	public void register(I_Observer o,Fileter f) {
		update_list.put(o,f);
		Logger.dump(4, "An observer Registered ");
	}

/**
 * This method removers observers
 */
	@Override
	public void remove(I_Observer o) {
		update_list.remove(o);
		
	}
	/**
	 * set update_string i.e new string
	 * call notify all observers
	 * @param update_String
	 */
	public void setUpdate_String(String update_String) {
		Update_String = update_String;
		this.nofify();
	}

	/**
	 * set the tab name for which this update string belongs
	 * @param tab_Name
	 */
	public void setTab_Name(String tab_Name) {
		Tab_Name = tab_Name;
	}

}
