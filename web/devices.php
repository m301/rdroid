<?PHP

session_start();
ini_set('include_path', '.:/home/mads8929/public_html/');
include("rdroid/functions.php");
$id = $_SESSION['sID'];
$usr = cde($_SESSION['id'], $id);

if (checkValid($id, $usr)) {
    include("include/templateClass.php");
    $page = new Template($_SERVER);
    $page->settings = array('fb_like' => 0, 'rdroid' => 1);
    $page->title = "Devices" . $SOFTNAME;
    $page->article_title = "Devices" . $SOFTNAME;
    $page->PrintHeadTop();
    $page->PrintBetweenHeadAndBody();

    ?>

    <?PHP

    printDevices($usr);

    ?>
    <?PHP

    $page->PrintAfterBody();

    ?>
<?PHP

}

?>