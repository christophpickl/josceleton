package foo.app;

import foo.logic.Logic;
import foo.model.Model;

/**
 * This is class App.
 * 
 * @since 0.1
 */
public class App {
	
	public void doit() {
		System.out.println("App doit() ... VERSION 0.1 -- START");
		new Logic().doit();
		new Model().doit();
		System.out.println("App doit() ... VERSION 0.1 -- END");
	}
	
	@Deprecated
	public String testit() {
		return "a";
	}
	
}