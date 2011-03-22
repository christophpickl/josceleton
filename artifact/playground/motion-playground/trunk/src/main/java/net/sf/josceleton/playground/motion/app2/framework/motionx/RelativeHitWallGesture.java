package net.sf.josceleton.playground.motion.app2.framework.motionx;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.josceleton.Josceleton;
import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.service.motion.ContinuousMotionStream;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Joints;
import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.core.api.entity.location.Coordinate;
import net.sf.josceleton.core.api.entity.location.Direction;

public class RelativeHitWallGesture extends AbstractGesture<RelativeHitWallResult> {
	
	private static final Log LOG = LogFactory.getLog(RelativeHitWallGesture.class);
	
	public static void main(String[] args) {
		Connection c = Josceleton.openConnection();
		ContinuousMotionStream cms = Josceleton.getContinuousMotionStreamFactory().create(c);
		RelativeHitWallGesture g = new RelativeHitWallGesture();
		cms.addListener(g);
		new JFrame().setVisible(true);
	}
	
	private boolean wasTriggered = false;
	
	private final Direction direction = Direction.Z;
	private final Joint pivotJoint = Joints.TORSO();
	private final Joint joint = Joints.HAND().LEFT();
	private final float distance = 0.48F;
	
	@Override public void onMoved(Joint movedJoint, Coordinate updatedCoordinate, Skeleton skeleton) {
		if(movedJoint.equals(joint) == false) {
			return;
		}
		if(skeleton.isAvailableFor(pivotJoint) == false) {
			System.err.println("insufficient data");
			return;
		}
		final float jointCoord = direction.extractValue(updatedCoordinate);
		final float pivotCoord = direction.extractValue(skeleton.get(pivotJoint));
		final float diff = pivotCoord - jointCoord;
		final boolean isInWall = diff > distance;
		
//		System.out.println(isInWall + " ==> DIFF "+diff);
		if(isInWall == true && wasTriggered == false) {
			LOG.info("HIT!!! on: " + jointCoord + " for joined: " + this.joint);
			this.dispatchGestureDetected(new RelativeHitWallResult(updatedCoordinate));
			this.wasTriggered = true;
//			Toolkit.getDefaultToolkit().beep();
			
		} else if(wasTriggered == true && isInWall == false) {
			LOG.debug("UNTRIGGER");
			this.wasTriggered = false;
		}
	}

}
