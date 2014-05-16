/* *
 * [CountDefects.java]
 * Desc:    asks user whether last date modified was at least a month ago;
 *          asks user to enter four-digit year (defectYear);
 *          -if last modified date of file is at least 30 days from the present date:
 *              reads defect records from scrubbed flat file;
 *              stores year and defect count in an array;
 *              writes array to output file;
 *          -otherwise, just scans text file for requested year and finds the defectCount
 *           corresponding to defectYear.
 *          program then reports count for defectYear back to user
 * Author:  Kristin, Eddie, Jairo, Mashari
 * Modification History:    created 24-Jan-2014 for HW2
 *          27-Jan-2014 --- added return statements to main() and appendRecord();
 *                          added System.exit(0) to appendRecord() for if file not found
 *          28-Jan-2014 --- methods added
 *          29-Jan-2014 --- modifications, code added to complete methods
 *          06-May-2014 --- imported into new project COSC3337HW3
 */
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Iterator;
//import java.util.Date;

public class CountDefects
{
    public static void main(Scanner ifile1, Scanner ifile2, PrintWriter ofile1)
    {
        boolean hasBeenModified = findLastModified();
        String defectYear = getDefectYear();
        
        if(hasBeenModified == true)
        {
            HashMap<String, Integer> defectMap = new HashMap<String, Integer>();
            defectMap = countDefects(ifile1);  
            writeToFile(defectMap, ofile1);
        }

        findYearDefects(ifile2, defectYear);               
        return;
    
    }//end of method main()

    /* *
     * Pre:  (nothing)
     *       asks user whether file has been modified or not, takes their word for it
     * Post: (boolean)
     *       returns true if file has been modified; otherwise, returns false.
     */
    public static boolean findLastModified()
    {  
        boolean hasBeenModified = false;        
        //long dateLastModified = file1.lastModified();
        //long dateCurrent = Date();

        Scanner console = new Scanner(System.in);

        System.out.println("Welcome to the Count Defects program!");
        System.out.println("Has the original defect file been modified in the " +
        "past month?  Type: 'yes' if it has been modified, 'no' if it has " +
        "not. If you are unsure, please type 'yes'.");

        String modifiedYesNo = (console.nextLine()).toUpperCase();

        if(modifiedYesNo.equals("YES"))
        {
            hasBeenModified = true;
        }

        else if(modifiedYesNo.equals("NO"))
        {
            hasBeenModified = false;
        }

        else  //if user typed neither "yes" nor "no"
        {
            System.out.println("You typed neither 'yes' nor 'no'.");
            System.out.println("Executive decision: I'll take that as a 'yes'!");
            hasBeenModified = true;
        }

        //System.out.println(dateLastModified);
        return hasBeenModified;

    }//end findLastModified()   
    
    /* *
     * Pre:  (none) gets defectYear from user
     * Post: returns String defectYear
     */
    public static String getDefectYear()
    {
        String defectYear = "";
        Scanner console = new Scanner(System.in);
        
        /* ask user to enter year */
        System.out.println("Please enter a four-digit year for which the defects " +
                "will be counted:");
        
        defectYear = console.nextLine();
        return defectYear;
        
    }//end of method getDefectYear()
    
    /* *
     * Pre:  (Scanner)
     *       receives input file to be read from
     * Post: (HashMap<String, Integer>)
     *       returns HashMap defectMap which contains key,value pairs year,defectCount
     */
    public static HashMap<String, Integer> countDefects(Scanner ifile1)
    {
        /* instantiate HashMap */
        HashMap<String, Integer> defectMap = new HashMap<String, Integer>();

        /* initialize variables */
        int fieldCount = 0;
        int yearCount = 0;
        int yearFieldNumber = 4;
        String fieldText = "";
        String yearString = "";

        //String year = "";
        int defectCount = 0;

        /* read ifile1 field by field */
        while(ifile1.hasNext())
        {
            while(fieldCount < yearFieldNumber)
            {
                fieldCount++;
                fieldText = ifile1.next();

                /* if field4 (model year) equals defectYear, add 1 to yearCount */
                if(fieldCount == yearFieldNumber)
                {
                    yearString = fieldText;
                    
                    if(defectMap.containsKey(yearString))
                    {
                        defectMap.put(yearString, (defectMap.get(yearString) + 1));
                    }
                    
                    else  //if defectMap doesn't contain key of "yearString"
                    {
                        defectMap.put(yearString,  1);
                    }
                        
                }//end of IF(fieldCount = yearFieldNumber)

            }//end of WHILE(fieldCount < yearFieldNumber)
                
            ifile1.nextLine();  //advance cursor to next line to reduce i/o
            fieldCount = 0;  //reset field counter when we advance to next record

        }//end of WHILE(ifile1.hasNext())

        /* close input file */
        ifile1.close();
        return defectMap;
        
    }//end of method countDefects()
    
    /* *
     * Pre:  receives Map defectMap
     * Post: (none)  writes defectMap to text file
     */
    public static void writeToFile(HashMap<String, Integer> defectMap, PrintWriter ofile1)
    {
        Iterator itr = (Iterator) defectMap.keySet().iterator();

        /* iterate through all keys (years) for defectMap;
         * write the year and defect count for the given year to output file */
        while(itr.hasNext())
        {
            String year = (String) itr.next();
            int defectYearCount = defectMap.get(year);

            ofile1.println(year + "," + defectYearCount);  
        }
        
        ofile1.close();
        return;
                
    }//end of method writeToFile()
    
    /* *
     * Pre:  (Scanner, String)
     *       receives String defectYear
     * Post: (nothing)
     *       reports number of defects found for user-provided year
     */
    public static void findYearDefects(Scanner ifile2, String defectYear)
    {
        int fieldCount = 0;
        int fieldsPerRecord = 2;
        String defectCount = "";         /* defect count listed for a given year */
        String defectYearCount = "";     /* the defect count listed for defectYear */
        boolean defectYearFound = false;
        String year = "";                /* year listed in field1 */
        String reportStatement = "";

        /* read ifile1 field by field */
        while(ifile2.hasNext())
        {
            while(fieldCount < fieldsPerRecord)
            {                    
                year = ifile2.next();
                fieldCount++;

                defectCount = ifile2.next();
                fieldCount++;

                /* if field1 (model year) equals defectYear, get the defectCount
                 * listed for that year (field2) and set defectYearCount equal to
                 * that value of defectCount */
                if(year.equals(defectYear))
                {
                    defectYearCount = defectCount;
                    defectYearFound = true;

                }//end of IF

            }//end of WHILE(fieldCount < fieldsPerRecord)

            fieldCount = 0;  //reset field counter when we reach fieldsPerRecord

        }//end of WHILE(ifile1.hasNext())

        /* close input file */
        ifile2.close();

        /* set the content of the message that will be displayed to the user */
        if(defectYearFound == true)
        {
            reportStatement = "The number of defects found for " +
            "model year " + defectYear + " vehicles is " + 
            defectYearCount;
        }

        else  //if(defectYearFound == false)
        {
            reportStatement = "The year " + defectYear + " you requested was not " +
            "found.";
        }

        /* report number of defects counted for defectYear */
        System.out.println(reportStatement);
        return;

    }//end findYearDefects()
        
}//end CountDefects.java