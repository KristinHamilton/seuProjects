<?php
/* *
 * [asg9.php]
 * main script for asg9
 * creates the html for the page structure, including a button that calls
 * js function submitPhoneForm()
 * 
 * Kristin Hamilton
 * cosc2328
 * created Sat, 07-Dec-2013
 *  
 *  asg9:  MySQL db ("update" queries); JavaScript.
 *  Donuts-R-Us, online order forms and using js with AJAX to change page content
 *  without reloading page (asynchronous, the "A" in AJAX)
 */
//echo phpinfo();
ini_set ("display_errors", "1");
error_reporting(E_ALL);

require_once "../asg8/asg8funx.php";
printDocHeadingJS("asg9.css", "Donuts-R-Us", "asg9.js");

echo "<body>\n
		
<div class='heading'>
	<h1>Donuts-R-Us</h1>
</div>\n
		
<div class='back2CShome'>
    <a href='http://kristin.create.stedwards.edu/cosc2328/index.php'>Back to CS Home</a>
</div>\n
		
<!--  start content  -->\n
<div class='content'>\n
		
	<div id='mainMsgDiv' class='headingcontent mainMsgDiv'>
		<h2>Welcome to Donuts-R-Us!</h2>
	</div>\n
		
	<div id='errorDiv' class='errortext errorDiv'>
	</div>\n
		
	<div id='bodyDiv' class='bodyDiv'>\n
		<p class='statement'>\n
			Please enter your name and your 10-digit phone number to retrieve 
			your most recent order information\n
		</p>\n
		<p>Name:\t\n
			<input type='text' id='customerName' size='20' maxlength='30' />\n
		</p>\n
		<p>Phone number:\t\n
			<input type='text' id='phoneNumber' size='10' maxlength='10' />\n
		</p>\n
	</div>\n
		
	<div id='buttonsDiv'>\n
		
		<div id='buttonDiv1' class='buttonDiv buttonDiv1'>\n
		</div>\n
		
	  	<div id='buttonDiv2' class='buttonDiv buttonDiv2'>\n
	  	</div>\n
		
	  	<div id='buttonDiv3' class='buttonDiv buttonDiv3'>\n
	    	<button onclick='submitPhoneForm();' type='button'>Submit</button>\n
	  	</div>\n
		
	</div>\n
		
</div>\n
<!--  end content  -->\n";

printDocFooter();

echo
"</body>\n
</html>\n";

?>