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
 * Kristin Hamilton
 * cosc2328
 * 12-Nov-2013
 */
require_once "../db/mydb.php";

$id = $_GET['id'];
$view = $_GET['view'];  # full(1) or thumb(0)

if($view == 0)
{
	$imageView = "thumbImg";
}
else
{
	$imageView = "fullImg";	
}
	
$db = dbConnect();	
$query = "SELECT $imageView FROM image WHERE imgID = $id";
$result = mysqli_query($db, $query) or die("Error selecting an image version");

while($row = mysqli_fetch_assoc($result))
{
	$image = base64_decode($row[$imageView]);
	echo $image;
}
mysqli_close($db);
?>