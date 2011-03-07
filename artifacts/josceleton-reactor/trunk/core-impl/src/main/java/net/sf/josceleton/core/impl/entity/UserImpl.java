package net.sf.josceleton.core.impl.entity;

import net.sf.josceleton.commons.exception.InvalidArgumentException;
import net.sf.josceleton.core.api.entity.User;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

class UserImpl implements User {

	private final int id;
	
	private final int osceletonId;
	
	
	@Inject UserImpl(@Assisted("id") final int id, @Assisted("osceletonId") final int osceletonId) {
		// LUXURY @AOP CONTRACT add precondition argument check
		//                  maybe not for coordinate, as too many instances will be created (performance, you know...)
		if(id < 1) {
			throw InvalidArgumentException.newInstance("id", Integer.valueOf(id), "< 1");
		}
		if(osceletonId < 1) {
			throw InvalidArgumentException.newInstance("osceletonId", Integer.valueOf(osceletonId), "< 1");
		}
		if(id < osceletonId) { // internal (unique) ID must always be >= than (ID-reusing) osceleton ID
			throw InvalidArgumentException.newInstance("id, osceletonId", id + ", " + osceletonId, "id >= osceletonId");
		}
		
		this.id = id;
		this.osceletonId = osceletonId;
		
		
	}

	/** {@inheritDoc} from {@link User} */
	@Override public final int getId() {
		return this.id;
	}

	/** {@inheritDoc} from {@link User} */
	@Override public final int getOsceletonId() {
		return this.osceletonId;
	}

	@Override public final boolean equals(final Object other) {
		if(this == other) { return true; }
		if((other instanceof User) == false) { return false; }
		final User that = (User) other;
		return this.getOsceletonId() == that.getOsceletonId() && this.getId() == that.getId();
	}
	
	@Override public final int hashCode() {
		return this.id;
	}
	
	@Override public final String toString() {
		return "UserImpl[id=" + this.id + ", osceletonId=" + this.osceletonId + "]";
	}
	
}
