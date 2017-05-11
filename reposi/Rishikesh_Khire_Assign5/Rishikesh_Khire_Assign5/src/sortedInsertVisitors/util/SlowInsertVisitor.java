package sortedInsertVisitors.util;

/**
 * This class implements the Visitor interface
 * @author rkhire1
 *
 */
public class SlowInsertVisitor implements Visitor {
	private FileProcessor fproc= null;

	/**
	 * The constructor takes in instance of FileProcessor
	 * and set it to a local variable
	 * @param fProcess
	 */
	public SlowInsertVisitor(FileProcessor fProcess)
	{
		this.fproc = fProcess;
	}
	/**
	 * The visit function takes instance of MyDataStructure array list and open the file
	 * read it integer by integer as per format and calls the SlowInsert algorithm to sort the list
	 * i.e firts appen then sort
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
			list.insert(a);
			list.sort();
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
	 * read it integer by integer as per format and calls the SlowInsert algorithm to sort the list
	 * i.e firts appen then sort
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
			vlist.insert(a);
			vlist.sort();
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
