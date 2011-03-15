#!/bin/bash
#
# this script is designed to be used from within eclipse:
# 1. configure a new external tool
# 2. set location to: ${workspace_loc:/project_management/mvn_scripts/josceleton_site_and_open.sh}
# 3. working directory to: ${workspace_loc:/josceleton}
# 4. run

mvn site:site -Dreport.skip

if [ $? -eq 0 ]; then
	open target/site/index.html
fi

#say "site done!"
