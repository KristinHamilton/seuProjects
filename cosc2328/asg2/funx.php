<?php
//
//[funx.php]
//
//PHP-frequently used functions
//
//Kristin Hamilton
//cosc2328
//this file is a renamed copy of classfunphp originally created by Dr Baker
//
//
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


//(BELOW)  printDocFooter()
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


