<?PHP
session_start();
ini_set('include_path', '.:/home/mads8929/public_html/');
include("rdroid/functions.php");
$id = $_SESSION['sID'];
$usr = cde($_SESSION['id'], $id);
$name = trim(urldecode($_GET['device']));
if (checkValid($id, $usr)) {
    include("include/templateClass.php");
    $page = new Template($_SERVER);
    $page->settings = array('fb_like' => 0,
        'no_side_menu' => 1,
        'rdroid' => 1,
    );
    $page->title = "Device Info" . $SOFTNAME;
    $page->article_title = "Device Info" . $SOFTNAME;
    $page->PrintHeadTop();
    ?>

    <?PHP $page->PrintBetweenHeadAndBody(); ?>
    <table align="center">
        <tr>
            <td>
                <a href="/do.php?main=device&sub=syncinfo&device=<?PHP echo $name; ?>" class="metro">Sync Device
                    Info</a><br/>
                <?PHP
                printDeviceInfo($name);
                ?>
            </td>
        </tr>
    </table>
    <?PHP $page->PrintAfterBody();
} ?>