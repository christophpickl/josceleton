package net.sf.josceleton.josceleton.test.integration;

import net.sf.josceleton.motion.api.gesture.GestureFactoryFacade;

public abstract class AbstractMotionIntegrationTest extends AbstractIntegrationTest {
	
	protected final GestureFactoryFacade newGesture() {
		return this.getInjectorButBeCarefulToNotMessUpYourTestCode().getInstance(GestureFactoryFacade.class);
	}
	
}
