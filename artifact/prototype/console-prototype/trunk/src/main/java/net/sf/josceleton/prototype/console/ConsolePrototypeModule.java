package net.sf.josceleton.prototype.console;

import net.sf.josceleton.josceleton.JosceletonGuiceModule;
import net.sf.josceleton.prototype.console.misc.MiscModule;
import net.sf.josceleton.prototype.console.notification.GrowlNotificationModule;
import net.sf.josceleton.prototype.console.view.UserPanelFactory;
import net.sf.josceleton.prototype.console.view.UserPanelImpl;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryProvider;

public class ConsolePrototypeModule extends AbstractModule {
	
	
	@Override final protected void configure() {
		
		install(new JosceletonGuiceModule());
		install(new GrowlNotificationModule());
		
		install(new MiscModule());
		
		bind(UserPanelFactory.class).toProvider(
				FactoryProvider.newFactory(UserPanelFactory.class, UserPanelImpl.class));
		
	}

}
