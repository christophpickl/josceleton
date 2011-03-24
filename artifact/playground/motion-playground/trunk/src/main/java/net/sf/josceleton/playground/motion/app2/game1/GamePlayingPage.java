package net.sf.josceleton.playground.motion.app2.game1;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

import net.sf.josceleton.connection.api.service.motion.MotionStreamListener;
import net.sf.josceleton.core.api.entity.joint.Joints;
import net.sf.josceleton.core.api.entity.location.Coordinate;
import net.sf.josceleton.playground.motion.app2.framework.motionx.GestureListener;
import net.sf.josceleton.playground.motion.app2.framework.motionx.RelativeHitWallGesture;
import net.sf.josceleton.playground.motion.app2.framework.motionx.RelativeHitWallResult;
import net.sf.josceleton.playground.motion.app2.framework.page.Page;
import net.sf.josceleton.playground.motion.app2.framework.page.PageArgs;
import net.sf.josceleton.playground.motion.app2.framework.view.resources.Images;
import net.sf.josceleton.playground.motion.app2.framework.view.resources.Sounds;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;
import net.sf.josceleton.playground.motion.common.TimerTaskRunner;
import net.sf.josceleton.prototype.console.util.RandomUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GamePlayingPage extends Page<GamePlayingView> implements GestureListener<RelativeHitWallResult>, Runnable {
	
	private static final Log LOG = LogFactory.getLog(GamePlayingPage.class);
	public static final String ID = GamePlayingPage.class.getName();
	private static final int HITAREA_HALF_DEVIATION = 80;
	private static final Dimension HITAREA_SIZE = new Dimension(HITAREA_HALF_DEVIATION * 2, HITAREA_HALF_DEVIATION * 2 + 50);
	private static final int SECONDS_TO_PLAY = 20;
	private static final int PUNCH_INDICATOR_DISPLAY_TIME = 1000;
	
	private final Collection<MotionStreamListener> streamListeners;
	final GamePlayingView view;

	private int currentSecond = 0;
	private int countHit = 0;
	private int countMiss = 0;
	
	private final RelativeHitWallGesture gestureLeft = new RelativeHitWallGesture(Joints.HAND().LEFT());
	private final RelativeHitWallGesture gestureRight = new RelativeHitWallGesture(Joints.HAND().RIGHT());
	private Timer timer;
	private WorldSnapshot world;
	
	private Rectangle currentFaceHitArea;
	
	public GamePlayingPage() {
		super(GamePlayingPage.ID, new GamePlayingView());
		this.view = this.getView();
		
		this.streamListeners = Arrays.asList((MotionStreamListener) this.gestureLeft, this.gestureRight);
	}

	@Override public Collection<MotionStreamListener> getMotionStreamListeners() {
		return this.streamListeners;
	}

	@Override protected void _start(WorldSnapshot pWorld, PageArgs args) {
		LOG.info("start()");
		this.world = pWorld;
		this.world.setCursorVisible(false);
		this.gestureLeft.addListener(this);
		this.gestureRight.addListener(this);
		
		this.currentSecond = 0;
		this.countHit = 0;
		this.countMiss = 0;
		
		this.updateViewForNextFace();
		this.timer = new Timer();
		this.timer.scheduleAtFixedRate(new TimerTaskRunner(this), 0, 1000);
	}
	
	@Override public void stop() {
		LOG.info("stop()");
		this.world.setCursorVisible(true);
		this.timer.cancel();
		this.timer = null;
		this.gestureLeft.removeListener(this);
		this.gestureRight.removeListener(this);
	}
	
	@Override
	public void onGestureDetected(RelativeHitWallResult result) {
		final Coordinate coord = result.getCoordinate();
		// TODO shortcut um zusehen ob etwas in einem bereich ist:
//			touch = (foreground > touchDepthMin) & (foreground < touchDepthMax);
//			// https://github.com/robbeofficial/KinectTouch/blob/master/src/KinectTouch.cpp
		
		LOG.debug("onGestureDetected() coord: " + coord);
		
		final Point punchPoint = this.world.transformCoordinate(coord);
		final Image image = result.getSource() == this.gestureLeft ? Images.PUNCH_RIGHT : Images.PUNCH_LEFT; // osceleton is mirror on by default
		final Punch punch = new Punch(punchPoint, result.getMovedJoint(), image);
		
		Timer t = new Timer();
		t.schedule(new TimerTask() { @Override public void run() {
				GamePlayingPage.this.view.removePunch(punch);
		} }, PUNCH_INDICATOR_DISPLAY_TIME);
		
		this.view.addPunch(punch);
		
		if(this.currentFaceHitArea.contains(punchPoint)) {
			this.countHit++;
			this.view.setCountHit(this.countHit);
			Sounds.PUNCH_HIT.start();
			
			this.updateViewForNextFace();
			
		} else {
			this.countMiss++;
			Sounds.PUNCH_MISS.start();
		}
	}
	
	private void updateViewForNextFace() {
		float rx = RandomUtil.nextFloat() / 2 + 0.20F /* 0.25 - 0.75 */;
		float ry = RandomUtil.nextFloat() / 2 + 0.15F /* 0.30 - 0.70 */;
		final Point punchPoint = this.world.transformCoordinate(rx, ry);
		
		this.currentFaceHitArea = new Rectangle(new Point(punchPoint.x - HITAREA_HALF_DEVIATION,
														  punchPoint.y - HITAREA_HALF_DEVIATION),
												HITAREA_SIZE);
		
//		this.view.DEBUG_setDebugFaceHitArea(this.currentFaceHitArea);
		this.view.setCurrentFaceLocation(this.currentFaceHitArea.getLocation());
	}

	@Override
	public void run() {
		LOG.info("run() ... currentSecond=" + this.currentSecond);
		this.view.setCurrentSecond(SECONDS_TO_PLAY - this.currentSecond); // transform to countdown
		
		if(this.currentSecond == SECONDS_TO_PLAY) {
			// will invoke stop()
			this.dispatchNavigateTo(GameOverPage.ID,
				PageArgs.set().
					keyVal(GameOverPage.ARG_HITS, Integer.valueOf(this.countHit)).
					keyVal(GameOverPage.ARG_MISSES, Integer.valueOf(this.countMiss)).
					keyVal(GameOverPage.ARG_TIME_PLAYED, Integer.valueOf(SECONDS_TO_PLAY)).
					build());
			
		} else {
			this.currentSecond++;
		}
	}
	
}
