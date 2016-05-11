<?php
# [asg6.php]
# main script/page logic for assignment6
# Kristin Hamilton
# cosc2328
# file created: Weds, 23-Oct-2013
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
ini_set ("display_errors", "1");
error_reporting(E_ALL);

require_once "asg6funx.php";
printDocHeading("asg6.css", "Artistic Expression Page");

echo "<body>	
	<div class='heading'>
		<h1><strong>Artistic Expression Page</strong></h1>
	</div>

    <div class='back2CShome'>
        <a href='http://kristin.create.stedwards.edu/cosc2328/index.php'>Back to CS Home</a>
    </div>
		
	<div class='content'>
	<!--  start of content  -->\n";

if(empty($_POST))
{
   	showForm();	
}
else if(isset($_POST['submitImgParameters']))
{
	checkFormData();
}
else if(isset($_POST['submitMakeThumbnail']))
{
	makeThumbnail();
}
else if(isset($_POST['submitShowForm']))
{
	# return to first page
	showForm();
}

echo "	</div>\n	<!--  end of content  -->\n";
printDocFooter();
echo "</body>\n</html>\n";
?>
