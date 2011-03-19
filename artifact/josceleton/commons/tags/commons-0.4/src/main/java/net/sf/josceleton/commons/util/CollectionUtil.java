package net.sf.josceleton.commons.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @since 0.
 */
public final class CollectionUtil {

	private CollectionUtil() {
		// utility class is not instantiable
	}
	
	public static <T> Set<T> toUnmodifiableSet(final T... items) {
		if(items.length == 0) {
			return Collections.emptySet();
		}
		return Collections.unmodifiableSet(new HashSet<T>(Arrays.asList(items)));
	}

	public static <T> Set<T> mergeToUnmodifiableSet(final T item, final T[] optionallyMoreItems) {
		final Set<T> set = new HashSet<T>(1 + optionallyMoreItems.length);
		
		set.add(item);
		for (final T optionally : optionallyMoreItems) {
			set.add(optionally);
		}
		
		return Collections.unmodifiableSet(set);
	}

}
