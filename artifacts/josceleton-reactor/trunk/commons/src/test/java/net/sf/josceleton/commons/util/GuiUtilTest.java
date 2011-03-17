package net.sf.josceleton.commons.util;

import static net.sf.josceleton.commons.test.matcher.JosceletonMatchers.hasSinglePrivateNullifiedConstructorAndInvokeIt;
import static org.hamcrest.MatcherAssert.assertThat;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.testng.annotations.Test;

@SuppressWarnings("boxing")
public class GuiUtilTest {

	@Test public final void hasUtilityConstructor() {
		assertThat(CloseableUtil.class, hasSinglePrivateNullifiedConstructorAndInvokeIt());
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "component == null")
	public final void setCenterLocationNullifiedWillFail() {
		GuiUtil.setCenterLocation(null);
	}
	
	@Test
	public final void setCenterLocation() {
		final Mockery mockery = new Mockery() { { setImposteriser(ClassImposteriser.INSTANCE); } };
		final Component mockedComponent = this.createMockedComponent(mockery, 0, 0);
		GuiUtil.setCenterLocation(mockedComponent);
		mockery.assertIsSatisfied();
	}
	
	@Test
	public final void setCenterLocationWithOffset() {
		final Mockery mockery = new Mockery() { { setImposteriser(ClassImposteriser.INSTANCE); } };
		final int xOffset = 10;
		final int yOffset = -20;
		final Component mockedComponent = this.createMockedComponent(mockery, xOffset, yOffset);
		GuiUtil.setCenterLocation(mockedComponent, xOffset, yOffset);
		mockery.assertIsSatisfied();
	}
	
	private Component createMockedComponent(final Mockery mockery, final int xOffset, final int yOffset) {
		final Component mockedComponent = mockery.mock(Component.class);
		final int componentWidth = 80;
		final int componentHeight = 50;
		
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		final int expectedX = (screenSize.width - componentWidth) / 2 + xOffset;
        final int expectedY = (screenSize.height - componentHeight) / 2 + yOffset;
        
		mockery.checking(new Expectations() { {
			oneOf(mockedComponent).getWidth();
			will(returnValue(componentWidth));
			oneOf(mockedComponent).getHeight();
			will(returnValue(componentHeight));
			oneOf(mockedComponent).setLocation(expectedX, expectedY);
		}});
		
		return mockedComponent;
	}

}
