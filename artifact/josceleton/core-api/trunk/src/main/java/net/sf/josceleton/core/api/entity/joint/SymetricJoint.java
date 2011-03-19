package net.sf.josceleton.core.api.entity.joint;

import net.sf.josceleton.core.api.entity.joint.JointParts.LeftJoint;
import net.sf.josceleton.core.api.entity.joint.JointParts.RightJoint;

/**
 * @since 0.4
 */
interface SymetricJoint<J, LJ extends LeftJoint<J>, RJ extends RightJoint<J>> {

	/** This is part of the API as it will be visibile to user. */
	LJ LEFT();
	
	/** This is part of the API as it will be visibile to user. */
	RJ RIGHT();
	
}
