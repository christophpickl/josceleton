package net.sf.josceleton.core.impl.async;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import net.sf.josceleton.commons.test.util.TestUtil;
import net.sf.josceleton.core.api.async.AsyncTest;
import net.sf.josceleton.core.api.test.DummyListener;

public class AsyncDelegatorTest extends AsyncTest<AsyncDelegator<? super DummyListener>> {

	@Override protected final AsyncDelegator<? super DummyListener> createTestee() {
		return new AsyncDelegator<DummyListener>();
	}

	@SuppressWarnings("boxing")
	@Override protected final void assertListenersCount(
			final AsyncDelegator<? super DummyListener> delegator,
			final int expectedCount) {
		assertThat(TestUtil.countIterable(delegator.getListeners()), equalTo(expectedCount));
	}
	
}
