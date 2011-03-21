package net.sf.josceleton.playground.motion.app2.framework;

import java.util.HashMap;
import java.util.Map;

import net.sf.josceleton.playground.motion.app2.framework.page.Page;
import net.sf.josceleton.playground.motion.app2.framework.page.system.LoginPage;


public class Navigation {
	
	private final Page startPage;
	
	private final Map<String, Page> pagesById = new HashMap<String, Page>();
	
	public Navigation(String pageIdAfterLogin, Page... pages) {
		this.startPage = new LoginPage(pageIdAfterLogin);
		// MINOR integrity check
		for (Page page : pages) {
			this.pagesById.put(page.getId(), page);
		}
	}

	public Page getStartPage() {
		return this.startPage;
	}

	public Page getPageById(String pageId) {
		final Page page = this.pagesById.get(pageId);
		if(page == null) {
			throw new RuntimeException("Could not find page by ID [" + pageId + "]!");
		}
		return page;
	}
	
}
