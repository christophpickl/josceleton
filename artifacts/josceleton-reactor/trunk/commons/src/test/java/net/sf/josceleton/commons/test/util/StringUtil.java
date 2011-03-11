package net.sf.josceleton.commons.test.util;

public final class StringUtil {
	
	private StringUtil() {
		// utility classes should not be instantiable
	}
	
	public static String toStringClassTypeNames(final Class<?>[] classes) {
		final StringBuilder builder = new StringBuilder();
		builder.append("[");
		boolean first = true;
		for (final Class<?> clazz : classes) {
			if(first == false) {
				builder.append(", ");
			}
			first = false;
			builder.append(clazz.getSimpleName());
		}
		builder.append("]");
		return builder.toString();
	}
	
	public static String toStringObjectsWithType(final Object[] objects) {
		final StringBuilder builder = new StringBuilder();
		builder.append("[");
		boolean first = true;
		for (int i = 0; i < objects.length; i++) {
			final Object object = objects[i];
			if(first == false) {
				builder.append(" ");
			}
			first = false;
			builder.append((i + 1) + ". " + object.getClass().getSimpleName() + " [" + object + "]");
		}
		builder.append("].length=" + objects.length);
		return builder.toString();
	}
}
