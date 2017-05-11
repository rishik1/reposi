package taskmanager.display;

import java.util.ArrayList;

import taskmanager.util.Logger;
import taskmanager.worker.Performance_tab;
import taskmanager.worker.Process_tab;
import taskmanager.worker.User_tab;


/**
 * Display class is used to display results
 *it has arrayList to store displaying object wrapped around respective process or performance or user object
 * @author aashay-Rishikesh
 *
 */
public class Display {
private static ArrayList<Display> list = new ArrayList<Display>();
Performance_tab perform;
Process_tab process;
User_tab users;
int flag;
/**
 * Display constructor for Performance _tab object 
 * which is pushed from Performance observer and stored in a list
 * and setting flag of this object =2 for performance_tab object
 * @param perform
 */
public Display(Performance_tab perform)
{
	Logger.dump(2, "IN Display constructor");
	this.perform =perform;
	flag =2;
	list.add(this);
}
/**
 * 	 Display constructor for Process _tab object 
 * which is pushed from Process observer and stored in a list
 * and setting flag of this object =1 for process_tab object
 * @param process
 */
public Display(Process_tab process)
{
	Logger.dump(2, "IN Display constructor");
	this.process =process;
	flag =1;
	list.add(this);
}
/**
 *  Display constructor for user _tab object 
 * which is pushed from User observer and stored in a list
 * and setting flag of this object =3 for user_tab object
 * @param users
 */
public Display(User_tab users){
	Logger.dump(2, "IN Display constructor");
	this.users =users;
	flag=3;
	list.add(this);
}

/**
 * Default constuctor
 */
public Display() {
	Logger.dump(2, "IN Display constructor");
}
/**
 * showresults method
 * Traverse the list and if flagg=1 means process object 
 * if flag =2 performance object
 * if flag =3 user object
 * and printed respectively
 */
public void showResult()
{
	for(Display x:list)
	{
		if(x.getFlag() == 1)
		{
			Process_tab pro = x.getProcess();
			System.out.println(pro);
			
		}
		else if(x.getFlag() == 2)
		{
			Performance_tab per = x.getPerform();
			System.out.println(per);
			
		}
		else if(x.getFlag()==3)
		{
			User_tab use = x.getUsers();
			System.out.println(use);
		}
	}
	
}
/**
 * Performance_tab getter setter
 * @return performance object
 */
public Performance_tab getPerform() {
	return this.perform;
}

/**
 * get method for process
 * @return
 */
public Process_tab getProcess() {
	return this.process;
}

/**
 * user_tab getter
 * @return user_tab object
 */
public User_tab getUsers() {
	return this.users;
}


public int getFlag() {
	return flag;
}


}
