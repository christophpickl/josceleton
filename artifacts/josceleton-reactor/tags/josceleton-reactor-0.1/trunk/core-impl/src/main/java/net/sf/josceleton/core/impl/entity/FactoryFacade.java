package net.sf.josceleton.core.impl.entity;

import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.UserState;
import net.sf.josceleton.core.api.entity.body.BodyPart;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.message.UserMessage;

public interface FactoryFacade { // extends renamed CoordinateFactory, JointMessageFactory, UserMessageFactory {
	
	Coordinate createCoordinate(float x, float y, float z);
	
	JointMessage createJointMessage(User user, BodyPart jointPart, Coordinate coordinate);
	
	UserMessage createUserMessage(User user, UserState userState);
	
}
