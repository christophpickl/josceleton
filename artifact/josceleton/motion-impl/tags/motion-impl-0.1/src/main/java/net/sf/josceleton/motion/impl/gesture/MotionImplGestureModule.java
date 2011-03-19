package net.sf.josceleton.motion.impl.gesture;

import net.sf.josceleton.motion.api.gesture.GestureFactoryFacade;
import net.sf.josceleton.motion.impl.gesture.hitwall.MotionImplGestureHitwallModule;

import com.google.inject.AbstractModule;

/**
 * @since 0.4
 */
public class MotionImplGestureModule extends AbstractModule {

	/**
	 * @since 0.4
	 */
	@Override protected final void configure() {
		
		bind(GestureFactoryFacade.class).to(GestureFactoryFacadeImpl.class);
		
		install(new MotionImplGestureHitwallModule());
		
	}

}
