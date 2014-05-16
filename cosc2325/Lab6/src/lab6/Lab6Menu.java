package lab6;
/* *
 * [Lab6Menu.java]
 * Author: Kristin Hamilton
 * Desc: set of methods to be used with HousePetListImpl.java. methods listed below.
 *       main(), displayMenu(), and getUserOption().
 *       menuAdd(), menuRemove(), menuRemoveAll(), menuFind(), menuGetSize(),
 *       menuGetByName(), menuModifyAge(), and menuWriteToFile().
 *       [Lab5] did not modify.
 *       [Lab6] menuAddVisit() and menuRemoveVisit().
 * Date created: 04-Mar-2014 for Lab6
 * Date last modified: 17-Mar-2014
 */
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Lab6Menu
{
    /* *
     * Pre:  (none - main() method) someone has to call the program for main() to run 
     * Post: instantiates a new HousePetListImpl() object.
     * 
     *       performs the following actions until do/while loop is terminated.
     *       
     *       do/while loop is terminated if the user enters 'q' (exit) or if user enters
     *       an invalid choice (enters something other than one of the menu choices).
     *       
     *       inside of do/while loop, main() follows the procedure outlined below.
     *       first calls displayMenu() to display program menu to user.
     *       then calls getUserOption() and stores return value in char userOption.
     *       calls method in HousePetListImpl.java that corresponds to the user's option
     *       choice. menu will be redisplayed after the completion of each choice path,
     *       except for the event that the user (voluntarily via exit or
     *       involuntarily via invalid option) chooses to terminate the program.
     *       
     *       program terminates in the following way.
     *       exitSave() checks whether HousePetList has been modified since beginning of
     *       program; if so, asks whether user would like to save, if so, calls
     *       writeToFile(). displayExitMsg() will then be called regardless of the 'saved'
     *       situation, to inform user that they are exiting the program.
     */
    public static void main(String[] args)
    {
        HousePetList hpArray = new HousePetListImpl();
        char userOption = '#';
        
        do
        {
            displayMenu();
            userOption = getUserOption();
            
            switch(userOption)
            {
                /* a.  populate list with HousePet data from a file */
                case 'a':
                    HousePetListImpl.populate(hpArray);
                    break;
                    
                /* b.  add a HousePet to the list */
                case 'b':
                    menuAdd(hpArray);
                    break;
                    
                /* c.  remove a HousePet from the list */                    
                case 'c':
                    menuRemove(hpArray);
                    break;
                
                /* d.  remove ALL HousePets from the list */
                case 'd':
                    menuRemoveAll(hpArray);
                    break;
                    
                /* e.  view the current list of HousePets */    
                case 'e':
                    HousePetListImpl.displayAll(hpArray);
                    break;
                    
                /* f.  find out if a HousePet is in the current list */
                case 'f':
                    menuFind(hpArray);
                    break;
                    
                /* g.  view the number of HousePets in the current list */
                case 'g':
                    menuGetSize(hpArray);
                    break;
                    
                /* h.  search for a HousePet by name */
                case 'h':
                    menuGetByName(hpArray);
                    break;
                        
                /* i.  modify the age of a HousePet in the list */    
                case 'i':
                    menuModifyAge(hpArray);
                    break;
                     
                /* j.  save */    
                case 'j':
                    menuWriteToFile(hpArray);
                    break;
                    
                /* k.  add a VetVisit for a HousePet */    
                case 'k':
                    menuAddVisit(hpArray);
                    break;
                    
                /* l.  remove a VetVisit for a HousePet */    
                case 'l':
                    menuRemoveVisit(hpArray);
                    break;    
                
                /* q.  exit */    
                case 'q':
                    HousePetListImpl.exitSave(hpArray);
                    break;
                    
                /* invalid option entered */   
                default:
                    System.out.println("Invalid option entered.");
                    HousePetListImpl.exitSave(hpArray);
                    //HousePetListImpl.displayExitMsg();
                    break;
            
            }//end switch(userOption)
            
        }
        while(userOption != 'q');  //end of DO...WHILE
        
    }//end main()
    
    /* *
     * Pre:  called by main() to display menu to user before calling getUserOption() to
     *       obtain user's menu option choice
     * Post: (none) just displays menu, mission accomplished
     */
    public static void displayMenu()
    {
        System.out.println("");
        System.out.println("**********************************");
        System.out.println("Welcome to the HousePet program!");
        System.out.println();
        System.out.println("a.  populate list with HousePet data from a file");
        System.out.println("b.  add a HousePet to the list");
        System.out.println("c.  remove a HousePet from the list");
        System.out.println("d.  remove ALL HousePets from the list");
        System.out.println("e.  view the current list of HousePets");
        System.out.println("f.  find out if a HousePet is in the current list");
        System.out.println("g.  view the number of HousePets in the current list");
        System.out.println("h.  search for a HousePet by name");
        System.out.println("i.  modify the age of a HousePet in the list");
        System.out.println();
        System.out.println("k.  add a VetVisit for a HousePet");
        System.out.println("l.  remove a VetVisit for a HousePet");
        System.out.println();
        System.out.println("j.  save");
        System.out.println("q.  exit");
        System.out.println("**********************************");
        
        return;
        
    }//end displayMenu()
    
    /* *
     * Pre:  called by main(); prompts user to enter menu choice
     * Post: converts input to lowercase, reduces user input to the first char entered,
     *       then returns char to be used in switch(userOption) for menu/program
     *       navigation
     */
    public static char getUserOption()
    {
        char userOption = '#';
        String userInput = "";
        String lowercaseInput = "";
        @SuppressWarnings("resource")
        Scanner console = new Scanner(System.in);
        
        do
        {
            System.out.println("Please enter an option from the menu above");
        
            try
            {
                userInput = console.next();
            }
            catch(NoSuchElementException e)
            {
                System.out.println("Error: no available tokens found " + e);
            }
            catch(IllegalStateException e)
            {
                System.out.println("Error: Scanner is closed " + e);
            }
        
            lowercaseInput = userInput.toLowerCase();

            /* get the first char of user input */
            if(lowercaseInput.length() > 0)
            {
                userOption = lowercaseInput.charAt(0);
            
            }//end IF

        }
        while(userOption != 'a' && userOption != 'b' && userOption != 'c' &&
              userOption != 'd' && userOption != 'e' && userOption != 'f' &&
              userOption != 'g' && userOption != 'h' && userOption != 'i' &&
              userOption != 'j' && userOption != 'k' && userOption != 'l' &&
              userOption != 'q');
      
        return userOption;
        
    }//end getUserOption()
    
    /* *
     * Pre:  called by Lab6Menu.main(), case 'b'.
     *       expects to receive instantiated HousePetList.
     *       prompts user to input attributes for a new HousePet object (one prompt per
     *       attribute; four prompts total).
     *       calls setters with the user-provided data, then calls add(), which returns
     *       true if HousePet has been added to the list, false if not
     * Post: displays a msg to user reporting the outcome of the add() attempt
     */
    public static void menuAdd(HousePetList hpArray)
    {
        HousePet housepet = new HousePet();
        @SuppressWarnings("resource")
        Scanner console = new Scanner(System.in);
        
        //alternate option: use one statement to ask user to enter all four attributes,
        //separated by newlines, then call readFromScanner()
        
        int aChipID = 0;
        String aName = "";
        String aPetType = "";
        double anAge = 0;
        boolean addedHpToList = false;
        String outputStatement = "";
        
        /* chipID */        
        System.out.println("Please enter a chipID for the new HousePet");
        
        try
        {
            aChipID = console.nextInt();
            console.nextLine();
        }
        catch(InputMismatchException e)
        {
            System.out.println("Error: a non-int was entered " + e);
            System.out.println("chipID will be set to default of 0.");
            console.nextLine();
        }
        catch(NoSuchElementException e)
        {
            System.out.println("Error: chipID not found " + e);
            System.out.println("chipID will be set to default of 0");
            console.nextLine();
        }
        catch(IllegalStateException e)
        {
            System.out.println("Error: Scanner is closed " + e);
            System.out.println("chipID will be set to default of 0");
            console.nextLine();
        }
        
        /* name */
        System.out.println("Please enter the name of the new HousePet");
        
        try
        {
            aName = console.nextLine();
        }
        catch(NoSuchElementException e)
        {
            System.out.println("Error: name not found " + e);
            System.out.println("Name will be set to default value of \"**no name**\"");
        }
        catch(IllegalStateException e)
        {
            System.out.println("Error: Scanner is closed " + e);
            System.out.println("Name will be set to default value of \"**no name**\"");
        }
        
        /* petType */
        System.out.println("Please enter the petType of the new HousePet");
        
        try
        {
            aPetType = console.nextLine();
        }
        catch(NoSuchElementException e)
        {
            System.out.println("Error: petType not found " + e);
            System.out.println("Pet type will be set to default value of " +
                "\"**no type**\"");
        }
        catch(IllegalStateException e)
        {
            System.out.println("Error: Scanner is closed " + e);
            System.out.println("Pet type will be set to default value of " +
                "\"**no type**\"");
        }
        
        /* age */
        System.out.println("Please enter the age of the new HousePet");
        
        try
        {
            anAge = console.nextDouble();
            console.nextLine();
        }
        catch(InputMismatchException e)
        {
            System.out.println("Error: a non-double was entered " + e);
            System.out.println("Age will be set to default value of 0.0");
            console.nextLine();
        }
        catch(NoSuchElementException e)
        {
            System.out.println("Error: chipID not found " + e);
            System.out.println("chipID will be set to default of 0");
            console.nextLine();
        }
        catch(IllegalStateException e)
        {
            System.out.println("Error: Scanner is closed " + e);
            System.out.println("Age will be set to default value of 0.0");
            console.nextLine();
        }

        /* set housepet's attributes */
        housepet.setChipId(aChipID);
        housepet.setName(aName);
        housepet.setPetType(aPetType);
        housepet.setAge(anAge);
        
        /* try to add housepet to hpArray */
        addedHpToList = hpArray.add(housepet);
        
        /* determine outputStatement content */
        if(addedHpToList == true)
        {
            outputStatement = "The following HousePet was added to the list:";
        }
        
        else //if(addedHpToList == false)
        {
            outputStatement = "The HousePet was not added to the list, either because " +
                "the chipID specified belongs to a HousePet that is already in the " +
                "list, or because the list is already full of HousePets.";
        }
        
        /* report back to user whether housepet was or was not added to list */
        System.out.println(outputStatement);
        System.out.println(housepet);

        return;
        
    }//end menuAdd()
    
    /* *
     * Pre:  called by Lab6Menu.main(), case 'c'.
     *       expects to receive instantiated HousePetList and instantiated HousePet.
     *       calls displayAll() in order to allow the user to view the current list of
     *       HousePets before choosing one for removal.
     *       prompts user to enter chipID of HousePet they wish to remove from the list.
     *       calls setChipID on housepet using user-provided chipID;
     *       if the user had entered an invalid int (negative int), the setter will have
     *       set the chipID for housepet to 0. so then, if chipID > 0, menuRemove() calls
     *       remove() and stores the result in HousePet housepet.
     *       determines content of msg that will be displayed when reporting back to user
     * Post: displays msg to user, informing them of the result of the attempt to remove
     *       the HousePet with the chipID they entered
     *       
     *       if a HousePet has been removed from the list, hasBeenSaved will have been
     *       set to false in remove().
     */
    public static void menuRemove(HousePetList hpArray)
    {
        HousePet housepet = new HousePet();
        HousePet removedHP = new HousePet();
        @SuppressWarnings("resource")
        Scanner console = new Scanner(System.in);
        int aChipID = -1;
        String outputStatement = "";
        
        /* display all HousePets */
        HousePetListImpl.displayAll(hpArray);
        
        /* prompt user to choose which HousePet to remove */
        System.out.println("Please enter the chipID of the HousePet you wish to remove");
        
        try
        {
            aChipID = console.nextInt();            
        }
        catch(InputMismatchException e)
        {
            System.out.println("Error: a non-int was entered " + e);
            return;  
        }
        catch(NoSuchElementException e)
        {
            System.out.println("Error: chipID not found " + e);
        }
        catch(IllegalStateException e)
        {
            System.out.println("Error: Scanner is closed " + e);
        }
        
        housepet.setChipId(aChipID);  //if user entered invalid value, chipID will = 0.
                
        /* if user-provided chipID is valid, call remove(),which will return
         * HousePet that has been removed from the list
         */
        if(aChipID > 0)
        {
            removedHP = hpArray.remove(housepet);
            
            /* determine the contents of outputStatement */
            if(removedHP == null)
            {
                outputStatement = "the HousePet with chipID " + aChipID +
                    " was not found in the list";
                
            }//end IF(removedHP == null)
            
            else  //if(hpArray contains housepet)
            {
                outputStatement = "The HousePet was removed from the list; its " +
                    "attributes are displayed below.\n" + removedHP;
                
            }//end ELSE
            
        }//end IF(chipIdToRemove > 0)
        
        else  //if(chipIdToRemove <= 0)
        {
            outputStatement = "You entered " + aChipID + ".\n" +
                "The chipID you entered was either zero or a negative integer.\n" +
                "A negative number is an invalid value for a chipID, and a HousePet " +
                "with a chipID of zero is an unacceptable candidate for removal since " +
                "there may currently be more than one HousePet with a chipID of 0.\n" +
                "In either case, the HousePet was not removed from the list.";
            
        }//end ELSE(IF(chipIdToRemove <= 0))

        /* report the results of the removal attempt back to the user */
        System.out.println(outputStatement);
        
        return;
        
    }//end menuRemove()
    
    /* *
     * Pre:  called by Lab6Menu.main(), case 'd'.
     *       expects to receive instantiated HousePetList.
     *       calls removeAll() on hpArray
     * Post: displays a msg to user informing them that all HousePets have been removed
     *       from the list.
     *       
     *       hasBeenSaved will have been set to false in removeAll().
     */
    public static void menuRemoveAll(HousePetList hpArray)
    {
        hpArray.removeAll();
        
        System.out.println("All HousePets have been removed from the list");
        
        return;
        
    }//end menuRemoveAll()
    
    /* *
     * Pre:  called by Lab6Menu.main(), case 'f'.
     *       expects to receive instantiates HousePetList and instantiated HousePet.
     *       calls find(). i probably could have done a better job of distributing the
     *       load between this method and find(), but it seemed unnecessarily complicated
     *       to do so. this method could probably be eliminated
     * Post: doesnt even return anything why did i write this method
     */
    public static void menuFind(HousePetList hpArray)
    {
        HousePetListImpl.find(hpArray);
        return;
        
    }//end menuFind()
    
    /* *
     * Pre:  called by Lab6Menu.main(), case 'g'. calls getSize() to get the # of
     *       HousePets currently in hpArray (hpCount)
     * Post: reports number of HousePets currently in the list by displaying a msg
     */
    public static void menuGetSize(HousePetList hpArray)
    {
        System.out.println("There are currently " + hpArray.getSize() + " " +
            "HousePets in the list");
        return;
        
    }//end menuGetSize()
    
    /* *
     * Pre:  called by Lab6Menu.main(), case 'h'.
     *       expects to receive instantiated HousePetList.
     *       prompts user to enter a name, then calls getByName() to get String
     *       (may be empty String, or may contain one or more HousePets).
     * Post: displays result of search to user.
     *       either tells user that no HousePets were found with the name provided,
     *       or displays list of (one or more) HousePets having the name entered by user
     */
    public static void menuGetByName(HousePetList hpArray)
    {
       @SuppressWarnings("resource")
       Scanner console = new Scanner(System.in);
       String userName = "";
       String hpNameString = "";
       
       /* prompt user to input a name */
       System.out.println("Please enter the name to search for");
       
       try
       {
           userName = console.nextLine();
       }
       catch(NoSuchElementException e)
       {
           System.out.println("Error: name not found " + e);
       }
       catch(IllegalStateException e)
       {
           System.out.println("Error: Scanner is closed " + e);
       }
       
       /* get hpNameString from getByName() */
       hpNameString = hpArray.getByName(userName);
       
       if(hpNameString.equals(""))
       {
           System.out.println("No HousePets were found with the name " + userName);  
       }
       
       else  //if(!(hpNameString.equals("")))
       {
           System.out.println("All the HousePets with the name " + userName +
               " are shown below:");
           System.out.println();
           System.out.println(hpNameString);
       }
       
       return;
        
    }//end menuGetByName()
    
    /* *
     * Pre:  called by Lab6Menu.main(), case 'i'.
     *       expects to receive instantiated HousePetList.
     *       calls displayAll() to display current list of HousePets, before prompting
     *       user to enter a chipID, and then a new age.
     *       calls modifyAge() which will try to find the given chipID in the hpArray,
     *       and if the chipID is found, tries to modify that HousePet's age attribute.
     *       if successful, modifyAge() will return true; if chipID not found in current
     *       list or if new age entered by user is a negative number, will return false.
     * Post: reports to user the outcome of the attempt to modify a HousePet's age;
     *       msg displayed to user determined based on whether modifyAge() returned true
     *       or returned false.
     * 
     *       if the HousePet's age has been modified, hasBeenSaved will have been set to
     *       false in modifyAge().
     */
    public static void menuModifyAge(HousePetList hpArray)
    {
        @SuppressWarnings("resource")
        Scanner console = new Scanner(System.in);
        int hpChipID = -1;
        double newAge = -1;
        boolean modifiedAge = false;
        
        /* display list of HousePets to user so they can view the chipIDs and ages before
         * deciding to modify a HousePet's age attribute */
        HousePetListImpl.displayAll(hpArray);
        
        /* prompt user to enter chipID */
        System.out.println("Please enter the chipID for the HousePet whose age you " +
            "would like to modify:");
        
        try
        {
            hpChipID = console.nextInt();
        }
        catch(InputMismatchException e)
        {
            System.out.println("Error: a non-int was entered " + e);
        }
        catch(NoSuchElementException e)
        {
            System.out.println("Error: chipID not found " + e);
        }
        catch(IllegalStateException e)
        {
            System.out.println("Error: Scanner is closed " + e);
        }
          
        /* prompt user to enter new age */
        System.out.println("Please enter the new age for the HousePet");
        
        try
        {
            newAge = console.nextDouble();
        }
        catch(InputMismatchException e)
        {
            System.out.println("Error: a non-double was entered " + e);
        }
        catch(NoSuchElementException e)
        {
            System.out.println("Error: age not found " + e);
        }
        catch(IllegalStateException e)
        {
            System.out.println("Error: Scanner is closed " + e);
        }
        
        /* call modifyAge on hpArray since we havent looked for the HousePet yet */
        modifiedAge = hpArray.modifyAge(hpChipID, newAge);
        
        /* if age modified successfully */
        if(modifiedAge == true)
        {
            System.out.println("The age of the HousePet with chipID " + hpChipID +
                " has been updated successfully.");
        }
        
        /* if age not modified */
        else  //if(modifiedAge == false)
        {
            System.out.println("Attempt to modify age unsuccessful:");
            System.out.println("Either the new age entered was invalid (negative) or " +
                "a HousePet with chipID " + hpChipID + " was not located in the " +
                "current list");
        }
        
        return;
        
    }//end menuModifyAge()
    
    /* *
     * Pre:  called by Lab6Menu.main(), case 'j'.
     *       expects to receive instantiated HousePetList.
     *       prompts user for filename of file where current list data will be written;
     *       calls writeToFile(), which handles the actual writing to file.
     *       writeToFile() returns true or false, in response to write operation being
     *       successfully executed or not, respectively.
     * Post: displays msg to user reporting outcome of write attempt based on the value
     *       returned by writeToFile().       
     * 
     *       if the current list has been written to destination file, hasBeenSaved will
     *       have been set to true in writeToFile().
     */
    public static void menuWriteToFile(HousePetList hpList)
    {
        @SuppressWarnings("resource")
        Scanner console = new Scanner(System.in);
        String filename = "";
        boolean hasBeenWritten = false;
        
        /* prompt user for filename */
        System.out.println("Please enter the name of the file you wish to save to");
        
        try
        {
            filename = console.nextLine();
        }
        catch(NoSuchElementException e)
        {
            System.out.println("Error: filename not found " + e);
        }
        catch(IllegalStateException e)
        {
            System.out.println("Error: Scanner is closed " + e);
        }
        
        hasBeenWritten = hpList.writeToFile(filename);
        
        if(hasBeenWritten == true)
        {
            System.out.println("The current list of HousePets has been saved to " + 
                filename);
        }
        
        else  //if(hasBeenWritten == false)
        {
            System.out.println("Save to file unsuccessful.");
        }
        
        return;
        
    }//end menuWriteToFile()
        
    /* *
     * Pre:  (HousePetList)
     *       called by Lab6Menu.main(), case 'k'.
     *       expects to receive instantiated HousePetList hpArray
     *       prompts user to enter chipID and visit information for a VetVisit theyd like
     *       to add to the visitList for a HousePet with the given chipID
     *       performs various other checks, see comments within method
     * Post: (nothing)
     *       if valid data is provided by user, adds a VetVisit to the HousePet's visitList
     *       visitCount is incremented from within VetVisitListImpl.add()
     *       see code and comments within method for more detailed descriptions
     */
    public static void menuAddVisit(HousePetList hpArray)
    {        
        int userChipID = 0;
        HousePet tempHousePet = new HousePet(); /* new HP created with default chipID */
        @SuppressWarnings("resource")
        Scanner console = new Scanner(System.in);
        boolean containsHousePet = false;
        boolean hasBeenAdded = false;
        
        System.out.println("Please enter the chipID of the HousePet you would like to " +
            "add a visit for.");
        
        try 
        {
            userChipID = console.nextInt();
            tempHousePet.setChipId(userChipID);
        }
        catch(InputMismatchException e)
        {
            System.out.println("Error: a non-int was entered " + e);
        }
        catch(NoSuchElementException e)
        {
            System.out.println("Error: no console input found " + e);
        }
        catch(IllegalStateException e)
        {
            System.out.println("Error: Scanner is closed " + e);
        }
                
        /* see if chipID is valid (HousePet with that chipID is in the HousePet list) */
        containsHousePet = hpArray.contains(tempHousePet);
        
        if(containsHousePet == false)
        {
            System.out.println("Invalid chipID entered: HousePet not found in the list");
        }
        
        /* if a HousePet is found with chipID matching the int provided by user, proceed
         * with prompts asking user to enter additional information, beginning with what
         * type of visit theyd like to add. regardless of the visit type, ask for date,
         * location, and doctor (generate a new visitID so they dont have to just make
         * one up on demand). then, if standard visit, prompt user to enter vaccines,
         * license, and other care; if urgent visit, prompt them to enter diagnosis and
         * treatment */
        else  //if(containsHousePet == true)
        {
            String userType = "";
            String visitType = "";
            boolean validTypeEntered = false;
            boolean validDateEntered = false;
            String visitID = "0"; /* dont want to have user come up with random String */
            String visitDate = "";
            String visitLocation = "";
            String visitDoctor = "";
            VetVisit newVisit;
            
            /* prompt user for new visit data */
            System.out.println("What type of VetVisit would you like to add? ");
            
            do
            {
                System.out.println("Please enter \"Urgent\" or \"Standard\".");
                userType = console.next();
                console.nextLine();
                
                if(userType.equalsIgnoreCase("standard") || userType.equalsIgnoreCase("urgent"))
                {
                    visitType = userType;
                    validTypeEntered = true;
                } 
            }
            while(validTypeEntered == false);
            
            /* prompt user for date of visit; dont let them off the hook until they enter
             * something semi-reasonable
             * the regex below only limits date entries to the format:
             *   "1 to 2 digits / 1 to 2 digits / 4 digits"
             * the first (set of) digit(s) is limited to: (0)0 to 19 -> 1 to 20 converted
             *   goal in reality would be to limit it to (0)0 to 11
             * the second (set of) digit(s) is limited to: (0)0 to 39
             *   goal in reality would be to limit it to (0)1 to 31
             * the last set of digits must be 4 in length and is limited to: 1000 to 2999
             *   goal in reality would be to limit it to something like 1990 to 2030
             */
            System.out.print("Please enter a date for the VetVisit; ");
            
            do
            {
                System.out.println("Please enter date in the format of mm/dd/yyyy");
                
                /* limit possible date entries somewhat */
                if(console.hasNext("[01]{0,1}[0-9]{1}/[0-3]{0,1}[0-9]{1}/[12]{1}[09]{1}[019]{1}[0-9]{1}"))
                {
                    visitDate = console.next("[01]{0,1}[0-9]{1}/[0-3]{0,1}[0-9]{1}/[12]{1}[09]{1}[019]{1}[0-9]{1}");
                    validDateEntered = true;
                }
                
                console.nextLine();
                
            }
            while(validDateEntered == false);
            
            System.out.print("Please enter the vet clinic location: ");
            visitLocation = console.nextLine();
            
            System.out.print("Please enter the name of the veterinarian: ");
            visitDoctor = console.nextLine();
            
            /* standard visit */
            if(visitType.equalsIgnoreCase("standard"))
            {
                String visitVaccines = "";
                int visitLicense = 0;
                String visitCare = "";
                
                System.out.print("Please enter vaccines given: ");
                visitVaccines = console.nextLine();
                
                System.out.print("Please enter the license #: ");
                
                try
                {
                    visitLicense = console.nextInt();
                    console.nextLine();
                }
                catch(InputMismatchException e)
                {
                    System.out.println("Error: a non-int was entered " + e);
                }
                catch(NoSuchElementException e)
                {
                    System.out.println("Error: no console input found " + e);
                }
                catch(IllegalStateException e)
                {
                    System.out.println("Error: Scanner is closed " + e);
                }
                                
                System.out.print("Please enter other care: ");
                visitCare = console.nextLine();               
                
                /* make newVisit a new VetVisitStandard with call to full constructor */
                newVisit = new VetVisitStandard(visitID, visitDate, visitLocation,
                        visitDoctor, visitVaccines, visitLicense, visitCare);
                
            }//end IF(visitType.equalsIgnoreCase("standard"))
            
            /* urgent visit */
            else  //if(visitType.equalsIgnoreCase("urgent"))
            {
                String visitDiagnosis = "";
                String visitTreatment = "";
                
                System.out.print("Please enter the diagnosis: ");
                visitDiagnosis = console.nextLine();
                
                System.out.print("Please enter the treatment: ");
                visitTreatment = console.nextLine();
                
                /* make newVisit a new VetVisitUrgent with call to full constructor */
                newVisit = new VetVisitUrgent(visitID, visitDate, visitLocation,
                        visitDoctor, visitDiagnosis, visitTreatment);
                
            }//end ELSE(IF(visitType.equalsIgnoreCase("urgent")))
            
            /* try to add the newVisit to the visitList for the HousePet */
            hasBeenAdded = hpArray.addVetVisit(userChipID, newVisit);
            
        }//end ELSE(IF(containsHousePet == true))
        
        /* determine message to display to user, depending on whether the VetVisit
         * has been added successfully */
        if(hasBeenAdded == false)
        {
            System.out.println("Unable to add VetVisit.");
        }
        
        else  //if(hasBeenAdded == true)
        {
            System.out.println();
            System.out.println("The VetVisit has been added (see data below).");
            ((HousePetListImpl) hpArray).displayHousePet(tempHousePet);
        }
            
        return;
        
    }//end menuAddVisit()
    
    /* *
     * Pre:  (HousePetList)
     *       called by Lab6Menu.main(), case 'l'.
     *       expects to receive instantiated HousePetList hpArray
     *       prompts user for chipID of HousePet for whom theyd like to remove a VetVisit;
     *       if chipID valid, prompts user for visitID and date for visit theyd like
     *       removed; see comments within method for additional explanations
     *       
     * Post: (nothing)
     *       if user enters valid data, a VetVisit is removed from the visitList of the
     *       given HousePet; visitCount is decremented from within
     *       VetVisitListImpl.remove(); removed VetVisit is appended to cancellations
     *       file 'cancel.txt', which is initiated by HousePetListImpl.removeVetVisit().
     *       play-by-play provided within method
     *       
     * note to self: when you prompt user for visit info, just have them enter visitID
     * and the visit date (there should be no duplicates, so visitID plus date should be
     * sufficient data in order to distinguish which visit the user wishes to be removed)
     * note that both pieces of data are necessary because two visits may have the same
     * date, and, while unlikely, it is also possible that two visits may have the same
     * visitID, especially if the source of that visitID is the user and not the
     * HousePet.generateVisitID() method called from default constructor
     */
    public static void menuRemoveVisit(HousePetList hpArray)
    {
        int userChipID = 0;
        HousePet tempHousePet = new HousePet(); /* new HP created with default chipID */
        @SuppressWarnings("resource")
        Scanner console = new Scanner(System.in);
        boolean containsHousePet = false;
        String visitID = "";
        String visitDate = "";
        boolean hasBeenRemoved = false;
        
        /* prompt for chipID */
        System.out.println("Please enter the chipID of the HousePet you would like to " +
            "cancel a visit for.");
        
        try 
        {
            userChipID = console.nextInt();
            tempHousePet.setChipId(userChipID);           
        }
        catch(InputMismatchException e)
        {
            System.out.println("Error: a non-int was entered " + e);
        }
        catch(NoSuchElementException e)
        {
            System.out.println("Error: no console input found " + e);
        }
        catch(IllegalStateException e)
        {
            System.out.println("Error: Scanner is closed " + e);
        }
        
        /* check if hpArray contains a HousePet with chipID matching that of tempHousePet */ 
        containsHousePet = hpArray.contains(tempHousePet);
        
        /* if HousePet with matching chipID not found in hpArray, display msg to user;
         * otherwise, display the HousePet's data so user can more easily decide which
         * visit they want to remove, then ask them for visitID and date for the visit
         * they want removed from visitList for that HousePet
         */
        if(containsHousePet == false)
        {
            System.out.println("Invalid chipID entered: no HousePet with chipID " +
                userChipID + " in the list");
            
        }//end IF(containsHousePet == false)
        
        else  //if(containsHousePet == true)
        {
            VetVisit visitToRemove = new VetVisitStandard(); //just assigning a type
            
            /* prompt user to specify which visit theyd like removed from the visitList */
            System.out.println("All attributes for the HousePet with chipID " + 
                    tempHousePet.getChipId() + " are shown below.");
            
            ((HousePetListImpl) hpArray).displayHousePet(tempHousePet);
            
            System.out.print("Please enter the visitID for the visit you wish to cancel: ");
            visitID = console.next();
            console.nextLine();
            
            System.out.print("Please enter the date for the visit you wish to cancel: ");
            visitDate = console.next();
            console.nextLine();
            
            System.out.println();
            
            visitToRemove.setVisitID(visitID);
            visitToRemove.setDate(visitDate);
            
            /* try to remove the VetVisit from HousePet's visitList; callee method will
             * see to it that visitCount is decremented if removal successful, and that
             * the info for the removed visit is written to to cancellations file
             * 'cancel.txt' */
            hasBeenRemoved = hpArray.removeVetVisit(tempHousePet.getChipId(), visitToRemove);
            
        }//end ELSE(IF(containsHousePet == true))
        
        /* determine message to display to user depending on whether visit has been
         * removed from visitList */
        if(hasBeenRemoved == true)  //hasBeenRemoved still false if HP not found in list
        {
            System.out.println("The visit has been added to the cancellations file " +
                    "\"cancel.txt\". The modified HousePet data is shown below.");
            
            ((HousePetListImpl) hpArray).displayHousePet(tempHousePet);
        }
        
        else  //if(hasBeenRemoved == false)
        {
            System.out.println("Unable to remove VetVisit.");
        }
            
        return;            
            
    }//end menuRemoveVisit()  
    
}//end of Lab6Menu.java