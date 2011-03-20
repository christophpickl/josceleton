package net.sf.josceleton.core.impl.entity.location;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import net.sf.josceleton.core.api.entity.location.RangeScaler;
import net.sf.josceleton.core.api.entity.location.RangeScalerTest;
import net.sf.josceleton.core.api.test.TestableRange;

import org.testng.annotations.Test;

/**
 * @since 0.5
 */
public class RangeScalerImplTest extends RangeScalerTest {

	@Override protected final RangeScaler newTestee() {
		return new RangeScalerImpl();
	}
	
}
