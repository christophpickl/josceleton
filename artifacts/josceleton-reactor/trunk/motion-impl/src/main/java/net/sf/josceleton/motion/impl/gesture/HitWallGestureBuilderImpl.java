package net.sf.josceleton.motion.impl.gesture;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import net.sf.josceleton.core.api.entity.XyzDirection;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Joints;
import net.sf.josceleton.motion.api.gesture.GestureListener;
import net.sf.josceleton.motion.api.gesture.HitWallGesture;
import net.sf.josceleton.motion.api.gesture.HitWallGestureBuilder;

/**
 * @since 0.4
 */
class HitWallGestureBuilderImpl extends GestureBuilderImpl<GestureListener, HitWallGesture>
	implements HitWallGestureBuilder {
	
	private static final Collection<Joint> DEFAULT_ATTACHED_JOINTS;
	static {
		final Collection<Joint> tmp = new HashSet<Joint>();
		tmp.add(Joints.HAND().RIGHT());
		DEFAULT_ATTACHED_JOINTS = Collections.unmodifiableCollection(tmp);
	}
	
	private static final XyzDirection DEFAULT_DIRECTION = XyzDirection.Y;
	
	private static final float DEFAULT_COORDINATE = 0.6F;
	
	private static final boolean DEFAULT_LOWER = true;

	
	private XyzDirection pDirection = DEFAULT_DIRECTION;
	
	private float pCoordinateValue = DEFAULT_COORDINATE;
	
	private boolean pTriggerLower = DEFAULT_LOWER;
	
	
	@Override public final HitWallGesture build() {
		final Collection<Joint> attachedJoints;
		final Collection<Joint> configuredAttachedJoints = this.getPAttachedJoints();
		if(configuredAttachedJoints != null) {
			attachedJoints = configuredAttachedJoints;
		} else {
			attachedJoints = DEFAULT_ATTACHED_JOINTS;
		}
		
		return new HitWallGestureImpl(
			new HitWallGestureConfigurationImpl(
				this.pCoordinateValue, this.pDirection, this.pTriggerLower, attachedJoints));
	}

	// MINOR check for null args in builder?
	
	@Override public final HitWallGestureBuilder direction(final XyzDirection direction) {
		this.pDirection = direction;
		return this;
	}

	@Override public final HitWallGestureBuilder coordinateValue(final float coordinateValue) {
		this.pCoordinateValue = coordinateValue;
		return this;
	}

	@Override public final HitWallGestureBuilder triggerLower(final boolean triggerLower) {
		this.pTriggerLower = triggerLower;
		return this;
	}

}
