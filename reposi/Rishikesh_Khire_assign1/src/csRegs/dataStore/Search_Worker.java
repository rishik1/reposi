package csRegs.dataStore;

import csRegs.util.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Search_Worker provides implementation of Worker INterface
 * Function of this class is it search open the file and create threds to readFile and search file through
 * FileReader
 * @author rkhire1
 *
 */
public class Search_Worker implements Worker {
	/**
	 * constructor
	 */
	public Search_Worker()
	{
	
		Logger.dump(4,"IN the SearchWorker");
	}
	/**
	 * 
	 * this will create the specified number of threads
	 * call FileReader2 for each thread
	 * On completion join thread
	 * close FileReader and BufferedReader
	 * 
	 * @param no_threads
	 * @param file
	 */
	public Search_Worker(int no_threads,String file)
	{
		FileReader fp1 = null;
	
		Logger.dump(4,"IN the SearchWorker");
		try {
			fp1 = new FileReader(file);
		} 
		catch (FileNotFoundException e)
		{
				e.printStackTrace();
		}
		
    	BufferedReader buff = new BufferedReader(fp1);
    	FileReader2 rd=new FileReader2(buff);
    	
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
			try
			{
				thread[i].join();
			}
			catch (InterruptedException e)
			{
				
				e.printStackTrace();
			}
			
		}
    	
    	try {
			buff.close();
		} 
    	catch (IOException e)
    	{
			e.printStackTrace();
		}
		
	}
	
}
