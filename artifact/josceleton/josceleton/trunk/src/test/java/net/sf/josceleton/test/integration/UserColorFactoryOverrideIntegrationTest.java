package net.sf.josceleton.test.integration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import net.sf.josceleton.JosceletonGuice;
import net.sf.josceleton.JosceletonModule;
import net.sf.josceleton.commons.test.jmock.AbstractMockeryTest;
import net.sf.josceleton.connection.api.Connector;
import net.sf.josceleton.core.api.entity.user.UserColorFactory;
import net.sf.josceleton.core.impl.entity.user.UserFactory;

import org.jmock.Expectations;
import org.testng.annotations.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;

public class UserColorFactoryOverrideIntegrationTest extends AbstractMockeryTest {
	
	@SuppressWarnings("boxing")
	@Test public final void overridingColorFactory() {
		final int col1 = 0xFEEDDE;
		final int col2 = 0xADBEEF;
		
		final UserColorFactory mockedColorFactory = mock(UserColorFactory.class);
		checking(new Expectations() { {
			oneOf(mockedColorFactory).create(); will(returnValue(col1));
			oneOf(mockedColorFactory).create(); will(returnValue(col2));
		}});
		
		final Injector injector = Guice.createInjector(
			Modules.override(new JosceletonModule()).with(new AbstractModule() {
				@Override protected void configure() { bind(UserColorFactory.class).toInstance(mockedColorFactory); }}
		));
		
		final UserFactory fact = injector.getInstance(UserFactory.class);
		assertThat(fact.create(42).getColor(), equalTo(col1));
		assertThat(fact.create(42).getColor(), equalTo(col2));
	}
	
	static {
		final UserColorFactory myColorFactory = new UserColorFactory() {
			@Override public int create() {
				return 0xFFCC00;
		} };
		final Module myModule = new AbstractModule() {
			@Override protected void configure() {
				bind(UserColorFactory.class).toInstance(myColorFactory);
		} };
		final Injector injector = JosceletonGuice.newInjectorOverriddenBy(myModule);
		final Connector connector = injector.getInstance(Connector.class);
//		... = connector.openConnection();
	}
}
