package net.sf.josceleton.core.api.entity.body;

final class OsceletonJointConstants {
	
	// TODO actually there is also a Osceleton(Message)Jonstants class, providing "new_user", etc...
	
	static final String HEAD = "head";
	static final String NECK = "neck";
	static final String TORSO = "torso";
	
	static final String SHOULDER_LEFT = "l_shoulder";
	static final String SHOULDER_RIGHT = "r_shoulder";
	static final String ELBOW_LEFT = "l_elbow";
	static final String ELBOW_RIGHT = "r_elbow";
	static final String HAND_LEFT = "l_hand";
	static final String HAND_RIGHT = "r_hand";
	static final String HIP_LEFT = "l_hip";
	static final String HIP_RIGHT = "r_hip";
	static final String KNEE_LEFT = "l_knee";
	static final String KNEE_RIGHT = "r_knee";
	static final String ANKLE_LEFT = "l_ankle";
	static final String ANKLE_RIGHT = "r_ankle";
	static final String FOOT_LEFT = "l_foot";
	static final String FOOT_RIGHT = "r_foot";
	
	
	private OsceletonJointConstants() {
		// no instantiation allowed as this is a constant provider only
	}
	
}
