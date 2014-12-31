<?PHP
/* MAD Don't forget to close </head> */
//   Sample Code

/*

$top=1; //Add top.php
$title="Your Title"; //Title of Page
$addtitle=1; //Add add-title.php
$description="Description Goes Here !"; 
$keywords="Keywords,MADSAC";
$robot=1; //0 or 1 to allow robots or not
$revisit=1; //Time to revisit page
$style=1; //Enable/Disable Stylesheet
$jquery=1;
$charcount=1; //Charcount script
$fblike=1;
$topmenu=1;
$extrahead=1; //Add extrahead.php
$mobile=0; //Mobile Devices
head($top,$title,$addtitle,$description,$keywords,$robot,$revisit,$style,$jquery,$charcount,$fblike,$topmenu,$extrahead,$mobile);
echo "</head>";

*/
//End Of Sample Code

function head($top, $title, $addtitle, $description, $keywords, $robot, $revisit, $style, $jquery, $charcount, $fblike, $topmenu, $extrahead, $mobile){
$ipath = "head/";
if ($robot)
    $robot = "index,follow";
else
    $robot = "no index,no follow";

?>
<?PHP if ($top) include($ipath . "top.php"); ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<HEAD>
    <TITLE><?PHP echo $title; ?><?PHP if ($addtitle) include($ipath . "add-title.php"); ?></TITLE>
    <META NAME="description" CONTENT="<?PHP echo $description; ?>">
    <META NAME="keywords" CONTENT="<?PHP echo $keywords; ?>">
    <META NAME="robot" CONTENT="<?PHP echo $robot; ?>">
    <META NAME="revisit-after" CONTENT="<?PHP echo $revisit; ?>">
    <?PHP if ($style) include($ipath . "style.php"); ?>
    <?PHP if ($jquery) include($ipath . "jquery.php"); ?>
    <?PHP if ($charcount) include($ipath . "charcount.php"); ?>
    <?PHP if ($topmenu) include($ipath . "topmenu-head.php"); ?>
    <?PHP if ($fblike) include($ipath . "fblike-head.php"); ?>
    <?PHP if ($extrahead) include($ipath . "extrahead.php"); ?>
    <?PHP } ?>
