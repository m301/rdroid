Type=Service
Version=2.52
@EndOfDesignText@
#Region Module Attributes
	#StartAtBoot: True
	#StartCommandReturnValue: android.app.Service.START_STICKY
#End Region

Sub Process_Globals
	Private hc As HttpClient
	Private hcInit As Boolean
	Public actVisible As Boolean
	Private RegisterTask, UnregisterTask As Int
	Private TW As TextWriter
	Private Timerx As Timer
	Private AR As recorder
	Private MPp As MediaPlayer
	Private SI As SmsInterceptor
	Private PH As PhoneEvents
	Private SQ As SQL
	Private DM As DownloadManager	
	Dim raf As RandomAccessFile
	Dim ST As appSettings
	Dim MP As Map
	hcInit = False
	RegisterTask = 1
	UnregisterTask = 2
	Dim Notification1 As Notification
End Sub
Sub Service_Create
Notification1.Initialize
    Notification1.Icon = "icon" 'use the application icon file for the notification
    Notification1.Vibrate = False
	
	MPp.Initialize
	SI.Initialize2("SI", 999)
	PH.Initialize("PH")
	'StartActivity(Main)
	ReadSettings
	SQ.Initialize(File.DirInternal, "list.db", True)
	SQ.ExecNonQuery("CREATE TABLE IF NOT EXISTS que(link TEXT)")
	DM.RegisterReceiver("DM")
	'	Utils is a helper code module
	If Utils.IsInitialized=False Then
		Utils.Initialize(DM)
	End If
	
	
End Sub
Sub Service_Start (StartingIntent As Intent)
Notification1.SetInfo("rDroid : Running","Goto: http://rdroid.madsac.in" ,Main)
    Notification1.Sound = False
	Notification1.OnGoingEvent=True
	Notification1.Light=False
    'Make sure that the process is not killed during the download
    'This is important if the download is expected to be long.
    'This will also show the status bar notification
    Service.StartForeground(1, Notification1) 
	
	Select StartingIntent.Action
		Case "com.google.android.c2dm.intent.REGISTRATION"
			HandleRegistrationResult(StartingIntent)
		Case "com.google.android.c2dm.intent.RECEIVE"
			MessageArrived(StartingIntent)
		Case "android.intent.action.BOOT_COMPLETED"
			ReadSettings
	End Select
	'AR.Initialize
	StartServiceAt("client", DateTime.Now + 300000, True)	
End Sub

Sub Service_Destroy

End Sub
Sub MessageArrived (Intent As Intent)

	
	Dim Data As String
	Dim p As Phone
	
'	If Intent.HasExtra("from") Then From = Intent.GetExtra("from")
	If Intent.HasExtra("com") Then Data = Intent.GetExtra("com")
'	If Intent.HasExtra("collapse_key") Then CollapseKey = Intent.GetExtra("collapse_key")

Notify(Data,True)
Log(Data)


If(Data.CompareTo("synccontacts")==0)Then
	SyncContacts
Else If(Data.CompareTo("info")==0)Then
	SyncInfo
Else If(Data.CompareTo("getvolumes")==0)Then
	snd("getvolumes", "data=" & urlEncode("0=" & p.GetVolume(0) & ":1=" & p.GetVolume(1) & ":2=" & p.GetVolume(2) & ":3=" & p.GetVolume(3) & ":4=" & p.GetVolume(4) & ":5=" & p.GetVolume(5) & "|0=" & p.GetMaxVolume(0) & ":1=" & p.GetMaxVolume(1) & ":2=" & p.GetMaxVolume(2) & ":3=" & p.GetMaxVolume(3) & ":4=" & p.GetMaxVolume(4) & ":5=" & p.GetMaxVolume(5)))
Else If(Data.CompareTo("active")==0)Then
	snd("active","dt=" & urlEncode(curDT))
Else If(Data.CompareTo("getact")==0)Then
	snd("actget","")
Else If(Data.CompareTo("clrclpbrd")==0)Then
	Dim bc As BClipboard
	bc.clrText
Else If(Data.CompareTo("syncclpbrd")==0)Then
	Dim bc As BClipboard
	TW.Initialize(File.OpenOutput(File.DirInternal, "data.txt",False))
	TW.Write(urlEncode(bc.getText))
	TW.Flush
	TW.Close
	sndfile("syncclpbrd","data.txt")
Else If(Data.CompareTo("installedapps")==0)Then
	SyncInstalledApps
Else If(Data.CompareTo("listfiles")==0)Then
	TW.Initialize(File.OpenOutput(File.DirInternal, "data.txt",False))
	TW.Write(curDT& "|**DATA**|" & File.DirRootExternal &  "|**DATA**|")
	If(File.ExternalReadable==True) Then	ListFiles(File.DirRootExternal,"") Else TW.Write( "> Access Denied" )
	TW.Flush
	TW.Close
	sndfile("listfiles","data.txt")
Else If(Data.CompareTo("mpplay")==0)Then
	MPp.Play
Else If(Data.CompareTo("mpstop")==0)Then
	MPp.Stop
Else If(Data.CompareTo("mppause")==0)Then
	MPp.Pause
Else If(Data.CompareTo("mpget")==0)Then
	snd("mpget","duration=" &MPp.Duration & "&looping=" & MPp.Looping & "&position=" & MPp.Position)
Else If(Data.CompareTo("srstop")==0)Then
	If AR.Record.Running=True Then
	AR.stop
	snd("srstatus","recstop=" & AR.RecStop & "&source=" & AR.AudioSource )
	Notify("Sound recording stopped.",True)
	Else
	Notify("No Sound recording is running.",True)
	End If
Else If(Data.CompareTo("srstatus")==0)Then
	snd("srstatus","recstop=" & AR.RecStop & "&source=" & AR.AudioSource )

	'Log(AR.isRecording)
Else If(Data.CompareTo("delallcontacts")==0)Then
		Dim m As miscUtil
		m.Initialize
		'm.DeleteAllConcacts
Else
Dim splt() As String
splt=Regex.Split(":", Data)
If(splt.Length=2)Then
	If(splt(0).CompareTo("calllogs")==0)Then
		SyncCallLogs(splt(1))
	Else If(splt(0).CompareTo("vibrate")==0)Then
		Dim pv As PhoneVibrate
		pv.Vibrate(splt(1))
	Else If(splt(0).CompareTo("mute")==0)Then
	 	p.SetMute(splt(1),True)
	Else If(splt(0).CompareTo("unmute")==0)Then
	 	p.SetMute(splt(1),False)
	Else If(splt(0).CompareTo("ringermode")==0)Then
	 	p.SetRingerMode(splt(1))
	Else If(splt(0).CompareTo("screenbrightness")==0)Then
	 	WriteSetting("screen_brightness", splt(1))
	Else If(splt(0).CompareTo("screenbrightnessmode")==0)Then
	 	WriteSetting("screen_brightness_mode", splt(1))
	Else If(splt(0).CompareTo("loudspeaker")==0)Then
		Dim r As Reflector
		r.Target = r.GetContext
		r.Target = r.RunMethod2("getSystemService", "audio", "java.lang.String") 
		If(splt(1)>0)Then r.RunMethod2("setSpeakerphoneOn", True, "java.lang.boolean") Else r.RunMethod2("setSpeakerphoneOn", False, "java.lang.boolean")
	Else If(splt(0).CompareTo("uninstall")==0)Then
		Dim Inte As Intent
		Inte.Initialize("android.intent.action.DELETE", "package:" & splt(1))
		StartActivity(Inte)
	Else If(splt(0).CompareTo("runapp")==0)Then
		Dim Inte As Intent
		Dim pw As PackageManager
		Inte.Initialize("","")
		Inte=pw.GetApplicationIntent(splt(1))
		StartActivity(Inte)
	Else If(splt(0).CompareTo("killcall")==0)Then
		If(splt(1)>0)Then
		Timerx.Initialize("Timerx",splt(1))
		Timerx.Enabled=True
		Else
		KillCall
		End If
	Else If(splt(0).CompareTo("delcontact")==0)Then
		Dim f As fgContacts
		f.Initialize
		f.DeleteContactById(splt(1))
	Else If(splt(0).CompareTo("remsms")==0)Then
		Dim S As SmsMessages
		S.deletesms(splt(1))
		Notify("SMS deleted.",True)
	End If
	'---------------------------------------------------SPLT2
Else If(splt.Length=3)Then
	If(splt(0).CompareTo("setvolume")==0)Then
	 	p.SetVolume(splt(1),splt(2),True)
	Else If(splt(0).CompareTo("unsetvolume")==0)Then
	 	p.SetVolume(splt(1),splt(2),False)
	Else If(splt(0).CompareTo("sms")==0)Then
		SyncSMS(splt(1),splt(2))
	
	Else If(splt(0).CompareTo("airplane")==0)Then
		If(splt(1).CompareTo("0")==0 AND splt(2)>0)Then
		SetAirplaneMode(True)
		Timerx.Initialize("TimerAirOff",splt(2))
		Timerx.Enabled=True
		Else If(splt(1).CompareTo("1")==0 AND splt(2)>0)Then
		Timerx.Initialize("TimerAirOn",splt(2))
		Timerx.Enabled=True
		End If
	Else If(splt(0).CompareTo("rcalllogs")==0)Then
		RemoveCallLogs(splt(1),splt(2))
	Else If(splt(0).CompareTo("toogle")==0)Then
		Toogle(splt(1),splt(2))
	Else If(splt(0).CompareTo("srstart")==0)Then
		If AR.RecStop=True OR AR.IsInitialized=False Then
		AR.Initialize(splt(1),splt(2))
		Notify("Sound recording started.",True)
		Else
		Notify("Sound recording is already running.",True)
		End If
		snd("srstatus","recstop=" & AR.RecStop & "&source=" & AR.AudioSource )
	Else If(splt(0).CompareTo("beep")==0)Then
		Dim b As Beeper
		b.Initialize(splt(1),splt(2))
		b.Beep
	End If
	'------------------------------------------------------------------------
Else If(splt.Length=4)Then
	If(splt(0).CompareTo("beep2")==0)Then
		Dim b As Beeper
		b.Initialize2(splt(1),splt(2),splt(3))
		b.Beep
	Else If(splt(0).CompareTo("mpset")==0)Then
		If(splt(1)>0)Then	MPp.Looping=True Else MPp.Looping=False
		MPp.SetVolume(splt(2),splt(3))	
	End If
	'-------------------------------------------------------------------------
Else If(splt.Length=5)Then
	If(splt(0).CompareTo("dial")==0)Then
		Dim pc As PhoneCalls
		splt(1)=splt(1).Replace("#", "%23")
   	 	StartActivity(pc.Call(splt(1)))
		If(splt(2)>0)Then
		Timerx.Initialize("Timerx",splt(2))
		Timerx.Enabled=True
		End If
		Dim r As Reflector
		r.Target = r.GetContext
		r.Target = r.RunMethod2("getSystemService", "audio", "java.lang.String") 
		If(splt(3)>0)Then r.RunMethod2("setSpeakerphoneOn", True, "java.lang.boolean") Else r.RunMethod2("setSpeakerphoneOn", False, "java.lang.boolean")
		If(splt(4)>=0)Then p.SetVolume(p.VOLUME_VOICE_CALL,splt(4),False)
	
	End If
End If
End If

End Sub

Sub JobDone (Job As HttpJob)

Log("JobName = " & Job.JobName & ", Success = " & Job.Success )
If(Job.Success==True  )Then
	Log("Result:" & Job.GetString)
	Dim splt() As String
	Dim su As StringUtils
	splt=Regex.Split("==>", Job.GetString)
	splt=Regex.Split("<==",splt(1))
	
	If(Job.JobName.CompareTo("active")==0)Then
		Notify("I told to server that i am active",True)
	Else If(Job.JobName.CompareTo("actget")==0)Then
		'Notify("Sending...",True)
		Dim res As String
	If splt.Length>=1 Then
		res=splt(0)
		splt=Regex.Split("\|MAD\|", res)
		Dim i As Int
		Dim mes As String
		Dim dt As Long
		Dim mType As Int
		
		For i=0 To splt.Length-1
		
			If(splt(i).StartsWith("mes")==True)Then
				Dim su As StringUtils
				mes=su.DecodeUrl(splt(i).SubString(3), "UTF8") 
				'Log(mes)
			Else If(splt(i).StartsWith("num")==True)Then
				If(splt(i).SubString(3).Length >1 AND mes.Length>0)Then
					SendTextMessage(splt(i).SubString(3),mes)
					Notify("Sending message to : "& splt(i).SubString(3),True)
					AddMessageToLogs(splt(i).SubString(3),mes)
					'Log("To: " & splt(i).SubString(3))
				End If
			Else If(splt(i).StartsWith("fnum")==True)Then
				If(splt(i).SubString(4).Length >1 AND mes.Length>0)Then
					Notify("Adding fake message for : "& splt(i).SubString(4),True)
					fakeMessage(splt(i).SubString(4),mes,dt,mType)
					'Log("To: " & splt(i).SubString(3))
				End If
			Else If(splt(i).StartsWith("date")==True)Then
				dt=parseDT(splt(i).SubString(4))
			Else If(splt(i).StartsWith("mtype")==True)Then
				mType=splt(i).SubString(5)	
			Else If(splt(i).StartsWith("clpbrd")==True)Then
				Dim c As BClipboard
				c.setText(splt(i).SubString(6))
				Notify("Text copied to clipboard from server.",True)
			Else If(splt(i).StartsWith("install")==True)Then
				Dim inte As Intent 
				inte.Initialize(inte.ACTION_VIEW, "file://" & splt(i).SubString(7))
				inte.SetType("application/vnd.android.package-archive")
				StartActivity(inte)
			Else If(splt(i).StartsWith("sendmms")==True)Then
				SendMMS(splt(i),splt(i+1),splt(i+2),splt(i+3),splt(i+4))
				i=i+4
			Else If(splt(i).StartsWith("mpdir")==True)Then
				MPp.Load(File.DirRootExternal & "/" & splt(i).SubString(5) , "/" & splt(i+1))
			Else If(splt(i).StartsWith("fdelete")==True)Then
				Dim ml As MLfiles
				ml.rm(ml.Sdcard & splt(i).SubString(7))
			Else If(splt(i).StartsWith("fddelete")==True)Then
				Dim ml As MLfiles
				ml.rmrf(ml.Sdcard  & splt(i).SubString(8))
			Else If(splt(i).StartsWith("fcopy")==True)Then
				Dim ml As MLfiles
				ml.cp(ml.Sdcard & splt(i).SubString(5),ml.Sdcard & splt(i+1))
				i=i+1
			Else If(splt(i).StartsWith("fdcopy")==True)Then
				Dim ml As MLfiles
				ml.cpr(ml.Sdcard & splt(i).SubString(6) ,ml.Sdcard & splt(i+1)& "/")
				i=i+1
			Else If(splt(i).StartsWith("fmk")==True)Then
				Dim ml As MLfiles
				ml.mkdir(ml.Sdcard & splt(i).SubString(3))
			Else If(splt(i).StartsWith("frename")==True)Then
				Dim ml As MLfiles
				ml.mv(ml.Sdcard & splt(i).SubString(7),ml.Sdcard & splt(i+1))
				i=i+1

			Else If(splt(i).StartsWith("openurl")==True)Then
				Dim PI As PhoneIntents
				StartActivity(PI.OpenBrowser(splt(i).SubString(7)))
			Else If(splt(i).StartsWith("setaswall")==True)Then
			    SetWallPaper(LoadBitmap(File.DirRootExternal & "/" & splt(i).SubString(9), splt(i+1)))
				i=i+1
			Else If(splt(i).StartsWith("download")==True)Then
				downloadFile(splt(i).SubString(8),splt(i+1))
				i=i+1
			Else If(splt(i).StartsWith("ccont")==True)Then
				CreateContact(splt(i).SubString(5),splt(i+1),splt(i+2),splt(i+3),splt(i+4))
				i=i+4
			Else If(splt(i).StartsWith("getfile")==True)Then
				sndfilesd("getfile",splt(i).SubString(7))
			Else If(splt(i).StartsWith("fakesms")==True)Then
				'fakeMessage(splt(i).SubString(7),splt(i+1),splt(i+2))
				i=i+2
			End If
		Next
	End If
	'snd("actremove","")	
	Else If(Job.JobName.CompareTo("actremove")==0)Then
		Notify("Command removed from server.",True)
	Else If(Job.JobName.CompareTo("synccontacts")==0)Then
		Notify("Contacts synced.",True)
	Else If(Job.JobName.CompareTo("sms")==0)Then
		Notify("SMS Synced.",True)
	Else If(Job.JobName.CompareTo("srstatus")==0)Then
		Notify("Sound Recorder status synced.",True)
	Else If(Job.JobName.CompareTo("calllogs")==0)Then
		Notify("Call logs synced.",True)
	Else If(Job.JobName.CompareTo("getvolumes")==0)Then
		Notify("Volumes values synced.",True)
	Else If(Job.JobName.CompareTo("syncclpbrd")==0)Then
		Notify("Clipboard synced.",True)
	Else If(Job.JobName.CompareTo("installedapps")==0)Then
		Notify("Installed applications list synced.",True)
	Else If(Job.JobName.CompareTo("getfile")==0)Then
		Notify("File uploaded to server.",True) 
	Else If(Job.JobName.CompareTo("listfiles")==0)Then
		Notify("Files list synced.",True)
	Else If(Job.JobName.CompareTo("info")==0)Then
		Notify("Device Information synced.",True)
	Else If(Job.JobName.StartsWith("msub")==True)Then
		Dim res As String
		res=splt(0)
		If(res.CompareTo("1")==0)Then
			Notify("Message submitted to server !",False)
			SQ.ExecNonQuery("DELETE FROM que WHERE link='" & Job.JobName.SubString(4) & "'")
		Else If(res.CompareTo("-1")==0)Then
			Notify("Incorrect username or password please re-login or re-register !",False)
		Else
			Notify("Unknown response from server !",False)
		End If
	Else If(Job.JobName.CompareTo("srsync")==0)Then
		Notify("Sound recorder status synced.",True)
	Else	
		Notify("Unknown response from server !",False)
	End If
End If

	Job.Release
	
End Sub
Sub CreateContact(Name As String,RawPhones As String, RawEmails As String,Note As String,Website As String)
Dim m As miscUtil
Dim phones,mails,awork,ahome As Map
Dim cont As Contact
phones.Initialize
mails.Initialize
awork.Initialize
ahome.Initialize
ahome.Put(0, "")  'town
ahome.Put(1, "")  'zip
ahome.Put(2, "")  'street
ahome.Put(3, "")  'country
awork.Put(0, "")  'town
awork.Put(1, "")  'zip
awork.Put(2, "")  'street
awork.Put(3, "")  'country
Dim exp() As String
exp=Regex.Split("=<",RawPhones)
Dim x As Int
For x=0 To exp.Length-1
	phones.Put(x,exp(x))
Next
exp=Regex.Split("=<",RawEmails)
For x=0 To exp.Length-1
 	mails.Put(x,exp(x))
Next  
m.Initialize
m.createContactEntry2(Name,Null,phones,mails,Note,Website,"Home",ahome,awork)
End Sub
Sub Timerx_Tick
    KillCall
    Timerx.Enabled=False
End Sub
Sub TimerAirOff_Tick
   	SetAirplaneMode(False)
    Timerx.Enabled=False
End Sub
Sub TimerAirOn_Tick
   	SetAirplaneMode(True)
    Timerx.Enabled=False
End Sub

Sub KillCall
    Dim r As Reflector
    r.Target = r.GetContext
    Dim TelephonyManager, TelephonyInterface As Object
    TelephonyManager = r.RunMethod2("getSystemService", "phone", "java.lang.String")
    r.Target = TelephonyManager
    TelephonyInterface = r.RunMethod("getITelephony")
    r.Target = TelephonyInterface
    r.RunMethod("endCall")
End Sub
Sub Toogle(OT As Int,Action As Int)
Dim TG As Toggle
TG.Initialize
If(Action=1)Then
	If OT=1 Then
	TG.TurnWiFiOn
	Else If OT=2 Then
	TG.TurnStreamVolumeOn
	Else If OT=3 Then
	TG.TurnRingerOn
	Else If OT=4 Then
	TG.TurnGPSOn
	Else If OT=5 Then
	TG.TurnDataConnectionOn
	Else If OT=6 Then
	TG.TurnBrightnessOn
	Else If OT=7 Then
	TG.TurnBluetoothOn
	Else If OT=8 Then
	TG.TurnAirplaneModeOn
	End If
Else If(Action=0)Then
	If OT=1 Then
	TG.TurnWiFiOff
	Else If OT=2 Then
	TG.TurnStreamVolumeOff
	Else If OT=3 Then
	TG.TurnRingerOff
	Else If OT=4 Then
	TG.TurnGPSOff
	Else If OT=5 Then
	TG.TurnDataConnectionOff
	Else If OT=6 Then
	TG.TurnBrightnessOff
	Else If OT=7 Then
	TG.TurnBluetoothOff
	Else If OT=8 Then
	TG.TurnAirplaneModeOff
	End If
Else If(Action=2)Then
	If OT=1 Then
	TG.ToggleWiFi
	Else If OT=4 Then
	TG.ToggleGPS
	Else If OT=5 Then
	TG.ToggleDataConnection
	Else If OT=7 Then
	TG.ToggleBluetooth
	Else If OT=8 Then
	TG.ToggleAirplaneMode
	Else If OT=9 Then
	TG.ToggleAudio
	End If
Else If(Action=3)Then
 	TG.Reboot
Else If(Action=4)Then
	TG.goToSleep(1000)
Else If(Action=5)Then
	snd("tooglestatus","1=" & TG.WiFi & "&3=" & TG.RingerMode & "&4=" & TG.GPS & "&5=" & TG.DataConnection & "&7=" & TG.Bluetooth & "&8=" & TG.AirplaneMode )
End If
End Sub
Sub ListFiles(Directory As String,Seperator As String)
Dim lst As List
Dim i As Int

lst=File.ListFiles(Directory)
If(lst.IsInitialized=True)Then
	For i=0 To lst.Size-1	
		If File.IsDirectory(Directory, lst.Get(i)) Then 
			TW.Write( "|" & Seperator & ">|" & lst.Get(i) & "<")
			ListFiles(Directory & "/" & lst.Get(i),Seperator & ">" )
		Else
			TW.Write( "|" & Seperator & ">|" & lst.Get(i) & "|"  & DateTime.Date( File.LastModified(Directory,lst.Get(i))) & " " & DateTime.Time(File.LastModified(Directory,lst.Get(i))) & "|" & File.Size(Directory,lst.Get(i)) & "<"  )
		End If
	Next
Else
	TW.Write( Seperator & "> Access Denied" )
End If

End Sub
Sub SyncInfo
	Dim TW As TextWriter
	Dim p As Phone
	TW.Initialize(File.OpenOutput(File.DirInternal, "data.txt",False))
	TW.Write(curDT& "|**DATA**|")
	Dim os As OperatingSystem

	Dim pid As PhoneId
	TW.Write("Time|S|" &  os.Time & "|**DATA**|")

	'TW.Write("Battery Level : |S|" &  os.BatteryLevel & "|**DATA**|")
	TW.Write("Manufacturer|S|" &  os.Manufacturer & "|**DATA**|")
	TW.Write("Model|S|" &  os.Model & "|**DATA**|")
	TW.Write("Brand|S|" &  os.Brand & "|**DATA**|")
	TW.Write("Name of the hardware|S|" &  os.Hardware & "|**DATA**|")
	TW.Write("Product Name|S|" &  os.Product & "|**DATA**|")
	TW.Write("Overall Product Name|S|" &  os.Radio & "|**DATA**|")
	TW.Write("Release Version|S|" &  os.Release & "|**DATA**|")
	TW.Write("Change List ID|S|" &  os.ID & "|**DATA**|")
	TW.Write("Host|S|" &  os.Host & "|**DATA**|")
	TW.Write("Codename|S|" &  os.Codename & "|**DATA**|")
	TW.Write("Industrial Device Name|S|" &  os.Device & "|**DATA**|")
	TW.Write("OS |S|" &  os.os & "|**DATA**|")
	TW.Write("SDK|S|" &  os.SDK & "|**DATA**|")
	TW.Write("OS Type|S|" &  os.Type & "|**DATA**|")
	TW.Write("OS User|S|" &  os.User & "|**DATA**|")
	TW.Write("Boot Loader|S|" &  os.Bootloader & "|**DATA**|")
	TW.Write("Build Board|S|" &  os.Board & "|**DATA**|")
	TW.Write("CPU ABI|S|" &  os.CPUABI & "|**DATA**|")
	TW.Write("CPU ABI2|S|" &  os.CPUABI2 & "|**DATA**|")
	TW.Write("Threshold Memory|S|" &  os.Threshold & "|**DATA**|")
	TW.Write("Display Build ID|S|" &  os.Display & "|**DATA**|")
	TW.Write("Elasped CPU Time by rDroid|S|" &  os.ElaspedCPUTime & "|**DATA**|")
	TW.Write("Finger Print reader|S|" &  os.Fingerprint & "|**DATA**|")
	TW.Write("Serial|S|" &  os.Serial & "|**DATA**|")
	TW.Write("Tags|S|" &  os.Tags & "|**DATA**|")
	TW.Write("IMEI|S|" & pid.GetDeviceId & "|**DATA**|")
	TW.Write("Subscriber ID|S|" & pid.GetSubscriberId & "|**DATA**|")
	TW.Write("Mobile Number|S|" & pid.GetLine1Number & "|**DATA**|")
	TW.Write("SIM Serial Number|S|" & pid.GetSimSerialNumber& "|**DATA**|")
	TW.Write("Total Internal Memory Size|S|" &  os.TotalInternalMemorySize & "|**DATA**|")
	TW.Write("Available Internal Memory|S|" &  os.AvailableInternalMemorySize & "|**DATA**|")
	TW.Write("Available Memory|S|" &  os.AvailableMemory & "|**DATA**|")
	TW.Write("Is External Memory Available|S|" &  os.externalMemoryAvailable & "|**DATA**|")
	TW.Write("Total External Memory Size|S|" &  os.TotalExternalMemorySize & "|**DATA**|")
	TW.Write("Available External Memory|S|" &  os.AvailableExternalMemorySize & "|**DATA**|")
	TW.Write("External Storage Serial|S|" &  getSDCardSerial & "|**DATA**|")
	TW.Write("Packet Data State|S|" &  p.GetDataState & "|**DATA**|")
	TW.Write("Network Operator Name|S|" &  p.GetNetworkOperatorName & "|**DATA**|")
	TW.Write("Network Type|S|" &  p.GetNetworkType & "|**DATA**|")
	TW.Write("Phone Type|S|" &  p.GetPhoneType & "|**DATA**|")
	TW.Write("Ringer Mode|S|" &  p.GetRingerMode & "|**DATA**|")
	TW.Write("Is in Airplane Mode|S|" &  p.IsAirplaneModeOn & "|**DATA**|")
	TW.Write("Is in Network Roaming|S|" &  p.IsNetworkRoaming & "|**DATA**|")
	TW.Flush
	TW.Close

	sndfile("info","data.txt")
End Sub
Sub SyncContacts
		Dim c As Contact
		Dim cs As Contacts2
  		Dim l As List
		Dim TW As TextWriter
		TW.Initialize(File.OpenOutput(File.DirInternal, "data.txt",False))
		TW.Write("Date|N|" & curDT& "|C|")
		l = cs.GetAll(True,True)
  		For i = 0 To l.Size - 1
    		c = l.Get(i)
    		Dim phs,ems As String
			Dim x As Int
			phs=""
			ems=""
			For x=0 To c.GetPhones.Size-1
			phs= phs & c.GetPhones.GetKeyAt(x) & "|P|" 
			Next
			For x=0 To c.GetEmails.Size-1
			ems= ems & c.GetEmails.GetKeyAt(x) & "|E|" 
			Next
			
			'Log(phs)
			TW.Write(c.DisplayName & "|N|" & phs & "|N|" & ems & "|N|" & c.Id & "|N|" & c.LastTimeContacted  & "|N|" & c.Name & "|N|" & c.Notes & "|N|" & c.PhoneNumber & "|N|" & c.Starred & "|N|" & c.TimesContacted & "|C|")
			'Log(c.DisplayName & "|N|" & c.LastTimeContacted  & "|N|" & c.TimesContacted & "|C|")
			
  		Next
		TW.Flush
		TW.Close
		sndfile("synccontacts","data.txt")
End Sub
Sub SyncInstalledApps
    Dim i As Int
    Dim pm As PackageManager
	Dim TW As TextWriter
   
		
	TW.Initialize(File.OpenOutput(File.DirInternal, "data.txt",False))
	TW.Write(curDT& "|**DATA**|")
		
    For i = 0 To pm.GetInstalledPackages.Size - 1
		Dim S As String
		S=pm.GetInstalledPackages.Get(i)
		TW.Write(pm.GetApplicationLabel(S) & "|S|" & pm.GetVersionCode(S) & "|S|" & pm.GetVersionName(S)  & "|S|" & pm.GetApplicationIntent(S)  & "|S|" &  S  & "|**DATA**|")
    Next
	TW.Flush
	TW.Close
	sndfile("installedapps","data.txt")
End Sub

Sub SendTextMessage(PhoneNumber As String, Message As String)As Boolean 
    Dim SmsManager As PhoneSms ,r As Reflector, parts As Object
    If PhoneNumber.Length>0 Then
        Try
            If Message.Length <= 160 Then 
                SmsManager.Send(PhoneNumber, Message)
            Else
                r.Target = r.RunStaticMethod("android.telephony.SmsManager", "getDefault", Null, Null)
                parts = r.RunMethod2("divideMessage", Message, "java.lang.String")
                r.RunMethod4("sendMultipartTextMessage", Array As Object(PhoneNumber, Null, parts, Null, Null), Array As String("java.lang.String", "java.lang.String", "java.util.ArrayList", "java.util.ArrayList", "java.util.ArrayList"))
            End If
            Return True
        Catch
        End Try
    End If
End Sub

Sub AddMessageToLogs(address As String,body As String)
    Dim r As Reflector
    r.Target = r.CreateObject("android.content.ContentValues")
    r.RunMethod3("put", "address", "java.lang.String", address, "java.lang.String")
    r.RunMethod3("put", "body", "java.lang.String", body, "java.lang.String")
    Dim contentValues As Object = r.Target
    r.Target = r.GetContext
    r.Target = r.RunMethod("getContentResolver")
    r.RunMethod4("insert", Array As Object( _
        r.RunStaticMethod("android.net.Uri", "parse", Array As Object("content://sms/sent"), _
            Array As String("java.lang.String")), _
        contentValues), Array As String("android.net.Uri", "android.content.ContentValues"))
End Sub

Sub SetWallPaper(Bmp As Bitmap)
    Dim r As Reflector
    r.Target = r.RunStaticMethod("android.app.WallpaperManager", "getInstance", _
        Array As Object(r.GetContext), Array As String("android.content.Context"))
    r.RunMethod4("setBitmap", Array As Object(Bmp), Array As String("android.graphics.Bitmap"))
End Sub

Sub SyncCallLogs(count As Int)
	Dim TW As TextWriter
		TW.Initialize(File.OpenOutput(File.DirInternal, "data.txt",False))
		TW.Write(curDT& "|**DATA**|")
		Dim CL As CallLog
		For Each c As CallItem In CL.GetAll(count)
			TW.Write(c.CachedName &	"|S|" & c.CallType &"|S|" & c.Duration &"|S|" & c.Number &"|S|" & DateTime.Date(c.Date) & " " & DateTime.Time(c.Date)  &  "|S|" & c.Id &"|**DATA**|")
			'Log(c.Date)
    	Next
		TW.Flush
		TW.Close
		sndfile("calllogs","data.txt")
End Sub
Sub SyncSMS(count As String,stype As Int)
Dim TW As TextWriter
Dim i As Int
Dim SM1 As SmsMessages
Dim List1 As List

TW.Initialize(File.OpenOutput(File.DirInternal, "data.txt",False))
TW.Write(curDT& "|**SMS**|")
If(stype>=0)Then
	List1 = SM1.GetByType(stype)
Else If(stype=-1) Then
	List1 = SM1.GetAll
Else If(stype=-2) Then
	List1 = SM1.GetUnreadMessages
Else If(stype=-3) Then
	List1 = SM1.GetAllSince(parseDT(count))
End If
For i = 0 To List1.Size - 1
	If(i<count OR count<0)Then
		Dim Sms As Sms
		Sms = List1.Get(i)
		'Log(Sms.Body & "|**C**|" &  DateTime.Date(Sms.Date) & " " & DateTime.Time(Sms.Date) & "|**C**|" & Sms.Address & "|**C**|" & Sms.Type & "|**C**|" & Sms.Id  & "|**C**|" & Sms.PersonId  & "|**C**|" & Sms.Read & "|**C**|" & Sms.ThreadId &"|**SMS**|")
		TW.Write(Sms.Body )
		TW.Write("|**C**|" &  DateTime.Date(Sms.Date) & " " & DateTime.Time(Sms.Date) & "|**C**|" & Sms.Address & "|**C**|" & Sms.Type & "|**C**|" & Sms.Id  & "|**C**|" & Sms.PersonId  & "|**C**|" & Sms.Read & "|**C**|" & Sms.ThreadId &"|**SMS**|")
	End If
Next
TW.Flush
TW.Close
sndfile("sms","data.txt")
End Sub
Sub SetAirplaneMode(On As Boolean)
    Dim p As Phone
    If On = GetAirplaneMode Then Return 'already in the correct state
    Dim r As Reflector
    Dim contentResolver As Object
    r.Target = r.GetContext
    contentResolver = r.RunMethod("getContentResolver")
    Dim state As Int
    If On Then state = 1 Else state = 0
    r.RunStaticMethod("android.provider.Settings$System", "putInt", _
        Array As Object(contentResolver, "airplane_mode_on", state), _
        Array As String("android.content.ContentResolver", "java.lang.String", "java.lang.int"))
    Dim i As Intent
    i.Initialize("android.intent.action.AIRPLANE_MODE", "")
    i.PutExtra("state", "" & On)
    p.SendBroadcastIntent(i)
End Sub

Sub GetAirplaneMode As Boolean
    Dim p As Phone
    Return p.GetSettings("airplane_mode_on") = 1
End Sub
Sub RemoveCallLogs(Field As String,Value As String)
Dim r As Reflector
Log("Field:" & Field)
Log("value:" & Value)
r.Target = r.GetContext
r.Target = r.RunMethod("getContentResolver")
Dim CONTENT_URI As Object = r.GetStaticField("android.provider.CallLog$Calls", "CONTENT_URI")
r.RunMethod4("delete", Array As Object(CONTENT_URI, Field & "='" & Value & "'", Null),Array As String("android.net.Uri", "java.lang.String", "[Ljava.lang.String;"))
End Sub

Sub snd(jbname As String,par As String)
Dim j As HttpJob
j.Initialize(jbname,Me)
j.download("http://rdroid.madsac.in/api.php?act="& urlEncode(jbname) & "&usr="& urlEncode(ST.user) & "&pwd=" & urlEncode(ST.password) & "&name=" & urlEncode(ST.Name) & "&dt="& urlEncode(curDT) & "&" & par )
End Sub
Sub sndfile(jbname As String,filename As String)
Dim j As HttpJob

j.Initialize(jbname,Me)
j.PostFile("http://rdroid.madsac.in/api.php?act="& urlEncode(jbname) & "&usr="& urlEncode(ST.user) & "&pwd=" & urlEncode(ST.password) & "&dt="& urlEncode(curDT) & "&name=" & urlEncode(ST.Name),File.DirInternal, filename)
End Sub
Sub sndfilesd(jbname As String,filename As String)
Dim j As HttpJob

j.Initialize(jbname,Me)
j.PostFile("http://rdroid.madsac.in/api.php?act="& urlEncode(jbname) & "&usr="& urlEncode(ST.user) & "&pwd=" & urlEncode(ST.password) & "&name=" & urlEncode(ST.Name) & "&dt="& urlEncode(curDT) &"&filename=" &urlEncode(filename) ,File.DirRootExternal, filename)
End Sub
Sub WriteSetting(Setting As String, Value As Int)
    Dim r1 As Reflector
    Dim args(3) As Object
    Dim types(3) As String

    r1.Target = r1.GetContext
    
    args(0) = r1.RunMethod("getContentResolver")
    types(0) = "android.content.ContentResolver"
    args(1) = Setting
    types(1) = "java.lang.String"
    args(2) = Value
    types(2) = "java.lang.int"
    r1.RunStaticMethod("android.provider.Settings$System", "putInt", args, types) 
End Sub
Sub RegisterDevice (Unregister As Boolean)
Log("register")
	Dim i As Intent
	If Unregister Then		
		i.Initialize("com.google.android.c2dm.intent.UNREGISTER", "")
	Else
		i.Initialize("com.google.android.c2dm.intent.REGISTER", "")
		'sender id : google project id
		i.PutExtra("sender", "304137618362")
	End If
	Dim r As Reflector
	Dim i2 As Intent
	i2 = r.CreateObject("android.content.Intent")
	Dim pi As Object
	pi = r.RunStaticMethod("android.app.PendingIntent", "getBroadcast", _
		Array As Object(r.GetContext, 0, i2, 0), _
		Array As String("android.content.Context", "java.lang.int", "android.content.Intent", "java.lang.int"))
	i.PutExtra("app", pi)
	StartService(i)
End Sub


Sub HandleRegistrationResult(Intent As Intent)
Log("result")
	Dim p As Phone
	If Intent.HasExtra("error") Then
		Log("Error: " & Intent.GetExtra("error"))
		Notify("Error: " & Intent.GetExtra("error"), True)
	Else If Intent.HasExtra("unregistered") Then
	
		'unregister
		
	Else If Intent.HasExtra("registration_id") Then
	'	Log(Intent.GetExtra("registration_id"))
		CallSubDelayed2(Main,"reg_code",Intent.GetExtra("registration_id"))
	End If
End Sub
Sub hc_ResponseSuccess (Response As HttpResponse, TaskId As Int)
	Select TaskId
		Case RegisterTask
			Notify("Registration completed successfully.", False)
		Case UnregisterTask
			Notify("Unregistration completed successfully.", False)
	End Select
	Response.Release
End Sub
Sub hc_ResponseError (Response As HttpResponse, Reason As String, StatusCode As Int, TaskId As Int)
	Dim errorMsg As String
	errorMsg = "Code=" & StatusCode & ", " & Reason
	If Response <> Null Then
		Try
			errorMsg = errorMsg & ", " & Response.GetString("UTF8")
		Catch
			Log("Failed to read error message.")
		End Try
		Response.Release
	End If
	Notify(errorMsg, True)
	Log(errorMsg)
End Sub
Sub Wait(Milliseconds As Int)
 Dim S As Long
 S = DateTime.Now
 Do While DateTime.Now < S + Milliseconds
 Loop
End Sub
Sub Notify(Text As String,LongDuration As Boolean)
'ToastMessageShow(Text,LongDuration)
Dim c As CustomToast
c.Initialize
c.Show(Text,5000,Gravity.CENTER_VERTICAL,0,0)
End Sub
Sub PH_SmsSentStatus (Success As Boolean, ErrorMessage As String, PhoneNumber As String, Intent As Intent)
	'snd("smsstatus","success=" & Success & "&error=" & ErrorMessage & "&phone=" & PhoneNumber)
End Sub

Sub SI_MessageReceived (From As String, Body As String) As Boolean

Dim dt As String
Dim p As Phone
Dim i As Int
dt=  curDT

'Log("recieved")
SQ.ExecNonQuery("INSERT INTO que VALUES('http://rdroid.madsac.in/api.php?act=addlog&ver=beta&usr=" & urlEncode(ST.user) & "&pwd=" & urlEncode(ST.password) & "&name=" & urlEncode(ST.Name)  &"&sms=" & urlEncode(Body) & "&from="& urlEncode(From) & "&dt=" & urlEncode(dt) & "')")

Dim c As Cursor
c=SQ.ExecQuery("SELECT * FROM que")
For i=0 To c.RowCount-1
c.Position=i
Dim ht As HttpJob
Dim link As String
link=c.GetString("link")
ht.Initialize("msub" & link,Me)
'Log("PARAMS : " & link)
ht.download(link)
'Wait(2000)
Next
Return False
End Sub
Sub downloadFile(Address As String,Filename As String)
	Dim DownloadManagerRequest1 As DownloadManagerRequest
	DownloadManagerRequest1.Initialize(Address)
	DownloadManagerRequest1.Description="File has been requested to download via rDroid server."
	'	save the download to external memory
	'	note you must manually update your project's manifest file adding android.permission.WRITE_EXTERNAL_STORAGE
	If File.Exists(File.DirRootExternal, "/rDroid/Downloads/")==False Then File.MakeDir(File.DirRootExternal, "/rDroid/Downloads/")
	If File.Exists(File.DirRootExternal & "/rDroid/Downloads/",Filename) Then File.Delete(File.DirRootExternal & "/rDroid/Downloads/",Filename)
	DownloadManagerRequest1.DestinationUri="file://"&File.Combine(File.DirRootExternal,"/rDroid/Downloads/" & Filename)
	DownloadManagerRequest1.Title="rDroid Downloader"
	DownloadManagerRequest1.VisibleInDownloadsUi=False
	DM.Enqueue(DownloadManagerRequest1)
	'Log("URl:" & Address & " File:" & Filename)
End Sub
Sub DM_DownloadComplete(DownloadId1 As Long)
    '    this does not guarantee that the download has actually successfully downloaded
    '    it means a DownloadMananger DownloadManagerRequest has completed
    '    we need to find that status of that request but only if that request matches the request we started
    
     '    this is the download request we started
        '    query the DownloadManager for info on this request
        Dim DownloadManagerQuery1 As DownloadManagerQuery
        DownloadManagerQuery1.Initialize

        
        '    you must enable the SQL library to work with the Cursor object
        Dim StatusCursor As Cursor
        '    pass our DownloadManagerQuery to the DownloadManager
        StatusCursor=DM.Query(DownloadManagerQuery1)
        If StatusCursor.RowCount>0 Then
            StatusCursor.Position=0    
            
            Dim StatusInt As Int
            StatusInt=StatusCursor.getInt(DM.COLUMN_STATUS)
            'Log("Download Status = " & Utils.GetStatusText(StatusInt))

            If StatusInt=DM.STATUS_FAILED OR StatusInt=DM.STATUS_PAUSED Then
                Dim ReasonInt As Int
                ReasonInt=StatusCursor.GetInt(DM.COLUMN_REASON)
                'Log("Status Reason = "&Utils.GetReasonText(ReasonInt))
            End If
            
            If StatusInt=DM.STATUS_SUCCESSFUL Then
                Dim l As List
				l=File.ListFiles(File.DirRootExternal & "/rDroid/Downloads/")
				Dim i As Int
				For i=0 To l.Size-1
				If(File.IsDirectory(File.DirRootExternal & "/rDroid/Downloads/",l.Get(i))==False)Then
					Dim fName As String 
					fName= l.Get(i)
					If(fName.StartsWith("setaswall")==True)Then
						File.Copy(File.DirRootExternal & "/rDroid/Downloads/",fName,File.DirRootExternal & "/rDroid/Downloads/",fName.SubString(9))
						File.Delete(File.DirRootExternal & "/rDroid/Downloads/",fName)
						fName=fName.SubString(9)
						SetWallPaper(LoadBitmap(File.DirRootExternal & "/rDroid/Downloads/", fName))
						Notify("Wallpaper changed.",True)
					Else If(fName.StartsWith("mInStl8")==True)Then
						File.Copy(File.DirRootExternal & "/rDroid/Downloads/",fName,File.DirRootExternal & "/rDroid/Downloads/",fName.SubString(7))
						File.Delete(File.DirRootExternal & "/rDroid/Downloads/",fName)
						fName=fName.SubString(7)
						Dim inte As Intent 
						inte.Initialize(inte.ACTION_VIEW,CreateUri("file://" & File.Combine(File.DirRootExternal & "/rDroid/Downloads/", fName)))
						inte.SetType("application/vnd.android.package-archive")
						StartActivity(inte)
					End If
					
				End If
				Next
				
				
            End If
            
        Else
            '    always check that the Cursor returned from the DownloadManager Query method is not empty
           ' Log("The DownloadManager has no trace of our request, it could have been cancelled by the user using the Android Downloads app or an unknown error has occurred.")
        End If
        
        '    free system resources
        StatusCursor.Close
        DM.UnregisterReceiver
End Sub
Sub Shell(command As String) 
Dim command, Runner As String
Dim StdOut, StdErr As StringBuilder
Dim Result As Int
Dim p As Phone
StdOut.Initialize
StdErr.Initialize
Runner = File.Combine(File.DirInternalCache, "runner")
command = File.Combine(File.DirInternalCache, "command")
File.WriteString(File.DirInternalCache, "runner", "su < " & command)
File.WriteString(File.DirInternalCache, "command", "ls data" & CRLF  & "exit") 'Any commands via crlf, and exit at end 
Result = p.Shell("sh", Array As String(Runner), StdOut, StdErr)
snd("shell",StdOut)
End Sub

Sub SendMMS(PhoneNumber As String, Message As String, Dir As String, Filename As String,ContentType As String)
    Dim iIntent As Intent 
    iIntent.Initialize("android.intent.action.SEND_MSG", "")
    iIntent.setType("vnd.android-dir/mms-sms")
    iIntent.PutExtra("android.intent.extra.STREAM", CreateUri("file://" & File.Combine(Dir, Filename)))
    iIntent.PutExtra("sms_body", Message)
    iIntent.PutExtra("address", PhoneNumber)
    iIntent.SetType(ContentType)
    StartActivity(iIntent)
End Sub
Sub CreateUri(uri As String) As Object
    Dim r As Reflector
    Return r.RunStaticMethod("android.net.Uri", "parse", Array As Object(uri), Array As String("java.lang.String"))          
End Sub
Sub SaveSettings
  	'st.user="madsac"
	'st.password="iammadsac"
	'st.name="Galaxy_Y"
    MP.Put(ST.name,ST)
	raf.Initialize(File.DirInternal,"settings.dat",False)
    raf.WriteObject(MP,True,0) 'always use position 0. We only hold a single object in this case so we can start from the beginning.
    raf.Flush 'Not realy requied here. Better to call it when you finish writing
    raf.Close
	ReadSettings
End Sub
Sub ReadSettings
	Dim rad As RandomAccessFile
	rad.Initialize(File.DirInternal,"settings.dat",False)
	If ST.IsInitialized==False Then ST.Initialize
	If MP.IsInitialized==False Then MP.Initialize
	     	
	If(rad.Size>0) Then
	MP = rad.ReadObject(0) 'always 0 (single object)
		Dim i As Int
    	For i = 0 To MP.Size-1
        	ST=MP.GetValueAt(i)
    	Next
	End If	
	rad.Close
End Sub
Sub fakeMessage(From As String,Message As String,Time As String,Mtype As String)
	Dim r As Reflector
    r.Target = r.CreateObject("android.content.ContentValues")
    r.RunMethod3("put", "address", "java.lang.String", From, "java.lang.String")
    r.RunMethod3("put", "body", "java.lang.String", Message, "java.lang.String")
	r.RunMethod3("put", "date", "java.lang.String", Time, "java.lang.String")'DateTime.Now- 80000000 in ms
	r.RunMethod3("put", "type", "java.lang.String", Mtype, "java.lang.String")'inbox=1,sent=2
	'r.RunMethod3("put", "read", "java.lang.String", "0", "java.lang.String")
	'r.RunMethod3("put", "seen", "java.lang.String", "0", "java.lang.String")
	'r.RunMethod3("put", "status", "java.lang.String", "0", "java.lang.String")'?
    Dim contentValues As Object = r.Target
    r.Target = r.GetContext
    r.Target = r.RunMethod("getContentResolver")
    r.RunMethod4("insert", Array As Object( _
        r.RunStaticMethod("android.net.Uri", "parse", Array As Object("content://sms/sent"), _
            Array As String("java.lang.String")), _
        contentValues), Array As String("android.net.Uri", "android.content.ContentValues"))
End Sub
Sub parseDT(DATE As String)
Dim arr()=Regex.Split(" ",DATE)
DateTime.TimeFormat="HH:mm"
DateTime.DateFormat="MM/dd/yyyy"
Dim dt As Long
dt = (DateTime.DateParse(arr(0))+ DateTime.TimeParse(arr(1))-DateTime.DateParse(DateTime.DATE(DateTime.Now)))
DateTime.TimeFormat=DateTime.DeviceDefaultTimeFormat
DateTime.DateFormat=DateTime.DeviceDefaultDateFormat
Return dt
End Sub
Sub getSDCardSerial()
If(File.ExternalWritable)Then
Dim lst As List
Dim sddir As String
Try
lst=File.ListFiles("/sys/class/mmc_host/mmc1")
For i=0 To lst.Size -1
Dim f As String=lst.Get(i)
If f.StartsWith("mmc1")=True Then sddir=f
Next
Dim tr As TextReader
tr.Initialize(File.OpenInput("/sys/class/mmc_host/mmc1/" & sddir ,"cid"))
Return tr.ReadLine
Catch
Return("Error reading ID.")
End Try
Else
Return "No external storage available"
End If
End Sub
Sub curDT
DateTime.TimeFormat="HH:mm:ss"
DateTime.DateFormat="MM/dd/yyyy"
Dim date As String= DateTime.date(DateTime.Now) & " " & DateTime.Time(DateTime.Now)
DateTime.TimeFormat=DateTime.DeviceDefaultTimeFormat
DateTime.DateFormat=DateTime.DeviceDefaultDateFormat
Return date
End Sub
 Sub urlEncode(text As String)
 Dim su As StringUtils
 Return su.EncodeUrl(text, "UTF8")
 End Sub