package net.sf.josceleton.josceleton.test.integration;

import java.util.Collection;
import java.util.Date;

import net.sf.josceleton.commons.test.jmock.AbstractMockeryTest;
import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.Connector;
import net.sf.josceleton.connection.api.service.UserService;
import net.sf.josceleton.connection.api.test.TestableUserServiceListener;
import net.sf.josceleton.connection.api.test.TestableUserServiceListener.UserAndState;
import net.sf.josceleton.connection.impl.osc.OscAddress;
import net.sf.josceleton.connection.impl.osc.OscPortOpener;
import net.sf.josceleton.connection.impl.test.TestableConnectionListener;
import net.sf.josceleton.connection.impl.test.TestableOSCMessage;
import net.sf.josceleton.connection.impl.test.TestableOscPort;
import net.sf.josceleton.core.api.entity.UserState;
import net.sf.josceleton.core.api.entity.body.BodyPart;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.message.UserMessage;
import net.sf.josceleton.josceleton.JosceletonGuiceModule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jmock.Expectations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;

@SuppressWarnings({ "boxing", "synthetic-access" })
public abstract class AbstractIntegrationTest extends AbstractMockeryTest {
	
	private static final Log LOG = LogFactory.getLog(AbstractIntegrationTest.class);
	
	private Connection connection;
	private UserService userService;
	
	private TestableOscPort testableOscPort;
	private TestableConnectionListener connectionCollector;
	private TestableUserServiceListener userServiceCollector;
	
	@BeforeMethod public final void setUpConnection() {
		this.testableOscPort = new TestableOscPort();

		final OscPortOpener mockedPortOpener = this.mock(OscPortOpener.class);
		this.checking(new Expectations() {{
			oneOf(mockedPortOpener).connect(with(any(int.class)));
			will(returnValue(AbstractIntegrationTest.this.testableOscPort));
		}});
		
		// see http://google-guice.googlecode.com/svn/trunk/latest-javadoc/com/google/inject/util/Modules.html
		Module functionalTestModule = Modules.override(new JosceletonGuiceModule()).with(new AbstractModule() {
			@Override protected void configure() {
				bind(OscPortOpener.class).toInstance(mockedPortOpener);
			}
		});
		
		Injector injector = Guice.createInjector(functionalTestModule);
		Connector connector = injector.getInstance(Connector.class);

		this.connection = connector.openConnection();
		this.connectionCollector = new TestableConnectionListener();
		this.connection.addListener(this.connectionCollector);
		this.userService = this.connection.getUserService();
		
		this.userServiceCollector = new TestableUserServiceListener();
		this.userService.addListener(this.userServiceCollector);
	}
	
	@AfterMethod public final void tearDownConnection() {
		this.userServiceCollector = null;
		this.connectionCollector = null;
		this.testableOscPort = null;
		this.userService = null;
		this.connection.close();
		this.connection = null;
	}

	protected final Collection<JointMessage> rawReceivedJointMessages() {
		return this.connectionCollector.getReceivedJointMessages();
	}
	protected final Collection<UserMessage> rawReceivedUserMessages() {
		return this.connectionCollector.getReceivedUserMessages();
	}
	protected final Collection<UserAndState> receivedUserServiceMessages() {
		return this.userServiceCollector.getReceivedMessages();
	}
	
	
	protected final void dispatchJointMessage(int userId, BodyPart part, float... coordinates) {
		LOG.debug("+dispatchJointMessage(..)");
		Float x = coordinates.length > 0 ? coordinates[0] : Float.valueOf(0.0F);
		Float y = coordinates.length > 1 ? coordinates[1] : Float.valueOf(0.0F);
		Float z = coordinates.length > 2 ? coordinates[2] : Float.valueOf(0.0F);
		this.dispatchOscMessage(OscAddress.JOINT, new Object[] {
				part.getOsceletonId(), Integer.valueOf(userId), x, y, z });
	}
	
	protected final void dispatchUserMessage(int userId, UserState state) {
		LOG.debug("+dispatchUserMessage(userId, state=" + state + ")");
		this.dispatchOscMessage(FOOOOOOO_byState(state), new Object[] { Integer.valueOf(userId) });
	}
	
	public static final OscAddress FOOOOOOO_byState(UserState state) {
		if(state == UserState.WAITING) { // FIXME @TEST outsource OscAddress <=> UserState conversion
			return OscAddress.NEW_USER;
		}if(state == UserState.PROCESSING) {
			return OscAddress.NEW_SKEL;
		}if(state == UserState.DEAD) {
			return OscAddress.LOST_USER;
		}return null;
	}
	
	private void dispatchOscMessage(OscAddress address, Object[] arguments) {
		this.testableOscPort.getListeners().get(address).
			acceptMessage(new Date(), new TestableOSCMessage(address.getAddress(), arguments));
	}
}
