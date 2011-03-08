package net.sf.josceleton.core.impl.async;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import net.sf.josceleton.commons.test.util.TestUtil;
import net.sf.josceleton.core.api.async.AsyncTest;
import net.sf.josceleton.core.api.test.DummyListener;

import org.testng.annotations.Test;

@SuppressWarnings("boxing")
public class CloseableAndAsyncSkeletonTest extends AsyncTest<CloseableAndAsyncSkeleton<? super DummyListener>> {
	
	@Test
	public final void testIsYetClosed() {
		final DummyCloseableAndAsync testee = new DummyCloseableAndAsync();
		assertThat(testee.isYetClosed(), equalTo(false));
		testee.close();
		assertThat(testee.isYetClosed(), equalTo(true));
	}
	
	@Test(expectedExceptions = IllegalStateException.class)
	public final void twiceCloseWillFail() {
		final DummyCloseableAndAsync skeleton = new DummyCloseableAndAsync();
		
		skeleton.close();
		skeleton.close();
	}
	
	@Override protected final void assertListenersCount(
			final CloseableAndAsyncSkeleton<? super DummyListener> async,
			final int expectedCount) {
		assertThat(TestUtil.countIterable(async.getListeners()), equalTo(expectedCount));
	}
	
	@Override protected final CloseableAndAsyncSkeleton<? super DummyListener> createTestee() {
		return new DummyCloseableAndAsync();
	}
	
	
	static class DummyCloseableAndAsync extends CloseableAndAsyncSkeleton<DummyListener> {
		@Override protected void prepareToClose() { /* deliberately empty */ }
	}

}
