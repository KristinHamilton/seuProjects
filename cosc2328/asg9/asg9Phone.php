<?php
/* *
 * [asg9Phone.php]
 * secondary php script for asg9
 * receives "$_GET" data from asg9js.js
 * 
 * Kristin Hamilton
 * cosc2328
 * (re)created Fri, 13-Dec-2013
 * originally created Mon, 09-Dec-2013
 */

require "../db/mydb.php";

$phoneNumber = htmlentities($_GET['phoneNumber']);
$currentCustomerName = htmlentities($_GET['customerName']);
$submitOrder = htmlentities($_GET['submitOrder']);

$previousDrinkID = "";
$previousNumJelly = "";
$previousNumGlazed = "";
$previousNumChocolate = "";

/* *
 * to show the users previous order information to them when they first view order form,
 * query database to see if the phone number they entered is present in db
 */
$db = adodbConnect();
$donutQuery = "SELECT drinkID, numJelly, numGlazed, numChocolate FROM donutOrder ".
    "WHERE phoneNumber=". $phoneNumber;
$donutResult = $db->EXECUTE($donutQuery) or die("Error selecting previous order information");

$rowCount = $donutResult->RowCount();  /* $rowCount = 0 or 1 */

/* *
 * if $phoneNumber isnt in database, there is no previous order information to be
 * found, so set previous drinkID to "5 (No drinK)", and leave the donut quantities
 * null, since drink choice will be collected via dropdown menu, and donut quantities
 * will be collected via text box input
 */
if($rowCount == 0)
{
    $previousDrinkID = "5";
}

/* *
 * if $rowCount != 0, then previous order info is present in db.
 * collect info, storing in variables
 */
else  //if $rowCount != 0
{
    while($donutRow = $donutResult->FetchRow())
    {
        $previousDrinkID = $donutRow['drinkID'];
        $previousNumJelly = $donutRow['numJelly'];
        $previousNumGlazed = $donutRow['numGlazed'];
        $previousNumChocolate = $donutRow['numChocolate'];
        
    }//end of WHILE($donutRow)
	
}//end of ELSE (if $rowCount != 0)

/* *
 * if $submitOrder = "false", the phone form has been submitted, so display the
 * order form
 */
if($submitOrder == "false")
{
    showOrderForm($phoneNumber, $currentCustomerName, $previousDrinkID, $previousNumJelly, $previousNumGlazed, $previousNumChocolate);
}

/* *
 * if $submitOrder = "true", the order form has been submitted, so display the
 * order confirmation page
 */
else  //if $submitOrder == "true"
{
    $currentDrinkID = htmlentities($_GET['drinkID']);
    $currentNumJelly = htmlentities($_GET['numJelly']);
    $currentNumGlazed = htmlentities($_GET['numGlazed']);
    $currentNumChocolate = htmlentities($_GET['numChocolate']);
    
    /* *
     * if $rowCount = 0, the phone number entered by user was not found in database,
     * so add $phoneNumber, $currentCustomerName, and order info to db via an
     * "insert" query
     */
    if($rowCount == 0)  //$rowCount, from $donutQuery at beginning of script
    {
        $insertQuery = "INSERT INTO donutOrder(phoneNumber, customerName, drinkID, numJelly, numGlazed, numChocolate) ".
            "VALUES ('". $phoneNumber ."', '". $currentCustomerName ."', '". $currentDrinkID ."', ".
            "'". $currentNumJelly ."', '". $currentNumGlazed ."', '". $currentNumChocolate ."')";
        $db->EXECUTE($insertQuery) or die("Error inserting current order information into database");
        
    }//end of IF($rowCount == 0)
    
    /* *
     * if previous order info is present in db ($rowCount == 1), compare each previous
     * order value to the current order value;  if different, append the field and
     * value to all other non-matching current fields/values to be included in "update"
     * query, therey changing any values necessary to match the db record for a given
     * phone number to the current order info for that phone number
     */
    else  //if $rowCount != 0
    {
        $numFields = 0;
        $comma = "";
        $fields = "";
    	 
        $a = "$currentCustomerName != $previousCustomerName";
        $b = "$currentDrinkID != $previousDrinkID";
        $c = "$currentNumJelly != $previousNumJelly";
        $d = "$currentNumGlazed != $previousNumGlazed";
        $e = "$currentNumChocolate != $previousNumChocolate";
    	 
        if($a)
            $numFields++;
        if($b)
            $numFields++;
        if($c)
            $numFields++;
        if($d)
            $numFields++;
        if($e)
            $numFields++;
    	 
    	if($numFields > 1)
    		$comma = ", ";
    	 
        if($a) {
    	    $fields .= "customerName='". $currentCustomerName ."'";
        }
    	 
        if($b) {
            $fields .= $comma ."drinkID='". $currentDrinkID ."'";
        }
    	 
        if($c) {
            $fields .= $comma ."numJelly='". $currentNumJelly ."'";
        }
    	 
        if($d) {
            $fields .= $comma ."numGlazed='". $currentNumGlazed ."'";
        }
    	 
        if($e) {
            $fields .= $comma ."numChocolate='". $currentNumChocolate ."'";
        }
    	
        /* perform the "update" query */
        if($numFields > 0)
        {
            $updateQuery = "UPDATE donutOrder SET ". $fields ." WHERE phoneNumber=". $phoneNumber;
            $updateResult = $db->EXECUTE($updateQuery) or die("Error updating order information");
    	
        }//end IF($numFields > 0)
        
    }//end of ELSE (if $rowCount != 0)

    /* *
     * to show the drink description rather than the drinkID on the order confirmation
     * page, query drinkChoice table to find the description matching the drinkID
     */
    $drinkQuery = "SELECT drinkDesc FROM drinkChoice WHERE drinkID=". $currentDrinkID;
    $drinkResult = $db->EXECUTE($drinkQuery) or die("Error selecting current drink description");
    
    /* set $currentDrinkDesc and call showConfirmationPage() */
    while($drinkRow = $drinkResult->FetchRow())
    {
    	$currentDrinkDesc = $drinkRow['drinkDesc'];
    	showConfirmationPage($phoneNumber, $currentCustomerName, $currentDrinkID, $currentDrinkDesc, $currentNumJelly, $currentNumGlazed, $currentNumChocolate);
    
    }//end of WHILE($drinkRow2)
    
}//end of ELSE (if $submitOrder == "true")


/* * * * * * * * * *  FUNCTIONS BELOW  * * * * * * * * * */


/* *
 * [BELOW]  showOrderForm()
 * ` displays order form, prepopulating it with previous order values, if present
 * ` if previous order info does not exist, "prepopuplate" form wiht default values:
 *   drinkID = 5 (No drink);  all donut quantities = null
 */
function showOrderForm($phoneNumber, $currentCustomerName, $previousDrinkID, $previousNumJelly="", $previousNumGlazed="", $previousNumChocolate="")
{
    $drinks = showDrinkChoices($previousDrinkID);
    $jelly = showDonutChoices("numJelly", $previousNumJelly);
    $glazed = showDonutChoices("numGlazed", $previousNumGlazed);
    $chocolate = showDonutChoices("numChocolate", $previousNumChocolate);
	
    $output = "<p class='statement'>Modify your previous order, then click Submit order.  ".
    "If you'd like to clear the form, click Clear;  if you would ".
    "like to start over with a different phone number, click Start over</p>\n".
    "<p>Drink choice:\t". $drinks ."</p>\n".
    $jelly ."\n".
    $glazed ."\n".
    $chocolate ."\n";

    print $output;
    
}//end of showOrderForm()

/* *
 * [BELOW]  showDrinkChoices($previousDrinkID)
 * ` create drop-down menu of drink choices
 * ` drinkID, drinkDesc: 1, Coffee; 2, Iced tea; 3, Hot tea; 4, Orange juice; 5, No drink
 * ` populate menu via query to drinkChoice table in database
 * ` if $previousDrinkID = present drinkID in drinkChoice table, set that drinkID to
 *   selected
 */
function showDrinkChoices($previousDrinkID)
{
    $db = adodbConnect();
    $drinkQuery2 = "SELECT * FROM drinkChoice";
    $drinkResult2 = $db->EXECUTE($drinkQuery2) or die("Error selecting drink choices");
    
    //$selected = "";
    $drinks = "<select id='drinkID' name='drinkID'>\n";
    
    while($drinkRow2 = $drinkResult2->FetchRow())
    {		
        if($previousDrinkID == $drinkRow2['drinkID'])
        {
            //$selected = " selected='selected'";
            $drinks .= "<option value='". $drinkRow2['drinkID'] ."' selected='selected'>".
                $drinkRow2['drinkDesc'] .
                "</option>\n";
        }

        else  //if($previousDrinkID != $drinkRow2['drinkID'])
        {
            $drinks .= "<option value='". $drinkRow2['drinkID'] ."'>".
                $drinkRow2['drinkDesc'] .
                "</option>\n";
        }
		
    }//end of WHILE($drinkRow3)
    
    $drinks .= "</select>\n";
	
    return $drinks;
	     
}//end of showDrinkChoices()


/* *
 * [BELOW]  showDonutChoices($donutField, $previousNumDonuts)
 * example call:  showDonutChoices("numJelly", $previousNumJelly)
 * ` creates text input fields where user will enter quantity of donuts they wish
 *   to order
 * ` prepopulate form with previous order quantities, if present;  otherwise, donut
 *   quantities will be null
 */
function showDonutChoices($donutField, $previousNumDonuts)
{
    $label = "";
	
    if($donutField == "numJelly") {
    	$label = "# Jelly donuts:";
    }
	
    else if($donutField == "numGlazed") {
    	$label = "# Glazed donuts:";
    }
	
    else if($donutField = "numChocolate") {
    	$label = "# Chocolate donuts:";
    }
    
    $donuts = "<p>". $label ."\t<input type='text' id='". $donutField ."' value='". $previousNumDonuts ."' /></p>\n";
	
    return $donuts;
    
}//end of showDonutChoices()


/* *
 * [BELOW]  showConfirmationPage()
 * ` creates order confirmation page, where users order info is displayed
 * ` also reports approximate wait time, based on the number fo donuts ordered,
 *   and whether a drink was selected
 *   
 * ` notes on approximate wait time:
 *   ` if total number donuts is less than 12, then the approximate wait time will
 *     be reported as zero to zero minutes, which doesnt make logical sense
 *   ` solution to this problem used below:  if min wait time = 0 but donuts were
 *     ordered (non-zero qty) and a drink was ordered, set min time to 1;
 *     similarly, set max wait time to 2 in the case described
 *   ` additionally, if min and max wait times are equal, add 1 to max wait time,
 *     so that approximate wait time is reported as a range between two different
 *     numbers
 *   ` in the event that the # donuts ordered = 0 and "No drink" was ordered,
 *     wait time will be reported as 0 to 0 minutes, which accurately corresponds
 *     to the order information
 */
function showConfirmationPage($phoneNumber, $currentCustomerName, $currentDrinkID, $currentDrinkDesc, $currentNumJelly, $currentNumGlazed, $currentNumChocolate)
{
    $totalNumDonuts = $currentNumJelly + $currentNumGlazed + $currentNumChocolate;
    
    if($currentDrinkID != "5") {
        $totalNumDonuts += 1;
    }
    	
    $dozens = $totalNumDonuts / 12;
    $waitTimeMin = floor($dozens * 3);
    $waitTimeMax = ceil($dozens * 5);
    
    if($waitTimeMin == 0 && $totalNumDonuts != 0 && $currentDrinkID != "5")
        $waitTimeMin = 1;

    if($waitTimeMax == 0 && $totalNumDonuts != 0 && $currentDrinkID != "5")
        $waitTimeMax = 2;
    
    if($waitTimeMin == $waitTimeMax && $totalNumDonuts !=0 && $currentDrinkID != "5")
        $waitTimeMax += 1;
	
    $confirmationOutput = "<p>A copy of your order is shown below:</p>\n".
    "<hr />\n".
    "<p>Customer name:\t". $currentCustomerName ."</p>\n".
    "<p>Phone number:\t". $phoneNumber ."</p>\n".
    "<p>Drink choice:\t". $currentDrinkDesc ."</p>\n".
    "<p># Jelly donuts:\t". $currentNumJelly ."</p>\n".
    "<p># Glazed donuts:\t". $currentNumGlazed ."</p>\n".
    "<p># Chocolate donuts:\t". $currentNumChocolate ."</p>\n".
    "<hr />\n".
    "<p>Your order will be ready for you to pick up in approximately ". $waitTimeMin ." to ". $waitTimeMax ." minutes.</p>\n".
    "<p class='statement' style='font-size:1.2em;'>Thank you for choosing Donuts-R-Us!</p>\n";

    print $confirmationOutput;
	
}//end of showConfirmationPage()

?>