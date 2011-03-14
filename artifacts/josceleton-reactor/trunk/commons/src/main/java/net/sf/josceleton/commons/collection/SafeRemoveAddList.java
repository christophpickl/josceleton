package net.sf.josceleton.commons.collection;

import java.util.Arrays;
import java.util.LinkedList;

public class SafeRemoveAddList<E> extends LinkedList<E> {
	
	private static final long serialVersionUID = -4952366764007772589L;

	@Override public final boolean remove(final Object entity) {
		final boolean wasRemoved = super.remove(entity);
		if(wasRemoved == true) {
			return true;
		}
		
		throw new RuntimeException("Could not remove " + entity + " from list: " +
				Arrays.toString(this.toArray()));
	}
	
	@Override public final boolean add(final E entity) {
		final boolean wasAdded = super.add(entity);
		if(wasAdded == true) {
			return true;
		}
		
		throw new RuntimeException("Already added " + entity + " to list: " +
				Arrays.toString(this.toArray()));
	}
	
}
