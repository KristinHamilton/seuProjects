/* *
 * [RemoveRecord.java]
 * Desc:    checks whether file exists (if not, displays error);
 *          reads defect records from scrubbed flat file DefectFile.csv;
 *          writes all records (except for record to be removed) to output file;
 *          reports when task has been accomplished
 * Author:  Kristin, Eddie, Jairo, Mashari
 * Modification History:    created 19-Jan-2014 through 20-Jan-2014 for HW1
 *          27-Jan-2014 --- added return statements to main() and appendRecord();
 *                          added System.exit(0) to appendRecord() for if file not found
 *          28-Jan-2014 --- added code for asking user to enter record number to delete
 *          06-May-2014 --- imported into new project COSC3337HW3
 */
import java.util.Scanner;
import java.io.PrintWriter;

public class RemoveRecord
{
    /* *
     * Pre:  (Scanner, PrintWriter, int)
     * Post: (nothing)
     */
    public static void removeRecordFromFile(Scanner ifile1, PrintWriter ofile1,
                                            int recordToDelete)
    {
        int recordCount = 0;
        String recordText = "";

        /* read input file, one record at a time;
         * write each record to output file as it's read in*/
        while(ifile1.hasNext())
        {
            recordCount++;
            recordText = ifile1.next();

            if(recordCount != recordToDelete)
            {
                ofile1.println(recordText);
            }

        }//end WHILE

        /* close input file; close output file */
        ifile1.close();
        ofile1.close();
        return;

    }//end removeRecordFromFile()

}//end RemoveRecord.java