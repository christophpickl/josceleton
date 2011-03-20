package net.sf.josceleton.josceleton.test.integration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import net.sf.josceleton.core.api.entity.location.Direction;
import net.sf.josceleton.core.api.entity.location.RangeFactory;
import net.sf.josceleton.core.api.entity.location.RangeScaler;

import org.testng.annotations.Test;

/**
 * Simple integration test (object wiring only), as specific tests with several numbers are done within unit tests.
 * 
 * @since 0.5
 */
@SuppressWarnings("boxing")
public class RangeScalerIntegrationTest extends AbstractIntegrationTest<RangeScalerIntegrationTest> {
	
	@Test public final void scaleProperly() {
		final RangeScaler scaler = this.getRangeScaler();
		final RangeFactory factory = this.getRangeFactory();
		
		assertThat(scaler.scale(0.3F, factory.create(Direction.X, 0, 100)), equalTo(30));
		assertThat(scaler.scale(0.8F, factory.create(Direction.Y, 0, 10)), equalTo(8));
		assertThat(scaler.scale(3.5F, factory.create(Direction.Z, 0, 10)), equalTo(5));

		assertThat(scaler.scale(0.6F, factory.createSpecific(0.5F, 0.7F, 0, 127)), equalTo(64));
		assertThat(scaler.scale(0.6F, factory.createSpecific(0.9F, 0.4F, 0, 127)), equalTo(76));
		assertThat(scaler.scale(0.8F, factory.createSpecific(0.9F, 0.4F, 0, 127)), equalTo(25));
	}
	
	
}
