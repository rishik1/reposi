package chat.server;

import chat.serverUtility.Server;
/**
 * serverMain is the Driver class for server 
 * this class start server
 * @author rkhire1
 *
 */
public class ServerDriver {

	public static void main(String args[]) {
		
		if(args.length >1)
		{
			System.out.println("Wrong number of input");
			System.exit(0);
		}
	
		
		int portNo = Integer.parseInt(args[0]);
		if(portNo <0)
		{
			System.out.println("wrong input");
			System.exit(0);
		}
		Server serverChat = new Server(5);
		serverChat.startServer(portNo);
		

	}

}
