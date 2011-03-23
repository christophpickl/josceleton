package net.sf.josceleton.playground.motion.app2.game1;

import java.awt.Point;
import java.awt.Toolkit;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.josceleton.connection.api.service.motion.MotionStreamListener;
import net.sf.josceleton.core.api.entity.location.Coordinate;
import net.sf.josceleton.playground.motion.app2.framework.motionx.GestureListener;
import net.sf.josceleton.playground.motion.app2.framework.motionx.RelativeHitWallGesture;
import net.sf.josceleton.playground.motion.app2.framework.motionx.RelativeHitWallResult;
import net.sf.josceleton.playground.motion.app2.framework.page.Page;
import net.sf.josceleton.playground.motion.app2.framework.sound.Sound;

public class GamePage extends Page<GamePageView> implements GestureListener<RelativeHitWallResult> {
	
	private static final Log LOG = LogFactory.getLog(GamePage.class);
	
	private static final int COUNT_HIT_MAX = 3;
	
	private final Collection<MotionStreamListener> streamListeners;
//	private final MotionStreamDispatcher dispatcher;
	private final GamePageView view;

	private int countHit = 0;
	private final RelativeHitWallGesture gesture = new RelativeHitWallGesture();
	private final String pageIdMain;
	public GamePage(String id, String pageIdMain) {
		super(id, new GamePageView(pageIdMain));
		this.view = this.getView();
		this.pageIdMain = pageIdMain;
		
		this.gesture.addListener(this);
		this.streamListeners = Arrays.asList((MotionStreamListener) this.gesture);
//		this.dispatcher = new MotionStreamDispatcher();
	}

	@Override public Collection<MotionStreamListener> getMotionStreamListeners() {
		return this.streamListeners;
	}

	@Override public void start() {
		LOG.info("start()");
		this.countHit = 0;
		this.view.updateNewDumbFace();
	}

	@Override public void stop() {
		LOG.info("stop()");
	}
	private final static float HIT_DEVIATION = 0.12F;
	@Override
	public void onGestureDetected(RelativeHitWallResult result) {
		final Coordinate coord = result.getCoordinate();
		// TODO shortcut um zusehen ob etwas in einem bereich ist:
//			touch = (foreground > touchDepthMin) & (foreground < touchDepthMax);
//			// https://github.com/robbeofficial/KinectTouch/blob/master/src/KinectTouch.cpp
		
		LOG.debug("onGestureDetected() coord: " + coord);
//		System.out.println("GamePage onGestureDetected:");
//		System.out.println("	CORD " + coord.x() + "/" + coord.y());
//		System.out.println("	VIEW "+this.view.xRand+"/" + this.view.yRand);
		
		if(coord.x() < this.view.xRand + HIT_DEVIATION && coord.x() > this.view.xRand - HIT_DEVIATION && // TODO use rectangle + provided hit test!
		   coord.y() < this.view.yRand + HIT_DEVIATION && coord.y() > this.view.yRand - HIT_DEVIATION) {
			
			Sound.PUNCH_HIT.start();
			
			this.countHit++;
			if(this.countHit == COUNT_HIT_MAX) {
				this.dispatchNavigateTo(this.pageIdMain);
			} else {
				this.view.updateNewDumbFace();
			}
		} else {
			Sound.PUNCH_MISS.start();
		}
	}
	
}
