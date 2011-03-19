package net.sf.josceleton.commons.test.util;

import java.lang.reflect.Constructor;

import net.sf.josceleton.commons.exception.InvalidArgumentException;

public final class TestTypeUtil {
	
	private TestTypeUtil() {
		// utility classes should not be instantiable
	}
	
	public static boolean hasSingleConstructorWithVisibility(final Class<?> clazz, final int visibilityModifier) {
		final Constructor<?>[] constructors = clazz.getConstructors();
		if(constructors.length != 0) { return false; }
		
		final Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
		if(declaredConstructors.length != 1) { return false; }
		
		final Constructor<?> singleDeclaredConstructor = declaredConstructors[0];
		
//		if(Modifier.isPrivate(singleDeclaredConstructor.getModifiers()) == false) { return false; }
		final boolean isVisibilityConform =
			(singleDeclaredConstructor.getModifiers() & visibilityModifier) == visibilityModifier;
		if(isVisibilityConform == false) { return false; }
		
		return true;
	}

	public static void breakUpAndInvokeUnvisibleNullifiedConstructor(
			final Class<?> clazz,
			final Constructor<?> constructor) {
		
		if(constructor.getParameterTypes().length != 0) {
			throw InvalidArgumentException.newInstance("constructor.parameterTypes.length",
				Integer.valueOf(constructor.getParameterTypes().length), "!= 0");
		}
		constructor.setAccessible(true);
		
		try {
			constructor.newInstance();
		} catch (final Exception e) {
			throw new RuntimeException("WARNING: could not create instance for class [" + clazz.getName() + "]!", e);
		}
	}
	
}
