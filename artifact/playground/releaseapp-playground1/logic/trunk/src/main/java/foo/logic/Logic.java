package foo.logic;

import foo.model.Model;

/**
 * This is class Logic.
 * 
 * @since 0.1
 */
public class Logic {

	public void doit() {
		System.out.println("Logic doit() ... VERSION 0.4 -- START");
		new Model().doit();
		System.out.println("Logic doit() ... VERSION 0.4 -- END");
		
	}
	
	@Deprecated
	public String testit() {
		return "a";
	}
	
}