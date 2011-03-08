package net.sf.josceleton.commons.reflect;

public class ClassAdapterFactoryImpl implements ClassAdapterFactory {

	@Override public final <T> ClassAdapter<T> create(final Class<T> innerClass) {
		return new ClassAdapterImpl<T>(innerClass);
	}

}
