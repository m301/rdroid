<?PHP
include("functions.php");

?>
<?PHP

include($pHead . "include/head.php");
include($pHead . "include/body.php");
include($pHead . "include/info.php");


$title = "About" . $add_title; //Title of Page
$atitle = "About" . $add_atitle; //Title of Article
$description = "login in mad iplogger";
$keywords = "";


head($top, $title, $addtitle, $description, $keywords, $robot, $revisit, $style, $jquery, $charcount, $fblike, $topmenu, $extrahead, $mobile);
?>
<?PHP
echo "</head>";
body_top($atop, $atitle, $aaddtitle, $topmenu, $notice, $menupath, $fblike, $mobile);
?>


    <table align="center">
        Hello !<br>
        This is an IP Logger which logs IP of user from image and stores it.You can use it to track whether an email has
        been read or not or for getting someone's IP.It is not meant for hacking/cracking activities it is just for
        personal use.If you use it for illegal activity then you have no right to use it.
        <br>
        If you have any suggestion/comment/feedback/question mail me : <a href="mailto:madsacsoft@gmail.com">madsacsoft@gmail.com</a><br>
        Thank You,

        <tr>
            <td>


    </table>




<?PHP
body_bottom($bottom, $footer, $mobile);
?>