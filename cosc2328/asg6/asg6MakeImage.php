<?php

//[asg6MakeImage.php]
//contains one function:  drawImage
//
//Kristin Hamilton
//cosc2328
//file created: Fri, 01-Nov-2013
//

//[BELOW]  drawImage($imgHeight, $imgWidth)
//creates color schemes
//draws image graphics
//outputs image to temp file
//displays image on page
//
function drawImage($imgHeight, $imgWidth)
{
print
"	<!--  [BELOW] CONSTRUCT COLOR SCHEMES  -->\n";

	//create blank img
	$image = imagecreatetruecolor($imgWidth, $imgHeight);

	//rainbow color scheme
	$rGray = imagecolorallocate($image, 119, 119, 119);
	$rPink = imagecolorallocate($image, 255, 17, 153);
	$rRed = imagecolorallocate($image, 255, 0, 0);
	$rOrange = imagecolorallocate($image, 255, 119, 0);
	$rYellow = imagecolorallocate($image, 255, 255, 0);
	$rGreen = imagecolorallocate($image, 0, 136, 0);
	$rBlue = imagecolorallocate($image, 0, 0, 255);
	$rIndigo = imagecolorallocate($image, 136, 0, 255);
	$rViolet = imagecolorallocate($image, 221, 0, 238);
	
	//jewel color scheme
	$jBrown = imagecolorallocate($image, 136, 68, 17);
	$jPink = imagecolorallocate($image, 221, 17, 68);
	$jRed = imagecolorallocate($image, 119, 0, 0);
	$jOrange = imagecolorallocate($image, 255, 68, 0);
	$jYellow = imagecolorallocate($image, 255, 187, 0);
	$jGreen = imagecolorallocate($image, 0, 153, 0);
	$jBlue = imagecolorallocate($image, 0, 0, 136);
	$jIndigo = imagecolorallocate($image, 68, 0, 136);
	$jViolet = imagecolorallocate($image, 153, 0, 153);
	
	//neon color scheme
	$nBlack = imagecolorallocate($image, 0, 0, 0);
	$nPink = imagecolorallocate($image, 255, 0, 204);
	$nRed = imagecolorallocate($image, 255, 0, 51);
	$nOrange = imagecolorallocate($image, 255, 51, 0);
	$nYellow = imagecolorallocate($image, 204, 255, 51);
	$nGreen = imagecolorallocate($image, 0, 255, 0);
	$nBlue = imagecolorallocate($image, 0, 85, 255);
	$nIndigo = imagecolorallocate($image, 129, 0, 255);
	$nViolet = imagecolorallocate($image, 204, 0, 255);

	//construct array of rainbow colors
	$rainbowColorsArray = array(
		$rGray, $rPink, $rRed, $rOrange, $rYellow, $rGreen, $rBlue, $rIndigo, $rViolet);

	//construct array of jewel colors
	$jewelColorsArray = array(
		$jBrown, $jPink, $jRed, $jOrange, $jYellow, $jGreen, $jBlue, $jIndigo, $jViolet);

	//construct array of neon colors
	$neonColorsArray = array(
		$nBlack, $nPink, $nRed, $nOrange, $nYellow, $nGreen, $nBlue, $nIndigo, $nViolet);

print
"	<!--  end of CONSTRUCT COLOR SCHEMES -->\n".

"	<!--  [BELOW]  MAKE IMAGE  -->\n";
	//figure out which color scheme to use according to user input
	$imgColorChoice = $_POST['imgColorChoice'];
	
	if($imgColorChoice == 1)
	{
		$colorSchemeArray = $rainbowColorsArray;
	}
				
	else if($imgColorChoice == 2)
	{
		$colorSchemeArray = $jewelColorsArray;
	}
		
	else if($imgColorChoice == 3)
	{
		$colorSchemeArray = $neonColorsArray;
	}
	
	//fill in with background color
	imagefill($image, 0, 0, $colorSchemeArray[0]);

	//DRAW GRAPHICS...
	//polygons(x2) in center of image
	//imagepolygon($image, array(coords of vertices in form x1,y1, x2,y2, etc.),
	//	number of vertices, $color)
	imagepolygon($image, array(
		100, 400, 600, 100, 700, 300, 150, 600, 100, 400), 4, $colorSchemeArray[1]);
	imagepolygon($image, array(
		200, 300, 250, 450, 600, 500, 350, 300, 200, 250), 4, $colorSchemeArray[5]);

	//blue "funnel"
	//imageellipse($image, x-coord of ellipse center, y-coord of ellipse center,
	//	ellipse width, ellipse height, $color)
	for($i=0; $i<100; $i++)
	{
		imageellipse($image, ($i*5)+500, $i*10, $i, $i*5, $colorSchemeArray[6]);
	}

	//"whirlwind" at bottom
	//imagefilledellipse($image, x-coord of ellipse center, y-coord of ellipse center,
	//	ellipse width, ellipse height, $color, fill mode)
	imagefilledellipse($image, 300, 730, 300, 100, $colorSchemeArray[8]);
	
	//imagefilledarc($image, x-coord of ellipse center, y-coord of ellipse center,
	//	ellipse width, ellipse height, start angle, end angle, $color, fill mode)
	imagefilledarc($image, 300, 705, 250, 75, 90, 360, $colorSchemeArray[4], IMG_ARC_NOFILL);
	imagefilledarc($image, 325, 705, 250, 75, 300, 45, $colorSchemeArray[2], IMG_ARC_NOFILL);
	imagefilledarc($image, 330, 725, 225, 60, 0, 330, $colorSchemeArray[6], IMG_ARC_NOFILL);
	imagefilledarc($image, 325, 680, 225, 55, 0, 300, $colorSchemeArray[1], IMG_ARC_NOFILL);
	imagefilledarc($image, 320, 700, 225, 55, 50, 180, $colorSchemeArray[7], IMG_ARC_NOFILL);
	imagefilledarc($image, 275, 730, 200, 70, 270, 180, $colorSchemeArray[5], IMG_ARC_NOFILL);
	imagefilledarc($image, 335, 650, 175, 50, 0, 300, $colorSchemeArray[3], IMG_ARC_NOFILL);
	imagefilledarc($image, 340, 670, 175, 55, 250, 60, $colorSchemeArray[4], IMG_ARC_NOFILL);

	//star polygon
	//imagepolygon($image, array(coords of vertices in form x1,y1, x2,y2, etc.),
	//	number of vertices, $color)
	for($i=0; $i<10; $i++)
	{
		imagepolygon($image, array(
			0, 20, 
			733-($i*3), ($i*3)+33, 
			750-($i*3), ($i*3)+$i, 
			766-($i*3), ($i*3)+33, 
			800-($i*3), ($i*3)+33, 
			775-($i*3), ($i*3)+66, 
			800-($i*3), ($i*3)+100, 
			755-($i*3), ($i*3)+85, 
			780, 800, 
			725, ($i*3)+66, 
			0, 20), 
			10, $colorSchemeArray[5]);

		}//end of for loop

	//interconnected loops
	//imagearc($image, x-coord of ellipse center, y-coord of ellipse center,
	//	ellipse width, ellipse height, start angle, end angle, $color)
	for($i=0; $i<50; $i++)
	{
		imagearc($image, 600, 700, ($i+100), ($i+100), 0, 360, $colorSchemeArray[1]);
		imagearc($image, 600, 500, ($i+100), ($i+100), 0, 360, $colorSchemeArray[4]);
		imagearc($image, 600, 600, ($i+100), ($i+100), 0, 360, $colorSchemeArray[5]);
		imagearc($image, 700, 600, ($i+100), ($i+100), 0, 360, $colorSchemeArray[1]);
		imagearc($image, 500, 600, ($i+100), ($i+100), 0, 360, $colorSchemeArray[4]);
		
	}//end of for loop

	//"beach ball"	 
	//imagefilledarc($image, x-coord of ellipse center, y-coord of ellipse center,
	//	ellipse width, ellipse height, start angle, end angle, $color, fill mode)
	imagefilledarc($image, 110, 110, 200, 200, 0, 50, $colorSchemeArray[2], IMG_ARC_CHORD);
	imagefilledarc($image, 110, 110, 200, 200, 60, 110, $colorSchemeArray[6], IMG_ARC_CHORD);
	imagefilledarc($image, 110, 110, 200, 200, 120, 170, $colorSchemeArray[1], IMG_ARC_CHORD);
	imagefilledarc($image, 110, 110, 200, 200, 180, 230, $colorSchemeArray[5], IMG_ARC_CHORD);
	imagefilledarc($image, 110, 110, 200, 200, 240, 290, $colorSchemeArray[3], IMG_ARC_CHORD);
	imagefilledarc($image, 110, 110, 200, 200, 300, 350, $colorSchemeArray[4], IMG_ARC_CHORD);
	
	//give each segment of "beach ball" a "cutout"
	//imagefilledarc($image, x-coord of ellipse center, y-coord of ellipse center,
	//	ellipse width, ellipse height, start angle, end angle, $color, fill mode)
	imagefilledarc($image, 110, 110, 150, 150, 2, 48, $colorSchemeArray[0], IMG_ARC_CHORD);
	imagefilledarc($image, 110, 110, 150, 150, 62, 108, $colorSchemeArray[0], IMG_ARC_CHORD);
	imagefilledarc($image, 110, 110, 150, 150, 122, 168, $colorSchemeArray[0], IMG_ARC_CHORD);
	imagefilledarc($image, 110, 110, 150, 150, 182, 228, $colorSchemeArray[0], IMG_ARC_CHORD);
	imagefilledarc($image, 110, 110, 150, 150, 242, 288, $colorSchemeArray[0], IMG_ARC_CHORD);
	imagefilledarc($image, 110, 110, 150, 150, 302, 348, $colorSchemeArray[0], IMG_ARC_CHORD);

	//lines fanning out
	//imageline($image, x1, y1, x2, y2, $color). draws line from coords (x1,y1) to (x2,y2).
	for($i=0; $i<70; $i++)
	{
		imageline($image, 0, 800, $i*5, $i+500, $colorSchemeArray[7]);
		imageline($image, 300, 500, $i*5, $i+500, $colorSchemeArray[3]);

	}//end of for loop

	//thick crossing lines
	//set line thickness to 25 px
	imagesetthickness($image, 25);

	//imageline($image, x1, y1, x2, y2, $color). draws line from coords (x1,y1) to (x2,y2).
	imageline($image, 350, 0, 0, 450, $colorSchemeArray[8]);
	imageline($image, 360, 0, 0, 460, $colorSchemeArray[5]);
	imageline($image, 380, 0, 0, 480, $colorSchemeArray[2]);

	imageline($image, 0, 400, 450, 0, $colorSchemeArray[1]);
	imageline($image, 0, 410, 460, 0, $colorSchemeArray[4]);
	imageline($image, 0, 430, 480, 0, $colorSchemeArray[7]);

	//save img out to temp directory
	imagepng($image, "../temp/tempPNG.png");

	//output img according to user-supplied dimensions ($imgHeight and $imgWidth below)
print
'	<img src="../temp/tempPNG.png" alt="artistic expression image" ' .
'		height='."$imgHeight".' width='."$imgWidth".' />';

	//clear buffer of $image
	imagedestroy($image);

}// end of function drawImage

?>
