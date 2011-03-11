package net.sf.josceleton.core.api.test;

import net.sf.josceleton.core.api.entity.User;

/**
 * @since 0.3
 */
public class UserX implements User {

	private final int id;

	private final int osceletonId;
	
	
	public UserX(final int osceletonId) {
		this(1, osceletonId); // no assumptions on internally created id
	}
	
	public UserX(final int id, final int osceletonId) {
		this.id = id;
		this.osceletonId = osceletonId;
	}
	
	
	@Override public final int getId() {
		return this.id;
	}
	
	@Override public final int getOsceletonId() {
		return this.osceletonId;
	}
	
	@Override public final String toString() {
		return "UserX[id=" + this.id + ", osceletonId=" + this.osceletonId + "]";
	}
}
