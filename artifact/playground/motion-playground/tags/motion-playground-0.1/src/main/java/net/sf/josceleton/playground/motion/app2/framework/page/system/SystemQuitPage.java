package net.sf.josceleton.playground.motion.app2.framework.page.system;

import net.sf.josceleton.playground.motion.app2.framework.page.Page;
import net.sf.josceleton.playground.motion.app2.framework.page.PageArgs;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;

public class SystemQuitPage extends Page<SystemQuitView> {

	public static final String ID = SystemQuitPage.class.getName();
	public static final String ARTIFICIAL_ID_CONFIRMED = ID + "__confirmed";
	
	private final SystemQuitView view;
	
	public SystemQuitPage(String idBackToRecentPage) {
		super(ID, new SystemQuitView(idBackToRecentPage, ARTIFICIAL_ID_CONFIRMED));
		this.view = this.getView();
	}


	@Override protected void _start(WorldSnapshot world, PageArgs args) {
		this.view.setArguments(args);
	}

}
