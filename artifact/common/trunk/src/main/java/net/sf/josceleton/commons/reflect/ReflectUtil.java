package net.sf.josceleton.commons.reflect;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @since 0.1
 */
public final class ReflectUtil {
	
	private static final Map<Class<?>, Class<?>> PRIMITIVES_TO_BOXED;
	static {
		final Map<Class<?>, Class<?>> tmp = new HashMap<Class<?>, Class<?>>();
		tmp.put(Boolean.TYPE, Boolean.class);
		tmp.put(Float.TYPE, Float.class);
		tmp.put(Double.TYPE, Double.class);
		tmp.put(Byte.TYPE, Byte.class);
		tmp.put(Character.TYPE, Character.class);
		tmp.put(Integer.TYPE, Integer.class);
		tmp.put(Long.TYPE, Long.class);
		PRIMITIVES_TO_BOXED = Collections.unmodifiableMap(tmp);
	}
	
	private ReflectUtil() { /* utility class should not be instantiable */ }

	/**
	 * @since 0.1
	 */
	@SuppressWarnings("unchecked")
	public static <T> Constructor<T> findConstructor(final ClassAdapter<T> clazz, final Object[] arguments) {
		final Constructor<T>[] constructors = (Constructor<T>[]) clazz.getConstructors();
		
		for (final Constructor<T> currentConstructor : constructors) {
			
			final Class<?>[] parameterTypes = currentConstructor.getParameterTypes();
			if(ReflectUtil.typesAreCompatible(ReflectUtil.adapt(parameterTypes), arguments) == true) {
				// there should be only one valid constructor, so returning immediately is just fine
				return currentConstructor;
			}
		}
		
		throw DynamicInstantiationException.newForNotFoundConstructor(clazz, arguments);
	}

	private static ClassAdapter<?>[] adapt(final Class<?>[] raw) {
		final ClassAdapter<?>[] result = new ClassAdapter<?>[raw.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = ClassAdapterImpl.create(raw[i]);
		}
		return result;
	}
	
	private static boolean typesAreCompatible(final ClassAdapter<?>[] parameterTypes, final Object[] givenArguments) {
		if(parameterTypes.length != givenArguments.length) {
			return false;
		}
		for (int i = 0; i < parameterTypes.length; i++) {
			final ClassAdapter<?> parameterType = parameterTypes[i];
			final Class<?> argumentClass = givenArguments[i].getClass();
			final ClassAdapter<?> argumentType = ClassAdapterImpl.create(argumentClass);
			if(ReflectUtil.isAssignable(argumentType, parameterType) == false) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Enhances common {@link Class#isAssignableFrom(Class)} with additional primitive type checks.
	 * 
	 * @param typeUnderInspection is the type under inspection which will be checked for assignment.
	 * @param superType reference type to check <code>typeUnderInspection</code> against.
	 * @return true if <code>typeUnderInspection</code> is of the same type or a sub-type of <code>superType</code>.
	 * @since 0.1
	 */
	public static boolean isAssignable(final ClassAdapter<?> typeUnderInspection, final ClassAdapter<?> superType) {
		if(superType.isPrimitive() == false) {
			return superType.isAssignableFrom(typeUnderInspection);
		}
		
		final Class<?> boxedSourceType = PRIMITIVES_TO_BOXED.get(superType.getInnerClass());
		if(boxedSourceType == null) {
			throw new RuntimeException("Could not find assignable for primitive type [" + superType + "]!");
		}
		
		return boxedSourceType == typeUnderInspection.getInnerClass();
	}
}
