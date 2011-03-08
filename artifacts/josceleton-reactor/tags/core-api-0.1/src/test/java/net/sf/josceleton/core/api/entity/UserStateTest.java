package net.sf.josceleton.core.api.entity;

import net.sf.josceleton.commons.test.AbstractEnumTest;

public class UserStateTest extends AbstractEnumTest<UserState> {

	@Override protected final EnumValueOfDescriptor<UserState> getValueOfDescriptor() {
		return new EnumValueOfDescriptor<UserState>(UserState.class,
			"WAITING", UserState.WAITING, "PROCESSING", UserState.PROCESSING, "DEAD", UserState.DEAD);
	}

	@Override protected final EnumValuesDescriptor<UserState> getValuesDescriptor() {
		return new EnumValuesDescriptor<UserState>(UserState.class,
			UserState.WAITING, UserState.PROCESSING, UserState.DEAD);
	}
	
	
	
}
