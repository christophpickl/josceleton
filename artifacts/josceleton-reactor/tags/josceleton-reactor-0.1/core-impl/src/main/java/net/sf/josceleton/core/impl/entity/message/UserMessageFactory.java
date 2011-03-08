package net.sf.josceleton.core.impl.entity.message;

import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.UserState;
import net.sf.josceleton.core.api.entity.message.UserMessage;

public interface UserMessageFactory {
	
	UserMessage create(User user, UserState userState);
	
}
