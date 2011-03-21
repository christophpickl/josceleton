package net.sf.josceleton.playground.motion.app2.framework;

import net.sf.josceleton.connection.api.service.motion.ContinuousMotionStream;
import net.sf.josceleton.connection.api.service.motion.MotionStreamListener;
import net.sf.josceleton.core.api.async.Closeable;
import net.sf.josceleton.playground.motion.common.DrawSurface;

public class PageManager implements Closeable, PageListener {
	
	private final Navigation navigation;
	private final ContinuousMotionStream motionStream;
	private final DrawSurface surface;
	private final MotionStreamListener addedListener;
	private Page currentPage;
	private final WorldSnapshotFactory factory;
	
	public PageManager(Navigation navigation, ContinuousMotionStream motionStream, DrawSurface surface, WorldSnapshotFactory factory) {
		this.navigation = navigation;
		this.motionStream = motionStream;
		this.surface = surface;
		this.addedListener = new ReroutingListener(factory, this.surface);
		this.currentPage = navigation.getStartPage();
		this.factory = factory;
	}
	
	public void start() {
		System.out.println("PageManger: start()");
		this.currentPage.addListener(this);
		
		this.motionStream.addListener(this.addedListener);
		this.surface.onUpdated(this.factory.createInitialDummy());
//		final PageView view = page.getView();
//		this.surface.setView(view);
	}
	
	@Override
	public void close() {
		this.motionStream.removeListener(this.addedListener);
	}

	@Override
	public void onNavigate(String pageId) {
		
		System.out.println("on NAVIGATE NAVIGATE NAVIGATE NAVIGATE NAVIGATE NAVIGATE  ==> " + pageId);
		
		this.currentPage.removeListener(this);
		// lookup navigation next page
	}
	
	
}
