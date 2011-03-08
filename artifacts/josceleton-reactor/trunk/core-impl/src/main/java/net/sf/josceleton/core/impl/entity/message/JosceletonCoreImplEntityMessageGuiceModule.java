package net.sf.josceleton.core.impl.entity.message;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryProvider;

public class JosceletonCoreImplEntityMessageGuiceModule extends AbstractModule {

	@Override protected final void configure() {
		
		this.bind(JointMessageFactory.class).toProvider(
			FactoryProvider.newFactory(JointMessageFactory.class, JointMessageImpl.class));
		
		this.bind(UserMessageFactory.class).toProvider(
			FactoryProvider.newFactory(UserMessageFactory.class, UserMessageImpl.class));
		
	}

}
