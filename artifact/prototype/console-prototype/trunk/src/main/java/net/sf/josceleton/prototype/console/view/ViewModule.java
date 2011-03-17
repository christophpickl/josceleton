package net.sf.josceleton.prototype.console.view;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryProvider;

public class ViewModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(UserPanelFactory.class).toProvider(
				FactoryProvider.newFactory(UserPanelFactory.class, UserPanelImpl.class));
	}

}
