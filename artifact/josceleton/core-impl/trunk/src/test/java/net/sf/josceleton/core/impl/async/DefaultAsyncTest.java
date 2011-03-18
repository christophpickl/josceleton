package net.sf.josceleton.core.impl.async;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import net.sf.josceleton.commons.test.util.TestUtil;
import net.sf.josceleton.core.api.async.AsyncTest;
import net.sf.josceleton.core.api.async.Listener;

public class DefaultAsyncTest extends AsyncTest<DefaultAsync<Listener>> {

	@Override protected final DefaultAsync<Listener> createTestee() {
		return new DefaultAsync<Listener>();
	}

	@SuppressWarnings("boxing")
	@Override protected final void assertListenersCount(
			final DefaultAsync<Listener> delegator,
			final int expectedCount) {
		
		assertThat(TestUtil.countIterable(delegator.getListeners()), equalTo(expectedCount));
	}
	
}
