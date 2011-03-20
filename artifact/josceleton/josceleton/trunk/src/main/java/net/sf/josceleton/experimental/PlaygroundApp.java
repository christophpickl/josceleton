package net.sf.josceleton.experimental;

import net.sf.josceleton.Josceleton;
import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.Connector;
import net.sf.josceleton.connection.api.service.motion.ContinuousMotionStream;
import net.sf.josceleton.connection.api.service.motion.MotionStream;
import net.sf.josceleton.connection.api.service.motion.MotionStreamFactory;
import net.sf.josceleton.connection.api.service.motion.MotionStreamListener;
import net.sf.josceleton.connection.api.service.user.UserService;
import net.sf.josceleton.connection.api.service.user.UserServiceListener;
import net.sf.josceleton.connection.impl.service.motion.ContinuousMotionStreamFactory;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Joints;
import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.core.api.entity.location.Coordinate;
import net.sf.josceleton.core.api.entity.location.Direction;
import net.sf.josceleton.core.api.entity.user.User;
import net.sf.josceleton.motion.api.gesture.GestureFactoryFacade;
import net.sf.josceleton.motion.api.gesture.hitwall.HitWallGesture;
import net.sf.josceleton.motion.api.gesture.hitwall.HitWallListener;

import com.google.inject.Injector;

class PlaygroundApp {
	
	public static void main(final String[] args) {
		new PlaygroundApp().firstContinuousMotionStreamPlayground();
	}
	
	public final void firstContinuousMotionStreamPlayground() {
		final Injector injector = Josceleton.newGuiceInjector();
		final Connector connector = injector.getInstance(Connector.class);
		final Connection connection = connector.openConnection();
		final ContinuousMotionStreamFactory factory = injector.getInstance(ContinuousMotionStreamFactory.class);
		final ContinuousMotionStream stream = factory.create(connection);
		
		stream.addListener(new MotionStreamListener() {
			// FIXME evtl hat ContinuousMotionStream eigenen listener typ
			// um auch den User durchzureichen! (eigentlich selbe methode, nur mit zusaetzlichem parameter
			@Override public void onMoved(final Joint joint, final Coordinate coordinate, final Skeleton skeleton) {
				if(joint.equals(Joints.HEAD())) {
					System.out.println("MOOOOOVED z: " + Math.round(coordinate.z() * 100));
				}
			}
		});
		
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
	private void firstGesturePlaygroundSetup(final Injector injector, final MotionStreamListener listener) {
		final Connector connector = injector.getInstance(Connector.class);
		final Connection connection = connector.openConnection();
		final MotionStreamFactory factory = injector.getInstance(MotionStreamFactory.class);
		final MotionStream stream = factory.create(connection);
		
		connection.getUserService().addListener(new UserServiceListener() {
			@Override public void onUserWaiting(final User user) {
				System.out.println("onUserWaiting(user=" + user + ")"); }
			
			@Override public void onUserProcessing(final User user) {
				System.out.println("onUserProcessing(user=" + user + ")");
				stream.addListenerFor(user, listener); }
			
			@Override public void onUserDead(final User user) {
				System.out.println("onUserDead(user=" + user + ")");
				stream.removeListenerFor(user, listener); }
			});
	}


}
