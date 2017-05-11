package taskmanager.worker;

import taskmanager.filters.Fileter;

/**
 * Interface subject is called which has 3 methods
 * notify
 * register observer
 * remove observer
 * @author aashay-Rishikesh
 *
 */
public interface I_Subject {
	public void nofify();
	public void register(I_Observer o,Fileter f);
	public void remove(I_Observer o);

}
