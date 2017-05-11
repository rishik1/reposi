package chat.util;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map.Entry;

import chat.serverUtility.ClientChatHandler;

/**
 * ThreadPool class preinitializes all the threads and provide the threads Server
 * when needed.
 * ThreadPool is singleton class  
 * @author rkhire1
 *
 */

public class ThreadPool {
	private static ThreadPool firstInstance =null;
	private static int j;
	 
	public static HashMap<Integer,MyThread> hm = new HashMap<Integer,MyThread>();
	private  MyThread workerThreads[];  
	private int capacity =0;
	
	
	/**
	 * Private constructor of thread pool so cannot be instantiated outside the class
	 */
	private ThreadPool()
	{
		
	}
/**
 * This method return the one and only instance that can be created of the ThreadPool class
 * @return firstInstance of threadPool
 */
public static ThreadPool getInstance()
	{
		if(firstInstance == null)
			{
				firstInstance = new ThreadPool();
			}
		return firstInstance;
	}
	
/**
 *This threadPoolInit function initializes number of threads equal to input capacity
 *and storing the status of the threads in  myThread object which stored corresponding client socket id
 *and thread status	
 * @param capacity
 */
public void threadPoolInit(int capacity)
	{
		this.capacity = capacity;
		System.out.println(capacity);
		workerThreads = new MyThread[capacity];
		
		for(MyThread thread :workerThreads)
		{
			thread=new MyThread();
			thread.setStatus(false);
			hm.put(j, thread);
			j++;
		}
		
	}


/**
 * This function returns the available instance of the thread by status
 * @param clientSocket
 * @return Thread
 */
public Thread borrowThread(Socket clientSocket)
{
	int i=0;
	Thread giveThread =null;
	Integer key;
	
	Boolean stat;
	
	for (Entry<Integer, MyThread> entry : hm.entrySet()) {
		
	    key = entry.getKey();
	    MyThread thread = entry.getValue();
		stat=thread.getStatus();
		if(!stat)
		{
			thread.makeActive();
			 giveThread=new Thread(new ClientChatHandler(clientSocket));
			thread.setYourThread(giveThread);
			i++;
			break;
		}
	}
	
	return giveThread;
}
/**
 * This function returns hashmap which contains unique entries of clientid and threads 
 * @return HashMap
 */
public HashMap<Integer, MyThread>getAllClients()
{
	return hm;
}


}

