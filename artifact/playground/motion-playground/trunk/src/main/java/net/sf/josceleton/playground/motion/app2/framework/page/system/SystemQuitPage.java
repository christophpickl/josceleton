package net.sf.josceleton.playground.motion.app2.framework.page.system;

import net.sf.josceleton.playground.motion.app2.framework.page.Page;

public class SystemQuitPage extends Page {

	public static final String ID = "__system_quit";
	public static final String ID_CONFIRMED = "__system_quit_confirmed";
	
	public SystemQuitPage(String idOfContinuingPage) {
		super(ID, new SystemQuitView(idOfContinuingPage, ID_CONFIRMED));
	}

}
