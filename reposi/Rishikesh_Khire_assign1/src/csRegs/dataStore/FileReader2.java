package csRegs.dataStore;

import java.io.BufferedReader;
import java.io.IOException;

import csRegs.util.Logger;
/**
 * FileReader2 class is used to read Search File 
 * it entertains the threads by proviing run mrthod which in turn read search_file simultaneously
 * @author rkhire1
 *
 */
public class FileReader2 implements Runnable {

	Searcher search_obj = new Result_Searcher();    // object of Result_Searcher
	private BufferedReader reader =null;

/**
 * default constructor
 */
	public FileReader2()
	{
		Logger.dump(4,"IN the FileReader2 for searchWorker");
	}
	
/**
 *  Parameterized contructor
 *  sets the buffered reader
 * @param buf
 */
	public FileReader2(BufferedReader buf)
	{
		
		Logger.dump(4,"IN the FileReader2 for searchWorker");
		reader = buf;
	}
	
/**
 * 	readfile to read from the line by line from search_file 
 * search_obj function in INTERFACE Searcher is called
 * 
 */
	public synchronized void readfile()
	{
		String line;		
		try {
			while((line = reader.readLine())!= null)
			{
				search_obj.search(line);
		
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * run() method is provided to start running threads spawn from  Search_Worker
	 */
	public void run() {
			
			Logger.dump(3,"IN Search Thread of FileReader2");
			this.readfile();
		
	}
}

