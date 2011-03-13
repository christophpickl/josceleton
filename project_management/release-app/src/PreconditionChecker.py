import os
from commons import hitEnter

class PreconditionChecker:
    
    def areSatisfied(self, config):
        
        self._confirm("Check that you have opened a SSH shell to sourceforge via:\n\
\n\
    ssh -t %s,josceleton@shell.sf.net create" % config.username)
        
        
        self._confirm("Have you updated all POM versions?\n\
(parent references, properties.versions in parent-pom)")
        
#        if self._sshShellExisting(config) == False:
#            fooo

        return True
    
    def _confirm(self, message):
        print message
        print
        hitEnter()
        
    def _sshShellExisting(self, config):
        sshUser = config.username + ",josceleton"
        cmd = "ssh %s@shell.sf.net ls" % sshUser
        print cmd
        retCode = os.system(cmd) # FIXME FAILS!!!!!!!!!!!!
#        ssh_askpass: exec(/usr/libexec/ssh-askpass): No such file or directory
#        Permission denied, please try again.
#        ssh_askpass: exec(/usr/libexec/ssh-askpass): No such file or directory
#        Permission denied, please try again.
#        ssh_askpass: exec(/usr/libexec/ssh-askpass): No such file or directory
#        Permission denied (publickey,password,keyboard-interactive).
#        retCode: 65280

        print "retCode:", retCode
        return retCode == 0
