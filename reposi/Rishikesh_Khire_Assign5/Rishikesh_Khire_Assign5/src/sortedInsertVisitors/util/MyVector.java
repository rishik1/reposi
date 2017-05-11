package sortedInsertVisitors.util;

import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;
/**
 * MyVector class implememts the MyDataStructur interface
 * @author rkhire1
 *
 */
public class MyVector implements MyDataStructure{
	private Visitor thisVisitor;
	private Vector<Integer> vector;

	/**
	 * constructor initializes the a vector  
	 */
	public MyVector()
	{
		vector = new Vector<Integer>();
	}
	/**
	 * accept method sets the visitor of the algorithm in a local variable and call the visit method of visitor
	 */
	@Override
	public void accept(Visitor myVisitor) {
		thisVisitor = myVisitor;
		thisVisitor.visit(this);
	}
	
	/**
	 * SortedAdd function adds the input integer to the correct position in the
	 * vector list and move the remaining elements to create space for the the input integer 
	 * @param a
	 */
	public void sortedInsert(int a)
	{
		 int pos = Collections.binarySearch(this.vector, a);
		    if (pos < 0) {
		        this.vector.add(-pos-1, a);
		    }
		
	}
	/**
	 * This function takes input integer and append at the end of vector list
	 * @param a
	 */
	public void insert(int a)
	{
		this.vector.add(a);
	}
	/**
	 * This function sort the vector list
	 */
	public void sort()
	{
		Collections.sort(vector);
	}
/**
 * display method prints the arraylist
 */
	@Override
	public void display() {
	     Iterator iter = vector.iterator();
		 while(iter.hasNext())
		 {
			 System.out.println(" "+iter.next());
		 }
	     
	}
	
	/**
	 * This function clear the arraylist
	 */
	public void reset() {
		this.vector.clear();
		
	}

}
