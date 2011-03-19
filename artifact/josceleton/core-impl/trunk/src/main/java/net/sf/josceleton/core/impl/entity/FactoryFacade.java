package net.sf.josceleton.core.impl.entity;

import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.location.Coordinate;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.message.UserMessage;
import net.sf.josceleton.core.api.entity.user.User;
import net.sf.josceleton.core.api.entity.user.UserState;

public interface FactoryFacade { // extends renamed CoordinateFactory, JointMessageFactory, UserMessageFactory {
	
	Coordinate createCoordinate(float x, float y, float z);
	
	JointMessage createJointMessage(User user, Joint joint, Coordinate coordinate);
	
	UserMessage createUserMessage(User user, UserState userState);
	
}
