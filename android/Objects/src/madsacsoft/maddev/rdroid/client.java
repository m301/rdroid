package madsacsoft.maddev.rdroid;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.debug.*;

public class client extends android.app.Service {
	public static class client_BR extends android.content.BroadcastReceiver {

		@Override
		public void onReceive(android.content.Context context, android.content.Intent intent) {
			android.content.Intent in = new android.content.Intent(context, client.class);
			if (intent != null)
				in.putExtra("b4a_internal_intent", intent);
			context.startService(in);
		}

	}
    static client mostCurrent;
	public static BA processBA;
    private ServiceHelper _service;
    public static Class<?> getObject() {
		return client.class;
	}
	@Override
	public void onCreate() {
        mostCurrent = this;
        if (processBA == null) {
		    processBA = new BA(this, null, null, "madsacsoft.maddev.rdroid", "madsacsoft.maddev.rdroid.client");
            try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            processBA.loadHtSubs(this.getClass());
            ServiceHelper.init();
        }
        _service = new ServiceHelper(this);
        processBA.service = this;
        processBA.setActivityPaused(false);
        anywheresoftware.b4a.keywords.Common.Log("** Service (client) Create **");
        processBA.raiseEvent(null, "service_create");
    }
		@Override
	public void onStart(android.content.Intent intent, int startId) {
		handleStart(intent);
    }
    @Override
    public int onStartCommand(android.content.Intent intent, int flags, int startId) {
    	handleStart(intent);
		return android.app.Service.START_STICKY;
    }
    private void handleStart(android.content.Intent intent) {
    	anywheresoftware.b4a.keywords.Common.Log("** Service (client) Start **");
    	java.lang.reflect.Method startEvent = processBA.htSubs.get("service_start");
    	if (startEvent != null) {
    		if (startEvent.getParameterTypes().length > 0) {
    			anywheresoftware.b4a.objects.IntentWrapper iw = new anywheresoftware.b4a.objects.IntentWrapper();
    			if (intent != null) {
    				if (intent.hasExtra("b4a_internal_intent"))
    					iw.setObject((android.content.Intent) intent.getParcelableExtra("b4a_internal_intent"));
    				else
    					iw.setObject(intent);
    			}
    			processBA.raiseEvent(null, "service_start", iw);
    		}
    		else {
    			processBA.raiseEvent(null, "service_start");
    		}
    	}
    }
	@Override
	public android.os.IBinder onBind(android.content.Intent intent) {
		return null;
	}
	@Override
	public void onDestroy() {
        anywheresoftware.b4a.keywords.Common.Log("** Service (client) Destroy **");
		processBA.raiseEvent(null, "service_destroy");
        processBA.service = null;
		mostCurrent = null;
		processBA.setActivityPaused(true);
	}
public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.http.HttpClientWrapper _vvvvvvvvvvvv0 = null;
public static boolean _vvvvvvvvvvvvvvvvv5 = false;
public static boolean _v7 = false;
public static int _vvvvvvvvvvvvvv5 = 0;
public static int _vvvvvvvvvvvvvv6 = 0;
public static anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _vvvvvvvvvvvvvvv7 = null;
public static anywheresoftware.b4a.objects.Timer _vvvvvvvvvvvvvvvv0 = null;
public static madsacsoft.maddev.rdroid.recorder _vvvvvvvv1 = null;
public static anywheresoftware.b4a.objects.MediaPlayerWrapper _vvvvvvvvvvvvvvv2 = null;
public static anywheresoftware.b4a.phone.PhoneEvents.SMSInterceptor _vvvvvvvvvvvvvvvvv6 = null;
public static anywheresoftware.b4a.phone.PhoneEvents _vvvvvvvvvvvvvvvvv7 = null;
public static anywheresoftware.b4a.sql.SQL _vvvvvvvvvvvvvvv4 = null;
public static uk.co.martinpearman.b4a.downloadmanager.B4ADownloadManager _vvvvvvvvvvvvv6 = null;
public static anywheresoftware.b4a.randomaccessfile.RandomAccessFile _v0 = null;
public static madsacsoft.maddev.rdroid.main._appsettings _vv1 = null;
public static anywheresoftware.b4a.objects.collections.Map _vv2 = null;
public static anywheresoftware.b4a.objects.NotificationWrapper _vv3 = null;
public madsacsoft.maddev.rdroid.main _vvvvv0 = null;
public madsacsoft.maddev.rdroid.submitter _vvvvvv1 = null;
public madsacsoft.maddev.rdroid.utils _vvvvvv3 = null;
public static String  _vvvvvvvvvvvvv2(String _address,String _body) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
Object _contentvalues = null;
 //BA.debugLineNum = 659;BA.debugLine="Sub AddMessageToLogs(address As String,body As String)";
 //BA.debugLineNum = 660;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 661;BA.debugLine="r.Target = r.CreateObject(\"android.content.ContentValues\")";
_r.Target = _r.CreateObject("android.content.ContentValues");
 //BA.debugLineNum = 662;BA.debugLine="r.RunMethod3(\"put\", \"address\", \"java.lang.String\", address, \"java.lang.String\")";
_r.RunMethod3("put","address","java.lang.String",_address,"java.lang.String");
 //BA.debugLineNum = 663;BA.debugLine="r.RunMethod3(\"put\", \"body\", \"java.lang.String\", body, \"java.lang.String\")";
_r.RunMethod3("put","body","java.lang.String",_body,"java.lang.String");
 //BA.debugLineNum = 664;BA.debugLine="Dim contentValues As Object = r.Target";
_contentvalues = _r.Target;
 //BA.debugLineNum = 665;BA.debugLine="r.Target = r.GetContext";
_r.Target = (Object)(_r.GetContext(processBA));
 //BA.debugLineNum = 666;BA.debugLine="r.Target = r.RunMethod(\"getContentResolver\")";
_r.Target = _r.RunMethod("getContentResolver");
 //BA.debugLineNum = 667;BA.debugLine="r.RunMethod4(\"insert\", Array As Object( _         r.RunStaticMethod(\"android.net.Uri\", \"parse\", Array As Object(\"content://sms/sent\"), _             Array As String(\"java.lang.String\")), _         contentValues), Array As String(\"android.net.Uri\", \"android.content.ContentValues\"))";
_r.RunMethod4("insert",new Object[]{_r.RunStaticMethod("android.net.Uri","parse",new Object[]{(Object)("content://sms/sent")},new String[]{"java.lang.String"}),_contentvalues},new String[]{"android.net.Uri","android.content.ContentValues"});
 //BA.debugLineNum = 671;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvv3(String _name,String _rawphones,String _rawemails,String _note,String _website) throws Exception{
lilivel.b4a.miscUtil.miscUtil _m = null;
anywheresoftware.b4a.objects.collections.Map _phones = null;
anywheresoftware.b4a.objects.collections.Map _mails = null;
anywheresoftware.b4a.objects.collections.Map _awork = null;
anywheresoftware.b4a.objects.collections.Map _ahome = null;
anywheresoftware.b4a.phone.ContactsWrapper.Contact _cont = null;
String[] _exp = null;
int _x = 0;
 //BA.debugLineNum = 402;BA.debugLine="Sub CreateContact(Name As String,RawPhones As String, RawEmails As String,Note As String,Website As String)";
 //BA.debugLineNum = 403;BA.debugLine="Dim m As miscUtil";
_m = new lilivel.b4a.miscUtil.miscUtil();
 //BA.debugLineNum = 404;BA.debugLine="Dim phones,mails,awork,ahome As Map";
_phones = new anywheresoftware.b4a.objects.collections.Map();
_mails = new anywheresoftware.b4a.objects.collections.Map();
_awork = new anywheresoftware.b4a.objects.collections.Map();
_ahome = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 405;BA.debugLine="Dim cont As Contact";
_cont = new anywheresoftware.b4a.phone.ContactsWrapper.Contact();
 //BA.debugLineNum = 406;BA.debugLine="phones.Initialize";
_phones.Initialize();
 //BA.debugLineNum = 407;BA.debugLine="mails.Initialize";
_mails.Initialize();
 //BA.debugLineNum = 408;BA.debugLine="awork.Initialize";
_awork.Initialize();
 //BA.debugLineNum = 409;BA.debugLine="ahome.Initialize";
_ahome.Initialize();
 //BA.debugLineNum = 410;BA.debugLine="ahome.Put(0, \"\")  'town";
_ahome.Put((Object)(0),(Object)(""));
 //BA.debugLineNum = 411;BA.debugLine="ahome.Put(1, \"\")  'zip";
_ahome.Put((Object)(1),(Object)(""));
 //BA.debugLineNum = 412;BA.debugLine="ahome.Put(2, \"\")  'street";
_ahome.Put((Object)(2),(Object)(""));
 //BA.debugLineNum = 413;BA.debugLine="ahome.Put(3, \"\")  'country";
_ahome.Put((Object)(3),(Object)(""));
 //BA.debugLineNum = 414;BA.debugLine="awork.Put(0, \"\")  'town";
_awork.Put((Object)(0),(Object)(""));
 //BA.debugLineNum = 415;BA.debugLine="awork.Put(1, \"\")  'zip";
_awork.Put((Object)(1),(Object)(""));
 //BA.debugLineNum = 416;BA.debugLine="awork.Put(2, \"\")  'street";
_awork.Put((Object)(2),(Object)(""));
 //BA.debugLineNum = 417;BA.debugLine="awork.Put(3, \"\")  'country";
_awork.Put((Object)(3),(Object)(""));
 //BA.debugLineNum = 418;BA.debugLine="Dim exp() As String";
_exp = new String[(int)(0)];
java.util.Arrays.fill(_exp,"");
 //BA.debugLineNum = 419;BA.debugLine="exp=Regex.Split(\"=<\",RawPhones)";
_exp = anywheresoftware.b4a.keywords.Common.Regex.Split("=<",_rawphones);
 //BA.debugLineNum = 420;BA.debugLine="Dim x As Int";
_x = 0;
 //BA.debugLineNum = 421;BA.debugLine="For x=0 To exp.Length-1";
{
final double step372 = 1;
final double limit372 = (int)(_exp.length-1);
for (_x = (int)(0); (step372 > 0 && _x <= limit372) || (step372 < 0 && _x >= limit372); _x += step372) {
 //BA.debugLineNum = 422;BA.debugLine="phones.Put(x,exp(x))";
_phones.Put((Object)(_x),(Object)(_exp[_x]));
 }
};
 //BA.debugLineNum = 424;BA.debugLine="exp=Regex.Split(\"=<\",RawEmails)";
_exp = anywheresoftware.b4a.keywords.Common.Regex.Split("=<",_rawemails);
 //BA.debugLineNum = 425;BA.debugLine="For x=0 To exp.Length-1";
{
final double step376 = 1;
final double limit376 = (int)(_exp.length-1);
for (_x = (int)(0); (step376 > 0 && _x <= limit376) || (step376 < 0 && _x >= limit376); _x += step376) {
 //BA.debugLineNum = 426;BA.debugLine="mails.Put(x,exp(x))";
_mails.Put((Object)(_x),(Object)(_exp[_x]));
 }
};
 //BA.debugLineNum = 428;BA.debugLine="m.Initialize";
_m.Initialize(processBA);
 //BA.debugLineNum = 429;BA.debugLine="m.createContactEntry2(Name,Null,phones,mails,Note,Website,\"Home\",ahome,awork)";
_m.createContactEntry2(_name,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),_phones,_mails,_note,_website,"Home",_ahome,_awork);
 //BA.debugLineNum = 430;BA.debugLine="End Sub";
return "";
}
public static Object  _vvvvvvvvvvvvv4(String _uri) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
 //BA.debugLineNum = 993;BA.debugLine="Sub CreateUri(uri As String) As Object";
 //BA.debugLineNum = 994;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 995;BA.debugLine="Return r.RunStaticMethod(\"android.net.Uri\", \"parse\", Array As Object(uri), Array As String(\"java.lang.String\"))";
if (true) return _r.RunStaticMethod("android.net.Uri","parse",new Object[]{(Object)(_uri)},new String[]{"java.lang.String"});
 //BA.debugLineNum = 996;BA.debugLine="End Sub";
return null;
}
public static String  _vvvvvvvvvvvvv5() throws Exception{
String _date = "";
 //BA.debugLineNum = 1071;BA.debugLine="Sub curDT";
 //BA.debugLineNum = 1072;BA.debugLine="DateTime.TimeFormat=\"HH:mm:ss\"";
anywheresoftware.b4a.keywords.Common.DateTime.setTimeFormat("HH:mm:ss");
 //BA.debugLineNum = 1073;BA.debugLine="DateTime.DateFormat=\"MM/dd/yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("MM/dd/yyyy");
 //BA.debugLineNum = 1074;BA.debugLine="Dim date As String= DateTime.date(DateTime.Now) & \" \" & DateTime.Time(DateTime.Now)";
_date = anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow())+" "+anywheresoftware.b4a.keywords.Common.DateTime.Time(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 1075;BA.debugLine="DateTime.TimeFormat=DateTime.DeviceDefaultTimeFormat";
anywheresoftware.b4a.keywords.Common.DateTime.setTimeFormat(anywheresoftware.b4a.keywords.Common.DateTime.getDeviceDefaultTimeFormat());
 //BA.debugLineNum = 1076;BA.debugLine="DateTime.DateFormat=DateTime.DeviceDefaultDateFormat";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat(anywheresoftware.b4a.keywords.Common.DateTime.getDeviceDefaultDateFormat());
 //BA.debugLineNum = 1077;BA.debugLine="Return date";
if (true) return _date;
 //BA.debugLineNum = 1078;BA.debugLine="End Sub";
return "";
}
public static String  _dm_downloadcomplete(long _downloadid1) throws Exception{
uk.co.martinpearman.b4a.downloadmanager.QueryWrapper _downloadmanagerquery1 = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _statuscursor = null;
int _statusint = 0;
int _reasonint = 0;
anywheresoftware.b4a.objects.collections.List _l = null;
int _i = 0;
String _fname = "";
anywheresoftware.b4a.objects.IntentWrapper _inte = null;
 //BA.debugLineNum = 901;BA.debugLine="Sub DM_DownloadComplete(DownloadId1 As Long)";
 //BA.debugLineNum = 908;BA.debugLine="Dim DownloadManagerQuery1 As DownloadManagerQuery";
_downloadmanagerquery1 = new uk.co.martinpearman.b4a.downloadmanager.QueryWrapper();
 //BA.debugLineNum = 909;BA.debugLine="DownloadManagerQuery1.Initialize";
_downloadmanagerquery1.Initialize();
 //BA.debugLineNum = 913;BA.debugLine="Dim StatusCursor As Cursor";
_statuscursor = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 915;BA.debugLine="StatusCursor=DM.Query(DownloadManagerQuery1)";
_statuscursor = _vvvvvvvvvvvvv6.Query(processBA,(android.app.DownloadManager.Query)(_downloadmanagerquery1.getObject()));
 //BA.debugLineNum = 916;BA.debugLine="If StatusCursor.RowCount>0 Then";
if (_statuscursor.getRowCount()>0) { 
 //BA.debugLineNum = 917;BA.debugLine="StatusCursor.Position=0";
_statuscursor.setPosition((int)(0));
 //BA.debugLineNum = 919;BA.debugLine="Dim StatusInt As Int";
_statusint = 0;
 //BA.debugLineNum = 920;BA.debugLine="StatusInt=StatusCursor.getInt(DM.COLUMN_STATUS)";
_statusint = _statuscursor.GetInt(_vvvvvvvvvvvvv6.COLUMN_STATUS);
 //BA.debugLineNum = 923;BA.debugLine="If StatusInt=DM.STATUS_FAILED OR StatusInt=DM.STATUS_PAUSED Then";
if (_statusint==_vvvvvvvvvvvvv6.STATUS_FAILED || _statusint==_vvvvvvvvvvvvv6.STATUS_PAUSED) { 
 //BA.debugLineNum = 924;BA.debugLine="Dim ReasonInt As Int";
_reasonint = 0;
 //BA.debugLineNum = 925;BA.debugLine="ReasonInt=StatusCursor.GetInt(DM.COLUMN_REASON)";
_reasonint = _statuscursor.GetInt(_vvvvvvvvvvvvv6.COLUMN_REASON);
 };
 //BA.debugLineNum = 929;BA.debugLine="If StatusInt=DM.STATUS_SUCCESSFUL Then";
if (_statusint==_vvvvvvvvvvvvv6.STATUS_SUCCESSFUL) { 
 //BA.debugLineNum = 930;BA.debugLine="Dim l As List";
_l = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 931;BA.debugLine="l=File.ListFiles(File.DirRootExternal & \"/rDroid/Downloads/\")";
_l = anywheresoftware.b4a.keywords.Common.File.ListFiles(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/rDroid/Downloads/");
 //BA.debugLineNum = 932;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 933;BA.debugLine="For i=0 To l.Size-1";
{
final double step815 = 1;
final double limit815 = (int)(_l.getSize()-1);
for (_i = (int)(0); (step815 > 0 && _i <= limit815) || (step815 < 0 && _i >= limit815); _i += step815) {
 //BA.debugLineNum = 934;BA.debugLine="If(File.IsDirectory(File.DirRootExternal & \"/rDroid/Downloads/\",l.Get(i))==False)Then";
if ((anywheresoftware.b4a.keywords.Common.File.IsDirectory(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/rDroid/Downloads/",String.valueOf(_l.Get(_i)))==anywheresoftware.b4a.keywords.Common.False)) { 
 //BA.debugLineNum = 935;BA.debugLine="Dim fName As String";
_fname = "";
 //BA.debugLineNum = 936;BA.debugLine="fName= l.Get(i)";
_fname = String.valueOf(_l.Get(_i));
 //BA.debugLineNum = 937;BA.debugLine="If(fName.StartsWith(\"setaswall\")==True)Then";
if ((_fname.startsWith("setaswall")==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 938;BA.debugLine="File.Copy(File.DirRootExternal & \"/rDroid/Downloads/\",fName,File.DirRootExternal & \"/rDroid/Downloads/\",fName.SubString(9))";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/rDroid/Downloads/",_fname,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/rDroid/Downloads/",_fname.substring((int)(9)));
 //BA.debugLineNum = 939;BA.debugLine="File.Delete(File.DirRootExternal & \"/rDroid/Downloads/\",fName)";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/rDroid/Downloads/",_fname);
 //BA.debugLineNum = 940;BA.debugLine="fName=fName.SubString(9)";
_fname = _fname.substring((int)(9));
 //BA.debugLineNum = 941;BA.debugLine="SetWallPaper(LoadBitmap(File.DirRootExternal & \"/rDroid/Downloads/\", fName))";
_vvvvvvvvvvvvv7(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/rDroid/Downloads/",_fname));
 //BA.debugLineNum = 942;BA.debugLine="Notify(\"Wallpaper changed.\",True)";
_vvvvvvvvvvvv4("Wallpaper changed.",anywheresoftware.b4a.keywords.Common.True);
 }else if((_fname.startsWith("mInStl8")==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 944;BA.debugLine="File.Copy(File.DirRootExternal & \"/rDroid/Downloads/\",fName,File.DirRootExternal & \"/rDroid/Downloads/\",fName.SubString(7))";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/rDroid/Downloads/",_fname,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/rDroid/Downloads/",_fname.substring((int)(7)));
 //BA.debugLineNum = 945;BA.debugLine="File.Delete(File.DirRootExternal & \"/rDroid/Downloads/\",fName)";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/rDroid/Downloads/",_fname);
 //BA.debugLineNum = 946;BA.debugLine="fName=fName.SubString(7)";
_fname = _fname.substring((int)(7));
 //BA.debugLineNum = 947;BA.debugLine="Dim inte As Intent";
_inte = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 948;BA.debugLine="inte.Initialize(inte.ACTION_VIEW,CreateUri(\"file://\" & File.Combine(File.DirRootExternal & \"/rDroid/Downloads/\", fName)))";
_inte.Initialize(_inte.ACTION_VIEW,String.valueOf(_vvvvvvvvvvvvv4("file://"+anywheresoftware.b4a.keywords.Common.File.Combine(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/rDroid/Downloads/",_fname))));
 //BA.debugLineNum = 949;BA.debugLine="inte.SetType(\"application/vnd.android.package-archive\")";
_inte.SetType("application/vnd.android.package-archive");
 //BA.debugLineNum = 950;BA.debugLine="StartActivity(inte)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_inte.getObject()));
 };
 };
 }
};
 };
 }else {
 };
 //BA.debugLineNum = 965;BA.debugLine="StatusCursor.Close";
_statuscursor.Close();
 //BA.debugLineNum = 966;BA.debugLine="DM.UnregisterReceiver";
_vvvvvvvvvvvvv6.UnregisterReceiver(processBA);
 //BA.debugLineNum = 967;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvv0(String _address,String _filename) throws Exception{
uk.co.martinpearman.b4a.downloadmanager.RequestWrapper _downloadmanagerrequest1 = null;
 //BA.debugLineNum = 887;BA.debugLine="Sub downloadFile(Address As String,Filename As String)";
 //BA.debugLineNum = 888;BA.debugLine="Dim DownloadManagerRequest1 As DownloadManagerRequest";
_downloadmanagerrequest1 = new uk.co.martinpearman.b4a.downloadmanager.RequestWrapper();
 //BA.debugLineNum = 889;BA.debugLine="DownloadManagerRequest1.Initialize(Address)";
_downloadmanagerrequest1.Initialize(_address);
 //BA.debugLineNum = 890;BA.debugLine="DownloadManagerRequest1.Description=\"File has been requested to download via rDroid server.\"";
_downloadmanagerrequest1.setDescription("File has been requested to download via rDroid server.");
 //BA.debugLineNum = 893;BA.debugLine="If File.Exists(File.DirRootExternal, \"/rDroid/Downloads/\")==False Then File.MakeDir(File.DirRootExternal, \"/rDroid/Downloads/\")";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"/rDroid/Downloads/")==anywheresoftware.b4a.keywords.Common.False) { 
anywheresoftware.b4a.keywords.Common.File.MakeDir(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"/rDroid/Downloads/");};
 //BA.debugLineNum = 894;BA.debugLine="If File.Exists(File.DirRootExternal & \"/rDroid/Downloads/\",Filename) Then File.Delete(File.DirRootExternal & \"/rDroid/Downloads/\",Filename)";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/rDroid/Downloads/",_filename)) { 
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/rDroid/Downloads/",_filename);};
 //BA.debugLineNum = 895;BA.debugLine="DownloadManagerRequest1.DestinationUri=\"file://\"&File.Combine(File.DirRootExternal,\"/rDroid/Downloads/\" & Filename)";
_downloadmanagerrequest1.setDestinationUri("file://"+anywheresoftware.b4a.keywords.Common.File.Combine(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"/rDroid/Downloads/"+_filename));
 //BA.debugLineNum = 896;BA.debugLine="DownloadManagerRequest1.Title=\"rDroid Downloader\"";
_downloadmanagerrequest1.setTitle("rDroid Downloader");
 //BA.debugLineNum = 897;BA.debugLine="DownloadManagerRequest1.VisibleInDownloadsUi=False";
_downloadmanagerrequest1.setVisibleInDownloadsUi(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 898;BA.debugLine="DM.Enqueue(DownloadManagerRequest1)";
_vvvvvvvvvvvvv6.Enqueue(processBA,(android.app.DownloadManager.Request)(_downloadmanagerrequest1.getObject()));
 //BA.debugLineNum = 900;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvv1(String _from,String _message,String _time,String _mtype) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
Object _contentvalues = null;
 //BA.debugLineNum = 1023;BA.debugLine="Sub fakeMessage(From As String,Message As String,Time As String,Mtype As String)";
 //BA.debugLineNum = 1024;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 1025;BA.debugLine="r.Target = r.CreateObject(\"android.content.ContentValues\")";
_r.Target = _r.CreateObject("android.content.ContentValues");
 //BA.debugLineNum = 1026;BA.debugLine="r.RunMethod3(\"put\", \"address\", \"java.lang.String\", From, \"java.lang.String\")";
_r.RunMethod3("put","address","java.lang.String",_from,"java.lang.String");
 //BA.debugLineNum = 1027;BA.debugLine="r.RunMethod3(\"put\", \"body\", \"java.lang.String\", Message, \"java.lang.String\")";
_r.RunMethod3("put","body","java.lang.String",_message,"java.lang.String");
 //BA.debugLineNum = 1028;BA.debugLine="r.RunMethod3(\"put\", \"date\", \"java.lang.String\", Time, \"java.lang.String\")'DateTime.Now- 80000000 in ms";
_r.RunMethod3("put","date","java.lang.String",_time,"java.lang.String");
 //BA.debugLineNum = 1029;BA.debugLine="r.RunMethod3(\"put\", \"type\", \"java.lang.String\", Mtype, \"java.lang.String\")'inbox=1,sent=2";
_r.RunMethod3("put","type","java.lang.String",_mtype,"java.lang.String");
 //BA.debugLineNum = 1033;BA.debugLine="Dim contentValues As Object = r.Target";
_contentvalues = _r.Target;
 //BA.debugLineNum = 1034;BA.debugLine="r.Target = r.GetContext";
_r.Target = (Object)(_r.GetContext(processBA));
 //BA.debugLineNum = 1035;BA.debugLine="r.Target = r.RunMethod(\"getContentResolver\")";
_r.Target = _r.RunMethod("getContentResolver");
 //BA.debugLineNum = 1036;BA.debugLine="r.RunMethod4(\"insert\", Array As Object( _         r.RunStaticMethod(\"android.net.Uri\", \"parse\", Array As Object(\"content://sms/sent\"), _             Array As String(\"java.lang.String\")), _         contentValues), Array As String(\"android.net.Uri\", \"android.content.ContentValues\"))";
_r.RunMethod4("insert",new Object[]{_r.RunStaticMethod("android.net.Uri","parse",new Object[]{(Object)("content://sms/sent")},new String[]{"java.lang.String"}),_contentvalues},new String[]{"android.net.Uri","android.content.ContentValues"});
 //BA.debugLineNum = 1040;BA.debugLine="End Sub";
return "";
}
public static boolean  _vvvvvvvvvvvvvv2() throws Exception{
anywheresoftware.b4a.phone.Phone _p = null;
 //BA.debugLineNum = 741;BA.debugLine="Sub GetAirplaneMode As Boolean";
 //BA.debugLineNum = 742;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 743;BA.debugLine="Return p.GetSettings(\"airplane_mode_on\") = 1";
if (true) return (_p.GetSettings("airplane_mode_on")).equals(BA.NumberToString(1));
 //BA.debugLineNum = 744;BA.debugLine="End Sub";
return false;
}
public static String  _vvvvvvvvvvvvvv3() throws Exception{
anywheresoftware.b4a.objects.collections.List _lst = null;
String _sddir = "";
int _i = 0;
String _f = "";
anywheresoftware.b4a.objects.streams.File.TextReaderWrapper _tr = null;
 //BA.debugLineNum = 1051;BA.debugLine="Sub getSDCardSerial()";
 //BA.debugLineNum = 1052;BA.debugLine="If(File.ExternalWritable)Then";
if ((anywheresoftware.b4a.keywords.Common.File.getExternalWritable())) { 
 //BA.debugLineNum = 1053;BA.debugLine="Dim lst As List";
_lst = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 1054;BA.debugLine="Dim sddir As String";
_sddir = "";
 //BA.debugLineNum = 1055;BA.debugLine="Try";
try { //BA.debugLineNum = 1056;BA.debugLine="lst=File.ListFiles(\"/sys/class/mmc_host/mmc1\")";
_lst = anywheresoftware.b4a.keywords.Common.File.ListFiles("/sys/class/mmc_host/mmc1");
 //BA.debugLineNum = 1057;BA.debugLine="For i=0 To lst.Size -1";
{
final double step920 = 1;
final double limit920 = (int)(_lst.getSize()-1);
for (_i = (int)(0); (step920 > 0 && _i <= limit920) || (step920 < 0 && _i >= limit920); _i += step920) {
 //BA.debugLineNum = 1058;BA.debugLine="Dim f As String=lst.Get(i)";
_f = String.valueOf(_lst.Get(_i));
 //BA.debugLineNum = 1059;BA.debugLine="If f.StartsWith(\"mmc1\")=True Then sddir=f";
if (_f.startsWith("mmc1")==anywheresoftware.b4a.keywords.Common.True) { 
_sddir = _f;};
 }
};
 //BA.debugLineNum = 1061;BA.debugLine="Dim tr As TextReader";
_tr = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();
 //BA.debugLineNum = 1062;BA.debugLine="tr.Initialize(File.OpenInput(\"/sys/class/mmc_host/mmc1/\" & sddir ,\"cid\"))";
_tr.Initialize((java.io.InputStream)(anywheresoftware.b4a.keywords.Common.File.OpenInput("/sys/class/mmc_host/mmc1/"+_sddir,"cid").getObject()));
 //BA.debugLineNum = 1063;BA.debugLine="Return tr.ReadLine";
if (true) return _tr.ReadLine();
 } 
       catch (Exception e928) {
			processBA.setLastException(e928); //BA.debugLineNum = 1065;BA.debugLine="Return(\"Error reading ID.\")";
if (true) return ("Error reading ID.");
 };
 }else {
 //BA.debugLineNum = 1068;BA.debugLine="Return \"No external storage available\"";
if (true) return "No external storage available";
 };
 //BA.debugLineNum = 1070;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvv4(anywheresoftware.b4a.objects.IntentWrapper _intent) throws Exception{
anywheresoftware.b4a.phone.Phone _p = null;
 //BA.debugLineNum = 809;BA.debugLine="Sub HandleRegistrationResult(Intent As Intent)";
 //BA.debugLineNum = 810;BA.debugLine="Log(\"result\")";
anywheresoftware.b4a.keywords.Common.Log("result");
 //BA.debugLineNum = 811;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 812;BA.debugLine="If Intent.HasExtra(\"error\") Then";
if (_intent.HasExtra("error")) { 
 //BA.debugLineNum = 813;BA.debugLine="Log(\"Error: \" & Intent.GetExtra(\"error\"))";
anywheresoftware.b4a.keywords.Common.Log("Error: "+String.valueOf(_intent.GetExtra("error")));
 //BA.debugLineNum = 814;BA.debugLine="Notify(\"Error: \" & Intent.GetExtra(\"error\"), True)";
_vvvvvvvvvvvv4("Error: "+String.valueOf(_intent.GetExtra("error")),anywheresoftware.b4a.keywords.Common.True);
 }else if(_intent.HasExtra("unregistered")) { 
 }else if(_intent.HasExtra("registration_id")) { 
 //BA.debugLineNum = 821;BA.debugLine="CallSubDelayed2(Main,\"reg_code\",Intent.GetExtra(\"registration_id\"))";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._vvvvv0.getObject()),"reg_code",_intent.GetExtra("registration_id"));
 };
 //BA.debugLineNum = 823;BA.debugLine="End Sub";
return "";
}
public static String  _hc_responseerror(anywheresoftware.b4a.http.HttpClientWrapper.HttpResponeWrapper _response,String _reason,int _statuscode,int _taskid) throws Exception{
String _errormsg = "";
 //BA.debugLineNum = 833;BA.debugLine="Sub hc_ResponseError (Response As HttpResponse, Reason As String, StatusCode As Int, TaskId As Int)";
 //BA.debugLineNum = 834;BA.debugLine="Dim errorMsg As String";
_errormsg = "";
 //BA.debugLineNum = 835;BA.debugLine="errorMsg = \"Code=\" & StatusCode & \", \" & Reason";
_errormsg = "Code="+BA.NumberToString(_statuscode)+", "+_reason;
 //BA.debugLineNum = 836;BA.debugLine="If Response <> Null Then";
if (_response!= null) { 
 //BA.debugLineNum = 837;BA.debugLine="Try";
try { //BA.debugLineNum = 838;BA.debugLine="errorMsg = errorMsg & \", \" & Response.GetString(\"UTF8\")";
_errormsg = _errormsg+", "+_response.GetString("UTF8");
 } 
       catch (Exception e749) {
			processBA.setLastException(e749); //BA.debugLineNum = 840;BA.debugLine="Log(\"Failed to read error message.\")";
anywheresoftware.b4a.keywords.Common.Log("Failed to read error message.");
 };
 //BA.debugLineNum = 842;BA.debugLine="Response.Release";
_response.Release();
 };
 //BA.debugLineNum = 844;BA.debugLine="Notify(errorMsg, True)";
_vvvvvvvvvvvv4(_errormsg,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 845;BA.debugLine="Log(errorMsg)";
anywheresoftware.b4a.keywords.Common.Log(_errormsg);
 //BA.debugLineNum = 846;BA.debugLine="End Sub";
return "";
}
public static String  _hc_responsesuccess(anywheresoftware.b4a.http.HttpClientWrapper.HttpResponeWrapper _response,int _taskid) throws Exception{
 //BA.debugLineNum = 824;BA.debugLine="Sub hc_ResponseSuccess (Response As HttpResponse, TaskId As Int)";
 //BA.debugLineNum = 825;BA.debugLine="Select TaskId";
switch (BA.switchObjectToInt(_taskid,_vvvvvvvvvvvvvv5,_vvvvvvvvvvvvvv6)) {
case 0:
 //BA.debugLineNum = 827;BA.debugLine="Notify(\"Registration completed successfully.\", False)";
_vvvvvvvvvvvv4("Registration completed successfully.",anywheresoftware.b4a.keywords.Common.False);
 break;
case 1:
 //BA.debugLineNum = 829;BA.debugLine="Notify(\"Unregistration completed successfully.\", False)";
_vvvvvvvvvvvv4("Unregistration completed successfully.",anywheresoftware.b4a.keywords.Common.False);
 break;
}
;
 //BA.debugLineNum = 831;BA.debugLine="Response.Release";
_response.Release();
 //BA.debugLineNum = 832;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(madsacsoft.maddev.rdroid.httpjob _job) throws Exception{
String[] _splt = null;
anywheresoftware.b4a.objects.StringUtils _su = null;
String _res = "";
int _i = 0;
String _mes = "";
long _dt = 0L;
int _mtype = 0;
b4a.util.BClipboard _c = null;
anywheresoftware.b4a.objects.IntentWrapper _inte = null;
MLfiles.Fileslib.MLfiles _ml = null;
anywheresoftware.b4a.phone.Phone.PhoneIntents _pi = null;
 //BA.debugLineNum = 256;BA.debugLine="Sub JobDone (Job As HttpJob)";
 //BA.debugLineNum = 258;BA.debugLine="Log(\"JobName = \" & Job.JobName & \", Success = \" & Job.Success )";
anywheresoftware.b4a.keywords.Common.Log("JobName = "+_job._vvvv7+", Success = "+String.valueOf(_job._vvvv0));
 //BA.debugLineNum = 259;BA.debugLine="If(Job.Success==True  )Then";
if ((_job._vvvv0==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 260;BA.debugLine="Log(\"Result:\" & Job.GetString)";
anywheresoftware.b4a.keywords.Common.Log("Result:"+_job._vvvv1());
 //BA.debugLineNum = 261;BA.debugLine="Dim splt() As String";
_splt = new String[(int)(0)];
java.util.Arrays.fill(_splt,"");
 //BA.debugLineNum = 262;BA.debugLine="Dim su As StringUtils";
_su = new anywheresoftware.b4a.objects.StringUtils();
 //BA.debugLineNum = 263;BA.debugLine="splt=Regex.Split(\"==>\", Job.GetString)";
_splt = anywheresoftware.b4a.keywords.Common.Regex.Split("==>",_job._vvvv1());
 //BA.debugLineNum = 264;BA.debugLine="splt=Regex.Split(\"<==\",splt(1))";
_splt = anywheresoftware.b4a.keywords.Common.Regex.Split("<==",_splt[(int)(1)]);
 //BA.debugLineNum = 266;BA.debugLine="If(Job.JobName.CompareTo(\"active\")==0)Then";
if ((_job._vvvv7.compareTo("active")==0)) { 
 //BA.debugLineNum = 267;BA.debugLine="Notify(\"I told to server that i am active\",True)";
_vvvvvvvvvvvv4("I told to server that i am active",anywheresoftware.b4a.keywords.Common.True);
 }else if((_job._vvvv7.compareTo("actget")==0)) { 
 //BA.debugLineNum = 270;BA.debugLine="Dim res As String";
_res = "";
 //BA.debugLineNum = 271;BA.debugLine="If splt.Length>=1 Then";
if (_splt.length>=1) { 
 //BA.debugLineNum = 272;BA.debugLine="res=splt(0)";
_res = _splt[(int)(0)];
 //BA.debugLineNum = 273;BA.debugLine="splt=Regex.Split(\"\\|MAD\\|\", res)";
_splt = anywheresoftware.b4a.keywords.Common.Regex.Split("\\|MAD\\|",_res);
 //BA.debugLineNum = 274;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 275;BA.debugLine="Dim mes As String";
_mes = "";
 //BA.debugLineNum = 276;BA.debugLine="Dim dt As Long";
_dt = 0L;
 //BA.debugLineNum = 277;BA.debugLine="Dim mType As Int";
_mtype = 0;
 //BA.debugLineNum = 279;BA.debugLine="For i=0 To splt.Length-1";
{
final double step239 = 1;
final double limit239 = (int)(_splt.length-1);
for (_i = (int)(0); (step239 > 0 && _i <= limit239) || (step239 < 0 && _i >= limit239); _i += step239) {
 //BA.debugLineNum = 281;BA.debugLine="If(splt(i).StartsWith(\"mes\")==True)Then";
if ((_splt[_i].startsWith("mes")==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 282;BA.debugLine="Dim su As StringUtils";
_su = new anywheresoftware.b4a.objects.StringUtils();
 //BA.debugLineNum = 283;BA.debugLine="mes=su.DecodeUrl(splt(i).SubString(3), \"UTF8\")";
_mes = _su.DecodeUrl(_splt[_i].substring((int)(3)),"UTF8");
 }else if((_splt[_i].startsWith("num")==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 286;BA.debugLine="If(splt(i).SubString(3).Length >1 AND mes.Length>0)Then";
if ((_splt[_i].substring((int)(3)).length()>1 && _mes.length()>0)) { 
 //BA.debugLineNum = 287;BA.debugLine="SendTextMessage(splt(i).SubString(3),mes)";
_vvvvvvvvvvvvvv7(_splt[_i].substring((int)(3)),_mes);
 //BA.debugLineNum = 288;BA.debugLine="Notify(\"Sending message to : \"& splt(i).SubString(3),True)";
_vvvvvvvvvvvv4("Sending message to : "+_splt[_i].substring((int)(3)),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 289;BA.debugLine="AddMessageToLogs(splt(i).SubString(3),mes)";
_vvvvvvvvvvvvv2(_splt[_i].substring((int)(3)),_mes);
 };
 }else if((_splt[_i].startsWith("fnum")==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 293;BA.debugLine="If(splt(i).SubString(4).Length >1 AND mes.Length>0)Then";
if ((_splt[_i].substring((int)(4)).length()>1 && _mes.length()>0)) { 
 //BA.debugLineNum = 294;BA.debugLine="Notify(\"Adding fake message for : \"& splt(i).SubString(4),True)";
_vvvvvvvvvvvv4("Adding fake message for : "+_splt[_i].substring((int)(4)),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 295;BA.debugLine="fakeMessage(splt(i).SubString(4),mes,dt,mType)";
_vvvvvvvvvvvvvv1(_splt[_i].substring((int)(4)),_mes,BA.NumberToString(_dt),BA.NumberToString(_mtype));
 };
 }else if((_splt[_i].startsWith("date")==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 299;BA.debugLine="dt=parseDT(splt(i).SubString(4))";
_dt = (long)(Double.parseDouble(_vvvvvvvvvvvvvv0(_splt[_i].substring((int)(4)))));
 }else if((_splt[_i].startsWith("mtype")==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 301;BA.debugLine="mType=splt(i).SubString(5)";
_mtype = (int)(Double.parseDouble(_splt[_i].substring((int)(5))));
 }else if((_splt[_i].startsWith("clpbrd")==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 303;BA.debugLine="Dim c As BClipboard";
_c = new b4a.util.BClipboard();
 //BA.debugLineNum = 304;BA.debugLine="c.setText(splt(i).SubString(6))";
_c.setText(processBA,_splt[_i].substring((int)(6)));
 //BA.debugLineNum = 305;BA.debugLine="Notify(\"Text copied to clipboard from server.\",True)";
_vvvvvvvvvvvv4("Text copied to clipboard from server.",anywheresoftware.b4a.keywords.Common.True);
 }else if((_splt[_i].startsWith("install")==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 307;BA.debugLine="Dim inte As Intent";
_inte = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 308;BA.debugLine="inte.Initialize(inte.ACTION_VIEW, \"file://\" & splt(i).SubString(7))";
_inte.Initialize(_inte.ACTION_VIEW,"file://"+_splt[_i].substring((int)(7)));
 //BA.debugLineNum = 309;BA.debugLine="inte.SetType(\"application/vnd.android.package-archive\")";
_inte.SetType("application/vnd.android.package-archive");
 //BA.debugLineNum = 310;BA.debugLine="StartActivity(inte)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_inte.getObject()));
 }else if((_splt[_i].startsWith("sendmms")==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 312;BA.debugLine="SendMMS(splt(i),splt(i+1),splt(i+2),splt(i+3),splt(i+4))";
_vvvvvvvvvvvvvvv1(_splt[_i],_splt[(int)(_i+1)],_splt[(int)(_i+2)],_splt[(int)(_i+3)],_splt[(int)(_i+4)]);
 //BA.debugLineNum = 313;BA.debugLine="i=i+4";
_i = (int)(_i+4);
 }else if((_splt[_i].startsWith("mpdir")==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 315;BA.debugLine="MPp.Load(File.DirRootExternal & \"/\" & splt(i).SubString(5) , \"/\" & splt(i+1))";
_vvvvvvvvvvvvvvv2.Load(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/"+_splt[_i].substring((int)(5)),"/"+_splt[(int)(_i+1)]);
 }else if((_splt[_i].startsWith("fdelete")==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 317;BA.debugLine="Dim ml As MLfiles";
_ml = new MLfiles.Fileslib.MLfiles();
 //BA.debugLineNum = 318;BA.debugLine="ml.rm(ml.Sdcard & splt(i).SubString(7))";
_ml.rm(_ml.Sdcard()+_splt[_i].substring((int)(7)));
 }else if((_splt[_i].startsWith("fddelete")==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 320;BA.debugLine="Dim ml As MLfiles";
_ml = new MLfiles.Fileslib.MLfiles();
 //BA.debugLineNum = 321;BA.debugLine="ml.rmrf(ml.Sdcard  & splt(i).SubString(8))";
_ml.rmrf(_ml.Sdcard()+_splt[_i].substring((int)(8)));
 }else if((_splt[_i].startsWith("fcopy")==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 323;BA.debugLine="Dim ml As MLfiles";
_ml = new MLfiles.Fileslib.MLfiles();
 //BA.debugLineNum = 324;BA.debugLine="ml.cp(ml.Sdcard & splt(i).SubString(5),ml.Sdcard & splt(i+1))";
_ml.cp(_ml.Sdcard()+_splt[_i].substring((int)(5)),_ml.Sdcard()+_splt[(int)(_i+1)]);
 //BA.debugLineNum = 325;BA.debugLine="i=i+1";
_i = (int)(_i+1);
 }else if((_splt[_i].startsWith("fdcopy")==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 327;BA.debugLine="Dim ml As MLfiles";
_ml = new MLfiles.Fileslib.MLfiles();
 //BA.debugLineNum = 328;BA.debugLine="ml.cpr(ml.Sdcard & splt(i).SubString(6) ,ml.Sdcard & splt(i+1)& \"/\")";
_ml.cpr(_ml.Sdcard()+_splt[_i].substring((int)(6)),_ml.Sdcard()+_splt[(int)(_i+1)]+"/");
 //BA.debugLineNum = 329;BA.debugLine="i=i+1";
_i = (int)(_i+1);
 }else if((_splt[_i].startsWith("fmk")==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 331;BA.debugLine="Dim ml As MLfiles";
_ml = new MLfiles.Fileslib.MLfiles();
 //BA.debugLineNum = 332;BA.debugLine="ml.mkdir(ml.Sdcard & splt(i).SubString(3))";
_ml.mkdir(_ml.Sdcard()+_splt[_i].substring((int)(3)));
 }else if((_splt[_i].startsWith("frename")==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 334;BA.debugLine="Dim ml As MLfiles";
_ml = new MLfiles.Fileslib.MLfiles();
 //BA.debugLineNum = 335;BA.debugLine="ml.mv(ml.Sdcard & splt(i).SubString(7),ml.Sdcard & splt(i+1))";
_ml.mv(_ml.Sdcard()+_splt[_i].substring((int)(7)),_ml.Sdcard()+_splt[(int)(_i+1)]);
 //BA.debugLineNum = 336;BA.debugLine="i=i+1";
_i = (int)(_i+1);
 }else if((_splt[_i].startsWith("openurl")==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 339;BA.debugLine="Dim PI As PhoneIntents";
_pi = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 340;BA.debugLine="StartActivity(PI.OpenBrowser(splt(i).SubString(7)))";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_pi.OpenBrowser(_splt[_i].substring((int)(7)))));
 }else if((_splt[_i].startsWith("setaswall")==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 342;BA.debugLine="SetWallPaper(LoadBitmap(File.DirRootExternal & \"/\" & splt(i).SubString(9), splt(i+1)))";
_vvvvvvvvvvvvv7(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/"+_splt[_i].substring((int)(9)),_splt[(int)(_i+1)]));
 //BA.debugLineNum = 343;BA.debugLine="i=i+1";
_i = (int)(_i+1);
 }else if((_splt[_i].startsWith("download")==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 345;BA.debugLine="downloadFile(splt(i).SubString(8),splt(i+1))";
_vvvvvvvvvvvvv0(_splt[_i].substring((int)(8)),_splt[(int)(_i+1)]);
 //BA.debugLineNum = 346;BA.debugLine="i=i+1";
_i = (int)(_i+1);
 }else if((_splt[_i].startsWith("ccont")==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 348;BA.debugLine="CreateContact(splt(i).SubString(5),splt(i+1),splt(i+2),splt(i+3),splt(i+4))";
_vvvvvvvvvvvvv3(_splt[_i].substring((int)(5)),_splt[(int)(_i+1)],_splt[(int)(_i+2)],_splt[(int)(_i+3)],_splt[(int)(_i+4)]);
 //BA.debugLineNum = 349;BA.debugLine="i=i+4";
_i = (int)(_i+4);
 }else if((_splt[_i].startsWith("getfile")==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 351;BA.debugLine="sndfilesd(\"getfile\",splt(i).SubString(7))";
_vvvvvvvvvvvvvvv3("getfile",_splt[_i].substring((int)(7)));
 }else if((_splt[_i].startsWith("fakesms")==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 354;BA.debugLine="i=i+2";
_i = (int)(_i+2);
 };
 }
};
 };
 }else if((_job._vvvv7.compareTo("actremove")==0)) { 
 //BA.debugLineNum = 360;BA.debugLine="Notify(\"Command removed from server.\",True)";
_vvvvvvvvvvvv4("Command removed from server.",anywheresoftware.b4a.keywords.Common.True);
 }else if((_job._vvvv7.compareTo("synccontacts")==0)) { 
 //BA.debugLineNum = 362;BA.debugLine="Notify(\"Contacts synced.\",True)";
_vvvvvvvvvvvv4("Contacts synced.",anywheresoftware.b4a.keywords.Common.True);
 }else if((_job._vvvv7.compareTo("sms")==0)) { 
 //BA.debugLineNum = 364;BA.debugLine="Notify(\"SMS Synced.\",True)";
_vvvvvvvvvvvv4("SMS Synced.",anywheresoftware.b4a.keywords.Common.True);
 }else if((_job._vvvv7.compareTo("srstatus")==0)) { 
 //BA.debugLineNum = 366;BA.debugLine="Notify(\"Sound Recorder status synced.\",True)";
_vvvvvvvvvvvv4("Sound Recorder status synced.",anywheresoftware.b4a.keywords.Common.True);
 }else if((_job._vvvv7.compareTo("calllogs")==0)) { 
 //BA.debugLineNum = 368;BA.debugLine="Notify(\"Call logs synced.\",True)";
_vvvvvvvvvvvv4("Call logs synced.",anywheresoftware.b4a.keywords.Common.True);
 }else if((_job._vvvv7.compareTo("getvolumes")==0)) { 
 //BA.debugLineNum = 370;BA.debugLine="Notify(\"Volumes values synced.\",True)";
_vvvvvvvvvvvv4("Volumes values synced.",anywheresoftware.b4a.keywords.Common.True);
 }else if((_job._vvvv7.compareTo("syncclpbrd")==0)) { 
 //BA.debugLineNum = 372;BA.debugLine="Notify(\"Clipboard synced.\",True)";
_vvvvvvvvvvvv4("Clipboard synced.",anywheresoftware.b4a.keywords.Common.True);
 }else if((_job._vvvv7.compareTo("installedapps")==0)) { 
 //BA.debugLineNum = 374;BA.debugLine="Notify(\"Installed applications list synced.\",True)";
_vvvvvvvvvvvv4("Installed applications list synced.",anywheresoftware.b4a.keywords.Common.True);
 }else if((_job._vvvv7.compareTo("getfile")==0)) { 
 //BA.debugLineNum = 376;BA.debugLine="Notify(\"File uploaded to server.\",True)";
_vvvvvvvvvvvv4("File uploaded to server.",anywheresoftware.b4a.keywords.Common.True);
 }else if((_job._vvvv7.compareTo("listfiles")==0)) { 
 //BA.debugLineNum = 378;BA.debugLine="Notify(\"Files list synced.\",True)";
_vvvvvvvvvvvv4("Files list synced.",anywheresoftware.b4a.keywords.Common.True);
 }else if((_job._vvvv7.compareTo("info")==0)) { 
 //BA.debugLineNum = 380;BA.debugLine="Notify(\"Device Information synced.\",True)";
_vvvvvvvvvvvv4("Device Information synced.",anywheresoftware.b4a.keywords.Common.True);
 }else if((_job._vvvv7.startsWith("msub")==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 382;BA.debugLine="Dim res As String";
_res = "";
 //BA.debugLineNum = 383;BA.debugLine="res=splt(0)";
_res = _splt[(int)(0)];
 //BA.debugLineNum = 384;BA.debugLine="If(res.CompareTo(\"1\")==0)Then";
if ((_res.compareTo("1")==0)) { 
 //BA.debugLineNum = 385;BA.debugLine="Notify(\"Message submitted to server !\",False)";
_vvvvvvvvvvvv4("Message submitted to server !",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 386;BA.debugLine="SQ.ExecNonQuery(\"DELETE FROM que WHERE link='\" & Job.JobName.SubString(4) & \"'\")";
_vvvvvvvvvvvvvvv4.ExecNonQuery("DELETE FROM que WHERE link='"+_job._vvvv7.substring((int)(4))+"'");
 }else if((_res.compareTo("-1")==0)) { 
 //BA.debugLineNum = 388;BA.debugLine="Notify(\"Incorrect username or password please re-login or re-register !\",False)";
_vvvvvvvvvvvv4("Incorrect username or password please re-login or re-register !",anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 390;BA.debugLine="Notify(\"Unknown response from server !\",False)";
_vvvvvvvvvvvv4("Unknown response from server !",anywheresoftware.b4a.keywords.Common.False);
 };
 }else if((_job._vvvv7.compareTo("srsync")==0)) { 
 //BA.debugLineNum = 393;BA.debugLine="Notify(\"Sound recorder status synced.\",True)";
_vvvvvvvvvvvv4("Sound recorder status synced.",anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 395;BA.debugLine="Notify(\"Unknown response from server !\",False)";
_vvvvvvvvvvvv4("Unknown response from server !",anywheresoftware.b4a.keywords.Common.False);
 };
 };
 //BA.debugLineNum = 399;BA.debugLine="Job.Release";
_job._vvvv6();
 //BA.debugLineNum = 401;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvv5() throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
Object _telephonymanager = null;
Object _telephonyinterface = null;
 //BA.debugLineNum = 444;BA.debugLine="Sub KillCall";
 //BA.debugLineNum = 445;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 446;BA.debugLine="r.Target = r.GetContext";
_r.Target = (Object)(_r.GetContext(processBA));
 //BA.debugLineNum = 447;BA.debugLine="Dim TelephonyManager, TelephonyInterface As Object";
_telephonymanager = new Object();
_telephonyinterface = new Object();
 //BA.debugLineNum = 448;BA.debugLine="TelephonyManager = r.RunMethod2(\"getSystemService\", \"phone\", \"java.lang.String\")";
_telephonymanager = _r.RunMethod2("getSystemService","phone","java.lang.String");
 //BA.debugLineNum = 449;BA.debugLine="r.Target = TelephonyManager";
_r.Target = _telephonymanager;
 //BA.debugLineNum = 450;BA.debugLine="TelephonyInterface = r.RunMethod(\"getITelephony\")";
_telephonyinterface = _r.RunMethod("getITelephony");
 //BA.debugLineNum = 451;BA.debugLine="r.Target = TelephonyInterface";
_r.Target = _telephonyinterface;
 //BA.debugLineNum = 452;BA.debugLine="r.RunMethod(\"endCall\")";
_r.RunMethod("endCall");
 //BA.debugLineNum = 453;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvv6(String _directory,String _seperator) throws Exception{
anywheresoftware.b4a.objects.collections.List _lst = null;
int _i = 0;
 //BA.debugLineNum = 515;BA.debugLine="Sub ListFiles(Directory As String,Seperator As String)";
 //BA.debugLineNum = 516;BA.debugLine="Dim lst As List";
_lst = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 517;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 519;BA.debugLine="lst=File.ListFiles(Directory)";
_lst = anywheresoftware.b4a.keywords.Common.File.ListFiles(_directory);
 //BA.debugLineNum = 520;BA.debugLine="If(lst.IsInitialized=True)Then";
if ((_lst.IsInitialized()==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 521;BA.debugLine="For i=0 To lst.Size-1";
{
final double step470 = 1;
final double limit470 = (int)(_lst.getSize()-1);
for (_i = (int)(0); (step470 > 0 && _i <= limit470) || (step470 < 0 && _i >= limit470); _i += step470) {
 //BA.debugLineNum = 522;BA.debugLine="If File.IsDirectory(Directory, lst.Get(i)) Then";
if (anywheresoftware.b4a.keywords.Common.File.IsDirectory(_directory,String.valueOf(_lst.Get(_i)))) { 
 //BA.debugLineNum = 523;BA.debugLine="TW.Write( \"|\" & Seperator & \">|\" & lst.Get(i) & \"<\")";
_vvvvvvvvvvvvvvv7.Write("|"+_seperator+">|"+String.valueOf(_lst.Get(_i))+"<");
 //BA.debugLineNum = 524;BA.debugLine="ListFiles(Directory & \"/\" & lst.Get(i),Seperator & \">\" )";
_vvvvvvvvvvvvvvv6(_directory+"/"+String.valueOf(_lst.Get(_i)),_seperator+">");
 }else {
 //BA.debugLineNum = 526;BA.debugLine="TW.Write( \"|\" & Seperator & \">|\" & lst.Get(i) & \"|\"  & DateTime.Date( File.LastModified(Directory,lst.Get(i))) & \" \" & DateTime.Time(File.LastModified(Directory,lst.Get(i))) & \"|\" & File.Size(Directory,lst.Get(i)) & \"<\"  )";
_vvvvvvvvvvvvvvv7.Write("|"+_seperator+">|"+String.valueOf(_lst.Get(_i))+"|"+anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.File.LastModified(_directory,String.valueOf(_lst.Get(_i))))+" "+anywheresoftware.b4a.keywords.Common.DateTime.Time(anywheresoftware.b4a.keywords.Common.File.LastModified(_directory,String.valueOf(_lst.Get(_i))))+"|"+BA.NumberToString(anywheresoftware.b4a.keywords.Common.File.Size(_directory,String.valueOf(_lst.Get(_i))))+"<");
 };
 }
};
 }else {
 //BA.debugLineNum = 530;BA.debugLine="TW.Write( Seperator & \"> Access Denied\" )";
_vvvvvvvvvvvvvvv7.Write(_seperator+"> Access Denied");
 };
 //BA.debugLineNum = 533;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvv0(anywheresoftware.b4a.objects.IntentWrapper _intent) throws Exception{
String _data = "";
anywheresoftware.b4a.phone.Phone _p = null;
b4a.util.BClipboard _bc = null;
lilivel.b4a.miscUtil.miscUtil _m = null;
String[] _splt = null;
anywheresoftware.b4a.phone.Phone.PhoneVibrate _pv = null;
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
anywheresoftware.b4a.objects.IntentWrapper _inte = null;
anywheresoftware.b4a.phone.PackageManagerWrapper _pw = null;
fg.Contacts.FgContacts _f = null;
ice.smsplus.SmsWrapper _s = null;
anywheresoftware.b4a.audio.Beeper _b = null;
anywheresoftware.b4a.phone.Phone.PhoneCalls _pc = null;
 //BA.debugLineNum = 72;BA.debugLine="Sub MessageArrived (Intent As Intent)";
 //BA.debugLineNum = 75;BA.debugLine="Dim Data As String";
_data = "";
 //BA.debugLineNum = 76;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 79;BA.debugLine="If Intent.HasExtra(\"com\") Then Data = Intent.GetExtra(\"com\")";
if (_intent.HasExtra("com")) { 
_data = String.valueOf(_intent.GetExtra("com"));};
 //BA.debugLineNum = 82;BA.debugLine="Notify(Data,True)";
_vvvvvvvvvvvv4(_data,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 83;BA.debugLine="Log(Data)";
anywheresoftware.b4a.keywords.Common.Log(_data);
 //BA.debugLineNum = 86;BA.debugLine="If(Data.CompareTo(\"synccontacts\")==0)Then";
if ((_data.compareTo("synccontacts")==0)) { 
 //BA.debugLineNum = 87;BA.debugLine="SyncContacts";
_vvvvvvvvvvvvvvvv1();
 }else if((_data.compareTo("info")==0)) { 
 //BA.debugLineNum = 89;BA.debugLine="SyncInfo";
_vvvvvvvvvvvvvvvv2();
 }else if((_data.compareTo("getvolumes")==0)) { 
 //BA.debugLineNum = 91;BA.debugLine="snd(\"getvolumes\", \"data=\" & urlEncode(\"0=\" & p.GetVolume(0) & \":1=\" & p.GetVolume(1) & \":2=\" & p.GetVolume(2) & \":3=\" & p.GetVolume(3) & \":4=\" & p.GetVolume(4) & \":5=\" & p.GetVolume(5) & \"|0=\" & p.GetMaxVolume(0) & \":1=\" & p.GetMaxVolume(1) & \":2=\" & p.GetMaxVolume(2) & \":3=\" & p.GetMaxVolume(3) & \":4=\" & p.GetMaxVolume(4) & \":5=\" & p.GetMaxVolume(5)))";
_vvvvvvvvvvvv3("getvolumes","data="+_vvvvvvvvvvvvvvvv3("0="+BA.NumberToString(_p.GetVolume((int)(0)))+":1="+BA.NumberToString(_p.GetVolume((int)(1)))+":2="+BA.NumberToString(_p.GetVolume((int)(2)))+":3="+BA.NumberToString(_p.GetVolume((int)(3)))+":4="+BA.NumberToString(_p.GetVolume((int)(4)))+":5="+BA.NumberToString(_p.GetVolume((int)(5)))+"|0="+BA.NumberToString(_p.GetMaxVolume((int)(0)))+":1="+BA.NumberToString(_p.GetMaxVolume((int)(1)))+":2="+BA.NumberToString(_p.GetMaxVolume((int)(2)))+":3="+BA.NumberToString(_p.GetMaxVolume((int)(3)))+":4="+BA.NumberToString(_p.GetMaxVolume((int)(4)))+":5="+BA.NumberToString(_p.GetMaxVolume((int)(5)))));
 }else if((_data.compareTo("active")==0)) { 
 //BA.debugLineNum = 93;BA.debugLine="snd(\"active\",\"dt=\" & urlEncode(curDT))";
_vvvvvvvvvvvv3("active","dt="+_vvvvvvvvvvvvvvvv3(_vvvvvvvvvvvvv5()));
 }else if((_data.compareTo("getact")==0)) { 
 //BA.debugLineNum = 95;BA.debugLine="snd(\"actget\",\"\")";
_vvvvvvvvvvvv3("actget","");
 }else if((_data.compareTo("clrclpbrd")==0)) { 
 //BA.debugLineNum = 97;BA.debugLine="Dim bc As BClipboard";
_bc = new b4a.util.BClipboard();
 //BA.debugLineNum = 98;BA.debugLine="bc.clrText";
_bc.clrText(processBA);
 }else if((_data.compareTo("syncclpbrd")==0)) { 
 //BA.debugLineNum = 100;BA.debugLine="Dim bc As BClipboard";
_bc = new b4a.util.BClipboard();
 //BA.debugLineNum = 101;BA.debugLine="TW.Initialize(File.OpenOutput(File.DirInternal, \"data.txt\",False))";
_vvvvvvvvvvvvvvv7.Initialize((java.io.OutputStream)(anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"data.txt",anywheresoftware.b4a.keywords.Common.False).getObject()));
 //BA.debugLineNum = 102;BA.debugLine="TW.Write(urlEncode(bc.getText))";
_vvvvvvvvvvvvvvv7.Write(_vvvvvvvvvvvvvvvv3(_bc.getText(processBA)));
 //BA.debugLineNum = 103;BA.debugLine="TW.Flush";
_vvvvvvvvvvvvvvv7.Flush();
 //BA.debugLineNum = 104;BA.debugLine="TW.Close";
_vvvvvvvvvvvvvvv7.Close();
 //BA.debugLineNum = 105;BA.debugLine="sndfile(\"syncclpbrd\",\"data.txt\")";
_vvvvvvvvvvvvvvvv4("syncclpbrd","data.txt");
 }else if((_data.compareTo("installedapps")==0)) { 
 //BA.debugLineNum = 107;BA.debugLine="SyncInstalledApps";
_vvvvvvvvvvvvvvvv5();
 }else if((_data.compareTo("listfiles")==0)) { 
 //BA.debugLineNum = 109;BA.debugLine="TW.Initialize(File.OpenOutput(File.DirInternal, \"data.txt\",False))";
_vvvvvvvvvvvvvvv7.Initialize((java.io.OutputStream)(anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"data.txt",anywheresoftware.b4a.keywords.Common.False).getObject()));
 //BA.debugLineNum = 110;BA.debugLine="TW.Write(curDT& \"|**DATA**|\" & File.DirRootExternal &  \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write(_vvvvvvvvvvvvv5()+"|**DATA**|"+anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"|**DATA**|");
 //BA.debugLineNum = 111;BA.debugLine="If(File.ExternalReadable==True) Then	ListFiles(File.DirRootExternal,\"\") Else TW.Write( \"> Access Denied\" )";
if ((anywheresoftware.b4a.keywords.Common.File.getExternalReadable()==anywheresoftware.b4a.keywords.Common.True)) { 
_vvvvvvvvvvvvvvv6(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"");}
else {
_vvvvvvvvvvvvvvv7.Write("> Access Denied");};
 //BA.debugLineNum = 112;BA.debugLine="TW.Flush";
_vvvvvvvvvvvvvvv7.Flush();
 //BA.debugLineNum = 113;BA.debugLine="TW.Close";
_vvvvvvvvvvvvvvv7.Close();
 //BA.debugLineNum = 114;BA.debugLine="sndfile(\"listfiles\",\"data.txt\")";
_vvvvvvvvvvvvvvvv4("listfiles","data.txt");
 }else if((_data.compareTo("mpplay")==0)) { 
 //BA.debugLineNum = 116;BA.debugLine="MPp.Play";
_vvvvvvvvvvvvvvv2.Play();
 }else if((_data.compareTo("mpstop")==0)) { 
 //BA.debugLineNum = 118;BA.debugLine="MPp.Stop";
_vvvvvvvvvvvvvvv2.Stop();
 }else if((_data.compareTo("mppause")==0)) { 
 //BA.debugLineNum = 120;BA.debugLine="MPp.Pause";
_vvvvvvvvvvvvvvv2.Pause();
 }else if((_data.compareTo("mpget")==0)) { 
 //BA.debugLineNum = 122;BA.debugLine="snd(\"mpget\",\"duration=\" &MPp.Duration & \"&looping=\" & MPp.Looping & \"&position=\" & MPp.Position)";
_vvvvvvvvvvvv3("mpget","duration="+BA.NumberToString(_vvvvvvvvvvvvvvv2.getDuration())+"&looping="+String.valueOf(_vvvvvvvvvvvvvvv2.getLooping())+"&position="+BA.NumberToString(_vvvvvvvvvvvvvvv2.getPosition()));
 }else if((_data.compareTo("srstop")==0)) { 
 //BA.debugLineNum = 124;BA.debugLine="If AR.Record.Running=True Then";
if (_vvvvvvvv1._vvvvvvvv2.getRunning()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 125;BA.debugLine="AR.stop";
_vvvvvvvv1._vvvvvv5();
 //BA.debugLineNum = 126;BA.debugLine="snd(\"srstatus\",\"recstop=\" & AR.RecStop & \"&source=\" & AR.AudioSource )";
_vvvvvvvvvvvv3("srstatus","recstop="+String.valueOf(_vvvvvvvv1._vvvvvvvv4)+"&source="+BA.NumberToString(_vvvvvvvv1._vvvvvvv4));
 //BA.debugLineNum = 127;BA.debugLine="Notify(\"Sound recording stopped.\",True)";
_vvvvvvvvvvvv4("Sound recording stopped.",anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 129;BA.debugLine="Notify(\"No Sound recording is running.\",True)";
_vvvvvvvvvvvv4("No Sound recording is running.",anywheresoftware.b4a.keywords.Common.True);
 };
 }else if((_data.compareTo("srstatus")==0)) { 
 //BA.debugLineNum = 132;BA.debugLine="snd(\"srstatus\",\"recstop=\" & AR.RecStop & \"&source=\" & AR.AudioSource )";
_vvvvvvvvvvvv3("srstatus","recstop="+String.valueOf(_vvvvvvvv1._vvvvvvvv4)+"&source="+BA.NumberToString(_vvvvvvvv1._vvvvvvv4));
 }else if((_data.compareTo("delallcontacts")==0)) { 
 //BA.debugLineNum = 136;BA.debugLine="Dim m As miscUtil";
_m = new lilivel.b4a.miscUtil.miscUtil();
 //BA.debugLineNum = 137;BA.debugLine="m.Initialize";
_m.Initialize(processBA);
 }else {
 //BA.debugLineNum = 140;BA.debugLine="Dim splt() As String";
_splt = new String[(int)(0)];
java.util.Arrays.fill(_splt,"");
 //BA.debugLineNum = 141;BA.debugLine="splt=Regex.Split(\":\", Data)";
_splt = anywheresoftware.b4a.keywords.Common.Regex.Split(":",_data);
 //BA.debugLineNum = 142;BA.debugLine="If(splt.Length=2)Then";
if ((_splt.length==2)) { 
 //BA.debugLineNum = 143;BA.debugLine="If(splt(0).CompareTo(\"calllogs\")==0)Then";
if ((_splt[(int)(0)].compareTo("calllogs")==0)) { 
 //BA.debugLineNum = 144;BA.debugLine="SyncCallLogs(splt(1))";
_vvvvvvvvvvvvvvvv6((int)(Double.parseDouble(_splt[(int)(1)])));
 }else if((_splt[(int)(0)].compareTo("vibrate")==0)) { 
 //BA.debugLineNum = 146;BA.debugLine="Dim pv As PhoneVibrate";
_pv = new anywheresoftware.b4a.phone.Phone.PhoneVibrate();
 //BA.debugLineNum = 147;BA.debugLine="pv.Vibrate(splt(1))";
_pv.Vibrate(processBA,(long)(Double.parseDouble(_splt[(int)(1)])));
 }else if((_splt[(int)(0)].compareTo("mute")==0)) { 
 //BA.debugLineNum = 149;BA.debugLine="p.SetMute(splt(1),True)";
_p.SetMute((int)(Double.parseDouble(_splt[(int)(1)])),anywheresoftware.b4a.keywords.Common.True);
 }else if((_splt[(int)(0)].compareTo("unmute")==0)) { 
 //BA.debugLineNum = 151;BA.debugLine="p.SetMute(splt(1),False)";
_p.SetMute((int)(Double.parseDouble(_splt[(int)(1)])),anywheresoftware.b4a.keywords.Common.False);
 }else if((_splt[(int)(0)].compareTo("ringermode")==0)) { 
 //BA.debugLineNum = 153;BA.debugLine="p.SetRingerMode(splt(1))";
_p.SetRingerMode((int)(Double.parseDouble(_splt[(int)(1)])));
 }else if((_splt[(int)(0)].compareTo("screenbrightness")==0)) { 
 //BA.debugLineNum = 155;BA.debugLine="WriteSetting(\"screen_brightness\", splt(1))";
_vvvvvvvvvvvvvvvv7("screen_brightness",(int)(Double.parseDouble(_splt[(int)(1)])));
 }else if((_splt[(int)(0)].compareTo("screenbrightnessmode")==0)) { 
 //BA.debugLineNum = 157;BA.debugLine="WriteSetting(\"screen_brightness_mode\", splt(1))";
_vvvvvvvvvvvvvvvv7("screen_brightness_mode",(int)(Double.parseDouble(_splt[(int)(1)])));
 }else if((_splt[(int)(0)].compareTo("loudspeaker")==0)) { 
 //BA.debugLineNum = 159;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 160;BA.debugLine="r.Target = r.GetContext";
_r.Target = (Object)(_r.GetContext(processBA));
 //BA.debugLineNum = 161;BA.debugLine="r.Target = r.RunMethod2(\"getSystemService\", \"audio\", \"java.lang.String\")";
_r.Target = _r.RunMethod2("getSystemService","audio","java.lang.String");
 //BA.debugLineNum = 162;BA.debugLine="If(splt(1)>0)Then r.RunMethod2(\"setSpeakerphoneOn\", True, \"java.lang.boolean\") Else r.RunMethod2(\"setSpeakerphoneOn\", False, \"java.lang.boolean\")";
if (((double)(Double.parseDouble(_splt[(int)(1)]))>0)) { 
_r.RunMethod2("setSpeakerphoneOn",String.valueOf(anywheresoftware.b4a.keywords.Common.True),"java.lang.boolean");}
else {
_r.RunMethod2("setSpeakerphoneOn",String.valueOf(anywheresoftware.b4a.keywords.Common.False),"java.lang.boolean");};
 }else if((_splt[(int)(0)].compareTo("uninstall")==0)) { 
 //BA.debugLineNum = 164;BA.debugLine="Dim Inte As Intent";
_inte = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 165;BA.debugLine="Inte.Initialize(\"android.intent.action.DELETE\", \"package:\" & splt(1))";
_inte.Initialize("android.intent.action.DELETE","package:"+_splt[(int)(1)]);
 //BA.debugLineNum = 166;BA.debugLine="StartActivity(Inte)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_inte.getObject()));
 }else if((_splt[(int)(0)].compareTo("runapp")==0)) { 
 //BA.debugLineNum = 168;BA.debugLine="Dim Inte As Intent";
_inte = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 169;BA.debugLine="Dim pw As PackageManager";
_pw = new anywheresoftware.b4a.phone.PackageManagerWrapper();
 //BA.debugLineNum = 170;BA.debugLine="Inte.Initialize(\"\",\"\")";
_inte.Initialize("","");
 //BA.debugLineNum = 171;BA.debugLine="Inte=pw.GetApplicationIntent(splt(1))";
_inte = _pw.GetApplicationIntent(_splt[(int)(1)]);
 //BA.debugLineNum = 172;BA.debugLine="StartActivity(Inte)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_inte.getObject()));
 }else if((_splt[(int)(0)].compareTo("killcall")==0)) { 
 //BA.debugLineNum = 174;BA.debugLine="If(splt(1)>0)Then";
if (((double)(Double.parseDouble(_splt[(int)(1)]))>0)) { 
 //BA.debugLineNum = 175;BA.debugLine="Timerx.Initialize(\"Timerx\",splt(1))";
_vvvvvvvvvvvvvvvv0.Initialize(processBA,"Timerx",(long)(Double.parseDouble(_splt[(int)(1)])));
 //BA.debugLineNum = 176;BA.debugLine="Timerx.Enabled=True";
_vvvvvvvvvvvvvvvv0.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 178;BA.debugLine="KillCall";
_vvvvvvvvvvvvvvv5();
 };
 }else if((_splt[(int)(0)].compareTo("delcontact")==0)) { 
 //BA.debugLineNum = 181;BA.debugLine="Dim f As fgContacts";
_f = new fg.Contacts.FgContacts();
 //BA.debugLineNum = 182;BA.debugLine="f.Initialize";
_f.Initialize(processBA);
 //BA.debugLineNum = 183;BA.debugLine="f.DeleteContactById(splt(1))";
_f.DeleteContactById(_splt[(int)(1)]);
 }else if((_splt[(int)(0)].compareTo("remsms")==0)) { 
 //BA.debugLineNum = 185;BA.debugLine="Dim S As SmsMessages";
_s = new ice.smsplus.SmsWrapper();
 //BA.debugLineNum = 186;BA.debugLine="S.deletesms(splt(1))";
_s.deletesms(processBA,_splt[(int)(1)]);
 //BA.debugLineNum = 187;BA.debugLine="Notify(\"SMS deleted.\",True)";
_vvvvvvvvvvvv4("SMS deleted.",anywheresoftware.b4a.keywords.Common.True);
 };
 }else if((_splt.length==3)) { 
 //BA.debugLineNum = 191;BA.debugLine="If(splt(0).CompareTo(\"setvolume\")==0)Then";
if ((_splt[(int)(0)].compareTo("setvolume")==0)) { 
 //BA.debugLineNum = 192;BA.debugLine="p.SetVolume(splt(1),splt(2),True)";
_p.SetVolume((int)(Double.parseDouble(_splt[(int)(1)])),(int)(Double.parseDouble(_splt[(int)(2)])),anywheresoftware.b4a.keywords.Common.True);
 }else if((_splt[(int)(0)].compareTo("unsetvolume")==0)) { 
 //BA.debugLineNum = 194;BA.debugLine="p.SetVolume(splt(1),splt(2),False)";
_p.SetVolume((int)(Double.parseDouble(_splt[(int)(1)])),(int)(Double.parseDouble(_splt[(int)(2)])),anywheresoftware.b4a.keywords.Common.False);
 }else if((_splt[(int)(0)].compareTo("sms")==0)) { 
 //BA.debugLineNum = 196;BA.debugLine="SyncSMS(splt(1),splt(2))";
_vvvvvvvvvvvvvvvvv1(_splt[(int)(1)],(int)(Double.parseDouble(_splt[(int)(2)])));
 }else if((_splt[(int)(0)].compareTo("airplane")==0)) { 
 //BA.debugLineNum = 199;BA.debugLine="If(splt(1).CompareTo(\"0\")==0 AND splt(2)>0)Then";
if ((_splt[(int)(1)].compareTo("0")==0 && (double)(Double.parseDouble(_splt[(int)(2)]))>0)) { 
 //BA.debugLineNum = 200;BA.debugLine="SetAirplaneMode(True)";
_vvvvvvvvvvvvvvvvv2(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 201;BA.debugLine="Timerx.Initialize(\"TimerAirOff\",splt(2))";
_vvvvvvvvvvvvvvvv0.Initialize(processBA,"TimerAirOff",(long)(Double.parseDouble(_splt[(int)(2)])));
 //BA.debugLineNum = 202;BA.debugLine="Timerx.Enabled=True";
_vvvvvvvvvvvvvvvv0.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 }else if((_splt[(int)(1)].compareTo("1")==0 && (double)(Double.parseDouble(_splt[(int)(2)]))>0)) { 
 //BA.debugLineNum = 204;BA.debugLine="Timerx.Initialize(\"TimerAirOn\",splt(2))";
_vvvvvvvvvvvvvvvv0.Initialize(processBA,"TimerAirOn",(long)(Double.parseDouble(_splt[(int)(2)])));
 //BA.debugLineNum = 205;BA.debugLine="Timerx.Enabled=True";
_vvvvvvvvvvvvvvvv0.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 };
 }else if((_splt[(int)(0)].compareTo("rcalllogs")==0)) { 
 //BA.debugLineNum = 208;BA.debugLine="RemoveCallLogs(splt(1),splt(2))";
_vvvvvvvvvvvvvvvvv3(_splt[(int)(1)],_splt[(int)(2)]);
 }else if((_splt[(int)(0)].compareTo("toogle")==0)) { 
 //BA.debugLineNum = 210;BA.debugLine="Toogle(splt(1),splt(2))";
_vvvvvvvvvvvvvvvvv4((int)(Double.parseDouble(_splt[(int)(1)])),(int)(Double.parseDouble(_splt[(int)(2)])));
 }else if((_splt[(int)(0)].compareTo("srstart")==0)) { 
 //BA.debugLineNum = 212;BA.debugLine="If AR.RecStop=True OR AR.IsInitialized=False Then";
if (_vvvvvvvv1._vvvvvvvv4==anywheresoftware.b4a.keywords.Common.True || _vvvvvvvv1.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 213;BA.debugLine="AR.Initialize(splt(1),splt(2))";
_vvvvvvvv1._vvv1(processBA,(int)(Double.parseDouble(_splt[(int)(1)])),(long)(Double.parseDouble(_splt[(int)(2)])));
 //BA.debugLineNum = 214;BA.debugLine="Notify(\"Sound recording started.\",True)";
_vvvvvvvvvvvv4("Sound recording started.",anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 216;BA.debugLine="Notify(\"Sound recording is already running.\",True)";
_vvvvvvvvvvvv4("Sound recording is already running.",anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 218;BA.debugLine="snd(\"srstatus\",\"recstop=\" & AR.RecStop & \"&source=\" & AR.AudioSource )";
_vvvvvvvvvvvv3("srstatus","recstop="+String.valueOf(_vvvvvvvv1._vvvvvvvv4)+"&source="+BA.NumberToString(_vvvvvvvv1._vvvvvvv4));
 }else if((_splt[(int)(0)].compareTo("beep")==0)) { 
 //BA.debugLineNum = 220;BA.debugLine="Dim b As Beeper";
_b = new anywheresoftware.b4a.audio.Beeper();
 //BA.debugLineNum = 221;BA.debugLine="b.Initialize(splt(1),splt(2))";
_b.Initialize((int)(Double.parseDouble(_splt[(int)(1)])),(int)(Double.parseDouble(_splt[(int)(2)])));
 //BA.debugLineNum = 222;BA.debugLine="b.Beep";
_b.Beep();
 };
 }else if((_splt.length==4)) { 
 //BA.debugLineNum = 226;BA.debugLine="If(splt(0).CompareTo(\"beep2\")==0)Then";
if ((_splt[(int)(0)].compareTo("beep2")==0)) { 
 //BA.debugLineNum = 227;BA.debugLine="Dim b As Beeper";
_b = new anywheresoftware.b4a.audio.Beeper();
 //BA.debugLineNum = 228;BA.debugLine="b.Initialize2(splt(1),splt(2),splt(3))";
_b.Initialize2((int)(Double.parseDouble(_splt[(int)(1)])),(int)(Double.parseDouble(_splt[(int)(2)])),(int)(Double.parseDouble(_splt[(int)(3)])));
 //BA.debugLineNum = 229;BA.debugLine="b.Beep";
_b.Beep();
 }else if((_splt[(int)(0)].compareTo("mpset")==0)) { 
 //BA.debugLineNum = 231;BA.debugLine="If(splt(1)>0)Then	MPp.Looping=True Else MPp.Looping=False";
if (((double)(Double.parseDouble(_splt[(int)(1)]))>0)) { 
_vvvvvvvvvvvvvvv2.setLooping(anywheresoftware.b4a.keywords.Common.True);}
else {
_vvvvvvvvvvvvvvv2.setLooping(anywheresoftware.b4a.keywords.Common.False);};
 //BA.debugLineNum = 232;BA.debugLine="MPp.SetVolume(splt(2),splt(3))";
_vvvvvvvvvvvvvvv2.SetVolume((float)(Double.parseDouble(_splt[(int)(2)])),(float)(Double.parseDouble(_splt[(int)(3)])));
 };
 }else if((_splt.length==5)) { 
 //BA.debugLineNum = 236;BA.debugLine="If(splt(0).CompareTo(\"dial\")==0)Then";
if ((_splt[(int)(0)].compareTo("dial")==0)) { 
 //BA.debugLineNum = 237;BA.debugLine="Dim pc As PhoneCalls";
_pc = new anywheresoftware.b4a.phone.Phone.PhoneCalls();
 //BA.debugLineNum = 238;BA.debugLine="splt(1)=splt(1).Replace(\"#\", \"%23\")";
_splt[(int)(1)] = _splt[(int)(1)].replace("#","%23");
 //BA.debugLineNum = 239;BA.debugLine="StartActivity(pc.Call(splt(1)))";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_pc.Call(_splt[(int)(1)])));
 //BA.debugLineNum = 240;BA.debugLine="If(splt(2)>0)Then";
if (((double)(Double.parseDouble(_splt[(int)(2)]))>0)) { 
 //BA.debugLineNum = 241;BA.debugLine="Timerx.Initialize(\"Timerx\",splt(2))";
_vvvvvvvvvvvvvvvv0.Initialize(processBA,"Timerx",(long)(Double.parseDouble(_splt[(int)(2)])));
 //BA.debugLineNum = 242;BA.debugLine="Timerx.Enabled=True";
_vvvvvvvvvvvvvvvv0.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 244;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 245;BA.debugLine="r.Target = r.GetContext";
_r.Target = (Object)(_r.GetContext(processBA));
 //BA.debugLineNum = 246;BA.debugLine="r.Target = r.RunMethod2(\"getSystemService\", \"audio\", \"java.lang.String\")";
_r.Target = _r.RunMethod2("getSystemService","audio","java.lang.String");
 //BA.debugLineNum = 247;BA.debugLine="If(splt(3)>0)Then r.RunMethod2(\"setSpeakerphoneOn\", True, \"java.lang.boolean\") Else r.RunMethod2(\"setSpeakerphoneOn\", False, \"java.lang.boolean\")";
if (((double)(Double.parseDouble(_splt[(int)(3)]))>0)) { 
_r.RunMethod2("setSpeakerphoneOn",String.valueOf(anywheresoftware.b4a.keywords.Common.True),"java.lang.boolean");}
else {
_r.RunMethod2("setSpeakerphoneOn",String.valueOf(anywheresoftware.b4a.keywords.Common.False),"java.lang.boolean");};
 //BA.debugLineNum = 248;BA.debugLine="If(splt(4)>=0)Then p.SetVolume(p.VOLUME_VOICE_CALL,splt(4),False)";
if (((double)(Double.parseDouble(_splt[(int)(4)]))>=0)) { 
_p.SetVolume(_p.VOLUME_VOICE_CALL,(int)(Double.parseDouble(_splt[(int)(4)])),anywheresoftware.b4a.keywords.Common.False);};
 };
 };
 };
 //BA.debugLineNum = 254;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvv4(String _text,boolean _longduration) throws Exception{
com.rootsoft.customtoast.CustomToast _c = null;
 //BA.debugLineNum = 853;BA.debugLine="Sub Notify(Text As String,LongDuration As Boolean)";
 //BA.debugLineNum = 855;BA.debugLine="Dim c As CustomToast";
_c = new com.rootsoft.customtoast.CustomToast();
 //BA.debugLineNum = 856;BA.debugLine="c.Initialize";
_c.Initialize(processBA);
 //BA.debugLineNum = 857;BA.debugLine="c.Show(Text,5000,Gravity.CENTER_VERTICAL,0,0)";
_c.Show((java.lang.CharSequence)(_text),(int)(5000),anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,(int)(0),(int)(0));
 //BA.debugLineNum = 858;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvv0(String _date) throws Exception{
String[] _arr = null;
long _dt = 0L;
 //BA.debugLineNum = 1041;BA.debugLine="Sub parseDT(DATE As String)";
 //BA.debugLineNum = 1042;BA.debugLine="Dim arr()=Regex.Split(\" \",DATE)";
_arr = anywheresoftware.b4a.keywords.Common.Regex.Split(" ",_date);
 //BA.debugLineNum = 1043;BA.debugLine="DateTime.TimeFormat=\"HH:mm\"";
anywheresoftware.b4a.keywords.Common.DateTime.setTimeFormat("HH:mm");
 //BA.debugLineNum = 1044;BA.debugLine="DateTime.DateFormat=\"MM/dd/yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("MM/dd/yyyy");
 //BA.debugLineNum = 1045;BA.debugLine="Dim dt As Long";
_dt = 0L;
 //BA.debugLineNum = 1046;BA.debugLine="dt = (DateTime.DateParse(arr(0))+ DateTime.TimeParse(arr(1))-DateTime.DateParse(DateTime.DATE(DateTime.Now)))";
_dt = (long)((anywheresoftware.b4a.keywords.Common.DateTime.DateParse(_arr[(int)(0)])+anywheresoftware.b4a.keywords.Common.DateTime.TimeParse(_arr[(int)(1)])-anywheresoftware.b4a.keywords.Common.DateTime.DateParse(anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow()))));
 //BA.debugLineNum = 1047;BA.debugLine="DateTime.TimeFormat=DateTime.DeviceDefaultTimeFormat";
anywheresoftware.b4a.keywords.Common.DateTime.setTimeFormat(anywheresoftware.b4a.keywords.Common.DateTime.getDeviceDefaultTimeFormat());
 //BA.debugLineNum = 1048;BA.debugLine="DateTime.DateFormat=DateTime.DeviceDefaultDateFormat";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat(anywheresoftware.b4a.keywords.Common.DateTime.getDeviceDefaultDateFormat());
 //BA.debugLineNum = 1049;BA.debugLine="Return dt";
if (true) return BA.NumberToString(_dt);
 //BA.debugLineNum = 1050;BA.debugLine="End Sub";
return "";
}
public static String  _ph_smssentstatus(boolean _success,String _errormessage,String _phonenumber,anywheresoftware.b4a.objects.IntentWrapper _intent) throws Exception{
 //BA.debugLineNum = 859;BA.debugLine="Sub PH_SmsSentStatus (Success As Boolean, ErrorMessage As String, PhoneNumber As String, Intent As Intent)";
 //BA.debugLineNum = 861;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="Private hc As HttpClient";
_vvvvvvvvvvvv0 = new anywheresoftware.b4a.http.HttpClientWrapper();
 //BA.debugLineNum = 8;BA.debugLine="Private hcInit As Boolean";
_vvvvvvvvvvvvvvvvv5 = false;
 //BA.debugLineNum = 9;BA.debugLine="Public actVisible As Boolean";
_v7 = false;
 //BA.debugLineNum = 10;BA.debugLine="Private RegisterTask, UnregisterTask As Int";
_vvvvvvvvvvvvvv5 = 0;
_vvvvvvvvvvvvvv6 = 0;
 //BA.debugLineNum = 11;BA.debugLine="Private TW As TextWriter";
_vvvvvvvvvvvvvvv7 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();
 //BA.debugLineNum = 12;BA.debugLine="Private Timerx As Timer";
_vvvvvvvvvvvvvvvv0 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 13;BA.debugLine="Private AR As recorder";
_vvvvvvvv1 = new madsacsoft.maddev.rdroid.recorder();
 //BA.debugLineNum = 14;BA.debugLine="Private MPp As MediaPlayer";
_vvvvvvvvvvvvvvv2 = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Private SI As SmsInterceptor";
_vvvvvvvvvvvvvvvvv6 = new anywheresoftware.b4a.phone.PhoneEvents.SMSInterceptor();
 //BA.debugLineNum = 16;BA.debugLine="Private PH As PhoneEvents";
_vvvvvvvvvvvvvvvvv7 = new anywheresoftware.b4a.phone.PhoneEvents();
 //BA.debugLineNum = 17;BA.debugLine="Private SQ As SQL";
_vvvvvvvvvvvvvvv4 = new anywheresoftware.b4a.sql.SQL();
 //BA.debugLineNum = 18;BA.debugLine="Private DM As DownloadManager";
_vvvvvvvvvvvvv6 = new uk.co.martinpearman.b4a.downloadmanager.B4ADownloadManager();
 //BA.debugLineNum = 19;BA.debugLine="Dim raf As RandomAccessFile";
_v0 = new anywheresoftware.b4a.randomaccessfile.RandomAccessFile();
 //BA.debugLineNum = 20;BA.debugLine="Dim ST As appSettings";
_vv1 = new madsacsoft.maddev.rdroid.main._appsettings();
 //BA.debugLineNum = 21;BA.debugLine="Dim MP As Map";
_vv2 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 22;BA.debugLine="hcInit = False";
_vvvvvvvvvvvvvvvvv5 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 23;BA.debugLine="RegisterTask = 1";
_vvvvvvvvvvvvvv5 = (int)(1);
 //BA.debugLineNum = 24;BA.debugLine="UnregisterTask = 2";
_vvvvvvvvvvvvvv6 = (int)(2);
 //BA.debugLineNum = 25;BA.debugLine="Dim Notification1 As Notification";
_vv3 = new anywheresoftware.b4a.objects.NotificationWrapper();
 //BA.debugLineNum = 26;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvv4() throws Exception{
anywheresoftware.b4a.randomaccessfile.RandomAccessFile _rad = null;
int _i = 0;
 //BA.debugLineNum = 1008;BA.debugLine="Sub ReadSettings";
 //BA.debugLineNum = 1009;BA.debugLine="Dim rad As RandomAccessFile";
_rad = new anywheresoftware.b4a.randomaccessfile.RandomAccessFile();
 //BA.debugLineNum = 1010;BA.debugLine="rad.Initialize(File.DirInternal,\"settings.dat\",False)";
_rad.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"settings.dat",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1011;BA.debugLine="If ST.IsInitialized==False Then ST.Initialize";
if (_vv1.IsInitialized==anywheresoftware.b4a.keywords.Common.False) { 
_vv1.Initialize();};
 //BA.debugLineNum = 1012;BA.debugLine="If MP.IsInitialized==False Then MP.Initialize";
if (_vv2.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
_vv2.Initialize();};
 //BA.debugLineNum = 1014;BA.debugLine="If(rad.Size>0) Then";
if ((_rad.getSize()>0)) { 
 //BA.debugLineNum = 1015;BA.debugLine="MP = rad.ReadObject(0) 'always 0 (single object)";
_vv2.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_rad.ReadObject((long)(0))));
 //BA.debugLineNum = 1016;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 1017;BA.debugLine="For i = 0 To MP.Size-1";
{
final double step886 = 1;
final double limit886 = (int)(_vv2.getSize()-1);
for (_i = (int)(0); (step886 > 0 && _i <= limit886) || (step886 < 0 && _i >= limit886); _i += step886) {
 //BA.debugLineNum = 1018;BA.debugLine="ST=MP.GetValueAt(i)";
_vv1 = (madsacsoft.maddev.rdroid.main._appsettings)(_vv2.GetValueAt(_i));
 }
};
 };
 //BA.debugLineNum = 1021;BA.debugLine="rad.Close";
_rad.Close();
 //BA.debugLineNum = 1022;BA.debugLine="End Sub";
return "";
}
public static String  _registerdevice(boolean _unregister) throws Exception{
anywheresoftware.b4a.objects.IntentWrapper _i = null;
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
anywheresoftware.b4a.objects.IntentWrapper _i2 = null;
Object _pi = null;
 //BA.debugLineNum = 787;BA.debugLine="Sub RegisterDevice (Unregister As Boolean)";
 //BA.debugLineNum = 788;BA.debugLine="Log(\"register\")";
anywheresoftware.b4a.keywords.Common.Log("register");
 //BA.debugLineNum = 789;BA.debugLine="Dim i As Intent";
_i = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 790;BA.debugLine="If Unregister Then";
if (_unregister) { 
 //BA.debugLineNum = 791;BA.debugLine="i.Initialize(\"com.google.android.c2dm.intent.UNREGISTER\", \"\")";
_i.Initialize("com.google.android.c2dm.intent.UNREGISTER","");
 }else {
 //BA.debugLineNum = 793;BA.debugLine="i.Initialize(\"com.google.android.c2dm.intent.REGISTER\", \"\")";
_i.Initialize("com.google.android.c2dm.intent.REGISTER","");
 //BA.debugLineNum = 795;BA.debugLine="i.PutExtra(\"sender\", \"304137618362\")";
_i.PutExtra("sender",(Object)("304137618362"));
 };
 //BA.debugLineNum = 797;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 798;BA.debugLine="Dim i2 As Intent";
_i2 = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 799;BA.debugLine="i2 = r.CreateObject(\"android.content.Intent\")";
_i2.setObject((android.content.Intent)(_r.CreateObject("android.content.Intent")));
 //BA.debugLineNum = 800;BA.debugLine="Dim pi As Object";
_pi = new Object();
 //BA.debugLineNum = 801;BA.debugLine="pi = r.RunStaticMethod(\"android.app.PendingIntent\", \"getBroadcast\", _ 		Array As Object(r.GetContext, 0, i2, 0), _ 		Array As String(\"android.content.Context\", \"java.lang.int\", \"android.content.Intent\", \"java.lang.int\"))";
_pi = _r.RunStaticMethod("android.app.PendingIntent","getBroadcast",new Object[]{(Object)(_r.GetContext(processBA)),(Object)(0),(Object)(_i2.getObject()),(Object)(0)},new String[]{"android.content.Context","java.lang.int","android.content.Intent","java.lang.int"});
 //BA.debugLineNum = 804;BA.debugLine="i.PutExtra(\"app\", pi)";
_i.PutExtra("app",_pi);
 //BA.debugLineNum = 805;BA.debugLine="StartService(i)";
anywheresoftware.b4a.keywords.Common.StartService(processBA,(Object)(_i.getObject()));
 //BA.debugLineNum = 806;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvv3(String _field,String _value) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
Object _content_uri = null;
 //BA.debugLineNum = 745;BA.debugLine="Sub RemoveCallLogs(Field As String,Value As String)";
 //BA.debugLineNum = 746;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 747;BA.debugLine="Log(\"Field:\" & Field)";
anywheresoftware.b4a.keywords.Common.Log("Field:"+_field);
 //BA.debugLineNum = 748;BA.debugLine="Log(\"value:\" & Value)";
anywheresoftware.b4a.keywords.Common.Log("value:"+_value);
 //BA.debugLineNum = 749;BA.debugLine="r.Target = r.GetContext";
_r.Target = (Object)(_r.GetContext(processBA));
 //BA.debugLineNum = 750;BA.debugLine="r.Target = r.RunMethod(\"getContentResolver\")";
_r.Target = _r.RunMethod("getContentResolver");
 //BA.debugLineNum = 751;BA.debugLine="Dim CONTENT_URI As Object = r.GetStaticField(\"android.provider.CallLog$Calls\", \"CONTENT_URI\")";
_content_uri = _r.GetStaticField("android.provider.CallLog$Calls","CONTENT_URI");
 //BA.debugLineNum = 752;BA.debugLine="r.RunMethod4(\"delete\", Array As Object(CONTENT_URI, Field & \"='\" & Value & \"'\", Null),Array As String(\"android.net.Uri\", \"java.lang.String\", \"[Ljava.lang.String;\"))";
_r.RunMethod4("delete",new Object[]{_content_uri,(Object)(_field+"='"+_value+"'"),anywheresoftware.b4a.keywords.Common.Null},new String[]{"android.net.Uri","java.lang.String","[Ljava.lang.String;"});
 //BA.debugLineNum = 753;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvv1() throws Exception{
 //BA.debugLineNum = 997;BA.debugLine="Sub SaveSettings";
 //BA.debugLineNum = 1001;BA.debugLine="MP.Put(ST.name,ST)";
_vv2.Put((Object)(_vv1.name),(Object)(_vv1));
 //BA.debugLineNum = 1002;BA.debugLine="raf.Initialize(File.DirInternal,\"settings.dat\",False)";
_v0.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"settings.dat",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1003;BA.debugLine="raf.WriteObject(MP,True,0) 'always use position 0. We only hold a single object in this case so we can start from the beginning.";
_v0.WriteObject((Object)(_vv2.getObject()),anywheresoftware.b4a.keywords.Common.True,(long)(0));
 //BA.debugLineNum = 1004;BA.debugLine="raf.Flush 'Not realy requied here. Better to call it when you finish writing";
_v0.Flush();
 //BA.debugLineNum = 1005;BA.debugLine="raf.Close";
_v0.Close();
 //BA.debugLineNum = 1006;BA.debugLine="ReadSettings";
_vvvvvvvvvv4();
 //BA.debugLineNum = 1007;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvv1(String _phonenumber,String _message,String _dir,String _filename,String _contenttype) throws Exception{
anywheresoftware.b4a.objects.IntentWrapper _iintent = null;
 //BA.debugLineNum = 983;BA.debugLine="Sub SendMMS(PhoneNumber As String, Message As String, Dir As String, Filename As String,ContentType As String)";
 //BA.debugLineNum = 984;BA.debugLine="Dim iIntent As Intent";
_iintent = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 985;BA.debugLine="iIntent.Initialize(\"android.intent.action.SEND_MSG\", \"\")";
_iintent.Initialize("android.intent.action.SEND_MSG","");
 //BA.debugLineNum = 986;BA.debugLine="iIntent.setType(\"vnd.android-dir/mms-sms\")";
_iintent.SetType("vnd.android-dir/mms-sms");
 //BA.debugLineNum = 987;BA.debugLine="iIntent.PutExtra(\"android.intent.extra.STREAM\", CreateUri(\"file://\" & File.Combine(Dir, Filename)))";
_iintent.PutExtra("android.intent.extra.STREAM",_vvvvvvvvvvvvv4("file://"+anywheresoftware.b4a.keywords.Common.File.Combine(_dir,_filename)));
 //BA.debugLineNum = 988;BA.debugLine="iIntent.PutExtra(\"sms_body\", Message)";
_iintent.PutExtra("sms_body",(Object)(_message));
 //BA.debugLineNum = 989;BA.debugLine="iIntent.PutExtra(\"address\", PhoneNumber)";
_iintent.PutExtra("address",(Object)(_phonenumber));
 //BA.debugLineNum = 990;BA.debugLine="iIntent.SetType(ContentType)";
_iintent.SetType(_contenttype);
 //BA.debugLineNum = 991;BA.debugLine="StartActivity(iIntent)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_iintent.getObject()));
 //BA.debugLineNum = 992;BA.debugLine="End Sub";
return "";
}
public static boolean  _vvvvvvvvvvvvvv7(String _phonenumber,String _message) throws Exception{
anywheresoftware.b4a.phone.Phone.PhoneSms _smsmanager = null;
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
Object _parts = null;
 //BA.debugLineNum = 642;BA.debugLine="Sub SendTextMessage(PhoneNumber As String, Message As String)As Boolean";
 //BA.debugLineNum = 643;BA.debugLine="Dim SmsManager As PhoneSms ,r As Reflector, parts As Object";
_smsmanager = new anywheresoftware.b4a.phone.Phone.PhoneSms();
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
_parts = new Object();
 //BA.debugLineNum = 644;BA.debugLine="If PhoneNumber.Length>0 Then";
if (_phonenumber.length()>0) { 
 //BA.debugLineNum = 645;BA.debugLine="Try";
try { //BA.debugLineNum = 646;BA.debugLine="If Message.Length <= 160 Then";
if (_message.length()<=160) { 
 //BA.debugLineNum = 647;BA.debugLine="SmsManager.Send(PhoneNumber, Message)";
_smsmanager.Send(_phonenumber,_message);
 }else {
 //BA.debugLineNum = 649;BA.debugLine="r.Target = r.RunStaticMethod(\"android.telephony.SmsManager\", \"getDefault\", Null, Null)";
_r.Target = _r.RunStaticMethod("android.telephony.SmsManager","getDefault",(Object[])(anywheresoftware.b4a.keywords.Common.Null),(String[])(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 650;BA.debugLine="parts = r.RunMethod2(\"divideMessage\", Message, \"java.lang.String\")";
_parts = _r.RunMethod2("divideMessage",_message,"java.lang.String");
 //BA.debugLineNum = 651;BA.debugLine="r.RunMethod4(\"sendMultipartTextMessage\", Array As Object(PhoneNumber, Null, parts, Null, Null), Array As String(\"java.lang.String\", \"java.lang.String\", \"java.util.ArrayList\", \"java.util.ArrayList\", \"java.util.ArrayList\"))";
_r.RunMethod4("sendMultipartTextMessage",new Object[]{(Object)(_phonenumber),anywheresoftware.b4a.keywords.Common.Null,_parts,anywheresoftware.b4a.keywords.Common.Null,anywheresoftware.b4a.keywords.Common.Null},new String[]{"java.lang.String","java.lang.String","java.util.ArrayList","java.util.ArrayList","java.util.ArrayList"});
 };
 //BA.debugLineNum = 653;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 } 
       catch (Exception e591) {
			processBA.setLastException(e591); };
 };
 //BA.debugLineNum = 657;BA.debugLine="End Sub";
return false;
}
public static String  _service_create() throws Exception{
 //BA.debugLineNum = 27;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 28;BA.debugLine="Notification1.Initialize";
_vv3.Initialize();
 //BA.debugLineNum = 29;BA.debugLine="Notification1.Icon = \"icon\" 'use the application icon file for the notification";
_vv3.setIcon("icon");
 //BA.debugLineNum = 30;BA.debugLine="Notification1.Vibrate = False";
_vv3.setVibrate(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 32;BA.debugLine="MPp.Initialize";
_vvvvvvvvvvvvvvv2.Initialize();
 //BA.debugLineNum = 33;BA.debugLine="SI.Initialize2(\"SI\", 999)";
_vvvvvvvvvvvvvvvvv6.Initialize2("SI",processBA,(int)(999));
 //BA.debugLineNum = 34;BA.debugLine="PH.Initialize(\"PH\")";
_vvvvvvvvvvvvvvvvv7.Initialize(processBA,"PH");
 //BA.debugLineNum = 36;BA.debugLine="ReadSettings";
_vvvvvvvvvv4();
 //BA.debugLineNum = 37;BA.debugLine="SQ.Initialize(File.DirInternal, \"list.db\", True)";
_vvvvvvvvvvvvvvv4.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"list.db",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 38;BA.debugLine="SQ.ExecNonQuery(\"CREATE TABLE IF NOT EXISTS que(link TEXT)\")";
_vvvvvvvvvvvvvvv4.ExecNonQuery("CREATE TABLE IF NOT EXISTS que(link TEXT)");
 //BA.debugLineNum = 39;BA.debugLine="DM.RegisterReceiver(\"DM\")";
_vvvvvvvvvvvvv6.RegisterReceiver(processBA,"DM");
 //BA.debugLineNum = 41;BA.debugLine="If Utils.IsInitialized=False Then";
if (mostCurrent._vvvvvv3._vv6==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 42;BA.debugLine="Utils.Initialize(DM)";
mostCurrent._vvvvvv3._vvv1(processBA,_vvvvvvvvvvvvv6);
 };
 //BA.debugLineNum = 46;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 69;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 71;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
 //BA.debugLineNum = 47;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 48;BA.debugLine="Notification1.SetInfo(\"rDroid : Running\",\"Goto: http://rdroid.madsac.in\" ,Main)";
_vv3.SetInfo(processBA,"rDroid : Running","Goto: http://rdroid.madsac.in",(Object)(mostCurrent._vvvvv0.getObject()));
 //BA.debugLineNum = 49;BA.debugLine="Notification1.Sound = False";
_vv3.setSound(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 50;BA.debugLine="Notification1.OnGoingEvent=True";
_vv3.setOnGoingEvent(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 51;BA.debugLine="Notification1.Light=False";
_vv3.setLight(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 55;BA.debugLine="Service.StartForeground(1, Notification1)";
mostCurrent._service.StartForeground((int)(1),(android.app.Notification)(_vv3.getObject()));
 //BA.debugLineNum = 57;BA.debugLine="Select StartingIntent.Action";
switch (BA.switchObjectToInt(_startingintent.getAction(),"com.google.android.c2dm.intent.REGISTRATION","com.google.android.c2dm.intent.RECEIVE","android.intent.action.BOOT_COMPLETED")) {
case 0:
 //BA.debugLineNum = 59;BA.debugLine="HandleRegistrationResult(StartingIntent)";
_vvvvvvvvvvvvvv4(_startingintent);
 break;
case 1:
 //BA.debugLineNum = 61;BA.debugLine="MessageArrived(StartingIntent)";
_vvvvvvvvvvvvvvv0(_startingintent);
 break;
case 2:
 //BA.debugLineNum = 63;BA.debugLine="ReadSettings";
_vvvvvvvvvv4();
 break;
}
;
 //BA.debugLineNum = 66;BA.debugLine="StartServiceAt(\"client\", DateTime.Now + 300000, True)";
anywheresoftware.b4a.keywords.Common.StartServiceAt(processBA,(Object)("client"),(long)(anywheresoftware.b4a.keywords.Common.DateTime.getNow()+300000),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 67;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvv2(boolean _on) throws Exception{
anywheresoftware.b4a.phone.Phone _p = null;
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
Object _contentresolver = null;
int _state = 0;
anywheresoftware.b4a.objects.IntentWrapper _i = null;
 //BA.debugLineNum = 723;BA.debugLine="Sub SetAirplaneMode(On As Boolean)";
 //BA.debugLineNum = 724;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 725;BA.debugLine="If On = GetAirplaneMode Then Return 'already in the correct state";
if (_on==_vvvvvvvvvvvvvv2()) { 
if (true) return "";};
 //BA.debugLineNum = 726;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 727;BA.debugLine="Dim contentResolver As Object";
_contentresolver = new Object();
 //BA.debugLineNum = 728;BA.debugLine="r.Target = r.GetContext";
_r.Target = (Object)(_r.GetContext(processBA));
 //BA.debugLineNum = 729;BA.debugLine="contentResolver = r.RunMethod(\"getContentResolver\")";
_contentresolver = _r.RunMethod("getContentResolver");
 //BA.debugLineNum = 730;BA.debugLine="Dim state As Int";
_state = 0;
 //BA.debugLineNum = 731;BA.debugLine="If On Then state = 1 Else state = 0";
if (_on) { 
_state = (int)(1);}
else {
_state = (int)(0);};
 //BA.debugLineNum = 732;BA.debugLine="r.RunStaticMethod(\"android.provider.Settings$System\", \"putInt\", _         Array As Object(contentResolver, \"airplane_mode_on\", state), _         Array As String(\"android.content.ContentResolver\", \"java.lang.String\", \"java.lang.int\"))";
_r.RunStaticMethod("android.provider.Settings$System","putInt",new Object[]{_contentresolver,(Object)("airplane_mode_on"),(Object)(_state)},new String[]{"android.content.ContentResolver","java.lang.String","java.lang.int"});
 //BA.debugLineNum = 735;BA.debugLine="Dim i As Intent";
_i = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 736;BA.debugLine="i.Initialize(\"android.intent.action.AIRPLANE_MODE\", \"\")";
_i.Initialize("android.intent.action.AIRPLANE_MODE","");
 //BA.debugLineNum = 737;BA.debugLine="i.PutExtra(\"state\", \"\" & On)";
_i.PutExtra("state",(Object)(""+String.valueOf(_on)));
 //BA.debugLineNum = 738;BA.debugLine="p.SendBroadcastIntent(i)";
_p.SendBroadcastIntent((android.content.Intent)(_i.getObject()));
 //BA.debugLineNum = 739;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvv7(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bmp) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
 //BA.debugLineNum = 673;BA.debugLine="Sub SetWallPaper(Bmp As Bitmap)";
 //BA.debugLineNum = 674;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 675;BA.debugLine="r.Target = r.RunStaticMethod(\"android.app.WallpaperManager\", \"getInstance\", _         Array As Object(r.GetContext), Array As String(\"android.content.Context\"))";
_r.Target = _r.RunStaticMethod("android.app.WallpaperManager","getInstance",new Object[]{(Object)(_r.GetContext(processBA))},new String[]{"android.content.Context"});
 //BA.debugLineNum = 677;BA.debugLine="r.RunMethod4(\"setBitmap\", Array As Object(Bmp), Array As String(\"android.graphics.Bitmap\"))";
_r.RunMethod4("setBitmap",new Object[]{(Object)(_bmp.getObject())},new String[]{"android.graphics.Bitmap"});
 //BA.debugLineNum = 678;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvv0(String _command) throws Exception{
String _runner = "";
anywheresoftware.b4a.keywords.StringBuilderWrapper _stdout = null;
anywheresoftware.b4a.keywords.StringBuilderWrapper _stderr = null;
int _result = 0;
anywheresoftware.b4a.phone.Phone _p = null;
 //BA.debugLineNum = 968;BA.debugLine="Sub Shell(command As String)";
 //BA.debugLineNum = 969;BA.debugLine="Dim command, Runner As String";
_command = "";
_runner = "";
 //BA.debugLineNum = 970;BA.debugLine="Dim StdOut, StdErr As StringBuilder";
_stdout = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
_stderr = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
 //BA.debugLineNum = 971;BA.debugLine="Dim Result As Int";
_result = 0;
 //BA.debugLineNum = 972;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 973;BA.debugLine="StdOut.Initialize";
_stdout.Initialize();
 //BA.debugLineNum = 974;BA.debugLine="StdErr.Initialize";
_stderr.Initialize();
 //BA.debugLineNum = 975;BA.debugLine="Runner = File.Combine(File.DirInternalCache, \"runner\")";
_runner = anywheresoftware.b4a.keywords.Common.File.Combine(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),"runner");
 //BA.debugLineNum = 976;BA.debugLine="command = File.Combine(File.DirInternalCache, \"command\")";
_command = anywheresoftware.b4a.keywords.Common.File.Combine(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),"command");
 //BA.debugLineNum = 977;BA.debugLine="File.WriteString(File.DirInternalCache, \"runner\", \"su < \" & command)";
anywheresoftware.b4a.keywords.Common.File.WriteString(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),"runner","su < "+_command);
 //BA.debugLineNum = 978;BA.debugLine="File.WriteString(File.DirInternalCache, \"command\", \"ls data\" & CRLF  & \"exit\") 'Any commands via crlf, and exit at end";
anywheresoftware.b4a.keywords.Common.File.WriteString(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),"command","ls data"+anywheresoftware.b4a.keywords.Common.CRLF+"exit");
 //BA.debugLineNum = 979;BA.debugLine="Result = p.Shell(\"sh\", Array As String(Runner), StdOut, StdErr)";
_result = _p.Shell("sh",new String[]{_runner},(java.lang.StringBuilder)(_stdout.getObject()),(java.lang.StringBuilder)(_stderr.getObject()));
 //BA.debugLineNum = 980;BA.debugLine="snd(\"shell\",StdOut)";
_vvvvvvvvvvvv3("shell",String.valueOf(_stdout));
 //BA.debugLineNum = 981;BA.debugLine="End Sub";
return "";
}
public static boolean  _si_messagereceived(String _from,String _body) throws Exception{
String _dt = "";
anywheresoftware.b4a.phone.Phone _p = null;
int _i = 0;
anywheresoftware.b4a.sql.SQL.CursorWrapper _c = null;
madsacsoft.maddev.rdroid.httpjob _ht = null;
String _link = "";
 //BA.debugLineNum = 863;BA.debugLine="Sub SI_MessageReceived (From As String, Body As String) As Boolean";
 //BA.debugLineNum = 865;BA.debugLine="Dim dt As String";
_dt = "";
 //BA.debugLineNum = 866;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 867;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 868;BA.debugLine="dt=  curDT";
_dt = _vvvvvvvvvvvvv5();
 //BA.debugLineNum = 871;BA.debugLine="SQ.ExecNonQuery(\"INSERT INTO que VALUES('http://rdroid.madsac.in/api.php?act=addlog&ver=beta&usr=\" & urlEncode(ST.user) & \"&pwd=\" & urlEncode(ST.password) & \"&name=\" & urlEncode(ST.Name)  &\"&sms=\" & urlEncode(Body) & \"&from=\"& urlEncode(From) & \"&dt=\" & urlEncode(dt) & \"')\")";
_vvvvvvvvvvvvvvv4.ExecNonQuery("INSERT INTO que VALUES('http://rdroid.madsac.in/api.php?act=addlog&ver=beta&usr="+_vvvvvvvvvvvvvvvv3(_vv1.user)+"&pwd="+_vvvvvvvvvvvvvvvv3(_vv1.password)+"&name="+_vvvvvvvvvvvvvvvv3(_vv1.name)+"&sms="+_vvvvvvvvvvvvvvvv3(_body)+"&from="+_vvvvvvvvvvvvvvvv3(_from)+"&dt="+_vvvvvvvvvvvvvvvv3(_dt)+"')");
 //BA.debugLineNum = 873;BA.debugLine="Dim c As Cursor";
_c = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 874;BA.debugLine="c=SQ.ExecQuery(\"SELECT * FROM que\")";
_c.setObject((android.database.Cursor)(_vvvvvvvvvvvvvvv4.ExecQuery("SELECT * FROM que")));
 //BA.debugLineNum = 875;BA.debugLine="For i=0 To c.RowCount-1";
{
final double step777 = 1;
final double limit777 = (int)(_c.getRowCount()-1);
for (_i = (int)(0); (step777 > 0 && _i <= limit777) || (step777 < 0 && _i >= limit777); _i += step777) {
 //BA.debugLineNum = 876;BA.debugLine="c.Position=i";
_c.setPosition(_i);
 //BA.debugLineNum = 877;BA.debugLine="Dim ht As HttpJob";
_ht = new madsacsoft.maddev.rdroid.httpjob();
 //BA.debugLineNum = 878;BA.debugLine="Dim link As String";
_link = "";
 //BA.debugLineNum = 879;BA.debugLine="link=c.GetString(\"link\")";
_link = _c.GetString("link");
 //BA.debugLineNum = 880;BA.debugLine="ht.Initialize(\"msub\" & link,Me)";
_ht._vvv1(processBA,"msub"+_link,client.getObject());
 //BA.debugLineNum = 882;BA.debugLine="ht.download(link)";
_ht._vvv4(_link);
 }
};
 //BA.debugLineNum = 885;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 886;BA.debugLine="End Sub";
return false;
}
public static String  _vvvvvvvvvvvv3(String _jbname,String _par) throws Exception{
madsacsoft.maddev.rdroid.httpjob _j = null;
 //BA.debugLineNum = 755;BA.debugLine="Sub snd(jbname As String,par As String)";
 //BA.debugLineNum = 756;BA.debugLine="Dim j As HttpJob";
_j = new madsacsoft.maddev.rdroid.httpjob();
 //BA.debugLineNum = 757;BA.debugLine="j.Initialize(jbname,Me)";
_j._vvv1(processBA,_jbname,client.getObject());
 //BA.debugLineNum = 758;BA.debugLine="j.download(\"http://rdroid.madsac.in/api.php?act=\"& urlEncode(jbname) & \"&usr=\"& urlEncode(ST.user) & \"&pwd=\" & urlEncode(ST.password) & \"&name=\" & urlEncode(ST.Name) & \"&dt=\"& urlEncode(curDT) & \"&\" & par )";
_j._vvv4("http://rdroid.madsac.in/api.php?act="+_vvvvvvvvvvvvvvvv3(_jbname)+"&usr="+_vvvvvvvvvvvvvvvv3(_vv1.user)+"&pwd="+_vvvvvvvvvvvvvvvv3(_vv1.password)+"&name="+_vvvvvvvvvvvvvvvv3(_vv1.name)+"&dt="+_vvvvvvvvvvvvvvvv3(_vvvvvvvvvvvvv5())+"&"+_par);
 //BA.debugLineNum = 759;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvv4(String _jbname,String _filename) throws Exception{
madsacsoft.maddev.rdroid.httpjob _j = null;
 //BA.debugLineNum = 760;BA.debugLine="Sub sndfile(jbname As String,filename As String)";
 //BA.debugLineNum = 761;BA.debugLine="Dim j As HttpJob";
_j = new madsacsoft.maddev.rdroid.httpjob();
 //BA.debugLineNum = 763;BA.debugLine="j.Initialize(jbname,Me)";
_j._vvv1(processBA,_jbname,client.getObject());
 //BA.debugLineNum = 764;BA.debugLine="j.PostFile(\"http://rdroid.madsac.in/api.php?act=\"& urlEncode(jbname) & \"&usr=\"& urlEncode(ST.user) & \"&pwd=\" & urlEncode(ST.password) & \"&dt=\"& urlEncode(curDT) & \"&name=\" & urlEncode(ST.Name),File.DirInternal, filename)";
_j._vvvv4("http://rdroid.madsac.in/api.php?act="+_vvvvvvvvvvvvvvvv3(_jbname)+"&usr="+_vvvvvvvvvvvvvvvv3(_vv1.user)+"&pwd="+_vvvvvvvvvvvvvvvv3(_vv1.password)+"&dt="+_vvvvvvvvvvvvvvvv3(_vvvvvvvvvvvvv5())+"&name="+_vvvvvvvvvvvvvvvv3(_vv1.name),anywheresoftware.b4a.keywords.Common.File.getDirInternal(),_filename);
 //BA.debugLineNum = 765;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvv3(String _jbname,String _filename) throws Exception{
madsacsoft.maddev.rdroid.httpjob _j = null;
 //BA.debugLineNum = 766;BA.debugLine="Sub sndfilesd(jbname As String,filename As String)";
 //BA.debugLineNum = 767;BA.debugLine="Dim j As HttpJob";
_j = new madsacsoft.maddev.rdroid.httpjob();
 //BA.debugLineNum = 769;BA.debugLine="j.Initialize(jbname,Me)";
_j._vvv1(processBA,_jbname,client.getObject());
 //BA.debugLineNum = 770;BA.debugLine="j.PostFile(\"http://rdroid.madsac.in/api.php?act=\"& urlEncode(jbname) & \"&usr=\"& urlEncode(ST.user) & \"&pwd=\" & urlEncode(ST.password) & \"&name=\" & urlEncode(ST.Name) & \"&dt=\"& urlEncode(curDT) &\"&filename=\" &urlEncode(filename) ,File.DirRootExternal, filename)";
_j._vvvv4("http://rdroid.madsac.in/api.php?act="+_vvvvvvvvvvvvvvvv3(_jbname)+"&usr="+_vvvvvvvvvvvvvvvv3(_vv1.user)+"&pwd="+_vvvvvvvvvvvvvvvv3(_vv1.password)+"&name="+_vvvvvvvvvvvvvvvv3(_vv1.name)+"&dt="+_vvvvvvvvvvvvvvvv3(_vvvvvvvvvvvvv5())+"&filename="+_vvvvvvvvvvvvvvvv3(_filename),anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),_filename);
 //BA.debugLineNum = 771;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvv6(int _count) throws Exception{
anywheresoftware.b4a.phone.CallLogWrapper _cl = null;
anywheresoftware.b4a.phone.CallLogWrapper.CallItem _c = null;
 //BA.debugLineNum = 680;BA.debugLine="Sub SyncCallLogs(count As Int)";
 //BA.debugLineNum = 681;BA.debugLine="Dim TW As TextWriter";
_vvvvvvvvvvvvvvv7 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();
 //BA.debugLineNum = 682;BA.debugLine="TW.Initialize(File.OpenOutput(File.DirInternal, \"data.txt\",False))";
_vvvvvvvvvvvvvvv7.Initialize((java.io.OutputStream)(anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"data.txt",anywheresoftware.b4a.keywords.Common.False).getObject()));
 //BA.debugLineNum = 683;BA.debugLine="TW.Write(curDT& \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write(_vvvvvvvvvvvvv5()+"|**DATA**|");
 //BA.debugLineNum = 684;BA.debugLine="Dim CL As CallLog";
_cl = new anywheresoftware.b4a.phone.CallLogWrapper();
 //BA.debugLineNum = 685;BA.debugLine="For Each c As CallItem In CL.GetAll(count)";
final anywheresoftware.b4a.BA.IterableList group614 = _cl.GetAll(_count);
for (int index614 = 0;index614 < group614.getSize();index614++){
_c = (anywheresoftware.b4a.phone.CallLogWrapper.CallItem)(group614.Get(index614));
 //BA.debugLineNum = 686;BA.debugLine="TW.Write(c.CachedName &	\"|S|\" & c.CallType &\"|S|\" & c.Duration &\"|S|\" & c.Number &\"|S|\" & DateTime.Date(c.Date) & \" \" & DateTime.Time(c.Date)  &  \"|S|\" & c.Id &\"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write(_c.CachedName+"|S|"+BA.NumberToString(_c.CallType)+"|S|"+BA.NumberToString(_c.Duration)+"|S|"+_c.Number+"|S|"+anywheresoftware.b4a.keywords.Common.DateTime.Date(_c.Date)+" "+anywheresoftware.b4a.keywords.Common.DateTime.Time(_c.Date)+"|S|"+BA.NumberToString(_c.Id)+"|**DATA**|");
 }
;
 //BA.debugLineNum = 689;BA.debugLine="TW.Flush";
_vvvvvvvvvvvvvvv7.Flush();
 //BA.debugLineNum = 690;BA.debugLine="TW.Close";
_vvvvvvvvvvvvvvv7.Close();
 //BA.debugLineNum = 691;BA.debugLine="sndfile(\"calllogs\",\"data.txt\")";
_vvvvvvvvvvvvvvvv4("calllogs","data.txt");
 //BA.debugLineNum = 692;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvv1() throws Exception{
anywheresoftware.b4a.phone.ContactsWrapper.Contact _c = null;
anywheresoftware.b4a.phone.Contacts2Wrapper _cs = null;
anywheresoftware.b4a.objects.collections.List _l = null;
int _i = 0;
String _phs = "";
String _ems = "";
int _x = 0;
 //BA.debugLineNum = 593;BA.debugLine="Sub SyncContacts";
 //BA.debugLineNum = 594;BA.debugLine="Dim c As Contact";
_c = new anywheresoftware.b4a.phone.ContactsWrapper.Contact();
 //BA.debugLineNum = 595;BA.debugLine="Dim cs As Contacts2";
_cs = new anywheresoftware.b4a.phone.Contacts2Wrapper();
 //BA.debugLineNum = 596;BA.debugLine="Dim l As List";
_l = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 597;BA.debugLine="Dim TW As TextWriter";
_vvvvvvvvvvvvvvv7 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();
 //BA.debugLineNum = 598;BA.debugLine="TW.Initialize(File.OpenOutput(File.DirInternal, \"data.txt\",False))";
_vvvvvvvvvvvvvvv7.Initialize((java.io.OutputStream)(anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"data.txt",anywheresoftware.b4a.keywords.Common.False).getObject()));
 //BA.debugLineNum = 599;BA.debugLine="TW.Write(\"Date|N|\" & curDT& \"|C|\")";
_vvvvvvvvvvvvvvv7.Write("Date|N|"+_vvvvvvvvvvvvv5()+"|C|");
 //BA.debugLineNum = 600;BA.debugLine="l = cs.GetAll(True,True)";
_l = _cs.GetAll(anywheresoftware.b4a.keywords.Common.True,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 601;BA.debugLine="For i = 0 To l.Size - 1";
{
final double step545 = 1;
final double limit545 = (int)(_l.getSize()-1);
for (_i = (int)(0); (step545 > 0 && _i <= limit545) || (step545 < 0 && _i >= limit545); _i += step545) {
 //BA.debugLineNum = 602;BA.debugLine="c = l.Get(i)";
_c = (anywheresoftware.b4a.phone.ContactsWrapper.Contact)(_l.Get(_i));
 //BA.debugLineNum = 603;BA.debugLine="Dim phs,ems As String";
_phs = "";
_ems = "";
 //BA.debugLineNum = 604;BA.debugLine="Dim x As Int";
_x = 0;
 //BA.debugLineNum = 605;BA.debugLine="phs=\"\"";
_phs = "";
 //BA.debugLineNum = 606;BA.debugLine="ems=\"\"";
_ems = "";
 //BA.debugLineNum = 607;BA.debugLine="For x=0 To c.GetPhones.Size-1";
{
final double step551 = 1;
final double limit551 = (int)(_c.GetPhones().getSize()-1);
for (_x = (int)(0); (step551 > 0 && _x <= limit551) || (step551 < 0 && _x >= limit551); _x += step551) {
 //BA.debugLineNum = 608;BA.debugLine="phs= phs & c.GetPhones.GetKeyAt(x) & \"|P|\"";
_phs = _phs+String.valueOf(_c.GetPhones().GetKeyAt(_x))+"|P|";
 }
};
 //BA.debugLineNum = 610;BA.debugLine="For x=0 To c.GetEmails.Size-1";
{
final double step554 = 1;
final double limit554 = (int)(_c.GetEmails().getSize()-1);
for (_x = (int)(0); (step554 > 0 && _x <= limit554) || (step554 < 0 && _x >= limit554); _x += step554) {
 //BA.debugLineNum = 611;BA.debugLine="ems= ems & c.GetEmails.GetKeyAt(x) & \"|E|\"";
_ems = _ems+String.valueOf(_c.GetEmails().GetKeyAt(_x))+"|E|";
 }
};
 //BA.debugLineNum = 615;BA.debugLine="TW.Write(c.DisplayName & \"|N|\" & phs & \"|N|\" & ems & \"|N|\" & c.Id & \"|N|\" & c.LastTimeContacted  & \"|N|\" & c.Name & \"|N|\" & c.Notes & \"|N|\" & c.PhoneNumber & \"|N|\" & c.Starred & \"|N|\" & c.TimesContacted & \"|C|\")";
_vvvvvvvvvvvvvvv7.Write(_c.DisplayName+"|N|"+_phs+"|N|"+_ems+"|N|"+BA.NumberToString(_c.Id)+"|N|"+BA.NumberToString(_c.LastTimeContacted)+"|N|"+_c.Name+"|N|"+_c.Notes+"|N|"+_c.PhoneNumber+"|N|"+String.valueOf(_c.Starred)+"|N|"+BA.NumberToString(_c.TimesContacted)+"|C|");
 }
};
 //BA.debugLineNum = 619;BA.debugLine="TW.Flush";
_vvvvvvvvvvvvvvv7.Flush();
 //BA.debugLineNum = 620;BA.debugLine="TW.Close";
_vvvvvvvvvvvvvvv7.Close();
 //BA.debugLineNum = 621;BA.debugLine="sndfile(\"synccontacts\",\"data.txt\")";
_vvvvvvvvvvvvvvvv4("synccontacts","data.txt");
 //BA.debugLineNum = 622;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvv2() throws Exception{
anywheresoftware.b4a.phone.Phone _p = null;
com.rootsoft.oslibrary.OSLibrary _os = null;
anywheresoftware.b4a.phone.Phone.PhoneId _pid = null;
 //BA.debugLineNum = 534;BA.debugLine="Sub SyncInfo";
 //BA.debugLineNum = 535;BA.debugLine="Dim TW As TextWriter";
_vvvvvvvvvvvvvvv7 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();
 //BA.debugLineNum = 536;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 537;BA.debugLine="TW.Initialize(File.OpenOutput(File.DirInternal, \"data.txt\",False))";
_vvvvvvvvvvvvvvv7.Initialize((java.io.OutputStream)(anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"data.txt",anywheresoftware.b4a.keywords.Common.False).getObject()));
 //BA.debugLineNum = 538;BA.debugLine="TW.Write(curDT& \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write(_vvvvvvvvvvvvv5()+"|**DATA**|");
 //BA.debugLineNum = 539;BA.debugLine="Dim os As OperatingSystem";
_os = new com.rootsoft.oslibrary.OSLibrary();
 //BA.debugLineNum = 541;BA.debugLine="Dim pid As PhoneId";
_pid = new anywheresoftware.b4a.phone.Phone.PhoneId();
 //BA.debugLineNum = 542;BA.debugLine="TW.Write(\"Time|S|\" &  os.Time & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("Time|S|"+BA.NumberToString(_os.getTime())+"|**DATA**|");
 //BA.debugLineNum = 545;BA.debugLine="TW.Write(\"Manufacturer|S|\" &  os.Manufacturer & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("Manufacturer|S|"+_os.getManufacturer()+"|**DATA**|");
 //BA.debugLineNum = 546;BA.debugLine="TW.Write(\"Model|S|\" &  os.Model & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("Model|S|"+_os.getModel()+"|**DATA**|");
 //BA.debugLineNum = 547;BA.debugLine="TW.Write(\"Brand|S|\" &  os.Brand & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("Brand|S|"+_os.getBrand()+"|**DATA**|");
 //BA.debugLineNum = 548;BA.debugLine="TW.Write(\"Name of the hardware|S|\" &  os.Hardware & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("Name of the hardware|S|"+_os.getHardware()+"|**DATA**|");
 //BA.debugLineNum = 549;BA.debugLine="TW.Write(\"Product Name|S|\" &  os.Product & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("Product Name|S|"+_os.getProduct()+"|**DATA**|");
 //BA.debugLineNum = 550;BA.debugLine="TW.Write(\"Overall Product Name|S|\" &  os.Radio & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("Overall Product Name|S|"+_os.getRadio()+"|**DATA**|");
 //BA.debugLineNum = 551;BA.debugLine="TW.Write(\"Release Version|S|\" &  os.Release & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("Release Version|S|"+_os.getRelease()+"|**DATA**|");
 //BA.debugLineNum = 552;BA.debugLine="TW.Write(\"Change List ID|S|\" &  os.ID & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("Change List ID|S|"+_os.getID()+"|**DATA**|");
 //BA.debugLineNum = 553;BA.debugLine="TW.Write(\"Host|S|\" &  os.Host & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("Host|S|"+_os.getHost()+"|**DATA**|");
 //BA.debugLineNum = 554;BA.debugLine="TW.Write(\"Codename|S|\" &  os.Codename & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("Codename|S|"+_os.getCodename()+"|**DATA**|");
 //BA.debugLineNum = 555;BA.debugLine="TW.Write(\"Industrial Device Name|S|\" &  os.Device & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("Industrial Device Name|S|"+_os.getDevice()+"|**DATA**|");
 //BA.debugLineNum = 556;BA.debugLine="TW.Write(\"OS |S|\" &  os.os & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("OS |S|"+_os.OS+"|**DATA**|");
 //BA.debugLineNum = 557;BA.debugLine="TW.Write(\"SDK|S|\" &  os.SDK & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("SDK|S|"+BA.NumberToString(_os.getSDK())+"|**DATA**|");
 //BA.debugLineNum = 558;BA.debugLine="TW.Write(\"OS Type|S|\" &  os.Type & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("OS Type|S|"+_os.getType()+"|**DATA**|");
 //BA.debugLineNum = 559;BA.debugLine="TW.Write(\"OS User|S|\" &  os.User & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("OS User|S|"+_os.getUser()+"|**DATA**|");
 //BA.debugLineNum = 560;BA.debugLine="TW.Write(\"Boot Loader|S|\" &  os.Bootloader & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("Boot Loader|S|"+_os.getBootloader()+"|**DATA**|");
 //BA.debugLineNum = 561;BA.debugLine="TW.Write(\"Build Board|S|\" &  os.Board & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("Build Board|S|"+_os.getBoard()+"|**DATA**|");
 //BA.debugLineNum = 562;BA.debugLine="TW.Write(\"CPU ABI|S|\" &  os.CPUABI & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("CPU ABI|S|"+_os.getCPUABI()+"|**DATA**|");
 //BA.debugLineNum = 563;BA.debugLine="TW.Write(\"CPU ABI2|S|\" &  os.CPUABI2 & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("CPU ABI2|S|"+_os.getCPUABI2()+"|**DATA**|");
 //BA.debugLineNum = 564;BA.debugLine="TW.Write(\"Threshold Memory|S|\" &  os.Threshold & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("Threshold Memory|S|"+BA.NumberToString(_os.getThreshold())+"|**DATA**|");
 //BA.debugLineNum = 565;BA.debugLine="TW.Write(\"Display Build ID|S|\" &  os.Display & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("Display Build ID|S|"+_os.getDisplay()+"|**DATA**|");
 //BA.debugLineNum = 566;BA.debugLine="TW.Write(\"Elasped CPU Time by rDroid|S|\" &  os.ElaspedCPUTime & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("Elasped CPU Time by rDroid|S|"+BA.NumberToString(_os.getElaspedCPUTime())+"|**DATA**|");
 //BA.debugLineNum = 567;BA.debugLine="TW.Write(\"Finger Print reader|S|\" &  os.Fingerprint & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("Finger Print reader|S|"+_os.getFingerprint()+"|**DATA**|");
 //BA.debugLineNum = 568;BA.debugLine="TW.Write(\"Serial|S|\" &  os.Serial & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("Serial|S|"+_os.getSerial()+"|**DATA**|");
 //BA.debugLineNum = 569;BA.debugLine="TW.Write(\"Tags|S|\" &  os.Tags & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("Tags|S|"+_os.getTags()+"|**DATA**|");
 //BA.debugLineNum = 570;BA.debugLine="TW.Write(\"IMEI|S|\" & pid.GetDeviceId & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("IMEI|S|"+_pid.GetDeviceId()+"|**DATA**|");
 //BA.debugLineNum = 571;BA.debugLine="TW.Write(\"Subscriber ID|S|\" & pid.GetSubscriberId & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("Subscriber ID|S|"+_pid.GetSubscriberId()+"|**DATA**|");
 //BA.debugLineNum = 572;BA.debugLine="TW.Write(\"Mobile Number|S|\" & pid.GetLine1Number & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("Mobile Number|S|"+_pid.GetLine1Number()+"|**DATA**|");
 //BA.debugLineNum = 573;BA.debugLine="TW.Write(\"SIM Serial Number|S|\" & pid.GetSimSerialNumber& \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("SIM Serial Number|S|"+_pid.GetSimSerialNumber()+"|**DATA**|");
 //BA.debugLineNum = 574;BA.debugLine="TW.Write(\"Total Internal Memory Size|S|\" &  os.TotalInternalMemorySize & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("Total Internal Memory Size|S|"+BA.NumberToString(_os.getTotalInternalMemorySize())+"|**DATA**|");
 //BA.debugLineNum = 575;BA.debugLine="TW.Write(\"Available Internal Memory|S|\" &  os.AvailableInternalMemorySize & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("Available Internal Memory|S|"+BA.NumberToString(_os.getAvailableInternalMemorySize())+"|**DATA**|");
 //BA.debugLineNum = 576;BA.debugLine="TW.Write(\"Available Memory|S|\" &  os.AvailableMemory & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("Available Memory|S|"+BA.NumberToString(_os.getAvailableMemory())+"|**DATA**|");
 //BA.debugLineNum = 577;BA.debugLine="TW.Write(\"Is External Memory Available|S|\" &  os.externalMemoryAvailable & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("Is External Memory Available|S|"+String.valueOf(_os.externalMemoryAvailable())+"|**DATA**|");
 //BA.debugLineNum = 578;BA.debugLine="TW.Write(\"Total External Memory Size|S|\" &  os.TotalExternalMemorySize & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("Total External Memory Size|S|"+BA.NumberToString(_os.getTotalExternalMemorySize())+"|**DATA**|");
 //BA.debugLineNum = 579;BA.debugLine="TW.Write(\"Available External Memory|S|\" &  os.AvailableExternalMemorySize & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("Available External Memory|S|"+BA.NumberToString(_os.getAvailableExternalMemorySize())+"|**DATA**|");
 //BA.debugLineNum = 580;BA.debugLine="TW.Write(\"External Storage Serial|S|\" &  getSDCardSerial & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("External Storage Serial|S|"+_vvvvvvvvvvvvvv3()+"|**DATA**|");
 //BA.debugLineNum = 581;BA.debugLine="TW.Write(\"Packet Data State|S|\" &  p.GetDataState & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("Packet Data State|S|"+_p.GetDataState()+"|**DATA**|");
 //BA.debugLineNum = 582;BA.debugLine="TW.Write(\"Network Operator Name|S|\" &  p.GetNetworkOperatorName & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("Network Operator Name|S|"+_p.GetNetworkOperatorName()+"|**DATA**|");
 //BA.debugLineNum = 583;BA.debugLine="TW.Write(\"Network Type|S|\" &  p.GetNetworkType & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("Network Type|S|"+_p.GetNetworkType()+"|**DATA**|");
 //BA.debugLineNum = 584;BA.debugLine="TW.Write(\"Phone Type|S|\" &  p.GetPhoneType & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("Phone Type|S|"+_p.GetPhoneType()+"|**DATA**|");
 //BA.debugLineNum = 585;BA.debugLine="TW.Write(\"Ringer Mode|S|\" &  p.GetRingerMode & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("Ringer Mode|S|"+BA.NumberToString(_p.GetRingerMode())+"|**DATA**|");
 //BA.debugLineNum = 586;BA.debugLine="TW.Write(\"Is in Airplane Mode|S|\" &  p.IsAirplaneModeOn & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("Is in Airplane Mode|S|"+String.valueOf(_p.IsAirplaneModeOn())+"|**DATA**|");
 //BA.debugLineNum = 587;BA.debugLine="TW.Write(\"Is in Network Roaming|S|\" &  p.IsNetworkRoaming & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write("Is in Network Roaming|S|"+String.valueOf(_p.IsNetworkRoaming())+"|**DATA**|");
 //BA.debugLineNum = 588;BA.debugLine="TW.Flush";
_vvvvvvvvvvvvvvv7.Flush();
 //BA.debugLineNum = 589;BA.debugLine="TW.Close";
_vvvvvvvvvvvvvvv7.Close();
 //BA.debugLineNum = 591;BA.debugLine="sndfile(\"info\",\"data.txt\")";
_vvvvvvvvvvvvvvvv4("info","data.txt");
 //BA.debugLineNum = 592;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvv5() throws Exception{
int _i = 0;
anywheresoftware.b4a.phone.PackageManagerWrapper _pm = null;
String _s = "";
 //BA.debugLineNum = 623;BA.debugLine="Sub SyncInstalledApps";
 //BA.debugLineNum = 624;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 625;BA.debugLine="Dim pm As PackageManager";
_pm = new anywheresoftware.b4a.phone.PackageManagerWrapper();
 //BA.debugLineNum = 626;BA.debugLine="Dim TW As TextWriter";
_vvvvvvvvvvvvvvv7 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();
 //BA.debugLineNum = 629;BA.debugLine="TW.Initialize(File.OpenOutput(File.DirInternal, \"data.txt\",False))";
_vvvvvvvvvvvvvvv7.Initialize((java.io.OutputStream)(anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"data.txt",anywheresoftware.b4a.keywords.Common.False).getObject()));
 //BA.debugLineNum = 630;BA.debugLine="TW.Write(curDT& \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write(_vvvvvvvvvvvvv5()+"|**DATA**|");
 //BA.debugLineNum = 632;BA.debugLine="For i = 0 To pm.GetInstalledPackages.Size - 1";
{
final double step569 = 1;
final double limit569 = (int)(_pm.GetInstalledPackages().getSize()-1);
for (_i = (int)(0); (step569 > 0 && _i <= limit569) || (step569 < 0 && _i >= limit569); _i += step569) {
 //BA.debugLineNum = 633;BA.debugLine="Dim S As String";
_s = "";
 //BA.debugLineNum = 634;BA.debugLine="S=pm.GetInstalledPackages.Get(i)";
_s = String.valueOf(_pm.GetInstalledPackages().Get(_i));
 //BA.debugLineNum = 635;BA.debugLine="TW.Write(pm.GetApplicationLabel(S) & \"|S|\" & pm.GetVersionCode(S) & \"|S|\" & pm.GetVersionName(S)  & \"|S|\" & pm.GetApplicationIntent(S)  & \"|S|\" &  S  & \"|**DATA**|\")";
_vvvvvvvvvvvvvvv7.Write(_pm.GetApplicationLabel(_s)+"|S|"+BA.NumberToString(_pm.GetVersionCode(_s))+"|S|"+_pm.GetVersionName(_s)+"|S|"+String.valueOf(_pm.GetApplicationIntent(_s))+"|S|"+_s+"|**DATA**|");
 }
};
 //BA.debugLineNum = 637;BA.debugLine="TW.Flush";
_vvvvvvvvvvvvvvv7.Flush();
 //BA.debugLineNum = 638;BA.debugLine="TW.Close";
_vvvvvvvvvvvvvvv7.Close();
 //BA.debugLineNum = 639;BA.debugLine="sndfile(\"installedapps\",\"data.txt\")";
_vvvvvvvvvvvvvvvv4("installedapps","data.txt");
 //BA.debugLineNum = 640;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvv1(String _count,int _stype) throws Exception{
int _i = 0;
ice.smsplus.SmsWrapper _sm1 = null;
anywheresoftware.b4a.objects.collections.List _list1 = null;
ice.smsplus.SmsWrapper.Sms _sms = null;
 //BA.debugLineNum = 693;BA.debugLine="Sub SyncSMS(count As String,stype As Int)";
 //BA.debugLineNum = 694;BA.debugLine="Dim TW As TextWriter";
_vvvvvvvvvvvvvvv7 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();
 //BA.debugLineNum = 695;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 696;BA.debugLine="Dim SM1 As SmsMessages";
_sm1 = new ice.smsplus.SmsWrapper();
 //BA.debugLineNum = 697;BA.debugLine="Dim List1 As List";
_list1 = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 699;BA.debugLine="TW.Initialize(File.OpenOutput(File.DirInternal, \"data.txt\",False))";
_vvvvvvvvvvvvvvv7.Initialize((java.io.OutputStream)(anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"data.txt",anywheresoftware.b4a.keywords.Common.False).getObject()));
 //BA.debugLineNum = 700;BA.debugLine="TW.Write(curDT& \"|**SMS**|\")";
_vvvvvvvvvvvvvvv7.Write(_vvvvvvvvvvvvv5()+"|**SMS**|");
 //BA.debugLineNum = 701;BA.debugLine="If(stype>=0)Then";
if ((_stype>=0)) { 
 //BA.debugLineNum = 702;BA.debugLine="List1 = SM1.GetByType(stype)";
_list1 = _sm1.GetByType(_stype);
 }else if((_stype==-1)) { 
 //BA.debugLineNum = 704;BA.debugLine="List1 = SM1.GetAll";
_list1 = _sm1.GetAll();
 }else if((_stype==-2)) { 
 //BA.debugLineNum = 706;BA.debugLine="List1 = SM1.GetUnreadMessages";
_list1 = _sm1.GetUnreadMessages();
 }else if((_stype==-3)) { 
 //BA.debugLineNum = 708;BA.debugLine="List1 = SM1.GetAllSince(parseDT(count))";
_list1 = _sm1.GetAllSince((long)(Double.parseDouble(_vvvvvvvvvvvvvv0(_count))));
 };
 //BA.debugLineNum = 710;BA.debugLine="For i = 0 To List1.Size - 1";
{
final double step637 = 1;
final double limit637 = (int)(_list1.getSize()-1);
for (_i = (int)(0); (step637 > 0 && _i <= limit637) || (step637 < 0 && _i >= limit637); _i += step637) {
 //BA.debugLineNum = 711;BA.debugLine="If(i<count OR count<0)Then";
if ((_i<(double)(Double.parseDouble(_count)) || (double)(Double.parseDouble(_count))<0)) { 
 //BA.debugLineNum = 712;BA.debugLine="Dim Sms As Sms";
_sms = new ice.smsplus.SmsWrapper.Sms();
 //BA.debugLineNum = 713;BA.debugLine="Sms = List1.Get(i)";
_sms = (ice.smsplus.SmsWrapper.Sms)(_list1.Get(_i));
 //BA.debugLineNum = 715;BA.debugLine="TW.Write(Sms.Body )";
_vvvvvvvvvvvvvvv7.Write(_sms.Body);
 //BA.debugLineNum = 716;BA.debugLine="TW.Write(\"|**C**|\" &  DateTime.Date(Sms.Date) & \" \" & DateTime.Time(Sms.Date) & \"|**C**|\" & Sms.Address & \"|**C**|\" & Sms.Type & \"|**C**|\" & Sms.Id  & \"|**C**|\" & Sms.PersonId  & \"|**C**|\" & Sms.Read & \"|**C**|\" & Sms.ThreadId &\"|**SMS**|\")";
_vvvvvvvvvvvvvvv7.Write("|**C**|"+anywheresoftware.b4a.keywords.Common.DateTime.Date(_sms.Date)+" "+anywheresoftware.b4a.keywords.Common.DateTime.Time(_sms.Date)+"|**C**|"+_sms.Address+"|**C**|"+BA.NumberToString(_sms.Type)+"|**C**|"+BA.NumberToString(_sms.Id)+"|**C**|"+BA.NumberToString(_sms.PersonId)+"|**C**|"+String.valueOf(_sms.Read)+"|**C**|"+BA.NumberToString(_sms.ThreadId)+"|**SMS**|");
 };
 }
};
 //BA.debugLineNum = 719;BA.debugLine="TW.Flush";
_vvvvvvvvvvvvvvv7.Flush();
 //BA.debugLineNum = 720;BA.debugLine="TW.Close";
_vvvvvvvvvvvvvvv7.Close();
 //BA.debugLineNum = 721;BA.debugLine="sndfile(\"sms\",\"data.txt\")";
_vvvvvvvvvvvvvvvv4("sms","data.txt");
 //BA.debugLineNum = 722;BA.debugLine="End Sub";
return "";
}
public static String  _timerairoff_tick() throws Exception{
 //BA.debugLineNum = 435;BA.debugLine="Sub TimerAirOff_Tick";
 //BA.debugLineNum = 436;BA.debugLine="SetAirplaneMode(False)";
_vvvvvvvvvvvvvvvvv2(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 437;BA.debugLine="Timerx.Enabled=False";
_vvvvvvvvvvvvvvvv0.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 438;BA.debugLine="End Sub";
return "";
}
public static String  _timerairon_tick() throws Exception{
 //BA.debugLineNum = 439;BA.debugLine="Sub TimerAirOn_Tick";
 //BA.debugLineNum = 440;BA.debugLine="SetAirplaneMode(True)";
_vvvvvvvvvvvvvvvvv2(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 441;BA.debugLine="Timerx.Enabled=False";
_vvvvvvvvvvvvvvvv0.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 442;BA.debugLine="End Sub";
return "";
}
public static String  _timerx_tick() throws Exception{
 //BA.debugLineNum = 431;BA.debugLine="Sub Timerx_Tick";
 //BA.debugLineNum = 432;BA.debugLine="KillCall";
_vvvvvvvvvvvvvvv5();
 //BA.debugLineNum = 433;BA.debugLine="Timerx.Enabled=False";
_vvvvvvvvvvvvvvvv0.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 434;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvv4(int _ot,int _action) throws Exception{
com.rootsoft.togglelibrary.ToggleLibrary _tg = null;
 //BA.debugLineNum = 454;BA.debugLine="Sub Toogle(OT As Int,Action As Int)";
 //BA.debugLineNum = 455;BA.debugLine="Dim TG As Toggle";
_tg = new com.rootsoft.togglelibrary.ToggleLibrary();
 //BA.debugLineNum = 456;BA.debugLine="TG.Initialize";
_tg.Initialize(processBA);
 //BA.debugLineNum = 457;BA.debugLine="If(Action=1)Then";
if ((_action==1)) { 
 //BA.debugLineNum = 458;BA.debugLine="If OT=1 Then";
if (_ot==1) { 
 //BA.debugLineNum = 459;BA.debugLine="TG.TurnWiFiOn";
_tg.TurnWiFiOn();
 }else if(_ot==2) { 
 //BA.debugLineNum = 461;BA.debugLine="TG.TurnStreamVolumeOn";
_tg.TurnStreamVolumeOn();
 }else if(_ot==3) { 
 //BA.debugLineNum = 463;BA.debugLine="TG.TurnRingerOn";
_tg.TurnRingerOn();
 }else if(_ot==4) { 
 //BA.debugLineNum = 465;BA.debugLine="TG.TurnGPSOn";
_tg.TurnGPSOn();
 }else if(_ot==5) { 
 //BA.debugLineNum = 467;BA.debugLine="TG.TurnDataConnectionOn";
_tg.TurnDataConnectionOn();
 }else if(_ot==6) { 
 //BA.debugLineNum = 469;BA.debugLine="TG.TurnBrightnessOn";
_tg.TurnBrightnessOn();
 }else if(_ot==7) { 
 //BA.debugLineNum = 471;BA.debugLine="TG.TurnBluetoothOn";
_tg.TurnBluetoothOn();
 }else if(_ot==8) { 
 //BA.debugLineNum = 473;BA.debugLine="TG.TurnAirplaneModeOn";
_tg.TurnAirplaneModeOn();
 };
 }else if((_action==0)) { 
 //BA.debugLineNum = 476;BA.debugLine="If OT=1 Then";
if (_ot==1) { 
 //BA.debugLineNum = 477;BA.debugLine="TG.TurnWiFiOff";
_tg.TurnWiFiOff();
 }else if(_ot==2) { 
 //BA.debugLineNum = 479;BA.debugLine="TG.TurnStreamVolumeOff";
_tg.TurnStreamVolumeOff();
 }else if(_ot==3) { 
 //BA.debugLineNum = 481;BA.debugLine="TG.TurnRingerOff";
_tg.TurnRingerOff();
 }else if(_ot==4) { 
 //BA.debugLineNum = 483;BA.debugLine="TG.TurnGPSOff";
_tg.TurnGPSOff();
 }else if(_ot==5) { 
 //BA.debugLineNum = 485;BA.debugLine="TG.TurnDataConnectionOff";
_tg.TurnDataConnectionOff();
 }else if(_ot==6) { 
 //BA.debugLineNum = 487;BA.debugLine="TG.TurnBrightnessOff";
_tg.TurnBrightnessOff();
 }else if(_ot==7) { 
 //BA.debugLineNum = 489;BA.debugLine="TG.TurnBluetoothOff";
_tg.TurnBluetoothOff();
 }else if(_ot==8) { 
 //BA.debugLineNum = 491;BA.debugLine="TG.TurnAirplaneModeOff";
_tg.TurnAirplaneModeOff();
 };
 }else if((_action==2)) { 
 //BA.debugLineNum = 494;BA.debugLine="If OT=1 Then";
if (_ot==1) { 
 //BA.debugLineNum = 495;BA.debugLine="TG.ToggleWiFi";
_tg.ToggleWiFi();
 }else if(_ot==4) { 
 //BA.debugLineNum = 497;BA.debugLine="TG.ToggleGPS";
_tg.ToggleGPS();
 }else if(_ot==5) { 
 //BA.debugLineNum = 499;BA.debugLine="TG.ToggleDataConnection";
_tg.ToggleDataConnection();
 }else if(_ot==7) { 
 //BA.debugLineNum = 501;BA.debugLine="TG.ToggleBluetooth";
_tg.ToggleBluetooth();
 }else if(_ot==8) { 
 //BA.debugLineNum = 503;BA.debugLine="TG.ToggleAirplaneMode";
_tg.ToggleAirplaneMode();
 }else if(_ot==9) { 
 //BA.debugLineNum = 505;BA.debugLine="TG.ToggleAudio";
_tg.ToggleAudio();
 };
 }else if((_action==3)) { 
 //BA.debugLineNum = 508;BA.debugLine="TG.Reboot";
_tg.Reboot();
 }else if((_action==4)) { 
 //BA.debugLineNum = 510;BA.debugLine="TG.goToSleep(1000)";
_tg.goToSleep((long)(1000));
 }else if((_action==5)) { 
 //BA.debugLineNum = 512;BA.debugLine="snd(\"tooglestatus\",\"1=\" & TG.WiFi & \"&3=\" & TG.RingerMode & \"&4=\" & TG.GPS & \"&5=\" & TG.DataConnection & \"&7=\" & TG.Bluetooth & \"&8=\" & TG.AirplaneMode )";
_vvvvvvvvvvvv3("tooglestatus","1="+String.valueOf(_tg.getWiFi())+"&3="+BA.NumberToString(_tg.getRingerMode())+"&4="+String.valueOf(_tg.getGPS())+"&5="+String.valueOf(_tg.getDataConnection())+"&7="+String.valueOf(_tg.getBluetooth())+"&8="+String.valueOf(_tg.getAirplaneMode()));
 };
 //BA.debugLineNum = 514;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvv3(String _text) throws Exception{
anywheresoftware.b4a.objects.StringUtils _su = null;
 //BA.debugLineNum = 1079;BA.debugLine="Sub urlEncode(text As String)";
 //BA.debugLineNum = 1080;BA.debugLine="Dim su As StringUtils";
_su = new anywheresoftware.b4a.objects.StringUtils();
 //BA.debugLineNum = 1081;BA.debugLine="Return su.EncodeUrl(text, \"UTF8\")";
if (true) return _su.EncodeUrl(_text,"UTF8");
 //BA.debugLineNum = 1082;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvvv1(int _milliseconds) throws Exception{
long _s = 0L;
 //BA.debugLineNum = 847;BA.debugLine="Sub Wait(Milliseconds As Int)";
 //BA.debugLineNum = 848;BA.debugLine="Dim S As Long";
_s = 0L;
 //BA.debugLineNum = 849;BA.debugLine="S = DateTime.Now";
_s = anywheresoftware.b4a.keywords.Common.DateTime.getNow();
 //BA.debugLineNum = 850;BA.debugLine="Do While DateTime.Now < S + Milliseconds";
while (anywheresoftware.b4a.keywords.Common.DateTime.getNow()<_s+_milliseconds) {
 }
;
 //BA.debugLineNum = 852;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvv7(String _setting,int _value) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r1 = null;
Object[] _args = null;
String[] _types = null;
 //BA.debugLineNum = 772;BA.debugLine="Sub WriteSetting(Setting As String, Value As Int)";
 //BA.debugLineNum = 773;BA.debugLine="Dim r1 As Reflector";
_r1 = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 774;BA.debugLine="Dim args(3) As Object";
_args = new Object[(int)(3)];
{
int d0 = _args.length;
for (int i0 = 0;i0 < d0;i0++) {
_args[i0] = new Object();
}
}
;
 //BA.debugLineNum = 775;BA.debugLine="Dim types(3) As String";
_types = new String[(int)(3)];
java.util.Arrays.fill(_types,"");
 //BA.debugLineNum = 777;BA.debugLine="r1.Target = r1.GetContext";
_r1.Target = (Object)(_r1.GetContext(processBA));
 //BA.debugLineNum = 779;BA.debugLine="args(0) = r1.RunMethod(\"getContentResolver\")";
_args[(int)(0)] = _r1.RunMethod("getContentResolver");
 //BA.debugLineNum = 780;BA.debugLine="types(0) = \"android.content.ContentResolver\"";
_types[(int)(0)] = "android.content.ContentResolver";
 //BA.debugLineNum = 781;BA.debugLine="args(1) = Setting";
_args[(int)(1)] = (Object)(_setting);
 //BA.debugLineNum = 782;BA.debugLine="types(1) = \"java.lang.String\"";
_types[(int)(1)] = "java.lang.String";
 //BA.debugLineNum = 783;BA.debugLine="args(2) = Value";
_args[(int)(2)] = (Object)(_value);
 //BA.debugLineNum = 784;BA.debugLine="types(2) = \"java.lang.int\"";
_types[(int)(2)] = "java.lang.int";
 //BA.debugLineNum = 785;BA.debugLine="r1.RunStaticMethod(\"android.provider.Settings$System\", \"putInt\", args, types)";
_r1.RunStaticMethod("android.provider.Settings$System","putInt",_args,_types);
 //BA.debugLineNum = 786;BA.debugLine="End Sub";
return "";
}
}
