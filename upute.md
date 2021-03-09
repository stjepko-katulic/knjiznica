I have the same issue and I've installed the developers default version of the MySQL-Workbench with the installer. 
I recently updated my hardware and reinstalled Windows 10, maybe it's a bug of Windows 20H2 or of the Workbench itself?

I found a workaround: Use the CMD or the Powershell to start or stop the MySQL-Service of the server.
 In my case, it has the name MySQL80. Take a look in your services of windows when the commands below are not working.

Use <ins>**net start MySQL80**</ins> to start the server. Use <ins>**net stop MySQL80**</ins> to stop the server.

Remember to open the CMD or the Powershell as Administrator. 
I think this issue is a bug of the workbench itself because the service is start and stopable with the command line.
