<?php
# [asg5funx.php]
# Mad Libs assignment
# Kristin Hamilton
# cosc2328
# 04-Oct-2013

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

# Loads story selection page if nothing has been posted
function showStorySelectionForm()
{
	$self = $_SERVER['PHP_SELF'];

	echo "	
	<form method='post' action='$self'>\n
		<div>\n
			<h2><strong>Please choose a story for Mad Libs</strong></h2>\n
			<p>\n
				<input type='radio' name='storyChoice' value='1' checked='checked' />
				Inspirational speech\n
				<input type='radio' name='storyChoice' value='2' />
				Nursery rhymes\n
				<input type='radio' name='storyChoice' value='3' />
				Children&apos;s book\n
			</p>\n
			<br />\n
			<p>\n
				<input type='submit' name='submitStorySelection' value='Submit' />\n
			</p>\n
		</div>\n
	</form>\n";
}

# Displays appropriate story form for user to fill in the blanks, once story 
# choice has been made
function showFillInBlanksForm($previousNum = "", $valueArray = "")
{	
	$num = "";
	if(is_null($valueArray) || !is_array($valueArray)){ $valueArray = array(); }
	
	$self = $_SERVER['PHP_SELF'];
	if(isset($_POST['storyChoice']))
	{
		$num = $_POST['storyChoice'];  # $num is story choice of user
	}
	
	if($num == ""){ $num = $previousNum; }

	$filename = "story$num"."R.txt";
	$contents = file_get_contents($filename);
	$pattern = "/\[([a-z]{1,9}.*?[0-9]{0,1})\]/";
	# Regex explanation:  String starts and ends with bracket characters.
	# After opening bracket, match from 1 to 9 instances (curly braces) of lowercase 
	#  alphabet letters ([a-z]).
	# Next match 0 or more (*) of any character except newline"\n" (.), but make the 
	#  fewest matches possible (*?).
	# Then, match for 0 or 1 instances ({0,1}) of digits between 0 and 9 ([0-9]).
	# Make a 2nd array which is populated by a subgroup (in parenthesis) of the initial 
	#  array of matches, and is identical to the first array, except for these matches 
	#  are everything inside of the two brackets, and exclusive of those two brackets

	preg_match_all($pattern, $contents, $matches);
	# Note (preg_match_all vs preg_match): 
	#  preg_match stops at first match; preg_match_all keeps going
	
	echo "
	<form method='post' action='$self'>\n	
		<div>\n
			<h2>Please fill in all the blanks below, according to their descriptions;  
			when you are done, click 'Submit'</h2>\n
			<h3>'Reset' will clear all the text boxes, and 'Start Over' will take you 
			back to the story selection page</h3>\n
			<br />\n
			<table cellspacing='20'>\n";

			for($i = 0; $i < count($matches[1]); $i++)
			{
				if($i > count($valueArray) - 1){ $valueArray[$i] = ""; }
				
				echo  "
				<tr>\n
					<td>\n",
						$matches[1][$i] , 
					"\n</td>\n			
					<td>\n
						<input type='text' name='value[]' value='", $valueArray[$i] ,"' />\n
						<br>\n
					</td>\n
				</tr>\n";
			}
			
	echo "
			</table>\n
			<br />\n
			<p>\n
				<input type='reset' value='Reset' />\n
				<input type='submit' name='submitMadLibsForm' value='Submit' />\n
				<input type='submit' name='submitStartOver' value='Start Over' />\n
			</p>\n
		</div>\n
		<input type='hidden' name='num' value='$num' />\n
	</form>\n";
	
	if(isset($_POST['reset']))
	{
		showFillInBlanksForm($num, $valueArray);
	}
}

# Checks submitted mad libs data for HTML
function processMadLibsForm()
{
	$errormessage = "";	
	$num = $_POST['num'];
	$checkArray = $_POST['value'];

	for($i = 0; $i < count($checkArray); $i++)
	{
		$valueArray[$i] = $checkArray[$i];		
	}
	
	for($i = 0; $i < count($valueArray); $i++)
	{
		if($valueArray[$i] == "")
		{
			$valueArray[$i] == "";
			$errormessage = "<div class='errortext'>Please fill out all the blanks</div>\n";
		}
	}

	if($errormessage != "")
	{
		echo $errormessage;
		showFillInBlanksForm($num, $valueArray);
	}
	else
	{
		showMadLibsResults();
	}
}

# called by processMadLibsForm() if good/nonmissing data has been posted by user
function showMadLibsResults()
{
	showOriginalStory();
	showMadLibsStory();
	showMatches();
	showSubmitStartOver();
}

# prints original story on mad libs results page
function showOriginalStory()
{
	$numH = $_POST["num"];
	$filename = "story$numH.txt";
	$contents = file_get_contents($filename);
	$lineStory = nl2br($contents);
	echo "<p>\n<h2><strong>Original Story:</strong></h2>\n</p>\n<p>\n$lineStory\n</p>\n";
}

# prints story with substitutions made on mad libs results page
function showMadLibsStory()
{
	$numH = $_POST["num"];
	$filename = "story$numH"."R.txt";
	$contents = file_get_contents($filename);
	$pattern = "/\[([a-z]{1,9}.*?[0-9]{0,1})\]/";
	preg_match_all($pattern, $contents, $matches);
	
echo "
	<p>\n
		<br />\n
		<h2><strong>Mad Libs Story:</strong></h2>\n
	</p>\n";

    # replace each match with user input
	for ($i=0; $i<count($matches[0]); $i++)
	{
		$patternR = $matches[0][$i];
		$valueArray = $_POST['value'];
		$result = $valueArray[$i];
		$contents = preg_replace($patternR, $result, $contents);
		$lineStory = nl2br($contents);
	}
	
	echo "
		<p>\n$lineStory\n</p>\n";
}

# Print total match:  $matches[0] and $matches[1]
# $matches[0] is the array of matches that includes the brackets around them
# $matches[1] is the array of matches for the first subgroup: $matches[0] with the 
#  brackets excluded
function showMatches()
{
	$numH = $_POST["num"];
	$filename = "story$numH"."R.txt";
	$contents = file_get_contents($filename);

    # reproduce matches for display to screen
	$pattern = "/\[([a-z]{1,9}.*?[0-9]{0,1})\]/";
	preg_match_all($pattern, $contents, $matches);

	echo "<br /><h2><strong>Total Match:</strong></h2>\n<h3>\$matches[0]:</h3>\n";
	foreach ($matches[0] as $match){ echo "$match<br />\n";	}
	echo "<h3>\$matches[1]:</h3>\n";
	foreach ($matches[1] as $match){ echo "$match<br />\n"; }
	echo "<br /><br />\n";
}

# Submit button that allows user to go back to story selection page and choose a 
# different story
function showSubmitStartOver()
{
	$self = $_SERVER['PHP_SELF'];
	echo "
		<form method='post' action='$self'>\n
			<p>\n
				<input type='submit' name='submitStartOver' value='Start Over' />\n
			</p>\n
		</form>\n";
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