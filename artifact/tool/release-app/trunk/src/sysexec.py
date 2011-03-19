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
    
    notifyi("ATTENTION", "Confirmation required.")
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
    if INTERACTIVE == True:
        notifyi("ATTENTION", "Interaction required.")
        raw_input(_HIT_ENTER_MESSAGE)
    else:
        print _HIT_ENTER_MESSAGE

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

def sysexec(command, displayedCommand = None, overrideSysexecWithTrue = False):
    if displayedCommand == None: displayedCommand = command
    if SYSEXEC_ENABLED == True or overrideSysexecWithTrue == True:
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

_LOGS_APPNAME = "LogSmiley Dispatcher"
_LOGS_APPICON = "LogSmiley"
_LOGE_APPNAME = "LogError Dispatcher"
_LOGE_APPICON = "LogError"
_LOGW_APPNAME = "LogWarning Dispatcher"
_LOGW_APPICON = "LogWarning"
_LOGI_APPNAME = "LogInfo Dispatcher"
_LOGI_APPICON = "LogInfo"
_LOGD_APPNAME = "LogDebug Dispatcher"
_LOGD_APPICON = "LogDebug"
_LOGT_APPNAME = "LogTrace Dispatcher"
_LOGT_APPICON = "LogTrace"

def registerGrowl():
    _register(_LOGS_APPNAME, _LOGS_APPICON)
    _register(_LOGE_APPNAME, _LOGE_APPICON)
    _register(_LOGW_APPNAME, _LOGW_APPICON)
    _register(_LOGI_APPNAME, _LOGI_APPICON)
    _register(_LOGD_APPNAME, _LOGD_APPICON)
    _register(_LOGT_APPNAME, _LOGT_APPICON)
def _register(logLevelAppName, logLevelAppIcon):
    _osascript(["register as application \\\"%s\\\" all notifications { \\\"LOG\\\" } default notifications { \\\"LOG\\\" } icon of application \\\"%s\\\"" %
                    (logLevelAppName, logLevelAppIcon) ])
    
def notifys(title, message): _notify(title, message, _LOGS_APPNAME)
def notifye(title, message): _notify("ERROR! %s" % title, message, _LOGE_APPNAME)
def notifyw(title, message): _notify("WARNING! %s" % title, message, _LOGW_APPNAME)
def notifyi(title, message): _notify(title, message, _LOGI_APPNAME)
def notifyd(title, message): _notify(title, message, _LOGD_APPNAME)
def notifyt(title, message): _notify(title, message, _LOGT_APPNAME)
def _notify(title, message, logLevelAppName):
    _osascript([ "notify with name \\\"LOG\\\" title \\\"%s\\\" description \\\"%s\\\" application name \\\"%s\\\"" %
                       (title.replace("\"", "'"), message.replace("\"", "'"), logLevelAppName), ])
    
def _osascript(lines):
    lines.insert(0, "tell application \\\"GrowlHelperApp\\\"")
    lines.append("end tell")
    sysexec("osascript " + " ".join([ "-e \"" + i + "\"" for i in lines ]), "osascript -e ...", True) # override SYSEXEC
