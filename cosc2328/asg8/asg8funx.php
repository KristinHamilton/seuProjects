<?php

/* [asg8funx.php]
 * functions required for asg8.php
 * 
 * Kristin Hamilton
 * cosc2328
 * asg8funx.php created: Sat, 07-Dec-2013
 * asg7funx.php created: Thurs, 07-Nov-2013
 */

/* asg8 functions added:
 * printDocHeadingJS
 * showLoginForm
 * validateUser
 * generateLoginKey
 * logoutUser
 * showSubmitLogout
 * 
 * asg8js.js: showPicture
 */
 
/* asg7 functions:
 * printDocHeading
 * showUploadForm
 * checkUploadForm
 * addImg2db
 * makeThumbnail
 * showAllImages
 * showCategoryChoiceForm
 * showCategoryImages
 * showSubmitAddAnotherImg
 * showSubmitReturn2db
 * printDocFooter
 */

//echo phpinfo();
//ini_set ("display_errors", "1");
//error_reporting(E_ALL);

require "../db/mydb.php";

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
}//end printDocHeadingJS

/* *
 * [BELOW] showSubmitBypassForm()
 * creates a button that allows me to skip the upload image form and the login form
 * while editing page code
 * clicking on this button loads page with all images in database displayed
 */
function showSubmitBypassForm()
{
    $self = $_SERVER['PHP_SELF'];
    print
    "<form method='post' action='$self'><div>\n".
        "<input type='submit' name='submitBypassForm' value='I don&apos;t want to add an image today' />\n" .
    "</div></form>\n";

}//end of showSubmitBypassForm()


/* *
 * [BELOW]  showLoginForm()
 * displays login form
 * collects user input: username, password
 * calls generateLoginKey() and puts key into hidden input to be submitted
 * with username and password when login submit button is clicked
 * page will be reloaded with an errormessage displayed, but no fields
 * repopulated, if login credentials fail to be validated by validateUser()
 */
function showLoginForm($errormsg = "")
{
    //showSubmitBypassForm();
    $_SESSION['login'] = 0;
    print $errormsg;
	
    $self = $_SERVER['PHP_SELF'];
	
    print
    "<!--  start of form  -->\n".
    "<form method='post' action='$self'>\n".
    
    "  <div style='background-color:#333333;color:#FFFFCC;width:80%;'".
    "'			   margin:3%;padding:3%;float:left;border:solid gray;overflow:auto;'>\n".
    
    "    <div style='font-size:1.3em;margin:5% 0% 5% 3%;'>".
    "		 <strong>Log in to view the image database:<br /></strong>".
    "	</div>\n".
    
    "   <div></div>\n".
    
        /* username entry (text) */
        "<div style='width:70%;margin:0 1.5% 0 3%;float:left;'>Username:\n".
        "   <input type='text' name='username' value='' />\n".
        
        /* password entry (password) */
            "Password:\n".
        "   <input type='password' name='password' value='' />\n".
        "</div>\n";
    
        /* generate loginKey */
        $loginKey = generateLoginKey();
        $_SESSION['loginKey'] = $loginKey;
          
        /* submit login button and hidden field containing loginKey */
        print
        "<div style='width:10%;margin:0 0 5% 0;float:left;'>\n".
        	"<input type='hidden' name='loginKey' value='". $loginKey ."' />\n".
        	"<input type='submit' name='submitLogin' value='Log in' />\n".
        "</div>\n".
        
        "<div></div>\n".
        
      "</div>\n".
      
    "</form>\n".
    "<!--  end of form  -->\n";
 
}//end showLoginForm()

 
/* *
 * [BELOW]  validateUser()
 * validates user login form input (username and password)
 * dont need to check and see if anything has been left blank in login form,
 * because as long as there are no null usernames or passwords in the $usersArray,
 * an errormessage will already be generated since the blank field doesnt match
 * key/value in array
 * any error messages need to be as generic as possible so that no hints to login
 * are revealed;  if login credentials are not validated, call showLoginForm(),
 * but do not send back username or password to repopulate fields
 */
function validateUser()
{
    $errormsg = "<div class='errortext'>Error: Invalid credentials entered</div>\n";
    $printError = "False";
     
    $username = htmlentities($_POST['username']);
    $password = htmlentities($_POST['password']);
     
    /* create array of key/value pairs where key is username, and value is password */
    $usersArray = array(
        'kristin' => 'hamilton',
        'bowie hamilton' => 'winters coat',
        'lisa' => 'dragon',
        'seu' => 'topper'     
    );
    
    //print_r($usersArray);
    //print $_SESSION['loginKey'];
    
    /* check if username entered appears as a key in $usersArray */
    if(!array_key_exists($username, $usersArray))
    {
    	$printError = "True";	
    }
    /* check if password entered is the value at key $username in $usersArray */
    else
    {
    	if($password != $usersArray[$username])
    	{
    	    $printError = "True";
    	}
    	else  //if both $username and $password are valid
    	{
    		$_SESSION['login'] = "1";
    	}
    	
    }//end of ELSE
    
    /* if $printError="True", display $errormsg and reload login form, without 
     * repopulating form */
    if($printError == "True")
    {
    	showLoginForm($errormsg);    	
    }
    
    else
    {
    	showSubmitLogout();
    	showUploadForm();
    }
 
}//end of validateUser()

 
/* *
 * [BELOW]  generateLoginKey()
 * generates a loginKey from a string of alpha-numeric characters
 * allows us to define the state of 'logged in' as ending when 'logout'
 * submit button is clicked by user
 * hence, a user can't press 'back' in their browser to log back in
 */
function generateLoginKey()
{
 	/* define the values that function may use to generate the random string */
 	$resourceString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
 	
 	/* determine the length of $resourceString */
 	$length = strlen($resourceString);
 	
 	/* generate a random 15-character loginKey */
 	for($i = 0; $i < 15; $i++)
 	{
            /* get a random integer (max int = $length) */
            $randomPosition = rand()%$length;
 		 
            /* get one character from $resourceString at $randomPosition, and append it
             * to $loginKey */
            $loginKey .= substr($resourceString, $randomPosition, 1);
 	 
 	}//end of FOR loop
 	
 	return $loginKey;
 	
}//end of generateLoginKey()
 
 
/* *
 * [BELOW]  showSubmitLogout()
 * creates submit button with form
 * clicking button allows user to log out of image database
 */
function showSubmitLogout()
{
    $self = $_SERVER['PHP_SELF'];
    print
    "<!--  start of form  -->\n".
    "<form method='post' action='$self'>\n".
    "  <div class='statementcontent' style='width:40%;float:right;'>\n".
    "    You are currently logged in to the database  ".
    "    <input type='submit' name='submitLogout' value='Log out' />\n".
    "  </div>\n".
    "</form>\n".
    "<!--  end of form  -->\n";
 	
}//end of showSubmitLogout()


/* *
 * [BELOW]  logoutUser()
 * sets session login value to 0 ('not logged in')
 * calls generateLoginKey() to generate a new key so user can't hit 'back'
 * in browser and be able to log back in
 */
function logoutUser()
{
    /* set session login value to 0 ('not currently logged in') */
    $_SESSION['login'] = 0;
 	
    /* generate a new loginKey and set session loginKey value to the new value */
    $_SESSION['loginKey'] = generateLoginKey();
 	
}//end of logoutUser()
 
 
/* * 
 * [BELOW]  showUploadForm();
 * displays form that collects user input for image upload:
 * 	$filename= image filename.
 * 	$imgDesc = image description.
 *  $imgCat  = image categor(ies).
 * one (submit) button (value="submitForm"):  posts form data
 */
function showUploadForm($filename="", $imgDesc="", $imgCat="")
{		
	$self = $_SERVER['PHP_SELF'];
	
print
"		<div class='headingcontent'>\n".
"			<h2><strong>Upload an image to the database</strong></h2>\n".
"		</div>\n";

print
"	<!--  START OF FORM  -->\n".
"	<form method='post' action='$self' enctype='multipart/form-data'>\n".

"	<table cellspacing='15'>\n".
"		<tr>\n".
"			<td>\n".
"				<div class='statement'><strong>Select image file:</strong></div>\n".
"			</td>\n".
"			<td>\n".
"				<input type='file' name='filename' value='". $filename ."' />\n".
"			</td>\n".
"		</tr>\n".
"		<tr>\n".
"			<td>\n".
"				<div class='statement'><strong>Enter an image description:</strong>" .
"				</div>\n".
"			</td>\n".
"			<td>\n".
"				<input type='text' name='imgDesc' value='". $imgDesc."' />\n".				
"			</td>\n".
"		</tr>\n".
"		<tr>\n".
"			<td>\n".
"				<div class='statement'><strong>Select one or more<br />categories:" . 
"									  </strong>\n".
"				</div>\n".
"			</td>\n".
"			<td>\n";

		$db = adodbConnect();
		$categoryQuery = "SELECT * FROM category";
		$categoryResult = $db->EXECUTE($categoryQuery) or die("Error selecting cat");
		
		/* print category checkboxes in a single column */
		while($categoryRow = $categoryResult->FetchRow())
		{
		print
		"	<input type='checkbox' name='imgCat[]' " .
		"		  value='". $categoryRow['catID'] ."' />";
		
		print $categoryRow['catDesc'] ."<br />\n";

		}//end of WHILE loop
print
"			</td>\n".
"		</tr>\n".
"		<tr>\n".
"			<td>\n";
                showSubmitBypassForm();
print
"			</td>\n".
"			<td>\n".
"				<input type='submit' name='submitForm' value='Submit' />\n".
"			</td>\n".
"		</tr>\n".
"	</table>\n".

"	</form>\n".
"	<!-- END OF FORM  -->\n";

}//end showUploadForm()


/* *
 * [BELOW]  checkUploadForm()
 * if user clicks submit button on first page, validate the file data:
 *   check that $filename is not blank
 *   check $filename for:  extension (jpg/jpeg), mime type (image/jpeg)
 *   check that filesize < 2MB; check that file hasn't already been uploaded
 *   check that $filename doesnt already exist in temp directory
 *   check $imgDesc and $imgCat for html;
 *   check that neither $imgDesc (nor $imgCat) is blank
 *   check that at least one category $imgCat has been selected
 * if any data is found to be invalid, display $errormsg; redisplay and repopulate form
 * if $errormsg has not been generated, move $filename to temp directory, and 
 *   call addImg2db($filename, $fullContents, $imgDesc, $imgCat)
 */
function checkUploadForm()
{	
	/* check for HTML etc */
	$filename = $_FILES['filename'];			//array of files
	$basename = $filename['name'];				//userfilename(.extension)
	$imgDesc = htmlentities($_POST['imgDesc']);	//string
	$imgCat = $_POST['imgCat'];					//array of category choices (1+)
	
	$extension = pathinfo($basename, PATHINFO_EXTENSION);
	/* inspects extension without doing String parsing/replacing/etc.
	 * PATHINFO_EXTENSION restricts the info returned by the pathinfo() fn to
	 * just the file extension
	 */
	
	$allowedExtensions = array("jpg", "JPG", "jpeg", "JPEG");
	//the extension returned by pathinfo() will be compared to the array of
	//file extensions that we have decided this script will accept
	
	/* check $filename:  if blank, show $errormsg */
	if(empty($basename))
	{
		//$filename = "";
		$errormsg .= "<div class='errortext'>Please enter a filename</div>\n";
	
	}//end of IF ($filename == "")
	
	/* check that file extension is either jpg or jpeg */
	else if(!in_array($extension, $allowedExtensions))
	{
		//$filename = "";
		$errormsg .= "<div class='errortext'>" .
				"Image extension must be either jpg or jpeg.  " .
				"Please select another image</div>\n";
	
	}//end of IF(!in_array($extension, $allowedExtensions))
	
	/* reads first few bytes of code in the actual file, to see whether
	 * the info contained therein is consistent with known image types/signatures
	 * for list of imagetype constants and values, see:
	 * <http://php.net/manual/en/function.exif-imagetype.php>
	 */
	/* check that file type is jpg/jpeg */
/*	else if(exif_imagetype($basename) != IMAGETYPE_JPEG)
	{
		//$filename = "";
		$errormsg .= "<div class='errortext'>" .
				"Please select an image with a jpg/jpeg file type</div>\n";
	
	}//end of IF($imgType != IMAGETYPE_JPEG)
*/	
	/* check that filesize is less than 2MB */
	/*
	 * ~>@: supress error that occurs if filesize() fails)
	* ~>$basename: only want to check the size of the individual file, not the entire
	* $_FILES array (for that, $filename would be used here)
	* ~>clearstatcache(): since the results of filesize() fn are cached, according to
	* php documentation, clear the cache so that if user modifies $filename and tries
	* to upload it again, the filesize wont be affected by data cached during previous
	* upload attempts
	*/
	else if(@filesize($basename) > 2000000)
	{
		clearstatcache();
		//$filename = "";
		$errormsg .= "<div class='errortext'>File size exceeds 2MB.  " .
				"Please select a smaller image</div>\n";
	
	}//end of ELSE IF(@filesize($filename) > 2000000)
	
	
	/* check that file hasnt already been uploaded */
	else if(is_uploaded_file($basename))
	{
		//$filename = "";
		$errormsg .= "<div class='errortext'>" .
				"Image already uploaded.  Please choose another image</div>\n";
	
	}//end of IF($imgType != IMAGETYPE_JPEG)
	
	/* check if file already exists */
	#skipping this step this time, just to simplify the code and reduce the passing
	#around of filename/basename variables
	#usually you need to include something like this for security of files, so that
	#uploaded user files dont have free reign to write over extant files in your
	#directory
	/*	else if(file_exists("../temp/" . $basename))
	 {
	 //$filename = "";
	 $errormsg .= "<div class='errortext'>" .
	 "Image already exists.  Please choose another image</div>\n";
	
	 }//end of IF($imgType != IMAGETYPE_JPEG)
	 */
	
  //check $imgDesc:  if blank, show error message

	if($imgDesc == "")
	{
		$imgDesc = "";
		$errormsg .= "<div class='errortext'>Please enter an image description</div>\n";

	}

  //check $imgCat:  if no category is selected, show error message

	if($imgCat == "")
	{
		$imgCat = "";
		$errormsg .= "<div class='errortext'>Please select at least one image category". 
					 "</div>\n";

	}
    
  /* *
   * if an error message has been generated, print $errormsg, and resend data to
   * showForm();  otherwise, call addImg2db()
   */
	if($errormsg)
	{
		print $errormsg;
		showUploadForm($filename, $imgDesc, $imgCat);
		
	}//end of IF($errormsg)
	
	else //if (!errormsg)
	{
		$filepath = "../temp/tempJPG.jpg";
		$fullContents = 
			chunk_split(base64_encode(file_get_contents($filename['tmp_name'])));
		
		/* move file to temp directory */
		move_uploaded_file($filename['tmp_name'], $filepath); //deletes the temp version
		chmod($filepath, 0755);
		
		addImg2db($fullContents, $filepath, $imgDesc, $imgCat);
		
	}//end of ELSE IF(!$errormsg)
	
}//end of checkUploadForm()


/* *
 * [BELOW] addImg2db()
 * thumbnail image is made within this function via a call to makeThumbnail()
 * prepares and inserts data into MySQL tables:
 * $fullContents, $thumbContents, $imgDesc -> image table
 * $imgID, $catID -> image2category table
 */
function addImg2db($fullContents, $filepath, $imgDesc, $imgCat)
{
	/* CREATE THUMBNAIL */
	$thumbContents = makeThumbnail($filepath);
	
	$imgDesc = stripslashes($imgDesc);
	$imgDesc = addslashes($imgDesc);

	$db = adodbConnect();
	$imageQuery = "INSERT INTO image(fullImg, thumbImg, imgDesc)" .
		   "VALUES('$fullContents', '$thumbContents', '$imgDesc')";

	$imageResult = $db->EXECUTE($imageQuery) or die("Error inserting into image table");

	$lastID = $db->insert_id();
	//id of last row INSERT INTO was performed on during $imageQuery (immediately above)
	//use in $bridgeQuery to populate image2category table (a few lines down)

	/* populate image2category bridge table */
	//there may be more than one category selected for one image
	foreach($imgCat as $cat)
	{
		$bridgeQuery = "INSERT INTO image2category(imgID, catID) " .
										"VALUES('$lastID', '$cat')";
		$bridgeResult = $db->EXECUTE($bridgeQuery) or die("Error inserting " .
												 "into image2category table");
	
	}//end of FOREACH loop
	
	showAllImages();
	
}//end of addImg2db()


/* *
 * [BELOW]  makeThumbnail()
 * creates thumbnail image from full image:
 * gets dimensions of original image, which is stored in tempJPG.jpg in
 * temp directory
 * then determines what the orientation is
 * assigns width and height, maintaining aspect ratio, for thumbnail image
 * maximum length of one side of thumbImg = 100 px
 * creates the thumbnail, saving it to tempThumbJPG.jpg in temp directory,
 * then reads the contents of that file into variable $thumbContents,
 * and returns $thumbContents to addImg2db() to be inserted into database
 */
function makeThumbnail($filepath)
{
  /* produce original img so its height and width may be used to make thumbnail */
	$img = imagecreatefromjpeg($filepath);

  /* get dimensions of original image */
	$width = imagesx($img);
	$height = imagesy($img);
	
  /* CALCULATE THUMBNAIL DIMS BASED ON ORIENTATION OF ORIGINAL IMAGE $IMG */
  	/* landscape orientation */
	if($height < $width)
	{
		$newWd = 100;
		$newHt = floor($height*($newWd/$width));
		
	}//end of IF(landscape orientation)

  	/* portrait orientation */
	else if($height > $width)
	{
		$newHt = 100;
		$newWd = floor($width*($newHt/$height));

	}//end of ELSE IF(portrait orientation)

  	/* image is square */
	else //if($height == $width)
	{
		$newHt = 100;
		$newWd = 100;

	}//end of ELSE(if(square))

  	/* create resource image $thumb with new dimensions */
	$thumb = imagecreatetruecolor($newWd, $newHt);
	
  	/* copy $img, scaling its dims down to $thumb dims */
	imagecopyresized($thumb, $img, 0, 0, 0, 0, $newWd, $newHt, $width, $height);

	/* output thumb img to $thumbpath, then read contents of file into $thumbContents */
	imagejpeg($thumb, "../temp/tempThumbJPG.jpg");
	$thumbContents = 
		chunk_split(base64_encode(file_get_contents("../temp/tempThumbJPG.jpg")));
	
	/* clear up memory */
	imagedestroy($thumb);
	
	return $thumbContents;
	
}//end of makeThumbnail()


/* *
 * [BELOW]  showAllImages()
 * prints thumbnails of all imgs stored in database
 * alongside each thumbnail are the image description and the image categor(ies)
 * clicking on a given thumbnail will load the full sized image by itself in the window
 */
function showAllImages()
{
    $self = $_SERVER['PHP_SELF'];
	$db = adodbConnect();
	$imageQuery = "SELECT imgID, imgDesc FROM image";
	$imageResult = $db->EXECUTE($imageQuery) or die("Error selecting columns " . 
													  		"from image table");
	/* get number of images currently in database */
	$totalImgsInDB = $imageResult->RecordCount();

print"<div style='width:97%;margin:1%;float:left;'>\n" .
"	     <div class='headingcontent'><h2><strong>Viewing all images</strong></h2>".
"		 </div>\n".

"	     <div class='statementcontent'><em>There are currently ". $totalImgsInDB .
	          " images in the database</em>".
"	     </div>\n" .
"     </div>\n";

print"<div style='background-color:#999999;width:97%;margin:1%;float:left;'>\n";
	showCategoryChoiceForm();
	print "</div>\n";
	
		/* unlike in asg7, images will be displayed in one column (right side of page)
		 * on left side of page will be large div for full sized img to appear
		 * the thumbnail images will appear in a scroll-box big enough to show three
		 * thumbnail rows at a time
		 */
		print "<div style='height:500px;width:97%;margin:1%;float:left;'>\n";//big everything div
		print
		"<div class='placeholdertext' style='height:100%;width:40.3226%;'".
		"'		background-color:#333333;border:solid gray;float:left;overflow:auto;'>".
		"	<div id='fullDiv' class='placeholdertext'>".
				"click on a thumbnail to see the full image here".
		"	</div>".
		"</div>\n";
		print
		"<div style='height:100%;width:55%;background-color:#333333;border:solid gray;'".
		"'			 float:right;overflow:auto;'>\n";


	while($imageRow = $imageResult->FetchRow())
	{		
		$imgID = $imageRow['imgID'];
		$imgDesc = $imageRow['imgDesc'];
		$imgTagThumb = "<img src='imageView.php?id=$imgID&view=0' " .
							"alt='thumbnail image' />";
				
		$bridgeQuery = "SELECT * FROM image2category WHERE imgID=". $imgID;
		$bridgeResult = $db->EXECUTE($bridgeQuery) or die("Error selecting catID");

		print
	/* div for one image row */
	" <div style='height:25%;width:96.25%;background-color:#FFFFEE;'".
	"			 'padding:1%;margin:1%;float:left;border:solid gray;'>\n".
	/* div containing "cell" with thumbnail img inside */
	"  	<div style='height:100%;width:17.9211%;float:left;overflow:auto;'>\n".
	"       <a onclick='showPicture(". $imgID .")'>". $imgTagThumb ."</a></td>\n".
	"   </div>\n";
	print
	   /* space between img and img info divs */
	"  <div style='height:100%;width:0.896057%;float:left;'>".
	"  </div>\n".
	
	   /* "cell" containing imgDesc and categories */
	"  <div style='height:100%;width:81.1828%;float:left;overflow:auto;'>\n".
	
			/* top half of "cell" reserved for imgDesc */
	"		<div class='statement' style='height:55%;overflow:auto;'><strong>" .
				"Description:  </strong>" .
	"			<div class='condensedcontent'>".
					$imgDesc .
	"			</div>\n".
	"		</div>\n".
	
			/* lower half of "cell" reserved for categories */
	"		<div class='statement' style='height:44%;overflow:auto;'><strong>".
				"Categories:  </strong>".
	"		  	<div class='condensedcontent'>\n";

					/* fetch rows (get catIDs) one by one from query results */
					while($bridgeRow = $bridgeResult->FetchRow())
					{
						$categoryQuery = "SELECT catDesc FROM category " .
						  				 "WHERE catID=". $bridgeRow['catID'];
						$categoryResult = $db->EXECUTE($categoryQuery) or die(
												"Error selecting category description");
						
						/* fetch catDesc from query results */
						while($categoryRow = $categoryResult->FetchRow())
						{
							$catDesc = $categoryRow['catDesc'];
						}

						print "  " . $catDesc . ".\n";
					
					}//end of WHILE($bridgeRow) loop
		
					print
					"<div style='float:right;'>\n";//div for remove button
		      			showSubmitRemoveImage($imgID);
					print
					"</div>\n".			//close div for remove button
				"</div>\n".				//close div for catDescs		
			"</div>\n".   				//close div for Categories heading
	    "</div>\n".    					//close div for image info "cell"
	"</div>\n";     					//close div for black-bordered one-image box
	    	
	}//end of WHILE($imageRow) loop
	
	print" </div>\n";      //close div for large scroll-box
	print"</div>\n";       //close div for large everything div

	print"<div style='width:100%;margin:1.5%;float:left;'>\n";
	            showSubmitAddAnotherImg();
	print"</div>\n";

}//end of showAllImages()


/* *
 * [BELOW]  removeImageFromDB();()
 * when remove button is clicked, that image will be removed from the database:
 * tables image and image2category
 * then, a notice that image has been removed will be displayed
 * and the page will be reloaded to show all images now in database
 */
function removeImageFromDB()
{
	$imgID_X = htmlentities($_POST['imgID']);
	
	$db = adodbConnect();
	$imgRemQuery = "DELETE FROM image WHERE imgID=" .$imgID_X;
	$db->EXECUTE($imgRemQuery) or die("Error removing image from 1st table");
	$bridgeRemQuery = "DELETE FROM image2category WHERE imgID='". $imgID_X ."'";
	$db->EXECUTE($bridgeRemQuery) or die("Error removing image from 2nd table");
	
    print "<div id='noticeDiv' class='noticemsg'>Image ". $imgID_X ." has been removed ".
	          "from database</div>\n";
    
	showAllImages();

}//end of removeImageFromDB()

/* *
 * [BELOW]  showCategoryChoiceForm()
 * creates a form with a drop-down menu where user may choose from among the image
 * categories a category of images to view
 * also creates a submit button:  when they have chosen a category, button will submit
 * their category choice
 */
function showCategoryChoiceForm()
{
	$self = $_SERVER['PHP_SELF'];	
print
"	<!--  START OF FORM  -->\n".
"	<form method='post' action='$self'>\n".
"	  <div>\n".
"		<table cellspacing='15'>\n".
"			<tr>\n".
"				<td>\n".
"					<div class='statementcontentalt'>" .
"						<strong>Select a category of images to view:</strong>" .
"					</div>\n".
"				</td>\n".
"				<td>\n".
"					<select name='categoryChoice'>\n";//defines start of drop down menu

	/* retrieve category descriptions from db to populate drop down menu options */
	$db = adodbConnect();
	$categoryQuery = "SELECT * FROM category";
	$categoryResult = $db->EXECUTE($categoryQuery) or die("Error selecting cat");

	while($categoryRow = $categoryResult->FetchRow())
	{
	print
	"	<option value='". $categoryRow['catID'] ."'>" .
						  $categoryRow['catDesc'] . "</option>\n";
	}//end of WHILE($categoryRow) loop
print
"					</select>\n".//end of drop down menu
"				</td>\n".
"				<td>\n".
"					<input type='submit' name='submitCategoryChoice' " .
"									    value='View category' />\n" .
"				</td>\n".
"			</tr>\n".	
"		</table>\n".
"     </div>\n".
"	</form>\n".
"	<!--  END OF FORM  -->\n" ;
	
}//end showCategoryChoiceForm()


/* *
 * [BELOW]  showCategoryImages()
 * does the same thing as showAllImages(), except showCategoryImages() first filters
 * the images to include only those within a certain (user-selected) category, then:
 * prints thumbnails of all imgs stored in database
 * alongside each thumbnail are the image description and the image categor(ies)
 * clicking on a given thumbnail will load the full sized image by itself in the window
 */
function showCategoryImages()
{
	$catChoice = $_POST['categoryChoice'];
	
	$db = adodbConnect();
	$categoryQuery1 = "SELECT catDesc FROM category WHERE catID='". $catChoice ."'";
	$categoryResult1 = $db->EXECUTE($categoryQuery1) or die("Error selecting category");
	$bridgeQuery1 = "SELECT imgID from image2category WHERE catID=". $catChoice;
	$bridgeResult1 = $db->EXECUTE($bridgeQuery1) or die("Error selecting image ID");

	$totalImgsInCategory = $bridgeResult1->RecordCount();
	
	$count = 0;

	while($catDescRow = $categoryResult1->FetchRow())
	{
	    print "<div style='width:97%;margin:1.5%;float:left;'>\n";
	    print
	    "<div class='headingcontent'><h2><strong>Viewing images in category: ". 
		    $catDescRow['catDesc'] ."</strong></h2></div>\n".
		"<div class='statementcontent'><em>There are currently ". $totalImgsInCategory .
		    " images in the ". $catDescRow['catDesc'] ." category</em></div>\n";
	    print "</div>\n";
	}

	    print"<div style='background-color:#999999;width:97%;margin:1.5%;float:left;'>\n";
		        showCategoryChoiceForm();
	    print "</div>\n";
	
		/* unlike in asg7, images will be displayed in one column (right side of page)
		 * on left side of page will be large div for full sized img to appear
		 * the thumbnail images will appear in a scroll-box big enough to show three
		 * thumbnail rows at a time
		 */
		print "<div style='height:500px;width:97%;margin:1%;float:left;'>\n";//big everything div
		print "<div id='fullDiv' style='height:100%;width:40.3226%;background-color:#333333;border:solid gray;float:left;overflow:auto;'></div>\n";
		print "<div style='height:100%;width:55%;background-color:#333333;border:solid gray;float:right;overflow:auto;'>\n";

	    /* fetch the imgID from the bridge query result */
	    while($row = $bridgeResult1->FetchRow())
	    {
		    $imgID1 = $row['imgID'];
		    $imageQuery1 = "SELECT imgID,imgDesc FROM image WHERE imgID=" . $imgID1;
		    $imageResult1 = $db->EXECUTE($imageQuery1) or die("Error selecting from table");

		    while($imageRow = $imageResult1->FetchRow())
		    {		
		        $imgID = $imageRow['imgID'];
		        $imgDesc = $imageRow['imgDesc'];
		        $imgTagFull = "showPicture($imgID)";
		        $imgTagThumb = "<img src='imageView.php?id=$imgID&view=0' " .
							   "alt='thumbnail image' />";
				
		        $bridgeQuery = "SELECT * FROM image2category WHERE imgID=". $imgID;
		        $bridgeResult = $db->EXECUTE($bridgeQuery) or die("Error selecting catID");
		
	print
	/* div for one image row */
	"<div style='height:25%;width:96.25%;background-color:#FFFFEE;padding:1%;margin:1%;float:left;border:solid gray;'>\n".
	/* div containing "cell" with thumbnail img inside */
	"  <div style='height:100%;width:17.9211%;float:left;overflow:auto;'>\n".
	"    <a onclick='showPicture($imgID)'>". $imgTagThumb ."</a></td>\n".
	"  </div>\n";
	print
	   /* space between img and img info divs */
	"  <div style='height:100%;width:0.896057%;float:left;'></div>\n".
	   /* "cell" containing imgDesc and categories */
	"  <div style='height:100%;width:81.1828%;float:left;overflow:auto;'>\n".
						/* top half of "cell" reserved for imgDesc */
	"					<div class='statement' style='height:55%;overflow:auto;'><strong>" .
						    "Description:  </strong>" .
	"						  <div class='condensedcontent'>".
							    $imgDesc .
	"						  </div>\n".
	"					</div>\n".
						/* lower half of "cell" reserved for categories */
	"					<div class='statement' style='height:44%;overflow:auto;'><strong>".
						    "Categories:  </strong>".
	"						  <div class='condensedcontent'>\n";

		        /* fetch rows (get catIDs) one by one from query results */
		        while($bridgeRow = $bridgeResult->FetchRow())
		        {
			        $categoryQuery = "SELECT catDesc FROM category " .
						             "WHERE catID=". $bridgeRow['catID'];
			        $categoryResult = $db->EXECUTE($categoryQuery) or die("Error selecting " .
														 	          "category description");
			        /* fetch catDesc from query results */
			        while($categoryRow = $categoryResult->FetchRow())
			        {
				        $catDesc = $categoryRow['catDesc'];

			        }//end of WHILE($categoryRow) loop

			        print "  " . $catDesc . ".\n";
					
		        }//end of WHILE($bridgeRow) loop
		
		    print " <div style='float:right;'>\n";//div for remove button
		               showSubmitRemoveImage($imgID);
		    print " </div>\n";//close div for remove button
		
		    print
	        "     </div>\n".  //close div for catDescs		
            "    </div>\n".   //close div for Categories heading
            "   </div>\n".    //close div for image info "cell"
            "  </div>\n";     //close div for black-bordered one-image box
	
	    }//end of WHILE($imageRow) loop
	
    }//end of WHILE($rowImgID) loop

  	print" </div>\n";    //close div for large scroll-box
	print"</div>\n";     //close div for large everything div
  
	print "<div style='width:97%;margin:1.5%;float:left;'>";	
	          showSubmitReturn2db();
	          showSubmitAddAnotherImg();
	print "</div>\n";
	
}//end of showCategoryImages()


/* *
 * [BELOW]  showSubmitReturn2db()
 * creates form with button that allows user to return to db when viewing
 * imgs in selected category
 */
function showSubmitReturn2db()
{
	$self = $_SERVER['PHP_SELF'];
print
"	<form method='post' action='$self'>\n" .
"		<input type='submit' name='submitReturn2db' " .
"							value='Return to database' />\n" .
"	</form>\n";
	
}//end of showSubmitReturn2db()


/* *
 * [BELOW]  showSubmitAddAnotherImg()
 * creates button that allows user to return to upload form and upload another image
 */
function showSubmitAddAnotherImg()
{
	$self = $_SERVER['PHP_SELF'];
print
"	<form method='post' action='$self'>\n" .
"		<div>\n".
"			<input type='submit' name='submitAddAnotherImg' " .
"			 				    value='Add another image' />\n" .
"		</div>\n".
"	</form>\n";

}//end of showSubmitAddAnotherImg()


/* *
 * [BELOW]  showSubmitRemoveImage()
 * creates button that allows user to remove an image from the database
 * there is also a hidden input that stores the imgID associated with a certain
 * instance of the 'remove' button
 */
 function showSubmitRemoveImage($imgID)
 {
	$self = $_SERVER['PHP_SELF'];
 print
 "  <!--  start of form  -->\n".
 "  <form method='post' action='$self'>\n".
 "    <input type='submit' name='submitRemoveImage' value='Remove' />\n".
 "    <input type='hidden' name='imgID' value='". $imgID ."' />\n".
 "  </form>\n".
 "  <!--  end of form  -->\n";
 
 }//end of showSubmitRemoveImage()


/* *
 *  [BELOW]  printDocFooter()
 * Prints DOCUMENT FOOTER
 * 
 * Footer includes the following
 * 	SEU-cosc2328-web programming-fall2013
 * 	page last updated with time and date stamp
 * 	link to HTML and CSS validators
 * 	link to stedwards.edu with copyright symbol
 */
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