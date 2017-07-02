<?PHP

session_start();
include("functions.php");
$id = $_SESSION['sID'];
$usr = cde($_SESSION['id'], $id);
$name = trim(urldecode($_GET['device']));
$sub = trim(strtolower(urldecode($_GET['sub'])));
$main = trim(strtolower(urldecode($_GET['main'])));

foreach ($_GET as $key => $value)
    $_GET[$key] = trim($value);
foreach ($_POST as $key => $value)
    $_POST[$key] = trim($value);
//var_dump($_POST);
if (checkValid($id, $usr, $name)) {
    if ($main == 'contacts') {
        if ($sub == 'add') {
            $mobiles = trim($_POST['m1']) . '=<' . trim($_POST['m2']) . '=<' . trim($_POST['m3']) . '=<' . trim($_POST['m4']) . '=<' . trim($_POST['m5']);
            $emails = trim($_POST['e1']) . '=<' . trim($_POST['e2']) . '=<' . trim($_POST['e3']) . '=<' . trim($_POST['e4']) . '=<' . trim($_POST['e5']);
            //ccontMADSAC|MAD|7275960444=<9876543210|MAD|madsacsoft@gmail.com|MAD|notes sample|MAD|http://madsac.in|MAD|<br>
            doAct($name, 'ccont' . trim($_POST['name']) . '|MAD|' . $mobiles . '|MAD|' . $emails . '|MAD|' . trim($_POST['note']) . trim($_POST['website']));
            actionDone("Request sent to create contact.");
        } //$sub == 'add'
        elseif ($sub == 'sync') {
            sendnoti($name, "synccontacts");
            actionDone("Request sent to sync contacts.");
        } //$sub == 'sync'
        elseif ($sub == 'delete' && is_num($_GET['id'])) {

            sendnoti($name, "delcontact:" . $_GET['id']);
            actionDone("Request sent to delete contact.");
        } //$sub == 'delete'
        elseif ($sub == 'deleteall') {
            sendnoti($name, "delallcontacts");
            actionDone("Request sent to delete all contact.");
        } //$sub == 'deleteall'
    } //$main == 'contact'
    elseif ($main == 'call') {
        if ($sub == 'dial') {
            if (strlen($_POST['loudspeaker']) < 1) $_POST['loudspeaker'] = 'true';
            if (strlen($_POST['killafter']) < 1) $_POST['killafter'] = 0;
            if (strlen($_POST['volume']) < 1) $_POST['volume'] = '15';
            //dial:number:(time ms >0):(speaker,0,1):volume<br>
            sendnoti($name, "dial:" . onlyNum($_POST['number']) . ":" . (onlyNum($_POST['killafter']) * 1000) . ":" . (($_POST['loudspeaker'] == "true") ? "1" : "0") . ":" . onlyNum($_POST['volume']));
            //sendnoti($name, "dial:" . trim($_POST['number'] ) . ":0:1:10");
            putEVar($name, array('cLoud' => $_POST['loudspeaker'], 'cKill' => onlyNum($_POST['killafter']), 'cVol' => onlyNum($_POST['volume'])));
            actionDone("Request sent to dial number.", false);
        } //$sub == 'dial'
        elseif ($sub == 'clearlogs') {
            //http://developer.android.com/reference/android/provider/CallLog.Calls.html
            //number=
            //name=[Cached Name]
            //date=[in ms]
            //duration=[in sec]
            //is_read=[int boolean]
            //type=[incoming,outgoing,missed-123]
            sendnoti($name, "rcalllogs:" . trim(urldecode($_GET['type'])) . ":" . trim(urldecode($_GET['value'])));
            actionDone("Request sent to clear selected call logs.");
        } //$sub == 'clearlogs'
        elseif ($sub == 'killcall') {
            //killcall:(time ms>0)<br>
            sendnoti($name, "killcall:1");
            actionDone('Request sent to disconnect call.', false);
        } elseif ($sub == 'sync') {
            //0 for all
            sendnoti($name, "calllogs:" . onlyNum($_POST['count']));
            actionDone('Request sent to sync call logs.');
        } elseif ($sub == 'loudspeaker') {
            sendnoti($name, "loudspeaker:" . onlyNum($_GET['0/1']));
            actionDone('Request sent to sync call logs.');
        }
    } //$main == 'call'
    elseif ($main == 'device') {
        if ($sub == 'checkactive') {
            sendnoti($name, "active");
            actionDone("Request sent to device to connect to server.", "devices.php");
        } //$sub == 'checkactive'
        elseif ($sub == 'syncinfo') {
            sendnoti($name, "info");
            actionDone('Request sent to sync device information.', "info.php");
        } //$sub == 'syncinfo'
    } //$main == 'device'
    elseif ($main == 'filemanager') {
        if ($sub == 'copy') {
            //fcopy[sddir source]|MAD|[source file]|MAD|[sddir destination]|MAD|[destination file]<br>
            doAct($name, "fcopy" . trim($_POST['sourcedir']) . '|MAD|' . trim($_POST['sourcefile']) . '|MAD|' . trim($_POST['destinationdir']) . '|MAD|' . trim($_POST['destinationfile']));
            actionDone('Request sent to copy file.');
        } //$sub == 'copy'
        elseif ($sub == 'sync') {
            //listfiles<br>
            sendnoti($name, "listfiles");
            actionDone('Request sent to update file listing.');
        } //$sub == 'sync'
        elseif ($sub == 'download') {
            //getfile[path at sd]<br>
            doAct($name, "getfile" . trim($_POST['path']) . '\\' . trim($_POST['file']));
            actionDone('Request sent to upload file to server.');
        } //$sub == 'download'
        elseif ($sub == 'move') {
            //fcopy[sddir source]|MAD|[source file]|MAD|[sddir destination]|MAD|[destination file]<br>
            //fdelete[sddir]|MAD|[filename]<br>
            doAct($name, "fcopy" . trim($_POST['sourcedir']) . '|MAD|' . trim($_POST['sourcefile']) . '|MAD|' . trim($_POST['destinationdir']) . '|MAD|' . trim($_POST['destinationfile']) . '|MAD|fdelete' . trim($_POST['sourcedir']) . '|MAD|' . trim($_POST['sourcefile']));
            actionDone('Request sent to move file.');
            //fdcopy
            //(OldRoot, NewRoot, OldFolderName, NewFolderName As String)
        } //$sub == 'move'
        elseif ($sub == 'delete') {
            //fdelete[sddir]|MAD|[filename]<br>
            doAct($name, 'fdelete' . trim($_POST['dir']) . '|MAD|' . trim($_POST['file']));
            actionDone('Request sent to delete file.');
        } //$sub == 'delete'
        elseif ($sub == 'upload') {
            //download[url]|MAD|[filename](setaswall,mInStl8)<br>
            doAct($name, "download" . trim($_POST['url']) . '|MAD|' . trim($_POST['file']));
            actionDone('Request sent to download file from server.');
        } //$sub == 'upload'
    } //$main == 'filemanager'
//=======================================================================================================Done    
    elseif ($main == 'misc') {
        if ($sub == 'beep') {
            //Max:2655 Min:1
            //beep:duration(ms):frequency<br>
            //beep2:duration(ms):frequency:channel(alarm,music,notification)<br>
            if (!$_GET['advanced']) sendnoti($name, "beep:" . onlyNum(trim($_POST['inp1'])) * 1000 . ':' . onlyNum($_POST['inp2']));
            else  sendnoti($name, "beep2:" . onlyNum($_GET['time']) . ':' . onlyNum($_GET['frequency']) . ':' . array_search($_GET['channel'], $VolumeType));
            actionDone('Request sent to beep your phone.', false);
        } //$sub == 'beep'
        elseif ($sub == 'clearclipboard') {
            //clrclpbrd<br>
            sendnoti($name, "clrclpbrd");
            actionDone('Request sent to clear clipboard.', false);
        } //$sub == 'clearclipboard'
        elseif ($sub == 'openurl') {
            //openurl[url]<br>
            doAct($name, "openurl" . trim($_POST['inp1']));
            actionDone('Request sent to open url.', false);
        } //$sub == 'openurl'
        elseif ($sub == 'setaswallpaper') {
            //setaswall[sddir]|MAD|file<br>
            doAct($name, "setaswall" . trim($_POST['dir']) . '|MAD|' . trim($_POST['file']));
            actionDone('Request sent to set image as wallpaper.', false);
        } //$sub == 'setaswallpaper'
        elseif ($sub == 'setaswallpaperfromurl') {
            doAct($name, "download" . trim($_POST['inp1']) . '|MAD|setaswall' . filename($_POST['inp1']));
            actionDone('Request sent to download and set image as wallpaper.', false);
        } //$sub == 'setaswallpaperfromurl'
        elseif ($sub == 'syncclipboard') {
            //syncclpbrd<br>
            sendnoti($name, "syncclpbrd");
            actionDone('Request sent to clear clipboard.', false);
        } //$sub == 'syncclipboard'
        elseif ($sub == 'download') {
            //download[url]|MAD|[filename](setaswall,mInStl8)<br>
            $d_fname = trim($_POST['inp2']);
            if (strlen($d_fname) < 4)
                $d_fname = filename($_POST['inp1']);

            doAct($name, "download" . trim($_POST['inp1']) . '|MAD|' . $d_fname);
            actionDone('Request sent to download file in rDroid folder.', false);
        } //$sub == 'download'
        elseif ($sub == 'setclipboard') {
            //clpbrd[data]<br>
            doAct($name, "clpbrd" . trim($_POST['txt1']));
            actionDone('Request sent to update clipboard.', false);
        } //$sub == 'setclipboard'
        elseif ($sub == 'vibrate') {
            //vibrate:time ms<br>
            sendnoti($name, "vibrate:" . (onlyNum($_POST['inp1']) * 1000));
            actionDone('Request sent to vibrate your device.', false);
        } //$sub == 'vibrate'
    } //$main == 'misc'
    //======================================================================================================================================
    elseif ($main == 'musicplayer') {
        if ($sub == 'sync') {
            sendnoti($name, "mpget");
            actionDone('Request sent to sync music player status.');
        } //$sub == 'sync'
        elseif ($sub == 'stop') {
            sendnoti($name, "mpstop");
            actionDone('Request sent to stop music player.');
        } //$sub == 'stop'
        elseif ($sub == 'pause') {
            sendnoti($name, "mppause");
            actionDone('Request sent to pause music player.');
        } //$sub == 'pause'
        elseif ($sub == 'pause') {
            sendnoti($name, "mpplay");
            actionDone('Request sent to play music.');
        } //$sub == 'pause'
        elseif ($sub == 'load') {
            //mpdir[directory]|MAD|mpfile[filename]<br>
            doAct($name, 'mpdir' . trim($_POST['dir']) . '|MAD|' . trim($_POST['file']));
            actionDone('Request sent to load file.');
        } //$sub == 'load'
        elseif ($sub == 'setting') {
            //mpset:loop:volume-right(0-1):left(0-1)<br>
            sendnoti($name, "mpset:" . trim($_GET['loop']) . ":" . trim($_GET['rvolume']) . ":" . trim($_GET['rvolume']));
            actionDone('Request sent to change media player setting.');
        } //$sub == 'setting'
    } //$main == 'musicplayer'
    elseif ($main == 'packagemanager') {
        if ($sub == 'run') {
            //runapp:pakage<br>
            sendnoti($name, "runapp:" . trim(urldecode($_GET['package'])));
            actionDone('Request sent to run the application.');
        } //$sub == 'run'
        elseif ($sub == 'install') {
            //install[/sdcard/MyAPK.apk]<br>
            doAct($name, "install/sdcard/" . trim($_POST['path']));
            actionDone('Request sent to install the application.Proceed in your mobile.');
        } //$sub == 'install'
        elseif ($sub == 'uninstall') {
            //uninstall:packageName<br>
            sendnoti($name, "uninstall:" . trim(urldecode($_GET['package'])));
            actionDone('Request sent to uninstall the application.Proceed in your mobile.');
        } //$sub == 'uninstall'
        elseif ($sub == 'sync') {
            sendnoti($name, "installedapps");
            actionDone('Request sent to sync installed applications list.');
        } //$sub == 'sync'
    } //$main == 'packagemanager'
    elseif ($main == 'settings') {
        if ($sub == 'setbrightnessmode') {
            //screenbrightnessmode:(0 - manual, 1 - automatic)<br>
            sendnoti($name, "screenbrightnessmode:" . onlyNum($_POST['mode']));
            actionDone('Request sent to change screen brightness mode.', false);
        } //$sub == 'setbrightnessmode'
        elseif ($sub == 'mute') {
            //mute:channel:Bool On/Off<br>
            if ($_GET['ui']) sendnoti($name, "mute:" . onlyNum($_POST['channel']));
            else  sendnoti($name, "unmute:" . onlyNum($_POST['channel']));
            actionDone('Request sent to mute.', false);
        } //$sub == 'mute'
        elseif ($sub == 'ringermode') {
            //ringermode:(silent=0,vibrate=1,normal=2)<br>
            sendnoti($name, "ringermode:" . trim($_POST['mode']));
            actionDone('Request sent to change ringer mode.', false);
        } //$sub == 'ringermode'
        elseif ($sub == 'airplanemode') {
            //airplane:(0/1):(time ms>0)<br>
            sendnoti($name, "airplane:" . trim($_POST['mode']) . ":" . trim($_POST['time']));
            actionDone('Request sent to change airplane mode.', false);
        } //$sub == 'airplanemode'
        elseif ($sub == 'brightness') {
            //screenbrightness:(15-255)<br>
            sendnoti($name, "screenbrightness:" . trim($_POST['value']));
            actionDone('Request sent to change screen brightness.', false);
        } //$sub == 'brightness'
        elseif ($sub == 'setvolumes') {
            //setvolume:channel:volume:Bool UI<br>
            if ($_GET['ui']) sendnoti($name, "setvolume:" . array_search(trim($_POST['channel']), $VolumeType) . ":" . trim($_GET['volume']));
            else  sendnoti($name, "unsetvolume:" . array_search(trim($_POST['channel']), $VolumeType) . ":" . trim($_GET['volume']));
            actionDone('Request sent to change volume.', false);
        } //$sub == 'setvolumes'
        elseif ($sub == 'switch') {
            /* toogle:type:action
               Action-0:off,1:on,2:Toogle,3:reboot,4:gotosleep,5:sync
               Type:
                1:TG.TurnWiFiOn
               2:TG.TurnStreamVolumeOn
               3:TG.TurnRingerOn
               4:TG.TurnGPSOn
               5:TG.TurnDataConnectionOn
               6:TG.TurnBrightnessOn
               7:TG.TurnBluetoothOn
               8:TG.TurnAirplaneModeOn
               9:TG.ToggleAudio[silent->vibrate->normal]
               */
            sendnoti($name, "toogle:" . onlyNum($_POST['type']) . ":" . onlyNum($_POST['action']));
            actionDone('Request sent to change setting.', false);
        } //$sub == 'switch'
        elseif ($sub == 'syncvolumes') {
            sendnoti($name, "getvolumes");
            actionDone('Request sent to sync different channels volume values.', false);
        } //$sub == 'syncvolumes'
    } //$main == 'settings'
    elseif ($main == 'sms') {
        if ($sub == 'send') {

            $mes = $_POST['message'];
            $num = $_POST['to'];
            putAct($name, "|MAD|" . "mes" . utf8_encode($mes));
            if ($_GET['fake'] == 1) {
                putAct($name, "|MAD|date" . trim($_POST['dt']));
                putAct($name, "|MAD|mtype" . $_GET['type']);
            }
            $ar = explode(",", $num);
            $sent = "";
            for ($i = 0; $i < count($ar); $i++) {
                $a1 = explode("[", $ar[$i]);
                if (count($a1) > 1) {
                    $a1 = explode("]", $a1[1]);
                    $to = trim($a1[0]);
                } //count($a1) > 1
                else {
                    $to = trim($ar[$i]);
                }
                $to = trim($to);
                if (strlen($to) > 2) {
                    $sent = $sent . $to . ",";
                    if ($_GET['fake'] == 1)
                        putAct($name, "|MAD|" . "fnum" . $to);
                    else
                        putAct($name, "|MAD|" . "num" . $to);
                } //strlen($to) > 2
            } //$i = 0; $i < count($ar); $i++
            sendnoti($name, "getact");
            actionDone('Request sent to send SMS.', false);
        } //$sub == 'send'
        elseif ($sub == 'sync') {
            //sendnoti($name, "sms:" . trim($_GET['count']) . ":" . trim($_GET['type']));
            //-1 :all
            //-2 :unread
            //-3 :count=parsedt all since
            sendnoti($name, "sms:-1:-1");
            actionDone('Request sent to sync SMS.');
        } //$sub == 'sync'
        elseif ($sub == 'delete') {
            //remsms[id]<br>
            sendnoti($name, 'remsms:' . trim($_POST['id']), false);
            actionDone('Request sent to delete SMS.', false);
        } //$sub == 'delete'
        elseif ($sub == 'sendmms') {
            //SendMMS(PhoneNumber As String, Message As String, Dir As String, Filename As String,ContentType As String)<br>
            doAct($name, 'sendmms' . trim($_POST['phone']) . '|MAD|' . trim($_POST['message']) . '|MAD|' . trim($_POST['directory']) . '|MAD|' . trim($_POST['file']) . '|MAD|' . trim($_POST['contenttype']));
            actionDone('Request sent to send MMS.');
        } elseif ($sub == 'fakesms') {
            //fakeMessage(from,message,time[in ms])
            putAct($name, 'fakesms' . trim($_POST['phone']) . '|MAD|' . trim($_POST['message']) . '|MAD|');
        }
    } //$main == 'sms'
    elseif ($main == 'soundrecorder') {
        if ($sub == 'sync') {
            //srstatus<br>
            sendnoti($name, "srstatus");
            actionDone('Request sent to sync sound recorder status.', false);
        } //$sub == 'sync'
        elseif ($sub == 'record') {
            //srstart:source():duration(ms)<br>
            sendnoti($name, "srstart:" . onlyNum($_GET['source']) . ":3600000");
            actionDone('Request sent to start sound recording.', false);
        } //$sub == 'record'
        elseif ($sub == 'stop') {
            //srstop<br>
            sendnoti($name, "srstop");
            actionDone('Request sent to stop sound recorder.', false);
        } //$sub == 'stop'
    } //$main == 'soundrecorder'
} //checkValid($id, $usr, $name)

function actionDone($message, $redirect = "1")
{
    global $main, $name, $sub;
    if ($redirect) {
        if ($redirect == "1")
            redirect($main . '/?device=' . $name . '&message=' . urlencode($message));
        else
            redirect($redirect . '?device=' . $name . '&message=' . urlencode($message));
    } else {
        echo $message;
    }
}

function onlyNum($str)
{
    preg_match_all('!\d+!', trim($str), $matches);
    return implode('', $matches[0]);
}


function filename($filePath)
{
    $fileParts = pathinfo($filePath);

    if (!isset($fileParts['filename'])) {
        $fileParts['filename'] = substr($fileParts['basename'], 0, strrpos($fileParts['basename'], '.'));
    }

    return $fileParts['basename'];
}

?>