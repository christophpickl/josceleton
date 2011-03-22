package net.sf.josceleton.playground.motion.app2.framework.page;


import net.sf.josceleton.connection.api.service.motion.ContinuousMotionStream;
import net.sf.josceleton.connection.api.service.motion.MotionStreamListener;
import net.sf.josceleton.core.api.async.Closeable;
import net.sf.josceleton.core.impl.async.DefaultAsync;
import net.sf.josceleton.playground.motion.app2.framework.Navigation;
import net.sf.josceleton.playground.motion.app2.framework.motionx.Position;
import net.sf.josceleton.playground.motion.app2.framework.motionx.PositionListener;
import net.sf.josceleton.playground.motion.app2.framework.motionx.PsiPosition;
import net.sf.josceleton.playground.motion.app2.framework.page.system.SystemLoginPage;
import net.sf.josceleton.playground.motion.app2.framework.page.system.SystemQuitPage;
import net.sf.josceleton.playground.motion.app2.framework.view.DrawSurface;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshotFactory;

public class PageManager extends DefaultAsync<PageManagerListener> implements Closeable, PageListener {
	
	private final Position quitPosition = new PsiPosition();
	private final Navigation navigation;
	private final ContinuousMotionStream motionStream;
	private final DrawSurface surface;
	private final MotionStreamListener liveTransformer;
	private Page currentPage;
	private final WorldSnapshotFactory factory;
	
	public PageManager(Navigation navigation, ContinuousMotionStream motionStream, DrawSurface surface, WorldSnapshotFactory factory) {
		this.navigation = navigation;
		this.motionStream = motionStream;
		this.surface = surface;
		
		// directly redirect messages from motion strema to surface
		this.liveTransformer = new MotionMessageLiveStreamTransformer(factory, this.surface);
		this.factory = factory;
	}
	
	public void start() {
		System.out.println("PageManger: start()");

		this.motionStream.addListener(this.liveTransformer);
		this.surface.onUpdated(this.factory.createInitialDummy()); // create first/artifical world snapshot
		
		this.quitPosition.addListener(new PositionListener() { @Override public void onPositionDetected() {
			onQuitPositionDetected();
		}});
		this.motionStream.addListener(this.quitPosition);
		
		this.updateForNewPage(new SystemLoginPage(this.navigation.getPageIdAfterLogin()));
	}
	
	/** {@inheritDoc} from {@link PageListener} */
	@Override public void onNavigate(String pageId) {
//		TODO implement some beep sound when user interaction fired: AudioFile.S1.start();
		System.out.println("on NAVIGATE NAVIGATE NAVIGATE NAVIGATE NAVIGATE NAVIGATE =============> " + pageId);
		this.currentPage.removeListener(this);
		
		// quit is only exception (no other special cases leading to an if-else-cascade!)
		if(pageId.equals(SystemQuitPage.ID_CONFIRMED)) {
			for(PageManagerListener listener : this.getListeners()) {
				listener.onQuit();
			}
			return;
		} else if(this.currentPage instanceof SystemQuitPage) {
			// quit was not confirmed; continue navigating to recent page
			this.motionStream.addListener(this.quitPosition);
		}
		
		final Page newPage;
		if(pageId.equals(SystemQuitPage.ID)) {
			newPage = new SystemQuitPage(this.currentPage.getId());
		} else {
			newPage = this.navigation.getPageById(pageId);
		}
		
		if(newPage == null) {
			throw new RuntimeException("Could not find page by id [" + pageId + "]! (implement precheck validation!)");
		}
		this.updateForNewPage(newPage);
	}
	
	private void updateForNewPage(Page newPage) {
		this.currentPage = newPage;
		this.currentPage.addListener(this);
		this.surface.setView(newPage.getView());
	}
	
	void onQuitPositionDetected() {
		this.motionStream.removeListener(this.quitPosition);
		// safe old state?
		this.onNavigate(SystemQuitPage.ID);

		// A. confirm: quit
		// B. abort: register psiposition again and continue with previous page (restore state!)
	}
	
	@Override
	public void close() {
		// TODO use aspectj to ensure all opened resources are closed again!
		this.motionStream.removeListener(this.liveTransformer);
		this.motionStream.removeListener(this.quitPosition);
	}
	
}
