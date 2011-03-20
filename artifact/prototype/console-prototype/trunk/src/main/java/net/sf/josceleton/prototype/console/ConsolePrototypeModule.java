package net.sf.josceleton.prototype.console;

import net.sf.josceleton.JosceletonGuiceModule;
import net.sf.josceleton.prototype.console.glue.GlueModule;
import net.sf.josceleton.prototype.console.notification.GrowlModule;
import net.sf.josceleton.prototype.console.view.ViewModule;

import com.google.inject.AbstractModule;

public class ConsolePrototypeModule extends AbstractModule {
	
	
	@Override final protected void configure() {
		
		install(new JosceletonGuiceModule());
		
		install(new GrowlModule());
		install(new GlueModule());
		install(new ViewModule());
		
		
	}

}
