package net.sf.josceleton.core.impl.entity.message;

import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.body.BodyPart;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.message.JointMessageTest;

public class JointMessageImplTest extends JointMessageTest {

	/** {@inheritDoc} from {@link JointMessageTest} */
	@Override
	protected final JointMessage createTestee(final User user, final BodyPart jointPart, final Coordinate coordinate) {
		return new JointMessageImpl(user, jointPart, coordinate);
	}
	
}
