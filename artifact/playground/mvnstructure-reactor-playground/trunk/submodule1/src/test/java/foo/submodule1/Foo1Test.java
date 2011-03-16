package foo.submodule1;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

public class Foo1Test {
	
	@Test public void testAdd1() {
		assertThat(new Foo1().add1(3), equalTo(4));
	}
	
}