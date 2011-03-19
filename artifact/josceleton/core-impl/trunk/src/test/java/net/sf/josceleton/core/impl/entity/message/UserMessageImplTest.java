package net.sf.josceleton.core.impl.entity.message;

import net.sf.josceleton.core.api.entity.message.UserMessage;
import net.sf.josceleton.core.api.entity.message.UserMessageTest;
import net.sf.josceleton.core.api.entity.user.User;
import net.sf.josceleton.core.api.entity.user.UserState;

public class UserMessageImplTest extends UserMessageTest {

	/** {@inheritDoc} from {@link UserMessageTest} */
	@Override protected final UserMessage createTestee(final User user, final UserState userState) {
		return new UserMessageImpl(user, userState);
	}

}
