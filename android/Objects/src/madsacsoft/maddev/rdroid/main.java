package madsacsoft.maddev.rdroid;

import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class main extends Activity implements B4AActivity{
	public static main mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	private static final boolean fullScreen = false;
	private static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "madsacsoft.maddev.rdroid", "madsacsoft.maddev.rdroid.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                anywheresoftware.b4a.keywords.Common.Log("Killing previous instance (main).");
				p.finish();
			}
		}
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
		BA.handler.postDelayed(new WaitForLayout(), 5);

	}
	private static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "madsacsoft.maddev.rdroid", "madsacsoft.maddev.rdroid.main");
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        initializeProcessGlobals();		
        initializeGlobals();
        
        anywheresoftware.b4a.keywords.Common.Log("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        anywheresoftware.b4a.keywords.Common.Log("** Activity (main) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
		return true;
	}
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEvent(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return main.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true)
				return true;
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
		this.setIntent(intent);
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null) //workaround for emulator bug (Issue 2423)
            return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        anywheresoftware.b4a.keywords.Common.Log("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            anywheresoftware.b4a.keywords.Common.Log("** Activity (main) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}

public static class _appsettings{
public boolean IsInitialized;
public String user;
public String password;
public String name;
public void Initialize() {
IsInitialized = true;
user = "";
password = "";
name = "";
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.randomaccessfile.RandomAccessFile _v0 = null;
public static String _vvv2 = "";
public anywheresoftware.b4a.objects.ButtonWrapper _vvvvvvvvvv6 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _vvvvvvvvvv7 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _vvvvvvvvvv0 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _vvvvvvvvvvv1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _vvvvvvvvvvv2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _rgstr = null;
public anywheresoftware.b4a.objects.ButtonWrapper _add_logger = null;
public anywheresoftware.b4a.objects.ButtonWrapper _update_logger = null;
public anywheresoftware.b4a.objects.ButtonWrapper _loggers = null;
public anywheresoftware.b4a.objects.ButtonWrapper _stopserv = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvvvv3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvvvv4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbldevice = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblusr = null;
public anywheresoftware.b4a.objects.LabelWrapper _servstatus = null;
public anywheresoftware.b4a.objects.EditTextWrapper _vvvvvvvvvvv5 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _vvvvvvvvvvv6 = null;
public anywheresoftware.b4a.objects.PanelWrapper _vvvvvvvvvv1 = null;
public anywheresoftware.b4a.objects.PanelWrapper _vvvvvvvvvvv7 = null;
public static boolean _vvvvvvvvvv3 = false;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _vvvvvvvvvv2 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _vvvvvvvvvvv0 = null;
public anywheresoftware.b4a.objects.ListViewWrapper _logger_list = null;
public anywheresoftware.b4a.objects.collections.Map _vv2 = null;
public madsacsoft.maddev.rdroid.slidemenu _vvvvvvvvvv5 = null;
public madsacsoft.maddev.rdroid.main._appsettings _vv1 = null;
public anywheresoftware.b4a.objects.PanelWrapper _actionbar = null;
public anywheresoftware.b4a.objects.ButtonWrapper _reg_ok = null;
public anywheresoftware.b4a.objects.EditTextWrapper _reg_pass1 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _reg_pass2 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _reg_user = null;
public madsacsoft.maddev.rdroid.submitter _vvvvvv1 = null;
public madsacsoft.maddev.rdroid.client _vvvvvv2 = null;
public madsacsoft.maddev.rdroid.utils _vvvvvv3 = null;
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 39;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 40;BA.debugLine="Activity.LoadLayout(\"blank\")";
mostCurrent._activity.LoadLayout("blank",mostCurrent.activityBA);
 //BA.debugLineNum = 41;BA.debugLine="home.Initialize(\"\")";
mostCurrent._vvvvvvvvvv1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 42;BA.debugLine="home.LoadLayout(\"main\")";
mostCurrent._vvvvvvvvvv1.LoadLayout("main",mostCurrent.activityBA);
 //BA.debugLineNum = 43;BA.debugLine="Activity.AddView(home,0,0,100%x,100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._vvvvvvvvvv1.getObject()),(int)(0),(int)(0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float)(100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float)(100),mostCurrent.activityBA));
 //BA.debugLineNum = 44;BA.debugLine="home.Visible=True";
mostCurrent._vvvvvvvvvv1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 45;BA.debugLine="Bmp.Initialize(File.DirAssets, \"android48.png\")";
mostCurrent._vvvvvvvvvv2.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"android48.png");
 //BA.debugLineNum = 46;BA.debugLine="updt=False";
_vvvvvvvvvv3 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 47;BA.debugLine="ReadSettings";
_vvvvvvvvvv4();
 //BA.debugLineNum = 48;BA.debugLine="If File.Exists(File.DirRootExternal, \"/rDroid\")==False Then File.MakeDir(File.DirRootExternal, \"/rDroid\")";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"/rDroid")==anywheresoftware.b4a.keywords.Common.False) { 
anywheresoftware.b4a.keywords.Common.File.MakeDir(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"/rDroid");};
 //BA.debugLineNum = 49;BA.debugLine="If(client.actVisible==True)Then ExitApplication";
if ((mostCurrent._vvvvvv2._v7==anywheresoftware.b4a.keywords.Common.True)) { 
anywheresoftware.b4a.keywords.Common.ExitApplication();};
 //BA.debugLineNum = 59;BA.debugLine="sm.Initialize(Activity, Me, \"SlideMenu\", 42dip, 180dip)";
mostCurrent._vvvvvvvvvv5._vvv1(mostCurrent.activityBA,mostCurrent._activity,main.getObject(),"SlideMenu",anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(42)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(180)));
 //BA.debugLineNum = 60;BA.debugLine="sm.AddItem(\"Home\", LoadBitmap(File.DirAssets, \"Home.ico\"), 1)";
mostCurrent._vvvvvvvvvv5._vvvvvvvv6("Home",anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Home.ico"),(Object)(1));
 //BA.debugLineNum = 61;BA.debugLine="sm.AddItem(\"Settings\",  LoadBitmap(File.DirAssets, \"Settings.ico\"), 2)";
mostCurrent._vvvvvvvvvv5._vvvvvvvv6("Settings",anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Settings.ico"),(Object)(2));
 //BA.debugLineNum = 62;BA.debugLine="sm.AddItem(\"Help\", LoadBitmap(File.DirAssets, \"Help.ico\"), 3)";
mostCurrent._vvvvvvvvvv5._vvvvvvvv6("Help",anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Help.ico"),(Object)(3));
 //BA.debugLineNum = 63;BA.debugLine="sm.AddItem(\"Contact\", LoadBitmap(File.DirAssets, \"Burn.png\"), 4)";
mostCurrent._vvvvvvvvvv5._vvvvvvvv6("Contact",anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Burn.png"),(Object)(4));
 //BA.debugLineNum = 64;BA.debugLine="sm.AddItem(\"About\",  LoadBitmap(File.DirAssets, \"Info.ico\"), 5)";
mostCurrent._vvvvvvvvvv5._vvvvvvvv6("About",anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Info.ico"),(Object)(5));
 //BA.debugLineNum = 69;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 71;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 73;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK AND sm.isVisible Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK && mostCurrent._vvvvvvvvvv5._vvvvvvvv0()) { 
 //BA.debugLineNum = 74;BA.debugLine="sm.Hide";
mostCurrent._vvvvvvvvvv5._vvvvvvvv7();
 //BA.debugLineNum = 75;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 79;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_MENU Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_MENU) { 
 //BA.debugLineNum = 80;BA.debugLine="If sm.isVisible Then sm.Hide Else sm.Show";
if (mostCurrent._vvvvvvvvvv5._vvvvvvvv0()) { 
mostCurrent._vvvvvvvvvv5._vvvvvvvv7();}
else {
mostCurrent._vvvvvvvvvv5._vvvvvvvvv1();};
 //BA.debugLineNum = 81;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 85;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 90;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 92;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 86;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 87;BA.debugLine="ReadSettings";
_vvvvvvvvvv4();
 //BA.debugLineNum = 88;BA.debugLine="End Sub";
return "";
}
public static String  _add_logger_click() throws Exception{
 //BA.debugLineNum = 265;BA.debugLine="Sub add_logger_Click";
 //BA.debugLineNum = 266;BA.debugLine="logger_add(False)";
_logger_add(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 267;BA.debugLine="End Sub";
return "";
}
public static String  _btnshow_click() throws Exception{
 //BA.debugLineNum = 129;BA.debugLine="Sub btnShow_Click";
 //BA.debugLineNum = 130;BA.debugLine="sm.Show";
mostCurrent._vvvvvvvvvv5._vvvvvvvvv1();
 //BA.debugLineNum = 131;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (processGlobalsRun == false) {
	    processGlobalsRun = true;
		try {
		        main._process_globals();
submitter._process_globals();
client._process_globals();
utils._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
return vis;}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 20;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 21;BA.debugLine="Dim startlisten,stoplisten,open,register,lgn,rgstr,add_logger,update_logger,loggers,stopServ As Button";
mostCurrent._vvvvvvvvvv6 = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._vvvvvvvvvv7 = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._vvvvvvvvvv0 = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._vvvvvvvvvvv1 = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._vvvvvvvvvvv2 = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._rgstr = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._add_logger = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._update_logger = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._loggers = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._stopserv = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Dim Label1,Label2,lbldevice,lblusr,servStatus As Label";
mostCurrent._vvvvvvvvvvv3 = new anywheresoftware.b4a.objects.LabelWrapper();
mostCurrent._vvvvvvvvvvv4 = new anywheresoftware.b4a.objects.LabelWrapper();
mostCurrent._lbldevice = new anywheresoftware.b4a.objects.LabelWrapper();
mostCurrent._lblusr = new anywheresoftware.b4a.objects.LabelWrapper();
mostCurrent._servstatus = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Dim uid,link As EditText";
mostCurrent._vvvvvvvvvvv5 = new anywheresoftware.b4a.objects.EditTextWrapper();
mostCurrent._vvvvvvvvvvv6 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Dim home,frun As Panel";
mostCurrent._vvvvvvvvvv1 = new anywheresoftware.b4a.objects.PanelWrapper();
mostCurrent._vvvvvvvvvvv7 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Dim updt As Boolean";
_vvvvvvvvvv3 = false;
 //BA.debugLineNum = 26;BA.debugLine="Dim Bmp As Bitmap";
mostCurrent._vvvvvvvvvv2 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Dim ImageView1 As ImageView";
mostCurrent._vvvvvvvvvvv0 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Dim logger_list As ListView";
mostCurrent._logger_list = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Dim MP As Map";
mostCurrent._vv2 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 30;BA.debugLine="Dim sm As SlideMenu";
mostCurrent._vvvvvvvvvv5 = new madsacsoft.maddev.rdroid.slidemenu();
 //BA.debugLineNum = 31;BA.debugLine="Dim ST As appSettings";
mostCurrent._vv1 = new madsacsoft.maddev.rdroid.main._appsettings();
 //BA.debugLineNum = 32;BA.debugLine="Dim ActionBar As Panel";
mostCurrent._actionbar = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Dim reg_ok As Button";
mostCurrent._reg_ok = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Dim reg_pass1 As EditText";
mostCurrent._reg_pass1 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Dim reg_pass2 As EditText";
mostCurrent._reg_pass2 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Dim reg_user As EditText";
mostCurrent._reg_user = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 37;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(madsacsoft.maddev.rdroid.httpjob _job) throws Exception{
String[] _splt = null;
String _res = "";
String[] _sp2 = null;
int _x = 0;
 //BA.debugLineNum = 167;BA.debugLine="Sub JobDone (Job As HttpJob)";
 //BA.debugLineNum = 168;BA.debugLine="If(Job.Success==True  )Then";
if ((_job._vvvv0==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 169;BA.debugLine="Log(Job.JobName & \":\" & Job.GetString)";
anywheresoftware.b4a.keywords.Common.Log(_job._vvvv7+":"+_job._vvvv1());
 //BA.debugLineNum = 170;BA.debugLine="Dim splt() As String";
_splt = new String[(int)(0)];
java.util.Arrays.fill(_splt,"");
 //BA.debugLineNum = 171;BA.debugLine="splt=Regex.Split(\"==>\", Job.GetString)";
_splt = anywheresoftware.b4a.keywords.Common.Regex.Split("==>",_job._vvvv1());
 //BA.debugLineNum = 172;BA.debugLine="splt=Regex.Split(\"<==\",splt(1))";
_splt = anywheresoftware.b4a.keywords.Common.Regex.Split("<==",_splt[(int)(1)]);
 //BA.debugLineNum = 173;BA.debugLine="Dim res As String";
_res = "";
 //BA.debugLineNum = 174;BA.debugLine="If splt.Length>0 Then res=splt(0)";
if (_splt.length>0) { 
_res = _splt[(int)(0)];};
 //BA.debugLineNum = 176;BA.debugLine="If(Job.JobName.CompareTo(\"register\")==0)Then";
if ((_job._vvvv7.compareTo("register")==0)) { 
 //BA.debugLineNum = 177;BA.debugLine="If(res.StartsWith(\"1\")==True)Then";
if ((_res.startsWith("1")==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 178;BA.debugLine="home.RemoveAllViews";
mostCurrent._vvvvvvvvvv1.RemoveAllViews();
 //BA.debugLineNum = 179;BA.debugLine="home.LoadLayout(\"main\")";
mostCurrent._vvvvvvvvvv1.LoadLayout("main",mostCurrent.activityBA);
 //BA.debugLineNum = 180;BA.debugLine="SaveSettings";
_vvvvvvvvvvvv1();
 }else {
 //BA.debugLineNum = 182;BA.debugLine="ReadSettings";
_vvvvvvvvvv4();
 };
 //BA.debugLineNum = 184;BA.debugLine="Msgbox(res.SubString(1),\"Registration result !\")";
anywheresoftware.b4a.keywords.Common.Msgbox(_res.substring((int)(1)),"Registration result !",mostCurrent.activityBA);
 }else if((_job._vvvv7.compareTo("login")==0)) { 
 //BA.debugLineNum = 186;BA.debugLine="If(res.CompareTo(\"1\")==0)Then login_home(False) Else login_usr(True)";
if ((_res.compareTo("1")==0)) { 
_login_home(anywheresoftware.b4a.keywords.Common.False);}
else {
_login_usr(anywheresoftware.b4a.keywords.Common.True);};
 }else if((_job._vvvv7.compareTo("addlogger")==0)) { 
 //BA.debugLineNum = 188;BA.debugLine="If(res.StartsWith(\"1\")==True)Then";
if ((_res.startsWith("1")==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 189;BA.debugLine="home.RemoveAllViews";
mostCurrent._vvvvvvvvvv1.RemoveAllViews();
 //BA.debugLineNum = 190;BA.debugLine="home.LoadLayout(\"main\")";
mostCurrent._vvvvvvvvvv1.LoadLayout("main",mostCurrent.activityBA);
 //BA.debugLineNum = 191;BA.debugLine="SaveSettings";
_vvvvvvvvvvvv1();
 //BA.debugLineNum = 192;BA.debugLine="Msgbox(res.SubString(1),\"Registered\")";
anywheresoftware.b4a.keywords.Common.Msgbox(_res.substring((int)(1)),"Registered",mostCurrent.activityBA);
 }else {
 //BA.debugLineNum = 194;BA.debugLine="ReadSettings";
_vvvvvvvvvv4();
 //BA.debugLineNum = 195;BA.debugLine="logger_add(True)";
_logger_add(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 196;BA.debugLine="Msgbox(res.SubString(1),\"Response\")";
anywheresoftware.b4a.keywords.Common.Msgbox(_res.substring((int)(1)),"Response",mostCurrent.activityBA);
 };
 }else if((_job._vvvv7.compareTo("list")==0)) { 
 //BA.debugLineNum = 199;BA.debugLine="Dim sp2() As String";
_sp2 = new String[(int)(0)];
java.util.Arrays.fill(_sp2,"");
 //BA.debugLineNum = 200;BA.debugLine="sp2=Regex.Split(\"=<\",res)";
_sp2 = anywheresoftware.b4a.keywords.Common.Regex.Split("=<",_res);
 //BA.debugLineNum = 201;BA.debugLine="Dim x As Int";
_x = 0;
 //BA.debugLineNum = 202;BA.debugLine="x=0";
_x = (int)(0);
 //BA.debugLineNum = 203;BA.debugLine="Log(sp2.Length)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(_sp2.length));
 //BA.debugLineNum = 204;BA.debugLine="If(sp2.Length>0)Then";
if ((_sp2.length>0)) { 
 //BA.debugLineNum = 205;BA.debugLine="For x=0 To sp2.Length-1";
{
final double step156 = 1;
final double limit156 = (int)(_sp2.length-1);
for (_x = (int)(0); (step156 > 0 && _x <= limit156) || (step156 < 0 && _x >= limit156); _x += step156) {
 //BA.debugLineNum = 206;BA.debugLine="logger_list.AddSingleLine(sp2(x))";
mostCurrent._logger_list.AddSingleLine(_sp2[_x]);
 }
};
 //BA.debugLineNum = 208;BA.debugLine="logger_list.Width=home.Width";
mostCurrent._logger_list.setWidth(mostCurrent._vvvvvvvvvv1.getWidth());
 //BA.debugLineNum = 209;BA.debugLine="logger_list.Height=home.Height";
mostCurrent._logger_list.setHeight(mostCurrent._vvvvvvvvvv1.getHeight());
 //BA.debugLineNum = 210;BA.debugLine="logger_list.Visible=True";
mostCurrent._logger_list.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 211;BA.debugLine="update_logger.Visible=False";
mostCurrent._update_logger.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 212;BA.debugLine="add_logger.Visible=False";
mostCurrent._add_logger.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 214;BA.debugLine="ToastMessageShow(\"No registered device found !\",True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("No registered device found !",anywheresoftware.b4a.keywords.Common.True);
 };
 }else if((_job._vvvv7.compareTo("update")==0)) { 
 //BA.debugLineNum = 217;BA.debugLine="updt=False";
_vvvvvvvvvv3 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 218;BA.debugLine="If(res.CompareTo(\"1\")==0)Then";
if ((_res.compareTo("1")==0)) { 
 //BA.debugLineNum = 220;BA.debugLine="home.RemoveAllViews";
mostCurrent._vvvvvvvvvv1.RemoveAllViews();
 //BA.debugLineNum = 221;BA.debugLine="home.LoadLayout(\"main\")";
mostCurrent._vvvvvvvvvv1.LoadLayout("main",mostCurrent.activityBA);
 //BA.debugLineNum = 222;BA.debugLine="SaveSettings";
_vvvvvvvvvvvv1();
 //BA.debugLineNum = 223;BA.debugLine="If (IsPaused(client)=False)Then StopService(client)";
if ((anywheresoftware.b4a.keywords.Common.IsPaused(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvv2.getObject()))==anywheresoftware.b4a.keywords.Common.False)) { 
anywheresoftware.b4a.keywords.Common.StopService(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvv2.getObject()));};
 //BA.debugLineNum = 224;BA.debugLine="StartService(client)";
anywheresoftware.b4a.keywords.Common.StartService(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvv2.getObject()));
 //BA.debugLineNum = 225;BA.debugLine="Msgbox(\"Device setting update.Your new device name is : \" & ST.name ,\"Update\")";
anywheresoftware.b4a.keywords.Common.Msgbox("Device setting update.Your new device name is : "+mostCurrent._vv1.name,"Update",mostCurrent.activityBA);
 }else {
 //BA.debugLineNum = 228;BA.debugLine="ReadSettings";
_vvvvvvvvvv4();
 //BA.debugLineNum = 229;BA.debugLine="Msgbox(\"Unable to update\",\"Not Updated\")";
anywheresoftware.b4a.keywords.Common.Msgbox("Unable to update","Not Updated",mostCurrent.activityBA);
 };
 };
 }else {
 //BA.debugLineNum = 234;BA.debugLine="Msgbox(\"Unable to connect to server.Please retry again !\",\"Unable  to connect !\")";
anywheresoftware.b4a.keywords.Common.Msgbox("Unable to connect to server.Please retry again !","Unable  to connect !",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 241;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 242;BA.debugLine="Job.Release";
_job._vvvv6();
 //BA.debugLineNum = 243;BA.debugLine="End Sub";
return "";
}
public static String  _lgn_click() throws Exception{
 //BA.debugLineNum = 249;BA.debugLine="Sub lgn_Click";
 //BA.debugLineNum = 250;BA.debugLine="login_usr(False)";
_login_usr(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 251;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvv2() throws Exception{
 //BA.debugLineNum = 415;BA.debugLine="Sub load()";
 //BA.debugLineNum = 417;BA.debugLine="End Sub";
return "";
}
public static String  _logger_add(boolean _re) throws Exception{
anywheresoftware.b4a.agraham.dialogs.InputDialog _id = null;
int _ret = 0;
 //BA.debugLineNum = 396;BA.debugLine="Sub logger_add(re As Boolean)";
 //BA.debugLineNum = 397;BA.debugLine="Dim id As InputDialog";
_id = new anywheresoftware.b4a.agraham.dialogs.InputDialog();
 //BA.debugLineNum = 398;BA.debugLine="Dim ret As Int";
_ret = 0;
 //BA.debugLineNum = 400;BA.debugLine="id.PasswordMode=False";
_id.setPasswordMode(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 401;BA.debugLine="id.InputType=id.INPUT_TYPE_TEXT";
_id.setInputType(_id.INPUT_TYPE_TEXT);
 //BA.debugLineNum = 402;BA.debugLine="id.Input = \"\"";
_id.setInput("");
 //BA.debugLineNum = 403;BA.debugLine="id.Hint = \"Device name\"";
_id.setHint("Device name");
 //BA.debugLineNum = 404;BA.debugLine="id.HintColor = Colors.ARGB(196, 255, 140, 0)";
_id.setHintColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int)(196),(int)(255),(int)(140),(int)(0)));
 //BA.debugLineNum = 405;BA.debugLine="ret = DialogResponse.CANCEL";
_ret = anywheresoftware.b4a.keywords.Common.DialogResponse.CANCEL;
 //BA.debugLineNum = 406;BA.debugLine="ret = id.Show(\"Enter your new device name \", \"Device name\", \"Add\", \"\", \"\",Bmp)+2";
_ret = (int)(_id.Show("Enter your new device name ","Device name","Add","","",mostCurrent.activityBA,(android.graphics.Bitmap)(mostCurrent._vvvvvvvvvv2.getObject()))+2);
 //BA.debugLineNum = 407;BA.debugLine="If(ret>0)Then";
if ((_ret>0)) { 
 //BA.debugLineNum = 408;BA.debugLine="ST.name=id.Input";
mostCurrent._vv1.name = _id.getInput();
 //BA.debugLineNum = 409;BA.debugLine="CallSubDelayed2(client, \"RegisterDevice\", False)";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvv2.getObject()),"RegisterDevice",(Object)(anywheresoftware.b4a.keywords.Common.False));
 //BA.debugLineNum = 410;BA.debugLine="ProgressDialogShow2(\"Registering new device...\",False)";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,"Registering new device...",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 414;BA.debugLine="End Sub";
return "";
}
public static String  _logger_list_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 256;BA.debugLine="Sub logger_list_ItemClick (Position As Int, Value As Object)";
 //BA.debugLineNum = 257;BA.debugLine="CallSubDelayed2(client, \"RegisterDevice\", False)";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvv2.getObject()),"RegisterDevice",(Object)(anywheresoftware.b4a.keywords.Common.False));
 //BA.debugLineNum = 258;BA.debugLine="updt=True";
_vvvvvvvvvv3 = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 259;BA.debugLine="ST.name=Value";
mostCurrent._vv1.name = String.valueOf(_value);
 //BA.debugLineNum = 261;BA.debugLine="ProgressDialogShow2(\"Please wait while updating...\",False)";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,"Please wait while updating...",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 263;BA.debugLine="logger_list.Visible=False";
mostCurrent._logger_list.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 264;BA.debugLine="End Sub";
return "";
}
public static String  _login_home(boolean _re) throws Exception{
 //BA.debugLineNum = 317;BA.debugLine="Sub login_home(re As Boolean)";
 //BA.debugLineNum = 318;BA.debugLine="home.RemoveAllViews";
mostCurrent._vvvvvvvvvv1.RemoveAllViews();
 //BA.debugLineNum = 319;BA.debugLine="home.LoadLayout(\"login\")";
mostCurrent._vvvvvvvvvv1.LoadLayout("login",mostCurrent.activityBA);
 //BA.debugLineNum = 321;BA.debugLine="If(re==False)Then";
if ((_re==anywheresoftware.b4a.keywords.Common.False)) { 
 //BA.debugLineNum = 322;BA.debugLine="update_logger.Visible=True";
mostCurrent._update_logger.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 323;BA.debugLine="add_logger.Visible=True";
mostCurrent._add_logger.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 324;BA.debugLine="logger_list.Visible=False";
mostCurrent._logger_list.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 325;BA.debugLine="SaveSettings";
_vvvvvvvvvvvv1();
 }else {
 //BA.debugLineNum = 327;BA.debugLine="update_logger.Visible=False";
mostCurrent._update_logger.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 328;BA.debugLine="add_logger.Visible=False";
mostCurrent._add_logger.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 329;BA.debugLine="logger_list.Visible=True";
mostCurrent._logger_list.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 331;BA.debugLine="End Sub";
return "";
}
public static String  _login_usr(boolean _re) throws Exception{
anywheresoftware.b4a.agraham.dialogs.InputDialog _id = null;
int _ret = 0;
 //BA.debugLineNum = 291;BA.debugLine="Sub login_usr(re As Boolean)";
 //BA.debugLineNum = 292;BA.debugLine="If(re==True)Then";
if ((_re==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 293;BA.debugLine="Msgbox(\"Incorrect username or password.\",\"Incorrect login\")";
anywheresoftware.b4a.keywords.Common.Msgbox("Incorrect username or password.","Incorrect login",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 295;BA.debugLine="Dim Id As InputDialog";
_id = new anywheresoftware.b4a.agraham.dialogs.InputDialog();
 //BA.debugLineNum = 296;BA.debugLine="Dim ret As Int";
_ret = 0;
 //BA.debugLineNum = 298;BA.debugLine="Id.PasswordMode=False";
_id.setPasswordMode(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 299;BA.debugLine="Id.InputType=Id.INPUT_TYPE_TEXT";
_id.setInputType(_id.INPUT_TYPE_TEXT);
 //BA.debugLineNum = 300;BA.debugLine="Id.Input = ST.user";
_id.setInput(mostCurrent._vv1.user);
 //BA.debugLineNum = 301;BA.debugLine="Id.Hint = \"Username\"";
_id.setHint("Username");
 //BA.debugLineNum = 302;BA.debugLine="Id.HintColor = Colors.ARGB(196, 255, 140, 0)";
_id.setHintColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int)(196),(int)(255),(int)(140),(int)(0)));
 //BA.debugLineNum = 303;BA.debugLine="ret = DialogResponse.CANCEL";
_ret = anywheresoftware.b4a.keywords.Common.DialogResponse.CANCEL;
 //BA.debugLineNum = 304;BA.debugLine="ret = Id.Show(\"Enter username \", \"Username\", \"Ok\", \"\", \"\",Bmp)+2";
_ret = (int)(_id.Show("Enter username ","Username","Ok","","",mostCurrent.activityBA,(android.graphics.Bitmap)(mostCurrent._vvvvvvvvvv2.getObject()))+2);
 //BA.debugLineNum = 305;BA.debugLine="ST.user=Id.Input";
mostCurrent._vv1.user = _id.getInput();
 //BA.debugLineNum = 307;BA.debugLine="Id.PasswordMode=True";
_id.setPasswordMode(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 308;BA.debugLine="Id.Input=ST.password";
_id.setInput(mostCurrent._vv1.password);
 //BA.debugLineNum = 309;BA.debugLine="Id.Hint = \"Password\"";
_id.setHint("Password");
 //BA.debugLineNum = 310;BA.debugLine="If(ret>0)Then";
if ((_ret>0)) { 
 //BA.debugLineNum = 311;BA.debugLine="ret = Id.Show(\"Password \", \"Password\", \"Login\", \"\", \"\",Bmp)+2";
_ret = (int)(_id.Show("Password ","Password","Login","","",mostCurrent.activityBA,(android.graphics.Bitmap)(mostCurrent._vvvvvvvvvv2.getObject()))+2);
 //BA.debugLineNum = 312;BA.debugLine="ST.password=Id.Input";
mostCurrent._vv1.password = _id.getInput();
 //BA.debugLineNum = 313;BA.debugLine="ProgressDialogShow2(\"Please wait while authenticating...\",False)";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,"Please wait while authenticating...",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 314;BA.debugLine="snd(\"login\",\"\")";
_vvvvvvvvvvvv3("login","");
 };
 //BA.debugLineNum = 316;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvv4(String _text,boolean _longduration) throws Exception{
com.rootsoft.customtoast.CustomToast _c = null;
 //BA.debugLineNum = 418;BA.debugLine="Sub Notify(Text As String,LongDuration As Boolean)";
 //BA.debugLineNum = 420;BA.debugLine="Dim c As CustomToast";
_c = new com.rootsoft.customtoast.CustomToast();
 //BA.debugLineNum = 421;BA.debugLine="c.Initialize";
_c.Initialize(processBA);
 //BA.debugLineNum = 422;BA.debugLine="c.Show(Text,5000,Gravity.CENTER_VERTICAL,0,0)";
_c.Show((java.lang.CharSequence)(_text),(int)(5000),anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,(int)(0),(int)(0));
 //BA.debugLineNum = 423;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 15;BA.debugLine="Type appSettings (user As String, password As String,name As String)";
;
 //BA.debugLineNum = 16;BA.debugLine="Dim raf As RandomAccessFile";
_v0 = new anywheresoftware.b4a.randomaccessfile.RandomAccessFile();
 //BA.debugLineNum = 17;BA.debugLine="Public AppVersion  As String : AppVersion =\"1.0.1beta\"";
_vvv2 = "";
 //BA.debugLineNum = 17;BA.debugLine="Public AppVersion  As String : AppVersion =\"1.0.1beta\"";
_vvv2 = BA.__b (new byte[] {109,127,70,50,19,101,80,61,47}, 83249);
 //BA.debugLineNum = 18;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvv4() throws Exception{
anywheresoftware.b4a.randomaccessfile.RandomAccessFile _rad = null;
int _i = 0;
 //BA.debugLineNum = 343;BA.debugLine="Sub ReadSettings";
 //BA.debugLineNum = 344;BA.debugLine="Dim rad As RandomAccessFile";
_rad = new anywheresoftware.b4a.randomaccessfile.RandomAccessFile();
 //BA.debugLineNum = 345;BA.debugLine="rad.Initialize(File.DirInternal,\"settings.dat\",False)";
_rad.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"settings.dat",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 346;BA.debugLine="If ST.IsInitialized==False Then ST.Initialize";
if (mostCurrent._vv1.IsInitialized==anywheresoftware.b4a.keywords.Common.False) { 
mostCurrent._vv1.Initialize();};
 //BA.debugLineNum = 347;BA.debugLine="If MP.IsInitialized==False Then MP.Initialize";
if (mostCurrent._vv2.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
mostCurrent._vv2.Initialize();};
 //BA.debugLineNum = 349;BA.debugLine="If(rad.Size>0) Then";
if ((_rad.getSize()>0)) { 
 //BA.debugLineNum = 350;BA.debugLine="MP = rad.ReadObject(0) 'always 0 (single object)";
mostCurrent._vv2.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_rad.ReadObject((long)(0))));
 //BA.debugLineNum = 351;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 352;BA.debugLine="For i = 0 To MP.Size-1";
{
final double step281 = 1;
final double limit281 = (int)(mostCurrent._vv2.getSize()-1);
for (_i = (int)(0); (step281 > 0 && _i <= limit281) || (step281 < 0 && _i >= limit281); _i += step281) {
 //BA.debugLineNum = 353;BA.debugLine="ST=MP.GetValueAt(i)";
mostCurrent._vv1 = (madsacsoft.maddev.rdroid.main._appsettings)(mostCurrent._vv2.GetValueAt(_i));
 }
};
 };
 //BA.debugLineNum = 357;BA.debugLine="If(IsPaused(\"Main\")==False)Then";
if ((anywheresoftware.b4a.keywords.Common.IsPaused(mostCurrent.activityBA,(Object)("Main"))==anywheresoftware.b4a.keywords.Common.False)) { 
 //BA.debugLineNum = 358;BA.debugLine="If(ST.user.CompareTo(\"\")==0) Then";
if ((mostCurrent._vv1.user.compareTo("")==0)) { 
 //BA.debugLineNum = 359;BA.debugLine="lblusr.Text=\"No active account\"";
mostCurrent._lblusr.setText((Object)("No active account"));
 }else {
 //BA.debugLineNum = 361;BA.debugLine="lblusr.Text=\"Username : \" & ST.user";
mostCurrent._lblusr.setText((Object)("Username : "+mostCurrent._vv1.user));
 };
 //BA.debugLineNum = 364;BA.debugLine="If(ST.name.CompareTo(\"\")==0 AND IsPaused(\"Main\")==False) Then";
if ((mostCurrent._vv1.name.compareTo("")==0 && anywheresoftware.b4a.keywords.Common.IsPaused(mostCurrent.activityBA,(Object)("Main"))==anywheresoftware.b4a.keywords.Common.False)) { 
 //BA.debugLineNum = 365;BA.debugLine="lbldevice.Text=\"Please add a device.\"";
mostCurrent._lbldevice.setText((Object)("Please add a device."));
 //BA.debugLineNum = 366;BA.debugLine="ToastMessageShow(\"No active account.\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("No active account.",anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 368;BA.debugLine="If(IsPaused(\"Main\")==False)Then		lbldevice.Text=\"Device name : \" & ST.name";
if ((anywheresoftware.b4a.keywords.Common.IsPaused(mostCurrent.activityBA,(Object)("Main"))==anywheresoftware.b4a.keywords.Common.False)) { 
mostCurrent._lbldevice.setText((Object)("Device name : "+mostCurrent._vv1.name));};
 };
 };
 //BA.debugLineNum = 373;BA.debugLine="If(IsPaused(\"client\")==True)Then";
if ((anywheresoftware.b4a.keywords.Common.IsPaused(mostCurrent.activityBA,(Object)("client"))==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 374;BA.debugLine="StartService(client)";
anywheresoftware.b4a.keywords.Common.StartService(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvv2.getObject()));
 //BA.debugLineNum = 375;BA.debugLine="ToastMessageShow(\"rDroid is active now.\",True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("rDroid is active now.",anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 377;BA.debugLine="If(IsPaused(\"client\")==True)Then";
if ((anywheresoftware.b4a.keywords.Common.IsPaused(mostCurrent.activityBA,(Object)("client"))==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 378;BA.debugLine="servStatus.Text=\"rDroid Status : Deactivated\"";
mostCurrent._servstatus.setText((Object)("rDroid Status : Deactivated"));
 }else {
 //BA.debugLineNum = 380;BA.debugLine="servStatus.Text=\"rDroid Status : Active\"";
mostCurrent._servstatus.setText((Object)("rDroid Status : Active"));
 //BA.debugLineNum = 381;BA.debugLine="stopServ.Visible=True";
mostCurrent._stopserv.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 384;BA.debugLine="rad.Close";
_rad.Close();
 //BA.debugLineNum = 385;BA.debugLine="End Sub";
return "";
}
public static String  _reg_code(String _rid) throws Exception{
anywheresoftware.b4a.phone.Phone _p = null;
 //BA.debugLineNum = 386;BA.debugLine="Sub reg_code(rid As String)";
 //BA.debugLineNum = 387;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 389;BA.debugLine="If (updt==False) Then";
if ((_vvvvvvvvvv3==anywheresoftware.b4a.keywords.Common.False)) { 
 //BA.debugLineNum = 390;BA.debugLine="snd(\"addlogger\",\"gID=\" & rid & \"&dID=\" & p.GetSettings(\"android_id\") )";
_vvvvvvvvvvvv3("addlogger","gID="+_rid+"&dID="+_p.GetSettings("android_id"));
 }else {
 //BA.debugLineNum = 392;BA.debugLine="snd(\"update\",\"gID=\" & rid & \"&dID=\" & p.GetSettings(\"android_id\") )";
_vvvvvvvvvvvv3("update","gID="+_rid+"&dID="+_p.GetSettings("android_id"));
 };
 //BA.debugLineNum = 394;BA.debugLine="End Sub";
return "";
}
public static String  _reg_ok_click() throws Exception{
 //BA.debugLineNum = 277;BA.debugLine="Sub reg_ok_Click";
 //BA.debugLineNum = 278;BA.debugLine="If(reg_user.Text.Length<5)Then";
if ((mostCurrent._reg_user.getText().length()<5)) { 
 //BA.debugLineNum = 279;BA.debugLine="Msgbox(\"Username can be of minimum 4 characters.\",\"Invalid username\")";
anywheresoftware.b4a.keywords.Common.Msgbox("Username can be of minimum 4 characters.","Invalid username",mostCurrent.activityBA);
 }else if(((mostCurrent._reg_pass1.getText()).equals(mostCurrent._reg_pass2.getText()) == false)) { 
 //BA.debugLineNum = 281;BA.debugLine="Msgbox(\"Passwords should match.\",\"Password donot match\")";
anywheresoftware.b4a.keywords.Common.Msgbox("Passwords should match.","Password donot match",mostCurrent.activityBA);
 }else {
 //BA.debugLineNum = 283;BA.debugLine="ST.user=reg_user.Text";
mostCurrent._vv1.user = mostCurrent._reg_user.getText();
 //BA.debugLineNum = 284;BA.debugLine="ST.password=reg_pass1.Text";
mostCurrent._vv1.password = mostCurrent._reg_pass1.getText();
 //BA.debugLineNum = 285;BA.debugLine="ST.name=\"\"";
mostCurrent._vv1.name = "";
 //BA.debugLineNum = 286;BA.debugLine="snd(\"register\",\"\")";
_vvvvvvvvvvvv3("register","");
 //BA.debugLineNum = 287;BA.debugLine="ProgressDialogShow2(\"Please wait while registering...\",False)";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,"Please wait while registering...",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 289;BA.debugLine="End Sub";
return "";
}
public static String  _rgstr_click() throws Exception{
 //BA.debugLineNum = 245;BA.debugLine="Sub rgstr_Click";
 //BA.debugLineNum = 246;BA.debugLine="home.RemoveAllViews";
mostCurrent._vvvvvvvvvv1.RemoveAllViews();
 //BA.debugLineNum = 247;BA.debugLine="home.LoadLayout(\"reg\")";
mostCurrent._vvvvvvvvvv1.LoadLayout("reg",mostCurrent.activityBA);
 //BA.debugLineNum = 248;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvv1() throws Exception{
 //BA.debugLineNum = 332;BA.debugLine="Sub SaveSettings";
 //BA.debugLineNum = 336;BA.debugLine="MP.Put(ST.name,ST)";
mostCurrent._vv2.Put((Object)(mostCurrent._vv1.name),(Object)(mostCurrent._vv1));
 //BA.debugLineNum = 337;BA.debugLine="raf.Initialize(File.DirInternal,\"settings.dat\",False)";
_v0.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"settings.dat",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 338;BA.debugLine="raf.WriteObject(MP,True,0) 'always use position 0. We only hold a single object in this case so we can start from the beginning.";
_v0.WriteObject((Object)(mostCurrent._vv2.getObject()),anywheresoftware.b4a.keywords.Common.True,(long)(0));
 //BA.debugLineNum = 339;BA.debugLine="raf.Flush 'Not realy requied here. Better to call it when you finish writing";
_v0.Flush();
 //BA.debugLineNum = 340;BA.debugLine="raf.Close";
_v0.Close();
 //BA.debugLineNum = 341;BA.debugLine="ReadSettings";
_vvvvvvvvvv4();
 //BA.debugLineNum = 342;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvv5(String _message,String _title,String _positive) throws Exception{
anywheresoftware.b4a.agraham.richstring.RichStringBuilder.RichString _rs = null;
anywheresoftware.b4a.specci48.spdialogs.SPDialogs.Msgbox3 _msg = null;
 //BA.debugLineNum = 106;BA.debugLine="Sub ShowMessage(Message As String,Title As String,Positive As String)";
 //BA.debugLineNum = 107;BA.debugLine="Dim rs As RichString";
_rs = new anywheresoftware.b4a.agraham.richstring.RichStringBuilder.RichString();
 //BA.debugLineNum = 108;BA.debugLine="rs.Initialize(Message)";
_rs.Initialize((java.lang.CharSequence)(_message));
 //BA.debugLineNum = 109;BA.debugLine="rs.RelativeSize2(1.5, \"{1.5}\")";
_rs.RelativeSize2((float)(1.5),"{1.5}");
 //BA.debugLineNum = 110;BA.debugLine="rs.Typeface2(\"serif\", \"{T}\")";
_rs.Typeface2("serif","{T}");
 //BA.debugLineNum = 111;BA.debugLine="rs.Underscore2(\"{U}\")";
_rs.Underscore2("{U}");
 //BA.debugLineNum = 112;BA.debugLine="rs.Color2(Colors.Red, \"{RED}\")";
_rs.Color2(anywheresoftware.b4a.keywords.Common.Colors.Red,"{RED}");
 //BA.debugLineNum = 113;BA.debugLine="rs.Color2(Colors.Blue,\"{BLUE}\")";
_rs.Color2(anywheresoftware.b4a.keywords.Common.Colors.Blue,"{BLUE}");
 //BA.debugLineNum = 114;BA.debugLine="rs.Color2(Colors.Cyan,\"{CYAN}\")";
_rs.Color2(anywheresoftware.b4a.keywords.Common.Colors.Cyan,"{CYAN}");
 //BA.debugLineNum = 115;BA.debugLine="rs.Color2(Colors.Green,\"{GREEN}\")";
_rs.Color2(anywheresoftware.b4a.keywords.Common.Colors.Green,"{GREEN}");
 //BA.debugLineNum = 116;BA.debugLine="rs.Color2(Colors.Yellow,\"{YELLOW}\")";
_rs.Color2(anywheresoftware.b4a.keywords.Common.Colors.Yellow,"{YELLOW}");
 //BA.debugLineNum = 117;BA.debugLine="rs.Color2(Colors.White,\"{WHITE}\")";
_rs.Color2(anywheresoftware.b4a.keywords.Common.Colors.White,"{WHITE}");
 //BA.debugLineNum = 118;BA.debugLine="rs.Style2(rs.STYLE_BOLD_ITALIC, \"{BI}\")";
_rs.Style2(_rs.STYLE_BOLD_ITALIC,"{BI}");
 //BA.debugLineNum = 119;BA.debugLine="rs.Subscript2(\"{SUB}\")";
_rs.Subscript2("{SUB}");
 //BA.debugLineNum = 120;BA.debugLine="rs.Superscript2(\"{SUP}\")";
_rs.Superscript2("{SUP}");
 //BA.debugLineNum = 121;BA.debugLine="rs.Strikethrough2(\"{STRIKE}\")";
_rs.Strikethrough2("{STRIKE}");
 //BA.debugLineNum = 122;BA.debugLine="rs.Style2(rs.STYLE_ITALIC,\"{I}\")";
_rs.Style2(_rs.STYLE_ITALIC,"{I}");
 //BA.debugLineNum = 123;BA.debugLine="rs.Style2(rs.STYLE_BOLD,\"{B}\")";
_rs.Style2(_rs.STYLE_BOLD,"{B}");
 //BA.debugLineNum = 125;BA.debugLine="Dim msg As Msgbox3";
_msg = new anywheresoftware.b4a.specci48.spdialogs.SPDialogs.Msgbox3();
 //BA.debugLineNum = 126;BA.debugLine="msg.Show(rs, Title, Positive, \"\", \"\", Null)";
_msg.Show((java.lang.CharSequence)(_rs.getObject()),(java.lang.CharSequence)(_title),(java.lang.CharSequence)(_positive),(java.lang.CharSequence)(""),(java.lang.CharSequence)(""),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA);
 //BA.debugLineNum = 127;BA.debugLine="End Sub";
return "";
}
public static String  _slidemenu_click(Object _item) throws Exception{
 //BA.debugLineNum = 94;BA.debugLine="Sub SlideMenu_Click(Item As Object)";
 //BA.debugLineNum = 96;BA.debugLine="If Item=1 Then";
if ((_item).equals((Object)(1))) { 
 //BA.debugLineNum = 97;BA.debugLine="home.Visible=True";
mostCurrent._vvvvvvvvvv1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if((_item).equals((Object)(3))) { 
 //BA.debugLineNum = 99;BA.debugLine="ShowMessage(\"{B} Help {B}\" & CRLF & \"After installing the application & creating account visit {GREEN}http://rdroid.madsac.in{GREEN} to control you device remotely from anywhere.For full help visit {GREEN}http://rdroid.madsac.in/help.php{GREEN}.\",\"Help\",\"OK\")";
_vvvvvvvvvvvv5("{B} Help {B}"+anywheresoftware.b4a.keywords.Common.CRLF+"After installing the application & creating account visit {GREEN}http://rdroid.madsac.in{GREEN} to control you device remotely from anywhere.For full help visit {GREEN}http://rdroid.madsac.in/help.php{GREEN}.","Help","OK");
 }else if((_item).equals((Object)(4))) { 
 //BA.debugLineNum = 101;BA.debugLine="ShowMessage(\"{B} Help/Support/Suggestion {B}\" & CRLF & \"Are you facing a problem or have an suggestion to make rDroid better. Just drop an e-mail at {GREEN}madsacsoft@gmail.com{GREEN} or just raise it at {GREEN}http://forum.madsac.in/.{GREEN}.\",\"Help\",\"OK\")";
_vvvvvvvvvvvv5("{B} Help/Support/Suggestion {B}"+anywheresoftware.b4a.keywords.Common.CRLF+"Are you facing a problem or have an suggestion to make rDroid better. Just drop an e-mail at {GREEN}madsacsoft@gmail.com{GREEN} or just raise it at {GREEN}http://forum.madsac.in/.{GREEN}.","Help","OK");
 }else if((_item).equals((Object)(5))) { 
 //BA.debugLineNum = 103;BA.debugLine="ShowMessage(\"{B} MAD rDROID{B}\" & CRLF & \"{RED}{B}Version : {RED} \" & AppVersion &  CRLF  & \"{B}{I}Read declaimer at http://rdroid.madsac.in/declaimer.php{I}\",\"About\",\"OK\")";
_vvvvvvvvvvvv5("{B} MAD rDROID{B}"+anywheresoftware.b4a.keywords.Common.CRLF+"{RED}{B}Version : {RED} "+_vvv2+anywheresoftware.b4a.keywords.Common.CRLF+"{B}{I}Read declaimer at http://rdroid.madsac.in/declaimer.php{I}","About","OK");
 };
 //BA.debugLineNum = 105;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvv3(String _jbname,String _par) throws Exception{
madsacsoft.maddev.rdroid.httpjob _j = null;
anywheresoftware.b4a.objects.StringUtils _su = null;
 //BA.debugLineNum = 269;BA.debugLine="Sub snd(jbname As String,par As String)";
 //BA.debugLineNum = 270;BA.debugLine="Dim j As HttpJob";
_j = new madsacsoft.maddev.rdroid.httpjob();
 //BA.debugLineNum = 271;BA.debugLine="Dim su As StringUtils";
_su = new anywheresoftware.b4a.objects.StringUtils();
 //BA.debugLineNum = 272;BA.debugLine="j.Initialize(jbname,Me)";
_j._vvv1(processBA,_jbname,main.getObject());
 //BA.debugLineNum = 273;BA.debugLine="j.Download(\"http://rdroid.madsac.in/api.php?ver=beta&act=\"& su.EncodeUrl(jbname, \"UTF8\") & \"&usr=\"& su.EncodeUrl(ST.user, \"UTF8\") & \"&pwd=\" & su.EncodeUrl(ST.password, \"UTF8\") & \"&name=\" & su.EncodeUrl(ST.Name, \"UTF8\") & \"&\" & par )";
_j._vvv4("http://rdroid.madsac.in/api.php?ver=beta&act="+_su.EncodeUrl(_jbname,"UTF8")+"&usr="+_su.EncodeUrl(mostCurrent._vv1.user,"UTF8")+"&pwd="+_su.EncodeUrl(mostCurrent._vv1.password,"UTF8")+"&name="+_su.EncodeUrl(mostCurrent._vv1.name,"UTF8")+"&"+_par);
 //BA.debugLineNum = 275;BA.debugLine="End Sub";
return "";
}
public static String  _stopserv_down() throws Exception{
 //BA.debugLineNum = 424;BA.debugLine="Sub stopServ_Down";
 //BA.debugLineNum = 425;BA.debugLine="If(IsPaused(client)=False) Then";
if ((anywheresoftware.b4a.keywords.Common.IsPaused(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvv2.getObject()))==anywheresoftware.b4a.keywords.Common.False)) { 
 //BA.debugLineNum = 426;BA.debugLine="stopServ.Text=\"Activate rDroid\"";
mostCurrent._stopserv.setText((Object)("Activate rDroid"));
 //BA.debugLineNum = 427;BA.debugLine="servStatus.Text=\"rDroid Status : Deactivated\"";
mostCurrent._servstatus.setText((Object)("rDroid Status : Deactivated"));
 //BA.debugLineNum = 428;BA.debugLine="StopService(client)";
anywheresoftware.b4a.keywords.Common.StopService(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvv2.getObject()));
 }else {
 //BA.debugLineNum = 430;BA.debugLine="StartService(client)";
anywheresoftware.b4a.keywords.Common.StartService(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvv2.getObject()));
 //BA.debugLineNum = 432;BA.debugLine="servStatus.Text=\"rDroid Status : Active\"";
mostCurrent._servstatus.setText((Object)("rDroid Status : Active"));
 //BA.debugLineNum = 433;BA.debugLine="stopServ.Text=\"Deactivate rDroid\"";
mostCurrent._stopserv.setText((Object)("Deactivate rDroid"));
 };
 //BA.debugLineNum = 435;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvv6() throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _obj1 = null;
anywheresoftware.b4a.agraham.reflection.Reflection _obj2 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper _c = null;
long _now = 0L;
long _i = 0L;
String _dt = "";
Object[] _args = null;
String[] _types = null;
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _out = null;
 //BA.debugLineNum = 135;BA.debugLine="Sub TakeScreenshot ()";
 //BA.debugLineNum = 138;BA.debugLine="If File.Exists(File.DirRootExternal, \"/rDroid/Screenshots\")==False Then File.MakeDir(File.DirRootExternal, \"/rDroid/Screenshots\")";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"/rDroid/Screenshots")==anywheresoftware.b4a.keywords.Common.False) { 
anywheresoftware.b4a.keywords.Common.File.MakeDir(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"/rDroid/Screenshots");};
 //BA.debugLineNum = 139;BA.debugLine="Dim Obj1, Obj2 As Reflector";
_obj1 = new anywheresoftware.b4a.agraham.reflection.Reflection();
_obj2 = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 140;BA.debugLine="Dim Bmp As Bitmap";
mostCurrent._vvvvvvvvvv2 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 141;BA.debugLine="Dim c As Canvas";
_c = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 142;BA.debugLine="Dim now, i As Long";
_now = 0L;
_i = 0L;
 //BA.debugLineNum = 143;BA.debugLine="Dim dt As String";
_dt = "";
 //BA.debugLineNum = 144;BA.debugLine="DateTime.DateFormat = \"yyMMddHHmmss\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("yyMMddHHmmss");
 //BA.debugLineNum = 145;BA.debugLine="now = DateTime.now";
_now = anywheresoftware.b4a.keywords.Common.DateTime.getNow();
 //BA.debugLineNum = 146;BA.debugLine="dt = DateTime.date(now) ' e.g.: \"110812150355\" is Aug.12, 2011, 3:03:55 p.m.";
_dt = anywheresoftware.b4a.keywords.Common.DateTime.Date(_now);
 //BA.debugLineNum = 147;BA.debugLine="Obj1.Target = Obj1.GetActivityBA";
_obj1.Target = (Object)(_obj1.GetActivityBA(processBA));
 //BA.debugLineNum = 148;BA.debugLine="Obj1.Target = Obj1.GetField(\"vg\")";
_obj1.Target = _obj1.GetField("vg");
 //BA.debugLineNum = 149;BA.debugLine="Bmp.InitializeMutable(Activity.Width,Activity.Height)";
mostCurrent._vvvvvvvvvv2.InitializeMutable(mostCurrent._activity.getWidth(),mostCurrent._activity.getHeight());
 //BA.debugLineNum = 150;BA.debugLine="c.Initialize2(Bmp)";
_c.Initialize2((android.graphics.Bitmap)(mostCurrent._vvvvvvvvvv2.getObject()));
 //BA.debugLineNum = 151;BA.debugLine="Dim args(1) As Object";
_args = new Object[(int)(1)];
{
int d0 = _args.length;
for (int i0 = 0;i0 < d0;i0++) {
_args[i0] = new Object();
}
}
;
 //BA.debugLineNum = 152;BA.debugLine="Dim types(1) As String";
_types = new String[(int)(1)];
java.util.Arrays.fill(_types,"");
 //BA.debugLineNum = 153;BA.debugLine="Obj2.Target = c";
_obj2.Target = (Object)(_c);
 //BA.debugLineNum = 154;BA.debugLine="Obj2.Target = Obj2.GetField(\"canvas\")";
_obj2.Target = _obj2.GetField("canvas");
 //BA.debugLineNum = 155;BA.debugLine="args(0) = Obj2.Target";
_args[(int)(0)] = _obj2.Target;
 //BA.debugLineNum = 156;BA.debugLine="types(0) = \"android.graphics.Canvas\"";
_types[(int)(0)] = "android.graphics.Canvas";
 //BA.debugLineNum = 157;BA.debugLine="Obj1.RunMethod4(\"draw\", args, types)";
_obj1.RunMethod4("draw",_args,_types);
 //BA.debugLineNum = 158;BA.debugLine="Dim Out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 159;BA.debugLine="Out = File.OpenOutput(File.DirRootExternal & \"/rDroid/Screenshots\" , dt & \".png\", False)";
_out = anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/rDroid/Screenshots",_dt+".png",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 160;BA.debugLine="Bmp.WriteToStream(Out, 100, \"PNG\")";
mostCurrent._vvvvvvvvvv2.WriteToStream((java.io.OutputStream)(_out.getObject()),(int)(100),BA.getEnumFromString(android.graphics.Bitmap.CompressFormat.class,"PNG"));
 //BA.debugLineNum = 161;BA.debugLine="Out.close";
_out.Close();
 //BA.debugLineNum = 162;BA.debugLine="Notify(\"Screenshot Captured\", False)";
_vvvvvvvvvvvv4("Screenshot Captured",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 164;BA.debugLine="End Sub";
return "";
}
public static String  _update_logger_click() throws Exception{
 //BA.debugLineNum = 252;BA.debugLine="Sub update_logger_Click";
 //BA.debugLineNum = 253;BA.debugLine="snd(\"list\",\"\")";
_vvvvvvvvvvvv3("list","");
 //BA.debugLineNum = 254;BA.debugLine="ProgressDialogShow2(\"Please wait while retriving list...\",False)";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,"Please wait while retriving list...",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 255;BA.debugLine="End Sub";
return "";
}
}
