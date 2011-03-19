package net.sf.josceleton.core.impl.entity.message;

import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.message.JointMessage;

public interface JointMessageFactory {
	
	JointMessage create(User user, Joint joint, Coordinate coordinate);
	
}
