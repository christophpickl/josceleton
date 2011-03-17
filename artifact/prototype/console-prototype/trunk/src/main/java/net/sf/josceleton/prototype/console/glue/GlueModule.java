package net.sf.josceleton.prototype.console.glue;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryProvider;

public class GlueModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(GlueCodeFactory.class).toProvider(
				FactoryProvider.newFactory(GlueCodeFactory.class, GlueCodeImpl.class));
		
		bind(ConsolePresenterFactory.class).toProvider(
				FactoryProvider.newFactory(ConsolePresenterFactory.class, ConsolePresenterImpl.class));
	}

}
