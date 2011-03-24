package net.sf.josceleton.playground.motion.app2.framework.page;

import net.sf.josceleton.core.api.async.Listener;

public interface PageListener extends Listener {

	void onNavigate(String pageId);

	void onNavigate(String pageId, PageArgs args);
	
}
