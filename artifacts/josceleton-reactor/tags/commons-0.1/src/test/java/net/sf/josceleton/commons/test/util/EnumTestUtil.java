package net.sf.josceleton.commons.test.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matchers;

public final class EnumTestUtil {
	// MINOR @TEST REFACTOR EnumTestUtil to hamcrest matcher

	private EnumTestUtil() { /* utility class*/ }

	@SuppressWarnings("boxing")
	public static void testValuesMethod(final Class<?> enumClass, final Object... expectedValues) throws Exception {
		final Object[] values = (Object[]) enumClass.getMethod("values").invoke(null);
		
		assertThat(values.length, equalTo(expectedValues.length));
		
		for (int i = 0; i < expectedValues.length; i++) {
			assertThat(values, Matchers.hasItemInArray(expectedValues[i]));
		}
	}
	
}
