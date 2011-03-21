package net.sf.josceleton.playground.motion.app2.framework.page.system;

import java.awt.Graphics2D;
import java.awt.Image;

import net.sf.josceleton.playground.motion.app2.framework.view.AbstractPageView;
import net.sf.josceleton.playground.motion.app2.framework.view.common.Styles;
import net.sf.josceleton.playground.motion.app2.framework.view.component.Button;
import net.sf.josceleton.playground.motion.app2.framework.view.component.ButtonImage;
import net.sf.josceleton.playground.motion.app2.framework.view.component.ButtonListener;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;

public class LoginView extends AbstractPageView {
	
	private final Button btnContinue;
	
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
		if(world.getSkeleton() == null) { // will only get dispatched once at early beginning
			Styles.setRegularFont(g);
			g.drawString("Waiting for incoming data ...", world.getHorizontalCenter() - 260, world.getVerticalCenter());
		} else {
			this.btnContinue.drawOnPosition(g, world.getHorizontalCenter() - this.continueImageHalfWidth, world.getVerticalCenter(), world);
		}
	}

}
