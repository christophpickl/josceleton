package net.sf.josceleton.connection.impl.osc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import net.sf.josceleton.commons.test.AbstractModuleTest;

import org.testng.annotations.Test;

import com.google.inject.Module;

public class ConnectionImplOscModuleTest extends AbstractModuleTest {

	@Override protected final Module createTestee() {
		return new ConnectionImplOscModule();
	}
	
	@Test public final void providerExecutesProperyAndReturnsValue() {
		assertThat(new ConnectionImplOscModule().getOscPortInClassAdapter(), not(nullValue()));
	}
	
}
