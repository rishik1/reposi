
package csRegs.driver;

import csRegs.dataStore.DataStore;
import csRegs.util.Logger;
import csRegs.dataStore.Populate_Worker;
import csRegs.dataStore.RegistrationStore;
import csRegs.dataStore.Search_Worker;
import csRegs.dataStore.Worker;
/**
 * Driver class to contains main function which calls populate worker and search worker
 * main sets Logger Debug value and input to main is 
 * in order  INPUT FILE POPULATE_NO_of_THREADS SEACRH_FILE SEACRHWORKER_NO_THREADS DEBUG_VALUE
 * @author rkhire1
 *
 */
public class Driver {

    public static void main(String args[]) {

    	String input_file = args[0];
    	int nn_thread = Integer.parseInt(args[1]);
    	String search_file = args[2];
    	int mm_thread = Integer.parseInt(args[3]);
    	int debug_value = Integer.parseInt(args[4]);
    
    	
    	Logger.setDebugValue(debug_value);
    	Worker populate = new Populate_Worker(nn_thread,input_file);
    	
    	DataStore n1 = new RegistrationStore();
    	//n1.displayRegistrationStore();
    	Worker search = new Search_Worker(mm_thread,search_file); 
    	
    	Logger.dump(1, "Results are ");

    } // end main(...)
} // end class Driver