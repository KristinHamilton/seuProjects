<?php
# [asg3funx.php]
# functions for asg3.php
# Kristin Hamilton
# cosc2328
# 14-Sep-2013

# Prints DOCUMENT HEADING block
function printDocHeading($stylesheet, $title)
{
	echo "
    <!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN'\n
      'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>\n
    <html xmlns='http://www.w3.org/1999/xhtml' lang='en' xml:lang='en'>\n
    <head>\n
    <meta http-equiv='Content-type' content='text/html;charset=UTF-8' />\n
    <link rel='STYLESHEET' href='$stylesheet' type='text/css' />\n
    <title>\n$title\n</title>\n
    </head>\n";
}

# Displays form to user (either blank or with repopulated data). If all data entered 
# correctly, submits form via 'submit' button for gas mileage calculation
function showForm($makeModel="", $miles="", $gallons="")
{
	$self = $_SERVER['PHP_SELF'];

	echo "
	<div>\n
		<form method='get' action='$self'>\n
		
			<table>\n
				<tr>\n
					<h3>\n
					<td>Vehicle make and model (example: Honda Civic)</td>\n
					<td><input type='text' name='makeModel' value='$makeModel' /></td>\n
					</h3>\n
				</tr>\n
				<tr>\n
					<h3>
					<td>Miles traveled (example: 100)</td>\n
					<td><input type='text' name='miles' value='$miles' /></td>\n
					</h3>\n
				</tr>\n
				<tr>\n
					<h3>
					<td>Gallons of gas used (example: 5)</td>\n
					<td><input type='text' name='gallons' value='$gallons' /></td>\n
					</h3>\n
				</tr>\n
			</table>\n
			
			<h3><br /><br />\n
			<input type='submit' name='submitMileageInfo' value='Calculate Gas Mileage' />\n
			</h3>\n

		</form>\n
	</div>\n";
}

# Function prescreens form data before sending it for calculations
# 
# Expects to find the following data in GET
# 	makeModel - expects text
# 	miles - expects text in the form of positive real numbers
# 	gallons - expects text in the form of positive real numbers
# 
# If all data is present and valid
# 	gas mileage calculated
# 	gas mileage reported to user with vehicle make and model
# 	tips for better gas mileage shown
# 	link to original blank form shown
# 
# If all data not present and/or valid
# 	submitted form re-presented to user
# 	re-presented form is repopulated
# 	re-presented form contains a relevant error message
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
		$errormessage .= "<div class='errortext'><h3>Please enter a make and model for ".
			"your vehicle</h3></div>\n";
	}

	if($miles == "")
	{
		$miles = "";
		$errormessage .= "<div class='errortext'><h3>Please enter a number for miles ".
			"traveled</h3></div>\n";
	}
	else if($miles == !is_numeric($miles))
	{
		$miles = "";
		$errormessage .= "<div class='errortext'><h3>Please enter miles traveled, ".
			"numerically</h3></div>\n";
	}
	else if($miles <= 0)
	{
		$miles = "";
		$errormessage .= "<div class='errortext'><h3>Please enter a number greater ".
			"than zero for number of miles traveled</h3></div>\n";
	}

	if($gallons == "")
	{
		$gallons = "";
		$errormessage .= "<div class='errortext'><h3>Please enter a number for gallons ".
			"of gas used</h3></div>\n";
	}
	else if($gallons == !is_numeric($gallons))
	{
		$gallons = "";
		$errormessage .= "<div class='errortext'><h3>Please enter gallons of gas ".
			"used, numerically</h3></div>\n";
	}
	else if($gallons <= 0)
	{
		$gallons = "";
		$errormessage .= "<div class='errortext'><h3>Please enter a number greater ".
			"than zero for gallons of gas used</h3></div>\n";
	}

	if($errormessage)
	{
		echo $errormessage;
		showForm($makeModel, $miles, $gallons);
	}
	else
	{
		$result = "<br /><strong>The gas mileage for your $makeModel is $gasMileage ".
			"miles per gallon (mpg).</strong>\n";
		
		echo "<h2>$result</h2>\n
		<br />\n
		<div class='tips'><h3>$tips</h3></div>\n
		<br />\n
		<h2>Happy Driving!</h2>\n";
	}

	echo "	<br />\n
		<a href='http://www.cs.stedwards.edu/~khamilt2/cosc2328/asg3.php'>".
		"<strong>Back to blank form</strong></a>\n";
}

# Prints DOCUMENT FOOTER
function printDocFooter()
{
	echo "
		<div class='clear'></div>\n
		<div class='footer'>\n
		 	St. Edward&apos;s University .:. cosc 2328 .:. fall 2013\n
			<br /><br />\n
		  	<em>Last Updated:</em>\n
		        <script type='text/javascript'>\n
		             document.write(document.lastModified.slice(0,10));\n
		        </script>\n
			&nbsp;&nbsp;&nbsp;\n
			<a href='http://validator.w3.org/check?uri=referer'><b>HTML</b></a>\n
			&nbsp;&nbsp;&nbsp;\n
			<a href='http://jigsaw.w3.org/css-validator/check?uri=referer'><b>CSS</b></a>\n
			&nbsp;&nbsp;&nbsp;\n
			&copy;\n
			<a href='http://www.stedwards.edu'><b>SEU</b></a>\n
		</div>\n
		<!--  end of footer  -->\n";
}
?>
