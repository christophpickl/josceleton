package net.sf.josceleton.playground.motion.app2.framework;

import net.sf.josceleton.playground.motion.app2.framework.page.Page;
import net.sf.josceleton.playground.motion.app2.framework.page.system.LoginPage;


public class Navigation {
	
	private final Page startPage;
	
	
	public Navigation(LoginPage startPage) {
		this.startPage = startPage;
	}


	public Page getStartPage() {
		return this.startPage;
	}
	
}
