package net.sf.josceleton.josceleton;

import net.sf.josceleton.commons.CommonsModule;
import net.sf.josceleton.connection.impl.ConnectionImplModule;
import net.sf.josceleton.core.api.CoreApiModule;
import net.sf.josceleton.core.impl.CoreImplModule;
import net.sf.josceleton.motion.impl.MotionImplModule;

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
		this.install(new CommonsModule());
		this.install(new CoreApiModule());
		this.install(new CoreImplModule());
		this.install(new ConnectionImplModule());
		this.install(new MotionImplModule());
		
	}

}
