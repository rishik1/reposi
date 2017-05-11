
package csRegs.dataStore;

import csRegs.util.Logger;
/**
 * 
 * StudentInfo class provide details of student read from input file and this class object are store in 
 * Registration store
 * @author rkhire1
 *
 */
public class StudentInfo {
	public String first_name;
	public String last_name;
	public String instructor;
	public int course_no;
	
	/**
	 * Default constructor
	 */
	public StudentInfo()
	{
		if(Logger.getDebugValue()==4)
		Logger.dump(4,"IN the StudentInfo");
	}
	
	/**
	 * Parameterized constructor sets the value of 
	 * first_name
	 * last_name
	 * Instructor_name
	 * course_no
	 * @param first
	 * @param last
	 * @param instruct
	 * @param course
	 */
	StudentInfo(String first,String last,String instruct,int course)
	{
		if(Logger.getDebugValue()==4)
		Logger.dump(4,"IN the StudentInfo");
		first_name =first;
		last_name = last;
		instructor = instruct;
		course_no =course;
	}
	
	/**
	 * returns firstname
	 * @return
	 */
	public String getFirstName()
	{
		return(this.first_name);
	}
/**
 * 
 * @return lastname
 */
	public String getLastName()
	{
		return(this.last_name);
	}
	/**
	 * 
	 * @return instructor_name
	 */
	public String getInstructor()
	{
		return(instructor);
	}

/**
 * toString method to provide sting containing first_name last_name instuctor_name and coursenumber
 */
	public String toString() {
		return "StudentInfo [first_name=" + first_name + ", last_name="
				+ last_name + ", instructor=" + instructor + ", course_no="
				+ course_no + "]";
	}
}
