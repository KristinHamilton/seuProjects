<?php
# Kristin Hamilton
# asg3.php
# cosc2328
# 14-Sep-2013
# 
# asg3.php will present a form with three entry boxes make/model of car, gallons of 
# gas, and miles traveled. upon input by user, gas mileage will be calculated and 
# printed to screen

require_once "asg3funx.php";
printDocHeading("asg3.css", "Calculate Your Gas Mileage");

echo
"<body>\n

	<div class='heading'>\n
		<h1><strong>Are You Getting What You Paid For?</strong></h1>\n
		<h2>Calculate Your Gas Mileage</h2>\n
	</div>\n

    <div class='back2CShome'>
        <a href='http://kristin.create.stedwards.edu/cosc2328/index.php'>Back to CS Home</a>
    </div>\n

	<div class='content'>\n
	<!--  start of content  -->\n

		<div class='headingcontent'>\n
			<h1><strong>GAS MILEAGE CALCULATOR</strong></h1>\n
		</div>\n
		<br />\n";

if(empty($_GET))
{
	showForm();
}
else
{
	checkFormData();
}

echo "	</div>\n	<!--  end of content  -->\n";
printDocFooter();
echo "</body>\n</html>\n";
?>
