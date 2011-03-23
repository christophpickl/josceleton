package net.sf.josceleton.playground.motion.app2.game1;

import net.sf.josceleton.playground.motion.app2.framework.page.Page;

public class GameOverPage extends Page<GameOverView> {

	public static final String ID = GameOverPage.class.getName();
	
	private final GameOverView view;
	
	public GameOverPage() {
		super(GameOverPage.ID, new GameOverView());
		this.view = this.getView();
	}

}
