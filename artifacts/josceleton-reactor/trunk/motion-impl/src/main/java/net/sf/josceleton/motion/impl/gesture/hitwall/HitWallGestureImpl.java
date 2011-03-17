package net.sf.josceleton.motion.impl.gesture.hitwall;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.XyzDirection;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.motion.api.gesture.hitwall.HitWallConfig;
import net.sf.josceleton.motion.api.gesture.hitwall.HitWallGesture;
import net.sf.josceleton.motion.api.gesture.hitwall.HitWallListener;
import net.sf.josceleton.motion.impl.gesture.AbstractJointableGesture;

/**
 * @since 0.4
 */
class HitWallGestureImpl extends AbstractJointableGesture<HitWallConfig, HitWallListener> implements HitWallGesture {
	
	private final HitWallConfig configuration;
	
	private final XyzDirection configDirecton;
	
	private final boolean triggerLower;
	
	private final float coordinateValue;
	
	
	private boolean wasYetTriggered = false;
	
	
	@Inject HitWallGestureImpl(@Assisted final HitWallConfig configuration) {
		super(configuration);
		this.configuration = configuration;
		this.configDirecton = this.configuration.getDirection();
		this.triggerLower = this.configuration.getTriggerOnLower();
		this.coordinateValue = this.configuration.getCoordinate();
	}
	
	/** {@inheritDoc} from {@link AbstractJointableGesture} */
	@Override protected final void onMovedRelevantJoint(
			final Joint relevantJoint, final Coordinate updatedCoordinate, final Skeleton skeleton) {
		
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
