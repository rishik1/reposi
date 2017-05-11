
package csRegs.dataStore;
import csRegs.util.Logger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Registration Store is implementation of Interface DataStore
 * provide a Single Instance HashMap data Structure to store entries generated from populate_Workers input File
 * it can add record to Data Structure
 * 
 * @author rkhire1
 *
 */

public class RegistrationStore implements DataStore{
	/**
	 * HashMap data structure to store object records
	 */
	static HashMap <String,StudentInfo> Registration = new HashMap <String,StudentInfo>();
 /**
   * constructor
  */
	public RegistrationStore()
	{	
		Logger.dump(4,"IN the Registration Store");
	}
	
	/**
	 * function addStudent adds student record as key value pair to Student list
	 *key = String ,value is student object 
	 * student object has 3 keys for same objects firstnam lastname instructor.
	 * 
	 */
	public void addStudent(String first,String last,String instructor,int course)
	{
		StudentInfo student = new StudentInfo(first,last,instructor,course);
		Registration.put(first.trim(), student);
		Registration.put(last.trim(), student);
		Registration.put(instructor.trim(), student);
	}
	
	/**
	 * displayRegistrationStore
	 * displays the Hash Map sequentially using iterator
	 * 
	 */
	public void displayRegistrationStore()
	{
		Iterator i = Registration.entrySet().iterator();
		while(i.hasNext())
		{
			Map.Entry pair = (Map.Entry)i.next();
			StudentInfo stud = (StudentInfo) pair.getValue();
			stud.toString();
		}
		
	}
	/**
	 * getStore function return the single hash map instance created to store key and object value pair
	 * 
	 */
	public HashMap <String,StudentInfo> getStore()
	{
		return(Registration);
		
	}



	
}
