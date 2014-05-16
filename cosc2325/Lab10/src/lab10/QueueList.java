package lab10;
/* *
 * [QueueList.java]
 * Author: Kristin Hamilton
 * Desc: StackList interface implemented by QueueLImpl()
 * Date created:  29-Apr-2014 for Lab10
 * Date last modified: 04-May-2014  
 */
public interface QueueList<T>
{
    public boolean isEmpty();
    /* returns true if this.size() returns 0; otherwise, returns false */
    
    public boolean isFull();
    /* returns true if this.size() == this.getMaxSize(); otherwise, returns false */
    
    public int size();
    /* returns #elements in current queue */
    
    public int getMaxSize();
    /* returns this.maxCount */
    
    public void setMaxSize(int newSize);
    /* sets maxCount to newSize if newSize >= 0. if there are currently more than newSize
     * elements in current queue, removes elements as necessary from queue, beginning
     * with last element; returns nothing. */
    
    public boolean add(T element);
    /* throws RuntimeException if this.isFull();
     * otherwise, adds element to queue; increments queueCount */
    
    public T remove();
    /* throws RuntimeException if this.isEmpty();
     * otherwise, removes and returns last element of queue; decrements queueCount */
    
    public T front();
    /* throws RuntimeException if this.isEmpty();
     * otherwise, returns first element of current queue */
    
    public T last();
    /* throws RuntimeException if this.isEmpty();
     * otherwise, returns last element of current queue */
    
    public void clear();
    /* removes all elements from current queue; resets queueCount to zero */

}//end QueueList interface
