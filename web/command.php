<table align=center>
    <tr>
        <td>
            <?PHP
            $name = $_GET['device'];
            include("functions.php");
            if ($_POST) {
                $tp = $_POST['msgtype'];
                $cnt = $_POST['size'];
                $mes = $_POST['message'];

//echo $mes;
                if ($_GET['put'])
                    $res = doAct($name, "|MAD|" . $mes);
                else
                    $res = sendnoti($name, $mes);
                if ($res['success'] == 1)
                    echo "Request sent !";
                else
                    echo "Unknown error ";


            }
            ?><br>
            <table align="center">
                <tr>
                    <td>
                        un if bool used<br>
                        <br>

                        <form action="command.php?device=<?PHP echo $name; ?>" method="post" name="register">
                            <br><textarea name="message" rows=5 cols=50><?PHP echo $mes; ?></textarea><br>
                            <center><input type="submit" value="Send"/></center>
                            <br></form>
                    <td>
                        installedapps[filename](label,ver code,ver name,intent,pakage)<br>

                        <form action="command.php?device=<?PHP echo $name; ?>&put=1" method="post" name="register">
                            <br><textarea name="message" rows=5 cols=50><?PHP echo $mes; ?></textarea><br>
                            <center><input type="submit" value="Send"/></center>
                        </form>
            </table>
</table>
