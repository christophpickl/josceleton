package net.sf.josceleton.core.api.entity.message;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import net.sf.josceleton.commons.test.EqualsDescriptor;
import net.sf.josceleton.commons.test.util.TestUtil;
import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.body.BodyPart;

import org.jmock.Mockery;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public abstract class JointMessageTest extends AbstractMessageTest<JointMessage> {

	private User sameUser;
	private BodyPart sameBodyPart;
	private Coordinate sameCoordinate;
	
	private User differentUser;
	private BodyPart differentBodyPart;
	private Coordinate differentCoordinate;
	

	@BeforeMethod public final void setUpSameStuff() {
		final Mockery mockery = new Mockery();
		
		this.sameUser = mockery.mock(User.class, "sameUser");
		this.sameBodyPart = mockery.mock(BodyPart.class, "sameBodyPart");
		this.sameCoordinate = mockery.mock(Coordinate.class, "sameCoordinate");
		
		this.differentUser = mockery.mock(User.class, "differentUser");
		this.differentBodyPart = mockery.mock(BodyPart.class, "differentBodyPart");
		this.differentCoordinate = mockery.mock(Coordinate.class, "differentCoordinate");
	}

	/** {@inheritDoc} from {@link AbstractEqualsTest} */
	@Override protected final EqualsDescriptor<JointMessage> createEqualsDescriptor() {
		final JointMessage sameA = this.createSameTesteeForEquals();
		final JointMessage sameB = this.createSameTesteeForEquals();
		
		final JointMessage differentC = this.createTestee(this.sameUser, this.sameBodyPart, this.differentCoordinate);
		final JointMessage differentD = this.createTestee(this.sameUser, this.differentBodyPart, this.sameCoordinate);
		final JointMessage differentE = this.createTestee(this.differentUser, this.sameBodyPart, this.sameCoordinate);
		
		return new EqualsDescriptor<JointMessage>(sameA, sameB, differentC, differentD, differentE);
	}

	/** {@inheritDoc} from {@link AbstractEqualsTest} */
	@Override protected final JointMessage createSameTesteeForEquals() {
		return this.createTestee(this.sameUser, this.sameBodyPart, this.sameCoordinate);
	}
	
	protected abstract JointMessage createTestee(final User user, final BodyPart bodyPart, final Coordinate coordinate);
	
	@Test
	public final void simpleCreationAndGetterTestForJoint() {
		final Mockery mockery = new Mockery();
		
		final User mockedUser = mockery.mock(User.class);
		final BodyPart mockedBodyPart = mockery.mock(BodyPart.class);
		final Coordinate mockedCoordinate = mockery.mock(Coordinate.class);
		
		final JointMessage message = this.createTestee(mockedUser, mockedBodyPart, mockedCoordinate);
		
		assertThat(message.getUser(), equalTo(mockedUser));
		assertThat(message.getJointPart(), equalTo(mockedBodyPart));
		assertThat(message.getCoordinate(), equalTo(mockedCoordinate));
		
		mockery.assertIsSatisfied();
	}
	
	@Test
	public final void testToString() {
		final Mockery mockery = new Mockery();
		
		final User mockedUser = mockery.mock(User.class);
		final BodyPart mockedBodyPart = mockery.mock(BodyPart.class);
		final Coordinate mockedCoordinate = mockery.mock(Coordinate.class);
		
		final JointMessage message = this.createTestee(mockedUser, mockedBodyPart, mockedCoordinate);
		TestUtil.assertObjectToString(message,
			mockedUser.toString(), mockedBodyPart.toString(), mockedCoordinate.toString()); 
		
		mockery.assertIsSatisfied();
	}
}
