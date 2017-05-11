package chat.serverUtility;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import chat.util.ThreadPool;
/**
 * This class provide starting server functionality
 * set the socket and port no of the Server and start accepting clients
 * @author rkhire1
 *
 */
public class Server {
	
private int threadPoolCapacity=0;
private static Socket socket =null;
int port;
public static int i;
/**
 *set the capacity of thread pool 
 * @param capacity
 */
public Server(int capacity)
{
	this.threadPoolCapacity=capacity;
}
	
/**
 * This function starts the server and takes portno on which to start
 * getInstance of threadpool and set capacity
 * wait for clients 
 * on accepting clients get thread from thread pool and start client Handler threads
 * @param portNo
 */

	public void startServer(int portNo)
	{
		port =portNo;
		ServerSocket serverSocket =null;
		Thread thread = null;
		ThreadPool myPool = ThreadPool.getInstance();
		myPool.threadPoolInit(this.threadPoolCapacity);
		
		try
		{
			serverSocket = new ServerSocket(port);
		}
		catch (IOException e) {
			System.out.println("SERVER NOT STARTED ");
			System.exit(0);
		}
		System.out.println("SERVER STARTED");
		
		while(true)
		{ 
	         if(i<5)
	         {
				try
				{
					socket = serverSocket.accept();
					System.out.println("----------Client connected----------");
				}
				catch (IOException e1)
				{
					System.out.println("problem in accepting connection");
					System.exit(0);
				}
				
				thread = myPool.borrowThread(socket);
				if(thread == null)
				{
					System.out.println("Client handler not present to handle new client");
					System.exit(0);
				}
				thread.start();
				i++;
	         }
	         	else
	         	{
	         		System.out.println("Server Overloaded");
	         		break;
	         	}
			}
			
		
		try {
			socket.close();
		} catch (IOException e) {

			System.err.println("error in closing server socket");
			System.exit(0);
		}	
		}
			
}


	
