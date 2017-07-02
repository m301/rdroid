<?PHP
session_start();
ini_set('include_path', '.:/home/madsac/public_html/');
include("rdroid/functions.php");
if ($_POST) {
    $usr = $_POST['username'];
    $pwd = $_POST['password'];
    register($usr, $pwd);
    if (login($usr, $pwd)) {
        $_SESSION['sID'] = rstr();
        $id = $_SESSION['sID'];
        $_SESSION['id'] = cen($usr, $id);


        header("Location: devices.php");

    } else {

        header("Location: login.php?incorrect=1");
    }

} else {
    include("include/templateClass.php");
    $page = new Template($_SERVER);
    $page->settings = array('fb_like' => 0
    );

    $page->title = "Login " . $SOFTNAME;
    $page->article_title = "Login " . $SOFTNAME;
    $page->description = "Login to rDroid";
    $page->keywords = "login,rdroid,remote android,free software," . $SOFTNAME;

    $page->PrintHeadTop();
    ?>
    <?PHP $page->PrintBetweenHeadAndBody(); ?>

    <table align="center">
        <tr>
            <td>


                <?PHP

                if ($_GET['incorrect'])
                    echo "Incorrect username or password !<br>";
                ?><br>

                <form action="login.php" method="post">
                    Username : <input type="text" name="username" size=20 value="madsac"></input><br>
                    Password : <input type="password" name="password" size=20 value="iammadsac"></input><br>
                    <center><input type="submit" value="Login" class="g-button"></input></center>
                    <br>Not Registered ? <a href="register.php">Click Here</a>

    </table>
    <?PHP $page->PrintAfterBody(); ?>
<?PHP } ?>