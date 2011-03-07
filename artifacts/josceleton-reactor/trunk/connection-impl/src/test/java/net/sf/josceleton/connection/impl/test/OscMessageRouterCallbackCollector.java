package net.sf.josceleton.connection.impl.test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import net.sf.josceleton.connection.impl.OscMessageRouterCallback;

import com.illposed.osc.OSCMessage;

public class OscMessageRouterCallbackCollector implements OscMessageRouterCallback {
	
	private final List<OSCMessage> receivedJointMessages = new LinkedList<OSCMessage>();
	
	private final List<OSCMessage> receiveduserMessages = new LinkedList<OSCMessage>();
	
	
	@Override public final void onAcceptedJointMessage(final Date date, final OSCMessage oscMessage) {
		this.receivedJointMessages.add(oscMessage);
	}
	
	@Override public final void onAcceptedUserMessage(final Date date, final OSCMessage oscMessage) {
		this.receiveduserMessages.add(oscMessage);
	}
	
	
	public final List<OSCMessage> getReceivedJointMessages() {
		return this.receivedJointMessages;
	}
	
	public final List<OSCMessage> getReceiveduserMessages() {
		return this.receiveduserMessages;
	}
}
