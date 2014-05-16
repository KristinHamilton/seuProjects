<?php

//[asg6.php]
//main script/page logic for assignment6
//
//Kristin Hamilton
//cosc2328
//file created: Weds, 23-Oct-2013
//
//
//

/*
asg6.php:  Objective.  PHP GD Library assignment
Create image canvas:
	User input.	Height (200 to 800 px);  Width (200 to 800 px)
			Color Scheme (3 to choose from;  show thumbnails next to radio buttons)
Create image with canvas:
	PNG file
	At least 8 different graphical images
	Create image with user input as parameters;  show on screen
	"Create Thumbnail" button.
			Width:  80 px;  Height:  proportional to width (maintain aspect ratio)
			Display preview/thumbnail below original image on screen
	Use PHP GD Library to find methods for drawing scheme
*/

//echo phpinfo();
//ini_set ("display_errors", "1");
//error_reporting(E_ALL);

require "asg6funx.php";
    /*
	asg6funx.php consists of functions:  printDocHeading, showForm, checkFormData,
       showSubmitMakeThumbnail, showSubmitShowForm, makeThumbnail, and printDocFooter.
    */
     //file "asg6MakeImage.php" is required in file "asg6funx.php"

printDocHeading("asg6css.css", "Artistic Expression Page");
#prints document heading

print
"<body>\n".

"	<div class='heading'>\n".
"	<!--  start of heading  -->\n".
"		<h1><strong>Artistic Expression Page</strong></h1>\n".
"	</div>\n".
"	<!--  end of heading  -->\n".

"    <div class='back2CShome'>" .
"        <a href='http://www.cs.stedwards.edu/~khamilt2/cosc2328/'>Back to CS Home</a>" .
"    </div>\n".

"	<div class='content'>\n".
"	<!--  start of content  -->\n".

"	<!--  PAGE LOGIC BELOW  -->\n";
	    //if nothing is posted, load image parameter selection page
		    //user input collected:
		    //image canvas size.  Height (200 to 800 px);  Width (200 to 800 px)
		    //color scheme. 3 choices
		    //1 (submit) button
		if(empty($_POST))
		{
		   	showForm();	
		}

	    //if user clicks submit button on first page, check text input for html
	    //also check that text input for img height and width is not blank, non-numeric,
	    //or outside of the range of 200 to 800px;  if it is, display error msg
	    //        pass valid $imgHeight and $imgWidth to function drawImage in file "asg6MakeImage.php"
		else if($_POST['submitImgParameters'])
		{
			checkFormData();
			    //checks form data, calls function drawImage to create color schemes and generate png image
			    //also prints 2 (submit) buttons:  one to call makeThumbnail, and one to create "back to first page" button
		}

	    //if user clicks on "create thumbnail" button,
	    //create thumbnail image from original image, and display thumbnail underneath original image
		else if($_POST['submitMakeThumbnail'])
		{
			makeThumbnail();
		}

	    //if user clicks/posts submit button (value "Back to first page),
	    //return to first page
		else if($_POST['submitShowForm'])
		{
			showForm();
		}

print
"	</div>\n".
"	<!--  end of content  -->\n";

printDocFooter();
#prints document footer

print
"</body>\n".
"</html>\n";

?>
