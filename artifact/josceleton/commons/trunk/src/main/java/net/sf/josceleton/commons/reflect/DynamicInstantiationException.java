package net.sf.josceleton.commons.reflect;

import java.util.Arrays;

import net.sf.josceleton.commons.exception.JosceletonException;

/**
 * @since 0.1
 */
public class DynamicInstantiationException extends JosceletonException {

	private static final long serialVersionUID = -4612520808805464973L;
	
	
	private final ClassAdapter<?> clazz;
	
	private final Object[] arguments;
	
	
	protected DynamicInstantiationException(
			final String message,
			final ClassAdapter<?> clazz,
			final Object[] arguments,
			final Throwable cause) {
		super(message, cause);
		
		this.clazz = clazz;
		this.arguments = arguments;
	}

	/**
	 * @param clazz tried, but failed, to instantiate.
	 * @since 0.1
	 */
	public static DynamicInstantiationException newForInstantiation(
			final ClassAdapter<?> clazz,
			final Object[] arguments,
			final Throwable cause) {
		return new DynamicInstantiationException(
			"Could not create instance for class [" + clazz.getName() + "] " +
				"with arguments " +  Arrays.toString(arguments) + "!",
			clazz, arguments, cause
		);
	}

	/**
	 * @param clazz which did not have a proper constructor for given arguments.
	 * @since 0.1
	 */
	public static DynamicInstantiationException newForNotFoundConstructor(
			final ClassAdapter<?> clazz,
			final Object[] arguments) {
		return new DynamicInstantiationException(
			"Could not find proper constructor for class [" + clazz.getName() + "] " +
				"with arguments " + Arrays.toString(arguments) + "!",
			clazz, arguments, null // no cause
		);
	}

	/**
	 * @since 0.1
	 */
	public final ClassAdapter<?> getClazz() {
		return this.clazz;
	}

	/**
	 * @since 0.1
	 */
	public final Object[] getArguments() {
		final Object[] copiedArguments = new Object[this.arguments.length];
		System.arraycopy(this.arguments, 0, copiedArguments, 0, this.arguments.length);
		return copiedArguments;
	}

}
