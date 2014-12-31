package madsacsoft.maddev.rdroid;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.debug.*;

public class submitter extends android.app.Service {
	public static class submitter_BR extends android.content.BroadcastReceiver {

		@Override
		public void onReceive(android.content.Context context, android.content.Intent intent) {
			android.content.Intent in = new android.content.Intent(context, submitter.class);
			if (intent != null)
				in.putExtra("b4a_internal_intent", intent);
			context.startService(in);
		}

	}
    static submitter mostCurrent;
	public static BA processBA;
    private ServiceHelper _service;
    public static Class<?> getObject() {
		return submitter.class;
	}
	@Override
	public void onCreate() {
        mostCurrent = this;
        if (processBA == null) {
		    processBA = new BA(this, null, null, "madsacsoft.maddev.rdroid", "madsacsoft.maddev.rdroid.submitter");
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
        anywheresoftware.b4a.keywords.Common.Log("** Service (submitter) Create **");
        processBA.raiseEvent(null, "service_create");
    }
		@Override
	public void onStart(android.content.Intent intent, int startId) {
		handleStart(intent);
    }
    @Override
    public int onStartCommand(android.content.Intent intent, int flags, int startId) {
    	handleStart(intent);
		return android.app.Service.START_NOT_STICKY;
    }
    private void handleStart(android.content.Intent intent) {
    	anywheresoftware.b4a.keywords.Common.Log("** Service (submitter) Start **");
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
        anywheresoftware.b4a.keywords.Common.Log("** Service (submitter) Destroy **");
		processBA.raiseEvent(null, "service_destroy");
        processBA.service = null;
		mostCurrent = null;
		processBA.setActivityPaused(true);
	}
public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.http.HttpClientWrapper _vvvvvvvvvvvv0 = null;
public static anywheresoftware.b4a.objects.collections.Map _v5 = null;
public static String _v6 = "";
public static int _vvvvvvvvvvvvv1 = 0;
public madsacsoft.maddev.rdroid.main _vvvvv0 = null;
public madsacsoft.maddev.rdroid.client _vvvvvv2 = null;
public madsacsoft.maddev.rdroid.utils _vvvvvv3 = null;
public static String  _vvvvvvvvvvvv7(int _taskid,boolean _success,String _errormessage) throws Exception{
madsacsoft.maddev.rdroid.httpjob _job = null;
 //BA.debugLineNum = 65;BA.debugLine="Sub CompleteJob(TaskId As Int, success As Boolean, errorMessage As String)";
 //BA.debugLineNum = 66;BA.debugLine="Dim job As HttpJob";
_job = new madsacsoft.maddev.rdroid.httpjob();
 //BA.debugLineNum = 67;BA.debugLine="job = TaskIdToJob.Get(TaskId)";
_job = (madsacsoft.maddev.rdroid.httpjob)(_v5.Get((Object)(_taskid)));
 //BA.debugLineNum = 68;BA.debugLine="TaskIdToJob.Remove(TaskId)";
_v5.Remove((Object)(_taskid));
 //BA.debugLineNum = 69;BA.debugLine="job.success = success";
_job._vvvv0 = _success;
 //BA.debugLineNum = 70;BA.debugLine="job.errorMessage = errorMessage";
_job._vvvvv3 = _errormessage;
 //BA.debugLineNum = 71;BA.debugLine="job.Complete(TaskId)";
_job._vvv3(_taskid);
 //BA.debugLineNum = 72;BA.debugLine="End Sub";
return "";
}
public static String  _hc_responseerror(anywheresoftware.b4a.http.HttpClientWrapper.HttpResponeWrapper _response,String _reason,int _statuscode,int _taskid) throws Exception{
 //BA.debugLineNum = 53;BA.debugLine="Sub hc_ResponseError (Response As HttpResponse, Reason As String, StatusCode As Int, TaskId As Int)";
 //BA.debugLineNum = 54;BA.debugLine="If Response <> Null Then";
if (_response!= null) { 
 //BA.debugLineNum = 55;BA.debugLine="Try";
try { } 
       catch (Exception e39) {
			processBA.setLastException(e39); };
 //BA.debugLineNum = 60;BA.debugLine="Response.Release";
_response.Release();
 };
 //BA.debugLineNum = 62;BA.debugLine="CompleteJob(TaskId, False, Reason)";
_vvvvvvvvvvvv7(_taskid,anywheresoftware.b4a.keywords.Common.False,_reason);
 //BA.debugLineNum = 63;BA.debugLine="End Sub";
return "";
}
public static String  _hc_responsesuccess(anywheresoftware.b4a.http.HttpClientWrapper.HttpResponeWrapper _response,int _taskid) throws Exception{
 //BA.debugLineNum = 40;BA.debugLine="Sub hc_ResponseSuccess (Response As HttpResponse, TaskId As Int)";
 //BA.debugLineNum = 41;BA.debugLine="Response.GetAsynchronously(\"response\", File.OpenOutput(TempFolder, TaskId, False), _ 		True, TaskId)";
_response.GetAsynchronously(processBA,"response",(java.io.OutputStream)(anywheresoftware.b4a.keywords.Common.File.OpenOutput(_v6,BA.NumberToString(_taskid),anywheresoftware.b4a.keywords.Common.False).getObject()),anywheresoftware.b4a.keywords.Common.True,_taskid);
 //BA.debugLineNum = 43;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 7;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="Private hc As HttpClient";
_vvvvvvvvvvvv0 = new anywheresoftware.b4a.http.HttpClientWrapper();
 //BA.debugLineNum = 9;BA.debugLine="Public TaskIdToJob As Map";
_v5 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 10;BA.debugLine="Public TempFolder";
_v6 = "";
 //BA.debugLineNum = 11;BA.debugLine="Private taskCounter As Int";
_vvvvvvvvvvvvv1 = 0;
 //BA.debugLineNum = 12;BA.debugLine="End Sub";
return "";
}
public static String  _response_streamfinish(boolean _success,int _taskid) throws Exception{
 //BA.debugLineNum = 45;BA.debugLine="Sub Response_StreamFinish (Success As Boolean, TaskId As Int)";
 //BA.debugLineNum = 46;BA.debugLine="If Success Then";
if (_success) { 
 //BA.debugLineNum = 47;BA.debugLine="CompleteJob(TaskId, Success, \"\")";
_vvvvvvvvvvvv7(_taskid,_success,"");
 }else {
 //BA.debugLineNum = 49;BA.debugLine="CompleteJob(TaskId, Success, LastException.Message)";
_vvvvvvvvvvvv7(_taskid,_success,anywheresoftware.b4a.keywords.Common.LastException(processBA).getMessage());
 };
 //BA.debugLineNum = 51;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
 //BA.debugLineNum = 14;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 15;BA.debugLine="TempFolder = File.DirInternalCache";
_v6 = anywheresoftware.b4a.keywords.Common.File.getDirInternalCache();
 //BA.debugLineNum = 16;BA.debugLine="hc.Initialize(\"hc\")";
_vvvvvvvvvvvv0.Initialize("hc");
 //BA.debugLineNum = 17;BA.debugLine="TaskIdToJob.Initialize";
_v5.Initialize();
 //BA.debugLineNum = 19;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 25;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 27;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
 //BA.debugLineNum = 21;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 23;BA.debugLine="End Sub";
return "";
}
public static int  _submitjob(madsacsoft.maddev.rdroid.httpjob _job) throws Exception{
 //BA.debugLineNum = 29;BA.debugLine="Public Sub SubmitJob(job As HttpJob) As Int";
 //BA.debugLineNum = 30;BA.debugLine="taskCounter = taskCounter + 1";
_vvvvvvvvvvvvv1 = (int)(_vvvvvvvvvvvvv1+1);
 //BA.debugLineNum = 31;BA.debugLine="TaskIdToJob.Put(taskCounter, job)";
_v5.Put((Object)(_vvvvvvvvvvvvv1),(Object)(_job));
 //BA.debugLineNum = 32;BA.debugLine="If job.Username <> \"\" AND job.Password <> \"\" Then";
if ((_job._vvvvv1).equals("") == false && (_job._vvvvv2).equals("") == false) { 
 //BA.debugLineNum = 33;BA.debugLine="hc.ExecuteCredentials(job.GetRequest, taskCounter, job.Username, job.Password)";
_vvvvvvvvvvvv0.ExecuteCredentials(processBA,_job._vvv0(),_vvvvvvvvvvvvv1,_job._vvvvv1,_job._vvvvv2);
 }else {
 //BA.debugLineNum = 35;BA.debugLine="hc.Execute(job.GetRequest, taskCounter)";
_vvvvvvvvvvvv0.Execute(processBA,_job._vvv0(),_vvvvvvvvvvvvv1);
 };
 //BA.debugLineNum = 37;BA.debugLine="Return taskCounter";
if (true) return _vvvvvvvvvvvvv1;
 //BA.debugLineNum = 38;BA.debugLine="End Sub";
return 0;
}
}
