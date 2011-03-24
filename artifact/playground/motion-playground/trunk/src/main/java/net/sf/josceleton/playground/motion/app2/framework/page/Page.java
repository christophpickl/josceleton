package net.sf.josceleton.playground.motion.app2.framework.page;

import java.util.Collection;
import java.util.HashSet;

import net.sf.josceleton.connection.api.service.motion.MotionStreamListener;
import net.sf.josceleton.core.impl.async.DefaultAsync;
import net.sf.josceleton.playground.motion.app2.framework.view.PageView;
import net.sf.josceleton.playground.motion.app2.framework.view.PageViewListener;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;

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
	@Override public final void onNavigateTo(String pageId, PageArgs passingArgs) {
		this.dispatchNavigateTo(pageId, passingArgs);
	}
//	protected final void dispatchNavigateTo(String pageId) {
//		this.dispatchNavigateTo(pageId, null);
//	}

	protected final void dispatchNavigateTo(String pageId, PageArgs passingArgs) {
		for(PageListener listener : this.getListeners()) {
			listener.onNavigate(pageId, passingArgs);
		}
	}
	private PageArgs args;
	public final PageArgs getArgs() {
		return this.args;
	}
	public final void start(WorldSnapshot world, PageArgs pArgs) {
		this.args = pArgs;
		this._start(world, this.args);
	}

	@SuppressWarnings("unused")
	protected void _start(WorldSnapshot world, PageArgs pArgs) {
		// can be overridden
	}

	public void stop() {
		// can be overridden
	}

	public Collection<MotionStreamListener> getMotionStreamListeners() {
		return EMPTY;
	}
	
}
