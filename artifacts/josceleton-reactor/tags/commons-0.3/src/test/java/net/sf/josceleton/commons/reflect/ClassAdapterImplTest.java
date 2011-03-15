package net.sf.josceleton.commons.reflect;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import net.sf.josceleton.commons.test.util.TestUtil;

import org.testng.annotations.Test;

@SuppressWarnings("boxing")
public class ClassAdapterImplTest {
	
	@Test
	public final void testEverything() {
		final Class<ClassAdapterImplTest> clazz = ClassAdapterImplTest.class;
		final ClassAdapterImpl<ClassAdapterImplTest> testee = ClassAdapterImpl.create(clazz);
		
		assertThat(testee.getInnerClass(), equalTo(clazz));
		assertThat(testee.getName(), equalTo(clazz.getName()));
		assertThat(testee.getConstructors(), equalTo(clazz.getConstructors()));
		assertThat(testee.isPrimitive(), equalTo(false));
		assertThat(testee.isAssignableFrom(ClassAdapterImpl.create(clazz)), equalTo(true));
		TestUtil.assertObjectToString(testee, clazz.getName());
	}
	
	@Test
	public final void testPrimitive() {
		assertThat(ClassAdapterImpl.create(int.class).isPrimitive(), equalTo(true));
	}
	
}
