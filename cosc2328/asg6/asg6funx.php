<?php

//[asg6funx.php]
//functions required for asg6.php
//
//Kristin Hamilton
//cosc2328
//created: Weds, 23-Oct-2013

/*
functions:  Table of Contents
	printDocHeading
	showForm
	checkFormData
	showSubmitMakeThumbnail
	showSubmitShowForm
	makeThumbnail
	printDocFooter
*/

/*
asg6funx.php:  Note.
requires file "asg6MakeImage.php," which contains function drawImage.
*/

require "asg6MakeImage.php";

//[BELOW] printDocHeading ($stylesheet, $title)
//Prints DOCUMENT HEADING block
//args:
//	$stylesheet - name of stylesheet relative to this page
//	$title - title of page 
//
//Heading includes the following
//	documentation info
//	<html> opening tag only
//	<meta /> tag
//	<head> and </head> tags 
//	stylesheet link
//	<title> and </title> tags
//
//
function printDocHeading($stylesheet, $title)
{
  print
    '<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" ' . "\n" .
      '"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> ' ."\n" .
    '<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">' ."\n" .
    '<head> ' ."\n" .
    '<meta http-equiv="Content-type" content="text/html;charset=UTF-8" />'.
    "\n" .
    '<link rel="STYLESHEET" href="' . $stylesheet . '"  type="text/css" />'."\n" .
    '<title>' . "\n" .$title . "\n" .'</title> ' ."\n" .
    ' </head> '. "\n" ;
}


//[BELOW]  showForm($imgHeight, $imgWidth);
//displays form that collects user input for image dimensions and color scheme
//user input collected:
//	image canvas size.  Height (200 to 800 px);  Width (200 to 800 px)
//	color scheme. 3 choices
//1 (submit) button:  posts form data
//
function showForm($imgHeight="", $imgWidth="")
{
	global $rainbowColorsArray, $jewelColorsArray, $neonColorsArray;
	$self = $_SERVER['PHP_SELF'];

print
"		<div class='headingcontent'>\n".
"		<!--  start of headingcontent  -->\n".

"			<h1><strong>Set image parameters below</strong></h1>\n".

"		</div>\n".
"		<!--  end of div headingcontent  -->\n".

"	<!--  START OF FORM  -->\n".
"	<form method='post' action='$self'>\n";

print
'		<table cellspacing="20">'.
'			<tr><td><h2>Select an image size:</h2></td></tr>'.

'			<tr>'.
'				<td>'.
'				Please enter a height (200 to 800 pixels)'.
'				</td>';
print
"				<td>\n".
"				<input type='text' name='imgHeight' maxlength='3' value='$imgHeight' />\n".
"				</td>\n";
print
'			</tr>'.

'		 	<tr>'.
'				<td>'.
'				Please enter a width (200 to 800 pixels)'.
'				</td>';

print
"				<td>\n".
"				<input type='text' name='imgWidth'  maxlength='3' value='$imgWidth' />\n".
"				</td>\n";
print
'			</tr>'.

'		</table>'.

'		<p></p>';

print
'		<table cellspacing="20">'.
'			<tr><td></td></tr>'.

'			<tr align="center">'.
'				<td><h2>Select a color scheme:</h2></td>'.

'				<td></td>'.

'				<td>'.
'				<img src="rainbowPreview.png" alt="rainbows on gray" />'.
'				</td>'.

'				<td>'.
'				<img src="jewelPreview.png" alt="jewels on brown" />'.
'				</td>'.

'				<td>'.
'				<img src="neonPreview.png" alt="neons on black" />'.
'				</td>'.
'			</tr>'.
			
'			<tr>'.
'				<td></td>'.
'				<td></td>'.

'				<td>'.
'				<input type="radio" name="imgColorChoice" value="1" checked="checked" />'.
'				rainbow colors on gray background'.
'				</td>'.

'				<td>'.
'				<input type="radio" name="imgColorChoice" value="2" />'.
'				jewel tones on brown background'.
'				</td>'.

'				<td>'.
'				<input type="radio" name="imgColorChoice" value="3" />'.
'				neon colors on black background'.
'				</td>'.
'			</tr>'.

'		</table>'.

'		<p></p>'.

'		<table cellspacing="20">'.
'			<tr><td>'.
'			<input type="submit" name="submitImgParameters" value="Submit" />'.
'			</td></tr>'.
'		</table>';

print
"	</form>\n".
"	<!-- END OF FORM  -->\n";

}//end showForm($imgHeight, $imgWidth)


//[BELOW]  checkFormData()
//if user clicks submit button on first page, check text input for html
//also check that text input for img height and width is not blank, non-numeric,
//or outside of the range of 200 to 800px;
//	if text input generates error msg, redisplay first page, showing error message(s), and repopulate text box(es)
//
//calls function drawImage:  pass valid $imgHeight and $imgWidth to function drawImage in file "asg6MakeImage.php"
//calls function showSubmitMakeThumbnail (to create thumbnail image), and
//	 function showSubmitShowForm (allows user to go back to first page)
//
function checkFormData()
{
    //check $imgHeight and $imgWidth for HTML
	$imgHeight = htmlentities($_POST['imgHeight']);
	$imgWidth = htmlentities($_POST['imgWidth']);

    //check $imgHeight:  if blank, show error message
	if($imgHeight == "")
	{
		$imgHeight = "Please enter a height\n";
		$errormsg .= "<div class='errortext'><h3>Please enter a height</h3></div>\n";
	}

    //check $imgHeight:  if non-numeric, show error message
	else if(!is_numeric($imgHeight))
	{
		$imgHeight = "Please enter a height, numerically\n";
		$errormsg .= "<div class='errortext'><h3>Please enter a height, numerically</h3></div>\n";
	}

    //check $imgHeight:  if outside the range of 200 to 800 (pixels), show error message
	else if($imgHeight < 200 || $imgHeight > 800)
	{
		$imgHeight = "Please enter a height between 200 and 800\n";
		$errormsg .= "<div class='errortext'><h3>Please enter a height between 200 and 800</h3></div>\n";
	}

    //check $imgWidth:  if blank, show error message
	if($imgWidth == "")
	{
		$imgWidth = "Please enter a width\n";
		$errormsg .= "<div class='errortext'><h3>Please enter a width</h3></div>\n";
	}

    //check $imgWidth:  if non-numeric, show error message
	else if( !is_numeric($imgWidth))
	{
		$imgWidth = "Please enter a width, numerically\n";
		$errormsg .= "<div class='errortext'><h3>Please enter a width, numerically</h3></div>\n";
	}

    //check $imgWidth:  if outside the range of 200 to 800 (pixels), show error message

	else if($imgWidth < 200 || $imgWidth > 800)
	{
		$imgWidth = "Please enter a width between 200 and 800\n";
		$errormsg .= "<div class='errortext'><h3>Please enter a width between 200 and 800</h3></div>\n";
	}

    //if an error message has been generated by the above checks, print the error message, and resend data to showForm($imgHeight, $imgWidth)
    //otherwise, call drawImage(pass $imgHeight, $imgWidth)
	if($errormsg)
	{
		print $errormsg;
		showForm($imgHeight, $imgWidth);
	}
	
	else
	{
       print
	"	<div>\n";

			drawImage($imgHeight, $imgWidth);

	    //arrange the two buttons, genereated by the functions below, into a single line beneath the image
	print	   
	"	<table>\n".   
	"		<tr>\n".
	
	"			<td>\n";			
				    //display a button to allow user to go back to image parameter selection page
					showSubmitShowForm();
	print
	"			</td>\n".
	"			<td>\n";
				    //display button to allow user to create thumbnail image
					showSubmitMakeThumbnail();
	print	   "</td>\n".
	"		</tr>\n".
	"	</table>\n".
	
	"	</div>\n";
	}

}//end of checkFormData()


//[BELOW] showSubmitMakeThumbnail()
//creates button that, when clicked/posted, calls function makeThumbnail to create thumbnail image
//
function showSubmitMakeThumbnail()
{
	$self = $_SERVER['PHP_SELF'];

print
"	<form method= 'post' action='$self'>\n";
print
'		<input type="submit" name="submitMakeThumbnail" value="Create thumbnail" />';
print
"	</form>\n";

}//end of showSubmitMakeThumbnail()


//[BELOW]  showSubmitShowForm()
//creates button that allows user to return to first page
//
function showSubmitShowForm()
{
	$self = $_SERVER['PHP_SELF'];

print
"	<form method='post' action='$self'>\n".
"		<input type='submit' name='submitShowForm' value='Back to first page' />\n".
"	</form>\n";

}//end of showSubmitShowForm()


//[BELOW]  makeThumbnail()
//creates thumbnail image:
//first creates image resource $originalImage, from original png image
//then gets the width and height of the original image,
//	which are stored in variables $origWidth and $origHeight
//stores value of 80px in variable $newWidth
//	calculates ratio of 80px to the width of the original image
//	then scales down original height in proportion to original width,
//	to maintain aspect ratio of original image
//finally, prints original image, with thumbnail image beneath it on the page
//1 (submit) button:  allows user to go back to first page
//
function makeThumbnail()
{
	$originalImage = imagecreatefrompng("../temp/tempPNG.png");
	
    //get dimensions of original image
	$origWidth = imagesx($originalImage);
	$origHeight = imagesy($originalImage);

    //calculate dimensions of thumbnail
	$newWidth = 80;
	$ratioOldToNewWidth = 80/$origWidth;
	$newHeight = $origHeight*$ratioOldToNewWidth;

print
'	<table>'.
'		<tr><td><div class="headingcontent"><h2>Original image:<br /></h2></div></td></tr>'.
'		<tr><td><img src="../temp/tempPNG.png" alt="Original image" height='."$origHeight".' width='."$origWidth".' /></td></tr>'.
'		<tr><td><div class="headingcontent"><h2>Thumbnail image:<br /></h2></div></td></tr>'.
'		<tr align="center"><td><img src="../temp/tempPNG.png" alt="Thumbnail image" height='."$newHeight".' width='."$newWidth".' /></td></tr>'.
'	</table>';

    //display a button to allow user to go back to image parameter selection page
	showSubmitShowForm();

}//end of makeThumbnail()


//[BELOW]  printDocFooter()
//Prints DOCUMENT FOOTER
//
//Footer includes the following
//	SEU-cosc2328-web programming-fall2013
//	page last updated with time and date stamp
//	link to HTML and CSS validators
//	link to stedwards.edu with copyright symbol
//
function printDocFooter()
{
print
"	<div class='clear'></div>\n".	
"	<div class='footer'>\n".
" 	St. Edward&apos;s University .:. cosc 2328 .:. fall 2013\n".
"	<br /><br />\n".
  
"  	<em>Last Updated:</em>\n".
"        <script type='text/javascript'>\n".
"             document.write(document.lastModified.slice(0,10));\n".
"        </script>\n".

"	&nbsp;&nbsp;&nbsp;\n".
"	<a href='http://validator.w3.org/check?uri=referer'><strong>HTML</strong></a>\n".
"	&nbsp;&nbsp;&nbsp;\n".
"	<a href='http://jigsaw.w3.org/css-validator/check?uri=referer'><strong>CSS</strong></a>\n".
"	&nbsp;&nbsp;&nbsp;\n".

"	&copy;\n".
"	<a href='http://www.stedwards.edu'><strong>SEU</strong></a>\n".
	
"	</div>\n".
"	<!--  end of footer  -->\n";

}//end printDocFooter() 

?>