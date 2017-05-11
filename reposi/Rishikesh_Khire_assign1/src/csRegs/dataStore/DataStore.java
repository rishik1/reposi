package csRegs.dataStore;

import java.util.HashMap;

import csRegs.dataStore.StudentInfo;

/**
 * Interface to provide abstraction to class Registration Store
 * @author rkhire1
 *
 */

public interface DataStore{
	
	
	public void displayRegistrationStore();
	public void addStudent(String first,String last,String instructor,int course);
	public HashMap <String,StudentInfo> getStore();

}
