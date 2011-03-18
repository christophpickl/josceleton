import os
import getpass

from config_global import INTERACTIVE, SYSEXEC_ENABLED
from logging import * #@UnusedWildImport


def inputConfirmation(overwriteInteractiveFlag = False):
    yesCmd = "yes"
    noCmd = "no"
    prompt = "Confirm [%s/%s] >> " % (yesCmd, noCmd)
    
    if INTERACTIVE == False and overwriteInteractiveFlag == False:
        print "%s DEFAULT YES" % prompt
        return True
    
    while True:
        userEntered = raw_input(prompt)
        if userEntered == yesCmd:
            return True
        if userEntered == noCmd:
            return False

def filterDashDashDeePasswd(subject):
    token = "-Dpassword"
    idxToken = subject.find(token)
    if idxToken == -1:
        return subject
    
    idxBeforePass = idxToken + len(token)
    idxUntilNextWhitepsace = subject[idxBeforePass:].find(" ")
    if idxUntilNextWhitepsace == -1:
        endPart = ""
    else:
        endPart = subject[idxBeforePass + idxUntilNextWhitepsace:]
    
    return subject[0:idxBeforePass] + "=*****" + endPart

    
def filterDashDashPasswd(subject):
    token = "--password"
    idxToken = subject.find(token)
    if idxToken == -1:
        return subject
    
    idxBeforePass = idxToken + len(token)
    idxUntilNextWhitepsace = subject[idxBeforePass:].strip().find(" ")
    if idxUntilNextWhitepsace == -1:
        passwd = subject[idxBeforePass:].strip()
    else:
        passwd = subject[idxBeforePass:idxBeforePass+idxUntilNextWhitepsace+1].strip()
    idxAfterPass = idxBeforePass + len(passwd) + 2
    return subject[0 : idxBeforePass] + " ***** " + subject[idxAfterPass:]
    


_HIT_ENTER_MESSAGE = "        <================ HIT ENTER ================>"
def hitEnter():
    if INTERACTIVE == True: raw_input(_HIT_ENTER_MESSAGE)
    else: print _HIT_ENTER_MESSAGE

def uname():
    return getpass.getuser()
    
def chdir(path):
    logd("Changing path to: %s" % path)
    if SYSEXEC_ENABLED == True: os.chdir(path)

def mkdir(folder):
    logd("Creating folder: %s" % folder)
    if SYSEXEC_ENABLED == True: os.mkdir(folder)

def writeFile(path, content):
    logd("Creating textfile: %s" % path)
    if SYSEXEC_ENABLED == False: return
    openfile = open(path,"w")
    openfile.write(content)
    openfile.close()

def sysexec(command, displayedCommand = None):
    if displayedCommand == None: displayedCommand = command
    if SYSEXEC_ENABLED == True:
        logd(">> %s" % displayedCommand)
        retCode = os.system(command)
        if retCode != 0: raise Exception("Could not execute command: [%s]!" % displayedCommand)
    else:
        logd("SKIPPED >> %s" % displayedCommand)

def sayOsx(text):
    sysexec("say \"%s\"" % text) 

def openOsx(path):
    sysexec("open %" % path)

def mvn(arguments):
    cmd = "mvn %s" % arguments
    sysexec(cmd, filterDashDashDeePasswd(cmd))
    
def svn(arguments):
    cmd = "svn %s" % arguments
    sysexec(cmd, filterDashDashPasswd(cmd))
    
def wget(arguments):
    sysexec("wget %s" % arguments) 


_GROWL_NOTIFICATION_NAME = "Common Notification"
_GROWL_APP_NAME = "Josceleton Release Application"
def registerGrowl():
    _osascript(["register as application \\\"%s\\\" all notifications { \\\"%s\\\" } default notifications { \\\"%s\\\" } icon of application \\\"%s\\\"" %
                    (_GROWL_APP_NAME, _GROWL_NOTIFICATION_NAME, _GROWL_NOTIFICATION_NAME, "Script Editor") ])
def notify(title, message):
    _osascript([ "notify with name \\\"%s\\\" title \\\"%s\\\" description \\\"%s\\\" application name \\\"%s\\\"" %
                       (_GROWL_NOTIFICATION_NAME, title.replace("\"", "'"), message.replace("\"", "'"), _GROWL_APP_NAME), ])
def _osascript(lines):
    lines.insert(0, "tell application \\\"GrowlHelperApp\\\"")
    lines.append("end tell")
    sysexec("osascript " + " ".join([ "-e \"" + i + "\"" for i in lines ]), "osascript -e ...")

if __name__ == "__main__":
    registerGrowl()
    notify("myTitle", "myMessage")
    notify("myTitle", "myMessage 2")
    