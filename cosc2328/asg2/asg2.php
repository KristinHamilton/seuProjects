<?php

   #Output same content as index.html using PHP functions
   #PHP functions used:  document heading;footer
   #6-Sep-2013
   #assignment2
   #cosc2328


require "funx.php";
#reference file containing PHP functions

printDocHeading("start.css", "CS Home Page");
#call function to generate document heading

print
#print html content-start


"<!--  body of document starts here  -->\n".
"<body>\n".


"	<div class='heading'>\n".
" 		<h2> Kristin Hamilton - CS Home Page </h2>\n".
"	</div>\n".
"	<!--  end of heading  -->\n".

"    <div class='back2CShome'>" .
"        <a href='http://www.cs.stedwards.edu/~khamilt2/cosc2328/'>Back to CS Home</a>" .
"    </div>\n".


"	<div class='content'>\n".
"	<!--  start of content  -->\n".


"	<!--  float left starts here -->\n".
"	<div class='floatleft'>\n".

"	<h3> My major </h3>\n".

"    	     Computer Science\n".


"	<h4> My hobbies </h4>\n".

"	     <ul>\n".

"	     <li> Exploring in the woods </li>\n".

"	     <li> Playing with the dogs </li>\n".

"	     <li> Texas Master Naturalist activities</li>\n".

"	     <li> Cooking </li>\n".

"	     <li> Spending time with family </li>\n".

"	     <li> Reading field guides </li>\n".

"	     </ul>\n".


" 	<h6> Some helpful links: </h6>\n".

"  	     <a href='http://validator.w3.org'> html validator </a>\n".

"  	     <br /><br />\n".

"  	     <a href='http://w3schools.com/html/default.asp'> html reference </a>\n".

"  	     <br /><br />\n".

"  	     <a href='http://w3schools.com/css/default.asp'> css reference </a>\n".

"  	     <br /><br />\n".

"  	     <a href='http://html-color-codes.com/'> color codes reference </a>\n".

"  	     <br /><br />\n".

"  	     <a href='http://www.ajaxgoals.com/'> ajax resource </a>\n".

"  	     <br /><br />\n".

"  	     <a href='http://www.dreamincode.net/forums/forum/74-web-development/'> web development forum </a>\n".

"  	     <br /><br />\n".


"	</div>\n".
"	<!--  end of float left  -->\n".


"	<!--  start new float left  -->\n".
"	<div class='floatleft'>\n".


"  	<div class='border'> \n".


"    	<br />\n".

"    	<em>Bowie, my dog-nephew, and Charlie</em>\n".

"    	<br />\n".

"    	<img src='BowieAndCharlie.jpg' alt='Bowie and Charlie' />\n".

"    	<br />\n".


"  	</div>\n".
"	<!--  end of border  -->\n".


"	</div>\n".
"	<!--  end of float left  -->\n".


"	</div>\n".
"	<!--  end of content  -->\n".


"	<div class='clear'></div>\n";


printDocFooter();
#prints page footer


print 

"</body>\n".
"<!--  end of body  -->\n".


"</html>\n";



?>