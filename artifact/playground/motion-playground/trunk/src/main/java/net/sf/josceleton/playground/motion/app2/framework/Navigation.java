package net.sf.josceleton.playground.motion.app2.framework;

import java.util.HashMap;
import java.util.Map;

import net.sf.josceleton.playground.motion.app2.framework.page.Page;
import net.sf.josceleton.playground.motion.app2.framework.view.PageView;


public class Navigation {
	
	private final Map<String, Page<? extends PageView>> pagesById = new HashMap<String, Page<? extends PageView>>();
	private final String pageIdAfterLogin;
	
	public Navigation(String pageIdAfterLogin, Page<? extends PageView>... pages) {
		this.pageIdAfterLogin = pageIdAfterLogin;
		// MINOR integrity check of referenced ids
		for (Page page : pages) {
			this.pagesById.put(page.getId(), page);
		}
	}

	public String getPageIdAfterLogin() {
		return this.pageIdAfterLogin;
	}

	public Page<? extends PageView> getPageById(String pageId) {
		final Page page = this.pagesById.get(pageId);
		if(page == null) {
			throw new RuntimeException("Could not find page by ID [" + pageId + "]!");
		}
		return page;
	}
	
}
