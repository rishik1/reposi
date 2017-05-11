package chat.clientUtility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import chat.util.Logger;
/**
 * This class sets the connection with the Server 
 * creates unique Input output streams to communicate with the server
 * stores backup accepted from client and output it to file on user request
 * @author rkhire1
 *
 */
public class ClientClass {
private String hostName = null;
private int portNo;
private static String bckup = null;
/**
 * this function starts the client
 * create socket for connection with the server
 * provide the menu to the user by provide options to communicate
 * 	do the respective communication functionality
 */
public ClientClass(String hName,int portNum)
{
	this.hostName = hName;
	this.portNo = portNum;
}
public void clientStart()
	{
		InputStreamReader serverStream = null;
		PrintWriter toServer =null;
		BufferedReader serverInput =null;
		String clientWrites = null;
		String serverSays = null;
		Socket clientSocket =null;
		String clientName = null;
		boolean flag = false;
		Scanner fromClient = new Scanner(System.in);
		int choice;
		
		try {
			clientSocket =new Socket(this.hostName,this.portNo);
		} 
		catch (IOException e)
		{
			System.out.println("problem creating socket");
			System.exit(0);
		}
		
		try
		{
			toServer = new PrintWriter(clientSocket.getOutputStream(),true);
			serverStream = new InputStreamReader(clientSocket.getInputStream());
			serverInput =new BufferedReader(serverStream);
		} 
		catch (IOException e)
		{

			System.out.println("problem Input/outptut Stream at Client");
			System.exit(0);
		}
		
		while(true)
		{
			System.out.println(":::::::::: MENU :::::::::");
			System.out.println("1. RECIEVE FROM SERVER");
			System.out.println("2. SEND TO SERVEER");
			System.out.println("3. GIVE YOUR NAME");
			System.out.println("4. Exit from client ");
			System.out.println("Enter choice ");
			clientWrites = fromClient.nextLine();
			choice = Integer.parseInt(clientWrites);
			
			switch(choice)
			{
				
				case 1 :
					try
					{
						serverSays = serverInput.readLine();
						if(serverSays == null)
						{
							System.out.println("NOT CONNECTED.... EXITING");
							try 
							{
								clientSocket.close();
							}
							catch (IOException e)
							{
								System.err.println("ERROR IN CLOSING CLIENT");
								System.exit(0);
							}
							return;
						}			
						
						System.out.println("SERVER SENT  >>"+serverSays+'\n');
						if(flag==true)
						{
							System.out.println("SENDING BACKUP TO DUMP");
							bckup = serverSays;
							Logger.dump(bckup);
							
							flag=false;
						}
					}
					catch (IOException e)
					{
						System.out.println("problem writing from client to server");
						System.exit(0);		
					}
					if(serverSays.equals("exit"))
					break;
				break;
				
				case 2 :
					clientWrites = fromClient.nextLine();
					if(clientName == null)
					{
						System.out.println("MYSELF >>"+clientWrites);
						toServer.println(clientWrites);
					}
					else
					{
						System.out.println(clientName +" >>"+clientWrites);
						toServer.println(clientWrites);
					}
					if(clientWrites.equals("backup"))
					{
						flag =true;
					}
					if(clientWrites.equals("exit"))
					return;
				
				break;
				
				case 3:
					clientWrites = fromClient.nextLine();
					clientName = clientWrites;
					System.out.println("Name"+clientWrites);
					toServer.println("Name"+clientWrites);
				break;
				
				case 4:
					System.out.println("client Closing");
					try {
						clientSocket.close();
						System.exit(0);
					}
					catch (IOException e) {
						System.err.println("Error in closing connection");
						System.exit(0);
				}
					break;
				
				default :
					System.out.println("Wrong choice");
					break;
			}
			
		}
		
	}
	
	
}
