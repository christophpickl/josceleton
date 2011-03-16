package net.sf.josceleton.motion.impl.gesture.hitwall;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryProvider;

/**
 * @since 0.4
 */
public class MotionImplGestureHitwallModule extends AbstractModule {

	@Override final protected void configure() {

//		bind(HitWallGestureBuilder.class).to(HitWallGestureBuilderImpl.class);
		this.bind(HitWallBuilderFactory.class).toProvider(
				FactoryProvider.newFactory(HitWallBuilderFactory.class, HitWallBuilderImpl.class));
		
	}

}
