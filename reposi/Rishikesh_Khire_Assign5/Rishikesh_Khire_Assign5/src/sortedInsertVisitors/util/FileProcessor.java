package sortedInsertVisitors.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
/**
 * FileProcessor class provide the functionality of reading file integer by integer
 * @author rkhire1
 *
 */
public class FileProcessor {
	private String filename;
	private BufferedReader buff;

/**
 * Parameterized constructor takes in the filename and set it to a local variable 
 * and initializes the Buffered reader instance
 * @param name
 */
public FileProcessor(String name)
{
	
	this.filename=name;
	this.buff=null;
}

/**
 * OpenFile method is used to open the file
 * create BufferedReader and FileReader to create stream to  read from file
 */
	public void Openfile()
	{
		try{
			this.buff=new BufferedReader(new FileReader(this.filename)); 
		}
		catch(FileNotFoundException e){
		
			System.out.println("File dosen't exist");
			System.exit(1);	
		}
		finally{
		}
	}
	
	/**
	 * The function nextInt reads the file line by line i.e and considering the format of file should be
	 * 1 integer on each line ..this function returns the first integer on the line and ignore other integer 
	 * @return
	 */
	public int nextInt()
	{
		int res = -1;
		String str;
		String[] s;
		try {
			if(buff.ready())
			{
				
				str = buff.readLine();
				if(str != null)
				{
				s= str.split(" ");
					try{
					res = Integer.parseInt(s[0]);
					}
					catch(Exception e)
					{
						System.out.println("improper input file");
						System.exit(0);
					}
					}
				} 
				else
				{
					System.out.println(" ");
				}
			
		} catch (IOException e) {
			System.err.println(" Problem in reading from file");
			System.exit(0);
			
		}
		
		return res;	
	}

	/**
	 * the close function closes the file 
	 */
	public void close() {
		
		try {
			buff.close();
		} catch (IOException e) {
		
			System.err.println("Error in closing File ");
			System.exit(0);
		}
	}



}
