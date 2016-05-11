<?php
# [asg5.php]
# Kristin Hamilton
# cosc2328
# 19-Oct-2013
# 
# asg5:
# Create mad libs page:  let user choose one of three stories, then create an array of 
#  regex matches with appropriate story.
# Print fill-in-the-blanks-form, each text input box adjacent to matches array value 
#  which describes the suggested input.
# Insert users' input into story by replacing the matched values with the user values,
#  and print a results page that includes the original story, the story with 
#  substitutions made, the total match, and a button to allow user to go back to 
#  initial page and choose another story

//echo phpinfo();
ini_set ("display_errors", "1");
error_reporting(E_ALL);

require_once "asg5funx.php";
printDocHeading("asg5.css", "Mad Libs");

echo "<body>\n
		
	<div class='heading'>\n
		<h1><strong>MAD LIBS</strong></h1>\n
	</div>\n
		
    <div class='back2CShome'>\n
        <a href='http://kristin.create.stedwards.edu/cosc2328/index.php'>Back to CS Home</a>\n
    </div>\n

    <div class='content'>\n
	<!--  start of content  -->\n";

if(empty($_POST))
{
	showStorySelectionForm();	
}
else if(isset($_POST['submitStorySelection']))
{
	# if story choice has been made, display appropriate form for user to fill in blanks
	showFillInBlanksForm();
}
else if(isset($_POST['submitMadLibsForm']))
{
   	processMadLibsForm();		
}
else if(isset($_POST['submitStartOver']))
{
	# if user clicks on submitStartOver button ("Start Over") on either 
	# fill-in-the-blanks page or mad libs results page, display story selection page 
	# so they can choose new story
	showStorySelectionForm();
}	

echo "	</div>\n	<!--  end of content  -->\n";
printDocFooter();
echo "</body>\n</html>\n";
?>
