/* *
 *[asg8js.js]
 * javascript function showPicture() for asg8
 * puts full image into fullDiv div on page using javascript
 * 
 * Kristin Hamilton
 * cosc2328
 * created Sun, 08-Dec-2013
 */
function showPicture(imgID)
{
    var output = "<img src='imageView.php?id=" + imgID + "&view=1' alt='full image' />";
    document.getElementById("fullDiv").innerHTML = output;
}