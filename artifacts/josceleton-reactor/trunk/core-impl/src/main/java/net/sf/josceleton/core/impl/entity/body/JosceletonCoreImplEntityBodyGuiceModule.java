package net.sf.josceleton.core.impl.entity.body;

import net.sf.josceleton.core.api.entity.joint.Skeleton;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryProvider;

/**
 * @since 0.4
 */
public class JosceletonCoreImplEntityBodyGuiceModule extends AbstractModule {

	/**
	 * @since 0.4
	 */
	@Override protected final void configure() {
		bind(Skeleton.class).to(SkeletonImpl.class);
		bind(SkeletonFactory.class).toProvider(
				FactoryProvider.newFactory(SkeletonFactory.class, SkeletonImpl.class));
	}
	
}
