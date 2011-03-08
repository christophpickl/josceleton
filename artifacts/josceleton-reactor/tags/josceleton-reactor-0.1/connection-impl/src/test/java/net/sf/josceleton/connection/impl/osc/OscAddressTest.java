package net.sf.josceleton.connection.impl.osc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import net.sf.josceleton.commons.test.AbstractEnumTest;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class OscAddressTest extends AbstractEnumTest<OscAddress> {

	@Override protected final EnumValueOfDescriptor<OscAddress> getValueOfDescriptor() {
		return new EnumValueOfDescriptor<OscAddress>(OscAddress.class, "JOINT", OscAddress.JOINT,
				"NEW_USER", OscAddress.NEW_USER, "NEW_SKEL", OscAddress.NEW_SKEL, "LOST_USER", OscAddress.LOST_USER);
	}

	@Override protected final EnumValuesDescriptor<OscAddress> getValuesDescriptor() {
		return new EnumValuesDescriptor<OscAddress>(OscAddress.class,
				OscAddress.JOINT, OscAddress.NEW_USER, OscAddress.NEW_SKEL, OscAddress.LOST_USER);
	}
	
	@DataProvider(name = "provideAddressValues")
	public final Object[][] provideAddressValues() {
		return new Object[][] {
			new Object[] { OscAddress.JOINT, "/joint" },
			new Object[] { OscAddress.NEW_USER, "/new_user" },
			new Object[] { OscAddress.NEW_SKEL, "/new_skel" },
			new Object[] { OscAddress.LOST_USER, "/lost_user" }
		};
	}
	@Test(dataProvider = "provideAddressValues")
	public final void testGetAddress(final OscAddress addressEnum, final String expectedAddressValue) {
		assertThat(addressEnum.getAddress(), equalTo(expectedAddressValue));
	}

}
