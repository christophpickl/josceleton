package net.sf.josceleton.core.api.async;

import net.sf.josceleton.core.api.test.DummyListener;

import org.testng.annotations.Test;

public abstract class AsyncTest<A extends Async<? super DummyListener>> {
	
	protected abstract A createTestee();
	
	protected abstract void assertListenersCount(final A async, final int expectedCount);
	
	@Test public final void abstractAsyncTestAddingAndRemovingAndCounting() {
		final A async = this.createTestee();
		this.assertListenersCount(async, 0);
		
		async.addListener(new DummyListener());
		this.assertListenersCount(async, 1);
		
		final DummyListener yetStored = new DummyListener();
		async.addListener(yetStored);
		this.assertListenersCount(async, 2);
		async.addListener(yetStored);
		this.assertListenersCount(async, 2);
		
		async.removeListener(yetStored);
		async.removeListener(yetStored);
		this.assertListenersCount(async, 1);
	}
	
}
