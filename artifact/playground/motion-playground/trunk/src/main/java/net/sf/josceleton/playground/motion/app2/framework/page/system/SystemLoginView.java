package net.sf.josceleton.playground.motion.app2.framework.page.system;

import java.awt.Dimension;
import java.awt.Graphics2D;

import net.sf.josceleton.playground.motion.app2.framework.view.AbstractPageView;
import net.sf.josceleton.playground.motion.app2.framework.view.component.Button;
import net.sf.josceleton.playground.motion.app2.framework.view.component.ButtonImage;
import net.sf.josceleton.playground.motion.app2.framework.view.component.ButtonListener;
import net.sf.josceleton.playground.motion.app2.framework.view.component.Text;
import net.sf.josceleton.playground.motion.app2.framework.view.resources.Images;
import net.sf.josceleton.playground.motion.app2.framework.view.resources.Style;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;

public class SystemLoginView extends AbstractPageView {
	
	private final Text txtContinue = new Text("Hover over the Button to continue", Style.Text.MAIN);
	private final Button btnContinue;
	
	private final Text txtWaiting = new Text("Calibrating ...", Style.Text.MAIN);
	private final int psiTransparentImageHalfWidth;
	
	public SystemLoginView(final String idOfNextPage) {
		this.btnContinue = new ButtonImage(Images.NEXT);
//		this.btnContinue = new ButtonString("Continue");
		
		this.psiTransparentImageHalfWidth = Images.PSI_TRANSPARENT.getWidth(null) / 2;
		this.btnContinue.addListener(new ButtonListener() {
			@SuppressWarnings("synthetic-access")
			@Override public void onClicked(Button source) {
				dispatch(idOfNextPage);
			}
		});
	}
	
	@Override public void drawWithMaxSize(WorldSnapshot world, Graphics2D g, int width, int height) {
		if(world.getSkeleton() == null) { // will only get dispatched once at early beginning
			g.drawImage(Images.PSI_TRANSPARENT, world.getHorizontalCenter() - this.psiTransparentImageHalfWidth, world.getVerticalCenter() - 100, null);
			
			final Dimension size = this.txtWaiting.calculateSize(g);
			this.txtWaiting.drawOnPosition(g, world.getHorizontalCenter() - size.width / 2, world.getVerticalCenter(), world);
			
		} else {
			
			this.btnContinue.drawOnPosition(g, world.getHorizontalCenter() - this.btnContinue.calculateHalfWidth(g),
					world.getVerticalCenter() - 50, world);
			this.txtContinue.drawOnPosition(g, world.getHorizontalCenter() - this.txtContinue.calculateSize(g).width / 2,
					world.getHeight() - 50, world);
		}
	}

}
