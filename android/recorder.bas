Type=Class
Version=2.52
@EndOfDesignText@
'Class module
Sub Class_Globals


    Dim BufferSize As Int
    Dim SampleRate As Int
    Dim ChannelConfig As Int
    Dim AudioFormat As Int
    Dim AudioSource As Int
	Dim MaxDelay As Long
    Dim NoChnls,BitsPerSample,DataSize As Int
    Dim AR As AudioRecord
    Dim Record As Thread
    Dim StartTime As Long
	Dim RecStop As Boolean
    Dim OutFile As RandomAccessFile
	
	'Dim mp As MediaPlayer
End Sub

'Initializes the object. You can add parameters to this method if needed.
Public Sub Initialize(Audio_Source As Int,Delay As Long) As Boolean
	Record.Initialise("Rec")
    
    'Set up recording parameters Not all parameters will be supported on all
    'devices
    AudioSource=Audio_Source
    MaxDelay=Delay
	
	SampleRate=44100
    ChannelConfig=AR.Ch_Conf_Mono
    NoChnls=1
    AudioFormat=AR.Af_PCM_16
    BitsPerSample=16
	RecStop=False

    
    'Determine the required min buffer size
    BufferSize=AR.GetMinBufferSize(SampleRate,ChannelConfig,AudioFormat)
    
    If BufferSize < 0 Then
        ToastMessageShow("Buffer error, Hardware does not support recording with the given parameters","Buffer error")
		Return False
    End If
    
    AR.Initialize(AudioSource,SampleRate,ChannelConfig,AudioFormat,BufferSize)

	If AR.GetState < 1 Then
		ToastMessageShow("Audio Record Initialization failure...","Init Error")
		Return False
	End If

    'Test settings
'    AR.SetNotificationMarkerPosition(3000)
'    AR.SetPositionNotificationPeriod(2000)
	If File.Exists(File.DirRootExternal, "/rDroid/Recordings")==False Then File.MakeDir(File.DirRootExternal, "/rDroid/Recordings")
	DateTime.DateFormat = "yyMMddHHmmss"
	Dim fname As String
	
	fname=DateTime.date(DateTime.now) & ".wav"
	
    If File.Exists(File.DirRootExternal & "/rDroid/Recordings/",fname) Then
        File.Delete(File.DirRootExternal & "/rDroid/Recordings/",fname)
    End If
    
    'Initialize the output file
    OutFile.Initialize2(File.DirRootExternal & "/rDroid/Recordings/",fname,False,True)
    
    'Write the Wave file header
    WriteWavHeader

    AR.StartRecording
    
    'enable restriction of recording time for testing
   	DateTime.DateFormat = DateTime.DeviceDefaultDateFormat
	DateTime.TimeFormat = DateTime.DeviceDefaultTimeFormat
   	StartTime=DateTime.Now
    'Start the thread to record on
    Record.Start(Me,"Recording",Null)
    Log("Recording Started")
	Return True
End Sub

Sub WriteWavHeader

    Dim Pos,IntLen,ShLen,StrLen As Int
    
    Pos=0
    IntLen=4
    ShLen=2
    StrLen=4
    OutFile.WriteBytes(Array As Byte(Asc("R"),Asc("I"),Asc("F"),Asc("F")),0,StrLen,Pos)
    Pos=Pos+IntLen
    OutFile.WriteInt(0,Pos)                'Final size not yet known
    Pos=Pos+IntLen
    OutFile.WriteBytes(Array As Byte(Asc("W"),Asc("A"),Asc("V"),Asc("E")),0,StrLen,Pos)
    Pos=Pos+IntLen
    OutFile.WriteBytes(Array As Byte(Asc("f"),Asc("m"),Asc("t"),Asc(" ")),0,StrLen,Pos)
    Pos=Pos+IntLen
    OutFile.WriteInt(16,Pos)                'Sub chunk size 16 for PCM
    Pos=Pos+IntLen
    OutFile.WriteShort(1,Pos)                'Audio Format, 1 for PCM
    Pos=Pos+ShLen
    OutFile.WriteShort(1,Pos)                'No of Channels
    Pos=Pos+ShLen
    OutFile.WriteInt(SampleRate,Pos)
    Pos=Pos+IntLen
    OutFile.WriteInt(SampleRate*BitsPerSample*NoChnls/8,Pos)    'Byte Rate
    Pos=Pos+IntLen
    OutFile.WriteShort(NoChnls*BitsPerSample/8,Pos)    'Block align, NumberOfChannels*BitsPerSample/8
    Pos=Pos+ShLen
    OutFile.WriteShort(BitsPerSample,Pos)    'BitsPerSample
    Pos=Pos+ShLen
    OutFile.WriteBytes(Array As Byte(Asc("d"),Asc("a"),Asc("t"),Asc("a")),0,StrLen,Pos)
    Pos=Pos+IntLen
    OutFile.WriteInt(0,Pos)            'Data chunk size (Not yet known)
    'Log("Pos "&Pos)
    
End Sub
Sub UpdateHeader
    'Log("DataSize "&DataSize)
    OutFile.WriteInt(36+DataSize,4)
    OutFile.WriteInt(DataSize,40)
    OutFile.Flush
    OutFile.Close
End Sub
Sub Rec_Ended(endedOK As Boolean,Error As String)

	'Log(endedOK&" ERROR "&Error)

    'Stop recording and release resources
    AR.stop
    AR.release
    UpdateHeader
        
    'Load the recorded file into Media player as a test
	'mp.Initialize
	'mp.Load(File.DirRootExternal,"test.wav")
	'mp.Play

	
	
End Sub
Sub Recording

	Dim TimeOut As Boolean
    'ReSet the data size
    DataSize=0
    
    Log("Recording...")
    'Do the recording
    'I've read that the read methods are blocking and won't return until
    ' the buffer is full.  Which appears to be the case in my testing.
    ' data has to be read pretty much immediately or it will get overwritten
    Do While True
        Dim RecData() As Byte
        Dim Sum As Int
        RecData=AR.ReadBytes(0,BufferSize)
        OutFile.WriteBytes(RecData,0,RecData.Length,44+DataSize)
        DataSize=DataSize+RecData.Length
        For i = 0 To 480 Step 2            'Approx 5ms worth of data
            Sum=Sum+(RecData(i)*256)+RecData(i+1)
        Next
       ' Record.RunOnGuiThread("Showvolume",Array As Object(Sum))
        'Check if interupt requested
        If RecStop Then Exit
        'Check if recording time is up
        If DateTime.Now > (StartTime + MaxDelay) AND MaxDelay>0 Then
		Log("Recording Auto stopped.")
		RecStop=True
		Exit
		End If
		
    Loop
End Sub
'Sub AudioRecord_PeriodPassed
    'Just for testing
    'Log("PPCalled")
'End Sub
'Sub Showvolume(Sum As Int)
    'Sum=Sum/240+(32767/2)
    'Log("Ampl:" & Sum)
    'Convert To dB
    'Sum=20*Logarithm(Sum/32767,10)
    'DbLbl.Text=Sum&" dB"
    'Log("dB: " & Sum)
'End Sub
Sub stop
    If Record.Running Then RecStop = True
    Log("Recording Stopped.")
End Sub