package net.sf.josceleton.commons.reflect;

import static net.sf.josceleton.commons.test.matcher.JosceletonMatchers.hasSinglePrivateNullifiedConstructorAndInvokeIt;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.lang.reflect.Constructor;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@SuppressWarnings("boxing")
public class ReflectUtil2Test {

	@Test public final void hasSinglePrivateNullifiedConstructor() {
		assertThat(ReflectUtil.class, hasSinglePrivateNullifiedConstructorAndInvokeIt());
	}
	
	@Test(dataProvider = "provideSomeClassesToFindConstructor")
	public final void findConstructorProperly(final Class<?> constructorContainingClass, final Object[] arguments) {
		final Constructor<?> actualConstructor = ReflectUtil.findConstructor(
			ClassAdapterImpl.create(constructorContainingClass), arguments);
		
		assertThat(actualConstructor, not(nullValue()));
		assertThat(constructorContainingClass == actualConstructor.getDeclaringClass(), equalTo(true));
		
	}
	
	@DataProvider(name = "providerAssignablesForComplexTypes")
	public final Object[][] providerAssignablesForComplexTypes() {
		return new Object[][] {
			new Object[] { Object.class, Object.class },
			new Object[] { String.class, Object.class },
			new Object[] { TestParent.class, Object.class },
			new Object[] { TestInterface.class, Object.class },
			new Object[] { TestChild.class, Object.class },
			new Object[] { TestChild.class, TestParent.class },
			new Object[] { TestChild.class, TestInterface.class },
			new Object[] { TestChild.class, TestChild.class }
		};
	}
	@Test(dataProvider = "providerAssignablesForComplexTypes")
	public final void isAssignableFromForComplexTypes(final Class<?> classUnderInspection, final Class<?> superClass) {
		this.assertIsAssignable(true, classUnderInspection, superClass);
	}

	@DataProvider(name = "providerNonAssignablesForComplexTypes")
	public final Object[][] providerNonAssignablesForComplexTypes() {
		return new Object[][] {
			new Object[] { Object.class, String.class },
			new Object[] { Object.class, TestParent.class },
			new Object[] { Object.class, TestInterface.class },
			new Object[] { Object.class, TestChild.class },
			new Object[] { TestParent.class, TestChild.class },
			new Object[] { TestParent.class, TestInterface.class },
		};
	}
	@Test(dataProvider = "providerNonAssignablesForComplexTypes")
	public final void isNonAssignableFromForComplexTypes(
			final Class<?> classUnderInspection,
			final Class<?> superClass) {
		this.assertIsAssignable(false, classUnderInspection, superClass);
	}
	
	private void assertIsAssignable(
			final boolean expectedResult,
			final Class<?> classUnderInspection,
			final Class<?> superClass) {
		assertThat(
				ReflectUtil.isAssignable(
					ClassAdapterImpl.create(classUnderInspection),
					ClassAdapterImpl.create(superClass)
				), 
				equalTo(expectedResult)
			);
	}
	
	static class TestParent { /* empty */ }
	interface TestInterface { /* empty */ }
	static class TestChild extends TestParent implements TestInterface { /* empty */ }

	
	private static final String MOCKED_CLASS_NAME = "MockedClassName";

	@Test(expectedExceptions = DynamicInstantiationException.class,
			expectedExceptionsMessageRegExp = "Could not find proper constructor for class " +
					"\\[" + MOCKED_CLASS_NAME + "\\] with arguments \\[fooArg\\]!")
	public final void findConstructorButNonExistingFails() {
		final Mockery mockery = new Mockery();
		
		final ClassAdapter<?> mockedClassAdapter = this.mockClassAdapter(mockery);
		
		ReflectUtil.findConstructor(mockedClassAdapter, new Object[] { "fooArg" });
	}
	
	private ClassAdapter<?> mockClassAdapter(final Mockery mockery) {
		final Constructor<?>[] constructors = new Constructor<?>[] {}; // final Constructor can not be mocked ;(
		final ClassAdapter<?> mockedClassAdapter = mockery.mock(ClassAdapter.class);
		
		mockery.checking(new Expectations() { {
			oneOf(mockedClassAdapter).getConstructors();
			will(returnValue(constructors));
			
			allowing(mockedClassAdapter).getName();
			will(returnValue(MOCKED_CLASS_NAME));
		}});
		
		return mockedClassAdapter;
	}
	
	@SuppressWarnings("unused") static class Dummy1 { public Dummy1(final String fooArg) { /* empty */ } }
	@SuppressWarnings("unused") static class Dummy2 {
		public Dummy2(final Integer arg1) { /* empty */ }
		public Dummy2(final String arg1, final String arg2) { /* empty */ }
		public Dummy2(final String arg1, final Integer arg2) { /* empty */ }
	}
	
	@DataProvider(name = "provideSomeClassesToFindConstructor")
	public final Object[][] provideSomeClassesToFindConstructor() {
		return new Object[][] {
			providerFactory(Dummy1.class, "fooArgValue"),
			providerFactory(Dummy2.class, "fooArgValue", 42)
		};
	}
	
	private static Object[] providerFactory(final Class<?> clazz, final Object... arguments) {
		return new Object[] { clazz, arguments };
	}

}
