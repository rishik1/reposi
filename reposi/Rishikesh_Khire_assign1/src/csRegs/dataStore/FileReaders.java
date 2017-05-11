package csRegs.dataStore;

import java.io.BufferedReader;
import java.io.IOException;

import csRegs.util.Logger;
/**
 * FileReaders implements Runnable 
 * It provide run() function for number of threads
 * Main function is to readfile()-->input file
 * 
 * @author rkhire1
 *
 */
public class FileReaders implements Runnable {
	BufferedReader reader=null;
	public DataStore info=new RegistrationStore();;
	
	FileReaders()
	{	
		Logger.dump(4,"IN the FileReader for populateWorker");
	}
	FileReaders(BufferedReader buff)
	{
		Logger.dump(4,"IN the FileReader for populateWorker");
		this.reader = buff;
		
	}
	
	/**
	 * provide multi threaded readfile in synchronized manner
	 * line of file is read line by line and 
	 * addStudent function of INTERFACE DATA STORE 
	 * 
	 */
	public synchronized void readfile()
	{
		String line;
		
		
		
		try {
			while((line = reader.readLine())!= null)
			{

				String record[] = line.split(" ");
				String first = record[0];
				String last = record[1];
				String instructor = record[2];
				int course_no = Integer.parseInt(record[3]);
				info.addStudent(first, last, instructor, course_no);
		
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	
	/**
	 * when threads starts it calls run and then readfile is called for each thread
	 */
	public void run()
	{
		Logger.dump(3,"IN populate Worker thread :run method()");
		this.readfile();
		
	}
	

	

}
