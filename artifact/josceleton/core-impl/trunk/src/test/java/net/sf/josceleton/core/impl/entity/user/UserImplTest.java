package net.sf.josceleton.core.impl.entity.user;

import net.sf.josceleton.core.api.entity.UserTest;
import net.sf.josceleton.core.api.entity.user.User;

public class UserImplTest extends UserTest {

	@Override protected final User createTestee(final int uniqueId, final int osceletonId) {
		return new UserImpl(uniqueId, osceletonId);
	}
	
}
