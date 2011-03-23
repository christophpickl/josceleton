package net.sf.josceleton.playground.motion.app2.game1;

import java.util.Arrays;
import java.util.Collection;
import java.util.Timer;

import net.sf.josceleton.connection.api.service.motion.MotionStreamListener;
import net.sf.josceleton.core.api.entity.location.Coordinate;
import net.sf.josceleton.playground.motion.app2.framework.motionx.GestureListener;
import net.sf.josceleton.playground.motion.app2.framework.motionx.RelativeHitWallGesture;
import net.sf.josceleton.playground.motion.app2.framework.motionx.RelativeHitWallResult;
import net.sf.josceleton.playground.motion.app2.framework.page.Page;
import net.sf.josceleton.playground.motion.app2.framework.view.resources.Sounds;
import net.sf.josceleton.playground.motion.common.TimerTaskRunner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GamePlayingPage extends Page<GamePlayingView> implements GestureListener<RelativeHitWallResult>, Runnable {
	
	private static final Log LOG = LogFactory.getLog(GamePlayingPage.class);
	public static final String ID = GamePlayingPage.class.getName();
	private static final float HIT_DEVIATION = 0.12F;
	private static final int SECONDS_TO_PLAY = 2;
	
	private final Collection<MotionStreamListener> streamListeners;
	private final GamePlayingView view;

	private int currentSecond = 0;
	private int countHit = 0;
	private int countMiss = 0;
	
	private final RelativeHitWallGesture gesture = new RelativeHitWallGesture();
	private Timer timer;
	
	public GamePlayingPage() {
		super(GamePlayingPage.ID, new GamePlayingView());
		this.view = this.getView();
		
		this.streamListeners = Arrays.asList((MotionStreamListener) this.gesture);
	}

	@Override public Collection<MotionStreamListener> getMotionStreamListeners() {
		return this.streamListeners;
	}

	@Override public void start() {
		LOG.info("start()");
		this.gesture.addListener(this);
		
		this.currentSecond = 0;
		this.countHit = 0;
		this.countMiss = 0;
		
		this.view.updateNewDumbFace();
		this.timer = new Timer();
		this.timer.scheduleAtFixedRate(new TimerTaskRunner(this), 0, 1000);
	}

	@Override public void stop() {
		LOG.info("stop()");
		this.timer.cancel();
		this.timer = null;
		this.gesture.removeListener(this);
	}
	
	@Override
	public void onGestureDetected(RelativeHitWallResult result) {
		final Coordinate coord = result.getCoordinate();
		// TODO shortcut um zusehen ob etwas in einem bereich ist:
//			touch = (foreground > touchDepthMin) & (foreground < touchDepthMax);
//			// https://github.com/robbeofficial/KinectTouch/blob/master/src/KinectTouch.cpp
		
		LOG.debug("onGestureDetected() coord: " + coord);
		
		if(coord.x() < this.view.xRand + HIT_DEVIATION && coord.x() > this.view.xRand - HIT_DEVIATION && // TODO use rectangle + provided hit test!
		   coord.y() < this.view.yRand + HIT_DEVIATION && coord.y() > this.view.yRand - HIT_DEVIATION) {
			
			Sounds.PUNCH_HIT.start();
			
			this.countHit++;
			this.view.updateNewDumbFace();
			this.view.setCountHit(this.countHit);
			
		} else {
			this.countMiss++;
			Sounds.PUNCH_MISS.start();
		}
	}

	@Override
	public void run() {
		LOG.info("run() ... currentSecond=" + this.currentSecond);
		this.view.setCurrentSecond(SECONDS_TO_PLAY - this.currentSecond); // transform to countdown
		
		if(this.currentSecond == SECONDS_TO_PLAY) {
			System.out.println("========================== GAME ENDED");
			System.out.println("HITS: " + this.countHit);
			System.out.println("MISSES: " + this.countMiss);
			this.dispatchNavigateTo(GameOverPage.ID); // will invoke stop()
			
		} else {
			this.currentSecond++;
		}
	}
	
}
