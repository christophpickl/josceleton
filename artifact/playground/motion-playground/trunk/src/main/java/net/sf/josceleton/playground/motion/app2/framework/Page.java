package net.sf.josceleton.playground.motion.app2.framework;

import net.sf.josceleton.core.impl.async.DefaultAsync;

public abstract class Page extends DefaultAsync<PageListener> implements PageViewListener {
	
	private final String id;
	
	private final PageView view;

	public Page(String id, final PageView view) {
		this.id = id;
		this.view = view;
		this.view.addListener(this);
	}

	public String getId() {
		return this.id;
	}

	public PageView getView() {
		return this.view;
	}

	@Override
	public void onNavigateTo(String pageId) {
		for(PageListener listener : this.getListeners()) {
			listener.onNavigate(pageId);
		}
	}
	
}
