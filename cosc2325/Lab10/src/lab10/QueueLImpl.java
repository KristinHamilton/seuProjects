package lab10;
/* *
 * [QueueLImpl.java]
 * Author: Kristin Hamilton
 * Desc: LinkedList<T> implementation of QueueList<T>
 * Date created:  29-Apr-2014 for Lab10
 * Date last modified: 04-May-2014
 */
public class QueueLImpl<T> implements QueueList<T>
{
    private Node<T> firstNode;
    private Node<T> lastNode;
    private int queueCount;     /* # items in current queue */
    private int maxCount;       /* (mutable) max allowable size for current queue */
    
    /* *
     * Pre:  (nothing)
     * Post: (nothing)
     *       instantiates new QueueLImpl() with default values for attributes
     */
    public QueueLImpl()
    {
        this.firstNode = null;
        this.lastNode = null;
        this.queueCount = 0;
        this.maxCount = 5;
    }
    
    /* *
     * Pre:  (nothing)
     * Post: (String)
     *       if current queue contains 0 elements, returns String stating queue is empty.
     *       otherwise, builds and returns a String of all items in current queueList,
     *       beginning with this.firstNode, which corresponds to the "front of the queue"
     */
    public String toString()
    {
        String toStringOut = "";
        
        if(this.isEmpty())
        {
            toStringOut = "Queue is empty.";
        }
        
        else
        {
            for(Node<T> currentNode = this.firstNode; currentNode != null; currentNode = currentNode.link)
            {
                toStringOut += currentNode.data + "\n";    
                
            }            
        }
                       
        return toStringOut;
        
    }//end toString    
    
    /* *
     * Pre:  (nothing)
     * Post: (int)
     *       returns int this.maxCount, the # items in current queue
     */
    public int getMaxSize()
    {
        return this.maxCount;        
        
    }//end getMaxSize()
    
    /* *
     * Pre:  (int)
     *       expects to receive positive int newMaxSize
     * Post: (nothing)
     *       performs check to verify newMaxSize received as arg is >= 0.
     *       if newMaxSize is invalid, prints error msg to console.
     *       otherwise, performs the following:
     *         if newMaxSize is the same as the current value of maxCount, just exits
     *         without modifying any values.
     *         if newMaxSize is smaller than both the current value of maxCount and the
     *         size of the current queue, removes elements from queue beginning at
     *         lastNode, and stopping only when #elements in queue has been reduced to
     *         newMaxSize. then, sets value of queueCount equal to that of newMaxSize.
     *       sets this.maxCount to newMaxSize, as long as newMaxSize >= 0 and
     *       newMaxSize is different from current value of maxCount.
     *       returns nothing.
     */
    public void setMaxSize(int newMaxSize)
    {
        if(newMaxSize < 0)
        {
            System.out.println("New size must be a nonnegative integer. " +
                    "maxCount has not been modified.");
            return;
        }
        
        if(newMaxSize == maxCount)
        {
            return;            
        }
        
        if(newMaxSize < this.maxCount && newMaxSize < this.queueCount)
        {
            int i = 0;
            
            for(Node<T> currentNode = this.firstNode; currentNode != null; currentNode = currentNode.link)
            {
                i++;
                
                if(i == newMaxSize)
                {
                    currentNode.link = null;
                    this.lastNode = currentNode;
                }
            }
            
            this.queueCount = newMaxSize;
        }
        
        this.maxCount = newMaxSize;
        return;        
        
    }//end setMaxSize()
    
    /* *
     * Pre:  (nothing)
     * Post: (boolean)
     *       compares the size of the current queue to 0.
     *       if size is 0, returns true;
     *       otherwise, returns false.
     */
    public boolean isEmpty()
    {
        boolean isEmpty = false;
        
        if(this.size() == 0)
        {
            isEmpty = true;
        }
        
        return isEmpty;
        
    }//end isEmpty()
    
    /* *
     * Pre:  (nothing)
     * Post: (boolean)
     *       compares the size of the current queue to maxCount, the maximum allowable
     *       size for the queue.
     *       if this.queueCount is less than this.maxCount, returns true;
     *       otherwise, returns false.
     */
    public boolean isFull()
    {
        boolean isFull = false;
        
        if(this.size() >= this.maxCount)
        {
            isFull = true;
        }
        
        return isFull;
        
    }//end isFull()
    
    /* *
     * Pre:  (nothing)
     * Post: (int)
     *       returns the value of queueCount, the # items in the current queue.
     */
    public int size()
    {
        return this.queueCount;
        
    }//end size()
    
    /* *
     * Pre:  (T)
     *       expects to be called on a non-full linkedList implementation of QueueList;
     *       expects to receive non-null T newItem as argument
     * Post: (nothing)
     *       as long as newItem is not null and linkedList isnt full (currently has less
     *       than maxCount elements), adds newItem to queue by making a new node, assigning
     *       newItem as new node's data, linking the new node to lastNode, and then
     *       setting lastNode to the new node; queueCount is then incremented.
     *       returns nothing
     */
    public boolean add(T newItem)
    {
        
        if(this.isFull())
        {
            throw new RuntimeException();
        }
        
        if(newItem == null)
        {
            return false;
        }
        
        Node<T> newNode = new Node<T>();
        newNode.data = newItem;
        
        if(this.isEmpty())
        {
            this.firstNode = newNode;
            this.firstNode.link = this.lastNode;
        }
        
        else  //if(!this.isEmpty())
        {
            this.lastNode.link = newNode;
        }
        
        this.lastNode = newNode;
        this.queueCount++;        
        return true;
        
    }//end add()
    
    /* *
     * Pre:  (nothing)
     *       expects to be called on a non-empty linkedList implementation of QueueList
     * Post: (T)
     *       if queue is empty, throws runtimeException; otherwise, peels arg (removes
     *       element) from back of queue (data at linkedList's lastNode), decrements
     *       queueCount, and returns the removed element (lastNode.data) to the caller
     */
    public T remove()
    {
        /* if theres nothing in the queue, throw a runtime exception */
        if(this.isEmpty())
        {
            throw new RuntimeException();
        }
        
        /* if queue isnt empty, peel last arg off the queue and return it */
        Node<T> removedNode = new Node<T>();
        removedNode = this.lastNode;
        
        for(Node<T> previousNode = this.firstNode, currentNode = previousNode.link; 
            currentNode != null; 
            previousNode = previousNode.link, currentNode = currentNode.link)
        {           
            if(currentNode.link == null)
            {
                previousNode.link = null;
                this.lastNode = previousNode;
            }
        }

        this.queueCount--;
        return removedNode.data;
        
    }//end remove()
    
    /* *
     * Pre:  (nothing)
     *       expects to be called on instance of QueueLImpl that contains at least one
     *       element or non-null node.
     * Post: (T)
     *       if queue is empty, throws runtimeError;
     *       otherwise, returns T data from node at front of current queue (this.firstNode)
     */
    public T front()
    {
        if(this.isEmpty())
        {
            throw new RuntimeException();
        }
        
        /* if queue isnt empty, show data for node at front of queue */
        return this.firstNode.data;
        
    }//end front()
    
    /* *
     * Pre:  (nothing)
     *       expects to be called on instance of QueueLImpl that contains at least one
     *       element or non-null node.
     * Post: (T)
     *       if queue is empty, throws runtimeError;
     *       otherwise, returns T data from node at back of current queue (this.lastNode)
     */
    public T last()
    {
        if(this.isEmpty())
        {
            throw new RuntimeException();
        }
        
        /* if queue isnt empty, show data for node at back of queue */
        return this.lastNode.data;
        
    }//end last()
    
    /* *
     * Pre:  (nothing)
     * Post: (nothing)
     *       removes all items from queue, by setting firstNode and lastNode to null,
     *       and resetting queueCount to zero.
     *       returns nothing.
     */
    public void clear()
    {
        this.firstNode = null;
        this.lastNode = null;
        this.queueCount = 0;        
        return;
        
    }//end clear()
        
}//end QueueLImpl.java