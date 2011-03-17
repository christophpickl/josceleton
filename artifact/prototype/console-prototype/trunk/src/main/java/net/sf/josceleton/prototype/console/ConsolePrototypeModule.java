package net.sf.josceleton.prototype.console;

import net.sf.josceleton.josceleton.JosceletonGuiceModule;
import net.sf.josceleton.prototype.console.view.UserPanelFactory;
import net.sf.josceleton.prototype.console.view.UserPanelImpl;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryProvider;

public class ConsolePrototypeModule extends AbstractModule {

	@Override final protected void configure() {
		
		install(new JosceletonGuiceModule());
		
		bind(UserPanelFactory.class).toProvider(
				FactoryProvider.newFactory(UserPanelFactory.class, UserPanelImpl.class));
		
	}

}
