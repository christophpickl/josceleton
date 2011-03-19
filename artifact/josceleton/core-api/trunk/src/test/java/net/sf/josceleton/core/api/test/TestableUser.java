package net.sf.josceleton.core.api.test;

import net.sf.josceleton.core.api.entity.user.User;

/**
 * @since 0.3
 */
public class TestableUser implements User {

	private final int uniqueId;

	private final int osceletonId;
	
	
	public TestableUser(final int osceletonId) {
		this(1, osceletonId); // no assumptions on internally created id
	}
	
	public TestableUser(final int uniqueId, final int osceletonId) {
		this.uniqueId = uniqueId;
		this.osceletonId = osceletonId;
	}
	
	
	@Override public final int getUniqueId() {
		return this.uniqueId;
	}
	
	@Override public final int getOsceletonId() {
		return this.osceletonId;
	}
	
	@Override public final String toString() {
		return "UserX[id=" + this.uniqueId + ", osceletonId=" + this.osceletonId + "]";
	}
}
