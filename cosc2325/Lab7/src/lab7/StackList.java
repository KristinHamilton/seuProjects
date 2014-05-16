package lab7;
/* *
 * [StackList.java]
 * Author: Kristin Hamilton
 * Desc: StackList interface implemented by StackAImpl7() and StackLImpl7()
 * Date created:  18-Mar-2014 for Lab7
 * Date last modified: 28-Mar-2014  
 */
public interface StackList<T>
{
    public static final int MAX_ELEMENTS = 5;
    public boolean isEmpty();
    public boolean isFull();
    public int size();
    public void push(T element);
    public T pop();
    public T peek();
    public void clear();

}//end StackList7 interface
