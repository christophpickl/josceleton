import datetime
import sys

from commons import * #@UnusedWildImport
from logging import * #@UnusedWildImport

from Releaser import Releaser #@UnusedImport
from Packager import Packager
from config_global import PRECONDITIONS_ENABLED
from sysexec import uname

def loadDynamicConfiguration(configModuleName):
    exec('from %s import Configuration' % configModuleName)
    loadedConfig = Configuration() #@UndefinedVariable
    
    logi("Loaded dynamic configuration from Module [%s.py]" % configModuleName)
    logd("  Username: %s" % loadedConfig.username)
    logd("  Workspace: %s" % loadedConfig.workspace)
    logd("  Configured Artifacts: %s" % len(loadedConfig.artifacts))

    return loadedConfig


if __name__ == "__main__":
    timeStart = datetime.datetime.now()
    # LUXURY pause timer when waiting for user interaction
    
    logd("Starting app with configuration:")
    logd("  SYSEXEC_ENABLED = %s" % SYSEXEC_ENABLED)
    logd("  PRECONDITIONS_ENABLED = %s" % PRECONDITIONS_ENABLED)

    configModuleName = "config_%s" % uname()
    try:
        config = loadDynamicConfiguration(configModuleName)
    except ImportError:
        loge("Could not find configuration module [%s]!" % configModuleName)
        sys.exit(1)
    
    os.environ['PATH'] = "%s:%s" % (os.environ['PATH'], "/opt/local/bin")
    
    wasAnError = True
    try:
#        Releaser().createNewWith(config)
        Packager().wrapUp(config)
        wasAnError = False
    except Exception as e:
        loge("Fatal application error: %s" % e, e)
        sys.exit(1)
    
    timeEnd = datetime.datetime.now()
    duration = timeEnd - timeStart
    logi("(needed %s)" % duration)
    logi()
    if wasAnError == False: logi("SUCCESS")
    else: loge("ERROR!!!")
    
    