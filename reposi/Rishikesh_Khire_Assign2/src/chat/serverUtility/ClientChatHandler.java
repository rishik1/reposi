package chat.serverUtility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map.Entry;
/**
 * This class Threads are created to handle the clients
 * Each clients is given a unique clientid and client name and stored in hasmap hm1 and hname
 *   
 * @author rkhire1
 *
 */
public class ClientChatHandler implements Runnable {

	public Socket socket=null;
	public String converse ="";
	public volatile static int choice;
	public int currentClient = 0;
	public static HashMap<Integer,Socket> hm1=new HashMap <Integer,Socket>();
	public static HashMap<Integer,String> hname = new HashMap <Integer,String>();
	public static boolean switched = true;
	public static int clientid;
	public InputStreamReader clientStream = null;
	public PrintWriter toClient =null;
	public BufferedReader clientInput =null;
	public volatile static Socket choiceSocket; 
	
/**
 * constructor of the thread creates unique entry for
 * each thread in with their respective clientname and clientid in hasmap hm1 and hname
 * each client thread has unique client socket 	
 * @param clientSocket
 */
	public ClientChatHandler(Socket clientSocket)
	{
		this.socket =clientSocket;
		clientid++;
		this.currentClient = clientid;
		hm1.put(clientid, this.socket);
		hname.put(clientid, null);

	}
	

	/**
	 * run method provide functionality of running multiple threads
	 * get user input for sending,receiving message or exiting the client thread
	 * Also get the client to which communication is to be done
	 * if same client then proceed with the respective functionality
	 * else keep this thread waiting and activate another thread.
	 * exiting the server exit by closes all the clients first and exiting from the threads
	 * Server closes in ServerMain 
	 */
	@Override
	public void run() {

		String line[] =null;
		String clientSays = null;
		String serverWrites = null;
		String cname =" ";
		int clientno =100;
		Socket temp=null;
		int i=0;
		
		BufferedReader in = new BufferedReader (new InputStreamReader (System.in));
		
		
		Scanner fromServer = new Scanner(System.in);
		
	while(true)
		{
		
			while(choiceSocket!=this.socket && clientid != 1);

				System.out.println(":::::::::::::::::   MENU   ::::::::::::::");
				System.out.println("1. Send message to specific Client");
				System.out.println("2. Print Message from Client");
				System.out.println("3. quit Server");
				System.out.println("Enter Choice::::::::::::::::");
				if(fromServer.hasNext()){
					choice = fromServer.nextInt();
				}
			
				System.out.println(":::::::::::::::::client's Running are:::::");
				i=1;
				for (Entry<Integer, String> entry : hname.entrySet()) 
				{	
					if(entry.getValue() == null)
				   System.out.println(i +". Client no"+entry.getKey());
					else
					{
						System.out.println(i+"."+entry.getValue());
					}
				   i++;
				}
				
					while(true)
					{
						System.out.println("Enter the choice of client:::::::::");
						clientno = fromServer.nextInt();
						if(hm1.containsKey(clientno))
						{
							temp=hm1.get(clientno);
							break;
						}
						else
						System.out.println("Wrong Choice");
					}
					choiceSocket = temp;
					
			while(choiceSocket!=this.socket);

			
			try
		 	{
			clientStream = new InputStreamReader(socket.getInputStream());
			clientInput = new BufferedReader(clientStream);
			toClient = new PrintWriter(socket.getOutputStream(),true);
		 	} 
		 	catch (IOException e)
		 	{
			System.out.println("Problem in ClientStream ");
			System.exit(0);
		 	}
			switch(choice)
			{
					
				case 1:
					System.out.println("SERVER   > ");
					try
					{
						serverWrites = in.readLine();
					} 
					catch (IOException e1) {
						System.err.println("Problem in readline");
						System.exit(0);
					}  
					this.converse = this.converse +"SREVER :"+serverWrites;
					if(serverWrites.equals("exit"))
						return;
			
					toClient.println(serverWrites);	
				break;
				
				
				case 2:
					try
					{
						clientSays = clientInput.readLine();
					}
					catch (IOException e)
					{
						System.out.println("Client connection lost");
						System.exit(0);
					}
					if(clientSays == null)
					{
						System.out.println("NOT CONNECTED....CLIENT EXITED");
						try 
						{
							socket.close();
						}
						catch (IOException e)
						{
							System.err.println("ERROR IN CLOSING CLIENT");
							System.exit(0);
						}
						hm1.remove(this.currentClient);
						hname.remove(this.currentClient);
							if(hm1.isEmpty())
							{
								System.out.println("No More Clients");
								System.exit(0);
							}
							
							break;
					}			
					
					
					if((cname = hname.get(this.currentClient))!= null)
						{
							System.out.println(cname+"::::::::::>>> "+ clientSays);
							this.converse = this.converse + cname + clientSays + " " ;
						}
					else
						{
							System.out.println("CLIENT NO "+Integer.toString(this.currentClient) +"  >"+ clientSays);
							this.converse = this.converse + "CLIENT NO" + Integer.toString(this.currentClient) + clientSays+" " ;
						}
					if(clientSays!= null)
						{
						
					if(clientSays.equals("backup"))
						{
							System.out.println(this.converse);
							if((cname = hname.get(this.currentClient))!= null)
							{
								this.converse = cname +" "+this.converse + " ";
							}
							else
							{
								this.converse = "CLIENT NO" + Integer.toString(this.currentClient)+this.converse+ " ";
							} 
							toClient.println(this.converse);
						}
					else if(clientSays.contains("Name"))
						{
							cname = clientSays.substring(4, clientSays.length());
							hname.put(this.currentClient,cname);
						
						}
					}	
					if(clientSays.equals("exit"))
						break;
				break;
				
				case 3 : 
					System.out.println("SERVER CLOSING");
					for (Entry<Integer, Socket> entry : hm1.entrySet()) {
					    int key = entry.getKey();
					     Socket val = entry.getValue();
					     System.out.println("CLOSING CLIENT NO"+key);
					    try {
							val.close();
						} 
					    catch (IOException e)
					    {
							System.err.println("Error in clsoing clients");
							System.exit(0);
						}
					    hm1.remove(val);
					    System.exit(0);
					    
					}	
					break;
				default : System.out.println("Wrong Choice");
					break;
			}
	
		}// inner while ends
		
	}
	
}
