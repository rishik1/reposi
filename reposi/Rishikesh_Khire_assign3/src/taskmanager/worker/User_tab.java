package taskmanager.worker;

import taskmanager.util.Logger;

public class User_tab {
String userName;
String status ;


/**
 * Constructor user_tab
 */
public User_tab()
{
	Logger.dump(2, "IN User_tab object constructor");
}

/**
 * Constructor user_tab
 */
public User_tab(String userName,String status)
{
	this.userName = userName;
	this.status = status;
}

/**
 * set user_tabusername 
 */
public String getUserName() {
	return userName;
}

/**
 * set user_tab status
 */
public void setUserName(String userName) {
	this.userName = userName;
}

/**
 * getStatus
 */
public String getStatus() {
	return status;
}

/**
 * set status of 
 */
public void setStatus(String status) {
	this.status = status;
}

/**
 * tostring method to print usertab
 */
@Override
public String toString() {
	return "Users [userName=" + this.userName + ", status=" + this.status + "]";
}


}
