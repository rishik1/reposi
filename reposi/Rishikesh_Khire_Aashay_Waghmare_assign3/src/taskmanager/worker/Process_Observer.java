package taskmanager.worker;

import java.util.ArrayList;
import java.util.List;

import taskmanager.filters.Fileter;
import taskmanager.filters.ProcessFilter;
import taskmanager.display.Display;
import taskmanager.util.Logger;
/**
 * Process_observer class implements I_observer interface
 * It has ArrayList to store list of Processes objects in it.
 * object of Process Filter is created
 * @author aashay-Rishikesh
 *
 */
public class Process_Observer implements I_Observer {
	private Display disp;
	public List<Process_tab> list=new ArrayList<Process_tab>();
	private Process_tab tab;
	private Fileter proc=new ProcessFilter();
	private Subject sub=null;
	
/**
 * Proces_Observer constructor sets the subject object
 * 
 * @param s
 */
  		public Process_Observer(Subject s)
 
		{
			this.sub=s;
			sub.register(this,proc);
			Logger.dump(2, "IN Process observer");
		}
	/**
	 * update method 
	 * it create object for each structure occurrence in file
	 *  to update arraylist containing object of process_tab
	 * and add the element in the display
	 * 	
	 */
		@Override
		public void update(String s) {
		
			String[] s1=new String[10];
			s1=s.split(",");
			tab=new Process_tab(s1[0],Integer.parseInt(s1[2]),Integer.parseInt(s1[3]),s1[4],s1[1]);
			list.add(tab);
			
			disp =new Display(tab);
			Logger.dump(3, "Process observer notified ");
		}
	

	}

	


