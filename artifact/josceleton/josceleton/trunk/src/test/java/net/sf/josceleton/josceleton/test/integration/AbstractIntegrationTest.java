package net.sf.josceleton.josceleton.test.integration;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import net.sf.josceleton.commons.test.jmock.AbstractMockeryTest;
import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.Connector;
import net.sf.josceleton.connection.api.service.motion.MotionSupplier;
import net.sf.josceleton.connection.api.service.motion.MotionSupplierFactory;
import net.sf.josceleton.connection.api.service.user.UserService;
import net.sf.josceleton.connection.api.test.TestableUserServiceListener;
import net.sf.josceleton.connection.api.test.UserAndState;
import net.sf.josceleton.connection.impl.osc.OscAddress;
import net.sf.josceleton.connection.impl.osc.OscPortOpener;
import net.sf.josceleton.connection.impl.test.OscAddressConverterUtil;
import net.sf.josceleton.connection.impl.test.TestableConnectionListener;
import net.sf.josceleton.connection.impl.test.TestableOSCMessage;
import net.sf.josceleton.connection.impl.test.TestableOscPort;
import net.sf.josceleton.core.api.entity.UserState;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.message.UserMessage;
import net.sf.josceleton.josceleton.JosceletonGuiceModule;

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
	
	private Injector injector;
	
	private Connection connection;
	private UserService userService;
	
	private TestableOscPort testableOscPort;
	private TestableConnectionListener connectionCollector;
	private TestableUserServiceListener userServiceCollector;
	private MotionSupplierFactory separatorCache;
	
	@BeforeMethod public final void setUpConnection() {
		this.testableOscPort = new TestableOscPort();

		final OscPortOpener mockedPortOpener = this.mock(OscPortOpener.class);
		this.checking(new Expectations() { {
			oneOf(mockedPortOpener).connect(with(any(int.class)));
			will(returnValue(AbstractIntegrationTest.this.testableOscPort));
		}});
		
		// see http://google-guice.googlecode.com/svn/trunk/latest-javadoc/com/google/inject/util/Modules.html
		final Module functionalTestModule = Modules.override(new JosceletonGuiceModule()).with(new AbstractModule() {
			@Override protected void configure() {
				bind(OscPortOpener.class).toInstance(mockedPortOpener);
			}
		});
		
		this.injector = Guice.createInjector(functionalTestModule);
		final Connector connector = this.injector.getInstance(Connector.class);
		
		this.separatorCache = this.injector.getInstance(MotionSupplierFactory.class);
		
		this.connection = connector.openConnection();
		this.connectionCollector = new TestableConnectionListener();
		this.connection.addListener(this.connectionCollector);
		this.userService = this.connection.getUserService();
		
		this.userServiceCollector = new TestableUserServiceListener();
		this.userService.addListener(this.userServiceCollector);
	}
	
	protected final Injector getInjectorButBeCarefulToNotMessUpYourTestCode() {
		return this.injector;
	}
	
	protected final MotionSupplier getMotionSeparator() {
		return this.separatorCache.create(this.connection);
	}
	
	@AfterMethod public final void tearDownConnection() {
		this.userServiceCollector = null;
		this.connectionCollector = null;
		this.testableOscPort = null;
		this.userService = null;
		this.connection.close();
		this.connection = null;
		this.separatorCache = null;
		// MINOR @TEST maybe remove all listeners in tear down 
	}

	protected final Collection<JointMessage> getRawReceivedJointMessages() {
		return this.connectionCollector.getReceivedJointMessages();
	}
	protected final Collection<UserMessage> getRawReceivedUserMessages() {
		return this.connectionCollector.getReceivedUserMessages();
	}
	protected final List<UserAndState> getReceivedUserServiceMessages() {
		return this.userServiceCollector.getReceivedMessages();
	}
	
	
	protected final void dispatchJointMessage(final int userId, final Joint joint, final float... coordinates) {
		final Float x = coordinates.length > 0 ? coordinates[0] : Float.valueOf(0.0F);
		final Float y = coordinates.length > 1 ? coordinates[1] : Float.valueOf(0.0F);
		final Float z = coordinates.length > 2 ? coordinates[2] : Float.valueOf(0.0F);
		this.dispatchOscMessage(OscAddress.JOINT, new Object[] {
				joint.getOsceletonId(), Integer.valueOf(userId), x, y, z });
	}
	
	protected final void dispatchUserMessage(final int userId, final UserState state) {
		this.dispatchOscMessage(OscAddressConverterUtil.convertUserStateToOscAddress(state), new Object[] { Integer.valueOf(userId) });
	}
	
	
	private void dispatchOscMessage(final OscAddress address, final Object[] arguments) {
		this.testableOscPort.getListeners().get(address).
			acceptMessage(new Date(), new TestableOSCMessage(address.getAddress(), arguments));
	}
}
