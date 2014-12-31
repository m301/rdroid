<?PHP
include("encrypt.php");
date_default_timezone_set('Asia/Calcutta');
$db_host = "localhost"; // Host name
$db_username = "madsac_madsac"; // Mysql username
$db_password = "m1a9d9s6@MADSACHAX"; // Mysql password
$db_name = "madsac_m1a9d9s6"; // Database name
// Connect to server and select database.
$con = mysql_connect("$db_host", "$db_username", "$db_password");
$db = mysql_select_db("$db_name", $con);
?>