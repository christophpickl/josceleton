package net.sf.josceleton.core.impl.async;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import net.sf.josceleton.commons.test.util.TestUtil;
import net.sf.josceleton.core.api.async.AsyncFor;
import net.sf.josceleton.core.api.async.AsyncForTest;
import net.sf.josceleton.core.api.async.Listener;

@SuppressWarnings("boxing")
public class DefaultAsyncForTest extends AsyncForTest {

	@Override protected final <K, L extends Listener> void assertListenersCount(
			final AsyncFor<K, L> asyncFor, final K key, final int expectedCount) {
		final DefaultAsyncFor<K, L> defaultAsyncFor = (DefaultAsyncFor<K, L>) asyncFor;
		
		assertThat(TestUtil.countIterable(defaultAsyncFor.getListenersFor(key)), equalTo(expectedCount));
	}

	@Override protected final <K, L extends Listener> AsyncFor<K, L> createTestee(
			final Class<K> keyType, final Class<L> listenerType) {
		return new DefaultAsyncFor<K, L>();
	}

}
