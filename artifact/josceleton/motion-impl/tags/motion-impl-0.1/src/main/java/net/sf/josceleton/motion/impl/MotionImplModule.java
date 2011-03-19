package net.sf.josceleton.motion.impl;

import net.sf.josceleton.motion.impl.gesture.MotionImplGestureModule;

import com.google.inject.AbstractModule;

/**
 * @since 0.4
 */
public class MotionImplModule extends AbstractModule {

	@Override protected final void configure() {
		
		install(new MotionImplGestureModule());
		
	}

}
