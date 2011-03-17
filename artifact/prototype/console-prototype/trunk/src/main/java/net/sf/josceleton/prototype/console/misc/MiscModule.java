package net.sf.josceleton.prototype.console.misc;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryProvider;

public class MiscModule extends AbstractModule {

	@Override
	protected void configure() {
		this.bind(GlueCodeFactory.class).toProvider(
				FactoryProvider.newFactory(GlueCodeFactory.class, GlueCodeImpl.class));
	}

}
