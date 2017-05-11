package sortedInsertVisitors.util;
/**
 * MyDataStructure interface class provide abstraction to MyArray ans MyVector class
 * @author rkhire1
 *
 */
public interface MyDataStructure {
	public void accept(Visitor myVisitor);
	public void display();

}
