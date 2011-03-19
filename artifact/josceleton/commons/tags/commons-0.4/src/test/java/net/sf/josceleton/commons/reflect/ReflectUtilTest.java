package net.sf.josceleton.commons.reflect;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import net.sf.josceleton.commons.test.jmock.ClassMockery;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@SuppressWarnings("boxing")
public class ReflectUtilTest {
	
	@DataProvider(name = "provideAssignablePrimitiveTypes")
	public final Object[][] provideAssignablePrimitiveTypes() {
		return new Object[][] {
			new Object[] { Boolean.class, Boolean.TYPE },
			new Object[] { Byte.class, Byte.TYPE },
			new Object[] { Character.class, Character.TYPE },
			new Object[] { Integer.class, Integer.TYPE },
			new Object[] { Long.class, Long.TYPE },
			new Object[] { Float.class, Float.TYPE },
			new Object[] { Double.class, Double.TYPE }
			// MINOR ??? @TEST reflect necessary for arrays as well? how to handle them?
		};
	}
	@Test(dataProvider = "provideAssignablePrimitiveTypes")
	public final <A, B> void assignablePrimitiveTypesReturnTrue(
			final Class<B> typeUnderInspection, final Class<A> superType) {
		assertThat(
			ReflectUtil.isAssignable(
				ClassAdapterImpl.create(typeUnderInspection),
				ClassAdapterImpl.create(superType)
			),
			equalTo(true));
	}
	
	
	@DataProvider(name = "provideNonAssignablePrimitiveTypes")
	public final Object[][] provideNonAssignablePrimitiveTypes() {
		return new Object[][] {
			new Object[] { Boolean.class, Integer.TYPE },
			new Object[] { Boolean.class, Float.TYPE },
			new Object[] { Long.class, Integer.TYPE },
			new Object[] { Integer.class, Long.TYPE },
			new Object[] { Double.class, Float.TYPE },
			new Object[] { Float.class, Double.TYPE }
		};
	}
	@Test(dataProvider = "provideNonAssignablePrimitiveTypes")
	public final <A, B> void nonAssignablePrimitiveTypesReturnFalse(
			final Class<A> typeUnderInspection, final Class<B> superType) {
		assertThat(
			ReflectUtil.isAssignable(
				ClassAdapterImpl.create(typeUnderInspection),
				ClassAdapterImpl.create(superType)
			),
			equalTo(false));
	}
	
	@Test(expectedExceptions = RuntimeException.class,
		expectedExceptionsMessageRegExp = "Could not find assignable for primitive type \\[classAdapter\\]!")
	public final void isAssignableForArtificialPrimitiveFails() {
		final Mockery mockery = new ClassMockery();
		final ClassAdapter<?> mockedPrimitive = mockery.mock(ClassAdapter.class);
		mockery.checking(new Expectations() { {
			oneOf(mockedPrimitive).isPrimitive();
			will(returnValue(true));
			
			oneOf(mockedPrimitive).getInnerClass();
			will(returnValue(Object.class/* something which is not a primitive class */));
		}});
		ReflectUtil.isAssignable(ClassAdapterImpl.STRING, mockedPrimitive); // String ... just something
	}

	
}
