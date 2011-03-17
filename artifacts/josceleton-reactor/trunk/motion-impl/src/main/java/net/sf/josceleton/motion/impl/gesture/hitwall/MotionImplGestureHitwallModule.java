package net.sf.josceleton.motion.impl.gesture.hitwall;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryProvider;

/**
 * @since 0.4
 */
public class MotionImplGestureHitwallModule extends AbstractModule {

	@Override protected final void configure() {
		
		bind(HitWallConfigFactory.class).toProvider(
				FactoryProvider.newFactory(HitWallConfigFactory.class, HitWallConfigImpl.class));
		bind(HitWallGestureFactory.class).toProvider(
				FactoryProvider.newFactory(HitWallGestureFactory.class, HitWallGestureImpl.class));
		bind(HitWallBuilderFactory.class).toProvider(
				FactoryProvider.newFactory(HitWallBuilderFactory.class, HitWallBuilderImpl.class));
		
	}

}
