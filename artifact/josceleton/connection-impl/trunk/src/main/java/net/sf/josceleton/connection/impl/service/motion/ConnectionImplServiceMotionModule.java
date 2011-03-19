package net.sf.josceleton.connection.impl.service.motion;

import net.sf.josceleton.connection.api.service.motion.MotionSupplierFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.assistedinject.FactoryProvider;

/**
 * @since 0.3
 */
public class ConnectionImplServiceMotionModule extends AbstractModule {

	@Override protected final void configure() {
		bind(MotionSupplierFactory.class).to(MotionSupplierFactoryImpl.class).in(Scopes.SINGLETON);
		bind(MotionSupplierInternalFactory.class).toProvider(
				FactoryProvider.newFactory(MotionSupplierInternalFactory.class, MotionSupplierImpl.class));
	}

}
