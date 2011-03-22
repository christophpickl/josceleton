package net.sf.josceleton.playground.motion.app2.game1;

import java.awt.Graphics2D;
import java.awt.Image;

import net.sf.josceleton.playground.motion.app2.framework.motionx.RelativeHitWallGesture;
import net.sf.josceleton.playground.motion.app2.framework.view.AbstractPageView;
import net.sf.josceleton.playground.motion.app2.framework.view.common.Resources;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;
import net.sf.josceleton.prototype.console.util.RandomUtil;

class GamePageView extends AbstractPageView {
		
	private static final Image IMG = Resources.DUMB_FACE;
		private final String pageIdBack;
		
		private boolean isEnabled = false;
		
		float xRand = -1.0F;
		float yRand = -1.0F;
		private int screenPositionX;
		private int screenPositionY;
		
		public GamePageView(final String pageIdBack) {
			this.pageIdBack = pageIdBack;
//			this.hitDumbFace.addListener(this);
		}
		
		
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
			} else {
				System.out.println("GamePageView: not drawing anything, as it is disabled.");
			}
//			this.hitDumbFace.drawOnPosition(g, this.xRand, this.yRand, world);
		}

		public void updateNewDumbFace() {
			System.out.println("GamePageView updateNewDumbFace()");
			this.isEnabled = true;
			this.xRand = -1;
		}

		public void stop() {
			this.isEnabled = false;
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