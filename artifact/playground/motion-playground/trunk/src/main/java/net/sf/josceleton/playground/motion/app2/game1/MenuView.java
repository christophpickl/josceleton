package net.sf.josceleton.playground.motion.app2.game1;

import java.awt.Graphics2D;
import java.awt.Image;

import net.sf.josceleton.playground.motion.app2.framework.page.system.SystemQuitPage;
import net.sf.josceleton.playground.motion.app2.framework.view.AbstractPageView;
import net.sf.josceleton.playground.motion.app2.framework.view.component.Button;
import net.sf.josceleton.playground.motion.app2.framework.view.component.ButtonListener;
import net.sf.josceleton.playground.motion.app2.framework.view.component.ButtonString;
import net.sf.josceleton.playground.motion.app2.framework.view.resources.Images;
import net.sf.josceleton.playground.motion.app2.framework.view.resources.Style;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;

class MenuView extends AbstractPageView {
	
	private static final Image BOXER = Images.BOXER;
	
	private final Button btnStartGame = new ButtonString("Start Game");
	private final Button btnQuit = new ButtonString("Quit");
	private String boxerMessage = "";
	
	@SuppressWarnings("synthetic-access")
	public MenuView() {
		this.btnStartGame.addListener(new ButtonListener() { @Override public void onClicked(Button source) {
				dispatch(GamePlayingPage.ID);
		}});
		this.btnQuit.addListener(new ButtonListener() { @Override public void onClicked(Button source) {
			dispatch(SystemQuitPage.ID);
		}});
	}
	
	@Override
	public void drawWithMaxSize(WorldSnapshot world, Graphics2D g, int width, int height) {
		int boxerX = world.getHorizontalCenter() - BOXER.getWidth(null) / 2 + 20;
		int boxerY = world.getVerticalCenter() - 240;
		g.drawImage(BOXER, boxerX, boxerY, null);
		
		Style.Text.BOXER_BUBBLE.on(g);
		g.drawString(this.boxerMessage, boxerX + 100, boxerY + 30);
		
		final int btnStartGameYPos = world.getVerticalCenter() + 50;
		this.btnStartGame.drawOnPosition(g, world.getHorizontalCenter() - ButtonString.DEFAULT_HALF_WIDTH,
				btnStartGameYPos, world);
		this.btnQuit.drawOnPosition(g, world.getHorizontalCenter() - ButtonString.DEFAULT_HALF_WIDTH,
				btnStartGameYPos + this.btnStartGame.calculateHeight(g) + /*gap*/60, world);
	}
	
	public void setBoxerMessage(String boxerMessage) {
		this.boxerMessage = boxerMessage;
	}
}
