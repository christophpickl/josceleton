package net.sf.josceleton.prototype.console.glue;

import net.sf.josceleton.connection.api.service.user.AvailableUsersCollection;
import net.sf.josceleton.prototype.console.notification.GrowlNotifier;

public interface GlueCodeFactory {
	
	GlueCode create(OscConnectionWindowGlueListener listener, AvailableUsersCollection users, GrowlNotifier growlNotifier);
	
}
