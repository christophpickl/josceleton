package foo.submodule2;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

public class Foo2Test {
	
	@Test public void testAdd2() {
		assertThat(new Foo2().add2(3), equalTo(5));
	}
	
}