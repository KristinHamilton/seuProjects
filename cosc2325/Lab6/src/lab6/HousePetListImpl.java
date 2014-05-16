package lab6;
/* *
 * [HousePetListImpl.java]
 * Author: Kristin Hamilton
 * Desc: WITH CORRECTIONS. sort methods corrected.
 *       implements HousePetList; a collection of methods for HousePetListImpl objects
 *       methods include the following.
 *       [Lab2] default and full constructors, toString(), readFromScanner(),
 *       sortByChipIdentifier(), sortByName();
 *       [Lab3] populate(), add(), remove(), removeAll(), displayAll(), find(),
 *       getSize(), displayExitMsg(), contains(), and binarySearch().
 *       [Lab4] getByName(), modifyAge(), writeToFile(), exitSave(), isSaved().
 *       added attribute to HousePetListImpl class: boolean hasBeenSaved.
 *       [Lab5] did not modify
 *       [Lab6] displayHousePet(), addVetVisit(), removeVetVisit().
 * Date created: 04-Mar-2014 for Lab6
 * Date last modified: 17-Mar-2014
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class HousePetListImpl implements HousePetList
{
    private HousePet[] hpList;
    private int hpCount;
    private boolean hasBeenSaved;
    
    /* *
     * Pre:  (none - default/no-arg constructor)
     * Post: (none) instantiates new HousePetListImpl
     * default constructor for HousePetListImpl object
     */
    public HousePetListImpl()
    {
        this.hpList = new HousePet[HousePetList.MAX_SIZE];
        this.hpCount = 0;  /* counter for # objects currently in hpArray */
        this.hasBeenSaved = true;
        
    }//end default constructor    
    
    /* *
     * Pre:  (none) called on a HousePetList object (hpArray)
     * Post: if hpArray is null or contains 0 HousePet objects, returns String of
     *       "hpArray is empty";
     *       otherwise, returns hpArray in human-readable String form, with each HousePet
     *       object being represented on a single line
     */
    public String toString()
    {
        String toStringOut = "";
        
        if(this.hpCount == 0)
        {
            toStringOut += "hpArray is empty";
            
        }// end IF
        
        for(int i = 0; i < this.hpCount; i++)
        {
            toStringOut += hpList[i] + "\n";
            
        }//end FOR
        
        return toStringOut;
        
    }//end toString()    
    
    /* *
     * Pre:  requires that HousePetList objects (type: list/array) be instantiated in
     *       place where this method is called from.
     *       expects to receive Scanner inputSource, where inputSource is a text file
     *       containing HousePet data; this method does not use this file directly, but
     *       rather passes inputSource in call to HousePet.readFromScanner().
     *       FileNotFoundException is expected to be thrown in caller method,
     *       though the method below will check to make sure inputSource is not null.
     * Post: (none) list/array of HousePet objects (hpArray) is populated with HousePets:
     *       each time HousePet.readFromScanner() returns true (meaning that all
     *       attributes have been read in), and as long as hpCount < MAX_SIZE,
     *       this method will perform the following actions. 
     *         create a new HousePet object, then call add() to enable checking for
     *         duplicates. add() will also do the following: increment hpCount, and
     *         call sortByChipIdentifier(). then, call sortByChipIdentifier(), to keep
     *         hpArray in sorted order.
     *         
     *         if a HousePet has been added to list, hasBeenSaved will be set to false.
     */
    public void readFromScanner(Scanner inputSource)
    {
        if(inputSource == null)
        {
            System.out.println("no source file found; exiting readFromScanner()");
            return;
        }
        
        HousePet newHP = new HousePet();
        boolean allAttributesFound = newHP.readFromScanner(inputSource);
        boolean hpAdded = false;
        
        while(allAttributesFound == true && hpCount < HousePetList.MAX_SIZE)
        {
            /* add newHP to end of hpArray, increment hpCount */
            hpAdded = this.add(newHP);
                
            /* instantiate new HousePet, then call HousePet.readFromScanner() to get
             * the HousePet's data/attributes from input text file inputSource */
            newHP = new HousePet();
            allAttributesFound = newHP.readFromScanner(inputSource);
            
        }//end WHILE
            
        /* keep hpArray in sorted order (by ChipId) */
        sortByChipIdentifier();
        
        if(hpAdded == true)
        {
            hasBeenSaved = false;
        }
        
        return;
        
    }//end readFromScanner()   
    
    /* *
     * Pre:  (none - selection sort)
     * Post: (none) sorts hpArray by chipID of HousePet by calling
     *       HousePet.compareTo() on HousePet at given index in hpArray
     */
    public void sortByChipIdentifier()
    {   
        /* iterator1(i):  iterate over hpArray starting at index 0 */
        for(int i = 0; i < hpCount - 1; i++)
        {
            int min = i;
            
             /* dont do anything if theres not at least 2 elements in hpArray */
             if(hpCount >= 2)
             {
                 /* iterator2(k):  iterate over hpArray starting at index 1 */
                 for(int k = i + 1; k < hpCount; k++)
                 {
                     /* if chipId of HousePet at hpArray[i] is larger than that of
                      * HousePet at hpArray[i+1], change value of min from i to k */
                     if((hpList[k].compareTo(hpList[i])) < 0)
                     {
                         min = k;
                                                 
                     }//end IF
                                       
                 }//end FOR(k)
                 
                 /* swap the positions of the two HousePets within the array, by
                  * using a temp "middleman" to preserve the value of hpArray[i] for
                  * the duration of the swap */
                 HousePet temp = hpList[i];
                 hpList[i] = hpList[min];
                 hpList[min] = temp;
                
             }//end IF(hpCount >= 2)
            
        }//end FOR(i)
        
    }//end sortByChipIdentifier()   
    
    /* *
     * Pre:  (none - selection sort)
     * Post: (none) sorts hpArray by pet name of HousePet by calling
     *       HousePet.compareByName()
     */
    public void sortByName()
    {   
        /* iterator1(i):  iterate over hpArray starting at index 0 */
        for(int i = 0; i < hpCount - 1; i++)
        {
            int min = i;
            
            /* dont do anything if theres not at least 2 elements in hpArray */
            if(hpCount >= 2)
            {
                /* iterator2(k):  iterate over hpArray starting at index 1 */
                for(int k = i + 1; k < hpCount; k++)
                {
                    /* if compareByName() returns a negative int
                     * change value of min from i to k */
                    if((hpList[k].compareByName(hpList[i])) < 0)
                    {
                        min = k;
                                                
                    }//end IF
                                    
                }//end FOR(k)
                
                /* swap the positions of the two HousePets within the array, by
                 * using a temp "middleman" to preserve the value of hpArray[i] for
                 * the duration of the swap */
                HousePet temp = hpList[i];
                hpList[i] = hpList[min];
                hpList[min] = temp;
                        
            }//end IF(hpCount >= 2)
            
        }//end FOR(i)
        
        return;
        
    }//end sortByName()    
    
    /* ---------------------------- new methods ---------------------------- */    
    
    /* *
     * Pre:  called by Lab6Menu.main(), case 'a'.
     *       expects to receive instantiated HousePetList.
     *       prompts user to enter name of file containing HousePet data.
     *       if filename invalid, displays error msg;
     *       otherwise, creates new Scanner with filename, and calls readFromScanner()
     * Post: calls hpArray.readFromScanner() if able to create new Scanner; hpArray will
     *       be populated with HousePets using file data, if the data is valid
     *       
     *       if HousePets have been added to list, hasBeenSaved will have been set to
     *       false in readFromScanner().
     */
    public static void populate(HousePetList hpArray)
    {
        @SuppressWarnings("resource")
        Scanner console = new Scanner(System.in);
        String filename = "";
        
        System.out.println("Please enter the filename for the file containing HousePet " +
            "data");
                
        try
        {
            filename = console.nextLine(); 
            File file1 = new File(filename);
            Scanner ifile1 = new Scanner(file1);
            hpArray.readFromScanner(ifile1);
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Error: the file " + filename + " was not found " + e);

            return;            
        }
        
        return;
        
    }//end populate()
    
    /* *
     * Pre:  (HousePet)
     *       expects to be called on an instance of HousePetListImpl
     *       expects to receive an instantiated HousePet for whom the chipID has been set
     *       to a user-provided int from within the caller method
     *       caller method is Lab6Menu.menuRemoveVisit(), which performs the following
     *       actions prior to calling this method.
     *         checks to see whether a HousePet with the given chipID actually exists in
     *         the HousePetList
     * Post: (nothing)
     *       this method was written to provide a means of assigning a given "temp"
     *       HousePet to a HousePet currently in the HousePetList from outside of this
     *       class, if the chipIDs match.
     *       traverses hpList, checking the chipID of tempHousePet against the chipID of
     *       each HousePet in the current list. if the chipIDs are identical, assigns
     *       that HousePet in the list to tempHousePet (kind of slaps a name tag on it to
     *       say 'hi im hpList[i]' so its associated with/given the identity of a HousePet
     *       in the current list).
     *       if a match is found, then the assignment step mentioned above is carried out,
     *       and it breaks out of the FOR loop.
     *       then, displays that HousePet's data in the console.
     */
    public void displayHousePet(HousePet tempHousePet)
    {        
        for(int i = 0; i < hpCount; i++)
        {
            if(tempHousePet.getChipId() == hpList[i].getChipId())
            {
                tempHousePet = hpList[i];
                break;
            }
            
        }//end FOR
        
        System.out.println(tempHousePet);
        return;
        
    }//end displayHousePet()
    
    /* *
     * Pre:  called by Lab6Menu.main(), case 'e'.
     *       expects to receive instantiated HousePetList.
     *       calls toString on hpArray, because hpArray appears in a String context
     * Post: all HousePets currently in the list are displayed, each one on a single line
     */
    public static void displayAll(HousePetList hpArray)
    {
        hpArray.sortByChipIdentifier();
        System.out.println("The current list of HousePets is shown below.\n");
        System.out.print(hpArray);      
        return;
        
    }//end displayAll()
        
    /* *
     * Pre:  called by exitSave() and Lab6Menu.main(), default case.
     * Post: displays farewell msg to user, informing them the program is being exited
     */
    public static void displayExitMsg()
    {
        System.out.println("Thank you for using the HousePet program. Exiting program");
        System.exit(0);
        
    }//end displayExitMsg()
    
/* ----------------------------------------------------------------------------------- */

    /* *
     * Pre:  called by menuAdd() and readFromScanner().
     *       expects to receive instantiated HousePet.
     *       checks whether there is room for an additional housepet in hpArray, and 
     *       whether a HousePet with housepet's same chipID already exists in hpArray.
     * Post: if there is not room in list for another HousePet, or if a HousePet with
     *       housepet's chipID is found in the current list, that HousePet will not be
     *       added to the list, and an error msg will be displayed to user containing
     *       the "offending" HousePet's info; add() will return false in this case.
     *       if the checks give the go-ahead, housepet is added to hpArray, hpCount is
     *       incremented, and hpArray is sorted by chipID. returns true if housepet was
     *       added to the list.
     *       
     *       if housepet has been added to the list, sets hasBeenSaved to false.
     */
    public boolean add(HousePet housepet)
    {
        boolean addedHousePet = false;
        boolean containsHP = this.contains(housepet);
        
        /* don't try to add housepet to hpArray under the following conditions:
         *   if hpCount already exceeds or equals MAX_SIZE
         *   AND/OR
         *   if hpArray already contains a HousePet with the same chipID as housepet
         */
        if(hpCount >= MAX_SIZE || containsHP == true)
        {
            System.out.println("Error [unable to add HousePet] " + housepet);
                      
            return addedHousePet;
        }
        
        /* if there's room in hpArray, and if theres not already another HousePet in
         * hpArray with the same chipID as housepet, then do the following:
         *   set hpArray sub hpCount to housepet
         *   increment hpCount
         *   sort hpArray by chipID
         *   change boolean addedHousePet to true
         *   set hasBeenSaved to false
         */
        hpList[hpCount] = housepet;
        hpCount++;
        this.sortByChipIdentifier();
        addedHousePet = true;
        hasBeenSaved = false;
        
        return addedHousePet;
        
    }//end add()
    
    /* *
     * Pre:  (int chipID, VetVisit visit)
     *       expects to be called on an instance of a HousePetListImpl.
     *       expects to receive int chipID and VetVisit visitX.
     *       caller method is Lab6Menu.menuAddVisit(), which checks that a HousePet
     *       with chipID of int chipID already exists in the HousePetList with a call
     *       to contains().
     * Post: (boolean)
     *       tries to add visitX to visitList for tempHousePet.
     *       if visitX is successfully added to the visitList for tempHousePet,
     *       visitCount is incremented in VetVisitListImpl.add() and this method returns
     *       true; otherwise, returns false
     */
    public boolean addVetVisit(int chipID, VetVisit visitX)
    {        
        boolean hasBeenAdded = false;
        HousePet tempHousePet = new HousePet();
        tempHousePet.setChipId(chipID);
        
        /* assign a HousePet from the hpList to tempHousePet if their chipIDs match */
        for(int i = 0; i < hpCount; i++)
        {
            if(tempHousePet.getChipId() == hpList[i].getChipId())
            {
                tempHousePet = hpList[i];
                break;
            }
            
        }//end FOR
        
        /* VetVisitListImpl.add(visitX) will do a null check for visitX and verify
         * that the visit thats trying to be added doesnt already exist in the visitList.
         * if visit is added, VVLI.add() increments visitCount and returns true. */
        hasBeenAdded = tempHousePet.getVisitList().add(visitX);
        
        return hasBeenAdded;
        
    }//end addVetVisit()
    
    /* *
     * Pre:  called by menuRemove().
     *       expects to receive instantiated HousePet.
     *       calls binarySearch() to see whether housepet with given chipID is in list.
     * Post: if chipID not found, returns null;
     *       otherwise, stores HousePet to be removed in housepet field, moves each
     *       HousePet up one position in the list, sets the last index (hpCount - 1)
     *       to null, decrements hpCount, and calls sortByChipIdentifier() to re-sort
     *       hpArray. then, returns housepet.
     *       
     *       if housepet has been removed from the list, sets hasBeenSaved to false.
     */
    public HousePet remove(HousePet housepet)
    {   
        HousePet removedHP = new HousePet();
        
        int hpIndex = this.binarySearch(housepet);
        
        if(hpIndex == -1)  //HousePet not found in list
        {
            return null;
        }
        
        else  //if (HousePet found in list)
        {            
            /* store HousePet at hpIndex in returnHP */
            removedHP = hpList[hpIndex];
            
            for(int i = hpIndex; i < hpCount - hpIndex; i++)
            {
                /* move all the HousePets up a spot in the array */
                hpList[i] = hpList[i + 1];
                
            }//end FOR
            
            /* set last HousePet's former index to null since we moved it */
            hpList[hpCount - 1] = null;
            
            /* decrement hpCount */
            hpCount--;
            
            /* sort hpArray */
            this.sortByChipIdentifier();
            
            hasBeenSaved = false;
            
        }//end ELSE

        /* return HousePet */
        return removedHP;
        
    }//end remove()
    
    /* *
     * Pre:  (int, VetVisit)
     *       expects to receive user-provided int chipID of the HousePet for whom the user
     *       would like to remove a VetVisit; also expects to receive VetVisit visitX,
     *       the VetVisit the user would like to remove from HousePet's visitList. 
     *       called by Lab6Menu.menuRemoveVisit(), if current HousePetList contains a
     *       HousePet with the same chipID as the chipID that is provided by the user.
     *       this method assigns the HousePet with the matching chipID in the current list
     *       to tempHousePet
     * Post: (boolean)
     *       tries to remove VetVisit visitX from tempHousePet's visitList. if successful,
     *       VetVisitListImpl.remove() returns the VetVisit that has been removed from
     *       the list; otherwise, returns null
     *       if VetVisit returned by VVLI.remove() is not null, writes that VetVisits info
     *       to cancellations file 'cancel.txt' along with tempHousePet's chipID, and
     *       changes value of boolean hasBeenRemoved to true. visitCount is decremented
     *       in VetVisitListImpl.remove().
     *       returns boolean hasBeenRemoved.      
     */
    public boolean removeVetVisit(int chipID, VetVisit visitX)
    {
        HousePet tempHousePet = new HousePet();
        boolean hasBeenRemoved = false;
        
        tempHousePet.setChipId(chipID);
        
        /* assign a HousePet that is in the current hpList to tempHousePet if their
         * chipIDs match */
        for(int i = 0; i < hpCount; i++)
        {
            if(tempHousePet.getChipId() == hpList[i].getChipId())
            {
                tempHousePet = hpList[i];
                break;
            }
            
        }//end FOR
        
        /* try to remove visitX from tempHousePet's visitList; if successfully removed,
         * remove() will return a VetVisit; otherwise, it will return null */
        VetVisit removedVisit = tempHousePet.getVisitList().remove(visitX);
        
        /* if a visit is removed from the visitList, append the visit info to 'cancel.txt'
         * cancellations file along with the HousePet's chipID */
        if(removedVisit != null)
        {
            hasBeenRemoved = true;
            VetVisit.writeToCancellationsFile(chipID, removedVisit);
        }
        
        return hasBeenRemoved;
        
    }//end removeVetVisit()
    
    /* *
     * Pre:  called by menuRemoveAll() on an instance of hpArray
     * Post: sets every HousePet in hpArray to null; resets hpCount to 0
     * 
     *       sets hasBeenSaved to false.
     */
    public void removeAll()
    {        
        for(int i = 0; i < hpCount; i++)
        {
            hpList[i] = null;    
            
        }//end FOR
        
        hpCount = 0;
        hasBeenSaved = false;
        
        return;
        
    }//end removeAll()
           
    /* *
     * Pre:  called by menuFind().
     *       expects to receive instantiated HousePetList and instantiated HousePet.
     *       prompts user to enter chipID of HousePet they want to
     *       find; calls contains() on housepet to see whether it exists in hpArray
     * Post: reports back to user by displaying msg revealing whether HousePet could be
     *       found in the list
     */
    public static void find(HousePetList hpArray)
    {
        HousePet housepet = new HousePet();
        int aChipID = 0;
        @SuppressWarnings("resource")
        Scanner console = new Scanner(System.in);
        String consoleOutMsg = "";
        boolean containsHousePet = true;
        
        System.out.println("Please enter the chipID of the HousePet you're looking for.");
        
        try
        {
            aChipID = console.nextInt();
            housepet.setChipId(aChipID);
        }
        catch(InputMismatchException e)
        {
            System.out.println("Error: a non-int was entered " + e);
        }
        catch(NoSuchElementException e)
        {
            System.out.println("Error: chipID not found " + e);
        }
        catch(IllegalStateException e)
        {
            System.out.println("Error: Scanner is closed " + e);
        }
        
        /* if an invalid int were to be sent to the setter, chipId would be set to 0 */
        if(aChipID != 0)
        {
            containsHousePet = hpArray.contains(housepet);
            
            if(containsHousePet == false)
            {
                consoleOutMsg = "not ";
                
            }//end IF(containsHousePet == false)
            
        }//end IF(aChipID != 0)
        
        System.out.println("A HousePet with chipID " + aChipID + " was " + consoleOutMsg + 
            "found in the current list");
               
        return;
                
    }//end find()    
    
    /* *
     * Pre:  called by add() and find() on an instance of HousePetList.
     *       expects to receive instantiated HousePet.
     * Post: calls binarySearch() to see whether a HousePet is already in hpArray that
     *       has the same chipID as housepet
     *       binarySearch() will return a -1 if chipID was not found in hpArray;
     *       any number besides -1 returned by binarySearch() is the index in hpArray
     *       where chipID was found to have occurred
     *       if binarySearch() returns -1, contains() will return false; otherwise,
     *       contains() will return true
     */
    public boolean contains(HousePet housepet)
    {
        int index = this.binarySearch(housepet);
        boolean containsHousePet = true;
        
        /* if binarySearch() returns -1, then chipID was not found in hpArray */
        if(index == -1)
        {
            containsHousePet = false;
            
        }//end IF
        
        return containsHousePet;
        
    }//end contains()
    
    /* *
     * Pre:  called by contains() (which is called by add() and find());
     *       also called by remove()
     *       expects to receive instantiated HousePet.
     * Post: returns -1 if chipID not found in hpArray;
     *       otherwise, returns the index at which the housepet was found
     */
    private int binarySearch(HousePet housepet)
    {
        int lowIndex = 0;
        int highIndex = hpCount - 1;
        
        while(lowIndex <= highIndex)
        {
            int midIndex = (lowIndex + highIndex) / 2;
            int compareResult = hpList[midIndex].compareTo(housepet);
                   
            /* chipID of HousePet at midIndex EQUALS the target chipID */
            if(compareResult == 0)
            {
                return midIndex;
                
            }//end IF
            
            /* chipID of HousePet at midIndex is HIGHER than the target chipID */
            else if(compareResult > 0)
            {
                highIndex = midIndex - 1;
                
            }//end ELSE IF
            
            /* chipID of HousePet at midIndex is LOWER than the target chipID */
            else  //if(compareResult < 0)
            {
                lowIndex = midIndex + 1;
                
            }//end ELSE
                        
        }//end WHILE
        
        /* if target chipID not found once entire array has been searched, return -1 */
        return -1;
        
    }//end binarySearch()
        
    /* *
     * Pre:  called on an instance of hpArray
     * Post: returns size of array (hpCount = # HousePets in hpArray)
     */
    public int getSize()
    {
        int arraySize = hpCount;        
        
        return arraySize;
        
    }//end getSize()
    
    /* *
     * Pre:  called by menuGetByName().
     *       expects to receive String housepetName.
     *       creates a new instance of a HousePet, then searches for any HousePets with
     *       name same as housepetName received (differences in capitalization are
     *       ignored)
     *       if found, toString() is called on that HousePet, and appended to String
     *       hpString along with a newline character
     * Post: returns hpString to calling method; hpString may either be an empty String
     *       (no matches found), or may contain a concatenated list of one or more
     *       HousePets, separated by a newline character (one HousePet per line).
     */
    public String getByName(String housepetName)
    {
        HousePet listPet = new HousePet();
        String name = "";
        String hpString = "";
        
        /* traverse list of HousePets, getting the name of each one and comparing it to
         * housepetName received in method call */
        for(int i = 0; i < hpCount; i++)
        {
            listPet = hpList[i];      
            name = listPet.getName();
            
            /* if a HousePet is found that has a name equal to housepetName received from
             * calling method, then compareToIgnoreCase() will return 0 */
            if(housepetName.compareToIgnoreCase(name) == 0)
            {
                /* append the HousePet to hpString, followed by a newline character */
                hpString += listPet.toString() + "\n";           
            }
            
        }//end FOR
        
        return hpString;
        
    }//end getByName()
    
    /* *
     * Pre:  called by menuModifyAge().
     *       expects to receive two numbers: one int, and one double. the int is a user-
     *       provided chipID to be searched for; the double is a user-provided age that
     *       will be used to replace the current age for a HousePet having the chipID
     *       being searched for, if such a HousePet is actually located in the list.
     *       creates a new HousePet instance, calls setChipId() to set its chipID to the
     *       int the user entered, and calls binarySearch() to find the index at which
     *       that HousePet is stored within hpArray.
     * Post: if HousePet is not found with the chipID the user entered, AND/OR if the
     *       age the user provided is a negative double, modifyAge() returns false;
     *       otherwise, if HousePet is found in the list, that HousePet's age is set to
     *       the new age, boolean modifiedAge is changed to true, and hasBeenSaved is set
     *       to false.
     *       returns boolean modified age to caller method.
     * 
     *       if tempHousepet's age has been modified, sets hasBeenSaved to false.
     */
    public boolean modifyAge(int hpChipID, double newAge)
    {
        boolean modifiedAge = false;
        
        /* create a temporary HousePet instance so we can call binarySearch on it */
        HousePet tempHousepet = new HousePet();
        tempHousepet.setChipId(hpChipID);
        
        /* call binarySearch() to see if a HousePet with chipID = hpChipID can be found
         * in list */
        int hpIndex = this.binarySearch(tempHousepet);
        
        /* return false if either or both of the following are true:
         *   no HousePet with chipID = hpChipID can be found in the list
         *   newAge provided by user is invalid number (negative number);
         * otherwise, do the following.
         *   set age of HousePet at hpIndex in hpArray to newAge
         *   change boolean modifiedAge to true */
        if(hpIndex == -1 || newAge < 0)
        {
            return false;
        }
        
        else
        {
            hpList[hpIndex].setAge(newAge);
            modifiedAge = true;
            hasBeenSaved = false;
        }
        
        return modifiedAge;
        
    }//end modifyAge()
    
    /* *
     * Pre:  called by menuWriteToFile().
     *       expects to receive a String which is a user-provided filename.
     *       tries to create a new PrintWriter with a new File "filename."
     *       if successful, iterates over hpArray, and calls HousePet.writeToFile() on
     *       each HousePet. 
     * Post: returns false if invalid filename given or if unable to open file for
     *       writing (ofile1 will remain null, so hasBeenWritten will remain false);
     *       returns true if all HousePet data was successfully written to the file.
     *       
     *       if  list has been written to destination file, sets hasBeenSaved to true.
     */
    public boolean writeToFile(String filename)
    {
        /* instantiate PrintWriter ofile1 as null, and call getSize() to obtain current
         * number of HousePets in hpArray (to be used for FOR loop iteration limit) */
        PrintWriter ofile1 = null;
        int listSize = getSize();
        boolean hasBeenWritten = false;
        
        /* try to create a new PrintWriter using user-provided filename */
        try
        {
            ofile1 = new PrintWriter(new File(filename));
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Error: Unable to open destination file " + filename +
                " " + e);
            hasBeenWritten = false;
        }
        
        /* if PrintWriter ofile1 was created successfully, iterate over FOR loop, calling
         * HousePet.writeToFile() on each HousePet in the list.
         * Note: could alternatively put all of the code that is in the "IF" below
         * into the try block under line with PrintWriter assignment as ofile1 */
        if(!ofile1.equals(null))
        {
            for(int i = 0; i < listSize; i++)
            {
                /* call HousePet.writeToFile() to write the attributes of current
                 * HousePet to the file then write separator "****\n" */
                hpList[i].writeToFile(ofile1);
                ofile1.println("****");
                
            }//end of FOR loop
            
            hasBeenWritten = true;
        }
        
        if(hasBeenWritten == true)
        {
            hasBeenSaved = true;
        }
        
        /* close PrintWriter ofile1: "flush the output buffer" */
        ofile1.close();
        
        return hasBeenWritten;
        
    }//end writeToFile()
    
    /* *
     * Pre:  called by menuExitSave().
     *       expects to receive instantiated HousePetList.
     *       checks the current status of hasBeenSaved with call to isSaved().
     *       if HousePetList has not been modified since beginning of program, isSaved()
     *       will return true, and exitSave() will call displayExitMsg() before the
     *       program is terminated.
     *       if isSaved() returns false, exitSave() asks user whether they would like to
     *       save to a file before exiting the program. if user enters "yes" or "y"
     *       (non case sensitive), calls menuWriteToFile()
     * Post: depending on user input, current list will or will not be written to a file
     *       ("saved"). displayExitMsg() is then called to inform user that they are
     *       exiting the program.
     * 
     *       if list has been successfully saved, hasBeenSaved will have been set to true
     *       in writeToFile(), which is called by menuWriteToFile(), which is called by
     *       exitSave()
     */
    public static void exitSave(HousePetList hpArray)
    {
        /* check whether HousePetList has been modified since start of program */
        boolean isSaved = hpArray.isSaved();
        //if hasBeenSaved == false:
        //  asks user if they want to save before exiting
        //    if yes:
        //      call menuWriteToFile(), which will prompt user for filename, etc
        //    if no:
        //      call displayExitMsg()
        
        /* if no changes have been made to HousePetList during the current run of the
         * program, call displayExitMsg() to let user know they are exiting program */
        if(isSaved == true)
        {
            displayExitMsg();
            return;
        }
        
        /* if HousePetList has been modified during the course of the program, ask user
         * whether they want to save or not, and prompt them for their response */
        //else(if(isSaved == false))
        @SuppressWarnings("resource")
        Scanner console = new Scanner(System.in);
        String userResponse = "";
            
        System.out.println("Current list has not been saved.");
        System.out.println("Would you like to save before exiting program?");
        
        do
        {
            System.out.println("Please type \"yes\" or \"no\"");
            userResponse = console.next();
            console.nextLine();
        }
        while(!userResponse.equalsIgnoreCase("yes") &&
              !userResponse.equalsIgnoreCase("y") &&
              !userResponse.equalsIgnoreCase("no") &&
              !userResponse.equalsIgnoreCase("n"));

        /* if user would like to save, call menuWriteToFile() which will do all the
         * remaining tasks such as prompting user to enter filename to which the current
         * list data will be written, handling the writing to file, etc. */
        if(userResponse.equalsIgnoreCase("yes") || userResponse.equalsIgnoreCase("y"))
        {
            Lab6Menu.menuWriteToFile(hpArray);
        }
           
        /* display msg to user informing them they are exiting the program */
        displayExitMsg();
        
        return;
        
    }//end writeToFile()
    
    /* *
     * Pre:  (none) expects to be called on a HousePetList instance
     * Post: returns boolean keeping track of changes during program.
     *       hasBeenSaved will be true if HousePetList has not been modified
     *       hasBeenSaved will be false if HousePetList has been modified
     */
    public boolean isSaved()
    {
        return hasBeenSaved;
        
    }//end isSaved()
    
}//end HousePetListImpl.java