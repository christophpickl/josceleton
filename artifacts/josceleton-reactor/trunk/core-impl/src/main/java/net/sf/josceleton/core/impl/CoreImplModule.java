package net.sf.josceleton.core.impl;

import net.sf.josceleton.core.impl.entity.CoreImplEntityModule;
import net.sf.josceleton.core.impl.entity.joint.CoreImplEntityJointModule;
import net.sf.josceleton.core.impl.entity.message.CoreImplEntityMessageModule;

import com.google.inject.AbstractModule;

/**
 * @since 0.4
 */
public class CoreImplModule extends AbstractModule {

	@Override protected final void configure() {
		this.install(new CoreImplEntityJointModule());
		this.install(new CoreImplEntityMessageModule());
		this.install(new CoreImplEntityModule());
	}

}
