Set ws = CreateObject("Wscript.Shell")
Dim currTime,time
currTime = now
currentPath = createobject("Scripting.FileSystemObject").GetFolder(".").Path
t = replace(currTime," ","")			'时间格式中'/',':'不符合win命名规范，需要替换。
t2 = replace(t,"/","")
time = replace(t2,":","")
ws.run "C:\\Users\ALLIN\\AppData\\Roaming\npm\\appium.cmd --log-level info --log-timestamp --log " + currentPath + "\logs\AppiumLog\log"+time+".log",0