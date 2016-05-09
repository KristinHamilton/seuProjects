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
//ini_set ("display_errors", "1");
//error_reporting(E_ALL);

require "asg8funx.php";
#require for header and footer

printDocHeadingJS("asg9.css", "Donuts-R-Us", "asg9.js");
#prints document header with text/javascript included in meta tag

print "<body>\n";

print "<div class='heading'><h1>Donuts-R-Us</h1></div>\n";

print "<div class='back2CShome'><a href='http://cs.stedwards.edu/~khamilt2/cosc2328'>" .
"Back to CS Home</a></div>\n";

print
"<!--  start of content  -->\n" .
"<div class='content'>\n";


print
 /* mainMsgDiv */
"<div id='mainMsgDiv' class='headingcontent' style='width:90%;margin:1.5%;padding:1% 5% 1% 5%;'>".
"  <h2>Welcome to Donuts-R-Us!</h2>".
 /* close mainMsgDiv */
"</div>\n";


print
 /* errorDiv */
"<div id='errorDiv' class='errortext' style='width:90%;margin:1.5%;padding:0 5% 0 5%;'>".
 /* close errorDiv */
"</div>\n";


print
 /* bodyDiv */
"<!--  start of bodyDiv  -->\n".
"<div id='bodyDiv' style='width:90%;margin:0 1.5%;padding:1% 5% 1% 5%;'>\n";

print
"<p class='statement'>Please enter your name and your 10-digit phone number to retrieve your most recent order information</p>\n".

"<p>Name:\t<input type='text' id='customerName' size='20' maxlength='30' /></p>\n".

"<p>Phone number:\t<input type='text' id='phoneNumber' size='10' maxlength='10' /></p>\n";

print
 /* close bodyDiv */
"</div>\n".
"<!--  end of bodyDiv  -->\n";


print
 /* buttonsDiv */
"<!--  start of buttonsDiv  -->\n".
"<div id='buttonsDiv'>\n".

   /* buttonDiv1 */
"  <div id='buttonDiv1' style='width:25%;margin:1.5%;padding:2% 2% 5% 5%;float:left;'>\n".
   /* close buttonDiv1 */
"  </div>\n".

   /* buttonDiv2 */
"  <div id='buttonDiv2' style='width:25%;margin:1.5% auto;padding:2% 2% 5% 5%;float:left;'>\n".
   /* close buttonDiv2*/
"  </div>\n".

   /* buttonDiv3 */
"  <div id='buttonDiv3' style='width:25%;margin:1.5% 0 1.5% 1.5%;padding:2% 0 5% 5%;float:left;'>\n".
"    <button onclick='submitPhoneForm();' type='button'>Submit</button>\n".
   /* close buttonDiv3 */
"  </div>\n".

 /* close buttonsDiv */
"</div>\n".
"<!--  end of buttonsDiv  -->\n";


print
"</div>\n" ;
"<!--  end of content  -->\n";

printDocFooter();
#prints document footer

print
"</body>\n".
"</html>\n";

?>