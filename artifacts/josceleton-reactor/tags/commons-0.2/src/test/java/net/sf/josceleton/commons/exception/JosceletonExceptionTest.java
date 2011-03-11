package net.sf.josceleton.commons.exception;

import net.sf.josceleton.commons.test.AbstractExceptionTest;

public class JosceletonExceptionTest extends AbstractExceptionTest {

	@Override protected final Class<? extends JosceletonException> getExceptionClass() {
		return JosceletonException.class;
	}

}
