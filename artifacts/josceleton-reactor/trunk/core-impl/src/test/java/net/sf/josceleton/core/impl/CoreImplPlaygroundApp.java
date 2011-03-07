package net.sf.josceleton.core.impl;

import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.impl.entity.CoordinateFactory;
import net.sf.josceleton.core.impl.entity.CoreImplEntityGuiceModule;
import net.sf.josceleton.core.impl.entity.message.CoreImplMessageGuiceModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

class CoreImplPlaygroundApp {
	
	public static void main(final String[] args) {
		System.out.println("START");
		
		final Injector injector = Guice.createInjector(
			new CoreImplEntityGuiceModule(),
			new CoreImplMessageGuiceModule()
		);
		
		final CoreImplPlaygroundApp app = new CoreImplPlaygroundApp(injector);
		app.doCreateAndPrintCoordinate();
		
		System.out.println("END");
	}
	
	private final Injector injector;
	
	public CoreImplPlaygroundApp(final Injector injector) {
		this.injector = injector;
	}

	public void doCreateAndPrintCoordinate() {
		final CoordinateFactory factory = this.injector.getInstance(CoordinateFactory.class);
		final Coordinate coordinate = factory.create(0.1F, 0.2F, 2.2F);
		System.out.println(coordinate);
	}
}
