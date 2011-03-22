package net.sf.josceleton.playground.motion.app2.framework.page;

import java.util.Collection;
import java.util.HashSet;

import net.sf.josceleton.connection.api.service.motion.MotionStreamListener;
import net.sf.josceleton.core.impl.async.DefaultAsync;
import net.sf.josceleton.playground.motion.app2.framework.view.PageView;
import net.sf.josceleton.playground.motion.app2.framework.view.PageViewListener;

public abstract class Page<V extends PageView> extends DefaultAsync<PageListener> implements PageViewListener {
	
	private static final Collection<MotionStreamListener> EMPTY = new HashSet<MotionStreamListener>(0);
	
	private final String id;
	
	private final V view;

	public Page(String id, final V view) {
		this.id = id;
		this.view = view;
		this.view.addListener(this);
	}
	
	public V getView() {
		return this.view;
	}

	public String getId() {
		return this.id;
	}

	/** {@inheritDoc} from {@link PageViewListener} */
	@Override public final void onNavigateTo(String pageId) {
		this.dispatchNavigateTo(pageId);
	}
	
	protected final void dispatchNavigateTo(String pageId) {
		for(PageListener listener : this.getListeners()) {
			listener.onNavigate(pageId);
		}
	}

	public void start() {
		// can be overridden
	}

	public void stop() {
		// can be overridden
	}

	public Collection<MotionStreamListener> getMotionStreamListeners() {
		return EMPTY;
	}
	
}
