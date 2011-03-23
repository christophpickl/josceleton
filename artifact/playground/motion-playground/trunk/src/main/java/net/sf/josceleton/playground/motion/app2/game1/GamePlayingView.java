package net.sf.josceleton.playground.motion.app2.game1;

import java.awt.Graphics2D;
import java.awt.Image;

import net.sf.josceleton.playground.motion.app2.framework.motionx.RelativeHitWallGesture;
import net.sf.josceleton.playground.motion.app2.framework.view.AbstractPageView;
import net.sf.josceleton.playground.motion.app2.framework.view.resources.Images;
import net.sf.josceleton.playground.motion.app2.framework.view.resources.Style;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;
import net.sf.josceleton.prototype.console.util.RandomUtil;

class GamePlayingView extends AbstractPageView {
		
	private static final Image IMG = Images.DUMB_FACE;
	
	private boolean isEnabled = false;
	
	float xRand = -1.0F;
	float yRand = -1.0F;
	private int screenPositionX;
	private int screenPositionY;
	private int currentSecond;

	private int countHit;
	
	@Override
	public void drawWithMaxSize(WorldSnapshot world, Graphics2D g, int width, int height) {
		// TODO was ist der unterschied zwischen zb width und world.getWidth??
		if(this.isEnabled == true) {
			if(this.xRand == -1) {
				this.xRand = RandomUtil.nextFloat() / 2 + 0.25F;
				this.yRand = RandomUtil.nextFloat() / 2 + 0.25F; 
				
				this.screenPositionX = Math.round(this.xRand * world.getWidth());
				this.screenPositionY = Math.round(this.yRand * world.getHeight());
			}
			
			g.drawImage(IMG, this.screenPositionX, this.screenPositionY, null);
			
			final int gapLeft = 10;
			Style.Text.TIME_INDICATOR.on(g);
			int yCurrent = 40;
			g.drawString(formatSeconds(), gapLeft, yCurrent);
			yCurrent += 50;
			g.drawString("Hits: " + this.countHit, gapLeft, yCurrent);
			
		} else {
			System.out.println("GamePageView: not drawing anything, as it is disabled.");
		}
//		this.hitDumbFace.drawOnPosition(g, this.xRand, this.yRand, world);
	}
	
	private String formatSeconds() {
		final int min = this.currentSecond / 60;
		final int sec = this.currentSecond % 60;
		return "Time Left: " + min + ":" + (sec < 10 ? "0" : "") + sec;
	}

	public void updateNewDumbFace() {
		System.out.println("GamePageView updateNewDumbFace()");
		this.isEnabled = true;
		this.xRand = -1;
	}

	public void setCurrentSecond(int currentSecond) {
		this.currentSecond = currentSecond;
	}

	public void stop() {
		this.isEnabled = false;
	}

	public void setCountHit(int countHit) {
		this.countHit = countHit;
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
