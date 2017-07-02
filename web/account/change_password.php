<?PHP
include("functions.php");
$id = $_SESSION['sID'];
$usr = cde($_SESSION['id'], $id);
if (!(isset($id) && isset($usr))) {
    no_login();
} else {
    ?>
    <?PHP
    include($pHead . "include/head.php");
    include($pHead . "include/body.php");
    include($pHead . "include/info.php");


    $title = "Change Password" . $add_title; //Title of Page
    $atitle = "Change Password" . $add_atitle; //Title of Article
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
                    $pwd = $_POST['password'];
                    if ($pwd == $_POST['repassword'])
                        if (change_pwd($usr, $pwd))
                            echo "Password successfully changed.";
                        else
                            echo "An unknown error occurred.";
                    else
                        echo "Passwords do not match.";

                    ?><br>

                <?PHP
                } else {
                    ?>
                    <table align="center">
                        <tr>
                            <td>
                                <form action="change_password.php" method="post" name="register">
                                    New Password : <input type="password" name="password" size=30></input><br>
                                    Re-Password &nbsp;&nbsp;: <input type="password" name="repassword"
                                                                     size=30></input><br>

                                    <center><input type="submit" value="Change Password"></input></center>
                    </table>
                <?PHP
                }
                ?>
    </table>
    <?PHP
    body_bottom($bottom, $footer, $mobile);
}
?>