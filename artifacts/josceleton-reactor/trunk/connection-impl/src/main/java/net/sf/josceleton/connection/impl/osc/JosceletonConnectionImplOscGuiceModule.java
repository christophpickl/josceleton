package net.sf.josceleton.connection.impl.osc;

import net.sf.josceleton.commons.reflect.ClassAdapter;
import net.sf.josceleton.commons.reflect.ClassAdapterImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryProvider;
import com.illposed.osc.OSCPortIn;

public class JosceletonConnectionImplOscGuiceModule extends AbstractModule {

	@Override protected final void configure() {
		
		bind(OscMessageTransformer.class).to(OscMessageTransformerImpl.class).in(Scopes.SINGLETON);
		bind(OscPortFactory.class).toProvider(FactoryProvider.newFactory(OscPortFactory.class, OscPortImpl.class));
		bind(OscPortOpener.class).to(OscPortOpenerImpl.class).in(Scopes.SINGLETON);
		
	}

	@Provides @Singleton public final ClassAdapter<OSCPortIn> getOscPortInClassAdapter() {
	  return new ClassAdapterImpl<OSCPortIn>(OSCPortIn.class);
	}

}
