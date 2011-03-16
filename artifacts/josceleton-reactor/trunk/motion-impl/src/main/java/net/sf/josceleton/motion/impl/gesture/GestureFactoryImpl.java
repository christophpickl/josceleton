package net.sf.josceleton.motion.impl.gesture;

import net.sf.josceleton.motion.api.gesture.GestureFactory;
import net.sf.josceleton.motion.api.gesture.HitWallGestureBuilder;

import com.google.inject.Inject;

class GestureFactoryImpl implements GestureFactory {
	
	private final HitWallGestureBuilderFactory hitWallFactory;
	
	@Inject GestureFactoryImpl(final HitWallGestureBuilderFactory hitWallFactory) {
		this.hitWallFactory = hitWallFactory;
	}

	@Override public final HitWallGestureBuilder newHitWall() {
		return this.hitWallFactory.create();
	}

}
