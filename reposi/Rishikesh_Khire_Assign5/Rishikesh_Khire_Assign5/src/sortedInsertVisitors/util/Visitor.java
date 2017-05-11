package sortedInsertVisitors.util;
/**
 * Visitor interface provide the abstraction to sloInsertVisito and sortedInsertVisitor
 * @author rkhire1
 *
 */
public interface Visitor {
	public void visit(MyArray list);
	
	public void visit(MyVector vlist);
	


}
