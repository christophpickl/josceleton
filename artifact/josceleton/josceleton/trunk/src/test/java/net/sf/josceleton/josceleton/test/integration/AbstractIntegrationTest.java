package net.sf.josceleton.josceleton.test.integration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import net.sf.josceleton.commons.exception.InvalidArgumentException;
import net.sf.josceleton.commons.test.jmock.AbstractMockeryTest;
import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.Connector;
import net.sf.josceleton.connection.api.service.motion.ContinuousMotionSupplier;
import net.sf.josceleton.connection.api.service.motion.MotionSupplier;
import net.sf.josceleton.connection.api.service.motion.MotionSupplierFactory;
import net.sf.josceleton.connection.api.service.user.UserService;
import net.sf.josceleton.connection.api.test.TestableUserServiceListener;
import net.sf.josceleton.connection.api.test.UserAndState;
import net.sf.josceleton.connection.impl.osc.OscAddress;
import net.sf.josceleton.connection.impl.osc.OscPortOpener;
import net.sf.josceleton.connection.impl.service.motion.ContinuousMotionSupplierFactory;
import net.sf.josceleton.connection.impl.test.OscAddressConverterUtil;
import net.sf.josceleton.connection.impl.test.TestableConnectionListener;
import net.sf.josceleton.connection.impl.test.TestableOSCMessage;
import net.sf.josceleton.connection.impl.test.TestableOscPort;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.message.UserMessage;
import net.sf.josceleton.core.api.entity.user.User;
import net.sf.josceleton.core.api.entity.user.UserState;
import net.sf.josceleton.josceleton.JosceletonGuiceModule;

import org.jmock.Expectations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;

@SuppressWarnings({ "boxing", "synthetic-access", "unchecked" })
public class AbstractIntegrationTest<T extends AbstractIntegrationTest<T>> extends AbstractMockeryTest {
	
	private Injector injector;
	
	private Connection connection;
	private UserService userService;
	
	private TestableOscPort testableOscPort;
	private TestableConnectionListener connectionCollector;
	private TestableUserServiceListener userServiceCollector;
	
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
		// LUXURY @TEST maybe remove all listeners in tear down 
	}
	

	protected final void dispatchJointMessage(final User user, final Joint joint, final float... coordinates) {
		this.dispatchJointMessage(user.getOsceletonId(), joint, coordinates);
	}

	protected final void dispatchJointMessage(final int userId, final Joint joint, final float... coordinates) {
		if(coordinates.length <= 3 == false) {
			throw InvalidArgumentException.newInstance("coordinates.length", coordinates.length, "<= 3");
		}
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
	
	

	/**
	 * @return raw <code>JointMessage</code>s received from <code>Connection</code>.
	 */
	protected final Collection<JointMessage> getReceivedJointMessages() {
		return this.connectionCollector.getReceivedJointMessages();
	}

	/**
	 * @return raw <code>UserMessage</code>s received from <code>Connection</code>.
	 */
	protected final Collection<UserMessage> getReceivedUserMessages() {
		return this.connectionCollector.getReceivedUserMessages();
	}
	
	protected final List<UserAndState> getReceivedUserServiceMessages() {
		return this.userServiceCollector.getOnAnyParameter();
	}
	

	
	/**
	 * Sometimes its necessary to have access to the internal injector. BUT ...
	 * ... one should be careful not to mess up, as this member is used for this class' internal workings.
	 */
	protected final Injector getInjectorButBeCarefulToNotMessUpYourTestCode() {
		return this.injector;
	}
	
	protected final MotionSupplier getMotionSupplier() {
		// yes, always ask injector, as it will be more like how the enduser will user josceleton.
		final MotionSupplierFactory factory = this.injector.getInstance(MotionSupplierFactory.class);
		return factory.create(this.connection);
	}

	protected final ContinuousMotionSupplier getContinuousMotionSupplier() {
		return this.injector.getInstance(ContinuousMotionSupplierFactory.class).create(this.connection);
	}
	
	

	protected final T login(final int osceletonId) {
		this.dispatchUserMessage(osceletonId, UserState.WAITING);
		this.dispatchUserMessage(osceletonId, UserState.PROCESSING);
		
		final List<UserAndState> userServiceMsgs = this.getReceivedUserServiceMessages();
		final UserAndState uas = userServiceMsgs.get(userServiceMsgs.size() - 1);
		
		// sanity checks ;)
		assertThat(uas.getState(), equalTo(UserState.PROCESSING));
		assertThat(uas.getUser().getOsceletonId(), equalTo(osceletonId));
		
		// uas.getUser() ... not needed
		return (T) this;
	}
	
	protected final T logout(final int userId) {
		this.dispatchUserMessage(userId, UserState.DEAD);
		return (T) this;
	}
}
