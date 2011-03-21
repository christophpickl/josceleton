package net.sf.josceleton.playground.motion.app2.framework.page;


import net.sf.josceleton.connection.api.service.motion.ContinuousMotionStream;
import net.sf.josceleton.connection.api.service.motion.MotionStreamListener;
import net.sf.josceleton.core.api.async.Closeable;
import net.sf.josceleton.playground.motion.app2.framework.Navigation;
import net.sf.josceleton.playground.motion.app2.framework.motionx.Position;
import net.sf.josceleton.playground.motion.app2.framework.motionx.PositionListener;
import net.sf.josceleton.playground.motion.app2.framework.motionx.PsiPosition;
import net.sf.josceleton.playground.motion.app2.framework.view.DrawSurface;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshotFactory;

public class PageManager implements Closeable, PageListener {
	
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
		this.currentPage = this.navigation.getStartPage();
		this.currentPage.addListener(this);
		this.surface.setView(this.currentPage.getView());
		
		this.motionStream.addListener(this.liveTransformer);
		this.surface.onUpdated(this.factory.createInitialDummy());
		
		this.quitPosition.addListener(new PositionListener() { @Override public void onPositionDetected() {
			// remove position as listener
			// change page and get confirmation
			// A. confirm: quit
			// B. abort: register psiposition again and continue with previous page (restore state!)
			System.out.println("JIPI KA JEEEEEEEEEEEEEEEEEE");
		}});
		this.motionStream.addListener(this.quitPosition);
	}

	@Override
	public void onNavigate(String pageId) {
		System.out.println("on NAVIGATE NAVIGATE NAVIGATE NAVIGATE NAVIGATE NAVIGATE  ==> " + pageId);
		this.currentPage.removeListener(this);
		
		final Page newPage = this.navigation.getPageById(pageId);
		newPage.addListener(this);
		this.surface.setView(newPage.getView());
	}
	
	@Override
	public void close() {
		this.motionStream.removeListener(this.liveTransformer);
		this.motionStream.removeListener(this.quitPosition);
	}
	
}
