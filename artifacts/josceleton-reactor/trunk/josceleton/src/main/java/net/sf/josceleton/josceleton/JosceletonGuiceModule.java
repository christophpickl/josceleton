package net.sf.josceleton.josceleton;

import net.sf.josceleton.commons.reflect.CommonsReflectGuiceModule;
import net.sf.josceleton.connection.impl.ConnectionImplGuiceModule;
import net.sf.josceleton.connection.impl.osc.ConnectionImplOscGuiceModule;
import net.sf.josceleton.connection.impl.service.motion.ConnectionImplServiceMotionGuiceModule;
import net.sf.josceleton.connection.impl.service.user.ConnectionImplServiceUserGuiceModule;
import net.sf.josceleton.core.impl.entity.CoreImplEntityGuiceModule;
import net.sf.josceleton.core.impl.entity.body.CoreImplEntityBodyGuiceModule;
import net.sf.josceleton.core.impl.entity.message.CoreImplEntityMessageGuiceModule;

import com.google.inject.AbstractModule;

/**
 * Contains all <a href="http://code.google.com/p/google-guice/" target="_blank">Google Guice</a> submodules.
 * 
 * Internally uses the {@link #install(com.google.inject.Module)} method to provide access to all Josceleton classes.
 * 
 * @since 0.2
 */
public class JosceletonGuiceModule extends AbstractModule { // NO extends PrivateModule; dont limit access to internals.
	
	@Override protected final void configure() {
		// "bottom to top" ordering of modules:
		
		this.install(new CommonsReflectGuiceModule());
		
		this.install(new CoreImplEntityBodyGuiceModule());
		this.install(new CoreImplEntityMessageGuiceModule());
		this.install(new CoreImplEntityGuiceModule());
		
		this.install(new ConnectionImplOscGuiceModule());
		this.install(new ConnectionImplServiceMotionGuiceModule());
		this.install(new ConnectionImplServiceUserGuiceModule());
		this.install(new ConnectionImplGuiceModule());
	}

}
