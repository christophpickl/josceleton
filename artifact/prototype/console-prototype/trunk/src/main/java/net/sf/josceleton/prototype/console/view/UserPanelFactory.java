package net.sf.josceleton.prototype.console.view;

import net.sf.josceleton.core.api.entity.User;

public interface UserPanelFactory {
	
	UserPanel create(User user);
	
}
