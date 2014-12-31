package madsacsoft.maddev.rdroid;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class recorder extends B4AClass.ImplB4AClass{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "madsacsoft.maddev.rdroid.recorder");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
        }
        _class_globals();
    }


 public anywheresoftware.b4a.keywords.Common __c = null;
public int _vvvvvv0 = 0;
public int _vvvvvvv1 = 0;
public int _vvvvvvv2 = 0;
public int _vvvvvvv3 = 0;
public int _vvvvvvv4 = 0;
public long _vvvvvvv5 = 0L;
public int _vvvvvvv6 = 0;
public int _vvvvvvv7 = 0;
public int _vvvvvvv0 = 0;
public stevel05.audiorecord.AudioRecording _vvvvvvvv1 = null;
public anywheresoftware.b4a.agraham.threading.Threading _vvvvvvvv2 = null;
public long _vvvvvvvv3 = 0L;
public boolean _vvvvvvvv4 = false;
public anywheresoftware.b4a.randomaccessfile.RandomAccessFile _vvvvvvvv5 = null;
public madsacsoft.maddev.rdroid.main _vvvvv0 = null;
public madsacsoft.maddev.rdroid.submitter _vvvvvv1 = null;
public madsacsoft.maddev.rdroid.client _vvvvvv2 = null;
public madsacsoft.maddev.rdroid.utils _vvvvvv3 = null;
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 2;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 5;BA.debugLine="Dim BufferSize As Int";
_vvvvvv0 = 0;
 //BA.debugLineNum = 6;BA.debugLine="Dim SampleRate As Int";
_vvvvvvv1 = 0;
 //BA.debugLineNum = 7;BA.debugLine="Dim ChannelConfig As Int";
_vvvvvvv2 = 0;
 //BA.debugLineNum = 8;BA.debugLine="Dim AudioFormat As Int";
_vvvvvvv3 = 0;
 //BA.debugLineNum = 9;BA.debugLine="Dim AudioSource As Int";
_vvvvvvv4 = 0;
 //BA.debugLineNum = 10;BA.debugLine="Dim MaxDelay As Long";
_vvvvvvv5 = 0L;
 //BA.debugLineNum = 11;BA.debugLine="Dim NoChnls,BitsPerSample,DataSize As Int";
_vvvvvvv6 = 0;
_vvvvvvv7 = 0;
_vvvvvvv0 = 0;
 //BA.debugLineNum = 12;BA.debugLine="Dim AR As AudioRecord";
_vvvvvvvv1 = new stevel05.audiorecord.AudioRecording();
 //BA.debugLineNum = 13;BA.debugLine="Dim Record As Thread";
_vvvvvvvv2 = new anywheresoftware.b4a.agraham.threading.Threading();
 //BA.debugLineNum = 14;BA.debugLine="Dim StartTime As Long";
_vvvvvvvv3 = 0L;
 //BA.debugLineNum = 15;BA.debugLine="Dim RecStop As Boolean";
_vvvvvvvv4 = false;
 //BA.debugLineNum = 16;BA.debugLine="Dim OutFile As RandomAccessFile";
_vvvvvvvv5 = new anywheresoftware.b4a.randomaccessfile.RandomAccessFile();
 //BA.debugLineNum = 19;BA.debugLine="End Sub";
return "";
}
public boolean  _vvv1(anywheresoftware.b4a.BA _ba,int _audio_source,long _delay) throws Exception{
innerInitialize(_ba);
String _fname = "";
 //BA.debugLineNum = 22;BA.debugLine="Public Sub Initialize(Audio_Source As Int,Delay As Long) As Boolean";
 //BA.debugLineNum = 23;BA.debugLine="Record.Initialise(\"Rec\")";
_vvvvvvvv2.Initialise(ba,"Rec");
 //BA.debugLineNum = 27;BA.debugLine="AudioSource=Audio_Source";
_vvvvvvv4 = _audio_source;
 //BA.debugLineNum = 28;BA.debugLine="MaxDelay=Delay";
_vvvvvvv5 = _delay;
 //BA.debugLineNum = 30;BA.debugLine="SampleRate=44100";
_vvvvvvv1 = (int)(44100);
 //BA.debugLineNum = 31;BA.debugLine="ChannelConfig=AR.Ch_Conf_Mono";
_vvvvvvv2 = _vvvvvvvv1.CH_CONF_MONO;
 //BA.debugLineNum = 32;BA.debugLine="NoChnls=1";
_vvvvvvv6 = (int)(1);
 //BA.debugLineNum = 33;BA.debugLine="AudioFormat=AR.Af_PCM_16";
_vvvvvvv3 = _vvvvvvvv1.AF_PCM_16;
 //BA.debugLineNum = 34;BA.debugLine="BitsPerSample=16";
_vvvvvvv7 = (int)(16);
 //BA.debugLineNum = 35;BA.debugLine="RecStop=False";
_vvvvvvvv4 = __c.False;
 //BA.debugLineNum = 39;BA.debugLine="BufferSize=AR.GetMinBufferSize(SampleRate,ChannelConfig,AudioFormat)";
_vvvvvv0 = _vvvvvvvv1.GetMinBufferSize(_vvvvvvv1,_vvvvvvv2,_vvvvvvv3);
 //BA.debugLineNum = 41;BA.debugLine="If BufferSize < 0 Then";
if (_vvvvvv0<0) { 
 //BA.debugLineNum = 42;BA.debugLine="ToastMessageShow(\"Buffer error, Hardware does not support recording with the given parameters\",\"Buffer error\")";
__c.ToastMessageShow("Buffer error, Hardware does not support recording with the given parameters",BA.ObjectToBoolean("Buffer error"));
 //BA.debugLineNum = 43;BA.debugLine="Return False";
if (true) return __c.False;
 };
 //BA.debugLineNum = 46;BA.debugLine="AR.Initialize(AudioSource,SampleRate,ChannelConfig,AudioFormat,BufferSize)";
_vvvvvvvv1.Initialize(ba,_vvvvvvv4,_vvvvvvv1,_vvvvvvv2,_vvvvvvv3,_vvvvvv0);
 //BA.debugLineNum = 48;BA.debugLine="If AR.GetState < 1 Then";
if (_vvvvvvvv1.GetState()<1) { 
 //BA.debugLineNum = 49;BA.debugLine="ToastMessageShow(\"Audio Record Initialization failure...\",\"Init Error\")";
__c.ToastMessageShow("Audio Record Initialization failure...",BA.ObjectToBoolean("Init Error"));
 //BA.debugLineNum = 50;BA.debugLine="Return False";
if (true) return __c.False;
 };
 //BA.debugLineNum = 56;BA.debugLine="If File.Exists(File.DirRootExternal, \"/rDroid/Recordings\")==False Then File.MakeDir(File.DirRootExternal, \"/rDroid/Recordings\")";
if (__c.File.Exists(__c.File.getDirRootExternal(),"/rDroid/Recordings")==__c.False) { 
__c.File.MakeDir(__c.File.getDirRootExternal(),"/rDroid/Recordings");};
 //BA.debugLineNum = 57;BA.debugLine="DateTime.DateFormat = \"yyMMddHHmmss\"";
__c.DateTime.setDateFormat("yyMMddHHmmss");
 //BA.debugLineNum = 58;BA.debugLine="Dim fname As String";
_fname = "";
 //BA.debugLineNum = 60;BA.debugLine="fname=DateTime.date(DateTime.now) & \".wav\"";
_fname = __c.DateTime.Date(__c.DateTime.getNow())+".wav";
 //BA.debugLineNum = 62;BA.debugLine="If File.Exists(File.DirRootExternal & \"/rDroid/Recordings/\",fname) Then";
if (__c.File.Exists(__c.File.getDirRootExternal()+"/rDroid/Recordings/",_fname)) { 
 //BA.debugLineNum = 63;BA.debugLine="File.Delete(File.DirRootExternal & \"/rDroid/Recordings/\",fname)";
__c.File.Delete(__c.File.getDirRootExternal()+"/rDroid/Recordings/",_fname);
 };
 //BA.debugLineNum = 67;BA.debugLine="OutFile.Initialize2(File.DirRootExternal & \"/rDroid/Recordings/\",fname,False,True)";
_vvvvvvvv5.Initialize2(__c.File.getDirRootExternal()+"/rDroid/Recordings/",_fname,__c.False,__c.True);
 //BA.debugLineNum = 70;BA.debugLine="WriteWavHeader";
_vvvvvv7();
 //BA.debugLineNum = 72;BA.debugLine="AR.StartRecording";
_vvvvvvvv1.StartRecording();
 //BA.debugLineNum = 75;BA.debugLine="DateTime.DateFormat = DateTime.DeviceDefaultDateFormat";
__c.DateTime.setDateFormat(__c.DateTime.getDeviceDefaultDateFormat());
 //BA.debugLineNum = 76;BA.debugLine="DateTime.TimeFormat = DateTime.DeviceDefaultTimeFormat";
__c.DateTime.setTimeFormat(__c.DateTime.getDeviceDefaultTimeFormat());
 //BA.debugLineNum = 77;BA.debugLine="StartTime=DateTime.Now";
_vvvvvvvv3 = __c.DateTime.getNow();
 //BA.debugLineNum = 79;BA.debugLine="Record.Start(Me,\"Recording\",Null)";
_vvvvvvvv2.Start(this,"Recording",(Object[])(__c.Null));
 //BA.debugLineNum = 80;BA.debugLine="Log(\"Recording Started\")";
__c.Log("Recording Started");
 //BA.debugLineNum = 81;BA.debugLine="Return True";
if (true) return __c.True;
 //BA.debugLineNum = 82;BA.debugLine="End Sub";
return false;
}
public String  _rec_ended(boolean _endedok,String _error) throws Exception{
 //BA.debugLineNum = 127;BA.debugLine="Sub Rec_Ended(endedOK As Boolean,Error As String)";
 //BA.debugLineNum = 132;BA.debugLine="AR.stop";
_vvvvvvvv1.Stop();
 //BA.debugLineNum = 133;BA.debugLine="AR.release";
_vvvvvvvv1.release();
 //BA.debugLineNum = 134;BA.debugLine="UpdateHeader";
_vvvvvv6();
 //BA.debugLineNum = 143;BA.debugLine="End Sub";
return "";
}
public String  _vvvvvv4() throws Exception{
boolean _timeout = false;
byte[] _recdata = null;
int _sum = 0;
int _i = 0;
 //BA.debugLineNum = 144;BA.debugLine="Sub Recording";
 //BA.debugLineNum = 146;BA.debugLine="Dim TimeOut As Boolean";
_timeout = false;
 //BA.debugLineNum = 148;BA.debugLine="DataSize=0";
_vvvvvvv0 = (int)(0);
 //BA.debugLineNum = 150;BA.debugLine="Log(\"Recording...\")";
__c.Log("Recording...");
 //BA.debugLineNum = 155;BA.debugLine="Do While True";
while (__c.True) {
 //BA.debugLineNum = 156;BA.debugLine="Dim RecData() As Byte";
_recdata = new byte[(int)(0)];
;
 //BA.debugLineNum = 157;BA.debugLine="Dim Sum As Int";
_sum = 0;
 //BA.debugLineNum = 158;BA.debugLine="RecData=AR.ReadBytes(0,BufferSize)";
_recdata = _vvvvvvvv1.ReadBytes((int)(0),_vvvvvv0);
 //BA.debugLineNum = 159;BA.debugLine="OutFile.WriteBytes(RecData,0,RecData.Length,44+DataSize)";
_vvvvvvvv5.WriteBytes(_recdata,(int)(0),_recdata.length,(long)(44+_vvvvvvv0));
 //BA.debugLineNum = 160;BA.debugLine="DataSize=DataSize+RecData.Length";
_vvvvvvv0 = (int)(_vvvvvvv0+_recdata.length);
 //BA.debugLineNum = 161;BA.debugLine="For i = 0 To 480 Step 2            'Approx 5ms worth of data";
{
final double step104 = 2;
final double limit104 = (int)(480);
for (_i = (int)(0); (step104 > 0 && _i <= limit104) || (step104 < 0 && _i >= limit104); _i += step104) {
 //BA.debugLineNum = 162;BA.debugLine="Sum=Sum+(RecData(i)*256)+RecData(i+1)";
_sum = (int)(_sum+(_recdata[_i]*256)+_recdata[(int)(_i+1)]);
 }
};
 //BA.debugLineNum = 166;BA.debugLine="If RecStop Then Exit";
if (_vvvvvvvv4) { 
if (true) break;};
 //BA.debugLineNum = 168;BA.debugLine="If DateTime.Now > (StartTime + MaxDelay) AND MaxDelay>0 Then";
if (__c.DateTime.getNow()>(_vvvvvvvv3+_vvvvvvv5) && _vvvvvvv5>0) { 
 //BA.debugLineNum = 169;BA.debugLine="Log(\"Recording Auto stopped.\")";
__c.Log("Recording Auto stopped.");
 //BA.debugLineNum = 170;BA.debugLine="RecStop=True";
_vvvvvvvv4 = __c.True;
 //BA.debugLineNum = 171;BA.debugLine="Exit";
if (true) break;
 };
 }
;
 //BA.debugLineNum = 175;BA.debugLine="End Sub";
return "";
}
public String  _vvvvvv5() throws Exception{
 //BA.debugLineNum = 188;BA.debugLine="Sub stop";
 //BA.debugLineNum = 189;BA.debugLine="If Record.Running Then RecStop = True";
if (_vvvvvvvv2.getRunning()) { 
_vvvvvvvv4 = __c.True;};
 //BA.debugLineNum = 190;BA.debugLine="Log(\"Recording Stopped.\")";
__c.Log("Recording Stopped.");
 //BA.debugLineNum = 191;BA.debugLine="End Sub";
return "";
}
public String  _vvvvvv6() throws Exception{
 //BA.debugLineNum = 120;BA.debugLine="Sub UpdateHeader";
 //BA.debugLineNum = 122;BA.debugLine="OutFile.WriteInt(36+DataSize,4)";
_vvvvvvvv5.WriteInt((int)(36+_vvvvvvv0),(long)(4));
 //BA.debugLineNum = 123;BA.debugLine="OutFile.WriteInt(DataSize,40)";
_vvvvvvvv5.WriteInt(_vvvvvvv0,(long)(40));
 //BA.debugLineNum = 124;BA.debugLine="OutFile.Flush";
_vvvvvvvv5.Flush();
 //BA.debugLineNum = 125;BA.debugLine="OutFile.Close";
_vvvvvvvv5.Close();
 //BA.debugLineNum = 126;BA.debugLine="End Sub";
return "";
}
public String  _vvvvvv7() throws Exception{
int _pos = 0;
int _intlen = 0;
int _shlen = 0;
int _strlen = 0;
 //BA.debugLineNum = 84;BA.debugLine="Sub WriteWavHeader";
 //BA.debugLineNum = 86;BA.debugLine="Dim Pos,IntLen,ShLen,StrLen As Int";
_pos = 0;
_intlen = 0;
_shlen = 0;
_strlen = 0;
 //BA.debugLineNum = 88;BA.debugLine="Pos=0";
_pos = (int)(0);
 //BA.debugLineNum = 89;BA.debugLine="IntLen=4";
_intlen = (int)(4);
 //BA.debugLineNum = 90;BA.debugLine="ShLen=2";
_shlen = (int)(2);
 //BA.debugLineNum = 91;BA.debugLine="StrLen=4";
_strlen = (int)(4);
 //BA.debugLineNum = 92;BA.debugLine="OutFile.WriteBytes(Array As Byte(Asc(\"R\"),Asc(\"I\"),Asc(\"F\"),Asc(\"F\")),0,StrLen,Pos)";
_vvvvvvvv5.WriteBytes(new byte[]{(byte)(__c.Asc(BA.ObjectToChar("R"))),(byte)(__c.Asc(BA.ObjectToChar("I"))),(byte)(__c.Asc(BA.ObjectToChar("F"))),(byte)(__c.Asc(BA.ObjectToChar("F")))},(int)(0),_strlen,(long)(_pos));
 //BA.debugLineNum = 93;BA.debugLine="Pos=Pos+IntLen";
_pos = (int)(_pos+_intlen);
 //BA.debugLineNum = 94;BA.debugLine="OutFile.WriteInt(0,Pos)                'Final size not yet known";
_vvvvvvvv5.WriteInt((int)(0),(long)(_pos));
 //BA.debugLineNum = 95;BA.debugLine="Pos=Pos+IntLen";
_pos = (int)(_pos+_intlen);
 //BA.debugLineNum = 96;BA.debugLine="OutFile.WriteBytes(Array As Byte(Asc(\"W\"),Asc(\"A\"),Asc(\"V\"),Asc(\"E\")),0,StrLen,Pos)";
_vvvvvvvv5.WriteBytes(new byte[]{(byte)(__c.Asc(BA.ObjectToChar("W"))),(byte)(__c.Asc(BA.ObjectToChar("A"))),(byte)(__c.Asc(BA.ObjectToChar("V"))),(byte)(__c.Asc(BA.ObjectToChar("E")))},(int)(0),_strlen,(long)(_pos));
 //BA.debugLineNum = 97;BA.debugLine="Pos=Pos+IntLen";
_pos = (int)(_pos+_intlen);
 //BA.debugLineNum = 98;BA.debugLine="OutFile.WriteBytes(Array As Byte(Asc(\"f\"),Asc(\"m\"),Asc(\"t\"),Asc(\" \")),0,StrLen,Pos)";
_vvvvvvvv5.WriteBytes(new byte[]{(byte)(__c.Asc(BA.ObjectToChar("f"))),(byte)(__c.Asc(BA.ObjectToChar("m"))),(byte)(__c.Asc(BA.ObjectToChar("t"))),(byte)(__c.Asc(BA.ObjectToChar(" ")))},(int)(0),_strlen,(long)(_pos));
 //BA.debugLineNum = 99;BA.debugLine="Pos=Pos+IntLen";
_pos = (int)(_pos+_intlen);
 //BA.debugLineNum = 100;BA.debugLine="OutFile.WriteInt(16,Pos)                'Sub chunk size 16 for PCM";
_vvvvvvvv5.WriteInt((int)(16),(long)(_pos));
 //BA.debugLineNum = 101;BA.debugLine="Pos=Pos+IntLen";
_pos = (int)(_pos+_intlen);
 //BA.debugLineNum = 102;BA.debugLine="OutFile.WriteShort(1,Pos)                'Audio Format, 1 for PCM";
_vvvvvvvv5.WriteShort((short)(1),(long)(_pos));
 //BA.debugLineNum = 103;BA.debugLine="Pos=Pos+ShLen";
_pos = (int)(_pos+_shlen);
 //BA.debugLineNum = 104;BA.debugLine="OutFile.WriteShort(1,Pos)                'No of Channels";
_vvvvvvvv5.WriteShort((short)(1),(long)(_pos));
 //BA.debugLineNum = 105;BA.debugLine="Pos=Pos+ShLen";
_pos = (int)(_pos+_shlen);
 //BA.debugLineNum = 106;BA.debugLine="OutFile.WriteInt(SampleRate,Pos)";
_vvvvvvvv5.WriteInt(_vvvvvvv1,(long)(_pos));
 //BA.debugLineNum = 107;BA.debugLine="Pos=Pos+IntLen";
_pos = (int)(_pos+_intlen);
 //BA.debugLineNum = 108;BA.debugLine="OutFile.WriteInt(SampleRate*BitsPerSample*NoChnls/8,Pos)    'Byte Rate";
_vvvvvvvv5.WriteInt((int)(_vvvvvvv1*_vvvvvvv7*_vvvvvvv6/(double)8),(long)(_pos));
 //BA.debugLineNum = 109;BA.debugLine="Pos=Pos+IntLen";
_pos = (int)(_pos+_intlen);
 //BA.debugLineNum = 110;BA.debugLine="OutFile.WriteShort(NoChnls*BitsPerSample/8,Pos)    'Block align, NumberOfChannels*BitsPerSample/8";
_vvvvvvvv5.WriteShort((short)(_vvvvvvv6*_vvvvvvv7/(double)8),(long)(_pos));
 //BA.debugLineNum = 111;BA.debugLine="Pos=Pos+ShLen";
_pos = (int)(_pos+_shlen);
 //BA.debugLineNum = 112;BA.debugLine="OutFile.WriteShort(BitsPerSample,Pos)    'BitsPerSample";
_vvvvvvvv5.WriteShort((short)(_vvvvvvv7),(long)(_pos));
 //BA.debugLineNum = 113;BA.debugLine="Pos=Pos+ShLen";
_pos = (int)(_pos+_shlen);
 //BA.debugLineNum = 114;BA.debugLine="OutFile.WriteBytes(Array As Byte(Asc(\"d\"),Asc(\"a\"),Asc(\"t\"),Asc(\"a\")),0,StrLen,Pos)";
_vvvvvvvv5.WriteBytes(new byte[]{(byte)(__c.Asc(BA.ObjectToChar("d"))),(byte)(__c.Asc(BA.ObjectToChar("a"))),(byte)(__c.Asc(BA.ObjectToChar("t"))),(byte)(__c.Asc(BA.ObjectToChar("a")))},(int)(0),_strlen,(long)(_pos));
 //BA.debugLineNum = 115;BA.debugLine="Pos=Pos+IntLen";
_pos = (int)(_pos+_intlen);
 //BA.debugLineNum = 116;BA.debugLine="OutFile.WriteInt(0,Pos)            'Data chunk size (Not yet known)";
_vvvvvvvv5.WriteInt((int)(0),(long)(_pos));
 //BA.debugLineNum = 119;BA.debugLine="End Sub";
return "";
}
}
