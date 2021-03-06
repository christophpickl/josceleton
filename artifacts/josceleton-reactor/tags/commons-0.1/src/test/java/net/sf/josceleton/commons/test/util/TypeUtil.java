package net.sf.josceleton.commons.test.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import net.sf.josceleton.commons.exception.InvalidArgumentException;

public final class TypeUtil {
	
	private TypeUtil() {
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
		
		// LUXURY @AOP CONTRACT use it to check preconditions / validating arguments
		if(constructor.getParameterTypes().length != 0) {
			throw InvalidArgumentException.newInstance("constructor.parameterTypes.length",
				Integer.valueOf(constructor.getParameterTypes().length), "!= 0");
		}
		constructor.setAccessible(true);
		
		final String instantiationErrorMessage = "WARNING: could not create instance " +
		"for class [" + clazz.getName() + "]!";
		try {
			constructor.newInstance();
		} catch (final IllegalArgumentException e) {
			throw new RuntimeException(instantiationErrorMessage, e);
		} catch (final InstantiationException e) {
			throw new RuntimeException(instantiationErrorMessage, e);
		} catch (final IllegalAccessException e) {
			throw new RuntimeException(instantiationErrorMessage, e);
		} catch (final InvocationTargetException e) {
			throw new RuntimeException(instantiationErrorMessage, e);
		}
	}
	
}
