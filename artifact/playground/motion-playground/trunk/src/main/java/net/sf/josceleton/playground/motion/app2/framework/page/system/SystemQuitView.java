package net.sf.josceleton.playground.motion.app2.framework.page.system;

import java.awt.Dimension;
import java.awt.Graphics2D;

import net.sf.josceleton.playground.motion.app2.framework.page.PageArgs;
import net.sf.josceleton.playground.motion.app2.framework.view.AbstractPageView;
import net.sf.josceleton.playground.motion.app2.framework.view.component.Button;
import net.sf.josceleton.playground.motion.app2.framework.view.component.ButtonImage;
import net.sf.josceleton.playground.motion.app2.framework.view.component.ButtonListener;
import net.sf.josceleton.playground.motion.app2.framework.view.component.Text;
import net.sf.josceleton.playground.motion.app2.framework.view.resources.Images;
import net.sf.josceleton.playground.motion.app2.framework.view.resources.Style;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SystemQuitView extends AbstractPageView {
	
	private static final Log LOG = LogFactory.getLog(SystemQuitView.class);
	
	private final Button btnOkay;
	private final Button btnNokay;
	private final Text txtConfirm = new Text("Really Quit?", Style.Text.MAIN);
	private PageArgs args;
	
	@SuppressWarnings("synthetic-access")
	public SystemQuitView(final String idBackToRecentPage, final String idQuitConfirmed) {
		LOG.info("new SystemQuitView(idBackToRecentPage="+idBackToRecentPage+", idQuitConfirmed="+idQuitConfirmed+")");
		
		this.btnOkay = new ButtonImage(Images.OKAY);
		this.btnNokay = new ButtonImage(Images.NOKAY);
		
		this.btnOkay.addListener(new ButtonListener() { @Override public void onClicked(Button source) {
			dispatch(idQuitConfirmed); } });
		this.btnNokay.addListener(new ButtonListener() { @Override public void onClicked(Button source) {
			dispatch(idBackToRecentPage, SystemQuitView.this.args); } });
	}
	
	@Override public void drawWithMaxSize(WorldSnapshot world, Graphics2D g, int width, int height) {
		final Dimension size = this.txtConfirm.calculateSize(g);
		this.txtConfirm.drawOnPosition(g, world.getHorizontalCenter() - size.width / 2 + /*WTF?!*/30, world.getVerticalCenter() - 100, world);
		
		this.btnOkay.drawOnPosition(g, world.getHorizontalCenter() - 200, world.getVerticalCenter() + 20, world);
		this.btnNokay.drawOnPosition(g, world.getHorizontalCenter() + 200, world.getVerticalCenter() + 20, world);
	}

	public void setArguments(PageArgs args) {
		this.args = args;
	}

}
