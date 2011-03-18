package net.sf.josceleton.core.api.async;

import net.sf.josceleton.commons.test.jmock.AbstractMockeryTest;

import org.testng.annotations.Test;

public abstract class AsyncTest<A extends Async<Listener>> extends AbstractMockeryTest {
	
	protected abstract A createTestee();
	
	protected abstract void assertListenersCount(final A async, final int expectedCount);
	
	@Test public final void abstractAsyncTestAddingAndRemovingAndCounting() {
		final A async = this.createTestee();
		this.assertListenersCount(async, 0);
		
		async.addListener(this.mock(Listener.class, "listener1"));
		this.assertListenersCount(async, 1);
		
		final Listener yetStored = this.mock(Listener.class, "listener2");
		async.addListener(yetStored);
		this.assertListenersCount(async, 2);
		async.addListener(yetStored);
		this.assertListenersCount(async, 2);
		
		async.removeListener(yetStored);
		async.removeListener(yetStored);
		this.assertListenersCount(async, 1);
	}
	
}
