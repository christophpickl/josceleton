package net.sf.josceleton.core.impl.entity.message;

import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.location.Coordinate;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.user.User;

public interface JointMessageFactory {
	
	JointMessage create(User user, Joint joint, Coordinate coordinate);
	
}
