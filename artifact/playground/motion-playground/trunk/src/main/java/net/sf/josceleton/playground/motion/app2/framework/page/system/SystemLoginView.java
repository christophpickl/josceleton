package net.sf.josceleton.playground.motion.app2.framework.page.system;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;

import net.sf.josceleton.playground.motion.app2.framework.view.AbstractPageView;
import net.sf.josceleton.playground.motion.app2.framework.view.component.Button;
import net.sf.josceleton.playground.motion.app2.framework.view.component.ButtonImage;
import net.sf.josceleton.playground.motion.app2.framework.view.component.ButtonListener;
import net.sf.josceleton.playground.motion.app2.framework.view.component.Text;
import net.sf.josceleton.playground.motion.app2.framework.view.resources.Style;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;

public class SystemLoginView extends AbstractPageView {
	
	private final Button btnContinue;
	private final Text txtWaiting = new Text("Calibrating ...", Style.Text.MAIN);
	private final int continueImageHalfWidth;
	
	public SystemLoginView(final String idOfNextPage, Image continueImage) {
		this.btnContinue = new ButtonImage(continueImage);
		
		this.continueImageHalfWidth = continueImage.getWidth(null) / 2;
		
		this.btnContinue.addListener(new ButtonListener() {
			@SuppressWarnings("synthetic-access")
			@Override public void onClicked(Button source) {
				dispatch(idOfNextPage);
			}
		});
	}
	
	@Override public void drawWithMaxSize(WorldSnapshot world, Graphics2D g, int width, int height) {
		
		if(world.getSkeleton() == null) { // will only get dispatched once at early beginning
			final Dimension size = this.txtWaiting.calculateSize(g);
			this.txtWaiting.drawOnPosition(g, world.getHorizontalCenter() - size.width / 2, world.getVerticalCenter(), world);
			
		} else {
			this.btnContinue.drawOnPosition(g, world.getHorizontalCenter() - this.continueImageHalfWidth, world.getVerticalCenter(), world);
			
		}
	}

}
