package net.sf.josceleton.prototype.console.glue;

import net.sf.josceleton.connection.api.service.user.AvailableUsersCollection;
import net.sf.josceleton.prototype.console.view.UserPanel;

public interface GlueCodeListener {
	
	void onAddUserPanel(final UserPanel userPanel);
	
	void onRemoveUserPanel(UserPanel userPanel);
	
	void onUserCountChanged(final AvailableUsersCollection users);
	
}