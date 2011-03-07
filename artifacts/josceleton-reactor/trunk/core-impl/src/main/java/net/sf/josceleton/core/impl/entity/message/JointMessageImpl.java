package net.sf.josceleton.core.impl.entity.message;

import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.body.BodyPart;
import net.sf.josceleton.core.api.entity.message.JointMessage;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

class JointMessageImpl extends GenericMessageImpl implements JointMessage {
	
	private final BodyPart jointPart;
	
	private final Coordinate coordinate;
	
	
	@Inject JointMessageImpl(
			@Assisted final User user,
			@Assisted final BodyPart jointPart,
			@Assisted final Coordinate coordinate) {
		super(user);
		this.jointPart = jointPart;
		this.coordinate = coordinate;
	}


	/** {@inheritDoc} from {@link JointMessage} */
	@Override public final BodyPart getJointPart() {
		return this.jointPart;
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
				this.getJointPart().equals(that.getJointPart()) &&
				this.getCoordinate().equals(that.getCoordinate());
	}

	@Override public final int hashCode() {
		return this.getUser().hashCode();
	}
	
	@Override public final String toString() {
		return "JointMessageImpl[" +
					"user=" + this.getUser() + ", " +
					"jointPart=" + this.jointPart + ", " +
					"coordinate=" + this.coordinate +
				"]";
	}

}
