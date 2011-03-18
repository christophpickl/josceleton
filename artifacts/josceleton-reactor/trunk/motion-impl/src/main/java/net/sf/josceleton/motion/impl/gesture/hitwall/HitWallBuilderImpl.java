package net.sf.josceleton.motion.impl.gesture.hitwall;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import net.sf.josceleton.core.api.entity.XyzDirection;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Joints;
import net.sf.josceleton.motion.api.gesture.hitwall.HitWallBuilder;
import net.sf.josceleton.motion.api.gesture.hitwall.HitWallConfig;
import net.sf.josceleton.motion.api.gesture.hitwall.HitWallGesture;
import net.sf.josceleton.motion.api.gesture.hitwall.HitWallListener;
import net.sf.josceleton.motion.impl.gesture.AbstractJointableGestureBuilder;

import com.google.inject.Inject;

/**
 * @since 0.4
 */
class HitWallBuilderImpl
	extends AbstractJointableGestureBuilder<
		HitWallBuilder,
		HitWallGesture,
		HitWallConfig,
		HitWallListener>
	implements HitWallBuilder {
	
	private static final Collection<Joint> DEFAULT_RELEVANT_JOINTS;
	static {
		final Collection<Joint> tmp = new HashSet<Joint>();
		tmp.add(Joints.HAND().RIGHT());
		DEFAULT_RELEVANT_JOINTS = Collections.unmodifiableCollection(tmp);
	}
	
	private static final XyzDirection DEFAULT_DIRECTION = XyzDirection.Y;
	
	private static final float DEFAULT_COORDINATE = 0.6F;
	
	private static final boolean DEFAULT_LOWER = true;

	
	private XyzDirection pDirection = DEFAULT_DIRECTION;
	
	private float pCoordinate = DEFAULT_COORDINATE;
	
	private boolean pTriggerOnLower = DEFAULT_LOWER;
	
	private final HitWallGestureFactory gestureFactory;
	
	private final HitWallConfigFactory configFactory;
	
	
	@Inject HitWallBuilderImpl(final HitWallGestureFactory gestureFactory, final HitWallConfigFactory configFactory) {
		this.gestureFactory = gestureFactory;
		this.configFactory = configFactory;
	}

	@Override public final HitWallGesture build() {
		final Collection<Joint> relevantJoints;
		final Collection<Joint> configuredAttachedJoints = this.getPAttachedJoints();
		if(configuredAttachedJoints != null) {
			relevantJoints = configuredAttachedJoints;
		} else {
			relevantJoints = DEFAULT_RELEVANT_JOINTS;
		}
		final HitWallConfig config = this.configFactory.create(
				relevantJoints, this.pCoordinate, this.pDirection, this.pTriggerOnLower);
		return this.gestureFactory.create(config);
	}

	// MINOR check for null args in builder?
	
	@Override public final HitWallBuilder direction(final XyzDirection direction) {
		// TODO if(direction == null)  1. throw exception? 2. set to default value?
		this.pDirection = direction;
		return this;
	}

	@Override public final HitWallBuilder coordinate(final float coordinateValue) {
		// TODO validate correct argument: [0.0 .. 7.0] (as it could be either for X/Y or Z, we dont know yet)
		// OUTSOURCE this argument check! maybe create own type CoordinateValue
		this.pCoordinate = coordinateValue;
		return this;
	}

	@Override public final HitWallBuilder triggerOnLower(final boolean triggerOnLower) {
		this.pTriggerOnLower = triggerOnLower;
		return this;
	}

}
