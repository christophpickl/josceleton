package net.sf.josceleton.connection.impl.test;

import java.util.LinkedList;
import java.util.List;

import net.sf.josceleton.connection.api.ConnectionListener;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.message.UserMessage;

public class TestableConnectionListener implements ConnectionListener {
	
	private final List<JointMessage> receivedJointMessages = new LinkedList<JointMessage>();
	
	private final List<UserMessage> receivedUserMessages = new LinkedList<UserMessage>();
	
	
	@Override public void onJointMessage(final JointMessage message) {
		this.receivedJointMessages.add(message);
	}

	@Override public void onUserMessage(final UserMessage message) {
		this.receivedUserMessages.add(message);
	}
	
	public final List<JointMessage> getReceivedJointMessages() {
		return this.receivedJointMessages;
	}
	
	public final List<UserMessage> getReceivedUserMessages() {
		return this.receivedUserMessages;
	}
	
}
