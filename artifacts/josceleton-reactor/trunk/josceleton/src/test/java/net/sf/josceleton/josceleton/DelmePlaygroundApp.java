package net.sf.josceleton.josceleton;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.service.motion.MotionSeparator;
import net.sf.josceleton.connection.api.service.motion.MotionSeparatorCache;
import net.sf.josceleton.connection.api.service.motion.MotionSeparatorListener;
import net.sf.josceleton.connection.api.service.user.UserService;
import net.sf.josceleton.connection.api.service.user.UserServiceListener;
import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.body.BodyPart;
import net.sf.josceleton.core.api.entity.body.Skeleton;

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

	public static void thisIsHowNewMotionServiceCouldWork() {
		final Connection c = Josceleton.openConnection();
		
		final UserService us = c.getUserService();
		final User u = us.getProcessingUsers().iterator().next();
//		us.addListener(new UserServiceListener() {
//			public void onUserProcessing(User user) {
//				u = ...
		
		final MotionSeparatorCache cache = new MotionSeparatorCache() { // singleton!
			@Override public MotionSeparator lookupMotionSeparator(final Connection c2) {
				return null; } };
//		final MotionSeparator ms = Joseleton.getMotionSeparator(c); { internally calls lookupMotionSeparator }
		final MotionSeparator ms = cache.lookupMotionSeparator(c);
//		
		final MotionSeparatorListener msl = new MotionSeparatorListener() {
			@Override public void onMoved(final BodyPart part, final Coordinate coordinate, final Skeleton skeleton) {
				System.out.println("moved part " + part + " to coordinate: " + coordinate);
		} };
		ms.addListenerFor(u, msl);
		ms.removeListenerFor(u, msl);
	}
	
}
