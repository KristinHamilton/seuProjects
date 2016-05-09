<?php
session_set_cookie_params(300);  //session length until expiration = 300 seconds
session_start();

/* *
 * [asg8.php]
 * main script/page logic for asg8
 * 
 * Kristin Hamilton
 * cosc2328
 * asg8.php created: Sat, 07-Nov-2013
 */

/* *
 * Summary for asg8.php:
 * using $_SESSIONS for Login; add remove buttons next to each image.
 * Functions created for asg7 provide the following:
 * STORES IMAGE BINARY IN MySQL DATABASE
 * DISPLAYS DATABASE CONTENTS:
 *   Shows page with image thumbnails, along with image description, categories, and
 *   remove button.
 *   When thumbnail is clicked, full size image opens up in browser window
 * TABLES USED IN ASG7/8:
 *    table 1 "image."  columns: imgID (int, auto_inc), fullImg (longBLOB),
 *      thumbImg (longBLOB), imgDesc (varchar)
 *    table 2 "category."  columns: catID (int), catDesc (varchar)
 *    table 3 "image2Category." BRIDGE TABLE.  columns: imgID, catID
 */

//echo phpinfo();
//ini_set ("display_errors", "1");
//error_reporting(E_ALL);

require "asg8funx.php";
    /*
     * [asg7funx.php fns: Table of Contents]
     * printDocHeadingJS
     * showUploadForm
     * checkUploadForm
     * addImg2db
     * makeThumbnail
     * showAllImages
     * showCategoryChoiceForm
     * showCategoryImages
     * showSubmitAddAnotherImg
     * showSubmitReturn2db
     * printDocFooter
     */

printDocHeadingJS("asg8.css", "Image Database with Login", "asg8.js");
#prints document heading

print
"<body>\n".

"    <div class='heading'>\n".
"        <h1><strong>Image Database</strong></h1>\n".
"    </div>\n".

"    <div class='back2CShome'>" .
"        <a href='http://www.cs.stedwards.edu/~khamilt2/cosc2328/'>Back to CS Home</a>" .
"    </div>\n".

"    <div class='content'>\n".
"    <!--  start of content  -->\n";

//print "cookies:<br />\n";
//print_r($_COOKIE);
//print "<br />\n";

print
"<!--  PAGE LOGIC BELOW  -->\n";

/* *
 * if session variable 'login' != 1, that means the user is not currently logged in
 * we want to validate user if the $_POST array is empty because we don't want
 * anyone pressing the back button in their browser and be able to log in
 * to address this we use a randomly-generated 15-digit loginKey, which is linked to
 * login input via hidden input field, and remains the same until user clicks 
 * log out, at which point a new key is generated
 * if the program is not currently validating user, then show the initial login form
 */
if($_SESSION['login'] != 1) //if not logged in
{
	//print_r($_POST);
    if(!empty($_POST))
    {
    	validateUser();
    }
    
    else
    {
    	showLoginForm();
    }
	
}//end of IF(not logged in)

/* *
 * if the session variable 'login' = 1, then that means the user has entered login
 * credentials which have been validated by the program
 * if the user clicks 'logout', then perform log out action by calling logoutUser()
 * if the user hasnt clicked 'logout', then display log out button and follow the
 * remaining page logic
 */
else if($_SESSION['login'] == 1)
{
    //print_r($_POST);
    //setcookie("message", "hello", 24*7*3600);
	
    if($_POST['submitLogout'])
    {
        logoutUser();
        showLoginForm();
    }
    
    /* all the rest of the page logic below */
	else 
	{
       /* *
        * if user clicks 'i don't want to upload an image today' load database page
        */
       if($_POST['submitBypassForm'])
        {
        	showSubmitLogout();
            showAllImages();
        }
		
        /* *
         * if user clicks submit button on first page, call function to validate form data
         * if any data is found to be invalid, print an errormessage $errormsg;
         * redisplay and repopulate form
         * if no $errormsg, pass validated data to function to add data to the database
         * then display all images in the database
         */
        else if($_POST['submitForm'])
            checkUploadForm();

        /* *
         * if user clicks "View Category" submit button after selecting a category to
         * view, show all images in that category
         */
        else if($_POST['submitCategoryChoice'])
        {
        	showSubmitLogout();
            showCategoryImages();
        }
		
        /* *
         * if user clicks "Remove" submit button while viewing images, remove that image
         * from the database
         */
        else if($_POST['submitRemoveImage'])
            removeImageFromDB();
		
        /* *
         * if user clicks "Return to database" submit button while viewing images in a
         * chosen category, re-display database
         */
        else if($_POST['submitReturn2db'])
        {
        	showSubmitLogout();
            showAllImages();
        }
		
        /* *
         * if user clicks "Add another image" submit button, display the image upload form
         */
        else if($_POST['submitAddAnotherImg'])
        {
        	showSubmitLogout();
            showUploadForm();
        }

	}//end of ELSE
	
}//end of IF(logged in)

print
"    </div>\n".
"    <!--  end of content  -->\n";

printDocFooter();
#prints document footer

print
"</body>\n".
"</html>\n";

?>