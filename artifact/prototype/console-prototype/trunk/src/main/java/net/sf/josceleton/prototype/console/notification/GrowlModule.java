package net.sf.josceleton.prototype.console.notification;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryProvider;

public class GrowlModule extends AbstractModule {

	@Override protected final void configure() {
		this.bind(GrowlNotifierFactory.class).toProvider(
				FactoryProvider.newFactory(GrowlNotifierFactory.class, GrowlNotifierImpl.class));
	}

}
