package net.sf.josceleton.core.impl.entity.message;

import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.location.Coordinate;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.message.JointMessageTest;
import net.sf.josceleton.core.api.entity.user.User;

public class JointMessageImplTest extends JointMessageTest {

	/** {@inheritDoc} from {@link JointMessageTest} */
	@Override
	protected final JointMessage createTestee(final User user, final Joint joint, final Coordinate coordinate) {
		return new JointMessageImpl(user, joint, coordinate);
	}
	
}
