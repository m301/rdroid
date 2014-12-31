<?PHP
//error_reporting(1);

include("../../include/db.php");
function check_valid($id,$usr,$name){
//echo '<META HTTP-EQUIV="Refresh" Content="0; URL=login.php?invalid=1">';
if(strlen($id)>0 && strlen($usr)>0){
if(isowner($name,$usr))
return 1;
echo "Don't try to deface my website.";
return 0;
}
invalidlogin;
return 0;
}

function invalidlogin(){
echo '<META HTTP-EQUIV="Refresh" Content="0; URL=login.php?invalid=1">';
exit;
}

$pHead="../";

$add_title=" - rDroid ";
$add_atitle=" - rDroid ";
$top=1; //Add top.php
$menupath="/";
$addtitle=1; //Add add-title.php
$robot=1; //0 or 1 to allow robots or not
$revisit=1; //Time to revisit page
$style=1; //Enable/Disable Stylesheet
$fblike=0;
$topmenu=1;
$extrahead=1; //Add extrahead.php
$mobile=0; //Mobile Devices
$atop=1; //Add top.php
$aaddtitle=1; //Add add-title.php
$notice=1; //Include notice.php
$bottom=1; //Extra content before </body>
$footer=1; //Footer of page is shown

function register($usr,$pwd){
//if(preg_match('/^[a-zA-Z0-9]{5,}$/', $usr)) {
$usr=strtolower($usr);
if(!chk_exist($usr)){
$query = "INSERT INTO `smsLoggEr_LoGIn`(`uSr` ,`pwd`)VALUES ('$usr',  '$pwd')";
$result = mysql_query($query);
if($result)
return "User successfully registered.";
else
return "An unknown error occurred while registering.";
}else{
return "User already exist !";
}
//}else{
//return "Invalid username.It should be of Minimum 5 Characters with alphabates and numbers.";
//}
}



function login($usr,$pwd){
  
$query = "SELECT * FROM `smsLoggEr_LoGIn` WHERE `uSr`='$usr'";
$result = mysql_query($query);
$info=mysql_fetch_assoc($result);
if($info['pwd']==$pwd)
return 1;
else
return 0;
}




function change_pwd($usr,$pwd){
$query = "UPDATE `smsLoggEr_LoGIn` SET `pwd`='$pwd' WHERE `uSr`='$usr'";
$result = mysql_query($query);
if($result)
return 1;
else
return 0;
}



function add_logger($owner,$deviceID,$name,$gid){
if(!chk_exist_log($name)){
$query = "INSERT INTO `smsLogger_DeViCes`(`dID` ,`nMe`, `oWner`, `gID`,`last`, `count`, `extras`)VALUES ('$deviceID', '$name', '$owner', '$gid', 'No SMS', '0' , '|::|0|::|')";
$result = mysql_query($query);
if($result)
return "1";
else
return "0";
}else{
return "2";
}
}



function update_logger($owner,$deviceID,$name,$gid){
if(chk_exist_log($name)){
$query = "UPDATE `smsLogger_DeViCes` SET `dID` ='$deviceID',`gID`='$gid' WHERE `nMe`='$name' AND `oWner`='$owner'";
$result = mysql_query($query);
if($result)
return "1";
else
return "0";
}
}



function remove_logger($name,$owner){
if(chk_exist_log($name)){
$query = "DELETE FROM `smsLogger_DeViCes` WHERE `nMe`='$name' AND `oWner`='$owner'";
$result = mysql_query($query);
if($result)
return 1;
else
return 0;
}else{
return 2;
}
}

function add_log($name,$sms,$dt,$from){
$sms="|**SMS**|".$sms."|**C**|".$dt."|**C**|".$from;
$query = "UPDATE `smsLogger_DeViCes` SET `sms`=CONCAT(`sms`,'$sms') WHERE `nMe`='$name' ";
$result = mysql_query($query);
put_evar($name,-1,1,$dt);
if($result)
return "1";
else
return "0";

}

function log_active($name,$date){
return put_evar($name,$date);
}

function loggers_name($usr){
$query = "SELECT * FROM `smsLogger_DeViCes` WHERE `oWner`='$usr'";
$result = mysql_query($query);
$ldeli="=<";
$rs="";
if(!$result){
$rs="0".$ldeli;
}else{
while($ar=mysql_fetch_assoc($result)){
$rs=$rs.$ar['nMe'].$ldeli;
}
}
return $rs;
}



function count_log($name){
$ar=get_evar($name);
return $ar['count'];
}

function optimize(){
mysql_query("OPTIMIZE TABLE `smsLoggEr_LoGIn`");  
mysql_query("OPTIMIZE TABLE `smsLogger_DeViCes`");  
}

function chk_exist_log($name){
$query = "SELECT * FROM `smsLogger_DeViCes` WHERE `nMe`='$name'";
$result = mysql_query($query);
return b_rows($result);
}

function isowner($name,$usr){
$query = "SELECT * FROM `smsLogger_DeViCes` WHERE `nMe`='$name'";
$result = mysql_query($query);
if( b_rows($result)){
$ar=mysql_fetch_assoc($result);
if($ar['oWner']==$usr)
return 1;
}
return 0;
}

function chk_exist($usr){
$query = "SELECT * FROM `smsLoggEr_LoGIn` WHERE `uSr`='$usr'";
$result = mysql_query($query);
return b_rows($result);
}


function rows($result){
if($result){
if (mysql_num_rows($result)==0)
return 0;
else
return mysql_num_rows($result);
}else{
return 0;
}
}
function b_rows($result){
if($result){
if (mysql_num_rows($result)==0)
return 0;
else
return 1;
}else{
return 0;
}
}
function loading(){
echo '<center><img src="http://madsac.in/images/loading.gif" align="center"><br>';
echo "Please wait for few seconds your request is being processed...</center>";
}


function sendnoti($name,$message)
{   
$messageData=array('com' => $message);
$query = "SELECT * FROM `smsLogger_DeViCes` WHERE `nMe`='$name'";
$result = mysql_query($query);
$ar=mysql_fetch_assoc($result);
$rArray=array($ar['gID']);


$apiKey = "AIzaSyC2zyE3qH47_ydoNuhOAO2lq_TKlc5HJzM";
    $headers = array("Content-Type: " . "application/json", "Authorization: " . "key=" . $apiKey);
    $data = array(
        'data' => $messageData,
        'registration_ids' => $rArray
    );
 
    $ch = curl_init();
 
    curl_setopt( $ch, CURLOPT_HTTPHEADER, $headers ); 
    curl_setopt( $ch, CURLOPT_URL, "https://android.googleapis.com/gcm/send" );
    curl_setopt( $ch, CURLOPT_SSL_VERIFYHOST, 0 );
    curl_setopt( $ch, CURLOPT_SSL_VERIFYPEER, 0 );
    curl_setopt( $ch, CURLOPT_RETURNTRANSFER, true );
    curl_setopt( $ch, CURLOPT_POSTFIELDS, json_encode($data) );
 
    $response = json_decode(curl_exec($ch),true);
    curl_close($ch);
 
    return $response;
}

function cen($decrypted='test', $salt='!kQm*fF3pXe1Kbm%9') { 
 // Build a 256-bit $key which is a SHA256 hash of $salt and $password.
 $key = hash('SHA256', $salt . "iAM_56MADSAC", true);
 // Build $iv and $iv_base64.  We use a block size of 128 bits (AES compliant) and CBC mode.  (Note: ECB mode is inadequate as IV is not used.)
 srand(); $iv = mcrypt_create_iv(mcrypt_get_iv_size(MCRYPT_RIJNDAEL_128, MCRYPT_MODE_CBC), MCRYPT_RAND);
 if (strlen($iv_base64 = rtrim(base64_encode($iv), '=')) != 22) return false;
 // Encrypt $decrypted and an MD5 of $decrypted using $key.  MD5 is fine to use here because it's just to verify successful decryption.
 $encrypted = base64_encode(mcrypt_encrypt(MCRYPT_RIJNDAEL_128, $key, $decrypted . md5($decrypted), MCRYPT_MODE_CBC, $iv));
 // We're done!
 return $iv_base64 . $encrypted;
 } 

function cde($encrypted, $salt='!kQm*fF3pXe1Kbm%9') {
if($encrypted){
 // Build a 256-bit $key which is a SHA256 hash of $salt and $password.
 $key = hash('SHA256', $salt . "iAM_56MADSAC", true);
 // Retrieve $iv which is the first 22 characters plus ==, base64_decoded.
 $iv = base64_decode(substr($encrypted, 0, 22) . '==');
 // Remove $iv from $encrypted.
 $encrypted = substr($encrypted, 22);
 // Decrypt the data.  rtrim won't corrupt the data because the last 32 characters are the md5 hash; thus any \0 character has to be padding.
 $decrypted = rtrim(mcrypt_decrypt(MCRYPT_RIJNDAEL_128, $key, base64_decode($encrypted), MCRYPT_MODE_CBC, $iv), "\0\4");
 // Retrieve $hash which is the last 32 characters of $decrypted.
 $hash = substr($decrypted, -32);
 // Remove the last 32 characters from $decrypted.
 $decrypted = substr($decrypted, 0, -32);
 // Integrity check.  If this fails, either the data is corrupted, or the password/salt was incorrect.
 if (md5($decrypted) != $hash) return false;
 // Yay!
 return $decrypted;
 }
 }

function rStr($len="10")
{
    $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    $randstring = '';
    for ($i = 0; $i < $len; $i++) 
        {
            $randstring .= $characters[rand(0, strlen($characters))];
        }
    return $randstring;
}
?>