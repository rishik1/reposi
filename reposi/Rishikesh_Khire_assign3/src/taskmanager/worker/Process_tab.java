package taskmanager.worker;

import taskmanager.util.Logger;
/**
 * Process tab to save process structure
 * @author aashay-Rishikesh
 *
 */
public class Process_tab {
	
	String processname;
	int cpu_vlaue;
	int memory;
	String description;
	String User_Name;

	/**
	 * constructor
	 */
	public Process_tab(){
		Logger.dump(2, "IN Process_tab object constructor");
		}

	/**
	 * Parametrized constructorr
	 * @param processname
	 * @param cpu_vlaue
	 * @param memory
	 * @param description
	 * @param user_Name
	 */
	public Process_tab(String processname, int cpu_vlaue, int memory,
			String description, String user_Name) {
		Logger.dump(2, "IN Performance_tab object constructor");
		this.processname = processname;
		this.cpu_vlaue = cpu_vlaue;
		this.memory = memory;
		this.description = description;
		User_Name = user_Name;
	}

/**
 * toString to print process strucutre
 */
	@Override
	public String toString() {
		return "Process_tab [processname=" + this.processname + ", cpu_vlaue="
				+ cpu_vlaue + ", memory=" + this.memory + ", description="
				+ this.description + ", User_Name=" + this.User_Name + "]";
	}


/*
 * Below are all get and set methods for the process tab
 * 
 */
	public String getProcessname() {
		return processname;
	}


	public void setProcessname(String processname) {
		this.processname = processname;
	}


	public int getCpu_vlaue() {
		return cpu_vlaue;
	}


	public void setCpu_vlaue(int cpu_vlaue) {
		this.cpu_vlaue = cpu_vlaue;
	}


	public int getMemory() {
		return memory;
	}


	public void setMemory(int memory) {
		this.memory = memory;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getUser_Name() {
		return User_Name;
	}


	public void setUser_Name(String user_Name) {
		User_Name = user_Name;
	}

}
