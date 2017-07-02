<?PHP
include("functions.php");

?>
<?PHP

include($pHead . "include/head.php");
include($pHead . "include/body.php");
include($pHead . "include/info.php");


$title = "Register" . $add_title; //Title of Page
$atitle = "Register" . $add_atitle; //Title of Article
$description = "register in ip logger";
$keywords = "register in ip logger";

head($top, $title, $addtitle, $description, $keywords, $robot, $revisit, $style, $jquery, $charcount, $fblike, $topmenu, $extrahead, $mobile);
?>

<?PHP
echo "</head>";
body_top($atop, $atitle, $aaddtitle, $topmenu, $notice, $menupath, $fblike, $mobile);
?>

    <table align=center>
        <tr>
            <td>
                <?PHP

                if ($_POST) {
                    $usr = $_POST['username'];
                    $pwd = $_POST['password'];
                    if ($pwd == $_POST['repassword'])
                        echo register($usr, $pwd) . "<br>Your username is :" . strtolower($usr);
                    else
                        echo "Passwords do not match.";

                    ?><br>
                    Now you can <a href="login.php">Login</a> and proceed.

                <?PHP
                } else {
                    ?>
                    <table align="center" style="border-style: dotted;
border-width: 5px;" cellpadding="25">
                        <tr>
                            <td>
                                <form action="register.php" method="post" name="register">
                                    Username &nbsp;&nbsp;&nbsp;: <input type="text" name="username" size=30></input><br><br>
                                    Password &nbsp;&nbsp;&nbsp;&nbsp;: <input type="password" name="password"
                                                                              size=30></input><br>
                                    Re-Password : <input type="password" name="repassword" size=30></input><br>
                                    <br>By clicking on register you accept our <a href="about.php">Terms and
                                        conditions.</a><br>
                                    <center><input type="submit" value="Register"></input></center>
                    </table>
                <?PHP
                }
                ?>
    </table>
<?PHP
body_bottom($bottom, $footer, $mobile);
?>