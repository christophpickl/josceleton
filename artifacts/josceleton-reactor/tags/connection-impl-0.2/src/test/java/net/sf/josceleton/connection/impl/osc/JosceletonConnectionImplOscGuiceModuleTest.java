package net.sf.josceleton.connection.impl.osc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import net.sf.josceleton.commons.test.AbstractGuiceModuleTest;

import org.testng.annotations.Test;

import com.google.inject.Module;

public class JosceletonConnectionImplOscGuiceModuleTest extends AbstractGuiceModuleTest {

	@Override protected final Module createTestee() {
		return new JosceletonConnectionImplOscGuiceModule();
	}
	
	@Test public final void providerExecutesProperyAndReturnsValue() {
		assertThat(new JosceletonConnectionImplOscGuiceModule().getOscPortInClassAdapter(), not(nullValue()));
	}
	
}
