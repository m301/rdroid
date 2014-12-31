<?PHP
session_start();
$ipath = "../";
include($ipath . "functions.php");
$id = $_SESSION['sID'];
$usr = cde($_SESSION['id'], $id);
$name = trim(urldecode($_GET['device']));
if (checkValid($id, $usr, $name)) {
    sendnoti($name, "delcontact:" . trim($_GET['id']));
    redirect('contacts/?device=' . $name, 2);
}
?>