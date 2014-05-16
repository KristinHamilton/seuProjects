<?php
header('Content-Type: image/jpeg');
/* *
 * [imageView.php]
 * called in showAllImages() and showCategoryImages() in asg7funx.php
 * contains header required for browser viewing of binary data as an image
 * uses $_GET data to determine whether thumbnail image or full image should be displayed
 * once determination has been made, queries the db to select the appropriate field
 * prints the image data, allowing the image to be seen
 * having this script separately allows header() to be sent once per image
 * 
 * ***the lack of a closing php tag "?>" is intentional***
 * 
 * Kristin Hamilton
 * cosc2328
 * 12-Nov-2013
 */

require "../db/mydb.php";
//require "../db/mydbTest.php";

$id = $_GET['id'];
$view = $_GET['view'];//full(1) or thumb(0)

if($view == 0)
{
	$imageView = "thumbImg";
	
}//end of IF($view == 0)

else //if($view == 1)
{
	$imageView = "fullImg";
	
}//end of ELSE
	
$db = adodbConnect();	
$viewQuery = "SELECT ". $imageView ." FROM image WHERE imgID=". $id;
$viewResult = $db->EXECUTE($viewQuery) or die("Error selecting an image version");

while($viewRow = $viewResult->FetchRow())
{
	$image = base64_decode($viewRow[$imageView]);
	print $image;
}?>