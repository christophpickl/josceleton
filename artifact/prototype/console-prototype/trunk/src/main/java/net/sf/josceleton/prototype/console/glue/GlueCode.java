package net.sf.josceleton.prototype.console.glue;

import net.sf.josceleton.connection.api.ConnectionListener;
import net.sf.josceleton.connection.api.service.user.UserServiceListener;

public interface GlueCode extends UserServiceListener, ConnectionListener {
	
	void initAvailableUsers();
	
}
