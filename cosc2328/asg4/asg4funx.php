<?php
# [asg4funx.php]
# functions for asg4.php
# Kristin Hamilton
# cosc2328
# 16-Sep-2013

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

# Restores original comment content then prints comments to screen with date time 
# and entry fields
function showBlogComments()
{
	$filename = "../../../s3n25wv/blogComments.txt";
	$fh = fopen($filename, "r");
	
	if (!$fh)
	{
		echo "<h3><em>Be the first to comment on this blog</em></h3>\n<br />\n";
		return;
	}

	while ($line = fgets($fh, 4096))
	{
		$list = preg_split("/\|/", $line);
		$date = $list[0];
		$entry = $list[1];

		$entry = str_replace("***", "|", $entry);
		echo "<h3>$date</h3><h4>$entry</h4>\n";
	}

	fclose($fh);
}

# Displays form with text box where user may enter their new comment
function showTypeInCommentForm()
{
	$self = $_SERVER['PHP_SELF'];
	echo "<form method='post' action='$self'>\n".
		"	<div>\n".
		"		<p>\n".
		"			<input type='submit' name='submitNewComment' value='Submit Comment' />\n".
		"			<input type='reset' value='Clear Entry' />\n".
		"		</p>\n".
		"		<textarea rows='8' cols='50' name='commentEntry'>\n".
		"			Type your comment here\n".
		"		</textarea>\n".
		"	</div>\n".
		"</form>\n";
}

# Validates comment contents; replaces all pipe chars | with three tildes ~~~
function processAddEntry()
{
	$filename = "../../../s3n25wv/blogComments.txt";
	$entry = htmlentities($_POST['commentEntry']);
	if ($entry == ""){ return; }

	# Replacing pipe chars with three tildes
	$entry = str_replace("|", "***", $entry);

	$output = "";
	$fh = fopen($filename, "r");
	while ($line = fgets($fh, 4096)){ $output .= $line; }
	fclose($fh);

	$date = date("D d M Y  H:i:s T");
	$newEntry = $date."|".$entry."|"."\n";
	$output = $newEntry.$output;

	$fh = fopen($filename, "w");
	fwrite($fh, $output, strlen($output));
	fclose($fh);
}

# Displays two buttons: "Add a Comment" and "Delete All Comments"
function showAddDeleteForm()
{
	$self = $_SERVER['PHP_SELF'];
	echo "<form method='post' action='$self'>\n".		
		"	<p>\n".
		"		<input type='submit' name='submitAddComment' value='Add a Comment' />\n".
		"		<input type='submit' name='submitDeleteAll' value='Delete All Comments' />\n".
		"	</p>\n".
		"</form>\n";
}

# Deletes all data in flat file by opening file for writing, thereby truncating the 
# file at the beginning
function deleteAllEntries()
{
	$filename = "../../../s3n25wv/blogComments.txt";
	$fh = fopen($filename, "w");
	fclose($fh);
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