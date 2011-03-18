package net.sf.josceleton.commons.reflect;

/**
 * Comes in handy for (unit) testing if you want to mock out instantiation of third party classes.
 * 
 * This type especially solves the problem of third party classes' constructor executing some logic.
 * 
 * @since 0.1
 */
public interface DynamicInstantiator {
	
	/**
	 * @throws DynamicInstantiationException
	 * @since 0.1
	 */
	<T> T create(ClassAdapter<T> clazz, Object... arguments);
	
}
