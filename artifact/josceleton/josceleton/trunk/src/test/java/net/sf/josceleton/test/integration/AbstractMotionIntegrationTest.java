package net.sf.josceleton.test.integration;

import net.sf.josceleton.motion.api.gesture.GestureFactoryFacade;

public abstract class AbstractMotionIntegrationTest<T extends AbstractMotionIntegrationTest<T>> extends AbstractIntegrationTest<T> {
	
	protected final GestureFactoryFacade newGesture() {
		return this.getInjectorButBeCarefulToNotMessUpYourTestCode().getInstance(GestureFactoryFacade.class);
	}
	
}
