<?PHP
session_start();
ini_set('include_path', '.:/home/mads8929/public_html/');
include("rdroid/functions.php");
$id = $_SESSION['sID'];
$usr = cde($_SESSION['id'], $id);
$name = trim(urldecode($_GET['device']));

if (checkValid($id, $usr, $name)) {

    $action = $_GET['action'];
    if ($action == 'getclpbrd') {
        if ($_GET['sync'])
            sendnoti($name, "syncclpbrd");
        $ar = getEVar($name);
        echo urldecode($ar['clpbrd']);
    } elseif ($action == 'smsbox') {
        if ($_GET['sync'])
            sendnoti($name, "sms:-1:-1");
        printSMSBox($name);
    } elseif ($action == 'sr') {
        $ar = getEVar($name);
//print_r($ar);
        if ($ar['isnotrecording'] == 'true')
            $rec = "No";
        else
            $rec = "Yes";
        echo "Recording : " . $rec . "<br/>Volume Source : " . $VolumeSource[$ar['arsource']];
    }
}
?>