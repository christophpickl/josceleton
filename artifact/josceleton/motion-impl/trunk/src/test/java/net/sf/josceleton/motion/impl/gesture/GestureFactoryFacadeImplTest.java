package net.sf.josceleton.motion.impl.gesture;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import net.sf.josceleton.commons.test.jmock.AbstractMockeryTest;
import net.sf.josceleton.motion.api.gesture.GestureFactoryFacade;
import net.sf.josceleton.motion.api.gesture.hitwall.HitWallBuilder;
import net.sf.josceleton.motion.impl.gesture.hitwall.HitWallBuilderFactory;

import org.jmock.Expectations;
import org.testng.annotations.Test;

public class GestureFactoryFacadeImplTest extends AbstractMockeryTest {
	
	@Test
	public final void newHitWall() {
		final HitWallBuilderFactory mockedHitWallFactory = this.mock(HitWallBuilderFactory.class);
		final HitWallBuilder expectedBuilder = this.mock(HitWallBuilder.class);
		this.checking(new Expectations() { {
			oneOf(mockedHitWallFactory).create();
			will(returnValue(expectedBuilder));
		}});
		final GestureFactoryFacade testee = new GestureFactoryFacadeImpl(mockedHitWallFactory);
		
		assertThat(testee.newHitWall(), equalTo(expectedBuilder));
	}
	
}
