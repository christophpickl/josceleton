package net.sf.josceleton.commons.test.jmock;

import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;

// final Mockery mockery = new ClassMockery();

public class ClassMockery extends Mockery {
	
	{
		this.setImposteriser(ClassImposteriser.INSTANCE);
	}
	
}
