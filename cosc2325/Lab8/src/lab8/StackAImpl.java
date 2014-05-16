package lab8;
/* *
 * [StackAImpl.java]
 * Author: Kristin Hamilton
 * Desc: ArrayList<T> implementation of StackList<T>
 * Date created:  01-Apr-2014 for Lab8
 * Date last modified: 04-Apr-2014
 */
import java.util.ArrayList;

public class StackAImpl<T> implements StackList<T>
{
    private ArrayList<T> stackList;
    /* this.stackList is an attribute of the StackAImpl class, and is of the type
     * ArrayList.
     * the 'this' keyword refers to an instance of the current class, and is of the type
     * StackList.
     * so can call methods for ArrayLists given in JavaSE API only on stackList.
     */
    
    /* *
     * Pre:  (nothing)
     * Post: (nothing) instantiates new StackAImpl() with default values for attributes
     */
    public StackAImpl()
    {
        this.stackList = new ArrayList<T>();
        return;
    }
    
    /* *
     * Pre:  (nothing)
     * Post: (String)
     *       builds and returns a String of all items in this.stackList, beginning at the
     *       highest non-null index of the ArrayList, which corresponds to the "top of
     *       the stack"
     *       
     * Note: for ArrayList, "top of stack" corresponds to the location at which we add
     *       new elements, and is the end of the ArrayList (in contrast to LinkedLists,
     *       where "top of the stack" would be the front or firstNode)
     */
    public String toString()
    {
        String toStringOut = "";
        
        for(int i = this.stackList.size(); i > 0 ; i--)
        {
            toStringOut += this.stackList.get(i - 1) + "\n";    
            
        }//end FOR
               
        return toStringOut;
        
    }//end toString
    
    /* *
     * Pre:  (nothing)
     * Post: (boolean)
     *       compares the size of the current stack to 0. if size is 0, returns true;
     *       otherwise, returns false.
     */
    public boolean isEmpty()
    {
        boolean isEmpty = false;
        
        if(this.stackList.size() == 0)
            isEmpty = true;
        
        return isEmpty;
        
    }//end isEmpty()
    
    /* *
     * Pre:  (nothing)
     * Post: (boolean)
     *       compares the size of the current stack to MAX_ELEMENTS, the maximum allowable
     *       size for the stack. if #elements is less than the max, returns true;
     *       otherwise, returns false.
     */
    public boolean isFull()
    {
        boolean isFull = false;
        
        if(this.stackList.size() >= MAX_ELEMENTS)
            isFull = true;
        
        return isFull;
        
    }//end isFull()
    
    /* *
     * Pre:  (nothing)
     * Post: (int)
     *       returns the size of the current stack, which is the number of elements in the
     *       ArrayList (stackList)
     */
    public int size()
    {
        return this.stackList.size();
        
    }//end size()
    
    /* *
     * Pre:  (T)
     *       expects to be called on a non-full ArrayList implementation of StackList;
     *       expects to receive non-null T newItem as argument
     * Post: (nothing)
     *       as long as newItem is not null and stackList isnt full (currently has less
     *       than 5 elements), calls (ArrayList.)add() on stackList to add newItem to the
     *       stack. returns nothing
     */
    public void push(T newItem)
    {
        /* null check for element */
        if(newItem != null && !this.isFull())
            this.stackList.add(newItem);
        
        return;
        
    }//end push()
    
    /* *
     * Pre:  (nothing)
     *       expects to be called on a non-empty ArrayList implementation of StackList
     * Post: (T)
     *       if stack is empty, throws RuntimeException; otherwise, peels arg (removes
     *       element) from top of the stack (element at highest existing non-null index of
     *       stackList) and returns the removed element (T poppedItem) to the caller
     */
    public T pop()
    {
        if(this.isEmpty())
            throw new RuntimeException();
                
        //pop procedure
        int lastIndex = this.stackList.size() - 1;
        T poppedItem = this.stackList.get(lastIndex);
        this.stackList.remove(lastIndex);
        
        return poppedItem;
        
    }//end pop()
    
    /* *
     * Pre:  (nothing)
     * Post: (T)
     *       if stack is empty, throws runtimeError; otherwise, returns T peekItem from
     *       the top of the current stack (item at highest existing non-null index of
     *       stackList)
     */
    public T peek()
    {
        if(this.isEmpty())
            throw new RuntimeException();
        
        //peek procedure
        int lastIndex = this.stackList.size() - 1;
        T peekItem = this.stackList.get(lastIndex);
        
        return peekItem;
        
    }//end peek()
    
    /* *
     * Pre:  (nothing)
     * Post: (nothing)
     *       removes all items from the stack, by calling (ArrayList.)clear() on stackList
     */
    public void clear()
    {
        this.stackList.clear();
        return;
        
    }//end clear()
    
}//end StackAImpl.java