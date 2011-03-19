package net.sf.josceleton.commons.util;

import static net.sf.josceleton.commons.test.matcher.JosceletonMatchers.hasSinglePrivateNullifiedConstructorAndInvokeIt;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.Closeable;
import java.io.IOException;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.annotations.Test;

@SuppressWarnings("boxing")
public class CloseableUtilTest {

	@Test public final void hasUtilityConstructor() {
		assertThat(CloseableUtil.class, hasSinglePrivateNullifiedConstructorAndInvokeIt());
	}
	
	@Test
	public final void closeCorrectly() throws Exception {
		final Mockery mockery = new Mockery();
		final Closeable mockedCloseable = mockery.mock(Closeable.class);
		mockery.checking(new Expectations() { {
			oneOf(mockedCloseable).close();
		}});
		
		assertThat(CloseableUtil.close(mockedCloseable), equalTo(true));
		mockery.assertIsSatisfied();
	}
	
	@Test
	public final void closeAndThrowDoesNothing() throws Exception {
		final Mockery mockery = new Mockery();
		final Closeable mockedCloseable = mockery.mock(Closeable.class);
		mockery.checking(new Expectations() { {
			oneOf(mockedCloseable).close();
			will(throwException(new IOException("Mocked closeable thrown dummy exception for test.")));
		}});
		
		assertThat(CloseableUtil.close(mockedCloseable), equalTo(false));
		mockery.assertIsSatisfied();
	}
	
	@Test
	public final void closeNullDoesNothing() {
		assertThat(CloseableUtil.close(null), equalTo(false));
	}

}
