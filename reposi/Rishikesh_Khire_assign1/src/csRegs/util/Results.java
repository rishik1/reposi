
package csRegs.util;

import java.util.ArrayList;
import csRegs.dataStore.StudentInfo;
/**
 * 
 * Result class provides implementation of the interface resultInterface
 * Creates DataStructure to store Results objects in arrayList 
 * @author rkhire1
 *
 */
public class Results implements resultInterface{

	public static ArrayList<StudentInfo> resultData = new ArrayList<StudentInfo>();
/**
 * default constructor
 */
	public Results(){
		Logger.dump(4,"IN the Results");
		
	}
	
	/**
	 * storeResult provide the StudentInfo object which are found in the RegistrationStore DataStructure 
	 */
	public void storeResult(StudentInfo student)
	{
		resultData.add(student);
	}
	
	/**
	 * display Data displays the results in the Stored in ArrayList dataStructure of results. 
	 */
	public void displayData(){
		
		for(StudentInfo student : resultData)
		{

			System.out.println("Results::"+student.getFirstName()+student.getLastName());
			
		}
	}
	
	
}
