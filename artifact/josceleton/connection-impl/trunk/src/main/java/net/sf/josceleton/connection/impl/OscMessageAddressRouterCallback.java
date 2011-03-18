package net.sf.josceleton.connection.impl;

import java.util.Date;

import com.illposed.osc.OSCMessage;

public interface OscMessageAddressRouterCallback {
	
	void onAcceptedJointMessage(Date date, OSCMessage oscMessage);
	
	void onAcceptedUserMessage(Date date, OSCMessage oscMessage);
	
}
