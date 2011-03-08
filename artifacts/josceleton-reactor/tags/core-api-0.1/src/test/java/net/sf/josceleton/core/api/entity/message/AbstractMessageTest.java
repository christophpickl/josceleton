package net.sf.josceleton.core.api.entity.message;

import net.sf.josceleton.commons.test.AbstractEqualsTest;

// MINOR @TEST DRY MessageTests should extends EqualsTest AND MockeryTest

public abstract class AbstractMessageTest<M extends GenericMessage>
	extends AbstractEqualsTest<M> {
	// marker interface/class, whatever ...
}
