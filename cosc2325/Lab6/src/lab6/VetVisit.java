package lab6;
/* *
 * [VetVisit.java]
 * Author: Kristin Hamilton
 * Desc: abstract superclass VetVisit extended by VetVisitStandard and VetVisitUrgent
 *       subclasses (contains code common to both subclasses, such as code peculiar to
 *       attributes visitID, date, doctor, location, which includes the both default and
 *       full constructors, as well as getters and setters, and the toString() method
 *       for these 4 attributes).
 *       other methods include the following. compareTo() and compareByVisitID().
 *       [Lab6] added plainDataToString(), writeToCancellationsFile(), and writeToFile().
 * Date created: 04-Mar-2014 for Lab6
 * Date last modified: 17-Mar-2014
 */
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.GregorianCalendar;
import java.util.Random;

public abstract class VetVisit implements Comparable <VetVisit>
{
    public static final int MAX_ID_LENGTH = 4;
    private String visitID;         /* unique number for each visit */
    private GregorianCalendar date; /* date of visit */
    private String location;        /* location of visit */
    private String doctor;          /* veterinarian */

    /* ------------------------ constructor methods ------------------------ */
    
    /* *
     * Pre:  (nothing)
     * Post: (nothing)
     *       instantiates either a VetVisitStandard or a VetVisitUrgent with default
     *       values for attributes:
     *       sets VetVisit attributes to default values. since both setDoctor and
     *       setLocation are called with an empty String as the argument, the doctor
     *       attribute will be set to default of "**no doctor**" and the location will
     *       be set to default of "**no location**" by each respective setter
     */
    public VetVisit()
    {
        this.setVisitID(generateNewVisitID());
        this.setDate("1/1/2000");
        this.setLocation("");
        this.setDoctor("");
                
    }//end default constructor
    
    /* *
     * Pre:  (String theDate, String theDoctor, String theLocation)
     *       expects to receive String theDate, String theDoctor, and String theLocation
     * Post: (nothing)
     *       instantiates either a VetVisitStandard or a VetVisitUrgent with argument
     *       values for attributes, if valid values are provided.
     *       invalid values for attributes include empty Strings or null values;
     *       if any invalid values are received, the corresponding attribute will be set
     *       to the default value for that attribute: default date: "1/1/2000",
     *       default doctor: "**no doctor**", default location: "**no location**"
     */
    public VetVisit(String theVisitID, String theDate, String theLocation,
                    String theDoctor)
    {
        this.setVisitID(theVisitID);
        this.setDate(theDate);
        this.setLocation(theLocation);
        this.setDoctor(theDoctor);
        
    }//end full constructor
    
    /* ------------------------- accessor methods -------------------------- */
    
    /* *
     * Pre:  (nothing)
     *       expects to be called on an instance of a VetVisitStandard or VetVisitUrgent
     * Post: (String)
     *       returns String value of visitID attribute for VetVisit
     */
    public String getVisitID()
    {
        return this.visitID;
        
    }//end getVisitID()   
    
    /* *
     * Pre:  (nothing)
     *       expects to be called on an instance of a VetVisitStandard or VetVisitUrgent
     * Post: (String)
     *       calls MyUtils.dateToString() to convert date attribute from type
     *       GregorianCalendar to its String representation;
     *       returns String value of date attribute for VetVisit
     */
    public String getDateString()
    {
        String dateString = MyUtils.dateToString(date);
        
        return dateString;
        
    }//end getDate()
       
    /* *
     * Pre:  (nothing)
     *       expects to be called on an instance of a VetVisitStandard or VetVisitUrgent
     * Post: (GregorianCalendar)
     *       returns GregorianCalendar value of date attribute for VetVisit
     */
    public GregorianCalendar getDateCalendar()
    {
        return this.date;
        
    }//end getDateCalendar()
    
    /* *
     * Pre:  (nothing)
     *       expects to be called on an instance of a VetVisitStandard or VetVisitUrgent
     * Post: (String)
     *       returns String value of location attribute for VetVisit
     */
    public String getLocation()
    {        
        return this.location;
        
    }//end getLocation()
    
    /* *
     * Pre:  (nothing)
     *       expects to be called on an instance of a VetVisitStandard or VetVisitUrgent
     * Post: (String)
     *       returns String value of doctor attribute for VetVisit
     */
    public String getDoctor()
    {
        return this.doctor;
        
    }//end getDoctor()
    
    /* -------------------------- mutator methods -------------------------- */
    
    /* *
     * Pre:  (String theVisitID)
     *       expects to be called on an instance of a VetVisitStandard or VetVisitUrgent;
     *       receives String theVisitID for a given VetVisit;
     *       the length of theVisitID should be less than MAX_ID_LENGTH
     * Post: (nothing)
     *       if theVisitID is invalid (length greater than MAX_ID_LENGTH), shortens
     *       theVisitID to its first 4 chars (substring);
     *       sets this.visitID to theVisitID; returns nothing.
     */
    public void setVisitID(String theVisitID)
    {
        if(theVisitID.equals("0"))
        {
            theVisitID = generateNewVisitID();
        }
        
        /* check length of theVisitID */
        if(theVisitID.length() > MAX_ID_LENGTH)
        {
            /* shorten theVisitID to valid length by taking a substring consisting of its
             * first four chars (index 0 of theVisitID to index = MAX_LENGTH_ID (= 4) of
             * theVisitID) */
            theVisitID = theVisitID.substring(0, MAX_ID_LENGTH);
            
        }//end IF(theVisitID.length() > MAX_ID_LENGTH)
        
        /* set this.visitID */
        this.visitID = theVisitID;        
        
    }//end setVisitID()       
    
    /* *
     * Pre:  (String)
     *       expects to be called on an instance of a VetVisitStandard or VetVisitUrgent;
     *       receives String theDate for a given VetVisit
     * Post: (nothing)
     *       converts String theDate to GregorianCalendar version by call to
     *       MyUtils.stringToDate, then sets date attribute for VetVisit to the
     *       GregorianCalendar date
     */
    public void setDate(String theDate)
    {
        this.date = MyUtils.stringToDate(theDate);
                
    }//end setDate()
    
    /* *
     * Pre:  (String)
     *       expects to be called on an instance of a VetVisitStandard or VetVisitUrgent;
     *       receives String theLocation for a given VetVisit
     * Post: (nothing)
     *       if theLocation is not null and theLocation is not an empty String and the
     *       length of theLocation is greater than 0, removes any whitespace from
     *       theLocation, then sets this.location to theLocation;
     *       otherwise, sets this.location to default value of "**no location**"
     */
    public void setLocation(String theLocation)
    {
        if(!theLocation.equals(null) && !theLocation.equals("") &&
            theLocation.length() > 0)
        {
            /* set this.location to theLocation, and remove any leading or lagging
             * whitespace */
            this.location = theLocation.trim();
        }
                
        else
        {
            this.location = "**no location**";
        }
        
    }//end setLocation()
    
    /* *
     * Pre:  (String)
     *       expects to be called on an instance of a VetVisitStandard or VetVisitUrgent;
     *       receives String theDoctor for a given VetVisit
     * Post: (nothing)
     *       if theDoctor is not null and theDoctor is not an empty String and the length
     *       of theDoctor is greater than 0, removes whitespace from theDoctor, and
     *       sets this.doctor to theDoctor;
     *       otherwise, sets this.doctor to default value of "**no doctor**"
     */
    public void setDoctor(String theDoctor)
    {
        if(!theDoctor.equals(null) && !theDoctor.equals("") &&
            theDoctor.length() > 0)
        {
            /* set this.doctor to theDoctor, and remove any leading or lagging
             * whitespace */
            this.doctor = theDoctor.trim();
        }
        
        else
        {
            this.doctor = "**no doctor**";
        }
                
    }//end setDoctor()
        
    /* --------------------------- other methods --------------------------- */
    
    /* *
     * Pre:  (nothing)
     *       expects to be called on an instance of a VetVisitStandard or VetVisitUrgent
     * Post: (String)
     *       returns String containing the visitID, date, doctor, and location for a
     *       given VetVisitStandard or VetVisitUrgent object
     */
    public String toString()
    {
        String toStringOut = "";
              
        /* assemble String to be returned, containing all attributes common to both
         * VetVisitStandard and VetVisitUrgent objects */
        toStringOut = "visitID: "   + this.getVisitID()    + "  " +
                      "date: "      + this.getDateString() + "  " +
                      "location: "  + this.getLocation()   + "  " +
                      "doctor: "    + this.getDoctor();
                
        return toStringOut;
        
    }//end toString()
    
    /* *
     * Pre:  (nothing)
     *       expects to be called on an instance of a VetVisit
     *       caller methods are VetVisitStandard.plainDataToString() and
     *       VetVisitUrgent.plainDataToString(); both these methods use a call to 
     *       super.plainDataToString() to achieve their tasks
     * Post: (String plainDataOut)
     *       assembles and returns String containing all of a HousePet's VetVisit
     *       attributes in a format that can be written directly to an output file so that
     *       the data will be readable by our program's readFromScanner() methods
     */
    public String plainDataToString()
    {
        String plainDataOut = "";
                
        plainDataOut = this.getVisitID()  + "  " + this.getDateString() + "  " +
                       this.getLocation() + "  " + this.getDoctor();
        
        return plainDataOut;        
        
    }//end plainDataToString()
    
    /* *
     * Pre:  (nothing)
     *       expects to be called on an instance of a VetVisitStandard or VetVisitUrgent
     * Post: (String) newVisitID made of String of random chars of length MAX_ID_LENGTH
     */
    public String generateNewVisitID()
    {
        /* instantiate variables */
        String chars = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String newVisitID = "";
        
        Random randm = new Random();
        
        /* build String of length MAX_ID_LENGTH by way of selecting a series of random
         * chars (from String chars above), appending each newly-selected char to
         * newVisitID
         */
        for(int i = 0; i < MAX_ID_LENGTH; i++)
        {
            int randomInt = randm.nextInt(chars.length());
            char randomChar = chars.charAt(randomInt);
            newVisitID += randomChar;
            
        }//end FOR
        
        return newVisitID;        
        
    }//end generateNewVisitID()
    
    /* ----------------------- Comparable<T> methods ----------------------- */
    
    /* *
     * Pre:  (none - interface)
     * Post: (none - interface)
     */
    public interface Comparable<T>
    {
        public int compareTo(T VetVisit);
        
    }   
    
    /* *
     * Pre:  (VetVisit) expects to receive a VetVisit object
     * Post: (int) returns one of the following values.
     *       -1  if this VetVisit's date is less recent than visitX's date,
     *           OR
     *           if the two dates are the same, AND this VetVisit's visitID
     *           comes alphabetically after visitX's visitID.
     *        0  only if it is both true that:
     *           this VetVisit's date is the same as visitX's date,
     *           AND
     *           this this VetVisit's visitID is the same as visitX's visitID.
     *        1  if this VetVisit's date is more recent than visitX's date,
     *           OR
     *           if the two dates are the same, but this VetVisit's visitID comes
     *           alphabetically before visitX's visitID.
     */
    public int compareTo(VetVisit visitX)
    {
        int returnValue = 0;
        GregorianCalendar thisDate = this.getDateCalendar();
        GregorianCalendar visitXDate = visitX.getDateCalendar();
        
        if(thisDate.compareTo(visitXDate) < 0)
        {
            returnValue = -1;
        }
        
        else if(thisDate.compareTo(visitXDate) > 0)
        {
            returnValue = 1;
        }
                    
        /* if, after comparing the two dates, it is determined that the two dates are
         * equal, defer to compareByVisitID to break the tie 
         * NOTE: need to multiply the return value of compareByVisitID() by -1 because
         * when compareTo() is called, we are planning to order the visits by date, 
         * most recent to least recent, which is the opposite order that we want to have
         * the visitIDs ordered (alphanumerically a to z 1 to 3 etc) 
         * summary: we want to order first by date in decreasing order, and if dates are
         * equal we then want to order by visitID, in INCREASING order. multiplying by -1
         * allows our objective to jive with what the program actually does */
        else  //if(thisDate.compareTo(visitXDate) == 0)
        {
            returnValue = (-1)*(this.compareByVisitID(visitX));            
            
        }//end IF(returnValue == 0)
        
        return returnValue;
        
    }//end compareTo()  
    
    /* *
     * Pre:  (VetVisit) expects to receive a VetVisit object
     * Post: (int) returns one of the following values.
     *       -1  if this VetVisit's visitID comes alphabetically after visitX's visitID
     *        0  if this VetVisit's visitID is the same as visitX's visitID
     *        1  if this VetVisit's visitID comes alphabetically before visitX's visitID
     */
    public int compareByVisitID(VetVisit visitX)
    {
        String thisVisitID = this.getVisitID();
        String visitXVisitID = visitX.getVisitID();
        
        int returnValue = thisVisitID.compareToIgnoreCase(visitXVisitID);

        return returnValue;
        
    }//end compareByVisitID()   
    
    /* note on the methods below (hashCode() and equals()):
     * i dont think we need (use) these two methods in this lab, but the reason to have
     * equals() is obviously to override the default equals() method; in the oracle
     * java docs, it is suggested that, if equals() is overridden, that hashCode() also
     * be overridden, especially for if the equals() method that you wrote winds up
     * ordering objects in a way other than their natural ordering would (i think!?)
     */
    
    /* *
     * Pre:  (nothing)
     * Post: (int)
     */
    //public int hashCode()
    //{
        //*****check on this method*****

    //}//end hashCode()   
    
    /* *
     * Pre:  (VetVisit)
     * Post: (boolean)
     */
    //public boolean equals(VetVisit visit)
    //{
      //  boolean visitsAreEqual = false;
        
    //*****check on this method*****
        
        //return visitsAreEqual;
        
    //}//end equals()
    
    /* ---------------------------- i/o methods ---------------------------- */
    
    /* --- input data from file: MyUtils.vetVisitReadFromScanner() --- */
    
    /* *
     * Pre:  (int, VetVisit)
     *       expects to receive int chipID and VetVisit removedVisit
     *       caller method is HousePetListImpl.removeVetVisit(), which performs the
     *       following actions prior to calling this method.
     *         first, determines whether the chipID provided by the user is actually the
     *         chipID of a HousePet that is currently in the HousePetList.
     *         then, proceeds to remove a certain VetVisit from that HousePet's visitList
     *         (this removed visit is the "removedVisit" parameter in the method heading).
     *       finally, this method is called in order to add the removed visit to the
     *       cancellations file, along with that HousePet's chipID.
     * Post: (nothing)
     *       uses FileWriter to append chipID and cancelled visit information to the end
     *       of 'cancel.txt' output file
     *       (NOTE: using "false" arg in new FileWriter() would result in new data being
     *       appended to the BEGINNING of the 'cancel.txt' file)
     */
    public static void writeToCancellationsFile(int chipID, VetVisit removedVisit)
    {        
        String filename = "cancel.txt";
        File file1 = new File(filename);
        FileWriter ofile1 = null;
        
        try
        {
            ofile1 = new FileWriter(file1, true); /* true = "append to END of file" */
            ofile1.write(chipID + "\n");
            ofile1.write(removedVisit.plainDataToString() + "\n");
            ofile1.write("****" + "\n");
            ofile1.close();
        }
        catch(IOException e)
        {
            System.out.println("Error: FileWriter has thrown an IOException " + e);
        }
        
        return;
        
    }//end writeToCancellationsFile()
    
    /* *
     * Pre:  (PrintWriter ofile1)
     * Post: (nothing)
     * okay seriously this method is not at all being used by the caller method, but its
     * giving me an error without it. basically what is actually happening is that in
     * VVLI.writeToFile(), theres a line "node.data.writeToFile(ofile1);" which in
     * reality is NOT calling the method below, but is calling either VVstd.writeToFile()
     * or VVurgent.writeToFile(), depending on what type of visit the current node in
     * visitList is. so i basically just folded and put this method back in but just be
     * aware its only for show and is a peace offering to the IDE. i could be missing
     * something here, but that's what it looks like is going on from where im sitting
     */
    public void writeToFile(PrintWriter ofile1)
    {
       //see plainDataToString().
    }//end writeToFile()
    
}//end of VetVisit.java
