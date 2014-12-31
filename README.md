rdroid
======

Remotely manage your android phone.


Features 
==========================
I tried to keep list small, it has nearly everything can be done easily, obviously not eveything.
- Contacts sync - Create, Read, Delete, Delete all contacts ;)
- Device information, everything
- Volumes - media, call, notification - get/set
- is Device connected to internet
- Apps - installed apps list, uninstall, open app
- File structure - Upload, Delete, Create, copy, move, rename
- Remote Media Player
- Audio recording - interval based automatic, manual
- Call Logs
- Vibrate phone
- Ringer mode - vibrate, mute etc.
- Screenbrightness - get/set
- turn loudspeaker on/off
- kill call - instantly, n seconds
- SMS - remove, send real, add fake :)
- Airplane mode - on, off, toogle
- Beep phone at given frequency :D
- Dial a number 
- Clipboard - get/set
- Send MMS
- Open url
- Setwallpaper
- Download file
- Shell command execute
	


History, Should matter you
==========================
I was worked on this project during highschool,in 2011-2012.
I wasn't familier with versioning systems, so there is no past history of code.
The code is written very badly, it lacks proper implementation of modularity but still at that time for me it was a sample of good code ;)


What next ?
===============
I dont have much time to rewrite code and don't find reason to keep it with me :)
As this is old code, many logics can be changed because they are bad example of bad practise, but still code will work with tweaks & modification.


Does it works ? No.   What ?
=============================
Yes this code doesn't works, You need to fix it to make it work.


I can see madsac.in in code.
-------------------------------------------
It was owned by me, I know it is bad practise, i should have used relative urls & path.


It ever worked ?
-----------------
Yes, it was working in past, i used to show it to classmates ;)


How it works ?
----------------
Pretty easy,
Communication between client was using google c2dm. 
From webclient a notification was sent to device, as soon as notification was recieved data was uploaded to website.
For bigger actions like create sms, actions were taken from php file, more like queue.
Thats it.


What languages you used ?
==========================
Web : PHP, MYSQL, HTML :), CSS, jQuery, few css jquery based plugin/framework like metroui, kendo, jstree etc.

Android : Basic4Android (I was/am a big fan of Visual Basic 6.0, May release another project)


Future plan ?
==============
Currently i am working as freelancer, as i want to fund my other projects (robotics stuff) & study.
I will not be getting much time to make major changes,but i will try to fix current version slowly.
If i am able to generate enough revenue either from donation or from commercial license, i will start increasing time spent on this project.
Moreover, if i get enough donation i will remove commerical license.

By donation i means, donating for opensource license as you want to improve the project.


A quick note
=============
This is my first opensource project, If you think that changing license will benefit project & me or have anyother suggestion please contact me. 


License
==================================
Code is released under dual license Apache 2.0/ Commerical.
Contributors can be granted commercial license, for free.

```
Copyright 2014-2015 Madhurendra Sachan.

rDroid is dual-licensed.

Cases that include commercialization of rdroid require a commercial, non-free license. Otherwise, the system can be used under the terms of Apache License v2.0, found at the bottom of this document.

Cases of commercialization are:

Using rdroid to provide commercial managed/Software-as-a-Service services.
Distributing rdroid as a commercial product or as part of one.
Cases which don't require a commercial license, and thus fall under the terms of Apache License v2.0, include (but are not limited to):

So long as that doesn't conflict with the commercialization clause.
Using rdroid for personal use.
Any non-commercial use of rdroid.
If you need to acquire a commercial license or are unsure about whether you need to acquire a commercial license, please get in touch, we'll be happy to clarify things for you and work with you to accommodate your requirements.

You can use the licensing contact form to contact us about these matters.

Code contributions will be accepted under the Apache License v2.0.
```
Note : License can only be upgraded which means, if i ever change license it will be towards opensource. Eg: removing commercial license.
