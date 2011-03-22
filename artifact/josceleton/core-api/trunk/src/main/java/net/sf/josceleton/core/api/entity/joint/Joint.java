package net.sf.josceleton.core.api.entity.joint;

/**
 * This class represents joints in the human body at which two parts of the skeleton are fitted together.
 * 
 * Available joints (until now) are:
 * <ul>
 *   <li>Head</li>
 *   <li>Neck</li>
 *   <li>Torso</li>
 *   <li>Left/Right Shoulder</li>
 *   <li>Left/Right Elbow</li>
 *   <li>Left/Right Hand</li>
 *   <li>Left/Right Hip</li>
 *   <li>Left/Right Knee</li>
 *   <li>Left/Right Ankle</li>
 *   <li>Left/Right Foot</li>
 * </ul>
 * 
 * @see Joints
 */
public interface Joint {
	
	/** @return a user display friendly text representation of this joint; e.g.: "Head" or "Left Hand". */
	String getLabel();
	
	/** @return the unique name of a joint as known from osceleton; e.g.: "head" or "l_hand". */
	String getOsceletonId();
	
	// FIXME !!!!!!!!!!!!!!!!!!!!! ANKLE joint rausnehmen!
	
}
