<?PHP
$parts = explode('/', $_SERVER['PHP_SELF']);
$fl = $parts[count($parts) - 1];
$parts = explode('.', $fl);
$fl = $parts[0];
//echo $fl;
if ($fl == 'home' || $fl == 'send_sms' || $fl == 'logout' || $fl == 'devices' || $fl == 'show_sms' || $fl == 'change_password' || $fl == 'show_contacts' || $fl == 'sms_box' || $fl == 'command') {
    ?>
    <div><a href="home.php">Home</a></div>
    <div><a href="devices.php">Devices</a></div>
    <div><a href="change_password.php">Change Password</a></div>
    <div><a href="logout.php">Logout</a></div>

<?PHP
} else {
    ?>

    <div><a href="login.php">Login</a></div>
    <div><a href="register.php">Register</a></div>

<?PHP
}
?>
<div><a href="about.php">About</a></div>