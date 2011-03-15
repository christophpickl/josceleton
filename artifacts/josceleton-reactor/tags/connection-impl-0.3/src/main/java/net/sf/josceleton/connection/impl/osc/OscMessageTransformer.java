package net.sf.josceleton.connection.impl.osc;

import net.sf.josceleton.connection.impl.service.UserStore;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.message.UserMessage;

import com.illposed.osc.OSCMessage;

public interface OscMessageTransformer {

	/**
	 * Expected OSCMessage arguments:
	 * 
	 * [0]:String ... the (body) joint part, eg: "l_hand"
	 * [1]:Integer ... osceleton specific user ID
	 * [2]:Float ... value of X coordinate
	 * [3]:Float ... value of Y coordinate
	 * [4]:Float ... value of Z coordinate
	 */
	JointMessage transformJointMessage(OSCMessage oscMessage, UserStore userStore);
	
	/**
	 * 
	 * @param oscMessage
	 * @param userStore
	 * @return
	 */
	UserMessage transformUserMessage(OSCMessage oscMessage, UserStore userStore);
	
}
