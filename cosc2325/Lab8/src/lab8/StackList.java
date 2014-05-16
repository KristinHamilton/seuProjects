package lab8;
/* *
 * [StackList.java]
 * Author: Kristin Hamilton
 * Desc: StackList interface implemented by StackAImpl() and StackLImpl()
 * Date created:  01-Apr-2014 for Lab8
 * Date last modified: 04-Apr-2014  
 */
public interface StackList<T>
{
    public static final int MAX_ELEMENTS = 50;
    public boolean isEmpty();
    public boolean isFull();
    public int size();
    public void push(T element);
    public T pop();
    public T peek();
    public void clear();

}//end StackList interface