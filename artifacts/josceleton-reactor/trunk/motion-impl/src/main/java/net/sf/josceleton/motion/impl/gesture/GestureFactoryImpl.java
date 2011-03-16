package net.sf.josceleton.motion.impl.gesture;

import net.sf.josceleton.motion.api.gesture.GestureFactory;
import net.sf.josceleton.motion.api.gesture.HitWallBuilder;

import com.google.inject.Inject;

class GestureFactoryImpl implements GestureFactory {
	
	private final HitWallBuilderFactory hitWallFactory;
	
	@Inject GestureFactoryImpl(final HitWallBuilderFactory hitWallFactory) {
		this.hitWallFactory = hitWallFactory;
	}

	@Override public final HitWallBuilder newHitWall() {
		return this.hitWallFactory.create();
	}

}
