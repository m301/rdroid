# This project is unmaintained as of now, but as I get time (In next few months, probably), It will be fixed to work.

## rdroid

Remotely manage your android phone AKA RAT !

## History!
I worked on this project during highschool, in year 2011-2012.
Since, I wasn't familier with versioning systems, Hence, there is no past history of code.

The code is written in pathetic manner, it lacks modularity, comments, test-cases, documentation, schema backup etc but another fact is during that period for me it was an example of good code ;)


## Features 
I tried to keep list small, it has almost all frequently needed actions.
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
	

## How it works ?
Pretty easy,
- Communication between android client and webclient is done using google c2dm. 
- From webclient a notification is sent to device, as soon as notification is recieved data is uploaded to website using webapi.
- For actions which required more data to be sent to device like create sms, actions were taken from php file, more like queue.
Thats it !



## How it's built ?
Web : `PHP`, `MYSQL`, `HTML` :), `CSS`, `jQuery`, few css jquery based plugin/framework like `metroui`, `kendo`, `jstree` etc.

Android : Basic4Android (I was/am a big fan of Visual Basic 6.0, Some examples can be found in another repsitory like [github.com/m301/vb6arc](https://github.com/m301/vb6arc))



#### What next ?
I dont have much time to rewrite code and didn't find reason to keep it with me :)
As this is old code, many logics can be improved as they are bad example of ill-programming practise, but still code will work with minor modification.

#### Future plan ?
It is unmaintained project, if people are interested - I might fix it to make it work.


## Does it work out-of-box ? 
No, this code doesn't works out of box, You need to change domains, figure out the schema, get google gcm keys etc.


## License

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
