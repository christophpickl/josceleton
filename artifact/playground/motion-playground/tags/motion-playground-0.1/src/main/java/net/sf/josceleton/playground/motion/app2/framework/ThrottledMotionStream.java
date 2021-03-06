package net.sf.josceleton.playground.motion.app2.framework;

import java.io.Closeable;
import java.util.Collection;
import java.util.Timer;
import java.util.Vector;

import net.sf.josceleton.connection.api.service.motion.ContinuousMotionStream;
import net.sf.josceleton.connection.api.service.motion.MotionStreamListener;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.core.api.entity.location.Coordinate;
import net.sf.josceleton.core.impl.async.DefaultAsync;
import net.sf.josceleton.playground.motion.app2.IThrottledMotionStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ThrottledMotionStream
	extends DefaultAsync<MotionStreamListener>
		implements ContinuousMotionStream, Closeable, MotionStreamListener, Runnable, IThrottledMotionStream {
	private static final Log LOG = LogFactory.getLog(ThrottledMotionStream.class);
	private static final int SYSOUT_EVERY = 150;
	private int sysoutCount = SYSOUT_EVERY - 1;
	
	private final ContinuousMotionStream motionStream;
	private final Timer timer = new Timer(ThrottledMotionStream.class.getSimpleName() + "-TimerThread");
	private final Collection<MotionStreamListener> removeRequests = new Vector<MotionStreamListener>();
	private Joint recentJoint;
	private Coordinate recentCoordinate;
	private Skeleton recentSkeleton;
	
	public ThrottledMotionStream(final ContinuousMotionStream motionStream /*TODO final int framesPerSecond*/) {
		this.motionStream = motionStream;
		this.motionStream.addListener(this);
//		this.timer.scheduleAtFixedRate(new TimerTaskRunner(this), 0, 1000 / framesPerSecond);
	}

	@Override public void close() { // MINOR check closeable state with AOP
		this.timer.cancel();
		this.motionStream.removeListener(this);
	}

	@Override public synchronized void onMoved(Joint movedJoint, Coordinate updatedCoordinate, Skeleton skeleton) {
//		this.recentJoint = movedJoint;
//		this.recentCoordinate = updatedCoordinate;
//		this.recentSkeleton = skeleton;
		
		for(MotionStreamListener listener : this.removeRequests) {
			LOG.debug("really removing listener: " + listener);
			this.removeListener(listener);
		}
		this.removeRequests.clear();
		for(MotionStreamListener listener : getListeners()) {
			listener.onMoved(movedJoint, updatedCoordinate, skeleton);
		}
	}
	
	@Override public void run() {
		if(this.recentSkeleton == null) {
			this.sysoutCount++;
			if(this.sysoutCount == SYSOUT_EVERY) {
				this.sysoutCount = 0;
				LOG.info("not dispatching timed event as no data was yet received!");
			}
			return;
		}
		for(MotionStreamListener listener : getListeners()) {
			listener.onMoved(this.recentJoint, this.recentCoordinate, this.recentSkeleton);
		}
	}
	
	@Override
	public void requestForRemove(MotionStreamListener listener) {
		LOG.debug("requestForRemove("+listener+")");
		boolean changed = this.removeRequests.add(listener);
		if(changed == false) {
			LOG.warn("request for remove was not added, as yet added! " + listener);
		}
	}
	
}
