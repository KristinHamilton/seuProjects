/* *
 * [HW3Menu.java]
 * Desc: Main program for cosc3337 hw3; presents program menu to user, gets their menu
 *       choice, and controls the flow of the program.
 *       calls AppendRecord.appendRecordToFile(), RemoveRecord.removeRecordFromFile(),
 *       ModifyField.modifyVehicleMake(), and CountDefects.main().
 * Author: Kristin Hamilton
 * Date created: 06-May-2014
 * Date last modified: 06-May-2014
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class HW3Menu
{
    public static void main(String[] args)
    {
        int i = 0;
        char userMenuChoice = ' ';
        
        do
        {
            switch(i)
            {
                case 0:
                    displayMenu();
                case 1:
                case 2:
                    i++;
                    break;
                                 
                case 3:
                    i = 0;
                    break;
            }

            userMenuChoice = getUserMenuChoice();
        
            switch(userMenuChoice)
            {
                case 'a':
                case 'A':
                    menuAppendRecord();
                    break;

                case 'b':
                case 'B':
                    menuRemoveRecord();
                    break;

                case 'c':
                case 'C':
                    menuModifyField();
                    break;

                case 'd':
                case 'D':
                    menuCountDefects();
                    break;

                case 'x':
                case 'X':
                    displayExitMessage();               
                    break;

                default:
                    System.out.println("Invalid option entered.");
                    break;            

            }//end switch(userMenuChoice)

        }
        while(userMenuChoice != 'x' && userMenuChoice != 'X');

        return;
        
    }//end main()

    /* *
     * Pre:  (nothing)
     * Post: (nothing)
     */
    public static void displayMenu()
    {
        System.out.println("****************************************");
        System.out.println("Welcome to the program!");
        System.out.println();
        System.out.println("a.  Add a record to the file");
        System.out.println("b.  Remove a record from the file");
        System.out.println("c.  Modify a field in the file (similar to find/replace)");
        System.out.println("d.  Find the number of defects reported for a given year");
        System.out.println();
        System.out.println("x.  Exit");
        System.out.println("****************************************");
        return;
        
    }//end displayMenu()
    
    /* *
     * Pre:  (nothing)
     * Post: (char)
     */
    public static char getUserMenuChoice()
    {
        Scanner console = new Scanner(System.in);
        String userInput = "";
        char userMenuChoice = ' ';
        
        System.out.println("Please enter an option from the menu.");
        userInput = console.next();
        
        if(userInput.length() > 0)
        {
            userMenuChoice = userInput.charAt(0);
        }
        
        else
        {
            userMenuChoice = 0;
        }        
                
        return userMenuChoice;
        
    }//end getUserMenuChoice()
    
    /* *
     * Pre:  (nothing)
     * Post: (nothing)
     */
    public static File getMainInputFile()
    {
        /* initialize variables */
        //String filename = "3337DefectFile10.csv";
        String filename = "DefectFile.csv";

        /* set input file */
        File file1 = new File(filename);

        /* if filename not found, display error message and exit program */
        if(!file1.exists())
        {
            System.out.println("Input file " + filename + " does not exist. "
                   + "Get it together");
            System.exit(0);            
        }

        return file1;        
        
    }//end getFileFromUser()
    
    /* *
     * Pre:  (nothing)
     * Post: (nothing)
     */
    public static void displayExitMessage()
    {
        System.out.println("\nThanks for using the program!");
        return;
        
    }//end displayExitMessage()
    
    /* *
     * Pre:  (nothing)
     * Post: (nothing)
     */
    public static void menuAppendRecord()
    {
        File file1 = getMainInputFile();
        String outputFilename = "DefectFileProcessed.csv";
        String newRecord = "C89011,BMW,240Z,2014,VEHICLE SPEED CONTROL:LINKAGES," +
        "BMW,20130306,20140116,,THROTTLE NON-RETURN";
        
        try
        {
            /* set field delimiters, output file */
            Scanner inputFile = new Scanner(file1).useDelimiter("\\n");
            PrintWriter outputFile = new PrintWriter(new File(outputFilename));
            
            AppendRecord.appendRecordToFile(inputFile, outputFile, newRecord);
            inputFile.close();
            outputFile.close();
            
            /* report back to user that objective has been accomplished */
            System.out.println("One new record has been added:");
            System.out.println("  " + newRecord);
            System.out.println("All data has been written to the file: " + outputFilename);
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Error: destination filename invalid " + e);
        }

        return;
        
    }//end menuAppendRecord()
    
    /* *
     * Pre:  (nothing)
     * Post: (nothing)
     */
    public static void menuRemoveRecord()
    {
        File file1 = getMainInputFile();
        Scanner console = new Scanner(System.in);
        String outputFilename = "DefectFileProcessed.csv";

        try
        {
            /* set field delimiters, output file */
            Scanner inputFile = new Scanner(file1).useDelimiter("\\n");
            PrintWriter outputFile = new PrintWriter(new File(outputFilename));

            System.out.println("Please enter the record you wish to delete:");
            int recordToDelete = console.nextInt();
            
            RemoveRecord.removeRecordFromFile(inputFile, outputFile, recordToDelete);
            inputFile.close();
            outputFile.close();
            
            /* report back to user that objective has been accomplished */
            System.out.println("Record number " + recordToDelete + " has been removed.");
            System.out.println("All data has been written to the file: " + outputFilename);
        }
        catch(FileNotFoundException e)
        {
            System.out.println();
        }

        return;
        
    }//end menuRemoveRecord()
    
    /* *
     * Pre:  (nothing)
     * Post: (nothing)
     */
    public static void menuModifyField()
    {
        File file1 = getMainInputFile();
        String outputFilename = "DefectFileProcessed.csv";
        String vehicleMake = "FORD";
        String newVehicleMake = "FOR";

        try
        {
            /* set field delimiters, output file */
            Scanner inputFile = new Scanner(file1).useDelimiter(",");
            PrintWriter outputFile = new PrintWriter(outputFilename);

            ModifyField.modifyVehicleMake(inputFile, outputFile, vehicleMake, newVehicleMake);
            inputFile.close();
            outputFile.close();

            /* report back to user that objective has been accomplished */
            System.out.println("Vehicle make " + vehicleMake + " has been modified to " +
                                newVehicleMake);
            System.out.println("All data has been written to the file: " + outputFilename);
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Error: destination filename invalid " + e);
        }
        
        return;
        
    }//end menuModifyField()
    
    /* *
     * Pre:  (nothing)
     * Post: (nothing)
     */
    public static void menuCountDefects()
    {
        File file1 = getMainInputFile();
        Scanner console = new Scanner(System.in);
        File file2 = new File("DefectList.csv");

        try
        {            
            /* set field delimiters, PrintWriter */
            Scanner inputFile = new Scanner(file1).useDelimiter(",");
            Scanner inputIndexFile = new Scanner(file2).useDelimiter(",|\\n");
            PrintWriter outputIndexFile = new PrintWriter(file2);
            
            CountDefects.main(inputFile, inputIndexFile, outputIndexFile);
            inputFile.close();
            inputIndexFile.close();
            outputIndexFile.close();
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Error: destination filename invalid " + e);
        }

        return;
        
    }//end menuCountDefects()
    
}//end HW3Menu.java