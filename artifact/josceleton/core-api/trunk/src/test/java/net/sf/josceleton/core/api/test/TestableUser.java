package net.sf.josceleton.core.api.test;

import net.sf.josceleton.core.api.entity.user.User;

/**
 * @since 0.3
 */
public class TestableUser implements User {

	private final int uniqueId;
	private final int osceletonId;
	private final int color;
	

	public TestableUser(final int osceletonId) {
		this(1, osceletonId, 0x999999);
	}

	public TestableUser(final int osceletonId, final int color) {
		this(1, osceletonId, color); // no assumptions on internally created id
	}
	
	public TestableUser(final int uniqueId, final int osceletonId, final int color) {
		this.uniqueId = uniqueId;
		this.osceletonId = osceletonId;
		this.color = color;
	}
	
	
	@Override public final int getUniqueId() {
		return this.uniqueId;
	}
	
	@Override public final int getOsceletonId() {
		return this.osceletonId;
	}
	
	@Override public final int getColor() {
		return this.color;
	}
	
	@Override public final String toString() {
		return "TestableUser[id=" + this.uniqueId + ", osceletonId=" + this.osceletonId + ", color=" + this.color + "]";
	}
}
