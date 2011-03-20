package net.sf.josceleton.experimental;

import net.sf.josceleton.Josceleton;
import net.sf.josceleton.motion.api.gesture.GestureFactoryFacade;
import net.sf.josceleton.motion.api.gesture.hitwall.HitWallBuilder;

/**
 * @since 0.5
 */
public final class JosceletonGestures {
	
	private static final GestureFactoryFacade DELEGATE = Josceleton.getGestureFactoryFacade();
	
	private JosceletonGestures() {
		// not instantiable
	}
	
	public static HitWallBuilder newHitWall() {
		return DELEGATE.newHitWall();
	}

}
