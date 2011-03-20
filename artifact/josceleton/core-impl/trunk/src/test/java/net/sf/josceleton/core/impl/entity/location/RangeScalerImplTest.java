package net.sf.josceleton.core.impl.entity.location;

import net.sf.josceleton.core.api.entity.location.RangeScaler;
import net.sf.josceleton.core.api.entity.location.RangeScalerTest;

/**
 * @since 0.5
 */
public class RangeScalerImplTest extends RangeScalerTest {

	@Override protected final RangeScaler newTestee() {
		return new RangeScalerImpl();
	}

}
