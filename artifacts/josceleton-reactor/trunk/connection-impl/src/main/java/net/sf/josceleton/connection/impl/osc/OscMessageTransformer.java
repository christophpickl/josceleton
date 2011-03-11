package net.sf.josceleton.connection.impl.osc;

import net.sf.josceleton.connection.impl.service.UserStore;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.message.UserMessage;

import com.illposed.osc.OSCMessage;

public interface OscMessageTransformer {
	
	JointMessage transformJointMessage(OSCMessage oscMessage, UserStore userManager);
	
	UserMessage transformUserMessage(OSCMessage oscMessage, UserStore userManager);
	
}
