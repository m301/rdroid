<?PHP
include("functions.php");
$id = $_SESSION['sID'];
$usr = cde($_SESSION['id'], $id);
$id = $_GET['id'];

if ($id) {

    header("Location: home.php?msg=" . urlencode(remove($usr, $id)));
} else {
    header("Location: home.php");
}
?>