package net.sf.josceleton.commons.reflect;

public interface ClassAdapterFactory {
	
	<T> ClassAdapter<T> create(Class<T> innerClass);
	
}
