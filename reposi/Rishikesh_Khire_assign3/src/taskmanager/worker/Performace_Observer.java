package taskmanager.worker;

import java.util.ArrayList;
import java.util.List;

import taskmanager.filters.Fileter;
import taskmanager.filters.PerforamanceFilter;
import taskmanager.display.Display;
import taskmanager.util.Logger;

/**
 * Performance_Observer class implements I_obsrver interface
 * it implements update method of interface
 * it has earliest to store object of Performance_tab
 * also it has object of filter which implements performance filter 
 * @author aashay-Rishikesh
 *
 */
public class Performace_Observer implements I_Observer {
	private Display disp;
	private Subject sub=null;
	private Performance_tab tab;
	public List<Performance_tab> list=new ArrayList<Performance_tab>();
	private Fileter perf=new PerforamanceFilter(); 
	
	/**
	 * Default Performance_observer constructor
	 */
	public Performace_Observer()
	{
		Logger.dump(2, "IN Performance observer");
	}
	/**
	 * parametrized performance pbserver constructor called from subject
	 * sets the reference to subject
	 * @param s
	 */
	public Performace_Observer(Subject s)
	{
		this.sub=s;
		sub.register(this,perf);
		Logger.dump(2, "IN Performance observer");
	}
	
	/**
	 * update method add the user_tab object created into arrayList of User_object
	 * @param String s as structure of user_tab read from file
	 */
	@Override
	public void update(String s) {

		String[] s1=new String[10];
		s1=s.split(",");
		tab=new Performance_tab(Integer.parseInt(s1[0]),Integer.parseInt(s1[1]),Integer.parseInt(s1[2]),Integer.parseInt(s1[3]));
		list.add(tab);
			
		//System.out.println(tab);
		disp =new Display(tab);
		Logger.dump(3, "Performance observer notified ");
	}
	
   
}
