<?PHP
session_start();
ini_set('include_path', '.:/home/mads8929/public_html/');
include("rdroid/functions.php");
$id = $_SESSION['sID'];
$usr = cde($_SESSION['id'], $id);
$name = trim(urldecode($_GET['device']));
if (checkValid($id, $usr, $name)) {
    include("include/templateClass.php");
    $page = new Template($_SERVER);
    $page->settings = array('fb_like' => 0,
        'rdroid' => 0,
        'no_side_menu' => 1,
        'jq-1.9' => 1,
        'jq.filetree' => 1,
    );
    $page->title = "FM" . $SOFTNAME;
    $page->article_title = "FM" . $SOFTNAME;
    $page->PrintHeadTop();
    ?>
    <script type="text/javascript">
        $(document).ready(function () {
            $('#fb').fileTree({
                root: '/',
                script: 'fm_handler.php',
                expandSpeed: 1000,
                collapseSpeed: 1000,
                multiFolder: false
            }, function (file) {
                alert(file);
            });
        });
    </script>

    <style>

    </style>

    <?PHP $page->PrintBetweenHeadAndBody(); ?>
    <div id="fb"></div>

    <?PHP $page->PrintAfterBody();
} ?>