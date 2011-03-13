import getpass
import httplib
import os
from commons_logging import * #@UnusedWildImport
from config_global import SYSEXEC_ENABLED, INTERACTIVE


def webFileExists(baseWebUrl, fileToCheck):
    logd("Checking if web file exists: http://%s%s" % (baseWebUrl, fileToCheck))
    if SYSEXEC_ENABLED == False: return True
    
    connection = httplib.HTTPConnection(baseWebUrl)
    connection.request("HEAD", fileToCheck)
    response = connection.getresponse()
    connection.close()
    return response.status == 200

_HIT_ENTER_MESSAGE = "        <================ HIT ENTER ================>"
def hitEnter():
    if INTERACTIVE == True: raw_input(_HIT_ENTER_MESSAGE)
    else: print _HIT_ENTER_MESSAGE

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
    

def uname():
    return getpass.getuser()

def say(text):
    execSystem("say \"%s\"" % text) 

def open(path):
    execSystem("open %" % path)

def mvn(arguments):
    cmd = "mvn %s" % arguments
    execSystem(cmd, filterDashDashDeePasswd(cmd))
    
def svn(arguments):
    cmd = "svn %s" % arguments
    execSystem(cmd, filterDashDashPasswd(cmd))
    
def execSystem(command, displayedCommand = None):
    if displayedCommand == None: displayedCommand = command
    logd(">> %s" % displayedCommand)
    if SYSEXEC_ENABLED == True:
        retCode = os.system(command)
        if retCode != 0: raise Exception("Could not execute command: [%s]!" % displayedCommand)

def mkdir(folder):
    logd("Creating folder: %s" % folder)
    if SYSEXEC_ENABLED == True: os.mkdir(folder)
    
def chdir(path):
    logd("Changing path to: %s" % path)
    if SYSEXEC_ENABLED == True: os.chdir(path)
