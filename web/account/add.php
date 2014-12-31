<?PHP
include("functions.php");

$id = $_SESSION['sID'];
$usr = cde($_SESSION['id'], $id);
if (!(isset($id) && isset($usr))) {
    echo '<META HTTP-EQUIV="Refresh" Content="0; URL=login.php?invalid=1">';
} else {
    ?>
    <?PHP
    $pHead = "../";
    include($pHead . "include/head.php");
    include($pHead . "include/body.php");
    include($pHead . "include/info.php");


    $title = "Add Logger" . $add_title; //Title of Page
    $atitle = "Add Logger" . $add_atitle; //Title of Article
    $description = "login in mad iplogger";
    $keywords = "";


    head($top, $title, $addtitle, $description, $keywords, $robot, $revisit, $style, $jquery, $charcount, $fblike, $topmenu, $extrahead, $mobile);
    ?>
    <?PHP
    echo "</head>";
    body_top($atop, $atitle, $aaddtitle, $topmenu, $notice, $menupath, $fblike, $mobile);
    ?>


    <?PHP

    if ($_POST) {
        $id = $_POST['id'];
        $url = $_POST['url'];


        $pos = strrpos($url, ".");
        $str = substr($url, ($pos + 1));
        $pos = strrpos($id, ".");
        $str2 = substr($id, ($pos + 1));


        if ($str == 'jpg' || $str == 'jpeg' || $str == 'gif' || $str == 'png' || $str == 'bmp' || $str == 'ico' || $str == 'psd' || $str == 'tif' || $str == 'tiff') {

            if ($str == $str2) {
                $res = add($usr, $id, $url);
                if ($res == 1)
                    echo "New logger created.Your new URL is <a href='http://madsac.in/image/" . $id . "'>http://madsac.in/image/" . $id . "</a>";
                else if ($res == 2)
                    echo "Logger with same name already registered.";
                else
                    echo "Unknown error occured.";

            } else
                echo "Image extensions do not match.";

        } else {
            echo "Given image extension is not supported by our service.";
        }

    }?><br>
    <form action="add.php" method="post">
        New URL : http://madsac.in/image/<input type="text" name="id" size=10 value="test.gif"></input><br>
        Real URL: <input type="test" name="url" size=100 value="http://madsac.in/images/smallest.gif"></input><br>
        <input type="submit" value="Add"></input><br>


<?PHP

body_bottom($bottom, $footer, $mobile);
}
?>