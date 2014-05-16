/* *
 * [AppendRecord.java]
 * Desc:    checks whether file exists (if not, displays error);
 *          reads defect records from scrubbed flat file DefectFile.csv;
 *          writes records to output file;
 *          appends a new record to end of output file;
 *          reports when task has been accomplished
 * Author:  Kristin Hamilton
 * Modification History:    created 19-Jan-2014 through 20-Jan-2014 for HW1
 *          27-Jan-2014 --- added return statements to main() and appendRecord();
 *                          added System.exit(0) to appendRecord() for if file not found
 *          06-May-2014 --- imported into new project COSC3337HW3
 */
import java.util.Scanner;
import java.io.PrintWriter;

public class AppendRecord
{
    /* *
     * Pre:  (Scanner, PrintWriter, String)
     * Post: (nothing)
     */
    public static void appendRecordToFile(Scanner inputFile, PrintWriter outputFile,
                                          String newRecord)
    {
        String recordText = "";

        /* read input file, one record at a time; write each record to output file as
         * it is read in*/
        while(inputFile.hasNext())
        {
            recordText = inputFile.next();
            outputFile.println(recordText);
        }

        /* write newRecord to output file; close input, output files */
        outputFile.println(newRecord);
        inputFile.close();
        outputFile.close();
        return;
        
    }//end appendRecordToFile()

}//end AppendRecord.java