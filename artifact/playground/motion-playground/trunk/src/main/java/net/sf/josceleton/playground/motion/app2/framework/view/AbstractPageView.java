package net.sf.josceleton.playground.motion.app2.framework.view;

import net.sf.josceleton.core.impl.async.DefaultAsync;

public abstract class AbstractPageView extends DefaultAsync<PageViewListener> implements PageView {
	
	protected final void dispatch(final String pageId) {
		for(PageViewListener listener : getListeners()) {
			listener.onNavigateTo(pageId);
		}
	}
	
}
