package net.sf.josceleton.motion.impl.gesture;

import net.sf.josceleton.motion.api.gesture.GestureFactory;
import net.sf.josceleton.motion.api.gesture.HitWallGesture;

import com.google.inject.AbstractModule;

/**
 * @since 0.4
 */
public class JosceletonMotionImplGestureGuiceModule extends AbstractModule {

	/**
	 * @since 0.4
	 */
	@Override protected final void configure() {
		
		bind(HitWallGesture.class).to(HitWallGestureImpl.class);
		
		bind(GestureFactory.class).to(GestureFactoryImpl.class);
		
	}

}
