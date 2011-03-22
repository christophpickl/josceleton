package net.sf.josceleton.core.impl.entity.user;

import net.sf.josceleton.commons.exception.InvalidArgumentException;
import net.sf.josceleton.core.api.entity.user.User;

class UserImpl implements User {

	private final int uniqueId;
	
	private final int osceletonId;
	
	private final int color;
	
	
//	@Inject UserImpl(@Assisted("uniqueId") final int uniqueId, @Assisted("osceletonId") final int osceletonId) {
	UserImpl(final int uniqueId, final int osceletonId, final int color) {
		if(uniqueId < 1) {
			throw InvalidArgumentException.newInstance("uniqueId", Integer.valueOf(uniqueId), "< 1");
		}
		if(osceletonId < 1) {
			throw InvalidArgumentException.newInstance("osceletonId", Integer.valueOf(osceletonId), "< 1");
		}
		
		// NO!!! if(uniqueId < osceletonId) { // internal (unique) ID must always be >= than (ID-reusing) osceleton ID
		// come on, dont be that restrictive :) ... we could, for example, replay a recorded osceleton session.
		
		this.uniqueId = uniqueId;
		this.osceletonId = osceletonId;
		this.color = color;
	}

	/** {@inheritDoc} from {@link User} */
	@Override public final int getUniqueId() {
		return this.uniqueId;
	}

	/** {@inheritDoc} from {@link User} */
	@Override public final int getOsceletonId() {
		return this.osceletonId;
	}

	/** {@inheritDoc} from {@link User} */
	@Override public final int getColor() {
		return this.color;
	}

	@Override public final boolean equals(final Object other) {
		if(this == other) { return true; }
		if((other instanceof User) == false) { return false; }
		final User that = (User) other;
		return
			this.getUniqueId() == that.getUniqueId() &&
			this.getOsceletonId() == that.getOsceletonId() &&
			this.getColor() == that.getColor();
	}
	
	@Override public final int hashCode() {
		return this.uniqueId;
	}
	
	@Override public final String toString() {
		return "UserImpl[" +
				"uniqueId=" + this.uniqueId + ", " +
				"osceletonId=" + this.osceletonId + ", " +
				"color=" + this.color +
				"]";
	}
	
}
