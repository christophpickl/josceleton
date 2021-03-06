package net.sf.josceleton.commons.test.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.hamcrest.Matcher;

// LUXURY @TEST REFACTOR TestUtil methods as a custom matcher

public final class TestUtil {
	
	private TestUtil() { /* utility class*/ }
	
	public static int countIterable(final Iterable<?> iterable) {
		final Iterator<?> it = iterable.iterator();
		int count = 0;
		
		while(it.hasNext() == true) {
			it.next();
			count++;
		}
		
		return count;
	}

	// LUXURY @TEST MOCK possible to mock toString()?
	//					 can not mock toString, even not with {{ setImposteriser(ClassImposteriser.INSTANCE); }};
	@SuppressWarnings("unchecked")
	public static <T> void assertObjectToString(final T type, final String... subParts) {
		final List<Matcher<String>> list = new LinkedList<Matcher<String>>();
		list.add(containsString(type.getClass().getSimpleName()));
		for (int i = 0; i < subParts.length; i++) {
			list.add(containsString(subParts[i]));
		}
		
		final Matcher<String>[] matchers = (Matcher<String>[]) list.toArray(new Matcher<?>[subParts.length]);
		assertThat(type.toString(), allOf(matchers));
	}
	
}
