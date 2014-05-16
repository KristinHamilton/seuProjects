<?php

/* *
 * [asg7.php]
 * main script/page logic for asg7
 * 
 * Kristin Hamilton
 * cosc2328
 * file created: Weds, 07-Nov-2013
 */

/* *
 * Summary for asg7.php:  MySQL/Image database.  
 * CREATES FRONT END OF DB
 * USER INPUT COLLECTED IN FILE UPLOAD FORM:
 *   Img file: file upload, Img desc: text (100 chars max),
 *   Img category (1+): checkbox array
 * STORES IMAGE BINARY IN MySQL DATABASE
 * DISPLAYS DATABASE CONTENTS:
 *   Shows page with image thumbnails, along with image description and categories
 *   When thumbnail is clicked, full size image opens up in browser window
 * TABLES USED IN ASG7:
 *    table 1 "image."  columns: imgID (int, auto_inc), fullImg (longBLOB),
 *      thumbImg (longBLOB), imgDesc (varchar)
 *    table 2 "category."  columns: catID (int), catDesc (varchar)
 *    table 3 "image2Category." BRIDGE TABLE.  columns: imgID, catID
 */

//echo phpinfo();
//ini_set ("display_errors", "1");
//error_reporting(E_ALL);

require "asg7funx.php";
    /*
     * [asg7funx.php fns: Table of Contents]
     * printDocHeading
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

printDocHeading("asg7css.css", "Image Database");
#prints document heading

print
"<body>\n".

"    <div class='heading'>\n".
"        <h1><strong>Image Database</strong></h1>\n".
"    </div>\n".

"    <div class='back2CShome'>" .
"	    <a href='http://www.cs.stedwards.edu/~khamilt2/cosc2328/'>Back to CS Home</a>" .
"    </div>\n".

"    <div class='content'>\n".
"    <!--  start of content  -->\n".

"    <!--  PAGE LOGIC BELOW  -->\n";

/* if nothing is posted, show file upload page
 * user input collected:
 *   file input. image to be uploaded (jpg/jpeg only)
 *   text input. image description. (max length 100 characters)
 *   checkbox input. (array of) image categories (one or more)
 * submit button
 */
if(empty($_POST))
    showUploadForm();

else if($_POST['submitBypassForm'])
	showAllImages();

/* if user clicks submit button on first page, call function to validate form data
 * if any data is found to be invalid, print an errormessage $errormsg;
 * redisplay and repopulate form
 * if no $errormsg, pass validated data to function to add data to the database
 * then display all images in the database
 */
else if($_POST['submitForm'])
    checkUploadForm();

/* if user clicks "View Category" submit button after selecting a category to
 * view, show all images in that category
 */
else if($_POST['submitCategoryChoice'])
    showCategoryImages();
		
/* if user clicks "Return to database" submit button while viewing images in a chosen 
 * category, re-display database
 */
else if($_POST['submitReturn2db'])
    showAllImages();

/* if user clicks "Add another image" submit button, display the image upload form
 */
else if($_POST['submitAddAnotherImg'])
    showUploadForm();

print
"    </div>\n".
"    <!--  end of content  -->\n";

printDocFooter();
#prints document footer

print
"</body>\n".
"</html>\n";

?>