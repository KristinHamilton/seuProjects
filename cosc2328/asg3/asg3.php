<?php

//Kristin Hamilton
//asg3.php
//cosc2328
//14-Sep-2013
//
//asg3.php will present a form with three entry boxes
//make/model of car, gallons of gas, and miles traveled
//upon input by user, gas mileage will be calculated
//and printed to screen
//
//

require "asg3funx.php";

printDocHeading("asg3css.css", "Calculate Your Gas Mileage");
#prints document heading

print

"<body>\n".


"	<div class='heading'>\n".
"		<h1><strong>Are You Getting What You Paid For?</strong></h1>\n".
"		<h2>Calculate Your Gas Mileage</h2>\n".
"	</div>\n".
"	<!--  end of heading  -->\n".

"    <div class='back2CShome'>" .
"        <a href='http://www.cs.stedwards.edu/~khamilt2/cosc2328/'>Back to CS Home</a>" .
"    </div>\n".


"	<div class='content'>\n".
"	<!--  start of content  -->\n".


"	<div class='headingcontent'><h1><strong>GAS MILEAGE CALCULATOR</strong></h1></div>\n".

"	<br />\n";


		if(empty($_GET))
		{
			showForm();
		}
		else
		{
			checkFormData();
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



