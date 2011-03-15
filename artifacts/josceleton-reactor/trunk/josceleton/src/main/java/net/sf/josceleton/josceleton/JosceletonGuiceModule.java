package net.sf.josceleton.josceleton;

import net.sf.josceleton.commons.reflect.JosceletonCommonsReflectGuiceModule;
import net.sf.josceleton.connection.impl.JosceletonConnectionImplGuiceModule;
import net.sf.josceleton.connection.impl.osc.JosceletonConnectionImplOscGuiceModule;
import net.sf.josceleton.connection.impl.service.user.JosceletionConnectionImplServiceUserGuiceModule;
import net.sf.josceleton.core.impl.entity.JosceletonCoreImplEntityGuiceModule;
import net.sf.josceleton.core.impl.entity.message.JosceletonCoreImplEntityMessageGuiceModule;

import com.google.inject.AbstractModule;

/**
 * Contains all <a href="http://code.google.com/p/google-guice/" target="_blank">Google Guice</a> submodules.
 * 
 * Internally uses the {@link #install(com.google.inject.Module)} method to provide access to all Josceleton classes.
 * 
 * @since 0.2
 */
public class JosceletonGuiceModule
	extends AbstractModule { // NO extends PrivateModule ... we dont want to artificially limit access to internals 
	
	@Override protected final void configure() {
		
		this.install(new JosceletonCommonsReflectGuiceModule());
		
		this.install(new JosceletonCoreImplEntityMessageGuiceModule());
		this.install(new JosceletonCoreImplEntityGuiceModule());
		
		this.install(new JosceletonConnectionImplOscGuiceModule());
		this.install(new JosceletionConnectionImplServiceUserGuiceModule());
		this.install(new JosceletonConnectionImplGuiceModule());
		
	}

}
