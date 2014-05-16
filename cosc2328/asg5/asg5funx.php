<?php

//[asg5funx.php]
//
//Kristin Hamilton
//cosc2328
//04-Oct-2013

/*asg5funx.php:  Mad Libs assignment*/


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


//[BELOW]  printDocHeadingJS($stylesheet, $title, $jsfile1, $jsfile2="")
//Prints DOCUMENT HEADING
//args:
//	$stylesheet - name of stylesheet relative to this page
//	$title - title of page
//	$jsfile1 - javascript file to be used in page
//	$jsfile2 - optional javascript file for page
//
//Heading includes the following
//	documentation info
//	<html> opening tag only
//	<head> and </head> tags
//	<meta /> tag
//	<script> and </script> for javascript file1
//	<script> and </script> for javascript file2, if present
//	stylesheet link
//	<title> and </title> tags
//  
//  
function printDocHeadingJS($stylesheet, $title, $jsfile1, $jsfile2="")
{
  print
    '<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" ' .
    '"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> ' . "\n". 
    '<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">' .
    '<head> ' . "\n". 
    '<meta http-equiv="Content-type" content="text/html;charset=UTF-8" />'.
    "\n" .
    '<script type="text/javascript" src="'.$jsfile1.'"> </script>' . "\n";
  if($jsfile2) {
    print '<script type="text/javascript" src="'.$jsfile2.'"> </script>' . "\n";
  } 
  print  
    '<link rel="STYLESHEET" href="' . $stylesheet . '"  type="text/css" />'.  "\n". 
    '<title>' . $title . '</title> ' . "\n". 
    ' </head> ' ;
}


//[BELOW]  showStorySelectionForm()
//Loads story selection page if nothing has been posted
//Story selection page consists of three radio buttons
//	1.  Inspirational speech. name=storyChoice. value=1
//	2.  Nursery rhymes. name=storyChoice. value=2
//	3.  Children's book. name=storyChoice. value=3
//Page has one submit-type button
//	Button location:  near bottom of story selection form
//	Button info:	    name=submitStorySelection. value=Submit
//
function showStorySelectionForm()
{
	$self = $_SERVER['PHP_SELF'];

print
"	<form method='post' action='$self'>\n";

print
'		<div>'.
'			<h2><strong>Please choose a story for Mad Libs</strong></h2>'.

'			<p>'.
'				<input type="radio" name="storyChoice" value="1" checked="checked" />'.
'				Inspirational speech'.

'				<input type="radio" name="storyChoice" value="2" />'.
'				Nursery rhymes'.

'				<input type="radio" name="storyChoice" value="3" />'.
'				Children&apos;s book'.
'			</p>'.

'			<br />'.

'			<p>'.
'				<input type="submit" name="submitStorySelection" value="Submit" />'.
'			</p>'.
'		</div>';

print
"	</form>\n";

}//end showStorySelectionForm()



//[BELOW]  showFillInBlanksForm($valueArray)
//Displays appropriate story form for user to fill in the blanks, once story choice has been made
//
//Three buttons are shown at bottom of fill-in-the-blanks form:
//	Button1. RESET.  value=Reset.  Clears form of all data
//	Button2. SUBMIT. name=submitStartOver value=Start Over.
//			   Lets user choose a different story: initiates call to showStorySelection()
//	Button3. SUBMIT. name=submitMadLibsForm. value=Submit. Posts form data;  will initiate call to processMadLibsForm()
//
//One hidden input field to pass variable $num (which is holding $_POST["storyChoice"] out of function)
//
function showFillInBlanksForm($previousNum = "", $valueArray = "")
{
	$self = $_SERVER['PHP_SELF'];
	$num = $_POST['storyChoice'];	//Result of this line:  $num is 1,2, or 3, depending on story choice of user

	if($num == "")
	{
		$num = $previousNum;
	}

	$filename = "story".$num."R.txt";
	$contents = file_get_contents($filename);
	$pattern = "/\[([a-z]{1,9}.*?[0-9]{0,1})\]/";

	    //Regex explanation:  String starts and ends with bracket characters.
	    //After opening bracket, match from 1 to 9 instances (curly braces) of lowercase alphabet letters ([a-z]).
	    //Next match 0 or more (*) of any character except newline"\n" (.), but make the fewest matches possible (*?).
	    //Then, match for 0 or 1 instances ({0,1}) of digits between 0 and 9 ([0-9]).
	    //Make a 2nd array which is populated by a subgroup (in parenthesis) of the initial array of matches, and is identical
	    //    to the first array, except for these matches are everything inside of the two brackets, and exclusive of those two brackets

	preg_match_all($pattern, $contents, $matches);

	    //Note:  preg_match_all vs preg_match. preg_match stops at first match; preg_match_all keeps going
	
print

"	<form method='post' action='$self'>\n";

print

'	<div>'.

'		<h2>Please fill in all the blanks below, according to their descriptions;  when you are done, click \'Submit\'</h2>'.
'		<h3>\'Reset\' will clear all the text boxes, and \'Start Over\' will take you back to the story selection page</h3>'.
'		<br />';

print 
	    //make a table
	    //	name="value[]":  creates array populated with user input in each successive text box

'		<table cellspacing="20">';

			for($i = 0; $i < count($matches[1]); $i++)
			{
				print 
				'<tr>'.
			
					'<td>'.
					$matches[1][$i].
					'</td>'.
			
					'<td>';
				print
					"<input type='text' name='value[]' value='$valueArray[$i]' /><br>";
				print
					'</td>'.
			
				'</tr>';
			
			}//end of for loop
			
print
'		</table>';

print
'			<br />'.
'			<p>'.
'				<input type="reset" value="Reset" />'.
'				<input type="submit" name="submitMadLibsForm" value="Submit" />'.
'				<input type="submit" name="submitStartOver" value="Start Over" />'.
'			</p>'.

'		</div>';

print
"	<input type='hidden' name='num' value='$num' />\n".
"	</form>\n";

	if($_POST['reset'])
	{
		showFillInBlanksForm($num, $valueArray);
	}

}//end showFillInBlanksForm($previousNum = "", $valueArray = "")


//[BELOW]  processMadLibsForm()
//Checks submitted mad libs data for HTML
//
function processMadLibsForm()
{
	$num = $_POST['num'];
	$checkArray = $_POST['value'];

	for($i = 0; $i < count($checkArray); $i++)
	{
		$valueArray[$i] = $checkArray[$i];		
	}
	
	for($i=0; $i<count($valueArray); $i++)
	{
		if($valueArray[$i] == "")
		{
			$valueArray[$i] == "";
			$errormessage = "<div class='errortext'>Please fill out all the blanks</div>\n";
		}
	}

	if($errormessage)
	{
		print $errormessage;
		showFillInBlanksForm($num, $valueArray);
	}

	else
	{
		showMadLibsResults();
	}

}//end processMadLibsForm()


//[BELOW]  showMadLibsResults()
//called by processMadLibsForm() if good/nonmissing data has been posted by user
//
function showMadLibsResults()
{
	showOriginalStory();
	showMadLibsStory();
	showMatches();
	showSubmitStartOver();

}//end showMadLibsResults()


//[BELOW] showOriginalStory()
//Print original story on mad libs results page
//
function showOriginalStory()
{
	$numH = $_POST["num"];
	$filename = 'story'.$numH.'.txt';

	$contents = file_get_contents($filename);
	$lineStory = nl2br($contents);

print
'	<p>'.
'		<h2><strong>'.
'		Original Story:'.
'		</strong></h2>'.
'	</p>'.

'	<p>';
		print $lineStory;
print
'	</p>';

}//end showOriginalStory()


//[BELOW]  showMadLibsStory()
//Print story with substitutions made on mad libs results page
//
function showMadLibsStory()
{
	$numH = $_POST["num"];
	$filename = 'story'.$numH.'R.txt';

	$contents = file_get_contents($filename);
	
	$pattern = "/\[([a-z]{1,9}.*?[0-9]{0,1})\]/";
	preg_match_all($pattern, $contents, $matches);
	
print
'	<p>'.
'		<br />'.
'		<h2><strong>'.
'		Mad Libs Story:'.
'		</strong></h2>'.
'	</p>';

    //loop through array of $matches[0], replacing each match with the value of array $valuesArray at each index $i
	for ($i=0; $i<count($matches[0]); $i++)
	{
		$patternR = $matches[0][$i];
		$valueArray = $_POST['value'];
		$result = $valueArray[$i];
		$contents = preg_replace($patternR, $result, $contents);
		$lineStory = nl2br($contents);
	}
	
print '<p>';
		print $lineStory;
print '</p>';

}//end showMadLibsStory()


//[BELOW]  showMatches()
//Print total match:  $matches[0] and $matches[1]
//$matches[0] is the array of matches that includes the brackets around them
//$matches[1] is the array of matches for the first subgroup: $matches[0] with the brackets excluded
//
function showMatches()
{
	$numH = $_POST["num"];
	$filename = 'story'.$numH.'R.txt';
	$contents = file_get_contents($filename);

    //reproducing array $matches so it can be printed
	$pattern = "/\[([a-z]{1,9}.*?[0-9]{0,1})\]/";
	preg_match_all($pattern, $contents, $matches);

print
'	<br />'.
'	<h2><strong>Total Match:</strong></h2>';

print
"	<h3>\$matches[0]:</h3>\n";

	foreach ($matches[0] as $match)
	{
		print "$match<br />\n";
	}

print
"	<h3>\$matches[1]:</h3>\n";

	foreach ($matches[1] as $match)
	{
		print "$match<br />\n";
	}

print
"	<br /><br />\n";

}//end showMatches()


//[BELOW]  showSubmitStartOver()
//Submit button that allows user to go back to story selection page and choose a different story
//
function showSubmitStartOver()
{
	$self = $_SERVER['PHP_SELF'];

print
"	<form method='post' action='$self'>\n";

print
'		<p>'.
'			<input type="submit" name="submitStartOver" value="Start Over" />'.
'		</p>';

print
"	</form>\n";

}//end showSubmitStartOver()


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