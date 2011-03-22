package net.sf.josceleton.playground.motion.app2.framework;

import java.util.HashMap;
import java.util.Map;

import net.sf.josceleton.playground.motion.app2.framework.page.Page;


public class Navigation {
	
	private final Map<String, Page> pagesById = new HashMap<String, Page>();
	private final String pageIdAfterLogin;
	
	public Navigation(String pageIdAfterLogin, Page... pages) {
		this.pageIdAfterLogin = pageIdAfterLogin;
		// MINOR integrity check of referenced ids
		for (Page page : pages) {
			this.pagesById.put(page.getId(), page);
		}
	}

	public String getPageIdAfterLogin() {
		return this.pageIdAfterLogin;
	}

	public Page getPageById(String pageId) {
		final Page page = this.pagesById.get(pageId);
		if(page == null) {
			throw new RuntimeException("Could not find page by ID [" + pageId + "]!");
		}
		return page;
	}
	
}
