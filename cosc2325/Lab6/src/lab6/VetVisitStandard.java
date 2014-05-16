package lab6;
/* *
 * [VetVisitStandard.java]
 * Author: Kristin Hamilton
 * Desc: subclass of (extends) abstract superclass VetVisit; contains instance methods
 *       for VetVisitStandard objects
 *       [Lab6] added plainDataToString(), writeToFile().
 * Date created: 04-Mar-2014 for Lab6
 * Date last modified: 17-Mar-2014
 */
import java.io.PrintWriter;

public class VetVisitStandard extends VetVisit
{
    private String vaccines;     //may be empty
    private int license;         //may be either zero or a positive int
    private String otherCare;    //may be empty
    
    /* ------------------------ constructor methods ------------------------ */
    
    /* *
     * Pre:  (nothing)
     * Post: (nothing)
     *       instantiates a VetVisitStandard object with attributes vaccines, license,
     *       and otherCare, having default values of "**no vaccines**", 0, and
     *       "**no treatment**", respectively
     */
    public VetVisitStandard()
    {
        super();
        this.setVaccines("**no vaccines**");
        this.setLicense(0);
        this.setOtherCare("**no other care**");
        
    }//end default constructor
    
    
    /* *
     * Pre:  (String, int, String)
     *       expects to receive String theVaccines, int theLicense, and String
     *       theOtherCare
     * Post: (nothing)
     *       instantiates new VetVisitStandard object with attributes date, doctor,
     *       location, vaccines, license, and treatment; also generates visitID
     */
    public VetVisitStandard(String theVisitID, String theDate, String theDoctor,
                            String theLocation,
                            String theVaccines, int theLicense, String theOtherCare)
    {
        super(theVisitID, theDate, theDoctor, theLocation);
        this.setVaccines(theVaccines);
        this.setLicense(theLicense);
        this.setOtherCare(theOtherCare);
        
    }//end full constructor
    
    /* ------------------------- accessor methods -------------------------- */
    
    /* *
     * Pre:  (nothing)
     * Post: (String)
     *       returns value of vaccines attribute for VetVisitStandard object
     */
    public String getVaccines()
    {
        return this.vaccines;
        
    }//end getVaccines()
    
    
    /* *
     * Pre:  (nothing)
     * Post: (int)
     *       returns value of license attribute for VetVisitStandard object
     */
    public int getLicense()
    {
        return this.license;
        
    }//end getLicense()
    
    
    /* *
     * Pre:  (nothing)
     * Post: (String)
     *       returns value of otherCare attribute for VetVisitStandard object
     */
    public String getOtherCare()
    {
        return this.otherCare;
        
    }//end getOtherCare()
    
    /* -------------------------- mutator methods -------------------------- */
    
    /* *
     * Pre:  (String theVaccines)
     *       may be empty; receives String theVaccines
     * Post: (nothing)
     *       sets vaccines attribute for VetVisitStandard object to theVaccines
     */
    public void setVaccines(String theVaccines)
    {
        /* may be empty */
        this.vaccines = theVaccines;        
        
    }//end setVaccines()
    
    
    /* *
     * Pre:  (int theLicense)
     *       may be either zero or a positive int (>= 0);
     *       receives int theLicense
     *       if license is a valid value, set this.license to theLicense;
     *       otherwise, *****???*****
     * Post: (nothing)
     *       sets license attribute for VetVisitStandard object to theLicense
     */
    public void setLicense(int theLicense)
    {
        /* check that license is greater than or equal to 0 */
        if(license >= 0)
        {
            this.license = theLicense;
            
        }//end of IF(license >= 0)
        
    }//end setLicense()
     
    
    /* *
     * Pre:  (String)
     *       may be empty; receives String theOtherCare
     * Post: (nothing)
     *       sets otherCare attribute for VetVisitStandard object to theOtherCare
     */
    public void setOtherCare(String theOtherCare)
    {
        /* may be empty */
        this.otherCare = theOtherCare;
        
    }//end setOtherCare()
    
    /* --------------------------- other methods --------------------------- */
    
    /* *
     * Pre:  (nothing)
     *       expects to be called on an instance of a VetVisitStandard object
     * Post: (String)
     *       appends (from superclass VetVisit) String containing the visitID, date,
     *       doctor, location for a given VetVisitStandard object to String containing
     *       vaccines, license, and other care for a given VetVisitStandard object
     */
    public String toString()
    {
        String toStringOut = "";
        String vetVisitAttributes = super.toString();
        String vetVisitStdAttributes = "";
        
        vetVisitStdAttributes = "vaccines: "   + this.getVaccines() + "  " +
                                "license: "    + this.getLicense() + "  " +
                                "other care: " + this.getOtherCare();
        
        toStringOut = vetVisitAttributes + "  " + vetVisitStdAttributes;
        
        return toStringOut;
        
    }//end toString()
    
    /* *
     * Pre:  (nothing)
     *       expects to be called on an instance of a VetVisitStandard
     *       caller methods are VetVisit.writeToCancellationsFile() and this.writeToFile()
     * Post: (String plainDataOut)
     *       assembles and returns String containing all of a HousePet's VetVisitStandard
     *       attributes in a format that can be written directly to an output file so that
     *       the data will be readable by our program's readFromScanner() methods
     */
    public String plainDataToString()
    {
        String plainDataOut = "";
        String vetVisitData = super.plainDataToString();
        String vetVisitStdData = "";
        
        vetVisitStdData = this.getVaccines() + "\n" +
                          this.getLicense()  + "  " + this.getOtherCare();
        
        plainDataOut = vetVisitData + "\n" + vetVisitStdData;
        
        return plainDataOut;
        
    }//end plainDataToString()
    
    /* *
     * Pre:  (PrintWriter)
     *       expects to be called on an instance of a VetVisitStandard object; caller
     *       method is VetVisitListImpl.writeToFile()
     *       expects to receive a valid (not null) PrintWriter
     *       performs null check for PrintWriter parameter
     * Post: (nothing)
     *       writes VetVisitStandard data to output file
     */
    public void writeToFile(PrintWriter ofile1)
    {
        if(ofile1 != null)
        {
            ofile1.println(this.plainDataToString());
        }

        return;
        
    }//end writeToFile()
    
    
}//end of VetVisitStandard.java
