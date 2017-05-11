package taskmanager.util;

import taskmanager.display.Display;



/**
 * Logger is used for debugger purpose 
 * DEBUG_LEVEL is set by driver code which accepts level from
 * consol and accordingly prints the user message.
 *  
 *  @author aashay-Rishi khire
 *
 */

public class Logger {
	public static int DEBUG_LEVEL;
		
    /**
     * This function getets the debug value
     * @return DEBUG_VALUE
     */
	public static int getDEBUG_LEVEL() {
		return DEBUG_LEVEL;
	}
/**
 * This function sets Debug Value
 * @param dEBUG_LEVEL
 */
	public static void setDEBUG_LEVEL(int dEBUG_LEVEL) {
		DEBUG_LEVEL = dEBUG_LEVEL;
	}
/**
 * This metho dump the on conditions 
 * if debug value =1 display theactual output
 * if =0 all errormessage
 * if =2 all consructors display
 * if =3  tab_notified
 * if =4 observer when added
 * @param debug
 * @param msg
 */
	public static void dump(int debug,String msg)
	{
					
		
		if(Logger.DEBUG_LEVEL==debug)
		{
			System.out.println(msg);
			if(debug==1)
			{
				Display dis=new Display();
				dis.showResult();
			}
		}
		
		
		
	}

}
