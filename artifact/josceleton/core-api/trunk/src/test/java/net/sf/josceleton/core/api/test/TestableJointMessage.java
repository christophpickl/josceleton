package net.sf.josceleton.core.api.test;

import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.location.Coordinate;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.user.User;

/**
 * @since 0.4
 */
public class TestableJointMessage implements JointMessage {
	
	private final User user;

	private final Joint joint;
	
	private final Coordinate coordinate;
	
	
	public TestableJointMessage(final User user, final Joint joint, final Coordinate coordinate) {
		this.user = user;
		this.joint = joint;
		this.coordinate = coordinate;
	}

	@Override public final User getUser() {
		return this.user;
	}
	
	@Override public final Joint getJoint() {
		return this.joint;
	}
	
	@Override public final Coordinate getCoordinate() {
		return this.coordinate;
	}

}
