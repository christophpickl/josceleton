package net.sf.josceleton.josceleton;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.Connector;
import net.sf.josceleton.connection.api.service.motion.MotionSupplierListener;
import net.sf.josceleton.connection.api.service.motion.MotionSupplier;
import net.sf.josceleton.connection.api.service.motion.MotionSupplierFactory;
import net.sf.josceleton.connection.api.service.user.UserService;
import net.sf.josceleton.connection.api.service.user.UserServiceListener;
import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.Direction;
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
			.direction(Direction.Y)
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
	private void firstGesturePlaygroundSetup(final Injector injector, final MotionSupplierListener listener) {
		final Connector connector = injector.getInstance(Connector.class);
		final Connection connection = connector.openConnection();
		final MotionSupplierFactory cache = injector.getInstance(MotionSupplierFactory.class);
		final MotionSupplier supplier = cache.create(connection);
		
		connection.getUserService().addListener(new UserServiceListener() {
			@Override public void onUserWaiting(final User user) {
				System.out.println("onUserWaiting(user=" + user + ")"); }
			
			@Override public void onUserProcessing(final User user) {
				System.out.println("onUserProcessing(user=" + user + ")");
				supplier.addListenerFor(user, listener); }
			
			@Override public void onUserDead(final User user) {
				System.out.println("onUserDead(user=" + user + ")");
				supplier.removeListenerFor(user, listener); }
			});
	}

	public final void thisIsHowNewMotionServiceCouldWork() {
		final Connection c = Josceleton.openConnection();
		
		final UserService us = c.getUserService();
		final User u = us.getProcessing().iterator().next();
//		us.addListener(new UserServiceListener() {
//			public void onUserProcessing(User user) {
//				u = ...
		
		final MotionSupplierFactory cache = new MotionSupplierFactory() { // singleton!
			@Override public MotionSupplier create(final Connection c2) {
				return null; } };
//		final MotionSupplier ms = Joseleton.getMotionSupplier(c); { internally calls lookupMotionSupplier }
		final MotionSupplier ms = cache.create(c);
//		
		final MotionSupplierListener msl = new MotionSupplierListener() {
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
