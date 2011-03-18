package net.sf.josceleton.commons.test;

import static org.hamcrest.MatcherAssert.assertThat;
import net.sf.josceleton.commons.exception.JosceletonException;
import net.sf.josceleton.commons.test.matcher.JosceletonMatchers;

import org.testng.annotations.Test;

public abstract class AbstractExceptionTest {
	
	@Test
	public final void singleDeclaredConstructorOfExceptionClassIsProtected() {
		assertThat(this.getExceptionClass(), JosceletonMatchers.hasSingleProtectedConstructor());
	}

	protected abstract Class<? extends JosceletonException> getExceptionClass();
	
}
