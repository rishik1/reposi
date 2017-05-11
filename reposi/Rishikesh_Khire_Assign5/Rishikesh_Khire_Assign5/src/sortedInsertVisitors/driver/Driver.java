package sortedInsertVisitors.driver;

import sortedInsertVisitors.util.FileProcessor;
import sortedInsertVisitors.util.MyArray;
import sortedInsertVisitors.util.MyDataStructure;
import sortedInsertVisitors.util.MyVector;
import sortedInsertVisitors.util.SlowInsertVisitor;
import sortedInsertVisitors.util.SortedInsertVisitor;
import sortedInsertVisitors.util.Visitor;

/**
 * Driver class contains main
 * @author rkhire1
 *
 */
public class Driver {
/**
 * Main function create instance of FileProcessor and pass filename to the constructor 
 * File name is taken input from command line argument
 * Instance of MyDatastrucutre are created implementing MyArray and MyVector
 * Instance of Visitor as SortedInsertVisitor and SlowInsertVisitor are created and accept method is called
 * on myArray and myVector
 * @param args
 */
	public static void main(String[] args) 
	{ 
		
		String filename;
		if(args.length==1)
		{
			filename=args[0];
			FileProcessor proc = new FileProcessor(filename);
	        MyDataStructure mArray = new MyArray();
	        MyDataStructure mVector = new MyVector();
	        
	        Visitor sortedInsert = new SortedInsertVisitor(proc);
	        Visitor normalInsert = new SlowInsertVisitor(proc);
	        
		System.out.println("Sorted Insert on ARRAY");
	        mArray.accept(sortedInsert);
	        
		System.out.println("Insert then SORT on ARRAY");
	        mArray.accept(normalInsert);

	        System.out.println("Sorted Insert on Vector");
	        mVector.accept(sortedInsert);

	        System.out.println("Inset and then sort on vector");
	        mVector.accept(normalInsert);
	        
			
		}
		else{
			System.out.println("Inproper input ");
			System.exit(0);
		}
	
	}

}
