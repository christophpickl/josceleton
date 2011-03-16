package net.sf.josceleton.motion.impl.gesture;

import net.sf.josceleton.motion.api.gesture.GestureFactoryFacade;
import net.sf.josceleton.motion.api.gesture.HitWallBuilder;
import net.sf.josceleton.motion.impl.gesture.hitwall.HitWallBuilderFactory;

import com.google.inject.Inject;

class GestureFactoryFacadeImpl implements GestureFactoryFacade {
	
	private final HitWallBuilderFactory hitWallFactory;
	
	@Inject GestureFactoryFacadeImpl(final HitWallBuilderFactory hitWallFactory) {
		this.hitWallFactory = hitWallFactory;
	}

	@Override public final HitWallBuilder newHitWall() {
		return this.hitWallFactory.create();
	}
	
	// public final SwipeGestureBuilder newSwipe()
	
}
