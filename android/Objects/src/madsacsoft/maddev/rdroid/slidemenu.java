package madsacsoft.maddev.rdroid;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class slidemenu extends B4AClass.ImplB4AClass{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "madsacsoft.maddev.rdroid.slidemenu");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
        }
        _class_globals();
    }


 public static class _actionitem{
public boolean IsInitialized;
public String Text;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper Image;
public Object Value;
public void Initialize() {
IsInitialized = true;
Text = "";
Image = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
Value = new Object();
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.PanelWrapper _vvvvvvvvv2 = null;
public anywheresoftware.b4a.objects.PanelWrapper _vvvvvvvvv3 = null;
public Object _vvvvvvvvv4 = null;
public String _vvvvvvvvv5 = "";
public anywheresoftware.b4a.objects.ListViewWrapper _vvvvvvvvv6 = null;
public anywheresoftware.b4a.objects.AnimationWrapper _vvvvvvvvv7 = null;
public anywheresoftware.b4a.objects.AnimationWrapper _vvvvvvvvv0 = null;
public madsacsoft.maddev.rdroid.main _vvvvv0 = null;
public madsacsoft.maddev.rdroid.submitter _vvvvvv1 = null;
public madsacsoft.maddev.rdroid.client _vvvvvv2 = null;
public madsacsoft.maddev.rdroid.utils _vvvvvv3 = null;
public String  _vvvvvvvv6(String _text,anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _image,Object _returnvalue) throws Exception{
madsacsoft.maddev.rdroid.slidemenu._actionitem _item = null;
 //BA.debugLineNum = 62;BA.debugLine="Public Sub AddItem(Text As String, Image As Bitmap, ReturnValue As Object)";
 //BA.debugLineNum = 63;BA.debugLine="Dim item As ActionItem";
_item = new madsacsoft.maddev.rdroid.slidemenu._actionitem();
 //BA.debugLineNum = 64;BA.debugLine="item.Initialize";
_item.Initialize();
 //BA.debugLineNum = 65;BA.debugLine="item.Text = Text";
_item.Text = _text;
 //BA.debugLineNum = 66;BA.debugLine="item.Image = Image";
_item.Image = _image;
 //BA.debugLineNum = 67;BA.debugLine="item.Value = ReturnValue";
_item.Value = _returnvalue;
 //BA.debugLineNum = 69;BA.debugLine="If Not(Image.IsInitialized) Then";
if (__c.Not(_image.IsInitialized())) { 
 //BA.debugLineNum = 70;BA.debugLine="mListView.AddTwoLinesAndBitmap2(Text, \"\", Null, ReturnValue)";
_vvvvvvvvv6.AddTwoLinesAndBitmap2(_text,"",(android.graphics.Bitmap)(__c.Null),_returnvalue);
 }else {
 //BA.debugLineNum = 72;BA.debugLine="mListView.AddTwoLinesAndBitmap2(Text, \"\", Image, ReturnValue)";
_vvvvvvvvv6.AddTwoLinesAndBitmap2(_text,"",(android.graphics.Bitmap)(_image.getObject()),_returnvalue);
 };
 //BA.debugLineNum = 74;BA.debugLine="End Sub";
return "";
}
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 7;BA.debugLine="Private Sub Class_Globals";
 //BA.debugLineNum = 8;BA.debugLine="Type ActionItem (Text As String, Image As Bitmap, Value As Object)";
;
 //BA.debugLineNum = 10;BA.debugLine="Private mSlidePanel As Panel";
_vvvvvvvvv2 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 11;BA.debugLine="Private mBackPanel As Panel";
_vvvvvvvvv3 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 13;BA.debugLine="Private mModule As Object";
_vvvvvvvvv4 = new Object();
 //BA.debugLineNum = 14;BA.debugLine="Private mEventName As String";
_vvvvvvvvv5 = "";
 //BA.debugLineNum = 16;BA.debugLine="Private mListView As ListView";
_vvvvvvvvv6 = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private mInAnimation As Animation";
_vvvvvvvvv7 = new anywheresoftware.b4a.objects.AnimationWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private mOutAnimation As Animation";
_vvvvvvvvv0 = new anywheresoftware.b4a.objects.AnimationWrapper();
 //BA.debugLineNum = 20;BA.debugLine="End Sub";
return "";
}
public String  _vvvvvvvv7() throws Exception{
 //BA.debugLineNum = 90;BA.debugLine="Public Sub Hide";
 //BA.debugLineNum = 91;BA.debugLine="If isVisible = False Then Return";
if (_vvvvvvvv0()==__c.False) { 
if (true) return "";};
 //BA.debugLineNum = 93;BA.debugLine="mBackPanel.Left = -mBackPanel.Width";
_vvvvvvvvv3.setLeft((int)(-_vvvvvvvvv3.getWidth()));
 //BA.debugLineNum = 94;BA.debugLine="mSlidePanel.Left = -mSlidePanel.Width";
_vvvvvvvvv2.setLeft((int)(-_vvvvvvvvv2.getWidth()));
 //BA.debugLineNum = 95;BA.debugLine="mOutAnimation.Start(mSlidePanel)";
_vvvvvvvvv0.Start((android.view.View)(_vvvvvvvvv2.getObject()));
 //BA.debugLineNum = 96;BA.debugLine="End Sub";
return "";
}
public String  _vvv1(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.ActivityWrapper _activity,Object _module,String _eventname,int _top,int _width) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 28;BA.debugLine="Sub Initialize(Activity As Activity, Module As Object, EventName As String, Top As Int, Width As Int)";
 //BA.debugLineNum = 29;BA.debugLine="mModule = Module";
_vvvvvvvvv4 = _module;
 //BA.debugLineNum = 30;BA.debugLine="mEventName = EventName";
_vvvvvvvvv5 = _eventname;
 //BA.debugLineNum = 32;BA.debugLine="mSlidePanel.Initialize(\"mSlidePanel\")";
_vvvvvvvvv2.Initialize(ba,"mSlidePanel");
 //BA.debugLineNum = 34;BA.debugLine="mListView.Initialize(\"mListView\")";
_vvvvvvvvv6.Initialize(ba,"mListView");
 //BA.debugLineNum = 35;BA.debugLine="mListView.TwoLinesAndBitmap.SecondLabel.Visible = False";
_vvvvvvvvv6.getTwoLinesAndBitmap().SecondLabel.setVisible(__c.False);
 //BA.debugLineNum = 36;BA.debugLine="mListView.TwoLinesAndBitmap.ItemHeight = 50dip";
_vvvvvvvvv6.getTwoLinesAndBitmap().setItemHeight(__c.DipToCurrent((int)(50)));
 //BA.debugLineNum = 37;BA.debugLine="mListView.TwoLinesAndBitmap.Label.Gravity = Gravity.CENTER_VERTICAL";
_vvvvvvvvv6.getTwoLinesAndBitmap().Label.setGravity(__c.Gravity.CENTER_VERTICAL);
 //BA.debugLineNum = 38;BA.debugLine="mListView.TwoLinesAndBitmap.Label.Height = mListView.TwoLinesAndBitmap.ItemHeight";
_vvvvvvvvv6.getTwoLinesAndBitmap().Label.setHeight(_vvvvvvvvv6.getTwoLinesAndBitmap().getItemHeight());
 //BA.debugLineNum = 39;BA.debugLine="mListView.TwoLinesAndBitmap.Label.Top = 0";
_vvvvvvvvv6.getTwoLinesAndBitmap().Label.setTop((int)(0));
 //BA.debugLineNum = 40;BA.debugLine="mListView.TwoLinesAndBitmap.ImageView.SetLayout(13dip, 13dip, 24dip, 24dip)";
_vvvvvvvvv6.getTwoLinesAndBitmap().ImageView.SetLayout(__c.DipToCurrent((int)(13)),__c.DipToCurrent((int)(13)),__c.DipToCurrent((int)(24)),__c.DipToCurrent((int)(24)));
 //BA.debugLineNum = 41;BA.debugLine="mListView.Color = Colors.Black";
_vvvvvvvvv6.setColor(__c.Colors.Black);
 //BA.debugLineNum = 43;BA.debugLine="mInAnimation.InitializeTranslate(\"\", -Width, 0, 0, 0)";
_vvvvvvvvv7.InitializeTranslate(ba,"",(float)(-_width),(float)(0),(float)(0),(float)(0));
 //BA.debugLineNum = 44;BA.debugLine="mInAnimation.Duration = 200";
_vvvvvvvvv7.setDuration((long)(200));
 //BA.debugLineNum = 45;BA.debugLine="mOutAnimation.InitializeTranslate(\"Out\", Width, 0, 0, 0)";
_vvvvvvvvv0.InitializeTranslate(ba,"Out",(float)(_width),(float)(0),(float)(0),(float)(0));
 //BA.debugLineNum = 46;BA.debugLine="mOutAnimation.Duration = 200";
_vvvvvvvvv0.setDuration((long)(200));
 //BA.debugLineNum = 48;BA.debugLine="Activity.AddView(mSlidePanel, 0, Top, Width, 100%y - Top)";
_activity.AddView((android.view.View)(_vvvvvvvvv2.getObject()),(int)(0),_top,_width,(int)(__c.PerYToCurrent((float)(100),ba)-_top));
 //BA.debugLineNum = 50;BA.debugLine="mBackPanel.Initialize(\"mBackPanel\")";
_vvvvvvvvv3.Initialize(ba,"mBackPanel");
 //BA.debugLineNum = 51;BA.debugLine="mBackPanel.Color = Colors.Transparent";
_vvvvvvvvv3.setColor(__c.Colors.Transparent);
 //BA.debugLineNum = 52;BA.debugLine="Activity.AddView(mBackPanel, -100%x, 0, 100%x, 100%y)";
_activity.AddView((android.view.View)(_vvvvvvvvv3.getObject()),(int)(-__c.PerXToCurrent((float)(100),ba)),(int)(0),__c.PerXToCurrent((float)(100),ba),__c.PerYToCurrent((float)(100),ba));
 //BA.debugLineNum = 54;BA.debugLine="mSlidePanel.AddView(mListView, 0, 0, mSlidePanel.Width, mSlidePanel.Height)";
_vvvvvvvvv2.AddView((android.view.View)(_vvvvvvvvv6.getObject()),(int)(0),(int)(0),_vvvvvvvvv2.getWidth(),_vvvvvvvvv2.getHeight());
 //BA.debugLineNum = 55;BA.debugLine="mSlidePanel.Visible = False";
_vvvvvvvvv2.setVisible(__c.False);
 //BA.debugLineNum = 56;BA.debugLine="End Sub";
return "";
}
public boolean  _vvvvvvvv0() throws Exception{
 //BA.debugLineNum = 118;BA.debugLine="Public Sub isVisible As Boolean";
 //BA.debugLineNum = 119;BA.debugLine="Return mSlidePanel.Visible";
if (true) return _vvvvvvvvv2.getVisible();
 //BA.debugLineNum = 120;BA.debugLine="End Sub";
return false;
}
public String  _mbackpanel_touch(int _action,float _x,float _y) throws Exception{
 //BA.debugLineNum = 102;BA.debugLine="Private Sub mBackPanel_Touch (Action As Int, X As Float, Y As Float)";
 //BA.debugLineNum = 103;BA.debugLine="If Action = 1 Then";
if (_action==1) { 
 //BA.debugLineNum = 104;BA.debugLine="Hide";
_vvvvvvvv7();
 };
 //BA.debugLineNum = 106;BA.debugLine="End Sub";
return "";
}
public String  _mlistview_itemclick(int _position,Object _value) throws Exception{
String _subname = "";
 //BA.debugLineNum = 108;BA.debugLine="Private Sub mListView_ItemClick (Position As Int, Value As Object)";
 //BA.debugLineNum = 109;BA.debugLine="Dim subname As String";
_subname = "";
 //BA.debugLineNum = 110;BA.debugLine="Hide";
_vvvvvvvv7();
 //BA.debugLineNum = 111;BA.debugLine="subname = mEventName & \"_Click\"";
_subname = _vvvvvvvvv5+"_Click";
 //BA.debugLineNum = 112;BA.debugLine="If SubExists(mModule, subname) Then";
if (__c.SubExists(ba,_vvvvvvvvv4,_subname)) { 
 //BA.debugLineNum = 113;BA.debugLine="CallSub2(mModule, subname, Value)";
__c.CallSub2(ba,_vvvvvvvvv4,_subname,_value);
 };
 //BA.debugLineNum = 115;BA.debugLine="End Sub";
return "";
}
public String  _out_animationend() throws Exception{
 //BA.debugLineNum = 98;BA.debugLine="Private Sub Out_AnimationEnd";
 //BA.debugLineNum = 99;BA.debugLine="mSlidePanel.Visible = False";
_vvvvvvvvv2.setVisible(__c.False);
 //BA.debugLineNum = 100;BA.debugLine="End Sub";
return "";
}
public String  _vvvvvvvvv1() throws Exception{
 //BA.debugLineNum = 77;BA.debugLine="Public Sub Show";
 //BA.debugLineNum = 78;BA.debugLine="If isVisible = True Then Return";
if (_vvvvvvvv0()==__c.True) { 
if (true) return "";};
 //BA.debugLineNum = 80;BA.debugLine="mBackPanel.BringToFront";
_vvvvvvvvv3.BringToFront();
 //BA.debugLineNum = 81;BA.debugLine="mSlidePanel.BringToFront";
_vvvvvvvvv2.BringToFront();
 //BA.debugLineNum = 82;BA.debugLine="mBackPanel.Left = 0";
_vvvvvvvvv3.setLeft((int)(0));
 //BA.debugLineNum = 83;BA.debugLine="mSlidePanel.Left = 0";
_vvvvvvvvv2.setLeft((int)(0));
 //BA.debugLineNum = 85;BA.debugLine="mSlidePanel.Visible = True";
_vvvvvvvvv2.setVisible(__c.True);
 //BA.debugLineNum = 86;BA.debugLine="mInAnimation.Start(mSlidePanel)";
_vvvvvvvvv7.Start((android.view.View)(_vvvvvvvvv2.getObject()));
 //BA.debugLineNum = 87;BA.debugLine="End Sub";
return "";
}
}
