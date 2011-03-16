package net.sf.josceleton.motion.impl.gesture.hitwall;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryProvider;

/**
 * @since 0.4
 */
public class MotionImplGestureHitwallModule extends AbstractModule {

	@Override protected final void configure() {

		this.bind(HitWallBuilderFactory.class).toProvider(
				FactoryProvider.newFactory(HitWallBuilderFactory.class, HitWallBuilderImpl.class));
		
	}

}
