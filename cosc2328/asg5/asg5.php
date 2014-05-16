<?php

//[asg5.php]
//
//Kristin Hamilton
//cosc2328
//19-Oct-2013

/*asg5-2.php:
Create mad libs page:  let user choose one of three stories, then create an array of regex matches with appropriate story.
Print fill-in-the-blanks-form, each text input box adjacent to matches array value which describes the suggested input.
Insert users' input into story by replacing the matched values with the user values,
and print a results page that includes the original story, the story with substitutions made,
the total match, and a button to allow user to go back to initial page and choose another story*/

//echo phpinfo();
//ini_set ("display_errors", "1");
//error_reporting(E_ALL);

require "asg5funx.php";

printDocHeading("asg5css.css", "Mad Libs");
#prints document heading

print
"<body>\n".

"	<div class='heading'>\n".
"		<h1><strong>MAD LIBS</strong></h1>\n".
"	</div>\n".

"    <div class='back2CShome'>" .
"        <a href='http://www.cs.stedwards.edu/~khamilt2/cosc2328/'>Back to CS Home</a>" .
"    </div>\n".

"	<div class='content'>\n".
"	<!--  start of content  -->\n";

	    //if nothing is posted, load story selection page
		if(empty($_POST))
		{
		    //Show story selection page:  User input collected with radio buttons
		    //Print 1 button: submitStorySelection ("Submit")
			showStorySelectionForm();	
		}

	    //if story choice has been made, display appropriate form for user to fill in the blanks
	    //generate form with table: column1, matches (excluding []). column2, text input boxes
	    //print 3 buttons: "Reset", submitMadLibsForm ("Submit"), submitStartOver ("Start Over")
		else if ($_POST['submitStorySelection'])
		{
			showFillInBlanksForm();

		}

	    //if user clicks on submitMadlibsForm button ("Submit")
	    //	 Check form data:  the "required" attribute in the text input tags in showFillInBlanksForm() provides error msg if left blank in most current browsers(not supported in IE9)
	    //     IE9 currently is the most recent IE version for those using Windows Vista OS;  for those using Windows 7 OS, the current IE version is IE10 or IE11
	    //  If user is currently using IE9 or earlier to view page, Mad Libs Results page will go ahead and load, but just show empty brackets where the input should have been
	    //once all field are populated, processMadLibsForm() is called, which checks data for HTML.
	    //once all fields are populated with good data, show mad libs results page:
	    //	mad libs results page:  print original story, print story with substitutions, print total match, and print "Start Over" submit button
		else if ($_POST['submitMadLibsForm'])
		{
		    	processMadLibsForm();		
		}

	    //if user clicks on submitStartOver button ("Start Over") on either fill-in-the-blanks page or mad libs results page,
	    //display story selection page so they can choose new story
		else if ($_POST['submitStartOver'])
		{
			showStorySelectionForm();
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



