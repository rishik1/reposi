package taskmanager.worker;

import taskmanager.util.Logger;

/**
 * 
 * @author aashay-Rishikesh
 *
 */
public class Performance_tab {
int currentMemUsage;
int currentCpuUsage;
static int totalPhysicalMemory;
static int totalCached;
static volatile int count;
/**
 * constructor
 */
public Performance_tab()
{	
	Logger.dump(2, "IN Performance_tab object constructor");
}
/**
 * Perforamnce tab parametrized constructor
 * @param cMemUsage
 * @param cpuUsage
 * @param totalPhysicalMemory
 * @param totalCached
 */
public Performance_tab(int cMemUsage,int cpuUsage,int totalPhysicalMemory,int totalCached)
{
	Logger.dump(2, "IN Performance_tab object constructor");
	count++;
	this.currentCpuUsage = cpuUsage;
    this.currentMemUsage = cMemUsage;
    if(count ==1)
    {
    	this.totalPhysicalMemory = totalPhysicalMemory;
    	this.totalCached = totalCached;
    }
}


/**
 * Below are all getter and setter for the int currentMemUsage;
currentCpuUsage ,totalPhysicalMemory,totalCached int count;
 * @return
 */
public int getCurrentMemUsage() {
	return currentMemUsage;
}

public void setCurrentMemUsage(int currentMemUsage) {
	this.currentMemUsage = currentMemUsage;
}

public int getCurrentCpuUsage() {
	return currentCpuUsage;
}

public void setCurrentCpuUsage(int currentCpuUsage) {
	this.currentCpuUsage = currentCpuUsage;
}



@Override
public String toString() {
	
	return "Performance_tab [currentMemUsage=" + this.currentMemUsage
			+ ", currentCpuUsage=" + this.currentCpuUsage + ", totalPhysicalMemory="
			+ this.totalPhysicalMemory + ", totalCached=" + this.totalCached + "]";
}

public int getTotalPhysicalMemory() {
	return totalPhysicalMemory;
}

public void setTotalPhysicalMemory(int totalPhysicalMemory) {
	this.totalPhysicalMemory = totalPhysicalMemory;
}

public int getTotalCached() {
	return totalCached;
}

public void setTotalCached(int totalCached) {
	this.totalCached = totalCached;
}


}