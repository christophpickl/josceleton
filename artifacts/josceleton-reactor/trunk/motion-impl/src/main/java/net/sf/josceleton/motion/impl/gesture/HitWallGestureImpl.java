package net.sf.josceleton.motion.impl.gesture;

import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.XyzDirection;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.motion.api.gesture.HitWallConfig;
import net.sf.josceleton.motion.api.gesture.HitWallGesture;
import net.sf.josceleton.motion.api.gesture.HitWallListener;

/**
 * @since 0.4
 */
class HitWallGestureImpl extends AbstractGesture<HitWallConfig, HitWallListener> implements HitWallGesture {
	
	private final HitWallConfig configuration;
	private final XyzDirection configDirecton;
	private final boolean triggerLower;
	private final float coordinateValue;
	
	private boolean wasYetTriggered = false;
	
	HitWallGestureImpl(final HitWallConfig configuration) {
		super(configuration);
		
		this.configuration = configuration;
		this.configDirecton = this.configuration.getDirection();
		this.triggerLower = this.configuration.getTriggerLower();
		this.coordinateValue = this.configuration.getCoordinateValue();
		// there is only one joint for hitwall gesture
//		final Joint joint = configuration.getJointsInterestedIn().iterator().next(); ... NO: already filtered by super
		
	}
	
	/** {@inheritDoc} from {@link AbstractGesture} */
	@Override protected final void onMovedInteresting(
			final Joint movedJoint, final Coordinate updatedCoordinate, final Skeleton skeleton) {
		final float actualCoordinate = this.configDirecton.extractValue(updatedCoordinate);
		
		if(this.wasYetTriggered == false) {
			final boolean shouldTrigger = this.checkTrigger(actualCoordinate);
			
			if(shouldTrigger == true) {
				this.dispatchGestureDetected(/* FIXME add argument ? oder "this"? wohl am besten...*/skeleton);
				this.wasYetTriggered = true;
			}
			
		} else {
			
			final boolean shouldUntrigger = this.checkUntrigger(actualCoordinate);
			
			if(shouldUntrigger == true) {
				this.wasYetTriggered = false;
			}
		}
	}

	// TODO DRY trigger und untrigger code refactoren ... oder vielleicht auch nicht (auch besser so fuer performance)
	// FIXME untrigger check toleranz bereich dazu
	
	private boolean checkTrigger(final float actualCoordinate) {
		if(this.triggerLower == true) {
			return actualCoordinate < this.coordinateValue;
		}
		return actualCoordinate > this.coordinateValue;
	}
	
	private boolean checkUntrigger(final float actualCoordinate) {
		if(this.triggerLower == true) {
			return actualCoordinate > this.coordinateValue;
		}
		return actualCoordinate < this.coordinateValue;
	}

}
