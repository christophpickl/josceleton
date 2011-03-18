package net.sf.josceleton.commons;

import net.sf.josceleton.commons.reflect.CommonsReflectModule;

import com.google.inject.AbstractModule;

/**
 * @since 0.4
 */
public class CommonsModule extends AbstractModule {

	@Override protected final void configure() {
		this.install(new CommonsReflectModule());
	}

}
