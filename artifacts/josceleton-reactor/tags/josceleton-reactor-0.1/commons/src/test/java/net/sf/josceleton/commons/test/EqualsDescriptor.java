package net.sf.josceleton.commons.test;

public class EqualsDescriptor<E> {
	
	private final E sameA;
	
	private final E sameB;

	private final E[] differents;

	
	public EqualsDescriptor(final E sameA, final E sameB, final E... differents) {
		this.sameA = sameA;
		this.sameB = sameB;
		this.differents = differents;
	}
	
	public final E getSameA() {
		return this.sameA;
	}
	
	public final E getSameB() {
		return this.sameB;
	}

	public final E[] getDifferents() {
		return this.differents;
	}

}
