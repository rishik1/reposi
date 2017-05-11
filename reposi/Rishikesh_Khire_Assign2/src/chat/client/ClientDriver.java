package chat.client;

import chat.clientUtility.ClientClass;
/**
 * This class is driver of the client 
 * It starts the client for communication with the Server
 * @author rkhire1
 *
 */
public class ClientDriver {
/**
 *This main function get object of client and and start it 
 * @param args
 */
public static void main(String args[]) {
	
		if(args.length >2)
		{
			System.out.println("Wrong number of input");
			System.exit(0);
		}
		String hostname = args[0];
		int portNo = Integer.parseInt(args[1]);
		if(portNo <0)
		{
			System.out.println("wrong input");
			System.exit(0);
		}	
		ClientClass client = new ClientClass(hostname,portNo);
		client.clientStart();
	}
}
