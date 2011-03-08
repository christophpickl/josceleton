package net.sf.josceleton.josceleton;

import net.sf.josceleton.commons.reflect.CommonsReflectGuiceModule;

import com.google.inject.AbstractModule;

public class JosceletonGuiceModule
	extends AbstractModule { // NO extends PrivateModule; we dont want to artificially limit access to internals 
	
	@Override protected final void configure() {
		this.install(
			new CommonsReflectGuiceModule()
			// FIXME finish list of guice modules
		);
	}

}
