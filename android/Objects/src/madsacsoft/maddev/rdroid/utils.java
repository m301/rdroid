package madsacsoft.maddev.rdroid;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class utils {
private static utils mostCurrent = new utils();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.objects.collections.Map _vv4 = null;
public static anywheresoftware.b4a.objects.collections.Map _vv5 = null;
public static boolean _vv6 = false;
public madsacsoft.maddev.rdroid.main _vvvvv0 = null;
public madsacsoft.maddev.rdroid.submitter _vvvvvv1 = null;
public madsacsoft.maddev.rdroid.client _vvvvvv2 = null;
public static String  _vv7(anywheresoftware.b4a.BA _ba,int _index) throws Exception{
 //BA.debugLineNum = 37;BA.debugLine="Sub GetReasonText(Index As Int) As String";
 //BA.debugLineNum = 38;BA.debugLine="Return ReasonTextMap.Get(Index)";
if (true) return String.valueOf(_vv4.Get((Object)(_index)));
 //BA.debugLineNum = 39;BA.debugLine="End Sub";
return "";
}
public static String  _vv0(anywheresoftware.b4a.BA _ba,int _index) throws Exception{
 //BA.debugLineNum = 41;BA.debugLine="Sub GetStatusText(Index As Int) As String";
 //BA.debugLineNum = 42;BA.debugLine="Return StatusTextMap.Get(Index)";
if (true) return String.valueOf(_vv5.Get((Object)(_index)));
 //BA.debugLineNum = 43;BA.debugLine="End Sub";
return "";
}
public static String  _vvv1(anywheresoftware.b4a.BA _ba,uk.co.martinpearman.b4a.downloadmanager.B4ADownloadManager _downloadmanager1) throws Exception{
 //BA.debugLineNum = 9;BA.debugLine="Sub Initialize(DownloadManager1 As DownloadManager)";
 //BA.debugLineNum = 10;BA.debugLine="If IsInitialized=False Then";
if (_vv6==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 11;BA.debugLine="ReasonTextMap.Initialize";
_vv4.Initialize();
 //BA.debugLineNum = 12;BA.debugLine="ReasonTextMap.Put(DownloadManager1.ERROR_CANNOT_RESUME, \"ERROR_CANNOT_RESUME\")";
_vv4.Put((Object)(_downloadmanager1.ERROR_CANNOT_RESUME),(Object)("ERROR_CANNOT_RESUME"));
 //BA.debugLineNum = 13;BA.debugLine="ReasonTextMap.Put(DownloadManager1.ERROR_DEVICE_NOT_FOUND, \"ERROR_DEVICE_NOT_FOUND\")";
_vv4.Put((Object)(_downloadmanager1.ERROR_DEVICE_NOT_FOUND),(Object)("ERROR_DEVICE_NOT_FOUND"));
 //BA.debugLineNum = 14;BA.debugLine="ReasonTextMap.Put(DownloadManager1.ERROR_FILE_ALREADY_EXISTS, \"ERROR_FILE_ALREADY_EXISTS\")";
_vv4.Put((Object)(_downloadmanager1.ERROR_FILE_ALREADY_EXISTS),(Object)("ERROR_FILE_ALREADY_EXISTS"));
 //BA.debugLineNum = 15;BA.debugLine="ReasonTextMap.Put(DownloadManager1.ERROR_FILE_ERROR, \"ERROR_FILE_ERROR\")";
_vv4.Put((Object)(_downloadmanager1.ERROR_FILE_ERROR),(Object)("ERROR_FILE_ERROR"));
 //BA.debugLineNum = 16;BA.debugLine="ReasonTextMap.Put(DownloadManager1.ERROR_HTTP_DATA_ERROR, \"ERROR_HTTP_DATA_ERROR\")";
_vv4.Put((Object)(_downloadmanager1.ERROR_HTTP_DATA_ERROR),(Object)("ERROR_HTTP_DATA_ERROR"));
 //BA.debugLineNum = 17;BA.debugLine="ReasonTextMap.Put(DownloadManager1.ERROR_INSUFFICIENT_SPACE, \"ERROR_INSUFFICIENT_SPACE\")";
_vv4.Put((Object)(_downloadmanager1.ERROR_INSUFFICIENT_SPACE),(Object)("ERROR_INSUFFICIENT_SPACE"));
 //BA.debugLineNum = 18;BA.debugLine="ReasonTextMap.Put(DownloadManager1.ERROR_TOO_MANY_REDIRECTS, \"ERROR_TOO_MANY_REDIRECTS\")";
_vv4.Put((Object)(_downloadmanager1.ERROR_TOO_MANY_REDIRECTS),(Object)("ERROR_TOO_MANY_REDIRECTS"));
 //BA.debugLineNum = 19;BA.debugLine="ReasonTextMap.Put(DownloadManager1.ERROR_UNHANDLED_HTTP_CODE, \"ERROR_UNHANDLED_HTTP_CODE\")";
_vv4.Put((Object)(_downloadmanager1.ERROR_UNHANDLED_HTTP_CODE),(Object)("ERROR_UNHANDLED_HTTP_CODE"));
 //BA.debugLineNum = 20;BA.debugLine="ReasonTextMap.Put(DownloadManager1.ERROR_UNKNOWN, \"ERROR_UNKNOWN\")";
_vv4.Put((Object)(_downloadmanager1.ERROR_UNKNOWN),(Object)("ERROR_UNKNOWN"));
 //BA.debugLineNum = 21;BA.debugLine="ReasonTextMap.Put(DownloadManager1.PAUSED_QUEUED_FOR_WIFI, \"PAUSED_QUEUED_FOR_WIFI\")";
_vv4.Put((Object)(_downloadmanager1.PAUSED_QUEUED_FOR_WIFI),(Object)("PAUSED_QUEUED_FOR_WIFI"));
 //BA.debugLineNum = 22;BA.debugLine="ReasonTextMap.Put(DownloadManager1.PAUSED_UNKNOWN, \"PAUSED_UNKNOWN\")";
_vv4.Put((Object)(_downloadmanager1.PAUSED_UNKNOWN),(Object)("PAUSED_UNKNOWN"));
 //BA.debugLineNum = 23;BA.debugLine="ReasonTextMap.Put(DownloadManager1.PAUSED_WAITING_FOR_NETWORK, \"PAUSED_WAITING_FOR_NETWORK\")";
_vv4.Put((Object)(_downloadmanager1.PAUSED_WAITING_FOR_NETWORK),(Object)("PAUSED_WAITING_FOR_NETWORK"));
 //BA.debugLineNum = 24;BA.debugLine="ReasonTextMap.Put(DownloadManager1.PAUSED_WAITING_TO_RETRY, \"PAUSED_WAITING_TO_RETRY\")";
_vv4.Put((Object)(_downloadmanager1.PAUSED_WAITING_TO_RETRY),(Object)("PAUSED_WAITING_TO_RETRY"));
 //BA.debugLineNum = 26;BA.debugLine="StatusTextMap.Initialize";
_vv5.Initialize();
 //BA.debugLineNum = 27;BA.debugLine="StatusTextMap.Put(DownloadManager1.STATUS_FAILED, \"STATUS_FAILED\")";
_vv5.Put((Object)(_downloadmanager1.STATUS_FAILED),(Object)("STATUS_FAILED"));
 //BA.debugLineNum = 28;BA.debugLine="StatusTextMap.Put(DownloadManager1.STATUS_PAUSED, \"STATUS_PAUSED\")";
_vv5.Put((Object)(_downloadmanager1.STATUS_PAUSED),(Object)("STATUS_PAUSED"));
 //BA.debugLineNum = 29;BA.debugLine="StatusTextMap.Put(DownloadManager1.STATUS_PENDING, \"STATUS_PENDING\")";
_vv5.Put((Object)(_downloadmanager1.STATUS_PENDING),(Object)("STATUS_PENDING"));
 //BA.debugLineNum = 30;BA.debugLine="StatusTextMap.Put(DownloadManager1.STATUS_RUNNING, \"STATUS_RUNNING\")";
_vv5.Put((Object)(_downloadmanager1.STATUS_RUNNING),(Object)("STATUS_RUNNING"));
 //BA.debugLineNum = 31;BA.debugLine="StatusTextMap.Put(DownloadManager1.STATUS_SUCCESSFUL, \"STATUS_SUCCESSFUL\")";
_vv5.Put((Object)(_downloadmanager1.STATUS_SUCCESSFUL),(Object)("STATUS_SUCCESSFUL"));
 //BA.debugLineNum = 33;BA.debugLine="IsInitialized=True";
_vv6 = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 35;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 2;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 3;BA.debugLine="Dim ReasonTextMap As Map";
_vv4 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 4;BA.debugLine="Dim StatusTextMap As Map";
_vv5 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 6;BA.debugLine="Dim IsInitialized As Boolean=False";
_vv6 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 7;BA.debugLine="End Sub";
return "";
}
}
