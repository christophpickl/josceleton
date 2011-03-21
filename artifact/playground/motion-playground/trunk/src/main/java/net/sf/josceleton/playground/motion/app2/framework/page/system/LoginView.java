package net.sf.josceleton.playground.motion.app2.framework.page.system;

import java.awt.Graphics2D;
import java.awt.Image;

import net.sf.josceleton.core.impl.async.DefaultAsync;
import net.sf.josceleton.playground.motion.app2.framework.view.Button;
import net.sf.josceleton.playground.motion.app2.framework.view.ButtonListener;
import net.sf.josceleton.playground.motion.app2.framework.view.PageView;
import net.sf.josceleton.playground.motion.app2.framework.view.PageViewListener;
import net.sf.josceleton.playground.motion.app2.framework.view.StyleConstants;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;

public class LoginView extends DefaultAsync<PageViewListener> implements PageView {
	
	private final Button btnContinue;
	private final int btnContinueImageHalfWidth;
	
	public LoginView(final String idOfNextPage, Image continueImage) {
		this.btnContinue = new Button(continueImage);
		this.btnContinueImageHalfWidth = continueImage.getWidth(null) / 2;
		
		this.btnContinue.addListener(new ButtonListener() {
			@SuppressWarnings("synthetic-access")
			@Override public void onClicked(Button source) {
				for(PageViewListener listener : getListeners()) {
					listener.onNavigateTo(idOfNextPage);
				}
			}
		});
	}
	
	@Override public void drawWithMaxSize(WorldSnapshot world, Graphics2D g, int width, int height) {
		StyleConstants.setCommentFont(g);
		g.drawString("Welcome, this is the login start page.", 20, world.getHeight() - 20);
		
		if(world.getSkeleton() == null) { // will only get dispatched once at early beginning
			StyleConstants.setRegularFont(g);
			g.drawString("Waiting for incoming data ...", world.getHorizontalCenter() - 260, world.getVerticalCenter());
		} else {
			this.btnContinue.drawOnPosition(world, g, world.getHorizontalCenter() - this.btnContinueImageHalfWidth, world.getVerticalCenter());
		}
	}

}
