<?php
//
//[asg3funx.php]
//
//functions for asg3.php
//
//Kristin Hamilton
//cosc2328
//14-Sep-2013
//this file is a renamed copy of classfunphp originally created by Dr Baker
//
//
//
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
}//end printDocHeading()



//[BELOW]  showForm($makeModel, $miles, $gallons)
//Displays form to user (either blank or with repopulated data)
//If all data entered correctly, submits form via 'submit' button
//for gas mileage calculation
//args:
//	$makeModel - contents/value of form box1, signifies vehicle make and model
//	$miles - contents/value of form box2, signifies miles traveled
//	$gallons - contents/value of form box3, signifies gallons of gasoline used
//
//Form displayed to user consists of the following elements
//	form box1 - vehicle make and model  
//	form box2 - miles traveled
//	form box3 - gallons of gasoline used
//	'submit' button at bottom of form
//
//
function showForm($makeModel="", $miles="", $gallons="")
{
	$self = $_SERVER['PHP_SELF'];

	print

"	<div><form method='get' action='$self'>\n".


"	<h3>Vehicle make and model (example: Honda Civic) &nbsp;<input type='text' name='makeModel' value='$makeModel' /></h3>\n".

"	<h3>Miles traveled (example: 100) &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='text' name='miles' value='$miles' /></h3>\n".

"	<h3>Gallons of gas used (example: 5)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='text' name='gallons' value='$gallons' /></h3>\n".

"	<h3><br /><br /><input type='submit' name='submitMileageInfo' value='Calculate Gas Mileage' /></h3>\n".


"	</form>\n".
"	<!--  end of form  -->\n".

"	</div>\n";

}//end showForm()



//[BELOW]  checkFormData()
//Function prescreens form data before sending it for calculations
//
//Expects to find the following data in GET
//	makeModel - expects text
//	miles - expects text in the form of positive real numbers
//	gallons - expects text in the form of positive real numbers
//
//If all data is present and valid
//	gas mileage calculated
//	gas mileage reported to user with vehicle make and model
//	tips for better gas mileage shown
//	link to original blank form shown
//
//If all data not present and/or valid
//	submitted form re-presented to user
//	re-presented form is repopulated
//	re-presented form contains a relevant error message
//
function checkFormData()
{
	$makeModel = htmlentities($_GET['makeModel']);
	$miles = htmlentities($_GET['miles']);
	$gallons = htmlentities($_GET['gallons']);
	$gasMileage = $miles/$gallons;
	$tips = "Tips for getting more miles out of your gas:\n".
		 "<ul>\n".
		 "<li>Make sure your tires are properly inflated</li>\n".
		 "<li>Roll your windows up for less air resistance</li>\n".
		 "<li>Clean out your car to reduce the weight of your car</li>\n".
		 "</ul>\n";


	if($makeModel == "")
	{
		$makeModel = "";
		$errormessage .= "<div class='errortext'><h3>Please enter a make and model for your vehicle</h3></div>\n";
	}

	if($miles == "")
	{
		$miles = "";
		$errormessage .= "<div class='errortext'><h3>Please enter a number for miles traveled</h3></div>\n";
	}
	

	else if($miles == !is_numeric($miles))
	{
		$miles = "";
		$errormessage .= "<div class='errortext'><h3>Please enter miles traveled, numerically</h3></div>\n";
	}


	else if($miles <= 0)
	{
		$miles = "";
		$errormessage .= "<div class='errortext'><h3>Please enter a number greater than zero for number of miles traveled</h3></div>\n";
	}


	if($gallons == "")
	{
		$gallons = "";
		$errormessage .= "<div class='errortext'><h3>Please enter a number for gallons of gas used</h3></div>\n";
	}
	

	else if($gallons == !is_numeric($gallons))
	{
		$gallons = "";
		$errormessage .= "<div class='errortext'><h3>Please enter gallons of gas used, numerically</h3></div>\n";
	}


	else if($gallons <= 0)
	{
		$gallons = "";
		$errormessage .= "<div class='errortext'><h3>Please enter a number greater than zero for gallons of gas used</h3></div>\n";
	}


	if($errormessage)
	{
		print $errormessage;
		showForm($makeModel, $miles, $gallons);
	}
	else
	{
		$result = "<br /><strong>The gas mileage for your $makeModel is $gasMileage miles per gallon (mpg).</strong>\n";
		print
		"<h2>$result</h2>\n".
		"<br />\n".
		"<div class='tips'><h3>$tips</h3></div>\n".
		"<br />\n".
		"<h2>Happy Driving!</h2>\n";
	}


	print

"	<br />\n".
"	<a href='http://www.cs.stedwards.edu/~khamilt2/cosc2328/asg3.php'><strong>Back to blank form</strong></a>\n";

}//end checkFormData()



//[BELOW]  printDocFooter()
//Prints DOCUMENT FOOTER
//
//Footer includes the following
//	SEU-cosc2328-web programming-fall2013
//	page last updated with time and date stamp
//	link to HTML and CSS validators
//	link to stedwards.edu with copyright symbol
//
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


