package net.sf.josceleton.playground.motion.app2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import net.sf.josceleton.playground.motion.app2.framework.page.Page;
import net.sf.josceleton.playground.motion.app2.framework.view.AbstractPageView;
import net.sf.josceleton.playground.motion.app2.framework.view.component.Button;
import net.sf.josceleton.playground.motion.app2.framework.view.component.ButtonListener;
import net.sf.josceleton.playground.motion.app2.framework.view.component.ButtonString;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;

class MainPage extends Page {
	
	public MainPage(String id, String pageIdGame) {
		super(id, new MainView(pageIdGame));
	}
	
	
	static class MainView extends AbstractPageView {
		
		private final Button btnStartGame = new ButtonString("Start Game");
		
		public MainView(final String pageIdGame) {
			this.btnStartGame.addListener(new ButtonListener() {
				@SuppressWarnings("synthetic-access")
				@Override
				public void onClicked(Button source) {
					dispatch(pageIdGame);
				}
			});
		}
		
		@Override
		public void drawWithMaxSize(WorldSnapshot world, Graphics2D g, int width, int height) {
			g.setColor(Color.PINK);
			g.drawString("MAIN PAGE", world.getHorizontalCenter(), world.getVerticalCenter());
			
			this.btnStartGame.drawOnPosition(g, world.getHorizontalCenter(), world.getVerticalCenter() + 100, world);
			
		}
	}
	
}
