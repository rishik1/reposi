package sortedInsertVisitors.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
/**
 * MyArray class implements the MyDataStructure interface
 * @author rkhire1
 *
 */
public class MyArray implements MyDataStructure{

	private Visitor thisVisitor;
	private ArrayList<Integer> list ;
	/**
	 * The constructor initializes the Arraylist instance 
	 */
	public MyArray()
	{
		list = new ArrayList<Integer>();
	}
    
	/**
	 * SortedAdd function adds the input integer to the correct position in the
	 * array list and move the remaining elements to create space for the the input integer 
	 * @param a
	 */
	public void SortedAdd(int a)
	{
		 int pos = Collections.binarySearch(this.list, a);
		    if (pos < 0) {
		        this.list.add(-pos-1, a);
		    }
		
	}
	
	/**
	 * Insert function append the input integer to end of arraylist
	 * @param a
	 */
	public void insert(int a)
	{
		list.add(a);
	}
	
	/**
	 * sort function sorts the array list using inbuilt Collections class.
	 */
	public void sort()
	{
		Collections.sort(list);
	}
	
	/**
	 * accept method sets the vivitor of the algorithm in a local variable and call the visit method of visitor
	 */
	@Override
	public void accept(Visitor myVisitor) {
		
		thisVisitor = myVisitor;
		thisVisitor.visit(this);
	}
	/**
	 * display method prints the arraylist
	 */
	@Override
	public void display() {
		Iterator<Integer> iter = list.iterator();
		
		while(iter.hasNext())
		{
			System.out.println(" "+ iter.next());
		}
		
	}
/**
 * This function clear the arraylist
 */
	public void reset() {
	list.clear();
		
	}

	

}
