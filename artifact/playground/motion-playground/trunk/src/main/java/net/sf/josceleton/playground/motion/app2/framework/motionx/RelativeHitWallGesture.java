package net.sf.josceleton.playground.motion.app2.framework.motionx;

import javax.swing.JFrame;

import net.sf.josceleton.Josceleton;
import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.service.motion.ContinuousMotionStream;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Joints;
import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.core.api.entity.location.Coordinate;
import net.sf.josceleton.core.api.entity.location.Direction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RelativeHitWallGesture extends AbstractGesture<RelativeHitWallResult> {
	
	private static final Log LOG = LogFactory.getLog(RelativeHitWallGesture.class);
	
	public static void main(String[] args) {
		Connection c = Josceleton.openConnection();
		ContinuousMotionStream cms = Josceleton.getContinuousMotionStreamFactory().create(c);
		RelativeHitWallGesture g = new RelativeHitWallGesture(Joints.HAND().LEFT());
		cms.addListener(g);
		new JFrame().setVisible(true);
	}
	
	private boolean wasTriggered = false;
	
	private final Direction direction = Direction.Z;
	private final Joint pivotJoint = Joints.TORSO();
	private final Joint joint;
	// TODO maybe should also take 3d distance into account (only straight punches are hard to do if have to hit somewhere near the edge)
	private final float distance = 0.40F;
	
	public RelativeHitWallGesture(final Joint joint) {
		this.joint = joint;
	}
	
	@Override public void onMoved(Joint movedJoint, Coordinate updatedCoordinate, Skeleton skeleton) {
		if(movedJoint.equals(this.joint) == false) {
			return;
		}
		if(skeleton.isAvailableFor(this.pivotJoint) == false) {
			System.err.println("insufficient data");
			return;
		}
		final float jointCoord = this.direction.extractValue(updatedCoordinate);
		final float pivotCoord = this.direction.extractValue(skeleton.get(this.pivotJoint));
		final float diff = pivotCoord - jointCoord;
		final boolean isInWall = diff > this.distance;
		
//		LOG.trace(isInWall + " ==> DIFF "+diff);
		if(isInWall == true && this.wasTriggered == false) {
			LOG.info("HIT! on: " + jointCoord + " for joined: " + this.joint);
			this.dispatchGestureDetected(new RelativeHitWallResult(this, updatedCoordinate, movedJoint));
			this.wasTriggered = true;
//			Toolkit.getDefaultToolkit().beep();
			
		} else if(this.wasTriggered == true && isInWall == false) {
			LOG.debug("UNTRIGGER");
			this.wasTriggered = false;
		}
	}

}
