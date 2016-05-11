<?php
/* *
 * [asg9Phone.php]
 * secondary php script for asg9
 * receives "$_GET" data from asg9js.js
 * 
 * Kristin Hamilton
 * cosc2328
 * created Mon, 09-Dec-2013
 */
require_once "../db/mydb.php";

$phoneNumber = htmlentities($_GET['phoneNumber']);
$currentCustomerName = htmlentities($_GET['customerName']);
$submitOrder = htmlentities($_GET['submitOrder']);

$dbName = "kristinc_DonutDB";
$query = "";
$result = "";
$previousCustomerName = "";
$previousDrinkID = "";
$previousNumJelly = "";
$previousNumGlazed = "";
$previousNumChocolate = "";

# prepopulate order form if possible
$db = dbConnect($dbName);
$query = "SELECT customerName, drinkID, numJelly, numGlazed, numChocolate FROM donutOrder ".
    "WHERE phoneNumber = '$phoneNumber';";
$result = mysqli_query($db, $query) or die("Error selecting previous order information");
$rowCount = mysqli_num_rows($result);  # $rowCount = 0 or 1

if($rowCount == 0)
{
    $previousDrinkID = "5";  # "No drink"
}
else
{
    while($row = mysqli_fetch_assoc($result))
    {
    	$previousCustomerName = $row['customerName'];
        $previousDrinkID = $row['drinkID'];
        $previousNumJelly = $row['numJelly'];
        $previousNumGlazed = $row['numGlazed'];
        $previousNumChocolate = $row['numChocolate'];   
    }	
}

if($submitOrder == "false")
{
	# phone form has been submitted, so display the order form
    showOrderForm($phoneNumber, $currentCustomerName, $previousDrinkID, 
    	$previousNumJelly, $previousNumGlazed, $previousNumChocolate);
}
else
{
	# order form has been submitted, so display the order confirmation page
	
    $currentDrinkID = htmlentities($_GET['drinkID']);
    $currentNumJelly = htmlentities($_GET['numJelly']);
    $currentNumGlazed = htmlentities($_GET['numGlazed']);
    $currentNumChocolate = htmlentities($_GET['numChocolate']);
    
     # if phone number entered by user not found in db, phoneNumber, currentCustomerName, 
     # and order info to db
    if($rowCount == 0)
    {
        $query = "INSERT INTO donutOrder(phoneNumber, customerName, drinkID, ".
        	"numJelly, numGlazed, numChocolate) VALUES ".
        	"('$phoneNumber', '$currentCustomerName', $currentDrinkID, ".
            "$currentNumJelly, $currentNumGlazed, $currentNumChocolate)";
        mysqli_query($db, $query) or die("Error inserting order information into db");   
    }
    else
    {
    	# if previous order info is present in db compare each previous
    	# order value to the current order value;  if different, append the field and
    	# value to all other non-matching current fields/values to be included in "update"
    	# query, thereby changing any values necessary to match the db record for a given
    	# phone number to the current order info for that phone number
    	
        $numFields = 0;
        $comma = "";
        $fields = "";
    	 
        $a = "$currentCustomerName != $previousCustomerName";
        $b = "$currentDrinkID != $previousDrinkID";
        $c = "$currentNumJelly != $previousNumJelly";
        $d = "$currentNumGlazed != $previousNumGlazed";
        $e = "$currentNumChocolate != $previousNumChocolate";
    	 
        if($a){ $numFields++; }
        if($b){ $numFields++; }
        if($c){ $numFields++; }
        if($d){ $numFields++; }
        if($e){ $numFields++; }
    	if($numFields > 1){ $comma = ","; }
    	 
        if($a){ $fields .= "customerName= '$currentCustomerName'"; }
        if($b){ $fields .= "$comma drinkID = $currentDrinkID"; }
        if($c){ $fields .= "$comma numJelly = $currentNumJelly"; }
        if($d){ $fields .= "$comma numGlazed = $currentNumGlazed"; }
        if($e){ $fields .= "$comma numChocolate = $currentNumChocolate"; }
    	
        if($numFields > 0)
        {
            $query = "UPDATE donutOrder SET $fields WHERE phoneNumber = '$phoneNumber';";
            $result = mysqli_query($db, $query) or die("Error updating order information");
        }
    }

    $query = "SELECT drinkDesc FROM drinkChoice WHERE drinkID = $currentDrinkID;";
    $result = mysqli_query($db, $query) or die("Error selecting current drink description");
    
    while($row = mysqli_fetch_assoc($result))
    {
    	$currentDrinkDesc = $row['drinkDesc'];
    	showConfirmationPage($phoneNumber, $currentCustomerName, $currentDrinkID, 
    		$currentDrinkDesc, $currentNumJelly, $currentNumGlazed, $currentNumChocolate);
    }
    
    mysqli_close($db);
}

/* *
 * [BELOW]  showOrderForm()
 *  displays order form, prepopulating it with previous order values, if present
 *  if previous order info does not exist, "prepopuplate" form with default values:
 *   drinkID = 5 (No drink);  all donut quantities => null
 */
function showOrderForm($phoneNumber, $currentCustomerName, $previousDrinkID, 
			$previousNumJelly = "", $previousNumGlazed = "", $previousNumChocolate = "")
{
    $drinks = showDrinkChoices($previousDrinkID);
    $jelly = showDonutChoices("numJelly", $previousNumJelly);
    $glazed = showDonutChoices("numGlazed", $previousNumGlazed);
    $chocolate = showDonutChoices("numChocolate", $previousNumChocolate);
	
    $output = "<p class='statement'>Modify your previous order, then click Submit ".
    	"order.  If you'd like to clear the form, click Clear;  if you would like to ".
    	"start over with a different phone number, click Start over</p>\n".
	    "<p>Drink choice:\t$drinks</p>\n$jelly\n$glazed\n$chocolate\n";

    echo $output;   
}

/* *
 * [BELOW]  showDrinkChoices($previousDrinkID)
 *  create drop-down menu of drink choices
 */
function showDrinkChoices($previousDrinkID)
{
	$dbName = "kristinc_DonutDB";
	$drinkID = "";
	$drinkDesc = "";
	
    $db = dbConnect($dbName);
    $query = "SELECT * FROM drinkChoice;";
    $result = mysqli_query($db, $query) or die("Error selecting drink choices");
    
    $drinks = "<select id='drinkID' name='drinkID'>\n";
    while($row = mysqli_fetch_assoc($result))
    {		
    	$drinkID = $row['drinkID'];
    	$drinkDesc = $row['drinkDesc'];
    	
    	$drinks .= "<option value='$drinkID'";
        if($previousDrinkID == $drinkID){ $drinks .= " selected='selected'"; }
        $drinks .= ">$drinkDesc</option>\n";
    }    
    $drinks .= "</select>\n";
    
    mysqli_close($db);
    return $drinks;	     
}

/* *
 * [BELOW]  showDonutChoices($donutField, $previousNumDonuts)
 * example call:  showDonutChoices("numJelly", $previousNumJelly)
 *  creates text input fields where user will enter qty of donuts they wish to order
 *  prepopulate form with previous order qtys, if present;  otherwise, donut qtys null
 */
function showDonutChoices($donutField, $previousNumDonuts)
{
	$donuts = "";
    $label = "";
	
    if($donutField == "numJelly")
    {
    	$label = "# Jelly donuts:"; 
    }
    else if($donutField == "numGlazed")
    { 
    	$label = "# Glazed donuts:"; 
    }
    else
    {
    	if($donutField = "numChocolate")
    	{
    		$label = "# Chocolate donuts:";
    	}
    }
    
    $donuts = "<p>$label\t".
    	"<input type='text' id='$donutField' value='$previousNumDonuts' /></p>\n";
    return $donuts;
}

/* *
 * [BELOW]  showConfirmationPage()
 *  creates order confirmation page, where users order info is displayed
 *  also reports approx wait time, based on #donuts ordered, and whether drink was selected
 *   
 *  notes on approx wait time:
 *    if total #donuts < 12: approx wait time = 0 to 0 minutes...
 *    solution to this problem used below:  if min wait time = 0 but donuts were
 *     ordered (non-zero qty) and a drink was ordered, set min time to 1;
 *     similarly, set max wait time to 2 in the case described
 *    additionally, if min and max wait times are equal, add 1 to max wait time,
 *     so that approx wait time is reported as a range between two diff #s
 *    if #donuts == 0 && "No drink" was ordered,
 *     wait time will be reported as 0 to 0 minutes, which accurately corresponds
 *     to the order info
 */
function showConfirmationPage($phoneNumber, $currentCustomerName, $currentDrinkID, 
			$currentDrinkDesc, $currentNumJelly, $currentNumGlazed, $currentNumChocolate)
{
    $totalNumDonuts = $currentNumJelly + $currentNumGlazed + $currentNumChocolate;
    
    if($currentDrinkID != "5"){ $totalNumDonuts += 1; }
    	
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
    "<p>Customer name:\t$currentCustomerName</p>\n".
    "<p>Phone number:\t$phoneNumber</p>\n".
    "<p>Drink choice:\t$currentDrinkDesc</p>\n".
    "<p># Jelly donuts:\t$currentNumJelly</p>\n".
    "<p># Glazed donuts:\t$currentNumGlazed</p>\n".
    "<p># Chocolate donuts:\t$currentNumChocolate</p>\n".
    "<hr />\n".
    "<p>Your order will be ready for you to pick up in approximately $waitTimeMin to".
    " $waitTimeMax minutes.</p>\n".
    "<p class='statement' style='font-size:1.2em;'>Thank you for choosing Donuts-R-Us!".
    "</p>\n";

    echo $confirmationOutput;
}

?>