package net.sf.josceleton.josceleton;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.service.UserService;
import net.sf.josceleton.connection.api.service.UserServiceListener;
import net.sf.josceleton.core.api.entity.User;

public class DelmePlaygroundApp {
	
	public static void main(String[] args) {
//		userServiceDebugger()
	}
	
	public static void userServiceDebugger() {
		Connection connection = Josceleton.openConnection();
		UserService service = connection.getUserService();
		
		service.addListener(new UserServiceListener() {
			@Override public void onUserWaiting(User user) {
				System.out.println("waiting to identify skeleton for: " + user);
			}
			@Override public void onUserProcessing(User user) {
				System.out.println("skeleton data is now being processed for: " + user);
			}
			
			@Override public void onUserDead(User user) {
				System.out.println("lost user: " + user);
			}
		});
//		connection.close();
	}
	
}
