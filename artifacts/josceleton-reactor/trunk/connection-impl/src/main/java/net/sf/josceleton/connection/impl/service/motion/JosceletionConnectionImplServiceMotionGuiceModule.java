package net.sf.josceleton.connection.impl.service.motion;

import net.sf.josceleton.connection.api.service.motion.MotionSeparatorCache;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.assistedinject.FactoryProvider;

/**
 * @since 0.3
 */
public class JosceletionConnectionImplServiceMotionGuiceModule extends AbstractModule {

	@Override protected final void configure() {
		bind(MotionSeparatorCache.class).to(MotionSeparatorCacheImpl.class).in(Scopes.SINGLETON);
		bind(MotionSeparatorFactory.class).toProvider(
				FactoryProvider.newFactory(MotionSeparatorFactory.class, MotionSeparatorImpl.class));
	}

}
