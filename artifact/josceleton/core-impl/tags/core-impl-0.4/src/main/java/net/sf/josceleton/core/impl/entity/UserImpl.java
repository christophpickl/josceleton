package net.sf.josceleton.core.impl.entity;

import net.sf.josceleton.commons.exception.InvalidArgumentException;
import net.sf.josceleton.core.api.entity.User;

class UserImpl implements User {

	private final int uniqueId;
	
	private final int osceletonId;
	
	
//	@Inject UserImpl(@Assisted("uniqueId") final int uniqueId, @Assisted("osceletonId") final int osceletonId) {
	UserImpl(final int uniqueId, final int osceletonId) {
		if(uniqueId < 1) {
			throw InvalidArgumentException.newInstance("uniqueId", Integer.valueOf(uniqueId), "< 1");
		}
		if(osceletonId < 1) {
			throw InvalidArgumentException.newInstance("osceletonId", Integer.valueOf(osceletonId), "< 1");
		}
		if(uniqueId < osceletonId) { // internal (unique) ID must always be >= than (ID-reusing) osceleton ID
			throw InvalidArgumentException.newInstance("uniqueId, osceletonId", uniqueId + ", " + osceletonId,
					"uniqueId >= osceletonId");
		}
		
		this.uniqueId = uniqueId;
		this.osceletonId = osceletonId;
		
		
	}

	/** {@inheritDoc} from {@link User} */
	@Override public final int getUniqueId() {
		return this.uniqueId;
	}

	/** {@inheritDoc} from {@link User} */
	@Override public final int getOsceletonId() {
		return this.osceletonId;
	}

	@Override public final boolean equals(final Object other) {
		if(this == other) { return true; }
		if((other instanceof User) == false) { return false; }
		final User that = (User) other;
		return this.getOsceletonId() == that.getOsceletonId() && this.getUniqueId() == that.getUniqueId();
	}
	
	@Override public final int hashCode() {
		return this.uniqueId;
	}
	
	@Override public final String toString() {
		return "UserImpl[uniqueId=" + this.uniqueId + ", osceletonId=" + this.osceletonId + "]";
	}
	
}
