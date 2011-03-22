package net.sf.josceleton.playground.motion.app2.framework.page;


import java.util.Collection;

import net.sf.josceleton.connection.api.service.motion.MotionStreamListener;
import net.sf.josceleton.core.api.async.Closeable;
import net.sf.josceleton.core.impl.async.DefaultAsync;
import net.sf.josceleton.playground.motion.app2.IThrottledMotionStream;
import net.sf.josceleton.playground.motion.app2.framework.Navigation;
import net.sf.josceleton.playground.motion.app2.framework.motionx.Position;
import net.sf.josceleton.playground.motion.app2.framework.motionx.PositionListener;
import net.sf.josceleton.playground.motion.app2.framework.motionx.PsiPosition;
import net.sf.josceleton.playground.motion.app2.framework.page.system.SystemLoginPage;
import net.sf.josceleton.playground.motion.app2.framework.page.system.SystemQuitPage;
import net.sf.josceleton.playground.motion.app2.framework.view.DrawSurface;
import net.sf.josceleton.playground.motion.app2.framework.view.PageView;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshotFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PageManager extends DefaultAsync<PageManagerListener> implements Closeable, PageListener {
	
	private static final Log LOG = LogFactory.getLog(PageManager.class);
	
	private final Position quitPosition = new PsiPosition();
	private final Navigation navigation;
	private final IThrottledMotionStream motionStream;
	private final DrawSurface surface;
	private final MotionStreamListener liveTransformer;
	private Page<? extends PageView> currentPage;
	private final WorldSnapshotFactory factory;
	private final SystemLoginPage loginPage;
	
	public PageManager(Navigation navigation, IThrottledMotionStream motionStream, DrawSurface surface, WorldSnapshotFactory factory) {
		this.navigation = navigation;
		this.motionStream = motionStream;
		this.surface = surface;
		this.loginPage = new SystemLoginPage(this.navigation.getPageIdAfterLogin());
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
		
		this.updateForNewPage(this.loginPage);
	}
	
	/** {@inheritDoc} from {@link PageListener} */
	@Override public void onNavigate(String pageId) {
//		TODO implement some beep sound when user interaction fired: AudioFile.S1.start();
		LOG.info("on NAVIGATE NAVIGATE NAVIGATE NAVIGATE NAVIGATE NAVIGATE =============> " + pageId);
		this.currentPage.stop();
		
		final Collection<MotionStreamListener> listeners = this.currentPage.getMotionStreamListeners();
		for (MotionStreamListener listener : listeners) {
			LOG.debug("Removing listener: " + listener);
			this.motionStream.requestForRemove(listener);
		}
		this.currentPage.removeListener(this);
		
		// quit is only exception (no other special cases leading to an if-else-cascade!)
		if(pageId.equals(SystemQuitPage.ID_CONFIRMED)) {
			LOG.info("Quit was confirmed by user.");
			for(PageManagerListener listener : this.getListeners()) {
				listener.onQuit();
			}
			return;
			
		} else if(pageId.equals(this.loginPage.getId())) { // user stood in psi position while in login page
			this.updateForNewPage(this.loginPage);
		}
		if(this.currentPage instanceof SystemQuitPage) {
			LOG.info("quit was not confirmed; continue navigating to recent page");
			this.motionStream.addListener(this.quitPosition);
		}
		
		final Page<? extends PageView> newPage;
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
	
	private void updateForNewPage(Page<? extends PageView> newPage) {
		this.currentPage = newPage;
		this.currentPage.addListener(this);
		final Collection<MotionStreamListener> listeners = this.currentPage.getMotionStreamListeners();
		for (MotionStreamListener listener : listeners) {
			this.motionStream.addListener(listener);
		}
		this.currentPage.start();
		
		this.surface.setView(newPage.getView());
	}
	
	void onQuitPositionDetected() {
		if(this.currentPage instanceof SystemQuitPage) {
			LOG.info("ignoring onQuitPositionDetected() as already on quit page.");
			return;
		}
		LOG.info("onQuitPositionDetected()");
		this.motionStream.requestForRemove(this.quitPosition);
		// safe old state?
		this.onNavigate(SystemQuitPage.ID); // FIXME current page soll dann nur in sleep mode gesetzt werden, nicht gleich stop

		// A. confirm: quit
		// B. abort: register psiposition again and continue with previous page (restore state!)
	}
	
	@Override
	public void close() {
		// TODO use aspectj to ensure all opened resources are closed again!
		this.motionStream.requestForRemove(this.liveTransformer);
		this.motionStream.requestForRemove(this.quitPosition);
	}
	
}
