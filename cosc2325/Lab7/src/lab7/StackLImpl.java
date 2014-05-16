package lab7;
/* *
 * [StackLImpl7.java]
 * Author: Kristin Hamilton
 * Desc: LinkedList<T> implementation of StackList7<T>
 * Date created:  18-Mar-2014 for Lab7
 * Date last modified: 28-Mar-2014
 */
public class StackLImpl<T> implements StackList<T>
{
    private Node<T> firstNode;
    private int stackCount;
    
    /* *
     * Pre:  (nothing)
     * Post: (nothing) instantiates new StackLImpl() with default values for attributes
     */
    public StackLImpl()
    {
        this.firstNode = null;
        this.stackCount = 0;
    }
    
    /* *
     * Pre:  (nothing)
     * Post: (String)
     *       builds and returns a String of all items in this.stackList, beginning with
     *       this.firstNode, which corresponds to the "top of the stack"
     * 
     * Note: for LinkedLists, "top of stack" corresponds to the location at which we add
     *       new elements, and is at the front or firstNode (in contrast to ArrayList,
     *       where "top of the stack" would be the highest non-null index of ArrayList)
     */
    public String toString()
    {
        String toStringOut = "";
        
        for(Node<T> currentNode = this.firstNode; currentNode != null; currentNode = currentNode.link)
        {
            toStringOut += currentNode.data + "\n";    
            
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
        
        if(this.size() == 0)
            isEmpty = true;
        
        return isEmpty;
        
    }//end isEmpty()
    
    /* *
     * Pre:  (nothing)
     * Post: (boolean)
     *       compares the size of the current stack to MAX_ELEMENTS, the maximum allowable
     *       size for the stack. if this.stackCount is not less than the max, returns true;
     *       otherwise, returns false.
     */
    public boolean isFull()
    {
        boolean isFull = false;
        
        if(this.size() >= MAX_ELEMENTS)
            isFull = true;
        
        return isFull;
        
    }//end isFull()
    
    /* *
     * Pre:  (nothing)
     * Post: (int)
     *       returns the value of stackCount, the count of items on the current stack.
     */
    public int size()
    {
        return this.stackCount;
        
    }//end size()
    
    /* *
     * Pre:  (T)
     *       expects to be called on a non-full linkedList implementation of StackList;
     *       expects to receive non-null T newItem as argument
     * Post: (nothing)
     *       as long as newItem is not null and linkedList isnt full (currently has less
     *       than 5 elements), adds newItem to the stack by making a new node, assigning
     *       newItem as the new node's data, linking the node to firstNode, and then
     *       setting firstNode to the new node; stackCount is then incremented.
     *       returns nothing
     */
    public void push(T newItem)
    {
        if(newItem != null && !this.isFull())
        {       
            Node<T> newNode = new Node<T>();
            newNode.data = newItem;
            newNode.link = this.firstNode;
            this.firstNode = newNode;
            this.stackCount++;
        }
        
        return;
        
    }//end push()
    
    /* *
     * Pre:  (nothing)
     *       expects to be called on a non-empty linkedList implementation of StackList
     * Post: (T)
     *       if stack is empty, throws runtimeException; otherwise, peels arg (removes
     *       element) from top of stack (data at linkedList's firstNode), decrements
     *       stackCount, and returns the removed element (firstNode.data) to the caller
     */
    public T pop()
    {
        /* if theres nothing on the stack, throw a runtime exception */
        if(this.isEmpty())
            throw new RuntimeException();
        
        /* if stack isnt empty, peel first arg of the stack and return it */
        Node<T> popNode = new Node<T>();
        popNode = this.firstNode;
        this.firstNode = this.firstNode.link;
        this.stackCount--;
        
        return popNode.data;
        
    }//end pop()
    
    /* *
     * Pre:  (nothing)
     * Post: (T)
     * if stack is empty, throws runtimeError; otherwise, returns arg from the top of the
     * current stack (linkedList's firstNode)
     */
    public T peek()
    {
        if(this.isEmpty())
            throw new RuntimeException();
        
        /* if stack isnt empty, show arg from the top of the stack */
        return this.firstNode.data;
        
    }//end peek()
    
    /* *
     * Pre:  (nothing)
     * Post: (nothing)
     *       removes all items from the stack, by setting firstNode to null and
     *       stackCount to zero
     */
    public void clear()
    {
        this.firstNode = null;
        this.stackCount = 0;
        
        return;
        
    }//end clear()
        
}//end StackLImpl.java