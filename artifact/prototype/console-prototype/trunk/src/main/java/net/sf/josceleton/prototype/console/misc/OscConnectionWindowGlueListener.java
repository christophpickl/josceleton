package net.sf.josceleton.prototype.console.misc;

import net.sf.josceleton.prototype.console.view.UserPanel;

public interface OscConnectionWindowGlueListener {
	
	void onAddUserPanel(final UserPanel userPanel);
	
	void onRemoveUserPanel(UserPanel userPanel);
	
	void onUserCountChanged(final int userReadyCount, final int userWaitingCount);
	
}