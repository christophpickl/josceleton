package net.sf.josceleton.motion.impl.gesture.hitwall;

import static net.sf.josceleton.commons.test.matcher.JosceletonMatchers.collectionHasUnordered;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import net.sf.josceleton.commons.exception.InvalidArgumentException;
import net.sf.josceleton.core.api.entity.Direction;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Joints;
import net.sf.josceleton.motion.api.gesture.hitwall.HitWallBuilder;
import net.sf.josceleton.motion.api.gesture.hitwall.HitWallConfig;
import net.sf.josceleton.motion.api.gesture.hitwall.HitWallGesture;
import net.sf.josceleton.motion.api.gesture.hitwall.HitWallListener;
import net.sf.josceleton.motion.impl.gesture.AbstractJointableGestureBuilder;
import net.sf.josceleton.motion.impl.gesture.AbstractJointableGestureBuilderTest;

import org.jmock.Expectations;
import org.testng.annotations.Test;

/**
 * @since 0.4
 */
@SuppressWarnings("boxing")
public class HitWallBuilderImplTest extends AbstractJointableGestureBuilderTest<
	HitWallBuilder,
	HitWallGesture,
	HitWallConfig,
	HitWallListener> {

	@Override protected final AbstractJointableGestureBuilder<
			HitWallBuilder,
			HitWallGesture,
			HitWallConfig,
			HitWallListener> createTestee() {
		final HitWallGestureFactory gestureFactory = this.mock(HitWallGestureFactory.class);
		final HitWallConfigFactory configFactory = this.mock(HitWallConfigFactory.class);
		return new HitWallBuilderImpl(gestureFactory, configFactory);
	}
	
	@Test
	public final void buildImmediateGestureAndTestDefaultValues() {
		final HitWallGesture mockedGesture = this.mock(HitWallGesture.class);
		final HitWallBuilderImpl builder = this.createAdvancedTestee(mockedGesture, new Joint[] { Joints.HAND().RIGHT() }, 0.6F, Direction.Y, true);
		final HitWallGesture actualGesture = builder.build();
		assertThat(actualGesture, is(sameInstance(mockedGesture)));
	}
	
	@Test
	public final void buildGestureAndConfigureValues() {
		final HitWallGesture mockedGesture = this.mock(HitWallGesture.class);
		
		final Joint[] joints = new Joint[] { Joints.KNEE().LEFT(), Joints.KNEE().RIGHT() };
		final float coordinate = 0.77F;
		final Direction direction = Direction.Z;
		final boolean triggerOnLower = false;
		
		final HitWallBuilderImpl builder = this.createAdvancedTestee(mockedGesture, joints, coordinate, direction, triggerOnLower);
		final HitWallGesture actualGesture = builder.relevantJoints(joints).coordinate(coordinate).direction(direction).triggerOnLower(triggerOnLower).build();
		assertThat(actualGesture, is(sameInstance(mockedGesture)));
	}
	
	private HitWallBuilderImpl createAdvancedTestee(final HitWallGesture mockedGesture, final Joint[] expectedDefaultJoints,
			final float coordinate, final Direction direction, final boolean triggerOnLower) {
		final HitWallConfig mockedConfig = this.mock(HitWallConfig.class);
		final HitWallConfigFactory configFactory = this.mock(HitWallConfigFactory.class);
		this.checking(new Expectations() { {
			oneOf(configFactory).create(with(collectionHasUnordered(expectedDefaultJoints)),
					with(equalTo(coordinate)), with(is(direction)), with(is(triggerOnLower))); // default values
			will(returnValue(mockedConfig));
		}});
		
		final HitWallGestureFactory gestureFactory = this.mock(HitWallGestureFactory.class);
		this.checking(new Expectations() { {
			oneOf(gestureFactory).create(mockedConfig);
			will(returnValue(mockedGesture));
		}});
		
		return new HitWallBuilderImpl(gestureFactory, configFactory);
	}

	@Test(expectedExceptions = InvalidArgumentException.class,
			expectedExceptionsMessageRegExp = ".*direction.*null.*")
	public final void passingNullDirectionFails() {
		final HitWallBuilder builder = this.newSimpleBuilder();
		builder.direction(null);
	}

	@Test(expectedExceptions = InvalidArgumentException.class,
			expectedExceptionsMessageRegExp = ".*coordinateValue.*7\\.1.*")
	public final void passingInvalidCoordinateFails() {
		final HitWallBuilder builder = this.newSimpleBuilder();
		builder.coordinate(7.1F);
	}

	@Test
	public final void passingInvalidCoordinateForNonZTemporarilySucceedsForBuilder() {
		final HitWallBuilder builder = this.newSimpleBuilder();
		builder.direction(Direction.X).coordinate(6.1F); // validations will be done within Config object afterwards
		// REMARK: integration test will check if builder.build() fails with wrong X and coordinate > 1.0!
	}
	
	private HitWallBuilder newSimpleBuilder() {
		final HitWallGestureFactory gestureFactory = this.mock(HitWallGestureFactory.class);
		final HitWallConfigFactory configFactory = this.mock(HitWallConfigFactory.class);
		return new HitWallBuilderImpl(gestureFactory, configFactory);
	}
}
