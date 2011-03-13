package net.sf.josceleton.commons.reflect;

import java.lang.reflect.Constructor;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

/**
 * Briding Java's reflection classes to Josceleton's pure interface world ;)
 */
public class ClassAdapterImpl<T> implements ClassAdapter<T> {
	
	private static final long serialVersionUID = 5544384595420106807L;

	public static final ClassAdapterImpl<String> STRING = new ClassAdapterImpl<String>(String.class);
	
	private final Class<T> innerClass;

	
	@Inject public ClassAdapterImpl(@Assisted final Class<T> innerClass) {
		this.innerClass = innerClass;
	}
	
	// FIXME @Deprecated avoid static factory method ClassAdapterImpl.create()
	public static <X> ClassAdapterImpl<X> create(final Class<X> innerClass) {
		return new ClassAdapterImpl<X>(innerClass);
	}
	
	/** {@inheritDoc} from {@link ClassAdapter} */
	@Override public final boolean isPrimitive() {
		return this.innerClass.isPrimitive();
	}

	@Override public final boolean isAssignableFrom(final ClassAdapter<?> targetType) {
		return this.innerClass.isAssignableFrom(targetType.getInnerClass());
	}

	@Override public final Class<T> getInnerClass() {
		return this.innerClass;
	}

	@Override public final Constructor<?>[] getConstructors() {
		return this.innerClass.getConstructors();
	}

	@Override public final String getName() {
		return this.innerClass.getName();
	}
	
	@Override public String toString() {
		return "ClassAdapterImpl[innerClass.name=" + this.innerClass.getName() + "]";
	}
}
