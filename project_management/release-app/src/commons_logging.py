import traceback

_LOG_LEVEL_TRACE = 10
_LOG_LEVEL_DEBUG = 20
_LOG_LEVEL_INFO  = 30
_LOG_LEVEL_WARN  = 40
_LOG_LEVEL_ERROR = 50
_LOG_LEVEL_FATAL = 60

_LOG_LEVEL_LABELS = {
                     _LOG_LEVEL_TRACE: "TRACE",
                     _LOG_LEVEL_DEBUG: "DEBUG",
                     _LOG_LEVEL_INFO:  "INFO ",
                     _LOG_LEVEL_WARN:  "WARN ",
                     _LOG_LEVEL_ERROR: "ERROR",
                     _LOG_LEVEL_FATAL: "FATAL"
                     }

def loge(msg, exception = None):
    _log(_LOG_LEVEL_ERROR, msg, exception)
def logw(msg = "", exception = None):
    _log(_LOG_LEVEL_WARN, msg, exception)
def logi(msg = "", exception = None):
    _log(_LOG_LEVEL_INFO, msg, exception)
def logd(msg = "", exception = None):
    _log(_LOG_LEVEL_DEBUG, msg, exception)
def logt(msg = "", exception = None):
    _log(_LOG_LEVEL_TRACE, msg, exception)

def _log(lvl, msg, exception):
    lbl = _LOG_LEVEL_LABELS[lvl]
    # ??? sys.stderr.write("[ERROR] %s" % msg)
    if msg.find("\n") == -1:
        print "[%s] %s" % (lbl, msg)
    else:
        for msgPart in msg.split("\n"):
            print "[%s] %s" % (lbl, msgPart)
    if exception != None:
        traceback.print_exc(None)
        