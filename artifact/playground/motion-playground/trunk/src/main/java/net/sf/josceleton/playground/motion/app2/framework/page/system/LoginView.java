package net.sf.josceleton.playground.motion.app2.framework.page.system;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;

import net.sf.josceleton.core.api.entity.location.Direction;
import net.sf.josceleton.playground.motion.app2.framework.view.AbstractPageView;
import net.sf.josceleton.playground.motion.app2.framework.view.common.Styles;
import net.sf.josceleton.playground.motion.app2.framework.view.component.Button;
import net.sf.josceleton.playground.motion.app2.framework.view.component.ButtonImage;
import net.sf.josceleton.playground.motion.app2.framework.view.component.ButtonListener;
import net.sf.josceleton.playground.motion.app2.framework.view.component.SkeletonDrawer;
import net.sf.josceleton.playground.motion.app2.framework.view.component.Text;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;

public class LoginView extends AbstractPageView {
	private static final int SKELETON_WIDTH = 200;
	private static final int SKELETON_HEIGHT = 200;
	private static final int SKELETON_GAP = 10;
	private final SkeletonDrawer skeletonDrawerXy = new SkeletonDrawer(new Dimension(SKELETON_WIDTH, SKELETON_HEIGHT), Direction.X, Direction.Y);
	private final SkeletonDrawer skeletonDrawerXz = new SkeletonDrawer(new Dimension(SKELETON_WIDTH, SKELETON_HEIGHT), Direction.X, Direction.Z);
	private final SkeletonDrawer skeletonDrawerYz = new SkeletonDrawer(new Dimension(SKELETON_WIDTH, SKELETON_HEIGHT), Direction.Y, Direction.Z);
	
	private final Button btnContinue;
	private final Text txtWaiting = new Text("Waiting for incoming data ...");
	private final int continueImageHalfWidth;
	
	public LoginView(final String idOfNextPage, Image continueImage) {
		this.btnContinue = new ButtonImage(continueImage);
		this.continueImageHalfWidth = continueImage.getWidth(null) / 2;
		
		this.btnContinue.addListener(new ButtonListener() {
			@SuppressWarnings("synthetic-access")
			@Override public void onClicked(Button source) {
				dispatch(idOfNextPage);
			}
		});
	}
	
	@Override public void drawWithMaxSize(WorldSnapshot world, Graphics2D g, int width, int height) {
		this.skeletonDrawerXy.drawOnPosition(g, 50,                                       10, world);
		this.skeletonDrawerXz.drawOnPosition(g, 50 +      SKELETON_WIDTH + SKELETON_GAP , 10, world);
		this.skeletonDrawerYz.drawOnPosition(g, 50 + 2 * (SKELETON_WIDTH + SKELETON_GAP), 10, world);
		
		if(world.getSkeleton() == null) { // will only get dispatched once at early beginning
			Styles.setRegularFont(g);
			this.txtWaiting.drawOnPosition(g, world.getHorizontalCenter() - 260, world.getVerticalCenter(), world);
			
		} else {
			this.btnContinue.drawOnPosition(g, world.getHorizontalCenter() - this.continueImageHalfWidth, world.getVerticalCenter(), world);
		}
	}

}
