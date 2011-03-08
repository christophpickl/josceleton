package net.sf.josceleton.connection.impl.osc;

import net.sf.josceleton.commons.reflect.ClassAdapter;
import net.sf.josceleton.commons.reflect.ClassAdapterImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.assistedinject.FactoryProvider;
import com.illposed.osc.OSCPortIn;

public class ConnectionImplOscGuiceModule extends AbstractModule {

	@Override protected final void configure() {
		
		bind(OscMessageTransformer.class).to(OscMessageTransformerImpl.class).in(Scopes.SINGLETON);
		bind(OscPortFactory.class).toProvider(FactoryProvider.newFactory(OscPortFactory.class, OscPortImpl.class));
		
		final ClassAdapter<OSCPortIn> oscPortInType = ClassAdapterImpl.create(OSCPortIn.class);
		bind(ClassAdapter.class).annotatedWith(OSCPortInAdapter.class).toInstance(oscPortInType);
		bind(OscPortOpener.class).to(OscPortOpenerImpl.class).in(Scopes.SINGLETON);
		
	}

}
