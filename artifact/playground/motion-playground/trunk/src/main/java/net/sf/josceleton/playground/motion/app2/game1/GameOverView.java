package net.sf.josceleton.playground.motion.app2.game1;

import java.awt.Graphics2D;

import net.sf.josceleton.playground.motion.app2.framework.view.AbstractPageView;
import net.sf.josceleton.playground.motion.app2.framework.view.component.Button;
import net.sf.josceleton.playground.motion.app2.framework.view.component.ButtonListener;
import net.sf.josceleton.playground.motion.app2.framework.view.component.ButtonString;
import net.sf.josceleton.playground.motion.app2.framework.view.component.Text;
import net.sf.josceleton.playground.motion.app2.framework.view.resources.Style;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;

public class GameOverView extends AbstractPageView {

	private final Button btnBack = new ButtonString("Back to Menu");
	private final Text txtGameOver = new Text("GAME OVER", Style.Text.MAIN);
	@SuppressWarnings("synthetic-access")
	public GameOverView() {
		this.btnBack.addListener(new ButtonListener() { @Override public void onClicked(Button source) {
				dispatch(MenuPage.ID);
		}});
	}
	
	@Override
	public void drawWithMaxSize(WorldSnapshot world, Graphics2D g, int width, int height) {
		
		
		this.txtGameOver.drawOnPosition(g, world.getHorizontalCenter() - this.txtGameOver.calculateSize(g).width / 2, 50, world);
		
		Style.Text.H1.on(g);
		final int xCommon = 100;
		int yCurrent = 100;
		g.drawString("Accuracy: 0.05%", xCommon, yCurrent);
		yCurrent += 50;
		g.drawString("Hits/Misses: 50 / 100", xCommon, yCurrent);
		
		this.btnBack.drawOnPosition(g, world.getWidth() - 300,
				world.getHeight() - 100, world);
		
	}

}
