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
	
	@SuppressWarnings("boxing")
	private static final Object[][] RANKING = new Object[][] {
		new Object[] { 2.0, "Fantastic! I've never seen such a great boxer!!! :)))" },
		new Object[] { 5.0, "Well done! Did you practice at home?! :)" },
		new Object[] { 10.0, "Okay, well... it could be better :-/" },
		new Object[] { 9999.0, "Let's say you are a pacifist... :(" }
	};
	
	private final Button btnBack = new ButtonString("<- Menu");
	private final Button btnReplay = new ButtonString("Replay ->");
	private final Text txtGameOver = new Text("GAME OVER", Style.Text.MAIN);

	private int countHits;
	private int countMisses;
	private int timePlayed;
	private Text rankingMessage;
	
	private double avgTimeHit;
	
	public void setArguments(int hits, int misses, int time) {
		this.countHits = hits;
		this.countMisses = misses;
		this.timePlayed = time;
		
		if(this.countHits == 0) {
			this.avgTimeHit = 0.0;
		} else {
			this.avgTimeHit = ((int) Math.round(this.timePlayed / this.countHits * 100)) / 100.0;
		}
		
		for (Object[] ranking : RANKING) {
			final double limit = ((Double) ranking[0]).doubleValue();
			if(this.avgTimeHit < limit) {
				this.rankingMessage = new Text((String) ranking[1], Style.Text.P);
				break;
			}
		}
	}
	
	@SuppressWarnings("synthetic-access")
	public GameOverView() {
		this.btnBack.addListener(new ButtonListener() { @Override public void onClicked(Button source) {
			dispatch(MenuPage.ID);
		}});
		this.btnReplay.addListener(new ButtonListener() { @Override public void onClicked(Button source) {
			dispatch(GamePlayingPage.ID);
		}});
	}
	
	@Override
	public void drawWithMaxSize(WorldSnapshot world, Graphics2D g, int width, int height) {
		
		this.txtGameOver.drawOnPosition(g, world.getHorizontalCenter() - this.txtGameOver.calculateSize(g).width / 2, 80, world);
		
		final int hitsAndMisses = this.countHits + this.countMisses;
		final int accuracyPercent = (int) Math.round((this.countHits / ((double) hitsAndMisses)) * 100);
		
		
		final int gapLeft = world.getHorizontalCenter() - 300;
		final int yOffset = world.getVerticalCenter() - 70;
		int i = 0;
		Style.Text.P.on(g);
		
		this.rankingMessage.drawOnPosition(g, world.getHorizontalCenter() - this.rankingMessage.calculateSize(g).width / 2, yOffset - 80, world);
		drawRow(g, "Hits / Misses:", this.countHits + " / " + this.countMisses, gapLeft, yOffset, i++);
		drawRow(g, "Accuracy:", accuracyPercent + "%", gapLeft, yOffset, i++);
		drawRow(g, "Hit Each:", this.avgTimeHit + " Seconds", gapLeft, yOffset, i++);
		
		this.btnBack.drawOnPosition(g, 60, world.getHeight() - 120, world);
		this.btnReplay.drawOnPosition(g, world.getWidth() - 300, world.getHeight() - 120, world);
	}
	
	private void drawRow(Graphics2D g, String label, String value, int gapLeft, int yOffset, int i) {
		final int y = yOffset + i * 70;
		g.drawString(label, gapLeft, y);
		g.drawString(value, gapLeft + 250, y);
	}

}
