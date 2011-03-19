package net.sf.josceleton.commons.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import net.sf.josceleton.commons.test.util.EnumTestUtil;

import org.testng.annotations.Test;

public abstract class AbstractEnumTest<E extends Enum<?>> {
	

	public static class EnumValueOfDescriptor<C> {
		public EnumValueOfDescriptor(final Class<C> clazz, final Object... pairwisedNameAndInstance) {
			this.clazz = clazz;
			this.pairwisedNameAndInstance = pairwisedNameAndInstance;
		}
		private Class<C> clazz;
		private Object[] pairwisedNameAndInstance;
	}

	protected abstract EnumValueOfDescriptor<E> getValueOfDescriptor();

	/** Shorthand for e.g.: "assertThat(Direction.valueOf("X"), equalTo(Direction.X));" */
	@SuppressWarnings({ "unchecked", "synthetic-access" })
	@Test
	public final void testValueOf() throws Exception {
		final EnumValueOfDescriptor<E> descriptor = this.getValueOfDescriptor();
		final Object[] pairs = descriptor.pairwisedNameAndInstance;
		final int n = pairs.length / 2;
		for (int i = 0; i < n; i++) {
			final String name = (String) pairs[i * 2];
			final E en = (E) pairs[i * 2 + 1];
			
			final E returnedEnum = (E) descriptor.clazz.getMethod("valueOf", String.class).invoke(null, name);
			assertThat(returnedEnum, equalTo(en));
		}
	}

	
	protected abstract EnumValuesDescriptor<E> getValuesDescriptor();
	
	@SuppressWarnings("synthetic-access")
	@Test
	public final void testEnumValues() throws Exception {
		final EnumValuesDescriptor<E> descriptor = this.getValuesDescriptor();
		EnumTestUtil.testValuesMethod(descriptor.clazz, descriptor.values);
	}

	public static class EnumValuesDescriptor<C extends Enum<?>> {
		public EnumValuesDescriptor(final Class<C> clazz, final Object... values) {
			this.clazz = clazz;
			this.values = values;
		}
		private Class<C> clazz;
		private Object[] values;
	}
}
