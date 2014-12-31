<?PHP
include("functions.php");

$id = $_SESSION['sID'];
$usr = cde($_SESSION['id'], $id);
if (!(isset($id) && isset($usr))) {
    echo '<META HTTP-EQUIV="Refresh" Content="0; URL=login.php?invalid=1">';
} else {
    ?>
    <?PHP
    include($pHead . "include/head.php");
    include($pHead . "include/body.php");
    include($pHead . "include/info.php");


    $title = "Home" . $add_title; //Title of Page
    $atitle = "Home" . $add_atitle; //Title of Article
    $description = "Home of ";
    $keywords = "";


    head($top, $title, $addtitle, $description, $keywords, $robot, $revisit, $style, $jquery, $charcount, $fblike, $topmenu, $extrahead, $mobile);
    ?>
    <?PHP
    echo "</head>";
    body_top($atop, $atitle, $aaddtitle, $topmenu, $notice, $menupath, $fblike, $mobile);
    ?>

    <?PHP

    echo $_GET['msg'];

    ?>

    <br>
    OMG !, How you are in ....this section is under development.


<?PHP
}
body_bottom($bottom, $footer, $mobile);
?>