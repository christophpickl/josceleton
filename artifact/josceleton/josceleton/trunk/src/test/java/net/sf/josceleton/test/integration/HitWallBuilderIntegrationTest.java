package net.sf.josceleton.test.integration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import net.sf.josceleton.commons.exception.InvalidArgumentException;
import net.sf.josceleton.core.api.entity.location.Direction;
import net.sf.josceleton.motion.api.gesture.hitwall.HitWallBuilder;

import org.testng.annotations.Test;

/**
 * @since 0.4
 */
public class HitWallBuilderIntegrationTest extends AbstractMotionIntegrationTest<HitWallBuilderIntegrationTest> {
	
	private HitWallBuilder createTemporarilyInvalidBuilder() {
		return this.newGesture().newHitWall().direction(Direction.X).coordinate(4.2F);
	}

	@Test public final void configureWithWrongCoordinateAndNotBuildingSucceeds() {
		assertThat(this.createTemporarilyInvalidBuilder(), notNullValue()); // nothing wrong about that
	}

	@Test(expectedExceptions = InvalidArgumentException.class,
			expectedExceptionsMessageRegExp = ".*coordinate.*4\\.2.*")
	public final void configureWithWrongCoordinateAndBuildingFails() {
		// should fail, as 4.2 vor X is wrong (but would not have been for Z)
		this.createTemporarilyInvalidBuilder().build();
	}
	
}
