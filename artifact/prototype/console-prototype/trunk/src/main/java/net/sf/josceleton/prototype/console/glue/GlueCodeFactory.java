package net.sf.josceleton.prototype.console.glue;

import net.sf.josceleton.connection.api.service.user.UsersCollection;
import net.sf.josceleton.prototype.console.notification.GrowlNotifier;

public interface GlueCodeFactory {
	
	GlueCode create(GlueCodeListener listener, UsersCollection users, GrowlNotifier growlNotifier);
	
}
