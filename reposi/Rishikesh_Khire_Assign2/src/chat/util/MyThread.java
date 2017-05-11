package chat.util;
/**
 * This class MyThread creates objects of client Threads with unique status and thread
 * @author rkhire1
 *
 */
public class MyThread {

	private Thread  yourThread=null;
	private Boolean status = false;
	
/**
 * Default constructor
 */
public MyThread() 
	{
		
	}
/**
 * This function make thread status active	
 */
public void makeActive()
	{
		setStatus(true);
	}
/**
 * This function make thread status inactive
 */
public void makeInActive()
	{
		setStatus(false);
	}
/**
 * This function returns the thread of the corresponding my thread object
 * @return Thread
 */
public Thread getYourThread() {
		return yourThread;
	}

/**
 * This function set object's thread to input thread 
 * @param yourThread
 */
public void setYourThread(Thread yourThread) {
		this.yourThread = yourThread;
	}
/**
* This function gets the status of the thread
*@return boolean
**/
public Boolean getStatus() {
		return this.status;
	}
/**
 * this function setStatus of myThread as true
 * @param status
 */
public void setStatus(Boolean status) {
		this.status = status;
	}
}
