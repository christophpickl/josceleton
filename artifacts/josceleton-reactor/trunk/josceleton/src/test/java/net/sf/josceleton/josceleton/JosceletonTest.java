package net.sf.josceleton.josceleton;

import static net.sf.josceleton.commons.test.matcher.JosceletonMatchers.hasSinglePrivateNullifiedConstructorAndInvokeIt;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.lang.reflect.Field;

import net.sf.josceleton.commons.test.jmock.AbstractMockeryTest;
import net.sf.josceleton.connection.api.Connection;

import org.hamcrest.Matchers;
import org.jmock.Expectations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class JosceletonTest extends AbstractMockeryTest {

	private JosceletonFacade previousFacade;
	

	@Test public final void openConnectionSuccessfully() throws Exception {
		final Connection expectedConnection = this.mock(Connection.class);
		this.forceStaticMockedFacade(expectedConnection, null);
		final Connection actualConnection = Josceleton.openConnection();
		
		assertThat(actualConnection, is(expectedConnection));
	}

	@Test public final void openConnectionOnPortSuccessfully() throws Exception {
		final Connection expectedConnection = this.mock(Connection.class);
		final Integer expectedPort = Integer.valueOf(42);
		this.forceStaticMockedFacade(expectedConnection, expectedPort);
		final Connection actualConnection = Josceleton.openConnectionOnPort(expectedPort.intValue());
		
		assertThat(actualConnection, is(expectedConnection));
	}
	
	private void forceStaticMockedFacade(final Connection expectedConnection, final Integer onPort) throws Exception {
		final JosceletonFacade mockedFacade = this.mock(JosceletonFacade.class);
		
		this.checking(new Expectations() { {
			if(onPort != null) {
				oneOf(mockedFacade).openConnectionOnPort(onPort.intValue());
			} else {
				oneOf(mockedFacade).openConnection();
			}
			will(returnValue(expectedConnection));
		}});
		
		this.bruteForceSetFacade(mockedFacade);
	}
	
	@Test public final void hasSinglePrivateNullifiedConstructor() {
		assertThat(Josceleton.class, hasSinglePrivateNullifiedConstructorAndInvokeIt());
	}
	
	@Test public final void newGuiceInjectior() {
		// we cant say more about it :-/
		assertThat(Josceleton.newGuiceInjector(), notNullValue());
		
		// NO: unfortunately, it seems as the Injector implementation checks for identity in its equals() method :(
//		final Injector expectedInjector = Guice.createInjector(new JosceletonGuiceModule());
//		final Injector actualInjector = Josceleton.newGuiceInjector();
//		assertThat(actualInjector, equalTo(expectedInjector));
	}
	
	
	@BeforeMethod public final void setUp() throws Exception {
		this.previousFacade = this.bruteForceGetFacade(); // save back old field
		// now test can safely overwrite static instance value :)
	}

	@AfterMethod public final void tearDown() throws Exception {
		this.bruteForceSetFacade(this.previousFacade);
	}
	
	@Test
	public final void newGuiceModule() {
		assertThat(Josceleton.newGuiceModule(), is(JosceletonGuiceModule.class));
	}

	
	private void bruteForceSetFacade(final JosceletonFacade facade) throws Exception {
		final Field field = this.bruteForGetFacadeField();
		field.set(null, facade);
	}
	
	private JosceletonFacade bruteForceGetFacade() throws Exception {
		final Field field = this.bruteForGetFacadeField();
		return (JosceletonFacade) field.get(null);
	}
	
	private Field bruteForGetFacadeField() throws Exception {
		final Field field = Josceleton.class.getDeclaredField("facade");
		field.setAccessible(true);
		return field;
	}
}
