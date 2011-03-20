package net.sf.josceleton.connection.impl.service.motion;

import net.sf.josceleton.connection.api.service.motion.MotionStreamFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.assistedinject.FactoryProvider;

/**
 * @since 0.3
 */
public class ConnectionImplServiceMotionModule extends AbstractModule {

	@Override protected final void configure() {
		bind(MotionStreamFactory.class).to(MotionStreamFactoryImpl.class).in(Scopes.SINGLETON);
		bind(MotionStreamInternalFactory.class).toProvider(
				FactoryProvider.newFactory(MotionStreamInternalFactory.class, MotionStreamImpl.class));
		
		bind(ContinuousMotionStreamInternalFactory.class).toProvider(FactoryProvider.newFactory(
			ContinuousMotionStreamInternalFactory.class, ContinuousMotionStreamInternalImpl.class));
		bind(ContinuousMotionStreamFactory.class).to(ContinuousMotionStreamFactoryImpl.class);
	}

}
