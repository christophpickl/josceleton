package net.sf.josceleton.playground.motion.app2.framework;

import net.sf.josceleton.core.api.async.Listener;

public interface PageViewListener extends Listener {
	
	void onNavigateTo(String pageId);
	
}
