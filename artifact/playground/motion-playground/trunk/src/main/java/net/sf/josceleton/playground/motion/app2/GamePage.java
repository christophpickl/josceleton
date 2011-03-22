package net.sf.josceleton.playground.motion.app2;

import java.awt.Dimension;
import java.awt.Graphics2D;

import net.sf.josceleton.playground.motion.app2.framework.page.Page;
import net.sf.josceleton.playground.motion.app2.framework.view.AbstractPageView;
import net.sf.josceleton.playground.motion.app2.framework.view.common.Resources;
import net.sf.josceleton.playground.motion.app2.framework.view.component.Button;
import net.sf.josceleton.playground.motion.app2.framework.view.component.ButtonImage;
import net.sf.josceleton.playground.motion.app2.framework.view.component.ButtonListener;
import net.sf.josceleton.playground.motion.app2.framework.view.component.ButtonString;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;

class GamePage extends Page {

	
	public GamePage(String id, String pageIdMain) {
		super(id, new GamePageView(pageIdMain));
	}
	
	
	
	static class GamePageView extends AbstractPageView {
		
		private final Button btnBack = new ButtonString("Back");
		private final Button hitDumbFace = new ButtonImage(Resources.DUMB_FACE);
		
		public GamePageView(final String pageIdMain) {
			this.btnBack.addListener(new ButtonListener() {
				@SuppressWarnings("synthetic-access")
				@Override
				public void onClicked(Button source) {
					dispatch(pageIdMain);
				}
			});
		}
		
		@Override
		public void drawWithMaxSize(WorldSnapshot world, Graphics2D g, int width, int height) {
			// TODO was ist der unterschied zwischen zb width und world.getWidth??
			
			this.btnBack.drawOnPosition(g, world.getHorizontalCenter(), world.getVerticalCenter() + 100, world);
			
		}
	}
	
}
