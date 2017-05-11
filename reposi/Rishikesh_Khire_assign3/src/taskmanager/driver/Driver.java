package taskmanager.driver;

import taskmanager.util.FReader;
import taskmanager.util.Logger;
/**
 * Driver class contains main method 
 * use to call FReader to initiate task Manager
 * 
 * @author aashay-Rishikesh 
 *
 */
class Driver {
/**
 * Main method is use to call Freader which starts taskmanager
 * input from console is Debug_value
 * @param args
 */
	public static void main(String args[])
	{
		int debuglevel;
		String filename;
		if(args.length==2)
		{
			filename=args[0];
			//Integer.parseInt(args[1]);
			
			debuglevel=Integer.parseInt(args[1]);

			if(debuglevel>=0 && debuglevel<=4)
			{

				Logger.setDEBUG_LEVEL(debuglevel);
				FReader f=new FReader(filename);
				f.Openfile();
				f.read();
			}
			else
			{
				System.out.println("Wrong  input");
				System.exit(0);
			}
		}
		else
		{
			System.out.println("Wrong no of input");
			System.exit(0);
		}
	}

}
