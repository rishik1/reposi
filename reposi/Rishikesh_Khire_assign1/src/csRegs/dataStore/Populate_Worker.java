

package csRegs.dataStore;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import csRegs.util.Logger;
/**
 * Populate worker calls implements interface Worker
 * It spawns threads to read from the INput File 
 * Parameterized constructor takes no of threads and Input File
 * threads are created and each calls run method FileReaders object
 * After completion of threads they are joined 
 * File reader and BufferedReader are closed
 * 
 * @author rkhire1
 *
 */

public class Populate_Worker implements Worker {
	
		Populate_Worker()
		{
			Logger.dump(4,"IN the PopulateWorker");
		}
	
		
/**
 * It spawns threads to read from the INput File 
 * Parameterized constructor takes no of threads and Input File
 * threads are created and each calls run method FileReaders object
 * After completion of threads they are joined 
 * File reader and BufferedReader are closed
		 * 
		 * @param no_threads
		 * @param input_file
 */
    public Populate_Worker(int no_threads,String input_file) {
   
    	Logger.dump(4,"IN the PopulateWorker");
    	FileReader fp = null;
    	try {
			fp= new FileReader(input_file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	BufferedReader buff = new BufferedReader(fp);
    	FileReaders rd=new FileReaders(buff);
    	Thread thread[]= new Thread[no_threads]; 
    	for(int i=0;i<no_threads;i++)
		{
			 thread[i] =new Thread(rd);
			
		}
    	for(int i=0;i<no_threads;i++)
		{
	
			thread[i].start();
		
		}
    	for(int i=0;i<no_threads;i++)
		{
			try {
				thread[i].join();
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			
		}
    	try {
			buff.close();
			fp.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return;
 
    }

    

} // end class PopulateWorker