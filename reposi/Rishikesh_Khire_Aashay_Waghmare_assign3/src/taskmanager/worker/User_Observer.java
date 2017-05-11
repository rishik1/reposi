package taskmanager.worker;

import java.util.ArrayList;
import java.util.List;

import taskmanager.filters.Fileter;
import taskmanager.filters.UserFilter;
import taskmanager.display.Display;
import taskmanager.util.Logger;
/**
 * User_Observer implements I_Observer interface
 * it has the list to store object of User_tab
 * it implements the I_Observer update method 
 * @author aashay-Rishikesh
 *
 */
public class User_Observer implements I_Observer {
	private Display disp;
    private Subject sub=null;
	private User_tab tab;
	public List<User_tab> list=new ArrayList<User_tab>();
	private Fileter user=new UserFilter();
	/**
	 * User_Observer Parameterized constructor sets the subject from where its called
	 * it registers the User_observer in the subject list of Observers
	 * @param s
	 */
	public User_Observer(Subject s)
	{
		sub=s;
		sub.register(this,user);
		Logger.dump(2, "IN User observer");	
	}
	
	
	/**
	 * update method add the user_tab object created into arrayList of User_object
	 * @param String s as structure of user_tab read from file
	 */
	@Override
	public void update(String s) {
	
		String[] s1=new String[10];
		s1=s.split(",");
		tab=new User_tab(s1[0],s1[1]);
		list.add(tab);
		Logger.dump(3, "User observer notified ");
	}

}
