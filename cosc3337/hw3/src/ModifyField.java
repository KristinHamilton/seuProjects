/* *
 * [ModifyField.java]
 * Desc:    reads defect records from scrubbed flat file DefectFile.csv;
 *          checks whether field2 (manufacturer) has certain value (if so, modifies it)
 *          writes all records to output file;
 * Author:  Kristin Hamilton
 * Modification History:    created 19-Jan-2014 through 20-Jan-2014 for HW1
 *          27-Jan-2014 --- added return statements to main() and appendRecord();
 *                          added System.exit(0) to appendRecord() for if file not found
 *          06-May-2014 --- imported into new project COSC3337HW3
 */
import java.util.Scanner;
import java.io.PrintWriter;

public class ModifyField
{
    /* *
     * Pre:  (Scanner, PrintWriter, String, String)
     * Post: (nothing)
     */
    public static void modifyVehicleMake(Scanner ifile1, PrintWriter ofile1,
                                         String vehicleMake, String newVehicleMake)
    {
        /* initialize variables */
        int fieldCount = 0;
        int fieldOfInterest = 2;
        int recordCount = 0;
        int recordsInFile = 42604;
        String fieldText1 = "";
        String fieldText2 = "";
        String defectRecord = "";

        /* read ifile1 field by field */
        while(ifile1.hasNext())
        {
            fieldCount++;

            if(fieldCount <= fieldOfInterest)
            {
                fieldText1 = ifile1.next();

                if(fieldCount == fieldOfInterest && fieldText1.equals(vehicleMake))
                {
                    fieldText2 = newVehicleMake;
                }

                else
                {
                    fieldText2 = fieldText1;
                }

                fieldText2 += ",";

            }//end of IF(fieldCount <= fieldOfInterest)

            else  //if(fieldCount > fieldOfInterest)
            {
                fieldText1 = ifile1.nextLine();
                fieldText2 = fieldText1 + "\n";

                /* reset fieldCount */
                fieldCount = 0;
                recordCount++;
            }

            /* append fieldText2 to defectRecord */
            defectRecord += fieldText2;

            /* below: lowering the divisor makes less printing i/o's but takes much 
             * more time.  examples below.  
             *          (no divisor)  --- tried this, took so long I never got to see
             *                            how long it would take to reach completion
             *          divisor = 200 --- i/o's = approx200  --- slower
             *          divisor = 2000 -- i/o's = approx2000 --- faster
             * the reason printing less often makes it much slower seems to be
             * because of the amount of data being held in the defectRecord variable.
             * below, we re-zero recordCount and "dump" defectRecord when we write
             * to file.
             * the less data defectRecord is holding, the faster the program proceeds;
             * however, we are trying to limit the i/o's to limit the cost of the
             * operation to the customer (and to ourselves/our take home pay)
             * also, this is only printing through record 42600, and it is adding a 
             * blank field somewhere around field3 or field4.
             */
            if(recordCount == (recordsInFile / 200))
            {
                /* write defectRecord to output file */
                ofile1.print(defectRecord);
                defectRecord = "";
                recordCount = 0;
            }

        }//end of WHILE(ifile1.hasNext())

        /* close input file; close output file */
        ifile1.close();
        ofile1.close();

        return;

    }//end modifyVehicleMake()

}//end ModifyField.java