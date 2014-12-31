Type=StaticCode
Version=2.52
@EndOfDesignText@
'	Utils Code module
Sub Process_Globals
	Dim ReasonTextMap As Map
	Dim StatusTextMap As Map
	
	Dim IsInitialized As Boolean=False
End Sub

Sub Initialize(DownloadManager1 As DownloadManager)
	If IsInitialized=False Then
		ReasonTextMap.Initialize
		ReasonTextMap.Put(DownloadManager1.ERROR_CANNOT_RESUME, "ERROR_CANNOT_RESUME")
		ReasonTextMap.Put(DownloadManager1.ERROR_DEVICE_NOT_FOUND, "ERROR_DEVICE_NOT_FOUND")
		ReasonTextMap.Put(DownloadManager1.ERROR_FILE_ALREADY_EXISTS, "ERROR_FILE_ALREADY_EXISTS")
		ReasonTextMap.Put(DownloadManager1.ERROR_FILE_ERROR, "ERROR_FILE_ERROR")
		ReasonTextMap.Put(DownloadManager1.ERROR_HTTP_DATA_ERROR, "ERROR_HTTP_DATA_ERROR")
		ReasonTextMap.Put(DownloadManager1.ERROR_INSUFFICIENT_SPACE, "ERROR_INSUFFICIENT_SPACE")
		ReasonTextMap.Put(DownloadManager1.ERROR_TOO_MANY_REDIRECTS, "ERROR_TOO_MANY_REDIRECTS")
		ReasonTextMap.Put(DownloadManager1.ERROR_UNHANDLED_HTTP_CODE, "ERROR_UNHANDLED_HTTP_CODE")
		ReasonTextMap.Put(DownloadManager1.ERROR_UNKNOWN, "ERROR_UNKNOWN")
		ReasonTextMap.Put(DownloadManager1.PAUSED_QUEUED_FOR_WIFI, "PAUSED_QUEUED_FOR_WIFI")
		ReasonTextMap.Put(DownloadManager1.PAUSED_UNKNOWN, "PAUSED_UNKNOWN")
		ReasonTextMap.Put(DownloadManager1.PAUSED_WAITING_FOR_NETWORK, "PAUSED_WAITING_FOR_NETWORK")
		ReasonTextMap.Put(DownloadManager1.PAUSED_WAITING_TO_RETRY, "PAUSED_WAITING_TO_RETRY")
		
		StatusTextMap.Initialize
		StatusTextMap.Put(DownloadManager1.STATUS_FAILED, "STATUS_FAILED")
		StatusTextMap.Put(DownloadManager1.STATUS_PAUSED, "STATUS_PAUSED")
		StatusTextMap.Put(DownloadManager1.STATUS_PENDING, "STATUS_PENDING")
		StatusTextMap.Put(DownloadManager1.STATUS_RUNNING, "STATUS_RUNNING")
		StatusTextMap.Put(DownloadManager1.STATUS_SUCCESSFUL, "STATUS_SUCCESSFUL")
		
		IsInitialized=True
	End If
End Sub

Sub GetReasonText(Index As Int) As String
	Return ReasonTextMap.Get(Index)
End Sub

Sub GetStatusText(Index As Int) As String
	Return StatusTextMap.Get(Index)
End Sub
