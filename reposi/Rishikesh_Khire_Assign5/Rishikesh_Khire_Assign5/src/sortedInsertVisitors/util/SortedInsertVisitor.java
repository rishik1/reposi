package sortedInsertVisitors.util;
/**
 * This class implements the Visitor interfa
 * @author rkhire1
 *
 */
public class SortedInsertVisitor implements Visitor {

	private FileProcessor fproc= null;
	/**
	 *  The constructor takes in instance of FileProcessor
	 * and set it to a local variable
	 * @param fProcess
	 */
	public SortedInsertVisitor(FileProcessor fProcess)
	{
		this.fproc = fProcess;
	}
	
	/**
	 * The visit function takes instance of MyDataStructure array list and open the file
	 * read it integer by integer as per format and calls the SortedInsert algorithm to sort add
	 * i.e add to correct position 
	 */
	@Override
	public void visit(MyArray list) {
		int a;
		this.fproc.Openfile();
		
		while(true)
		{
			a = this.fproc.nextInt();
			if(a!=-1)
			{
			list.SortedAdd(a);
			}
			else 
			{
				break;
			}
		}
		
		this.fproc.close();
		list.display();
		list.reset();
		
	}
/**
* The visit function takes instance of MyDataStructure vector list and open the file
* read it integer by integer as per format and calls the SortedInsert algorithm to sort add
* i.e add to correct position 
 */
	@Override
	public void visit(MyVector vlist) {

		int a;
		this.fproc.Openfile();
		
		while(true)
		{
			a = this.fproc.nextInt();
			if(a!=-1)
			{
			vlist.sortedInsert(a);
			}
			else 
			{
				break;
			}
		}
		this.fproc.close();
		vlist.display();
		vlist.reset();
		
	
	}
	
	
}
