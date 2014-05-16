<?php

/* [asg7funx.php]
 * functions required for asg7.php
 * 
 * Kristin Hamilton
 * cosc2328
 * created: Thurs, 07-Nov-2013
 */

/* functions:  Table of Contents
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

require "../db/mydb.php";

/* *
 * [BELOW] printDocHeading ()
 * Prints DOCUMENT HEADING block
 * args:
 * 	$stylesheet - name of stylesheet relative to this page
 *	$title - title of page 
 *
 * Heading includes the following
 * 	documentation info
 * 	<html> opening tag only
 *	<meta /> tag
 *	<head> and </head> tags 
 * 	stylesheet link
 *	<title> and </title> tags
 */
function printDocHeading($stylesheet, $title)
{
print
'	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" '				. "\n" .
'		"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> '				. "\n" .
'	<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">'	. "\n" .
'	<head> '																. "\n" .
'	<meta http-equiv="Content-type" content="text/html;charset=UTF-8" />'	. "\n" .
'	<link rel="STYLESHEET" href="' . $stylesheet . '"  type="text/css" />'	. "\n" .
'	<title>' . "\n" .$title . "\n" .'</title> '								. "\n" .
'	</head> '																. "\n" ;
}//end of printDocHeading()


/* *
 * [BELOW] showSubmitBypassForm()
 * creates a button that allows me to skip the upload image screen while editing
 * page code
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
 * [BELOW]  showUploadForm();
 * displays form that collects user input for image upload:
 * 	$filename= image filename.
 * 	$imgDesc = image description.
 *  $imgCat  = image categor(ies).
 * one (submit) button (value="submitForm"):  posts form data
 */
function showUploadForm($filename="", $imgDesc="", $imgCat="")
{	
	//showSubmitBypassForm();
	
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
		$fullContents = chunk_split(base64_encode(file_get_contents($filename['tmp_name'])));
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
	$thumbContents = chunk_split(base64_encode(file_get_contents("../temp/tempThumbJPG.jpg")));
	
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
	$db = adodbConnect();
	$imageQuery = "SELECT imgID, imgDesc FROM image";
	$imageResult = $db->EXECUTE($imageQuery) or die("Error selecting columns " . 
													  		"from image table");
	/* get number of images currently in database */
	$totalImgsInDB = $imageResult->RecordCount();

print"<div style='width:97%;margin:1.5%;float:left;'>\n" .
"	     <div class='headingcontent'><h2><strong>Viewing all images</strong></h2></div>\n".
"	     <div class='statementcontent'><em>There are currently ". $totalImgsInDB .
	          " images in the database</em></div>\n" .
"     </div>\n";

print"<div style='background-color:#999999;width:97%;margin:1.5%;float:left;'>\n";
	showCategoryChoiceForm();
	print "</div>\n";

	/* keep track of how many images have been printed out so far with $count;
	 * will start a new "row" (actually a div) when the # of imgs shown is an
	 * even number/integer
	 */
    $count = 0;

	while($imageRow = $imageResult->FetchRow())
	{
		$count++;
		
		$imgID = $imageRow['imgID'];
		$imgDesc = $imageRow['imgDesc'];
		$imgTagFull = "imageView.php?id=$imgID&view=1";
		$imgTagThumb = "<img src='imageView.php?id=$imgID&view=0' " .
							"alt='thumbnail image' />";
				
		$bridgeQuery = "SELECT * FROM image2category WHERE imgID=". $imgID;
		$bridgeResult = $db->EXECUTE($bridgeQuery) or die("Error selecting catID");
		
		/* if $count currently holds an odd value,
		 * start a new "row" so that images are displayed in a two-column layout
		 */
		if($count % 2 == 1)//if the remainder when $count is divided by 2 is zero
		{
			print
			"<div style='width:97%;margin:1.5%;float:left;'>".
			"\n";
		}
		
	print
	/* div for one image */
	"<div style='height:105px;width:45%;padding:1%;margin:1%;float:left;border:solid black;'>\n".
	/* div containing "cell" with thumbnail img inside */
	"  <div style='height:100%;width:17.9211%;float:left;overflow:auto;'>\n".
	"    <a href='". $imgTagFull ."'>". $imgTagThumb ."</a></td>\n".
	"  </div>\n";
	print
	   /* space between img and img info divs */
	"  <div style='height:100%;width:0.896057%;float:left;'></div>\n".
	   /* "cell" containing imgDesc and categories */
	"  <div style='height:100%;width:81.1828%;float:left;overflow:auto;'>\n".
						/* top half of "cell" reserved for imgDesc */
	"					<div class='statement' style='height:60%;overflow:auto;'><strong>" .
						    "Description:  </strong>" .
	"						  <div class='condensedcontent'>".
							    $imgDesc .
	"						  </div>\n".
	"					</div>\n".
						/* lower half of "cell" reserved for categories */
	"					<div class='statement' style='height:40%;overflow:auto;'><strong>".
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

	    print
	    "     </div>\n".  //close div for condensedcontent class
	    "    </div>\n".   //close div for statement class
	    "   </div>\n".    //close div for image info "cell"
	    "  </div>\n";     //close div for black-bordered row
	
	    /* print an extra closing div tag if this is the end of a row */
	    if($count % 2 == 0)
	    {
		    print "</div>\n";
	    }
	
	}//end of WHILE($imageRow) loop

	print"<div style='width:100%;margin:1.5%;float:left;'>\n";
	            showSubmitAddAnotherImg();
	print "</div>\n";

}//end of showAllImages()


/* [BELOW]  showCategoryChoiceForm()
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


/* [BELOW]  showCategoryImages()
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
		print"<div style='width:97%;margin:1.5%;float:left;'>\n";
	print
	"	<div class='headingcontent'><h2><strong>Viewing images in category: ". 
		$catDescRow['catDesc'] ."</strong></h2></div>\n".
		
	"	<div class='statementcontent'><em>There are currently ". $totalImgsInCategory .
		" images in the ". $catDescRow['catDesc'] ." category</em></div>\n";
	print "</div>\n";
	}

	print"<div style='background-color:#999999;width:97%;margin:1.5%;float:left;'>\n";
		showCategoryChoiceForm();
	print "</div>\n";
	
	/* fetch the imgID from the bridge query result */
	while($row = $bridgeResult1->FetchRow())
	{
		$imgID1 = $row['imgID'];
		$imageQuery1 = "SELECT imgID,imgDesc FROM image WHERE imgID=" . $imgID1;
		$imageResult1 = $db->EXECUTE($imageQuery1) or die("Error selecting from table");

		while($imageRow = $imageResult1->FetchRow())
		{
			$count++;
		
		$imgID = $imageRow['imgID'];
		$imgDesc = $imageRow['imgDesc'];
		$imgTagFull = "imageView.php?id=$imgID&view=1";
		$imgTagThumb = "<img src='imageView.php?id=$imgID&view=0' " .
							"alt='thumbnail image' />";
				
		$bridgeQuery = "SELECT * FROM image2category WHERE imgID=". $imgID;
		$bridgeResult = $db->EXECUTE($bridgeQuery) or die("Error selecting catID");
		
		/* if $count currently holds an odd value,
		 * start a new "row" so that images are displayed in a two-column layout
		 */
		if($count % 2 == 1)//if the remainder when $count is divided by 2 is zero
		{
			print
			"<div style='width:97%;margin:1.5%;float:left;'>".
			"\n";
		}
		
	print
	/* div for one image */
	"<div style='height:105px;width:45%;padding:1%;margin:1%;float:left;border:solid black;'>\n".
	/* div containing "cell" with thumbnail img inside */
	"  <div style='height:100%;width:17.9211%;float:left;overflow:auto;'>\n".
	"    <a href='". $imgTagFull ."'>". $imgTagThumb ."</a></td>\n".
	"  </div>\n";
	print
	   /* space between img and img info divs */
	"  <div style='height:100%;width:0.896057%;float:left;'></div>\n".
	   /* "cell" containing imgDesc and categories */
	"  <div style='height:100%;width:81.1828%;float:left;overflow:auto;'>\n".
						/* top half of "cell" reserved for imgDesc */
	"					<div class='statement' style='height:60%;overflow:auto;'><strong>" .
						    "Description:  </strong>" .
	"						  <div class='condensedcontent'>".
							    $imgDesc .
	"						  </div>\n".
	"					</div>\n".
						/* lower half of "cell" reserved for categories */
	"					<div class='statement' style='height:40%;overflow:auto;'><strong>".
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

	    print
	    "     </div>\n".  //close div for condensedcontent class
	    "    </div>\n".   //close div for statement class
	    "   </div>\n".    //close div for image info "cell"
	    "  </div>\n";     //close div for black-bordered row
	
	    /* print an extra closing div tag if this is the end of a row */
	    if($count % 2 == 0)
	    {
		    print "</div>\n";
	    }
	
	}//end of WHILE($imageRow) loop
	
  }//end of WHILE($rowImgID) loop

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