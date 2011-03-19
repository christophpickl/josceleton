
import net.pulseproject.mkinector.josceleton.api.entity.BodyPart;
import net.pulseproject.mkinector.josceleton.api.entity.Coordinate;
import net.pulseproject.mkinector.josceleton.api.entity.Direction;
import net.pulseproject.mkinector.josceleton.api.gesture.GestureListener;
import net.pulseproject.mkinector.josceleton.api.message.JointMessage;
import net.pulseproject.mkinector.josceleton.api.message.UserMessage;
import net.pulseproject.mkinector.josceleton.api.user.User;
import net.pulseproject.mkinector.josceleton.gesture.api.HitWallGesture;
import net.pulseproject.mkinector.josceleton.gesture.api.HitWallGestureResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import something.different.DefaultDispatchable;

public class HitWallGestureImpl
	extends DefaultDispatchable<GestureListener<HitWallGestureResult>>
	implements HitWallGesture {

	private static final Log LOG = LogFactory.getLog(HitWallGestureImpl.class);
	
	private static final double DEVIATION_TOLERANCE = 0.02;
	
	private final Direction direction;
	
	private final double triggerValue;
	
	private final boolean checkTriggerForHigher;
	
	private final double triggerValueDeviation;

	private boolean wasTriggered = false;

	private final BodyPart bodyPart;
	
	
	public HitWallGestureImpl(// TODO create GestureArguments object
			final BodyPart bodyPart,
			final Direction direction,
			final double triggerValue,
			final boolean checkTriggerForHigher) {
		this.bodyPart = bodyPart;
		this.direction = direction;
		this.triggerValue = triggerValue;
		this.checkTriggerForHigher = checkTriggerForHigher;
		
		this.triggerValueDeviation = this.triggerValue +
			(this.checkTriggerForHigher ? -DEVIATION_TOLERANCE : DEVIATION_TOLERANCE);
		
		LOG.debug("new HitWallGesture(filterer, jointType=" + bodyPart + ", direction=" + direction + ", " +
				"triggerValue=[" + triggerValue + "], checkTriggerForHigher=" + checkTriggerForHigher + ") ... " +
				"triggerValueDeviation=[" + this.triggerValueDeviation + "]");
	}

	@Override
	public void onUserMessage(final UserMessage message) {
		// ignore
	}

	@Override public final void onJointMessage(final JointMessage message) {
		final BodyPart messageBodyPart = message.getBodyPart();
		
		if(this.bodyPart.equals(messageBodyPart) == false) {
			return;
		}
		
		final double value = this.direction.extractValue(message.getCoordinate());
		if(this.checkTriggerForHigher == true) {
			
			if(value > this.triggerValue && this.wasTriggered == false) {
				this.doTrigger(message);
				
			} else if(value < this.triggerValueDeviation && this.wasTriggered != false) {
				LOG.trace("setting wasTriggered = false");
				this.wasTriggered = false;
			}
			
		} else { // this.checkTriggerForHigher == false
			
			if(value < this.triggerValue && this.wasTriggered == false) {
				this.doTrigger(message);
				
			} else if(value > this.triggerValueDeviation && this.wasTriggered != false) {
				LOG.trace("setting wasTriggered = false");
				this.wasTriggered = false;
			}
		}
	}
	
	private void doTrigger(final JointMessage message) {
		if(this.wasTriggered == true) {
			throw new AssertionError("this.wasTriggered == true");
		}
		
		LOG.trace("doTrigger()");
		this.wasTriggered = true;

		final User user = message.getUser();
		final Coordinate coordinate = message.getCoordinate();
		final HitWallGestureResult result = new HitWallGestureResultImpl(user, coordinate);
		
		for(final GestureListener<HitWallGestureResult> listener : this.getListeners()) {
			listener.onGestureDetected(result);
		}
	}
}
