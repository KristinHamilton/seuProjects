package lab6;
/* *
 * [VetVisitUrgent.java]
 * Author: Kristin Hamilton
 * Desc: subclass of (extends) abstract superclass VetVisit; contains instance methods
 *       for VetVisitUrgent objects
 *       [Lab6] added plainDataToString(), writeToFile().
 * Date created: 04-Mar-2014 for Lab6
 * Date last modified: 17-Mar-2014
 */
import java.io.PrintWriter;

public class VetVisitUrgent extends VetVisit
{
    private String diagnosis;   //may not be empty
    private String treatment;   //may not be empty
    
    /* ------------------------ constructor methods ------------------------ */
    
    /* *
     * Pre:  (nothing)
     * Post: (nothing)
     *       instantiates a VetVisitUrgent object with attributes diagnosis and treatment
     *       having default values of "**no diagnosis**" and "**no treatment**",
     *       respectively
     */
    public VetVisitUrgent()
    {
        super();
        this.setDiagnosis("**no diagnosis**");
        this.setTreatment("**no treatment**");        
        
    }//end default constructor
    
    
    /* *
     * Pre:  (String, String)
     *       expects to receive String theDiagnosis and String theTreatment;
     *       neither may be empty
     * Post: (nothing)
     *       instantiates new VetVisitUrgent object with attributes date, doctor,
     *       location, diagnosis, and treatment; also generates visitID
     */
    public VetVisitUrgent(String theVisitID, String theDate, String theDoctor,
                          String theLocation,
                          String theDiagnosis, String theTreatment)
    {
        super(theVisitID, theDate, theDoctor, theLocation);
        this.setDiagnosis(theDiagnosis);
        this.setTreatment(theTreatment);        
        
    }//end full constructor
    
    /* ------------------------- accessor methods -------------------------- */
    
    /* *
     * Pre:  (nothing)
     * Post: (String)
     *       returns value of diagnosis attribute for VetVisitUrgent object
     */
    public String getDiagnosis()
    {
        return this.diagnosis;
        
    }//end getDiagnosis()
    
    
    /* *
     * Pre:  (nothing)
     * Post: (String)
     *       returns value of diagnosis attribute for VetVisitUrgent object
     */
    public String getTreatment()
    {
        return this.treatment;
        
    }//end getTreatment()
    
    /* -------------------------- mutator methods -------------------------- */
    
    /* *
     * Pre:  (String)
     *       may not be empty; receives String theDiagnosis
     * Post: (nothing)
     *       sets diagnosis attribute for VetVisitUrgent object.
     *       if theDiagnosis is an empty String and/or is null, sets diagnosis to
     *       default of "**no diagnosis**";
     *       otherwise, sets diagnosis to theDiagnosis
     */
    public void setDiagnosis(String theDiagnosis)
    {
        if(!theDiagnosis.equals(null) && !theDiagnosis.equals("") &&
            theDiagnosis.length() > 0)
        {
            this.diagnosis = theDiagnosis;
            
        }//end of IF
        
        else
        {
            this.diagnosis = "**no diagnosis**";
            
        }//end of ELSE
        
    }//end setDiagnosis()
    
    
    /* *
     * Pre:  (String)
     *       may not be empty; receives String theTreatment
     * Post: (nothing)
     *       sets treatment attribute for VetVisitUrgent object.
     *       if theTreatment is an empty String and/or is null, sets treatment to
     *       default of "**no treatment**";
     *       otherwise, sets treatment to theTreatment
     */
    public void setTreatment(String theTreatment)
    {
        if(!theTreatment.equals(null) && !theTreatment.equals("") &&
            theTreatment.length() > 0)
        {
            this.treatment = theTreatment;
            
        }//end of IF
        
        else
        {
            this.treatment = "**no treatment**";
            
        }//end of ELSE
                
    }//end setTreatment()
    
    
    /* --------------------------- other methods --------------------------- */
    
    /* *
     * Pre:  (nothing)
     *       expects to be called on an instance of a VetVisitUrgent object
     * Post: (String)
     *       appends (from superclass VetVisit) String containing the visitID, date,
     *       doctor, location for a given VetVisitUrgent object to String containing
     *       diagnosis and treatment for a given VetVisitUrgent
     */
    public String toString()
    {
        String toStringOut = "";
        String vetVisitAttributes = super.toString();
        String vetVisitUrgAttributes = "";
        
        vetVisitUrgAttributes = "diagnosis: "   + this.getDiagnosis() + "  " +
                                "treatment: "   + this.getTreatment();
        
        toStringOut = vetVisitAttributes + "  " + vetVisitUrgAttributes;
        
        return toStringOut;
        
    }//end toString()
   
    /* *
     * Pre:  (nothing)
     *       expects to be called on an instance of a VetVisitStandard
     *       caller methods are VetVisit.writeToCancellationsFile() and this.writeToFile()
     * Post: (String plainDataOut)
     *       assembles and returns String containing all of a HousePet's VetVisitUrgent
     *       attributes in a format that can be written directly to an output file so that
     *       the data will be readable by our program's readFromScanner() methods
     */
    public String plainDataToString()
    {
        String plainDataOut = "";
        String vetVisitData = super.plainDataToString();
        String vetVisitUrgData = "";
        
        vetVisitUrgData = this.getDiagnosis() + "\n" +
                          this.getTreatment();
        
        plainDataOut = vetVisitData + "\n" + vetVisitUrgData;
        
        return plainDataOut;
        
    }//end plainDataToString()
    
    /* *
     * Pre:  (PrintWriter ofile1)
     *       expects to be called on an instance of a VetVisitUrgent object; caller
     *       method is VetVisitListImpl.writeToFile()
     *       expects to receive a valid (not null) PrintWriter
     *       performs null check for PrintWriter parameter
     * Post: (nothing)
     *       writes VetVisitUrgent data to output file
     */
    public void writeToFile(PrintWriter ofile1)
    {
        if(ofile1 != null)
        {
            ofile1.println(this.plainDataToString());
        }
        
        return;
        
    }//end writeToFile()
    
}//end of VetVisitUrgent.java