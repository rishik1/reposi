
package csRegs.util;

import csRegs.util.Logger;
import csRegs.util.Results;
import csRegs.util.resultInterface;

/**
 * Logger class provide the different case values to be printed for different debug Vlaue
 * DEBUG_VALUE=4 [Print to stdout, using the Logger, everytime a constructor is called]
 * DEBUG_VALUE=3 [Print to stdout, using the Logger, everytime a thread's run() method is called]
 * DEBUG_VALUE=2 [Print to stdout, using the Logger, everytime an entry is added to the Results data structure]
 * DEBUG_VALUE=1 [Print to stdout, using the Logger, the search results]
 * DEBUG_VALUE=0 [No output should be printed from the application]
 * 
 * @author rkhire1
 *
 */
public class Logger {
static private int DEBUG_VALUE;
static String message;
	
	private static Logger log = new Logger();
	/**
	 * setDebugValues
	 * sets the DEBUG LEVEL .
	 * @param value
	 */
	public static void setDebugValue(int value)
	{
		DEBUG_VALUE = value;
	}
	
	/**
	 * gets the value of DEBUG LEVEL
	 * @return
	 */
	public static int getDebugValue()
	{
		return(DEBUG_VALUE);
	}
	
	/**
	 * @param value
	 * @param msg
	 * different functionlity are implements as per the Debug_levels if the current Debug_Value matched.
	 * 
	 */
	public static void dump(int value,String msg)
	{
	
		if(value == DEBUG_VALUE && DEBUG_VALUE ==1)
		{
			System.out.println(msg);
			resultInterface printResult = new Results();
			printResult.displayData();
		}
		
			if(value == DEBUG_VALUE && DEBUG_VALUE ==2)
			System.out.println(msg);
			
			if(value == DEBUG_VALUE && DEBUG_VALUE ==3)
			System.out.println(msg);
		
			if(value == DEBUG_VALUE && DEBUG_VALUE ==4)
			System.out.println(msg);
		}
		
	

}
