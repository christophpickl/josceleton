import config_global
import sys
from commons import *
from Release import Release
from PostconditionChecker import PostconditionChecker
from config_global import PRECONDITIONS_ENABLED, SYSEXEC_ENABLED


def loadDynamicConfiguration(configModuleName):
    exec('from %s import Configuration' % configModuleName)
    loadedConfig = Configuration() #@UndefinedVariable
    
    logi("Loaded dynamic configuration from Module [%s.py]" % configModuleName)
    logd("  Username: %s" % loadedConfig.username)

    return loadedConfig


if __name__ == "__main__":
    print "SYSEXEC_ENABLED = %s" % SYSEXEC_ENABLED 
    print "PRECONDITIONS_ENABLED = %s" % PRECONDITIONS_ENABLED

    configModuleName = "config_%s" % uname()
    try:
        config = loadDynamicConfiguration(configModuleName)
    except ImportError:
        loge("Could not find configuration module [%s]!" % configModuleName)
        sys.exit(1)
    
    Release().createNewWith(config)