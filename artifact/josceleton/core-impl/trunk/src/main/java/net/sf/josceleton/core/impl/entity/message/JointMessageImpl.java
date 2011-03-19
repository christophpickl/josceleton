package net.sf.josceleton.core.impl.entity.message;

import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.location.Coordinate;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.user.User;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

class JointMessageImpl extends GenericMessageImpl implements JointMessage {
	
	private final Joint joint;
	
	private final Coordinate coordinate;
	
	
	@Inject JointMessageImpl(
			@Assisted final User user,
			@Assisted final Joint joint,
			@Assisted final Coordinate coordinate) {
		super(user);
		this.joint = joint;
		this.coordinate = coordinate;
	}


	/** {@inheritDoc} from {@link JointMessage} */
	@Override public final Joint getJoint() {
		return this.joint;
	}

	/** {@inheritDoc} from {@link JointMessage} */
	@Override public final Coordinate getCoordinate() {
		return this.coordinate;
	}
	
	@Override public final boolean equals(final Object other) {
		if(this == other) { return true; }
		if((other instanceof JointMessage) == false) { return false; }
		final JointMessage that = (JointMessage) other;
		return	this.getUser().equals(that.getUser()) &&
				this.getJoint().equals(that.getJoint()) &&
				this.getCoordinate().equals(that.getCoordinate());
	}

	@Override public final int hashCode() {
		return this.getUser().hashCode();
	}
	
	@Override public final String toString() {
		return "JointMessageImpl[" +
					"user=" + this.getUser() + ", " +
					"joint=" + this.joint + ", " +
					"coordinate=" + this.coordinate +
				"]";
	}

}
