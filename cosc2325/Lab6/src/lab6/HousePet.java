package lab6;
/* *
 * [HousePet.java]
 * Author: Kristin Hamilton
 * Desc: copy of HousePet.java from lab3. implements Comparator<>;
 *       contains methods for HousePet objects, which include the following.
 *       default and full constructors, setters and getters for all four HousePet
 *       attributes (chipId, name, petType, and age), toString(), readFromScanner(),
 *       compareTo(), and compareByName().
 *       [Lab4] writeToFile() and new catch blocks added
 *       [Lab5] did not modify
 *       [Lab6] modified readFromScanner() to read visitList data from file
 * Date created:  04-Mar-2014 for Lab6
 * Date last modified: 17-Mar-2014  
 */
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/* -------------------------- constructor methods -------------------------- */

public class HousePet implements Comparable<HousePet>
{
    private int chipId;
    private String name;
    private String petType;
    private double age;
    private VetVisitList visitList;  /* immutable */
        
    /* *
     * Pre:  (none-default constructor)
     * Post: instantiates new HousePet object
     */
    public HousePet()
    {
        this.chipId = 0;
        this.name = "**no name**";
        this.petType = "**no type**";
        this.age = 0.0;
        this.visitList = new VetVisitListImpl();
        
    }//end default constructor  
    
    /* *
     * Pre:  full constructor-expects to receive the following data.
     *       int theChipId, String theName, String thePetType, and double theAge
     * Post: creates new HousePet object using values specified in method call.
     *       if any of the attribute values were invalid, HousePet object will be
     *       created with default values for those attributes. defaults are as follows.
     *       chipID = 0, name = "**no name**", petType = "**no type**", age = 0.0
     */
    public HousePet(int theChipId, String theName, String thePetType, double theAge)
    {
        if(theChipId < 0)
        {        
            chipId = 0;
        }
        
        else
        {
            chipId = theChipId;
        }
        
        if(theName.equals("") || theName.equals(null))
        {
            name = "**no name**";
        }
        
        else
        {
            name = theName;
        }
        
        if(thePetType.equals("") || thePetType.equals(null))
        {
            petType = "**no type**";
        }

        else
        {
            petType = thePetType;
        }
        
        if(theAge < 0)
        {
            age = 0.0;
        }
        
        else
        {
            age = theAge;
        }      
        
    }//end full constructor   
    
    /* -------------------------- accessor methods -------------------------- */
    
    /* *
     * Pre:  (none-accessor) called on a HousePet instance
     * Post: returns chipId attribute for instance of certain HousePet object
     */
    public int getChipId()
    {
        return chipId;        
    }   
    
    /* *
     * Pre:  (none-accessor) called on a HousePet instance
     * Post: returns name attribute for instance of certain HousePet object
     */
    public String getName()
    {
        return name;
    }
    
    /* *
     * Pre:  (none-accessor) called on a HousePet instance
     * Post: returns petType attribute for instance of certain HousePet object
     */
    public String getPetType()
    {
        return petType;
    }   
    
    /* *
     * Pre:  (none-accessor) called on a HousePet instance
     * Post: returns age attribute for instance of certain HousePet object
     */
    public double getAge()
    {
        return age;
    }    
    
    /* *
     * Pre:  (none-accessor) called on a HousePet instance
     * Post: returns visitList attribute for instance of certain HousePet object
     */
    public VetVisitList getVisitList()
    {
        return visitList;
    }  
    
    
    /* -------------------------- mutator methods -------------------------- */
    
    /* *
     * Pre:  receives int theChipId
     * Post: sets chipId attribute for HousePet object.
     *       if chipID invalid (negative number) sets chipId to default of 0;
     *       otherwise, sets chipId to theChipId
     */
    public void setChipId(int theChipId)
    {
        if(theChipId < 0)
        {        
            chipId = 0;
        }
        
        else
        {
            chipId = theChipId;
        }
        
        return;
        
    }//end setChipId() 
    
    /* *
     * Pre:  receives String theName
     * Post: sets name attribute for HousePet object.
     *       if theName is an empty String and/or is null, sets name to default of
     *       "**no name**";
     *       otherwise, sets name to theName
     */
    public void setName(String theName)
    {
        if(theName.equals("") || theName.equals(null))
        {
            name = "**no name**";
        }
        
        else
        {
            name = theName;
        }
               
        return;
        
    }//end setName()   
    
    /* *
     * Pre:  receives String thePetType
     * Post: sets petType attribute for HousePet object.
     *       if thePetType is an empty String and/or is null, sets petType to default of
     *       "**no type**";
     *       otherwise, sets petType to thePetType
     */
    public void setPetType(String thePetType)
    {
        if(thePetType.equals("") || thePetType.equals(null))
        {
            petType = "**no type**";
        }

        else
        {
            petType = thePetType;
        }
        
        return;
        
    }//end setPetType()
    
    /* *
     * Pre:  receives double theAge
     * Post: sets age attribute for HousePet object.
     *       if age invalid (negative number) sets age to default of 0;
     *       otherwise, sets age to theAge
     */
    public void setAge(double theAge)
    {
        if(theAge < 0)
        {
            age = 0.0;
        }
        
        else
        {
            age = theAge;
        }
        
        return;
        
    }//end setAge()
    
    /* --------------------------- other methods --------------------------- */
    
    /* *
     * Pre:  object method to be used on HousePet object;  overrides default toString()
     * Post: returns String of HousePet attributes, in a single horizontal line of text
     */
    public String toString()
    {
        String toStringOut = 
            "chipID: "    + this.getChipId()  + "  " + "name: " + this.getName() + "  " +
            "petType: "   + this.getPetType() + "  " + "age: "  + this.getAge()  + "  " +
            "visitList: " + this.getVisitList();
        
        return toStringOut;
        
    }//end toString()
        
    /* *
     * Pre:  called by HousePetListImpl.readFromScanner().
     *       expects to receive Scanner ifile1, where ifile1 is a text file
     *       containing HousePet (attribute) data of the form:
     *           1\n2\n3\n4\n , where:
     *           1 = int chipId, 2 = String name, 3 = String petType, and 4 = double age,
     *           and each field is followed by a newline/carriage return.
     *       data in input file should be scrubbed, or program will not proceed as
     *       expected.
     * Post: reads in attribute data for HousePet object, returns boolean true if all
     *       four attributes have been read in
     */
    public boolean readFromScanner(Scanner ifile1)
    {
        boolean allAttributesFound = false;
        int attributesRead = 0;
        
        int aChipId = 0;
        String aName = "";
        String aPetType = "";
        double anAge = 0;

        /* only way ive been able to avoid NoSuchElementException is the first condition
         * below, and the only way ive been able to avoid NullPointerException is to
         * add the second condition below. the IF block below is THE ONLY THING that Ive
         * tried that will work as expected and read in the data on the text file
         * correctly. i may just be missing something really obvious though
         */
        if(!ifile1.hasNext() || ifile1.hasNext("\\*\\*\\*\\*"))
        {
            return allAttributesFound;  //returns false           
        }
        
        try
        {
            aChipId = ifile1.nextInt();
            attributesRead++;
            ifile1.nextLine();  //move cursor to next line
        }
        catch(InputMismatchException e)
        {
            System.out.println("Error: a non-int was entered " + e);
            ifile1.nextLine();  //move cursor to next line
        }
        catch(NoSuchElementException e)
        {
            System.out.println("Error: chipId not found " + e);
        }
        catch(IllegalStateException e)
        {
            System.out.println("Error: Scanner is closed " + e);
        }
        
        try
        {
            aName = ifile1.nextLine().trim();  //in case name contains whitespace
            attributesRead++;
        }
        catch(NoSuchElementException e)
        {
            System.out.println("Error: name not found " + e);
        }
        catch(IllegalStateException e)
        {
            System.out.println("Error: Scanner is closed " + e);
        }
        
        try
        {
            aPetType = ifile1.nextLine().trim();
            attributesRead++;
        }
        catch(NoSuchElementException e)
        {
            System.out.println("Error: petType not found " + e);
        }
        catch(IllegalStateException e)
        {
            System.out.println("Error: Scanner is closed " + e);
        }
        
        try
        {
            anAge = ifile1.nextDouble();
            attributesRead++;
            //ifile1.nextLine();  //move cursor to next line
        }
        catch(InputMismatchException e)
        {
            System.out.println("Error: a non-double was entered " + e);
            ifile1.nextLine();  //move cursor to next line
        }
        catch(NoSuchElementException e)
        {
            System.out.println("Error: age not found " + e);
        }
        catch(IllegalStateException e)
        {
            System.out.println("Error: Scanner is closed " + e);
        }

        this.setChipId(aChipId);
        this.setName(aName);
        this.setPetType(aPetType);
        this.setAge(anAge);
        
        /* if all four attributes read in, returns true */
        if(attributesRead == 4)
        {
            allAttributesFound = true;            
        }
        
        //if(ifile1.hasNext("\\*\\*\\*\\*"))
        //{
          //  this.visitList = null;
        //}
        //else
        //{
          MyUtils.vetVisitListReadFromScanner(ifile1, this.visitList);
        //}
            
        return allAttributesFound;

    }//end readFromScanner()
    
    /* *
     * Pre:  expects to receive a valid PrintWriter instance ofile1; expects to be called
     *       on an instantiated HousePet object
     * Post: if ofile1 is null, returns to caller method; otherwise, does the following.
     *       writes first four attributes of HousePet to ofile1, each attribute followed
     *       by a newline character. then calls visitList.writeToFile(), which will result
     *       in all VetVisits, if any, for the current HousePet being written to output
     *       file; at end of visitList data, a newline is printed, followed by "****\n"
     *       
     *       *NOTE: expects ofile1 to be closed inside of caller method
     *       *NOTE: expects that private boolean hasBeenSaved (in HousePetListImpl class)
     *        will be set to false inside of caller method
     */
    public void writeToFile(PrintWriter ofile1)
    {
        if(ofile1 != null)
        {
            ofile1.println(this.getChipId());
            ofile1.println(this.getName());
            ofile1.println(this.getPetType());
            ofile1.println(this.getAge());
            this.visitList.writeToFile(ofile1);
        }
        
        /* NOTE: close ofile1 in caller method */        
        /* NOTE: set hasBeenSaved to false in caller method */
        
        return;
        
    }//end writeToFile()
    
    /* ----------------------- Comparable<T> methods ----------------------- */
    
    /* *
     * Pre:  (none - interface)
     * Post: (none - interface)
     */
    public interface Comparable<T>
    {
        public int compareTo(T HousePet);
    }      
 
    /* *
     * Pre:  expects to receive a HousePet object
     * Post: returns one of the following values.
     *       -1  if this HousePet's chipID is less than housepet's chipID
     *        0  if this HousePet's chipID equals housepet's chipID
     *        1  if this HousePet's chipID is greater than housepet's chipID
     */
    public int compareTo(HousePet housepet)
    {
        int returnValue = 0;
        
        if(this.chipId < housepet.getChipId())
        {
            returnValue = -1;
        }
        
        if(this.chipId > housepet.getChipId())
        {
            returnValue = 1;
        }
        
        return returnValue;
        
    }//end compareTo()    
    
    /* *
     * Pre:  expects to receive a HousePet object
     * Post: returns one of the following values.
     *       -1  if this HousePet's name comes alphabetically before housepet's name,
     *           OR
     *           if both names begin with the same letter, and this HousePet's chipID
     *           is less than housepet's chipID.
     *        0  only if it is both true that:
     *           this HousePet's Name is the same as housepet's name,
     *           AND
     *           this HousePet's chipID is the same as housepet's chipID.
     *        1  if this HousePet's name goes alphabetically after housepet's name,
     *           OR
     *           if the two names are the same, but this HousePet's chipID
     *           is greater than housepet's chipID.
     * NOTE: i wrote this before i realized there was a compareToIgnoreCase method
     */
    public int compareByName(HousePet housepet)
    {
        int returnValue = 0;
        
        String hp1Name = this.getName();
        String hp2Name = housepet.getName();

        /* if this HousePet's name is the same as housepet's name, defer to compareTo()
         * as tiebreaker 
         * Note: this also includes case where both HousePet's names = "**no name**" */
        if(hp1Name.equalsIgnoreCase(hp2Name))
        {
            returnValue = this.compareTo(housepet); 
        }
        
        /* first, find the shorter of the two HousePet's names, so that we know where to
         * end the FOR loop, avoiding a NullPointerException.
         * then, make a copy of both HousePet's names and convert them to uppercase, to
         * remove differences in capitalization (lowercase - uppercase = 32 for a given
         * letter).
         * compare this HousePet's capitalized name with that of housepet,
         * char by char, until arriving upon a difference in letters, at which point
         * it will be determined whether the given char in this HousePet's name has a
         * greater or lesser numerical value than the given char in housepet's name.
         * returnValue will be then assigned as either -1 or 1, and the FOR
         * loop will be exited using break statement.
         * Note: ['*' = 42, 'A' = 65, 'a' = 97]. "**NO NAME**" < name not beginning with
         * an asterisk, so will be sorted to lower indices in compareByName() */ 
        else
        {
            int hp1Len = hp1Name.length();
            int hp2Len = hp2Name.length();
            int lenDiff = hp1Len - hp2Len;
            int shortestNameLen = 0;
            String hp1Caps = hp1Name.toUpperCase();
            String hp2Caps = hp2Name.toUpperCase();
            
            if(lenDiff < 0)
            {
                shortestNameLen = hp1Len;
            }
            
            else  //if(lenDiff > 0)
            {
                shortestNameLen = hp2Len;
            }
            
            for(int i = 0; i < shortestNameLen; i++)
            {
                if(hp1Caps.charAt(i) < hp2Caps.charAt(i))
                {
                    returnValue = -1;
                    break;
                        
                }//end IF
                    
                if(hp1Caps.charAt(i) > hp2Caps.charAt(i))
                {
                    returnValue = 1;
                    break;
                        
                }//end IF
                    
            }//end FOR
                            
        }//end ELSE
                
        return returnValue;
        
    }//end compareByName()    
    
}//end of HousePet.java