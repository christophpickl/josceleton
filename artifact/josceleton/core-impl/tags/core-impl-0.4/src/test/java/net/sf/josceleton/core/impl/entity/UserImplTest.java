package net.sf.josceleton.core.impl.entity;

import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.UserTest;

public class UserImplTest extends UserTest {

	@Override protected final User createTestee(final int uniqueId, final int osceletonId) {
		return new UserImpl(uniqueId, osceletonId);
	}
	
}
