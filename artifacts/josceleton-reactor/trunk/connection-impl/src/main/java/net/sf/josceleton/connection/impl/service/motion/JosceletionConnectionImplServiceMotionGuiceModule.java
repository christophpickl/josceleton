package net.sf.josceleton.connection.impl.service.motion;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryProvider;

/**
 * @since 0.3
 */
public class JosceletionConnectionImplServiceMotionGuiceModule extends AbstractModule {

	@Override protected final void configure() {
		this.bind(MotionSeparatorFactory.class).toProvider(
				FactoryProvider.newFactory(MotionSeparatorFactory.class, MotionSeparatorImpl.class));
	}

}
