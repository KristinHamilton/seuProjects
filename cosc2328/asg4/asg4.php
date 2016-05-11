<?
# [asg4.php]
# Kristin Hamilton
# cosc2328
# 16-Sep-2013
# Assignment4:  Create a blog that will accept comments and write them to a flat file

require_once "asg4funx.php";
printDocHeading("asg4.css", "Go Big or Go Home");

echo "<body>\n

	<div class='heading'>\n
		<h1>BAD IDEA JEANS BLOG</h1>\n
		<h2>Go Big or Go Home</h2>\n
	</div>\n

    <div class='back2CShome'>
        <a href='http://kristin.create.stedwards.edu/cosc2328/index.php'>Back to CS Home</a>
    </div>\n

    <div class='content'>\n
	<!--  start of content  -->\n

		<h2><strong>BAD IDEA JEANS BLOG</strong></h2>\n";

showBlogEntries();

if (empty($_POST))
{
	showAddDeleteForm();
	showBlogComments();
}
else if(isset($_POST['submitAddComment']))
{
	showTypeInCommentForm();
}
else if(isset($_POST['submitNewComment']))
{
	processAddEntry();
	showAddDeleteForm();
	showBlogComments();
}
else if(isset($_POST['submitDeleteAll']))
{
	deleteAllEntries();
	showAddDeleteForm();
	showBlogComments();
}

echo "		</div>\n		<!--  end of content  -->\n";
printDocFooter();
echo "	</body>\n</html>\n";

function showBlogEntries()
{
	echo "	
	<div class = 'blogentries'>\n

		<h4>\n
			<strong>FRIDAY</strong>\n
			<br />\n
			Today I bought a baby reticulated python.  His name is Ni&#241;ito.  
			Anybody know how big these things get?\n
			<br /><br /><br />\n
	
			<strong>SATURDAY</strong>\n
			<br />\n
			This morning I gave this shady man my phone number, just to get him to leave 
			me alone.  Except I just realized I accidentally gave him my social security 
			number by mistake.\n
			Anybody know how I should handle this?\n
			<br /><br />\n
			Ni&#241;ito is doing well, except his weight has quadrupled since yesterday, 
			and I am running low on crickets.\n
			Well, I've got to go!  Texas Hold 'Em game tonight!\n
			<br /><br /><br />\n
			
			<strong>SUNDAY</strong>\n
			<br />\n
			Last night, I was SUPER pumped because I thought I was finally going to be 
			able to pay my bookie.  I had all red cards, and went all in when I caught a 
			two of hearts on the river.\n
			But as it turns out, a flush is when all five cards have the same SUIT, not 
			the same color.  I tried to tell them that's not the rules I usually play by, 
			but they insisted I pay up, so now I've got no cash left.\n
			Plus, I used Ni&#241;ito as collateral, so there's that...\n
			<br /><br />\n
			The good news is that I've got an Organic Chemistry test in 3 hours.  I might 
			look some stuff over, but I'm not really worried because I am going to 
			DOMINATE that test!\n
			<br /><br /><br />\n
		</h4>\n

	</div>\n";
}
?>