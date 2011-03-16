package net.sf.josceleton.motion.impl.gesture.hitwall;

import static net.sf.josceleton.commons.test.matcher.JosceletonMatchers.collectionHas;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import net.sf.josceleton.core.api.entity.XyzDirection;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Joints;
import net.sf.josceleton.motion.api.gesture.hitwall.HitWallBuilder;
import net.sf.josceleton.motion.api.gesture.hitwall.HitWallConfig;
import net.sf.josceleton.motion.api.gesture.hitwall.HitWallGesture;
import net.sf.josceleton.motion.api.gesture.hitwall.HitWallListener;
import net.sf.josceleton.motion.impl.gesture.AbstractJointableGestureBuilder;
import net.sf.josceleton.motion.impl.gesture.AbstractJointableGestureBuilderTest;

import org.hamcrest.Matchers;
import org.jmock.Expectations;
import org.testng.annotations.Test;

/**
 * @since 0.4
 */
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
	
	@SuppressWarnings("boxing")
	@Test
	public final void buildImmediateGestureAndTestDefaultValues() {
		final HitWallConfig mockedConfig = this.mock(HitWallConfig.class);
		final HitWallGesture mockedGesture = this.mock(HitWallGesture.class);
		final Joint expectedRelevantJoint = Joints.HAND().RIGHT();
		final Joint[] expectedDefaultJoints = new Joint[] { expectedRelevantJoint };
		final HitWallConfigFactory configFactory = this.mock(HitWallConfigFactory.class);
		this.checking(new Expectations() { {
			oneOf(configFactory).create(with(collectionHas(expectedDefaultJoints)),
					with(equalTo(0.6F)), with(is(XyzDirection.Y)), with(is(true)));
			will(returnValue(mockedConfig));
		}});
		
		final HitWallGestureFactory gestureFactory = this.mock(HitWallGestureFactory.class);
		this.checking(new Expectations() { {
			oneOf(gestureFactory).create(mockedConfig);
			will(returnValue(mockedGesture));
		}});
		
		final HitWallBuilderImpl builder = new HitWallBuilderImpl(gestureFactory, configFactory);
		
		final HitWallGesture actualGesture = builder.build();
		assertThat(actualGesture, notNullValue());
		assertThat(actualGesture, is(Matchers.sameInstance(mockedGesture)));
		
	}
	
}
