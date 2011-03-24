package net.sf.josceleton.playground.motion.app2.game1;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;
import java.util.Vector;

import net.sf.josceleton.playground.motion.app2.framework.view.AbstractPageView;
import net.sf.josceleton.playground.motion.app2.framework.view.resources.Images;
import net.sf.josceleton.playground.motion.app2.framework.view.resources.Style;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

class GamePlayingView extends AbstractPageView {
	private static final Log LOG = LogFactory.getLog(GamePlayingView.class);
	private static final Image IMG = Images.DUMB_FACE;
	
	
//	float xRand = -1.0F;
//	float yRand = -1.0F;
	private Point currentFaceLocation;
//	private int screenPositionX;
//	private int screenPositionY;
	private int currentSecond;
	private Rectangle debugFaceHitArea;
	private int countHit;
	private List<Punch> punches = new Vector<Punch>();
	@Override
	public void drawWithMaxSize(WorldSnapshot world, Graphics2D g, int width, int height) {
		// TODO was ist der unterschied zwischen zb width und world.getWidth??
		
		if(this.currentFaceLocation != null) {
			g.drawImage(IMG, this.currentFaceLocation.x, this.currentFaceLocation.y, null);
		}
		
		final int gapLeft = 10;
		Style.Text.TIME_INDICATOR.on(g);
		int yCurrent = 40;
		g.drawString(formatSeconds(), gapLeft, yCurrent);
		yCurrent += 50;
		g.drawString("Hits: " + this.countHit, gapLeft, yCurrent);
		
		// draw punch indicators
		for (Punch punch : this.punches) {
//			g.fill(new Ellipse2D.Double(punch.getLocation().x, punch.getLocation().y, PUNCH_INDICATOR_DIAMETER, PUNCH_INDICATOR_DIAMETER));
			g.drawImage(punch.getImage(), punch.getLocation().x, punch.getLocation().y, null);
			
		}
		
		if(this.debugFaceHitArea != null) {
			Composite previousComp = g.getComposite();
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5F));
			g.setPaint(Color.BLUE);
			g.fill(this.debugFaceHitArea);
			g.setComposite(previousComp);
		}
	}
	
	private String formatSeconds() {
		final int min = this.currentSecond / 60;
		final int sec = this.currentSecond % 60;
		return "Time Left: " + min + ":" + (sec < 10 ? "0" : "") + sec;
	}

	public void setCurrentFaceLocation(Point currentFaceLocation) {
		this.currentFaceLocation = currentFaceLocation;
	}

	public void setCurrentSecond(int currentSecond) {
		this.currentSecond = currentSecond;
	}

	public void setCountHit(int countHit) {
		this.countHit = countHit;
	}
	
	public synchronized void addPunch(Punch punch) {
		LOG.trace("addPunch("+punch+")");
		this.punches.add(punch);
	}
	
	public synchronized void removePunch(Punch punch) {
		LOG.trace("removePunch("+punch+")");
		this.punches.remove(punch);
	}

	public void DEBUG_setDebugFaceHitArea(Rectangle pDebugFaceHitArea) {
		this.debugFaceHitArea = pDebugFaceHitArea;
	}

//		@Override
//		public void onClicked(Button source) {
//			this.countHit++;
//			
//			if(this.countHit == COUNT_HIT_MAX) {
//				this.resetState();
//				this.dispatch(this.pageIdBack);
//			} else {
//				this.xRand = -1;
//				this.yRand = -1;
//			}
//		}

}
