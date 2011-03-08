package net.sf.josceleton.commons.reflect;

import java.io.Serializable;
import java.lang.reflect.Constructor;

public interface ClassAdapter<T> extends Serializable {
	
	Class<T> getInnerClass();
	
	boolean isPrimitive();
	
	/**
	 * @see Class#isAssignableFrom(Class)
	 */
	boolean isAssignableFrom(ClassAdapter<?> targetType);

	Constructor<?>[] getConstructors(); // LUXURY @REFACTOR ClassAdapter should actually return ConstructorAdapter

	String getName();
	
}
