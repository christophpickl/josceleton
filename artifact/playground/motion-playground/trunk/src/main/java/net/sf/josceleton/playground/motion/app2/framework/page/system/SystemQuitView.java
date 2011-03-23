package net.sf.josceleton.playground.motion.app2.framework.page.system;

import java.awt.Dimension;
import java.awt.Graphics2D;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.josceleton.playground.motion.app2.framework.view.AbstractPageView;
import net.sf.josceleton.playground.motion.app2.framework.view.component.Button;
import net.sf.josceleton.playground.motion.app2.framework.view.component.ButtonImage;
import net.sf.josceleton.playground.motion.app2.framework.view.component.ButtonListener;
import net.sf.josceleton.playground.motion.app2.framework.view.component.Text;
import net.sf.josceleton.playground.motion.app2.framework.view.resources.Images;
import net.sf.josceleton.playground.motion.app2.framework.view.resources.Style;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;

public class SystemQuitView extends AbstractPageView {
	
	private static final Log LOG = LogFactory.getLog(SystemQuitView.class);
	
	private final Button btnOkay;
	private final Button btnNokay;
	private final Text txtConfirm = new Text("Really Quit?", Style.Text.MAIN);
	
	@SuppressWarnings("synthetic-access")
	public SystemQuitView(final String idOfContinuingPage, final String idOfNokay) {
		LOG.info("new SystemQuitView(idOfContinuingPage="+idOfContinuingPage+", idOfNokay="+idOfNokay+")");
		
		this.btnOkay = new ButtonImage(Images.OKAY);
		this.btnNokay = new ButtonImage(Images.NOKAY);
		
		this.btnOkay.addListener(new ButtonListener() { @Override public void onClicked(Button source) {
			dispatch(idOfNokay); } });
		this.btnNokay.addListener(new ButtonListener() { @Override public void onClicked(Button source) {
			dispatch(idOfContinuingPage); } });
	}
	
	@Override public void drawWithMaxSize(WorldSnapshot world, Graphics2D g, int width, int height) {
		final Dimension size = this.txtConfirm.calculateSize(g);
		this.txtConfirm.drawOnPosition(g, world.getHorizontalCenter() - size.width / 2 + /*WTF?!*/20, world.getVerticalCenter() - 100, world);
		
		this.btnOkay.drawOnPosition(g, world.getHorizontalCenter() - 200, world.getVerticalCenter() + 20, world);
		this.btnNokay.drawOnPosition(g, world.getHorizontalCenter() + 200, world.getVerticalCenter() + 20, world);
	}

}
