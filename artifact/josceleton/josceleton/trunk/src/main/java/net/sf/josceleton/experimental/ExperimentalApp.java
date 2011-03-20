package net.sf.josceleton.experimental;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.Connector;
import net.sf.josceleton.connection.api.service.motion.ContinuousMotionStream;
import net.sf.josceleton.connection.api.service.motion.MotionStreamListener;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Joints;
import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.core.api.entity.location.Coordinate;
import net.sf.josceleton.core.api.entity.location.Direction;
import net.sf.josceleton.core.api.entity.location.Range;
import net.sf.josceleton.core.api.entity.location.RangeScaler;
import net.sf.josceleton.motion.api.gesture.hitwall.HitWallGesture;

public class ExperimentalApp {
	
	public static void main(final String[] args) {
		new ExperimentalApp().doit();
	}
	
	public final void doit() {
		final Connector connector = new JosceletonConnector();
		final Connection connection = connector.openConnection();
		
//		connection.addListener(ConnectionListener)
		
//		final UserService userService = connection.getUserService();
//		final Collection<User> users = userService.getProcessing() / getWaiting()
//		userService.addListener(UserServiceListener)
		
//		final MotionStream motionStream = new JosceletonMotionStream(connection);
//		motionStream.addListenerFor(User, MotionStreamListener)
		
//		FIXME REDESIGN: cmStream = new JosceletonContinuousMotionStream( ---> motionStream <--- );
		final ContinuousMotionStream cmStream = new JosceletonContinuousMotionStream(connection);
//		cmStream.addListener(MotionStreamListener)
		
		final HitWallGesture gesture = JosceletonGestures.newHitWall().
				coordinate(0.4F).direction(Direction.X).relevantJoint(Joints.HEAD()).build();
//		gesture.addListener(HitWallListener)
		cmStream.addListener(gesture);
		
		this.useRangeScaler(cmStream, Joints.TORSO(), Direction.X, new JosceletonRange(0.0F, 1.0F, 0, 127));
	}
	
	private void useRangeScaler(final ContinuousMotionStream cmStream, final Joint joint,
			final Direction direction, final Range range) {
		final RangeScaler scaler = new JosceletonRangeScaler();
		cmStream.addListener(new MotionStreamListener() {
			@Override public final void onMoved(
					final Joint movedJoint, final Coordinate updatedCoordinate, final Skeleton skeleton) {
				if(movedJoint.equals(joint) == true) {
					final float coordinateValue = direction.extractValue(updatedCoordinate);
					final int scaledValue = scaler.scale(coordinateValue, range);
					System.out.println("Scaled value: " + scaledValue);
				}
			}
		});
	}
	
}
