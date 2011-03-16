package net.sf.josceleton.commons.test.matcher;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Iterator;

import net.sf.josceleton.commons.test.util.TestTypeUtil;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Checks if only one single private constructor with no arguments is declared and invokes it increasing coverage.
 */
public class JosceletonMatchers extends TypeSafeMatcher<Class<?>> {

	@Override public final boolean matchesSafely(final Class<?> clazz) {
		if(TestTypeUtil.hasSingleConstructorWithVisibility(clazz, Modifier.PRIVATE) == false) { return false; }
		
		final Constructor<?> singleDeclaredConstructor = clazz.getDeclaredConstructors()[0];
		
		if(singleDeclaredConstructor.getParameterTypes().length != 0) { return false; }
		TestTypeUtil.breakUpAndInvokeUnvisibleNullifiedConstructor(clazz, singleDeclaredConstructor);
		
		return true;
	}
	
	
	@Override public final void describeTo(final Description description) {
		description.appendText("not has a utility constructor (single zero-arg private)");
	}
	
	@Factory public static <T> Matcher<Class<?>> hasSinglePrivateNullifiedConstructorAndInvokeIt() {
		return new JosceletonMatchers();
	}
	

	@Factory public static <T> Matcher<Class<?>> hasSinglePrivateConstructor() {
		return new HasSingleConstructor(Modifier.PRIVATE);
	}

	@Factory public static <T> Matcher<Class<?>> hasSingleProtectedConstructor() {
		return new HasSingleConstructor(Modifier.PROTECTED);
	}

	@Factory public static <T> Matcher<Collection<T>> collectionHas(final T... items) {
		return new CollectionHas<T>(items);
	}
	
	private static class CollectionHas<T> extends TypeSafeMatcher<Collection<T>> {
		
		private final T[] items;
		
		private String descriptionAppend = "N/A";
		
		public CollectionHas(final T[] items) {
			this.items = items;
		}

		@Override
		public boolean matchesSafely(final Collection<T> collection) {
			if(this.items.length != collection.size()) {
				this.descriptionAppend = "length expected/actual: " + this.items.length + "/" + collection.size();
				return false;
			}
			final Iterator<T> thatIterator = collection.iterator();
			for (int i = 0; i < this.items.length; i++) {
				final Object thisI = this.items[i];
				final Object thatI = thatIterator.next();
				if(thisI.equals(thatI) == false) {
					this.descriptionAppend = "item on [" + i + "] expected/actual: " +
						thisI + "/" + thatI;
					return false;
				}
			}
			return true;
		}

		@Override
		public void describeTo(final Description description) {
			description.appendText("collection not equals (" + this.descriptionAppend + ")");
		}
		
	}
	
	private static class HasSingleConstructor extends TypeSafeMatcher<Class<?>> {
		
		private final int visibilityModifier;
		
		protected HasSingleConstructor(final int visibilityModifier) {
			this.visibilityModifier = visibilityModifier;
		}

		@Override public final boolean matchesSafely(final Class<?> clazz) {
			return TestTypeUtil.hasSingleConstructorWithVisibility(clazz, this.visibilityModifier);
		}
		
		@Override public final void describeTo(final Description description) {
			description.appendText("not has a single private constructor");
		}

	}

}
