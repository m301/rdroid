Type=Class
Version=2.52
@EndOfDesignText@
'SlideMenu Class module
'
'Author:  Markus Stipp
'Version: 1.0

'Class module
Private Sub Class_Globals
	Type ActionItem (Text As String, Image As Bitmap, Value As Object)
	
	Private mSlidePanel As Panel
	Private mBackPanel As Panel
	
	Private mModule As Object
	Private mEventName As String
	
	Private mListView As ListView

	Private mInAnimation As Animation
	Private mOutAnimation As Animation
End Sub

'Initializes the SlideMenu object
' Activity - Pass a reference to the Activity here where the Slidemenu should be added to.
' Module - The calling Module. Pass the "Me" reference here
' EventNAme - EventName for the Click event
' Top - Top position of the Menu.
' Width - Width of the menu
Sub Initialize(Activity As Activity, Module As Object, EventName As String, Top As Int, Width As Int)
	mModule = Module
	mEventName = EventName

	mSlidePanel.Initialize("mSlidePanel")

	mListView.Initialize("mListView")
	mListView.TwoLinesAndBitmap.SecondLabel.Visible = False
	mListView.TwoLinesAndBitmap.ItemHeight = 50dip
	mListView.TwoLinesAndBitmap.Label.Gravity = Gravity.CENTER_VERTICAL
	mListView.TwoLinesAndBitmap.Label.Height = mListView.TwoLinesAndBitmap.ItemHeight
	mListView.TwoLinesAndBitmap.Label.Top = 0
	mListView.TwoLinesAndBitmap.ImageView.SetLayout(13dip, 13dip, 24dip, 24dip)
	mListView.Color = Colors.Black

	mInAnimation.InitializeTranslate("", -Width, 0, 0, 0)
	mInAnimation.Duration = 200
	mOutAnimation.InitializeTranslate("Out", Width, 0, 0, 0)
	mOutAnimation.Duration = 200
	
	Activity.AddView(mSlidePanel, 0, Top, Width, 100%y - Top)
	
	mBackPanel.Initialize("mBackPanel")
	mBackPanel.Color = Colors.Transparent
	Activity.AddView(mBackPanel, -100%x, 0, 100%x, 100%y)

	mSlidePanel.AddView(mListView, 0, 0, mSlidePanel.Width, mSlidePanel.Height)
	mSlidePanel.Visible = False
End Sub

'Adds an item to the SlideMenu
' Text - Text to show in menu
' Image - Image to show
' ReturnValue - The value that will be returned in the Click event
Public Sub AddItem(Text As String, Image As Bitmap, ReturnValue As Object)
	Dim item As ActionItem
	item.Initialize
	item.Text = Text
	item.Image = Image
	item.Value = ReturnValue
	
	If Not(Image.IsInitialized) Then
		mListView.AddTwoLinesAndBitmap2(Text, "", Null, ReturnValue)
	Else
		mListView.AddTwoLinesAndBitmap2(Text, "", Image, ReturnValue)
	End If
End Sub

'Show the SlideMenu
Public Sub Show
	If isVisible = True Then Return
	
	mBackPanel.BringToFront
	mSlidePanel.BringToFront
	mBackPanel.Left = 0
	mSlidePanel.Left = 0
	
	mSlidePanel.Visible = True
	mInAnimation.Start(mSlidePanel)
End Sub

'Hide the SlideMenu
Public Sub Hide
	If isVisible = False Then Return
	
	mBackPanel.Left = -mBackPanel.Width
    mSlidePanel.Left = -mSlidePanel.Width
	mOutAnimation.Start(mSlidePanel)
End Sub

Private Sub Out_AnimationEnd
	mSlidePanel.Visible = False
End Sub

Private Sub mBackPanel_Touch (Action As Int, X As Float, Y As Float)
	If Action = 1 Then
		Hide
	End If
End Sub

Private Sub mListView_ItemClick (Position As Int, Value As Object)
	Dim subname As String
	Hide	
	subname = mEventName & "_Click"
	If SubExists(mModule, subname) Then
		CallSub2(mModule, subname, Value)
	End If
End Sub

'Check if the menu is currently visible
Public Sub isVisible As Boolean
	Return mSlidePanel.Visible
End Sub