package net.sf.josceleton.motion.impl.gesture;

import net.sf.josceleton.motion.api.gesture.GestureFactoryFacade;
import net.sf.josceleton.motion.api.gesture.hitwall.HitWallBuilder;
import net.sf.josceleton.motion.impl.gesture.hitwall.HitWallBuilderFactory;

import com.google.inject.Inject;

/**
 * @since 0.4
 */
class GestureFactoryFacadeImpl implements GestureFactoryFacade {
	
	private final HitWallBuilderFactory hitWallFactory;
	
	@Inject GestureFactoryFacadeImpl(final HitWallBuilderFactory hitWallFactory) {
		this.hitWallFactory = hitWallFactory;
	}

	/**
	 * @since 0.4
	 */
	@Override public final HitWallBuilder newHitWall() {
		return this.hitWallFactory.create();
	}
	
	// public final SwipeGestureBuilder newSwipe()
	
}
