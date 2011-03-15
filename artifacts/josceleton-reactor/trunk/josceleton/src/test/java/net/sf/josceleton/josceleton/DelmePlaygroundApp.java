package net.sf.josceleton.josceleton;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.service.user.UserService;
import net.sf.josceleton.connection.api.service.user.UserServiceListener;
import net.sf.josceleton.core.api.entity.User;

public class DelmePlaygroundApp {
	
	public static void main(final String[] args) {
//		new DelmePlaygroundApp()userServiceDebugger()
	}
	
	public final void userServiceDebugger() {
		final Connection connection = Josceleton.openConnection();
		final UserService service = connection.getUserService();
		
		service.addListener(new UserServiceListener() {
			@Override public void onUserWaiting(final User user) {
				System.out.println("waiting to identify skeleton for: " + user);
			}
			@Override public void onUserProcessing(final User user) {
				System.out.println("skeleton data is now being processed for: " + user);
			}
			
			@Override public void onUserDead(final User user) {
				System.out.println("lost user: " + user);
			}
		});
//		connection.close();
	}
	
}
