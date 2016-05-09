<?php

//[asg4funx.php]
//functions for asg4.php
//
//Kristin Hamilton
//cosc2328
//16-Sep-2013

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


//******************************************
//[BELOW]  showBlogComments()
//If flat file hasnt been created yet, prints a message to comment
//Reads contents of flat file:  $date at array $list index 0, and $entry at index 1
//To restore original comment content:
//	If there are three tildes ~~~ within $entry, replaces them with a pipe | (puts pipe back)
//Prints comments to screen with date time and entry fields
//
function showBlogComments()
{
	$filename = "../../../s3n25wv/blogComments.txt";
	$fh = fopen($filename, "r");
	
		if (!$fh)
		{
			print "<h3><em>Be the first to comment on this blog</em></h3>\n".
			print "<br />\n";
			return;
		}

		while ($line = fgets($fh, 4096))
		{
			$list = preg_split("/\|/", $line);
			$date = $list[0];
			$entry = $list[1];

			$entry = str_replace("***", "|", $entry);
			print "<h3>$date</h3><h4>$entry</h4>\n";
		}

	fclose($fh);

}//end showBlogComments()


//******************************************
//[BELOW]  showTypeInCommentForm()
//Displays form with text box where user may enter their new comment
//Displays two buttons:
//Button1.  Type:  "submit"  Name/value:  submitNewComment/Submit Comment
//Allows user to submit their new coment
//Button2.  Type:  "reset"  Value:  Clear Entry.
//Allows user to clear text box
//
function showTypeInCommentForm()
{
	$self = $_SERVER['PHP_SELF'];
	print 
		"<form method='post' action='$self'>\n".

		"<div>\n".
		
		"<p>\n".
		"<input type='submit' name='submitNewComment' value='Submit Comment' />\n".
		"<input type='reset' value='Clear Entry' />\n".
		"</p>\n".
		
		"<textarea rows='8' cols='50' name='commentEntry'>\n".
		"Type your comment here\n".
		"</textarea>\n".
				
		"</div>\n".

		"</form>\n".
		"<!--  end of type in comment form  -->\n";

}//end showTypeInCommentForm()


//******************************************
//[BELOW]  processAddEntry()
//Checks submitted comment for non-html
//If no comment written in text area, adds nothing to flat file
//If submission contains data,  reads entry
//	Replaces all pipes | with three tildes ~~~
//Then opens flat file for reading;  reads all data into $output
//Declares variables $date (date time info) and $newEntry (line of data separated by pipes)
//Reassigns $output to mean:  appends newEntry to the front of the old data
//Closes then opens file for writing, and writes $output to flat file
//
function processAddEntry()
{
	$filename = "../../../s3n25wv/blogComments.txt";
	$entry = htmlentities($_POST['commentEntry']);
	
	if ($entry == "")
	{
		return;
	}

	$entry = str_replace("|", "***", $entry);
	//in submitted comment, replacing pipes with three tildes

	$fh = fopen($filename, "r");
	$output = "";
	
	while ($line = fgets($fh, 4096))
	{
		$output .= $line;
	}
	
	fclose($fh);

	$date = date("D d M Y  H:i:s T");
	$newEntry = $date."|".$entry."|"."\n";
	$output = $newEntry.$output;

	$fh = fopen($filename, "w");
	fwrite($fh, $output, strlen($output));
	fclose($fh);

}//end processAddEntry()


//******************************************
//[BELOW]  showAddDeleteForm()
//Displays 2 "submit" buttons:
//Button1.  Name/Value:  submitAddComment/Add a Comment
//Button2.  Name/value:  submitDeleteAll/Delete All Comments
//
function showAddDeleteForm()
{
	$self = $_SERVER['PHP_SELF'];
	print 
		"<form method='post' action='$self'>\n".
		
		"<p>\n".
		"<input type='submit' name='submitAddComment' value='Add a Comment' />\n".
		"<input type='submit' name='submitDeleteAll' value='Delete All Comments' />\n".
		"</p>\n".
		
		"</form>\n".
		"<!--  end of type in comment form  -->\n";

}//end showAddDeleteForm()


//**********************************************
//[BELOW]  deleteAllEntries()
//Deletes all data in flat file by opening file for writing,
//thereby truncating the file at the beginning
//
function deleteAllEntries()
{
	$filename = "../../../s3n25wv/blogComments.txt";
	$fh = fopen($filename, "w");
	fclose($fh);

}//end deleteAllEntries()


//******************************************
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