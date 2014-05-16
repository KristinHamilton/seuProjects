package lab6;
/* *
 * [MyUtils.java]
 * Author: Dr. Baker, copied by Kristin Hamilton
 * Desc: contains methods dateToString() and stringToDate(), which allow conversion
 *       from GregorianCalendar date to String form of date, and from String date to
 *       GregorianCalendar form of date, respectively
 * Date created: 04-Mar-2014 for Lab6
 * Date last modified: 17-Mar-2014
 *       Modifications: added some minor String class modifications like trim(),
 *       refactored a few variables to help me not get confused
 */
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Scanner;
import java.util.StringTokenizer;

public class MyUtils
{
    /* *
     * Pre:  "receives: a date as a GregorianCalendar"
     * Post: "returns: received date as a string in format mm/dd/yyyy"
     */
    public static String dateToString(GregorianCalendar date)
    {
        String dateString = "";
        
        int month = date.get(Calendar.MONTH);
        month++; // add 1 due to zero-based months
        int day = date.get(Calendar.DAY_OF_MONTH);        
        int year = date.get(Calendar.YEAR);
               
        dateString = month + "/" + day + "/" + year;
        
        return dateString;
        
    }//end dateToString()
    
    /* *
     * Pre:  "receives: theDate as a String in format mm/dd/yyyy"
     * Post: "returns: received date as a correct GregorianCalendar object"
     */
    public static GregorianCalendar stringToDate(String theDate)
    {
        //GregorianCalendar date = null;
        
        StringTokenizer tokenizer = new StringTokenizer(theDate, "/");
        String temp = tokenizer.nextToken();  // grabs up to "/"
        int month=0, day=1, year=2000;  // default date values
        try {
          month = Integer.parseInt(temp);
          month--;  // zero-based months
          temp = tokenizer.nextToken();
          day = Integer.parseInt(temp);
          temp = tokenizer.nextToken();
          year = Integer.parseInt(temp);
        }
        catch(NumberFormatException e) {
           System.out.println("error extracting date, using default date");
        }
        return new GregorianCalendar(year, month, day);
        
        //return date;
        
    }//end stringToDate()
    
    // put the next 2 methods in MyUtils class


    // pre: in is open and ready to read from
    // post: reads 1 VetVisit (either urgent or standard) and returns that as a VetVisit instance of
    //       the appropriate type, returns null if no visit available to read or end of visit list.
    public static VetVisit vetVisitReadFromScanner(Scanner ifile1)
    {
        VetVisit visit = null;
        
        int aLicenseNumber;
        String line1,  aVisitId;
        String aDoctor, aLocation, aDate, someOtherCare, aTreatment, aDiagnosis, someVaccines;
        
        aVisitId = ifile1.next();
        
        // now check did we read the **** value?
        if(aVisitId.equals("****"))
            return null;  // if so, return null, so caller knows no more data 
        
        aDate = ifile1.next();
        aLocation = ifile1.next();
        aDoctor = ifile1.nextLine().trim();
        line1 = ifile1.nextLine().trim();
           
        // now check if what we see next is a license number (for Standard) or it must be an Urgent type 
        if(ifile1.hasNextInt())
        {
            someVaccines = line1;
            aLicenseNumber = ifile1.nextInt();
            someOtherCare = ifile1.nextLine().trim();
               
            visit = new VetVisitStandard(aVisitId, aDate, aLocation, aDoctor,
                                         someVaccines, aLicenseNumber,someOtherCare);
        }
           
        else
        {
            aDiagnosis = line1;
            aTreatment = ifile1.nextLine().trim();
               
            visit = new VetVisitUrgent(aVisitId, aDate, aLocation, aDoctor,
                                       aDiagnosis, aTreatment);                                           
        }
           
        return visit;  // sends back one of the two possible visit types, whichever was created.
           
    }//end vetVisitReadFromScanner()   
       
       
    // belongs in MyUtils
    // use this to populate/read the VetVisitList for a HousePet, reads 1 entire set of data
    // for the entire list of vetvisits for the current HousePet, called from HousePet's read from Scanner
    public static void vetVisitListReadFromScanner(Scanner ifile1, VetVisitList aList)
    {
        VetVisit visit = MyUtils.vetVisitReadFromScanner(ifile1);
        
        while (visit != null)
        {
            aList.add(visit);
            visit = MyUtils.vetVisitReadFromScanner(ifile1);
            
        }//end WHILE loop
        
    }//end vetVisitListReadFromScanner()
       
}//end MyUtils.java