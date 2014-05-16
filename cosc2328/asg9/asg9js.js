/* *
 *[asg9js.js]
 * javascript functions to accompany asg9.php
 * Kristin Hamilton
 * cosc2328
 * created Sat, 07-Dec-2013
 */

/* declare global variables thePhoneNumber and theCustomerName */
var thePhoneNumber = "1234567890";
var theCustomerName = "KristinBHamilton";

/* *
 * [BELOW]  showButton(buttonName, buttonDivNumber)
 * Example call:  showButton("Start over", "3");
 *   Creates buttons;
 *   ButtonDivNumber parameter allows this function to be called with the number of
 *     the buttonDiv where button should be placed on the page.  this is convenient
 *     since the "Start over" button will be placed on two different page displays, and
 *     this function allows the buttons to be created easily and to be placed in
 *     different positions each time they appear.
 */
function showButton(buttonName, buttonDivNumber)
{
    var functionName = "";
	
    if(buttonName == "Submit")
        functionName = "submitPhoneForm();";
	
    else if(buttonName == "Start over")
        functionName = 'showPhoneForm("clear errorDiv");';
	
    else if(buttonName == "Clear order")
        functionName = "clearOrderForm();";
	
    else if(buttonName == "Submit order")
        functionName = "submitOrderForm();";
    	
    var buttonOut = "<button type='button' onclick='"+functionName+"'>"+buttonName+"</button>\n";
    var divName = "buttonDiv"+buttonDivNumber;
    document.getElementById(divName).innerHTML = buttonOut;
    
}//end of showButton()


/* *
 * [BELOW]  clearDiv(divName)
 * Example call:  clearDiv("mainMsgDiv");
 * Clears content from the element that is named as an argument in the method call
 */
function clearDiv(divName)
{
    document.getElementById(divName).innerHTML = "";

}//end of clearBodyDiv()


/* *
 * [BELOW]  createRequest()
 * Creates a request object; if request object is not created, returns false.
 * Function copied from set of methods for use with AJAX written by Dr. Baker
 */
function createRequest()
{
    var ajaxRequest;  //the xmlrequest object

    try {
        //Opera 8.0+, Firefox, Safari
        ajaxRequest = new XMLHttpRequest();
    }
    catch (e) {
        //Internet Explorer Browsers
        try {
            ajaxRequest = new ActiveXObject("Msxml2.XMLHTTP");
        }
        catch (e) {
            try {
                ajaxRequest = new ActiveXObject("Microsoft.XMLHTTP");
            }
            catch (e) {
                //Something went wrong
                alert("Your browser broke!");
                return false;                
            }             
        }        
    }
    
    return ajaxRequest;
    
}//end of createRequest()


/* *
 * [BELOW]  showPhoneForm(clearPreviousErrorMsg)
 * Example call 1:  showPhoneForm("clear errorDiv");  //removes previous errormsg
 * Ex2:  showPhoneForm("anything other than 'clear errorDiv'")  //allow errormsg display
 *   Reproduces the phone/customer name form first displayed by asg9.php.
 *   If user clicks "Start over" button at any time, clears buttons (if present) in
 *     buttonDiv1 and buttonDiv2, and displays submit button in buttonDiv3.
 *   Clears previous error message if showPhoneForm() is called via "Start over"
 *     button, but error message is still displayed if phone number and name aren't
 *     validated by checkPhone() and checkName(), respectively.
 */
function showPhoneForm(clearPreviousErrorMsg)
{	
    if(clearPreviousErrorMsg == "clear errorDiv") {
        clearDiv("errorDiv");
    }
    
    var mainMsgOut = "<h2>Welcome to Donuts-R-Us!</h2>\n";
    var bodyOut = "<p class='statement'>Please enter your name and 10-digit "+
        "phone number to retrieve your most recent order information</p>\n"+
        "<p class='statementcontent'>Name:\t<input type='text' "+
            "id='customerName' value='' size='20' maxlength='30' /></p>\n"+
        "<p class='statementcontent'>Phone number:\t<input type='text' "+
            "id='phoneNumber' value='' size='10' maxlength='10' /></p>\n";    
    
    document.getElementById("mainMsgDiv").innerHTML = mainMsgOut;
    document.getElementById("bodyDiv").innerHTML = bodyOut;
    showButton("Submit", "3");
    
    document.getElementById("customerName").value = "";
    document.getElementById("phoneNumber").value = "";
    clearDiv("buttonDiv1");
    clearDiv("buttonDiv2");

}//end of showPhoneForm()


/* *
 * [BELOW]  submitPhoneForm()
 * Collects values entered by user in the phoneNumber and customerName input boxes.
 * If values are validated by checkPhone() and checkName(), respectively,
 *   calls createRequest() to create request object, then opens a "GET" request using
 *   url variable that contains phoneNumber, customerName, and submitOrder.
 *     SubmitOrder = "false" here, which asg9Phone.php will recognize as meaning that
 *     the phone form has been submitted, so the db will be queried to see if
 *     phoneNumber appears in db, associated with previous order data, and that the
 *     order form should then be displayed, prepopulated with either default values
 *     (if phone# not found) or user's previous order information (if phone# is found).
 * Calls handleSubmitPhoneForm() to handle the response.
 * If phoneNumber and customerName do not check out to be valid, displays error msg
 *   at top of phone form, but does not repopulate any fields.
 */
function submitPhoneForm()
{
    var phoneNumber = document.getElementById("phoneNumber").value;
    var customerName = document.getElementById("customerName").value;
    var submitOrder = "false";
    
    phoneResult = checkPhone(phoneNumber);
    nameResult = customerName;
    
    if(phoneResult && nameResult)
    {
        var request = createRequest();
        thePhoneNumber = phoneNumber;
        theCustomerName = customerName;
        var url = "asg9Phone.php?phoneNumber="+phoneNumber+"&customerName="+customerName+
            "&submitOrder="+submitOrder;
    	
        request.open("GET", url, true);
        request.onreadystatechange = function() {
            handleSubmitPhoneForm(request);};
        request.send(null);
    	    	
    }//end of IF(phoneResult && nameResult)
    
    else  //if phoneNumber and customerName don't both check out as valid
    {
        var error = "<h3>Please enter your name and your 10-digit phone number, "+
        "excluding any hyphens, parenthesis, or other non-numeric characters</h3>\n";
    	
        document.getElementById("errorDiv").innerHTML = error;
        showPhoneForm("show error msg");
    	
    }//end of ELSE

}//end of submitPhoneForm()


/* *
 * [BELOW]  checkPhone(thePhone)
 * Checks phoneNumber:  checks that length = 10 characters, and checks that those
 *   10 characters are numerical;  if not valid, returns false, triggering error message
 *   display on phone form
 */
function checkPhone(thePhone)
{
    var phoneCheckResult = false;
    
    if(thePhone.length != 10) {
        return phoneCheckResult;  //= false
    }
    
    phoneCheckResult = true;
    
    for(i = 0; i < thePhone.length; i++)
    {
        var phoneChar = thePhone.charAt(i);
    	
        if(phoneChar < '0' || phoneChar > '9')
        {
            phoneCheckResult = false;
            break;
    		
        }//end of IF
    	
    }//end of FOR loop
    
    return phoneCheckResult;

}//end of checkPhone()


/* *
 * [BELOW]  checkName(theName)
 * Right now, just checks the length of customerName:  checks that it consists of
 *   at least one character, and is no longer than 30 characters.
 * If customerName violates these limits, returns false, causing an error message
 *   to be displayed at the top of phone form;  otherwise, returns true.
 */
function checkName(theName)
{
    var nameCheckResult = false;
    
    if(theName.length < 1 || theName.length > 30)
    return nameCheckResult;
    
    nameCheckResult = true;
    /*
    for(i = 0; i < theName.length; i++)
    {
        if(theName.charAt(i) >= '0' || theName.charAt(i) <= '9')
        {
            nameCheckResult = false;
            break;
    		
    }//end of IF
    	
    }//end of FOR loop
	*/
    return nameCheckResult;
    
}//end of checkName()


/* *
 * [BELOW]  handleSubmitPhoneForm(request)
 * Handles the response from submitPhoneForm():
 * If response is received, displays order form and instructions which are received
 *   as response text.
 * Displays main message heading at top of page, and three buttons at bottom of page
 *   ("Start over", "Clear form", and "Submit order").
 * Clears any previous error message, if present.
 */
function handleSubmitPhoneForm(request)
{
    if(request.readyState == 4)
    {
        var mainMsgOut = "<h2>Good morning, "+theCustomerName+"!\n";
        
        document.getElementById("mainMsgDiv").innerHTML= mainMsgOut;
        document.getElementById("bodyDiv").innerHTML= request.responseText;	    
        showButton("Start over", "1");
        showButton("Clear order", "2");
        showButton("Submit order", "3");

        clearDiv("errorDiv");
        
    }//end of IF

}//end of handleSubmitPhoneForm()


/* *
 * [BELOW]  submitOrderForm()
 * Collects order values entered by user for drink choice and qty of donuts:
 * If values are validated by checkOrderForm(), calls createRequest() to create
 *   request object, then opens a "GET" request using url variable that contains
 *   phoneNumber, customerName, submitOrder, drinkID, numJelly, numGlazed, and
 *   numChocolate.
 *     SubmitOrder = "true" here, which asg9Phone.php will recognize as meaning that
 *       the order form has been submitted, so "insert" query (if first-time customer)
 *       or "update" query (if returning customer) will be performed, and order
 *       confirmation page should then be displayed.
 * Calls handleSubmitOrderForm() to handle the response.
 * If donut quantities entered do not check out to be valid, displays error msg at top of
 *   order form, but does not repopulate any fields.
 */
function submitOrderForm()
{
    var drinkID = document.getElementById("drinkID").value;
    var numJelly = document.getElementById("numJelly").value;
    var numGlazed = document.getElementById("numGlazed").value;
    var numChocolate = document.getElementById("numChocolate").value;
    var submitOrder = "true";
	
    var drinkResult = drinkID;
    var jellyResult = checkOrderForm(numJelly);
    var glazedResult = checkOrderForm(numGlazed);
    var chocolateResult = checkOrderForm(numChocolate);
	
    if(drinkResult && jellyResult && glazedResult && chocolateResult)
    {
        var request = createRequest();
        var url = "asg9Phone.php?phoneNumber="+thePhoneNumber+"&customerName="+theCustomerName+
        "&drinkID="+drinkID+"&numJelly="+numJelly+"&numGlazed="+numGlazed+
        "&numChocolate="+numChocolate+"&submitOrder="+submitOrder;
		
        request.open("GET", url, true);
        request.onreadystatechange = function() {
            handleSubmitOrderForm(request);};
			
        request.send(null);
		
    }//end of IF(drinkResult && jellyResult && glazedResult && chocolateResult)
  
    else  //if all the vars don't check out as valid
    {
        var error = "<h3>Please enter a quantity (from 0 to 99, inclusive) "+
            "in each of the blanks</h3>\n";
        document.getElementById("errorDiv").innerHTML = error;
		
        submitPhoneForm();  //questionable method call here!
		
    }//end of ELSE
		
}//end of submitOrderForm()


/* *
 * [BELOW]  checkOrderForm(donutQty)
 * Checks characters entered by user in text box for quantity of donuts:
 *   Checks that none are blank, checks that all are between 0 and 99, inclusive, and
 *   checks that only numbers were entered.
 * If donutQty invalid, returns false;  otherwise, returns true.
 */
function checkOrderForm(donutQty)
{
    var checkResult = false;
    
    if(donutQty.length < 1 || donutQty.length > 2) {
        return checkResult;  //= false
    }
    
    checkResult = true;
    
    for(i = 0; i < donutQty.length; i++) 
    {
        var charDigit = donutQty.charAt(i);
    	
        if(charDigit < '0' || charDigit > '9')
        {
            checkResult = false;
            break;
    		
        }//end of IF
    	
    }//end of FOR loop
    
    return checkResult;

}//end of checkOrderForm()


/* *
 * [BELOW]  clearOrderForm()
 * Clears order form:  sets drink choice to "No drink", and sets all donut qtys to null;
 * Also clears errorDiv of any previous error message
 */
function clearOrderForm()
{
    document.getElementById("drinkID").value = "5";
    document.getElementById("numJelly").value = "";
    document.getElementById("numGlazed").value = "";
    document.getElementById("numChocolate").value = "";
    
	clearDiv("errorDiv");

}//end of clearOrderForm()


/* *
 * [BELOW]  handleSubmitOrderForm(request)
 * Handles response from submitOrderForm(), if received.
 * Displays response text, which will be order confirmation information, and will also
 *   display approximate wait time.
 * Also displays main heading, and a "Start over" button, and clears all other buttons
 *   as well as any previous error message.
 */
function handleSubmitOrderForm(request)
{
    var mainMsgOut = "<h2>Order Confirmation</h2>\n";
	
    document.getElementById("mainMsgDiv").innerHTML = mainMsgOut;
    document.getElementById("bodyDiv").innerHTML = request.responseText;
    showButton("Start over", "3");
    
    clearDiv("errorDiv");
    clearDiv("buttonDiv1");
    clearDiv("buttonDiv2");
    
}//end of handleSubmitOrderForm()