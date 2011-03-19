package net.sf.josceleton.core.api.entity.message;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import net.sf.josceleton.commons.test.EqualsDescriptor;
import net.sf.josceleton.commons.test.util.TestUtil;
import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.joint.Joint;

import org.jmock.Mockery;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public abstract class JointMessageTest extends AbstractMessageTest<JointMessage> {

	private User sameUser;
	private Joint sameJoint;
	private Coordinate sameCoordinate;
	
	private User differentUser;
	private Joint differentJoint;
	private Coordinate differentCoordinate;
	

	@BeforeMethod public final void setUpSameStuff() {
		final Mockery mockery = new Mockery();
		
		this.sameUser = mockery.mock(User.class, "sameUser");
		this.sameJoint = mockery.mock(Joint.class, "sameJoint");
		this.sameCoordinate = mockery.mock(Coordinate.class, "sameCoordinate");
		
		this.differentUser = mockery.mock(User.class, "differentUser");
		this.differentJoint = mockery.mock(Joint.class, "differentJoint");
		this.differentCoordinate = mockery.mock(Coordinate.class, "differentCoordinate");
	}

	/** {@inheritDoc} from {@link AbstractEqualsTest} */
	@Override protected final EqualsDescriptor<JointMessage> createEqualsDescriptor() {
		final JointMessage sameA = this.createSameTesteeForEquals();
		final JointMessage sameB = this.createSameTesteeForEquals();
		
		final JointMessage differentC = this.createTestee(this.sameUser, this.sameJoint, this.differentCoordinate);
		final JointMessage differentD = this.createTestee(this.sameUser, this.differentJoint, this.sameCoordinate);
		final JointMessage differentE = this.createTestee(this.differentUser, this.sameJoint, this.sameCoordinate);
		
		return new EqualsDescriptor<JointMessage>(sameA, sameB, differentC, differentD, differentE);
	}

	/** {@inheritDoc} from {@link AbstractEqualsTest} */
	@Override protected final JointMessage createSameTesteeForEquals() {
		return this.createTestee(this.sameUser, this.sameJoint, this.sameCoordinate);
	}
	
	protected abstract JointMessage createTestee(final User user, final Joint joint, final Coordinate coordinate);
	
	@Test
	public final void simpleCreationAndGetterTestForJoint() {
		final Mockery mockery = new Mockery();
		
		final User mockedUser = mockery.mock(User.class);
		final Joint mockedJoint = mockery.mock(Joint.class);
		final Coordinate mockedCoordinate = mockery.mock(Coordinate.class);
		
		final JointMessage message = this.createTestee(mockedUser, mockedJoint, mockedCoordinate);
		
		assertThat(message.getUser(), equalTo(mockedUser));
		assertThat(message.getJoint(), equalTo(mockedJoint));
		assertThat(message.getCoordinate(), equalTo(mockedCoordinate));
		
		mockery.assertIsSatisfied();
	}
	
	@Test
	public final void testToString() {
		final Mockery mockery = new Mockery();
		
		final User mockedUser = mockery.mock(User.class);
		final Joint mockedJoint = mockery.mock(Joint.class);
		final Coordinate mockedCoordinate = mockery.mock(Coordinate.class);
		
		final JointMessage message = this.createTestee(mockedUser, mockedJoint, mockedCoordinate);
		TestUtil.assertObjectToString(message,
			mockedUser.toString(), mockedJoint.toString(), mockedCoordinate.toString()); 
		
		mockery.assertIsSatisfied();
	}
}
