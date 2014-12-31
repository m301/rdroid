<?PHP
$ipath = "../";
include($ipath . "functions.php");
$query = "SELECT * FROM  `` ";
$result = mysql_query($query);
echo '<table>';
while ($ar = mysql_fetch_assoc($result)) {
    echo '<tr>';
    foreach ($ar as $key => $var)
        echo '<td><b>' . $key . '</b><br><textarea rows=10 cols=20>' . urldecode(strd($var)) . '</textarea>';
//strd
}

?>
