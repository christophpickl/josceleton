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
		return Collections.unmodifiableSet(new HashSet<T>(Arrays.asList(items)));
	}

	public static <T> Set<T> mergeToUnmodifiableSet(final T item, final T[] optionallyMoreItems) {
		if(optionallyMoreItems.length == 0) {
			return CollectionUtil.toUnmodifiableSet(item);
		}
		final T[] newArray = (T[]) new Object[optionallyMoreItems.length + 1];
		newArray[0] = item;
		System.arraycopy(optionallyMoreItems, 0, newArray, 1, optionallyMoreItems.length);
		return CollectionUtil.toUnmodifiableSet(newArray);
		
		// TODO outsource merging 1+array to collection
//		final Set<Joint> allJoints = new HashSet<Joint>(1 + optionallyMoreJoints.length);
//		allJoints.add(atLeastOneJoint);
//		allJoints.addAll(Arrays.asList(optionallyMoreJoints));
//		Collections.unmodifiableSet(allJoints);
	}

}
