package net.sf.josceleton.playground.motion.app2.framework.view.component;

import net.sf.josceleton.core.api.async.Listener;

public interface ButtonListener extends Listener {
	
	void onClicked(Button source);
	
}
