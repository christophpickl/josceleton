package net.sf.josceleton.connection.api.test;

import java.util.LinkedList;
import java.util.List;

import net.sf.josceleton.connection.api.service.UserServiceListener;
import net.sf.josceleton.core.api.entity.User;

public class UserServiceListenerCollector implements UserServiceListener {
	
	// MINOR @CODE DRY introduce "abstract/generic collecting listener" thingy (for each onXyz()
	//					=> get arguments and store in list for each method)
	
	private List<User> deadUsers = new LinkedList<User>(); 
	
	private List<User> processingUsers = new LinkedList<User>(); 
	
	private List<User> waitingUsers = new LinkedList<User>(); 
	
	
	@Override public final void onUserDead(final User user) {
		this.deadUsers.add(user);
	}
	
	@Override public final void onUserProcessing(final User user) {
		this.processingUsers.add(user);
	}
	
	@Override public final void onUserWaiting(final User user) {
		this.waitingUsers.add(user);
	}
	
	
	public final List<User> getDeadUsers() {
		return this.deadUsers;
	}
	
	public final List<User> getProcessingUsers() {
		return this.processingUsers;
	}
	
	public final List<User> getWaitingUsers() {
		return this.waitingUsers;
	}
	
}
