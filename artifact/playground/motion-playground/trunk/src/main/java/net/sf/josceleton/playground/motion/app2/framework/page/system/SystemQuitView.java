package net.sf.josceleton.playground.motion.app2.framework.page.system;

import java.awt.Dimension;
import java.awt.Graphics2D;

import net.sf.josceleton.playground.motion.app2.framework.view.AbstractPageView;
import net.sf.josceleton.playground.motion.app2.framework.view.common.Resources;
import net.sf.josceleton.playground.motion.app2.framework.view.common.Style;
import net.sf.josceleton.playground.motion.app2.framework.view.component.Button;
import net.sf.josceleton.playground.motion.app2.framework.view.component.ButtonImage;
import net.sf.josceleton.playground.motion.app2.framework.view.component.ButtonListener;
import net.sf.josceleton.playground.motion.app2.framework.view.component.Text;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;

public class SystemQuitView extends AbstractPageView {
	
	private final Button btnOkay;
	private final Button btnNokay;
	private final Text txtComfirm = new Text("Really Quit?", Style.Text.MAIN);
	private Dimension txtConfirmSize;
	
	@SuppressWarnings("synthetic-access")
	public SystemQuitView(final String idOfContinuingPage, final String idOfNokay) {
		System.out.println("new SystemQuitView(idOfContinuingPage="+idOfContinuingPage+", idOfNokay="+idOfNokay+")");
		
		this.btnOkay = new ButtonImage(Resources.OKAY);
		this.btnNokay = new ButtonImage(Resources.NOKAY);
		
		this.btnOkay.addListener(new ButtonListener() { @Override public void onClicked(Button source) {
			dispatch(idOfNokay); } });
		this.btnNokay.addListener(new ButtonListener() { @Override public void onClicked(Button source) {
			dispatch(idOfContinuingPage); } });
	}
	
	@Override public void drawWithMaxSize(WorldSnapshot world, Graphics2D g, int width, int height) {
		if(txtConfirmSize == null) {
			txtConfirmSize = this.txtComfirm.calculateSize(g);
		}
		
		this.txtComfirm.drawOnPosition(g, world.getHorizontalCenter() - this.txtConfirmSize.width / 2, world.getVerticalCenter(), world);
		
		this.btnOkay.drawOnPosition(g, world.getHorizontalCenter() - 200, world.getVerticalCenter(), world);
		this.btnNokay.drawOnPosition(g, world.getHorizontalCenter() + 200, world.getVerticalCenter(), world);
	}

}
