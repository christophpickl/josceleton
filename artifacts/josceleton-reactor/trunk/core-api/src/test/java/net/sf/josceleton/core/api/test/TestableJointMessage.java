package net.sf.josceleton.core.api.test;

import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.body.BodyPart;
import net.sf.josceleton.core.api.entity.message.JointMessage;

/**
 * @since 0.4
 */
public class TestableJointMessage implements JointMessage {
	
	private final User user;

	private final BodyPart jointPart;
	
	private final Coordinate coordinate;
	
	
	public TestableJointMessage(final User user, final BodyPart jointPart, final Coordinate coordinate) {
		this.user = user;
		this.jointPart = jointPart;
		this.coordinate = coordinate;
	}

	@Override public final User getUser() {
		return this.user;
	}
	
	@Override public final BodyPart getJointPart() {
		return this.jointPart;
	}
	
	@Override public final Coordinate getCoordinate() {
		return this.coordinate;
	}

}
