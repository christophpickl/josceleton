package net.sf.josceleton.playground.motion.app2.game1;

import net.sf.josceleton.playground.motion.app2.framework.page.Page;
import net.sf.josceleton.playground.motion.app2.framework.page.PageArgs;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;

public class GameOverPage extends Page<GameOverView> {

	public static final String ID = GameOverPage.class.getName();
	
	public static final String ARG_HITS = "ARG_HITS";
	public static final String ARG_MISSES = "ARG_MISSES";
	public static final String ARG_TIME_PLAYED = "ARG_TIME_PLAYED";
	
	private final GameOverView view;
	
	public GameOverPage() {
		super(GameOverPage.ID, new GameOverView());
		this.view = this.getView();
	}

	@Override protected void _start(WorldSnapshot world, PageArgs args) {
		final int countHits = args.get(ARG_HITS, Integer.class).intValue();
		final int countMisses = args.get(ARG_MISSES, Integer.class).intValue();
		final int timePlayed = args.get(ARG_TIME_PLAYED, Integer.class).intValue();
		this.view.setArguments(countHits, countMisses, timePlayed);
	}
}
