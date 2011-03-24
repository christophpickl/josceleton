package net.sf.josceleton.playground.motion.common;

import net.sf.josceleton.core.api.async.Listener;

public interface WindowXListener extends Listener {
	
	void onQuit();
	
	// TODO onEscapeEntered(); ==> change fullscreen mode; needs some positioning fixes, as would not work properly as it is right now... :-|
	
}
