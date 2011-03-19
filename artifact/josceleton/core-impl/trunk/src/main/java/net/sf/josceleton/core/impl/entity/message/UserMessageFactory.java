package net.sf.josceleton.core.impl.entity.message;

import net.sf.josceleton.core.api.entity.message.UserMessage;
import net.sf.josceleton.core.api.entity.user.User;
import net.sf.josceleton.core.api.entity.user.UserState;

public interface UserMessageFactory {
	
	UserMessage create(User user, UserState userState);
	
}
