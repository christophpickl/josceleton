package net.sf.josceleton.josceleton;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.Connector;
import net.sf.josceleton.connection.api.service.motion.MotionListener;
import net.sf.josceleton.connection.api.service.motion.MotionSeparator;
import net.sf.josceleton.connection.api.service.motion.MotionSeparatorCache;
import net.sf.josceleton.connection.api.service.user.UserService;
import net.sf.josceleton.connection.api.service.user.UserServiceListener;
import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.XyzDirection;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Joints;
import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.motion.api.gesture.GestureFactoryFacade;
import net.sf.josceleton.motion.api.gesture.hitwall.HitWallGesture;
import net.sf.josceleton.motion.api.gesture.hitwall.HitWallListener;

import com.google.inject.Injector;

class PlaygroundApp {
	
	public static void main(final String[] args) {
		new PlaygroundApp().firstGesturePlayground();
	}

	public final void firstGesturePlayground() {
		final Injector injector = Josceleton.newGuiceInjector();
		final Joint joint = Joints.HAND().RIGHT();
		
		final GestureFactoryFacade factory = injector.getInstance(GestureFactoryFacade.class);
		final HitWallGesture gesture = factory.newHitWall()
			.direction(XyzDirection.Y)
			.triggerOnLower(true)
			.coordinate(0.5F)
			.relevantJoint(joint)
			.build();
		
		gesture.addListener(new HitWallListener() { @Override public void onGestureDetected(final Skeleton skeleton) {
			System.out.println("[INFO] ==========================>>>>>>> gesture detected on joint (" + joint + ") " +
					"with coordinate value: " + skeleton.get(joint)); } });
		
		this.firstGesturePlaygroundSetup(injector, gesture);
		
		System.out.println("Up and running...");
	}
	private void firstGesturePlaygroundSetup(final Injector injector, final MotionListener listener) {
		final Connector connector = injector.getInstance(Connector.class);
		final Connection connection = connector.openConnection();
		final MotionSeparatorCache cache = injector.getInstance(MotionSeparatorCache.class);
		final MotionSeparator separator = cache.lookupMotionSeparator(connection);
		
		connection.getUserService().addListener(new UserServiceListener() {
			@Override public void onUserWaiting(final User user) {
				System.out.println("onUserWaiting(user=" + user + ")"); }
			
			@Override public void onUserProcessing(final User user) {
				System.out.println("onUserProcessing(user=" + user + ")");
				separator.addListenerFor(user, listener); }
			
			@Override public void onUserDead(final User user) {
				System.out.println("onUserDead(user=" + user + ")");
				separator.removeListenerFor(user, listener); }
			});
	}

	public final void thisIsHowNewMotionServiceCouldWork() {
		final Connection c = Josceleton.openConnection();
		
		final UserService us = c.getUserService();
		final User u = us.getProcessing().iterator().next();
//		us.addListener(new UserServiceListener() {
//			public void onUserProcessing(User user) {
//				u = ...
		
		final MotionSeparatorCache cache = new MotionSeparatorCache() { // singleton!
			@Override public MotionSeparator lookupMotionSeparator(final Connection c2) {
				return null; } };
//		final MotionSeparator ms = Joseleton.getMotionSeparator(c); { internally calls lookupMotionSeparator }
		final MotionSeparator ms = cache.lookupMotionSeparator(c);
//		
		final MotionListener msl = new MotionListener() {
			@Override public void onMoved(final Joint joint, final Coordinate coordinate, final Skeleton skeleton) {
				System.out.println("moved " + joint + " to coordinate: " + coordinate);
		} };
		ms.addListenerFor(u, msl);
		ms.removeListenerFor(u, msl);
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
