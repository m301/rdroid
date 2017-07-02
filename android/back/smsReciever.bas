Type=Service
Version=2.30
StartAtBoot=True
@EndOfDesignText@
'Service module
Sub Process_Globals

'Dim sNotif As Notification
End Sub
Sub Service_Create


'
'
'sNotif.Initialize
'sNotif.Icon = "icon"
'sNotif.Sound = False
'sNotif.OnGoingEvent=True
End Sub
Sub PH_BatteryChanged (Level As Int, Scale As Int, Plugged As Boolean, Intent As Intent)
	
End Sub


Sub Service_Start
'sNotif.SetInfo("MAD SMS Reciever","Listening",Main)
'Service.StartForeground(1,sNotif)


End Sub


Sub JobDone (Job As HttpJob)
   ' Log("JobName = " & Job.JobName & ", Success = " & Job.Success )
    
	If(Job.Success==True  )Then
		
	
	Dim splt() As String
	splt=Regex.Split("==>", Job.GetString)
	splt=Regex.Split("<==",splt(1))
	Dim res As String
	res=splt(0)
	
	
	
	'If(params.Size==0 AND Job.Success=True)Then
	'StopService(submitter)
	'End If
	Job.Release

End Sub


