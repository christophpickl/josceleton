package net.sf.josceleton.commons.reflect;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import net.sf.josceleton.commons.exception.InvalidArgumentException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@SuppressWarnings("boxing")
public class DynamicInstantiatorImplTest {
	
	private DynamicInstantiator instantiator;

	@BeforeMethod public final void setUp() {
		this.instantiator = new DynamicInstantiatorImpl();
	}

	@AfterMethod public final void tearDown() {
		this.instantiator = null;
	}
	
	static class ProperArgumentsStoreDummy { private final String arg1; private final int arg2;
		public ProperArgumentsStoreDummy(final String arg1, final int arg2) { this.arg1 = arg1; this.arg2 = arg2; } }
	
	@SuppressWarnings("synthetic-access")
	@Test
	public final void correctDummyInstantiation() {
		final String expectedArg1 = "myArg2";
		final Integer expectedArg2 = Integer.valueOf(42);
		
		final ProperArgumentsStoreDummy dummyInstance =
			this.instantiatorCreate(ProperArgumentsStoreDummy.class, expectedArg1, expectedArg2);
		
		assertThat(dummyInstance.arg1, equalTo(expectedArg1));
		assertThat(dummyInstance.arg2, equalTo(expectedArg2));
	}
	
	private <T> T instantiatorCreate(final Class<T> clazz, final Object... expectedArgs) {
		return this.instantiator.create(ClassAdapterImpl.create(clazz), expectedArgs);
	}
	
	@Test(expectedExceptions = DynamicInstantiationException.class,
		expectedExceptionsMessageRegExp = "Could not find proper constructor.*")
	public final void incorrectDummyInstantiationFails() {
		this.instantiatorCreate(ProperArgumentsStoreDummy.class, "one argument is missing");
	}

	
	static final class PrivateConstructorDummy { private PrivateConstructorDummy() { /* deliberately empty */ } }
	@Test(expectedExceptions = DynamicInstantiationException.class,
		expectedExceptionsMessageRegExp = "Could not find proper constructor.*")
	
	public final void privateDummyInstantiationFails() {
		this.instantiatorCreate(PrivateConstructorDummy.class);
	}

	
	static class ExceptionThrowingDummy { public ExceptionThrowingDummy() {
			throw new RuntimeException("artificial dummy exception to fail constructor"); } }
	
	@Test(expectedExceptions = DynamicInstantiationException.class,
		expectedExceptionsMessageRegExp = "Could not create instance for class.*")
	public final void exceptionalDummyInstantiationFails() {
		this.instantiatorCreate(ExceptionThrowingDummy.class);
	}

	
	static class ArgumentValidatingDummy {
		public ArgumentValidatingDummy(final int notBelowZero) {
			if (notBelowZero < 0) { throw new IllegalArgumentException("notBelowZero < 0"); }
		}
	}
	
	
	@Test(expectedExceptions = DynamicInstantiationException.class,
		expectedExceptionsMessageRegExp = "Could not create instance for class.*")
	public final void argumentValidatingDummyInstantiationFails() {
		this.instantiatorCreate(ArgumentValidatingDummy.class, -13);
	}

	
	@Test(expectedExceptions = InvalidArgumentException.class,
			expectedExceptionsMessageRegExp = ".*null.*")
	public final void passingAnyNullArgumentFails() {
		this.instantiatorCreate(ProperArgumentsStoreDummy.class, null, 1337);
	}
	

	@SuppressWarnings("unused") static class NearlySameConstructorsDummy {
		public NearlySameConstructorsDummy(final Double arg1) { /* deliberately empty */ }
		public NearlySameConstructorsDummy(final Integer arg1, final Integer arg2) { /* deliberately empty */ }
		public NearlySameConstructorsDummy(final String arg1, final String arg2b) { /* deliberately empty */ }
	
//		does not work!!! public NearlySameConstructorsDummy(final String arg1, final boolean arg2a) { /* empty */ }
		public NearlySameConstructorsDummy(final String arg1, final /* capital B*/ Boolean arg2a) { /* empty */ }
	}
	
	/** To further increase branch coverage rate */
	@Test public final void nearlySameConstructors() {
		this.instantiatorCreate(NearlySameConstructorsDummy.class, "foo", false);
	}
	
}
