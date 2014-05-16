package lab6;
/* *
 * [VetVisitListImpl.java]
 * Author: Kristin Hamilton
 * Desc: implements VetVisitList interface; contains instance methods for
 *       VetVisitListImpl objects
 *       may store either VetVisitStandard or VetVisitUrgent instances.
 *       sorts visits by date in descending order (most recent to least recent)
 *       [Lab6] added writeToFile().
 * Date created: 04-Mar-2014 for Lab6
 * Date last modified: 17-Mar-2014
 */
import java.io.PrintWriter;
import java.util.GregorianCalendar;

public class VetVisitListImpl implements VetVisitList
{
    private int visitCount;
    private Node<VetVisit> firstNode;
  
    /* ------------------------ overriding methods ------------------------- */
    
    /* *
     * Pre:  (nothing)
     * Post: (nothing)
     *       instantiates a VetVisitListImpl object with attributes firstNode and
     *       visitCount
     */
    public VetVisitListImpl()
    {
        this.firstNode = null;
        this.visitCount = 0;
        
    }//end default constructor
    
    /* *
     * Pre:  (nothing)
     *       expects to be called on an instantiated VetVisitListImpl object
     * Post: (String)
     *       returns a String containing either a message reporting that the list is
     *       currently empty, if list contains no VetVisits, or returns a String
     *       consisting of (one VetVisit per line) all the attributes for each VetVisit
     *       currently in the VetVisitList
     */
    public String toString()
    {
        String toStringOut = "";
        
        if(this.visitCount == 0)
        {
            toStringOut = "No visits scheduled.\n";
        }
        
        else
        {
            toStringOut = "\n";
            
            for(Node<VetVisit> node = this.firstNode; node != null; node = node.link)
            {
                toStringOut += "  " + node.data + "\n";
                
            }//end FOR
            
        }//end ELSE
        
        return toStringOut;
        
    }//end toString()
            
    /* ---------------------- implementation methods ----------------------- */
    
    /* *
     * Pre:  (nothing)
     *       expects to be called on a VetVisitListImpl object
     * Post: (int)
     *       returns value of visitCount attribute for instance of VetVisitListImpl
     */
    public int size()
    {
        return visitCount;
        
    }//end size()
   
    /* *
     * Pre:  (GregorianCalendar)
     *       expects to receive GregorianCalendar date;
     *       searches linked list VetVisitListImpl instance for dates that match date
     *       received as argument
     * Post: (String)
     *       returns String of VetVisits all having dates equal to date received in
     *       method call
     */    
    public String getVetVisitListByDate(GregorianCalendar date)
    {
        String visitsOnDate = "";
        
        /* the four lines commented out would need to be included if traversal of the
         * linked list were approached using a WHILE loop; not necessary for FOR loop.
         */
        //Node<VetVisit> previousNode = null;
        //Node<VetVisit> currentNode = this.firstNode;
        
        for(Node<VetVisit> node = this.firstNode; node != null; node = node.link)
        {
            if(node.data.getDateCalendar().compareTo(date) == 0)
            {
                visitsOnDate += node.data + "\n";                
            }
            
            //previousNode = currentNode;
            //currentNode = currentNode.link;
                        
        }//end FOR
        
        return visitsOnDate;        
        
    }//end getVetVisitListByDate()
    
    /* *
     * Pre:  (VetVisit)
     *       expects to be called on an instance of VetVisitListImpl, expects to receive
     *       VetVisit visitX in method call as argument.
     *       searches linked list VetVisitListImpl for VetVisits having identical dates
     *       and visitIDs to visitX
     * Post: (boolean)
     *       if a VetVisit is found in VetVisitListImpl instance having identical date
     *       AND visitID to visitX received as argument, returns true;
     *       otherwise, returns false
     */
    public boolean contains(VetVisit visitX)
    {
        boolean haveFoundVisit = false;
        
        for(Node<VetVisit> node = this.firstNode; node != null; node = node.link)
        {            
            if(node.data.compareTo(visitX) == 0)
            {
                haveFoundVisit = true;
                break;    //break out of FOR loop
                
            }//end IF
            
        }//end FOR
        
        return haveFoundVisit;
        
    }//end contains()
    
    /* *
     * Pre:  (VetVisit)
     *       expects to be called on an instance of VetVisitListImpl, expects to receive
     *       VetVisit visitX as argument in method call.
     *       tries to add visitX to VetVisitListImpl object: determines whether a VetVisit
     *       with identical date AND visitID already can be found in list instance
     * Post: (boolean)
     *       if no VetVisit can be found in list that has both the same date and same
     *       visitID as visitX, determines where in linked list visitX should be added.
     *       in adding new visits to list, maintains list in sorted order, first by date
     *       (most recent to least recent), and then by visitID.
     *       returns true if visitX has been added to list; otherwise, returns false. 
     */
    public boolean add(VetVisit visitX)
    {
        boolean visitAdded = false;
        
        if(visitX.equals(null))
            return visitAdded;  //returns false
        
        /* check to verify visitX isn't already in the list. if it isn't already in the
         * list, proceed to add visitX to list (if visitX successfully added to list,
         * return true); otherwise, return false */
        if(!this.contains(visitX))
        {
            Node<VetVisit> currentNode = this.firstNode;
            Node<VetVisit> previousNode = null;  //no previous node if you're at firstNode
            Node<VetVisit> node = new Node<VetVisit>();
            node.data = visitX;
            
            /* determine where to add visitX in the linked list: we want to find the
             * first "node.data" already in the list that is larger than visitX
             * when position determined,
             * add visitX to linked list by altering the links of the node to be before it
             * and the node to be after it to include links to VisitX
             */
            while(currentNode != null)
            {
                /* if currentNode.data is less than visitX data, break out of loop */
                if(currentNode.data.compareTo(visitX) < 0)
                {
                    break;
                }
                
                /* move on to next node */
                previousNode = currentNode;
                currentNode = currentNode.link;
                
            }//end WHILE
            
            if(previousNode == null)
            {
                node.link = this.firstNode;
                this.firstNode = node;
            }
            
            else
            {
                node.link = previousNode.link;
                previousNode.link = node;
            }
            
            visitAdded = true;            
            this.visitCount++;
            
        }//end IF(!this.contains(visitX))
        
        //else if(this.contains(visitX)){}
        
        return visitAdded;        
        
    }//end add()
        
    /* *
     * Pre:  (VetVisit)
     *       expects to be called on a VetVisitListImpl object, expects to receive
     *       VetVisit visitX as argument in method call
     *       if visitCount > 0 and list contains visitX, tries to locate position of
     *       visitX within the list for removal
     * Post: (VetVisit)
     *       if visitX is located in list, modifies connections of linked list such that
     *       visitX node is effectively removed by no links pointing to or from its node
     *       returns VetVisit that was removed from list
     *       returns null if 0 visits currently in list, if list does not contain visitX,
     *       or if visitX could not otherwise be found in list
     */
    public VetVisit remove(VetVisit visitX)
    {    
        boolean visitHasBeenRemoved = false;
        int count = 0;
        
        if(!this.contains(visitX)  || this.visitCount == 0)
        {
            return null;
        }
     
        Node<VetVisit> previousNode = null;
        Node<VetVisit> currentNode = this.firstNode;
        
        while(visitHasBeenRemoved == false && count < this.visitCount)
        {
            count++;
            
            /* if the visit to be removed is found in the list */
            if(currentNode.data.compareTo(visitX) == 0)
            {
                /* if previousNode is not null, modify links to skip over currentNode,
                 * effectively removing visitX
                 */
                if(previousNode != null)
                {
                    previousNode.link = currentNode.link;
                    visitHasBeenRemoved = true;
                    break;
                }
                
                /* if previousNode is null and visitX has been found, that means visitX
                 * is in the position of firstNode, as there is no previous node;
                 * "remove" visitX by changing firstNode into whatever node it currently
                 * is linked to (the next node in the list)
                 */
                else  //if(previousNode == null)
                {
                    this.firstNode = currentNode.link;
                    visitHasBeenRemoved = true;
                    break;
                }              
                
            }//end IF(currentNode.data.compareTo(visitX) == 0)
               
            /* if the currentNode is not the node we're looking to remove, then we need
             * to advance to the next node.
             */
            else
            {
                previousNode = currentNode;
                currentNode = currentNode.link;
                
            }//end ELSE

        }//end WHILE
                
        if(visitHasBeenRemoved == true)
        {
            this.visitCount--;
        }
        
        return currentNode.data;
        
    }//end remove()
        
    /* *
     * Pre:  (nothing)
     *       for this linked list structure, if you lose the (connection to the)
     *       first node, then youve essentially lost the connection to the rest of the
     *       data (since the only one were keeping track of is firstNode)
     * Post: (nothing)
     */
    public void removeAll()
    {
        this.firstNode = null;
        this.visitCount = 0;
        
    }
    
    /* *
     * Pre:  (PrintWriter)
     *       expects to be called on an instance of visitList for a single HousePet in a
     *       HousePetList; caller method is HousePet.writeToFile().
     *       expects to receive valid (not null) PrintWriter.
     *       performs null check for PrintWriter and checks that there is at least one
     *       (> 0) VetVisit in the current VetVisitList
     * Post: (nothing)
     *       traverses linkedList of visits, and for each visit calls the appropriate
     *       writeToFile() object method, depending on what type of visit the current
     *       node is an instance of (standard or urgent). VetVisit attributes get written
     *       to output file.
     */
    public void writeToFile(PrintWriter ofile1)
    {
        if(ofile1 != null && this.visitCount > 0)
        {
            for(Node<VetVisit> node = this.firstNode; node != null; node = node.link)
            {
                node.data.writeToFile(ofile1);
                
            }//end FOR
            
        }//end IF(ofile1 != null && this.visitCount > 0)

        return;
        
    }//end writeToFile()
    
}//end VetVisitListImpl.java