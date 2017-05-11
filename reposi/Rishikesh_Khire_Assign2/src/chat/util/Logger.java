package chat.util;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
/**
 * This class provide the Logger functionality 
 * dumping the backup to file on user input
 * @author rkhire1
 *
 */
public class Logger {
/**
 * default constructor
 */
private Logger()
	{
		
	}
/**
 * This function dump the backup to the output file with the
 * name of the client
 * @param msg
 */
public static void dump(String msg)
	{
		PrintWriter writer =null;
		String fname[];
			fname = msg.split(" ");
			System.out.println(fname[0]);
		
			try {
				System.out.println("file created");
				writer = new PrintWriter(fname[0], "UTF-8");
			} catch (FileNotFoundException e) 
			{
				System.err.println("Error in writing to FIle");
				System.exit(0);
			}
			catch (UnsupportedEncodingException e) {
				System.err.println("Error in writing to FIle");
				System.exit(0);
			}
			
			writer.println(msg);
			writer.close();
		
	}
	
}

