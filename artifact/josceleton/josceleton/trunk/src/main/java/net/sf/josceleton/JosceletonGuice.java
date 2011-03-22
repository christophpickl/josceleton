package net.sf.josceleton;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;

/**
 * @since 0.5
 */
public final class JosceletonGuice {
	
	private JosceletonGuice() {
		// no instantiation for utility classes
	}
	
	/**
	 * @since 0.5
	 */
	public static Module newModule() {
		return new JosceletonModule();
	}
	
	/**
	 * @since 0.5
	 * @see {@link http://google-guice.googlecode.com/svn/trunk/latest-javadoc/com/google/inject/util/Modules.html}
	 */
	public static Module newModuleOverriddenBy(final Module... modules) {
		return Modules.override(JosceletonGuice.newModule()).with(modules);
	}
	
	/**
	 * Handy utility method only, creating a new "instance realm" and should only be invoked once.
	 * 
	 * @since 0.5
	 */
	public static Injector newInjector() {
		return Guice.createInjector(JosceletonGuice.newModule());
	}

	/**
	 * @since 0.5
	 */
	public static Injector newInjectorOverriddenBy(final Module... modules) {
		return Guice.createInjector(JosceletonGuice.newModuleOverriddenBy(modules));
	}
	
}
